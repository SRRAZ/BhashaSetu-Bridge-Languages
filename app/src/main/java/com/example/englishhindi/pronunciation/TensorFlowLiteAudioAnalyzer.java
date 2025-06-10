package com.example.englishhindi.pronunciation;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaMetadataRetriever;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.englishhindi.audio.AudioAnalyzer;

import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.support.common.FileUtil;
import org.tensorflow.lite.support.audio.TensorAudio;
import org.tensorflow.lite.support.audio.TensorAudioFormat;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * TensorFlow Lite implementation for audio analysis and pronunciation assessment.
 * This class uses a pre-trained TensorFlow Lite model to compare user pronunciation
 * with reference pronunciations.
 */
public class TensorFlowLiteAudioAnalyzer {
    private static final String TAG = "TFLiteAudioAnalyzer";
    
    // Model configuration
    private static final String MODEL_FILENAME = "audio_embedding_model.tflite";
    private static final int SAMPLE_RATE = 16000;
    private static final int RECORDING_LENGTH = 16000; // 1 second of audio at 16kHz
    
    // Class variables
    private Context context;
    private Interpreter tfLiteInterpreter;
    private TensorAudioFormat tensorAudioFormat;
    private Executor analyzerExecutor;
    private Handler mainHandler;
    
    // Cosine similarity thresholds for feedback
    private static final double EXCELLENT_THRESHOLD = 0.85;
    private static final double GOOD_THRESHOLD = 0.70;
    private static final double FAIR_THRESHOLD = 0.50;
    
    // Common pronunciation issues by language
    private Map<String, String> hindiToEnglishPronunciationTips = new HashMap<>();
    
    /**
     * Constructor
     * @param context Application context
     */
    public TensorFlowLiteAudioAnalyzer(Context context) {
        this.context = context.getApplicationContext();
        this.analyzerExecutor = Executors.newSingleThreadExecutor();
        this.mainHandler = new Handler(Looper.getMainLooper());
        
        // Initialize TensorFlow Lite
        try {
            MappedByteBuffer model = loadModelFile(context);
            Interpreter.Options options = new Interpreter.Options();
            options.setNumThreads(2);
            tfLiteInterpreter = new Interpreter(model, options);
            
            // Configure audio format for the model
            tensorAudioFormat = new TensorAudioFormat.Builder()
                    .setSampleRate(SAMPLE_RATE)
                    .setChannels(1)
                    .build();
            
            // Initialize pronunciation tips for Hindi speakers learning English
            initializePronunciationTips();
            
        } catch (IOException e) {
            Log.e(TAG, "Error loading TensorFlow Lite model", e);
        }
    }
    
    /**
     * Initialize pronunciation tips specific to Hindi speakers learning English
     */
    private void initializePronunciationTips() {
        // Common pronunciation issues for Hindi speakers
        hindiToEnglishPronunciationTips.put("v", "Hindi speakers often pronounce 'v' as 'w'. Focus on placing your upper teeth on your lower lip for 'v'.");
        hindiToEnglishPronunciationTips.put("w", "Hindi speakers may pronounce 'w' similarly to 'v'. Round your lips more for 'w'.");
        hindiToEnglishPronunciationTips.put("th", "The 'th' sound (as in 'think' or 'the') is often pronounced as 't' or 'd'. Place your tongue between your teeth.");
        hindiToEnglishPronunciationTips.put("r", "The English 'r' is different from Hindi. Don't roll your 'r' - keep your tongue raised and curved back.");
        hindiToEnglishPronunciationTips.put("l", "English 'l' is produced with the tongue tip touching the ridge behind upper front teeth.");
        hindiToEnglishPronunciationTips.put("a", "The 'a' in 'cat' is different from Hindi vowels. Open your mouth wider.");
        hindiToEnglishPronunciationTips.put("æ", "The short 'a' sound in words like 'cat' or 'hat' doesn't exist in Hindi. Practice opening your mouth wider.");
        hindiToEnglishPronunciationTips.put("ə", "The schwa sound (ə) as in 'about' should be relaxed and neutral.");
        hindiToEnglishPronunciationTips.put("ɪ", "The short 'i' in 'bit' is different from Hindi 'i'. Relax your mouth more.");
        hindiToEnglishPronunciationTips.put("ʊ", "The 'oo' in 'book' is shorter than in Hindi. Don't round your lips as much.");
        hindiToEnglishPronunciationTips.put("z", "The 'z' sound is often pronounced as 'j' by Hindi speakers. Make sure your vocal cords vibrate.");
        hindiToEnglishPronunciationTips.put("f", "Hindi speakers sometimes replace 'f' with 'ph'. Place your upper teeth on your lower lip.");
    }
    
