package com.bhashasetu.app.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.bhashasetu.app.model.UserProgress;
import java.util.List;

@Dao
public interface UserProgressDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertUserProgress(UserProgress userProgress);
    
    @Update
    void updateUserProgress(UserProgress userProgress);

    @Delete
    void deleteUserProgress(UserProgress userProgress);

    @Query("SELECT * FROM user_learning_progress WHERE userId = :userId AND wordId = :wordId LIMIT 1")
    LiveData<UserProgress> getUserProgressForWord(long userId, long wordId);

    @Query("SELECT * FROM user_learning_progress WHERE userId = :userId")
    LiveData<List<UserProgress>> getUserProgress(long userId);

    @Query("SELECT * FROM user_learning_progress WHERE userId = :userId AND lessonId = :lessonId")
    LiveData<List<UserProgress>> getUserProgressForLesson(long userId, long lessonId);

    @Query("SELECT * FROM user_learning_progress WHERE userId = :userId AND isMastered = 1")
    LiveData<List<UserProgress>> getMasteredWords(long userId);

    @Query("SELECT * FROM user_learning_progress WHERE userId = :userId AND learningStage = :stage")
    LiveData<List<UserProgress>> getProgressByStage(long userId, String stage);

    @Query("SELECT AVG(masteryLevel) FROM user_learning_progress WHERE userId = :userId")
    LiveData<Double> getAverageMasteryLevel(long userId);

    @Query("SELECT COUNT(*) FROM user_learning_progress WHERE userId = :userId AND isMastered = 1")
    LiveData<Integer> getMasteredWordCount(long userId);

/*
    @Query("SELECT * FROM user_learning_progress WHERE userId = :userId")
    LiveData<List<UserProgress>> getAllProgressForUser(int userId);

    @Query("SELECT * FROM user_learning_progress WHERE userId = :userId AND itemType = 'word' AND itemId = :wordId")
    LiveData<UserProgress> getWordProgress(int userId, int wordId);

    @Query("SELECT * FROM user_learning_progress WHERE userId = :userId AND itemType = 'lesson' AND itemId = :lessonId")
    LiveData<UserProgress> getLessonProgress(int userId, int lessonId);

    @Query("SELECT AVG(completionLevel) FROM user_learning_progress WHERE userId = :userId")
    LiveData<Float> getOverallProgress(int userId);

    @Query("SELECT * FROM user_learning_progress WHERE userId = :userId AND reviewDue <= :currentTime " +
           "AND itemType = 'word' ORDER BY reviewDue ASC LIMIT :limit")
    LiveData<List<UserProgress>> getDueWordReviews(int userId, long currentTime, int limit);

    @Query("SELECT * FROM user_learning_progress WHERE userId = :userId AND itemType = 'word' " +
           "ORDER BY completionLevel ASC LIMIT :limit")
    LiveData<List<UserProgress>> getLeastMasteredItems(int userId, int limit);

    @Query("UPDATE user_learning_progress SET completionLevel = :level WHERE userId = :userId " +
           "AND itemType = :itemType AND itemId = :itemId")
    void updateCompletionLevel(int userId, String itemType, int itemId, int level);

    @Query("UPDATE user_learning_progress SET reviewDue = :nextReview WHERE id = :progressId")
    void updateNextReview(int progressId, long nextReview);

    @Query("UPDATE user_learning_progress SET srLevel = :newLevel WHERE id = :progressId")
    void updateSpacedRepetitionLevel(int progressId, int newLevel);
*/
}