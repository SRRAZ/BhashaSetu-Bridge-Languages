package com.example.englishhindi.model.gamification;

/**
 * Enumeration of different sources of points in the app
 */
public enum PointsSource {
    EXERCISE_COMPLETION("Exercise Completion", "Points earned from completing exercises"),
    ACHIEVEMENT_UNLOCK("Achievement Unlock", "Points earned from unlocking achievements"),
    DAILY_STREAK("Daily Streak", "Points earned for maintaining a daily practice streak"),
    WORD_MASTERY("Word Mastery", "Points earned for mastering words"),
    INITIAL_BONUS("Initial Bonus", "Welcome bonus points"),
    PERFECT_SCORE("Perfect Score", "Points earned for achieving a perfect score"),
    LEVEL_UP("Level Up", "Points earned for reaching a new level");
    
    private final String displayName;
    private final String description;
    
    PointsSource(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public String toString() {
        return displayName;
    }
}