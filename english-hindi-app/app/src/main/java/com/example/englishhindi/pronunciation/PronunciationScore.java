package com.example.englishhindi.pronunciation;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.englishhindi.database.Converters;
import com.example.englishhindi.model.Word;

import java.util.Date;

/**
 * Entity class representing a pronunciation score for a single word attempt.
 * This tracks various metrics for pronunciation quality.
 */
@Entity(tableName = "pronunciation_scores")
@TypeConverters(Converters.class)
public class PronunciationScore {

    @PrimaryKey(autoGenerate = true)
    private long id;
    
    // The word being scored
    private Word word;
    
    // Overall accuracy score (0-100)
    private int accuracyScore;
    
    // Score for rhythm/timing (0-100)
    private int rhythmScore;
    
    // Score for intonation/pitch (0-100)
    private int intonationScore;
    
    // Detailed phonetic feedback
    private String phoneticFeedback;
    
    // Path to the recorded audio file
    private String recordingPath;
    
    // When this score was created
    private Date timestamp;
    
    // Session ID this score belongs to
    private long sessionId;
    
    public PronunciationScore() {
        // Default constructor required by Room
    }
    
    @Ignore
    public PronunciationScore(Word word, int accuracyScore) {
        this.word = word;
        this.accuracyScore = accuracyScore;
        this.timestamp = new Date();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public int getAccuracyScore() {
        return accuracyScore;
    }

    public void setAccuracyScore(int accuracyScore) {
        this.accuracyScore = accuracyScore;
    }

    public int getRhythmScore() {
        return rhythmScore;
    }

    public void setRhythmScore(int rhythmScore) {
        this.rhythmScore = rhythmScore;
    }

    public int getIntonationScore() {
        return intonationScore;
    }

    public void setIntonationScore(int intonationScore) {
        this.intonationScore = intonationScore;
    }

    public String getPhoneticFeedback() {
        return phoneticFeedback;
    }

    public void setPhoneticFeedback(String phoneticFeedback) {
        this.phoneticFeedback = phoneticFeedback;
    }

    public String getRecordingPath() {
        return recordingPath;
    }

    public void setRecordingPath(String recordingPath) {
        this.recordingPath = recordingPath;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }
    
    /**
     * Get a qualitative rating based on the accuracy score
     * @return String representing the qualitative rating
     */
    public String getQualitativeRating() {
        if (accuracyScore >= 90) {
            return "Excellent";
        } else if (accuracyScore >= 80) {
            return "Very Good";
        } else if (accuracyScore >= 70) {
            return "Good";
        } else if (accuracyScore >= 60) {
            return "Fair";
        } else if (accuracyScore >= 50) {
            return "Needs Practice";
        } else {
            return "Try Again";
        }
    }
    
    /**
     * Calculate an overall score based on all metrics
     * @return Combined score (0-100)
     */
    public int getOverallScore() {
        // Weight the different components
        return (int)(accuracyScore * 0.6 + rhythmScore * 0.2 + intonationScore * 0.2);
    }
}