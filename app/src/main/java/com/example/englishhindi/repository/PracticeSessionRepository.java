package com.example.englishhindi.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.englishhindi.database.PracticeSessionDao;
import com.example.englishhindi.database.WordDatabase;
import com.example.englishhindi.model.PracticeSession;

import java.util.List;

public class PracticeSessionRepository {
    
    private PracticeSessionDao practiceSessionDao;
    
    public PracticeSessionRepository(Application application) {
        WordDatabase database = WordDatabase.getInstance(application);
        practiceSessionDao = database.practiceSessionDao();
    }
    
    // Insert a new practice session
    public void insert(PracticeSession session) {
        new InsertSessionAsyncTask(practiceSessionDao).execute(session);
    }
    
    // Update an existing practice session
    public void update(PracticeSession session) {
        new UpdateSessionAsyncTask(practiceSessionDao).execute(session);
    }
    
    // Delete a practice session
    public void delete(PracticeSession session) {
        new DeleteSessionAsyncTask(practiceSessionDao).execute(session);
    }
    
    // Get all sessions for a user
    public LiveData<List<PracticeSession>> getAllSessionsForUser(int userId) {
        return practiceSessionDao.getAllSessionsForUser(userId);
    }
    
    // Get recent sessions for a user
    public LiveData<List<PracticeSession>> getRecentSessionsForUser(int userId, int limit) {
        return practiceSessionDao.getRecentSessionsForUser(userId, limit);
    }
    
    // Get a specific session by ID
    public LiveData<PracticeSession> getSessionById(int sessionId) {
        return practiceSessionDao.getSessionById(sessionId);
    }
    
    // Get sessions by type
    public LiveData<List<PracticeSession>> getSessionsByType(int userId, String sessionType) {
        return practiceSessionDao.getSessionsByType(userId, sessionType);
    }
    
    // Get sessions for a deck
    public LiveData<List<PracticeSession>> getSessionsForDeck(int userId, int deckId) {
        return practiceSessionDao.getSessionsForDeck(userId, deckId);
    }
    
    // Get sessions for a lesson
    public LiveData<List<PracticeSession>> getSessionsForLesson(int userId, int lessonId) {
        return practiceSessionDao.getSessionsForLesson(userId, lessonId);
    }
    
    // Get completed session count
    public int getCompletedSessionCount(int userId, String sessionType) {
        return practiceSessionDao.getCompletedSessionCount(userId, sessionType);
    }
    
    // Get average score
    public float getAverageScore(int userId) {
        return practiceSessionDao.getAverageScore(userId);
    }
    
    // Get total items practiced
    public int getTotalItemsPracticed(int userId) {
        return practiceSessionDao.getTotalItemsPracticed(userId);
    }
    
    // Get total correct answers
    public int getTotalCorrectAnswers(int userId) {
        return practiceSessionDao.getTotalCorrectAnswers(userId);
    }
    
    // Get session count today
    public int getSessionCountToday(int userId) {
        return practiceSessionDao.getSessionCountToday(userId);
    }
    
    // Complete a session
    public void completeSession(int sessionId, long endTime, int score, int correctAnswers) {
        new CompleteSessionAsyncTask(practiceSessionDao, sessionId, endTime, score, correctAnswers).execute();
    }
    
    // AsyncTask classes for database operations
    private static class InsertSessionAsyncTask extends AsyncTask<PracticeSession, Void, Void> {
        private PracticeSessionDao practiceSessionDao;
        
        private InsertSessionAsyncTask(PracticeSessionDao practiceSessionDao) {
            this.practiceSessionDao = practiceSessionDao;
        }
        
        @Override
        protected Void doInBackground(PracticeSession... sessions) {
            practiceSessionDao.insert(sessions[0]);
            return null;
        }
    }
    
    private static class UpdateSessionAsyncTask extends AsyncTask<PracticeSession, Void, Void> {
        private PracticeSessionDao practiceSessionDao;
        
        private UpdateSessionAsyncTask(PracticeSessionDao practiceSessionDao) {
            this.practiceSessionDao = practiceSessionDao;
        }
        
        @Override
        protected Void doInBackground(PracticeSession... sessions) {
            practiceSessionDao.update(sessions[0]);
            return null;
        }
    }
    
    private static class DeleteSessionAsyncTask extends AsyncTask<PracticeSession, Void, Void> {
        private PracticeSessionDao practiceSessionDao;
        
        private DeleteSessionAsyncTask(PracticeSessionDao practiceSessionDao) {
            this.practiceSessionDao = practiceSessionDao;
        }
        
        @Override
        protected Void doInBackground(PracticeSession... sessions) {
            practiceSessionDao.delete(sessions[0]);
            return null;
        }
    }
    
    private static class CompleteSessionAsyncTask extends AsyncTask<Void, Void, Void> {
        private PracticeSessionDao practiceSessionDao;
        private int sessionId;
        private long endTime;
        private int score;
        private int correctAnswers;
        
        private CompleteSessionAsyncTask(PracticeSessionDao practiceSessionDao, int sessionId, 
                                        long endTime, int score, int correctAnswers) {
            this.practiceSessionDao = practiceSessionDao;
            this.sessionId = sessionId;
            this.endTime = endTime;
            this.score = score;
            this.correctAnswers = correctAnswers;
        }
        
        @Override
        protected Void doInBackground(Void... voids) {
            practiceSessionDao.completeSession(sessionId, endTime, score, correctAnswers);
            return null;
        }
    }
}