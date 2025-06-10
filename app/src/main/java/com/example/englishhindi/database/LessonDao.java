package com.bhashasetu.app.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.bhashasetu.app.model.Lesson;
import com.bhashasetu.app.model.LessonWord;

import java.util.List;

@Dao
public interface LessonDao {
    
    @Insert
    long insert(Lesson lesson);
    
    @Update
    void update(Lesson lesson);
    
    @Delete
    void delete(Lesson lesson);
    
    @Query("SELECT * FROM lessons ORDER BY category, orderInCategory ASC")
    LiveData<List<Lesson>> getAllLessons();
    
    @Query("SELECT * FROM lessons WHERE category = :category ORDER BY orderInCategory ASC")
    LiveData<List<Lesson>> getLessonsByCategory(String category);
    
    @Query("SELECT * FROM lessons WHERE level = :level ORDER BY category, orderInCategory ASC")
    LiveData<List<Lesson>> getLessonsByLevel(String level);
    
    @Query("SELECT * FROM lessons WHERE id = :id")
    LiveData<Lesson> getLessonById(int id);
    
    @Query("SELECT * FROM lessons WHERE hasCompleted = 0 ORDER BY level, orderInCategory ASC LIMIT 1")
    LiveData<Lesson> getNextIncompleteLesson();
    
    @Query("SELECT DISTINCT category FROM lessons ORDER BY category ASC")
    LiveData<List<String>> getAllCategories();
    
    @Query("SELECT * FROM lessons WHERE id IN " +
           "(SELECT lessonId FROM lesson_words WHERE wordId = :wordId)")
    LiveData<List<Lesson>> getLessonsContainingWord(int wordId);
    
    @Insert
    void insertLessonWord(LessonWord lessonWord);
    
    @Query("DELETE FROM lesson_words WHERE lessonId = :lessonId AND wordId = :wordId")
    void removeLessonWord(int lessonId, int wordId);
    
    @Transaction
    @Query("SELECT EXISTS(SELECT 1 FROM lesson_words WHERE lessonId = :lessonId AND wordId = :wordId)")
    boolean lessonContainsWord(int lessonId, int wordId);
}