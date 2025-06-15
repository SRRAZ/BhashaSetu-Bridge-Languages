package com.bhashasetu.app.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.bhashasetu.app.R;
import com.bhashasetu.app.audio.AppAudioManager;
import com.bhashasetu.app.audio.AudioAnalyzer;
import com.bhashasetu.app.audio.AudioRecorder;
import com.bhashasetu.app.audio.TtsRecorder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * A custom view for recording and comparing pronunciation.
 * Provides a complete UI for recording, playback, and comparison with reference pronunciations.
 */
public class PronunciationRecorderView extends LinearLayout {

    private CardView cardViewRecorder;
    private TextView textViewRecorderTitle;
    private TextView textViewRecorderInstructions;
    private ImageButton buttonListenReference;
    private ImageButton buttonRecord;
    private ImageButton buttonPlayRecording;
    private LinearLayout layoutRecordingVisualizer;
    private LinearLayout layoutComparisonResult;
    private LinearLayout layoutRecorderDefault;
    private TextView textViewComparisonScore;
    private TextView textViewComparisonFeedback;
    private LinearLayout layoutRecordingControls;
    private Button buttonCancelRecording;
    private Button buttonStopRecording;
    private Button buttonCompare;

    // Visualizer bars
    private List<View> visualizerBars = new ArrayList<>();
    private List<ValueAnimator> visualizerAnimators = new ArrayList<>();
    private Handler visualizerHandler = new Handler(Looper.getMainLooper());
    private Runnable visualizerUpdateRunnable;

    // Audio components
    private AppAudioManager audioManager;
    private AudioRecorder audioRecorder;
    private AudioAnalyzer audioAnalyzer;

    // State tracking
    private boolean hasRecording = false;
    private String currentRecordingPath;
    private String referenceAudioPath;
    private int wordId = -1;
    private String wordText;

    // Callbacks
    private OnPronunciationComparedListener onPronunciationComparedListener;

    public PronunciationRecorderView(Context context) {
        super(context);
        init(context, null);
    }

    public PronunciationRecorderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PronunciationRecorderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * Initialize the view.
     */
    private void init(Context context, AttributeSet attrs) {
        // Inflate the layout
        LayoutInflater.from(context).inflate(R.layout.view_pronunciation_recorder, this, true);

        // Get audio components
        audioManager = AppAudioManager.getInstance(context);
        audioRecorder = new AudioRecorder(context);
        audioAnalyzer = new AudioAnalyzer(context);

        // Setup audio recorder listener
        audioRecorder.setOnAudioRecordingListener(new AudioRecorder.OnAudioRecordingListener() {
            @Override
            public void onRecordingStateChanged(AudioRecorder.RecordingState state) {
                updateRecordingState(state);
            }

            @Override
            public void onRecordingCompleted(String filePath) {
                hasRecording = true;
                currentRecordingPath = filePath;
                buttonPlayRecording.setEnabled(true);
                buttonCompare.setEnabled(true);
            }

            @Override
            public void onRecordingError(String errorMessage) {
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                resetRecordingState();
            }
        });

        // Find views
        cardViewRecorder = findViewById(R.id.card_view_recorder);
        textViewRecorderTitle = findViewById(R.id.text_view_recorder_title);
        textViewRecorderInstructions = findViewById(R.id.text_view_recorder_instructions);
        buttonListenReference = findViewById(R.id.button_listen_reference);
        buttonRecord = findViewById(R.id.button_record);
        buttonPlayRecording = findViewById(R.id.button_play_recording);
        layoutRecordingVisualizer = findViewById(R.id.layout_recording_visualizer);
        layoutComparisonResult = findViewById(R.id.layout_comparison_result);
        layoutRecorderDefault = findViewById(R.id.layout_recorder_default);
        textViewComparisonScore = findViewById(R.id.text_view_comparison_score);
        textViewComparisonFeedback = findViewById(R.id.text_view_comparison_feedback);
        layoutRecordingControls = findViewById(R.id.layout_recording_controls);
        buttonCancelRecording = findViewById(R.id.button_cancel_recording);
        buttonStopRecording = findViewById(R.id.button_stop_recording);
        buttonCompare = findViewById(R.id.button_compare);

        // Collect visualizer bars
        visualizerBars.add(findViewById(R.id.visualizer_bar_1));
        visualizerBars.add(findViewById(R.id.visualizer_bar_2));
        visualizerBars.add(findViewById(R.id.visualizer_bar_3));
        visualizerBars.add(findViewById(R.id.visualizer_bar_4));
        visualizerBars.add(findViewById(R.id.visualizer_bar_5));
        visualizerBars.add(findViewById(R.id.visualizer_bar_6));
        visualizerBars.add(findViewById(R.id.visualizer_bar_7));

        // Initialize visualizer animators
        for (View bar : visualizerBars) {
            ValueAnimator animator = ValueAnimator.ofInt(10, bar.getLayoutParams().height, 10);
            animator.setDuration(600);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.setRepeatMode(ValueAnimator.REVERSE);
            animator.addUpdateListener(animation -> {
                int value = (int) animation.getAnimatedValue();
                bar.getLayoutParams().height = value;
                bar.requestLayout();
            });
            visualizerAnimators.add(animator);
        }

        // Setup click listeners
        setupClickListeners();
    }

