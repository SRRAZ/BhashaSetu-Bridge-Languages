package com.bhashasetu.app.cache;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.util.LruCache;

import androidx.annotation.NonNull;
import androidx.annotation.RawRes;

import com.bhashasetu.app.audio.OptimizedAudioManager;
import com.bhashasetu.app.util.MemoryOptimizer;
import com.bhashasetu.app.util.NetworkOptimizer;

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
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Specialized caching system for audio files.
 * Efficiently manages download, storage, and access to audio resources
 * with support for streaming and preloading.
 */
public class AudioCache {
    private static final String TAG = "AudioCache";
    
    // Singleton instance
    private static AudioCache instance;
    
    // Context reference
    private final Context context;
    
    // Disk cache directory
    private final File diskCacheDir;
    
    // Background executor for disk operations
    private final ExecutorService diskExecutor;
    
    // Background executor for network operations
    private final ExecutorService networkExecutor;
    
    // Cache metadata tracking
    private final Map<String, AudioMetadata> metadataMap = new HashMap<>();
    
    // Running tasks
    private final Map<String, Future<?>> runningTasks = Collections.synchronizedMap(new HashMap<>());
    
    // Cache statistics tracking
    private int cacheHits = 0;
    private int cacheMisses = 0;
    private long totalDownloadBytes = 0;
    
    // Set of URLs that are currently being downloaded
    private final Set<String> downloadingUrls = Collections.synchronizedSet(new HashSet<>());
    
    // Memory cache for small audio files
    private final LruCache<String, byte[]> memoryCache;
    
    // Maximum size for memory-cacheable files (in bytes)
    private static final int MAX_MEMORY_CACHE_FILE_SIZE = 1024 * 1024; // 1MB
    
    // Default cache time: 7 days
    private static final long DEFAULT_CACHE_EXPIRY = TimeUnit.DAYS.toMillis(7);
    
    // Pending download requests (for when a URL is already being downloaded)
    private final Map<String, List<DownloadCallback>> pendingDownloads = 
            Collections.synchronizedMap(new HashMap<>());
    
    /**
     * Callback interface for audio file operations.
     */
    public interface AudioCallback {
        void onSuccess(File audioFile);
        void onError(Exception e);
        void onProgress(int progress);
    }
    
    /**
     * Private callback for internal download operations.
     */
    private interface DownloadCallback {
        void onComplete(File file);
        void onError(Exception e);
    }
    
    /**
     * Metadata for cached audio files.
     */
    private static class AudioMetadata {
        final String url;
        final long timestamp;
        final long expiry;
        final int accessCount;
        final long duration;
        final String mimeType;
        final long fileSize;
        
        AudioMetadata(String url, long timestamp, long expiry, 
                    long duration, String mimeType, long fileSize) {
            this.url = url;
            this.timestamp = timestamp;
            this.expiry = expiry;
            this.accessCount = 1;
            this.duration = duration;
            this.mimeType = mimeType;
            this.fileSize = fileSize;
        }
        
        AudioMetadata(String url, long timestamp, long expiry, int accessCount, 
                    long duration, String mimeType, long fileSize) {
            this.url = url;
            this.timestamp = timestamp;
            this.expiry = expiry;
            this.accessCount = accessCount;
            this.duration = duration;
            this.mimeType = mimeType;
            this.fileSize = fileSize;
        }
        
        AudioMetadata withIncrementedCount() {
            return new AudioMetadata(url, timestamp, expiry, accessCount + 1, 
                                   duration, mimeType, fileSize);
        }
        
        boolean isExpired() {
            return System.currentTimeMillis() > expiry;
        }
    }
    
    /**
     * Private constructor for singleton pattern.
     *
     * @param context Application context
     */
    private AudioCache(Context context) {
        this.context = context.getApplicationContext();
        
        // Initialize disk cache directory
        diskCacheDir = new File(context.getCacheDir(), "audio_cache");
        if (!diskCacheDir.exists()) {
            diskCacheDir.mkdirs();
        }
        
        // Initialize executors
        diskExecutor = Executors.newSingleThreadExecutor();
        networkExecutor = Executors.newFixedThreadPool(2);
        
        // Calculate memory cache size (1/8 of available memory)
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        
        // Initialize memory cache
        memoryCache = new LruCache<String, byte[]>(cacheSize) {
            @Override
            protected int sizeOf(String key, byte[] value) {
                // The cache size will be measured in kilobytes
                return value.length / 1024;
            }
        };
        
        // Load metadata
        loadMetadata();
    }
    
