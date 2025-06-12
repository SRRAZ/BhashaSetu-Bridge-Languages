package com.bhashasetu.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bhashasetu.app.R;
import com.bhashasetu.app.model.PracticeSession;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PracticeSessionAdapter extends ListAdapter<PracticeSession, PracticeSessionAdapter.PracticeSessionHolder> {
    
    private OnItemClickListener listener;
    
    public PracticeSessionAdapter() {
        super(DIFF_CALLBACK);
    }
    
    private static final DiffUtil.ItemCallback<PracticeSession> DIFF_CALLBACK = new DiffUtil.ItemCallback<PracticeSession>() {
        @Override
        public boolean areItemsTheSame(@NonNull PracticeSession oldItem, @NonNull PracticeSession newItem) {
            return oldItem.getId() == newItem.getId();
        }
        
        @Override
        public boolean areContentsTheSame(@NonNull PracticeSession oldItem, @NonNull PracticeSession newItem) {
            return oldItem.getStartTime() == newItem.getStartTime() &&
                   oldItem.getEndTime() == newItem.getEndTime() &&
                   oldItem.getSessionType().equals(newItem.getSessionType()) &&
                   oldItem.getCorrectAnswers() == newItem.getCorrectAnswers() &&
                   oldItem.getTotalItems() == newItem.getTotalItems() &&
                   oldItem.isCompleted() == newItem.isCompleted();
        }
    };
    
    @NonNull
    @Override
    public PracticeSessionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.practice_session_item, parent, false);
        return new PracticeSessionHolder(itemView);
    }
    
    @Override
    public void onBindViewHolder(@NonNull PracticeSessionHolder holder, int position) {
        PracticeSession currentSession = getItem(position);
        
        // Format the session date
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy - hh:mm a", Locale.getDefault());
        String sessionDate = sdf.format(new Date(currentSession.getStartTime()));
        holder.textViewSessionDate.setText(sessionDate);
        
        // Format the session type
        String sessionType = currentSession.getSessionType();
        String formattedType = sessionType.substring(0, 1).toUpperCase() + sessionType.substring(1) + " Practice";
        holder.textViewSessionType.setText(formattedType);
        
        // Set progress metrics
        if (currentSession.isCompleted()) {
            // For completed sessions, show the score and accuracy
            int score = currentSession.getScore();
            float accuracy = currentSession.getAccuracy();
            String scoreText = "Score: " + score + " | Accuracy: " + String.format("%.1f", accuracy) + "%";
            holder.textViewSessionStats.setText(scoreText);
            
            // Show duration for completed sessions
            String duration = "Duration: " + currentSession.getDurationFormatted();
            holder.textViewSessionDuration.setText(duration);
            holder.textViewSessionDuration.setVisibility(View.VISIBLE);
        } else {
            // For incomplete sessions, show progress
            String progressText = "In Progress | " + currentSession.getCurrentIndex() + 
                                 " of " + currentSession.getTotalItems() + " items";
            holder.textViewSessionStats.setText(progressText);
            holder.textViewSessionDuration.setVisibility(View.GONE);
        }
        
        // Set completion status indicator
        if (currentSession.isCompleted()) {
            holder.textViewSessionStatus.setText("Completed");
            holder.textViewSessionStatus.setBackgroundResource(R.drawable.badge_completed);
        } else {
            holder.textViewSessionStatus.setText("In Progress");
            holder.textViewSessionStatus.setBackgroundResource(R.drawable.badge_in_progress);
        }
    }
    
    public PracticeSession getSessionAt(int position) {
        return getItem(position);
    }
    
    class PracticeSessionHolder extends RecyclerView.ViewHolder {
        private TextView textViewSessionDate;
        private TextView textViewSessionType;
        private TextView textViewSessionStats;
        private TextView textViewSessionDuration;
        private TextView textViewSessionStatus;
        
        public PracticeSessionHolder(@NonNull View itemView) {
            super(itemView);
            textViewSessionDate = itemView.findViewById(R.id.text_view_session_date);
            textViewSessionType = itemView.findViewById(R.id.text_view_session_type);
            textViewSessionStats = itemView.findViewById(R.id.text_view_session_stats);
            textViewSessionDuration = itemView.findViewById(R.id.text_view_session_duration);
            textViewSessionStatus = itemView.findViewById(R.id.text_view_session_status);
            
            // Set up click listener
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
        void onItemClick(PracticeSession session);
    }
    
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}