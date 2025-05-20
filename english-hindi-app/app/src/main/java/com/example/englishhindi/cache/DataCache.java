package com.example.englishhindi.cache;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.LruCache;

import com.example.englishhindi.util.MemoryOptimizer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Comprehensive data caching system that combines memory and disk caching
 * for structured data objects. Optimized for performance and memory efficiency.
 */
public class DataCache {
    private static final String TAG = "DataCache";
    
    // Singleton instance
    private static DataCache instance;
    
    // Memory cache
    private final LruCache<String, CacheEntry<?>> memoryCache;
    
    // Context reference
    private final Context context;
    
    // Background executor for disk operations
    private final ExecutorService diskExecutor;
    
    // Disk cache directory
    private final File diskCacheDir;
    
    // Cache entry metadata tracking
    private final Map<String, CacheEntryMetadata> metadataMap;
    
    // Cache policy constants
    private static final long DEFAULT_EXPIRY_TIME = TimeUnit.HOURS.toMillis(1); // 1 hour
    private static final long MAX_EXPIRY_TIME = TimeUnit.DAYS.toMillis(7);      // 7 days
    
    // Cache statistics tracking
    private int memoryHits = 0;
    private int diskHits = 0;
    private int misses = 0;
    
    /**
     * Private constructor for singleton pattern.
     *
     * @param context Application context
     */
    private DataCache(Context context) {
        this.context = context.getApplicationContext();
        
        // Calculate cache size based on device memory
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8; // Use 1/8th of available memory
        
        // Initialize memory cache
        memoryCache = new LruCache<String, CacheEntry<?>>(cacheSize) {
            @Override
            protected int sizeOf(String key, CacheEntry<?> value) {
                // Rough estimation of entry size in KB
                // This could be improved with more accurate size calculation
                return 1 + (value.getData() != null ? estimateSize(value.getData()) : 0);
            }
        };
        
        // Initialize disk cache directory
        diskCacheDir = new File(context.getCacheDir(), "data_cache");
        if (!diskCacheDir.exists()) {
            diskCacheDir.mkdirs();
        }
        
        // Initialize metadata map
        metadataMap = new ConcurrentHashMap<>();
        
        // Initialize executor
        diskExecutor = Executors.newSingleThreadExecutor();
        
        // Load cache metadata from disk
        loadMetadata();
        
        // Schedule cleanup for expired entries
        scheduleCleanup();
    }
    
    /**
     * Get singleton instance.
     *
     * @param context Context
     * @return DataCache instance
     */
    public static synchronized DataCache getInstance(Context context) {
        if (instance == null) {
            instance = new DataCache(context);
        }
        return instance;
    }
    
    /**
     * Estimate the size of an object in KB.
     *
     * @param object Object to measure
     * @return Estimated size in KB
     */
    private int estimateSize(Object object) {
        if (object == null) {
            return 0;
        }
        
        // Simple estimation based on object type
        if (object instanceof String) {
            return ((String) object).length() / 512; // Assuming 2 bytes per char, 1KB = 512 chars
        } else if (object instanceof byte[]) {
            return ((byte[]) object).length / 1024;
        } else if (object instanceof List<?>) {
            List<?> list = (List<?>) object;
            int size = 4; // List overhead
            if (!list.isEmpty() && list.size() > 0) {
                // Estimate based on first item, multiply by count
                size += list.size() * estimateSize(list.get(0));
            }
            return size;
        } else if (object instanceof Map<?, ?>) {
            Map<?, ?> map = (Map<?, ?>) object;
            return 4 + map.size() * 4; // Simple estimate
        } else {
            // Default size estimate for other objects
            return 2; // 2KB default
        }
    }
    
    /**
     * Cache entry wrapper to store the value and metadata.
     *
     * @param <T> Data type
     */
    private static class CacheEntry<T> {
        private final T data;
        private final long expiryTime;
        private final CachePolicy policy;
        
        CacheEntry(T data, long expiryTime, CachePolicy policy) {
            this.data = data;
            this.expiryTime = expiryTime;
            this.policy = policy;
        }
        
        T getData() {
            return data;
        }
        
        long getExpiryTime() {
            return expiryTime;
        }
        
        CachePolicy getPolicy() {
            return policy;
        }
        
        boolean isExpired() {
            return System.currentTimeMillis() > expiryTime;
        }
    }
    
    /**
     * Cache entry metadata for disk cache tracking.
     */
    private static class CacheEntryMetadata implements Serializable {
        private static final long serialVersionUID = 1L;
        
        final String key;
        final long expiryTime;
        final CachePolicy policy;
        final long lastAccessTime;
        final int accessCount;
        
