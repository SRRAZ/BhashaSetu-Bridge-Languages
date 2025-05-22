package com.example.englishhindi.model.gamification;

/**
 * Types of achievements available in the app
 */
public enum AchievementType {
    WORDS_LEARNED("Word Master", "Learn a specific number of words"),
    PERFECT_QUIZ("Perfect Quizzer", "Complete quizzes with 100% accuracy"),
    DAILY_STREAK("Daily Streak", "Practice for consecutive days"),
    SESSION_COMPLETED("Practice Champion", "Complete practice sessions"),
    EXERCISES_COMPLETED("Exercise Master", "Complete various exercise types"),
    MASTERY_LEVEL("Mastery Achievement", "Reach mastery level 5 with words"),
    TIME_SPENT("Time Dedication", "Spend time learning"),
    CATEGORIES_COMPLETED("Category Expert", "Learn words from different categories");
    
    private final String displayName;
    private final String description;
    
    AchievementType(String displayName, String description) {
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