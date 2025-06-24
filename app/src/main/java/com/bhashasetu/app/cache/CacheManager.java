package com.bhashasetu.app.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.bhashasetu.app.model.Word;
import com.bhashasetu.app.util.FileUtils;
import com.bhashasetu.app.util.NetworkUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Enhanced version of CacheManager that implements intelligent prefetching,
 * better validation, and more efficient cache management.
 */
public class CacheManager {
    private static final String TAG = "CacheManager";
    
    private static final String AUDIO_CACHE_DIR = "audio_cache";
    private static final String IMAGE_CACHE_DIR = "image_cache";
    private static final String DATA_CACHE_DIR = "data_cache";
    private static final String PREF_CACHE = "cache_prefs";
    private static final String KEY_MAX_STORAGE_MB = "max_storage_mb";
    private static final String KEY_LAST_CLEANUP = "last_cleanup";
    
    // Cache validation constants
    private static final long VALIDATION_INTERVAL = 24 * 60 * 60 * 1000; // 24 hours
    private static final int MAX_CONCURRENT_DOWNLOADS = 5;
    
    private static CacheManager instance;
    
    private Context context;
    private Executor downloadExecutor;
    private ScheduledExecutorService maintenanceExecutor;
    private CacheDatabaseHelper dbHelper;
    private NetworkUtils networkUtils;
    private SharedPreferences prefs;
    
    // Maps to track download operations and queues
    private Set<String> currentDownloads = new HashSet<>();
    private Map<String, List<CacheCallback>> pendingCallbacks = new HashMap<>();
    private Map<String, CacheRequest> downloadQueue = new ConcurrentHashMap<>();
    private AtomicInteger activeDownloads = new AtomicInteger(0);
    
    // Cache statistics
    private long lastCacheSize = 0;
    private long lastCacheCalculationTime = 0;
    
    private CacheManager(Context context) {
        this.context = context.getApplicationContext();
        this.downloadExecutor = Executors.newFixedThreadPool(MAX_CONCURRENT_DOWNLOADS);
        this.maintenanceExecutor = Executors.newScheduledThreadPool(1);
        this.dbHelper = new CacheDatabaseHelper(context);
        this.networkUtils = NetworkUtils.getInstance(context);
        this.prefs = context.getSharedPreferences(PREF_CACHE, Context.MODE_PRIVATE);
        
        // Ensure cache directories exist
        createCacheDirectories();
        
        // Schedule maintenance tasks
        scheduleMaintenanceTasks();
    }
    
    /**
     * Get the singleton instance of the CacheManager
     * @param context Application context
     * @return CacheManager instance
     */
    public static synchronized CacheManager getInstance(Context context) {
        if (instance == null) {
            instance = new CacheManager(context);
        }
        return instance;
    }
    
    /**
     * Create necessary cache directories if they don't exist
     */
    private void createCacheDirectories() {
        File audioCacheDir = new File(context.getFilesDir(), AUDIO_CACHE_DIR);
        if (!audioCacheDir.exists()) {
            audioCacheDir.mkdirs();
        }
        
        File imageCacheDir = new File(context.getFilesDir(), IMAGE_CACHE_DIR);
        if (!imageCacheDir.exists()) {
            imageCacheDir.mkdirs();
        }
        
        File dataCacheDir = new File(context.getFilesDir(), DATA_CACHE_DIR);
        if (!dataCacheDir.exists()) {
            dataCacheDir.mkdirs();
        }
    }
    
    /**
     * Schedule periodic maintenance tasks
     */
    private void scheduleMaintenanceTasks() {
        // Check cache size and clean if needed every 6 hours
        maintenanceExecutor.scheduleAtFixedRate(() -> {
            try {
                performCacheMaintenance();
            } catch (Exception e) {
                Log.e(TAG, "Error during cache maintenance", e);
            }
        }, 1, 6, TimeUnit.HOURS);
        
        // Validate cache integrity daily
        maintenanceExecutor.scheduleAtFixedRate(() -> {
            try {
                validateCacheIntegrity();
            } catch (Exception e) {
                Log.e(TAG, "Error validating cache integrity", e);
            }
        }, 2, 24, TimeUnit.HOURS);
    }
    
