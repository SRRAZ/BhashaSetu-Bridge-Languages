package com.example.englishhindi.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.StatFs;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.englishhindi.util.MemoryOptimizer;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Implementation of the central cache management system that coordinates all caching operations.
 * Provides a unified interface for the application to interact with different cache subsystems.
 */
public class CacheManagerImpl {
    private static final String TAG = "CacheManagerImpl";
    
    // Singleton instance
    private static CacheManagerImpl instance;
    
    // Context reference
    private final Context context;
    
    // Cache subsystems
    private final DataCache dataCache;
    private final ImageCache imageCache;
    private final AudioCache audioCache;
    
    // Executor for background operations
    private final ExecutorService executor;
    
    // Cache metrics
    private long lastMaintenanceTime = 0;
    private static final long MAINTENANCE_INTERVAL = TimeUnit.HOURS.toMillis(6);
    
    // Cache preferences
    private final SharedPreferences preferences;
    private static final String PREFS_NAME = "cache_preferences";
    private static final String KEY_LAST_MAINTENANCE = "last_maintenance_time";
    private static final String KEY_CACHE_ENABLED = "cache_enabled";
    private static final String KEY_MAX_CACHE_SIZE = "max_cache_size";
    
    /**
     * Private constructor for singleton pattern.
     *
     * @param context Application context
     */
    private CacheManagerImpl(Context context) {
        this.context = context.getApplicationContext();
        
        // Initialize preferences
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        lastMaintenanceTime = preferences.getLong(KEY_LAST_MAINTENANCE, 0);
        
        // Initialize cache subsystems
        dataCache = DataCache.getInstance(context);
        imageCache = ImageCache.getInstance(context);
        audioCache = AudioCache.getInstance(context);
        
        // Initialize executor
        executor = Executors.newSingleThreadExecutor();
        
        // Perform maintenance if needed
        checkForMaintenance();
    }
    
    /**
     * Get singleton instance.
     *
     * @param context Context
     * @return CacheManagerImpl instance
     */
    public static synchronized CacheManagerImpl getInstance(Context context) {
        if (instance == null) {
            instance = new CacheManagerImpl(context);
        }
        return instance;
    }
    
    /**
     * Get the data cache.
     *
     * @return DataCache instance
     */
    public DataCache getDataCache() {
        return dataCache;
    }
    
    /**
     * Get the image cache.
     *
     * @return ImageCache instance
     */
    public ImageCache getImageCache() {
        return imageCache;
    }
    
    /**
     * Get the audio cache.
     *
     * @return AudioCache instance
     */
    public AudioCache getAudioCache() {
        return audioCache;
    }
    
    /**
     * Get the disk cache base directory.
     *
     * @return Cache directory
     */
    public File getCacheDir() {
        return context.getCacheDir();
    }
    
    /**
     * Check if maintenance is needed and perform it.
     */
    private void checkForMaintenance() {
        long now = System.currentTimeMillis();
        if (now - lastMaintenanceTime > MAINTENANCE_INTERVAL) {
            performMaintenance();
        }
    }
    
    /**
     * Perform maintenance operations.
     */
    public void performMaintenance() {
        executor.submit(() -> {
            // Clear expired data from all caches
            dataCache.clearExpired();
            imageCache.clearExpired();
            audioCache.clearExpired();
            
            // Trim cache if necessary
            long maxCacheSize = getMaxCacheSize();
            if (maxCacheSize > 0) {
                trimCache(maxCacheSize);
            }
            
            // Update last maintenance time
            lastMaintenanceTime = System.currentTimeMillis();
            preferences.edit().putLong(KEY_LAST_MAINTENANCE, lastMaintenanceTime).apply();
            
            Log.d(TAG, "Cache maintenance completed");
        });
    }
    
    /**
     * Trim the cache to the specified maximum size.
     *
     * @param maxSizeBytes Maximum cache size in bytes
     */
    private void trimCache(long maxSizeBytes) {
        // Get current cache size
        long totalSize = getTotalCacheSize();
        
        // If within limits, do nothing
        if (totalSize <= maxSizeBytes) {
            return;
        }
        
        // Calculate how much we need to remove
        long bytesToRemove = totalSize - maxSizeBytes;
        
        Log.d(TAG, "Trimming cache: current size=" + MemoryOptimizer.formatMemorySize(totalSize) +
                  ", max size=" + MemoryOptimizer.formatMemorySize(maxSizeBytes) +
                  ", to remove=" + MemoryOptimizer.formatMemorySize(bytesToRemove));
        
        // Clear low priority caches first
        clearLowPriorityCache((int) (bytesToRemove * 100 / totalSize), 0);
    }
    
    /**
     * Get the total size of all caches.
     *
     * @return Total cache size in bytes
     */
    public long getTotalCacheSize() {
        long size = 0;
        
        // Get size of cache directory
        size += getDirSize(context.getCacheDir());
        
        // Get size of external cache if available
        File externalCacheDir = context.getExternalCacheDir();
        if (externalCacheDir != null) {
            size += getDirSize(externalCacheDir);
        }
        
        return size;
    }
    
