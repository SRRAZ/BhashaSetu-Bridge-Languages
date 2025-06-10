package com.bhashasetu.app.model.gamification;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.bhashasetu.app.database.converters.BadgeTierConverter;
import com.bhashasetu.app.database.converters.DateConverter;

import java.util.Date;

/**
 * Entity representing a user badge (visual representation of achievements)
 */
@Entity(tableName = "badges")
@TypeConverters({DateConverter.class, BadgeTierConverter.class})
public class Badge {
    
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    private String name;
    private String description;
    private BadgeTier tier;
    private String imagePath;
    private boolean earned;
    private Date earnedAt;
    private int achievementId;  // Link to the achievement that unlocked this badge
    
    public Badge() {
        this.earned = false;
    }
    
    public Badge(String name, String description, BadgeTier tier, String imagePath, int achievementId) {
        this.name = name;
        this.description = description;
        this.tier = tier;
        this.imagePath = imagePath;
        this.earned = false;
        this.achievementId = achievementId;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public BadgeTier getTier() {
        return tier;
    }
    
    public void setTier(BadgeTier tier) {
        this.tier = tier;
    }
    
    public String getImagePath() {
        return imagePath;
    }
    
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    
    public boolean isEarned() {
        return earned;
    }
    
    public void setEarned(boolean earned) {
        this.earned = earned;
        if (earned && earnedAt == null) {
            this.earnedAt = new Date();
        }
    }
    
    public Date getEarnedAt() {
        return earnedAt;
    }
    
    public void setEarnedAt(Date earnedAt) {
        this.earnedAt = earnedAt;
    }
    
    public int getAchievementId() {
        return achievementId;
    }
    
    public void setAchievementId(int achievementId) {
        this.achievementId = achievementId;
    }
    
    /**
     * Get the color resource ID for this badge's tier
     * @return resource ID for the badge tier color
     */
    public int getTierColorResourceId() {
        return tier.getColorResourceId();
    }
}