package com.bhashasetu.app.model;

import android.content.Context;
import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manager class for handling difficulty levels, progression, and adaptive difficulty.
 * Provides methods for determining appropriate content difficulty based on user progress.
 */
public class DifficultyManager {

    // XP thresholds for each level
    private static final int[] LEVEL_XP_THRESHOLDS = {
        0,      // Level 1 (Beginner)
        500,    // Level 2 (Elementary)
        1500,   // Level 3 (Intermediate)
        4000,   // Level 4 (Advanced)
        10000   // Level 5 (Expert)
    };
    
    // Activity types for personalized difficulty
    public static final String ACTIVITY_VOCABULARY = "vocabulary";
    public static final String ACTIVITY_PRONUNCIATION = "pronunciation";
    public static final String ACTIVITY_GRAMMAR = "grammar";
    public static final String ACTIVITY_LISTENING = "listening";
    public static final String ACTIVITY_SPEAKING = "speaking";
    public static final String ACTIVITY_READING = "reading";
    public static final String ACTIVITY_WRITING = "writing";
    public static final String ACTIVITY_GAMES = "games";
    
    // Specific games for more granular difficulty control
    public static final String GAME_WORD_SCRAMBLE = "word_scramble";
    public static final String GAME_PICTURE_MATCH = "picture_match";
    public static final String GAME_WORD_ASSOCIATION = "word_association";
    public static final String GAME_FLASHCARDS = "flashcards";
    
    // Skill areas for tracking progress
    public static final String SKILL_VOCABULARY = "vocabulary";
    public static final String SKILL_PRONUNCIATION = "pronunciation";
    public static final String SKILL_GRAMMAR = "grammar";
    public static final String SKILL_LISTENING = "listening";
    public static final String SKILL_READING = "reading";
    public static final String SKILL_WRITING = "writing";
    
    // Learning paths
    public static final String PATH_GENERAL = "general";
    public static final String PATH_BUSINESS = "business";
    public static final String PATH_TRAVEL = "travel";
    public static final String PATH_ACADEMIC = "academic";
    public static final String PATH_DAILY_CONVERSATION = "daily_conversation";
    
    // Map of activity types to skill types (for updating skill progress)
    private static final Map<String, String> ACTIVITY_TO_SKILL_MAP = new HashMap<>();
    
    static {
        ACTIVITY_TO_SKILL_MAP.put(ACTIVITY_VOCABULARY, SKILL_VOCABULARY);
        ACTIVITY_TO_SKILL_MAP.put(ACTIVITY_PRONUNCIATION, SKILL_PRONUNCIATION);
        ACTIVITY_TO_SKILL_MAP.put(ACTIVITY_GRAMMAR, SKILL_GRAMMAR);
        ACTIVITY_TO_SKILL_MAP.put(ACTIVITY_LISTENING, SKILL_LISTENING);
        ACTIVITY_TO_SKILL_MAP.put(ACTIVITY_READING, SKILL_READING);
        ACTIVITY_TO_SKILL_MAP.put(ACTIVITY_WRITING, SKILL_WRITING);
        
        // Games map to multiple skills
        ACTIVITY_TO_SKILL_MAP.put(GAME_WORD_SCRAMBLE, SKILL_VOCABULARY);
        ACTIVITY_TO_SKILL_MAP.put(GAME_PICTURE_MATCH, SKILL_VOCABULARY);
        ACTIVITY_TO_SKILL_MAP.put(GAME_WORD_ASSOCIATION, SKILL_VOCABULARY);
        ACTIVITY_TO_SKILL_MAP.put(GAME_FLASHCARDS, SKILL_VOCABULARY);
    }
    
    // Private constructor to prevent instantiation
    private DifficultyManager() {}
    
    /**
     * Determine the user's level based on total XP
     * @param totalXp Total experience points
     * @return Level (1-5)
     */
    public static int getLevelForXp(int totalXp) {
        for (int i = LEVEL_XP_THRESHOLDS.length - 1; i >= 0; i--) {
            if (totalXp >= LEVEL_XP_THRESHOLDS[i]) {
                return i + 1;
            }
        }
        return 1; // Default to beginner
    }
    
    /**
     * Get the XP required for the next level
     * @param currentXp Current XP
     * @return XP required for next level, or 0 if at max level
     */
    public static int getXpForNextLevel(int currentXp) {
        int currentLevel = getLevelForXp(currentXp);
        if (currentLevel >= 5) {
            return 0; // Already at max level
        }
        return LEVEL_XP_THRESHOLDS[currentLevel] - currentXp;
    }
    
