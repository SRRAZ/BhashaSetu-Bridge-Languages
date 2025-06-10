package com.bhashasetu.app.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "lessons")
public class Lesson {
    
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    private String title;
    private String titleHindi;
    private String description;
    private String descriptionHindi;
    private String category;
    private String level;
    private int orderInCategory;
    private String content;
    private String contentHindi;
    private String imageUrl;
    private boolean hasCompleted;
    
    public Lesson(String title, String titleHindi, String description, String descriptionHindi, 
                  String category, String level, int orderInCategory, String content, 
                  String contentHindi, String imageUrl) {
        this.title = title;
        this.titleHindi = titleHindi;
        this.description = description;
        this.descriptionHindi = descriptionHindi;
        this.category = category;
        this.level = level;
        this.orderInCategory = orderInCategory;
        this.content = content;
        this.contentHindi = contentHindi;
        this.imageUrl = imageUrl;
        this.hasCompleted = false;
    }
    
    // Getters and Setters
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
    
    public void setDescriptionHindi(String descriptionHindi) {
        this.descriptionHindi = descriptionHindi;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
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