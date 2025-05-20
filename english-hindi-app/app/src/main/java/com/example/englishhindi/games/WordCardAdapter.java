package com.example.englishhindi.games;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.englishhindi.R;

import java.util.List;

/**
 * Adapter for the Word Association game's card grid.
 */
public class WordCardAdapter extends RecyclerView.Adapter<WordCardAdapter.WordCardViewHolder> {

    private Context context;
    private List<WordAssociationActivity.WordCard> cards;
    private OnWordCardClickListener listener;

    /**
     * Interface for card click events.
     */
    public interface OnWordCardClickListener {
        void onWordCardClick(int position);
    }

    /**
     * Constructor for the adapter.
     *
     * @param context The context
     * @param cards   The list of word cards
     * @param listener The click listener
     */
    public WordCardAdapter(Context context, List<WordAssociationActivity.WordCard> cards, OnWordCardClickListener listener) {
        this.context = context;
        this.cards = cards;
        this.listener = listener;
    }

    @NonNull
    @Override
    public WordCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_word_card, parent, false);
        return new WordCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordCardViewHolder holder, int position) {
        WordAssociationActivity.WordCard card = cards.get(position);
        card.setPosition(position);
        
        // Set card text if it's selected or already matched
        if (card.isSelected() || card.isMatched()) {
            holder.textViewCard.setText(card.getText());
        } else {
            holder.textViewCard.setText("?");
        }
        
        // Set card appearance based on state
        if (card.isMatched()) {
            // Matched card style
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorCorrect));
            holder.textViewCard.setTextColor(context.getResources().getColor(android.R.color.white));
            holder.cardView.setClickable(false);
        } else if (card.isSelected()) {
            // Selected card style
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorAccent));
            holder.textViewCard.setTextColor(context.getResources().getColor(android.R.color.white));
        } else {
            // Default card style
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(android.R.color.white));
            holder.textViewCard.setTextColor(context.getResources().getColor(android.R.color.black));
        }
        
        // Set different background for English vs Hindi cards
        if (card.getCardType() == WordAssociationActivity.WordCard.CardType.ENGLISH) {
            holder.cardView.setStrokeColor(context.getResources().getColor(R.color.colorPrimary));
            if (!card.isSelected() && !card.isMatched()) {
                holder.textViewCard.setText("E?");
            }
        } else {
            holder.cardView.setStrokeColor(context.getResources().getColor(R.color.colorAccent));
            if (!card.isSelected() && !card.isMatched()) {
                holder.textViewCard.setText("H?");
            }
        }
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    /**
     * ViewHolder for the word cards.
     */
    class WordCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        
        CardView cardView;
        TextView textViewCard;
        
        public WordCardViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            textViewCard = itemView.findViewById(R.id.text_view_card);
            
            itemView.setOnClickListener(this);
        }
        
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && listener != null) {
                listener.onWordCardClick(position);
            }
        }
    }
}