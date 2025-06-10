package com.bhashasetu.app.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.bhashasetu.app.database.UserProgressDao;
import com.bhashasetu.app.database.UserProgressRepository;
import com.bhashasetu.app.database.WordRepository;
import com.bhashasetu.app.model.DifficultyLevel;
import com.bhashasetu.app.model.DifficultyManager;
import com.bhashasetu.app.model.UserProgress;
import com.bhashasetu.app.model.Word;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Manager class for handling user progress tracking, updates, and recommendations.
 * Centralizes logic for XP gains, level progression, and difficulty adaptation.
 */
public class UserProgressManager {

    private static final String PREF_NAME = "user_progress_preferences";
    private static final String KEY_CURRENT_USER_ID = "current_user_id";
    private static final String KEY_LAST_LEVEL_UP_SHOWN = "last_level_up_shown";
    
    private static UserProgressManager instance;
    
    private Context context;
    private UserProgressRepository progressRepository;
    private WordRepository wordRepository;
    private Executor executor;
    private SharedPreferences preferences;
    
    private UserProgress cachedProgress;
    private MediatorLiveData<UserProgress> userProgressLiveData = new MediatorLiveData<>();
    
    private UserProgressManager(Context context) {
        this.context = context.getApplicationContext();
        this.progressRepository = new UserProgressRepository(context);
        this.wordRepository = new WordRepository(context);
        this.executor = Executors.newSingleThreadExecutor();
        this.preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        
        // Set up live data for current user's progress
        loadCurrentUserProgress();
    }
    
    /**
     * Get singleton instance of the manager
     * @param context Application context
     * @return The UserProgressManager instance
     */
    public static synchronized UserProgressManager getInstance(Context context) {
        if (instance == null) {
            instance = new UserProgressManager(context);
        }
        return instance;
    }
    
    /**
     * Load the current user's progress
     */
    private void loadCurrentUserProgress() {
        // Get current user ID from preferences
        long currentUserId = preferences.getLong(KEY_CURRENT_USER_ID, 1);
        
        // Observe user progress from repository
        LiveData<UserProgress> repositoryData = progressRepository.getUserProgressLiveData(currentUserId);
        userProgressLiveData.addSource(repositoryData, progress -> {
            cachedProgress = progress;
            userProgressLiveData.setValue(progress);
        });
    }
    
    /**
     * Get the current user's progress as LiveData
     * @return LiveData object that will emit user progress updates
     */
    public LiveData<UserProgress> getUserProgressLiveData() {
        return userProgressLiveData;
    }
    
    /**
     * Get the current user's progress (cached version)
     * @return UserProgress object or null if not loaded
     */
    public UserProgress getCachedProgress() {
        return cachedProgress;
    }
    
    /**
     * Set the current user ID
     * @param userId User ID
     */
    public void setCurrentUser(long userId) {
        preferences.edit().putLong(KEY_CURRENT_USER_ID, userId).apply();
        loadCurrentUserProgress();
    }
    
    /**
     * Create a new progress record for a user
     * @param userId User ID
     */
    public void createNewUserProgress(long userId) {
        executor.execute(() -> {
            UserProgress newProgress = new UserProgress(userId);
            progressRepository.insertUserProgress(newProgress);
        });
    }
    
    /**
     * Add XP to the current user's progress
     * @param xpPoints XP points to add
     * @param activity Activity type (for tracking)
     * @param callback Callback with the result, including whether a level-up occurred
     */
    public void addXp(int xpPoints, String activity, XpUpdateCallback callback) {
        if (cachedProgress == null) {
            if (callback != null) {
                callback.onXpUpdated(false, 0, 0);
            }
            return;
        }
        
        // Work with a copy to avoid concurrency issues
        UserProgress progress = cachedProgress;
        
        // Record current level before XP update
        int oldLevel = progress.getCurrentLevel();
        
        // Add XP and check for level up
        boolean leveledUp = progress.addXp(xpPoints);
        int newLevel = progress.getCurrentLevel();
        
        // Update last activity
        progress.setLastActivityDate(new Date());
        progress.updateStreak(new Date());
        
        // Save changes
        executor.execute(() -> {
            progressRepository.updateUserProgress(progress);
            
            // Check if we should show level up animation
            int lastLevelUpShown = preferences.getInt(KEY_LAST_LEVEL_UP_SHOWN, 0);
            boolean showLevelUp = leveledUp && newLevel > lastLevelUpShown;
            
            if (showLevelUp) {
                // Update the last level up shown
                preferences.edit().putInt(KEY_LAST_LEVEL_UP_SHOWN, newLevel).apply();
            }
            
            // Notify via callback on the main thread
            if (callback != null) {
                android.os.Handler mainHandler = new android.os.Handler(context.getMainLooper());
                mainHandler.post(() -> callback.onXpUpdated(showLevelUp, oldLevel, newLevel));
            }
        });
    }
    
