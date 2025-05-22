package com.example.englishhindi.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.englishhindi.data.model.Word
import com.example.englishhindi.data.relation.WordWithCategories
import com.example.englishhindi.data.relation.WordWithProgress
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the Word entity.
 */
@Dao
interface WordDao {
    // Basic CRUD operations
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: Word): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(words: List<Word>): List<Long>
    
    @Update
    suspend fun update(word: Word)
    
    @Delete
    suspend fun delete(word: Word)
    
    // Queries
    
    @Query("SELECT * FROM words WHERE id = :wordId")
    suspend fun getWordById(wordId: Long): Word?
    
    @Query("SELECT * FROM words WHERE id = :wordId")
    fun getWordByIdLiveData(wordId: Long): LiveData<Word?>
    
    @Query("SELECT * FROM words WHERE id = :wordId")
    fun getWordByIdFlow(wordId: Long): Flow<Word?>
    
    @Query("SELECT * FROM words ORDER BY englishText ASC")
    fun getAllWords(): Flow<List<Word>>
    
    @Query("SELECT * FROM words ORDER BY englishText ASC")
    fun getAllWordsLiveData(): LiveData<List<Word>>
    
    @Query("SELECT * FROM words WHERE isFavorite = 1 ORDER BY englishText ASC")
    fun getFavoriteWords(): Flow<List<Word>>
    
    // Search queries
    
    @Query("SELECT * FROM words WHERE englishText LIKE '%' || :query || '%' OR hindiText LIKE '%' || :query || '%' ORDER BY englishText ASC")
    fun searchWords(query: String): Flow<List<Word>>
    
    @Query("SELECT * FROM words WHERE difficulty = :level ORDER BY englishText ASC")
    fun getWordsByDifficulty(level: Int): Flow<List<Word>>
    
    @Query("SELECT * FROM words ORDER BY RANDOM() LIMIT :limit")
    suspend fun getRandomWords(limit: Int): List<Word>
    
    // Advanced queries with relationships
    
    @Transaction
    @Query("SELECT * FROM words WHERE id = :wordId")
    suspend fun getWordWithCategories(wordId: Long): WordWithCategories?
    
    @Transaction
    @Query("SELECT * FROM words")
    fun getAllWordsWithCategories(): Flow<List<WordWithCategories>>
    
    @Transaction
    @Query("SELECT * FROM words WHERE id = :wordId")
    suspend fun getWordWithProgress(wordId: Long): WordWithProgress?
    
    @Transaction
    @Query("SELECT w.* FROM words w INNER JOIN word_category_cross_refs wc ON w.id = wc.wordId WHERE wc.categoryId = :categoryId ORDER BY w.englishText ASC")
    fun getWordsByCategory(categoryId: Long): Flow<List<Word>>
    
    @Transaction
    @Query("SELECT w.* FROM words w INNER JOIN word_category_cross_refs wc ON w.id = wc.wordId WHERE wc.categoryId = :categoryId ORDER BY w.englishText ASC")
    fun getWordsByCategoryLiveData(categoryId: Long): LiveData<List<Word>>
    
    // Progress-related queries
    
    @Transaction
    @Query("""
        SELECT w.* FROM words w
        INNER JOIN user_progress up ON w.id = up.wordId
        WHERE up.nextReviewDue <= :currentTimeMillis
        ORDER BY up.nextReviewDue ASC
        LIMIT :limit
    """)
    suspend fun getWordsForReview(currentTimeMillis: Long, limit: Int): List<WordWithProgress>
    
    @Transaction
    @Query("""
        SELECT w.* FROM words w
        LEFT JOIN user_progress up ON w.id = up.wordId
        WHERE up.wordId IS NULL
        ORDER BY RANDOM()
        LIMIT :limit
    """)
    suspend fun getNewWordsForLearning(limit: Int): List<Word>
    
    // Statistics queries
    
    @Query("SELECT COUNT(*) FROM words")
    fun getTotalWordCount(): Flow<Int>
    
    @Query("SELECT COUNT(*) FROM words WHERE isFavorite = 1")
    fun getFavoriteWordCount(): Flow<Int>
    
    @Query("""
        SELECT COUNT(*) FROM words w
        INNER JOIN user_progress up ON w.id = up.wordId
        WHERE up.proficiencyLevel >= 85
    """)
    fun getMasteredWordCount(): Flow<Int>
    
    @Query("""
        SELECT COUNT(*) FROM words w
        INNER JOIN user_progress up ON w.id = up.wordId
        WHERE up.totalAttempts > 0
    """)
    fun getStartedWordCount(): Flow<Int>
    
    // Utility queries
    
    @Query("SELECT EXISTS(SELECT 1 FROM words WHERE englishText = :englishText LIMIT 1)")
    suspend fun wordExistsByEnglishText(englishText: String): Boolean
    
    @Query("SELECT COUNT(*) FROM words w INNER JOIN word_category_cross_refs wc ON w.id = wc.wordId WHERE wc.categoryId = :categoryId")
    fun getWordCountByCategory(categoryId: Long): Flow<Int>
}