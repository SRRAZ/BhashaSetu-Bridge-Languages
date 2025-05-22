package com.example.englishhindi.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.englishhindi.model.UserProgress;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Repository class for accessing and modifying user progress data.
 * Provides a clean API for the UserProgressManager to interact with the database.
 */
public class UserProgressRepository {

    private UserProgressDao userProgressDao;
    private ExecutorService executor;

    public UserProgressRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        userProgressDao = db.userProgressDao();
        executor = Executors.newSingleThreadExecutor();
    }

    /**
     * Get user progress for a specific user ID as LiveData
     * @param userId User ID
     * @return LiveData object with the user's progress
     */
    public LiveData<UserProgress> getUserProgressLiveData(long userId) {
        return userProgressDao.getUserProgressLiveData(userId);
    }

    /**
     * Get user progress for a specific user ID
     * @param userId User ID
     * @return UserProgress object or null if not found
     */
    public UserProgress getUserProgress(long userId) {
        return userProgressDao.getUserProgress(userId);
    }

    /**
     * Insert a new user progress record
     * @param userProgress UserProgress object to insert
     */
    public void insertUserProgress(UserProgress userProgress) {
        executor.execute(() -> {
            userProgressDao.insert(userProgress);
        });
    }

    /**
     * Update an existing user progress record
     * @param userProgress UserProgress object to update
     */
    public void updateUserProgress(UserProgress userProgress) {
        executor.execute(() -> {
            userProgressDao.update(userProgress);
        });
    }

    /**
     * Delete a user progress record
     * @param userProgress UserProgress object to delete
     */
    public void deleteUserProgress(UserProgress userProgress) {
        executor.execute(() -> {
            userProgressDao.delete(userProgress);
        });
    }
}