    /**
     * Get the size of a directory recursively.
     *
     * @param dir Directory to measure
     * @return Size in bytes
     */
    private long getDirSize(File dir) {
        if (dir == null || !dir.exists()) {
            return 0;
        }
        
        long size = 0;
        File[] files = dir.listFiles();
        
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    size += file.length();
                } else {
                    size += getDirSize(file);
                }
            }
        }
        
        return size;
    }
    
    /**
     * Clear low priority cache entries.
     *
     * @param percentage    Percentage of cache to clear (0-100)
     * @param priorityLevel Priority level threshold (entries with lower priority will be cleared)
     */
    public void clearLowPriorityCache(int percentage, int priorityLevel) {
        if (percentage <= 0) {
            return;
        }
        
        executor.submit(() -> {
            Log.d(TAG, "Clearing " + percentage + "% of low priority cache (priority < " + 
                     priorityLevel + ")");
            
            // Implement cache priority levels and selective clearing
            if (percentage >= 50) {
                imageCache.handleTrimMemory(android.content.ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW);
                audioCache.handleTrimMemory(android.content.ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW);
            } else {
                imageCache.handleTrimMemory(android.content.ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE);
                audioCache.handleTrimMemory(android.content.ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE);
            }
        });
    }
    
    /**
     * Check if caching is enabled.
     *
     * @return true if enabled
     */
    public boolean isCacheEnabled() {
        return preferences.getBoolean(KEY_CACHE_ENABLED, true);
    }
    
    /**
     * Set whether caching is enabled.
     *
     * @param enabled true to enable, false to disable
     */
    public void setCacheEnabled(boolean enabled) {
        preferences.edit().putBoolean(KEY_CACHE_ENABLED, enabled).apply();
    }
    
    /**
     * Get the maximum cache size in bytes.
     *
     * @return Maximum cache size or 0 for unlimited
     */
    public long getMaxCacheSize() {
        // Default to 50MB
        long defaultSize = 50 * 1024 * 1024;
        
        // Get from preferences or use default
        return preferences.getLong(KEY_MAX_CACHE_SIZE, defaultSize);
    }
    
    /**
     * Set the maximum cache size in bytes.
     *
     * @param maxSizeBytes Maximum cache size or 0 for unlimited
     */
    public void setMaxCacheSize(long maxSizeBytes) {
        preferences.edit().putLong(KEY_MAX_CACHE_SIZE, maxSizeBytes).apply();
    }
    
    /**
     * Clear all caches.
     */
    public void clearAllCaches() {
        executor.submit(() -> {
            dataCache.clear();
            imageCache.clearCache();
            audioCache.clearCache();
            
            Log.d(TAG, "All caches cleared");
        });
    }
    
    /**
     * Handle memory trim event.
     *
     * @param level Memory trim level
     */
    public void handleTrimMemory(int level) {
        if (level >= android.content.ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW) {
            // Aggressively clear caches
            executor.submit(() -> {
                imageCache.handleTrimMemory(level);
                audioCache.handleTrimMemory(level);
                
                Log.d(TAG, "Cache trimmed due to low memory (level " + level + ")");
            });
        }
    }
    
    /**
     * Get cache statistics.
     *
     * @return Map of statistics
     */
    public Map<String, String> getStatistics() {
        Map<String, String> stats = new HashMap<>();
        
        // Overall statistics
        stats.put("Cache Enabled", String.valueOf(isCacheEnabled()));
        stats.put("Max Cache Size", MemoryOptimizer.formatMemorySize(getMaxCacheSize()));
        stats.put("Total Cache Size", MemoryOptimizer.formatMemorySize(getTotalCacheSize()));
        stats.put("Last Maintenance", new java.util.Date(lastMaintenanceTime).toString());
        
        // Available storage
        stats.put("Available Storage", MemoryOptimizer.formatMemorySize(getAvailableStorage()));
        
        // Add subsystem statistics with prefixes
        Map<String, String> dataStats = dataCache.getStatistics();
        for (Map.Entry<String, String> entry : dataStats.entrySet()) {
            stats.put("Data: " + entry.getKey(), entry.getValue());
        }
        
        Map<String, String> imageStats = imageCache.getStatistics();
        for (Map.Entry<String, String> entry : imageStats.entrySet()) {
            stats.put("Image: " + entry.getKey(), entry.getValue());
        }
        
        Map<String, String> audioStats = audioCache.getStatistics();
        for (Map.Entry<String, String> entry : audioStats.entrySet()) {
            stats.put("Audio: " + entry.getKey(), entry.getValue());
        }
        
        return stats;
    }
    
    /**
     * Get available storage in bytes.
     *
     * @return Available storage
     */
    private long getAvailableStorage() {
        try {
            File path = ContextCompat.getDataDir(context);
            if (path == null) {
                path = context.getFilesDir();
            }
            
            if (path == null) {
                return 0;
            }
            
            StatFs stats = new StatFs(path.getAbsolutePath());
            return stats.getAvailableBlocksLong() * stats.getBlockSizeLong();
        } catch (Exception e) {
            Log.e(TAG, "Error getting available storage", e);
            return 0;
        }
    }
    
    /**
     * Release all resources.
     */
    public void release() {
        // Shutdown executor
        executor.shutdown();
        try {
            executor.awaitTermination(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Release subsystems
        dataCache.release();
        imageCache.release();
        audioCache.release();
    }
    
    /**
     * Cache data in memory and disk.
     *
     * @param key  Cache key
     * @param data Data to cache
     * @param <T>  Data type
     */
    public <T extends Serializable> void cacheData(String key, T data) {
        if (!isCacheEnabled()) {
            return;
        }
        
        dataCache.put(key, data);
    }
    
    /**
     * Get data from cache.
     *
     * @param key          Cache key
     * @param defaultValue Default value if not in cache
     * @param <T>          Data type
     * @return Cached data or default value
     */
    public <T extends Serializable> T getData(String key, T defaultValue) {
        if (!isCacheEnabled()) {
            return defaultValue;
        }
        
        return dataCache.get(key, defaultValue);
    }
    
    /**
     * Load image into an ImageView.
     *
     * @param imageView ImageView to load into
     * @param url       Image URL
     */
    public void loadImage(@NonNull android.widget.ImageView imageView, String url) {
        if (!isCacheEnabled()) {
            imageView.setImageResource(android.R.drawable.ic_menu_gallery);
            return;
        }
        
        imageCache.loadImage(imageView, url);
    }
    
    /**
     * Load audio file asynchronously.
     *
     * @param url      Audio URL
     * @param callback Callback for result
     */
    public void loadAudio(String url, AudioCache.AudioCallback callback) {
        if (!isCacheEnabled()) {
            callback.onError(new IllegalStateException("Caching is disabled"));
            return;
        }
        
        audioCache.loadAudio(url, callback);
    }
    
    /**
     * Preload resources for faster access.
     *
     * @param urls List of URLs to preload
     */
    public void preloadResources(@NonNull java.util.List<String> urls) {
        if (!isCacheEnabled()) {
            return;
        }
        
        executor.submit(() -> {
            for (String url : urls) {
                if (url.endsWith(".mp3") || url.endsWith(".wav") || url.endsWith(".ogg")) {
                    audioCache.preload(url);
                } else if (url.endsWith(".jpg") || url.endsWith(".jpeg") || 
                           url.endsWith(".png") || url.endsWith(".gif")) {
                    imageCache.preload(url);
                }
            }
        });
    }
    
    /**
     * Load bitmap from URL asynchronously.
     *
     * @param url      Image URL
     * @param callback Callback for result
     */
    public void loadBitmap(String url, ImageCache.ImageCallback callback) {
        if (!isCacheEnabled()) {
            callback.onError(new IllegalStateException("Caching is disabled"));
            return;
        }
        
        imageCache.loadImageAsync(url, callback);
    }
    
    /**
     * Calculate the optimal cache sizes based on device capabilities.
     */
    public void optimizeCacheSizes() {
        executor.submit(() -> {
            // Get device memory information
            MemoryOptimizer memoryOptimizer = MemoryOptimizer.getInstance(context);
            Map<String, String> memoryInfo = MemoryOptimizer.getDeviceMemoryInfo(context);
            
            // Calculate optimal maximum cache size
            // Default is 50MB, but adjust based on available storage
            long availableStorage = getAvailableStorage();
            long maxCacheSize;
            
            if (availableStorage > 2 * 1024 * 1024 * 1024) { // More than 2GB
                maxCacheSize = 200 * 1024 * 1024; // 200MB
            } else if (availableStorage > 1 * 1024 * 1024 * 1024) { // More than 1GB
                maxCacheSize = 100 * 1024 * 1024; // 100MB
            } else if (availableStorage > 500 * 1024 * 1024) { // More than 500MB
                maxCacheSize = 50 * 1024 * 1024; // 50MB
            } else {
                maxCacheSize = 25 * 1024 * 1024; // 25MB
            }
            
            // Set the maximum cache size
            setMaxCacheSize(maxCacheSize);
            
            // Resize image memory cache based on device memory
            boolean isLowMemory = MemoryOptimizer.isLowMemoryDevice(context);
            imageCache.resizeMemoryCache(isLowMemory ? 15 : 25); // 15% or 25% of available memory
            
            Log.d(TAG, "Cache sizes optimized for device: maxCacheSize=" + 
                     MemoryOptimizer.formatMemorySize(maxCacheSize) + 
                     ", lowMemory=" + isLowMemory);
        });
    }
}