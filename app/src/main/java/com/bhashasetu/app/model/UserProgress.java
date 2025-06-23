package com.bhashasetu.app.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_learning_progress", // ✅ Changed from "user_progress" to resolve conflict
        indices = {
                @Index(value = "userId"),
                @Index(value = "wordId"),
                @Index(value = "lessonId"),
                @Index(value = {"userId", "wordId"}),
                @Index(value = {"userId", "lessonId"})
        },
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "id",
                        childColumns = "userId",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Word.class,
                        parentColumns = "id",
                        childColumns = "wordId",
                        onDelete = ForeignKey.CASCADE
                )
        })
public class UserProgress {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private long userId;
    private long wordId;
    private long lessonId;
    private int masteryLevel;
    private int correctAttempts;
    private int totalAttempts;
    private double accuracy;
    private long firstLearnedAt;
    private long lastReviewedAt;
    private long nextReviewAt;
    private boolean isCompleted;
    private boolean isMastered;
    private int streakCount;
    private int reviewCount;
    private String learningStage; // "learning", "reviewing", "mastered"
    private long totalTimeSpent; // in milliseconds
    private String difficulty;

    // Default constructor (Room will use this)
    public UserProgress() {
    }

    // Mark other constructors with @Ignore
    @Ignore
    public UserProgress(long userId, long wordId, long lessonId) {
        this.userId = userId;
        this.wordId = wordId;
        this.lessonId = lessonId;
        this.masteryLevel = 0;
        this.correctAttempts = 0;
        this.totalAttempts = 0;
        this.accuracy = 0.0;
        this.firstLearnedAt = System.currentTimeMillis();
        this.lastReviewedAt = System.currentTimeMillis();
        this.nextReviewAt = System.currentTimeMillis() + (24 * 60 * 60 * 1000);
        this.isCompleted = false;
        this.isMastered = false;
        this.streakCount = 0;
        this.reviewCount = 0;
        this.learningStage = "learning";
        this.totalTimeSpent = 0;
        this.difficulty = "beginner";
    }

    @Ignore
    public UserProgress(long userId, long wordId, long lessonId, int masteryLevel,
                        int correctAttempts, int totalAttempts) {
        this.userId = userId;
        this.wordId = wordId;
        this.lessonId = lessonId;
        this.masteryLevel = masteryLevel;
        this.correctAttempts = correctAttempts;
        this.totalAttempts = totalAttempts;
        this.accuracy = totalAttempts > 0 ? (double) correctAttempts / totalAttempts * 100 : 0;
        this.firstLearnedAt = System.currentTimeMillis();
        this.lastReviewedAt = System.currentTimeMillis();
        this.isCompleted = masteryLevel >= 80;
        this.isMastered = masteryLevel >= 95;
        this.learningStage = isMastered ? "mastered" : (isCompleted ? "reviewing" : "learning");
    }

    // All getters and setters...
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }

    public long getWordId() { return wordId; }
    public void setWordId(long wordId) { this.wordId = wordId; }

    public long getLessonId() { return lessonId; }
    public void setLessonId(long lessonId) { this.lessonId = lessonId; }

    public int getMasteryLevel() { return masteryLevel; }
    public void setMasteryLevel(int masteryLevel) { this.masteryLevel = masteryLevel; }

    public int getCorrectAttempts() { return correctAttempts; }
    public void setCorrectAttempts(int correctAttempts) { this.correctAttempts = correctAttempts; }

    public int getTotalAttempts() { return totalAttempts; }
    public void setTotalAttempts(int totalAttempts) { this.totalAttempts = totalAttempts; }

    public double getAccuracy() { return accuracy; }
    public void setAccuracy(double accuracy) { this.accuracy = accuracy; }

    public long getFirstLearnedAt() { return firstLearnedAt; }
    public void setFirstLearnedAt(long firstLearnedAt) { this.firstLearnedAt = firstLearnedAt; }

    public long getLastReviewedAt() { return lastReviewedAt; }
    public void setLastReviewedAt(long lastReviewedAt) { this.lastReviewedAt = lastReviewedAt; }

    public long getNextReviewAt() { return nextReviewAt; }
    public void setNextReviewAt(long nextReviewAt) { this.nextReviewAt = nextReviewAt; }

    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }

    public boolean isMastered() { return isMastered; }
    public void setMastered(boolean mastered) { isMastered = mastered; }

    public int getStreakCount() { return streakCount; }
    public void setStreakCount(int streakCount) { this.streakCount = streakCount; }

    public int getReviewCount() { return reviewCount; }
    public void setReviewCount(int reviewCount) { this.reviewCount = reviewCount; }

    public String getLearningStage() { return learningStage; }
    public void setLearningStage(String learningStage) { this.learningStage = learningStage; }

    public long getTotalTimeSpent() { return totalTimeSpent; }
    public void setTotalTimeSpent(long totalTimeSpent) { this.totalTimeSpent = totalTimeSpent; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
}