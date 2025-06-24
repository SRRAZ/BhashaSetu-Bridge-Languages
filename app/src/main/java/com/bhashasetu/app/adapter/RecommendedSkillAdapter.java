package com.bhashasetu.app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bhashasetu.app.R;
import com.bhashasetu.app.manager.UserProgressManager;
import com.bhashasetu.app.model.DifficultyManager;
import com.bhashasetu.app.model.UserProgress;

import java.util.List;
import java.util.Map;

/**
 * Adapter for displaying recommended skills in a RecyclerView.
 * Shows skill importance, progress, and description.
 */
public class RecommendedSkillAdapter extends RecyclerView.Adapter<RecommendedSkillAdapter.SkillViewHolder> {

    private final Context context;
    private List<Pair<String, Double>> skills;
    private final UserProgressManager progressManager;
    private final UserProgress userProgress;
    
    private OnSkillClickListener listener;

    public RecommendedSkillAdapter(Context context, List<Pair<String, Double>> skills) {
        this.context = context;
        this.skills = skills;
        this.progressManager = UserProgressManager.getInstance(context);
        this.userProgress = progressManager.getCachedProgress();
    }

    @NonNull
    @Override
    public SkillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recommended_skill, parent, false);
        return new SkillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SkillViewHolder holder, int position) {
        Pair<String, Double> skillPair = skills.get(position);
        String skillId = skillPair.first;
        double importance = skillPair.second;
        
        // Set skill name and icon
        holder.setupSkill(skillId, importance);
        
        // Set progress if available
        if (userProgress != null) {
            Map<String, Integer> skillProgress = userProgress.getSkillProgress();
            int progress = skillProgress.getOrDefault(skillId, 0);
            holder.progressSkill.setProgress(progress);
        } else {
            holder.progressSkill.setProgress(0);
        }
        
        // Set click listener
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onSkillClick(skillId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return skills.size();
    }
    
    /**
     * Update the list of recommended skills
     * @param newSkills List of skill ID and importance pairs
     */
    public void updateSkills(List<Pair<String, Double>> newSkills) {
        this.skills = newSkills;
        notifyDataSetChanged();
    }
    
    /**
     * Set listener for skill item clicks
     * @param listener The listener to set
     */
    public void setOnSkillClickListener(OnSkillClickListener listener) {
        this.listener = listener;
    }
    
    /**
     * ViewHolder for skill items
     */
    class SkillViewHolder extends RecyclerView.ViewHolder {
        
        ImageView ivSkillIcon;
        TextView tvSkillName;
        TextView tvSkillDescription;
        TextView tvSkillImportance;
        ProgressBar progressSkill;
        
        public SkillViewHolder(@NonNull View itemView) {
            super(itemView);
            
            ivSkillIcon = itemView.findViewById(R.id.iv_skill_icon);
            tvSkillName = itemView.findViewById(R.id.tv_skill_name);
            tvSkillDescription = itemView.findViewById(R.id.tv_skill_description);
            tvSkillImportance = itemView.findViewById(R.id.tv_skill_importance);
            progressSkill = itemView.findViewById(R.id.progress_skill);
        }
        
        /**
         * Set up the skill information
         * @param skillId Skill identifier
         * @param importance Importance score (0.0-1.0)
         */
        public void setupSkill(String skillId, double importance) {
            // Set name and icon based on skill ID
            switch (skillId) {
                case DifficultyManager.SKILL_VOCABULARY:
                    tvSkillName.setText(R.string.vocabulary);
                    ivSkillIcon.setImageResource(R.drawable.ic_skill_vocabulary);
                    progressSkill.setProgressTintList(ContextCompat.getColorStateList(context, R.color.skill_vocabulary));
                    break;
                    
                case DifficultyManager.SKILL_PRONUNCIATION:
                    tvSkillName.setText(R.string.pronunciation);
                    ivSkillIcon.setImageResource(R.drawable.ic_skill_pronunciation);
                    progressSkill.setProgressTintList(ContextCompat.getColorStateList(context, R.color.skill_pronunciation));
                    break;
                    
                case DifficultyManager.SKILL_GRAMMAR:
                    tvSkillName.setText(R.string.grammar);
                    ivSkillIcon.setImageResource(R.drawable.ic_skill_grammar);
                    progressSkill.setProgressTintList(ContextCompat.getColorStateList(context, R.color.skill_grammar));
                    break;
                    
                case DifficultyManager.SKILL_LISTENING:
                    tvSkillName.setText(R.string.listening);
                    ivSkillIcon.setImageResource(R.drawable.ic_skill_listening);
                    progressSkill.setProgressTintList(ContextCompat.getColorStateList(context, R.color.skill_listening));
                    break;
                    
                case DifficultyManager.SKILL_READING:
                    tvSkillName.setText(R.string.reading);
                    ivSkillIcon.setImageResource(R.drawable.ic_skill_reading);
                    progressSkill.setProgressTintList(ContextCompat.getColorStateList(context, R.color.skill_reading));
                    break;
                    
                case DifficultyManager.SKILL_WRITING:
                    tvSkillName.setText(R.string.writing);
                    ivSkillIcon.setImageResource(R.drawable.ic_skill_writing);
                    progressSkill.setProgressTintList(ContextCompat.getColorStateList(context, R.color.skill_writing));
                    break;
            }
            
            // Set description based on skill and importance
            setSkillDescription(skillId, importance);
            
            // Set importance tag
            if (importance >= 0.8) {
                tvSkillImportance.setText(R.string.high_priority);
                tvSkillImportance.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.importance_high));
            } else if (importance >= 0.5) {
                tvSkillImportance.setText(R.string.medium_priority);
                tvSkillImportance.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.importance_medium));
            } else {
                tvSkillImportance.setText(R.string.low_priority);
                tvSkillImportance.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.importance_low));
            }
        }
        
        /**
         * Set the skill description based on skill type and importance
         * @param skillId Skill identifier
         * @param importance Importance score
         */
        private void setSkillDescription(String skillId, double importance) {
            int descriptionResId;
            
            // Select description based on skill and importance
            if (importance >= 0.8) {
                // High importance descriptions
                switch (skillId) {
                    case DifficultyManager.SKILL_VOCABULARY:
                        descriptionResId = R.string.vocabulary_high_importance;
                        break;
                    case DifficultyManager.SKILL_PRONUNCIATION:
                        descriptionResId = R.string.pronunciation_high_importance;
                        break;
                    case DifficultyManager.SKILL_GRAMMAR:
                        descriptionResId = R.string.grammar_high_importance;
                        break;
                    case DifficultyManager.SKILL_LISTENING:
                        descriptionResId = R.string.listening_high_importance;
                        break;
                    case DifficultyManager.SKILL_READING:
                        descriptionResId = R.string.reading_high_importance;
                        break;
                    case DifficultyManager.SKILL_WRITING:
                        descriptionResId = R.string.writing_high_importance;
                        break;
                    default:
                        descriptionResId = R.string.skill_general_description;
                }
            } else if (importance >= 0.5) {
                // Medium importance descriptions
                switch (skillId) {
                    case DifficultyManager.SKILL_VOCABULARY:
                        descriptionResId = R.string.vocabulary_medium_importance;
                        break;
                    case DifficultyManager.SKILL_PRONUNCIATION:
                        descriptionResId = R.string.pronunciation_medium_importance;
                        break;
                    case DifficultyManager.SKILL_GRAMMAR:
                        descriptionResId = R.string.grammar_medium_importance;
                        break;
                    case DifficultyManager.SKILL_LISTENING:
                        descriptionResId = R.string.listening_medium_importance;
                        break;
                    case DifficultyManager.SKILL_READING:
                        descriptionResId = R.string.reading_medium_importance;
                        break;
                    case DifficultyManager.SKILL_WRITING:
                        descriptionResId = R.string.writing_medium_importance;
                        break;
                    default:
                        descriptionResId = R.string.skill_general_description;
                }
            } else {
                // Low importance descriptions
                switch (skillId) {
                    case DifficultyManager.SKILL_VOCABULARY:
                        descriptionResId = R.string.vocabulary_low_importance;
                        break;
                    case DifficultyManager.SKILL_PRONUNCIATION:
                        descriptionResId = R.string.pronunciation_low_importance;
                        break;
                    case DifficultyManager.SKILL_GRAMMAR:
                        descriptionResId = R.string.grammar_low_importance;
                        break;
                    case DifficultyManager.SKILL_LISTENING:
                        descriptionResId = R.string.listening_low_importance;
                        break;
                    case DifficultyManager.SKILL_READING:
                        descriptionResId = R.string.reading_low_importance;
                        break;
                    case DifficultyManager.SKILL_WRITING:
                        descriptionResId = R.string.writing_low_importance;
                        break;
                    default:
                        descriptionResId = R.string.skill_general_description;
                }
            }
            
            tvSkillDescription.setText(descriptionResId);
        }
    }
    
    /**
     * Interface for handling skill clicks
     */
    public interface OnSkillClickListener {
        void onSkillClick(String skillId);
    }
}