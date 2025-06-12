package com.bhashasetu.app.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;

import com.bhashasetu.app.util.NetworkOptimizer;
import com.bhashasetu.app.util.ResourceOptimizer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Comprehensive image caching system that combines memory and disk caching for images.
 * Optimized for performance and memory efficiency.
 */
public class ImageCache {
    private static final String TAG = "ImageCache";
    
    // Singleton instance
    private static ImageCache instance;
    
    // Memory cache
    private final LruCache<String, Bitmap> memoryCache;
    
    // Context reference
    private final Context context;
    
    // Disk cache directory
    private final File diskCacheDir;
    
    // Background executor for disk operations
    private final ExecutorService diskExecutor;
    
    // Background executor for network operations
    private final ExecutorService networkExecutor;
    
    // Cache metadata tracking
    private final Map<String, CacheMetadata> metadataMap = new HashMap<>();
    
    // Running tasks
    private final Map<String, Future<?>> runningTasks = Collections.synchronizedMap(new HashMap<>());
    
    // Loading and error placeholders
    private final Map<String, Integer> placeholders = new HashMap<>();
    
    // Image view tracking (for cancellation)
    private final WeakHashMap<ImageView, String> imageViewMap = new WeakHashMap<>();
    
    // Set of URLs that are currently being downloaded
    private final Set<String> downloadingUrls = Collections.synchronizedSet(new HashSet<>());
    
    // Default cache time: 1 day
    private static final long DEFAULT_CACHE_EXPIRY = TimeUnit.DAYS.toMillis(1);
    
    // Pending download requests when a URL is already being downloaded
    private final Map<String, List<DownloadCallback>> pendingDownloads = Collections.synchronizedMap(new HashMap<>());
    
    // Cache statistics tracking
    private int memoryHits = 0;
    private int diskHits = 0;
    private int networkHits = 0;
    private long totalDownloadBytes = 0;
    
    // Preloading queue
    private final List<String> preloadQueue = Collections.synchronizedList(new ArrayList<>());
    private static final int MAX_PRELOAD_QUEUE_SIZE = 20;
    
    /**
     * Metadata for cached images.
     */
    private static class CacheMetadata {
        final String url;
        final long timestamp;
        final long expiry;
        final int accessCount;
        final int width;
        final int height;
        final long fileSize;
        
        CacheMetadata(String url, long timestamp, long expiry, int width, int height, long fileSize) {
            this.url = url;
            this.timestamp = timestamp;
            this.expiry = expiry;
            this.accessCount = 1;
            this.width = width;
            this.height = height;
            this.fileSize = fileSize;
        }
        
        CacheMetadata(String url, long timestamp, long expiry, 
                    int accessCount, int width, int height, long fileSize) {
            this.url = url;
            this.timestamp = timestamp;
            this.expiry = expiry;
            this.accessCount = accessCount;
            this.width = width;
            this.height = height;
            this.fileSize = fileSize;
        }
        
        CacheMetadata withIncrementedCount() {
            return new CacheMetadata(url, timestamp, expiry, 
                                    accessCount + 1, width, height, fileSize);
        }
        
        boolean isExpired() {
            return System.currentTimeMillis() > expiry;
        }
    }
    
    /**
     * Callback interface for image loading operations.
     */
    public interface ImageCallback {
        void onSuccess(Bitmap bitmap);
        void onError(Exception e);
    }
    
    /**
     * Private callback for internal download operations.
     */
    private interface DownloadCallback {
        void onComplete(Bitmap bitmap, File file);
        void onError(Exception e);
    }
    
