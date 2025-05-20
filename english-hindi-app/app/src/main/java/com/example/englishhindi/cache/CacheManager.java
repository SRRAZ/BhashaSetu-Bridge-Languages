package com.example.englishhindi.cache;

import android.content.Context;
import android.util.Log;

import com.example.englishhindi.model.Word;
import com.example.englishhindi.util.FileUtils;
import com.example.englishhindi.util.NetworkUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Manages the caching of app content for offline use.
 * Handles audio files, images, and essential data.
 */
public class CacheManager {
    private static final String TAG = "CacheManager";
    
    private static final String AUDIO_CACHE_DIR = "audio_cache";
    private static final String IMAGE_CACHE_DIR = "image_cache";
    private static final String DATA_CACHE_DIR = "data_cache";
    
    private static CacheManager instance;
    
    private Context context;
    private Executor executor;
    private CacheDatabaseHelper dbHelper;
    private NetworkUtils networkUtils;
    
    // Set to keep track of currently downloading files to avoid duplicates
    private Set<String> currentDownloads = new HashSet<>();
    
    private CacheManager(Context context) {
        this.context = context.getApplicationContext();
        this.executor = Executors.newFixedThreadPool(3); // Limit concurrent downloads
        this.dbHelper = new CacheDatabaseHelper(context);
        this.networkUtils = new NetworkUtils(context);
        
        // Ensure cache directories exist
        createCacheDirectories();
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
     * Generic method to cache a file from a URL
     * @param url URL to download from
     * @param destFile Destination file
     * @param type Type of content (audio, image, data)
     * @param priority Priority level
     * @param callback Callback for completion status
     */
    private void cacheFile(String url, File destFile, String type, int priority, CacheCallback callback) {
        // Check if already cached
        if (destFile.exists()) {
            // File exists, check if it's valid
            CacheEntry entry = dbHelper.getCacheEntry(url);
            if (entry != null && !entry.isExpired()) {
                // Valid cached file
                if (callback != null) {
                    callback.onCacheComplete(true, destFile.getAbsolutePath());
                }
                return;
            }
        }
        
        // Check if already downloading
        synchronized (currentDownloads) {
            if (currentDownloads.contains(url)) {
                // Already downloading
                if (callback != null) {
                    callback.onCacheComplete(false, null);
                }
                return;
            }
            
            // Add to downloading set
            currentDownloads.add(url);
        }
        
        // Not cached or expired, download
        executor.execute(() -> {
            try {
                // Check network connection
                if (!networkUtils.isNetworkAvailable()) {
                    Log.d(TAG, "Network not available, cannot download: " + url);
                    if (callback != null) {
                        callback.onCacheComplete(false, null);
                    }
                    synchronized (currentDownloads) {
                        currentDownloads.remove(url);
                    }
                    return;
                }
                
                // Download the file
                boolean success = FileUtils.downloadFile(url, destFile);
                
                if (success) {
                    // Save entry in database
                    CacheEntry entry = new CacheEntry(url, destFile.getAbsolutePath(), type, 
                            System.currentTimeMillis(), priority);
                    dbHelper.insertOrUpdateCacheEntry(entry);
                    
                    // Notify callback
                    if (callback != null) {
                        callback.onCacheComplete(true, destFile.getAbsolutePath());
                    }
                } else {
                    // Download failed
                    if (callback != null) {
                        callback.onCacheComplete(false, null);
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "Error caching file: " + url, e);
                if (callback != null) {
                    callback.onCacheComplete(false, null);
                }
            } finally {
                synchronized (currentDownloads) {
                    currentDownloads.remove(url);
                }
            }
        });
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
     * Generic method to get a cached file path
     * @param url URL of the original content
     * @return File path or null if not cached
     */
    private String getCachedFilePath(String url) {
        CacheEntry entry = dbHelper.getCacheEntry(url);
        if (entry != null && !entry.isExpired()) {
            File file = new File(entry.getFilePath());
            if (file.exists()) {
                // Update last accessed time
                entry.setLastAccessed(System.currentTimeMillis());
                dbHelper.updateLastAccessedTime(url, entry.getLastAccessed());
                return entry.getFilePath();
            } else {
                // File doesn't exist anymore, remove entry
                dbHelper.deleteCacheEntry(url);
            }
        }
        return null;
    }
    
    /**
     * Prefetch audio files for a list of words
     * @param words List of words to prefetch audio for
     */
    public void prefetchWordAudio(List<Word> words) {
        if (words == null || words.isEmpty()) {
            return;
        }
        
        // Execute on our background thread pool
        executor.execute(() -> {
            for (Word word : words) {
                // Skip if not online
                if (!networkUtils.isNetworkAvailable()) {
                    break;
                }
                
                String audioUrl = word.getAudioUrl();
                if (audioUrl != null && !audioUrl.isEmpty()) {
                    // Create filename from word
                    String filename = "word_" + word.getId() + "_" + word.getEnglishWord() + ".mp3";
                    
                    // Cache with priority based on word frequency
                    int priority = word.getUsageFrequency();
                    cacheAudioFile(audioUrl, filename, priority, null);
                }
            }
        });
    }
    
    /**
     * Prefetch images for a list of words
     * @param words List of words to prefetch images for
     */
    public void prefetchWordImages(List<Word> words) {
        if (words == null || words.isEmpty()) {
            return;
        }
        
        // Execute on our background thread pool
        executor.execute(() -> {
            for (Word word : words) {
                // Skip if not online
                if (!networkUtils.isNetworkAvailable()) {
                    break;
                }
                
                String imageUrl = word.getImageUrl();
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    // Create filename from word
                    String filename = "word_" + word.getId() + "_" + word.getEnglishWord() + ".jpg";
                    
                    // Cache with priority based on word frequency
                    int priority = word.getUsageFrequency();
                    cacheImageFile(imageUrl, filename, priority, null);
                }
            }
        });
    }
    
    /**
     * Clear old cache entries to free up space
     * @param maxAgeMillis Maximum age of cache entries to keep (in milliseconds)
     */
    public void clearOldCache(long maxAgeMillis) {
        executor.execute(() -> {
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
     * Clear low priority cache entries to free up space
     * @param minPriority Minimum priority to keep
     * @param maxEntriesToRemove Maximum number of entries to remove
     */
    public void clearLowPriorityCache(int minPriority, int maxEntriesToRemove) {
        executor.execute(() -> {
            List<CacheEntry> lowPriorityEntries = dbHelper.getLowPriorityEntries(minPriority, maxEntriesToRemove);
            
            for (CacheEntry entry : lowPriorityEntries) {
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
     * Get the total size of all cached files
     * @return Size in bytes
     */
    public long getTotalCacheSize() {
        long size = 0;
        
        size += getDirSize(getAudioCacheDir());
        size += getDirSize(getImageCacheDir());
        size += getDirSize(getDataCacheDir());
        
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
        
        executor.execute(() -> {
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