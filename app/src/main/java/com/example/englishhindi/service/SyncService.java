package com.bhashasetu.app.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.bhashasetu.app.R;
import com.bhashasetu.app.cache.CacheEntry;
import com.bhashasetu.app.cache.CacheManager;
import com.bhashasetu.app.database.WordRepository;
import com.bhashasetu.app.model.Word;
import com.bhashasetu.app.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Service for synchronizing app data for offline use.
 * Handles downloading and updating cached content.
 */
public class SyncService extends Service {
    
    private static final String TAG = "SyncService";
    private static final int NOTIFICATION_ID = 1001;
    private static final String CHANNEL_ID = "sync_channel";
    
    // Sync types
    public static final String SYNC_TYPE_FULL = "full";
    public static final String SYNC_TYPE_ESSENTIAL = "essential";
    public static final String SYNC_TYPE_RECENT = "recent";
    
    // Intent extras
    public static final String EXTRA_SYNC_TYPE = "sync_type";
    public static final String EXTRA_MAX_ITEMS = "max_items";
    public static final String EXTRA_WIFI_ONLY = "wifi_only";
    
    // Local binder for binding to the service
    private final IBinder binder = new LocalBinder();
    
    // Service dependencies
    private CacheManager cacheManager;
    private WordRepository wordRepository;
    private NetworkUtils networkUtils;
    private ExecutorService executor;
    private NotificationManager notificationManager;
    
    // State
    private final AtomicBoolean isSyncing = new AtomicBoolean(false);
    private int totalItems;
    private int completedItems;
    private String currentStatus;
    
    // Callbacks
    private SyncProgressListener progressListener;
    
    @Override
    public void onCreate() {
        super.onCreate();
        
        // Initialize dependencies
        cacheManager = CacheManager.getInstance(this);
        wordRepository = new WordRepository(this);
        networkUtils = new NetworkUtils(this);
        executor = Executors.newSingleThreadExecutor();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        
        // Create notification channel for Android O+
        createNotificationChannel();
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String syncType = intent.getStringExtra(EXTRA_SYNC_TYPE);
            int maxItems = intent.getIntExtra(EXTRA_MAX_ITEMS, -1);
            boolean wifiOnly = intent.getBooleanExtra(EXTRA_WIFI_ONLY, false);
            
            // Start sync operation
            startSync(syncType, maxItems, wifiOnly);
        }
        
        // Make it a foreground service
        startForeground(NOTIFICATION_ID, createNotification(0, 0, "Starting sync..."));
        
