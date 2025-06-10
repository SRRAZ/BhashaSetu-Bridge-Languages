package com.bhashasetu.app.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.bhashasetu.app.database.Converters;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Entity class for tracking a user's progress throughout the application.
 * Stores experience points, level, skills progress, and other tracking data.
 */
@Entity(tableName = "user_progress",
        foreignKeys = @ForeignKey(entity = User.class,
                                  parentColumns = "id",
                                  childColumns = "userId",
                                  onDelete = ForeignKey.CASCADE),
        indices = {@Index("userId")})
@TypeConverters(Converters.class)
public class UserProgress {

    @PrimaryKey(autoGenerate = true)
    private long id;
    
    private long userId;
    
    // Overall experience and level
    private int totalXp;
    private int currentLevel;
    private Date lastLevelUpDate;
    
    // Skill-specific progress (key: skill identifier, value: progress percentage 0-100)
    private Map<String, Integer> skillProgress;
    
    // Recently mastered words (IDs)
    private List<Long> masteredWordIds;
    
    // Learning streaks
    private int currentStreak;
    private int longestStreak;
    private Date lastActivityDate;
    
    // Learning path identifiers and completion status
    private String currentLearningPath;
    private List<String> completedPaths;
    
    // Category completion status (key: category ID, value: completion percentage 0-100)
    private Map<Long, Integer> categoryCompletion;
    
    // Custom personalized difficulty settings
    private Map<String, Integer> activityDifficultySettings;
    
    // Weekly learning targets and actual progress
    private int weeklyTargetMinutes;
    private int weeklyCompletedMinutes;
    
    // Recommendations tracking
    private List<Long> recommendedWordIds;
    private List<String> recommendedSkills;
    
    /**
     * Default constructor for Room
     */
    public UserProgress() {
        // Initialize collections
        skillProgress = new HashMap<>();
        masteredWordIds = new ArrayList<>();
        completedPaths = new ArrayList<>();
        categoryCompletion = new HashMap<>();
        activityDifficultySettings = new HashMap<>();
        recommendedWordIds = new ArrayList<>();
        recommendedSkills = new ArrayList<>();
        
        // Set default values
        totalXp = 0;
        currentLevel = 1;
        lastLevelUpDate = new Date();
        currentStreak = 0;
        longestStreak = 0;
        lastActivityDate = new Date();
        weeklyTargetMinutes = 60; // Default: 1 hour per week
        weeklyCompletedMinutes = 0;
    }
    
    /**
     * Create new progress record for a user
     * @param userId User ID
     */
    public UserProgress(long userId) {
        this();
        this.userId = userId;
    }
    
    // Getters and Setters
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getTotalXp() {
        return totalXp;
    }

