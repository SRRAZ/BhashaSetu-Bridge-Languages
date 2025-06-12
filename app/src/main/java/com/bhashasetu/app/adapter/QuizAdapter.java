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
import com.bhashasetu.app.model.Quiz;

public class QuizAdapter extends ListAdapter<Quiz, QuizAdapter.QuizHolder> {
    
    private OnItemClickListener listener;
    private boolean useHindiQuestions;
    
    public QuizAdapter() {
        super(DIFF_CALLBACK);
        this.useHindiQuestions = false;
    }
    
    private static final DiffUtil.ItemCallback<Quiz> DIFF_CALLBACK = new DiffUtil.ItemCallback<Quiz>() {
        @Override
        public boolean areItemsTheSame(@NonNull Quiz oldItem, @NonNull Quiz newItem) {
            return oldItem.getId() == newItem.getId();
        }
        
        @Override
        public boolean areContentsTheSame(@NonNull Quiz oldItem, @NonNull Quiz newItem) {
            return oldItem.getQuestion().equals(newItem.getQuestion()) &&
                   oldItem.getQuestionHindi().equals(newItem.getQuestionHindi()) &&
                   oldItem.getType().equals(newItem.getType()) &&
                   oldItem.getDifficulty() == newItem.getDifficulty();
        }
    };
    
    @NonNull
    @Override
    public QuizHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quiz_item, parent, false);
        return new QuizHolder(itemView);
    }
    
    @Override
    public void onBindViewHolder(@NonNull QuizHolder holder, int position) {
        Quiz currentQuiz = getItem(position);
        
        // Set the question based on language preference
        String primaryQuestion = useHindiQuestions ? currentQuiz.getQuestionHindi() : currentQuiz.getQuestion();
        String secondaryQuestion = useHindiQuestions ? currentQuiz.getQuestion() : currentQuiz.getQuestionHindi();
        
        holder.textViewQuestion.setText(primaryQuestion);
        holder.textViewQuestionSecondary.setText(secondaryQuestion);
        
        // Set quiz type
        String quizType = currentQuiz.getType();
        String formattedType = quizType.substring(0, 1).toUpperCase() + quizType.substring(1).replace("_", " ");
        holder.textViewQuizType.setText(formattedType);
        
        // Set difficulty level
        String difficulty = "Level " + currentQuiz.getDifficulty();
        holder.textViewDifficulty.setText(difficulty);
    }
    
    // Set whether to use Hindi questions as primary
    public void setUseHindiQuestions(boolean useHindiQuestions) {
        this.useHindiQuestions = useHindiQuestions;
        notifyDataSetChanged();
    }
    
    public Quiz getQuizAt(int position) {
        return getItem(position);
    }
    
    class QuizHolder extends RecyclerView.ViewHolder {
        private TextView textViewQuestion;
        private TextView textViewQuestionSecondary;
        private TextView textViewQuizType;
        private TextView textViewDifficulty;
        
        public QuizHolder(@NonNull View itemView) {
            super(itemView);
            textViewQuestion = itemView.findViewById(R.id.text_view_quiz_question);
            textViewQuestionSecondary = itemView.findViewById(R.id.text_view_quiz_question_secondary);
            textViewQuizType = itemView.findViewById(R.id.text_view_quiz_type);
            textViewDifficulty = itemView.findViewById(R.id.text_view_quiz_difficulty);
            
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
        void onItemClick(Quiz quiz);
    }
    
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}