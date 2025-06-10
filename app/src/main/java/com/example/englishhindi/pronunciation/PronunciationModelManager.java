package com.bhashasetu.app.pronunciation;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import com.bhashasetu.app.audio.AudioAnalyzer;
import com.bhashasetu.app.cache.CacheManager;
import com.bhashasetu.app.model.Word;

import org.tensorflow.lite.support.common.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Manages TensorFlow Lite models for pronunciation assessment.
 * Handles model loading, preparation, and selection of appropriate
 * comparison strategy based on available models and device capabilities.
 */
public class PronunciationModelManager {
    private static final String TAG = "PronunciationModelMgr";
    
    // Constants for model and asset paths
    private static final String MODEL_DIRECTORY = "pronunciation_models";
    private static final String MAIN_MODEL_FILENAME = "audio_embedding_model.tflite";
    private static final String FALLBACK_MODEL_FILENAME = "audio_embedding_lite.tflite";
    
    // Singleton instance
    private static PronunciationModelManager instance;
    
    // Context and analyzers
    private Context context;
    private TensorFlowLiteAudioAnalyzer tfLiteAudioAnalyzer;
    private AudioAnalyzer fallbackAudioAnalyzer;
    
    // Model availability flags
    private boolean mainModelAvailable = false;
    private boolean fallbackModelAvailable = false;
    
    // Pronunciation examples cache
    private Map<String, String> pronunciationExamples = new HashMap<>();
    
    /**
     * Get singleton instance
     * 
     * @param context Application context
     * @return PronunciationModelManager instance
     */
    public static synchronized PronunciationModelManager getInstance(Context context) {
        if (instance == null) {
            instance = new PronunciationModelManager(context);
        }
        return instance;
    }
    
    /**
     * Private constructor
     * 
     * @param context Application context
     */
    private PronunciationModelManager(Context context) {
        this.context = context.getApplicationContext();
        
        // Initialize the fallback analyzer
        fallbackAudioAnalyzer = new AudioAnalyzer(context);
        
        // Check and prepare models
        prepareModels();
    }
    
    /**
     * Check for model files and prepare them for use
     */
    private void prepareModels() {
        // Check if models already exist in internal storage
        File mainModelFile = new File(context.getFilesDir(), MODEL_DIRECTORY + "/" + MAIN_MODEL_FILENAME);
        File fallbackModelFile = new File(context.getFilesDir(), MODEL_DIRECTORY + "/" + FALLBACK_MODEL_FILENAME);
        
        mainModelAvailable = mainModelFile.exists();
        fallbackModelAvailable = fallbackModelFile.exists();
        
        // If models don't exist, extract them from assets
        if (!mainModelAvailable || !fallbackModelAvailable) {
            extractModelsFromAssets();
        } else {
            // Initialize TensorFlow Lite analyzer if model is available
            initializeTfLiteAnalyzer();
        }
    }
    
    /**
     * Extract models from assets to internal storage
     */
    private void extractModelsFromAssets() {
        AsyncTask.execute(() -> {
            try {
                // Create models directory if it doesn't exist
                File modelsDir = new File(context.getFilesDir(), MODEL_DIRECTORY);
                if (!modelsDir.exists()) {
                    modelsDir.mkdirs();
                }
                
                // Extract main model
                File mainModelFile = new File(modelsDir, MAIN_MODEL_FILENAME);
                if (extractAsset(MAIN_MODEL_FILENAME, mainModelFile)) {
                    mainModelAvailable = true;
                }
                
                // Extract fallback model
                File fallbackModelFile = new File(modelsDir, FALLBACK_MODEL_FILENAME);
                if (extractAsset(FALLBACK_MODEL_FILENAME, fallbackModelFile)) {
                    fallbackModelAvailable = true;
                }
                
                // Initialize TensorFlow Lite analyzer
                initializeTfLiteAnalyzer();
                
            } catch (Exception e) {
                Log.e(TAG, "Error extracting models from assets", e);
            }
        });
    }
    
    /**
     * Extract an asset file to internal storage
     * 
     * @param assetName Name of the asset file
     * @param outputFile Output file
     * @return True if extraction was successful, false otherwise
     */
    private boolean extractAsset(String assetName, File outputFile) {
        try {
            InputStream inputStream = context.getAssets().open(assetName);
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            
            byte[] buffer = new byte[1024];
            int read;
            
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            
            inputStream.close();
            outputStream.flush();
            outputStream.close();
            
            return true;
        } catch (IOException e) {
            Log.e(TAG, "Error extracting asset: " + assetName, e);
            return false;
        }
    }
    
    /**
     * Initialize the TensorFlow Lite analyzer with the appropriate model
     */
    private void initializeTfLiteAnalyzer() {
        if (mainModelAvailable) {
            // Initialize with main model
            try {
                tfLiteAudioAnalyzer = new TensorFlowLiteAudioAnalyzer(context);
                Log.i(TAG, "TensorFlow Lite analyzer initialized with main model");
            } catch (Exception e) {
                Log.e(TAG, "Error initializing TensorFlow Lite analyzer with main model", e);
                mainModelAvailable = false;
            }
        } else if (fallbackModelAvailable) {
            // Initialize with fallback model
            try {
                tfLiteAudioAnalyzer = new TensorFlowLiteAudioAnalyzer(context);
                Log.i(TAG, "TensorFlow Lite analyzer initialized with fallback model");
            } catch (Exception e) {
                Log.e(TAG, "Error initializing TensorFlow Lite analyzer with fallback model", e);
                fallbackModelAvailable = false;
            }
        }
    }
    
