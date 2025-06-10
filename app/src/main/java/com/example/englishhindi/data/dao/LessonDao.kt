package com.bhashasetu.app.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bhashasetu.app.data.model.Lesson
import com.bhashasetu.app.data.model.LessonWord
import com.bhashasetu.app.data.relation.LessonWithWords
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the Lesson entity.
 */
@Dao
interface LessonDao {
    // Basic CRUD operations
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(lesson: Lesson): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(lessons: List<Lesson>): List<Long>
    
    @Update
    suspend fun update(lesson: Lesson)
    
    @Delete
    suspend fun delete(lesson: Lesson)
    
    // LessonWord junction operations
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWordToLesson(lessonWord: LessonWord)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWordsToLesson(lessonWords: List<LessonWord>)
    
    @Delete
    suspend fun removeWordFromLesson(lessonWord: LessonWord)
    
    @Query("DELETE FROM lesson_words WHERE lessonId = :lessonId AND wordId = :wordId")
    suspend fun removeWordFromLessonById(lessonId: Long, wordId: Long)
    
    @Query("DELETE FROM lesson_words WHERE lessonId = :lessonId")
    suspend fun removeAllWordsFromLesson(lessonId: Long)
    
    @Query("UPDATE lesson_words SET orderInLesson = :newOrder WHERE lessonId = :lessonId AND wordId = :wordId")
    suspend fun updateWordOrder(lessonId: Long, wordId: Long, newOrder: Int)
    
    // Queries
    
    @Query("SELECT * FROM lessons WHERE id = :lessonId")
    suspend fun getLessonById(lessonId: Long): Lesson?
    
    @Query("SELECT * FROM lessons WHERE id = :lessonId")
    fun getLessonByIdLiveData(lessonId: Long): LiveData<Lesson?>
    
    @Query("SELECT * FROM lessons WHERE id = :lessonId")
    fun getLessonByIdFlow(lessonId: Long): Flow<Lesson?>
    
    @Query("SELECT * FROM lessons ORDER BY difficulty ASC, orderInCategory ASC, titleEnglish ASC")
    fun getAllLessons(): Flow<List<Lesson>>
    
    @Query("SELECT * FROM lessons ORDER BY difficulty ASC, orderInCategory ASC, titleEnglish ASC")
    fun getAllLessonsLiveData(): LiveData<List<Lesson>>
    
    @Query("SELECT * FROM lessons WHERE categoryId = :categoryId ORDER BY orderInCategory ASC, titleEnglish ASC")
    fun getLessonsByCategory(categoryId: Long): Flow<List<Lesson>>
    
    @Query("SELECT * FROM lessons WHERE difficulty = :difficulty ORDER BY orderInCategory ASC, titleEnglish ASC")
    fun getLessonsByDifficulty(difficulty: Int): Flow<List<Lesson>>
    
    @Query("SELECT * FROM lessons WHERE isCompleted = :isCompleted ORDER BY difficulty ASC, orderInCategory ASC")
    fun getLessonsByCompletionStatus(isCompleted: Boolean): Flow<List<Lesson>>
    
    @Query("SELECT * FROM lessons WHERE titleEnglish LIKE '%' || :query || '%' OR titleHindi LIKE '%' || :query || '%'")
    fun searchLessons(query: String): Flow<List<Lesson>>
    
    // Advanced queries with relationships
    
    @Transaction
    @Query("SELECT * FROM lessons WHERE id = :lessonId")
    suspend fun getLessonWithWords(lessonId: Long): LessonWithWords?
    
    @Transaction
    @Query("SELECT * FROM lessons WHERE id = :lessonId")
    fun getLessonWithWordsFlow(lessonId: Long): Flow<LessonWithWords?>
    
    @Transaction
    @Query("SELECT * FROM lessons")
    fun getAllLessonsWithWords(): Flow<List<LessonWithWords>>
    
    @Transaction
    @Query("SELECT * FROM lessons WHERE categoryId = :categoryId ORDER BY orderInCategory ASC")
    fun getLessonsWithWordsByCategory(categoryId: Long): Flow<List<LessonWithWords>>
    
    @Query("""
        SELECT l.* FROM lessons l
        INNER JOIN lesson_words lw ON l.id = lw.lessonId
        WHERE lw.wordId = :wordId
        ORDER BY l.difficulty ASC, l.orderInCategory ASC
    """)
    fun getLessonsContainingWord(wordId: Long): Flow<List<Lesson>>
    
    // Progress tracking
    
    @Query("UPDATE lessons SET isCompleted = :isCompleted, completedAt = :completedAt WHERE id = :lessonId")
    suspend fun updateLessonCompletion(lessonId: Long, isCompleted: Boolean, completedAt: Long?)
    
    @Query("UPDATE lessons SET lastAccessedAt = :accessedAt WHERE id = :lessonId")
    suspend fun updateLessonLastAccessed(lessonId: Long, accessedAt: Long)
    
    // Statistics queries
    
    @Query("SELECT COUNT(*) FROM lessons")
    fun getLessonCount(): Flow<Int>
    
    @Query("SELECT COUNT(*) FROM lessons WHERE isCompleted = 1")
    fun getCompletedLessonCount(): Flow<Int>
    
    @Query("SELECT COUNT(*) FROM lessons WHERE categoryId = :categoryId")
    fun getLessonCountByCategory(categoryId: Long): Flow<Int>
    
    @Query("SELECT COUNT(*) FROM lessons WHERE categoryId = :categoryId AND isCompleted = 1")
    fun getCompletedLessonCountByCategory(categoryId: Long): Flow<Int>
    
    // Utility queries
    
    @Query("SELECT EXISTS(SELECT 1 FROM lessons WHERE titleEnglish = :titleEnglish LIMIT 1)")
    suspend fun lessonExistsByTitle(titleEnglish: String): Boolean
    
    @Query("SELECT EXISTS(SELECT 1 FROM lesson_words WHERE lessonId = :lessonId AND wordId = :wordId LIMIT 1)")
    suspend fun isWordInLesson(lessonId: Long, wordId: Long): Boolean
    
    @Query("SELECT MAX(orderInCategory) + 1 FROM lessons WHERE categoryId = :categoryId")
    suspend fun getNextOrderInCategory(categoryId: Long): Int
}