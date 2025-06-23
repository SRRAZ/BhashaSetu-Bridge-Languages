package com.bhashasetu.app.model;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;
import java.util.Date;
import androidx.room.Ignore;

/**
 * Model class for legacy user achievements in the app.
 * Achievements are awarded for various learning milestones.
 */
@Entity(tableName = "legacy_achievements")
public class Achievement {

    // Achievement types
    public static final String TYPE_VOCABULARY = "vocabulary";
    public static final String TYPE_PRONUNCIATION = "pronunciation";
    public static final String TYPE_STREAK = "streak";
    public static final String TYPE_GAME = "game";
    public static final String TYPE_MASTERY = "mastery";

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String title;
    private String description;
    private String type;
    private int progressTarget;
    private int currentProgress;
    private boolean unlocked;
    private Long dateUnlocked;
    private int pointsValue;
    private int iconResId;

    // ✅ DEFAULT CONSTRUCTOR (Room uses this)
    public Achievement() {
        this.unlocked = false;
        this.currentProgress = 0;
    }

    // ✅ PARAMETERIZED CONSTRUCTORS (Add @Ignore to all)
    @Ignore
    public Achievement(String title, String description, String type, int progressTarget, int pointsValue, int iconResId) {
        this();
        this.title = title;
        this.description = description;
        this.type = type;
        this.progressTarget = progressTarget;
        this.pointsValue = pointsValue;
        this.iconResId = iconResId;
    }

    @Ignore
    public Achievement(String title, String description, String type, int progressTarget, int currentProgress,
                       boolean unlocked, Long dateUnlocked, int pointsValue, int iconResId) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.progressTarget = progressTarget;
        this.currentProgress = currentProgress;
        this.unlocked = unlocked;
        this.dateUnlocked = dateUnlocked;
        this.pointsValue = pointsValue;
        this.iconResId = iconResId;
    }

    // ✅ GETTERS AND SETTERS (ensure all fields have them)
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getProgressTarget() { return progressTarget; }
    public void setProgressTarget(int progressTarget) { this.progressTarget = progressTarget; }

    public int getCurrentProgress() { return currentProgress; }
    public void setCurrentProgress(int currentProgress) { this.currentProgress = currentProgress; }

    public boolean isUnlocked() { return unlocked; }
    public void setUnlocked(boolean unlocked) { this.unlocked = unlocked; }

    public Long getDateUnlocked() { return dateUnlocked; }
    public void setDateUnlocked(Long dateUnlocked) { this.dateUnlocked = dateUnlocked; }

    public int getPointsValue() { return pointsValue; }
    public void setPointsValue(int pointsValue) { this.pointsValue = pointsValue; }

    public int getIconResId() { return iconResId; }
    public void setIconResId(int iconResId) { this.iconResId = iconResId; }

    // ✅ UTILITY METHODS (Add @Ignore if they don't represent database fields)
    @Ignore
    public boolean isCompleted() {
        return currentProgress >= progressTarget;
    }

    @Ignore
    public double getProgressPercentage() {
        return progressTarget == 0 ? 0 : (double) currentProgress / progressTarget * 100;
    }

    @Ignore
    public String getFormattedProgress() {
        return currentProgress + "/" + progressTarget;
    }
}