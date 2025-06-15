package com.bhashasetu.app.audio;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.util.LruCache;

import androidx.preference.PreferenceManager;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Central manager for audio playback in the app.
 * Handles both recorded audio files and text-to-speech pronunciation.
 */
public class AppAudioManager {
    private static final String TAG = "AppAudioManager";
    private static final int MAX_CACHE_SIZE = 20; // Maximum number of cached audio players
    
    private static AppAudioManager instance;
    
    private Context context;
    private MediaPlayer currentPlayer;
    private TextToSpeech textToSpeech;
    private boolean ttsReady = false;
    private LruCache<String, MediaPlayer> playerCache;
    private Map<String, OnPlaybackCompletedListener> completionListeners;
    private AudioFocusRequest audioFocusRequest;
    private android.media.AudioManager systemAudioManager;
    private Executor audioExecutor;
    private Handler mainHandler;
    
    private float speechRate = 1.0f;
    private Locale currentTtsLocale = Locale.US;
    
    private AppAudioManager(Context context) {
        this.context = context.getApplicationContext();
        this.playerCache = new LruCache<>(MAX_CACHE_SIZE);
        this.completionListeners = new HashMap<>();
        this.systemAudioManager = (android.media.AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        this.audioExecutor = Executors.newSingleThreadExecutor();
        this.mainHandler = new Handler(Looper.getMainLooper());
        
        // Initialize TextToSpeech
        initTextToSpeech();
        
        // Set up AudioFocusRequest for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_ACCESSIBILITY)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .build();
            
            audioFocusRequest = new AudioFocusRequest.Builder(android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK)
                    .setAudioAttributes(audioAttributes)
                    .setWillPauseWhenDucked(true)
                    .setOnAudioFocusChangeListener(focusChange -> {
                        if (focusChange == android.media.AudioManager.AUDIOFOCUS_LOSS) {
                            stopPlayback();
                        }
                    })
                    .build();
        }
    }
    
    /**
     * Get the singleton instance of AppAudioManager.
     * 
     * @param context The application context
     * @return The AppAudioManager instance
     */
    public static synchronized AppAudioManager getInstance(Context context) {
        if (instance == null) {
            instance = new AppAudioManager(context);
        }
        return instance;
    }
    
    /**
     * Initialize the TextToSpeech engine.
     */
    private void initTextToSpeech() {
        textToSpeech = new TextToSpeech(context, status -> {
            if (status == TextToSpeech.SUCCESS) {
                // Set default language to English (US)
                int result = textToSpeech.setLanguage(currentTtsLocale);
                
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e(TAG, "Language not supported: " + currentTtsLocale);
                    ttsReady = false;
                } else {
                    ttsReady = true;
                }
            } else {
                Log.e(TAG, "TextToSpeech initialization failed");
                ttsReady = false;
            }
        });
        
        // Set up TTS progress listener
        textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {
                // TTS playback started
            }
            
            @Override
            public void onDone(String utteranceId) {
                // TTS playback completed
                mainHandler.post(() -> {
                    OnPlaybackCompletedListener listener = completionListeners.get(utteranceId);
                    if (listener != null) {
                        listener.onPlaybackCompleted();
                        completionListeners.remove(utteranceId);
                    }
                });
            }
            
