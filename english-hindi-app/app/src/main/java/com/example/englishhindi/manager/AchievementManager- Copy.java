package com.example.englishhindi.manager;

import android.app.Activity;
import android.content.Context;

import com.example.englishhindi.database.AchievementRepository;
import com.example.englishhindi.model.Achievement;
import com.example.englishhindi.model.AchievementRegistry;
import com.example.englishhindi.ui.AchievementUnlockedDialog;
import com.example.englishhindi.viewmodel.AchievementViewModel;

/**
 * Manager class for tracking and handling achievements.
 * This is a singleton that provides a simplified interface for updating achievements.
 */
public class AchievementManager {

    private static AchievementManager instance;
    
    private Context applicationContext;
    private AchievementViewModel viewModel;
    
    /**
     * Get the AchievementManager instance.
     * 
     * @param context The context
     * @return The AchievementManager instance
     */
    public static synchronized AchievementManager getInstance(Context context) {
        if (instance == null) {
            instance = new AchievementManager(context.getApplicationContext());
        }
        return instance;
    }
    
    /**
     * Private constructor to enforce singleton pattern.
     * 
     * @param context The application context
     */
    private AchievementManager(Context context) {
        this.applicationContext = context;
    }
    
    /**
     * Set the ViewModel to use for achievement operations.
     * 
     * @param viewModel The AchievementViewModel
     */
    public void setViewModel(AchievementViewModel viewModel) {
        this.viewModel = viewModel;
    }
    
    /**
     * Track a vocabulary word learned.
     * 
     * @param activity The activity for displaying dialogs
     */
    public void trackWordLearned(Activity activity) {
        if (viewModel == null) return;
        
        viewModel.checkTypeAchievements(Achievement.TYPE_VOCABULARY, 1, 
                achievement -> showAchievementUnlockedDialog(activity, achievement));
    }
    
    /**
     * Track a pronunciation practice.
     * 
     * @param activity The activity for displaying dialogs
     * @param correctPronunciation Whether the pronunciation was correct
     */
    public void trackPronunciationPractice(Activity activity, boolean correctPronunciation) {
        if (viewModel == null) return;
        
        // Track pronunciation practice
        viewModel.checkTypeAchievements(Achievement.TYPE_PRONUNCIATION, 1, 
                achievement -> showAchievementUnlockedDialog(activity, achievement));
        
        // Track pronunciation expert achievement separately
        if (correctPronunciation) {
            viewModel.updateAchievementProgress(
                    AchievementRegistry.PRONUNCIATION_EXPERT, 1,
                    achievement -> showAchievementUnlockedDialog(activity, achievement));
        }
    }
    
    /**
     * Track a game played.
     * 
     * @param activity The activity for displaying dialogs
     * @param gameType The type of game (word_scramble, picture_match, etc.)
     * @param score The game score
     */
    public void trackGamePlayed(Activity activity, String gameType, int score) {
        if (viewModel == null) return;
        
        // Track general game achievements
        viewModel.checkTypeAchievements(Achievement.TYPE_GAME, 1, 
                achievement -> showAchievementUnlockedDialog(activity, achievement));
        
        // Track game-specific achievements
        switch (gameType) {
            case "word_scramble":
                viewModel.updateAchievementProgress(
                        AchievementRegistry.WORD_SCRAMBLE_MASTER, 1,
                        achievement -> showAchievementUnlockedDialog(activity, achievement));
                break;
                
            case "picture_match":
                viewModel.updateAchievementProgress(
                        AchievementRegistry.PICTURE_MATCH_EXPERT, 1,
                        achievement -> showAchievementUnlockedDialog(activity, achievement));
                break;
                
            case "flashcard":
                viewModel.updateAchievementProgress(
                        AchievementRegistry.FLASHCARD_GURU, 1,
                        achievement -> showAchievementUnlockedDialog(activity, achievement));
                break;
        }
        
        // Check for perfect score achievement
        if (score >= 100) {
            viewModel.updateAchievementProgress(
                    AchievementRegistry.GAME_CHAMPION, 1,
                    achievement -> showAchievementUnlockedDialog(activity, achievement));
            
            // Perfect score is a one-time achievement
            viewModel.updateAchievementProgress(
                    AchievementRegistry.PERFECT_SCORE, 1,
                    achievement -> showAchievementUnlockedDialog(activity, achievement));
        }
    }
    
