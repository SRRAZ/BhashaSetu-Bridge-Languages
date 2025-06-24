package com.bhashasetu.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bhashasetu.app.R;
import com.bhashasetu.app.model.FlashcardDeck;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FlashcardDeckAdapter extends ListAdapter<FlashcardDeck, FlashcardDeckAdapter.FlashcardDeckHolder> {
    
    private OnItemClickListener listener;
    private OnFavoriteClickListener favoriteListener;
    private boolean useHindiTitles;
    
    public FlashcardDeckAdapter() {
        super(DIFF_CALLBACK);
        this.useHindiTitles = false;
    }
    
    private static final DiffUtil.ItemCallback<FlashcardDeck> DIFF_CALLBACK = new DiffUtil.ItemCallback<FlashcardDeck>() {
        @Override
        public boolean areItemsTheSame(@NonNull FlashcardDeck oldItem, @NonNull FlashcardDeck newItem) {
            return oldItem.getId() == newItem.getId();
        }
        
        @Override
        public boolean areContentsTheSame(@NonNull FlashcardDeck oldItem, @NonNull FlashcardDeck newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
                   oldItem.getNameHindi().equals(newItem.getNameHindi()) &&
                   oldItem.getCategory().equals(newItem.getCategory()) &&
                   oldItem.isFavorite() == newItem.isFavorite() &&
                   oldItem.getLastPracticed() == newItem.getLastPracticed();
        }
    };
    
    @NonNull
    @Override
    public FlashcardDeckHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.flashcard_deck_item, parent, false);
        return new FlashcardDeckHolder(itemView);
    }
    
    @Override
    public void onBindViewHolder(@NonNull FlashcardDeckHolder holder, int position) {
        FlashcardDeck currentDeck = getItem(position);
        
        // Set the primary title based on language preference
        String primaryTitle = useHindiTitles ? currentDeck.getNameHindi() : currentDeck.getName();
        String secondaryTitle = useHindiTitles ? currentDeck.getName() : currentDeck.getNameHindi();
        
        holder.textViewTitle.setText(primaryTitle);
        holder.textViewTitleSecondary.setText(secondaryTitle);
        
        // Set the description based on language preference
        String description = useHindiTitles ? 
                currentDeck.getDescriptionHindi() : 
                currentDeck.getDescription();
        holder.textViewDescription.setText(description);
        
        // Set category and word count
        holder.textViewCategory.setText(currentDeck.getCategory());
        holder.textViewCardCount.setText(String.valueOf(currentDeck.getWordCount()) + " cards");
        
        // Set last practiced date
        if (currentDeck.getLastPracticed() > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
            String lastPracticedDate = sdf.format(new Date(currentDeck.getLastPracticed()));
            holder.textViewLastPracticed.setText("Last practiced: " + lastPracticedDate);
            holder.textViewLastPracticed.setVisibility(View.VISIBLE);
        } else {
            holder.textViewLastPracticed.setVisibility(View.GONE);
        }
        
        // Set favorite status
        if (currentDeck.isFavorite()) {
            holder.buttonFavorite.setImageResource(R.drawable.ic_star_filled);
        } else {
            holder.buttonFavorite.setImageResource(R.drawable.ic_star_border);
        }
        
        // Set progress bar
        // In a real app, you would calculate this based on user progress
        // For now, we'll just set a random value
        int progress = (int) (Math.random() * 100);
        holder.progressBarMastery.setProgress(progress);
    }
    
    // Set whether to use Hindi titles as primary
    public void setUseHindiTitles(boolean useHindiTitles) {
        this.useHindiTitles = useHindiTitles;
        notifyDataSetChanged();
    }
    
    public FlashcardDeck getDeckAt(int position) {
        return getItem(position);
    }
    
    class FlashcardDeckHolder extends RecyclerView.ViewHolder {
        private final TextView textViewTitle;
        private final TextView textViewTitleSecondary;
        private final TextView textViewDescription;
        private final TextView textViewCategory;
        private final TextView textViewCardCount;
        private final TextView textViewLastPracticed;
        private final ImageButton buttonFavorite;
        private final ProgressBar progressBarMastery;
        
        public FlashcardDeckHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_deck_title);
            textViewTitleSecondary = itemView.findViewById(R.id.text_view_deck_title_secondary);
            textViewDescription = itemView.findViewById(R.id.text_view_deck_description);
            textViewCategory = itemView.findViewById(R.id.text_view_deck_category);
            textViewCardCount = itemView.findViewById(R.id.text_view_card_count);
            textViewLastPracticed = itemView.findViewById(R.id.text_view_last_practiced);
            buttonFavorite = itemView.findViewById(R.id.button_favorite_deck);
            progressBarMastery = itemView.findViewById(R.id.progress_bar_mastery);
            
            // Set up click listeners
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
            
            buttonFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (favoriteListener != null && position != RecyclerView.NO_POSITION) {
                        favoriteListener.onFavoriteClick(getItem(position));
                    }
                }
            });
        }
    }
    
    public interface OnItemClickListener {
        void onItemClick(FlashcardDeck deck);
    }
    
    public interface OnFavoriteClickListener {
        void onFavoriteClick(FlashcardDeck deck);
    }
    
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    
    public void setOnFavoriteClickListener(OnFavoriteClickListener listener) {
        this.favoriteListener = listener;
    }
}