    /**
     * Update progress for a completed activity
     * @param activityType Activity type identifier
     * @param performancePercent Performance percentage (0-100)
     * @param durationMinutes Duration in minutes
     * @param callback Callback with the result
     */
    public void updateActivityProgress(String activityType, int performancePercent, 
                                      int durationMinutes, ActivityUpdateCallback callback) {
        if (cachedProgress == null) {
            if (callback != null) {
                callback.onActivityUpdated(false, 0, 0);
            }
            return;
        }
        
        // Work with a copy to avoid concurrency issues
        UserProgress progress = cachedProgress;
        
        // Record current level
        int oldLevel = progress.getCurrentLevel();
        
        // Update progress and get earned XP
        int earnedXp = DifficultyManager.updateProgressForActivity(
            progress, activityType, performancePercent, durationMinutes);
        
        // Get new level after update
        int newLevel = progress.getCurrentLevel();
        boolean leveledUp = newLevel > oldLevel;
        
        // Save changes
        executor.execute(() -> {
            progressRepository.updateUserProgress(progress);
            
            // Check if we should show level up animation
            int lastLevelUpShown = preferences.getInt(KEY_LAST_LEVEL_UP_SHOWN, 0);
            boolean showLevelUp = leveledUp && newLevel > lastLevelUpShown;
            
            if (showLevelUp) {
                // Update the last level up shown
                preferences.edit().putInt(KEY_LAST_LEVEL_UP_SHOWN, newLevel).apply();
            }
            
            // Notify via callback on the main thread
            if (callback != null) {
                android.os.Handler mainHandler = new android.os.Handler(context.getMainLooper());
                mainHandler.post(() -> callback.onActivityUpdated(showLevelUp, earnedXp, newLevel));
            }
        });
    }
    
    /**
     * Get recommended difficulty level for an activity
     * @param activityType Activity type identifier
     * @return DifficultyLevel object with appropriate configuration
     */
    public DifficultyLevel getRecommendedDifficulty(String activityType) {
        if (cachedProgress == null) {
            // Default to beginner if no progress loaded
            return DifficultyLevel.fromLevel(DifficultyLevel.BEGINNER);
        }
        
        return DifficultyManager.getDifficultyForActivity(cachedProgress, activityType);
    }
    
    /**
     * Get word recommendations based on user progress
     * @param count Number of words to recommend
     * @param callback Callback with the results
     */
    public void getRecommendedWords(int count, RecommendationCallback<List<Word>> callback) {
        if (cachedProgress == null) {
            if (callback != null) {
                callback.onRecommendationsReady(null);
            }
            return;
        }
        
        // Get all words first, then filter based on progress
        executor.execute(() -> {
            List<Word> allWords = wordRepository.getAllWords();
            List<Long> recommendedIds = DifficultyManager.getRecommendedWords(cachedProgress, allWords, count);
            
            // Fetch the actual word objects
            List<Word> recommendedWords = wordRepository.getWordsByIds(recommendedIds);
            
            // Update recommended words in progress
            for (Word word : recommendedWords) {
                cachedProgress.addRecommendedWord(word.getId());
            }
            progressRepository.updateUserProgress(cachedProgress);
            
            // Notify via callback on the main thread
            if (callback != null) {
                android.os.Handler mainHandler = new android.os.Handler(context.getMainLooper());
                mainHandler.post(() -> callback.onRecommendationsReady(recommendedWords));
            }
        });
    }
    
    /**
     * Get skill recommendations based on user progress
     * @param callback Callback with the results
     */
    public void getRecommendedSkills(RecommendationCallback<List<Pair<String, Double>>> callback) {
        if (cachedProgress == null) {
            if (callback != null) {
                callback.onRecommendationsReady(null);
            }
            return;
        }
        
        // Calculate recommended skills
        List<Pair<String, Double>> recommendedSkills = DifficultyManager.getRecommendedSkills(cachedProgress);
        
        // Update recommended skills in progress
        for (Pair<String, Double> skill : recommendedSkills.subList(0, Math.min(3, recommendedSkills.size()))) {
            cachedProgress.addRecommendedSkill(skill.first);
        }
        executor.execute(() -> {
            progressRepository.updateUserProgress(cachedProgress);
        });
        
        // Notify via callback
        if (callback != null) {
            callback.onRecommendationsReady(recommendedSkills);
        }
    }
    
    /**
     * Update category completion percentage
     * @param categoryId Category ID
     * @param completionPercent Completion percentage (0-100)
     */
    public void updateCategoryCompletion(long categoryId, int completionPercent) {
        if (cachedProgress == null) {
            return;
        }
        
        cachedProgress.updateCategoryCompletion(categoryId, completionPercent);
        executor.execute(() -> {
            progressRepository.updateUserProgress(cachedProgress);
        });
    }
    
    /**
     * Add a word to the user's mastered words list
     * @param wordId Word ID
     */
    public void addMasteredWord(long wordId) {
        if (cachedProgress == null) {
            return;
        }
        
        cachedProgress.addMasteredWord(wordId);
        executor.execute(() -> {
            progressRepository.updateUserProgress(cachedProgress);
        });
    }
    
    /**
     * Set a custom difficulty level for an activity
     * @param activityType Activity type identifier
     * @param difficultyLevel Difficulty level (1-5)
     */
    public void setActivityDifficulty(String activityType, int difficultyLevel) {
        if (cachedProgress == null) {
            return;
        }
        
        cachedProgress.setActivityDifficulty(activityType, difficultyLevel);
        executor.execute(() -> {
            progressRepository.updateUserProgress(cachedProgress);
        });
    }
    
    /**
     * Reset the "last level up shown" tracker, forcing the next level up to show
     */
    public void resetLastLevelUpShown() {
        preferences.edit().putInt(KEY_LAST_LEVEL_UP_SHOWN, 0).apply();
    }
    
    /**
     * Callback interface for XP updates
     */
    public interface XpUpdateCallback {
        void onXpUpdated(boolean leveledUp, int oldLevel, int newLevel);
    }
    
    /**
     * Callback interface for activity updates
     */
    public interface ActivityUpdateCallback {
        void onActivityUpdated(boolean leveledUp, int earnedXp, int newLevel);
    }
    
    /**
     * Callback interface for recommendations
     */
    public interface RecommendationCallback<T> {
        void onRecommendationsReady(T recommendations);
    }
}