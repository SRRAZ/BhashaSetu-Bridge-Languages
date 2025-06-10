package com.bhashasetu.app.pronunciation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.englishhindi.R;
import com.example.englishhindi.model.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for displaying words in a RecyclerView for pronunciation practice selection.
 */
public class PronunciationWordAdapter extends RecyclerView.Adapter<PronunciationWordAdapter.WordViewHolder> {

    private Context context;
    private List<Word> words;
    private List<Word> selectedWords;
    private OnPronunciationItemClickListener listener;

    public PronunciationWordAdapter(Context context, List<Word> words, OnPronunciationItemClickListener listener) {
        this.context = context;
        this.words = words;
        this.selectedWords = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pronunciation_word, parent, false);
        return new WordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        Word word = words.get(position);
        
        // Set the English and Hindi words
        holder.tvEnglishWord.setText(word.getEnglishWord());
        holder.tvHindiWord.setText(word.getHindiWord());
        
        // Set difficulty indicator
        setDifficultyIndicator(holder, word.getDifficulty());
        
        // Set previous score if available
        setPreviousScore(holder, word);
        
        // Set checkbox state
        holder.checkboxSelect.setChecked(selectedWords.contains(word));
        
        // Set click listeners
        holder.itemView.setOnClickListener(v -> {
            boolean isSelected = !holder.checkboxSelect.isChecked();
            holder.checkboxSelect.setChecked(isSelected);
            updateSelectedWords(word, isSelected);
            if (listener != null) {
                listener.onPronunciationItemClicked(word, isSelected);
            }
        });
        
        holder.checkboxSelect.setOnClickListener(v -> {
            boolean isSelected = holder.checkboxSelect.isChecked();
            updateSelectedWords(word, isSelected);
            if (listener != null) {
                listener.onPronunciationItemClicked(word, isSelected);
            }
        });
        
        holder.btnPlayWord.setOnClickListener(v -> {
            if (listener != null) {
                listener.onPronunciationItemPlayClicked(word);
            }
        });
    }
    
    private void setDifficultyIndicator(WordViewHolder holder, int difficulty) {
        // Set color and text based on difficulty level
        int colorResId;
        String difficultyText;
        
        switch (difficulty) {
            case 1:
                colorResId = R.color.difficulty_easy;
                difficultyText = context.getString(R.string.difficulty_easy);
                break;
            case 2:
                colorResId = R.color.difficulty_medium;
                difficultyText = context.getString(R.string.difficulty_medium);
                break;
            case 3:
                colorResId = R.color.difficulty_hard;
                difficultyText = context.getString(R.string.difficulty_hard);
                break;
            default:
                colorResId = R.color.difficulty_easy;
                difficultyText = context.getString(R.string.difficulty_easy);
        }
        
        holder.tvDifficulty.setText(difficultyText);
        holder.tvDifficulty.setTextColor(context.getResources().getColor(colorResId));
    }
    
    private void setPreviousScore(WordViewHolder holder, Word word) {
        // In a real app, we'd look up the previous score for this word
        // For now, just hide the previous score view
        holder.tvPreviousScore.setVisibility(View.GONE);
        
        // Example of how to show a previous score
        // holder.tvPreviousScore.setText(context.getString(R.string.previous_score, 75));
        // holder.tvPreviousScore.setVisibility(View.VISIBLE);
    }
    
    private void updateSelectedWords(Word word, boolean isSelected) {
        if (isSelected) {
            if (!selectedWords.contains(word)) {
                selectedWords.add(word);
            }
        } else {
            selectedWords.remove(word);
        }
    }

    @Override
    public int getItemCount() {
        return words.size();
    }
    
    public void updateWords(List<Word> newWords) {
        this.words = newWords;
        notifyDataSetChanged();
    }
    
    public List<Word> getSelectedWords() {
        return selectedWords;
    }

    /**
     * ViewHolder for a pronunciation word item
     */
    class WordViewHolder extends RecyclerView.ViewHolder {
        
        TextView tvEnglishWord;
        TextView tvHindiWord;
        TextView tvDifficulty;
        TextView tvPreviousScore;
        CheckBox checkboxSelect;
        ImageButton btnPlayWord;

        public WordViewHolder(@NonNull View itemView) {
            super(itemView);
            
            tvEnglishWord = itemView.findViewById(R.id.tv_english_word);
            tvHindiWord = itemView.findViewById(R.id.tv_hindi_word);
            tvDifficulty = itemView.findViewById(R.id.tv_difficulty);
            tvPreviousScore = itemView.findViewById(R.id.tv_previous_score);
            checkboxSelect = itemView.findViewById(R.id.checkbox_select);
            btnPlayWord = itemView.findViewById(R.id.btn_play_word);
        }
    }
    
    /**
     * Interface for click events on pronunciation items
     */
    public interface OnPronunciationItemClickListener {
        void onPronunciationItemClicked(Word word, boolean isSelected);
        void onPronunciationItemPlayClicked(Word word);
    }
}