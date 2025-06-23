package com.bhashasetu.app.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "word_categories")
public class WordCategory {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;
    private String description;
    private String iconUrl;
    private int order;
    private boolean isActive;
    private long createdAt;
    private long updatedAt;
    private String color; // Hex color code for UI
    private int totalWords;

    // Default constructor (Room will use this)
    public WordCategory() {
    }

    // Mark other constructors with @Ignore to resolve Room warning
    @Ignore
    public WordCategory(String name, String description, String color) {
        this.name = name;
        this.description = description;
        this.isActive = true;
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
        this.color = color;
        this.totalWords = 0;
    }

    @Ignore
    public WordCategory(long id, String name, String description, String iconUrl,
                        int order, boolean isActive, long createdAt, long updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.iconUrl = iconUrl;
        this.order = order;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public long getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getIconUrl() { return iconUrl; }
    public void setIconUrl(String iconUrl) { this.iconUrl = iconUrl; }

    public int getOrder() { return order; }
    public void setOrder(int order) { this.order = order; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public int getTotalWords() { return totalWords; }
    public void setTotalWords(int totalWords) { this.totalWords = totalWords; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }

    public long getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(long updatedAt) { this.updatedAt = updatedAt; }
}
