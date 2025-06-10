package com.bhashasetu.app.model.gamification;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entity representing a user's level in the app
 */
@Entity(tableName = "user_levels")
public class UserLevel {
    
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    private int userId;
    private int level;
    private int currentExp;
    private int expToNextLevel;
    private int totalExp;
    private String title;
    
    public UserLevel() {
        this.level = 1;
        this.currentExp = 0;
        this.expToNextLevel = 100;
        this.totalExp = 0;
        this.title = "Beginner";
    }
    
    public UserLevel(int userId) {
        this.userId = userId;
        this.level = 1;
        this.currentExp = 0;
        this.expToNextLevel = 100;
        this.totalExp = 0;
        this.title = "Beginner";
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
    
    public int getLevel() {
        return level;
    }
    
    public void setLevel(int level) {
        this.level = level;
        updateTitle();
    }
    
    public int getCurrentExp() {
        return currentExp;
    }
    
    public void setCurrentExp(int currentExp) {
        this.currentExp = currentExp;
    }
    
    public int getExpToNextLevel() {
        return expToNextLevel;
    }
    
    public void setExpToNextLevel(int expToNextLevel) {
        this.expToNextLevel = expToNextLevel;
    }
    
    public int getTotalExp() {
        return totalExp;
    }
    
    public void setTotalExp(int totalExp) {
        this.totalExp = totalExp;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * Add experience points and check for level up
     * @param exp experience points to add
     * @return true if user leveled up
     */
    public boolean addExp(int exp) {
        int oldLevel = level;
        totalExp += exp;
        currentExp += exp;
        
        // Check for level up
        while (currentExp >= expToNextLevel) {
            levelUp();
        }
        
        return level > oldLevel;
    }
    
    /**
     * Increase level by 1 and recalculate exp requirements
     */
    private void levelUp() {
        level++;
        currentExp -= expToNextLevel;
        
        // Increase exp required for next level (formula can be adjusted)
        expToNextLevel = 100 + (level - 1) * 50;
        
        // Update title
        updateTitle();
    }
    
    /**
     * Update the title based on level
     */
    private void updateTitle() {
        if (level <= 5) {
            title = "Beginner";
        } else if (level <= 10) {
            title = "Novice";
        } else if (level <= 15) {
            title = "Student";
        } else if (level <= 20) {
            title = "Scholar";
        } else if (level <= 25) {
            title = "Expert";
        } else if (level <= 30) {
            title = "Master";
        } else {
            title = "Grandmaster";
        }
    }
    
    /**
     * Get progress to next level as a percentage
     * @return percentage progress (0-100)
     */
    public int getLevelProgressPercentage() {
        if (expToNextLevel == 0) {
            return 100;
        }
        return (int) (((float) currentExp / expToNextLevel) * 100);
    }
}