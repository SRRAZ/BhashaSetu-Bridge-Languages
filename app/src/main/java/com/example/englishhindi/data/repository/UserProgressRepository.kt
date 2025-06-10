package com.bhashasetu.app.data.repository

import androidx.lifecycle.LiveData
import com.bhashasetu.app.data.dao.UserProgressDao
import com.bhashasetu.app.data.model.UserProgress
import com.bhashasetu.app.data.relation.WordWithProgress
import kotlinx.coroutines.flow.Flow
import java.util.Date

class UserProgressRepository(private val userProgressDao: UserProgressDao) {

    fun getAllUserProgress(): Flow<List<UserProgress>> {
        return userProgressDao.getAllUserProgress()
    }
    
    suspend fun insert(userProgress: UserProgress) {
        userProgressDao.insert(userProgress)
    }
    
    suspend fun update(userProgress: UserProgress) {
        userProgressDao.update(userProgress)
    }
    
    suspend fun delete(userProgress: UserProgress) {
        userProgressDao.delete(userProgress)
    }
    
    fun getUserProgressByWordId(wordId: Long): Flow<UserProgress?> {
        return userProgressDao.getUserProgressByWordId(wordId)
    }
    
    fun getWordWithProgress(wordId: Long): Flow<WordWithProgress> {
        return userProgressDao.getWordWithProgress(wordId)
    }
    
    fun getAllWordsWithProgress(): Flow<List<WordWithProgress>> {
        return userProgressDao.getAllWordsWithProgress()
    }
    
    suspend fun incrementCorrectAnswers(wordId: Long) {
        userProgressDao.incrementCorrectAnswers(wordId)
    }
    
    suspend fun incrementIncorrectAnswers(wordId: Long) {
        userProgressDao.incrementIncorrectAnswers(wordId)
    }
    
    suspend fun updateProficiencyLevel(wordId: Long, proficiencyLevel: Int) {
        userProgressDao.updateProficiencyLevel(wordId, proficiencyLevel)
    }
    
    suspend fun updateLastReviewed(wordId: Long, lastReviewed: Date) {
        userProgressDao.updateLastReviewed(wordId, lastReviewed)
    }
    
    suspend fun updateNextReviewDate(wordId: Long, nextReviewDate: Date) {
        userProgressDao.updateNextReviewDate(wordId, nextReviewDate)
    }
    
    suspend fun incrementExposureCount(wordId: Long) {
        userProgressDao.incrementExposureCount(wordId)
    }
    
    suspend fun updateMemorizationStatus(wordId: Long, memorizationStatus: Int) {
        userProgressDao.updateMemorizationStatus(wordId, memorizationStatus)
    }
    
    suspend fun updateQuizScore(wordId: Long, score: Int) {
        userProgressDao.updateQuizScore(wordId, score)
    }
    
    fun getWordsForReview(currentDate: Date): Flow<List<UserProgress>> {
        return userProgressDao.getWordsForReview(currentDate)
    }
    
    fun getLearnedWordsCount(minProficiency: Int): Flow<Int> {
        return userProgressDao.getLearnedWordsCount(minProficiency)
    }
    
    fun getMostDifficultWords(limit: Int): Flow<List<UserProgress>> {
        return userProgressDao.getMostDifficultWords(limit)
    }
    
    fun getAverageProficiency(): Flow<Float> {
        return userProgressDao.getAverageProficiency()
    }
}