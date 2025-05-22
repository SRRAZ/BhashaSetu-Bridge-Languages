package com.example.englishhindi.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.englishhindi.model.gamification.UserStats;

/**
 * Data Access Object for UserStats entities
 */
@Dao
public interface UserStatsDao {

    @Insert
    long insert(UserStats userStats);
    
    @Update
    void update(UserStats userStats);
    
    @Query("SELECT * FROM user_stats WHERE userId = :userId")
    UserStats getUserStats(int userId);
    
    @Query("SELECT * FROM user_stats WHERE userId = :userId")
    LiveData<UserStats> getUserStatsLive(int userId);
    
    @Query("SELECT dailyStreak FROM user_stats WHERE userId = :userId")
    int getDailyStreak(int userId);
    
    @Query("UPDATE user_stats SET dailyStreak = :streak WHERE userId = :userId")
    void updateDailyStreak(int userId, int streak);
    
    @Query("UPDATE user_stats SET wordsLearned = wordsLearned + 1 WHERE userId = :userId")
    void incrementWordsLearned(int userId);
    
    @Query("UPDATE user_stats SET exercisesCompleted = exercisesCompleted + 1 WHERE userId = :userId")
    void incrementExercisesCompleted(int userId);
    
    @Query("UPDATE user_stats SET perfectScores = perfectScores + 1 WHERE userId = :userId")
    void incrementPerfectScores(int userId);
    
    @Query("UPDATE user_stats SET totalTimeSpent = totalTimeSpent + :seconds WHERE userId = :userId")
    void addTimeSpent(int userId, long seconds);
    
    @Query("UPDATE user_stats SET lastActive = :timestamp WHERE userId = :userId")
    void updateLastActive(int userId, long timestamp);
}