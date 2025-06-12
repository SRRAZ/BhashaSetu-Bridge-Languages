package com.bhashasetu.app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bhashasetu.app.model.UserProgress;
import com.bhashasetu.app.model.Word;
import com.bhashasetu.app.view.AudioPronunciationView;
import com.bhashasetu.app.view.PronunciationRecorderView;
import com.bhashasetu.app.viewmodel.UserProgressViewModel;
import com.bhashasetu.app.viewmodel.WordViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class PronunciationComparisonActivity extends BaseActivity implements 
 BottomNavigationView.OnNavigationItemSelectedListener,
        PronunciationRecorderView.OnPronunciationComparedListener {

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

    // UI Components
    private Toolbar toolbar;
    private CardView cardViewWord;
    private TextView textViewWordPrompt;
    private TextView textViewEnglishWord;
    private TextView textViewPronunciation;
    private TextView textViewHindiWord;
    private AudioPronunciationView audioPronunciationView;
    private PronunciationRecorderView pronunciationRecorderView;
    private CardView cardViewTips;
    private TextView textViewTipsTitle;
    private TextView textViewTipsContent;
    private CardView cardViewProgress;
    private TextView textViewProgressTitle;
    private TextView textViewProgressStats;
    private ProgressBar progressBarMastery;
    private TextView textViewMasteryLevel;
    private BottomNavigationView bottomNavigation;

    // ViewModels
    private WordViewModel wordViewModel;
    private UserProgressViewModel userProgressViewModel;

    // Current Word
    private Word currentWord;
    private UserProgress userProgress;

    // Pronunciation tips by sound pattern
    private Map<String, String> pronunciationTips = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pronunciation_comparison);

        // Initialize ViewModels
        wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        userProgressViewModel = new ViewModelProvider(this).get(UserProgressViewModel.class);

        // Find views
        toolbar = findViewById(R.id.toolbar);
        cardViewWord = findViewById(R.id.card_view_word);
        textViewWordPrompt = findViewById(R.id.text_view_word_prompt);
        textViewEnglishWord = findViewById(R.id.text_view_english_word);
        textViewPronunciation = findViewById(R.id.text_view_pronunciation);
        textViewHindiWord = findViewById(R.id.text_view_hindi_word);
        audioPronunciationView = findViewById(R.id.audio_pronunciation_view);
        // Use the enhanced audio pronunciation view with visualization
 if (audioPronunciationView instanceof EnhancedAudioPronunciationView) {
 ((EnhancedAudioPronunciationView) audioPronunciationView).setVisualizerEnabled(true);
        }
        pronunciationRecorderView = findViewById(R.id.pronunciation_recorder_view);
        cardViewTips = findViewById(R.id.card_view_tips);
        textViewTipsTitle = findViewById(R.id.text_view_tips_title);
        textViewTipsContent = findViewById(R.id.text_view_tips_content);
        cardViewProgress = findViewById(R.id.card_view_progress);
        textViewProgressTitle = findViewById(R.id.text_view_progress_title);
        textViewProgressStats = findViewById(R.id.text_view_progress_stats);
        progressBarMastery = findViewById(R.id.progress_bar_mastery);
        textViewMasteryLevel = findViewById(R.id.text_view_mastery_level);
        bottomNavigation = findViewById(R.id.bottom_navigation);

        // Set up toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Set up bottom navigation
        bottomNavigation.setOnNavigationItemSelectedListener(this);

        // Set up pronunciation recorder
        pronunciationRecorderView.setOnPronunciationComparedListener(this);

        // Initialize pronunciation tips
        initializePronunciationTips();

        // Load word from intent
        int wordId = getIntent().getIntExtra("word_id", -1);
        if (wordId != -1) {
            loadWord(wordId);
        } else {
            // If no word ID is provided, use a random word
            loadRandomWord();
        }

        // Check for audio recording permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    REQUEST_RECORD_AUDIO_PERMISSION);
        }
    }

    /**
     * Initialize pronunciation tips for different sound patterns.
     */
    private void initializePronunciationTips() {
        pronunciationTips.put("th", getString(R.string.tip_th_sound));
        pronunciationTips.put("r", getString(R.string.tip_r_sound));
        pronunciationTips.put("l", getString(R.string.tip_l_sound));
        pronunciationTips.put("w", getString(R.string.tip_w_sound));
        pronunciationTips.put("v", getString(R.string.tip_v_sound));
        pronunciationTips.put("sh", getString(R.string.tip_sh_sound));
        pronunciationTips.put("ch", getString(R.string.tip_ch_sound));
        pronunciationTips.put("j", getString(R.string.tip_j_sound));
        pronunciationTips.put("a", getString(R.string.tip_a_sound));
        pronunciationTips.put("e", getString(R.string.tip_e_sound));
        pronunciationTips.put("i", getString(R.string.tip_i_sound));
        pronunciationTips.put("o", getString(R.string.tip_o_sound));
        pronunciationTips.put("u", getString(R.string.tip_u_sound));
    }

    /**
     * Load a specific word by ID.
     *
     * @param wordId The ID of the word to load
     */
    private void loadWord(int wordId) {
        wordViewModel.getWordById(wordId).observe(this, new Observer<Word>() {
            @Override
            public void onChanged(Word word) {
                if (word != null) {
                    displayWord(word);
                    loadUserProgress(word.getId());
                }
            }
        });
    }

    /**
     * Load a random word.
     */
    private void loadRandomWord() {
        wordViewModel.getRandomWord().observe(this, new Observer<Word>() {
            @Override
            public void onChanged(Word word) {
                if (word != null) {
                    displayWord(word);
                    loadUserProgress(word.getId());
                }
            }
        });
    }

    /**
     * Load the user's progress for the current word.
     *
     * @param wordId The ID of the word
     */
    private void loadUserProgress(int wordId) {
        userProgressViewModel.getWordProgress(1, wordId).observe(this, new Observer<UserProgress>() {
            @Override
            public void onChanged(UserProgress progress) {
                if (progress != null) {
                    userProgress = progress;
 updateProgressUI();
                } else {
                    // Create new progress entry if none exists
                    userProgress = new UserProgress(1, "word", wordId);
                    userProgress.setAttemptCount(0);
                    userProgress.setCorrectCount(0);
                    userProgressViewModel.insert(userProgress);
                    updateProgressUI();
                }
            }
        });
    }

    /**
     * Display the word in the UI.
     *
     * @param word The word to display
     */
    private void displayWord(Word word) {
        currentWord = word;

        // Set word text
        textViewEnglishWord.setText(word.getEnglishWord());
        textViewHindiWord.setText(word.getHindiWord());
        textViewPronunciation.setText(word.getEnglishPronunciation());

        // Set up audio pronunciation
        audioPronunciationView.setWordId(word.getId());
        audioPronunciationView.setWordText(word.getEnglishWord());

        // Set up pronunciation recorder
        pronunciationRecorderView.setWord(word.getId(), word.getEnglishWord());

        // Set pronunciation tips
        setPronunciationTips(word.getEnglishWord());
    }

    /**
     * Set pronunciation tips based on the word.
     *
     * @param word The word to analyze
     */
    private void setPronunciationTips(String word) {
        StringBuilder tips = new StringBuilder();

        // Check for common sound patterns
        word = word.toLowerCase();

        boolean foundTip = false;
        
        for (Map.Entry<String, String> entry : pronunciationTips.entrySet()) {
            if (word.contains(entry.getKey())) {
                tips.append("• ").append(entry.getValue()).append("\n\n");
                foundTip = true;
            }
 continue;
        }

        if (!foundTip) {
            // Default tip if no specific patterns found
            tips.append(getString(R.string.tip_general_pronunciation));
        }

        // Add a tip about listening carefully
        tips.append("• ").append(getString(R.string.tip_listen_carefully));
        
        textViewTipsContent.setText(tips.toString());
    }

    /**
     * Update the progress UI with the user's current progress.
     */
    private void updateProgressUI() {
        if (userProgress != null) {
            int attemptCount = userProgress.getAttemptCount();
 int correctCount = userProgress.getCorrectCount();

            // Update attempts text
            String attemptsText = getResources().getQuantityString(
 R.plurals.pronunciation_attempts,
 attemptCount,
                    attemptCount);
            textViewProgressStats.setText(attemptsText);

            // Calculate mastery percentage
            int masteryPercentage = 0;
            if (attemptCount > 0) {
                masteryPercentage = (correctCount * 100) / attemptCount;
            }
            
            // Update progress bar
 progressBarMastery.setProgress(masteryPercentage);

            // Update mastery text
            DecimalFormat df = new DecimalFormat("#.#");
 String masteryText = getString(R.string.pronunciation_mastery,
                    df.format(masteryPercentage));
            textViewMasteryLevel.setText(masteryText);
        }
    }

    /**
     * Handle pronunciation comparison results.
     *
     * @param similarityScore The similarity score (0-100)
     * @param feedback The feedback message
     */
    @Override
    public void onPronunciationCompared(double similarityScore, String feedback) {
        // Update user progress
 if (userProgress != null) {
            boolean isCorrect = similarityScore >= 70; // Consider 70% or higher as correct
            userProgress.incrementAttemptCount();
            if (isCorrect) {
                userProgress.incrementCorrectCount();
            }
            userProgressViewModel.update(userProgress);
        }
    }

    /**
     * Handle permission request results.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                Toast.makeText(this, R.string.recording_permission_granted, Toast.LENGTH_SHORT).show();
            } else {
 // Permission denied
                Toast.makeText(this, R.string.recording_permission_denied, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Handle bottom navigation selection.
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                startActivity(new Intent(this, MainActivity.class));
                finish();
 return true;

            case R.id.navigation_practice:
                startActivity(new Intent(this, PracticeActivity.class));
                finish();
 return true;

            case R.id.navigation_lessons:
 startActivity(new Intent(this, LessonsActivity.class));
                finish();
                return true;
        }
        return false;
    }

    /**
     * Handle toolbar navigation.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}