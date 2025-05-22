package com.example.englishhindi.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Index;

@Entity(tableName = "user_progress",
        indices = {
            @Index(value = {"userId", "itemType", "itemId"}, unique = true),
            @Index("reviewDue")
        })
public class UserProgress {
    
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    private int userId;
    private String itemType; // 'word', 'lesson'
    private int itemId;
    private int completionLevel; // 0-100%
    private long lastPracticed;
    private long reviewDue;
    private int attemptCount;
    private int correctCount;
    private int srLevel; // Spaced repetition level (0-7)
    
    public UserProgress(int userId, String itemType, int itemId) {
        this.userId = userId;
        this.itemType = itemType;
        this.itemId = itemId;
        this.completionLevel = 0;
        this.lastPracticed = System.currentTimeMillis();
        this.reviewDue = System.currentTimeMillis() + (24 * 60 * 60 * 1000); // 1 day later
        this.attemptCount = 0;
        this.correctCount = 0;
        this.srLevel = 0;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getItemType() {
        return itemType;
    }
    
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
    
    public int getItemId() {
        return itemId;
    }
    
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
    
    public int getCompletionLevel() {
        return completionLevel;
    }
    
    public void setCompletionLevel(int completionLevel) {
        this.completionLevel = completionLevel;
    }
    
    public long getLastPracticed() {
        return lastPracticed;
    }
    
    public void setLastPracticed(long lastPracticed) {
        this.lastPracticed = lastPracticed;
    }
    
    public long getReviewDue() {
        return reviewDue;
    }
    
    public void setReviewDue(long reviewDue) {
        this.reviewDue = reviewDue;
    }
    
    public int getAttemptCount() {
        return attemptCount;
    }
    
    public void setAttemptCount(int attemptCount) {
        this.attemptCount = attemptCount;
    }
    
    public int getCorrectCount() {
        return correctCount;
    }
    
    public void setCorrectCount(int correctCount) {
        this.correctCount = correctCount;
    }
    
    public int getSrLevel() {
        return srLevel;
    }
    
    public void setSrLevel(int srLevel) {
        this.srLevel = srLevel;
    }
    
    // Calculate spaced repetition intervals
    public void updateSpacedRepetition(boolean wasCorrect) {
        lastPracticed = System.currentTimeMillis();
        attemptCount++;
        
        if (wasCorrect) {
            correctCount++;
            
            if (srLevel < 7) {
                srLevel++;
            }
            
            // Calculate next review time based on SR level
            long interval;
            switch (srLevel) {
                case 0: interval = 4 * 60 * 60 * 1000L; break;     // 4 hours
                case 1: interval = 8 * 60 * 60 * 1000L; break;     // 8 hours
                case 2: interval = 24 * 60 * 60 * 1000L; break;    // 1 day
                case 3: interval = 3 * 24 * 60 * 60 * 1000L; break; // 3 days
                case 4: interval = 7 * 24 * 60 * 60 * 1000L; break; // 1 week
                case 5: interval = 14 * 24 * 60 * 60 * 1000L; break; // 2 weeks
                case 6: interval = 30 * 24 * 60 * 60 * 1000L; break; // 1 month
                case 7: interval = 90 * 24 * 60 * 60 * 1000L; break; // 3 months
                default: interval = 24 * 60 * 60 * 1000L;           // 1 day default
            }
            
            reviewDue = lastPracticed + interval;
            
            // Update completion level (max 100%)
            completionLevel = Math.min(100, (int) (((float) correctCount / attemptCount) * 100) + (srLevel * 10));
        } else {
            // If answer was wrong, reduce SR level and schedule for sooner review
            if (srLevel > 0) {
                srLevel--;
            }
            
            // Review again in 1 hour
            reviewDue = lastPracticed + (1 * 60 * 60 * 1000L);
            
            // Adjust completion level
            completionLevel = Math.max(0, (int) (((float) correctCount / attemptCount) * 100) + (srLevel * 10));
        }
    }
}