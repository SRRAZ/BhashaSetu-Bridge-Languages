package com.example.englishhindi.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.englishhindi.database.Converters;
import com.example.englishhindi.pronunciation.PronunciationScore;

import java.util.Date;
import java.util.List;

@Entity(tableName = "pronunciation_sessions")
@TypeConverters(Converters.class)
public class PronunciationSession {

    @PrimaryKey(autoGenerate = true)
    private long id;
    
    private Date startTime;
    private Date endTime;
    
    // List of words in this practice session
    private List<Word> words;
    
    // Scores for each word (index corresponds to words list)
    private List<PronunciationScore> scores;
    
    // Overall session statistics
    private int averageAccuracyScore;
    private int averageRhythmScore;
    private int averageIntonationScore;
    
    // User ID (for multi-user support)
    private long userId;
    
    // Session difficulty level
    private int difficultyLevel;
    
    public PronunciationSession() {
        // Default constructor required by Room
    }
    
    @Ignore
    public PronunciationSession(List<Word> words) {
        this.words = words;
        this.startTime = new Date();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }

    public List<PronunciationScore> getScores() {
        return scores;
    }

    public void setScores(List<PronunciationScore> scores) {
        this.scores = scores;
    }

    public int getAverageAccuracyScore() {
        return averageAccuracyScore;
    }

    public void setAverageAccuracyScore(int averageAccuracyScore) {
        this.averageAccuracyScore = averageAccuracyScore;
    }

    public int getAverageRhythmScore() {
        return averageRhythmScore;
    }

    public void setAverageRhythmScore(int averageRhythmScore) {
        this.averageRhythmScore = averageRhythmScore;
    }

    public int getAverageIntonationScore() {
        return averageIntonationScore;
    }

    public void setAverageIntonationScore(int averageIntonationScore) {
        this.averageIntonationScore = averageIntonationScore;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }
    
    // Calculate session duration in minutes
    public double getDurationMinutes() {
        if (startTime == null || endTime == null) {
            return 0;
        }
        
        long durationMillis = endTime.getTime() - startTime.getTime();
        return durationMillis / (1000.0 * 60);
    }
    
    // Get completion percentage
    public int getCompletionPercentage() {
        if (words == null || words.isEmpty() || scores == null) {
            return 0;
        }
        
        int completed = 0;
        for (PronunciationScore score : scores) {
            if (score != null) {
                completed++;
            }
        }
        
        return (completed * 100) / words.size();
    }
}