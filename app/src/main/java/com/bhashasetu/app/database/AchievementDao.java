package com.bhashasetu.app.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.bhashasetu.app.model.Achievement;

import java.util.List;

/**
 * Data Access Object for Legacy Achievement entities.
 * Handles operations for the legacy_achievements table (backward compatibility).
 * Note: This DAO maintains the existing API for backward compatibility
 * while new features should use ModernAchievementDao.
 */
@Dao
public interface AchievementDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Achievement achievement);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Achievement> achievements);

    @Update
    void update(Achievement achievement);

    @Delete
    void delete(Achievement achievement);

    // ✅ FIXED: All queries now use "legacy_achievements" table

    /**
     * Get all achievements ordered by type and progress target
     */
    @Query("SELECT * FROM legacy_achievements ORDER BY type, progressTarget")
    LiveData<List<Achievement>> getAllAchievements();

    /**
     * Get all unlocked achievements ordered by unlock date (most recent first)
     */
    @Query("SELECT * FROM legacy_achievements WHERE unlocked = 1 ORDER BY dateUnlocked DESC")
    LiveData<List<Achievement>> getUnlockedAchievements();

    /**
     * Get achievements filtered by type
     */
    @Query("SELECT * FROM legacy_achievements WHERE type = :type ORDER BY progressTarget")
    LiveData<List<Achievement>> getAchievementsByType(String type);

    /**
     * Get achievement by ID (observable)
     */
    @Query("SELECT * FROM legacy_achievements WHERE id = :id")
    LiveData<Achievement> getAchievementById(long id);

    /**
     * Get achievement by ID (synchronous)
     */
    @Query("SELECT * FROM legacy_achievements WHERE id = :id")
    Achievement getAchievementByIdSync(long id);

    /**
     * Get total points from unlocked achievements
     */
    @Query("SELECT SUM(pointsValue) FROM legacy_achievements WHERE unlocked = 1")
    LiveData<Integer> getTotalPoints();

    /**
     * Get count of unlocked achievements
     */
    @Query("SELECT COUNT(*) FROM legacy_achievements WHERE unlocked = 1")
    LiveData<Integer> getUnlockedCount();

    /**
     * Get total achievement count
     */
    @Query("SELECT COUNT(*) FROM legacy_achievements")
    LiveData<Integer> getTotalCount();

    /**
     * Delete all achievements (for testing/reset)
     */
    @Query("DELETE FROM legacy_achievements")
    void deleteAll();

    // Additional helpful queries for legacy system

    /**
     * Get achievements by unlock status
     */
    @Query("SELECT * FROM legacy_achievements WHERE unlocked = :unlocked ORDER BY type, progressTarget")
    LiveData<List<Achievement>> getAchievementsByUnlockStatus(boolean unlocked);

    /**
     * Get achievements with progress above threshold
     */
    @Query("SELECT * FROM legacy_achievements WHERE currentProgress >= :minProgress ORDER BY currentProgress DESC")
    LiveData<List<Achievement>> getAchievementsWithMinProgress(int minProgress);

    /**
     * Update achievement progress
     */
    @Query("UPDATE legacy_achievements SET currentProgress = :progress WHERE id = :id")
    void updateProgress(long id, int progress);

    /**
     * Unlock achievement
     */
    @Query("UPDATE legacy_achievements SET unlocked = 1, dateUnlocked = :dateUnlocked WHERE id = :id")
    void unlockAchievement(long id, long dateUnlocked);

    /**
     * Get recently unlocked achievements
     */
    @Query("SELECT * FROM legacy_achievements WHERE unlocked = 1 AND dateUnlocked > :since ORDER BY dateUnlocked DESC LIMIT :limit")
    LiveData<List<Achievement>> getRecentlyUnlocked(long since, int limit);
}