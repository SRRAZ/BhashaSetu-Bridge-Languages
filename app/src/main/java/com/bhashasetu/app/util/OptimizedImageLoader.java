package com.bhashasetu.app.util;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bhashasetu.app.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Optimized image loading utility with memory and disk caching.
 * Efficiently manages memory usage and provides smooth image loading experience.
 */
public class OptimizedImageLoader {
    private static final String TAG = "OptimizedImageLoader";
    
    // Instance for singleton pattern
    private static OptimizedImageLoader instance;
    
    // Thread pool for image loading
    private final ExecutorService executorService;
    
    // Memory cache for loaded images
    private final LruCache<String, Bitmap> memoryCache;
    
    // Map of active loading tasks to avoid duplicate loading
    private final Map<ImageView, String> imageViewMap;
    
    // Map of running tasks for cancellation
    private final Map<String, Future<?>> runningTasks;
    
    // Application context
    private final Context context;
    
    // Disk cache directory
    private File diskCacheDir;
    
    // Default placeholder and error resources
    private final int defaultPlaceholderResId;
    private final int defaultErrorResId;
    
    /**
     * Private constructor for singleton pattern.
     *
     * @param context Application context
     */
    private OptimizedImageLoader(Context context) {
        this.context = context.getApplicationContext();
        
        // Create thread pool for background operations
        this.executorService = Executors.newFixedThreadPool(
                Math.max(2, Runtime.getRuntime().availableProcessors() - 1));
        
        // Get max available memory
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        
        // Use 1/8th of the available memory for this memory cache
        final int cacheSize = maxMemory / 8;
        
        // Create memory cache
        memoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than number of items
                return getBitmapSize(bitmap) / 1024;
            }
            
