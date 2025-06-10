package com.bhashasetu.app.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.bhashasetu.app.model.Quiz;

import java.util.List;

@Dao
public interface QuizDao {
    
    @Insert
    long insert(Quiz quiz);
    
    @Update
    void update(Quiz quiz);
    
    @Delete
    void delete(Quiz quiz);
    
    @Query("SELECT * FROM quizzes WHERE lessonId = :lessonId")
    LiveData<List<Quiz>> getQuizzesByLesson(int lessonId);
    
    @Query("SELECT * FROM quizzes ORDER BY RANDOM() LIMIT :limit")
    LiveData<List<Quiz>> getRandomQuizzes(int limit);
    
    @Query("SELECT * FROM quizzes WHERE difficulty <= :maxDifficulty ORDER BY RANDOM() LIMIT :limit")
    LiveData<List<Quiz>> getQuizzesByDifficulty(int maxDifficulty, int limit);
    
    @Query("SELECT * FROM quizzes WHERE id = :quizId")
    LiveData<Quiz> getQuizById(int quizId);
    
    @Query("SELECT COUNT(*) FROM quizzes WHERE lessonId = :lessonId")
    int getQuizCountForLesson(int lessonId);
    
    @Query("SELECT * FROM quizzes WHERE type = :quizType ORDER BY RANDOM() LIMIT :limit")
    LiveData<List<Quiz>> getQuizzesByType(String quizType, int limit);
    
    @Query("INSERT INTO quizzes (lessonId, question, questionHindi, correctAnswer, wrongAnswer1, wrongAnswer2, wrongAnswer3, explanation, explanationHindi, type, difficulty) " +
           "VALUES (:lessonId, :question, :questionHindi, :correctAnswer, :wrongAnswer1, :wrongAnswer2, :wrongAnswer3, :explanation, :explanationHindi, :type, :difficulty)")
    void insertQuizDetails(int lessonId, String question, String questionHindi, String correctAnswer,
                          String wrongAnswer1, String wrongAnswer2, String wrongAnswer3,
                          String explanation, String explanationHindi, String type, int difficulty);
}