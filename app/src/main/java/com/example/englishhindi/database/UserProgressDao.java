package com.example.englishhindi.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.englishhindi.model.UserProgress;

import java.util.List;

@Dao
public interface UserProgressDao {
    
    @Insert
    long insert(UserProgress progress);
    
    @Update
    void update(UserProgress progress);
    
    @Query("SELECT * FROM user_progress WHERE userId = :userId")
    LiveData<List<UserProgress>> getAllProgressForUser(int userId);
    
    @Query("SELECT * FROM user_progress WHERE userId = :userId AND itemType = 'word' AND itemId = :wordId")
    LiveData<UserProgress> getWordProgress(int userId, int wordId);
    
    @Query("SELECT * FROM user_progress WHERE userId = :userId AND itemType = 'lesson' AND itemId = :lessonId")
    LiveData<UserProgress> getLessonProgress(int userId, int lessonId);
    
    @Query("SELECT AVG(completionLevel) FROM user_progress WHERE userId = :userId")
    LiveData<Float> getOverallProgress(int userId);
    
    @Query("SELECT * FROM user_progress WHERE userId = :userId AND reviewDue <= :currentTime " +
           "AND itemType = 'word' ORDER BY reviewDue ASC LIMIT :limit")
    LiveData<List<UserProgress>> getDueWordReviews(int userId, long currentTime, int limit);
    
    @Query("SELECT * FROM user_progress WHERE userId = :userId AND itemType = 'word' " + 
           "ORDER BY completionLevel ASC LIMIT :limit")
    LiveData<List<UserProgress>> getLeastMasteredItems(int userId, int limit);
    
    @Query("UPDATE user_progress SET completionLevel = :level WHERE userId = :userId " +
           "AND itemType = :itemType AND itemId = :itemId")
    void updateCompletionLevel(int userId, String itemType, int itemId, int level);
    
    @Query("UPDATE user_progress SET reviewDue = :nextReview WHERE id = :progressId")
    void updateNextReview(int progressId, long nextReview);
    
    @Query("UPDATE user_progress SET srLevel = :newLevel WHERE id = :progressId")
    void updateSpacedRepetitionLevel(int progressId, int newLevel);
}