package com.bhashasetu.app.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.bhashasetu.app.pronunciation.PronunciationProgressStats;
import java.util.List;

@Dao
public interface PronunciationProgressStatsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertPronunciationStats(PronunciationProgressStats stats);

    @Update
    void updatePronunciationStats(PronunciationProgressStats stats);

    @Delete
    void deletePronunciationStats(PronunciationProgressStats stats);

    @Query("SELECT * FROM pronunciation_progress_stats WHERE userId = :userId AND wordId = :wordId LIMIT 1")
    LiveData<PronunciationProgressStats> getStatsForUserWord(long userId, long wordId);

    @Query("SELECT * FROM pronunciation_progress_stats WHERE userId = :userId")
    LiveData<List<PronunciationProgressStats>> getUserPronunciationStats(long userId);

    @Query("SELECT * FROM pronunciation_progress_stats WHERE userId = :userId AND isMastered = 1")
    LiveData<List<PronunciationProgressStats>> getMasteredPronunciations(long userId);

    @Query("SELECT AVG(averageAccuracy) FROM pronunciation_progress_stats WHERE userId = :userId")
    LiveData<Double> getAveragePronunciationAccuracy(long userId);

    @Query("SELECT COUNT(*) FROM pronunciation_progress_stats WHERE userId = :userId AND isMastered = 1")
    LiveData<Integer> getMasteredPronunciationCount(long userId);

    @Query("SELECT SUM(totalPracticeTime) FROM pronunciation_progress_stats WHERE userId = :userId")
    LiveData<Long> getTotalPracticeTime(long userId);
}