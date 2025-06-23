package com.bhashasetu.app.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import java.util.Date;

@Entity(tableName = "game_scores",
        indices = {
                @Index(value = "userId"), // Added index for foreign key to resolve warning
                @Index(value = "gameType"),
                @Index(value = {"userId", "gameType", "category", "difficulty"})
        },
        foreignKeys = @ForeignKey(
                entity = User.class,
                parentColumns = "id",
                childColumns = "userId",
                onDelete = ForeignKey.CASCADE

        //@ForeignKey(
        //        entity = Word.class,  // This is correct - references the entity class
        //        parentColumns = "id",
        //        childColumns = "wordId",  // This column name doesn't change
        //        onDelete = ForeignKey.CASCADE
        //)
        ))
public class GameScore {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private long userId;
    private String gameType; // "quiz", "flashcard", "pronunciation", etc.
    private int score;
    private int level;
    private long duration; // in milliseconds
    private int correctAnswers;
    private int totalQuestions;
    private long playedAt;
    private String difficulty; // "easy", "medium", "hard"
    private boolean isCompleted;

    // Additional fields for game scores
    private double accuracy; // percentage
    private String category; // word category
    private int maxScore;
    private long timeTaken; // in milliseconds
    private Date datePlayed;
    private String gameMode; // "timed", "endless", "challenge"

    // Default Constructor (Room will use this)
    public GameScore() {
    }

    // Mark other constructors with @Ignore to resolve Room warning
    @Ignore
    public GameScore(long userId, String gameType, int score, String category, String difficulty) {
        this.userId = userId;
        this.gameType = gameType;
        this.score = score;
        this.playedAt = System.currentTimeMillis();
        this.isCompleted = true;
        this.category = category;
        this.difficulty = difficulty;
    }

    @Ignore
    public GameScore(long userId, String gameType, int score, int level,
                     long duration, int correctAnswers, int totalQuestions) {
        this.userId = userId;
        this.gameType = gameType;
        this.score = score;
        this.level = level;
        this.duration = duration;
        this.correctAnswers = correctAnswers;
        this.totalQuestions = totalQuestions;
        this.accuracy = totalQuestions > 0 ? (double) correctAnswers / totalQuestions * 100 : 0;
        this.playedAt = System.currentTimeMillis();
        this.isCompleted = true;
    }

    // Getters and Setters (implement all)
    public long getId() { return id; }
    public void setId(int id) { this.id = id; }

    public long getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getGameType() { return gameType; }
    public void setGameType(String gameType) { this.gameType = gameType; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public long getDuration() { return duration; }
    public void setDuration(long duration) { this.duration = duration; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getCorrectAnswers() { return correctAnswers; }
    public void setCorrectAnswers(int correctAnswers) { this.correctAnswers = correctAnswers; }

    public int getTotalQuestions() { return totalQuestions; }
    public void setTotalQuestions(int totalQuestions) { this.totalQuestions = totalQuestions; }

    public double getAccuracy() { return accuracy; }
    public void setAccuracy(double accuracy) { this.accuracy = accuracy; }

    public long getPlayedAt() { return playedAt; }
    public void setPlayedAt(long playedAt) { this.playedAt = playedAt; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public int getMaxScore() { return maxScore; }
    public void setMaxScore(int maxScore) { this.maxScore = maxScore; }

    public long getTimeTaken() { return timeTaken; }
    public void setTimeTaken(long timeTaken) { this.timeTaken = timeTaken; }

    public Date getDatePlayed() { return datePlayed; }
    public void setDatePlayed(Date datePlayed) { this.datePlayed = datePlayed; }

    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }

    public String getGameMode() { return gameMode; }
    public void setGameMode(String gameMode) { this.gameMode = gameMode; }

    // Calculated methods
    public double getAccuracyPercentage() {
        if (totalQuestions == 0) return 0.0;
        return (double) correctAnswers / totalQuestions * 100.0;
    }

    public double getScorePercentage() {
        if (maxScore == 0) return 0.0;
        return (double) score / maxScore * 100.0;
    }
}