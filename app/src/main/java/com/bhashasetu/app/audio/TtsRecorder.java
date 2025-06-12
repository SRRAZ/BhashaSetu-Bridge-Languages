package com.bhashasetu.app.audio;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Utility class to record TTS output to an audio file.
 * This allows creating reference pronunciation files when native speaker recordings aren't available.
 */
public class TtsRecorder {
    private static final String TAG = "TtsRecorder";
    
    private Context context;
    private TextToSpeech textToSpeech;
    private boolean ttsReady = false;
    private Executor ttsExecutor;
    private Handler mainHandler;
    
    public TtsRecorder(Context context) {
        this.context = context.getApplicationContext();
        this.ttsExecutor = Executors.newSingleThreadExecutor();
        this.mainHandler = new Handler(Looper.getMainLooper());
        initTextToSpeech();
    }
    
    /**
     * Initialize the TextToSpeech engine.
     */
    private void initTextToSpeech() {
        textToSpeech = new TextToSpeech(context, status -> {
            if (status == TextToSpeech.SUCCESS) {
                // Set default language to English (US)
                int result = textToSpeech.setLanguage(Locale.US);
                
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e(TAG, "Language not supported: " + Locale.US);
                    ttsReady = false;
                } else {
                    ttsReady = true;
                }
            } else {
                Log.e(TAG, "TextToSpeech initialization failed");
                ttsReady = false;
            }
        });
    }
    
    /**
     * Record TTS pronunciation to a file.
     * 
     * @param text The text to speak and record
     * @param wordId The ID of the word (used for filename)
     * @param callback Callback for completion status
     */
    public void recordTtsPronunciation(String text, int wordId, RecordingCallback callback) {
        if (!ttsReady) {
            // Wait a bit and try again if TTS is still initializing
            mainHandler.postDelayed(() -> {
                if (ttsReady) {
                    recordTtsPronunciation(text, wordId, callback);
                } else {
                    notifyError(callback, "TextToSpeech not ready");
                }
            }, 1000);
            return;
        }
        
        ttsExecutor.execute(() -> {
            try {
                // Create output directory if it doesn't exist
                File audioDir = new File(context.getFilesDir(), "audio");
                if (!audioDir.exists()) {
                    audioDir.mkdirs();
                }
                
                // Set the output file path
                String filePath = new File(audioDir, "word_" + wordId + ".mp3").getAbsolutePath();
                
                // Set up TTS parameters
                String utteranceId = UUID.randomUUID().toString();
                
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    // Use the newer synthesizeToFile method on newer Android versions
                    File outputFile = new File(filePath);
                    
                    // Set up completion listener
                    final CountDownLatch latch = new CountDownLatch(1);
                    final boolean[] success = {false};
                    
                    textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                        @Override
                        public void onStart(String utteranceId) {
                            // TTS started
                        }
                        
                        @Override
                        public void onDone(String utteranceId) {
                            // TTS completed successfully
                            success[0] = true;
                            latch.countDown();
                        }
                        
                        @Override
                        public void onError(String utteranceId) {
                            // TTS error occurred
                            Log.e(TAG, "TTS Error for utterance: " + utteranceId);
                            success[0] = false;
                            latch.countDown();
                        }
                    });
                    
                    // Synthesize to file
                    int result = textToSpeech.synthesizeToFile(text, null, outputFile, utteranceId);
                    
                    if (result == TextToSpeech.ERROR) {
                        notifyError(callback, "Error starting TTS synthesis");
                        return;
                    }
                    
                    // Wait for completion (with timeout)
                    if (latch.await(10, TimeUnit.SECONDS)) {
                        if (success[0]) {
                            notifySuccess(callback, filePath);
                        } else {
                            notifyError(callback, "TTS synthesis failed");
                        }
                    } else {
                        notifyError(callback, "TTS synthesis timeout");
                    }
                    
                } else {
                    // Use deprecated method for older Android versions
                    HashMap<String, String> params = new HashMap<>();
                    params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, utteranceId);
                    
                    // Set up completion listener
                    final CountDownLatch latch = new CountDownLatch(1);
                    final boolean[] success = {false};
                    
                    textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                        @Override
                        public void onStart(String utteranceId) {
                            // TTS started
                        }
                        
                        @Override
                        public void onDone(String utteranceId) {
                            // TTS completed successfully
                            success[0] = true;
                            latch.countDown();
                        }
                        
                        @Override
                        public void onError(String utteranceId) {
                            // TTS error occurred
                            Log.e(TAG, "TTS Error for utterance: " + utteranceId);
                            success[0] = false;
                            latch.countDown();
                        }
                    });
                    
                    // Synthesize to file
                    int result = textToSpeech.synthesizeToFile(text, params, filePath);
                    
                    if (result == TextToSpeech.ERROR) {
                        notifyError(callback, "Error starting TTS synthesis");
                        return;
                    }
                    
                    // Wait for completion (with timeout)
                    if (latch.await(10, TimeUnit.SECONDS)) {
                        if (success[0]) {
                            notifySuccess(callback, filePath);
                        } else {
                            notifyError(callback, "TTS synthesis failed");
                        }
                    } else {
                        notifyError(callback, "TTS synthesis timeout");
                    }
                }
                
            } catch (Exception e) {
                Log.e(TAG, "Error recording TTS", e);
                notifyError(callback, "Error recording TTS: " + e.getMessage());
            }
        });
    }
    
    /**
     * Set the speech rate for TTS.
     * 
     * @param rate The speech rate (0.5 for slow, 1.0 for normal, 1.5 for fast)
     */
    public void setSpeechRate(float rate) {
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
                return true;
            }
        }
        return false;
    }
    
    /**
     * Notify the callback of a successful recording.
     * 
     * @param callback The callback to notify
     * @param filePath The path to the recorded file
     */
    private void notifySuccess(RecordingCallback callback, String filePath) {
        if (callback != null) {
            mainHandler.post(() -> callback.onRecordingCompleted(filePath));
        }
    }
    
    /**
     * Notify the callback of an error.
     * 
     * @param callback The callback to notify
     * @param errorMessage The error message
     */
    private void notifyError(RecordingCallback callback, String errorMessage) {
        if (callback != null) {
            mainHandler.post(() -> callback.onRecordingError(errorMessage));
        }
    }
    
    /**
     * Release resources.
     */
    public void release() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
            textToSpeech = null;
        }
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
     * Interface for TTS recording callbacks.
     */
    public interface RecordingCallback {
        void onRecordingCompleted(String filePath);
        void onRecordingError(String errorMessage);
    }
}