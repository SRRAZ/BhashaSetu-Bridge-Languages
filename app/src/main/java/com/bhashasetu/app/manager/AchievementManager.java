package com.bhashasetu.app.manager;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bhashasetu.app.model.Achievement;
import com.bhashasetu.app.model.AchievementRegistry;
import com.bhashasetu.app.model.Word;
import com.bhashasetu.app.ui.AchievementUnlockedDialog;
import com.bhashasetu.app.viewmodel.AchievementViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Manager class for tracking and handling achievements
 */
public class AchievementManager {
    private static AchievementManager instance;
    
    private final Context context;
    private final AchievementViewModel viewModel;
    private final SharedPreferences preferences;
    private final Handler mainHandler;
    
    private static final String PREF_NAME = "achievement_manager_prefs";
    private static final String KEY_LAST_LOGIN_DATE = "last_login_date";
    private static final String KEY_STREAK_DAYS = "streak_days";
    private static final String KEY_WORD_CATEGORIES = "word_categories";
    
    private final MutableLiveData<List<Achievement>> achievementQueue = new MutableLiveData<>(new ArrayList<>());
    private boolean isShowingAchievement = false;
    
    private AchievementManager(Context context) {
        this.context = context.getApplicationContext();
        this.viewModel = new AchievementViewModel((Application) this.context);
        this.preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.mainHandler = new Handler(Looper.getMainLooper());
        
        // Observe last unlocked achievement
        viewModel.getLastUnlockedAchievement().observeForever(achievement -> {
            if (achievement != null) {
                queueAchievementUnlock(achievement);
                viewModel.clearLastUnlockedAchievement();
            }
        });
    }
    
    public static synchronized AchievementManager getInstance(Context context) {
        if (instance == null) {
            instance = new AchievementManager(context);
        }
        return instance;
    }
    
    /**
     * Called when a user logs in to the app
     */
    public void trackAppOpen() {
        long today = System.currentTimeMillis();
        long lastLogin = preferences.getLong(KEY_LAST_LOGIN_DATE, 0);
        
        // Check if it's a new day (86400000 ms = 1 day)
        if (today - lastLogin > 86400000) {
            if (today - lastLogin < 172800000) { // less than 2 days (continuing streak)
                // Increment streak
                int currentStreak = preferences.getInt(KEY_STREAK_DAYS, 0);
                currentStreak++;
                preferences.edit().putInt(KEY_STREAK_DAYS, currentStreak).apply();
                
                // Check streak achievements
                checkStreakAchievements(currentStreak);
            } else {
                // Reset streak if more than 2 days have passed
                preferences.edit().putInt(KEY_STREAK_DAYS, 1).apply();
            }
            
            // Update last login date
            preferences.edit().putLong(KEY_LAST_LOGIN_DATE, today).apply();
        }
    }
    
    private void checkStreakAchievements(int streakDays) {
        if (streakDays >= 3) {
            trackAchievementProgress(AchievementRegistry.ACHIEVEMENT_DAILY_STREAK_3, streakDays);
        }
        
        if (streakDays >= 7) {
            trackAchievementProgress(AchievementRegistry.ACHIEVEMENT_DAILY_STREAK_7, streakDays);
        }
        
        if (streakDays >= 30) {
            trackAchievementProgress(AchievementRegistry.ACHIEVEMENT_DAILY_STREAK_30, streakDays);
        }
    }
    
    /**
     * Track when a word is learned
     */
    public void trackWordLearned(Word word) {
        // First word achievement
        trackAchievementProgress(AchievementRegistry.ACHIEVEMENT_FIRST_WORD, 1);
        
        // Count achievements
        LiveData<List<Achievement>> allAchievementsLiveData = viewModel.getAllAchievements();
        List<Achievement> allAchievements = allAchievementsLiveData.getValue();
        
        if (allAchievements != null) {
            // Find the word count achievements
            for (Achievement achievement : allAchievements) {
                if (achievement.getId().equals(AchievementRegistry.ACHIEVEMENT_TEN_WORDS) ||
                        achievement.getId().equals(AchievementRegistry.ACHIEVEMENT_FIFTY_WORDS) ||
                        achievement.getId().equals(AchievementRegistry.ACHIEVEMENT_HUNDRED_WORDS)) {
                    // Increment word count progress for all word count achievements
                    viewModel.incrementAchievementProgress(achievement.getId());
                }
            }
        }
        
        // Track word categories for the "wordsmith" achievement
        if (word.getCategory() != null && !word.getCategory().isEmpty()) {
            Set<String> categories = preferences.getStringSet(KEY_WORD_CATEGORIES, new HashSet<>());
            Set<String> updatedCategories = new HashSet<>(categories);
            updatedCategories.add(word.getCategory());
            
            preferences.edit().putStringSet(KEY_WORD_CATEGORIES, updatedCategories).apply();
            
            // Check if we've learned words from all categories
            if (updatedCategories.size() >= getRequiredCategoriesCount()) {
                trackAchievementProgress(AchievementRegistry.ACHIEVEMENT_WORDSMITH, 1);
            }
        }
    }
    
