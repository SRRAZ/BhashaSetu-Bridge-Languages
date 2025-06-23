package com.bhashasetu.app.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bhashasetu.app.data.model.Word
import kotlinx.coroutines.flow.Flow
import com.bhashasetu.app.data.relation.WordWithCategories
import com.bhashasetu.app.data.relation.WordWithProgress

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
    
    @Query("SELECT * FROM vocabulary_words WHERE id = :wordId")
    suspend fun getWordById(wordId: Long): Word?
    
    @Query("SELECT * FROM vocabulary_words WHERE id = :wordId")
    fun getWordByIdLiveData(wordId: Long): LiveData<Word?>
    
    @Query("SELECT * FROM vocabulary_words WHERE id = :wordId")
    fun getWordByIdFlow(wordId: Long): Flow<Word?>
    
    @Query("SELECT * FROM vocabulary_words ORDER BY englishText ASC")
    fun getAllWords(): Flow<List<Word>>
    
    @Query("SELECT * FROM vocabulary_words ORDER BY englishText ASC")
    fun getAllWordsLiveData(): LiveData<List<Word>>
    
    @Query("SELECT * FROM vocabulary_words WHERE isFavorite = 1 ORDER BY englishText ASC")
    fun getFavoriteWords(): Flow<List<Word>>
    
    // Search queries
    
    @Query("SELECT * FROM vocabulary_words WHERE englishText LIKE '%' || :query || '%' OR hindiText LIKE '%' || :query || '%' ORDER BY englishText ASC")
    fun searchWords(query: String): Flow<List<Word>>
    
    @Query("SELECT * FROM vocabulary_words WHERE difficulty = :level ORDER BY englishText ASC")
    fun getWordsByDifficulty(level: Int): Flow<List<Word>>
    
    @Query("SELECT * FROM vocabulary_words ORDER BY RANDOM() LIMIT :limit")
    suspend fun getRandomWords(limit: Int): List<Word>
    
    // Advanced queries with relationships
    
    @Transaction
    @Query("SELECT * FROM vocabulary_words WHERE id = :wordId")
    suspend fun getWordWithCategories(wordId: Long): WordWithCategories?
    
    @Transaction
    @Query("SELECT * FROM vocabulary_words")
    fun getAllWordsWithCategories(): Flow<List<WordWithCategories>>
    
    @Transaction
    @Query("SELECT * FROM vocabulary_words WHERE id = :wordId")
    suspend fun getWordWithProgress(wordId: Long): WordWithProgress?
    
    @Transaction
    @Query("SELECT w.* FROM vocabulary_words w INNER JOIN word_category_cross_refs wc ON w.id = wc.wordId WHERE wc.categoryId = :categoryId ORDER BY w.englishText ASC")
    fun getWordsByCategory(categoryId: Long): Flow<List<Word>>
    
    @Transaction
    @Query("SELECT w.* FROM vocabulary_words w INNER JOIN word_category_cross_refs wc ON w.id = wc.wordId WHERE wc.categoryId = :categoryId ORDER BY w.englishText ASC")
    fun getWordsByCategoryLiveData(categoryId: Long): LiveData<List<Word>>
    
    // Progress-related queries
    
    @Transaction
    @Query("""
        SELECT w.* FROM vocabulary_words w
        INNER JOIN user_learning_progress up ON w.id = up.wordId
        WHERE up.nextReviewDue <= :currentTimeMillis
        ORDER BY up.nextReviewDue ASC
        LIMIT :limit
    """)
    suspend fun getWordsForReview(currentTimeMillis: Long, limit: Int): List<WordWithProgress>
    
    @Transaction
    @Query("""
        SELECT w.* FROM vocabulary_words w
        LEFT JOIN user_learning_progress up ON w.id = up.wordId
        WHERE up.wordId IS NULL
        ORDER BY RANDOM()
        LIMIT :limit
    """)
    suspend fun getNewWordsForLearning(limit: Int): List<Word>
    
    // Statistics queries
    
    @Query("SELECT COUNT(*) FROM vocabulary_words")
    fun getTotalWordCount(): Flow<Int>
    
    @Query("SELECT COUNT(*) FROM vocabulary_words WHERE isFavorite = 1")
    fun getFavoriteWordCount(): Flow<Int>
    
    @Query("""
        SELECT COUNT(*) FROM vocabulary_words w
        INNER JOIN user_learning_progress up ON w.id = up.wordId
        WHERE up.proficiencyLevel >= 85
    """)
    fun getMasteredWordCount(): Flow<Int>
    
    @Query("""
        SELECT COUNT(*) FROM vocabulary_words w
        INNER JOIN user_learning_progress up ON w.id = up.wordId
        WHERE up.totalAttempts > 0
    """)
    fun getStartedWordCount(): Flow<Int>
    
    // Utility queries
    
    @Query("SELECT EXISTS(SELECT 1 FROM vocabulary_words WHERE englishText = :englishText LIMIT 1)")
    suspend fun wordExistsByEnglishText(englishText: String): Boolean
    
    @Query("SELECT COUNT(*) FROM vocabulary_words w INNER JOIN word_category_cross_refs wc ON w.id = wc.wordId WHERE wc.categoryId = :categoryId")
    fun getWordCountByCategory(categoryId: Long): Flow<Int>

    /**
     * ✅ FIXED: Get words for specific lesson using correct column names
     */
    @Query("""
        SELECT w.* FROM vocabulary_words w
        INNER JOIN lesson_words lw ON w.id = lw.wordId
        WHERE lw.lessonId = :lessonId 
        AND w.isActive = 1
        ORDER BY lw.orderInLesson ASC
    """)
    fun getWordsForLessonOrdered(lessonId: Long): LiveData<List<Word>>

    /**
     * ✅ FIXED: Get keywords for specific lesson
     */
    @Query("""
        SELECT w.* FROM vocabulary_words w
        INNER JOIN lesson_words lw ON w.id = lw.wordId
        WHERE lw.lessonId = :lessonId 
        AND lw.isKeyword = 1
        AND w.isActive = 1
        ORDER BY lw.orderInLesson ASC
    """)
    fun getKeywordsForLesson(lessonId: Long): LiveData<List<Word>>

    /**
     * ✅ FIXED: Get quiz words for specific lesson
     */
    @Query("""
        SELECT w.* FROM vocabulary_words w
        INNER JOIN lesson_words lw ON w.id = lw.wordId
        WHERE lw.lessonId = :lessonId 
        AND lw.includeInQuiz = 1
        AND w.isActive = 1
        ORDER BY lw.orderInLesson ASC
    """)
    fun getQuizWordsForLesson(lessonId: Long): LiveData<List<Word>>

    /**
     * ✅ FIXED: Search words within a specific lesson
     */
    @Query("""
        SELECT w.* FROM vocabulary_words w
        INNER JOIN lesson_words lw ON w.id = lw.wordId
        WHERE lw.lessonId = :lessonId 
        AND (w.englishWord LIKE '%' || :searchQuery || '%' 
             OR w.hindiWord LIKE '%' || :searchQuery || '%')
        AND w.isActive = 1
        ORDER BY lw.orderInLesson ASC
    """)
    fun searchWordsInLesson(lessonId: Long, searchQuery: String): LiveData<List<Word>>

    /**
     * ✅ FIXED: Get words by mastery level within lesson
     */
    @Query("""
        SELECT w.* FROM vocabulary_words w
        INNER JOIN lesson_words lw ON w.id = lw.wordId
        WHERE lw.lessonId = :lessonId 
        AND w.masteryLevel >= :minMastery
        AND w.isActive = 1
        ORDER BY w.masteryLevel DESC, lw.orderInLesson ASC
    """)
    fun getWordsByMasteryInLesson(lessonId: Long, minMastery: Int): LiveData<List<Word>>

    /**
     * ✅ NEW: Get lesson statistics
     */
    @Query("""
        SELECT 
            COUNT(*) as totalWords,
            COUNT(CASE WHEN lw.isKeyword = 1 THEN 1 END) as keywordCount,
            COUNT(CASE WHEN lw.includeInQuiz = 1 THEN 1 END) as quizWordCount,
            AVG(w.masteryLevel) as averageMastery
        FROM vocabulary_words w
        INNER JOIN lesson_words lw ON w.id = lw.wordId
        WHERE lw.lessonId = :lessonId 
        AND w.isActive = 1
    """)
    fun getLessonWordStats(lessonId: Long): LiveData<LessonWordStats>

    data class LessonWordStats(
        val totalWords: Int,
        val keywordCount: Int,
        val quizWordCount: Int,
        val averageMastery: Double
    )
}