            @Override
            public void onError(String utteranceId) {
                // TTS playback error
                Log.e(TAG, "TTS Error for utterance: " + utteranceId);
            }
        });
    }
    
    /**
     * Set the speech rate for TTS.
     * 
     * @param rate The speech rate (0.5 for slow, 1.0 for normal, 1.5 for fast)
     */
    public void setSpeechRate(float rate) {
        this.speechRate = rate;
        if (textToSpeech != null) {
            textToSpeech.setSpeechRate(rate);
        }
    }
    
    /**
     * Set the language for TTS.
     * 
     * @param locale The language locale (e.g., Locale.US for English, new Locale("hi") for Hindi)
     * @return True if the language was set successfully, false otherwise
     */
    public boolean setTtsLanguage(Locale locale) {
        if (textToSpeech != null && ttsReady) {
            int result = textToSpeech.setLanguage(locale);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e(TAG, "Language not supported: " + locale);
                return false;
            } else {
                currentTtsLocale = locale;
                return true;
            }
        }
        return false;
    }
    
    /**
     * Play a recorded audio file.
     * 
     * @param wordId The ID of the word
     * @param listener The listener to be notified when playback is completed
     */
    public void playWordAudio(int wordId, OnPlaybackCompletedListener listener) {
        audioExecutor.execute(() -> {
            File audioFile = getAudioFile(wordId);
            if (audioFile != null && audioFile.exists()) {
                playAudioFile(audioFile, listener);
            } else {
                // Fall back to TTS if audio file is not available
                mainHandler.post(() -> speakWordWithTts("word_" + wordId, listener));
            }
        });
    }
    
    /**
     * Get the audio file for a word.
     * 
     * @param wordId The ID of the word
     * @return The audio file, or null if it doesn't exist
     */
    private File getAudioFile(int wordId) {
        File audioDir = new File(context.getFilesDir(), "audio");
        if (!audioDir.exists()) {
            audioDir.mkdirs();
        }
        
        // Check if the audio file exists
        File audioFile = new File(audioDir, "word_" + wordId + ".mp3");
        return audioFile.exists() ? audioFile : null;
    }
    
    /**
     * Play an audio file.
     * 
     * @param file The audio file to play
     * @param listener The listener to be notified when playback is completed
     */
    private void playAudioFile(File file, OnPlaybackCompletedListener listener) {
        // Request audio focus
        requestAudioFocus();
        
        // Stop any current playback
        stopPlayback();
        
        try {
            // Check if we have a cached player for this file
            String filePath = file.getAbsolutePath();
            MediaPlayer player = playerCache.get(filePath);
            
            if (player == null) {
                // Create a new player
                player = new MediaPlayer();
                player.setDataSource(context, Uri.fromFile(file));
                player.prepare();
                playerCache.put(filePath, player);
            }
            
            // Set up completion listener
            player.setOnCompletionListener(mp -> {
                abandonAudioFocus();
                mainHandler.post(() -> {
                    if (listener != null) {
                        listener.onPlaybackCompleted();
                    }
                });
            });
            
            // Start playback
            player.start();
            currentPlayer = player;
            
        } catch (IOException e) {
            Log.e(TAG, "Error playing audio file: " + e.getMessage());
            abandonAudioFocus();
            
            // Fall back to TTS
            mainHandler.post(() -> {
                if (listener != null) {
                    speakWordWithTts("fallback_" + file.getName(), listener);
                }
            });
        }
    }
    
    /**
     * Speak a word using text-to-speech.
     * 
     * @param text The text to speak
     * @param listener The listener to be notified when playback is completed
     */
    public void speakWordWithTts(String text, OnPlaybackCompletedListener listener) {
        if (!ttsReady) {
            Log.e(TAG, "TTS not ready");
            if (listener != null) {
                listener.onPlaybackCompleted();
            }
            return;
        }
        
        // Request audio focus
        requestAudioFocus();
        
        // Stop any current playback
        stopPlayback();
        
        // Generate a unique utterance ID
        String utteranceId = UUID.randomUUID().toString();
        
        // Store the completion listener
        if (listener != null) {
            completionListeners.put(utteranceId, listener);
        }
        
        // Set the speech rate
        textToSpeech.setSpeechRate(speechRate);
        
        // Speak the text
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
    }
    
    /**
     * Request audio focus.
     */
    private void requestAudioFocus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            systemAudioManager.requestAudioFocus(audioFocusRequest);
        } else {
            systemAudioManager.requestAudioFocus(
                    focusChange -> {
                        if (focusChange == android.media.AudioManager.AUDIOFOCUS_LOSS) {
                            stopPlayback();
                        }
                    },
                    AppAudioManager.STREAM_MUSIC,
                    android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK);
        }
    }
    
    /**
     * Abandon audio focus.
     */
    private void abandonAudioFocus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            systemAudioManager.abandonAudioFocusRequest(audioFocusRequest);
        } else {
            systemAudioManager.abandonAudioFocus(null);
        }
    }
    
    /**
     * Stop any current playback.
     */
    public void stopPlayback() {
        // Stop MediaPlayer if playing
        if (currentPlayer != null && currentPlayer.isPlaying()) {
            currentPlayer.stop();
            currentPlayer.reset();
        }
        
        // Stop TTS if speaking
        if (textToSpeech != null && textToSpeech.isSpeaking()) {
            textToSpeech.stop();
        }
        
        // Abandon audio focus
        abandonAudioFocus();
    }
    
    /**
     * Release resources.
     */
    public void release() {
        // Stop any current playback
        stopPlayback();
        
        // Release all cached players
        for (int i = 0; i < playerCache.size(); i++) {
            MediaPlayer player = playerCache.valueAt(i);
            if (player != null) {
                player.release();
            }
        }
        playerCache.evictAll();
        
        // Shutdown TTS
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
            textToSpeech = null;
        }
        
        // Clear completion listeners
        completionListeners.clear();
        
        // Clear instance
        instance = null;
    }
    
    /**
     * Check if TTS is ready.
     * 
     * @return True if TTS is ready, false otherwise
     */
    public boolean isTtsReady() {
        return ttsReady;
    }
    
    /**
     * Interface for playback completion callbacks.
     */
    public interface OnPlaybackCompletedListener {
        void onPlaybackCompleted();
    }
}