package com.bhashasetu.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bhashasetu.app.R;
import com.bhashasetu.app.model.GameCategory;

import java.util.List;

/**
 * Adapter for displaying game categories in a grid.
 */
public class GameCategoryAdapter extends RecyclerView.Adapter<GameCategoryAdapter.GameViewHolder> {

    private Context context;
    private List<GameCategory> gameCategories;
    private OnGameSelectedListener listener;

    public GameCategoryAdapter(Context context, List<GameCategory> gameCategories, OnGameSelectedListener listener) {
        this.context = context;
        this.gameCategories = gameCategories;
        this.listener = listener;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_game_category, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        GameCategory gameCategory = gameCategories.get(position);
        
        // Set icon and text
        holder.imageViewIcon.setImageResource(gameCategory.getIconResId());
        holder.textViewTitle.setText(gameCategory.getTitle());
        holder.textViewDescription.setText(gameCategory.getDescription());
        
        // Set click listener
        holder.cardView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onGameSelected(gameCategory);
            }
        });
    }

    @Override
    public int getItemCount() {
        return gameCategories.size();
    }

    /**
     * ViewHolder for game category items.
     */
    static class GameViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageViewIcon;
        TextView textViewTitle;
        TextView textViewDescription;
        
        public GameViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            imageViewIcon = itemView.findViewById(R.id.image_view_icon);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
        }
    }

    /**
     * Interface for game selection callback.
     */
    public interface OnGameSelectedListener {
        void onGameSelected(GameCategory gameCategory);
    }
}