    /**
     * Private constructor for singleton pattern.
     *
     * @param context Application context
     */
    private ImageCache(Context context) {
        this.context = context.getApplicationContext();
        
        // Calculate memory cache size (1/4 of available memory)
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 4;
        
        // Initialize memory cache
        memoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    return bitmap.getAllocationByteCount() / 1024;
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
                    return bitmap.getByteCount() / 1024;
                } else {
                    return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
                }
            }
            
            @Override
            protected void entryRemoved(boolean evicted, String key, 
                                      Bitmap oldValue, Bitmap newValue) {
                // Don't recycle bitmap here as it might still be in use
                // Just let garbage collection handle it
            }
        };
        
        // Initialize disk cache directory
        diskCacheDir = new File(context.getCacheDir(), "image_cache");
        if (!diskCacheDir.exists()) {
            diskCacheDir.mkdirs();
        }
        
        // Initialize executors
        diskExecutor = Executors.newSingleThreadExecutor();
        networkExecutor = Executors.newFixedThreadPool(3);
        
        // Load metadata
        loadMetadata();
        
        // Initialize default placeholders
        placeholders.put("loading", android.R.drawable.ic_menu_gallery);
        placeholders.put("error", android.R.drawable.ic_dialog_alert);
        
        // Start preload processor
        startPreloadProcessor();
    }
    
    /**
     * Get singleton instance.
     *
     * @param context Context
     * @return ImageCache instance
     */
    public static synchronized ImageCache getInstance(Context context) {
        if (instance == null) {
            instance = new ImageCache(context);
        }
        return instance;
    }
    
    /**
     * Set custom placeholder resources.
     *
     * @param loadingPlaceholder Resource ID for loading placeholder
     * @param errorPlaceholder   Resource ID for error placeholder
     */
    public void setPlaceholders(int loadingPlaceholder, int errorPlaceholder) {
        placeholders.put("loading", loadingPlaceholder);
        placeholders.put("error", errorPlaceholder);
    }
    
    /**
     * Load image into an ImageView.
     *
     * @param imageView ImageView to load into
     * @param url       Image URL
     */
    public void loadImage(ImageView imageView, String url) {
        loadImage(imageView, url, 0, 0, DEFAULT_CACHE_EXPIRY);
    }
    
    /**
     * Load image into an ImageView with size specification.
     *
     * @param imageView ImageView to load into
     * @param url       Image URL
     * @param reqWidth  Required width (0 for original)
     * @param reqHeight Required height (0 for original)
     */
    public void loadImage(ImageView imageView, String url, int reqWidth, int reqHeight) {
        loadImage(imageView, url, reqWidth, reqHeight, DEFAULT_CACHE_EXPIRY);
    }
    
    /**
     * Load image into an ImageView with size and cache duration specification.
     *
     * @param imageView   ImageView to load into
     * @param url         Image URL
     * @param reqWidth    Required width (0 for original)
     * @param reqHeight   Required height (0 for original)
     * @param cacheExpiry Cache expiry in milliseconds
     */
    public void loadImage(final ImageView imageView, final String url, 
                        final int reqWidth, final int reqHeight, final long cacheExpiry) {
        if (imageView == null || url == null || url.trim().isEmpty()) {
            return;
        }
        
        // Cancel any pending loads for this ImageView
        cancelPendingLoad(imageView);
        
        // Associate this URL with the ImageView
        imageViewMap.put(imageView, url);
        
        // Set placeholder while loading
        imageView.setImageResource(placeholders.get("loading"));
        
        // Generate cache key based on URL and size
        final String cacheKey = generateCacheKey(url, reqWidth, reqHeight);
        
        // Check memory cache first
        Bitmap cachedBitmap = memoryCache.get(cacheKey);
        if (cachedBitmap != null && !cachedBitmap.isRecycled()) {
            memoryHits++;
            
            // Update metadata
            updateAccessCount(url);
            
            // Set the cached bitmap
            imageView.setImageBitmap(cachedBitmap);
            return;
        }
        
        // Check if we need to load from disk
        final File cachedFile = new File(diskCacheDir, generateFileName(url));
        if (cachedFile.exists()) {
            // Check if the cached version is expired
            CacheMetadata metadata = metadataMap.get(url);
            if (metadata != null && !metadata.isExpired()) {
                // Load from disk on background thread
                final Future<?> task = diskExecutor.submit(() -> {
                    try {
                        final Bitmap bitmap = loadBitmapFromFile(cachedFile, reqWidth, reqHeight);
                        if (bitmap != null) {
                            diskHits++;
                            
                            // Add to memory cache
                            memoryCache.put(cacheKey, bitmap);
                            
                            // Update metadata
                            updateAccessCount(url);
                            
                            // Set bitmap on main thread
                            imageView.post(() -> {
                                // Check if the ImageView is still associated with this URL
                                String currentUrl = imageViewMap.get(imageView);
                                if (url.equals(currentUrl)) {
                                    imageView.setImageBitmap(bitmap);
                                }
                            });
                        } else {
                            // Failed to load from disk, download from network
                            downloadImage(imageView, url, reqWidth, reqHeight, cacheExpiry);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Error loading image from disk: " + url, e);
                        // Failed to load from disk, download from network
                        downloadImage(imageView, url, reqWidth, reqHeight, cacheExpiry);
                    }
                });
                
                // Store the task for potential cancellation
                runningTasks.put(cacheKey, task);
            } else {
                // Cached version is expired, download from network
                downloadImage(imageView, url, reqWidth, reqHeight, cacheExpiry);
            }
        } else {
            // Not in disk cache, download from network
            downloadImage(imageView, url, reqWidth, reqHeight, cacheExpiry);
        }
    }
    
    /**
     * Download image from network.
     *
     * @param imageView   ImageView to load into
     * @param url         Image URL
     * @param reqWidth    Required width
     * @param reqHeight   Required height
     * @param cacheExpiry Cache expiry time
     */
    private void downloadImage(final ImageView imageView, final String url, 
                             final int reqWidth, final int reqHeight, final long cacheExpiry) {
        final String cacheKey = generateCacheKey(url, reqWidth, reqHeight);
        
        // Check if already downloading this URL
        if (downloadingUrls.contains(url)) {
            // Add to pending downloads
            pendingDownloads.computeIfAbsent(url, k -> new ArrayList<>())
                    .add(new DownloadCallback() {
                        @Override
                        public void onComplete(Bitmap bitmap, File file) {
                            // Check if the ImageView is still associated with this URL
                            String currentUrl = imageViewMap.get(imageView);
                            if (url.equals(currentUrl)) {
                                // Add to memory cache
                                memoryCache.put(cacheKey, bitmap);
                                
                                // Set bitmap on main thread
                                imageView.post(() -> imageView.setImageBitmap(bitmap));
                            }
                        }
                        
                        @Override
                        public void onError(Exception e) {
                            // Set error placeholder on main thread
                            imageView.post(() -> {
                                String currentUrl = imageViewMap.get(imageView);
                                if (url.equals(currentUrl)) {
                                    imageView.setImageResource(placeholders.get("error"));
                                }
                            });
                        }
                    });
            return;
        }
        
        // Mark as downloading
        downloadingUrls.add(url);
        
        // Download on background thread
        final Future<?> task = networkExecutor.submit(() -> {
            HttpURLConnection connection = null;
            InputStream inputStream = null;
            FileOutputStream outputStream = null;
            BufferedInputStream bufferedInputStream = null;
            
            try {
                // Create URL and open connection
                URL imageUrl = new URL(url);
                connection = (HttpURLConnection) imageUrl.openConnection();
                connection.setDoInput(true);
                connection.setConnectTimeout(15000);
                connection.setReadTimeout(15000);
                
                // Add cache control based on network conditions
                NetworkOptimizer networkOptimizer = NetworkOptimizer.getInstance(context);
                connection.setRequestProperty("Cache-Control", 
                                           networkOptimizer.getCachePolicy(false).toString());
                
                // Connect and check response code
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    throw new IOException("HTTP error code: " + responseCode);
                }
                
                // Get content length for stats
                long contentLength = connection.getContentLength();
                if (contentLength > 0) {
                    totalDownloadBytes += contentLength;
                }
                
                // Create temp file for download
                final File downloadFile = new File(diskCacheDir, generateFileName(url));
                
                // Download to file
                inputStream = connection.getInputStream();
                bufferedInputStream = new BufferedInputStream(inputStream);
                outputStream = new FileOutputStream(downloadFile);
                
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.flush();
                
                // Close streams
                bufferedInputStream.close();
                outputStream.close();
                
                // Decode bitmap from file
                final Bitmap bitmap = loadBitmapFromFile(downloadFile, reqWidth, reqHeight);
                if (bitmap == null) {
                    throw new IOException("Failed to decode bitmap");
                }
                
                // Update metadata
                updateMetadata(url, cacheExpiry, bitmap.getWidth(), bitmap.getHeight(), 
                              downloadFile.length());
                
                // Increment download count
                networkHits++;
                
                // Add to memory cache
                memoryCache.put(cacheKey, bitmap);
                
                // Set bitmap on main thread
                imageView.post(() -> {
                    // Check if the ImageView is still associated with this URL
                    String currentUrl = imageViewMap.get(imageView);
                    if (url.equals(currentUrl)) {
                        imageView.setImageBitmap(bitmap);
                    }
                });
                
                // Notify pending downloads
                notifyPendingDownloads(url, bitmap, downloadFile);
            } catch (Exception e) {
                Log.e(TAG, "Error downloading image: " + url, e);
                
                // Set error placeholder on main thread
                imageView.post(() -> {
                    String currentUrl = imageViewMap.get(imageView);
                    if (url.equals(currentUrl)) {
                        imageView.setImageResource(placeholders.get("error"));
                    }
                });
                
                // Notify pending downloads of error
                notifyPendingDownloadsError(url, e);
            } finally {
                // Cleanup
                try {
                    if (bufferedInputStream != null) {
                        bufferedInputStream.close();
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Error closing streams", e);
                }
                
                // Remove from downloading set
                downloadingUrls.remove(url);
                
                // Remove task from running tasks
                runningTasks.remove(cacheKey);
            }
        });
        
        // Store the task for potential cancellation
        runningTasks.put(cacheKey, task);
    }
    
    /**
     * Notify pending download callbacks of success.
     *
     * @param url    URL that was downloaded
     * @param bitmap Downloaded bitmap
     * @param file   Cache file
     */
    private void notifyPendingDownloads(String url, Bitmap bitmap, File file) {
        List<DownloadCallback> callbacks = pendingDownloads.remove(url);
        if (callbacks != null) {
            for (DownloadCallback callback : callbacks) {
                callback.onComplete(bitmap, file);
            }
        }
    }
    
    /**
     * Notify pending download callbacks of error.
     *
     * @param url URL that was downloaded
     * @param e   Exception that occurred
     */
    private void notifyPendingDownloadsError(String url, Exception e) {
        List<DownloadCallback> callbacks = pendingDownloads.remove(url);
        if (callbacks != null) {
            for (DownloadCallback callback : callbacks) {
                callback.onError(e);
            }
        }
    }
    
    /**
     * Load bitmap from local file with optional resizing.
     *
     * @param file      File to load from
     * @param reqWidth  Required width
     * @param reqHeight Required height
     * @return Decoded bitmap or null if failed
     */
    private Bitmap loadBitmapFromFile(File file, int reqWidth, int reqHeight) {
        try {
            // Decode image dimensions first
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(file.getAbsolutePath(), options);
            
            // Calculate inSampleSize if dimensions were specified
            if (reqWidth > 0 && reqHeight > 0) {
                options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
            } else {
                options.inSampleSize = 1;
            }
            
            // Use an appropriate bitmap configuration based on device memory
            ResourceOptimizer resourceOptimizer = ResourceOptimizer.getInstance(context);
            options.inPreferredConfig = resourceOptimizer.getBooleanQuality("prefer_rgb565") ? 
                                      Bitmap.Config.RGB_565 : Bitmap.Config.ARGB_8888;
            
            // Decode with the calculated inSampleSize
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        } catch (Exception e) {
            Log.e(TAG, "Error loading bitmap from file: " + file.getAbsolutePath(), e);
            return null;
        }
    }
    
    /**
     * Calculate the optimal inSampleSize to decode an image to a given size.
     *
     * @param options   BitmapFactory.Options with dimensions already populated
     * @param reqWidth  Required width
     * @param reqHeight Required height
     * @return Optimal inSampleSize value (power of 2)
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
            while ((halfHeight / inSampleSize) >= reqHeight && 
                   (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        
        return inSampleSize;
    }
    
    /**
     * Generate a cache key based on URL and dimensions.
     *
     * @param url       Image URL
     * @param reqWidth  Required width
     * @param reqHeight Required height
     * @return Cache key
     */
    private String generateCacheKey(String url, int reqWidth, int reqHeight) {
        return url + "_" + reqWidth + "x" + reqHeight;
    }
    
    /**
     * Generate a filename for disk cache based on URL.
     *
     * @param url Image URL
     * @return Filename
     */
    private String generateFileName(String url) {
        // Using a simple hash function for demo purposes
        // In production, a more robust hashing algorithm should be used
        return String.valueOf(url.hashCode()) + ".jpg";
    }
    
    /**
     * Update metadata for a cached image.
     *
     * @param url      URL
     * @param expiry   Expiry time
     * @param width    Image width
     * @param height   Image height
     * @param fileSize File size
     */
    private void updateMetadata(String url, long expiry, int width, int height, long fileSize) {
        long timestamp = System.currentTimeMillis();
        long expiryTime = timestamp + expiry;
        metadataMap.put(url, new CacheMetadata(url, timestamp, expiryTime, width, height, fileSize));
        
        // Schedule periodic saving of metadata
        saveMetadataDelayed();
    }
    
    /**
     * Update access count for a cached image.
     *
     * @param url URL
     */
    private void updateAccessCount(String url) {
        CacheMetadata metadata = metadataMap.get(url);
        if (metadata != null) {
            metadataMap.put(url, metadata.withIncrementedCount());
        }
    }
    
    /**
     * Load image asynchronously.
     *
     * @param url      Image URL
     * @param callback Callback for result
     */
    public void loadImageAsync(String url, ImageCallback callback) {
        loadImageAsync(url, 0, 0, DEFAULT_CACHE_EXPIRY, callback);
    }
    
    /**
     * Load image asynchronously with size specification.
     *
     * @param url       Image URL
     * @param reqWidth  Required width
     * @param reqHeight Required height
     * @param callback  Callback for result
     */
    public void loadImageAsync(String url, int reqWidth, int reqHeight, ImageCallback callback) {
        loadImageAsync(url, reqWidth, reqHeight, DEFAULT_CACHE_EXPIRY, callback);
    }
    
    /**
     * Load image asynchronously with size and cache duration specification.
     *
     * @param url         Image URL
     * @param reqWidth    Required width
     * @param reqHeight   Required height
     * @param cacheExpiry Cache expiry time
     * @param callback    Callback for result
     */
    public void loadImageAsync(final String url, final int reqWidth, 
                             final int reqHeight, final long cacheExpiry, 
                             final ImageCallback callback) {
        if (url == null || url.trim().isEmpty() || callback == null) {
            return;
        }
        
        // Generate cache key
        final String cacheKey = generateCacheKey(url, reqWidth, reqHeight);
        
        // Check memory cache first
        Bitmap cachedBitmap = memoryCache.get(cacheKey);
        if (cachedBitmap != null && !cachedBitmap.isRecycled()) {
            memoryHits++;
            
            // Update metadata
            updateAccessCount(url);
            
            // Return the cached bitmap
            callback.onSuccess(cachedBitmap);
            return;
        }
        
        // Check if we need to load from disk
        final File cachedFile = new File(diskCacheDir, generateFileName(url));
        if (cachedFile.exists()) {
            // Check if the cached version is expired
            CacheMetadata metadata = metadataMap.get(url);
            if (metadata != null && !metadata.isExpired()) {
                // Load from disk on background thread
                final Future<?> task = diskExecutor.submit(() -> {
                    try {
                        final Bitmap bitmap = loadBitmapFromFile(cachedFile, reqWidth, reqHeight);
                        if (bitmap != null) {
                            diskHits++;
                            
                            // Add to memory cache
                            memoryCache.put(cacheKey, bitmap);
                            
                            // Update metadata
                            updateAccessCount(url);
                            
                            // Call callback on main thread
                            new android.os.Handler(android.os.Looper.getMainLooper())
                                    .post(() -> callback.onSuccess(bitmap));
                        } else {
                            // Failed to load from disk, download from network
                            downloadImageAsync(url, reqWidth, reqHeight, cacheExpiry, callback);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Error loading image from disk: " + url, e);
                        // Failed to load from disk, download from network
                        downloadImageAsync(url, reqWidth, reqHeight, cacheExpiry, callback);
                    }
                });
                
                // Store the task for potential cancellation
                runningTasks.put(cacheKey, task);
            } else {
                // Cached version is expired, download from network
                downloadImageAsync(url, reqWidth, reqHeight, cacheExpiry, callback);
            }
        } else {
            // Not in disk cache, download from network
            downloadImageAsync(url, reqWidth, reqHeight, cacheExpiry, callback);
        }
    }
    
    /**
     * Download image asynchronously.
     *
     * @param url         Image URL
     * @param reqWidth    Required width
     * @param reqHeight   Required height
     * @param cacheExpiry Cache expiry time
     * @param callback    Callback for result
     */
    private void downloadImageAsync(final String url, final int reqWidth, 
                                  final int reqHeight, final long cacheExpiry, 
                                  final ImageCallback callback) {
        final String cacheKey = generateCacheKey(url, reqWidth, reqHeight);
        
        // Check if already downloading this URL
        if (downloadingUrls.contains(url)) {
            // Add to pending downloads
            pendingDownloads.computeIfAbsent(url, k -> new ArrayList<>())
                    .add(new DownloadCallback() {
                        @Override
                        public void onComplete(Bitmap bitmap, File file) {
                            // Add to memory cache
                            memoryCache.put(cacheKey, bitmap);
                            
                            // Call callback on main thread
                            new android.os.Handler(android.os.Looper.getMainLooper())
                                    .post(() -> callback.onSuccess(bitmap));
                        }
                        
                        @Override
                        public void onError(Exception e) {
                            // Call callback on main thread
                            new android.os.Handler(android.os.Looper.getMainLooper())
                                    .post(() -> callback.onError(e));
                        }
                    });
            return;
        }
        
        // Mark as downloading
        downloadingUrls.add(url);
        
        // Download on background thread
        final Future<?> task = networkExecutor.submit(() -> {
            HttpURLConnection connection = null;
            InputStream inputStream = null;
            FileOutputStream outputStream = null;
            BufferedInputStream bufferedInputStream = null;
            
            try {
                // Create URL and open connection
                URL imageUrl = new URL(url);
                connection = (HttpURLConnection) imageUrl.openConnection();
                connection.setDoInput(true);
                connection.setConnectTimeout(15000);
                connection.setReadTimeout(15000);
                
                // Add cache control based on network conditions
                NetworkOptimizer networkOptimizer = NetworkOptimizer.getInstance(context);
                connection.setRequestProperty("Cache-Control", 
                                           networkOptimizer.getCachePolicy(false).toString());
                
                // Connect and check response code
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    throw new IOException("HTTP error code: " + responseCode);
                }
                
                // Get content length for stats
                long contentLength = connection.getContentLength();
                if (contentLength > 0) {
                    totalDownloadBytes += contentLength;
                }
                
                // Create temp file for download
                final File downloadFile = new File(diskCacheDir, generateFileName(url));
                
                // Download to file
                inputStream = connection.getInputStream();
                bufferedInputStream = new BufferedInputStream(inputStream);
                outputStream = new FileOutputStream(downloadFile);
                
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.flush();
                
                // Close streams
                bufferedInputStream.close();
                outputStream.close();
                
                // Decode bitmap from file
                final Bitmap bitmap = loadBitmapFromFile(downloadFile, reqWidth, reqHeight);
                if (bitmap == null) {
                    throw new IOException("Failed to decode bitmap");
                }
                
                // Update metadata
                updateMetadata(url, cacheExpiry, bitmap.getWidth(), bitmap.getHeight(), 
                              downloadFile.length());
                
                // Increment download count
                networkHits++;
                
                // Add to memory cache
                memoryCache.put(cacheKey, bitmap);
                
                // Call callback on main thread
                new android.os.Handler(android.os.Looper.getMainLooper())
                        .post(() -> callback.onSuccess(bitmap));
                
                // Notify pending downloads
                notifyPendingDownloads(url, bitmap, downloadFile);
            } catch (Exception e) {
                Log.e(TAG, "Error downloading image: " + url, e);
                
                // Call callback on main thread
                new android.os.Handler(android.os.Looper.getMainLooper())
                        .post(() -> callback.onError(e));
                
                // Notify pending downloads of error
                notifyPendingDownloadsError(url, e);
            } finally {
                // Cleanup
                try {
                    if (bufferedInputStream != null) {
                        bufferedInputStream.close();
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Error closing streams", e);
                }
                
                // Remove from downloading set
                downloadingUrls.remove(url);
                
                // Remove task from running tasks
                runningTasks.remove(cacheKey);
            }
        });
        
        // Store the task for potential cancellation
        runningTasks.put(cacheKey, task);
    }
    
    /**
     * Cancel pending load for an ImageView.
     *
     * @param imageView ImageView
     */
    private void cancelPendingLoad(ImageView imageView) {
        if (imageView == null) {
            return;
        }
        
        String url = imageViewMap.get(imageView);
        if (url != null) {
            // Check if there's a task running for this URL
            for (int reqWidth : new int[]{0, imageView.getWidth()}) {
                for (int reqHeight : new int[]{0, imageView.getHeight()}) {
                    String cacheKey = generateCacheKey(url, reqWidth, reqHeight);
                    Future<?> task = runningTasks.get(cacheKey);
                    if (task != null) {
                        task.cancel(true);
                        runningTasks.remove(cacheKey);
                    }
                }
            }
        }
    }
    
    /**
     * Load local image from resource.
     *
     * @param imageView  ImageView to load into
     * @param resourceId Resource ID
     */
    public void loadResource(ImageView imageView, int resourceId) {
        if (imageView == null) {
            return;
        }
        
        // Cancel any pending loads for this ImageView
        cancelPendingLoad(imageView);
        
        // Clear URL association
        imageViewMap.remove(imageView);
        
        // Generate cache key
        final String cacheKey = "res_" + resourceId;
        
        // Check memory cache first
        Bitmap cachedBitmap = memoryCache.get(cacheKey);
        if (cachedBitmap != null && !cachedBitmap.isRecycled()) {
            memoryHits++;
            imageView.setImageBitmap(cachedBitmap);
            return;
        }
        
        // Load on background thread
        diskExecutor.submit(() -> {
            try {
                int width = imageView.getWidth() > 0 ? imageView.getWidth() : 0;
                int height = imageView.getHeight() > 0 ? imageView.getHeight() : 0;
                
                // Use resource optimizer to load bitmap efficiently
                ResourceOptimizer resourceOptimizer = ResourceOptimizer.getInstance(context);
                final Bitmap bitmap = resourceOptimizer.loadScaledBitmap(resourceId, width, height);
                
                if (bitmap != null) {
                    // Add to memory cache
                    memoryCache.put(cacheKey, bitmap);
                    
                    // Set bitmap on main thread
                    imageView.post(() -> imageView.setImageBitmap(bitmap));
                } else {
                    // Set error placeholder on main thread
                    imageView.post(() -> imageView.setImageResource(placeholders.get("error")));
                }
            } catch (Exception e) {
                Log.e(TAG, "Error loading resource: " + resourceId, e);
                
                // Set error placeholder on main thread
                imageView.post(() -> imageView.setImageResource(placeholders.get("error")));
            }
        });
    }
    
    /**
     * Load local image from file.
     *
     * @param imageView ImageView to load into
     * @param file      File to load from
     */
    public void loadFile(ImageView imageView, File file) {
        if (imageView == null || file == null || !file.exists()) {
            if (imageView != null) {
                imageView.setImageResource(placeholders.get("error"));
            }
            return;
        }
        
        // Cancel any pending loads for this ImageView
        cancelPendingLoad(imageView);
        
        // Clear URL association
        imageViewMap.remove(imageView);
        
        // Generate cache key
        final String cacheKey = "file_" + file.getAbsolutePath();
        
        // Check memory cache first
        Bitmap cachedBitmap = memoryCache.get(cacheKey);
        if (cachedBitmap != null && !cachedBitmap.isRecycled()) {
            memoryHits++;
            imageView.setImageBitmap(cachedBitmap);
            return;
        }
        
        // Load on background thread
        diskExecutor.submit(() -> {
            try {
                int width = imageView.getWidth() > 0 ? imageView.getWidth() : 0;
                int height = imageView.getHeight() > 0 ? imageView.getHeight() : 0;
                
                final Bitmap bitmap = loadBitmapFromFile(file, width, height);
                
                if (bitmap != null) {
                    // Add to memory cache
                    memoryCache.put(cacheKey, bitmap);
                    
                    // Set bitmap on main thread
                    imageView.post(() -> imageView.setImageBitmap(bitmap));
                } else {
                    // Set error placeholder on main thread
                    imageView.post(() -> imageView.setImageResource(placeholders.get("error")));
                }
            } catch (Exception e) {
                Log.e(TAG, "Error loading file: " + file.getAbsolutePath(), e);
                
                // Set error placeholder on main thread
                imageView.post(() -> imageView.setImageResource(placeholders.get("error")));
            }
        });
    }
    
    /**
     * Load local image from Uri.
     *
     * @param imageView ImageView to load into
     * @param uri       Uri to load from
     */
    public void loadUri(ImageView imageView, Uri uri) {
        if (imageView == null || uri == null) {
            if (imageView != null) {
                imageView.setImageResource(placeholders.get("error"));
            }
            return;
        }
        
        // Cancel any pending loads for this ImageView
        cancelPendingLoad(imageView);
        
        // Check if it's a network Uri
        String scheme = uri.getScheme();
        if ("http".equals(scheme) || "https".equals(scheme)) {
            loadImage(imageView, uri.toString());
            return;
        }
        
        // Local Uri
        // Clear URL association
        imageViewMap.remove(imageView);
        
        // Generate cache key
        final String cacheKey = "uri_" + uri.toString();
        
        // Check memory cache first
        Bitmap cachedBitmap = memoryCache.get(cacheKey);
        if (cachedBitmap != null && !cachedBitmap.isRecycled()) {
            memoryHits++;
            imageView.setImageBitmap(cachedBitmap);
            return;
        }
        
        // Set placeholder while loading
        imageView.setImageResource(placeholders.get("loading"));
        
        // Load on background thread
        diskExecutor.submit(() -> {
            try {
                int width = imageView.getWidth() > 0 ? imageView.getWidth() : 0;
                int height = imageView.getHeight() > 0 ? imageView.getHeight() : 0;
                
                // Decode dimensions first
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                
                InputStream inputStream = context.getContentResolver().openInputStream(uri);
                BitmapFactory.decodeStream(inputStream, null, options);
                if (inputStream != null) {
                    inputStream.close();
                }
                
                // Calculate inSampleSize if dimensions were specified
                if (width > 0 && height > 0) {
                    options.inSampleSize = calculateInSampleSize(options, width, height);
                } else {
                    options.inSampleSize = 1;
                }
                
                // Use appropriate bitmap configuration
                ResourceOptimizer resourceOptimizer = ResourceOptimizer.getInstance(context);
                options.inPreferredConfig = resourceOptimizer.getBooleanQuality("prefer_rgb565") ? 
                                          Bitmap.Config.RGB_565 : Bitmap.Config.ARGB_8888;
                
                // Decode with the calculated inSampleSize
                options.inJustDecodeBounds = false;
                inputStream = context.getContentResolver().openInputStream(uri);
                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
                if (inputStream != null) {
                    inputStream.close();
                }
                
                if (bitmap != null) {
                    // Add to memory cache
                    memoryCache.put(cacheKey, bitmap);
                    
                    // Set bitmap on main thread
                    imageView.post(() -> imageView.setImageBitmap(bitmap));
                } else {
                    // Set error placeholder on main thread
                    imageView.post(() -> imageView.setImageResource(placeholders.get("error")));
                }
            } catch (Exception e) {
                Log.e(TAG, "Error loading uri: " + uri, e);
                
                // Set error placeholder on main thread
                imageView.post(() -> imageView.setImageResource(placeholders.get("error")));
            }
        });
    }
    
    /**
     * Preload an image into the cache.
     *
     * @param url URL to preload
     */
    public void preload(String url) {
        preload(url, 0, 0, DEFAULT_CACHE_EXPIRY);
    }
    
    /**
     * Preload an image into the cache with size specification.
     *
     * @param url       URL to preload
     * @param reqWidth  Required width
     * @param reqHeight Required height
     */
    public void preload(String url, int reqWidth, int reqHeight) {
        preload(url, reqWidth, reqHeight, DEFAULT_CACHE_EXPIRY);
    }
    
    /**
     * Preload an image into the cache with size and cache duration specification.
     *
     * @param url         URL to preload
     * @param reqWidth    Required width
     * @param reqHeight   Required height
     * @param cacheExpiry Cache expiry time
     */
    public void preload(String url, int reqWidth, int reqHeight, long cacheExpiry) {
        if (url == null || url.trim().isEmpty()) {
            return;
        }
        
        // Add to preload queue
        synchronized (preloadQueue) {
            // Check if already in queue
            if (!preloadQueue.contains(url)) {
                // Remove oldest if queue is full
                if (preloadQueue.size() >= MAX_PRELOAD_QUEUE_SIZE) {
                    preloadQueue.remove(0);
                }
                
                // Add to queue
                preloadQueue.add(url);
            }
        }
    }
    
    /**
     * Start preload processor thread.
     */
    private void startPreloadProcessor() {
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    // Wait a bit to avoid hammering the network
                    Thread.sleep(1000);
                    
                    // Process preload queue
                    String url = null;
                    synchronized (preloadQueue) {
                        if (!preloadQueue.isEmpty()) {
                            url = preloadQueue.remove(0);
                        }
                    }
                    
                    if (url != null) {
                        final String finalUrl = url;
                        
                        // Check if already cached
                        if (isImageCached(finalUrl)) {
                            continue;
                        }
                        
                        // Check if already downloading
                        if (downloadingUrls.contains(finalUrl)) {
                            continue;
                        }
                        
                        // Download in background
                        loadImageAsync(finalUrl, 0, 0, DEFAULT_CACHE_EXPIRY, new ImageCallback() {
                            @Override
                            public void onSuccess(Bitmap bitmap) {
                                Log.d(TAG, "Preloaded image: " + finalUrl);
                            }
                            
                            @Override
                            public void onError(Exception e) {
                                Log.e(TAG, "Error preloading image: " + finalUrl, e);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                } catch (Exception e) {
                    Log.e(TAG, "Error in preload processor", e);
                }
            }
        }, "ImageCache-Preloader").start();
    }
    
    /**
     * Check if an image is cached.
     *
     * @param url URL to check
     * @return true if cached
     */
    public boolean isImageCached(String url) {
        if (url == null) {
            return false;
        }
        
        // Check memory cache
        String cacheKey = generateCacheKey(url, 0, 0);
        if (memoryCache.get(cacheKey) != null) {
            return true;
        }
        
        // Check disk cache
        File cachedFile = new File(diskCacheDir, generateFileName(url));
        if (cachedFile.exists()) {
            // Check if expired
            CacheMetadata metadata = metadataMap.get(url);
            return metadata != null && !metadata.isExpired();
        }
        
        return false;
    }
    
    /**
     * Clear all cached images.
     */
    public void clearCache() {
        // Clear memory cache
        memoryCache.evictAll();
        
        // Clear disk cache
        diskExecutor.submit(() -> {
            File[] files = diskCacheDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
            
            // Clear metadata
            metadataMap.clear();
            saveMetadata();
        });
    }
    
    /**
     * Clear cached image for a specific URL.
     *
     * @param url URL to clear
     */
    public void clearCache(String url) {
        if (url == null) {
            return;
        }
        
        // Remove from memory cache (all sizes)
        for (String key : new ArrayList<>(memoryCache.snapshot().keySet())) {
            if (key.startsWith(url + "_")) {
                memoryCache.remove(key);
            }
        }
        
        // Remove from disk cache
        diskExecutor.submit(() -> {
            File cachedFile = new File(diskCacheDir, generateFileName(url));
            if (cachedFile.exists()) {
                cachedFile.delete();
            }
            
            // Remove from metadata
            metadataMap.remove(url);
            saveMetadata();
        });
    }
    
    /**
     * Clear expired images.
     */
    public void clearExpired() {
        long now = System.currentTimeMillis();
        
        // Find expired entries
        List<String> expiredUrls = new ArrayList<>();
        for (Map.Entry<String, CacheMetadata> entry : metadataMap.entrySet()) {
            if (entry.getValue().isExpired()) {
                expiredUrls.add(entry.getKey());
            }
        }
        
        // Clear expired entries
        for (String url : expiredUrls) {
            clearCache(url);
        }
        
        // Log cleanup info
        if (!expiredUrls.isEmpty()) {
            Log.d(TAG, "Cleared " + expiredUrls.size() + " expired images");
        }
    }
    
    /**
     * Save metadata to disk.
     */
    private void saveMetadata() {
        File metadataFile = new File(diskCacheDir, "metadata.json");
        
        try (FileOutputStream fos = new FileOutputStream(metadataFile)) {
            // We would use real JSON serialization here
            // For simplicity, just saving a flag file
            fos.write(1);
        } catch (IOException e) {
            Log.e(TAG, "Error saving metadata", e);
        }
    }
    
    /**
     * Schedule delayed saving of metadata.
     */
    private void saveMetadataDelayed() {
        diskExecutor.submit(() -> {
            try {
                Thread.sleep(5000); // Wait 5 seconds before saving
                saveMetadata();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }
    
    /**
     * Load metadata from disk.
     */
    private void loadMetadata() {
        // In a real implementation, we would deserialize metadata from disk
        // For this example, we'll just initialize an empty map
        metadataMap.clear();
    }
    
    /**
     * Get cache statistics.
     *
     * @return Map of statistics
     */
    public Map<String, String> getStatistics() {
        Map<String, String> stats = new LinkedHashMap<>();
        
        stats.put("Memory Cache Size", memoryCache.size() + " images");
        stats.put("Memory Cache Size (KB)", memoryCache.size() / 1024 + " KB");
        stats.put("Memory Cache Max Size", memoryCache.maxSize() / 1024 + " KB");
        stats.put("Disk Cache Entries", metadataMap.size() + " images");
        
        // Calculate hit rates
        int totalRequests = memoryHits + diskHits + networkHits;
        float memoryHitRate = totalRequests > 0 ? ((float) memoryHits / totalRequests) * 100 : 0;
        float diskHitRate = totalRequests > 0 ? ((float) diskHits / totalRequests) * 100 : 0;
        float networkHitRate = totalRequests > 0 ? ((float) networkHits / totalRequests) * 100 : 0;
        
        stats.put("Memory Hits", String.valueOf(memoryHits));
        stats.put("Disk Hits", String.valueOf(diskHits));
        stats.put("Network Hits", String.valueOf(networkHits));
        stats.put("Memory Hit Rate", String.format("%.1f%%", memoryHitRate));
        stats.put("Disk Hit Rate", String.format("%.1f%%", diskHitRate));
        stats.put("Network Hit Rate", String.format("%.1f%%", networkHitRate));
        stats.put("Total Downloaded", com.bhashasetu.app.util.MemoryOptimizer
                .formatMemorySize(totalDownloadBytes));
        
        // Calculate disk cache size
        long diskSize = 0;
        File[] files = diskCacheDir.listFiles();
        if (files != null) {
            for (File file : files) {
                diskSize += file.length();
            }
        }
        
        stats.put("Disk Cache Size", com.bhashasetu.app.util.MemoryOptimizer
                .formatMemorySize(diskSize));
        
        return stats;
    }
    
    /**
     * Release all resources.
     */
    public void release() {
        // Cancel all tasks
        for (Future<?> task : runningTasks.values()) {
            task.cancel(true);
        }
        runningTasks.clear();
        
        // Shutdown executors
        diskExecutor.shutdown();
        networkExecutor.shutdown();
        
        // Wait for tasks to complete
        try {
            diskExecutor.awaitTermination(500, TimeUnit.MILLISECONDS);
            networkExecutor.awaitTermination(500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Save metadata
        saveMetadata();
    }
    
    /**
     * Handle memory pressure by clearing caches.
     *
     * @param level Memory trim level
     */
    public void handleTrimMemory(int level) {
        if (level >= android.content.ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW) {
            // Aggressive trimming: clear half the memory cache
            memoryCache.trimToSize(memoryCache.maxSize() / 2);
        } else if (level >= android.content.ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE) {
            // Moderate trimming: clear quarter of the memory cache
            memoryCache.trimToSize(memoryCache.maxSize() * 3 / 4);
        }
    }
    
    /**
     * Resize the memory cache based on available memory.
     *
     * @param percentage Percentage of available memory to use (1-50)
     */
    public void resizeMemoryCache(int percentage) {
        if (percentage < 1 || percentage > 50) {
            throw new IllegalArgumentException("Percentage must be between 1 and 50");
        }
        
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int newCacheSize = maxMemory * percentage / 100;
        
        memoryCache.resize(newCacheSize);
    }
    
    /**
     * Get default disk cache directory.
     *
     * @return Disk cache directory
     */
    public File getDiskCacheDir() {
        return diskCacheDir;
    }
    
    /**
     * Get image dimensions from cache metadata.
     *
     * @param url Image URL
     * @return Array with [width, height] or null if not in cache
     */
    @Nullable
    public int[] getImageDimensions(String url) {
        if (url == null) {
            return null;
        }
        
        CacheMetadata metadata = metadataMap.get(url);
        if (metadata != null) {
            return new int[]{metadata.width, metadata.height};
        }
        
        return null;
    }
    
    /**
     * Batch preload multiple images.
     *
     * @param urls List of URLs to preload
     */
    public void preloadBatch(List<String> urls) {
        if (urls == null || urls.isEmpty()) {
            return;
        }
        
        for (String url : urls) {
            preload(url);
        }
    }
    
    /**
     * Get image details for a cached image.
     *
     * @param url Image URL
     * @return Map of image details or null if not cached
     */
    @Nullable
    public Map<String, Object> getImageDetails(String url) {
        if (url == null) {
            return null;
        }
        
        CacheMetadata metadata = metadataMap.get(url);
        if (metadata == null) {
            return null;
        }
        
        Map<String, Object> details = new ArrayMap<>();
        details.put("url", metadata.url);
        details.put("width", metadata.width);
        details.put("height", metadata.height);
        details.put("size", metadata.fileSize);
        details.put("age", System.currentTimeMillis() - metadata.timestamp);
        details.put("expiry", metadata.expiry - System.currentTimeMillis());
        details.put("accessCount", metadata.accessCount);
        details.put("isExpired", metadata.isExpired());
        
        return details;
    }
}