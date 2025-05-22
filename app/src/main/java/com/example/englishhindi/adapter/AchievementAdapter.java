package com.example.englishhindi.adapter;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.englishhindi.R;
import com.example.englishhindi.model.Achievement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Adapter for displaying achievements in a RecyclerView
 */
public class AchievementAdapter extends ListAdapter<Achievement, AchievementAdapter.AchievementViewHolder> {
    
    private final OnAchievementClickListener clickListener;
    private final SimpleDateFormat dateFormat;
    
    public interface OnAchievementClickListener {
        void onAchievementClick(Achievement achievement);
    }
    
    public AchievementAdapter(OnAchievementClickListener clickListener) {
        super(DIFF_CALLBACK);
        this.clickListener = clickListener;
        this.dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
    }
    
    private static final DiffUtil.ItemCallback<Achievement> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Achievement>() {
                @Override
                public boolean areItemsTheSame(@NonNull Achievement oldItem, @NonNull Achievement newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }
                
                @Override
                public boolean areContentsTheSame(@NonNull Achievement oldItem, @NonNull Achievement newItem) {
                    return oldItem.getStatus() == newItem.getStatus() &&
                           oldItem.getProgressCurrent() == newItem.getProgressCurrent() &&
                           oldItem.isHidden() == newItem.isHidden();
                }
            };
    
    @NonNull
    @Override
    public AchievementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_achievement, parent, false);
        return new AchievementViewHolder(itemView);
    }
    
    @Override
    public void onBindViewHolder(@NonNull AchievementViewHolder holder, int position) {
        Achievement achievement = getItem(position);
        holder.bind(achievement, clickListener);
    }
    
    public class AchievementViewHolder extends RecyclerView.ViewHolder {
        private final ImageView iconImageView;
        private final TextView titleTextView;
        private final TextView descriptionTextView;
        private final TextView statusTextView;
        private final ProgressBar progressBar;
        private final TextView progressTextView;
        private final TextView pointsTextView;
        private final View achievementCardView;
        
        public AchievementViewHolder(@NonNull View itemView) {
            super(itemView);
            iconImageView = itemView.findViewById(R.id.achievement_icon);
            titleTextView = itemView.findViewById(R.id.achievement_title);
            descriptionTextView = itemView.findViewById(R.id.achievement_description);
            statusTextView = itemView.findViewById(R.id.achievement_status);
            progressBar = itemView.findViewById(R.id.achievement_progress_bar);
            progressTextView = itemView.findViewById(R.id.achievement_progress_text);
            pointsTextView = itemView.findViewById(R.id.achievement_points);
            achievementCardView = itemView.findViewById(R.id.achievement_card);
        }
        
        public void bind(Achievement achievement, OnAchievementClickListener listener) {
            Context context = itemView.getContext();
            
            // Set achievement icon
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
            if (achievement.isHidden() && achievement.getStatus() == Achievement.STATUS_LOCKED) {
                titleTextView.setText(R.string.hidden_achievement);
                descriptionTextView.setText(R.string.hidden_achievement_description);
            } else {
                titleTextView.setText(achievement.getTitle());
                descriptionTextView.setText(achievement.getDescription());
            }
            
            // Set status text
            if (achievement.getStatus() == Achievement.STATUS_UNLOCKED) {
                String unlockDate = dateFormat.format(new Date(achievement.getUnlockedTimestamp()));
                statusTextView.setText(context.getString(R.string.achievement_unlocked_on, unlockDate));
                statusTextView.setVisibility(View.VISIBLE);
            } else {
                statusTextView.setVisibility(View.GONE);
            }
            
            // Set progress
            progressBar.setMax(achievement.getProgressTarget());
            progressBar.setProgress(achievement.getProgressCurrent());
            
            if (achievement.getStatus() == Achievement.STATUS_UNLOCKED) {
                progressTextView.setText(context.getString(R.string.achievement_completed));
            } else {
                progressTextView.setText(context.getString(
                    R.string.achievement_progress_format,
                    achievement.getProgressCurrent(),
                    achievement.getProgressTarget()
                ));
            }
            
            // Set points
            pointsTextView.setText(context.getString(R.string.achievement_points_format, achievement.getPoints()));
            
            // Set click listener
            itemView.setOnClickListener(v -> {
                // Apply a scale animation when clicked
                Animation scaleAnim = AnimationUtils.loadAnimation(context, R.anim.achievement_card_click);
                achievementCardView.startAnimation(scaleAnim);
                
                // Call the click listener
                listener.onAchievementClick(achievement);
            });
        }
    }
}