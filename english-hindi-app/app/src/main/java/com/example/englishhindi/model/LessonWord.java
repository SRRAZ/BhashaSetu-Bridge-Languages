package com.example.englishhindi.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "lesson_words",
        primaryKeys = {"lessonId", "wordId"},
        foreignKeys = {
            @ForeignKey(entity = Lesson.class,
                        parentColumns = "id",
                        childColumns = "lessonId",
                        onDelete = ForeignKey.CASCADE),
            @ForeignKey(entity = Word.class,
                        parentColumns = "id",
                        childColumns = "wordId",
                        onDelete = ForeignKey.CASCADE)
        },
        indices = {
            @Index("lessonId"),
            @Index("wordId")
        })
public class LessonWord {
    
    private int lessonId;
    private int wordId;
    private int order;
    private String notes;
    
    public LessonWord(int lessonId, int wordId, int order, String notes) {
        this.lessonId = lessonId;
        this.wordId = wordId;
        this.order = order;
        this.notes = notes;
    }
    
    // Getters and Setters
    public int getLessonId() {
        return lessonId;
    }
    
    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
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