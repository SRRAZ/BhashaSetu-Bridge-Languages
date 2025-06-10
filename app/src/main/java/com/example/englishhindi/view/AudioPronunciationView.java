package com.bhashasetu.app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.bhashasetu.app.R;
import com.bhashasetu.app.audio.AudioManager;

import java.util.Locale;

/**
 * A custom view that provides audio pronunciation functionality.
 * Includes buttons for normal and slow pronunciation, with visual feedback during playback.
 */
public class AudioPronunciationView extends LinearLayout {

    private CardView cardContainer;
    private TextView labelText;
    private ImageButton buttonNormal;
    private ImageButton buttonSlow;
    private ProgressBar progressBar;
    private TextView statusText;

    private AudioManager audioManager;
    private String wordText;
    private int wordId = -1;
    private boolean isPlayingAudio = false;
    private Locale currentLocale = Locale.US;

    public AudioPronunciationView(Context context) {
        super(context);
        init(context, null);
    }

    public AudioPronunciationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AudioPronunciationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * Initialize the view.
     */
    private void init(Context context, AttributeSet attrs) {
        // Inflate the layout
        LayoutInflater.from(context).inflate(R.layout.view_audio_pronunciation, this, true);

        // Get the AudioManager instance
        audioManager = AudioManager.getInstance(context);

        // Find views
        cardContainer = findViewById(R.id.card_container);
        labelText = findViewById(R.id.text_pronunciation_label);
        buttonNormal = findViewById(R.id.button_normal_speed);
        buttonSlow = findViewById(R.id.button_slow_speed);
        progressBar = findViewById(R.id.progress_bar);
        statusText = findViewById(R.id.text_status);

        // Set up click listeners
        buttonNormal.setOnClickListener(v -> playPronunciation(false));
        buttonSlow.setOnClickListener(v -> playPronunciation(true));

        // Process custom attributes
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AudioPronunciationView);

            // Get label text
            String label = a.getString(R.styleable.AudioPronunciationView_pronunciationLabel);
            if (label != null) {
                labelText.setText(label);
            }

            // Get initial word text
            wordText = a.getString(R.styleable.AudioPronunciationView_wordText);

            // Get word ID
            wordId = a.getInt(R.styleable.AudioPronunciationView_wordId, -1);

            // Get language
            String language = a.getString(R.styleable.AudioPronunciationView_language);
            if (language != null) {
                if (language.equals("hi")) {
                    currentLocale = new Locale("hi");
                } else {
                    currentLocale = Locale.US;
                }
            }

            a.recycle();
        }

        // Hide status by default
        statusText.setVisibility(GONE);
        progressBar.setVisibility(GONE);
    }

    /**
     * Play the pronunciation of the word.
     *
     * @param slowMode Whether to play in slow mode
     */
    private void playPronunciation(boolean slowMode) {
        if (isPlayingAudio) {
            // Stop current playback
            audioManager.stopPlayback();
            resetPlaybackState();
            return;
        }

        if (wordText == null || wordText.isEmpty()) {
            return;
        }

        // Set speech rate
        audioManager.setSpeechRate(slowMode ? 0.5f : 1.0f);

        // Set language
        audioManager.setTtsLanguage(currentLocale);

        // Show progress
        isPlayingAudio = true;
        progressBar.setVisibility(VISIBLE);
        statusText.setVisibility(VISIBLE);
        statusText.setText(slowMode ? R.string.playing_slow : R.string.playing_pronunciation);

        // Update button state
        updateButtonState(slowMode);

        // Play the pronunciation
        if (wordId != -1) {
            // Try to play recorded audio first
            audioManager.playWordAudio(wordId, this::onPlaybackCompleted);
        } else {
            // Fall back to TTS
            audioManager.speakWordWithTts(wordText, this::onPlaybackCompleted);
        }
    }

    /**
     * Update the button state during playback.
     *
     * @param slowMode Whether slow mode is active
     */
    private void updateButtonState(boolean slowMode) {
        // Disable both buttons during playback
        buttonNormal.setEnabled(false);
        buttonSlow.setEnabled(false);

        // Show stop icon on the active button
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (slowMode) {
                buttonSlow.setImageResource(R.drawable.avd_play_to_stop);
                AnimatedVectorDrawable avd = (AnimatedVectorDrawable) buttonSlow.getDrawable();
                avd.start();
            } else {
                buttonNormal.setImageResource(R.drawable.avd_play_to_stop);
                AnimatedVectorDrawable avd = (AnimatedVectorDrawable) buttonNormal.getDrawable();
                avd.start();
            }
        } else {
            // Fallback for older devices
            if (slowMode) {
                buttonSlow.setImageResource(android.R.drawable.ic_media_pause);
            } else {
                buttonNormal.setImageResource(android.R.drawable.ic_media_pause);
            }
        }
    }

    /**
     * Reset the playback state after completion.
     */
    private void resetPlaybackState() {
        isPlayingAudio = false;
        progressBar.setVisibility(GONE);
        statusText.setVisibility(GONE);

        // Reset button states
        buttonNormal.setEnabled(true);
        buttonSlow.setEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            buttonNormal.setImageResource(R.drawable.avd_stop_to_play);
            buttonSlow.setImageResource(R.drawable.avd_stop_to_play);
            
            // Animate if needed
            if (buttonNormal.getDrawable() instanceof AnimatedVectorDrawable) {
                AnimatedVectorDrawable avd = (AnimatedVectorDrawable) buttonNormal.getDrawable();
                avd.start();
            }
            if (buttonSlow.getDrawable() instanceof AnimatedVectorDrawable) {
                AnimatedVectorDrawable avd = (AnimatedVectorDrawable) buttonSlow.getDrawable();
                avd.start();
            }
        } else {
            // Fallback for older devices
            buttonNormal.setImageResource(android.R.drawable.ic_media_play);
            buttonSlow.setImageResource(android.R.drawable.ic_media_play);
        }
    }

    /**
     * Callback for when playback is completed.
     */
    private void onPlaybackCompleted() {
        resetPlaybackState();
    }

    /**
     * Set the word text to pronounce.
     *
     * @param text The word text
     */
    public void setWordText(String text) {
        this.wordText = text;
    }

    /**
     * Set the word ID for recorded audio.
     *
     * @param id The word ID
     */
    public void setWordId(int id) {
        this.wordId = id;
    }

    /**
     * Set the pronunciation language.
     *
     * @param locale The language locale
     */
    public void setLanguage(Locale locale) {
        this.currentLocale = locale;
    }

    /**
     * Set the label text.
     *
     * @param label The label text
     */
    public void setLabel(String label) {
        labelText.setText(label);
    }

    /**
     * Stop any current playback when the view is detached.
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (isPlayingAudio) {
            audioManager.stopPlayback();
            isPlayingAudio = false;
        }
    }
}