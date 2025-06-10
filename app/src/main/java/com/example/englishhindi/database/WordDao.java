package com.bhashasetu.app.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.bhashasetu.app.model.Word;

import java.util.List;

@Dao
public interface WordDao {
    
    @Insert
    long insert(Word word);
    
    @Update
    void update(Word word);
    
    @Delete
    void delete(Word word);
    
    @Query("DELETE FROM words")
    void deleteAllWords();
    
    @Query("SELECT COUNT(*) FROM words")
    int getWordCount();
    
    @Query("SELECT * FROM words ORDER BY englishWord ASC")
    LiveData<List<Word>> getAllWords();
    
    @Query("SELECT * FROM words WHERE category = :category ORDER BY englishWord ASC")
    LiveData<List<Word>> getWordsByCategory(String category);
    
    @Query("SELECT * FROM words WHERE isFavorite = 1 ORDER BY englishWord ASC")
    LiveData<List<Word>> getFavoriteWords();
    
    @Query("SELECT * FROM words WHERE englishWord LIKE '%' || :query || '%' OR hindiWord LIKE '%' || :query || '%'")
    LiveData<List<Word>> searchWords(String query);
    
    @Query("SELECT * FROM words ORDER BY masteryLevel ASC LIMIT :limit")
    LiveData<List<Word>> getLeastMasteredWords(int limit);
    
    @Query("SELECT * FROM words WHERE id IN (:wordIds)")
    LiveData<List<Word>> getWordsByIds(List<Integer> wordIds);
    
    @Query("SELECT * FROM words WHERE difficulty <= :maxDifficulty ORDER BY RANDOM() LIMIT :limit")
    LiveData<List<Word>> getRandomWordsByDifficulty(int maxDifficulty, int limit);
    
    @Query("SELECT * FROM words WHERE id = :wordId")
    LiveData<Word> getWordById(int wordId);
    
    @Query("SELECT DISTINCT category FROM words ORDER BY category ASC")
    LiveData<List<String>> getAllCategories();
    
    @Transaction
    @Query("SELECT w.* FROM words w " +
           "INNER JOIN lesson_words lw ON w.id = lw.wordId " +
           "WHERE lw.lessonId = :lessonId ORDER BY lw.`order` ASC")
    LiveData<List<Word>> getWordsForLesson(int lessonId);
}