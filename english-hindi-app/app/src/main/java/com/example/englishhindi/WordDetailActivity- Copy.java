package com.example.englishhindi;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.example.englishhindi.base.BaseActivity;
import com.example.englishhindi.model.Word;
import com.example.englishhindi.model.exercise.ExerciseType;
import com.example.englishhindi.model.gamification.PointsSource;
import com.example.englishhindi.util.AudioManager;
import com.example.englishhindi.util.GamificationManager;
import com.example.englishhindi.view.AudioPronunciationView;
import com.example.englishhindi.view.WordImageView;
import com.example.englishhindi.viewmodel.WordViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class WordDetailActivity extends BaseActivity {

    private WordViewModel wordViewModel;
    private GamificationManager gamificationManager;
    private AudioManager audioManager;
    
    private Word currentWord;
    
    private WordImageView wordImageView;
    private AudioPronunciationView englishPronunciationView;
    private AudioPronunciationView hindiPronunciationView;
    private TextView textViewPartOfSpeech;
    private TextView textViewEnglishExample;
    private TextView textViewHindiExample;
    private TextView textViewCategory;
    private TextView textViewDifficulty;
    private ProgressBar progressMastery;
    private LinearLayout layoutUsageContext;
    private TextView textViewUsageContext;
    private LinearLayout layoutNotes;
    private TextView textViewNotes;
    private Button buttonPractice;
    private FloatingActionButton fabFavorite;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_detail);
        
        // Set up toolbar
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        // Initialize managers
        gamificationManager = GamificationManager.getInstance(this);
        audioManager = AudioManager.getInstance(this);
        
        // Initialize views
        wordImageView = findViewById(R.id.word_image_view);
        englishPronunciationView = findViewById(R.id.english_pronunciation_view);
        hindiPronunciationView = findViewById(R.id.hindi_pronunciation_view);
        textViewPartOfSpeech = findViewById(R.id.text_view_part_of_speech);
        textViewEnglishExample = findViewById(R.id.text_view_english_example);
        textViewHindiExample = findViewById(R.id.text_view_hindi_example);
        textViewCategory = findViewById(R.id.text_view_category);
        textViewDifficulty = findViewById(R.id.text_view_difficulty);
        progressMastery = findViewById(R.id.progress_mastery);
        layoutUsageContext = findViewById(R.id.layout_usage_context);
        textViewUsageContext = findViewById(R.id.text_view_usage_context);
        layoutNotes = findViewById(R.id.layout_notes);
        textViewNotes = findViewById(R.id.text_view_notes);
        buttonPractice = findViewById(R.id.button_practice);
        fabFavorite = findViewById(R.id.fab_favorite);
        
        // Initialize view model
        wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        
        // Get word ID from intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("word_id")) {
            int wordId = intent.getIntExtra("word_id", -1);
            loadWord(wordId);
        } else {
            Toast.makeText(this, "Error loading word", Toast.LENGTH_SHORT).show();
            finish();
        }
        
        // Set up click listeners
        buttonPractice.setOnClickListener(v -> startPractice());
        
        fabFavorite.setOnClickListener(v -> toggleFavorite());
        
        wordImageView.setOnImageClickListener(imageUrl -> {
            // Show full screen image view
            Toast.makeText(this, "Image fullscreen view would be shown here", Toast.LENGTH_SHORT).show();
        });
        
        // Update UI based on language
        updateUILanguage();
    }
    
    @Override
    protected void updateUILanguage() {
        if (currentWord != null) {
            setTitle(isHindiActive() ? currentWord.getHindiWord() : currentWord.getEnglishWord());
        }
        
        // Update button text
        buttonPractice.setText(isHindiActive() ? 
                "इस शब्द का अभ्यास करें" : 
                "Practice This Word");
    }
    
    /**
     * Load word details from database
     */
    private void loadWord(int wordId) {
        wordViewModel.getWordById(wordId).observe(this, word -> {
            if (word != null) {
                currentWord = word;
                populateWordDetails();
            }
        });
    }
    
    /**
     * Populate the UI with word details
     */
    private void populateWordDetails() {
        // Set activity title
        setTitle(isHindiActive() ? currentWord.getHindiWord() : currentWord.getEnglishWord());
        
        // Set word image
        if (currentWord.hasImage() && currentWord.getImageUrl() != null && !currentWord.getImageUrl().isEmpty()) {
            wordImageView.setImageUrl(currentWord.getImageUrl());
            wordImageView.setCaptionText(isHindiActive() ? currentWord.getHindiWord() : currentWord.getEnglishWord());
            wordImageView.setVisibility(View.VISIBLE);
        } else {
            wordImageView.setVisibility(View.GONE);
        }
        
        // Set English pronunciation
        englishPronunciationView.setWordText(currentWord.getEnglishWord());
        englishPronunciationView.setPronunciationText(currentWord.getEnglishPronunciation());
        
        if (currentWord.hasEnglishAudio() && currentWord.getEnglishAudioFileName() != null) {
            englishPronunciationView.setAudioFile(currentWord.getEnglishAudioFileName());
        }
        
        // Set Hindi pronunciation
        hindiPronunciationView.setWordText(currentWord.getHindiWord());
        hindiPronunciationView.setPronunciationText(currentWord.getHindiPronunciation());
        
        if (currentWord.hasHindiAudio() && currentWord.getHindiAudioFileName() != null) {
            hindiPronunciationView.setAudioFile(currentWord.getHindiAudioFileName());
        }
        
        // Set part of speech
        if (currentWord.getPartOfSpeech() != null && !currentWord.getPartOfSpeech().isEmpty()) {
            textViewPartOfSpeech.setText(currentWord.getPartOfSpeech());
            textViewPartOfSpeech.setVisibility(View.VISIBLE);
        } else {
            textViewPartOfSpeech.setVisibility(View.GONE);
        }
        
        // Set example sentences
        if (currentWord.getExampleSentenceEnglish() != null && !currentWord.getExampleSentenceEnglish().isEmpty()) {
            textViewEnglishExample.setText(currentWord.getExampleSentenceEnglish());
        } else {
            textViewEnglishExample.setText("No example available");
        }
        
        if (currentWord.getExampleSentenceHindi() != null && !currentWord.getExampleSentenceHindi().isEmpty()) {
            textViewHindiExample.setText(currentWord.getExampleSentenceHindi());
        } else {
            textViewHindiExample.setText("कोई उदाहरण उपलब्ध नहीं है");
        }
        
        // Set category
        textViewCategory.setText(currentWord.getCategory());
        
        // Set difficulty
        String[] difficultyLevels = {"Beginner", "Elementary", "Intermediate", "Advanced", "Expert"};
        int difficulty = Math.min(Math.max(currentWord.getDifficulty(), 1), 5);
        textViewDifficulty.setText(difficultyLevels[difficulty - 1]);
        
        // Set mastery level
        progressMastery.setProgress(currentWord.getMasteryLevel());
        
        // Set usage context
        if (currentWord.getUsageContext() != null && !currentWord.getUsageContext().isEmpty()) {
            textViewUsageContext.setText(currentWord.getUsageContext());
            layoutUsageContext.setVisibility(View.VISIBLE);
        } else {
            layoutUsageContext.setVisibility(View.GONE);
        }
        
        // Set notes
        if (currentWord.getNotes() != null && !currentWord.getNotes().isEmpty()) {
            textViewNotes.setText(currentWord.getNotes());
            layoutNotes.setVisibility(View.VISIBLE);
        } else {
            layoutNotes.setVisibility(View.GONE);
        }
        
        // Update favorite button
        updateFavoriteButton();
        
        // Record word view for gamification
        gamificationManager.recordWordLearned(currentWord);
    }
    
    /**
     * Update the favorite button appearance based on current word status
     */
    private void updateFavoriteButton() {
        if (currentWord.isFavorite()) {
            fabFavorite.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            fabFavorite.setImageResource(android.R.drawable.btn_star_big_off);
        }
    }
    
    /**
     * Toggle the favorite status of the current word
     */
    private void toggleFavorite() {
        if (currentWord != null) {
            boolean newStatus = !currentWord.isFavorite();
            currentWord.setFavorite(newStatus);
            
            wordViewModel.update(currentWord);
            updateFavoriteButton();
            
            // Show confirmation message
            Toast.makeText(this, 
                    newStatus ? R.string.add_to_favorites : R.string.remove_from_favorites, 
                    Toast.LENGTH_SHORT).show();
            
            // Award points for adding to favorites
            if (newStatus) {
                gamificationManager.addPoints(5, "Added to favorites: " + currentWord.getEnglishWord(), 
                        PointsSource.WORD_MASTERY, currentWord.getId());
            }
        }
    }
    
    /**
     * Start practice activity for this word
     */
    private void startPractice() {
        // In a real implementation, this would create a practice session
        // and launch the appropriate activity. For now, we'll just show a toast.
        Toast.makeText(this, "Practice activity would be launched here", Toast.LENGTH_SHORT).show();
        
        // Example of how this would be implemented:
        /*
        Intent intent = new Intent(this, PracticeActivity.class);
        intent.putExtra("exercise_type", ExerciseType.MULTIPLE_CHOICE.name());
        intent.putExtra("word_id", currentWord.getId());
        startActivity(intent);
        */
    }
    
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release audio resources
        audioManager.release();
    }
}