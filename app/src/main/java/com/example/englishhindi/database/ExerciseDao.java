package com.bhashasetu.app.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.bhashasetu.app.model.exercise.Exercise;
import com.bhashasetu.app.model.exercise.ExerciseType;

import java.util.List;

/**
 * Data Access Object for Exercise entities
 */
@Dao
public interface ExerciseDao {

    @Insert
    long insert(Exercise exercise);
    
    @Update
    void update(Exercise exercise);
    
    @Delete
    void delete(Exercise exercise);
    
    @Query("SELECT * FROM exercises WHERE id = :id")
    LiveData<Exercise> getExerciseById(int id);
    
    @Query("SELECT * FROM exercises")
    LiveData<List<Exercise>> getAllExercises();
    
    @Query("SELECT * FROM exercises WHERE isCompleted = 1 ORDER BY completedAt DESC")
    LiveData<List<Exercise>> getCompletedExercises();
    
    @Query("SELECT * FROM exercises WHERE isCompleted = 0")
    LiveData<List<Exercise>> getIncompleteExercises();
    
    @Query("SELECT * FROM exercises WHERE type = :type")
    LiveData<List<Exercise>> getExercisesByType(ExerciseType type);
    
    @Query("SELECT * FROM exercises WHERE category = :category")
    LiveData<List<Exercise>> getExercisesByCategory(String category);
    
    @Query("SELECT COUNT(*) FROM exercises WHERE isCompleted = 1")
    int getCompletedExerciseCount();
    
    @Query("SELECT COUNT(*) FROM exercises WHERE type = :type AND isCompleted = 1")
    int getCompletedExerciseCountByType(ExerciseType type);
    
    @Query("SELECT AVG(correctAnswers * 100.0 / totalQuestions) FROM exercises WHERE isCompleted = 1")
    float getAverageScorePercentage();
    
    @Query("SELECT * FROM exercises ORDER BY id DESC LIMIT :limit")
    LiveData<List<Exercise>> getRecentExercises(int limit);
}