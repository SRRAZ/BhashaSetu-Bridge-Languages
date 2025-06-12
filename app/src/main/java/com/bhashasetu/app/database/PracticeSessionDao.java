package com.bhashasetu.app.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.bhashasetu.app.model.PracticeSession;

import java.util.List;

@Dao
public interface PracticeSessionDao {
    
    @Insert
    long insert(PracticeSession session);
    
    @Update
    void update(PracticeSession session);
    
    @Delete
    void delete(PracticeSession session);
    
    @Query("SELECT * FROM practice_sessions WHERE userId = :userId ORDER BY startTime DESC")
    LiveData<List<PracticeSession>> getAllSessionsForUser(int userId);
    
    @Query("SELECT * FROM practice_sessions WHERE userId = :userId ORDER BY startTime DESC LIMIT :limit")
    LiveData<List<PracticeSession>> getRecentSessionsForUser(int userId, int limit);
    
    @Query("SELECT * FROM practice_sessions WHERE id = :sessionId")
    LiveData<PracticeSession> getSessionById(int sessionId);
    
    @Query("SELECT * FROM practice_sessions WHERE userId = :userId AND sessionType = :sessionType ORDER BY startTime DESC")
    LiveData<List<PracticeSession>> getSessionsByType(int userId, String sessionType);
    
    @Query("SELECT * FROM practice_sessions WHERE userId = :userId AND deckId = :deckId ORDER BY startTime DESC")
    LiveData<List<PracticeSession>> getSessionsForDeck(int userId, int deckId);
    
    @Query("SELECT * FROM practice_sessions WHERE userId = :userId AND lessonId = :lessonId ORDER BY startTime DESC")
    LiveData<List<PracticeSession>> getSessionsForLesson(int userId, int lessonId);
    
    @Query("SELECT COUNT(*) FROM practice_sessions WHERE userId = :userId AND sessionType = :sessionType AND isCompleted = 1")
    int getCompletedSessionCount(int userId, String sessionType);
    
    @Query("SELECT AVG(score) FROM practice_sessions WHERE userId = :userId AND isCompleted = 1")
    float getAverageScore(int userId);
    
    @Query("SELECT SUM(totalItems) FROM practice_sessions WHERE userId = :userId AND isCompleted = 1")
    int getTotalItemsPracticed(int userId);
    
    @Query("SELECT SUM(correctAnswers) FROM practice_sessions WHERE userId = :userId AND isCompleted = 1")
    int getTotalCorrectAnswers(int userId);
    
    @Query("SELECT COUNT(*) FROM practice_sessions WHERE userId = :userId AND date(startTime/1000, 'unixepoch') = date('now')")
    int getSessionCountToday(int userId);
    
    @Query("UPDATE practice_sessions SET isCompleted = 1, endTime = :endTime, score = :score, correctAnswers = :correctAnswers " +
           "WHERE id = :sessionId")
    void completeSession(int sessionId, long endTime, int score, int correctAnswers);
}