    /**
     * Perform cache maintenance operations
     * - Check cache size against limits
     * - Remove old entries if needed
     * - Remove corrupted files
     */
    private void performCacheMaintenance() {
        Log.d(TAG, "Performing cache maintenance");
        
        // Get current cache size
        long cacheSize = getTotalCacheSize();
        long maxCacheSizeBytes = getMaxStorageMB() * 1024 * 1024L;
        
        // If over limit, clean up
        if (cacheSize > maxCacheSizeBytes) {
            long bytesToFree = cacheSize - (maxCacheSizeBytes * 9 / 10); // Free up to 90% of max
            Log.d(TAG, "Cache over limit, freeing " + FileUtils.getReadableFileSize(bytesToFree));
            
            // First try removing expired entries
            long freedBytes = removeExpiredEntries();
            
            // If still need more space, remove low priority entries
            if (freedBytes < bytesToFree) {
                long remaining = bytesToFree - freedBytes;
                freedBytes += removeLowPriorityEntries(remaining);
            }
            
            // If still need more space, remove least recently used entries
            if (freedBytes < bytesToFree) {
                long remaining = bytesToFree - freedBytes;
                freedBytes += removeLeastRecentlyUsedEntries(remaining);
            }
            
            Log.d(TAG, "Freed " + FileUtils.getReadableFileSize(freedBytes) + " of cache space");
        }
        
        // Update last cleanup time
        prefs.edit().putLong(KEY_LAST_CLEANUP, System.currentTimeMillis()).apply();
    }
    
    /**
     * Validate the integrity of cached files
     * - Check if files exist and aren't corrupted
     * - Remove invalid cache entries
     */
    private void validateCacheIntegrity() {
        Log.d(TAG, "Validating cache integrity");
        
        List<CacheEntry> allEntries = dbHelper.getAllCacheEntries();
        int removed = 0;
        
        for (CacheEntry entry : allEntries) {
            File file = new File(entry.getFilePath());
            
            // Check if file exists and has valid size
            if (!file.exists() || file.length() == 0) {
                dbHelper.deleteCacheEntry(entry.getUrl());
                removed++;
                continue;
            }
            
            // For future implementation: add checksum validation
        }
        
        if (removed > 0) {
            Log.d(TAG, "Removed " + removed + " invalid cache entries during validation");
        }
    }
    
    /**
     * Cache an audio file from a URL
     * @param url URL to download from
     * @param filename Filename to save as (without path)
     * @param priority Priority level (higher number = higher priority)
     * @param callback Callback for completion status
     */
    public void cacheAudioFile(String url, String filename, int priority, CacheCallback callback) {
        cacheFile(url, new File(getAudioCacheDir(), filename), CacheEntry.TYPE_AUDIO, priority, callback);
    }
    
    /**
     * Cache an image file from a URL
     * @param url URL to download from
     * @param filename Filename to save as (without path)
     * @param priority Priority level (higher number = higher priority)
     * @param callback Callback for completion status
     */
    public void cacheImageFile(String url, String filename, int priority, CacheCallback callback) {
        cacheFile(url, new File(getImageCacheDir(), filename), CacheEntry.TYPE_IMAGE, priority, callback);
    }
    
    /**
     * Cache data from a URL
     * @param url URL to download from
     * @param filename Filename to save as (without path)
     * @param priority Priority level (higher number = higher priority)
     * @param callback Callback for completion status
     */
    public void cacheDataFile(String url, String filename, int priority, CacheCallback callback) {
        cacheFile(url, new File(getDataCacheDir(), filename), CacheEntry.TYPE_DATA, priority, callback);
    }

    /**
     * Generic method to cache a file from a URL with improved queueing and resilience
     *
     * @param url      URL to download from
     * @param destFile Destination file
     * @param type     Type of content (audio, image, data)
     * @param priority Priority level
     * @param callback Callback for completion status
     */
    private void cacheFile(String url, File destFile, String type, int priority, CacheCallback callback) {

    }
    
