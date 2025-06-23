package com.bhashasetu.app.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.bhashasetu.app.model.Word;
import java.util.List;

/**
 * Data Access Object for Word entities.
 * ✅ ROOM ERROR FIX: Fixed column name from 'order' to 'orderInLesson'
 */
@Dao
public interface WordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertWord(Word word);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWords(List<Word> words);

    @Update
    void updateWord(Word word);

    @Delete
    void deleteWord(Word word);

    @Query("DELETE FROM vocabulary_words WHERE id = :wordId")
    void deleteWordById(long wordId);

    // ✅ CORRECTED QUERIES - Using actual Word entity field names

    @Query("SELECT * FROM vocabulary_words WHERE id = :wordId")
    LiveData<Word> getWordById(long wordId);

    @Query("SELECT * FROM vocabulary_words WHERE categoryId = :categoryId AND isActive = 1 ORDER BY orderInLesson ASC")
    LiveData<List<Word>> getWordsByCategory(long categoryId);

    @Query("SELECT * FROM vocabulary_words WHERE lessonId = :lessonId AND isActive = 1 ORDER BY orderInLesson ASC")
    LiveData<List<Word>> getWordsForLesson(long lessonId);

    // ✅ Search using both English and Hindi words
    @Query("SELECT * FROM vocabulary_words WHERE " +
            "(englishWord LIKE '%' || :searchQuery || '%' OR " +
            "hindiWord LIKE '%' || :searchQuery || '%' OR " +
            "englishText LIKE '%' || :searchQuery || '%' OR " +
            "localText LIKE '%' || :searchQuery || '%') " +
            "AND isActive = 1")
    LiveData<List<Word>> searchWords(String searchQuery);

    @Query("SELECT * FROM vocabulary_words WHERE difficulty = :level AND isActive = 1 ORDER BY orderInLesson ASC")
    LiveData<List<Word>> getWordsByDifficulty(int level);

    @Query("SELECT * FROM vocabulary_words WHERE categoryId = :categoryId AND difficulty = :level AND isActive = 1 ORDER BY orderInLesson ASC")
    LiveData<List<Word>> getWordsByCategoryAndDifficulty(long categoryId, int level);

    @Query("SELECT COUNT(*) FROM vocabulary_words WHERE categoryId = :categoryId AND isActive = 1")
    LiveData<Integer> getWordCountByCategory(long categoryId);

    @Query("SELECT COUNT(*) FROM vocabulary_words WHERE lessonId = :lessonId AND isActive = 1")
    LiveData<Integer> getWordCountByLesson(long lessonId);

    @Query("SELECT * FROM vocabulary_words WHERE isActive = 1 ORDER BY timeAdded DESC LIMIT :limit")
    LiveData<List<Word>> getRecentWords(int limit);

    // ✅ Queries specific to your Hindi-English learning app
    @Query("SELECT * FROM vocabulary_words WHERE category = :category AND isActive = 1 ORDER BY englishWord ASC")
    LiveData<List<Word>> getWordsByCategory(String category);

    @Query("SELECT * FROM vocabulary_words WHERE isFavorite = 1 AND isActive = 1 ORDER BY englishWord ASC")
    LiveData<List<Word>> getFavoriteWords();

    @Query("SELECT * FROM vocabulary_words WHERE masteryLevel < :maxLevel AND isActive = 1 ORDER BY lastPracticed ASC")
    LiveData<List<Word>> getWordsNeedingPractice(int maxLevel);

    @Query("SELECT * FROM vocabulary_words WHERE nextReviewDue <= :currentTime AND isActive = 1 ORDER BY nextReviewDue ASC")
    LiveData<List<Word>> getWordsDueForReview(long currentTime);

    @Query("SELECT * FROM vocabulary_words WHERE partOfSpeech = :partOfSpeech AND isActive = 1 ORDER BY englishWord ASC")
    LiveData<List<Word>> getWordsByPartOfSpeech(String partOfSpeech);

    // ✅ Update queries using correct field names
    @Query("UPDATE vocabulary_words SET orderInLesson = :newOrder, updatedAt = :timestamp WHERE id = :wordId")
    void updateWordOrder(long wordId, int newOrder, long timestamp);

    @Query("UPDATE vocabulary_words SET isActive = :isActive, updatedAt = :timestamp WHERE id = :wordId")
    void updateWordStatus(long wordId, boolean isActive, long timestamp);

    @Query("UPDATE vocabulary_words SET isFavorite = :isFavorite, updatedAt = :timestamp WHERE id = :wordId")
    void updateFavoriteStatus(long wordId, boolean isFavorite, long timestamp);

    @Query("UPDATE vocabulary_words SET masteryLevel = :level, updatedAt = :timestamp WHERE id = :wordId")
    void updateMasteryLevel(long wordId, int level, long timestamp);

    @Query("UPDATE vocabulary_words SET " +
            "correctAttempts = :correctAttempts, " +
            "totalAttempts = :totalAttempts, " +
            "lastPracticed = :lastPracticed, " +
            "nextReviewDue = :nextReviewDue, " +
            "masteryLevel = :masteryLevel, " +
            "updatedAt = :timestamp " +
            "WHERE id = :wordId")
    void updateProgress(long wordId, int correctAttempts, int totalAttempts,
                        long lastPracticed, long nextReviewDue, int masteryLevel, long timestamp);

    // ✅ Analytics and statistics queries
    @Query("SELECT AVG(masteryLevel) FROM vocabulary_words WHERE isActive = 1")
    LiveData<Double> getAverageMasteryLevel();

    @Query("SELECT COUNT(*) FROM vocabulary_words WHERE masteryLevel >= :minLevel AND isActive = 1")
    LiveData<Integer> getWordsWithMinMastery(int minLevel);

    @Query("SELECT category, COUNT(*) as count FROM vocabulary_words WHERE isActive = 1 GROUP BY category")
    LiveData<List<CategoryWordCount>> getWordCountByCategory();

    @Query("SELECT difficulty, COUNT(*) as count FROM vocabulary_words WHERE isActive = 1 GROUP BY difficulty ORDER BY difficulty")
    LiveData<List<DifficultyWordCount>> getWordCountByDifficulty();

    // ✅ Advanced search and filtering
    @Query("SELECT * FROM vocabulary_words WHERE " +
            "difficulty BETWEEN :minDifficulty AND :maxDifficulty " +
            "AND masteryLevel BETWEEN :minMastery AND :maxMastery " +
            "AND isActive = 1 " +
            "ORDER BY orderInLesson ASC")
    LiveData<List<Word>> getWordsFiltered(int minDifficulty, int maxDifficulty,
                                          int minMastery, int maxMastery);

    @Query("SELECT * FROM vocabulary_words WHERE " +
            "hasEnglishAudio = 1 AND hasHindiAudio = 1 AND isActive = 1 " +
            "ORDER BY englishWord ASC")
    LiveData<List<Word>> getWordsWithAudio();

    @Query("SELECT * FROM vocabulary_words WHERE " +
            "hasImage = 1 AND isActive = 1 " +
            "ORDER BY englishWord ASC")
    LiveData<List<Word>> getWordsWithImages();

    // ✅ Complex join queries (if you have lessons table)
    @Query("SELECT w.* FROM vocabulary_words w " +
            "WHERE w.lessonId = :lessonId AND w.isActive = 1 " +
            "ORDER BY w.orderInLesson ASC")
    LiveData<List<Word>> getWordsForLessonWithDetails(long lessonId);

    // ✅ Random word selection for games
    @Query("SELECT * FROM vocabulary_words WHERE " +
            "difficulty = :difficulty AND isActive = 1 " +
            "ORDER BY RANDOM() LIMIT :count")
    LiveData<List<Word>> getRandomWordsByDifficulty(int difficulty, int count);

    @Query("SELECT * FROM vocabulary_words WHERE " +
            "category = :category AND isActive = 1 " +
            "ORDER BY RANDOM() LIMIT :count")
    LiveData<List<Word>> getRandomWordsByCategory(String category, int count);

    // ✅ Utility classes for complex query results
    class CategoryWordCount {
        public String category;
        public int count;
    }

    class DifficultyWordCount {
        public int difficulty;
        public int count;
    }
}