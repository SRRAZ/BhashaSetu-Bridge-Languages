package com.bhashasetu.app.data.repository

import androidx.lifecycle.LiveData
import com.bhashasetu.app.data.dao.QuizDao
import com.bhashasetu.app.data.model.Quiz
import com.bhashasetu.app.data.model.QuizQuestion
import com.bhashasetu.app.data.relation.QuizWithQuestions
import kotlinx.coroutines.flow.Flow
import java.util.Date

class QuizRepository(private val quizDao: QuizDao) {

    val allQuizzes: Flow<List<Quiz>> = quizDao.getAllQuizzes()
    
    suspend fun insert(quiz: Quiz): Long {
        return quizDao.insert(quiz)
    }
    
    suspend fun update(quiz: Quiz) {
        quizDao.update(quiz)
    }
    
    suspend fun delete(quiz: Quiz) {
        quizDao.delete(quiz)
    }
    
    fun getQuizById(id: Long): Flow<Quiz> {
        return quizDao.getQuizById(id)
    }
    
    fun getQuizzesByType(quizType: String): Flow<List<Quiz>> {
        return quizDao.getQuizzesByType(quizType)
    }
    
    fun getQuizzesByDifficulty(difficulty: Int): Flow<List<Quiz>> {
        return quizDao.getQuizzesByDifficulty(difficulty)
    }
    
    fun getQuizWithQuestions(quizId: Long): Flow<QuizWithQuestions> {
        return quizDao.getQuizWithQuestions(quizId)
    }
    
    fun getAllQuizzesWithQuestions(): Flow<List<QuizWithQuestions>> {
        return quizDao.getAllQuizzesWithQuestions()
    }
    
    suspend fun insertQuestion(question: QuizQuestion) {
        quizDao.insertQuestion(question)
    }
    
    suspend fun updateQuestion(question: QuizQuestion) {
        quizDao.updateQuestion(question)
    }
    
    suspend fun deleteQuestion(question: QuizQuestion) {
        quizDao.deleteQuestion(question)
    }
    
    suspend fun updateLastAttemptAndScore(quizId: Long, lastAttemptDate: Date, score: Int) {
        quizDao.updateLastAttemptAndScore(quizId, lastAttemptDate, score)
    }
    
    suspend fun incrementCompletionCount(quizId: Long) {
        quizDao.incrementCompletionCount(quizId)
    }
    
    fun getQuizzesByLessonId(lessonId: Long): Flow<List<Quiz>> {
        return quizDao.getQuizzesByLessonId(lessonId)
    }
    
    fun getQuizzesByCategoryId(categoryId: Long): Flow<List<Quiz>> {
        return quizDao.getQuizzesByCategoryId(categoryId)
    }
    
    fun getQuestionsByQuizId(quizId: Long): Flow<List<QuizQuestion>> {
        return quizDao.getQuestionsByQuizId(quizId)
    }
    
    fun getCompletedQuizCount(): Flow<Int> {
        return quizDao.getCompletedQuizCount()
    }
    
    fun getAverageQuizScore(): Flow<Float> {
        return quizDao.getAverageQuizScore()
    }
}