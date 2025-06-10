package com.bhashasetu.app.pronunciation;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.bhashasetu.app.model.PronunciationSession;

import java.util.List;

/**
 * Data Access Object for PronunciationSession entities.
 */
@Dao
public interface PronunciationSessionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(PronunciationSession session);

    @Update
    void update(PronunciationSession session);

    @Delete
    void delete(PronunciationSession session);

    @Query("SELECT * FROM pronunciation_sessions WHERE id = :sessionId")
    LiveData<PronunciationSession> getSessionById(long sessionId);

    @Query("SELECT * FROM pronunciation_sessions WHERE userId = :userId ORDER BY startTime DESC")
    LiveData<List<PronunciationSession>> getSessionsForUser(long userId);

    @Query("SELECT * FROM pronunciation_sessions ORDER BY startTime DESC LIMIT 1")
    LiveData<PronunciationSession> getMostRecentSession();

    @Query("SELECT COUNT(*) FROM pronunciation_sessions")
    int getSessionCount();

    @Query("SELECT COUNT(*) FROM pronunciation_sessions WHERE userId = :userId")
    int getUserSessionCount(long userId);

    @Query("SELECT AVG(averageAccuracyScore) FROM pronunciation_sessions WHERE userId = :userId")
    double getUserAverageAccuracyScore(long userId);

    @Query("SELECT AVG(averageRhythmScore) FROM pronunciation_sessions WHERE userId = :userId")
    double getUserAverageRhythmScore(long userId);

    @Query("SELECT AVG(averageIntonationScore) FROM pronunciation_sessions WHERE userId = :userId")
    double getUserAverageIntonationScore(long userId);

    /**
     * Get comprehensive statistics about a user's pronunciation progress
     * @param userId The user ID
     * @return LiveData with the statistics object
     */
    @Query("SELECT " +
           "COUNT(*) as sessionCount, " +
           "(SELECT COUNT(*) FROM pronunciation_scores WHERE sessionId IN " +
           "  (SELECT id FROM pronunciation_sessions WHERE userId = :userId)) as wordAttempts, " +
           "AVG(averageAccuracyScore) as averageAccuracyScore, " +
           "MAX(averageAccuracyScore) as bestAccuracyScore, " +
           "(SELECT COUNT(DISTINCT word) FROM pronunciation_scores WHERE sessionId IN " +
           "  (SELECT id FROM pronunciation_sessions WHERE userId = :userId)) as uniqueWordsPracticed " +
           "FROM pronunciation_sessions WHERE userId = :userId")
    LiveData<PronunciationProgressStats> getPronunciationProgressStats(long userId);
}