package com.bhashasetu.app.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.bhashasetu.app.model.gamification.Badge;
import com.bhashasetu.app.model.gamification.BadgeTier;

import java.util.List;

/**
 * Data Access Object for Badge entities
 */
@Dao
public interface BadgeDao {

    @Insert
    long insert(Badge badge);
    
    @Update
    void update(Badge badge);
    
    @Query("SELECT * FROM badges")
    LiveData<List<Badge>> getAllBadges();
    
    @Query("SELECT * FROM badges WHERE id = :id")
    LiveData<Badge> getBadgeById(int id);
    
    @Query("SELECT * FROM badges WHERE earned = 1 ORDER BY earnedAt DESC")
    LiveData<List<Badge>> getEarnedBadges();
    
    @Query("SELECT * FROM badges WHERE earned = 0")
    LiveData<List<Badge>> getUnearnedBadges();
    
    @Query("SELECT * FROM badges WHERE tier = :tier")
    LiveData<List<Badge>> getBadgesByTier(BadgeTier tier);
    
    @Query("SELECT * FROM badges WHERE achievementId = :achievementId")
    Badge getBadgeByAchievementId(int achievementId);
    
    @Query("SELECT COUNT(*) FROM badges WHERE earned = 1")
    int getEarnedBadgeCount();
    
    @Query("SELECT COUNT(*) FROM badges WHERE earned = 1 AND tier = :tier")
    int getEarnedBadgeCountByTier(BadgeTier tier);
}