package com.bhashasetu.app.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.bhashasetu.app.model.Achievement;

import java.util.List;

/**
 * Data Access Object for Achievement entities.
 */
@Dao
public interface AchievementDao {

    /**
     * Insert a new achievement.
     * 
     * @param achievement The achievement to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Achievement achievement);
    
    /**
     * Insert multiple achievements.
     * 
     * @param achievements The achievements to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Achievement> achievements);
    
    /**
     * Update an achievement.
     * 
     * @param achievement The achievement to update
     */
    @Update
    void update(Achievement achievement);
    
    /**
     * Get all achievements.
     * 
     * @return LiveData list of all achievements
     */
    @Query("SELECT * FROM legacy_achievements ORDER BY type, progressTarget")
    LiveData<List<Achievement>> getAllAchievements();
    
    /**
     * Get all unlocked achievements.
     * 
     * @return LiveData list of unlocked achievements
     */
    @Query("SELECT * FROM legacy_achievements WHERE unlocked = 1 ORDER BY dateUnlocked DESC")
    LiveData<List<Achievement>> getUnlockedAchievements();
    
    /**
     * Get achievements by type.
     * 
     * @param type The achievement type
     * @return LiveData list of achievements of the specified type
     */
    @Query("SELECT * FROM legacy_achievements WHERE type = :type ORDER BY progressTarget")
    LiveData<List<Achievement>> getAchievementsByType(String type);
    
    /**
     * Get an achievement by ID.
     * 
     * @param id The achievement ID
     * @return LiveData with the achievement
     */
    @Query("SELECT * FROM legacy_achievements WHERE id = :id")
    LiveData<Achievement> getAchievementById(String id);
    
    /**
     * Get an achievement by ID (non-LiveData).
     * 
     * @param id The achievement ID
     * @return The achievement
     */
    @Query("SELECT * FROM legacy_achievements WHERE id = :id")
    Achievement getAchievementByIdSync(String id);
    
    /**
     * Get the total number of achievement points earned.
     * 
     * @return LiveData with the total points
     */
    @Query("SELECT SUM(pointsValue) FROM legacy_achievements WHERE unlocked = 1")
    LiveData<Integer> getTotalPoints();
    
    /**
     * Get the total number of unlocked achievements.
     * 
     * @return LiveData with the count of unlocked achievements
     */
    @Query("SELECT COUNT(*) FROM legacy_achievements WHERE unlocked = 1")
    LiveData<Integer> getUnlockedCount();
    
    /**
     * Get the total number of achievements.
     * 
     * @return LiveData with the total count of achievements
     */
    @Query("SELECT COUNT(*) FROM legacy_achievements")
    LiveData<Integer> getTotalCount();
    
    /**
     * Delete all achievements.
     */
    @Query("DELETE FROM legacy_achievements")
    void deleteAll();
}