    /**
     * Compare a user's pronunciation with a reference using TensorFlow Lite
     * 
     * @param userRecordingPath Path to the user's recorded audio file
     * @param referenceRecordingPath Path to the reference audio file
     * @param callback Callback to return the comparison results
     */
    public void comparePronunciations(String userRecordingPath, 
                                     String referenceRecordingPath,
                                     AudioAnalyzer.PronunciationComparisonCallback callback) {
        // Check if TensorFlow Lite is properly initialized
        if (tfLiteInterpreter == null) {
            notifyError(callback, "TensorFlow Lite model not initialized");
            return;
        }
        
        // Process on background thread
        analyzerExecutor.execute(() -> {
            try {
                // Extract audio embeddings
                float[] userEmbedding = extractAudioEmbedding(userRecordingPath);
                float[] referenceEmbedding = extractAudioEmbedding(referenceRecordingPath);
                
                if (userEmbedding == null || referenceEmbedding == null) {
                    notifyError(callback, "Failed to extract audio embeddings");
                    return;
                }
                
                // Calculate cosine similarity between embeddings
                double similarity = calculateCosineSimilarity(userEmbedding, referenceEmbedding);
                
                // Normalize to 0-100 scale
                double normalizedScore = similarity * 100;
                
                // Generate appropriate feedback
                String feedback = generateFeedback(normalizedScore, new File(userRecordingPath).getName());
                
                // Notify on main thread
                final double finalScore = normalizedScore;
                final String finalFeedback = feedback;
                mainHandler.post(() -> callback.onComparisonResult(finalScore, finalFeedback));
                
            } catch (Exception e) {
                Log.e(TAG, "Error comparing pronunciations", e);
                notifyError(callback, "Error comparing pronunciations: " + e.getMessage());
            }
        });
    }
    
    /**
     * Extract audio embedding from a recording using TensorFlow Lite model
     * 
     * @param audioPath Path to the audio file
     * @return Float array containing the audio embedding
     */
    private float[] extractAudioEmbedding(String audioPath) {
        try {
            // Prepare audio data
            short[] audioData = loadAndPrepareAudioFile(audioPath);
            if (audioData == null) {
                return null;
            }
            
            // Create input tensor
            TensorAudio tensorAudio = new TensorAudio(tensorAudioFormat);
            tensorAudio.load(audioData);
            TensorBuffer inputBuffer = tensorAudio.getTensorBuffer();
            
            // Create output tensor
            int[] outputShape = tfLiteInterpreter.getOutputTensor(0).shape();
            TensorBuffer outputBuffer = TensorBuffer.createFixedSize(outputShape, 
                    tfLiteInterpreter.getOutputTensor(0).dataType());
            
            // Run inference
            tfLiteInterpreter.run(inputBuffer.getBuffer(), outputBuffer.getBuffer());
            
            // Return the embedding as a float array
            return outputBuffer.getFloatArray();
            
        } catch (Exception e) {
            Log.e(TAG, "Error extracting audio embedding", e);
            return null;
        }
    }
    
    /**
     * Load and prepare audio file for processing
     * 
     * @param audioPath Path to the audio file
     * @return Array of audio samples
     */
    private short[] loadAndPrepareAudioFile(String audioPath) {
        try {
            // Load audio file
            File audioFile = new File(audioPath);
            if (!audioFile.exists()) {
                Log.e(TAG, "Audio file does not exist: " + audioPath);
                return null;
            }
            
            // Get audio information using MediaMetadataRetriever
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(audioPath);
            
            // Read the audio data (simplified implementation)
            // In a real app, we would handle various audio formats, resample if needed, etc.
            FileInputStream fis = new FileInputStream(audioFile);
            byte[] data = new byte[fis.available()];
            fis.read(data);
            fis.close();
            
            // Convert byte array to short array (assuming 16-bit PCM)
            short[] audioData = new short[data.length / 2];
            ByteBuffer.wrap(data).asShortBuffer().get(audioData);
            
            // Normalize and resize to fixed length
            return normalizeAndResizeAudio(audioData, RECORDING_LENGTH);
            
        } catch (IOException e) {
            Log.e(TAG, "Error loading audio file", e);
            return null;
        }
    }
    
    /**
     * Normalize and resize audio data to fixed length
     * 
     * @param audioData Original audio data
     * @param targetLength Target length
     * @return Normalized and resized audio data
     */
    private short[] normalizeAndResizeAudio(short[] audioData, int targetLength) {
        if (audioData == null || audioData.length == 0) {
            return null;
        }
        
        // Find the maximum absolute value for normalization
        short maxAbsValue = 1; // Avoid division by zero
        for (short sample : audioData) {
            short absValue = (short) Math.abs(sample);
            if (absValue > maxAbsValue) {
                maxAbsValue = absValue;
            }
        }
        
        // Create target array
        short[] resizedData = new short[targetLength];
        
        // Simple resizing (linear interpolation would be better in a real app)
        if (audioData.length >= targetLength) {
            // Downsample by taking samples at regular intervals
            double stepSize = (double) audioData.length / targetLength;
            for (int i = 0; i < targetLength; i++) {
                int sourceIndex = (int) (i * stepSize);
                if (sourceIndex < audioData.length) {
                    // Normalize while copying
                    resizedData[i] = (short) ((audioData[sourceIndex] * 32767) / maxAbsValue);
                }
            }
        } else {
            // Upsample by repeating samples
            double stepSize = (double) audioData.length / targetLength;
            for (int i = 0; i < targetLength; i++) {
                int sourceIndex = (int) (i * stepSize);
                if (sourceIndex < audioData.length) {
                    // Normalize while copying
                    resizedData[i] = (short) ((audioData[sourceIndex] * 32767) / maxAbsValue);
                }
            }
        }
        
        return resizedData;
    }
    
