package com.bhashasetu.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import com.bhashasetu.app.model.PracticeSession;
import com.bhashasetu.app.model.UserProgress;
import com.bhashasetu.app.model.Word;
import com.bhashasetu.app.viewmodel.PracticeSessionViewModel;
import com.bhashasetu.app.viewmodel.UserProgressViewModel;
import com.bhashasetu.app.viewmodel.WordViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Locale;

import java.util.List;

public class SpellingPracticeActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    
    private TextView textViewTitle;
    private TextView textViewProgress;
    private ProgressBar progressBarSpelling;
    private TextView textViewHindiWord;
    private TextView textViewHindiPronunciation;
    private ImageButton buttonPlayPronunciation;
    private TextInputEditText editTextSpelling;
    private Button buttonCheckSpelling;
    private Button buttonNextWord;
    private CardView cardViewResult;
    private TextView textViewResult;
    
    private WordViewModel wordViewModel;
    private UserProgressViewModel userProgressViewModel;
    private PracticeSessionViewModel sessionViewModel;
    
    private TextToSpeech textToSpeech;
    private boolean ttsReady = false;
    
    private String category;
    private int difficulty;
    private int wordCount;
    private PracticeSession currentSession;
    private boolean hasCheckedSpelling = false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spelling_practice);
        
        // Set up ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.spelling);
        }
        
        // Initialize UI components
        textViewTitle = findViewById(R.id.text_view_spelling_title);
        textViewProgress = findViewById(R.id.text_view_progress);
        progressBarSpelling = findViewById(R.id.progress_bar_spelling);
        textViewHindiWord = findViewById(R.id.text_view_hindi_word);
        textViewHindiPronunciation = findViewById(R.id.text_view_hindi_pronunciation);
        buttonPlayPronunciation = findViewById(R.id.button_play_pronunciation);
        editTextSpelling = findViewById(R.id.edit_text_spelling);
        buttonCheckSpelling = findViewById(R.id.button_check_spelling);
        buttonNextWord = findViewById(R.id.button_next_word);
        cardViewResult = findViewById(R.id.card_view_result);
        textViewResult = findViewById(R.id.text_view_result);
        
        // Initialize ViewModels
        wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        userProgressViewModel = new ViewModelProvider(this).get(UserProgressViewModel.class);
        sessionViewModel = new ViewModelProvider(this).get(PracticeSessionViewModel.class);
        
        // Initialize Text-to-Speech
        textToSpeech = new TextToSpeech(this, this);
        
        // Get Intent extras
        category = getIntent().getStringExtra("category");
        difficulty = getIntent().getIntExtra("difficulty", 1);
        wordCount = getIntent().getIntExtra("word_count", 10);
        
        // Initialize the spelling session
        initializeSpellingSession();
        
        // Set up keyboard action listener
        editTextSpelling.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    buttonCheckSpelling.performClick();
                    return true;
                }
                return false;
            }
        });
        
        // Set up click listeners
        buttonCheckSpelling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkSpelling();
            }
        });
        
        buttonNextWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextWord();
            }
        });
        
        buttonPlayPronunciation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPronunciation();
            }
        });
    }
    
    private void initializeSpellingSession() {
        // Create a new practice session
        currentSession = sessionViewModel.createSpellingSession(1);
        sessionViewModel.insert(currentSession);
        
        // Load words for practice based on category and difficulty
        if (category != null) {
            // Load words from a specific category
            wordViewModel.getWordsByCategory(category).observe(this, new Observer<List<Word>>() {
                @Override
                public void onChanged(List<Word> words) {
                    if (words != null && !words.isEmpty()) {
                        // Filter by difficulty
                        for (Word word : words) {
                            if (word.getDifficulty() <= difficulty && currentSession.getWords().size() < wordCount) {
                                currentSession.getWords().add(word);
                            }
                        }
                        
                        if (currentSession.getWords().isEmpty()) {
                            // No words match criteria
                            Toast.makeText(SpellingPracticeActivity.this, "No words match the criteria", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            currentSession.setTotalItems(currentSession.getWords().size());
                            sessionViewModel.update(currentSession);
                            updateWordDisplay();
                        }
                    } else {
                        // No words in this category
                        Toast.makeText(SpellingPracticeActivity.this, "No words in this category", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            });
        } else {
            // Load random words by difficulty
            wordViewModel.getRandomWordsByDifficulty(difficulty, wordCount).observe(this, new Observer<List<Word>>() {
                @Override
                public void onChanged(List<Word> words) {
                    if (words != null && !words.isEmpty()) {
                        currentSession.setWords(words);
                        sessionViewModel.update(currentSession);
                        updateWordDisplay();
                    } else {
                        // No words match criteria
                        Toast.makeText(SpellingPracticeActivity.this, "No words match the criteria", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            });
        }
    }
    
    private void updateWordDisplay() {
        Word currentWord = currentSession.getCurrentWord();
        
        if (currentWord != null) {
            // Update progress indicator
            int currentIndex = currentSession.getCurrentIndex() + 1;
            int totalItems = currentSession.getTotalItems();
            textViewProgress.setText(getString(R.string.word_of_total, currentIndex, totalItems));
            progressBarSpelling.setProgress((int) ((float) currentIndex / totalItems * 100));
            
            // Display the Hindi word and pronunciation
            textViewHindiWord.setText(currentWord.getHindiWord());
            textViewHindiPronunciation.setText(getString(R.string.pronunciation_with_colon, currentWord.getHindiPronunciation()));
            
            // Clear previous input and result
            editTextSpelling.setText("");
            cardViewResult.setVisibility(View.GONE);
            
            // Reset UI state
            hasCheckedSpelling = false;
            buttonCheckSpelling.setVisibility(View.VISIBLE);
            buttonNextWord.setVisibility(View.GONE);
            editTextSpelling.setEnabled(true);
            
            // Set focus to the edit text
            editTextSpelling.requestFocus();
        }
    }
    
    private void checkSpelling() {
        String userSpelling = editTextSpelling.getText().toString().trim();
        
        if (userSpelling.isEmpty()) {
            Toast.makeText(this, "Please enter the spelling", Toast.LENGTH_SHORT).show();
            return;
        }
        
        Word currentWord = currentSession.getCurrentWord();
        if (currentWord == null) {
            return;
        }
        
        // Compare the user's spelling with the correct spelling
        boolean isCorrect = userSpelling.equalsIgnoreCase(currentWord.getEnglishWord());
        
        // Record the result
        currentSession.recordAnswer(isCorrect);
        
        // Update user progress for this word
        userProgressViewModel.getWordProgress(1, currentWord.getId()).observe(this, new Observer<UserProgress>() {
            @Override
            public void onChanged(UserProgress progress) {
                if (progress != null) {
                    userProgressViewModel.recordAttemptResult(progress, isCorrect);
                } else {
                    // Create a new progress entry if none exists
                    UserProgress newProgress = new UserProgress(1, "word", currentWord.getId());
                    newProgress.updateSpacedRepetition(isCorrect);
                    userProgressViewModel.insert(newProgress);
                }
            }
        });
        
        // Update the UI
        hasCheckedSpelling = true;
        cardViewResult.setVisibility(View.VISIBLE);
        
        if (isCorrect) {
            textViewResult.setText(getString(R.string.correct));
            cardViewResult.setCardBackgroundColor(getResources().getColor(R.color.colorCorrect));
        } else {
            textViewResult.setText(getString(R.string.incorrect_spelling, currentWord.getEnglishWord()));
            cardViewResult.setCardBackgroundColor(getResources().getColor(R.color.colorIncorrect));
        }
        
        // Disable input
        editTextSpelling.setEnabled(false);
        
        // Hide check button, show next button
        buttonCheckSpelling.setVisibility(View.GONE);
        buttonNextWord.setVisibility(View.VISIBLE);
    }
    
    private void showNextWord() {
        if (currentSession.moveToNext()) {
            updateWordDisplay();
        } else {
            // End of session
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
        
        // Show result dialog instead of just a toast
        showCompletionDialog(score);
    }
    
    private void showCompletionDialog(int score) {
        // Create a dialog to show the completion results
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        
        // Inflate a custom view for the dialog
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_practice_complete, null);
        builder.setView(dialogView);
        
        // Set up dialog components
        TextView textViewScore = dialogView.findViewById(R.id.text_view_score);
        TextView textViewFeedback = dialogView.findViewById(R.id.text_view_feedback);
        Button buttonClose = dialogView.findViewById(R.id.button_close);
        Button buttonRestart = dialogView.findViewById(R.id.button_restart);
        
        // Set the score
        textViewScore.setText(getString(R.string.score_percentage, score));
        
        // Set feedback based on score
        String feedback;
        if (score >= 90) {
            feedback = getString(R.string.excellent_feedback);
        } else if (score >= 70) {
            feedback = getString(R.string.very_good_feedback);
        } else if (score >= 50) {
            feedback = getString(R.string.good_effort_feedback);
        } else {
            feedback = getString(R.string.keep_practicing_feedback);
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
    
    private void playPronunciation() {
        if (!ttsReady) {
            Toast.makeText(this, "Text-to-speech is not ready yet", Toast.LENGTH_SHORT).show();
            return;
        }
        
        Word currentWord = currentSession.getCurrentWord();
        if (currentWord == null) {
            return;
        }
        
        // Get the word to pronounce (English word in this case)
        String wordToSpeak = currentWord.getEnglishWord();
        
        // Set speech rate (slightly slower for learning)
        textToSpeech.setSpeechRate(0.8f);
        
        // Speak the word
        textToSpeech.speak(wordToSpeak, TextToSpeech.QUEUE_FLUSH, null, "spelling_word_" + currentWord.getId());
    }
    
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            // Set English as language for TTS
            int result = textToSpeech.setLanguage(Locale.US);
            
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "Text-to-speech language not supported", Toast.LENGTH_SHORT).show();
            } else {
                ttsReady = true;
                buttonPlayPronunciation.setEnabled(true);
            }
        } else {
            Toast.makeText(this, "Text-to-speech initialization failed", Toast.LENGTH_SHORT).show();
            buttonPlayPronunciation.setEnabled(false);
        }
    }
    
    @Override
    protected void onDestroy() {
        // Shutdown TTS when activity is destroyed
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
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