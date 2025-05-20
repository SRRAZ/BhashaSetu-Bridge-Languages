package com.example.englishhindi.data.repository

import androidx.lifecycle.LiveData
import com.example.englishhindi.data.dao.LessonDao
import com.example.englishhindi.data.model.Lesson
import com.example.englishhindi.data.model.LessonWord
import com.example.englishhindi.data.relation.LessonWithWords
import kotlinx.coroutines.flow.Flow

class LessonRepository(private val lessonDao: LessonDao) {

    val allLessons: Flow<List<Lesson>> = lessonDao.getAllLessons()
    
    suspend fun insert(lesson: Lesson): Long {
        return lessonDao.insert(lesson)
    }
    
    suspend fun update(lesson: Lesson) {
        lessonDao.update(lesson)
    }
    
    suspend fun delete(lesson: Lesson) {
        lessonDao.delete(lesson)
    }
    
    fun getLessonById(id: Long): Flow<Lesson> {
        return lessonDao.getLessonById(id)
    }
    
    fun getLessonsByLevel(level: Int): Flow<List<Lesson>> {
        return lessonDao.getLessonsByLevel(level)
    }
    
    fun getCompletedLessons(): Flow<List<Lesson>> {
        return lessonDao.getCompletedLessons()
    }
    
    fun getPendingLessons(): Flow<List<Lesson>> {
        return lessonDao.getPendingLessons()
    }
    
    fun getLessonWithWords(lessonId: Long): Flow<LessonWithWords> {
        return lessonDao.getLessonWithWords(lessonId)
    }
    
    fun getAllLessonsWithWords(): Flow<List<LessonWithWords>> {
        return lessonDao.getAllLessonsWithWords()
    }
    
    suspend fun addWordToLesson(lessonWord: LessonWord) {
        lessonDao.insertLessonWord(lessonWord)
    }
    
    suspend fun removeWordFromLesson(lessonId: Long, wordId: Long) {
        lessonDao.deleteLessonWord(lessonId, wordId)
    }
    
    suspend fun markLessonAsCompleted(lessonId: Long) {
        lessonDao.markLessonAsCompleted(lessonId)
    }
    
    fun getUnlockedLessons(): Flow<List<Lesson>> {
        return lessonDao.getUnlockedLessons()
    }
    
    fun getLessonCount(): Flow<Int> {
        return lessonDao.getLessonCount()
    }
    
    fun getCompletedLessonCount(): Flow<Int> {
        return lessonDao.getCompletedLessonCount()
    }
}