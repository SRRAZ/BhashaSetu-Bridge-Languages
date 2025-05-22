package com.example.englishhindi.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.englishhindi.model.gamification.PointsSource;
import com.example.englishhindi.model.gamification.UserPoints;

import java.util.List;

/**
 * Data Access Object for UserPoints entities
 */
@Dao
public interface UserPointsDao {

    @Insert
    long insert(UserPoints userPoints);
    
    @Query("SELECT * FROM user_points WHERE userId = :userId ORDER BY earnedAt DESC")
    LiveData<List<UserPoints>> getAllPoints(int userId);
    
    @Query("SELECT * FROM user_points WHERE userId = :userId ORDER BY earnedAt DESC LIMIT :limit")
    LiveData<List<UserPoints>> getRecentPoints(int userId, int limit);
    
    @Query("SELECT * FROM user_points WHERE userId = :userId AND source = :source")
    LiveData<List<UserPoints>> getPointsBySource(int userId, PointsSource source);
    
    @Query("SELECT SUM(points) FROM user_points WHERE userId = :userId")
    int getTotalPoints(int userId);
    
    @Query("SELECT SUM(points) FROM user_points WHERE userId = :userId AND source = :source")
    int getTotalPointsBySource(int userId, PointsSource source);
    
    @Query("SELECT COUNT(*) FROM user_points WHERE userId = :userId")
    int getPointsEntryCount(int userId);
    
    @Query("SELECT MAX(points) FROM user_points WHERE userId = :userId")
    int getHighestPointsEarned(int userId);
    
    @Query("SELECT SUM(points) FROM user_points WHERE userId = :userId AND earnedAt >= :startTime AND earnedAt <= :endTime")
    int getPointsEarnedInTimeRange(int userId, long startTime, long endTime);
}