    // This would need to be updated with the actual number of word categories in the app
    private int getRequiredCategoriesCount() {
        return 5; // Example: 5 categories required for the wordsmith achievement
    }
    
    /**
     * Track when a game is played
     */
    public void trackGamePlayed(String gameType) {
        // First game achievement
        trackAchievementProgress(AchievementRegistry.ACHIEVEMENT_FIRST_GAME, 1);
        
        // Game master achievement
        trackAchievementProgress(AchievementRegistry.ACHIEVEMENT_GAME_MASTER, 1);
    }
    
    /**
     * Track when a perfect score is achieved in Word Scramble
     */
    public void trackWordScramblePerfectScore() {
        trackAchievementProgress(AchievementRegistry.ACHIEVEMENT_SCRAMBLE_PRO, 1);
    }
    
    /**
     * Track when Picture Match is completed with no errors
     */
    public void trackPictureMatchNoErrors() {
        trackAchievementProgress(AchievementRegistry.ACHIEVEMENT_MATCHING_EXPERT, 1);
    }
    
    /**
     * Track pronunciation practice
     */
    public void trackPronunciationPractice() {
        trackAchievementProgress(AchievementRegistry.ACHIEVEMENT_FIRST_PRONUNCIATION, 1);
        trackAchievementProgress(AchievementRegistry.ACHIEVEMENT_PRONUNCIATION_PRO, 1);
    }
    
    /**
     * Track perfect pronunciation score (95%+ match)
     */
    public void trackPerfectPronunciation() {
        trackAchievementProgress(AchievementRegistry.ACHIEVEMENT_PERFECT_PRONUNCIATION, 1);
    }
    
    /**
     * Track voice recording for the "talkative" achievement
     */
    public void trackVoiceRecording() {
        trackAchievementProgress(AchievementRegistry.ACHIEVEMENT_TALKATIVE, 1);
    }
    
    /**
     * Track achievement progress
     */
    public void trackAchievementProgress(String achievementId, int progressIncrement) {
        if (progressIncrement <= 0) return;
        
        if (progressIncrement == 1) {
            viewModel.incrementAchievementProgress(achievementId);
        } else {
            LiveData<Achievement> achievementLiveData = viewModel.getAchievementById(achievementId);
            Achievement achievement = achievementLiveData.getValue();
            
            if (achievement != null) {
                int currentProgress = achievement.getProgressCurrent();
                viewModel.updateProgress(achievementId, currentProgress + progressIncrement);
            }
        }
    }
    
    private void queueAchievementUnlock(Achievement achievement) {
        List<Achievement> queue = achievementQueue.getValue();
        if (queue == null) {
            queue = new ArrayList<>();
        }
        
        // Only add if not already in queue
        boolean alreadyQueued = false;
        for (Achievement queued : queue) {
            if (queued.getId().equals(achievement.getId())) {
                alreadyQueued = true;
                break;
            }
        }
        
        if (!alreadyQueued) {
            queue.add(achievement);
            achievementQueue.setValue(queue);
            showNextAchievement();
        }
    }
    
    private void showNextAchievement() {
        if (isShowingAchievement) return;
        
        List<Achievement> queue = achievementQueue.getValue();
        if (queue != null && !queue.isEmpty()) {
            isShowingAchievement = true;
            Achievement achievement = queue.get(0);
            
            mainHandler.post(() -> {
                AchievementUnlockedDialog dialog = new AchievementUnlockedDialog(context, achievement);
                dialog.setOnDismissListener(dialogInterface -> {
                    isShowingAchievement = false;
                    
                    // Remove from queue and show next
                    List<Achievement> updatedQueue = achievementQueue.getValue();
                    if (updatedQueue != null && !updatedQueue.isEmpty()) {
                        updatedQueue.remove(0);
                        achievementQueue.setValue(updatedQueue);
                        
                        // Small delay before showing next achievement
                        mainHandler.postDelayed(this::showNextAchievement, 500);
                    }
                });
                dialog.show();
            });
        }
    }
    
    /**
     * Get the current user points from unlocked achievements
     */
    public int getUserPoints() {
        return viewModel.getTotalPoints();
    }
    
    /**
     * Get the achievement view model for UI components
     */
    public AchievementViewModel getViewModel() {
        return viewModel;
    }
}