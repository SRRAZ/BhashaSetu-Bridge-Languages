package com.bhashasetu.app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bhashasetu.app.R;
import com.bhashasetu.app.model.AppSettings;
import com.bhashasetu.app.util.AudioManager;

/**
 * Custom view for displaying a word with audio pronunciation
 */
public class AudioPronunciationView extends LinearLayout {

    private TextView wordTextView;
    private TextView pronunciationTextView;
    private ImageButton audioButton;
    
    private String wordText;
    private String pronunciationText;
    private String audioFile;
    private boolean isPlaying;
    
    private OnAudioButtonClickListener listener;
    private AudioManager audioManager;

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
    
    private void init(Context context, AttributeSet attrs) {
        // Inflate layout
        LayoutInflater.from(context).inflate(R.layout.view_audio_pronunciation, this, true);
        
        // Get references to views
        wordTextView = findViewById(R.id.text_word);
        pronunciationTextView = findViewById(R.id.text_pronunciation);
        audioButton = findViewById(R.id.button_audio);
        
        // Initialize audio manager
        audioManager = AudioManager.getInstance(context);
        isPlaying = false;
        
        // Parse custom attributes if present
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AudioPronunciationView);
            
            wordText = a.getString(R.styleable.AudioPronunciationView_wordText);
            pronunciationText = a.getString(R.styleable.AudioPronunciationView_pronunciationText);
            audioFile = a.getString(R.styleable.AudioPronunciationView_audioFile);
            
            a.recycle();
        }
        
        // Set initial values
        if (wordText != null) {
            wordTextView.setText(wordText);
        }
        
        if (pronunciationText != null) {
            pronunciationTextView.setText(pronunciationText);
        }
        
        // Set up audio button click listener
        audioButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPlaying && audioFile != null && !audioFile.isEmpty()) {
                    playAudio();
                    
                    if (listener != null) {
                        listener.onAudioButtonClick(audioFile);
                    }
                }
            }
        });
        
        // Set up audio completion listener
        audioManager.setOnAudioCompletionListener(new AudioManager.OnAudioCompletionListener() {
            @Override
            public void onAudioCompleted() {
                isPlaying = false;
                updateAudioButtonState();
            }
            
            @Override
            public void onAudioError() {
                isPlaying = false;
                updateAudioButtonState();
            }
        });
        
        // Update visibility of components
        updateAudioButtonState();
    }
    
    /**
     * Play the audio file
     */
    private void playAudio() {
        if (!AppSettings.getInstance(getContext()).isAudioEnabled()) {
            return;
        }
        
        if (audioFile == null || audioFile.isEmpty()) {
            return;
        }
        
        // Check if audio file is a URL or a local file
        if (audioFile.startsWith("http://") || audioFile.startsWith("https://")) {
            audioManager.playFromUrl(audioFile);
        } else if (audioFile.startsWith("asset://")) {
            // Play from assets folder
            String assetPath = audioFile.substring(8);  // Remove "asset://" prefix
            audioManager.playFromAsset(assetPath);
        } else {
            // Play from local file
            audioManager.playFromFile(audioFile);
        }
        
        isPlaying = true;
        updateAudioButtonState();
    }
    
    /**
     * Update the audio button icon based on playback state
     */
    private void updateAudioButtonState() {
        if (isPlaying) {
            audioButton.setImageResource(android.R.drawable.ic_media_pause);
        } else {
            audioButton.setImageResource(android.R.drawable.ic_lock_silent_mode_off);
        }
        
        // Show audio button only if audio file is available
        audioButton.setVisibility(audioFile != null && !audioFile.isEmpty() ? View.VISIBLE : View.GONE);
    }
    
    /**
     * Set the word text
     * @param text word text to display
     */
    public void setWordText(String text) {
        this.wordText = text;
        wordTextView.setText(text);
    }
    
    /**
     * Set the pronunciation text
     * @param text pronunciation text to display
     */
    public void setPronunciationText(String text) {
        this.pronunciationText = text;
        pronunciationTextView.setText(text);
        pronunciationTextView.setVisibility(text != null && !text.isEmpty() ? View.VISIBLE : View.GONE);
    }
    
    /**
     * Set the audio file path or URL
     * @param audioFile file path or URL
     */
    public void setAudioFile(String audioFile) {
        this.audioFile = audioFile;
        updateAudioButtonState();
    }
    
    /**
     * Set a listener for audio button clicks
     * @param listener the listener to set
     */
    public void setOnAudioButtonClickListener(OnAudioButtonClickListener listener) {
        this.listener = listener;
    }
    
    /**
     * Interface for audio button click callbacks
     */
    public interface OnAudioButtonClickListener {
        void onAudioButtonClick(String audioFile);
    }
}