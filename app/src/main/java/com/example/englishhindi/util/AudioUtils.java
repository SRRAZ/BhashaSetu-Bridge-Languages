package com.example.englishhindi.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.util.Log;

import androidx.preference.PreferenceManager;

import com.example.englishhindi.R;
import com.example.englishhindi.audio.TtsRecorder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Utility class for audio-related operations in the app.
 */
public class AudioUtils {
    private static final String TAG = "AudioUtils";
    private static final String KEY_PREF_SPEECH_RATE = "speech_rate";
    private static final String KEY_PREF_TTS_VOICE = "tts_voice";
    
    /**
     * Get the preferred speech rate from preferences.
     * 
     * @param context The context
     * @return The speech rate (0.5 for slow, 1.0 for normal, 1.5 for fast)
     */
    public static float getPreferredSpeechRate(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String rate = prefs.getString(KEY_PREF_SPEECH_RATE, 
                context.getString(R.string.speech_rate_normal));
        try {
            return Float.parseFloat(rate);
        } catch (NumberFormatException e) {
            return 1.0f; // Default to normal speed
        }
    }
    
    /**
     * Set the preferred speech rate in preferences.
     * 
     * @param context The context
     * @param rate The speech rate to set
     */
    public static void setPreferredSpeechRate(Context context, float rate) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString(KEY_PREF_SPEECH_RATE, String.valueOf(rate)).apply();
    }
    
    /**
     * Check if audio file exists for a word.
     * 
     * @param context The context
     * @param wordId The word ID
     * @return true if the audio file exists, false otherwise
     */
    public static boolean hasAudioFile(Context context, int wordId) {
        File audioDir = new File(context.getFilesDir(), "audio");
        File audioFile = new File(audioDir, "word_" + wordId + ".mp3");
        return audioFile.exists();
    }
    
    /**
     * Get the path to the audio file for a word.
     * 
     * @param context The context
     * @param wordId The word ID
     * @return The audio file path, or null if it doesn't exist
     */
    public static String getAudioFilePath(Context context, int wordId) {
        File audioDir = new File(context.getFilesDir(), "audio");
        File audioFile = new File(audioDir, "word_" + wordId + ".mp3");
        if (audioFile.exists()) {
            return audioFile.getAbsolutePath();
        }
        return null;
    }
    
    /**
     * Get the duration of an audio file in milliseconds.
     * 
     * @param filePath The path to the audio file
     * @return The duration in milliseconds, or -1 if unknown
     */
    public static long getAudioDuration(String filePath) {
        if (filePath == null) {
            return -1;
        }
        
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            String durationStr = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            return durationStr != null ? Long.parseLong(durationStr) : -1;
        } catch (Exception e) {
            Log.e(TAG, "Error getting audio duration", e);
            return -1;
        } finally {
            retriever.release();
        }
    }
    
    /**
     * Convert a word to phonetic spelling based on common English pronunciation rules.
     * This is a simplified version and not a complete phonetic converter.
     * 
     * @param word The word to convert
     * @return The phonetic spelling
     */
    public static String getPhoneticSpelling(String word) {
        if (word == null || word.isEmpty()) {
            return "";
        }
        
        word = word.toLowerCase();
        
        // Common phonetic substitutions (simplified)
        word = word.replaceAll("ph", "f");
        word = word.replaceAll("th", "θ");
        word = word.replaceAll("sh", "ʃ");
        word = word.replaceAll("ch", "tʃ");
        word = word.replaceAll("zh", "ʒ");
        word = word.replaceAll("ng", "ŋ");
        
        // Vowel sounds (simplified)
        word = word.replaceAll("ee|ea", "i:");
        word = word.replaceAll("oo", "u:");
        word = word.replaceAll("ay|ai", "eɪ");
        word = word.replaceAll("ow|ou", "aʊ");
        word = word.replaceAll("oi|oy", "ɔɪ");
        
        return word;
    }
    
    /**
     * Generate a TTS audio file for a word if it doesn't already exist.
     * 
     * @param context The context
     * @param wordId The word ID
     * @param wordText The word text
     * @return true if the file was created or already exists, false on error
     */
    public static boolean ensureAudioFileExists(Context context, int wordId, String wordText) {
        // Check if file already exists
        if (hasAudioFile(context, wordId)) {
            return true;
        }
        
        // Create audio file directory if it doesn't exist
        File audioDir = new File(context.getFilesDir(), "audio");
        if (!audioDir.exists()) {
            if (!audioDir.mkdirs()) {
                Log.e(TAG, "Failed to create audio directory");
                return false;
            }
        }
        
        // Use TtsRecorder to create the audio file
        TtsRecorder ttsRecorder = new TtsRecorder(context);
        
        final boolean[] success = {false};
        final CountDownLatch latch = new CountDownLatch(1);
        
        ttsRecorder.recordTtsPronunciation(wordText, wordId, new TtsRecorder.RecordingCallback() {
            @Override
            public void onRecordingCompleted(String filePath) {
                success[0] = true;
                latch.countDown();
            }
            
            @Override
            public void onRecordingError(String errorMessage) {
                Log.e(TAG, "Error recording TTS: " + errorMessage);
                success[0] = false;
                latch.countDown();
            }
        });
        
        // Wait for completion with timeout
        try {
            latch.await(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Log.e(TAG, "Interrupted while waiting for TTS recording");
            return false;
        }
        
        // Clean up resources
        ttsRecorder.release();
        
        return success[0];
    }
    
    /**
     * Generate TTS audio files for a batch of words.
     * 
     * @param context The context
     * @param words The words to generate audio for (pairs of word ID and text)
     * @return The number of successfully generated files
     */
    public static int generateAudioFilesForBatch(Context context, List<WordIdPair> words) {
        int successCount = 0;
        
        for (WordIdPair word : words) {
            if (ensureAudioFileExists(context, word.getId(), word.getText())) {
                successCount++;
            }
        }
        
        return successCount;
    }
    
    /**
     * Clean up unused audio files to save storage space.
     * 
     * @param context The context
     * @param activeWordIds The IDs of words that should keep their audio files
     * @return The number of files cleaned up
     */
    public static int cleanupUnusedAudioFiles(Context context, List<Integer> activeWordIds) {
        File audioDir = new File(context.getFilesDir(), "audio");
        if (!audioDir.exists() || !audioDir.isDirectory()) {
            return 0;
        }
        
        File[] files = audioDir.listFiles();
        if (files == null || files.length == 0) {
            return 0;
        }
        
        int cleanedCount = 0;
        
        for (File file : files) {
            String fileName = file.getName();
            if (fileName.startsWith("word_") && fileName.endsWith(".mp3")) {
                try {
                    // Extract the word ID from the filename
                    String idStr = fileName.substring(5, fileName.length() - 4);
                    int wordId = Integer.parseInt(idStr);
                    
                    // Check if this ID is in the active list
                    if (!activeWordIds.contains(wordId)) {
                        if (file.delete()) {
                            cleanedCount++;
                        }
                    }
                } catch (NumberFormatException e) {
                    // Not a valid word audio file, skip it
                }
            }
        }
        
        return cleanedCount;
    }
    
    /**
     * Simple class to hold a word ID and text pair.
     */
    public static class WordIdPair {
        private int id;
        private String text;
        
        public WordIdPair(int id, String text) {
            this.id = id;
            this.text = text;
        }
        
        public int getId() {
            return id;
        }
        
        public String getText() {
            return text;
        }
    }
}