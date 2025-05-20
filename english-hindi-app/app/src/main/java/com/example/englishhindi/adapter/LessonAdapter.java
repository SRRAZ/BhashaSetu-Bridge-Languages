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
import com.example.englishhindi.model.Lesson;

public class LessonAdapter extends ListAdapter<Lesson, LessonAdapter.LessonHolder> {
    
    private OnItemClickListener listener;
    private boolean useHindiTitles;
    
    public LessonAdapter() {
        super(DIFF_CALLBACK);
        this.useHindiTitles = false;
    }
    
    private static final DiffUtil.ItemCallback<Lesson> DIFF_CALLBACK = new DiffUtil.ItemCallback<Lesson>() {
        @Override
        public boolean areItemsTheSame(@NonNull Lesson oldItem, @NonNull Lesson newItem) {
            return oldItem.getId() == newItem.getId();
        }
        
        @Override
        public boolean areContentsTheSame(@NonNull Lesson oldItem, @NonNull Lesson newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                   oldItem.getTitleHindi().equals(newItem.getTitleHindi()) &&
                   oldItem.getCategory().equals(newItem.getCategory()) &&
                   oldItem.getLevel().equals(newItem.getLevel()) &&
                   oldItem.isHasCompleted() == newItem.isHasCompleted();
        }
    };
    
    @NonNull
    @Override
    public LessonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lesson_item, parent, false);
        return new LessonHolder(itemView);
    }
    
    @Override
    public void onBindViewHolder(@NonNull LessonHolder holder, int position) {
        Lesson currentLesson = getItem(position);
        
        // Set the primary title based on language preference
        String primaryTitle = useHindiTitles ? currentLesson.getTitleHindi() : currentLesson.getTitle();
        String secondaryTitle = useHindiTitles ? currentLesson.getTitle() : currentLesson.getTitleHindi();
        
        holder.textViewTitle.setText(primaryTitle);
        holder.textViewTitleSecondary.setText(secondaryTitle);
        
        // Set the description based on language preference
        String description = useHindiTitles ? 
                currentLesson.getDescriptionHindi() : 
                currentLesson.getDescription();
        holder.textViewDescription.setText(description);
        
        // Set category and level indicators
        holder.textViewCategory.setText(currentLesson.getCategory());
        holder.textViewLevel.setText(currentLesson.getLevel());
        
        // Set completion status indicator
        if (currentLesson.isHasCompleted()) {
            holder.imageViewCompletionStatus.setImageResource(R.drawable.ic_check_circle);
            holder.imageViewCompletionStatus.setVisibility(View.VISIBLE);
        } else {
            holder.imageViewCompletionStatus.setVisibility(View.GONE);
        }
    }
    
    // Set whether to use Hindi titles as primary
    public void setUseHindiTitles(boolean useHindiTitles) {
        this.useHindiTitles = useHindiTitles;
        notifyDataSetChanged();
    }
    
    public Lesson getLessonAt(int position) {
        return getItem(position);
    }
    
    class LessonHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewTitleSecondary;
        private TextView textViewDescription;
        private TextView textViewCategory;
        private TextView textViewLevel;
        private ImageView imageViewCompletionStatus;
        
        public LessonHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_lesson_title);
            textViewTitleSecondary = itemView.findViewById(R.id.text_view_lesson_title_secondary);
            textViewDescription = itemView.findViewById(R.id.text_view_lesson_description);
            textViewCategory = itemView.findViewById(R.id.text_view_lesson_category);
            textViewLevel = itemView.findViewById(R.id.text_view_lesson_level);
            imageViewCompletionStatus = itemView.findViewById(R.id.image_view_lesson_completion);
            
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
        void onItemClick(Lesson lesson);
    }
    
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}