package com.bhashasetu.app.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.bhashasetu.app.model.Achievement;
import com.bhashasetu.app.model.AchievementRegistry;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.List;

/**
 * Repository for managing Achievement data.
 */
public class AchievementRepository {

    private AchievementDao achievementDao;
    private LiveData<List<Achievement>> allAchievements;
    private LiveData<List<Achievement>> unlockedAchievements;
    private LiveData<Integer> totalPoints;
    private LiveData<Integer> unlockedCount;
    private LiveData<Integer> totalCount;
    private ExecutorService executor;

    /**
     * Constructor.
     * 
     * @param application The application context
     */
    public AchievementRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        achievementDao = database.achievementDao();
        
        allAchievements = achievementDao.getAllAchievements();
        unlockedAchievements = achievementDao.getUnlockedAchievements();
        totalPoints = achievementDao.getTotalPoints();
        unlockedCount = achievementDao.getUnlockedCount();
        totalCount = achievementDao.getTotalCount();
        
        executor = Executors.newSingleThreadExecutor();
        
        // Initialize achievements if needed
        executor.execute(new InitializeAchievementsTask());
    }

    /**
     * Get all achievements.
     * 
     * @return LiveData list of all achievements
     */
    public LiveData<List<Achievement>> getAllAchievements() {
        return allAchievements;
    }

    /**
     * Get all unlocked achievements.
     * 
     * @return LiveData list of unlocked achievements
     */
    public LiveData<List<Achievement>> getUnlockedAchievements() {
        return unlockedAchievements;
    }

    /**
     * Get achievements by type.
     * 
     * @param type The achievement type
     * @return LiveData list of achievements of the specified type
     */
    public LiveData<List<Achievement>> getAchievementsByType(String type) {
        return achievementDao.getAchievementsByType(type);
    }

    /**
     * Get an achievement by ID.
     * 
     * @param id The achievement ID
     * @return LiveData with the achievement
     */
    public LiveData<Achievement> getAchievementById(String id) {
        return achievementDao.getAchievementById(id);
    }

    /**
     * Get the total achievement points.
     * 
     * @return LiveData with the total points
     */
    public LiveData<Integer> getTotalPoints() {
        return totalPoints;
    }

    /**
     * Get the count of unlocked achievements.
     * 
     * @return LiveData with the unlocked count
     */
    public LiveData<Integer> getUnlockedCount() {
        return unlockedCount;
    }

    /**
     * Get the total count of achievements.
     * 
     * @return LiveData with the total count
     */
    public LiveData<Integer> getTotalCount() {
        return totalCount;
    }

    /**
     * Update an achievement's progress.
     * 
     * @param achievementId The achievement ID
     * @param increment The amount to increment progress by
     * @param callback Callback to be notified if the achievement was unlocked
     */
    public void updateAchievementProgress(String achievementId, int increment, 
                                         OnAchievementUnlockedListener callback) {
        executor.execute(() -> {
            Achievement achievement = achievementDao.getAchievementByIdSync(achievementId);
            if (achievement != null) {
                boolean wasUnlocked = achievement.isUnlocked();
                achievement.incrementProgress(increment);
                achievementDao.update(achievement);
                
                // Notify callback if the achievement was just unlocked
                if (!wasUnlocked && achievement.isUnlocked() && callback != null) {
                    callback.onAchievementUnlocked(achievement);
                }
            }
        });
    }
    
    /**
     * Check and update progress for all achievements of a specific type.
     * 
     * @param type The achievement type
     * @param progressChecker Function to check if an achievement's progress should be updated
     * @param callback Callback to be notified when an achievement is unlocked
     */
    public void checkTypeAchievements(String type, int progressToAdd, 
                                     OnAchievementUnlockedListener callback) {
        executor.execute(() -> {
            List<Achievement> achievements = achievementDao.getAchievementsByType(type).getValue();
            if (achievements != null) {
                for (Achievement achievement : achievements) {
                    if (!achievement.isUnlocked()) {
                        boolean wasUnlocked = achievement.isUnlocked();
                        achievement.incrementProgress(progressToAdd);
                        achievementDao.update(achievement);
                        
                        // Notify callback if the achievement was just unlocked
                        if (!wasUnlocked && achievement.isUnlocked() && callback != null) {
                            callback.onAchievementUnlocked(achievement);
                        }
                    }
                }
            }
        });
    }

    /**
     * Unlock an achievement.
     * 
     * @param achievementId The achievement ID
     * @param callback Callback to be notified when the achievement is unlocked
     */
    public void unlockAchievement(String achievementId, OnAchievementUnlockedListener callback) {
        executor.execute(() -> {
            Achievement achievement = achievementDao.getAchievementByIdSync(achievementId);
            if (achievement != null && !achievement.isUnlocked()) {
                achievement.setUnlocked(true);
                achievement.setProgressCurrent(achievement.getProgressTarget());
                achievementDao.update(achievement);
                
                // Notify callback
                if (callback != null) {
                    callback.onAchievementUnlocked(achievement);
                }
            }
        });
    }

    /**
     * Initialize achievements in the database.
     */
    private class InitializeAchievementsTask implements Runnable {
        @Override
        public void run() {
            // Check if we need to initialize
            if (achievementDao.getTotalCount().getValue() == null || 
                achievementDao.getTotalCount().getValue() == 0) {
                
                // Clear existing achievements (if any)
                achievementDao.deleteAll();
                
                // Insert all standard achievements
                List<Achievement> achievements = AchievementRegistry.createAllAchievements();
                achievementDao.insertAll(achievements);
            }
        }
    }

    /**
     * Listener for achievement unlocked events.
     */
    public interface OnAchievementUnlockedListener {
        void onAchievementUnlocked(Achievement achievement);
    }
}