    public void setTotalXp(int totalXp) {
        this.totalXp = totalXp;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public Date getLastLevelUpDate() {
        return lastLevelUpDate;
    }

    public void setLastLevelUpDate(Date lastLevelUpDate) {
        this.lastLevelUpDate = lastLevelUpDate;
    }

    public Map<String, Integer> getSkillProgress() {
        return skillProgress;
    }

    public void setSkillProgress(Map<String, Integer> skillProgress) {
        this.skillProgress = skillProgress;
    }

    public List<Long> getMasteredWordIds() {
        return masteredWordIds;
    }

    public void setMasteredWordIds(List<Long> masteredWordIds) {
        this.masteredWordIds = masteredWordIds;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    }

    public int getLongestStreak() {
        return longestStreak;
    }

    public void setLongestStreak(int longestStreak) {
        this.longestStreak = longestStreak;
    }

    public Date getLastActivityDate() {
        return lastActivityDate;
    }

    public void setLastActivityDate(Date lastActivityDate) {
        this.lastActivityDate = lastActivityDate;
    }

    public String getCurrentLearningPath() {
        return currentLearningPath;
    }

    public void setCurrentLearningPath(String currentLearningPath) {
        this.currentLearningPath = currentLearningPath;
    }

    public List<String> getCompletedPaths() {
        return completedPaths;
    }

    public void setCompletedPaths(List<String> completedPaths) {
        this.completedPaths = completedPaths;
    }

    public Map<Long, Integer> getCategoryCompletion() {
        return categoryCompletion;
    }

    public void setCategoryCompletion(Map<Long, Integer> categoryCompletion) {
        this.categoryCompletion = categoryCompletion;
    }

    public Map<String, Integer> getActivityDifficultySettings() {
        return activityDifficultySettings;
    }

    public void setActivityDifficultySettings(Map<String, Integer> activityDifficultySettings) {
        this.activityDifficultySettings = activityDifficultySettings;
    }

    public int getWeeklyTargetMinutes() {
        return weeklyTargetMinutes;
    }

    public void setWeeklyTargetMinutes(int weeklyTargetMinutes) {
        this.weeklyTargetMinutes = weeklyTargetMinutes;
    }

    public int getWeeklyCompletedMinutes() {
        return weeklyCompletedMinutes;
    }

    public void setWeeklyCompletedMinutes(int weeklyCompletedMinutes) {
        this.weeklyCompletedMinutes = weeklyCompletedMinutes;
    }

    public List<Long> getRecommendedWordIds() {
        return recommendedWordIds;
    }

    public void setRecommendedWordIds(List<Long> recommendedWordIds) {
        this.recommendedWordIds = recommendedWordIds;
    }

    public List<String> getRecommendedSkills() {
        return recommendedSkills;
    }

    public void setRecommendedSkills(List<String> recommendedSkills) {
        this.recommendedSkills = recommendedSkills;
    }
    
    // Helper methods
    
    /**
     * Add XP points to the user's total and check for level up
     * @param xpPoints XP points to add
     * @return true if user leveled up as a result of this addition
     */
    public boolean addXp(int xpPoints) {
        int previousLevel = DifficultyManager.getLevelForXp(totalXp);
        totalXp += xpPoints;
        int newLevel = DifficultyManager.getLevelForXp(totalXp);
        
        if (newLevel > previousLevel) {
            currentLevel = newLevel;
            lastLevelUpDate = new Date();
            return true;
        }
        
        return false;
    }
    
    /**
     * Update a skill's progress percentage
     * @param skillId Skill identifier
     * @param progressPercent Progress percentage (0-100)
     */
    public void updateSkillProgress(String skillId, int progressPercent) {
        skillProgress.put(skillId, Math.min(100, Math.max(0, progressPercent)));
    }
    
    /**
     * Add a mastered word to the user's list
     * @param wordId Word ID
     */
    public void addMasteredWord(long wordId) {
        if (!masteredWordIds.contains(wordId)) {
            masteredWordIds.add(wordId);
        }
    }
    
    /**
     * Update user's daily learning streak
     * @param activityDate Date of the activity
     * @return true if streak was updated/maintained, false if streak was broken
     */
    public boolean updateStreak(Date activityDate) {
        if (lastActivityDate == null) {
            currentStreak = 1;
            lastActivityDate = activityDate;
            return true;
        }
        
        // Check if this is a new day within the streak window
        java.util.Calendar lastCal = java.util.Calendar.getInstance();
        lastCal.setTime(lastActivityDate);
        
        java.util.Calendar currentCal = java.util.Calendar.getInstance();
        currentCal.setTime(activityDate);
        
        // Check if date is the same day
        boolean isSameDay = 
            lastCal.get(java.util.Calendar.YEAR) == currentCal.get(java.util.Calendar.YEAR) &&
            lastCal.get(java.util.Calendar.DAY_OF_YEAR) == currentCal.get(java.util.Calendar.DAY_OF_YEAR);
            
        if (isSameDay) {
            // Same day, no streak change
            lastActivityDate = activityDate; // Update to latest time
            return true;
        }
        
        // Check if date is the next day
        lastCal.add(java.util.Calendar.DAY_OF_YEAR, 1);
        boolean isNextDay = 
            lastCal.get(java.util.Calendar.YEAR) == currentCal.get(java.util.Calendar.YEAR) &&
            lastCal.get(java.util.Calendar.DAY_OF_YEAR) == currentCal.get(java.util.Calendar.DAY_OF_YEAR);
            
        if (isNextDay) {
            // Next day, streak continues
            currentStreak++;
            if (currentStreak > longestStreak) {
                longestStreak = currentStreak;
            }
            lastActivityDate = activityDate;
            return true;
        }
        
        // If we got here, streak is broken
        currentStreak = 1; // Reset to 1 for today
        lastActivityDate = activityDate;
        return false;
    }
    
    /**
     * Mark a learning path as completed
     * @param pathId Path identifier
     */
    public void completeLearningPath(String pathId) {
        if (!completedPaths.contains(pathId)) {
            completedPaths.add(pathId);
        }
    }
    
    /**
     * Update category completion percentage
     * @param categoryId Category ID
     * @param completionPercent Completion percentage (0-100)
     */
    public void updateCategoryCompletion(long categoryId, int completionPercent) {
        categoryCompletion.put(categoryId, Math.min(100, Math.max(0, completionPercent)));
    }
    
    /**
     * Set a custom difficulty level for a specific activity type
     * @param activityType Activity type identifier
     * @param difficultyLevel Difficulty level (1-5)
     */
    public void setActivityDifficulty(String activityType, int difficultyLevel) {
        activityDifficultySettings.put(activityType, Math.min(5, Math.max(1, difficultyLevel)));
    }
    
    /**
     * Get the current difficulty level for an activity type
     * @param activityType Activity type identifier
     * @return Difficulty level (1-5), defaults to user's level or 1
     */
    public int getActivityDifficulty(String activityType) {
        return activityDifficultySettings.getOrDefault(activityType, currentLevel);
    }
    
    /**
     * Add a word to the recommended list
     * @param wordId Word ID
     */
    public void addRecommendedWord(long wordId) {
        if (!recommendedWordIds.contains(wordId)) {
            recommendedWordIds.add(wordId);
            // Keep the list at a reasonable size
            if (recommendedWordIds.size() > 50) {
                recommendedWordIds.remove(0);
            }
        }
    }
    
    /**
     * Add a skill to the recommended list
     * @param skillId Skill identifier
     */
    public void addRecommendedSkill(String skillId) {
        if (!recommendedSkills.contains(skillId)) {
            recommendedSkills.add(skillId);
            // Keep the list at a reasonable size
            if (recommendedSkills.size() > 5) {
                recommendedSkills.remove(0);
            }
        }
    }
    
    /**
     * Add learning minutes to the weekly total
     * @param minutes Minutes to add
     */
    public void addLearningMinutes(int minutes) {
        weeklyCompletedMinutes += minutes;
    }
    
    /**
     * Reset weekly learning minutes (e.g., at the start of a new week)
     */
    public void resetWeeklyMinutes() {
        weeklyCompletedMinutes = 0;
    }
    
    /**
     * Get the percentage of weekly target minutes completed
     * @return Completion percentage (0-100)
     */
    public int getWeeklyTargetCompletion() {
        if (weeklyTargetMinutes <= 0) {
            return 100;
        }
        return Math.min(100, (weeklyCompletedMinutes * 100) / weeklyTargetMinutes);
    }
}