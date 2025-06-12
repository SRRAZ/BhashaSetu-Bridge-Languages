package com.bhashasetu.app.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import com.bhashasetu.app.R;
import com.bhashasetu.app.cache.CacheManager;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Efficient image loading and caching utility.
 * Handles loading images from URL, file, or resources with memory caching,
 * disk caching via CacheManager, and bitmap pooling for reuse.
 */
public class ImageLoader {
    private static final String TAG = "ImageLoader";
    
    private static final int MAX_CACHE_SIZE = (int) (Runtime.getRuntime().maxMemory() / 8); // 1/8 of max memory
    private static final int MAX_POOL_SIZE = 10; // Max bitmaps in the reuse pool
    
    private final Context context;
    private final CacheManager cacheManager;
    private final LruCache<String, Bitmap> memoryCache;
    private final ConcurrentHashMap<String, BitmapLoadTask> taskMap = new ConcurrentHashMap<>();
    private final Executor executor;
    private final BitmapPool bitmapPool = new BitmapPool(MAX_POOL_SIZE);
    
    /**
     * Constructor
     * @param context Application context
     * @param cacheManager CacheManager instance for disk caching
     */
    public ImageLoader(Context context, CacheManager cacheManager) {
        this.context = context.getApplicationContext();
        this.cacheManager = cacheManager;
        this.executor = Executors.newFixedThreadPool(3);
        
        // Initialize memory cache
        memoryCache = new LruCache<String, Bitmap>(MAX_CACHE_SIZE) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // Calculate size in KB
                return bitmap.getByteCount() / 1024;
            }
            
            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                if (evicted) {
                    // Add to bitmap pool for reuse
                    bitmapPool.put(oldValue);
                }
            }
        };
    }
    
    /**
     * Load an image from URL into an ImageView
     * @param url Image URL
     * @param imageView Target ImageView
     * @param placeholderResId Placeholder resource ID
     * @param errorResId Error resource ID
     */
    public void loadImage(String url, ImageView imageView, @DrawableRes int placeholderResId, @DrawableRes int errorResId) {
        if (url == null || url.isEmpty()) {
            imageView.setImageResource(errorResId);
            return;
        }
        
        // Set a tag on the ImageView to track which URL it's loading
        imageView.setTag(R.id.tag_image_url, url);
        
        // Check memory cache first
        Bitmap cachedBitmap = memoryCache.get(url);
        if (cachedBitmap != null && !cachedBitmap.isRecycled()) {
            imageView.setImageBitmap(cachedBitmap);
            return;
        }
        
        // Set placeholder while loading
        imageView.setImageResource(placeholderResId);
        
        // Check if already loading
        BitmapLoadTask existingTask = taskMap.get(url);
        if (existingTask != null) {
            existingTask.addImageView(imageView);
            return;
        }
        
        // Create new task
        BitmapLoadTask task = new BitmapLoadTask(url, imageView, errorResId);
        taskMap.put(url, task);
        
        // Execute the task
        executor.execute(task);
    }
    
    /**
     * Load an image from URL with default placeholder and error resources
     * @param url Image URL
     * @param imageView Target ImageView
     */
    public void loadImage(String url, ImageView imageView) {
        loadImage(url, imageView, R.drawable.ic_image_placeholder, R.drawable.ic_image_error);
    }
    
    /**
     * Load an image from file into an ImageView
     * @param file Image file
     * @param imageView Target ImageView
     * @param placeholderResId Placeholder resource ID
     * @param errorResId Error resource ID
     */
    public void loadImage(File file, ImageView imageView, @DrawableRes int placeholderResId, @DrawableRes int errorResId) {
        if (file == null || !file.exists()) {
            imageView.setImageResource(errorResId);
            return;
        }
        
        String filePath = file.getAbsolutePath();
        imageView.setTag(R.id.tag_image_url, filePath);
        
        // Check memory cache first
        Bitmap cachedBitmap = memoryCache.get(filePath);
        if (cachedBitmap != null && !cachedBitmap.isRecycled()) {
            imageView.setImageBitmap(cachedBitmap);
            return;
        }
        
        // Set placeholder while loading
        imageView.setImageResource(placeholderResId);
        
        // Execute on background thread
        executor.execute(() -> {
            try {
                // Get bitmap options to check dimensions first
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(filePath, options);
                
                // Calculate sample size based on target size
                int targetSize = 512; // Target width/height
                options.inSampleSize = calculateInSampleSize(options, targetSize, targetSize);
                
                // Try to reuse a bitmap from the pool
                options.inJustDecodeBounds = false;
                options.inMutable = true; // Make bitmap mutable for reuse
                options.inBitmap = bitmapPool.get(options.outWidth, options.outHeight);
                
                final Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
                
                if (bitmap != null) {
                    // Add to memory cache
                    memoryCache.put(filePath, bitmap);
                    
                    // Check if the ImageView is still waiting for this file
                    AppExecutors.getInstance().mainThread().execute(() -> {
                        if (imageView != null && filePath.equals(imageView.getTag(R.id.tag_image_url))) {
                            imageView.setImageBitmap(bitmap);
                        }
                    });
                } else {
                    // Set error image
                    AppExecutors.getInstance().mainThread().execute(() -> {
                        if (imageView != null && filePath.equals(imageView.getTag(R.id.tag_image_url))) {
                            imageView.setImageResource(errorResId);
                        }
                    });
                }
            } catch (Exception e) {
                Log.e(TAG, "Error loading image from file: " + filePath, e);
                AppExecutors.getInstance().mainThread().execute(() -> {
                    if (imageView != null && filePath.equals(imageView.getTag(R.id.tag_image_url))) {
                        imageView.setImageResource(errorResId);
                    }
                });
            }
        });
    }
    
    /**
     * Load an image from a resource ID into an ImageView
     * @param resourceId Image resource ID
     * @param imageView Target ImageView
     */
    public void loadResource(@DrawableRes int resourceId, ImageView imageView) {
        String key = "res:" + resourceId;
        imageView.setTag(R.id.tag_image_url, key);
        
        // Check memory cache first
        Bitmap cachedBitmap = memoryCache.get(key);
        if (cachedBitmap != null && !cachedBitmap.isRecycled()) {
            imageView.setImageBitmap(cachedBitmap);
            return;
        }
        
        // Execute on background thread
        executor.execute(() -> {
            try {
                // Get bitmap options to check dimensions first
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeResource(context.getResources(), resourceId, options);
                
                // Calculate sample size based on target size
                int targetSize = 512; // Target width/height
                options.inSampleSize = calculateInSampleSize(options, targetSize, targetSize);
                
                // Try to reuse a bitmap from the pool
                options.inJustDecodeBounds = false;
                options.inMutable = true; // Make bitmap mutable for reuse
                options.inBitmap = bitmapPool.get(options.outWidth, options.outHeight);
                
                final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options);
                
                if (bitmap != null) {
                    // Add to memory cache
                    memoryCache.put(key, bitmap);
                    
                    // Check if the ImageView is still waiting for this resource
                    AppExecutors.getInstance().mainThread().execute(() -> {
                        if (imageView != null && key.equals(imageView.getTag(R.id.tag_image_url))) {
                            imageView.setImageBitmap(bitmap);
                        }
                    });
                }
            } catch (Exception e) {
                Log.e(TAG, "Error loading image from resource: " + resourceId, e);
            }
        });
    }
    
    /**
     * Clear memory cache
     */
    public void clearMemoryCache() {
        memoryCache.evictAll();
    }
    
    /**
     * Prefetch an image from URL into memory and disk cache
     * @param url Image URL
     */
    public void prefetchImage(String url) {
        if (url == null || url.isEmpty()) {
            return;
        }
        
        // Check if already in memory cache
        if (memoryCache.get(url) != null) {
            return;
        }
        
        // Create a filename from URL
        String filename = String.valueOf(url.hashCode()) + ".jpg";
        
        // Check if already loading
        if (taskMap.containsKey(url)) {
            return;
        }
        
        // Execute on background thread
        executor.execute(() -> {
            try {
                // First check disk cache via CacheManager
                String cachedPath = cacheManager.getCachedImagePath(url);
                
                if (cachedPath != null) {
                    // Load from disk and put in memory cache
                    Bitmap bitmap = BitmapFactory.decodeFile(cachedPath);
                    if (bitmap != null) {
                        memoryCache.put(url, bitmap);
                    }
                } else {
                    // Not in disk cache, fetch and cache
                    cacheManager.cacheImageFile(url, filename, 30, null);
                }
            } catch (Exception e) {
                Log.e(TAG, "Error prefetching image: " + url, e);
            } finally {
                // Remove from task map
                taskMap.remove(url);
            }
        });
    }
    
    /**
     * Calculate appropriate sample size for bitmap loading
     * @param options BitmapFactory.Options
     * @param reqWidth Requested width
     * @param reqHeight Requested height
     * @return Sample size (power of 2)
     */
    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        
        return inSampleSize;
    }
    
    /**
     * Task for loading bitmaps from URL
     */
    private class BitmapLoadTask implements Runnable {
        private final String url;
        private final int errorResId;
        private final ConcurrentHashMap<ImageView, WeakReference<ImageView>> imageViews = new ConcurrentHashMap<>();
        
        public BitmapLoadTask(String url, ImageView imageView, int errorResId) {
            this.url = url;
            this.errorResId = errorResId;
            addImageView(imageView);
        }
        
        public void addImageView(ImageView imageView) {
            if (imageView != null) {
                this.imageViews.put(imageView, new WeakReference<>(imageView));
            }
        }
        
        @Override
        public void run() {
            try {
                // First check disk cache via CacheManager
                String cachedPath = cacheManager.getCachedImagePath(url);
                
                if (cachedPath != null) {
                    // Get bitmap options to check dimensions first
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(cachedPath, options);
                    
                    // Calculate sample size based on target size
                    int targetSize = 512; // Target width/height
                    options.inSampleSize = calculateInSampleSize(options, targetSize, targetSize);
                    
                    // Try to reuse a bitmap from the pool
                    options.inJustDecodeBounds = false;
                    options.inMutable = true; // Make bitmap mutable for reuse
                    options.inBitmap = bitmapPool.get(options.outWidth, options.outHeight);
                    
                    Bitmap bitmap = BitmapFactory.decodeFile(cachedPath, options);
                    
                    if (bitmap != null) {
                        // Add to memory cache
                        memoryCache.put(url, bitmap);
                        
                        // Set bitmap to all associated ImageViews
                        setImageBitmapToViews(bitmap);
                    } else {
                        setErrorResource();
                    }
                } else {
                    // Create a filename from URL
                    String filename = String.valueOf(url.hashCode()) + ".jpg";
                    
                    // Cache the image file asynchronously
                    cacheManager.cacheImageFile(url, filename, 30, (success, filePath) -> {
                        if (success && filePath != null) {
                            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                            if (bitmap != null) {
                                // Add to memory cache
                                memoryCache.put(url, bitmap);
                                
                                // Set bitmap to all associated ImageViews
                                setImageBitmapToViews(bitmap);
                            } else {
                                setErrorResource();
                            }
                        } else {
                            setErrorResource();
                        }
                    });
                }
            } catch (Exception e) {
                Log.e(TAG, "Error loading image: " + url, e);
                setErrorResource();
            } finally {
                // Remove task from map
                taskMap.remove(url);
            }
        }
        
        private void setImageBitmapToViews(final Bitmap bitmap) {
            AppExecutors.getInstance().mainThread().execute(() -> {
                for (WeakReference<ImageView> weakReference : imageViews.values()) {
                    ImageView imageView = weakReference.get();
                    if (imageView != null && url.equals(imageView.getTag(R.id.tag_image_url))) {
                        imageView.setImageBitmap(bitmap);
                    }
                }
            });
        }
        
        private void setErrorResource() {
            AppExecutors.getInstance().mainThread().execute(() -> {
                for (WeakReference<ImageView> weakReference : imageViews.values()) {
                    ImageView imageView = weakReference.get();
                    if (imageView != null && url.equals(imageView.getTag(R.id.tag_image_url))) {
                        imageView.setImageResource(errorResId);
                    }
                }
            });
        }
    }
    
    /**
     * Bitmap pool for reusing bitmaps
     */
    private static class BitmapPool {
        private final LruCache<Integer, Bitmap> pool;
        
        public BitmapPool(int maxSize) {
            pool = new LruCache<Integer, Bitmap>(maxSize) {
                @Override
                protected int sizeOf(Integer key, Bitmap bitmap) {
                    return 1; // Count by number of bitmaps, not size
                }
            };
        }
        
        /**
         * Get a bitmap from the pool for reuse
         * @param width Required width
         * @param height Required height
         * @return Bitmap for reuse or null
         */
        public Bitmap get(int width, int height) {
            // Create a key based on dimensions
            int key = width * 31 + height;
            
            // Remove and return the bitmap
            return pool.remove(key);
        }
        
        /**
         * Put a bitmap in the pool for later reuse
         * @param bitmap Bitmap to pool
         */
        public void put(Bitmap bitmap) {
            if (bitmap != null && bitmap.isMutable()) {
                int key = bitmap.getWidth() * 31 + bitmap.getHeight();
                pool.put(key, bitmap);
            }
        }
        
        /**
         * Clear the pool
         */
        public void clear() {
            pool.evictAll();
        }
    }
}