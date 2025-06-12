package com.bhashasetu.app.pronunciation;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import androidx.lifecycle.LiveData;
import java.util.List;

/**
 * Data Access Object for PronunciationScore entities.
 */
@Dao
public interface PronunciationScoreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(PronunciationScore score);

    @Update
    void update(PronunciationScore score);

    @Delete
    void delete(PronunciationScore score);

    @Query("SELECT * FROM pronunciation_scores WHERE id = :scoreId")
    LiveData<PronunciationScore> getScoreById(long scoreId);

    @Query("SELECT * FROM pronunciation_scores WHERE sessionId = :sessionId ORDER BY timestamp ASC")
    LiveData<List<PronunciationScore>> getScoresForSession(long sessionId);

    @Query("SELECT * FROM pronunciation_scores WHERE word = :wordId ORDER BY timestamp DESC")
    LiveData<List<PronunciationScore>> getScoresForWord(long wordId);

    @Query("SELECT * FROM pronunciation_scores WHERE word = :wordId ORDER BY accuracyScore DESC LIMIT 1")
    LiveData<PronunciationScore> getBestScoreForWord(long wordId);

    @Query("SELECT AVG(accuracyScore) FROM pronunciation_scores WHERE word = :wordId")
    double getAverageScoreForWord(long wordId);

    @Query("DELETE FROM pronunciation_scores WHERE sessionId = :sessionId")
    void deleteScoresForSession(long sessionId);

    @Query("SELECT COUNT(*) FROM pronunciation_scores WHERE accuracyScore >= 80")
    int getHighScoreCount();

    @Query("SELECT * FROM pronunciation_scores ORDER BY timestamp DESC LIMIT :limit")
    LiveData<List<PronunciationScore>> getRecentScores(int limit);

    /**
     * Get a list of words that need more practice based on low scores
     * @param threshold The score threshold below which words need practice
     * @return LiveData list of word IDs
     */
    @Query("SELECT DISTINCT word FROM pronunciation_scores " +
           "GROUP BY word HAVING AVG(accuracyScore) < :threshold")
    LiveData<List<Long>> getWordsThatNeedPractice(int threshold);
}