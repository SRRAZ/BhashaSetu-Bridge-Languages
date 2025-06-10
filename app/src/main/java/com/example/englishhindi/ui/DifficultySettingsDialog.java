package com.bhashasetu.app.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.bhashasetu.app.R;
import com.bhashasetu.app.manager.UserProgressManager;
import com.bhashasetu.app.model.DifficultyLevel;
import com.bhashasetu.app.model.DifficultyManager;
import com.bhashasetu.app.model.UserProgress;

import java.util.HashMap;
import java.util.Map;

/**
 * Dialog for customizing difficulty settings across different skill areas.
 * Allows users to set individual difficulty levels for each activity type.
 */
public class DifficultySettingsDialog extends Dialog {

    private UserProgressManager progressManager;
    private UserProgress userProgress;
    private Map<String, Integer> difficultySettings = new HashMap<>();
    
    // UI Components
    private SeekBar seekVocabulary;
    private SeekBar seekPronunciation;
    private SeekBar seekGrammar;
    private SeekBar seekListening;
    private SeekBar seekReading;
    private SeekBar seekWriting;
    private SeekBar seekGames;
    
    private TextView tvVocabularyLevel;
    private TextView tvPronunciationLevel;
    private TextView tvGrammarLevel;
    private TextView tvListeningLevel;
    private TextView tvReadingLevel;
    private TextView tvWritingLevel;
    private TextView tvGamesLevel;
    
    private Button btnReset;
    private Button btnSave;
    
    private OnDifficultySettingsChangedListener listener;

    public DifficultySettingsDialog(@NonNull Context context) {
        super(context);
        progressManager = UserProgressManager.getInstance(context);
        userProgress = progressManager.getCachedProgress();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_difficulty_settings);
        
        // Initialize UI components
        initializeViews();
        
        // Load current settings
        loadCurrentSettings();
        