        CacheEntryMetadata(String key, long expiryTime, CachePolicy policy) {
            this.key = key;
            this.expiryTime = expiryTime;
            this.policy = policy;
            this.lastAccessTime = System.currentTimeMillis();
            this.accessCount = 1;
        }
        
        CacheEntryMetadata(String key, long expiryTime, CachePolicy policy, 
                         long lastAccessTime, int accessCount) {
            this.key = key;
            this.expiryTime = expiryTime;
            this.policy = policy;
            this.lastAccessTime = lastAccessTime;
            this.accessCount = accessCount;
        }
        
        CacheEntryMetadata withIncrementedAccessCount() {
            return new CacheEntryMetadata(key, expiryTime, policy, 
                                        System.currentTimeMillis(), accessCount + 1);
        }
        
        boolean isExpired() {
            return System.currentTimeMillis() > expiryTime;
        }
    }
    
    /**
     * Cache policy defining the behavior of cached data.
     */
    public enum CachePolicy {
        /**
         * Standard caching - stored in memory and disk with default expiry
         */
        STANDARD,
        
        /**
         * Memory only - not stored on disk
         */
        MEMORY_ONLY,
        
        /**
         * Disk only - not kept in memory cache
         */
        DISK_ONLY,
        
        /**
         * High priority - kept in memory as long as possible, disk backed
         */
        HIGH_PRIORITY,
        
        /**
         * Low priority - first to be evicted when memory pressure occurs
         */
        LOW_PRIORITY,
        
        /**
         * Persistent - stored for extended periods, useful for reference data
         */
        PERSISTENT
    }
    
    /**
     * Cache data with default policy and expiry.
     *
     * @param key  Cache key
     * @param data Data to cache
     * @param <T>  Data type
     */
    public <T extends Serializable> void put(String key, T data) {
        put(key, data, CachePolicy.STANDARD, DEFAULT_EXPIRY_TIME);
    }
    
    /**
     * Cache data with specified policy and default expiry.
     *
     * @param key    Cache key
     * @param data   Data to cache
     * @param policy Cache policy
     * @param <T>    Data type
     */
    public <T extends Serializable> void put(String key, T data, CachePolicy policy) {
        put(key, data, policy, getExpiryTimeForPolicy(policy));
    }
    
    /**
     * Cache data with specified policy and expiry.
     *
     * @param key        Cache key
     * @param data       Data to cache
     * @param policy     Cache policy
     * @param expiryTime Expiry time in milliseconds from now
     * @param <T>        Data type
     */
    public <T extends Serializable> void put(String key, T data, 
                                         CachePolicy policy, long expiryTime) {
        if (key == null || data == null) {
            return;
        }
        
        long absoluteExpiryTime = System.currentTimeMillis() + 
                                 Math.min(expiryTime, MAX_EXPIRY_TIME);
        
        // Create cache entry
        CacheEntry<T> entry = new CacheEntry<>(data, absoluteExpiryTime, policy);
        
        // Store in memory cache unless policy is DISK_ONLY
        if (policy != CachePolicy.DISK_ONLY) {
            memoryCache.put(key, entry);
        }
        
        // Store on disk unless policy is MEMORY_ONLY
        if (policy != CachePolicy.MEMORY_ONLY) {
            // Save metadata
            metadataMap.put(key, new CacheEntryMetadata(key, absoluteExpiryTime, policy));
            
            // Save to disk in background
            diskExecutor.execute(() -> {
                saveObjectToDisk(key, data, policy);
            });
        }
    }
    
    /**
     * Get data from cache.
     *
     * @param key          Cache key
     * @param defaultValue Default value if not found
     * @param <T>          Data type
     * @return Cached data or default value
     */
    @SuppressWarnings("unchecked")
    public <T extends Serializable> T get(String key, T defaultValue) {
        if (key == null) {
            return defaultValue;
        }
        
        // Check memory cache first
        CacheEntry<?> entry = memoryCache.get(key);
        if (entry != null) {
            if (!entry.isExpired()) {
                memoryHits++;
                
                // Update metadata access count
                CacheEntryMetadata metadata = metadataMap.get(key);
                if (metadata != null) {
                    metadataMap.put(key, metadata.withIncrementedAccessCount());
                }
                
                return (T) entry.getData();
            } else {
                // Expired entry, remove from memory cache
                memoryCache.remove(key);
            }
        }
        
        // Check if we should load from disk
        CacheEntryMetadata metadata = metadataMap.get(key);
        if (metadata != null && !metadata.isExpired() && 
            metadata.policy != CachePolicy.MEMORY_ONLY) {
            // Load from disk
            T data = loadObjectFromDisk(key);
            if (data != null) {
                diskHits++;
                
                // Update metadata access count
                metadataMap.put(key, metadata.withIncrementedAccessCount());
                
                // Add to memory cache unless policy is DISK_ONLY
                if (metadata.policy != CachePolicy.DISK_ONLY) {
                    memoryCache.put(key, new CacheEntry<>(data, metadata.expiryTime, metadata.policy));
                }
                
                return data;
            }
        }
        
        // Not found or expired
        misses++;
        return defaultValue;
    }
    
