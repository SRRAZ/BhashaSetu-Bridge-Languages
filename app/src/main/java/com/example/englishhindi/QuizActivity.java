package com.example.englishhindi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import com.example.englishhindi.model.PracticeSession;
import com.example.englishhindi.model.Quiz;
import com.example.englishhindi.model.UserProgress;
import com.example.englishhindi.viewmodel.PracticeSessionViewModel;
import com.example.englishhindi.viewmodel.QuizViewModel;
import com.example.englishhindi.viewmodel.UserProgressViewModel;

import java.util.List;

public class QuizActivity extends AppCompatActivity {
    
    private TextView textViewQuizTitle;
    private TextView textViewProgress;
    private ProgressBar progressBarQuiz;
    private TextView textViewQuestion;
    private TextView textViewQuestionHindi;
    private RadioGroup radioGroupOptions;
    private RadioButton radioOption1;
    private RadioButton radioOption2;
    private RadioButton radioOption3;
    private RadioButton radioOption4;
    private Button buttonCheckAnswer;
    private Button buttonNextQuestion;
    private LinearLayout layoutExplanation;
    private TextView textViewAnswerStatus;
    private TextView textViewExplanation;
    private TextView textViewExplanationHindi;
    
    private QuizViewModel quizViewModel;
    private UserProgressViewModel userProgressViewModel;
    private PracticeSessionViewModel sessionViewModel;
    
    private Animation animFadeIn;
    private Animation animSlideUp;
    
