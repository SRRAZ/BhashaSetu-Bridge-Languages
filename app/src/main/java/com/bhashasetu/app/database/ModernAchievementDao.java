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
 */
@Dao
public interface ModernAchievementDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Achievement achievement);
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Achievement> achievements);
    
    @Update
    void update(Achievement achievement);
    
    @Delete
    void delete(Achievement achievement);
    
    @Query("SELECT * FROM achievements ORDER BY category, type, titleEnglish")
    LiveData<List<Achievement>> getAllAchievements();
    
    @Query("SELECT * FROM achievements WHERE isUnlocked = 1 ORDER BY unlockedAt DESC")
    LiveData<List<Achievement>> getUnlockedAchievements();
    
    @Query("SELECT * FROM achievements WHERE isUnlocked = 0 ORDER BY category, titleEnglish")
    LiveData<List<Achievement>> getLockedAchievements();
    
    @Query("SELECT * FROM achievements WHERE category = :category ORDER BY type, titleEnglish")
    LiveData<List<Achievement>> getAchievementsByCategory(String category);
    
    @Query("SELECT * FROM achievements WHERE id = :id")
    LiveData<Achievement> getAchievementById(String id);
    
    @Query("SELECT * FROM achievements WHERE id = :id")
    Achievement getAchievementByIdSync(String id);
    
    @Query("UPDATE achievements SET currentProgress = currentProgress + :increment WHERE id = :id")
    void incrementProgress(String id, int increment);
    
    @Query("UPDATE achievements SET isUnlocked = 1, unlockedAt = :unlockedAt WHERE id = :id")
    void unlockAchievement(String id, long unlockedAt);
    
    @Query("SELECT SUM(pointsRewarded) FROM achievements WHERE isUnlocked = 1")
    LiveData<Integer> getTotalPointsFromAchievements();
    
    @Query("SELECT COUNT(*) FROM achievements WHERE isUnlocked = 1")
    LiveData<Integer> getUnlockedCount();
    
    @Query("SELECT COUNT(*) FROM achievements")
    LiveData<Integer> getTotalCount();
    
    @Query("DELETE FROM achievements")
    void deleteAll();
}