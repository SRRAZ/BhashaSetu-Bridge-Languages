package com.bhashasetu.app.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import com.bhashasetu.app.R;
import com.bhashasetu.app.cache.CacheManager;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Image loader with cache awareness for offline usage.
 * Loads images from cache first, then from network if needed.
 */
public class CacheAwareImageLoader {
    
    private static final int DEFAULT_PLACEHOLDER = R.drawable.placeholder_image;
    private static final int DEFAULT_ERROR = R.drawable.error_image;
    
    private static CacheAwareImageLoader instance;
    
    private Context context;
    private CacheManager cacheManager;
    private NetworkUtils networkUtils;
    private Executor executor;
    
    // Memory cache - simple LRU for this implementation
    private android.util.LruCache<String, Bitmap> memoryCache;
    
    private CacheAwareImageLoader(Context context) {
        this.context = context.getApplicationContext();
        this.cacheManager = CacheManager.getInstance(context);
        this.networkUtils = new NetworkUtils(context);
        this.executor = Executors.newFixedThreadPool(3);
        
        // Initialize memory cache with 1/8 of available memory
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 8;
        
        memoryCache = new android.util.LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // Size in kilobytes
                return bitmap.getByteCount() / 1024;
            }
        };
    }
    
    /**
     * Get the singleton instance of the image loader
     * @param context Application context
     * @return CacheAwareImageLoader instance
     */
    public static synchronized CacheAwareImageLoader getInstance(Context context) {
        if (instance == null) {
            instance = new CacheAwareImageLoader(context);
        }
        return instance;
    }
    
    /**
     * Load an image into an ImageView
     * @param url Image URL
     * @param imageView Target ImageView
     */
    public void loadImage(String url, ImageView imageView) {
        loadImage(url, imageView, DEFAULT_PLACEHOLDER, DEFAULT_ERROR);
    }
    
    /**
     * Load an image into an ImageView with custom placeholders
     * @param url Image URL
     * @param imageView Target ImageView
     * @param placeholderResId Placeholder drawable resource ID
     * @param errorResId Error drawable resource ID
     */
    public void loadImage(String url, ImageView imageView, 
                          @DrawableRes int placeholderResId, 
                          @DrawableRes int errorResId) {
        if (url == null || url.isEmpty() || imageView == null) {
            return;
        }
        
        // Set placeholder immediately
        imageView.setImageResource(placeholderResId);
        
        // Tag the ImageView with the URL to prevent images from loading into wrong views
        imageView.setTag(R.id.image_loader_tag, url);
        
        // Check memory cache first
        Bitmap cachedBitmap = getBitmapFromMemoryCache(url);
        if (cachedBitmap != null) {
            imageView.setImageBitmap(cachedBitmap);
            return;
        }
        
        // Load asynchronously
        executor.execute(new ImageLoadTask(context, url, new WeakReference<>(imageView), 
                placeholderResId, errorResId));
    }
    
    /**
     * Add a bitmap to the memory cache
     * @param key Cache key
     * @param bitmap Bitmap to cache
     */
    private void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (key != null && bitmap != null && getBitmapFromMemoryCache(key) == null) {
            memoryCache.put(key, bitmap);
        }
    }
    
    /**
     * Get a bitmap from the memory cache
     * @param key Cache key
     * @return Cached bitmap or null
     */
    private Bitmap getBitmapFromMemoryCache(String key) {
        return memoryCache.get(key);
    }
    
    /**
     * Clear the memory cache
     */
    public void clearMemoryCache() {
        memoryCache.evictAll();
    }
    
    /**
     * Prefetch an image for caching
     * @param url Image URL
     * @param priority Caching priority
     */
    public void prefetchImage(String url, int priority) {
        if (url == null || url.isEmpty()) {
            return;
        }
        
        executor.execute(() -> {
            // Generate a consistent filename from the URL
            String filename = generateFilenameFromUrl(url);
            
            // Check if already cached in memory
            if (getBitmapFromMemoryCache(url) != null) {
                return;
            }
            
            // Check if already cached on disk
            String cachedPath = cacheManager.getCachedImagePath(url);
            if (cachedPath != null) {
                // Load into memory cache
                Bitmap bitmap = BitmapFactory.decodeFile(cachedPath);
                if (bitmap != null) {
                    addBitmapToMemoryCache(url, bitmap);
                }
                return;
            }
            
            // If not cached and we have network, download
            if (networkUtils.isNetworkAvailable()) {
                cacheManager.cacheImageFile(url, filename, priority, null);
            }
        });
    }
    
    /**
     * Generate a consistent filename from a URL
     * @param url Image URL
     * @return Filename for caching
     */
    private String generateFilenameFromUrl(String url) {
        // Create a consistent filename based on URL hash
        int urlHash = url.hashCode();
        String extension = FileUtils.getFileExtension(url);
        if (extension == null) {
            extension = "jpg"; // Default extension
        }
        
        return "img_" + Math.abs(urlHash) + "." + extension;
    }
    
    /**
     * Task for loading images asynchronously
     */
    private class ImageLoadTask implements Runnable {
        private Context context;
        private String url;
        private WeakReference<ImageView> imageViewReference;
        private int placeholderResId;
        private int errorResId;
        
        public ImageLoadTask(Context context, String url, WeakReference<ImageView> imageViewReference,
                            int placeholderResId, int errorResId) {
            this.context = context;
            this.url = url;
            this.imageViewReference = imageViewReference;
            this.placeholderResId = placeholderResId;
            this.errorResId = errorResId;
        }
        
        @Override
        public void run() {
            Bitmap bitmap = null;
            
            // First check disk cache
            String cachedPath = cacheManager.getCachedImagePath(url);
            
            if (cachedPath != null) {
                // Load from disk cache
                bitmap = BitmapFactory.decodeFile(cachedPath);
            } else if (networkUtils.isNetworkAvailable()) {
                // If not in cache and we have network, download and cache
                String filename = generateFilenameFromUrl(url);
                
                cacheManager.cacheImageFile(url, filename, 50, (success, filePath) -> {
                    if (success && filePath != null) {
                        // Load from newly cached file
                        Bitmap cachedBitmap = BitmapFactory.decodeFile(filePath);
                        if (cachedBitmap != null) {
                            // Add to memory cache
                            addBitmapToMemoryCache(url, cachedBitmap);
                            // Update ImageView on UI thread
                            setImageBitmapOnMainThread(cachedBitmap);
                        } else {
                            // Failed to decode
                            setErrorImageOnMainThread();
                        }
                    } else {
                        // Cache failed
                        setErrorImageOnMainThread();
                    }
                });
                
                // Return early as the callback will handle setting the image
                return;
            }
            
            // If we have a bitmap from cache
            if (bitmap != null) {
                // Add to memory cache
                addBitmapToMemoryCache(url, bitmap);
                // Update ImageView on UI thread
                setImageBitmapOnMainThread(bitmap);
            } else {
                // No bitmap and no network - show error
                setErrorImageOnMainThread();
            }
        }
        
        private void setImageBitmapOnMainThread(final Bitmap bitmap) {
            android.os.Handler mainHandler = new android.os.Handler(context.getMainLooper());
            mainHandler.post(() -> {
                ImageView imageView = imageViewReference.get();
                if (imageView != null && isImageViewTagMatching(imageView)) {
                    imageView.setImageBitmap(bitmap);
                }
            });
        }
        
        private void setErrorImageOnMainThread() {
            android.os.Handler mainHandler = new android.os.Handler(context.getMainLooper());
            mainHandler.post(() -> {
                ImageView imageView = imageViewReference.get();
                if (imageView != null && isImageViewTagMatching(imageView)) {
                    imageView.setImageResource(errorResId);
                }
            });
        }
        
        /**
         * Check if the ImageView is still expecting this image URL
         * Prevents setting images on recycled views
         */
        private boolean isImageViewTagMatching(ImageView imageView) {
            Object tag = imageView.getTag(R.id.image_loader_tag);
            return tag != null && tag.equals(url);
        }
    }
}