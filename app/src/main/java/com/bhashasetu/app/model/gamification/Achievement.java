package com.bhashasetu.app.model.gamification;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.bhashasetu.app.database.converters.AchievementTypeConverter;
import com.bhashasetu.app.database.converters.DateConverter;
import java.util.Date;
import androidx.room.Ignore;

/**
 * Entity representing a user achievement
 */
@Entity(tableName = "user_achievements")
@TypeConverters({DateConverter.class, AchievementTypeConverter.class})
public class Achievement {
    
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    private String title;
    private String description;
    private AchievementType type;
    private int threshold;
    private int currentProgress;
    private boolean unlocked;
    private Date unlockedAt;
    private int pointsAwarded;
    private String badgeImagePath;
    
    public Achievement() {
        this.unlocked = false;
    }

    @Ignore
    public Achievement(String title, String description, AchievementType type, 
                      int threshold, int pointsAwarded, String badgeImagePath) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.threshold = threshold;
        this.currentProgress = 0;
        this.unlocked = false;
        this.pointsAwarded = pointsAwarded;
        this.badgeImagePath = badgeImagePath;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public AchievementType getType() {
        return type;
    }
    
    public void setType(AchievementType type) {
        this.type = type;
    }
    
    public int getThreshold() {
        return threshold;
    }
    
    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }
    
    public int getCurrentProgress() {
        return currentProgress;
    }
    
    public void setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress;
        checkUnlock();
    }
    
    public boolean isUnlocked() {
        return unlocked;
    }
    
    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
        if (unlocked && unlockedAt == null) {
            this.unlockedAt = new Date();
        }
    }
    
    public Date getUnlockedAt() {
        return unlockedAt;
    }
    
    public void setUnlockedAt(Date unlockedAt) {
        this.unlockedAt = unlockedAt;
    }
    
    public int getPointsAwarded() {
        return pointsAwarded;
    }
    
    public void setPointsAwarded(int pointsAwarded) {
        this.pointsAwarded = pointsAwarded;
    }
    
    public String getBadgeImagePath() {
        return badgeImagePath;
    }
    
    public void setBadgeImagePath(String badgeImagePath) {
        this.badgeImagePath = badgeImagePath;
    }
    
    /**
     * Increment progress by 1 and check if achievement is unlocked
     * @return true if the achievement was unlocked by this increment
     */
    public boolean incrementProgress() {
        boolean wasUnlocked = unlocked;
        currentProgress++;
        checkUnlock();
        return !wasUnlocked && unlocked;
    }
    
    /**
     * Increment progress by a specified amount and check if achievement is unlocked
     * @param amount amount to increment by
     * @return true if the achievement was unlocked by this increment
     */
    public boolean incrementProgress(int amount) {
        boolean wasUnlocked = unlocked;
        currentProgress += amount;
        checkUnlock();
        return !wasUnlocked && unlocked;
    }
    
    /**
     * Check if current progress meets threshold and unlock if it does
     */
    private void checkUnlock() {
        if (!unlocked && currentProgress >= threshold) {
            unlocked = true;
            unlockedAt = new Date();
        }
    }
    
    /**
     * Get progress as a percentage
     * @return percentage of completion (0-100)
     */
    public int getProgressPercentage() {
        if (threshold == 0) {
            return 0;
        }
        return Math.min(100, (int) (((float) currentProgress / threshold) * 100));
    }
}