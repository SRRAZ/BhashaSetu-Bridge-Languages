package com.example.englishhindi.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.englishhindi.R;
import com.example.englishhindi.model.AppSettings;
import com.example.englishhindi.model.Word;
import com.example.englishhindi.view.BilingualTextView;

public class WordAdapter extends ListAdapter<Word, WordAdapter.WordViewHolder> {
    
    private OnItemClickListener listener;
    
    public WordAdapter() {
        super(DIFF_CALLBACK);
    }
    
    private static final DiffUtil.ItemCallback<Word> DIFF_CALLBACK = new DiffUtil.ItemCallback<Word>() {
        @Override
        public boolean areItemsTheSame(@NonNull Word oldItem, @NonNull Word newItem) {
            return oldItem.getId() == newItem.getId();
        }
        
        @Override
        public boolean areContentsTheSame(@NonNull Word oldItem, @NonNull Word newItem) {
            return oldItem.getEnglishWord().equals(newItem.getEnglishWord()) &&
                   oldItem.getHindiWord().equals(newItem.getHindiWord()) &&
                   oldItem.isFavorite() == newItem.isFavorite() &&
                   oldItem.getCategory().equals(newItem.getCategory()) &&
                   oldItem.getMasteryLevel() == newItem.getMasteryLevel();
        }
    };
    
    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.word_item, parent, false);
        return new WordViewHolder(itemView);
    }
    
    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        Word currentWord = getItem(position);
        
        // Set bilingual text
        holder.bilingualTextView.setBilingualText(
                currentWord.getEnglishWord(),
                currentWord.getHindiWord()
        );
        
        // Set language emphasis based on app settings
        boolean isHindiMode = AppSettings.getInstance(holder.itemView.getContext()).useHindiInterface();
        holder.bilingualTextView.setLanguageEmphasis(isHindiMode);
        
        // Set category
        holder.textViewCategory.setText(currentWord.getCategory());
        
        // Set mastery level
        String masteryText;
        int masteryLevel = currentWord.getMasteryLevel();
        
        if (masteryLevel <= 1) {
            masteryText = holder.itemView.getContext().getString(R.string.review_due);
            holder.textViewMastery.setBackgroundResource(android.R.color.holo_red_light);
        } else if (masteryLevel <= 3) {
            masteryText = holder.itemView.getContext().getString(R.string.review_soon);
            holder.textViewMastery.setBackgroundResource(android.R.color.holo_orange_light);
        } else {
            masteryText = holder.itemView.getContext().getString(R.string.well_known);
            holder.textViewMastery.setBackgroundResource(android.R.color.holo_green_light);
        }
        
        holder.textViewMastery.setText(masteryText);
        
        // Set favorite status
        holder.imageViewFavorite.setVisibility(currentWord.isFavorite() ? View.VISIBLE : View.INVISIBLE);
    }
    
    public Word getWordAt(int position) {
        return getItem(position);
    }
    
    class WordViewHolder extends RecyclerView.ViewHolder {
        private BilingualTextView bilingualTextView;
        private TextView textViewCategory;
        private TextView textViewMastery;
        private ImageView imageViewFavorite;
        
        public WordViewHolder(@NonNull View itemView) {
            super(itemView);
            bilingualTextView = itemView.findViewById(R.id.bilingual_text_view);
            textViewCategory = itemView.findViewById(R.id.text_view_category);
            textViewMastery = itemView.findViewById(R.id.text_view_mastery);
            imageViewFavorite = itemView.findViewById(R.id.image_view_favorite);
            
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }
    
    public interface OnItemClickListener {
        void onItemClick(Word word);
    }
    
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}