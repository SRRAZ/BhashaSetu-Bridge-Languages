package com.example.englishhindi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.MenuItem;
import android.view.View;
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

import com.example.englishhindi.model.PracticeSession;
import com.example.englishhindi.model.UserProgress;
import com.example.englishhindi.model.Word;
import com.example.englishhindi.viewmodel.PracticeSessionViewModel;
import com.example.englishhindi.viewmodel.UserProgressViewModel;
import com.example.englishhindi.viewmodel.WordViewModel;

import java.util.List;
import java.util.Locale;

public class PronunciationPracticeActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    
    private TextView textViewTitle;
    private TextView textViewProgress;
    private ProgressBar progressBarPronunciation;
    private CardView cardViewWord;
    private TextView textViewWordPrompt;
    private TextView textViewEnglishWord;
    private TextView textViewHindiWord;
    private TextView textViewInstruction;
    private ImageButton buttonPlaySlowPronunciation;
    private ImageButton buttonPlayNormalPronunciation;
    private Button buttonEasy;
    private Button buttonHard;
    private Button buttonRepeat;
    private Button buttonNext;
    
    private WordViewModel wordViewModel;
    private UserProgressViewModel userProgressViewModel;
    private PracticeSessionViewModel sessionViewModel;
    
    private TextToSpeech textToSpeech;
    private boolean ttsReady = false;
    
    private String category;
    private int difficulty;
    private int wordCount;
    private PracticeSession currentSession;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pronunciation_practice);
        
        // Set up ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.pronunciation);
        }
        
        // Initialize UI components
        textViewTitle = findViewById(R.id.text_view_pronunciation_title);
        textViewProgress = findViewById(R.id.text_view_progress);
        progressBarPronunciation = findViewById(R.id.progress_bar_pronunciation);
        cardViewWord = findViewById(R.id.card_view_word);
        textViewWordPrompt = findViewById(R.id.text_view_word_prompt);
        textViewEnglishWord = findViewById(R.id.text_view_english_word);
        textViewHindiWord = findViewById(R.id.text_view_hindi_word);
        textViewInstruction = findViewById(R.id.text_view_instruction);
        buttonPlaySlowPronunciation = findViewById(R.id.button_play_slow_pronunciation);
        buttonPlayNormalPronunciation = findViewById(R.id.button_play_normal_pronunciation);
        buttonEasy = findViewById(R.id.button_easy);
        buttonHard = findViewById(R.id.button_hard);
        buttonRepeat = findViewById(R.id.button_repeat);
        buttonNext = findViewById(R.id.button_next);
        
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
        
        // Initialize the pronunciation session
        initializePronunciationSession();
        
        // Set up click listeners
        buttonPlaySlowPronunciation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPronunciation(true);
            }
        });
        
        buttonPlayNormalPronunciation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPronunciation(false);
            }
        });
        
        buttonEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordDifficulty(true);
            }
        });
        
        buttonHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordDifficulty(false);
            }
        });
        
        buttonRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatCurrentWord();
            }
        });
        
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextWord();
            }
        });
    }
    
    private void initializePronunciationSession() {
        // Create a new practice session
        currentSession = sessionViewModel.createPronunciationSession(1);
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
                            Toast.makeText(PronunciationPracticeActivity.this, "No words match the criteria", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            currentSession.setTotalItems(currentSession.getWords().size());
                            sessionViewModel.update(currentSession);
                            updateWordDisplay();
                            playPronunciation(false); // Play the word automatically when starting
                        }
                    } else {
                        // No words in this category
                        Toast.makeText(PronunciationPracticeActivity.this, "No words in this category", Toast.LENGTH_SHORT).show();
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
                        playPronunciation(false); // Play the word automatically when starting
                    } else {
                        // No words match criteria
                        Toast.makeText(PronunciationPracticeActivity.this, "No words match the criteria", Toast.LENGTH_SHORT).show();
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
            textViewProgress.setText("Word " + currentIndex + " of " + totalItems);
            progressBarPronunciation.setProgress((int) ((float) currentIndex / totalItems * 100));
            
            // Display the word
            textViewEnglishWord.setText(currentWord.getEnglishWord());
            textViewHindiWord.setText(currentWord.getHindiWord());
            
            // Enable buttons
            buttonEasy.setEnabled(true);
            buttonHard.setEnabled(true);
            buttonRepeat.setEnabled(true);
            buttonNext.setEnabled(currentIndex < totalItems);
        }
    }
    
    private void playPronunciation(boolean slowMode) {
        if (!ttsReady) {
            Toast.makeText(this, "Text-to-speech is not ready yet", Toast.LENGTH_SHORT).show();
            return;
        }
        
        Word currentWord = currentSession.getCurrentWord();
        if (currentWord == null) {
            return;
        }
        
        // Set speech rate (slower if slowMode is true)
        textToSpeech.setSpeechRate(slowMode ? 0.5f : 0.8f);
        
        // Speak the word
        textToSpeech.speak(currentWord.getEnglishWord(), TextToSpeech.QUEUE_FLUSH, null, "pronunciation_word_" + currentWord.getId());
    }
    
    private void recordDifficulty(boolean wasEasy) {
        Word currentWord = currentSession.getCurrentWord();
        if (currentWord == null) {
            return;
        }
        
        // Record result based on whether it was easy or hard
        currentSession.recordAnswer(wasEasy);
        
        // Update user progress for this word
        userProgressViewModel.getWordProgress(1, currentWord.getId()).observe(this, new Observer<UserProgress>() {
            @Override
            public void onChanged(UserProgress progress) {
                if (progress != null) {
                    userProgressViewModel.recordAttemptResult(progress, wasEasy);
                } else {
                    // Create a new progress entry if none exists
                    UserProgress newProgress = new UserProgress(1, "word", currentWord.getId());
                    newProgress.updateSpacedRepetition(wasEasy);
                    userProgressViewModel.insert(newProgress);
                }
            }
        });
        
        // Move to next word
        showNextWord();
    }
    
    private void repeatCurrentWord() {
        // Play the pronunciation again (normal speed)
        playPronunciation(false);
    }
    
    private void showNextWord() {
        if (currentSession.moveToNext()) {
            updateWordDisplay();
            // Play the new word automatically
            playPronunciation(false);
        } else {
            // End of session
            finishSession();
        }
    }
    
    private void finishSession() {
        // Complete the session
        currentSession.setCompleted(true);
        
        // Calculate the score (percentage of words marked as "easy")
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
        
        // Set custom text for pronunciation practice
        textViewCongratulations.setText("उच्चारण अभ्यास पूरा हुआ!\nPronunciation Practice Complete!");
        
        // Set the score
        textViewScore.setText(score + "%");
        
        // Set feedback based on score (this is the percentage of words marked as "easy")
        String feedback;
        if (score >= 90) {
            feedback = "आपका उच्चारण बहुत अच्छा है!\nYour pronunciation is excellent!";
        } else if (score >= 70) {
            feedback = "बढ़िया उच्चारण! थोड़ी और प्रैक्टिस से और बेहतर होगा।\nGreat pronunciation! A bit more practice will make it even better.";
        } else if (score >= 50) {
            feedback = "अच्छा प्रयास! नियमित रूप से बोलने का अभ्यास करें।\nGood effort! Practice speaking regularly.";
        } else {
            feedback = "उच्चारण सुधारने के लिए धीमी गति से अभ्यास करें।\nPractice slowly to improve your pronunciation.";
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
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            // Set English as language for TTS
            int result = textToSpeech.setLanguage(Locale.US);
            
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "Text-to-speech language not supported", Toast.LENGTH_SHORT).show();
            } else {
                ttsReady = true;
                buttonPlaySlowPronunciation.setEnabled(true);
                buttonPlayNormalPronunciation.setEnabled(true);
            }
        } else {
            Toast.makeText(this, "Text-to-speech initialization failed", Toast.LENGTH_SHORT).show();
            buttonPlaySlowPronunciation.setEnabled(false);
            buttonPlayNormalPronunciation.setEnabled(false);
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