        return START_STICKY;
    }
    
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
    
    /**
     * Set a listener for sync progress updates
     * @param listener Listener to set
     */
    public void setSyncProgressListener(SyncProgressListener listener) {
        this.progressListener = listener;
    }
    
    /**
     * Start a sync operation
     * @param syncType Type of sync (full, essential, recent)
     * @param maxItems Maximum number of items to sync
     * @param wifiOnly Only sync when connected to WiFi
     */
    public void startSync(String syncType, int maxItems, boolean wifiOnly) {
        if (isSyncing.get()) {
            Log.d(TAG, "Sync already in progress, ignoring request");
            return;
        }
        
        isSyncing.set(true);
        totalItems = 0;
        completedItems = 0;
        currentStatus = "Preparing to sync...";
        
        // Set WiFi-only mode if requested
        boolean previousWifiOnlyMode = networkUtils.isWifiOnlyModeEnabled();
        if (wifiOnly) {
            networkUtils.setWifiOnlyModeEnabled(true);
        }
        
        // Update notification
        updateNotification(0, 1, currentStatus);
        
        // Run sync in background
        executor.execute(() -> {
            try {
                if (SYNC_TYPE_FULL.equals(syncType)) {
                    performFullSync(maxItems);
                } else if (SYNC_TYPE_ESSENTIAL.equals(syncType)) {
                    performEssentialSync(maxItems);
                } else if (SYNC_TYPE_RECENT.equals(syncType)) {
                    performRecentSync(maxItems);
                } else {
                    // Default to essential sync
                    performEssentialSync(maxItems);
                }
            } catch (Exception e) {
                Log.e(TAG, "Error during sync", e);
                currentStatus = "Sync failed: " + e.getMessage();
                updateNotification(completedItems, totalItems, currentStatus);
            } finally {
                // Restore previous WiFi-only mode
                if (wifiOnly) {
                    networkUtils.setWifiOnlyModeEnabled(previousWifiOnlyMode);
                }
                
                // Update state
                isSyncing.set(false);
                
                // Update notification one last time
                updateNotification(completedItems, totalItems, 
                        completedItems == totalItems ? "Sync completed" : currentStatus);
                
                // Notify listener
                notifyProgressListener(completedItems, totalItems, currentStatus, true);
                
                // Stop the service
                stopForeground(true);
                stopSelf();
            }
        });
    }
    
    /**
     * Perform a full sync of all content
     * @param maxItems Maximum number of items to sync
     */
    private void performFullSync(int maxItems) {
        currentStatus = "Syncing all content...";
        notifyProgressListener(0, 0, currentStatus, false);
        
        // 1. Sync words and their audio/images
        syncAllWords(maxItems);
        
        // 2. Sync categories
        syncCategories();
        
        // 3. Sync additional content
        syncAdditionalContent();
        
        // 4. Clean up old/unused cache
        cleanupCache();
        
        currentStatus = "Full sync completed";
        notifyProgressListener(completedItems, totalItems, currentStatus, false);
    }
    
    /**
     * Perform an essential sync (minimum required for offline use)
     * @param maxItems Maximum number of items to sync
     */
    private void performEssentialSync(int maxItems) {
        currentStatus = "Syncing essential content...";
        notifyProgressListener(0, 0, currentStatus, false);
        
        // 1. Sync high-priority words
        syncEssentialWords(maxItems);
        
        // 2. Sync basic categories
        syncEssentialCategories();
        
        // 3. Clean up low-priority cache to save space
        cleanupLowPriorityCache();
        
        currentStatus = "Essential sync completed";
        notifyProgressListener(completedItems, totalItems, currentStatus, false);
    }
    
    /**
     * Perform a sync of recent content
     * @param maxItems Maximum number of items to sync
     */
    private void performRecentSync(int maxItems) {
        currentStatus = "Syncing recent content...";
        notifyProgressListener(0, 0, currentStatus, false);
        
        // 1. Sync recent words
        syncRecentWords(maxItems);
        
        // 2. Sync any changed content
        syncChangedContent();
        
        currentStatus = "Recent sync completed";
        notifyProgressListener(completedItems, totalItems, currentStatus, false);
    }
    
    /**
     * Sync all words and their associated content
     * @param maxItems Maximum number of words to sync
     */
    private void syncAllWords(int maxItems) {
        // Get all words from repository
        List<Word> words = wordRepository.getAllWords();
        
        if (maxItems > 0 && words.size() > maxItems) {
            // Limit to max items
            words = words.subList(0, maxItems);
        }
        
        totalItems += words.size() * 2; // Each word has audio and image
        updateNotification(completedItems, totalItems, "Syncing words...");
        
        // Cache audio for each word
        for (Word word : words) {
            if (!networkUtils.isNetworkAvailable()) {
                currentStatus = "Sync paused: No network connection";
                updateNotification(completedItems, totalItems, currentStatus);
                return;
            }
            
            syncWordContent(word);
        }
    }
    
    /**
     * Sync essential words (high priority)
     * @param maxItems Maximum number of words to sync
     */
    private void syncEssentialWords(int maxItems) {
        // Get essential words (high frequency, beginner level)
        List<Word> words = wordRepository.getEssentialWords();
        
        if (maxItems > 0 && words.size() > maxItems) {
            // Limit to max items
            words = words.subList(0, maxItems);
        }
        
        totalItems += words.size() * 2; // Each word has audio and image
        updateNotification(completedItems, totalItems, "Syncing essential words...");
        
        // Cache audio for each word
        for (Word word : words) {
            if (!networkUtils.isNetworkAvailable()) {
                currentStatus = "Sync paused: No network connection";
                updateNotification(completedItems, totalItems, currentStatus);
                return;
            }
            
            syncWordContent(word);
        }
    }
    
    /**
     * Sync recently added or updated words
     * @param maxItems Maximum number of words to sync
     */
    private void syncRecentWords(int maxItems) {
        // Get recent words
        List<Word> words = wordRepository.getRecentWords();
        
        if (maxItems > 0 && words.size() > maxItems) {
            // Limit to max items
            words = words.subList(0, maxItems);
        }
        
        totalItems += words.size() * 2; // Each word has audio and image
        updateNotification(completedItems, totalItems, "Syncing recent words...");
        
        // Cache audio for each word
        for (Word word : words) {
            if (!networkUtils.isNetworkAvailable()) {
                currentStatus = "Sync paused: No network connection";
                updateNotification(completedItems, totalItems, currentStatus);
                return;
            }
            
            syncWordContent(word);
        }
    }
    
    /**
     * Sync a word's content (audio and image)
     * @param word Word to sync
     */
    private void syncWordContent(Word word) {
        // Skip words without URLs
        if (word.getAudioUrl() == null && word.getImageUrl() == null) {
            completedItems += 2; // Count as completed
            updateNotification(completedItems, totalItems, "Syncing word: " + word.getEnglishWord());
            return;
        }
        
        // Sync audio
        if (word.getAudioUrl() != null && !word.getAudioUrl().isEmpty()) {
            String audioFileName = "word_" + word.getId() + "_" + word.getEnglishWord() + ".mp3";
            
            cacheManager.cacheAudioFile(word.getAudioUrl(), audioFileName, 
                    word.getUsageFrequency(), (success, filePath) -> {
                completedItems++;
                updateNotification(completedItems, totalItems, 
                        "Synced audio for: " + word.getEnglishWord());
            });
        } else {
            completedItems++; // Count audio as completed
        }
        
        // Sync image
        if (word.getImageUrl() != null && !word.getImageUrl().isEmpty()) {
            String imageFileName = "word_" + word.getId() + "_" + word.getEnglishWord() + ".jpg";
            
            cacheManager.cacheImageFile(word.getImageUrl(), imageFileName, 
                    word.getUsageFrequency(), (success, filePath) -> {
                completedItems++;
                updateNotification(completedItems, totalItems, 
                        "Synced image for: " + word.getEnglishWord());
            });
        } else {
            completedItems++; // Count image as completed
        }
    }
    
    /**
     * Sync categories
     */
    private void syncCategories() {
        // Implementation depends on how categories are stored and accessed
        // This is a placeholder implementation
        currentStatus = "Syncing categories...";
        updateNotification(completedItems, totalItems, currentStatus);
        
        // In a real app, this would sync category data
        // For now, just update the progress
        totalItems += 1;
        completedItems += 1;
        updateNotification(completedItems, totalItems, "Categories synced");
    }
    
    /**
     * Sync essential categories
     */
    private void syncEssentialCategories() {
        // Implementation depends on how categories are stored and accessed
        // This is a placeholder implementation
        currentStatus = "Syncing essential categories...";
        updateNotification(completedItems, totalItems, currentStatus);
        
        // In a real app, this would sync category data
        // For now, just update the progress
        totalItems += 1;
        completedItems += 1;
        updateNotification(completedItems, totalItems, "Essential categories synced");
    }
    
    /**
     * Sync additional content
     */
    private void syncAdditionalContent() {
        // Implementation depends on what additional content is needed
        // This is a placeholder implementation
        currentStatus = "Syncing additional content...";
        updateNotification(completedItems, totalItems, currentStatus);
        
        // In a real app, this would sync additional data
        // For now, just update the progress
        totalItems += 1;
        completedItems += 1;
        updateNotification(completedItems, totalItems, "Additional content synced");
    }
    
    /**
     * Sync changed content
     */
    private void syncChangedContent() {
        currentStatus = "Syncing changed content...";
        updateNotification(completedItems, totalItems, currentStatus);
        
        // Get all cache entries
        List<CacheEntry> entries = cacheManager.getAllCacheEntries();
        
        // For progress tracking
        totalItems += entries.size();
        updateNotification(completedItems, totalItems, currentStatus);
        
        // Sync entries with server to check for updates
        cacheManager.syncCacheEntries(entries, (totalEntries, updatedEntries) -> {
            completedItems += totalEntries;
            updateNotification(completedItems, totalItems, 
                    "Updated " + updatedEntries + " items");
        });
    }
    
    /**
     * Clean up old cache entries
     */
    private void cleanupCache() {
        currentStatus = "Cleaning up cache...";
        updateNotification(completedItems, totalItems, currentStatus);
        
        // Clean up entries older than 30 days
        long maxAge = 30 * 24 * 60 * 60 * 1000L; // 30 days in milliseconds
        cacheManager.clearOldCache(maxAge);
        
        totalItems += 1;
        completedItems += 1;
        updateNotification(completedItems, totalItems, "Cache cleanup complete");
    }
    
    /**
     * Clean up low priority cache entries
     */
    private void cleanupLowPriorityCache() {
        currentStatus = "Cleaning up low priority content...";
        updateNotification(completedItems, totalItems, currentStatus);
        
        // Clean up entries with priority below 50, up to 100 entries
        cacheManager.clearLowPriorityCache(50, 100);
        
        totalItems += 1;
        completedItems += 1;
        updateNotification(completedItems, totalItems, "Low priority cleanup complete");
    }
    
    /**
     * Create the notification channel (required for Android O+)
     */
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Sync Service";
            String description = "Notifications for content sync";
            int importance = NotificationManager.IMPORTANCE_LOW;
            
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, name, importance);
            channel.setDescription(description);
            
            notificationManager.createNotificationChannel(channel);
        }
    }
    
    /**
     * Create the foreground service notification
     * @param progress Current progress
     * @param max Maximum progress
     * @param status Status message
     * @return Notification object
     */
    private Notification createNotification(int progress, int max, String status) {
        Intent notificationIntent = new Intent(this, SyncService.class);
        PendingIntent pendingIntent = PendingIntent.getService(
                this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
        
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Syncing content")
                .setContentText(status)
                .setSmallIcon(R.drawable.ic_sync)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setOnlyAlertOnce(true);
        
        if (max > 0) {
            builder.setProgress(max, progress, false);
        } else {
            builder.setProgress(0, 0, true);
        }
        
        return builder.build();
    }
    
    /**
     * Update the foreground notification
     * @param progress Current progress
     * @param max Maximum progress
     * @param status Status message
     */
    private void updateNotification(int progress, int max, String status) {
        Notification notification = createNotification(progress, max, status);
        notificationManager.notify(NOTIFICATION_ID, notification);
        
        notifyProgressListener(progress, max, status, false);
    }
    
    /**
     * Notify the progress listener
     * @param progress Current progress
     * @param max Maximum progress
     * @param status Status message
     * @param isComplete Whether the sync is complete
     */
    private void notifyProgressListener(int progress, int max, String status, boolean isComplete) {
        if (progressListener != null) {
            // Notify on main thread
            android.os.Handler mainHandler = new android.os.Handler(getMainLooper());
            mainHandler.post(() -> {
                progressListener.onProgressUpdate(progress, max, status, isComplete);
            });
        }
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
    
    /**
     * Local binder class for binding to the service
     */
    public class LocalBinder extends Binder {
        public SyncService getService() {
            return SyncService.this;
        }
    }
    
    /**
     * Interface for sync progress updates
     */
    public interface SyncProgressListener {
        void onProgressUpdate(int progress, int max, String status, boolean isComplete);
    }
}