package com.example.englishhindi.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.englishhindi.model.UserProgress;

/**
 * Data Access Object for UserProgress entities.
 */
@Dao
public interface UserProgressDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserProgress userProgress);

    @Update
    void update(UserProgress userProgress);

    @Delete
    void delete(UserProgress userProgress);

    @Query("SELECT * FROM user_progress WHERE userId = :userId")
    UserProgress getUserProgress(long userId);

    @Query("SELECT * FROM user_progress WHERE userId = :userId")
    LiveData<UserProgress> getUserProgressLiveData(long userId);

    @Query("SELECT * FROM user_progress")
    LiveData<java.util.List<UserProgress>> getAllUserProgressLiveData();

    @Query("SELECT COUNT(*) FROM user_progress")
    int getUserProgressCount();

    @Query("DELETE FROM user_progress WHERE userId = :userId")
    void deleteUserProgressByUserId(long userId);
}