    private int quizId;
    private boolean isQuickQuiz;
    private int quizDifficulty;
    private int quizCount;
    private boolean isSingleQuiz;
    private PracticeSession currentSession;
    private boolean hasCheckedAnswer = false;
    private int selectedAnswerIndex = -1;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        
        // Set up ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.multiple_choice);
        }
        
        // Initialize UI components
        textViewQuizTitle = findViewById(R.id.text_view_quiz_title);
        textViewProgress = findViewById(R.id.text_view_progress);
        progressBarQuiz = findViewById(R.id.progress_bar_quiz);
        textViewQuestion = findViewById(R.id.text_view_question);
        textViewQuestionHindi = findViewById(R.id.text_view_question_hindi);
        radioGroupOptions = findViewById(R.id.radio_group_options);
        radioOption1 = findViewById(R.id.radio_option_1);
        radioOption2 = findViewById(R.id.radio_option_2);
        radioOption3 = findViewById(R.id.radio_option_3);
        radioOption4 = findViewById(R.id.radio_option_4);
        buttonCheckAnswer = findViewById(R.id.button_check_answer);
        buttonNextQuestion = findViewById(R.id.button_next_question);
        layoutExplanation = findViewById(R.id.layout_explanation);
        textViewAnswerStatus = findViewById(R.id.text_view_answer_status);
        textViewExplanation = findViewById(R.id.text_view_explanation);
        textViewExplanationHindi = findViewById(R.id.text_view_explanation_hindi);
        
        // Initialize ViewModels
        quizViewModel = new ViewModelProvider(this).get(QuizViewModel.class);
        userProgressViewModel = new ViewModelProvider(this).get(UserProgressViewModel.class);
        sessionViewModel = new ViewModelProvider(this).get(PracticeSessionViewModel.class);
        
        // Load animations
        animFadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        animSlideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up_in);
        
        // Get Intent extras
        quizId = getIntent().getIntExtra("quiz_id", -1);
        isQuickQuiz = getIntent().getBooleanExtra("quick_quiz", false);
        quizDifficulty = getIntent().getIntExtra("difficulty", 5);
        quizCount = getIntent().getIntExtra("quiz_count", 10);
        isSingleQuiz = getIntent().getBooleanExtra("single_quiz", false);
        
        // Initialize the appropriate quiz session
        if (isSingleQuiz && quizId != -1) {
            // Single quiz mode - load the specified quiz
            initializeSingleQuizSession();
        } else if (isQuickQuiz) {
            // Quick quiz mode - load random quizzes
            initializeQuickQuizSession();
        } else {
            // Something went wrong
            Toast.makeText(this, "Error: Invalid quiz parameters", Toast.LENGTH_SHORT).show();
            finish();
        }
        
        // Set up click listeners
        buttonCheckAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });
        
        buttonNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextQuestion();
            }
        });
        
        radioGroupOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_option_1) {
                    selectedAnswerIndex = 0;
                } else if (checkedId == R.id.radio_option_2) {
                    selectedAnswerIndex = 1;
                } else if (checkedId == R.id.radio_option_3) {
                    selectedAnswerIndex = 2;
                } else if (checkedId == R.id.radio_option_4) {
                    selectedAnswerIndex = 3;
                }
            }
        });
    }
    
    private void initializeSingleQuizSession() {
        // Get the quiz details
        quizViewModel.getQuizById(quizId).observe(this, new Observer<Quiz>() {
            @Override
            public void onChanged(Quiz quiz) {
                if (quiz != null) {
                    // Create a new practice session
                    currentSession = sessionViewModel.createQuizSession(1, 0);
                    currentSession.getQuizzes().add(quiz);
                    currentSession.setTotalItems(1);
                    sessionViewModel.insert(currentSession);
                    
                    // Show the quiz
                    updateQuizDisplay();
                } else {
                    // Quiz not found
                    Toast.makeText(QuizActivity.this, "Error: Quiz not found", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
    
    private void initializeQuickQuizSession() {
        // Set title for quick quiz
        textViewQuizTitle.setText("Quick Quiz");
        
        // Get random quizzes based on difficulty
        quizViewModel.getQuizzesByDifficulty(quizDifficulty, quizCount).observe(this, new Observer<List<Quiz>>() {
            @Override
            public void onChanged(List<Quiz> quizzes) {
                if (quizzes != null && !quizzes.isEmpty()) {
                    // Create a new practice session
                    currentSession = sessionViewModel.createQuizSession(1, 0);
                    currentSession.setQuizzes(quizzes);
                    sessionViewModel.insert(currentSession);
                    
                    // Show the first quiz
                    updateQuizDisplay();
                } else {
                    // No quizzes found
                    Toast.makeText(QuizActivity.this, "Error: No quizzes available", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
    
    private void updateQuizDisplay() {
        Quiz currentQuiz = currentSession.getCurrentQuiz();
        
        if (currentQuiz != null) {
            // Update progress indicator
            int currentIndex = currentSession.getCurrentIndex() + 1;
            int totalItems = currentSession.getTotalItems();
            textViewProgress.setText("Question " + currentIndex + " of " + totalItems);
            progressBarQuiz.setProgress((int) ((float) currentIndex / totalItems * 100));
            
            // Get shared preferences for interface language
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            boolean useHindiInterface = sharedPreferences.getBoolean("use_hindi_interface", false);
            
            // Set question text based on interface language preference
            if (useHindiInterface) {
                textViewQuestion.setText(currentQuiz.getQuestionHindi());
                textViewQuestionHindi.setText(currentQuiz.getQuestion());
            } else {
                textViewQuestion.setText(currentQuiz.getQuestion());
                textViewQuestionHindi.setText(currentQuiz.getQuestionHindi());
            }
            
            // Set up answer options
            String correctAnswer = currentQuiz.getCorrectAnswer();
            String wrongAnswer1 = currentQuiz.getWrongAnswer1();
            String wrongAnswer2 = currentQuiz.getWrongAnswer2();
            String wrongAnswer3 = currentQuiz.getWrongAnswer3();
            
            // Randomize the order of options
            int correctPosition = (int) (Math.random() * 4);
            
            radioOption1.setText(correctPosition == 0 ? correctAnswer : wrongAnswer1);
            radioOption2.setText(correctPosition == 1 ? correctAnswer : (correctPosition == 0 ? wrongAnswer1 : wrongAnswer2));
            radioOption3.setText(correctPosition == 2 ? correctAnswer : (correctPosition < 2 ? wrongAnswer2 : wrongAnswer3));
            radioOption4.setText(correctPosition == 3 ? correctAnswer : wrongAnswer3);
            
            // Clear any previously selected option
            radioGroupOptions.clearCheck();
            selectedAnswerIndex = -1;
            
            // Reset UI state
            hasCheckedAnswer = false;
            layoutExplanation.setVisibility(View.GONE);
            buttonCheckAnswer.setVisibility(View.VISIBLE);
            buttonNextQuestion.setVisibility(View.GONE);
            
            // Enable all radio buttons
            radioOption1.setEnabled(true);
            radioOption2.setEnabled(true);
            radioOption3.setEnabled(true);
            radioOption4.setEnabled(true);
        }
    }
    
    private void checkAnswer() {
        if (selectedAnswerIndex == -1) {
            Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
            return;
        }
        
        Quiz currentQuiz = currentSession.getCurrentQuiz();
        if (currentQuiz == null) {
            return;
        }
        
        // Determine if the selected answer is correct
        RadioButton selectedOption = null;
        switch (selectedAnswerIndex) {
            case 0:
                selectedOption = radioOption1;
                break;
            case 1:
                selectedOption = radioOption2;
                break;
            case 2:
                selectedOption = radioOption3;
                break;
            case 3:
                selectedOption = radioOption4;
                break;
        }
        
        if (selectedOption == null) {
            return;
        }
        
        boolean isCorrect = selectedOption.getText().toString().equals(currentQuiz.getCorrectAnswer());
        
        // Record the result
        currentSession.recordAnswer(isCorrect);
        
        // Update the UI
        hasCheckedAnswer = true;
        
        // Show explanation with animation
        layoutExplanation.setVisibility(View.VISIBLE);
        layoutExplanation.startAnimation(animSlideUp);
        
        if (isCorrect) {
            textViewAnswerStatus.setText("Correct!");
            layoutExplanation.setBackgroundResource(R.color.colorCorrect);
            
            // Give visual feedback by briefly highlighting the correct answer
            selectedOption.setBackgroundResource(R.color.colorCorrect);
            selectedOption.postDelayed(new Runnable() {
                @Override
                public void run() {
                    selectedOption.setBackgroundResource(android.R.color.transparent);
                }
            }, 1000);
        } else {
            textViewAnswerStatus.setText("Incorrect. The correct answer is: " + currentQuiz.getCorrectAnswer());
            layoutExplanation.setBackgroundResource(R.color.colorIncorrect);
            
            // Give visual feedback by highlighting the wrong answer in red and the correct one in green
            selectedOption.setBackgroundResource(R.color.colorIncorrect);
            
            // Find and highlight the correct answer
            String correctAnswer = currentQuiz.getCorrectAnswer();
            if (radioOption1.getText().toString().equals(correctAnswer)) {
                radioOption1.setBackgroundResource(R.color.colorCorrect);
            } else if (radioOption2.getText().toString().equals(correctAnswer)) {
                radioOption2.setBackgroundResource(R.color.colorCorrect);
            } else if (radioOption3.getText().toString().equals(correctAnswer)) {
                radioOption3.setBackgroundResource(R.color.colorCorrect);
            } else if (radioOption4.getText().toString().equals(correctAnswer)) {
                radioOption4.setBackgroundResource(R.color.colorCorrect);
            }
        }
        
        // Set the explanation text
        textViewExplanation.setText(currentQuiz.getExplanation());
        textViewExplanationHindi.setText(currentQuiz.getExplanationHindi());
        
        // Disable radio buttons
        radioOption1.setEnabled(false);
        radioOption2.setEnabled(false);
        radioOption3.setEnabled(false);
        radioOption4.setEnabled(false);
        
        // Hide check button, show next button
        buttonCheckAnswer.setVisibility(View.GONE);
        buttonNextQuestion.setVisibility(View.VISIBLE);
    }
    
    private void showNextQuestion() {
        if (currentSession.moveToNext()) {
            updateQuizDisplay();
        } else {
            // End of quiz
            finishSession();
        }
    }
    
    private void finishSession() {
        // Complete the session
        currentSession.setCompleted(true);
        
        // Calculate the score (percentage)
        int score = (int) currentSession.getAccuracy();
        currentSession.setScore(score);
        
        // Update the session in the database
        sessionViewModel.completeSession(currentSession);
        
        // Show completion dialog
        showCompletionDialog(score);
    }
    
    private void showCompletionDialog(int score) {
        // Create a dialog to show the completion results
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        
        // Inflate a custom view for the dialog
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_practice_complete, null);
        builder.setView(dialogView);
        
        // Set up dialog components
        TextView textViewCongratulations = dialogView.findViewById(R.id.text_view_congratulations);
        TextView textViewScore = dialogView.findViewById(R.id.text_view_score);
        TextView textViewFeedback = dialogView.findViewById(R.id.text_view_feedback);
        Button buttonClose = dialogView.findViewById(R.id.button_close);
        Button buttonRestart = dialogView.findViewById(R.id.button_restart);
        
        // Set custom text for quiz
        textViewCongratulations.setText("क्विज़ पूरा हुआ!\nQuiz Complete!");
        
        // Set the score
        textViewScore.setText(score + "%");
        
        // Set feedback based on score
        String feedback;
        if (score >= 90) {
            feedback = "शानदार! आप एक ज्ञानी व्यक्ति हैं।\nBrilliant! You're a knowledgeable person.";
        } else if (score >= 70) {
            feedback = "बहुत अच्छा! आपका ज्ञान अच्छा है।\nVery good! Your knowledge is solid.";
        } else if (score >= 50) {
            feedback = "अच्छा प्रयास! और अध्ययन करें।\nGood effort! Keep studying.";
        } else {
            feedback = "अभ्यास से सुधार होगा! दोबारा प्रयास करें।\nPractice makes perfect! Try again.";
        }
        textViewFeedback.setText(feedback);
        
        // Create and show the dialog
        final androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
        
        // Set up button click listeners
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });
        
        buttonRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                // Restart with the same parameters
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
    }
    
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}