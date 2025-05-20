package com.example.englishhindi.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.englishhindi.model.PracticeSession;
import com.example.englishhindi.repository.PracticeSessionRepository;

import java.util.List;

public class PracticeSessionViewModel extends AndroidViewModel {
    
    private PracticeSessionRepository repository;
    
    public PracticeSessionViewModel(@NonNull Application application) {
        super(application);
        repository = new PracticeSessionRepository(application);
    }
    
    // Insert a new practice session
    public void insert(PracticeSession session) {
        repository.insert(session);
    }
    
    // Update an existing practice session
    public void update(PracticeSession session) {
        repository.update(session);
    }
    
    // Delete a practice session
    public void delete(PracticeSession session) {
        repository.delete(session);
    }
    
    // Get all sessions for a user
    public LiveData<List<PracticeSession>> getAllSessionsForUser(int userId) {
        return repository.getAllSessionsForUser(userId);
    }
    
    // Get recent sessions for a user
    public LiveData<List<PracticeSession>> getRecentSessionsForUser(int userId, int limit) {
        return repository.getRecentSessionsForUser(userId, limit);
    }
    
    // Get a specific session by ID
    public LiveData<PracticeSession> getSessionById(int sessionId) {
        return repository.getSessionById(sessionId);
    }
    
    // Get sessions by type
    public LiveData<List<PracticeSession>> getSessionsByType(int userId, String sessionType) {
        return repository.getSessionsByType(userId, sessionType);
    }
    
    // Get sessions for a deck
    public LiveData<List<PracticeSession>> getSessionsForDeck(int userId, int deckId) {
        return repository.getSessionsForDeck(userId, deckId);
    }
    
    // Get sessions for a lesson
    public LiveData<List<PracticeSession>> getSessionsForLesson(int userId, int lessonId) {
        return repository.getSessionsForLesson(userId, lessonId);
    }
    
    // Get completed session count
    public int getCompletedSessionCount(int userId, String sessionType) {
        return repository.getCompletedSessionCount(userId, sessionType);
    }
    
    // Get average score
    public float getAverageScore(int userId) {
        return repository.getAverageScore(userId);
    }
    
    // Get total items practiced
    public int getTotalItemsPracticed(int userId) {
        return repository.getTotalItemsPracticed(userId);
    }
    
    // Get total correct answers
    public int getTotalCorrectAnswers(int userId) {
        return repository.getTotalCorrectAnswers(userId);
    }
    
    // Get session count today
    public int getSessionCountToday(int userId) {
        return repository.getSessionCountToday(userId);
    }
    
    // Complete a session
    public void completeSession(PracticeSession session) {
        session.setCompleted(true);
        repository.completeSession(session.getId(), session.getEndTime(), 
                                  session.getScore(), session.getCorrectAnswers());
    }
    
    // Create a new flashcard practice session
    public PracticeSession createFlashcardSession(int userId, int deckId) {
        PracticeSession session = new PracticeSession(userId, "flashcard");
        session.setDeckId(deckId);
        return session;
    }
    
    // Create a new quiz practice session
    public PracticeSession createQuizSession(int userId, int lessonId) {
        PracticeSession session = new PracticeSession(userId, "quiz");
        session.setLessonId(lessonId);
        return session;
    }
    
    // Create a new pronunciation practice session
    public PracticeSession createPronunciationSession(int userId) {
        return new PracticeSession(userId, "pronunciation");
    }
    
    // Create a new spelling practice session
    public PracticeSession createSpellingSession(int userId) {
        return new PracticeSession(userId, "spelling");
    }
}