    /**
     * Setup click listeners for buttons.
     */
    private void setupClickListeners() {
        // Listen to reference pronunciation
        buttonListenReference.setOnClickListener(v -> {
            if (wordId != -1) {
                playReferenceAudio();
            } else if (wordText != null && !wordText.isEmpty()) {
                speakReferenceWord();
            }
        });

        // Start recording
        buttonRecord.setOnClickListener(v -> {
            if (checkRecordPermission()) {
                startRecording();
            } else {
                requestRecordPermission();
            }
        });

        // Play back user's recording
        buttonPlayRecording.setOnClickListener(v -> {
            if (hasRecording && currentRecordingPath != null) {
                playUserRecording();
            }
        });

        // Cancel recording
        buttonCancelRecording.setOnClickListener(v -> {
            audioRecorder.cancelRecording();
            resetRecordingState();
        });

        // Stop recording
        buttonStopRecording.setOnClickListener(v -> {
            audioRecorder.stopRecording();
        });

        // Compare pronunciations
        buttonCompare.setOnClickListener(v -> {
            comparePronunciations();
        });
    }

    /**
     * Start recording user pronunciation.
     */
    private void startRecording() {
        // Reset previous recording state
        if (hasRecording) {
            hasRecording = false;
            currentRecordingPath = null;
            buttonPlayRecording.setEnabled(false);
            buttonCompare.setEnabled(false);
        }

        // Hide comparison results if visible
        layoutComparisonResult.setVisibility(GONE);
        layoutRecorderDefault.setVisibility(GONE);

        // Show recording UI
        layoutRecordingVisualizer.setVisibility(VISIBLE);
        layoutRecordingControls.setVisibility(VISIBLE);

        // Start the visualizer animation
        startVisualizerAnimation();

        // Start recording
        audioRecorder.startRecording(wordId);
    }

    /**
     * Update UI based on recording state.
     * 
     * @param state The current recording state
     */
    private void updateRecordingState(AudioRecorder.RecordingState state) {
        switch (state) {
            case RECORDING:
                buttonRecord.setEnabled(false);
                buttonListenReference.setEnabled(false);
                buttonPlayRecording.setEnabled(false);
                break;
                
            case STOPPED:
                stopVisualizerAnimation();
                layoutRecordingVisualizer.setVisibility(GONE);
                layoutRecordingControls.setVisibility(GONE);
                layoutRecorderDefault.setVisibility(VISIBLE);
                buttonRecord.setEnabled(true);
                buttonListenReference.setEnabled(true);
                buttonCompare.setVisibility(VISIBLE);
                break;
                
            case IDLE:
                resetRecordingState();
                break;
        }
    }

    /**
     * Reset the recording state.
     */
    private void resetRecordingState() {
        stopVisualizerAnimation();
        hasRecording = false;
        currentRecordingPath = null;
        
        layoutRecordingVisualizer.setVisibility(GONE);
        layoutRecordingControls.setVisibility(GONE);
        layoutComparisonResult.setVisibility(GONE);
        layoutRecorderDefault.setVisibility(VISIBLE);
        
        buttonRecord.setEnabled(true);
        buttonListenReference.setEnabled(true);
        buttonPlayRecording.setEnabled(false);
        buttonCompare.setEnabled(false);
        buttonCompare.setVisibility(GONE);
    }