    /**
     * Track a daily streak update.
     * 
     * @param activity The activity for displaying dialogs
     * @param streakDays The current streak in days
     */
    public void trackDailyStreak(Activity activity, int streakDays) {
        if (viewModel == null) return;
        
        // Track streak starter (3 days)
        if (streakDays >= 3) {
            viewModel.updateAchievementProgress(
                    AchievementRegistry.STREAK_STARTER, 3,
                    achievement -> showAchievementUnlockedDialog(activity, achievement));
        }
        
        // Track streak consistent (7 days)
        if (streakDays >= 7) {
            viewModel.updateAchievementProgress(
                    AchievementRegistry.STREAK_CONSISTENT, 7,
                    achievement -> showAchievementUnlockedDialog(activity, achievement));
        }
        
        // Track streak dedicated (30 days)
        if (streakDays >= 30) {
            viewModel.updateAchievementProgress(
                    AchievementRegistry.STREAK_DEDICATED, 30,
                    achievement -> showAchievementUnlockedDialog(activity, achievement));
        }
    }
    
    /**
     * Track a word mastery level update.
     * 
     * @param activity The activity for displaying dialogs
     * @param masteryLevel The mastery level (0-100)
     */
    public void trackWordMastery(Activity activity, int masteryLevel) {
        if (viewModel == null) return;
        
        // Different achievements based on mastery level
        if (masteryLevel >= 50) {
            viewModel.updateAchievementProgress(
                    AchievementRegistry.MASTERY_BEGINNER, 1,
                    achievement -> showAchievementUnlockedDialog(activity, achievement));
        }
        
        if (masteryLevel >= 75) {
            viewModel.updateAchievementProgress(
                    AchievementRegistry.MASTERY_INTERMEDIATE, 1,
                    achievement -> showAchievementUnlockedDialog(activity, achievement));
        }
        
        if (masteryLevel >= 100) {
            viewModel.updateAchievementProgress(
                    AchievementRegistry.MASTERY_ADVANCED, 1,
                    achievement -> showAchievementUnlockedDialog(activity, achievement));
        }
    }
    
    /**
     * Track a language switch event.
     * 
     * @param activity The activity for displaying dialogs
     */
    public void trackLanguageSwitch(Activity activity) {
        if (viewModel == null) return;
        
        // Track multilingual achievement
        viewModel.updateAchievementProgress(
                AchievementRegistry.MULTILINGUAL, 1,
                achievement -> showAchievementUnlockedDialog(activity, achievement));
    }
    
    /**
     * Track study time for special time-based achievements.
     * 
     * @param activity The activity for displaying dialogs
     * @param hourOfDay The hour of day (0-23)
     */
    public void trackStudyTime(Activity activity, int hourOfDay) {
        if (viewModel == null) return;
        
        // Early bird achievement (before 8am)
        if (hourOfDay < 8) {
            viewModel.updateAchievementProgress(
                    AchievementRegistry.EARLY_BIRD, 1,
                    achievement -> showAchievementUnlockedDialog(activity, achievement));
        }
        
        // Night owl achievement (after 10pm)
        if (hourOfDay >= 22) {
            viewModel.updateAchievementProgress(
                    AchievementRegistry.NIGHT_OWL, 1,
                    achievement -> showAchievementUnlockedDialog(activity, achievement));
        }
    }
    
    /**
     * Show a dialog when an achievement is unlocked.
     * 
     * @param activity The activity to show the dialog in
     * @param achievement The unlocked achievement
     */
    private void showAchievementUnlockedDialog(Activity activity, Achievement achievement) {
        if (activity == null || achievement == null) return;
        
        // Show achievement unlocked dialog on UI thread
        activity.runOnUiThread(() -> {
            AchievementUnlockedDialog dialog = new AchievementUnlockedDialog(activity, achievement);
            dialog.show();
        });
    }
}