package com.bhashasetu.app.audio;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Provides audio analysis capabilities for pronunciation comparison.
 * Compares user pronunciations with reference pronunciations to provide feedback.
 */
public class AudioAnalyzer {
    private static final String TAG = "AudioAnalyzer";
    
    private Context context;
    private Executor analyzerExecutor;
    private Handler mainHandler;
    
    private static final int SAMPLE_RATE = 44100;
    private static final int FFT_SIZE = 1024;
    private static final int OVERLAP = 512;
    
    public AudioAnalyzer(Context context) {
        this.context = context.getApplicationContext();
        this.analyzerExecutor = Executors.newSingleThreadExecutor();
        this.mainHandler = new Handler(Looper.getMainLooper());
    }
    
    /**
     * Compare a user recording with a reference pronunciation.
     * 
     * @param userRecordingPath Path to the user's pronunciation recording
     * @param referenceRecordingPath Path to the reference pronunciation recording
     * @param callback Callback to receive the similarity score
     */
    public void comparePronunciations(String userRecordingPath, 
                                     String referenceRecordingPath,
                                     PronunciationComparisonCallback callback) {
        analyzerExecutor.execute(() -> {
            try {
                // Extract audio features from both recordings
                List<float[]> userFeatures = extractMfccFeatures(userRecordingPath);
                List<float[]> referenceFeatures = extractMfccFeatures(referenceRecordingPath);
                
                if (userFeatures == null || referenceFeatures == null || 
                    userFeatures.isEmpty() || referenceFeatures.isEmpty()) {
                    notifyError(callback, "Failed to extract audio features");
                    return;
                }
                
                // Calculate similarity using Dynamic Time Warping
                double similarity = calculateDtwSimilarity(userFeatures, referenceFeatures);
                
                // Normalize score to 0-100% range
                // Lower DTW distance means higher similarity
                double normalizedScore = Math.max(0, Math.min(100, 100 * (1 - similarity / 10)));
                
                // Provide feedback categories
                String feedback;
                if (normalizedScore >= 85) {
                    feedback = "Excellent pronunciation!";
                } else if (normalizedScore >= 70) {
                    feedback = "Good pronunciation, keep practicing!";
                } else if (normalizedScore >= 50) {
                    feedback = "Fair pronunciation, try listening again.";
                } else {
                    feedback = "Keep practicing, listen carefully to the example.";
                }
                
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
     * Extract Mel-frequency cepstral coefficients (MFCC) features from an audio recording.
     * 
     * @param audioPath Path to the audio file
     * @return List of MFCC feature vectors
     */
    private List<float[]> extractMfccFeatures(String audioPath) {
        try {
            // Read audio data from file
            short[] audioData = readAudioFile(audioPath);
            if (audioData == null || audioData.length == 0) {
                return null;
            }
            
            // Process in frames with overlap
            List<float[]> features = new ArrayList<>();
            for (int frameStart = 0; frameStart < audioData.length - FFT_SIZE; frameStart += OVERLAP) {
                // Extract frame
                double[] frame = new double[FFT_SIZE];
                for (int i = 0; i < FFT_SIZE && frameStart + i < audioData.length; i++) {
                    frame[i] = audioData[frameStart + i] / 32768.0; // Normalize to [-1, 1]
                }
                
                // Apply Hamming window
                applyHammingWindow(frame);
                
                // Calculate MFCC for this frame
                float[] mfcc = calculateMfcc(frame);
                if (mfcc != null) {
                    features.add(mfcc);
                }
            }
            
            return features;
        } catch (Exception e) {
            Log.e(TAG, "Error extracting MFCC features", e);
            return null;
        }
    }
    
    /**
     * Read audio data from a file.
     * 
     * @param audioPath Path to the audio file
     * @return Array of audio samples
     */
    private short[] readAudioFile(String audioPath) {
        try {
            File audioFile = new File(audioPath);
            if (!audioFile.exists()) {
                Log.e(TAG, "Audio file does not exist: " + audioPath);
                return null;
            }
            
            // Use MediaExtractor to get audio format information
            MediaExtractor extractor = new MediaExtractor();
            extractor.setDataSource(audioPath);
            
            // Find the audio track
            for (int i = 0; i < extractor.getTrackCount(); i++) {
                MediaFormat format = extractor.getTrackFormat(i);
                String mime = format.getString(MediaFormat.KEY_MIME);
                if (mime != null && mime.startsWith("audio/")) {
                    extractor.selectTrack(i);
                    break;
                }
            }
            
            // Read raw audio data
            FileInputStream fis = new FileInputStream(audioFile);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            
            // Convert to 16-bit PCM if possible
            ByteBuffer byteBuffer = ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN);
            ShortBuffer shortBuffer = byteBuffer.asShortBuffer();
            short[] audioData = new short[shortBuffer.limit()];
            shortBuffer.get(audioData);
            
            extractor.release();
            return audioData;
            
        } catch (IOException e) {
            Log.e(TAG, "Error reading audio file", e);
            return null;
        }
    }
    
    /**
     * Apply a Hamming window to a frame of audio data.
     * 
     * @param frame The audio frame to process
     */
    private void applyHammingWindow(double[] frame) {
        for (int i = 0; i < frame.length; i++) {
            frame[i] *= 0.54 - 0.46 * Math.cos(2 * Math.PI * i / (frame.length - 1));
        }
    }
    
    /**
     * Calculate Mel-frequency cepstral coefficients for a frame of audio.
     * 
     * @param frame The audio frame to process
     * @return Array of MFCC values
     */
    private float[] calculateMfcc(double[] frame) {
        try {
            // Pad to power of 2 for FFT
            int fftSize = 1;
            while (fftSize < frame.length) {
                fftSize *= 2;
            }
            
            double[] paddedFrame = new double[fftSize];
            System.arraycopy(frame, 0, paddedFrame, 0, frame.length);
            
            // Perform FFT
            FastFourierTransformer transformer = new FastFourierTransformer(DftNormalization.STANDARD);
            Complex[] complexResults = transformer.transform(paddedFrame, TransformType.FORWARD);
            
            // Calculate power spectrum
            double[] powerSpectrum = new double[fftSize / 2];
            for (int i = 0; i < fftSize / 2; i++) {
                powerSpectrum[i] = complexResults[i].abs();
            }
            
            // Create Mel filterbank (simplified version)
            int numMelFilters = 20;
            double[] melFilterEnergies = applyMelFilterbank(powerSpectrum, numMelFilters);
            
            // Take log of filterbank energies
            for (int i = 0; i < melFilterEnergies.length; i++) {
                melFilterEnergies[i] = Math.log(melFilterEnergies[i] + 1e-10); // Avoid log(0)
            }
            
            // Calculate DCT (simplified)
            int numCepstralCoeffs = 13;
            float[] mfcc = new float[numCepstralCoeffs];
            for (int i = 0; i < numCepstralCoeffs; i++) {
                mfcc[i] = 0;
                for (int j = 0; j < numMelFilters; j++) {
                    mfcc[i] += melFilterEnergies[j] * Math.cos(Math.PI * i * (j + 0.5) / numMelFilters);
                }
            }
            
            return mfcc;
        } catch (Exception e) {
            Log.e(TAG, "Error calculating MFCC", e);
            return null;
        }
    }
    
    /**
     * Apply a Mel filterbank to a power spectrum.
     * 
     * @param powerSpectrum Power spectrum to filter
     * @param numFilters Number of Mel filters to apply
     * @return Filterbank energies
     */
    private double[] applyMelFilterbank(double[] powerSpectrum, int numFilters) {
        double[] melFilterEnergies = new double[numFilters];
        Arrays.fill(melFilterEnergies, 0);
        
        // Simplified Mel filterbank implementation
        // In a real application, a more sophisticated approach would be used
        
        int spectrumLength = powerSpectrum.length;
        for (int i = 0; i < numFilters; i++) {
            int filterStart = i * spectrumLength / (numFilters + 1);
            int filterMiddle = (i + 1) * spectrumLength / (numFilters + 1);
            int filterEnd = (i + 2) * spectrumLength / (numFilters + 1);
            
            // Triangular filter
            for (int j = filterStart; j < filterMiddle; j++) {
                if (j < spectrumLength) {
                    double weight = (j - filterStart) / (double)(filterMiddle - filterStart);
                    melFilterEnergies[i] += weight * powerSpectrum[j];
                }
            }
            
            for (int j = filterMiddle; j < filterEnd; j++) {
                if (j < spectrumLength) {
                    double weight = (filterEnd - j) / (double)(filterEnd - filterMiddle);
                    melFilterEnergies[i] += weight * powerSpectrum[j];
                }
            }
        }
        
        return melFilterEnergies;
    }
    
    /**
     * Calculate similarity using Dynamic Time Warping algorithm.
     * 
     * @param features1 First set of features
     * @param features2 Second set of features
     * @return DTW distance (lower means more similar)
     */
    private double calculateDtwSimilarity(List<float[]> features1, List<float[]> features2) {
        int n = features1.size();
        int m = features2.size();
        
        // Create cost matrix
        double[][] costMatrix = new double[n + 1][m + 1];
        
        // Initialize first row and column to infinity
        for (int i = 0; i <= n; i++) {
            Arrays.fill(costMatrix[i], Double.POSITIVE_INFINITY);
        }
        
        // Set starting point to 0
        costMatrix[0][0] = 0;
        
        // Fill the cost matrix
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                double cost = calculateDistance(features1.get(i - 1), features2.get(j - 1));
                costMatrix[i][j] = cost + Math.min(
                        Math.min(costMatrix[i - 1][j], costMatrix[i][j - 1]),
                        costMatrix[i - 1][j - 1]);
            }
        }
        
        // The DTW distance is in the bottom right cell
        return costMatrix[n][m];
    }
    
    /**
     * Calculate Euclidean distance between two feature vectors.
     * 
     * @param feature1 First feature vector
     * @param feature2 Second feature vector
     * @return Euclidean distance
     */
    private double calculateDistance(float[] feature1, float[] feature2) {
        double sum = 0;
        int length = Math.min(feature1.length, feature2.length);
        
        for (int i = 0; i < length; i++) {
            double diff = feature1[i] - feature2[i];
            sum += diff * diff;
        }
        
        return Math.sqrt(sum);
    }
    
    /**
     * Notify the callback of an error.
     * 
     * @param callback The callback to notify
     * @param errorMessage The error message
     */
    private void notifyError(PronunciationComparisonCallback callback, String errorMessage) {
        if (callback != null) {
            mainHandler.post(() -> callback.onComparisonError(errorMessage));
        }
    }
    
    /**
     * Interface for pronunciation comparison callbacks.
     */
    public interface PronunciationComparisonCallback {
        void onComparisonResult(double similarityScore, String feedback);
        void onComparisonError(String errorMessage);
    }
}