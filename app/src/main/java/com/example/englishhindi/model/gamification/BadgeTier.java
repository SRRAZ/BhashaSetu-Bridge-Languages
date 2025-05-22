package com.example.englishhindi.model.gamification;

import android.graphics.Color;

/**
 * Tiers for badges, representing different levels of achievement
 */
public enum BadgeTier {
    BRONZE("Bronze", Color.parseColor("#CD7F32")),
    SILVER("Silver", Color.parseColor("#C0C0C0")),
    GOLD("Gold", Color.parseColor("#FFD700")),
    PLATINUM("Platinum", Color.parseColor("#E5E4E2")),
    DIAMOND("Diamond", Color.parseColor("#B9F2FF"));
    
    private final String displayName;
    private final int colorValue;
    
    BadgeTier(String displayName, int colorValue) {
        this.displayName = displayName;
        this.colorValue = colorValue;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public int getColorValue() {
        return colorValue;
    }
    
    /**
     * Get the Android resource ID for this tier's color
     * @return resource ID
     */
    public int getColorResourceId() {
        // In a real app, this would return an actual resource ID
        // For now, we're returning 0 as a placeholder
        return 0;
    }
    
    @Override
    public String toString() {
        return displayName;
    }
}