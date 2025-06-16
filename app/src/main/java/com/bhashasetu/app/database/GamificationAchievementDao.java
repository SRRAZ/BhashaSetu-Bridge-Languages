package com.bhashasetu.app.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.bhashasetu.app.model.gamification.Achievement;
import com.bhashasetu.app.model.gamification.AchievementType;

import java.util.List;

/**
 * Data Access Object for Gamification Achievement entities.
 * Handles operations for the user_achievements table.
 */
@Dao
public interface GamificationAchievementDao {

    // ============ BASIC CRUD OPERATIONS ============

    /**
     * Insert a new gamification achievement.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Achievement achievement);

    /**
     * Insert multiple gamification achievements.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Achievement> achievements);

    /**
     * Update a gamification achievement.
     */
    @Update
    void update(Achievement achievement);

    /**
     * Delete a gamification achievement.
     */
    @Delete
    void delete(Achievement achievement);

    // ============ QUERY OPERATIONS ============

    /**
     * Get all gamification achievements.
     */
    @Query("SELECT * FROM user_achievements ORDER BY type, title")
    LiveData<List<Achievement>> getAllAchievements();

    /**
     * Get all unlocked gamification achievements.
     */
    @Query("SELECT * FROM user_achievements WHERE unlocked = 1 ORDER BY unlockedAt DESC")
    LiveData<List<Achievement>> getUnlockedAchievements();

    /**
     * Get all locked gamification achievements.
     */
    @Query("SELECT * FROM user_achievements WHERE unlocked = 0 ORDER BY type, title")
    LiveData<List<Achievement>> getLockedAchievements();

    /**
     * Get achievements by type.
     */
    @Query("SELECT * FROM user_achievements WHERE type = :type ORDER BY threshold")
    LiveData<List<Achievement>> getAchievementsByType(AchievementType type);

    /**
     * Get an achievement by ID.
     */
    @Query("SELECT * FROM user_achievements WHERE id = :id")
    LiveData<Achievement> getAchievementById(int id);

    /**
     * Get an achievement by ID (non-LiveData).
     */
    @Query("SELECT * FROM user_achievements WHERE id = :id")
    Achievement getAchievementByIdSync(int id);

    /**
     * Get achievements by title.
     */
    @Query("SELECT * FROM user_achievements WHERE title LIKE '%' || :title || '%' ORDER BY title")
    LiveData<List<Achievement>> getAchievementsByTitle(String title);

    // ============ PROGRESS TRACKING ============

    /**
     * Get achievements with progress above a threshold.
     */
    @Query("SELECT * FROM user_achievements WHERE currentProgress >= :minProgress ORDER BY currentProgress DESC")
    LiveData<List<Achievement>> getAchievementsWithMinProgress(int minProgress);

    /**
     * Get achievements that are close to completion.
     */
    @Query("SELECT * FROM user_achievements WHERE (currentProgress * 100.0 / threshold) >= :percentage AND unlocked = 0 ORDER BY (currentProgress * 100.0 / threshold) DESC")
    LiveData<List<Achievement>> getAchievementsNearCompletion(double percentage);

    /**
     * Update achievement progress.
     */
    @Query("UPDATE user_achievements SET currentProgress = :progress WHERE id = :id")
    void updateProgress(int id, int progress);

    /**
     * Increment achievement progress.
     */
    @Query("UPDATE user_achievements SET currentProgress = currentProgress + :increment WHERE id = :id")
    void incrementProgress(int id, int increment);

    /**
     * Unlock an achievement and set the unlock timestamp.
     */
    @Query("UPDATE user_achievements SET unlocked = 1, unlockedAt = :unlockedAt WHERE id = :id")
    void unlockAchievement(int id, java.util.Date unlockedAt);

    /**
     * Reset achievement progress.
     */
    @Query("UPDATE user_achievements SET currentProgress = 0, unlocked = 0, unlockedAt = NULL WHERE id = :id")
    void resetAchievement(int id);

    /**
     * Get achievements that can be unlocked (progress meets threshold).
     */
    @Query("SELECT * FROM user_achievements WHERE currentProgress >= threshold AND unlocked = 0")
    LiveData<List<Achievement>> getAchievementsReadyToUnlock();

    // ============ STATISTICS & ANALYTICS ============

    /**
     * Get total points awarded from unlocked achievements.
     */
    @Query("SELECT SUM(pointsAwarded) FROM user_achievements WHERE unlocked = 1")
    LiveData<Integer> getTotalPointsFromAchievements();

    /**
     * Get the number of unlocked achievements.
     */
    @Query("SELECT COUNT(*) FROM user_achievements WHERE unlocked = 1")
    LiveData<Integer> getUnlockedCount();

    /**
     * Get the total number of achievements.
     */
    @Query("SELECT COUNT(*) FROM user_achievements")
    LiveData<Integer> getTotalCount();

    /**
     * Get achievements by points awarded (sorted descending).
     */
    @Query("SELECT * FROM user_achievements ORDER BY pointsAwarded DESC")
    LiveData<List<Achievement>> getAchievementsByPoints();

    /**
     * Get recently unlocked achievements.
     */
    @Query("SELECT * FROM user_achievements WHERE unlocked = 1 ORDER BY unlockedAt DESC LIMIT :limit")
    LiveData<List<Achievement>> getRecentlyUnlocked(int limit);

    /**
     * Get completion percentage for all achievements.
     */
    @Query("SELECT (COUNT(CASE WHEN unlocked = 1 THEN 1 END) * 100.0 / COUNT(*)) FROM user_achievements")
    LiveData<Double> getCompletionPercentage();

    /**
     * Get achievement statistics grouped by type.
     */
    @Query("SELECT type, COUNT(*) as total, COUNT(CASE WHEN unlocked = 1 THEN 1 END) as unlocked, SUM(pointsAwarded) as totalPoints FROM user_achievements GROUP BY type ORDER BY type")
    LiveData<List<AchievementTypeStats>> getStatsByType();

    // ============ UTILITY OPERATIONS ============

    /**
     * Check if a specific achievement exists.
     */
    @Query("SELECT COUNT(*) > 0 FROM user_achievements WHERE title = :title")
    boolean achievementExists(String title);

    /**
     * Delete all achievements.
     */
    @Query("DELETE FROM user_achievements")
    void deleteAll();

    /**
     * Delete achievements by type.
     */
    @Query("DELETE FROM user_achievements WHERE type = :type")
    void deleteByType(AchievementType type);

    // ============ STATISTICS DATA CLASS ============

    /**
     * Inner class for achievement type statistics.
     */
    class AchievementTypeStats {
        public AchievementType type;
        public int total;
        public int unlocked;
        public int totalPoints;

        public AchievementTypeStats(AchievementType type, int total, int unlocked, int totalPoints) {
            this.type = type;
            this.total = total;
            this.unlocked = unlocked;
            this.totalPoints = totalPoints;
        }
    }
}