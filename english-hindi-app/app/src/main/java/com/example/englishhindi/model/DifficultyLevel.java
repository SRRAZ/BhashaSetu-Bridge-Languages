package com.example.englishhindi.model;

import androidx.annotation.DrawableRes;

import com.example.englishhindi.R;

/**
 * Represents a difficulty level in the learning system.
 * Each level has specific characteristics that affect content selection,
 * scoring, and user progression.
 */
public class DifficultyLevel {

    // Difficulty constants
    public static final int BEGINNER = 1;
    public static final int ELEMENTARY = 2;
    public static final int INTERMEDIATE = 3;
    public static final int ADVANCED = 4;
    public static final int EXPERT = 5;
    
    private int level;
    private String name;
    private String description;
    private int requiredXp;
    private int iconResId;
    private float scoreMultiplier;
    private int wordComplexityMax;
    private boolean includesSynonyms;
    private boolean includesIdioms;
    private int maxPhraseLength;
    private float timeMultiplier;
    
    /**
     * Get a pre-configured difficulty level by its integer value
     * @param level Integer level (1-5)
     * @return DifficultyLevel object with appropriate configurations
     */
    public static DifficultyLevel fromLevel(int level) {
        DifficultyLevel difficultyLevel = new DifficultyLevel();
        difficultyLevel.level = level;
        
        switch (level) {
            case BEGINNER:
                difficultyLevel.name = "Beginner";
                difficultyLevel.description = "Basic vocabulary and simple phrases";
                difficultyLevel.requiredXp = 0;
                difficultyLevel.iconResId = R.drawable.ic_difficulty_beginner;
                difficultyLevel.scoreMultiplier = 1.0f;
                difficultyLevel.wordComplexityMax = 1;
                difficultyLevel.includesSynonyms = false;
                difficultyLevel.includesIdioms = false;
                difficultyLevel.maxPhraseLength = 3;
                difficultyLevel.timeMultiplier = 1.5f;
                break;
                
            case ELEMENTARY:
                difficultyLevel.name = "Elementary";
                difficultyLevel.description = "Common vocabulary and everyday phrases";
                difficultyLevel.requiredXp = 500;
                difficultyLevel.iconResId = R.drawable.ic_difficulty_elementary;
                difficultyLevel.scoreMultiplier = 1.2f;
                difficultyLevel.wordComplexityMax = 2;
                difficultyLevel.includesSynonyms = true;
                difficultyLevel.includesIdioms = false;
                difficultyLevel.maxPhraseLength = 5;
                difficultyLevel.timeMultiplier = 1.2f;
                break;
                
            case INTERMEDIATE:
                difficultyLevel.name = "Intermediate";
                difficultyLevel.description = "Expanded vocabulary and conversational phrases";
                difficultyLevel.requiredXp = 1500;
                difficultyLevel.iconResId = R.drawable.ic_difficulty_intermediate;
                difficultyLevel.scoreMultiplier = 1.5f;
                difficultyLevel.wordComplexityMax = 3;
                difficultyLevel.includesSynonyms = true;
                difficultyLevel.includesIdioms = true;
                difficultyLevel.maxPhraseLength = 8;
                difficultyLevel.timeMultiplier = 1.0f;
                break;
                
            case ADVANCED:
                difficultyLevel.name = "Advanced";
                difficultyLevel.description = "Advanced vocabulary and complex expressions";
                difficultyLevel.requiredXp = 4000;
                difficultyLevel.iconResId = R.drawable.ic_difficulty_advanced;
                difficultyLevel.scoreMultiplier = 1.8f;
                difficultyLevel.wordComplexityMax = 4;
                difficultyLevel.includesSynonyms = true;
                difficultyLevel.includesIdioms = true;
                difficultyLevel.maxPhraseLength = 12;
                difficultyLevel.timeMultiplier = 0.8f;
                break;
                
            case EXPERT:
                difficultyLevel.name = "Expert";
                difficultyLevel.description = "Native-level vocabulary and expressions";
                difficultyLevel.requiredXp = 10000;
                difficultyLevel.iconResId = R.drawable.ic_difficulty_expert;
                difficultyLevel.scoreMultiplier = 2.0f;
                difficultyLevel.wordComplexityMax = 5;
                difficultyLevel.includesSynonyms = true;
                difficultyLevel.includesIdioms = true;
                difficultyLevel.maxPhraseLength = 20;
                difficultyLevel.timeMultiplier = 0.7f;
                break;
                
            default:
                // Default to beginner if invalid level
                return fromLevel(BEGINNER);
        }
        
        return difficultyLevel;
    }
    
    /**
     * Get the next difficulty level after this one
     * @return The next difficulty level, or the same level if already at maximum
     */
    public DifficultyLevel getNextLevel() {
        if (level < EXPERT) {
            return fromLevel(level + 1);
        } else {
            return this; // Already at max level
        }
    }
    
    /**
     * Check if a user has enough XP to reach this level
     * @param userXp The user's current XP
     * @return true if the user has enough XP for this level
     */
    public boolean isUnlockedForXp(int userXp) {
        return userXp >= requiredXp;
    }
    
    /**
     * Calculate additional XP earned based on this difficulty level
     * @param baseXp Base XP amount
     * @return Adjusted XP with difficulty multiplier
     */
    public int calculateXpReward(int baseXp) {
        return Math.round(baseXp * scoreMultiplier);
    }
    
    /**
     * Calculate time limit for timed activities based on this difficulty level
     * @param baseTimeSeconds Base time in seconds
     * @return Adjusted time with difficulty multiplier
     */
    public int calculateTimeLimit(int baseTimeSeconds) {
        return Math.round(baseTimeSeconds * timeMultiplier);
    }

    // Getters
    
    public int getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getRequiredXp() {
        return requiredXp;
    }

    @DrawableRes
    public int getIconResId() {
        return iconResId;
    }

    public float getScoreMultiplier() {
        return scoreMultiplier;
    }

    public int getWordComplexityMax() {
        return wordComplexityMax;
    }

    public boolean includesSynonyms() {
        return includesSynonyms;
    }

    public boolean includesIdioms() {
        return includesIdioms;
    }

    public int getMaxPhraseLength() {
        return maxPhraseLength;
    }

    public float getTimeMultiplier() {
        return timeMultiplier;
    }
}