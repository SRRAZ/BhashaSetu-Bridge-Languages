package com.example.englishhindi.pronunciation;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.englishhindi.R;
import com.example.englishhindi.audio.AudioFeedbackManager;

/**
 * Dialog that shows a summary of the pronunciation practice session.
 * Displays scores, progress, and provides feedback to the user.
 */
public class PronunciationSessionSummaryDialog extends Dialog {

    private PronunciationSessionStats stats;
    private AudioFeedbackManager audioFeedbackManager;
    
    // UI elements
    private TextView tvCompletionRate;
    private ProgressBar progressCompletion;
    private TextView tvAverageAccuracy;
    private ProgressBar progressAccuracy;
    private TextView tvDuration;
    private TextView tvFeedback;
    private TextView tvImprovement;
    private ImageView ivAchievement;
    private Button btnDone;
    private Button btnPracticeMore;

    public PronunciationSessionSummaryDialog(@NonNull Context context, PronunciationSessionStats stats) {
        super(context);
        this.stats = stats;
        this.audioFeedbackManager = new AudioFeedbackManager(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_pronunciation_summary);
        
        // Initialize UI elements
        initializeViews();
        
        // Set up the data in the UI
        setupUI();
        
        // Set up click listeners
        setupListeners();
        
        // Apply animations
        applyAnimations();
        
        // Play appropriate sound based on performance
        playFeedbackSound();
    }
    
    private void initializeViews() {
        tvCompletionRate = findViewById(R.id.tv_completion_rate);
        progressCompletion = findViewById(R.id.progress_completion);
        tvAverageAccuracy = findViewById(R.id.tv_average_accuracy);
        progressAccuracy = findViewById(R.id.progress_accuracy);
        tvDuration = findViewById(R.id.tv_duration);
        tvFeedback = findViewById(R.id.tv_feedback);
        tvImprovement = findViewById(R.id.tv_improvement);
        ivAchievement = findViewById(R.id.iv_achievement);
        btnDone = findViewById(R.id.btn_done);
        btnPracticeMore = findViewById(R.id.btn_practice_more);
    }
    
    private void setupUI() {
        // Set completion rate
        int completionRate = stats.getCompletionPercentage();
        tvCompletionRate.setText(getContext().getString(R.string.completion_rate, completionRate));
        progressCompletion.setProgress(completionRate);
        
        // Set average accuracy
        int accuracy = stats.getAverageAccuracyScore();
        tvAverageAccuracy.setText(getContext().getString(R.string.average_accuracy, accuracy));
        progressAccuracy.setProgress(accuracy);
        
        // Set session duration
        double duration = stats.getSessionDurationMinutes();
        tvDuration.setText(getContext().getString(R.string.session_duration, 
                formatDuration(duration)));
        
        // Set improvement if available
        int improvement = stats.getImprovementFromLastSession();
        if (improvement > 0) {
            tvImprovement.setText(getContext().getString(R.string.improvement_positive, improvement));
            tvImprovement.setVisibility(View.VISIBLE);
        } else if (improvement < 0) {
            tvImprovement.setText(getContext().getString(R.string.improvement_negative, Math.abs(improvement)));
            tvImprovement.setVisibility(View.VISIBLE);
        } else {
            tvImprovement.setVisibility(View.GONE);
        }
        
        // Set feedback based on performance
        setFeedbackText();
        
        // Show achievement icon if criteria are met
        if (stats.deservesPerfectPronunciationAchievement()) {
            ivAchievement.setImageResource(R.drawable.ic_achievement_pronunciation_perfect);
            ivAchievement.setVisibility(View.VISIBLE);
        } else if (stats.deservesDedicationAchievement()) {
            ivAchievement.setImageResource(R.drawable.ic_achievement_pronunciation_dedication);
            ivAchievement.setVisibility(View.VISIBLE);
        } else {
            ivAchievement.setVisibility(View.GONE);
        }
    }
    
    private void setupListeners() {
        btnDone.setOnClickListener(v -> {
            dismiss();
        });
        
        btnPracticeMore.setOnClickListener(v -> {
            // Return to the word selection screen without closing the activity
            dismiss();
            if (getOwnerActivity() instanceof PronunciationPracticeActivity) {
                // Reset the activity to practice more
                ((PronunciationPracticeActivity) getOwnerActivity()).resetForNewSession();
            }
        });
    }
    
    private void applyAnimations() {
        // Apply entrance animations to elements
        Animation fadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        Animation slideUp = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up);
        
        tvCompletionRate.startAnimation(fadeIn);
        progressCompletion.startAnimation(fadeIn);
        tvAverageAccuracy.startAnimation(fadeIn);
        progressAccuracy.startAnimation(fadeIn);
        tvDuration.startAnimation(fadeIn);
        tvFeedback.startAnimation(slideUp);
        
        if (ivAchievement.getVisibility() == View.VISIBLE) {
            Animation pulse = AnimationUtils.loadAnimation(getContext(), R.anim.pulse);
            ivAchievement.startAnimation(pulse);
        }
    }
    
    private void playFeedbackSound() {
        int score = stats.getAverageAccuracyScore();
        
        if (score >= 80) {
            audioFeedbackManager.playPositiveFeedback();
        } else if (score >= 60) {
            audioFeedbackManager.playNeutralFeedback();
        } else {
            audioFeedbackManager.playEncouragementFeedback();
        }
    }
    
    private void setFeedbackText() {
        int score = stats.getAverageAccuracyScore();
        String feedback;
        
        if (score >= 90) {
            feedback = getContext().getString(R.string.feedback_excellent_summary);
        } else if (score >= 80) {
            feedback = getContext().getString(R.string.feedback_very_good_summary);
        } else if (score >= 70) {
            feedback = getContext().getString(R.string.feedback_good_summary);
        } else if (score >= 60) {
            feedback = getContext().getString(R.string.feedback_fair_summary);
        } else if (score >= 50) {
            feedback = getContext().getString(R.string.feedback_needs_practice_summary);
        } else {
            feedback = getContext().getString(R.string.feedback_keep_practicing_summary);
        }
        
        tvFeedback.setText(feedback);
    }
    
    private String formatDuration(double minutes) {
        int mins = (int) minutes;
        int secs = (int) ((minutes - mins) * 60);
        
        return String.format("%d:%02d", mins, secs);
    }
}