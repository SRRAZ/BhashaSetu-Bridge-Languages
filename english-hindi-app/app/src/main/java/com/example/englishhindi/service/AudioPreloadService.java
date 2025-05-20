package com.example.englishhindi.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.englishhindi.R;
import com.example.englishhindi.database.WordRepository;
import com.example.englishhindi.model.Word;
import com.example.englishhindi.util.AudioUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Background service for preloading audio files.
 * This service generates TTS audio files for words in the database that don't have audio files yet.
 */
public class AudioPreloadService extends IntentService {
    private static final String TAG = "AudioPreloadService";
    private static final String CHANNEL_ID = "audio_preload_channel";
    private static final int NOTIFICATION_ID = 1001;
    private static final int BATCH_SIZE = 10;
    
    // Intent actions
    public static final String ACTION_PRELOAD_ALL = "com.example.englishhindi.action.PRELOAD_ALL";
    public static final String ACTION_PRELOAD_CATEGORY = "com.example.englishhindi.action.PRELOAD_CATEGORY";
    public static final String ACTION_PRELOAD_FAVORITES = "com.example.englishhindi.action.PRELOAD_FAVORITES";
    
    // Intent extras
    public static final String EXTRA_CATEGORY = "com.example.englishhindi.extra.CATEGORY";
    
    private WordRepository wordRepository;
    private NotificationManager notificationManager;
    
    public AudioPreloadService() {
        super("AudioPreloadService");
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        wordRepository = new WordRepository(getApplication());
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        
        // Create notification channel for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Audio Preload",
                    NotificationManager.IMPORTANCE_LOW);
            channel.setDescription("Used for preloading audio files in the background");
            notificationManager.createNotificationChannel(channel);
        }
        
        // Start as a foreground service with a notification
        startForeground(NOTIFICATION_ID, createNotification("Starting audio preload..."));
    }
    
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent == null) {
            return;
        }
        
        String action = intent.getAction();
        if (action == null) {
            return;
        }
        
        try {
            switch (action) {
                case ACTION_PRELOAD_ALL:
                    preloadAllAudio();
                    break;
                    
                case ACTION_PRELOAD_CATEGORY:
                    String category = intent.getStringExtra(EXTRA_CATEGORY);
                    if (category != null) {
                        preloadCategoryAudio(category);
                    }
                    break;
                    
                case ACTION_PRELOAD_FAVORITES:
                    preloadFavoritesAudio();
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "Error preloading audio", e);
        }
    }
    
    /**
     * Preload audio files for all words in the database.
     */
    private void preloadAllAudio() {
        List<Word> allWords = wordRepository.getAllWordsSync();
        if (allWords == null || allWords.isEmpty()) {
            return;
        }
        
        updateNotification("Preloading audio for " + allWords.size() + " words");
        processWordBatches(allWords);
    }
    
    /**
     * Preload audio files for words in a specific category.
     * 
     * @param category The category to preload
     */
    private void preloadCategoryAudio(String category) {
        List<Word> categoryWords = wordRepository.getWordsByCategorySync(category);
        if (categoryWords == null || categoryWords.isEmpty()) {
            return;
        }
        
        updateNotification("Preloading audio for " + categoryWords.size() + " words in " + category);
        processWordBatches(categoryWords);
    }
    
    /**
     * Preload audio files for favorite words.
     */
    private void preloadFavoritesAudio() {
        List<Word> favoriteWords = wordRepository.getFavoriteWordsSync();
        if (favoriteWords == null || favoriteWords.isEmpty()) {
            return;
        }
        
        updateNotification("Preloading audio for " + favoriteWords.size() + " favorite words");
        processWordBatches(favoriteWords);
    }
    
    /**
     * Process words in batches to generate audio files.
     * 
     * @param words The list of words to process
     */
    private void processWordBatches(List<Word> words) {
        int totalWords = words.size();
        int processedCount = 0;
        int successCount = 0;
        
        // Process in batches to avoid overwhelming the system
        for (int i = 0; i < totalWords; i += BATCH_SIZE) {
            int end = Math.min(i + BATCH_SIZE, totalWords);
            List<Word> batch = words.subList(i, end);
            
            // Convert to WordIdPairs
            List<AudioUtils.WordIdPair> pairs = new ArrayList<>();
            for (Word word : batch) {
                // Skip words that already have audio files
                if (!AudioUtils.hasAudioFile(this, word.getId())) {
                    pairs.add(new AudioUtils.WordIdPair(word.getId(), word.getEnglishWord()));
                } else {
                    processedCount++;
                }
            }
            
            // Generate audio files for this batch
            if (!pairs.isEmpty()) {
                int batchSuccess = AudioUtils.generateAudioFilesForBatch(this, pairs);
                successCount += batchSuccess;
                processedCount += pairs.size();
                
                // Update notification with progress
                int progressPercent = (int) ((processedCount * 100.0) / totalWords);
                updateNotification("Preloading audio: " + progressPercent + "% complete", progressPercent);
            }
        }
        
        // Final notification
        updateNotification("Audio preload complete: " + successCount + "/" + totalWords + " files generated");
    }
    
    /**
     * Create a notification for the service.
     * 
     * @param content The notification content
     * @return The notification
     */
    private Notification createNotification(String content) {
        return createNotification(content, 0);
    }
    
    /**
     * Create a notification with progress for the service.
     * 
     * @param content The notification content
     * @param progress The progress percentage (0-100)
     * @return The notification
     */
    private Notification createNotification(String content, int progress) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Audio Preload")
                .setContentText(content)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setOnlyAlertOnce(true);
        
        if (progress > 0) {
            builder.setProgress(100, progress, false);
        }
        
        return builder.build();
    }
    
    /**
     * Update the service notification.
     * 
     * @param content The new notification content
     */
    private void updateNotification(String content) {
        updateNotification(content, 0);
    }
    
    /**
     * Update the service notification with progress.
     * 
     * @param content The new notification content
     * @param progress The progress percentage (0-100)
     */
    private void updateNotification(String content, int progress) {
        Notification notification = createNotification(content, progress);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }
}