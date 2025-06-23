package com.bhashasetu.app.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.bhashasetu.app.data.model.Achievement;

import java.util.List;

/**
 * Data Access Object for Modern Achievement entities.
 * Handles operations for the achievements table (bilingual, feature-rich system).
 *
 * This DAO provides comprehensive achievement management including:
 * - Bilingual support (English + Hindi)
 * - Advanced progress tracking
 * - Category and type-based organization
 * - Hidden/secret achievement support
 * - Rich analytics and statistics
 * - Flexible trigger conditions
 */
@Dao
public interface ModernAchievementDao {

    // ================================
    // BASIC CRUD OPERATIONS
    // ================================

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Achievement achievement);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Achievement> achievements);

    @Update
    void update(Achievement achievement);

    @Delete
    void delete(Achievement achievement);

    // ================================
    // BASIC QUERIES
    // ================================

    /**
     * Get all achievements ordered by category, type, and English title
     */
    @Query("SELECT * FROM achievements ORDER BY category, type, titleEnglish")
    LiveData<List<Achievement>> getAllAchievements();

    /**
     * Get all achievements (synchronous) - for initialization
     */
    @Query("SELECT * FROM achievements ORDER BY category, type, titleEnglish")
    List<Achievement> getAllAchievementsSync();

    /**
     * Get unlocked achievements ordered by unlock time (most recent first)
     */
    @Query("SELECT * FROM achievements WHERE isUnlocked = 1 ORDER BY unlockedAt DESC")
    LiveData<List<Achievement>> getUnlockedAchievements();

    /**
     * Get locked achievements that are visible (not hidden)
     */
    @Query("SELECT * FROM achievements WHERE isUnlocked = 0 AND isHidden = 0 ORDER BY category, titleEnglish")
    LiveData<List<Achievement>> getLockedVisibleAchievements();

    /**
     * Get all visible achievements (both locked and unlocked, excluding hidden)
     */
    @Query("SELECT * FROM achievements WHERE isHidden = 0 ORDER BY category, type, titleEnglish")
    LiveData<List<Achievement>> getVisibleAchievements();

    // ================================
    // FILTERED QUERIES
    // ================================

    /**
     * Get achievements by category
     */
    @Query("SELECT * FROM achievements WHERE category = :category ORDER BY type, titleEnglish")
    LiveData<List<Achievement>> getAchievementsByCategory(String category);

    /**
     * Get achievements by type
     */
    @Query("SELECT * FROM achievements WHERE type = :type ORDER BY category, titleEnglish")
    LiveData<List<Achievement>> getAchievementsByType(String type);

    /**
     * Get achievements by category and type
     */
    @Query("SELECT * FROM achievements WHERE category = :category AND type = :type ORDER BY titleEnglish")
    LiveData<List<Achievement>> getAchievementsByCategoryAndType(String category, String type);

    /**
     * Get achievement by ID (observable)
     */
    @Query("SELECT * FROM achievements WHERE id = :id")
    LiveData<Achievement> getAchievementById(String id);

    /**
     * Get achievement by ID (synchronous)
     */
    @Query("SELECT * FROM achievements WHERE id = :id")
    Achievement getAchievementByIdSync(String id);

    // ================================
    // PROGRESS TRACKING
    // ================================

    /**
     * Increment achievement progress
     */
    @Query("UPDATE achievements SET currentProgress = currentProgress + :increment WHERE id = :id")
    void incrementProgress(String id, int increment);

    /**
     * Set achievement progress to specific value
     */
    @Query("UPDATE achievements SET currentProgress = :progress WHERE id = :id")
    void setProgress(String id, int progress);

    /**
     * Unlock achievement with timestamp
     */
    @Query("UPDATE achievements SET isUnlocked = 1, unlockedAt = :unlockedAt WHERE id = :id")
    void unlockAchievement(String id, long unlockedAt);

    /**
     * Bulk unlock multiple achievements
     */
    @Query("UPDATE achievements SET isUnlocked = 1, unlockedAt = :unlockedAt WHERE id IN (:ids)")
    void unlockAchievements(List<String> ids, long unlockedAt);

    /**
     * Get achievements near completion (progress >= percentage of max)
     */
    @Query("SELECT * FROM achievements WHERE (currentProgress * 100.0 / maxProgress) >= :percentage AND isUnlocked = 0 ORDER BY (currentProgress * 100.0 / maxProgress) DESC")
    LiveData<List<Achievement>> getAchievementsNearCompletion(double percentage);

    /**
     * Get achievements that can be unlocked (progress >= maxProgress but not yet unlocked)
     */
    @Query("SELECT * FROM achievements WHERE currentProgress >= maxProgress AND isUnlocked = 0")
    List<Achievement> getAchievementsReadyToUnlock();

    // ================================
    // STATISTICS AND ANALYTICS
    // ================================

    /**
     * Get total points from unlocked achievements
     */
    @Query("SELECT COALESCE(SUM(pointsRewarded), 0) FROM achievements WHERE isUnlocked = 1")
    LiveData<Integer> getTotalPointsFromAchievements();

    /**
     * Get total points from unlocked achievements (synchronous)
     */
    @Query("SELECT COALESCE(SUM(pointsRewarded), 0) FROM achievements WHERE isUnlocked = 1")
    int getTotalPointsFromAchievementsSync();

    /**
     * Get count of unlocked achievements
     */
    @Query("SELECT COUNT(*) FROM achievements WHERE isUnlocked = 1")
    LiveData<Integer> getUnlockedCount();

    /**
     * Get count of visible achievements (excluding hidden)
     */
    @Query("SELECT COUNT(*) FROM achievements WHERE isHidden = 0")
    LiveData<Integer> getVisibleAchievementCount();

    /**
     * Get total achievement count
     */
    @Query("SELECT COUNT(*) FROM achievements")
    LiveData<Integer> getTotalCount();

    /**
     * Get completion percentage (unlocked visible achievements / total visible achievements)
     */
    @Query("SELECT CASE WHEN (SELECT COUNT(*) FROM achievements WHERE isHidden = 0) = 0 THEN 0.0 ELSE (SELECT COUNT(*) * 100.0 FROM achievements WHERE isUnlocked = 1 AND isHidden = 0) / (SELECT COUNT(*) FROM achievements WHERE isHidden = 0) END")
    LiveData<Double> getCompletionPercentage();

    /**
     * Get progress statistics by category
     */
    @Query("SELECT category, COUNT(*) as total, SUM(CASE WHEN isUnlocked = 1 THEN 1 ELSE 0 END) as unlocked FROM achievements WHERE isHidden = 0 GROUP BY category ORDER BY category")
    LiveData<List<CategoryStats>> getStatsByCategory();

    // ================================
    // ADVANCED QUERIES
    // ================================

    /**
     * Get recently unlocked achievements with custom unlock messages
     */
    @Query("SELECT * FROM achievements WHERE isUnlocked = 1 AND unlockMessage IS NOT NULL AND unlockMessage != '' ORDER BY unlockedAt DESC LIMIT :limit")
    LiveData<List<Achievement>> getRecentUnlockedWithMessages(int limit);

    /**
     * Get unlocked secret/hidden achievements
     */
    @Query("SELECT * FROM achievements WHERE isHidden = 1 AND isUnlocked = 1 ORDER BY unlockedAt DESC")
    LiveData<List<Achievement>> getUnlockedSecretAchievements();

    /**
     * Get achievements unlocked in time range
     */
    @Query("SELECT * FROM achievements WHERE isUnlocked = 1 AND unlockedAt BETWEEN :startTime AND :endTime ORDER BY unlockedAt DESC")
    LiveData<List<Achievement>> getAchievementsUnlockedInRange(long startTime, long endTime);

    /**
     * Search achievements by title (English or Hindi)
     */
    @Query("SELECT * FROM achievements WHERE (titleEnglish LIKE '%' || :searchQuery || '%' OR titleHindi LIKE '%' || :searchQuery || '%') AND isHidden = 0 ORDER BY titleEnglish")
    LiveData<List<Achievement>> searchAchievements(String searchQuery);

    /**
     * Get distinct categories
     */
    @Query("SELECT DISTINCT category FROM achievements WHERE isHidden = 0 ORDER BY category")
    LiveData<List<String>> getAllCategories();

    /**
     * Get distinct types
     */
    @Query("SELECT DISTINCT type FROM achievements WHERE isHidden = 0 ORDER BY type")
    LiveData<List<String>> getAllTypes();

    // ================================
    // MAINTENANCE AND UTILITIES
    // ================================

    /**
     * Delete all achievements (for testing/reset)
     */
    @Query("DELETE FROM achievements")
    void deleteAll();

    /**
     * Delete achievements by category
     */
    @Query("DELETE FROM achievements WHERE category = :category")
    void deleteByCategory(String category);

    /**
     * Reset progress for all achievements
     */
    @Query("UPDATE achievements SET currentProgress = 0, isUnlocked = 0, unlockedAt = NULL")
    void resetAllProgress();

    /**
     * Reset progress for specific category
     */
    @Query("UPDATE achievements SET currentProgress = 0, isUnlocked = 0, unlockedAt = NULL WHERE category = :category")
    void resetProgressByCategory(String category);

    // ================================
    // HELPER CLASSES
    // ================================

    /**
     * Helper class for category statistics
     */
    class CategoryStats {
        public String category;
        public int total;
        public int unlocked;

        public double getCompletionPercentage() {
            return total == 0 ? 0.0 : (unlocked * 100.0 / total);
        }
    }
}