        // Set up listeners
        setupListeners();
    }
    
    private void initializeViews() {
        // Seek bars
        seekVocabulary = findViewById(R.id.seek_vocabulary);
        seekPronunciation = findViewById(R.id.seek_pronunciation);
        seekGrammar = findViewById(R.id.seek_grammar);
        seekListening = findViewById(R.id.seek_listening);
        seekReading = findViewById(R.id.seek_reading);
        seekWriting = findViewById(R.id.seek_writing);
        seekGames = findViewById(R.id.seek_games);
        
        // Text views
        tvVocabularyLevel = findViewById(R.id.tv_vocabulary_level);
        tvPronunciationLevel = findViewById(R.id.tv_pronunciation_level);
        tvGrammarLevel = findViewById(R.id.tv_grammar_level);
        tvListeningLevel = findViewById(R.id.tv_listening_level);
        tvReadingLevel = findViewById(R.id.tv_reading_level);
        tvWritingLevel = findViewById(R.id.tv_writing_level);
        tvGamesLevel = findViewById(R.id.tv_games_level);
        
        // Buttons
        btnReset = findViewById(R.id.btn_reset);
        btnSave = findViewById(R.id.btn_save);
    }
    
    private void loadCurrentSettings() {
        if (userProgress == null) {
            return;
        }
        
        // Get current settings from user progress
        Map<String, Integer> activitySettings = userProgress.getActivityDifficultySettings();
        
        // Initialize our local settings map
        difficultySettings.putAll(activitySettings);
        
        // Set seek bar positions
        setSeekBarPosition(seekVocabulary, DifficultyManager.ACTIVITY_VOCABULARY);
        setSeekBarPosition(seekPronunciation, DifficultyManager.ACTIVITY_PRONUNCIATION);
        setSeekBarPosition(seekGrammar, DifficultyManager.ACTIVITY_GRAMMAR);
        setSeekBarPosition(seekListening, DifficultyManager.ACTIVITY_LISTENING);
        setSeekBarPosition(seekReading, DifficultyManager.ACTIVITY_READING);
        setSeekBarPosition(seekWriting, DifficultyManager.ACTIVITY_WRITING);
        setSeekBarPosition(seekGames, DifficultyManager.ACTIVITY_GAMES);
        
        // Update level text views
        updateLevelText(tvVocabularyLevel, getActivityDifficulty(DifficultyManager.ACTIVITY_VOCABULARY));
        updateLevelText(tvPronunciationLevel, getActivityDifficulty(DifficultyManager.ACTIVITY_PRONUNCIATION));
        updateLevelText(tvGrammarLevel, getActivityDifficulty(DifficultyManager.ACTIVITY_GRAMMAR));
        updateLevelText(tvListeningLevel, getActivityDifficulty(DifficultyManager.ACTIVITY_LISTENING));
        updateLevelText(tvReadingLevel, getActivityDifficulty(DifficultyManager.ACTIVITY_READING));
        updateLevelText(tvWritingLevel, getActivityDifficulty(DifficultyManager.ACTIVITY_WRITING));
        updateLevelText(tvGamesLevel, getActivityDifficulty(DifficultyManager.ACTIVITY_GAMES));
    }
    
    private void setupListeners() {
        // Set listeners for seek bars
        setupSeekBarListener(seekVocabulary, tvVocabularyLevel, DifficultyManager.ACTIVITY_VOCABULARY);
        setupSeekBarListener(seekPronunciation, tvPronunciationLevel, DifficultyManager.ACTIVITY_PRONUNCIATION);
        setupSeekBarListener(seekGrammar, tvGrammarLevel, DifficultyManager.ACTIVITY_GRAMMAR);
        setupSeekBarListener(seekListening, tvListeningLevel, DifficultyManager.ACTIVITY_LISTENING);
        setupSeekBarListener(seekReading, tvReadingLevel, DifficultyManager.ACTIVITY_READING);
        setupSeekBarListener(seekWriting, tvWritingLevel, DifficultyManager.ACTIVITY_WRITING);
        setupSeekBarListener(seekGames, tvGamesLevel, DifficultyManager.ACTIVITY_GAMES);
        
        // Set button listeners
        btnReset.setOnClickListener(v -> resetToDefaults());
        
        btnSave.setOnClickListener(v -> {
            saveSettings();
            dismiss();
        });
    }
    
    private void setupSeekBarListener(SeekBar seekBar, TextView levelText, String activityType) {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Convert from 0-4 to 1-5
                int level = progress + 1;
                
                // Update the level text
                updateLevelText(levelText, level);
                
                // Update our settings map
                difficultySettings.put(activityType, level);
            }
            
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not needed
            }
            
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Not needed
            }
        });
    }
    
    private void setSeekBarPosition(SeekBar seekBar, String activityType) {
        // Adjust from 1-5 scale to 0-4 for SeekBar
        int level = getActivityDifficulty(activityType);
        seekBar.setProgress(level - 1);
    }
    
    private int getActivityDifficulty(String activityType) {
        // Get from our settings map, or user progress, or default to user level
        if (difficultySettings.containsKey(activityType)) {
            return difficultySettings.get(activityType);
        }
        
        if (userProgress != null) {
            return userProgress.getActivityDifficulty(activityType);
        }
        
        return 1; // Default to beginner
    }
    
    private void updateLevelText(TextView textView, int level) {
        DifficultyLevel difficultyLevel = DifficultyLevel.fromLevel(level);
        textView.setText(getContext().getString(R.string.difficulty_level_format, 
                difficultyLevel.getName(), level));
    }
    
    private void resetToDefaults() {
        // Clear our settings map
        difficultySettings.clear();
        
        // Default to user's current level
        int userLevel = userProgress != null ? userProgress.getCurrentLevel() : 1;
        
        // Reset seek bars
        seekVocabulary.setProgress(userLevel - 1);
        seekPronunciation.setProgress(userLevel - 1);
        seekGrammar.setProgress(userLevel - 1);
        seekListening.setProgress(userLevel - 1);
        seekReading.setProgress(userLevel - 1);
        seekWriting.setProgress(userLevel - 1);
        seekGames.setProgress(userLevel - 1);
        
        // Update level texts
        updateLevelText(tvVocabularyLevel, userLevel);
        updateLevelText(tvPronunciationLevel, userLevel);
        updateLevelText(tvGrammarLevel, userLevel);
        updateLevelText(tvListeningLevel, userLevel);
        updateLevelText(tvReadingLevel, userLevel);
        updateLevelText(tvWritingLevel, userLevel);
        updateLevelText(tvGamesLevel, userLevel);
    }
    
    private void saveSettings() {
        if (userProgress == null || progressManager == null) {
            return;
        }
        
        // Save each activity's difficulty level
        for (Map.Entry<String, Integer> entry : difficultySettings.entrySet()) {
            progressManager.setActivityDifficulty(entry.getKey(), entry.getValue());
        }
        
        // Notify listener
        if (listener != null) {
            listener.onDifficultySettingsChanged(difficultySettings);
        }
    }
    
    /**
     * Set listener for difficulty settings changes
     * @param listener The listener to set
     */
    public void setOnDifficultySettingsChangedListener(OnDifficultySettingsChangedListener listener) {
        this.listener = listener;
    }
    
    /**
     * Interface for handling difficulty settings changes
     */
    public interface OnDifficultySettingsChangedListener {
        void onDifficultySettingsChanged(Map<String, Integer> settings);
    }
}