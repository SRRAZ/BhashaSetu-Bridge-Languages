package com.example.englishhindi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.englishhindi.R;
import com.example.englishhindi.model.Achievement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Adapter for displaying achievements in a RecyclerView.
 */
public class AchievementAdapter extends RecyclerView.Adapter<AchievementAdapter.AchievementViewHolder> {

    private Context context;
    private List<Achievement> achievements;
    private OnAchievementClickListener listener;
    private boolean showSecret = false;

    /**
     * Constructor.
     * 
     * @param context The context
     * @param achievements The list of achievements
     * @param listener The click listener
     */
    public AchievementAdapter(Context context, List<Achievement> achievements, OnAchievementClickListener listener) {
        this.context = context;
        this.achievements = achievements;
        this.listener = listener;
    }

    /**
     * Set whether to show secret achievements.
     * 
     * @param showSecret True to show secret achievements, false to hide them
     */
    public void setShowSecret(boolean showSecret) {
        this.showSecret = showSecret;
        notifyDataSetChanged();
    }

    /**
     * Update the achievements list.
     * 
     * @param achievements The new list of achievements
     */
    public void setAchievements(List<Achievement> achievements) {
        this.achievements = achievements;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AchievementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_achievement, parent, false);
        return new AchievementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AchievementViewHolder holder, int position) {
        Achievement achievement = achievements.get(position);
        
        // Handle secret achievements
        if (achievement.isSecret() && !achievement.isUnlocked() && !showSecret) {
            holder.textViewTitle.setText(R.string.secret_achievement);
            holder.textViewDescription.setText(R.string.secret_achievement_description);
            holder.imageViewIcon.setImageResource(R.drawable.achievement_locked);
            holder.progressBar.setVisibility(View.GONE);
            holder.textViewProgress.setVisibility(View.GONE);
            holder.textViewPoints.setText(context.getString(R.string.points_format, "?"));
            holder.textViewUnlockDate.setVisibility(View.GONE);
            holder.imageViewLockIcon.setVisibility(View.VISIBLE);
            return;
        }
        
        // Set achievement details
        holder.textViewTitle.setText(achievement.getTitle());
        holder.textViewDescription.setText(achievement.getDescription());
        
        // Set icon
        if (achievement.isUnlocked()) {
            holder.imageViewIcon.setImageResource(achievement.getIconResId());
            holder.imageViewLockIcon.setVisibility(View.GONE);
        } else {
            holder.imageViewIcon.setImageResource(achievement.getIconResId());
            holder.imageViewIcon.setAlpha(0.5f);
            holder.imageViewLockIcon.setVisibility(View.VISIBLE);
        }
        
        // Set progress
        int progressPercentage = achievement.getProgressPercentage();
        holder.progressBar.setProgress(progressPercentage);
        
        if (achievement.isUnlocked()) {
            holder.textViewProgress.setText(context.getString(R.string.progress_complete));
            holder.progressBar.setProgressTintList(context.getColorStateList(R.color.colorCorrect));
        } else {
            holder.textViewProgress.setText(context.getString(
                    R.string.progress_format, 
                    achievement.getProgressCurrent(), 
                    achievement.getProgressTarget()));
        }
        
        // Set points
        holder.textViewPoints.setText(context.getString(
                R.string.points_format, String.valueOf(achievement.getPointsValue())));
        
        // Set unlock date if applicable
        if (achievement.isUnlocked()) {
            holder.textViewUnlockDate.setVisibility(View.VISIBLE);
            SimpleDateFormat format = new SimpleDateFormat("MMM d, yyyy", Locale.getDefault());
            String dateStr = format.format(new Date(achievement.getDateUnlocked()));
            holder.textViewUnlockDate.setText(context.getString(R.string.unlocked_on, dateStr));
        } else {
            holder.textViewUnlockDate.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return achievements != null ? achievements.size() : 0;
    }

    /**
     * ViewHolder for achievement items.
     */
    class AchievementViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        
        ImageView imageViewIcon;
        ImageView imageViewLockIcon;
        TextView textViewTitle;
        TextView textViewDescription;
        ProgressBar progressBar;
        TextView textViewProgress;
        TextView textViewPoints;
        TextView textViewUnlockDate;
        
        public AchievementViewHolder(@NonNull View itemView) {
            super(itemView);
            
            imageViewIcon = itemView.findViewById(R.id.image_view_achievement_icon);
            imageViewLockIcon = itemView.findViewById(R.id.image_view_lock_icon);
            textViewTitle = itemView.findViewById(R.id.text_view_achievement_title);
            textViewDescription = itemView.findViewById(R.id.text_view_achievement_description);
            progressBar = itemView.findViewById(R.id.progress_bar_achievement);
            textViewProgress = itemView.findViewById(R.id.text_view_achievement_progress);
            textViewPoints = itemView.findViewById(R.id.text_view_achievement_points);
            textViewUnlockDate = itemView.findViewById(R.id.text_view_achievement_date);
            
            itemView.setOnClickListener(this);
        }
        
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && listener != null) {
                listener.onAchievementClick(achievements.get(position));
            }
        }
    }

    /**
     * Interface for achievement click events.
     */
    public interface OnAchievementClickListener {
        void onAchievementClick(Achievement achievement);
    }
}