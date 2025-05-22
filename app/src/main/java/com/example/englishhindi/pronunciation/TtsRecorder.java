package com.example.englishhindi.pronunciation;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Utility class to record TTS (Text-To-Speech) output to audio files.
 * Used for generating reference pronunciations when actual recordings are not available.
 */
public class TtsRecorder {
    private static final String TAG = "TtsRecorder";
    
    private Context context;
    private TextToSpeech textToSpeech;
    private boolean ttsInitialized = false;
    private Executor recorderExecutor;
    private Handler mainHandler;
    
    /**
     * Constructor
     * 
     * @param context Application context
     */
    public TtsRecorder(Context context) {
        this.context = context.getApplicationContext();
        this.recorderExecutor = Executors.newSingleThreadExecutor();
        this.mainHandler = new Handler(Looper.getMainLooper());
        
        // Initialize TTS
        initializeTextToSpeech();
    }
    
    /**
     * Initialize the TextToSpeech engine
     */
    private void initializeTextToSpeech() {
        textToSpeech = new TextToSpeech(context, status -> {
            if (status == TextToSpeech.SUCCESS) {
                // Set language to US English for consistency
                int result = textToSpeech.setLanguage(Locale.US);
                
                if (result == TextToSpeech.LANG_MISSING_DATA ||
                    result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e(TAG, "US English language is not supported");
                    ttsInitialized = false;
                } else {
                    // Set speech rate slightly slower for clearer pronunciation
                    textToSpeech.setSpeechRate(0.8f);
                    ttsInitialized = true;
                }
            } else {
                Log.e(TAG, "TTS initialization failed with status: " + status);
                ttsInitialized = false;
            }
        });
    }
    
    /**
     * Record a TTS pronunciation of a word to a file
     * 
     * @param word The word to pronounce
     * @param wordId The ID of the word (for filename)
     * @param callback Callback for recording events
     */
    public void recordTtsPronunciation(String word, int wordId, RecordingCallback callback) {
        recorderExecutor.execute(() -> {
            // Wait for TTS to initialize if needed
            if (!waitForTtsInitialization()) {
                notifyError(callback, "TTS engine failed to initialize");
                return;
            }
            
            // Create output directory if it doesn't exist
            File audioDir = new File(context.getFilesDir(), "pronunciation_examples");
            if (!audioDir.exists()) {
                audioDir.mkdirs();
            }
            
            // Create output file
            File outputFile = new File(audioDir, "word_" + wordId + ".mp3");
            String outputPath = outputFile.getAbsolutePath();
            
            // Use TTS engine to synthesize and save speech
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // For Lollipop and above, we can use the TTS synthesize to file feature
                recordUsingTtsSynthesizeToFile(word, outputPath, callback);
            } else {
                // For older versions, we need a different approach
                // This would require audio recording while TTS speaks, which is complex
                notifyError(callback, "TTS recording not supported on this device");
            }
        });
    }
    
    /**
     * Record using TTS synthesizeToFile method (API 21+)
     * 
     * @param text Text to synthesize
     * @param outputPath Output file path
     * @param callback Callback for recording events
     */
    private void recordUsingTtsSynthesizeToFile(String text, String outputPath, RecordingCallback callback) {
        final String utteranceId = UUID.randomUUID().toString();
        final CountDownLatch latch = new CountDownLatch(1);
        final boolean[] success = {false};
        
        // Set up progress listener
        textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {
                // Not needed
            }
            
            @Override
            public void onDone(String utteranceId) {
                success[0] = true;
                latch.countDown();
            }
            
            @Override
            public void onError(String utteranceId) {
                success[0] = false;
                latch.countDown();
            }
            
            // Added for API 21+
            @Override
            public void onError(String utteranceId, int errorCode) {
                success[0] = false;
                latch.countDown();
            }
        });
        
        // For better pronunciation of standalone words, sometimes adding
        // "Say the word" before the word helps the TTS engine
        String enhancedText = text;
        
        // Create utterance params
        HashMap<String, String> params = new HashMap<>();
        params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, utteranceId);
        
        // Synthesize to file
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            File tempFile = new File(outputPath);
            int result = textToSpeech.synthesizeToFile(enhancedText, params, tempFile);
            
            if (result != TextToSpeech.SUCCESS) {
                notifyError(callback, "TTS synthesizeToFile failed with error code: " + result);
                return;
            }
            
            // Wait for completion
            try {
                boolean completed = latch.await(10, TimeUnit.SECONDS);
                if (!completed) {
                    notifyError(callback, "TTS synthesis timeout");
                    return;
                }
                
                if (success[0]) {
                    notifySuccess(callback, outputPath);
                } else {
                    notifyError(callback, "TTS synthesis failed");
                }
            } catch (InterruptedException e) {
                notifyError(callback, "Interrupted while waiting for TTS synthesis");
            }
        } else {
            // This code path should not be reached as we check API level earlier
            notifyError(callback, "TTS synthesizeToFile not supported on this device");
        }
    }
    
    /**
     * Wait for TTS to initialize
     * 
     * @return True if TTS initialized successfully, false otherwise
     */
    private boolean waitForTtsInitialization() {
        // Wait up to 5 seconds for TTS to initialize
        for (int i = 0; i < 50; i++) {
            if (ttsInitialized) {
                return true;
            }
            
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                return false;
            }
        }
        
        return ttsInitialized;
    }
    
    /**
     * Notify success on main thread
     * 
     * @param callback Callback to notify
     * @param filePath Path to the recorded file
     */
    private void notifySuccess(RecordingCallback callback, String filePath) {
        if (callback != null) {
            mainHandler.post(() -> callback.onRecordingCompleted(filePath));
        }
    }
    
    /**
     * Notify error on main thread
     * 
     * @param callback Callback to notify
     * @param errorMessage Error message
     */
    private void notifyError(RecordingCallback callback, String errorMessage) {
        if (callback != null) {
            mainHandler.post(() -> callback.onRecordingError(errorMessage));
        }
    }
    
    /**
     * Release resources
     */
    public void release() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
            textToSpeech = null;
        }
    }
    
    /**
     * Interface for recording callbacks
     */
    public interface RecordingCallback {
        void onRecordingCompleted(String filePath);
        void onRecordingError(String errorMessage);
    }
}