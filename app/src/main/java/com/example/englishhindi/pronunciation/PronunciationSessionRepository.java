package com.bhashasetu.app.pronunciation;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.bhashasetu.app.database.AppDatabase;
import com.bhashasetu.app.model.PronunciationSession;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Repository for accessing and modifying pronunciation session data.
 */
public class PronunciationSessionRepository {

    private PronunciationSessionDao sessionDao;
    private PronunciationScoreDao scoreDao;
    private ExecutorService executorService;

    public PronunciationSessionRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        sessionDao = db.pronunciationSessionDao();
        scoreDao = db.pronunciationScoreDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    /**
     * Get all pronunciation sessions for a specific user
     * @param userId The user ID
     * @return LiveData list of sessions
     */
    public LiveData<List<PronunciationSession>> getSessionsForUser(long userId) {
        return sessionDao.getSessionsForUser(userId);
    }

    /**
     * Get a specific session by ID
     * @param sessionId The session ID
     * @return LiveData with the session
     */
    public LiveData<PronunciationSession> getSessionById(long sessionId) {
        return sessionDao.getSessionById(sessionId);
    }

    /**
     * Insert a new session with its scores
     * @param session The session to insert
     */
    public void insertSession(PronunciationSession session) {
        executorService.execute(() -> {
            // First insert the session to get its ID
            long sessionId = sessionDao.insert(session);
            
            // Now insert all scores with the session ID
            if (session.getScores() != null) {
                for (PronunciationScore score : session.getScores()) {
                    if (score != null) {
                        score.setSessionId(sessionId);
                        scoreDao.insert(score);
                    }
                }
            }
        });
    }

    /**
     * Update an existing session
     * @param session The session to update
     */
    public void updateSession(PronunciationSession session) {
        executorService.execute(() -> {
            sessionDao.update(session);
        });
    }

    /**
     * Delete a session and all its scores
     * @param session The session to delete
     */
    public void deleteSession(PronunciationSession session) {
        executorService.execute(() -> {
            scoreDao.deleteScoresForSession(session.getId());
            sessionDao.delete(session);
        });
    }

    /**
     * Get all pronunciation scores for a specific word
     * @param wordId The word ID
     * @return LiveData list of scores
     */
    public LiveData<List<PronunciationScore>> getScoresForWord(long wordId) {
        return scoreDao.getScoresForWord(wordId);
    }

    /**
     * Get the most recent pronunciation session
     * @return LiveData with the most recent session
     */
    public LiveData<PronunciationSession> getMostRecentSession() {
        return sessionDao.getMostRecentSession();
    }

    /**
     * Get statistics about pronunciation progress
     * @param userId The user ID
     * @return LiveData with the statistics
     */
    public LiveData<PronunciationProgressStats> getPronunciationProgressStats(long userId) {
        return sessionDao.getPronunciationProgressStats(userId);
    }
}