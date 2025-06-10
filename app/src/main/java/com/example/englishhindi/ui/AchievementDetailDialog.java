package com.bhashasetu.app.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bhashasetu.app.R;
import com.bhashasetu.app.model.Achievement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Dialog to display detailed information about an achievement
 */
public class AchievementDetailDialog extends Dialog {
    
    private final Achievement achievement;
    private final SimpleDateFormat dateFormat;
    
    public AchievementDetailDialog(@NonNull Context context, Achievement achievement) {
        super(context);
        this.achievement = achievement;
        this.dateFormat = new SimpleDateFormat("MMMM dd, yyyy 'at' hh:mm a", Locale.getDefault());
        
        // Make dialog show as a full-width popup
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        );
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_achievement_detail);
        
        // Find views
        ImageView iconImageView = findViewById(R.id.achievement_icon);
        TextView titleTextView = findViewById(R.id.achievement_title);
        TextView descriptionTextView = findViewById(R.id.achievement_description);
        TextView statusTextView = findViewById(R.id.achievement_status);
        ProgressBar progressBar = findViewById(R.id.achievement_progress_bar);
        TextView progressTextView = findViewById(R.id.achievement_progress_text);
        TextView pointsTextView = findViewById(R.id.achievement_points);
        TextView categoryTextView = findViewById(R.id.achievement_category);
        Button closeButton = findViewById(R.id.close_button);
        
        // Set achievement details
        iconImageView.setImageResource(achievement.getIconResId());
        
        // Apply grayscale filter for locked achievements
        if (achievement.getStatus() == Achievement.STATUS_LOCKED) {
            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
            iconImageView.setColorFilter(filter);
        } else {
            iconImageView.clearColorFilter();
        }
        
        // Set title and description
        titleTextView.setText(achievement.getTitle());
        descriptionTextView.setText(achievement.getDescription());
        
        // Set status text
        if (achievement.getStatus() == Achievement.STATUS_UNLOCKED) {
            String unlockDate = dateFormat.format(new Date(achievement.getUnlockedTimestamp()));
            statusTextView.setText(getContext().getString(R.string.achievement_unlocked_on, unlockDate));
        } else {
            statusTextView.setText(R.string.achievement_locked);
        }
        
        // Set progress
        progressBar.setMax(achievement.getProgressTarget());
        progressBar.setProgress(achievement.getProgressCurrent());
        
        if (achievement.getStatus() == Achievement.STATUS_UNLOCKED) {
            progressTextView.setText(getContext().getString(R.string.achievement_completed));
        } else {
            progressTextView.setText(getContext().getString(
                R.string.achievement_progress_format,
                achievement.getProgressCurrent(),
                achievement.getProgressTarget()
            ));
        }
        
        // Set points
        pointsTextView.setText(getContext().getString(R.string.achievement_points_format, achievement.getPoints()));
        
        // Set category
        String categoryName;
        switch (achievement.getCategory()) {
            case "learning":
                categoryName = getContext().getString(R.string.achievement_category_learning);
                break;
            case "vocabulary":
                categoryName = getContext().getString(R.string.achievement_category_vocabulary);
                break;
            case "pronunciation":
                categoryName = getContext().getString(R.string.achievement_category_pronunciation);
                break;
            case "games":
                categoryName = getContext().getString(R.string.achievement_category_games);
                break;
            default:
                categoryName = achievement.getCategory();
                break;
        }
        categoryTextView.setText(categoryName);
        
        // Apply entry animation
        Animation slideIn = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_right);
        findViewById(R.id.dialog_content).startAnimation(slideIn);
        
        // Set close button action
        closeButton.setOnClickListener(v -> dismiss());
    }
}