            @Override
            protected void entryRemoved(boolean evicted, String key, 
                                      Bitmap oldValue, Bitmap newValue) {
                // Optional: Could implement a secondary cache here using SoftReference
            }
        };
        
        // Create image view map with weak references to prevent memory leaks
        imageViewMap = Collections.synchronizedMap(new WeakHashMap<>());
        
        // Create running tasks map
        runningTasks = Collections.synchronizedMap(new WeakHashMap<>());
        
        // Initialize disk cache directory
        initDiskCache();
        
        // Set default resources
        defaultPlaceholderResId = R.drawable.placeholder_image;
        defaultErrorResId = R.drawable.error_image;
    }
    
    /**
     * Get singleton instance.
     *
     * @param context Context
     * @return OptimizedImageLoader instance
     */
    public static OptimizedImageLoader getInstance(Context context) {
        if (instance == null) {
            synchronized (OptimizedImageLoader.class) {
                if (instance == null) {
                    instance = new OptimizedImageLoader(context);
                }
            }
        }
        return instance;
    }
    
    /**
     * Initialize disk cache directory.
     */
    private void initDiskCache() {
        try {
            // Check if external cache is available
            File cacheDir = context.getExternalCacheDir();
            if (cacheDir == null) {
                cacheDir = context.getCacheDir();
            }
            
            diskCacheDir = new File(cacheDir, "images");
            if (!diskCacheDir.exists()) {
                diskCacheDir.mkdirs();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error initializing disk cache", e);
            diskCacheDir = null;
        }
    }
    
    /**
     * Clear memory cache.
     */
    public void clearMemoryCache() {
        if (memoryCache != null) {
            memoryCache.evictAll();
        }
    }
    
    /**
     * Clear disk cache.
     */
    public void clearDiskCache() {
        try {
            if (diskCacheDir != null && diskCacheDir.exists()) {
                File[] files = diskCacheDir.listFiles();
                if (files != null) {
                    for (File file : files) {
                        file.delete();
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error clearing disk cache", e);
        }
    }
    
    /**
     * Get bitmap size based on Android version.
     *
     * @param bitmap Bitmap to measure
     * @return Size in bytes
     */
    private int getBitmapSize(Bitmap bitmap) {
        if (bitmap == null) return 0;
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return bitmap.getAllocationByteCount();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return bitmap.getByteCount();
        } else {
            return bitmap.getRowBytes() * bitmap.getHeight();
        }
    }
    
    /**
     * Generate cache key from URL.
     *
     * @param url Image URL
     * @return Cache key
     */
    private String getCacheKey(String url) {
        return String.valueOf(url.hashCode());
    }
    
    /**
     * Add bitmap to memory cache.
     *
     * @param key    Cache key
     * @param bitmap Bitmap to cache
     */
    private void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (key == null || bitmap == null) return;
        
        if (getBitmapFromMemoryCache(key) == null) {
            memoryCache.put(key, bitmap);
        }
    }
    
    /**
     * Get bitmap from memory cache.
     *
     * @param key Cache key
     * @return Cached bitmap or null if not found
     */
    private Bitmap getBitmapFromMemoryCache(String key) {
        if (key == null) return null;
        return memoryCache.get(key);
    }
    
    /**
     * Get disk cache file for key.
     *
     * @param key Cache key
     * @return Cache file or null if disk cache not available
     */
    private File getDiskCacheFile(String key) {
        if (diskCacheDir == null) return null;
        return new File(diskCacheDir, key);
    }
    
    /**
     * Save bitmap to disk cache.
     *
     * @param key    Cache key
     * @param bitmap Bitmap to cache
     */
    private void saveBitmapToDiskCache(String key, Bitmap bitmap) {
        if (key == null || bitmap == null || diskCacheDir == null) return;
        
        File file = getDiskCacheFile(key);
        if (file == null) return;
        
        try (FileOutputStream fos = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
        } catch (IOException e) {
            Log.e(TAG, "Error saving bitmap to disk cache", e);
        }
    }
    
    /**
     * Load bitmap from disk cache.
     *
     * @param key Cache key
     * @return Bitmap or null if not found
     */
    private Bitmap loadBitmapFromDiskCache(String key) {
        if (key == null || diskCacheDir == null) return null;
        
        File file = getDiskCacheFile(key);
        if (file == null || !file.exists()) return null;
        
        try {
            return BitmapFactory.decodeFile(file.getAbsolutePath());
        } catch (Exception e) {
            Log.e(TAG, "Error loading bitmap from disk cache", e);
            return null;
        }
    }
    
    /**
     * Download bitmap from URL.
     *
     * @param urlString URL to download from
     * @return Downloaded bitmap or null on failure
     */
    private Bitmap downloadBitmap(String urlString) {
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        
        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(15000);
            urlConnection.setReadTimeout(15000);
            urlConnection.setDoInput(true);
            urlConnection.connect();
            
            if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }
            
            inputStream = urlConnection.getInputStream();
            return BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            Log.e(TAG, "Error downloading image: " + urlString, e);
            return null;
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            } catch (IOException ignored) {
            }
        }
    }
    
    /**
     * Calculate sample size for bitmap decoding.
     *
     * @param options   BitmapFactory options
     * @param reqWidth  Required width
     * @param reqHeight Required height
     * @return Sample size
     */
    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
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
     * Decode bitmap from file with sampling to save memory.
     *
     * @param file      File to decode
     * @param reqWidth  Required width
     * @param reqHeight Required height
     * @return Decoded bitmap
     */
    private Bitmap decodeSampledBitmapFromFile(File file, int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(file.getAbsolutePath(), options);
    }
    
    /**
     * Decode bitmap from URL with sampling to save memory.
     *
     * @param url       URL to decode
     * @param reqWidth  Required width
     * @param reqHeight Required height
     * @return Decoded bitmap
     */
    private Bitmap downloadSampledBitmap(String url, int reqWidth, int reqHeight) {
        try {
            // Download to a temporary file
            File tempFile = File.createTempFile("temp_", ".jpg", diskCacheDir);
            
            // Download the image
            Bitmap downloadedBitmap = downloadBitmap(url);
            if (downloadedBitmap == null) {
                return null;
            }
            
            // Save to the temporary file
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                downloadedBitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                fos.flush();
            }
            
            // Decode with sampling
            Bitmap sampledBitmap = decodeSampledBitmapFromFile(tempFile, reqWidth, reqHeight);
            
            // Delete the temporary file
            tempFile.delete();
            
            return sampledBitmap;
        } catch (IOException e) {
            Log.e(TAG, "Error downloading sampled bitmap", e);
            return null;
        }
    }
    
    /**
     * Load bitmap with memory management.
     *
     * @param url       URL to load from
     * @param reqWidth  Required width
     * @param reqHeight Required height
     * @return Loaded bitmap or null on failure
     */
    private Bitmap loadBitmap(String url, int reqWidth, int reqHeight) {
        if (url == null) return null;
        
        final String key = getCacheKey(url);
        
        // Check memory cache first
        Bitmap cachedBitmap = getBitmapFromMemoryCache(key);
        if (cachedBitmap != null) {
            return cachedBitmap;
        }
        
        // Check disk cache next
        Bitmap diskCachedBitmap = loadBitmapFromDiskCache(key);
        if (diskCachedBitmap != null) {
            // Add to memory cache
            addBitmapToMemoryCache(key, diskCachedBitmap);
            return diskCachedBitmap;
        }
        
        // Download and sample the bitmap
        Bitmap downloadedBitmap = downloadSampledBitmap(url, reqWidth, reqHeight);
        if (downloadedBitmap != null) {
            // Add to caches
            addBitmapToMemoryCache(key, downloadedBitmap);
            saveBitmapToDiskCache(key, downloadedBitmap);
        }
        
        return downloadedBitmap;
    }
    
    /**
     * Convert DP to pixels.
     *
     * @param context Context
     * @param dp      Value in DP
     * @return Value in pixels
     */
    private int dpToPx(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
    
    /**
     * Load image into ImageView from URL.
     *
     * @param imageView ImageView to load into
     * @param url       URL to load from
     */
    public void loadImage(final ImageView imageView, final String url) {
        loadImage(imageView, url, defaultPlaceholderResId, defaultErrorResId);
    }
    
    /**
     * Load image into ImageView from URL with custom placeholders.
     *
     * @param imageView        ImageView to load into
     * @param url              URL to load from
     * @param placeholderResId Placeholder resource ID
     * @param errorResId       Error resource ID
     */
    public void loadImage(final ImageView imageView, final String url,
                        final int placeholderResId, final int errorResId) {
        if (imageView == null) return;
        
        // Cancel any existing loading tasks for this ImageView
        cancelLoadingForImageView(imageView);
        
        if (url == null) {
            imageView.setImageResource(errorResId);
            return;
        }
        
        // Save the URL for this ImageView
        imageViewMap.put(imageView, url);
        
        final String key = getCacheKey(url);
        
        // Check memory cache first (UI thread)
        Bitmap cachedBitmap = getBitmapFromMemoryCache(key);
        if (cachedBitmap != null) {
            imageView.setImageBitmap(cachedBitmap);
            return;
        }
        
        // Set placeholder while loading
        if (placeholderResId != 0) {
            imageView.setImageResource(placeholderResId);
        }
        
        // Create loading task
        Runnable loadingTask = () -> {
            // Get required dimensions based on ImageView
            final int width = imageView.getWidth() > 0 ? 
                imageView.getWidth() : dpToPx(context, 100);
            final int height = imageView.getHeight() > 0 ? 
                imageView.getHeight() : dpToPx(context, 100);
            
            // Load bitmap
            final Bitmap bitmap = loadBitmap(url, width, height);
            
            // Update UI on main thread
            imageView.post(() -> {
                // Check if ImageView is still targeted for this URL
                final String currentUrl = imageViewMap.get(imageView);
                if (url.equals(currentUrl)) {
                    if (bitmap != null) {
                        imageView.setImageBitmap(bitmap);
                    } else {
                        imageView.setImageResource(errorResId);
                    }
                    // Remove from running tasks
                    runningTasks.remove(url + "@" + imageView.hashCode());
                }
            });
        };
        
        // Start loading task
        final String taskKey = url + "@" + imageView.hashCode();
        Future<?> future = executorService.submit(loadingTask);
        runningTasks.put(taskKey, future);
    }
    
    /**
     * Load image from local resource.
     *
     * @param imageView  ImageView to load into
     * @param resourceId Resource ID to load
     */
    public void loadResource(final ImageView imageView, final int resourceId) {
        if (imageView == null) return;
        
        // Cancel any existing loading tasks for this ImageView
        cancelLoadingForImageView(imageView);
        
        // Clear URL mapping for this ImageView
        imageViewMap.remove(imageView);
        
        // Create loading task
        Runnable loadingTask = () -> {
            // Load bitmap with sampling to save memory
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(context.getResources(), resourceId, options);
            
            final int width = imageView.getWidth() > 0 ? 
                imageView.getWidth() : dpToPx(context, 100);
            final int height = imageView.getHeight() > 0 ? 
                imageView.getHeight() : dpToPx(context, 100);
            
            options.inSampleSize = calculateInSampleSize(options, width, height);
            options.inJustDecodeBounds = false;
            
            final Bitmap bitmap = BitmapFactory.decodeResource(
                    context.getResources(), resourceId, options);
            
            // Update UI on main thread
            imageView.post(() -> {
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                } else {
                    imageView.setImageResource(defaultErrorResId);
                }
            });
        };
        
        // Start loading task
        executorService.submit(loadingTask);
    }
    
    /**
     * Load image from file.
     *
     * @param imageView ImageView to load into
     * @param file      File to load from
     */
    public void loadFile(final ImageView imageView, final File file) {
        if (imageView == null || file == null || !file.exists()) {
            if (imageView != null) {
                imageView.setImageResource(defaultErrorResId);
            }
            return;
        }
        
        // Cancel any existing loading tasks for this ImageView
        cancelLoadingForImageView(imageView);
        
        // Clear URL mapping for this ImageView
        imageViewMap.remove(imageView);
        
        // Create loading task
        Runnable loadingTask = () -> {
            final int width = imageView.getWidth() > 0 ? 
                imageView.getWidth() : dpToPx(context, 100);
            final int height = imageView.getHeight() > 0 ? 
                imageView.getHeight() : dpToPx(context, 100);
            
            // Load bitmap with sampling
            final Bitmap bitmap = decodeSampledBitmapFromFile(file, width, height);
            
            // Update UI on main thread
            imageView.post(() -> {
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                } else {
                    imageView.setImageResource(defaultErrorResId);
                }
            });
        };
        
        // Start loading task
        executorService.submit(loadingTask);
    }
    
    /**
     * Load image from Uri.
     *
     * @param imageView ImageView to load into
     * @param uri       Uri to load from
     */
    public void loadUri(final ImageView imageView, final Uri uri) {
        if (imageView == null || uri == null) {
            if (imageView != null) {
                imageView.setImageResource(defaultErrorResId);
            }
            return;
        }
        
        // Check if it's a network Uri
        String scheme = uri.getScheme();
        if ("http".equals(scheme) || "https".equals(scheme)) {
            loadImage(imageView, uri.toString());
            return;
        }
        
        // Cancel any existing loading tasks for this ImageView
        cancelLoadingForImageView(imageView);
        
        // Clear URL mapping for this ImageView
        imageViewMap.remove(imageView);
        
        // Set placeholder while loading
        imageView.setImageResource(defaultPlaceholderResId);
        
        // Create loading task
        Runnable loadingTask = () -> {
            try {
                final int width = imageView.getWidth() > 0 ? 
                    imageView.getWidth() : dpToPx(context, 100);
                final int height = imageView.getHeight() > 0 ? 
                    imageView.getHeight() : dpToPx(context, 100);
                
                // First decode with inJustDecodeBounds=true to check dimensions
                final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                
                InputStream inputStream = context.getContentResolver().openInputStream(uri);
                BitmapFactory.decodeStream(inputStream, null, options);
                if (inputStream != null) {
                    inputStream.close();
                }
                
                // Calculate inSampleSize
                options.inSampleSize = calculateInSampleSize(options, width, height);
                
                // Decode bitmap with inSampleSize set
                options.inJustDecodeBounds = false;
                inputStream = context.getContentResolver().openInputStream(uri);
                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
                if (inputStream != null) {
                    inputStream.close();
                }
                
                // Update UI on main thread
                imageView.post(() -> {
                    if (bitmap != null) {
                        imageView.setImageBitmap(bitmap);
                    } else {
                        imageView.setImageResource(defaultErrorResId);
                    }
                });
            } catch (Exception e) {
                Log.e(TAG, "Error loading image from Uri: " + uri, e);
                imageView.post(() -> imageView.setImageResource(defaultErrorResId));
            }
        };
        
        // Start loading task
        executorService.submit(loadingTask);
    }
    
    /**
     * Preload image into cache.
     *
     * @param url URL to preload
     */
    public void preload(final String url) {
        if (url == null) return;
        
        final String key = getCacheKey(url);
        
        // Check if already in memory cache
        if (getBitmapFromMemoryCache(key) != null) {
            return;
        }
        
        // Create preloading task
        Runnable preloadTask = () -> {
            // Typical dimensions for preloading
            final int width = dpToPx(context, 100);
            final int height = dpToPx(context, 100);
            
            // Load bitmap
            loadBitmap(url, width, height);
        };
        
        // Start preloading task
        executorService.submit(preloadTask);
    }
    
    /**
     * Cancel loading task for ImageView.
     *
     * @param imageView ImageView to cancel loading for
     */
    private void cancelLoadingForImageView(ImageView imageView) {
        if (imageView == null) return;
        
        String url = imageViewMap.get(imageView);
        if (url != null) {
            String taskKey = url + "@" + imageView.hashCode();
            Future<?> future = runningTasks.get(taskKey);
            if (future != null) {
                future.cancel(true);
                runningTasks.remove(taskKey);
            }
        }
    }
    
    /**
     * Get the memory cache hit percentage.
     *
     * @return Hit percentage (0-100)
     */
    public int getMemoryCacheHitPercentage() {
        int hitCount = memoryCache.hitCount();
        int missCount = memoryCache.missCount();
        int totalCount = hitCount + missCount;
        
        if (totalCount == 0) return 0;
        return (hitCount * 100) / totalCount;
    }
    
    /**
     * Calculate ideal cache size based on device memory class.
     *
     * @param context Context
     * @return Ideal cache size in KB
     */
    private int calculateMemoryCacheSize(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        boolean largeHeap = (context.getApplicationInfo().flags & 
                           android.content.pm.ApplicationInfo.FLAG_LARGE_HEAP) != 0;
        int memoryClass = largeHeap ? am.getLargeMemoryClass() : am.getMemoryClass();
        
        // Use 1/8th of the available memory for this memory cache
        return (memoryClass * 1024) / 8;
    }
    
    /**
     * Get a bitmap synchronously from cache or load it.
     *
     * @param url URL to load from
     * @return Loaded bitmap or null on failure
     */
    public Bitmap getBitmapSync(String url) {
        if (url == null) return null;
        
        final String key = getCacheKey(url);
        
        // Check memory cache first
        Bitmap cachedBitmap = getBitmapFromMemoryCache(key);
        if (cachedBitmap != null) {
            return cachedBitmap;
        }
        
        // Check disk cache next
        Bitmap diskCachedBitmap = loadBitmapFromDiskCache(key);
        if (diskCachedBitmap != null) {
            // Add to memory cache
            addBitmapToMemoryCache(key, diskCachedBitmap);
            return diskCachedBitmap;
        }
        
        // Download the bitmap
        Bitmap downloadedBitmap = downloadBitmap(url);
        if (downloadedBitmap != null) {
            // Add to caches
            addBitmapToMemoryCache(key, downloadedBitmap);
            saveBitmapToDiskCache(key, downloadedBitmap);
        }
        
        return downloadedBitmap;
    }
    
    /**
     * Get a drawable from bitmap.
     *
     * @param bitmap Bitmap to convert
     * @return BitmapDrawable
     */
    @Nullable
    public Drawable getDrawableFromBitmap(@Nullable Bitmap bitmap) {
        if (bitmap == null) return null;
        return new BitmapDrawable(context.getResources(), bitmap);
    }
    
    /**
     * Shutdown the image loader and release resources.
     */
    public void shutdown() {
        executorService.shutdown();
        clearMemoryCache();
    }
}