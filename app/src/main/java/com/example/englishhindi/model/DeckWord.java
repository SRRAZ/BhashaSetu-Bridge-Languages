package com.bhashasetu.app.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "deck_words",
        primaryKeys = {"deckId", "wordId"},
        foreignKeys = {
            @ForeignKey(entity = FlashcardDeck.class,
                        parentColumns = "id",
                        childColumns = "wordId",
                        onDelete = ForeignKey.CASCADE)
 ,            @ForeignKey(entity = Word.class,
 parentColumns = "id",
 childColumns = "wordId",
 onDelete = ForeignKey.CASCADE)
        },
        indices = {
            @Index("deckId"),
            @Index("wordId")
        })
public class DeckWord {
    
    private int deckId;
    private int wordId;
    private int order;
    private String notes;
    
    public DeckWord(int deckId, int wordId, int order, String notes) {
        this.deckId = deckId;
        this.wordId = wordId;
        this.order = order;
        this.notes = notes;
    }
    
    // Getters and Setters
    public int getDeckId() {
        return deckId;
    }
    
    public void setDeckId(int deckId) {
        this.deckId = deckId;
    }
    
    public int getWordId() {
        return wordId;
    }
    
    public void setWordId(int wordId) {
        this.wordId = wordId;
    }
    
    public int getOrder() {
        return order;
    }
    
    public void setOrder(int order) {
        this.order = order;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
}