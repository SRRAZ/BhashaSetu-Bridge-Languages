package com.bhashasetu.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bhashasetu.app.R;
import com.bhashasetu.app.model.DifficultyLevel;
import com.bhashasetu.app.model.Word;

import java.util.List;

/**
 * Adapter for displaying recommended words in a RecyclerView.
 * Shows word details, difficulty, and provides a button to practice.
 */
public class RecommendedWordAdapter extends RecyclerView.Adapter<RecommendedWordAdapter.WordViewHolder> {

    private Context context;
    private List<Word> words;
    private OnWordClickListener listener;

    public RecommendedWordAdapter(Context context, List<Word> words) {
        this.context = context;
        this.words = words;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recommended_word, parent, false);
        return new WordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        Word word = words.get(position);
        
        // Set word text
        holder.tvEnglishWord.setText(word.getEnglishWord());
        holder.tvHindiWord.setText(word.getHindiWord());
        
        // Set difficulty
        DifficultyLevel difficultyLevel = DifficultyLevel.fromLevel(word.getDifficulty());
        holder.tvDifficulty.setText(difficultyLevel.getName());
        
        // Set color based on difficulty
        int colorResId;
        switch (word.getDifficulty()) {
            case 1:
                colorResId = R.color.difficulty_beginner;
                break;
            case 2:
                colorResId = R.color.difficulty_elementary;
                break;
            case 3:
                colorResId = R.color.difficulty_intermediate;
                break;
            case 4:
                colorResId = R.color.difficulty_advanced;
                break;
            case 5:
                colorResId = R.color.difficulty_expert;
                break;
            default:
                colorResId = R.color.difficulty_beginner;
        }
        holder.tvDifficulty.setTextColor(context.getResources().getColor(colorResId));
        
        // Set recommendation reason
        holder.tvRecommendationReason.setText(getRecommendationReason(word));
        
        // Set button click listener
        holder.btnPracticeWord.setOnClickListener(v -> {
            if (listener != null) {
                listener.onWordClick(word);
            }
        });
    }

    @Override
    public int getItemCount() {
        return words.size();
    }
    
    /**
     * Update the list of recommended words
     * @param newWords List of words to display
     */
    public void updateWords(List<Word> newWords) {
        this.words = newWords;
        notifyDataSetChanged();
    }
    
    /**
     * Set listener for word practice button clicks
     * @param listener The listener to set
     */
    public void setOnWordClickListener(OnWordClickListener listener) {
        this.listener = listener;
    }
    
    /**
     * Generate a recommendation reason for a word
     * @param word The word to generate a reason for
     * @return A reason string
     */
    private String getRecommendationReason(Word word) {
        // In a real app, this would be based on user data and word relationships
        // For now, use some simple rules based on word attributes
        
        if (word.getUsageFrequency() > 80) {
            return context.getString(R.string.recommendation_high_frequency);
        }
        
        if (word.getDifficulty() <= 2) {
            return context.getString(R.string.recommendation_beginner_friendly);
        }
        
        if (word.getDifficulty() >= 4) {
            return context.getString(R.string.recommendation_challenge);
        }
        
        // Default reason
        return context.getString(R.string.recommendation_builds_vocabulary);
    }
    
    /**
     * ViewHolder for word items
     */
    static class WordViewHolder extends RecyclerView.ViewHolder {
        
        TextView tvEnglishWord;
        TextView tvHindiWord;
        TextView tvDifficulty;
        TextView tvRecommendationReason;
        Button btnPracticeWord;
        
        public WordViewHolder(@NonNull View itemView) {
            super(itemView);
            
            tvEnglishWord = itemView.findViewById(R.id.tv_english_word);
            tvHindiWord = itemView.findViewById(R.id.tv_hindi_word);
            tvDifficulty = itemView.findViewById(R.id.tv_difficulty);
            tvRecommendationReason = itemView.findViewById(R.id.tv_recommendation_reason);
            btnPracticeWord = itemView.findViewById(R.id.btn_practice_word);
        }
    }
    
    /**
     * Interface for handling word clicks
     */
    public interface OnWordClickListener {
        void onWordClick(Word word);
    }
}