    /**
     * Process the download queue
     */
    private void processDownloadQueue() {
        // Check if we can start more downloads
        if (activeDownloads.get() >= MAX_CONCURRENT_DOWNLOADS) {
            return;
        }
        
        // Find the highest priority request
        CacheRequest nextRequest = null;
        int highestPriority = Integer.MIN_VALUE;
        
        for (CacheRequest request : downloadQueue.values()) {
            if (request.getPriority() > highestPriority) {
                highestPriority = request.getPriority();
                nextRequest = request;
            }
        }
        
        if (nextRequest == null) {
            return; // No requests in queue
        }
        
        // Remove from queue and add to active set
        final CacheRequest request = nextRequest;
        downloadQueue.remove(request.getUrl());
        
        synchronized (currentDownloads) {
            currentDownloads.add(request.getUrl());
        }
        
        activeDownloads.incrementAndGet();
        
        // Start download
        downloadExecutor.execute(() -> {
            try {
                performDownload(request);
            } finally {
                // Clean up
                synchronized (currentDownloads) {
                    currentDownloads.remove(request.getUrl());
                }
                activeDownloads.decrementAndGet();
                
                // Process next in queue
                processDownloadQueue();
            }
        });
    }
    
    /**
     * Perform the actual download operation
     * @param request The download request
     */
    private void performDownload(CacheRequest request) {
        boolean success = false;
        String filePath = null;
        
        try {
            // Check network connection
            if (!networkUtils.isNetworkAvailable()) {
                Log.d(TAG, "Network not available, cannot download: " + request.getUrl());
                return;
            }
            
            // Create temp file for download
            File tempFile = getTempFile(request.getDestFile());
            
            // Download the file to temp location first
            success = FileUtils.downloadFile(request.getUrl(), tempFile);
            
            if (success && tempFile.exists() && tempFile.length() > 0) {
                // Move from temp to final location
                try {
                    Files.move(tempFile.toPath(), request.getDestFile().toPath(), 
                            StandardCopyOption.REPLACE_EXISTING);
                    
                    // Save entry in database
                    CacheEntry entry = new CacheEntry(
                            request.getUrl(), 
                            request.getDestFile().getAbsolutePath(), 
                            request.getType(),
                            System.currentTimeMillis(), 
                            request.getPriority());
                    dbHelper.insertOrUpdateCacheEntry(entry);
                    
                    filePath = request.getDestFile().getAbsolutePath();
                } catch (IOException e) {
                    Log.e(TAG, "Error moving temp file to destination", e);
                    success = false;
                }
            } else {
                // Clean up failed temp file
                if (tempFile.exists()) {
                    tempFile.delete();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error caching file: " + request.getUrl(), e);
            success = false;
        }
        
        // Notify all pending callbacks
        final boolean finalSuccess = success;
        final String finalFilePath = filePath;
        
        synchronized (pendingCallbacks) {
            List<CacheCallback> callbacks = pendingCallbacks.remove(request.getUrl());
            if (callbacks != null) {
                for (CacheCallback callback : callbacks) {
                    try {
                        callback.onCacheComplete(finalSuccess, finalFilePath);
                    } catch (Exception e) {
                        Log.e(TAG, "Error in cache callback", e);
                    }
                }
            }
        }
    }
    
    /**
     * Get a temporary file for downloading
     * @param destFile The destination file
     * @return A temporary file
     */
    private File getTempFile(File destFile) {
        return new File(destFile.getAbsolutePath() + ".tmp");
    }
    
    /**
     * Get a cached audio file path if available
     * @param url URL of the original audio
     * @return File path or null if not cached
     */
    public String getCachedAudioPath(String url) {
        return getCachedFilePath(url);
    }
    
    /**
     * Get a cached image file path if available
     * @param url URL of the original image
     * @return File path or null if not cached
     */
    public String getCachedImagePath(String url) {
        return getCachedFilePath(url);
    }
    
    /**
     * Get a cached data file path if available
     * @param url URL of the original data
     * @return File path or null if not cached
     */
    public String getCachedDataPath(String url) {
        return getCachedFilePath(url);
    }
    
    /**
     * Generic method to get a cached file path with improved validation
     * @param url URL of the original content
     * @return File path or null if not cached
     */
    private String getCachedFilePath(String url) {
        CacheEntry entry = dbHelper.getCacheEntry(url);
        if (entry != null) {
            File file = new File(entry.getFilePath());
            if (file.exists() && file.length() > 0) {
                // Update last accessed time
                entry.setLastAccessed(System.currentTimeMillis());
                dbHelper.updateLastAccessedTime(url, entry.getLastAccessed());
                return entry.getFilePath();
            } else {
                // File doesn't exist or is empty
                dbHelper.deleteCacheEntry(url);
            }
        }
        return null;
    }
    
    /**
     * Validate a cache entry by checking if the file needs updating
     * @param entry The cache entry to validate
     */
    private void validateCacheEntry(CacheEntry entry) {
        if (!networkUtils.isNetworkAvailable()) {
            return; // Can't validate without network
        }
        
        try {
            File file = new File(entry.getFilePath());
            boolean needsUpdate = FileUtils.fileNeedsUpdate(entry.getUrl(), file);
            
            if (needsUpdate) {
                Log.d(TAG, "Cache entry needs update: " + entry.getUrl());
                
                // Re-download the file
                CacheRequest request = new CacheRequest(
                        entry.getUrl(), 
                        file,
                        entry.getType(),
                        entry.getPriority());
                
                // Add to queue with high priority
                request.setPriority(request.getPriority() + 50); // Boost priority for updates
                downloadQueue.put(entry.getUrl(), request);
                
                // Process queue
                processDownloadQueue();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error validating cache entry: " + entry.getUrl(), e);
        }
    }
    
    /**
     * Prefetch audio files for a list of words with improved prioritization
     * @param words List of words to prefetch audio for
     */
    public void prefetchWordAudio(List<Word> words) {
        if (words == null || words.isEmpty()) {
            return;
        }
        
        downloadExecutor.execute(() -> {
            for (Word word : words) {
                // Skip if not online
                if (!networkUtils.isNetworkAvailable()) {
                    break;
                }
                
                String audioUrl = word.getAudioUrl();
                if (audioUrl != null && !audioUrl.isEmpty()) {
                    // Create filename from word
                    String filename = "word_" + word.getId() + "_" + word.getEnglishWord() + ".mp3";
                    
                    // Calculate priority based on word frequency, level and favorites status
                    int priority = calculatePriority(word);
                    
                    cacheAudioFile(audioUrl, filename, priority, null);
                }
            }
        });
    }
    
    /**
     * Prefetch images for a list of words with improved prioritization
     * @param words List of words to prefetch images for
     */
    public void prefetchWordImages(List<Word> words) {
        if (words == null || words.isEmpty()) {
            return;
        }
        
        downloadExecutor.execute(() -> {
            for (Word word : words) {
                // Skip if not online
                if (!networkUtils.isNetworkAvailable()) {
                    break;
                }
                
                String imageUrl = word.getImageUrl();
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    // Create filename from word
                    String filename = "word_" + word.getId() + "_" + word.getEnglishWord() + ".jpg";
                    
                    // Calculate priority based on word frequency, level and favorites status
                    int priority = calculatePriority(word);
                    
                    cacheImageFile(imageUrl, filename, priority, null);
                }
            }
        });
    }
    
    /**
     * Calculate priority for a word based on its properties
     * @param word The word
     * @return Priority value (higher = more important)
     */
    private int calculatePriority(Word word) {
        int priority = word.getUsageFrequency();
        
        // Boost priority for favorites
        if (word.isFavorite()) {
            priority += 50;
        }
        
        // Boost priority for beginner level words
        if (word.getDifficultyLevel() <= 2) {
            priority += 30;
        }
        
        // Boost priority for recently accessed words
        if (System.currentTimeMillis() - word.getLastAccessed() < 7 * 24 * 60 * 60 * 1000) {
            priority += 20;
        }
        
        return priority;
    }
    
    /**
     * Remove expired cache entries
     * @return Number of bytes freed
     */
    private long removeExpiredEntries() {
        long freedBytes = 0;
        
        // Define expiration time as 60 days
        long expirationTime = System.currentTimeMillis() - (60L * 24 * 60 * 60 * 1000);
        List<CacheEntry> expiredEntries = dbHelper.getEntriesOlderThan(expirationTime);
        
        for (CacheEntry entry : expiredEntries) {
            // Delete file
            File file = new File(entry.getFilePath());
            if (file.exists()) {
                freedBytes += file.length();
                file.delete();
            }
            
            // Delete entry
            dbHelper.deleteCacheEntry(entry.getUrl());
        }
        
        return freedBytes;
    }
    
    /**
     * Remove low priority cache entries to free up space
     * @param bytesToFree Target number of bytes to free
     * @return Number of bytes freed
     */
    private long removeLowPriorityEntries(long bytesToFree) {
        long freedBytes = 0;
        
        // Get low priority entries (priority < 30)
        List<CacheEntry> lowPriorityEntries = dbHelper.getLowPriorityEntries(30, 100);
        
        for (CacheEntry entry : lowPriorityEntries) {
            // Delete file
            File file = new File(entry.getFilePath());
            if (file.exists()) {
                freedBytes += file.length();
                file.delete();
                
                // Delete entry
                dbHelper.deleteCacheEntry(entry.getUrl());
                
                // Check if we've freed enough space
                if (freedBytes >= bytesToFree) {
                    break;
                }
            }
        }
        
        return freedBytes;
    }
    
    /**
     * Remove least recently used cache entries to free up space
     * @param bytesToFree Target number of bytes to free
     * @return Number of bytes freed
     */
    private long removeLeastRecentlyUsedEntries(long bytesToFree) {
        long freedBytes = 0;
        
        // Get least recently used entries
        List<CacheEntry> lruEntries = dbHelper.getLeastRecentlyUsedEntries(50);
        
        for (CacheEntry entry : lruEntries) {
            // Delete file
            File file = new File(entry.getFilePath());
            if (file.exists()) {
                freedBytes += file.length();
                file.delete();
                
                // Delete entry
                dbHelper.deleteCacheEntry(entry.getUrl());
                
                // Check if we've freed enough space
                if (freedBytes >= bytesToFree) {
                    break;
                }
            }
        }
        
        return freedBytes;
    }
    
    /**
     * Clear old cache entries to free up space
     * @param maxAgeMillis Maximum age of cache entries to keep (in milliseconds)
     */
    public void clearOldCache(long maxAgeMillis) {
        downloadExecutor.execute(() -> {
            List<CacheEntry> oldEntries = dbHelper.getEntriesOlderThan(
                    System.currentTimeMillis() - maxAgeMillis);
            
            for (CacheEntry entry : oldEntries) {
                // Delete file
                File file = new File(entry.getFilePath());
                if (file.exists()) {
                    file.delete();
                }
                
                // Delete entry
                dbHelper.deleteCacheEntry(entry.getUrl());
            }
        });
    }
    
    /**
     * Clear all cache entries
     */
    public void clearAllCache() {
        downloadExecutor.execute(() -> {
            // Delete all files in cache directories
            deleteFilesInDir(getAudioCacheDir());
            deleteFilesInDir(getImageCacheDir());
            deleteFilesInDir(getDataCacheDir());
            
            // Clear database
            dbHelper.deleteAllEntries();
            
            // Reset cache stats
            lastCacheSize = 0;
            lastCacheCalculationTime = 0;
        });
    }
    
    /**
     * Delete all files in a directory
     * @param dir Directory to clear
     */
    private void deleteFilesInDir(File dir) {
        if (dir.exists()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        file.delete();
                    }
                }
            }
        }
    }
    
    /**
     * Get the total size of all cached files with caching for performance
     * @return Size in bytes
     */
    public long getTotalCacheSize() {
        // Use cached value if recent
        if (System.currentTimeMillis() - lastCacheCalculationTime < 5 * 60 * 1000) {
            return lastCacheSize;
        }
        
        long size = 0;
        
        size += getDirSize(getAudioCacheDir());
        size += getDirSize(getImageCacheDir());
        size += getDirSize(getDataCacheDir());
        
        // Cache the result
        lastCacheSize = size;
        lastCacheCalculationTime = System.currentTimeMillis();
        
        return size;
    }
    
    /**
     * Get the size of a directory and its contents
     * @param dir Directory to measure
     * @return Size in bytes
     */
    private long getDirSize(File dir) {
        long size = 0;
        
        if (dir.exists()) {
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
        }
        
        return size;
    }
    
    /**
     * Sync a list of cache entries with the server
     * This ensures cache has the latest versions of content
     * @param entries List of cache entries to sync
     * @param callback Callback for completion
     */
    public void syncCacheEntries(List<CacheEntry> entries, final SyncCallback callback) {
        if (entries == null || entries.isEmpty()) {
            if (callback != null) {
                callback.onSyncComplete(0, 0);
            }
            return;
        }
        
        downloadExecutor.execute(() -> {
            int totalEntries = entries.size();
            int updatedEntries = 0;
            
            for (CacheEntry entry : entries) {
                // Skip if not online
                if (!networkUtils.isNetworkAvailable()) {
                    break;
                }
                
                try {
                    // Check if file needs updating by comparing last modified date with server
                    boolean needsUpdate = FileUtils.fileNeedsUpdate(entry.getUrl(), new File(entry.getFilePath()));
                    
                    if (needsUpdate) {
                        // Re-download file
                        boolean success = FileUtils.downloadFile(entry.getUrl(), new File(entry.getFilePath()));
                        
                        if (success) {
                            // Update entry timestamp
                            entry.setTimestamp(System.currentTimeMillis());
                            dbHelper.updateTimestamp(entry.getUrl(), entry.getTimestamp());
                            updatedEntries++;
                        }
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error syncing cache entry: " + entry.getUrl(), e);
                }
            }
            
            if (callback != null) {
                callback.onSyncComplete(totalEntries, updatedEntries);
            }
        });
    }
    
    /**
     * Get a list of all cache entries
     * @return List of cache entries
     */
    public List<CacheEntry> getAllCacheEntries() {
        return dbHelper.getAllCacheEntries();
    }
    
    /**
     * Get audio cache directory
     * @return Audio cache directory
     */
    public File getAudioCacheDir() {
        return new File(context.getFilesDir(), AUDIO_CACHE_DIR);
    }
    
    /**
     * Get image cache directory
     * @return Image cache directory
     */
    public File getImageCacheDir() {
        return new File(context.getFilesDir(), IMAGE_CACHE_DIR);
    }
    
    /**
     * Get data cache directory
     * @return Data cache directory
     */
    public File getDataCacheDir() {
        return new File(context.getFilesDir(), DATA_CACHE_DIR);
    }
    
    /**
     * Get the maximum storage size in MB
     * @return Maximum storage size in MB
     */
    public int getMaxStorageMB() {
        return prefs.getInt(KEY_MAX_STORAGE_MB, 500);
    }
    
    /**
     * Set the maximum storage size in MB
     * @param maxStorageMB Maximum storage size in MB
     */
    public void setMaxStorageMB(int maxStorageMB) {
        prefs.edit().putInt(KEY_MAX_STORAGE_MB, maxStorageMB).apply();
        
        // Schedule maintenance to check if we need to free space
        maintenanceExecutor.schedule(this::performCacheMaintenance, 1, TimeUnit.SECONDS);
    }

    public void clearLowPriorityCache(int i, int i1) {
    }

    /**
     * Cache request class to encapsulate download information
     */
    private static class CacheRequest {
        private final String url;
        private final File destFile;
        private final String type;
        private int priority;
        
        public CacheRequest(String url, File destFile, String type, int priority) {
            this.url = url;
            this.destFile = destFile;
            this.type = type;
            this.priority = priority;
        }
        
        public String getUrl() {
            return url;
        }
        
        public File getDestFile() {
            return destFile;
        }
        
        public String getType() {
            return type;
        }
        
        public int getPriority() {
            return priority;
        }
        
        public void setPriority(int priority) {
            this.priority = priority;
        }
    }
    
    /**
     * Interface for cache operation callbacks
     */
    public interface CacheCallback {
        void onCacheComplete(boolean success, String filePath);
    }
    
    /**
     * Interface for sync operation callbacks
     */
    public interface SyncCallback {
        void onSyncComplete(int totalEntries, int updatedEntries);
    }
}