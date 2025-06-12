package com.bhashasetu.app.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.bhashasetu.app.database.UserProgressDao;
import com.bhashasetu.app.database.WordDatabase;
import com.bhashasetu.app.model.UserProgress;

import java.util.List;

public class UserProgressRepository {
    
    private UserProgressDao userProgressDao;
    
    public UserProgressRepository(Application application) {
        WordDatabase database = WordDatabase.getInstance(application);
        userProgressDao = database.userProgressDao();
    }
    
    // Insert a new progress entry
    public void insert(UserProgress progress) {
        new InsertUserProgressAsyncTask(userProgressDao).execute(progress);
    }
    
    // Update an existing progress entry
    public void update(UserProgress progress) {
        new UpdateUserProgressAsyncTask(userProgressDao).execute(progress);
    }
    
    // Get all progress entries for a user
    public LiveData<List<UserProgress>> getAllProgressForUser(int userId) {
        return userProgressDao.getAllProgressForUser(userId);
    }
    
    // Get progress for a specific word
    public LiveData<UserProgress> getWordProgress(int userId, int wordId) {
        return userProgressDao.getWordProgress(userId, wordId);
    }
    
    // Get progress for a specific lesson
    public LiveData<UserProgress> getLessonProgress(int userId, int lessonId) {
        return userProgressDao.getLessonProgress(userId, lessonId);
    }
    
    // Get overall progress percentage
    public LiveData<Float> getOverallProgress(int userId) {
        return userProgressDao.getOverallProgress(userId);
    }
    
    // Get words due for review
    public LiveData<List<UserProgress>> getDueWordReviews(int userId, long currentTime, int limit) {
        return userProgressDao.getDueWordReviews(userId, currentTime, limit);
    }
    
    // Get least mastered items
    public LiveData<List<UserProgress>> getLeastMasteredItems(int userId, int limit) {
        return userProgressDao.getLeastMasteredItems(userId, limit);
    }
    
    // Update completion level for an item
    public void updateCompletionLevel(int userId, String itemType, int itemId, int level) {
        new UpdateCompletionLevelAsyncTask(userProgressDao, userId, itemType, itemId, level).execute();
    }
    
    // Update next review time
    public void updateNextReview(int progressId, long nextReview) {
        new UpdateNextReviewAsyncTask(userProgressDao, progressId, nextReview).execute();
    }
    
    // Update spaced repetition level
    public void updateSpacedRepetitionLevel(int progressId, int newLevel) {
        new UpdateSRLevelAsyncTask(userProgressDao, progressId, newLevel).execute();
    }
    
    // Record attempt result and update progress
    public void recordAttemptResult(UserProgress progress, boolean wasCorrect) {
        progress.updateSpacedRepetition(wasCorrect);
        update(progress);
    }
    
    // AsyncTask classes for database operations
    private static class InsertUserProgressAsyncTask extends AsyncTask<UserProgress, Void, Void> {
        private UserProgressDao userProgressDao;
        
        private InsertUserProgressAsyncTask(UserProgressDao userProgressDao) {
            this.userProgressDao = userProgressDao;
        }
        
        @Override
        protected Void doInBackground(UserProgress... progress) {
            userProgressDao.insert(progress[0]);
            return null;
        }
    }
    
    private static class UpdateUserProgressAsyncTask extends AsyncTask<UserProgress, Void, Void> {
        private UserProgressDao userProgressDao;
        
        private UpdateUserProgressAsyncTask(UserProgressDao userProgressDao) {
            this.userProgressDao = userProgressDao;
        }
        
        @Override
        protected Void doInBackground(UserProgress... progress) {
            userProgressDao.update(progress[0]);
            return null;
        }
    }
    
    private static class UpdateCompletionLevelAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserProgressDao userProgressDao;
        private int userId;
        private String itemType;
        private int itemId;
        private int level;
        
        private UpdateCompletionLevelAsyncTask(UserProgressDao userProgressDao, int userId, 
                                               String itemType, int itemId, int level) {
            this.userProgressDao = userProgressDao;
            this.userId = userId;
            this.itemType = itemType;
            this.itemId = itemId;
            this.level = level;
        }
        
        @Override
        protected Void doInBackground(Void... voids) {
            userProgressDao.updateCompletionLevel(userId, itemType, itemId, level);
            return null;
        }
    }
    
    private static class UpdateNextReviewAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserProgressDao userProgressDao;
        private int progressId;
        private long nextReview;
        
        private UpdateNextReviewAsyncTask(UserProgressDao userProgressDao, int progressId, long nextReview) {
            this.userProgressDao = userProgressDao;
            this.progressId = progressId;
            this.nextReview = nextReview;
        }
        
        @Override
        protected Void doInBackground(Void... voids) {
            userProgressDao.updateNextReview(progressId, nextReview);
            return null;
        }
    }
    
    private static class UpdateSRLevelAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserProgressDao userProgressDao;
        private int progressId;
        private int newLevel;
        
        private UpdateSRLevelAsyncTask(UserProgressDao userProgressDao, int progressId, int newLevel) {
            this.userProgressDao = userProgressDao;
            this.progressId = progressId;
            this.newLevel = newLevel;
        }
        
        @Override
        protected Void doInBackground(Void... voids) {
            userProgressDao.updateSpacedRepetitionLevel(progressId, newLevel);
            return null;
        }
    }
}