    /**
     * Start the visualizer animation.
     */
    private void startVisualizerAnimation() {
        // Create a runnable to randomize visualizer heights
        if (visualizerUpdateRunnable == null) {
            visualizerUpdateRunnable = new Runnable() {
                @Override
                public void run() {
                    // Get amplitude from recorder if available
                    int amplitude = audioRecorder.getAmplitude();
                    float amplitudeFactor = Math.min(1.0f, amplitude / 16000.0f);
                    
                    // Update each bar's height
                    Random random = new Random();
                    for (int i = 0; i < visualizerAnimators.size(); i++) {
                        ValueAnimator animator = visualizerAnimators.get(i);
                        
                        // Calculate a height based on amplitude and randomness
                        int minHeight = 10;
                        int maxHeight = 10 + (int)(70 * amplitudeFactor);
                        int targetHeight = minHeight + random.nextInt(maxHeight - minHeight + 1);
                        
                        // Update the animator
                        animator.cancel();
                        animator.setIntValues(
                                visualizerBars.get(i).getLayoutParams().height,
                                targetHeight);
                        animator.start();
                    }
                    
                    // Schedule the next update
                    visualizerHandler.postDelayed(this, 150);
                }
            };
        }

        // Start the visualizer update loop
        visualizerHandler.post(visualizerUpdateRunnable);
    }

    /**
     * Stop the visualizer animation.
     */
    private void stopVisualizerAnimation() {
        // Cancel the update loop
        if (visualizerUpdateRunnable != null) {
            visualizerHandler.removeCallbacks(visualizerUpdateRunnable);
        }
        
        // Stop all animators
        for (ValueAnimator animator : visualizerAnimators) {
            animator.cancel();
        }
    }

    /**
     * Play the reference audio.
     */
    private void playReferenceAudio() {
        if (referenceAudioPath != null && new File(referenceAudioPath).exists()) {
            // Play the recorded reference audio
            audioManager.playWordAudio(wordId, null);
        } else {
            // Fall back to TTS
            speakReferenceWord();
        }
    }

    /**
     * Speak the reference word using TTS.
     */
    private void speakReferenceWord() {
        if (wordText != null && !wordText.isEmpty()) {
            audioManager.speakWordWithTts(wordText, null);
        }
    }

