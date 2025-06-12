package com.bhashasetu.app.audio;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Handles audio recording functionality for the app.
 * Used for recording user pronunciation for comparison and practice.
 */
public class AudioRecorder {
    private static final String TAG = "AudioRecorder";
    
    private Context context;
    private MediaRecorder recorder;
    private String currentFilePath;
    private RecordingState recordingState = RecordingState.IDLE;
    private Executor recorderExecutor;
    private Handler mainHandler;
    private OnAudioRecordingListener listener;
    
    /**
     * Recording states
     */
    public enum RecordingState {
        IDLE,
        RECORDING,
        PAUSED,
        STOPPED
    }
    
    public AudioRecorder(Context context) {
        this.context = context.getApplicationContext();
        this.recorderExecutor = Executors.newSingleThreadExecutor();
        this.mainHandler = new Handler(Looper.getMainLooper());
    }
    
    /**
     * Start recording audio to a file.
     * 
     * @param wordId The ID of the word being recorded, used for filename
     * @return True if recording started successfully, false otherwise
     */
    public boolean startRecording(int wordId) {
        if (recordingState == RecordingState.RECORDING) {
            Log.w(TAG, "Already recording");
            return false;
        }
        
        // Create output directory if it doesn't exist
        File audioDir = new File(context.getFilesDir(), "user_recordings");
        if (!audioDir.exists()) {
            audioDir.mkdirs();
        }
        
        // Set the output file path
        currentFilePath = new File(audioDir, "word_" + wordId + "_user.m4a").getAbsolutePath();
        
        // Initialize recorder on background thread
        recorderExecutor.execute(() -> {
            try {
                if (recorder != null) {
                    releaseRecorder();
                }
                
                // Create and configure the MediaRecorder
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    recorder = new MediaRecorder(context);
                } else {
                    recorder = new MediaRecorder();
                }
                
                recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
                recorder.setAudioEncodingBitRate(128000);
                recorder.setAudioSamplingRate(44100);
                recorder.setOutputFile(currentFilePath);
                
                // Prepare and start recording
                recorder.prepare();
                recorder.start();
                
                // Update state and notify listener
                recordingState = RecordingState.RECORDING;
                notifyStateChanged();
                
            } catch (IOException e) {
                Log.e(TAG, "Failed to start recording", e);
                releaseRecorder();
                notifyError("Failed to start recording: " + e.getMessage());
                return;
            }
        });
        
        return true;
    }
    
    /**
     * Stop the current recording.
     * 
     * @return The path to the recorded audio file, or null if no recording
     */
    public String stopRecording() {
        if (recordingState != RecordingState.RECORDING) {
            Log.w(TAG, "Not recording");
            return null;
        }
        
        final String filePath = currentFilePath;
        
        recorderExecutor.execute(() -> {
            try {
                if (recorder != null) {
                    recorder.stop();
                    releaseRecorder();
                    
                    // Update state and notify listener
                    recordingState = RecordingState.STOPPED;
                    notifyStateChanged();
                    notifyRecordingCompleted(filePath);
                }
            } catch (Exception e) {
                Log.e(TAG, "Failed to stop recording", e);
                releaseRecorder();
                notifyError("Failed to stop recording: " + e.getMessage());
            }
        });
        
        return filePath;
    }
    
    /**
     * Cancel the current recording and delete the file.
     */
    public void cancelRecording() {
        if (recordingState != RecordingState.RECORDING) {
            return;
        }
        
        final String filePath = currentFilePath;
        
        recorderExecutor.execute(() -> {
            try {
                if (recorder != null) {
                    recorder.stop();
                    releaseRecorder();
                }
            } catch (Exception e) {
                Log.e(TAG, "Error while canceling recording", e);
                releaseRecorder();
            }
            
            // Delete the file
            if (filePath != null) {
                new File(filePath).delete();
            }
            
            // Update state
            recordingState = RecordingState.IDLE;
            notifyStateChanged();
        });
    }
    
    /**
     * Get the amplitude level of the current recording.
     * 
     * @return The current amplitude level (0-32767), or 0 if not recording
     */
    public int getAmplitude() {
        if (recordingState == RecordingState.RECORDING && recorder != null) {
            try {
                return recorder.getMaxAmplitude();
            } catch (Exception e) {
                Log.e(TAG, "Failed to get amplitude", e);
            }
        }
        return 0;
    }
    
    /**
     * Release resources when done with recorder.
     */
    public void release() {
        recorderExecutor.execute(this::releaseRecorder);
    }
    
    /**
     * Internal method to release the MediaRecorder.
     */
    private void releaseRecorder() {
        if (recorder != null) {
            try {
                recorder.release();
            } catch (Exception e) {
                Log.e(TAG, "Error releasing recorder", e);
            } finally {
                recorder = null;
                recordingState = RecordingState.IDLE;
            }
        }
    }
    
    /**
     * Get the current recording state.
     * 
     * @return The current RecordingState
     */
    public RecordingState getRecordingState() {
        return recordingState;
    }
    
    /**
     * Set a listener for recording events.
     * 
     * @param listener The listener to set
     */
    public void setOnAudioRecordingListener(OnAudioRecordingListener listener) {
        this.listener = listener;
    }
    
    /**
     * Notify the listener that the recording state has changed.
     */
    private void notifyStateChanged() {
        if (listener != null) {
            mainHandler.post(() -> listener.onRecordingStateChanged(recordingState));
        }
    }
    
    /**
     * Notify the listener that recording has completed.
     * 
     * @param filePath The path to the recorded audio file
     */
    private void notifyRecordingCompleted(String filePath) {
        if (listener != null) {
            mainHandler.post(() -> listener.onRecordingCompleted(filePath));
        }
    }
    
    /**
     * Notify the listener that an error occurred.
     * 
     * @param errorMessage The error message
     */
    private void notifyError(String errorMessage) {
        if (listener != null) {
            mainHandler.post(() -> listener.onRecordingError(errorMessage));
        }
    }
    
    /**
     * Interface for recording event callbacks.
     */
    public interface OnAudioRecordingListener {
        void onRecordingStateChanged(RecordingState state);
        void onRecordingCompleted(String filePath);
        void onRecordingError(String errorMessage);
    }
}