    /**
     * Get singleton instance.
     *
     * @param context Context
     * @return AudioCache instance
     */
    public static synchronized AudioCache getInstance(Context context) {
        if (instance == null) {
            instance = new AudioCache(context);
        }
        return instance;
    }
    
    /**
     * Load audio file asynchronously.
     *
     * @param url      Audio URL
     * @param callback Callback for result
     */
    public void loadAudio(String url, AudioCallback callback) {
        loadAudio(url, DEFAULT_CACHE_EXPIRY, callback);
    }
    
    /**
     * Load audio file asynchronously with cache duration specification.
     *
     * @param url         Audio URL
     * @param cacheExpiry Cache expiry time
     * @param callback    Callback for result
     */
    public void loadAudio(final String url, final long cacheExpiry, final AudioCallback callback) {
        if (url == null || url.trim().isEmpty() || callback == null) {
            if (callback != null) {
                callback.onError(new IllegalArgumentException("Invalid URL"));
            }
            return;
        }
        
        // Check if in cache
        File cachedFile = getCachedFile(url);
        if (cachedFile.exists()) {
            // Check if the cached version is expired
            AudioMetadata metadata = metadataMap.get(url);
            if (metadata != null && !metadata.isExpired()) {
                // Increment hit count
                cacheHits++;
                
                // Update metadata
                updateAccessCount(url);
                
                // Return cached file
                callback.onSuccess(cachedFile);
                return;
            }
        }
        
        // Not in cache or expired, increment miss count
        cacheMisses++;
        
        // Check if already downloading this URL
        if (downloadingUrls.contains(url)) {
            // Add to pending downloads
            pendingDownloads.computeIfAbsent(url, k -> new ArrayList<>())
                    .add(new DownloadCallback() {
                        @Override
                        public void onComplete(File file) {
                            callback.onSuccess(file);
                        }
                        
                        @Override
                        public void onError(Exception e) {
                            callback.onError(e);
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
                URL audioUrl = new URL(url);
                connection = (HttpURLConnection) audioUrl.openConnection();
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
                
                // Get content length for stats and progress tracking
                int contentLength = connection.getContentLength();
                if (contentLength > 0) {
                    totalDownloadBytes += contentLength;
                }
                
                // Create temp file for download
                final File downloadFile = getCachedFile(url);
                
                // Download to file
                inputStream = connection.getInputStream();
                bufferedInputStream = new BufferedInputStream(inputStream);
                outputStream = new FileOutputStream(downloadFile);
                
                byte[] buffer = new byte[8192];
                int bytesRead;
                int totalBytesRead = 0;
                while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                    
                    // Update progress
                    totalBytesRead += bytesRead;
                    if (contentLength > 0) {
                        final int progress = (int) ((totalBytesRead * 100) / contentLength);
                        // Report progress on main thread
                        new android.os.Handler(android.os.Looper.getMainLooper())
                                .post(() -> callback.onProgress(progress));
                    }
                }
                outputStream.flush();
                
                // Close streams
                bufferedInputStream.close();
                outputStream.close();
                
                // Extract audio metadata
                long duration = 0;
                String mimeType = "audio/mpeg"; // Default
                
                try {
                    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                    retriever.setDataSource(downloadFile.getAbsolutePath());
                    
                    String durationStr = retriever.extractMetadata(
                            MediaMetadataRetriever.METADATA_KEY_DURATION);
                    if (durationStr != null) {
                        duration = Long.parseLong(durationStr);
                    }
                    
                    String mimeTypeStr = retriever.extractMetadata(
                            MediaMetadataRetriever.METADATA_KEY_MIMETYPE);
                    if (mimeTypeStr != null) {
                        mimeType = mimeTypeStr;
                    }
                    
                    retriever.release();
                } catch (Exception e) {
                    Log.e(TAG, "Error extracting audio metadata", e);
                }
                
                // Update metadata
                updateMetadata(url, cacheExpiry, duration, mimeType, downloadFile.length());
                
                // Cache small files in memory
                if (downloadFile.length() <= MAX_MEMORY_CACHE_FILE_SIZE) {
                    cacheInMemory(url, downloadFile);
                }
                
                // Call callback on main thread
                final File finalDownloadFile = downloadFile;
                new android.os.Handler(android.os.Looper.getMainLooper())
                        .post(() -> callback.onSuccess(finalDownloadFile));
                
                // Notify pending downloads
                notifyPendingDownloads(url, downloadFile);
            } catch (Exception e) {
                Log.e(TAG, "Error downloading audio: " + url, e);
                
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
                runningTasks.remove(url);
            }
        });
        
        // Store the task for potential cancellation
        runningTasks.put(url, task);
    }
    
