package com.bhashasetu.app.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bhashasetu.app.data.model.Quiz
import com.bhashasetu.app.data.model.QuizQuestion
import com.bhashasetu.app.data.relation.QuizWithQuestions
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the Quiz entity.
 */
@Dao
interface QuizDao {
    // Basic CRUD operations
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(quiz: Quiz): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(quizzes: List<Quiz>): List<Long>
    
    @Update
    suspend fun update(quiz: Quiz)
    
    @Delete
    suspend fun delete(quiz: Quiz)
    
    // Question operations
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestion(question: QuizQuestion): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<QuizQuestion>): List<Long>
    
    @Update
    suspend fun updateQuestion(question: QuizQuestion)
    
    @Delete
    suspend fun deleteQuestion(question: QuizQuestion)
    
    @Query("DELETE FROM quiz_questions WHERE quizId = :quizId")
    suspend fun deleteAllQuestionsForQuiz(quizId: Long)
    
    // Queries
    
    @Query("SELECT * FROM quizzes WHERE id = :quizId")
    suspend fun getQuizById(quizId: Long): Quiz?
    
    @Query("SELECT * FROM quizzes WHERE id = :quizId")
    fun getQuizByIdLiveData(quizId: Long): LiveData<Quiz?>
    
    @Query("SELECT * FROM quizzes WHERE id = :quizId")
    fun getQuizByIdFlow(quizId: Long): Flow<Quiz?>
    
    @Query("SELECT * FROM quizzes ORDER BY titleEnglish ASC")
    fun getAllQuizzes(): Flow<List<Quiz>>
    
    @Query("SELECT * FROM quizzes ORDER BY titleEnglish ASC")
    fun getAllQuizzesLiveData(): LiveData<List<Quiz>>
    
    @Query("SELECT * FROM quizzes WHERE lessonId = :lessonId")
    fun getQuizzesByLesson(lessonId: Long): Flow<List<Quiz>>
    
    @Query("SELECT * FROM quizzes WHERE categoryId = :categoryId")
    fun getQuizzesByCategory(categoryId: Long): Flow<List<Quiz>>
    
    @Query("SELECT * FROM quizzes WHERE difficulty = :difficulty")
    fun getQuizzesByDifficulty(difficulty: Int): Flow<List<Quiz>>
    
    @Query("SELECT * FROM quizzes WHERE isCompleted = :isCompleted")
    fun getQuizzesByCompletionStatus(isCompleted: Boolean): Flow<List<Quiz>>
    
    @Query("SELECT * FROM quizzes WHERE titleEnglish LIKE '%' || :query || '%' OR titleHindi LIKE '%' || :query || '%'")
    fun searchQuizzes(query: String): Flow<List<Quiz>>
    
    // Question queries
    
    @Query("SELECT * FROM quiz_questions WHERE id = :questionId")
    suspend fun getQuestionById(questionId: Long): QuizQuestion?
    
    @Query("SELECT * FROM quiz_questions WHERE quizId = :quizId ORDER BY orderInQuiz ASC")
    fun getQuestionsForQuiz(quizId: Long): Flow<List<QuizQuestion>>
    
    @Query("SELECT * FROM quiz_questions WHERE quizId = :quizId AND questionType = :questionType ORDER BY orderInQuiz ASC")
    fun getQuestionsForQuizByType(quizId: Long, questionType: Int): Flow<List<QuizQuestion>>
    
    // Advanced queries with relationships
    
    @Transaction
    @Query("SELECT * FROM quizzes WHERE id = :quizId")
    suspend fun getQuizWithQuestions(quizId: Long): QuizWithQuestions?
    
    @Transaction
    @Query("SELECT * FROM quizzes WHERE id = :quizId")
    fun getQuizWithQuestionsFlow(quizId: Long): Flow<QuizWithQuestions?>
    
    @Transaction
    @Query("SELECT * FROM quizzes")
    fun getAllQuizzesWithQuestions(): Flow<List<QuizWithQuestions>>
    
    @Transaction
    @Query("SELECT * FROM quizzes WHERE lessonId = :lessonId")
    fun getQuizzesWithQuestionsByLesson(lessonId: Long): Flow<List<QuizWithQuestions>>
    
    @Transaction
    @Query("SELECT * FROM quizzes WHERE categoryId = :categoryId")
    fun getQuizzesWithQuestionsByCategory(categoryId: Long): Flow<List<QuizWithQuestions>>
    
    // Progress tracking
    
    @Query("""
        UPDATE quizzes
        SET isCompleted = :isCompleted,
            lastAttemptScore = :score,
            bestScore = CASE WHEN :score > IFNULL(bestScore, 0) THEN :score ELSE bestScore END,
            attemptCount = attemptCount + 1,
            lastAttemptAt = :attemptTime,
            completedAt = CASE WHEN :isCompleted THEN :attemptTime ELSE completedAt END
        WHERE id = :quizId
    """)
    suspend fun updateQuizProgress(
        quizId: Long,
        isCompleted: Boolean,
        score: Int,
        attemptTime: Long
    )
    
    @Query("""
        UPDATE quiz_questions
        SET correctAttempts = correctAttempts + CASE WHEN :isCorrect THEN 1 ELSE 0 END,
            totalAttempts = totalAttempts + 1
        WHERE id = :questionId
    """)
    suspend fun updateQuestionStats(questionId: Long, isCorrect: Boolean)
    
    // Statistics queries
    
    @Query("SELECT COUNT(*) FROM quizzes")
    fun getQuizCount(): Flow<Int>
    
    @Query("SELECT COUNT(*) FROM quizzes WHERE isCompleted = 1")
    fun getCompletedQuizCount(): Flow<Int>
    
    @Query("SELECT AVG(CAST(lastAttemptScore AS FLOAT) / (SELECT COUNT(*) FROM quiz_questions WHERE quizId = quizzes.id)) FROM quizzes WHERE lastAttemptScore IS NOT NULL")
    fun getAverageQuizScore(): Flow<Float?>
    
    @Query("SELECT COUNT(*) FROM quiz_questions")
    fun getTotalQuestionCount(): Flow<Int>
    
    @Query("SELECT SUM(correctAttempts) FROM quiz_questions")
    fun getTotalCorrectAnswers(): Flow<Int?>
    
    @Query("SELECT SUM(totalAttempts) FROM quiz_questions")
    fun getTotalQuestionAttempts(): Flow<Int?>
}