    /**
     * Calculate cosine similarity between two embeddings
     * 
     * @param embedding1 First embedding
     * @param embedding2 Second embedding
     * @return Cosine similarity (0-1 range, higher means more similar)
     */
    private double calculateCosineSimilarity(float[] embedding1, float[] embedding2) {
        // Check if embeddings are valid
        if (embedding1 == null || embedding2 == null || 
            embedding1.length == 0 || embedding2.length == 0 ||
            embedding1.length != embedding2.length) {
            return 0.0;
        }
        
        double dotProduct = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;
        
        for (int i = 0; i < embedding1.length; i++) {
            dotProduct += embedding1[i] * embedding2[i];
            norm1 += embedding1[i] * embedding1[i];
            norm2 += embedding2[i] * embedding2[i];
        }
        
        if (norm1 <= 0.0 || norm2 <= 0.0) {
            return 0.0;
        }
        
        return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }
    
    /**
     * Generate feedback based on similarity score and word
     * 
     * @param score Similarity score (0-100)
     * @param wordFilename Filename of the word recording
     * @return Feedback message
     */
    private String generateFeedback(double score, String wordFilename) {
        // Extract word from filename (assuming format like "word_123_user.m4a")
        String word = "";
        if (wordFilename.contains("_")) {
            String[] parts = wordFilename.split("_");
            if (parts.length > 1) {
                // Try to extract the word from the filename
                try {
                    // If it's a number, use it as an ID
                    int wordId = Integer.parseInt(parts[1]);
                    // Here we would lookup the word text from the database
                } catch (NumberFormatException e) {
                    // If not a number, assume it's the word itself
                    word = parts[1].toLowerCase();
                }
            }
        }
        
        StringBuilder feedback = new StringBuilder();
        
        // Basic feedback based on score
        if (score >= EXCELLENT_THRESHOLD * 100) {
            feedback.append("Excellent pronunciation! You sound very natural. ");
        } else if (score >= GOOD_THRESHOLD * 100) {
            feedback.append("Good pronunciation! Keep practicing to sound even more natural. ");
        } else if (score >= FAIR_THRESHOLD * 100) {
            feedback.append("Your pronunciation is understandable, but needs more practice. ");
        } else {
            feedback.append("Try again and listen carefully to the example. ");
        }
        
        // Add specific pronunciation tips based on the word
        if (!word.isEmpty()) {
            for (Map.Entry<String, String> entry : hindiToEnglishPronunciationTips.entrySet()) {
                if (word.contains(entry.getKey())) {
                    feedback.append("\n\nTip: ").append(entry.getValue());
                    break; // Just add one tip to avoid overwhelming the user
                }
            }
        }
        
        // General advice for low scores
        if (score < GOOD_THRESHOLD * 100) {
            feedback.append("\n\nRemember to: " +
                    "\n• Speak at a natural pace" +
                    "\n• Pay attention to word stress" +
                    "\n• Listen to the example carefully before speaking");
        }
        
        return feedback.toString();
    }
    
    /**
     * Load TensorFlow Lite model from assets
     * 
     * @param context Application context
     * @return MappedByteBuffer containing the model
     * @throws IOException If model cannot be loaded
     */
    private MappedByteBuffer loadModelFile(Context context) throws IOException {
        try (AssetFileDescriptor fileDescriptor = context.getAssets().openFd(MODEL_FILENAME);
             FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor())) {
            FileChannel fileChannel = inputStream.getChannel();
            long startOffset = fileDescriptor.getStartOffset();
            long declaredLength = fileDescriptor.getDeclaredLength();
            return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
        }
    }
    
    /**
     * Notify error to callback on main thread
     * 
     * @param callback Callback to notify
     * @param errorMessage Error message
     */
    private void notifyError(AudioAnalyzer.PronunciationComparisonCallback callback, String errorMessage) {
        if (callback != null) {
            mainHandler.post(() -> callback.onComparisonError(errorMessage));
        }
    }
    
    /**
     * Release resources
     */
    public void close() {
        if (tfLiteInterpreter != null) {
            tfLiteInterpreter.close();
            tfLiteInterpreter = null;
        }
    }
}