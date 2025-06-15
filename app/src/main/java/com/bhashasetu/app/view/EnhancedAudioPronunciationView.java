package com.bhashasetu.app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bhashasetu.app.R;
import com.bhashasetu.app.audio.AppAudioManager;
import com.bhashasetu.app.audio.AudioVisualizer;

/**
 * Enhanced custom view for audio pronunciation features.
 * Provides buttons for normal and slow-speed pronunciation playback with audio visualization.
 */
public class EnhancedAudioPronunciationView extends LinearLayout {

    private TextView textWord;
    private TextView textLabel;
    private ImageButton buttonNormalSpeed;
    private ImageButton buttonSlowSpeed;
    private ProgressBar progressBar;
    private TextView textStatus;
    private AudioVisualizer audioVisualizer;

    private AppAudioManager audioManager;
    private int wordId = -1;
    private String wordText = "";
    private Handler handler = new Handler(Looper.getMainLooper());

    // State
    private boolean isPlaying = false;
    private Runnable amplitudeUpdateRunnable;

    public EnhancedAudioPronunciationView(Context context) {
        super(context);
        init(context, null);
    }

    public EnhancedAudioPronunciationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public EnhancedAudioPronunciationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(VERTICAL);

        // Inflate layout
        LayoutInflater.from(context).inflate(R.layout.view_audio_pronunciation, this);

        // Find views
        textLabel = findViewById(R.id.text_pronunciation_label);
        buttonNormalSpeed = findViewById(R.id.button_normal_speed);
        buttonSlowSpeed = findViewById(R.id.button_slow_speed);
        progressBar = findViewById(R.id.progress_bar);
        textStatus = findViewById(R.id.text_status);
        audioVisualizer = findViewById(R.id.audio_visualizer);

        // Get audio manager
        audioManager = AppAudioManager.getInstance(context);

        // Set up visualizer
        if (audioVisualizer != null) {
            audioVisualizer.setBarColor(context.getResources().getColor(R.color.colorAccent));
            audioVisualizer.setBarCount(7);
            audioVisualizer.setAnimationInterval(80);
        }

        // Set up click listeners
        buttonNormalSpeed.setOnClickListener(v -> playPronunciation(1.0f));
        buttonSlowSpeed.setOnClickListener(v -> playPronunciation(0.5f));

        // Apply custom attributes if available
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AudioPronunciationView);
            String label = a.getString(R.styleable.AudioPronunciationView_label);
            if (label != null) {
                textLabel.setText(label);
            }
            a.recycle();
        }

        // Create amplitude update runnable for visualizer animation
        amplitudeUpdateRunnable = new Runnable() {
            private int counter = 0;
            private float[] amplitudePattern = {0.2f, 0.3f, 0.5f, 0.7f, 0.9f, 0.7f, 0.5f, 0.3f};
            
            @Override
            public void run() {
                if (isPlaying && audioVisualizer != null) {
                    // Use a predetermined pattern for visualization
                    audioVisualizer.setAmplitude(amplitudePattern[counter % amplitudePattern.length]);
                    counter++;
                    handler.postDelayed(this, 150);
                }
            }
        };
    }

    /**
     * Set the word ID for pronunciation.
     *
     * @param wordId The word ID
     */
    public void setWordId(int wordId) {
        this.wordId = wordId;
    }

    /**
     * Set the word text for pronunciation.
     *
     * @param wordText The word text
     */
    public void setWordText(String wordText) {
        this.wordText = wordText;
    }

    /**
     * Set the label text.
     *
     * @param label The label text
     */
    public void setLabel(String label) {
        textLabel.setText(label);
    }

    /**
     * Play the pronunciation at the specified speed.
     *
     * @param speed The playback speed (0.5 for slow, 1.0 for normal)
     */
    private void playPronunciation(float speed) {
        if (isPlaying) {
            // Already playing, stop current playback
            audioManager.stopPlayback();
            resetPlaybackState();
            return;
        }

        // Update UI
        isPlaying = true;
        progressBar.setVisibility(View.GONE);
        textStatus.setVisibility(View.VISIBLE);
        textStatus.setText(speed < 1.0f ? R.string.playing_slow : R.string.playing);

        // Disable buttons
        buttonNormalSpeed.setEnabled(false);
        buttonSlowSpeed.setEnabled(false);

        // Show and start audio visualizer
        if (audioVisualizer != null) {
            audioVisualizer.setVisibility(View.VISIBLE);
            audioVisualizer.startAnimation();
            handler.post(amplitudeUpdateRunnable);
        }

        // Set the speech rate
        audioManager.setSpeechRate(speed);

        // Play the pronunciation
        if (wordId != -1) {
            // Try to play recorded audio first
            audioManager.playWordAudio(wordId, new AppAudioManager.OnPlaybackCompletedListener() {
                @Override
                public void onPlaybackCompleted() {
                    resetPlaybackState();
                }
            });
        } else if (wordText != null && !wordText.isEmpty()) {
            // Fall back to TTS
            audioManager.speakWordWithTts(wordText, new AppAudioManager.OnPlaybackCompletedListener() {
                @Override
                public void onPlaybackCompleted() {
                    resetPlaybackState();
                }
            });
        }

        // Set a timeout in case completion callback isn't triggered
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isPlaying) {
                    resetPlaybackState();
                }
            }
        }, 5000); // 5 second timeout
    }

    /**
     * Reset the playback state.
     */
    private void resetPlaybackState() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                isPlaying = false;
                progressBar.setVisibility(View.GONE);
                textStatus.setVisibility(View.GONE);
                buttonNormalSpeed.setEnabled(true);
                buttonSlowSpeed.setEnabled(true);
                
                // Stop and hide visualizer
                if (audioVisualizer != null) {
                    audioVisualizer.stopAnimation();
                    audioVisualizer.setVisibility(View.GONE);
                }
                
                // Remove amplitude updates
                handler.removeCallbacks(amplitudeUpdateRunnable);
            }
        });
    }

    /**
     * Clean up resources when detached from window.
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeCallbacksAndMessages(null);
        if (audioVisualizer != null) {
            audioVisualizer.stopAnimation();
        }
    }
}