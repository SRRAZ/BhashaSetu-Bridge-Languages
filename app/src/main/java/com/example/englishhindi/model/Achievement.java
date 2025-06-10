package com.bhashasetu.app.model;

import androidx.annotation.DrawableRes;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Model class for user achievements in the app.
 * Achievements are awarded for various learning milestones.
 */
@Entity(tableName = "achievements")
public class Achievement {

    // Achievement types
    public static final String TYPE_VOCABULARY = "vocabulary";
    public static final String TYPE_PRONUNCIATION = "pronunciation";
    public static final String TYPE_STREAK = "streak";
    public static final String TYPE_GAME = "game";
    public static final String TYPE_MASTERY = "mastery";
    
    @PrimaryKey
    private String id;
    
    private String title;
    private String description;
    private String type;
    private int iconResId;
    private int pointsValue;
    private boolean unlocked;
    private long dateUnlocked;
    private int progressCurrent;
    private int progressTarget;
    private boolean secret;
    
    /**
     * Default constructor for Room.
     */
    public Achievement() {
    }
    
    /**
     * Constructor for all fields.
     */
    public Achievement(String id, String title, String description, String type, 
                      int iconResId, int pointsValue, boolean unlocked, 
                      long dateUnlocked, int progressCurrent, int progressTarget, 
                      boolean secret) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.iconResId = iconResId;
        this.pointsValue = pointsValue;
        this.unlocked = unlocked;
        this.dateUnlocked = dateUnlocked;
        this.progressCurrent = progressCurrent;
        this.progressTarget = progressTarget;
        this.secret = secret;
    }
    
    /**
     * Constructor for locked achievements.
     */
    public Achievement(String id, String title, String description, String type, 
                      int iconResId, int pointsValue, int progressTarget, boolean secret) {
        this(id, title, description, type, iconResId, pointsValue, false, 
             0, 0, progressTarget, secret);
    }
    
    /**
     * Get the achievement ID.
     */
    public String getId() {
        return id;
    }
    
    /**
     * Set the achievement ID.
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * Get the achievement title.
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Set the achievement title.
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * Get the achievement description.
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Set the achievement description.
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Get the achievement type.
     */
    public String getType() {
        return type;
    }
    
    /**
     * Set the achievement type.
     */
    public void setType(String type) {
        this.type = type;
    }
    
    /**
     * Get the achievement icon resource ID.
     */
    public int getIconResId() {
        return iconResId;
    }
    
    /**
     * Set the achievement icon resource ID.
     */
    public void setIconResId(@DrawableRes int iconResId) {
        this.iconResId = iconResId;
    }
    
    /**
     * Get the achievement points value.
     */
    public int getPointsValue() {
        return pointsValue;
    }
    
    /**
     * Set the achievement points value.
     */
    public void setPointsValue(int pointsValue) {
        this.pointsValue = pointsValue;
    }
    
    /**
     * Check if the achievement is unlocked.
     */
    public boolean isUnlocked() {
        return unlocked;
    }
    
    /**
     * Set whether the achievement is unlocked.
     */
    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
        if (unlocked && dateUnlocked == 0) {
            this.dateUnlocked = System.currentTimeMillis();
        }
    }
    
    /**
     * Get the date the achievement was unlocked.
     */
    public long getDateUnlocked() {
        return dateUnlocked;
    }
    
    /**
     * Set the date the achievement was unlocked.
     */
    public void setDateUnlocked(long dateUnlocked) {
        this.dateUnlocked = dateUnlocked;
    }
    
    /**
     * Get the current progress towards the achievement.
     */
    public int getProgressCurrent() {
        return progressCurrent;
    }
    
    /**
     * Set the current progress towards the achievement.
     */
    public void setProgressCurrent(int progressCurrent) {
        this.progressCurrent = progressCurrent;
        
        // Check if the achievement should be unlocked
        if (progressCurrent >= progressTarget && !unlocked) {
            setUnlocked(true);
        }
    }
    
    /**
     * Increment the progress by a specified amount.
     * 
     * @param increment The amount to increment by
     * @return true if the achievement was unlocked by this update, false otherwise
     */
    public boolean incrementProgress(int increment) {
        boolean wasUnlocked = unlocked;
        setProgressCurrent(progressCurrent + increment);
        return !wasUnlocked && unlocked;
    }
    
    /**
     * Get the target progress for the achievement.
     */
    public int getProgressTarget() {
        return progressTarget;
    }
    
    /**
     * Set the target progress for the achievement.
     */
    public void setProgressTarget(int progressTarget) {
        this.progressTarget = progressTarget;
    }
    
    /**
     * Check if the achievement is secret (not shown until unlocked).
     */
    public boolean isSecret() {
        return secret;
    }
    
    /**
     * Set whether the achievement is secret.
     */
    public void setSecret(boolean secret) {
        this.secret = secret;
    }
    
    /**
     * Get the progress as a percentage (0-100).
     */
    public int getProgressPercentage() {
        if (progressTarget <= 0) {
            return 0;
        }
        return Math.min(100, (progressCurrent * 100) / progressTarget);
    }
}