    /**
     * Compare a user's pronunciation with a reference
     * 
     * @param userRecordingPath Path to the user's recording
     * @param referenceRecordingPath Path to the reference recording
     * @param callback Callback for the comparison result
     */
    public void comparePronunciations(String userRecordingPath, 
                                     String referenceRecordingPath,
                                     AudioAnalyzer.PronunciationComparisonCallback callback) {
        if (tfLiteAudioAnalyzer != null && (mainModelAvailable || fallbackModelAvailable)) {
            // Use TensorFlow Lite for comparison
            tfLiteAudioAnalyzer.comparePronunciations(userRecordingPath, referenceRecordingPath, callback);
        } else {
            // Fall back to basic audio analysis
            fallbackAudioAnalyzer.comparePronunciations(userRecordingPath, referenceRecordingPath, callback);
        }
    }
    
    /**
     * Get or create a reference pronunciation for a word
     * 
     * @param word The word to get pronunciation for
     * @param callback Callback when pronunciation is ready
     */
    public void getOrCreatePronunciationExample(Word word, OnPronunciationExampleReadyListener callback) {
        // Check if we already have this pronunciation cached
        String wordKey = word.getId() + "_" + word.getEnglishWord().toLowerCase();
        if (pronunciationExamples.containsKey(wordKey)) {
            String filePath = pronunciationExamples.get(wordKey);
            if (new File(filePath).exists()) {
                callback.onPronunciationExampleReady(filePath);
                return;
            } else {
                // Remove invalid entry
                pronunciationExamples.remove(wordKey);
            }
        }
        
        // Check for pronunciation audio in assets or cache
        File audioDir = new File(context.getFilesDir(), "pronunciation_examples");
        if (!audioDir.exists()) {
            audioDir.mkdirs();
        }
        
        // Try to find the file in various locations
        File audioFile = new File(audioDir, "word_" + word.getId() + ".mp3");
        if (audioFile.exists()) {
            String filePath = audioFile.getAbsolutePath();
            pronunciationExamples.put(wordKey, filePath);
            callback.onPronunciationExampleReady(filePath);
            return;
        }
        
        // If not found, check in assets
        String assetName = "audio/" + word.getEnglishWord().toLowerCase() + ".mp3";
        if (assetExists(assetName)) {
            try {
                // Copy from assets to internal storage
                InputStream inputStream = context.getAssets().open(assetName);
                FileOutputStream outputStream = new FileOutputStream(audioFile);
                
                byte[] buffer = new byte[1024];
                int read;
                
                while ((read = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, read);
                }
                
                inputStream.close();
                outputStream.flush();
                outputStream.close();
                
                String filePath = audioFile.getAbsolutePath();
                pronunciationExamples.put(wordKey, filePath);
                callback.onPronunciationExampleReady(filePath);
                return;
            } catch (IOException e) {
                Log.e(TAG, "Error copying pronunciation example from assets", e);
            }
        }
        
        // If we couldn't find an existing pronunciation, generate one using TTS
        generateTtsPronunciation(word, callback);
    }
    
    /**
     * Generate a TTS pronunciation for a word
     * 
     * @param word The word to generate pronunciation for
     * @param callback Callback when pronunciation is ready
     */
    private void generateTtsPronunciation(Word word, OnPronunciationExampleReadyListener callback) {
        // Generate pronunciation using TtsRecorder
        TtsRecorder ttsRecorder = new TtsRecorder(context);
        
        final CountDownLatch latch = new CountDownLatch(1);
        final String[] generatedPath = {null};
        
        ttsRecorder.recordTtsPronunciation(word.getEnglishWord(), word.getId(), new TtsRecorder.RecordingCallback() {
            @Override
            public void onRecordingCompleted(String filePath) {
                generatedPath[0] = filePath;
                latch.countDown();
            }
            
            @Override
            public void onRecordingError(String errorMessage) {
                Log.e(TAG, "Error generating TTS pronunciation: " + errorMessage);
                latch.countDown();
            }
        });
        
        // Wait for the recording to complete
        try {
            boolean completed = latch.await(5, TimeUnit.SECONDS);
            if (completed && generatedPath[0] != null) {
                String wordKey = word.getId() + "_" + word.getEnglishWord().toLowerCase();
                pronunciationExamples.put(wordKey, generatedPath[0]);
                callback.onPronunciationExampleReady(generatedPath[0]);
            } else {
                callback.onPronunciationExampleError("Failed to generate pronunciation");
            }
        } catch (InterruptedException e) {
            callback.onPronunciationExampleError("Interrupted while generating pronunciation");
        }
    }
    
    /**
     * Check if an asset exists
     * 
     * @param assetName Name of the asset
     * @return True if the asset exists, false otherwise
     */
    private boolean assetExists(String assetName) {
        try {
            InputStream inputStream = context.getAssets().open(assetName);
            inputStream.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
    /**
     * Get the optimal buffer size for audio recording
     * 
     * @return Buffer size in bytes
     */
    public int getOptimalBufferSize() {
        return 4096; // Default value, can be adjusted based on model requirements
    }
    
    /**
     * Check if TensorFlow Lite is available
     * 
     * @return True if TensorFlow Lite is available, false otherwise
     */
    public boolean isTensorFlowLiteAvailable() {
        return tfLiteAudioAnalyzer != null && (mainModelAvailable || fallbackModelAvailable);
    }
    
    /**
     * Release resources
     */
    public void release() {
        if (tfLiteAudioAnalyzer != null) {
            tfLiteAudioAnalyzer.close();
            tfLiteAudioAnalyzer = null;
        }
    }
    
    /**
     * Interface for pronunciation example ready callbacks
     */
    public interface OnPronunciationExampleReadyListener {
        void onPronunciationExampleReady(String filePath);
        void onPronunciationExampleError(String errorMessage);
    }
}