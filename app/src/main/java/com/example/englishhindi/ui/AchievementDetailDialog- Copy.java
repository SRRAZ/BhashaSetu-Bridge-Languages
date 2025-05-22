package com.example.englishhindi.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.englishhindi.R;
import com.example.englishhindi.model.Achievement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Dialog to display detailed information about an achievement.
 */
public class AchievementDetailDialog extends Dialog {

    private Achievement achievement;

    /**
     * Constructor.
     *
     * @param context The context
     * @param achievement The achievement to display
     */
    public AchievementDetailDialog(@NonNull Context context, Achievement achievement) {
        super(context);
        this.achievement = achievement;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_achievement_detail);
        
        // Set dialog window properties
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        
        // Find views
        ImageView imageViewIcon = findViewById(R.id.image_view_achievement_icon);
        TextView textViewTitle = findViewById(R.id.text_view_achievement_title);
        TextView textViewDescription = findViewById(R.id.text_view_achievement_description);
        TextView textViewPoints = findViewById(R.id.text_view_achievement_points);
        TextView textViewProgress = findViewById(R.id.text_view_achievement_progress);
        ProgressBar progressBar = findViewById(R.id.progress_bar_achievement);
        TextView textViewUnlockDate = findViewById(R.id.text_view_unlock_date);
        TextView textViewType = findViewById(R.id.text_view_achievement_type);
        Button buttonClose = findViewById(R.id.button_close);
        
        // Set achievement details
        if (achievement != null) {
            imageViewIcon.setImageResource(achievement.getIconResId());
            textViewTitle.setText(achievement.getTitle());
            textViewDescription.setText(achievement.getDescription());
            textViewPoints.setText(getContext().getString(
                    R.string.achievement_points, achievement.getPointsValue()));
            
            // Set achievement type
            String type = "";
            switch (achievement.getType()) {
                case Achievement.TYPE_VOCABULARY:
                    type = getContext().getString(R.string.vocabulary_achievement);
                    break;
                case Achievement.TYPE_PRONUNCIATION:
                    type = getContext().getString(R.string.pronunciation_achievement);
                    break;
                case Achievement.TYPE_GAME:
                    type = getContext().getString(R.string.game_achievement);
                    break;
                case Achievement.TYPE_STREAK:
                    type = getContext().getString(R.string.streak_achievement);
                    break;
                case Achievement.TYPE_MASTERY:
                    type = getContext().getString(R.string.mastery_achievement);
                    break;
            }
            textViewType.setText(type);
            
            // Set progress
            int progressPercentage = achievement.getProgressPercentage();
            progressBar.setProgress(progressPercentage);
            
            if (achievement.isUnlocked()) {
                textViewProgress.setText(getContext().getString(R.string.progress_complete));
                progressBar.setProgressTintList(getContext().getColorStateList(R.color.colorCorrect));
                
                // Show unlock date
                textViewUnlockDate.setVisibility(View.VISIBLE);
                SimpleDateFormat format = new SimpleDateFormat("MMM d, yyyy", Locale.getDefault());
                String dateStr = format.format(new Date(achievement.getDateUnlocked()));
                textViewUnlockDate.setText(getContext().getString(R.string.unlocked_on, dateStr));
            } else {
                textViewProgress.setText(getContext().getString(
                        R.string.progress_format, 
                        achievement.getProgressCurrent(), 
                        achievement.getProgressTarget()));
                textViewUnlockDate.setVisibility(View.GONE);
            }
        }
        
        // Set up button
        buttonClose.setOnClickListener(v -> dismiss());
    }
}