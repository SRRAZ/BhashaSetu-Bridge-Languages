package com.bhashasetu.app.model.gamification;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.bhashasetu.app.database.converters.DateConverter;

import java.util.Date;
import androidx.room.Ignore;

/**
 * Entity representing user statistics
 */
@Entity(tableName = "user_stats")
@TypeConverters(DateConverter.class)
public class UserStats {

    @PrimaryKey(autoGenerate = true)
    private int id;
    
    private int userId;
    private int wordsLearned;
    private int exercisesCompleted;
    private int dailyStreak;
    private int longestStreak;
    private int perfectScores;
    private long totalTimeSpent;  // In seconds
    private Date registeredAt;
    private Date lastActive;
    private int categoriesUnlocked;
    private int achievementsUnlocked;
    private int badgesEarned;
    
    public UserStats() {
        this.wordsLearned = 0;
        this.exercisesCompleted = 0;
        this.dailyStreak = 0;
        this.longestStreak = 0;
        this.perfectScores = 0;
        this.totalTimeSpent = 0;
        this.registeredAt = new Date();
        this.lastActive = new Date();
        this.categoriesUnlocked = 0;
        this.achievementsUnlocked = 0;
        this.badgesEarned = 0;
    }

    @Ignore
    public UserStats(int userId) {
        this.userId = userId;
        this.wordsLearned = 0;
        this.exercisesCompleted = 0;
        this.dailyStreak = 0;
        this.longestStreak = 0;
        this.perfectScores = 0;
        this.totalTimeSpent = 0;
        this.registeredAt = new Date();
        this.lastActive = new Date();
        this.categoriesUnlocked = 0;
        this.achievementsUnlocked = 0;
        this.badgesEarned = 0;
    }
    
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
    
    public int getWordsLearned() {
        return wordsLearned;
    }
    
    public void setWordsLearned(int wordsLearned) {
        this.wordsLearned = wordsLearned;
    }
    
    public int getExercisesCompleted() {
        return exercisesCompleted;
    }
    
    public void setExercisesCompleted(int exercisesCompleted) {
        this.exercisesCompleted = exercisesCompleted;
    }
    
    public int getDailyStreak() {
        return dailyStreak;
    }
    
    public void setDailyStreak(int dailyStreak) {
        this.dailyStreak = dailyStreak;
        
        // Update longest streak if current streak is longer
        if (dailyStreak > longestStreak) {
            this.longestStreak = dailyStreak;
        }
    }
    
    public int getLongestStreak() {
        return longestStreak;
    }
    
    public void setLongestStreak(int longestStreak) {
        this.longestStreak = longestStreak;
    }
    
    public int getPerfectScores() {
        return perfectScores;
    }
    
    public void setPerfectScores(int perfectScores) {
        this.perfectScores = perfectScores;
    }
    
    public long getTotalTimeSpent() {
        return totalTimeSpent;
    }
    
    public void setTotalTimeSpent(long totalTimeSpent) {
        this.totalTimeSpent = totalTimeSpent;
    }
    
    public Date getRegisteredAt() {
        return registeredAt;
    }
    
    public void setRegisteredAt(Date registeredAt) {
        this.registeredAt = registeredAt;
    }
    
    public Date getLastActive() {
        return lastActive;
    }
    
    public void setLastActive(Date lastActive) {
        this.lastActive = lastActive;
    }
    
    public int getCategoriesUnlocked() {
        return categoriesUnlocked;
    }
    
    public void setCategoriesUnlocked(int categoriesUnlocked) {
        this.categoriesUnlocked = categoriesUnlocked;
    }
    
    public int getAchievementsUnlocked() {
        return achievementsUnlocked;
    }
    
    public void setAchievementsUnlocked(int achievementsUnlocked) {
        this.achievementsUnlocked = achievementsUnlocked;
    }
    
    public int getBadgesEarned() {
        return badgesEarned;
    }
    
    public void setBadgesEarned(int badgesEarned) {
        this.badgesEarned = badgesEarned;
    }
    
    /**
     * Update total time spent
     * @param seconds seconds to add
     */
    public void addTimeSpent(long seconds) {
        this.totalTimeSpent += seconds;
    }
    
    /**
     * Increment words learned counter
     */
    public void incrementWordsLearned() {
        this.wordsLearned++;
    }
    
    /**
     * Increment exercises completed counter
     */
    public void incrementExercisesCompleted() {
        this.exercisesCompleted++;
    }
    
    /**
     * Increment perfect scores counter
     */
    public void incrementPerfectScores() {
        this.perfectScores++;
    }
    
    /**
     * Increment achievements unlocked counter
     */
    public void incrementAchievementsUnlocked() {
        this.achievementsUnlocked++;
    }
    
    /**
     * Increment badges earned counter
     */
    public void incrementBadgesEarned() {
        this.badgesEarned++;
    }
    
    /**
     * Update last active timestamp to now
     */
    public void updateLastActive() {
        this.lastActive = new Date();
    }
    
    /**
     * Check if user was active today
     * @return true if user was active today
     */
    public boolean wasActiveToday() {
        if (lastActive == null) {
            return false;
        }
        
        // Get current date (reset hour, minute, second)
        Date today = new Date();
        today.setHours(0);
        today.setMinutes(0);
        today.setSeconds(0);
        
        // Check if last active is after start of today
        return lastActive.after(today);
    }
    
    /**
     * Format total time spent as a string (e.g., "5h 30m")
     * @return formatted time string
     */
    public String getFormattedTimeSpent() {
        long hours = totalTimeSpent / 3600;
        long minutes = (totalTimeSpent % 3600) / 60;
        
        if (hours > 0) {
            return hours + "h " + minutes + "m";
        } else {
            return minutes + "m";
        }
    }
}