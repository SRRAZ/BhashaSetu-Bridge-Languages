package com.bhashasetu.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;import android.widget.Toast;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bhashasetu.app.model.Word;
import com.bhashasetu.app.viewmodel.WordViewModel;

public class WordDetailActivity extends AppCompatActivity {
    
    public static final String EXTRA_WORD_ID = "com.bhashasetu.app.EXTRA_WORD_ID";
    public static final String EXTRA_ENGLISH_WORD = "com.bhashasetu.app.EXTRA_ENGLISH_WORD";
    public static final String EXTRA_HINDI_WORD = "com.bhashasetu.app.EXTRA_HINDI_WORD";
    public static final String EXTRA_ENGLISH_PRONUNCIATION = "com.bhashasetu.app.EXTRA_ENGLISH_PRONUNCIATION";
    public static final String EXTRA_HINDI_PRONUNCIATION = "com.bhashasetu.app.EXTRA_HINDI_PRONUNCIATION";
    public static final String EXTRA_CATEGORY = "com.bhashasetu.app.EXTRA_CATEGORY";
    public static final String EXTRA_IS_FAVORITE = "com.bhashasetu.app.EXTRA_IS_FAVORITE";
    
    private WordViewModel wordViewModel;
    private int wordId;
    private boolean isFavorite;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_detail);
        
        // Initialize ViewModel
        wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        
        // Get views
        TextView textViewEnglishWord = findViewById(R.id.text_view_english_word_detail);
        TextView textViewHindiWord = findViewById(R.id.text_view_hindi_word_detail);
        TextView textViewEnglishPronunciation = findViewById(R.id.text_view_english_pronunciation);
        TextView textViewHindiPronunciation = findViewById(R.id.text_view_hindi_pronunciation);
        TextView textViewCategory = findViewById(R.id.text_view_category_detail);
        Button buttonFavorite = findViewById(R.id.button_favorite);
        
        // Get intent data
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_WORD_ID)) {
            wordId = intent.getIntExtra(EXTRA_WORD_ID, -1);
            String englishWord = intent.getStringExtra(EXTRA_ENGLISH_WORD);
            String hindiWord = intent.getStringExtra(EXTRA_HINDI_WORD);
            String englishPronunciation = intent.getStringExtra(EXTRA_ENGLISH_PRONUNCIATION);
            String hindiPronunciation = intent.getStringExtra(EXTRA_HINDI_PRONUNCIATION);
            String category = intent.getStringExtra(EXTRA_CATEGORY);
            isFavorite = intent.getBooleanExtra(EXTRA_IS_FAVORITE, false);
            
            // Set data to views
            textViewEnglishWord.setText(englishWord);
            textViewHindiWord.setText(hindiWord);
            textViewEnglishPronunciation.setText(englishPronunciation);
            textViewHindiPronunciation.setText(hindiPronunciation);
            textViewCategory.setText(category);
            
            // Set button text based on favorite status
            updateFavoriteButton(buttonFavorite);
            
            // Set title
            setTitle(englishWord);
        } else {
            finish();
            return;
        }
        
        // Handle favorite button click
        buttonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFavorite();
                updateFavoriteButton(buttonFavorite);
            }
        });
    }
    
    private void toggleFavorite() {
        // Create a Word object with the current data
        String englishWord = getIntent().getStringExtra(EXTRA_ENGLISH_WORD);
        String hindiWord = getIntent().getStringExtra(EXTRA_HINDI_WORD);
        String englishPronunciation = getIntent().getStringExtra(EXTRA_ENGLISH_PRONUNCIATION);
        String hindiPronunciation = getIntent().getStringExtra(EXTRA_HINDI_PRONUNCIATION);
        String category = getIntent().getStringExtra(EXTRA_CATEGORY);
        
        Word word = new Word(englishWord, hindiWord, englishPronunciation, hindiPronunciation, category);
        word.setId(wordId);
        
        // Toggle favorite status
        isFavorite = !isFavorite;
        word.setFavorite(isFavorite);
        
        // Update word in database
        wordViewModel.update(word);
        
        // Show toast
        if (isFavorite) {
            Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show();
        }
    }
    
    private void updateFavoriteButton(Button button) {
        if (isFavorite) {
            button.setText("Remove from Favorites");
        } else {
            button.setText("Add to Favorites");
        }
    }
}