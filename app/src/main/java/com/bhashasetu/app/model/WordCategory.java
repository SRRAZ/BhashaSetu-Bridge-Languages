package com.bhashasetu.app.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "word_categories")
public class WordCategory {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String description;
    private String color; // Hex color code for UI
    private int totalWords;
    private boolean isActive;

    // Constructors
    public WordCategory() {}

    public WordCategory(String name, String description, String color) {
        this.name = name;
        this.description = description;
        this.color = color;
        this.totalWords = 0;
        this.isActive = true;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public int getTotalWords() { return totalWords; }
    public void setTotalWords(int totalWords) { this.totalWords = totalWords; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
}
