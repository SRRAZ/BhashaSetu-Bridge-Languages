package com.bhashasetu.app.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import java.util.Date;

@Entity(tableName = "game_scores",
        foreignKeys = @ForeignKey(entity = User.class,
                parentColumns = "id",
                childColumns = "userId",
                onDelete = ForeignKey.CASCADE))
public class GameScore {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int userId;
    private String gameType; // "quiz", "flashcard", "pronunciation", etc.
    private String category; // word category
    private String difficulty; // "easy", "medium", "hard"
    private int score;
    private int maxScore;
    private int correctAnswers;
    private int totalQuestions;
    private long timeTaken; // in milliseconds
    private Date datePlayed;
    private boolean isCompleted;
    private String gameMode; // "timed", "endless", "challenge"

    // Constructors
    public GameScore() {
        this.datePlayed = new Date();
        this.isCompleted = false;
    }

    public GameScore(int userId, String gameType, String category, String difficulty) {
        this();
        this.userId = userId;
        this.gameType = gameType;
        this.category = category;
        this.difficulty = difficulty;
    }

    // Getters and Setters (implement all)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getGameType() { return gameType; }
    public void setGameType(String gameType) { this.gameType = gameType; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public int getMaxScore() { return maxScore; }
    public void setMaxScore(int maxScore) { this.maxScore = maxScore; }

    public int getCorrectAnswers() { return correctAnswers; }
    public void setCorrectAnswers(int correctAnswers) { this.correctAnswers = correctAnswers; }

    public int getTotalQuestions() { return totalQuestions; }
    public void setTotalQuestions(int totalQuestions) { this.totalQuestions = totalQuestions; }

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