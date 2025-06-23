package com.bhashasetu.app.pronunciation;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import com.bhashasetu.app.model.User;

/**
 * Class to hold comprehensive pronunciation progress statistics for a user.
 * Used for displaying overall progress in the app.
 */

@Entity(tableName = "pronunciation_progress_stats",
        indices = {
                @Index(value = "userId"),
                @Index(value = "wordId"),
                @Index(value = {"userId", "wordId"})
        },
        foreignKeys = @ForeignKey(
                entity = User.class,
                parentColumns = "id",
                childColumns = "userId",
                onDelete = ForeignKey.CASCADE
        ))
public class PronunciationProgressStats {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private long userId;
    private long wordId;
    private int attempts;
    private int successfulAttempts;
    private double averageAccuracy;
    private double bestAccuracy;
    private long totalPracticeTime; // in milliseconds
    private long lastPracticedAt;
    private boolean isMastered;
    private int streakCount;
    private String difficultyLevel;

    // Default constructor (Room will use this)
    public PronunciationProgressStats() {
    }

    // Mark other constructors with @Ignore to resolve Room warning
    @Ignore
    public PronunciationProgressStats(long userId, long wordId) {
        this.userId = userId;
        this.wordId = wordId;
        this.attempts = 0;
        this.successfulAttempts = 0;
        this.averageAccuracy = 0.0;
        this.bestAccuracy = 0.0;
        this.totalPracticeTime = 0;
        this.lastPracticedAt = System.currentTimeMillis();
        this.isMastered = false;
        this.streakCount = 0;
        this.difficultyLevel = "Beginner";
    }

    @Ignore
    public PronunciationProgressStats(long userId, long wordId, int attempts,
                                      int successfulAttempts, double averageAccuracy) {
        this.userId = userId;
        this.wordId = wordId;
        this.attempts = attempts;
        this.successfulAttempts = successfulAttempts;
        this.averageAccuracy = averageAccuracy;
        this.bestAccuracy = averageAccuracy;
        this.lastPracticedAt = System.currentTimeMillis();
        this.isMastered = averageAccuracy >= 85.0 && attempts >= 5;
    }

    // Getters and setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }

    public long getWordId() { return wordId; }
    public void setWordId(long wordId) { this.wordId = wordId; }

    public int getAttempts() { return attempts; }
    public void setAttempts(int attempts) { this.attempts = attempts; }

    public int getSuccessfulAttempts() { return successfulAttempts; }
    public void setSuccessfulAttempts(int successfulAttempts) { this.successfulAttempts = successfulAttempts; }

    public double getAverageAccuracy() { return averageAccuracy; }
    public void setAverageAccuracy(double averageAccuracy) { this.averageAccuracy = averageAccuracy; }

    public double getBestAccuracy() { return bestAccuracy; }
    public void setBestAccuracy(double bestAccuracy) { this.bestAccuracy = bestAccuracy; }

    public long getTotalPracticeTime() { return totalPracticeTime; }
    public void setTotalPracticeTime(long totalPracticeTime) { this.totalPracticeTime = totalPracticeTime; }

    public long getLastPracticedAt() { return lastPracticedAt; }
    public void setLastPracticedAt(long lastPracticedAt) { this.lastPracticedAt = lastPracticedAt; }

    public boolean isMastered() { return isMastered; }
    public void setMastered(boolean mastered) { isMastered = mastered; }

    public int getStreakCount() { return streakCount; }
    public void setStreakCount(int streakCount) { this.streakCount = streakCount; }

    public String getDifficultyLevel() { return difficultyLevel; }
    public void setDifficultyLevel(String difficultyLevel) { this.difficultyLevel = difficultyLevel; }
}