    /**
     * Get the appropriate difficulty level for an activity based on user progress
     * @param userProgress User progress data
     * @param activityType Activity type identifier
     * @return DifficultyLevel object with appropriate configuration
     */
    public static DifficultyLevel getDifficultyForActivity(UserProgress userProgress, String activityType) {
        // Check if user has a custom setting for this activity
        if (userProgress.getActivityDifficultySettings().containsKey(activityType)) {
            int customLevel = userProgress.getActivityDifficultySettings().get(activityType);
            return DifficultyLevel.fromLevel(customLevel);
        }
        
        // Otherwise use the user's overall level
        return DifficultyLevel.fromLevel(userProgress.getCurrentLevel());
    }
    
    /**
     * Update skill progress based on activity performance
     * @param userProgress User progress to update
     * @param activityType Activity type identifier
     * @param performancePercent Performance percentage (0-100)
     * @param durationMinutes Duration of the activity in minutes
     * @return Amount of XP earned
     */
    public static int updateProgressForActivity(UserProgress userProgress, String activityType, 
                                                int performancePercent, int durationMinutes) {
        // Update last activity date and streak
        boolean streakMaintained = userProgress.updateStreak(new java.util.Date());
        
        // Update learning minutes
        userProgress.addLearningMinutes(durationMinutes);
        
        // Calculate base XP (depends on performance and duration)
        int baseXp = calculateBaseXp(performancePercent, durationMinutes);
        
        // Apply difficulty multiplier
        DifficultyLevel difficultyLevel = getDifficultyForActivity(userProgress, activityType);
        int earnedXp = difficultyLevel.calculateXpReward(baseXp);
        
        // Bonus XP for maintaining streak
        if (streakMaintained && userProgress.getCurrentStreak() > 1) {
            earnedXp += Math.min(100, userProgress.getCurrentStreak());
        }
        
        // Add XP to user's total and check for level up
        boolean leveledUp = userProgress.addXp(earnedXp);
        
        // Update skill progress if applicable
        String skillId = ACTIVITY_TO_SKILL_MAP.get(activityType);
        if (skillId != null) {
            int currentProgress = userProgress.getSkillProgress().getOrDefault(skillId, 0);
            // Calculate skill progress increase (smaller for higher progress)
            int progressIncrease = Math.max(1, (int)(10 * (1.0 - (currentProgress / 100.0)) * (performancePercent / 100.0)));
            userProgress.updateSkillProgress(skillId, currentProgress + progressIncrease);
        }
        
        return earnedXp;
    }
    
    /**
     * Calculate base XP for an activity based on performance and duration
     * @param performancePercent Performance percentage (0-100)
     * @param durationMinutes Duration in minutes
     * @return Base XP amount
     */
    private static int calculateBaseXp(int performancePercent, int durationMinutes) {
        // Base formula: (performance percentage * duration factor)
        // Performance below 40% gives minimal XP
        int performanceFactor = Math.max(0, performancePercent - 40);
        int durationFactor = Math.min(15, durationMinutes); // Cap duration factor
        
        return (performanceFactor * durationFactor) / 10;
    }
    
    /**
     * Get recommended difficulty adjustment based on recent performance
     * @param recentPerformances List of recent performance percentages
     * @param currentDifficulty Current difficulty level (1-5)
     * @return Recommended new difficulty level, or 0 if no change recommended
     */
    public static int getRecommendedDifficultyAdjustment(List<Integer> recentPerformances, int currentDifficulty) {
        if (recentPerformances == null || recentPerformances.isEmpty()) {
            return 0; // No change
        }
        
        // Calculate average performance
        int sum = 0;
        for (int performance : recentPerformances) {
            sum += performance;
        }
        int averagePerformance = sum / recentPerformances.size();
        
        // Recommend increase if consistently high performance
        if (averagePerformance > 85 && currentDifficulty < 5) {
            return currentDifficulty + 1;
        }
        
        // Recommend decrease if consistently low performance
        if (averagePerformance < 40 && currentDifficulty > 1) {
            return currentDifficulty - 1;
        }
        
        return 0; // No change recommended
    }
    
    /**
     * Get personalized word recommendations based on user progress
     * @param userProgress User progress data
     * @param allWords List of all available words
     * @param count Number of recommendations to return
     * @return List of recommended word IDs
     */
    public static List<Long> getRecommendedWords(UserProgress userProgress, List<Word> allWords, int count) {
        List<Long> recommendations = new ArrayList<>();
        
        // Get user's current difficulty level
        int userLevel = userProgress.getCurrentLevel();
        
        // Get list of already mastered words
        List<Long> masteredWords = userProgress.getMasteredWordIds();
        
        // Filter words based on:
        // 1. Not already mastered
        // 2. Appropriate difficulty
        // 3. Relevant to current learning path
        List<Word> candidateWords = new ArrayList<>();
        
        String currentPath = userProgress.getCurrentLearningPath();
        
        for (Word word : allWords) {
            if (!masteredWords.contains(word.getId()) && 
                word.getDifficulty() <= userLevel + 1 &&
                word.getDifficulty() >= Math.max(1, userLevel - 1) &&
                (currentPath == null || word.getCategoryIds().contains(getPathCategoryId(currentPath)))) {
                
                candidateWords.add(word);
            }
        }
        
        // Sort by relevance (formula based on difficulty and category match)
        candidateWords.sort((w1, w2) -> {
            double relevance1 = calculateWordRelevance(w1, userProgress);
            double relevance2 = calculateWordRelevance(w2, userProgress);
            return Double.compare(relevance2, relevance1); // Higher relevance first
        });
        
        // Select top words
        int resultCount = Math.min(count, candidateWords.size());
        for (int i = 0; i < resultCount; i++) {
            recommendations.add(candidateWords.get(i).getId());
        }
        
        return recommendations;
    }
    
