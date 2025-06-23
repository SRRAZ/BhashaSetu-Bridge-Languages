package com.bhashasetu.app.data.repository

import androidx.lifecycle.LiveData
import com.bhashasetu.app.data.dao.LessonDao
import com.bhashasetu.app.data.dao.WordDao
import com.bhashasetu.app.data.relation.LessonWithWords
import com.bhashasetu.app.model.Lesson
import com.bhashasetu.app.model.Word
import com.bhashasetu.app.data.model.LessonWord
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow

@Singleton
class LessonRepository @Inject constructor(
    private val lessonDao: LessonDao,
    private val wordDao: WordDao
) {
    fun getLessonWithWords(lessonId: Long): LiveData<LessonWithWords> {
        return lessonDao.getLessonWithWords(lessonId)
    }

    fun getAllLessonsWithWords(): LiveData<List<LessonWithWords>> {
        return lessonDao.getAllLessonsWithWords()
    }

    suspend fun addWordToLesson(lessonId: Long, wordId: Long, order: Int = 0, isKeyword: Boolean = false, includeInQuiz: Boolean = true) {
        val lessonWord = LessonWord(
            lessonId = lessonId,
            wordId = wordId,
            orderInLesson = order,
            isKeyword = isKeyword,
            includeInQuiz = includeInQuiz
        )
        lessonDao.insertLessonWord(lessonWord)
    }

    suspend fun removeWordFromLesson(lessonId: Long, wordId: Long) {
        lessonDao.deleteLessonWord(lessonId, wordId)
    }

    suspend fun updateWordOrderInLesson(lessonId: Long, wordId: Long, newOrder: Int) {
        lessonDao.updateWordOrder(lessonId, wordId, newOrder)
    }

    val allLessons: Flow<List<Lesson>> = lessonDao.getAllLessons()
    
    suspend fun insert(lesson: Lesson): Long {
        return lessonDao.insert(lesson)
    }
    
    suspend fun update(lesson: Lesson) {
        lessonDao.update(lesson)
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