    /**
     * Play the user's recording.
     */
    private void playUserRecording() {
        if (currentRecordingPath != null && new File(currentRecordingPath).exists()) {
            // Use MediaPlayer to play the recording
            audioManager.stopPlayback(); // Stop any current playback
            
            // Create a MediaPlayer to play the recording
            android.media.MediaPlayer mediaPlayer = new android.media.MediaPlayer();
            try {
                mediaPlayer.setDataSource(currentRecordingPath);
                mediaPlayer.prepare();
                mediaPlayer.start();
                
                // Release MediaPlayer when done
                mediaPlayer.setOnCompletionListener(mp -> {
                    mp.release();
                });
            } catch (Exception e) {
                mediaPlayer.release();
                Toast.makeText(getContext(), "Error playing recording", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Compare the user's pronunciation with the reference.
     */
    private void comparePronunciations() {
        if (!hasRecording || currentRecordingPath == null) {
            Toast.makeText(getContext(), "Please record your pronunciation first", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Show loading state
        buttonCompare.setEnabled(false);
        buttonCompare.setText(R.string.comparing);
        
        // Hide other layouts
        layoutRecorderDefault.setVisibility(GONE);
        layoutRecordingVisualizer.setVisibility(GONE);
        
        // Determine reference path
        String refPath = referenceAudioPath;
        if (refPath == null || !new File(refPath).exists()) {
            // If no reference file exists, create a temporary one using TTS
            refPath = createTemporaryReferenceAudio();
        }
        
        // If we have a reference path, compare the recordings
        if (refPath != null) {
            audioAnalyzer.comparePronunciations(
                    currentRecordingPath,
                    refPath,
                    new AudioAnalyzer.PronunciationComparisonCallback() {
                        @Override
                        public void onComparisonResult(double similarityScore, String feedback) {
                            // Display results
                            textViewComparisonScore.setText(String.format("%.0f%%", similarityScore));
                            textViewComparisonFeedback.setText(feedback);
                            
                            // Show comparison result layout
                            layoutComparisonResult.setVisibility(VISIBLE);
                            
                            // Reset button
                            buttonCompare.setText(R.string.compare_pronunciation);
                            buttonCompare.setEnabled(true);
                            
                            // Notify listener
                            if (onPronunciationComparedListener != null) {
                                onPronunciationComparedListener.onPronunciationCompared(
                                        similarityScore, feedback);
                            }
                        }
                        
                        @Override
                        public void onComparisonError(String errorMessage) {
                            Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                            
                            // Reset UI
                            layoutRecorderDefault.setVisibility(VISIBLE);
                            buttonCompare.setText(R.string.compare_pronunciation);
                            buttonCompare.setEnabled(true);
                        }
                    });
        } else {
            // No reference audio available
            Toast.makeText(getContext(), "No reference audio available", Toast.LENGTH_SHORT).show();
            
            // Reset UI
            layoutRecorderDefault.setVisibility(VISIBLE);
            buttonCompare.setText(R.string.compare_pronunciation);
            buttonCompare.setEnabled(true);
        }
    }

    /**
     * Create a temporary reference audio file using TTS.
     * 
     * @return Path to the temporary file, or null if failed
     */
    private String createTemporaryReferenceAudio() {
        if (wordText == null || wordText.isEmpty()) {
            return null;
        }
        
        final String[] tempFilePath = {null};
        final CountDownLatch latch = new CountDownLatch(1);
        
        // Use TtsRecorder to create a reference file
        TtsRecorder ttsRecorder = new TtsRecorder(getContext());
        ttsRecorder.recordTtsPronunciation(wordText, wordId, new TtsRecorder.RecordingCallback() {
            @Override
            public void onRecordingCompleted(String filePath) {
                tempFilePath[0] = filePath;
                latch.countDown();
            }
            
            @Override
            public void onRecordingError(String errorMessage) {
                Log.e("PronunciationRecorderView", "Error creating reference audio: " + errorMessage);
                latch.countDown();
            }
        });
        
        // Wait for completion with timeout
        try {
            latch.await(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Log.e("PronunciationRecorderView", "Interrupted while waiting for TTS recording");
        }
        
        return tempFilePath[0];
    }

    /**
     * Check if we have permission to record audio.
     * 
     * @return True if we have permission, false otherwise
     */
    private boolean checkRecordPermission() {
        return ContextCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Request permission to record audio.
     */
    private void requestRecordPermission() {
        // This should be handled by the containing activity
        Toast.makeText(getContext(), "Permission to record audio is required", Toast.LENGTH_SHORT).show();
    }

    /**
     * Set the word to be pronounced.
     * 
     * @param wordId The ID of the word
     * @param wordText The text of the word
     */
    public void setWord(int wordId, String wordText) {
        this.wordId = wordId;
        this.wordText = wordText;
        
        // Check for existing reference audio
        checkReferenceAudio();
    }

    /**
     * Check if a reference audio file exists for this word.
     */
    private void checkReferenceAudio() {
        if (wordId != -1) {
            // Look for reference audio file
            File audioDir = new File(getContext().getFilesDir(), "audio");
            File audioFile = new File(audioDir, "word_" + wordId + ".mp3");
            
            if (audioFile.exists()) {
                referenceAudioPath = audioFile.getAbsolutePath();
            } else {
                referenceAudioPath = null;
            }
        }
    }

    /**
     * Set a listener for pronunciation comparison results.
     * 
     * @param listener The listener to set
     */
    public void setOnPronunciationComparedListener(OnPronunciationComparedListener listener) {
        this.onPronunciationComparedListener = listener;
    }

    /**
     * Interface for pronunciation comparison callbacks.
     */
    public interface OnPronunciationComparedListener {
        void onPronunciationCompared(double similarityScore, String feedback);
    }

    /**
     * Clean up resources when the view is detached.
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        
        // Stop any ongoing recording
        if (audioRecorder.getRecordingState() == AudioRecorder.RecordingState.RECORDING) {
            audioRecorder.cancelRecording();
        }
        
        // Stop visualizer animations
        stopVisualizerAnimation();
        
        // Clear visualizer update handler
        visualizerHandler.removeCallbacksAndMessages(null);
    }
}