    /**
     * Calculate relevance score for a word based on user progress
     * @param word Word to evaluate
     * @param userProgress User progress data
     * @return Relevance score (higher = more relevant)
     */
    private static double calculateWordRelevance(Word word, UserProgress userProgress) {
        int userLevel = userProgress.getCurrentLevel();
        double difficultyMatch = 1.0 - (Math.abs(word.getDifficulty() - userLevel) / 5.0);
        
        // Check category match with learning path
        double categoryMatch = 0.0;
        String currentPath = userProgress.getCurrentLearningPath();
        if (currentPath != null && word.getCategoryIds().contains(getPathCategoryId(currentPath))) {
            categoryMatch = 0.5;
        }
        
        // Prefer words that build on existing knowledge
        double knowledgeBuilding = 0.0;
        // Logic to calculate if word builds on existing knowledge would go here
        
        return difficultyMatch + categoryMatch + knowledgeBuilding;
    }
    
    /**
     * Map learning path to corresponding category ID
     * @param path Learning path identifier
     * @return Category ID associated with the path
     */
    private static long getPathCategoryId(String path) {
        // This would be implemented based on the actual category IDs in the app
        // For now, using placeholder values
        switch (path) {
            case PATH_BUSINESS: return 2;
            case PATH_TRAVEL: return 3;
            case PATH_ACADEMIC: return 4;
            case PATH_DAILY_CONVERSATION: return 5;
            case PATH_GENERAL:
            default: return 1;
        }
    }
    
    /**
     * Get personalized skill recommendations based on user progress
     * @param userProgress User progress data
     * @return List of skill IDs with their importance score (higher = more important)
     */
    public static List<Pair<String, Double>> getRecommendedSkills(UserProgress userProgress) {
        List<Pair<String, Double>> recommendations = new ArrayList<>();
        Map<String, Integer> skillProgress = userProgress.getSkillProgress();
        
        // Add all skills with their importance score
        // Importance is higher for lower progress and essential skills
        recommendations.add(new Pair<>(SKILL_VOCABULARY, calculateSkillImportance(SKILL_VOCABULARY, skillProgress)));
        recommendations.add(new Pair<>(SKILL_PRONUNCIATION, calculateSkillImportance(SKILL_PRONUNCIATION, skillProgress)));
        recommendations.add(new Pair<>(SKILL_GRAMMAR, calculateSkillImportance(SKILL_GRAMMAR, skillProgress)));
        recommendations.add(new Pair<>(SKILL_LISTENING, calculateSkillImportance(SKILL_LISTENING, skillProgress)));
        recommendations.add(new Pair<>(SKILL_READING, calculateSkillImportance(SKILL_READING, skillProgress)));
        recommendations.add(new Pair<>(SKILL_WRITING, calculateSkillImportance(SKILL_WRITING, skillProgress)));
        
        // Sort by importance (higher first)
        recommendations.sort((p1, p2) -> Double.compare(p2.second, p1.second));
        
        return recommendations;
    }
    
    /**
     * Calculate importance score for a skill
     * @param skillId Skill identifier
     * @param skillProgress Map of skill progress percentages
     * @return Importance score (higher = more important)
     */
    private static double calculateSkillImportance(String skillId, Map<String, Integer> skillProgress) {
        // Get current progress (default to 0)
        int progress = skillProgress.getOrDefault(skillId, 0);
        
        // Base importance is inverse of progress (lower progress = higher importance)
        double importanceFromProgress = 1.0 - (progress / 100.0);
        
        // Skill-specific importance factors
        double skillBaseFactor;
        switch (skillId) {
            case SKILL_VOCABULARY: skillBaseFactor = 1.0; break;  // Most essential
            case SKILL_PRONUNCIATION: skillBaseFactor = 0.9; break;
            case SKILL_GRAMMAR: skillBaseFactor = 0.8; break;
            case SKILL_LISTENING: skillBaseFactor = 0.8; break;
            case SKILL_READING: skillBaseFactor = 0.7; break;
            case SKILL_WRITING: skillBaseFactor = 0.7; break;
            default: skillBaseFactor = 0.5;
        }
        
        return importanceFromProgress * skillBaseFactor;
    }
}