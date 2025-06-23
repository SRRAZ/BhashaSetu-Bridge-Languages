package com.bhashasetu.app.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "lessons",
        indices = {
                @Index(value = "categoryId"),
                @Index(value = "order"),
                @Index(value = {"categoryId","order"})
        },
        foreignKeys = @ForeignKey(
                entity = WordCategory.class,
                parentColumns = "id",
                childColumns = "categoryId",
                onDelete = ForeignKey.CASCADE
        ))

public class Lesson {
    @PrimaryKey(autoGenerate = true)
    private long id;
    
    private String title;
    private String titleHindi;
    private String description;
    private String descriptionHindi;
    private long categoryId;
    private int order;
    private int difficultyLevel;
    private String objectives;
    private int estimatedDuration; // in minutes
    private boolean isActive;
    private boolean isLocked;
    private String thumbnailUrl;
    private long createdAt;
    private long updatedAt;
    private int totalWords;
    private String languagePair; // e.g., "en-hi" for English to Hindi

    // More fields as needed
    private String videoUrl; // For video lessons (Later to be used for audio lessons)
    private String category;
    private String level;
    private int orderInCategory;
    private String content;
    private String contentHindi;
    private String imageUrl;
    private boolean hasCompleted;

    // Default constructor (Room will use this)
    public Lesson() {
    }

    // Mark other constructors with @Ignore to resolve Room warning
    @Ignore
    public Lesson(String title, String titleHindi, String description, String descriptionHindi, 
                  String category, String level, int orderInCategory, String content, 
                  String contentHindi, String imageUrl, long categoryId) {
        this.title = title;
        this.titleHindi = titleHindi;
        this.description = description;
        this.descriptionHindi = descriptionHindi;
        this.categoryId = categoryId;
        this.isActive = true;
        this.isLocked = false;
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
        this.difficultyLevel = 1;
        this.estimatedDuration = 15;
        this.category = category;
        this.level = level;
        this.orderInCategory = orderInCategory;
        this.content = content;
        this.contentHindi = contentHindi;
        this.imageUrl = imageUrl;
        this.hasCompleted = false;
    }

    @Ignore
    public Lesson(String title, String description, long categoryId, int order,
                  int difficultyLevel, String objectives) {
        this.title = title;
        this.description = description;
        this.categoryId = categoryId;
        this.order = order;
        this.difficultyLevel = difficultyLevel;
        this.objectives = objectives;
        this.isActive = true;
        this.isLocked = false;
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
        this.estimatedDuration = 15;
    }
    
    // Getters and Setters
    public long getId() {return id;}
    public void setId(int id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getTitleHindi() {
        return titleHindi;
    }
    public void setTitleHindi(String titleHindi) {
        this.titleHindi = titleHindi;
    }
    
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescriptionHindi() {
        return descriptionHindi;
    }
    public void setDescriptionHindi(String descriptionHindi) {this.descriptionHindi = descriptionHindi;}

    public long getCategoryId() { return categoryId; }
    public void setCategoryId(long categoryId) { this.categoryId = categoryId; }

    public int getOrder() { return order; }
    public void setOrder(int order) { this.order = order; }

    public int getDifficultyLevel() { return difficultyLevel; }
    public void setDifficultyLevel(int difficultyLevel) { this.difficultyLevel = difficultyLevel; }

    public String getObjectives() { return objectives; }
    public void setObjectives(String objectives) { this.objectives = objectives; }

    public int getEstimatedDuration() { return estimatedDuration; }
    public void setEstimatedDuration(int estimatedDuration) { this.estimatedDuration = estimatedDuration; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public boolean isLocked() { return isLocked; }
    public void setLocked(boolean locked) { isLocked = locked; }

    public String getThumbnailUrl() { return thumbnailUrl; }
    public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }

    public long getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(long updatedAt) { this.updatedAt = updatedAt; }

    public int getTotalWords() { return totalWords; }
    public void setTotalWords(int totalWords) { this.totalWords = totalWords; }

    public String getLanguagePair() { return languagePair; }
    public void setLanguagePair(String languagePair) { this.languagePair = languagePair; }

    public String getVideoUrl() {return videoUrl; }
    public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public String getLevel() {
        return level;
    }
    public void setLevel(String level) {
        this.level = level;
    }
    
    public int getOrderInCategory() {
        return orderInCategory;
    }
    public void setOrderInCategory(int orderInCategory) {
        this.orderInCategory = orderInCategory;
    }
    
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getContentHindi() {
        return contentHindi;
    }
    public void setContentHindi(String contentHindi) {
        this.contentHindi = contentHindi;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public boolean isHasCompleted() {
        return hasCompleted;
    }
    public void setHasCompleted(boolean hasCompleted) {
        this.hasCompleted = hasCompleted;
    }
}