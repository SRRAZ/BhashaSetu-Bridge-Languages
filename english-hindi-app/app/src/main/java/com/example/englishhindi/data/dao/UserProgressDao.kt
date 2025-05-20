package com.example.englishhindi.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.englishhindi.data.model.UserProgress
import com.example.englishhindi.data.relation.WordWithProgress
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the UserProgress entity.
 */
@Dao
interface UserProgressDao {
    // Basic CRUD operations
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(progress: UserProgress)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(progressList: List<UserProgress>)
    
    @Update
    suspend fun update(progress: UserProgress)
    
    @Delete
    suspend fun delete(progress: UserProgress)
    
    @Query("DELETE FROM user_progress WHERE wordId = :wordId")
    suspend fun deleteProgressForWord(wordId: Long)
    
    // Queries
    
    @Query("SELECT * FROM user_progress WHERE wordId = :wordId")
    suspend fun getProgressForWord(wordId: Long): UserProgress?
    
    @Query("SELECT * FROM user_progress WHERE wordId = :wordId")
    fun getProgressForWordLiveData(wordId: Long): LiveData<UserProgress?>
    
    @Query("SELECT * FROM user_progress WHERE wordId = :wordId")
    fun getProgressForWordFlow(wordId: Long): Flow<UserProgress?>
    
    @Query("SELECT * FROM user_progress ORDER BY lastAttemptAt DESC")
    fun getAllProgress(): Flow<List<UserProgress>>
    
    // Advanced queries with relationships
    
    @Transaction
    @Query("""
        SELECT w.* FROM words w
        INNER JOIN user_progress up ON w.id = up.wordId
        ORDER BY up.proficiencyLevel ASC
    """)
    fun getWordsWithProgressByProficiency(): Flow<List<WordWithProgress>>
    
    @Transaction
    @Query("""
        SELECT w.* FROM words w
        INNER JOIN user_progress up ON w.id = up.wordId
        WHERE up.proficiencyLevel < 100
        ORDER BY up.lastAttemptAt DESC
        LIMIT :limit
    """)
    suspend fun getRecentlyStudiedWords(limit: Int): List<WordWithProgress>
    
    @Transaction
    @Query("""
        SELECT w.* FROM words w
        INNER JOIN user_progress up ON w.id = up.wordId
        WHERE up.nextReviewDue <= :currentTimeMs
        ORDER BY up.nextReviewDue ASC
        LIMIT :limit
    """)
    suspend fun getWordsForReview(currentTimeMs: Long, limit: Int): List<WordWithProgress>
    
    @Transaction
    @Query("""
        SELECT w.* FROM words w
        INNER JOIN user_progress up ON w.id = up.wordId
        INNER JOIN word_category_cross_refs wc ON w.id = wc.wordId
        WHERE wc.categoryId = :categoryId AND up.nextReviewDue <= :currentTimeMs
        ORDER BY up.nextReviewDue ASC
        LIMIT :limit
    """)
    suspend fun getWordsForReviewByCategory(
        categoryId: Long,
        currentTimeMs: Long,
        limit: Int
    ): List<WordWithProgress>
    
    // Spaced repetition scheduling
    
    @Query("""
        UPDATE user_progress
        SET 
            proficiencyLevel = :proficiencyLevel,
            correctAttempts = correctAttempts + CASE WHEN :isCorrect THEN 1 ELSE 0 END,
            totalAttempts = totalAttempts + 1,
            lastAttemptAt = :attemptTimeMs,
            lastCorrectAt = CASE WHEN :isCorrect THEN :attemptTimeMs ELSE lastCorrectAt END,
            lastConfidenceRating = :confidenceRating,
            easeFactor = :newEaseFactor,
            intervalDays = :newIntervalDays,
            repetitions = :newRepetitions,
            nextReviewDue = :nextReviewDueMs,
            timeSpentMs = timeSpentMs + :timeSpentMs
        WHERE wordId = :wordId
    """)
    suspend fun updateProgressAfterReview(
        wordId: Long,
        isCorrect: Boolean,
        proficiencyLevel: Int,
        confidenceRating: Int,
        newEaseFactor: Float,
        newIntervalDays: Int,
        newRepetitions: Int,
        attemptTimeMs: Long,
        nextReviewDueMs: Long,
        timeSpentMs: Long
    )
    
    @Query("SELECT COUNT(*) FROM user_progress")
    fun getTotalProgressCount(): Flow<Int>
    
    // Statistics queries
    
    @Query("SELECT COUNT(*) FROM user_progress WHERE proficiencyLevel >= 85")
    fun getMasteredWordCount(): Flow<Int>
    
    @Query("SELECT COUNT(*) FROM user_progress WHERE proficiencyLevel > 0 AND proficiencyLevel < 85")
    fun getLearningWordCount(): Flow<Int>
    
    @Query("SELECT COUNT(*) FROM user_progress WHERE totalAttempts > 0")
    fun getStartedWordCount(): Flow<Int>
    
    @Query("SELECT SUM(timeSpentMs) FROM user_progress")
    fun getTotalStudyTime(): Flow<Long?>
    
    @Query("""
        SELECT AVG(CAST(correctAttempts AS FLOAT) / CAST(totalAttempts AS FLOAT) * 100)
        FROM user_progress
        WHERE totalAttempts > 0
    """)
    fun getAverageAccuracy(): Flow<Float?>
    
    @Query("""
        SELECT COUNT(*) FROM user_progress
        WHERE proficiencyLevel >= :targetProficiency
        AND wordId IN (
            SELECT wordId FROM word_category_cross_refs
            WHERE categoryId = :categoryId
        )
    """)
    fun getMasteredWordCountByCategory(categoryId: Long, targetProficiency: Int = 85): Flow<Int>
    
    @Query("""
        SELECT COUNT(*) FROM word_category_cross_refs
        WHERE categoryId = :categoryId
    """)
    fun getTotalWordCountByCategory(categoryId: Long): Flow<Int>
}