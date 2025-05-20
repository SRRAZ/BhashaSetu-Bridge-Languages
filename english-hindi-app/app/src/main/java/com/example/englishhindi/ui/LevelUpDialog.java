package com.example.englishhindi.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.englishhindi.R;
import com.example.englishhindi.audio.AudioFeedbackManager;
import com.example.englishhindi.model.DifficultyLevel;

/**
 * Dialog shown when a user levels up, displaying the new level, unlocked content,
 * and providing celebratory animations and sounds.
 */
public class LevelUpDialog extends Dialog {

    private int newLevel;
    private String newContentDescription;
    private AudioFeedbackManager audioFeedbackManager;
    
    // UI Components
    private TextView tvNewLevel;
    private TextView tvCongratulations;
    private TextView tvUnlockedContent;
    private ImageView ivLevelIcon;
    private ImageView ivConfetti;
    private Button btnContinue;
    private StarburstView starburstView;
    
    private OnLevelUpContinueListener continueListener;

    public LevelUpDialog(@NonNull Context context, int newLevel) {
        super(context);
        this.newLevel = newLevel;
        this.audioFeedbackManager = new AudioFeedbackManager(context);
        
        // Set appropriate content description based on level
        DifficultyLevel difficultyLevel = DifficultyLevel.fromLevel(newLevel);
        this.newContentDescription = difficultyLevel.getDescription();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_level_up);
        
        // Initialize UI components
        tvNewLevel = findViewById(R.id.tv_new_level);
        tvCongratulations = findViewById(R.id.tv_congratulations);
        tvUnlockedContent = findViewById(R.id.tv_unlocked_content);
        ivLevelIcon = findViewById(R.id.iv_level_icon);
        ivConfetti = findViewById(R.id.iv_confetti);
        btnContinue = findViewById(R.id.btn_continue);
        starburstView = findViewById(R.id.starburst_view);
        
        // Set up UI data
        tvNewLevel.setText(getContext().getString(R.string.level_reached, newLevel));
        tvUnlockedContent.setText(getContext().getString(R.string.unlocked_content, newContentDescription));
        
        // Set level icon
        DifficultyLevel difficultyLevel = DifficultyLevel.fromLevel(newLevel);
        ivLevelIcon.setImageResource(difficultyLevel.getIconResId());
        
        // Set up animations
        setupAnimations();
        
        // Set up button click listener
        btnContinue.setOnClickListener(v -> {
            if (continueListener != null) {
                continueListener.onContinue(newLevel);
            }
            dismiss();
        });
        
        // Play celebration sound
        audioFeedbackManager.playLevelUpSound();
    }
    
    private void setupAnimations() {
        // Starburst animation
        starburstView.startAnimation();
        
        // Title animation
        Animation titleAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.scale_up);
        tvNewLevel.startAnimation(titleAnimation);
        
        // Confetti animation
        Animation confettiAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.confetti_fall);
        ivConfetti.startAnimation(confettiAnimation);
        
        // Icon pulse animation
        Animation pulseAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.pulse_repeat);
        ivLevelIcon.startAnimation(pulseAnimation);
        
        // Text animations
        Animation fadeInSlideUp = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in_slide_up);
        Animation fadeInDelayed = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in_delayed);
        
        tvCongratulations.startAnimation(fadeInSlideUp);
        tvUnlockedContent.startAnimation(fadeInDelayed);
        
        // Button animation
        Animation bounceIn = AnimationUtils.loadAnimation(getContext(), R.anim.bounce_in);
        btnContinue.startAnimation(bounceIn);
    }
    
    /**
     * Set a listener for when the user presses the continue button
     * @param listener The listener to set
     */
    public void setOnLevelUpContinueListener(OnLevelUpContinueListener listener) {
        this.continueListener = listener;
    }
    
    /**
     * Interface for handling continue button click
     */
    public interface OnLevelUpContinueListener {
        void onContinue(int level);
    }
}