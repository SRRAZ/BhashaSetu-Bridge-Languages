package com.example.englishhindi.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.englishhindi.model.gamification.UserLevel;

/**
 * Data Access Object for UserLevel entities
 */
@Dao
public interface UserLevelDao {

    @Insert
    long insert(UserLevel userLevel);
    
    @Update
    void update(UserLevel userLevel);
    
    @Query("SELECT * FROM user_levels WHERE userId = :userId")
    UserLevel getUserLevel(int userId);
    
    @Query("SELECT * FROM user_levels WHERE userId = :userId")
    LiveData<UserLevel> getUserLevelLive(int userId);
    
    @Query("UPDATE user_levels SET level = :level, currentExp = :currentExp, " +
           "expToNextLevel = :expToNextLevel, totalExp = :totalExp, " +
           "title = :title WHERE userId = :userId")
    void updateUserLevel(int userId, int level, int currentExp, int expToNextLevel, 
                         int totalExp, String title);
    
    @Query("SELECT MAX(level) FROM user_levels")
    int getHighestLevel();
    
    @Query("SELECT AVG(level) FROM user_levels")
    float getAverageLevel();
}