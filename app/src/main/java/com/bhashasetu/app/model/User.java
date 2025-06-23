package com.bhashasetu.app.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    private long id;

    // Basic User Information
    private String username;
    private String email;
    private String fullName; // ✅ Added missing field
    private String profilePicture;
    private long createdAt;
    private long lastActiveAt;
    private boolean isActive;

    // Learning Progress fields
    private int totalScore;
    private int level;
    private String preferredLanguage;
    private String learningGoal;     // e.g., "Learn Hindi", "Master pronunciation"
    private int totalPoints;         // Total accumulated learning points
    private int currentStreak;       // Current consecutive days of learning
    private int maxStreak;           // Maximum consecutive days achieved

    // Additional fields for comprehensive user tracking
    private int wordsLearned;        // Total words learned count
    private int lessonsCompleted;    // Total lessons completed
    private long totalStudyTime;     // Total study time in milliseconds
    private String currentLevel;     // Current learning level (Beginner, Intermediate, Advanced)
    private String targetLanguage;   // Language user is learning
    private String nativeLanguage;   // User's native language
    private boolean isPremium;       // Premium subscription status
    private long lastStreakDate;     // Last date when streak was updated
    private int weeklyGoal;          // Weekly learning goal (minutes or points)
    private int monthlyGoal;         // Monthly learning goal

    // Default constructor (Room will use this)
    public User() {
    }

    // Mark other constructors with @Ignore to resolve Room warning
    @Ignore
    public User(String username, String email, String fullName) {
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.createdAt = System.currentTimeMillis();
        this.lastActiveAt = System.currentTimeMillis();
        this.isActive = true;
        this.level = 1;
        this.totalScore = 0;
        this.totalPoints = 0;
        this.currentStreak = 0;
        this.maxStreak = 0;
        this.wordsLearned = 0;
        this.lessonsCompleted = 0;
        this.totalStudyTime = 0;
        this.currentLevel = "Beginner";
        this.isPremium = false;
        this.lastStreakDate = System.currentTimeMillis();
        this.weeklyGoal = 300; // 5 hours per week in minutes
        this.monthlyGoal = 1200; // 20 hours per month in minutes
    }

    @Ignore
    public User(long id, String username, String email, String fullName,
                String profilePicture, long createdAt, long lastActiveAt,
                int totalScore, int level, String preferredLanguage,
                boolean isActive, String learningGoal, int totalPoints,
                int currentStreak, int maxStreak) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.profilePicture = profilePicture;
        this.createdAt = createdAt;
        this.lastActiveAt = lastActiveAt;
        this.totalScore = totalScore;
        this.level = level;
        this.preferredLanguage = preferredLanguage;
        this.isActive = isActive;
        this.learningGoal = learningGoal;
        this.totalPoints = totalPoints;
        this.currentStreak = currentStreak;
        this.maxStreak = maxStreak;
    }

    // Getters and setters for all fields
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getProfilePicture() { return profilePicture; }
    public void setProfilePicture(String profilePicture) { this.profilePicture = profilePicture; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }

    public long getLastActiveAt() { return lastActiveAt; }
    public void setLastActiveAt(long lastActiveAt) { this.lastActiveAt = lastActiveAt; }

    public int getTotalScore() { return totalScore; }
    public void setTotalScore(int totalScore) { this.totalScore = totalScore; }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public String getPreferredLanguage() { return preferredLanguage; }
    public void setPreferredLanguage(String preferredLanguage) { this.preferredLanguage = preferredLanguage; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    // ✅ Getters and setters for new fields
    public String getLearningGoal() { return learningGoal; }
    public void setLearningGoal(String learningGoal) { this.learningGoal = learningGoal; }

    public int getTotalPoints() { return totalPoints; }
    public void setTotalPoints(int totalPoints) { this.totalPoints = totalPoints; }

    public int getCurrentStreak() { return currentStreak; }
    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
        // Update max streak if current exceeds it
        if (currentStreak > this.maxStreak) {
            this.maxStreak = currentStreak;
        }
    }

    public int getMaxStreak() { return maxStreak; }
    public void setMaxStreak(int maxStreak) { this.maxStreak = maxStreak; }

    public int getWordsLearned() { return wordsLearned; }
    public void setWordsLearned(int wordsLearned) { this.wordsLearned = wordsLearned; }

    public int getLessonsCompleted() { return lessonsCompleted; }
    public void setLessonsCompleted(int lessonsCompleted) { this.lessonsCompleted = lessonsCompleted; }

    public long getTotalStudyTime() { return totalStudyTime; }
    public void setTotalStudyTime(long totalStudyTime) { this.totalStudyTime = totalStudyTime; }

    public String getCurrentLevel() { return currentLevel; }
    public void setCurrentLevel(String currentLevel) { this.currentLevel = currentLevel; }

    public String getTargetLanguage() { return targetLanguage; }
    public void setTargetLanguage(String targetLanguage) { this.targetLanguage = targetLanguage; }

    public String getNativeLanguage() { return nativeLanguage; }
    public void setNativeLanguage(String nativeLanguage) { this.nativeLanguage = nativeLanguage; }

    public boolean isPremium() { return isPremium; }
    public void setPremium(boolean premium) { isPremium = premium; }

    public long getLastStreakDate() { return lastStreakDate; }
    public void setLastStreakDate(long lastStreakDate) { this.lastStreakDate = lastStreakDate; }

    public int getWeeklyGoal() { return weeklyGoal; }
    public void setWeeklyGoal(int weeklyGoal) { this.weeklyGoal = weeklyGoal; }

    public int getMonthlyGoal() { return monthlyGoal; }
    public void setMonthlyGoal(int monthlyGoal) { this.monthlyGoal = monthlyGoal; }

    // ✅ Utility methods for streak management
    public void incrementStreak() {
        this.currentStreak++;
        this.lastStreakDate = System.currentTimeMillis();
        if (this.currentStreak > this.maxStreak) {
            this.maxStreak = this.currentStreak;
        }
    }

    public void resetStreak() {
        this.currentStreak = 0;
        this.lastStreakDate = System.currentTimeMillis();
    }

    public void addPoints(int points) {
        this.totalPoints += points;
        this.totalScore += points;
    }

    public void addStudyTime(long timeInMillis) {
        this.totalStudyTime += timeInMillis;
    }
}