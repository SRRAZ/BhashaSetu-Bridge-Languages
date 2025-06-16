package com.bhashasetu.app.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.bhashasetu.app.model.GameScore;

import java.util.List;

/**
 * Data Access Object for GameScore entities.
 */
@Dao
public interface GameScoreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(GameScore gameScore);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<GameScore> gameScores);

    @Update
    void update(GameScore gameScore);

    @Delete
    void delete(GameScore gameScore);

    @Query("SELECT * FROM game_scores ORDER BY datePlayed DESC")
    LiveData<List<GameScore>> getAllScores();

    @Query("SELECT * FROM game_scores WHERE userId = :userId ORDER BY datePlayed DESC")
    LiveData<List<GameScore>> getScoresByUserId(int userId);

    @Query("SELECT * FROM game_scores WHERE gameType = :gameType ORDER BY score DESC")
    LiveData<List<GameScore>> getScoresByGameType(String gameType);

    @Query("SELECT * FROM game_scores WHERE userId = :userId AND gameType = :gameType ORDER BY datePlayed DESC")
    LiveData<List<GameScore>> getScoresByUserAndGameType(int userId, String gameType);

    @Query("SELECT MAX(score) FROM game_scores WHERE userId = :userId AND gameType = :gameType")
    LiveData<Integer> getHighestScore(int userId, String gameType);

    @Query("SELECT AVG(score) FROM game_scores WHERE userId = :userId AND gameType = :gameType")
    LiveData<Double> getAverageScore(int userId, String gameType);

    @Query("SELECT * FROM game_scores WHERE userId = :userId ORDER BY datePlayed DESC LIMIT :limit")
    LiveData<List<GameScore>> getRecentScores(int userId, int limit);

    @Query("SELECT * FROM game_scores WHERE gameType = :gameType ORDER BY score DESC LIMIT :limit")
    LiveData<List<GameScore>> getTopScores(String gameType, int limit);

    @Query("SELECT * FROM game_scores WHERE category = :category ORDER BY score DESC")
    LiveData<List<GameScore>> getScoresByCategory(String category);

    @Query("SELECT COUNT(*) FROM game_scores WHERE userId = :userId")
    LiveData<Integer> getTotalGamesPlayed(int userId);

    @Query("SELECT COUNT(*) FROM game_scores")
    LiveData<Integer> getTotalCount();

    @Query("DELETE FROM game_scores")
    void deleteAll();

    @Query("DELETE FROM game_scores WHERE userId = :userId")
    void deleteScoresByUserId(int userId);
}