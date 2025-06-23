package com.bhashasetu.app.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.bhashasetu.app.model.Lesson
import com.bhashasetu.app.data.relation.LessonWithWords
import com.bhashasetu.app.data.model.LessonWord

@Dao
interface LessonDao {

    // ✅ Basic Lesson operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLesson(lesson: Lesson): Long

    @Update
    suspend fun updateLesson(lesson: Lesson)

    @Delete
    suspend fun deleteLesson(lesson: Lesson)

    @Query("SELECT * FROM lessons WHERE id = :lessonId")
    fun getLessonById(lessonId: Long): LiveData<Lesson>

    @Query("SELECT * FROM lessons WHERE isActive = 1 ORDER BY `order` ASC")
    fun getAllActiveLessons(): LiveData<List<Lesson>>

    @Query("SELECT * FROM lessons WHERE categoryId = :categoryId AND isActive = 1 ORDER BY `order` ASC")
    fun getLessonsByCategory(categoryId: Long): LiveData<List<Lesson>>

    // ✅ FIXED: LessonWithWords queries using correct entity references

    /**
     * Get lesson with words - FIXED to use correct Word entity
     */
    @Transaction
    @Query("SELECT * FROM lessons WHERE id = :lessonId")
    fun getLessonWithWords(lessonId: Long): LiveData<LessonWithWords>

    /**
     * Get all lessons with their words - FIXED column references
     */
    @Transaction
    @Query("SELECT * FROM lessons WHERE isActive = 1 ORDER BY `order` ASC")
    fun getAllLessonsWithWords(): LiveData<List<LessonWithWords>>

    /**
     * Get lessons by category with words - FIXED entity references
     */
    @Transaction
    @Query("SELECT * FROM lessons WHERE categoryId = :categoryId AND isActive = 1 ORDER BY `order` ASC")
    fun getLessonsByCategoryWithWords(categoryId: Long): LiveData<List<LessonWithWords>>

    /**
     * ✅ FIXED: Complex query joining lessons and words with correct column names
     */
    @Transaction
    @Query("""
        SELECT l.* FROM lessons l
        INNER JOIN lesson_words lw ON l.id = lw.lessonId
        INNER JOIN vocabulary_words w ON lw.wordId = w.id
        WHERE w.difficulty = :difficulty 
        AND l.isActive = 1 
        AND w.isActive = 1
        GROUP BY l.id
        ORDER BY l.`order` ASC
    """)
    fun getLessonsWithWordsByDifficulty(difficulty: Int): LiveData<List<LessonWithWords>>

    /**
     * ✅ FIXED: Get lessons containing specific English word
     */
    @Transaction
    @Query("""
        SELECT l.* FROM lessons l
        INNER JOIN lesson_words lw ON l.id = lw.lessonId
        INNER JOIN vocabulary_words w ON lw.wordId = w.id
        WHERE w.englishWord LIKE '%' || :englishWord || '%'
        AND l.isActive = 1 
        AND w.isActive = 1
        GROUP BY l.id
        ORDER BY l.`order` ASC
    """)
    fun getLessonsContainingEnglishWord(englishWord: String): LiveData<List<LessonWithWords>>

    /**
     * ✅ FIXED: Get lessons containing specific Hindi word
     */
    @Transaction
    @Query("""
        SELECT l.* FROM lessons l
        INNER JOIN lesson_words lw ON l.id = lw.lessonId
        INNER JOIN vocabulary_words w ON lw.wordId = w.id
        WHERE w.hindiWord LIKE '%' || :hindiWord || '%'
        AND l.isActive = 1 
        AND w.isActive = 1
        GROUP BY l.id
        ORDER BY l.`order` ASC
    """)
    fun getLessonsContainingHindiWord(hindiWord: String): LiveData<List<LessonWithWords>>

    /**
     * ✅ NEW: Get lessons with quiz words only
     */
    @Transaction
    @Query("""
        SELECT l.* FROM lessons l
        INNER JOIN lesson_words lw ON l.id = lw.lessonId
        WHERE lw.includeInQuiz = 1
        AND l.isActive = 1
        GROUP BY l.id
        HAVING COUNT(lw.wordId) > 0
        ORDER BY l.`order` ASC
    """)
    fun getLessonsWithQuizWords(): LiveData<List<LessonWithWords>>

    // ✅ LessonWord junction table operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLessonWord(lessonWord: LessonWord): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLessonWords(lessonWords: List<LessonWord>)

    @Update
    suspend fun updateLessonWord(lessonWord: LessonWord)

    @Delete
    suspend fun deleteLessonWord(lessonWord: LessonWord)

    @Query("DELETE FROM lesson_words WHERE lessonId = :lessonId AND wordId = :wordId")
    suspend fun removeLessonWord(lessonId: Long, wordId: Long)

    @Query("SELECT * FROM lesson_words WHERE lessonId = :lessonId ORDER BY orderInLesson ASC")
    fun getLessonWords(lessonId: Long): LiveData<List<LessonWord>>

    @Query("UPDATE lesson_words SET orderInLesson = :order WHERE lessonId = :lessonId AND wordId = :wordId")
    suspend fun updateWordOrder(lessonId: Long, wordId: Long, order: Int)

    @Query("UPDATE lesson_words SET isKeyword = :isKeyword WHERE lessonId = :lessonId AND wordId = :wordId")
    suspend fun updateKeywordStatus(lessonId: Long, wordId: Long, isKeyword: Boolean)

    @Query("UPDATE lesson_words SET includeInQuiz = :includeInQuiz WHERE lessonId = :lessonId AND wordId = :wordId")
    suspend fun updateQuizInclusion(lessonId: Long, wordId: Long, includeInQuiz: Boolean)
}