    /**
     * Notify pending download callbacks of success.
     *
     * @param url  URL that was downloaded
     * @param file Cache file
     */
    private void notifyPendingDownloads(String url, File file) {
        List<DownloadCallback> callbacks = pendingDownloads.remove(url);
        if (callbacks != null) {
            for (DownloadCallback callback : callbacks) {
                callback.onComplete(file);
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
     * Cache audio file in memory if it's small enough.
     *
     * @param url  Audio URL
     * @param file Audio file
     */
    private void cacheInMemory(String url, File file) {
        if (file.length() > MAX_MEMORY_CACHE_FILE_SIZE) {
            return;
        }
        
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] data = new byte[(int) file.length()];
            int read = fis.read(data);
            if (read == data.length) {
                memoryCache.put(url, data);
            }
        } catch (IOException e) {
            Log.e(TAG, "Error caching audio in memory", e);
        }
    }
    
    /**
     * Get cached file for a URL.
     *
     * @param url Audio URL
     * @return Cache file
     */
    public File getCachedFile(String url) {
        return new File(diskCacheDir, generateFileName(url));
    }
    
    /**
     * Generate a filename for disk cache based on URL.
     *
     * @param url Audio URL
     * @return Filename
     */
    private String generateFileName(String url) {
        // Using a simple hash function for demo purposes
        // In production, a more robust hashing algorithm should be used
        return String.valueOf(url.hashCode()) + ".mp3";
    }
    
    /**
     * Update metadata for a cached audio file.
     *
     * @param url      URL
     * @param expiry   Expiry time
     * @param duration Audio duration in milliseconds
     * @param mimeType MIME type
     * @param fileSize File size in bytes
     */
    private void updateMetadata(String url, long expiry, long duration, String mimeType, long fileSize) {
        long timestamp = System.currentTimeMillis();
        long expiryTime = timestamp + expiry;
        metadataMap.put(url, new AudioMetadata(url, timestamp, expiryTime, duration, mimeType, fileSize));
        
        // Schedule periodic saving of metadata
        saveMetadataDelayed();
    }
    
    /**
     * Update access count for a cached audio file.
     *
     * @param url URL
     */
    private void updateAccessCount(String url) {
        AudioMetadata metadata = metadataMap.get(url);
        if (metadata != null) {
            metadataMap.put(url, metadata.withIncrementedCount());
        }
    }
    
    /**
     * Load audio from raw resource and cache it.
     *
     * @param resourceId Raw resource ID
     * @param fileName   Filename to use in cache
     * @param callback   Callback for result
     */
    public void loadFromResource(@RawRes final int resourceId, final String fileName, 
                              final AudioCallback callback) {
        // Generate URL-like key for the resource
        final String resKey = "android.resource://" + context.getPackageName() + "/raw/" + resourceId;
        
        // Check if in cache
        File cachedFile = new File(diskCacheDir, fileName);
        if (cachedFile.exists()) {
            // Increment hit count
            cacheHits++;
            
            // Check if metadata exists, create if not
            if (metadataMap.get(resKey) == null) {
                // Extract audio metadata
                long duration = 0;
                String mimeType = "audio/mpeg"; // Default
                
                try {
                    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                    retriever.setDataSource(cachedFile.getAbsolutePath());
                    
                    String durationStr = retriever.extractMetadata(
                            MediaMetadataRetriever.METADATA_KEY_DURATION);
                    if (durationStr != null) {
                        duration = Long.parseLong(durationStr);
                    }
                    
                    String mimeTypeStr = retriever.extractMetadata(
                            MediaMetadataRetriever.METADATA_KEY_MIMETYPE);
                    if (mimeTypeStr != null) {
                        mimeType = mimeTypeStr;
                    }
                    
                    retriever.release();
                } catch (Exception e) {
                    Log.e(TAG, "Error extracting audio metadata", e);
                }
                
                updateMetadata(resKey, DEFAULT_CACHE_EXPIRY, duration, mimeType, cachedFile.length());
            } else {
                // Update access count
                updateAccessCount(resKey);
            }
            
            // Return cached file
            callback.onSuccess(cachedFile);
            return;
        }
        
        // Not in cache, copy from raw resource
        diskExecutor.submit(() -> {
            try {
                // Open resource
                InputStream inputStream = context.getResources().openRawResource(resourceId);
                
                // Create output file
                FileOutputStream outputStream = new FileOutputStream(cachedFile);
                
                // Copy data
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.flush();
                
                // Close streams
                inputStream.close();
                outputStream.close();
                
                // Extract audio metadata
                long duration = 0;
                String mimeType = "audio/mpeg"; // Default
                
                try {
                    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                    retriever.setDataSource(cachedFile.getAbsolutePath());
                    
                    String durationStr = retriever.extractMetadata(
                            MediaMetadataRetriever.METADATA_KEY_DURATION);
                    if (durationStr != null) {
                        duration = Long.parseLong(durationStr);
                    }
                    
                    String mimeTypeStr = retriever.extractMetadata(
                            MediaMetadataRetriever.METADATA_KEY_MIMETYPE);
                    if (mimeTypeStr != null) {
                        mimeType = mimeTypeStr;
                    }
                    
                    retriever.release();
                } catch (Exception e) {
                    Log.e(TAG, "Error extracting audio metadata", e);
                }
                
                // Update metadata
                updateMetadata(resKey, DEFAULT_CACHE_EXPIRY, duration, mimeType, cachedFile.length());
                
                // Cache small files in memory
                if (cachedFile.length() <= MAX_MEMORY_CACHE_FILE_SIZE) {
                    cacheInMemory(resKey, cachedFile);
                }
                
                // Call callback on main thread
                new android.os.Handler(android.os.Looper.getMainLooper())
                        .post(() -> callback.onSuccess(cachedFile));
            } catch (Exception e) {
                Log.e(TAG, "Error loading audio from resource: " + resourceId, e);
                
                // Call callback on main thread
                new android.os.Handler(android.os.Looper.getMainLooper())
                        .post(() -> callback.onError(e));
            }
        });
    }
    
    /**
     * Preload audio file into cache.
     *
     * @param url URL to preload
     */
    public void preload(String url) {
        preload(url, DEFAULT_CACHE_EXPIRY);
    }
    
    /**
     * Preload audio file into cache with cache duration specification.
     *
     * @param url         URL to preload
     * @param cacheExpiry Cache expiry time
     */
    public void preload(final String url, final long cacheExpiry) {
        if (url == null || url.trim().isEmpty()) {
            return;
        }
        
        // Check if already cached
        File cachedFile = getCachedFile(url);
        if (cachedFile.exists()) {
            // Check if the cached version is expired
            AudioMetadata metadata = metadataMap.get(url);
            if (metadata != null && !metadata.isExpired()) {
                // Already cached and not expired
                return;
            }
        }
        
        // Check if already downloading
        if (downloadingUrls.contains(url)) {
            return;
        }
        
        // Preload in background
        loadAudio(url, cacheExpiry, new AudioCallback() {
            @Override
            public void onSuccess(File audioFile) {
                Log.d(TAG, "Preloaded audio: " + url);
            }
            
            @Override
            public void onError(Exception e) {
                Log.e(TAG, "Error preloading audio: " + url, e);
            }
            
            @Override
            public void onProgress(int progress) {
                // No progress reporting needed for preloading
            }
        });
    }
    
    /**
     * Check if audio is cached.
     *
     * @param url URL to check
     * @return true if cached
     */
    public boolean isAudioCached(String url) {
        if (url == null) {
            return false;
        }
        
        // Check if in cache
        File cachedFile = getCachedFile(url);
        if (cachedFile.exists()) {
            // Check if the cached version is expired
            AudioMetadata metadata = metadataMap.get(url);
            return metadata != null && !metadata.isExpired();
        }
        
        return false;
    }
    
    /**
     * Get audio file from cache without downloading if not present.
     *
     * @param url URL to get
     * @return Cached file or null if not cached
     */
    public File getCachedAudio(String url) {
        if (url == null) {
            return null;
        }
        
        // Check if in cache
        File cachedFile = getCachedFile(url);
        if (cachedFile.exists()) {
            // Check if the cached version is expired
            AudioMetadata metadata = metadataMap.get(url);
            if (metadata != null && !metadata.isExpired()) {
                // Increment hit count
                cacheHits++;
                
                // Update metadata
                updateAccessCount(url);
                
                return cachedFile;
            }
        }
        
        return null;
    }
    
    /**
     * Create a media player for a cached audio file.
     *
     * @param url      Audio URL
     * @param callback Callback for when media player is ready
     */
    public void createMediaPlayer(String url, OptimizedAudioManager.MediaPlayerCallback callback) {
        // Get cached file
        File cachedFile = getCachedAudio(url);
        if (cachedFile != null) {
            // Use OptimizedAudioManager to create media player
            OptimizedAudioManager audioManager = OptimizedAudioManager.getInstance(context);
            
            audioManager.createMediaPlayer(Uri.fromFile(cachedFile), false, callback);
        } else {
            // Not cached, download first
            loadAudio(url, new AudioCallback() {
                @Override
                public void onSuccess(File audioFile) {
                    // Use OptimizedAudioManager to create media player
                    OptimizedAudioManager audioManager = OptimizedAudioManager.getInstance(context);
                    
                    audioManager.createMediaPlayer(Uri.fromFile(audioFile), false, callback);
                }
                
                @Override
                public void onError(Exception e) {
                    callback.onMediaPlayerFailed(e);
                }
                
                @Override
                public void onProgress(int progress) {
                    // No progress reporting needed
                }
            });
        }
    }
    
    /**
     * Clear all cached audio files.
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
     * Clear cached audio for a specific URL.
     *
     * @param url URL to clear
     */
    public void clearCache(String url) {
        if (url == null) {
            return;
        }
        
        // Remove from memory cache
        memoryCache.remove(url);
        
        // Remove from disk cache
        diskExecutor.submit(() -> {
            File cachedFile = getCachedFile(url);
            if (cachedFile.exists()) {
                cachedFile.delete();
            }
            
            // Remove from metadata
            metadataMap.remove(url);
            saveMetadata();
        });
    }
    
    /**
     * Clear expired audio files.
     */
    public void clearExpired() {
        long now = System.currentTimeMillis();
        
        // Find expired entries
        List<String> expiredUrls = new ArrayList<>();
        for (Map.Entry<String, AudioMetadata> entry : metadataMap.entrySet()) {
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
            Log.d(TAG, "Cleared " + expiredUrls.size() + " expired audio files");
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
        Map<String, String> stats = new HashMap<>();
        
        stats.put("Memory Cache Size", memoryCache.size() + " files");
        stats.put("Memory Cache Size (KB)", memoryCache.size() / 1024 + " KB");
        stats.put("Memory Cache Max Size", memoryCache.maxSize() / 1024 + " KB");
        stats.put("Disk Cache Entries", metadataMap.size() + " files");
        
        // Calculate hit rates
        int totalRequests = cacheHits + cacheMisses;
        float hitRate = totalRequests > 0 ? ((float) cacheHits / totalRequests) * 100 : 0;
        
        stats.put("Cache Hits", String.valueOf(cacheHits));
        stats.put("Cache Misses", String.valueOf(cacheMisses));
        stats.put("Hit Rate", String.format("%.1f%%", hitRate));
        stats.put("Total Downloaded", MemoryOptimizer.formatMemorySize(totalDownloadBytes));
        
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
     * Get total cache size in bytes.
     *
     * @return Total cache size
     */
    public long getTotalCacheSize() {
        long diskSize = 0;
        File[] files = diskCacheDir.listFiles();
        if (files != null) {
            for (File file : files) {
                diskSize += file.length();
            }
        }
        
        return diskSize;
    }
    
    /**
     * Get audio file details from cache.
     *
     * @param url Audio URL
     * @return Map of details or null if not cached
     */
    public Map<String, Object> getAudioDetails(String url) {
        if (url == null) {
            return null;
        }
        
        AudioMetadata metadata = metadataMap.get(url);
        if (metadata == null) {
            return null;
        }
        
        Map<String, Object> details = new HashMap<>();
        details.put("url", metadata.url);
        details.put("duration", metadata.duration);
        details.put("mimeType", metadata.mimeType);
        details.put("fileSize", metadata.fileSize);
        details.put("age", System.currentTimeMillis() - metadata.timestamp);
        details.put("expiry", metadata.expiry - System.currentTimeMillis());
        details.put("accessCount", metadata.accessCount);
        details.put("isExpired", metadata.isExpired());
        
        return details;
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
            // Aggressive trimming: clear memory cache
            memoryCache.evictAll();
        } else if (level >= android.content.ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE) {
            // Moderate trimming: clear half the memory cache
            memoryCache.trimToSize(memoryCache.maxSize() / 2);
        }
    }
    
    /**
     * Batch preload multiple audio files.
     *
     * @param urls List of URLs to preload
     */
    public void preloadBatch(@NonNull List<String> urls) {
        if (urls.isEmpty()) {
            return;
        }
        
        for (String url : urls) {
            preload(url);
        }
    }
    
    /**
     * Get audio duration if cached.
     *
     * @param url Audio URL
     * @return Duration in milliseconds or -1 if not cached
     */
    public long getAudioDuration(String url) {
        if (url == null) {
            return -1;
        }
        
        AudioMetadata metadata = metadataMap.get(url);
        if (metadata != null) {
            return metadata.duration;
        }
        
        return -1;
    }
}