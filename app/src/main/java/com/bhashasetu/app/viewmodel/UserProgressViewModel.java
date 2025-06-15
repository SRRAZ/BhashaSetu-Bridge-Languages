package com.bhashasetu.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bhashasetu.app.model.UserProgress;
import com.bhashasetu.app.repository.UserProgressRepository;

import java.util.List;

public class UserProgressViewModel extends AndroidViewModel {
    
    private UserProgressRepository repository;
    
    public UserProgressViewModel(@NonNull Application application) {
        super(application);
        repository = new UserProgressRepository(application);
    }
    
    // Insert a new progress entry
    public void insert(UserProgress progress) {
        repository.insert(progress);
    }
    
    // Update an existing progress entry
    public void update(UserProgress progress) {
        repository.update(progress);
    }
    
    // Get all progress entries for a user
    public LiveData<List<UserProgress>> getAllProgressForUser(int userId) {
        return repository.getAllProgressForUser(userId);
    }
    
    // Get progress for a specific word
    public LiveData<UserProgress> getWordProgress(int userId, int wordId) {
        return repository.getWordProgress(userId, wordId);
    }
    
    // Get progress for a specific lesson
    public LiveData<UserProgress> getLessonProgress(int userId, int lessonId) {
        return repository.getLessonProgress(userId, lessonId);
    }
    
    // Get overall progress percentage
    public LiveData<Float> getOverallProgress(int userId) {
        return repository.getOverallProgress(userId);
    }
    
    // Get words due for review
    public LiveData<List<UserProgress>> getDueWordReviews(int userId, int limit) {
        long currentTime = System.currentTimeMillis();
        return repository.getDueWordReviews(userId, currentTime, limit);
    }
    
    // Get least mastered items
    public LiveData<List<UserProgress>> getLeastMasteredItems(int userId, int limit) {
        return repository.getLeastMasteredItems(userId, limit);
    }
    
    // Update completion level for an item
    public void updateCompletionLevel(int userId, String itemType, int itemId, int level) {
        repository.updateCompletionLevel(userId, itemType, itemId, level);
    }
    
    // Record attempt result and update progress
    public void recordAttemptResult(UserProgress progress, boolean wasCorrect) {
        repository.recordAttemptResult(progress, wasCorrect);
    }
}