package com.bhashasetu.app.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "flashcard_decks")
public class FlashcardDeck {
    
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    private String name;
    private String nameHindi;
    private String description;
    private String descriptionHindi;
    private String category;
    private int difficulty; // 1-5
    private boolean isFavorite;
    private long createdDate;
    private long lastPracticed;
    
    @Ignore
    private List<Word> words = new ArrayList<>();
    
    public FlashcardDeck(String name, String nameHindi, String description, String descriptionHindi, 
                        String category, int difficulty) {
        this.name = name;
        this.nameHindi = nameHindi;
        this.description = description;
        this.descriptionHindi = descriptionHindi;
        this.category = category;
        this.difficulty = difficulty;
        this.isFavorite = false;
        this.createdDate = System.currentTimeMillis();
        this.lastPracticed = 0;
    }
    
    // Getters and Setters
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
    
    public String getNameHindi() {
        return nameHindi;
    }
    
    public void setNameHindi(String nameHindi) {
        this.nameHindi = nameHindi;
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
    
    public int getDifficulty() {
        return difficulty;
    }
    
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
    
    public boolean isFavorite() {
        return isFavorite;
    }
    
    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
    
    public long getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }
    
    public long getLastPracticed() {
        return lastPracticed;
    }
    
    public void setLastPracticed(long lastPracticed) {
        this.lastPracticed = lastPracticed;
    }
    
    public List<Word> getWords() {
        return words;
    }
    
    public void setWords(List<Word> words) {
        this.words = words;
    }
    
    public void addWord(Word word) {
        if (words == null) {
            words = new ArrayList<>();
        }
        words.add(word);
    }
    
    public int getWordCount() {
        return words != null ? words.size() : 0;
    }
    
    public int getCompletedCount(List<UserProgress> progressList) {
        if (words == null || progressList == null) {
            return 0;
        }
        
        int count = 0;
        for (Word word : words) {
            for (UserProgress progress : progressList) {
                if (progress.getItemType().equals("word") && 
                    progress.getItemId() == word.getId() &&
                    progress.getCompletionLevel() >= 80) {
                    count++;
                    break;
                }
            }
        }
        
        return count;
    }
}