    /**
     * Get data from cache asynchronously.
     *
     * @param key      Cache key
     * @param callback Callback to handle result
     * @param <T>      Data type
     */
    public <T extends Serializable> void getAsync(String key, CacheCallback<T> callback) {
        if (key == null || callback == null) {
            return;
        }
        
        // Try to get from memory cache immediately
        try {
            @SuppressWarnings("unchecked")
            CacheEntry<T> entry = (CacheEntry<T>) memoryCache.get(key);
            if (entry != null && !entry.isExpired()) {
                memoryHits++;
                
                // Update metadata access count
                CacheEntryMetadata metadata = metadataMap.get(key);
                if (metadata != null) {
                    metadataMap.put(key, metadata.withIncrementedAccessCount());
                }
                
                callback.onSuccess(entry.getData());
                return;
            }
        } catch (ClassCastException e) {
            callback.onError(e);
            return;
        }
        
        // Check metadata to see if it's on disk
        CacheEntryMetadata metadata = metadataMap.get(key);
        if (metadata != null && !metadata.isExpired()) {
            // Load from disk in background
            diskExecutor.execute(() -> {
                try {
                    T data = loadObjectFromDisk(key);
                    if (data != null) {
                        diskHits++;
                        
                        // Update metadata access count
                        metadataMap.put(key, metadata.withIncrementedAccessCount());
                        
                        // Add to memory cache unless policy is DISK_ONLY
                        if (metadata.policy != CachePolicy.DISK_ONLY) {
                            CacheEntry<T> newEntry = new CacheEntry<>(data, 
                                                                     metadata.expiryTime, 
                                                                     metadata.policy);
                            memoryCache.put(key, newEntry);
                        }
                        
                        // Invoke callback on main thread
                        new android.os.Handler(android.os.Looper.getMainLooper())
                                .post(() -> callback.onSuccess(data));
                    } else {
                        misses++;
                        // Invoke callback on main thread
                        new android.os.Handler(android.os.Looper.getMainLooper())
                                .post(() -> callback.onNotFound());
                    }
                } catch (Exception e) {
                    // Invoke callback on main thread
                    new android.os.Handler(android.os.Looper.getMainLooper())
                            .post(() -> callback.onError(e));
                }
            });
        } else {
            misses++;
            callback.onNotFound();
        }
    }
    
    /**
     * Check if data exists in cache and is not expired.
     *
     * @param key Cache key
     * @return true if data exists and is valid
     */
    public boolean contains(String key) {
        if (key == null) {
            return false;
        }
        
        // Check memory cache first
        CacheEntry<?> entry = memoryCache.get(key);
        if (entry != null && !entry.isExpired()) {
            return true;
        }
        
        // Check disk cache via metadata
        CacheEntryMetadata metadata = metadataMap.get(key);
        return metadata != null && !metadata.isExpired() && 
               new File(diskCacheDir, generateFileName(key)).exists();
    }
    
    /**
     * Remove data from cache.
     *
     * @param key Cache key
     */
    public void remove(String key) {
        if (key == null) {
            return;
        }
        
        // Remove from memory cache
        memoryCache.remove(key);
        
        // Remove from metadata
        metadataMap.remove(key);
        
        // Delete from disk in background
        diskExecutor.execute(() -> {
            File file = new File(diskCacheDir, generateFileName(key));
            if (file.exists()) {
                file.delete();
            }
        });
    }
    
    /**
     * Clear all cached data.
     */
    public void clear() {
        // Clear memory cache
        memoryCache.evictAll();
        
        // Clear metadata
        metadataMap.clear();
        
        // Clear disk cache in background
        diskExecutor.execute(() -> {
            File[] files = diskCacheDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
        });
        
        // Reset statistics
        memoryHits = 0;
        diskHits = 0;
        misses = 0;
    }
    
    /**
     * Clear expired data.
     */
    public void clearExpired() {
        long now = System.currentTimeMillis();
        
        // Clear expired entries from metadata and disk
        List<String> expiredKeys = new ArrayList<>();
        for (Map.Entry<String, CacheEntryMetadata> entry : metadataMap.entrySet()) {
            if (entry.getValue().isExpired()) {
                expiredKeys.add(entry.getKey());
            }
        }
        
        // Remove expired entries
        for (String key : expiredKeys) {
            remove(key);
        }
        
        // Log cleanup info
        if (!expiredKeys.isEmpty()) {
            Log.d(TAG, "Cleared " + expiredKeys.size() + " expired entries");
        }
    }
    
    /**
     * Save object to disk.
     *
     * @param key    Cache key
     * @param object Object to save
     * @param policy Cache policy
     * @param <T>    Object type
     * @return true if successful
     */
    private <T extends Serializable> boolean saveObjectToDisk(String key, T object, CachePolicy policy) {
        if (key == null || object == null) {
            return false;
        }
        
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        
        try {
            File file = new File(diskCacheDir, generateFileName(key));
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            return true;
        } catch (IOException e) {
            Log.e(TAG, "Error saving object to disk", e);
            return false;
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                Log.e(TAG, "Error closing streams", e);
            }
        }
    }
    
    /**
     * Load object from disk.
     *
     * @param key Cache key
     * @param <T> Object type
     * @return Loaded object or null if not found
     */
    @SuppressWarnings("unchecked")
    private <T extends Serializable> T loadObjectFromDisk(String key) {
        if (key == null) {
            return null;
        }
        
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        
        try {
            File file = new File(diskCacheDir, generateFileName(key));
            if (!file.exists()) {
                return null;
            }
            
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            return (T) ois.readObject();
        } catch (Exception e) {
            Log.e(TAG, "Error loading object from disk", e);
            return null;
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                Log.e(TAG, "Error closing streams", e);
            }
        }
    }
    
    /**
     * Generate a filename for a cache key.
     *
     * @param key Cache key
     * @return Filename
     */
    private String generateFileName(String key) {
        // Simple MD5 hash would be better, but for simplicity:
        return key.replaceAll("[^a-zA-Z0-9]", "_") + ".cache";
    }
    
    /**
     * Get expiry time for a cache policy.
     *
     * @param policy Cache policy
     * @return Expiry time in milliseconds
     */
    private long getExpiryTimeForPolicy(CachePolicy policy) {
        switch (policy) {
            case PERSISTENT:
                return TimeUnit.DAYS.toMillis(30); // 30 days
            case HIGH_PRIORITY:
                return TimeUnit.DAYS.toMillis(1);  // 1 day
            case LOW_PRIORITY:
                return TimeUnit.HOURS.toMillis(1); // 1 hour
            case STANDARD:
            case MEMORY_ONLY:
            case DISK_ONLY:
            default:
                return DEFAULT_EXPIRY_TIME;       // 1 hour
        }
    }
    
    /**
     * Load cache metadata from disk.
     */
    @SuppressWarnings("unchecked")
    private void loadMetadata() {
        File metadataFile = new File(diskCacheDir, "metadata.dat");
        if (!metadataFile.exists()) {
            return;
        }
        
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        
        try {
            fis = new FileInputStream(metadataFile);
            ois = new ObjectInputStream(fis);
            Map<String, CacheEntryMetadata> loadedMetadata = (Map<String, CacheEntryMetadata>) ois.readObject();
            
            if (loadedMetadata != null) {
                metadataMap.putAll(loadedMetadata);
                
                // Remove expired entries
                Iterator<Map.Entry<String, CacheEntryMetadata>> iterator = 
                        metadataMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    if (iterator.next().getValue().isExpired()) {
                        iterator.remove();
                    }
                }
                
                Log.d(TAG, "Loaded " + metadataMap.size() + " metadata entries");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error loading metadata", e);
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                Log.e(TAG, "Error closing streams", e);
            }
        }
    }
    
    /**
     * Save cache metadata to disk.
     */
    private void saveMetadata() {
        File metadataFile = new File(diskCacheDir, "metadata.dat");
        
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        
        try {
            fos = new FileOutputStream(metadataFile);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(new HashMap<>(metadataMap));
        } catch (IOException e) {
            Log.e(TAG, "Error saving metadata", e);
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                Log.e(TAG, "Error closing streams", e);
            }
        }
    }
    
    /**
     * Schedule periodic cleanup of expired entries.
     */
    private void scheduleCleanup() {
        // Schedule cleanup task every hour
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    // Sleep for 1 hour
                    Thread.sleep(TimeUnit.HOURS.toMillis(1));
                    
                    // Perform cleanup
                    clearExpired();
                    
                    // Save metadata
                    saveMetadata();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }, "DataCache-Cleanup").start();
    }
    
    /**
     * Callback interface for asynchronous cache operations.
     *
     * @param <T> Data type
     */
    public interface CacheCallback<T> {
        void onSuccess(T data);
        void onNotFound();
        void onError(Exception e);
    }
    
    /**
     * Get cache statistics.
     *
     * @return Cache statistics
     */
    public Map<String, String> getStatistics() {
        Map<String, String> stats = new HashMap<>();
        
        stats.put("Memory Cache Size", memoryCache.size() + " entries");
        stats.put("Memory Cache Max Size", memoryCache.maxSize() + " KB");
        stats.put("Disk Cache Entries", metadataMap.size() + " entries");
        
        // Calculate hit rate
        int totalRequests = memoryHits + diskHits + misses;
        float hitRate = totalRequests > 0 ? 
                       ((float) (memoryHits + diskHits) / totalRequests) * 100 : 0;
        
        stats.put("Memory Hits", String.valueOf(memoryHits));
        stats.put("Disk Hits", String.valueOf(diskHits));
        stats.put("Misses", String.valueOf(misses));
        stats.put("Hit Rate", String.format("%.1f%%", hitRate));
        
        // Calculate disk cache size
        long diskSize = 0;
        File[] files = diskCacheDir.listFiles();
        if (files != null) {
            for (File file : files) {
                diskSize += file.length();
            }
        }
        
        stats.put("Disk Cache Size", MemoryOptimizer.formatMemorySize(diskSize));
        
        return stats;
    }
    
    /**
     * Pre-load data into cache.
     *
     * @param dataMap Map of key-value pairs to cache
     * @param policy  Cache policy to apply
     * @param <T>     Data type
     */
    public <T extends Serializable> void preload(Map<String, T> dataMap, CachePolicy policy) {
        if (dataMap == null || dataMap.isEmpty()) {
            return;
        }
        
        for (Map.Entry<String, T> entry : dataMap.entrySet()) {
            put(entry.getKey(), entry.getValue(), policy);
        }
    }
    
    /**
     * Get the maximum memory cache size in KB.
     *
     * @return Maximum cache size
     */
    public int getMaxMemoryCacheSize() {
        return memoryCache.maxSize();
    }
    
    /**
     * Resize the memory cache.
     *
     * @param maxSize New maximum size in KB
     */
    public void resizeMemoryCache(int maxSize) {
        memoryCache.resize(maxSize);
    }
    
    /**
     * Release resources.
     */
    public void release() {
        // Save metadata before releasing
        saveMetadata();
        
        // Shut down executor
        diskExecutor.shutdown();
        try {
            if (!diskExecutor.awaitTermination(500, TimeUnit.MILLISECONDS)) {
                diskExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            diskExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Batch put operation.
     *
     * @param entries Map of entries to cache
     * @param policy  Cache policy
     * @param <T>     Data type
     */
    public <T extends Serializable> void putAll(@NonNull Map<String, T> entries, 
                                             @NonNull CachePolicy policy) {
        if (entries.isEmpty()) {
            return;
        }
        
        long expiryTime = getExpiryTimeForPolicy(policy);
        for (Map.Entry<String, T> entry : entries.entrySet()) {
            put(entry.getKey(), entry.getValue(), policy, expiryTime);
        }
    }
    
    /**
     * Remove all entries with the given prefix.
     *
     * @param keyPrefix Key prefix
     * @return Number of entries removed
     */
    public int removeByPrefix(String keyPrefix) {
        if (keyPrefix == null || keyPrefix.isEmpty()) {
            return 0;
        }
        
        List<String> keysToRemove = new ArrayList<>();
        
        // Find all keys with the prefix
        for (String key : metadataMap.keySet()) {
            if (key.startsWith(keyPrefix)) {
                keysToRemove.add(key);
            }
        }
        
        // Remove all found keys
        for (String key : keysToRemove) {
            remove(key);
        }
        
        return keysToRemove.size();
    }
    
    /**
     * Get all entries with the given prefix.
     *
     * @param keyPrefix Key prefix
     * @return Map of matching entries
     */
    @SuppressWarnings("unchecked")
    public <T extends Serializable> Map<String, T> getAllByPrefix(String keyPrefix) {
        if (keyPrefix == null || keyPrefix.isEmpty()) {
            return new HashMap<>();
        }
        
        Map<String, T> result = new HashMap<>();
        
        // Find all keys with the prefix
        for (String key : metadataMap.keySet()) {
            if (key.startsWith(keyPrefix)) {
                T value = get(key, null);
                if (value != null) {
                    result.put(key, value);
                }
            }
        }
        
        return result;
    }
}