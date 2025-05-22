package com.example.englishhindi.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "practice_sessions")
public class PracticeSession {
    
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    private int userId;
    private String sessionType; // flashcard, quiz, spelling, pronunciation
    private long startTime;
    private long endTime;
    private int totalItems;
    private int correctAnswers;
    private int deckId; // if using a flashcard deck
    private int lessonId; // if practicing from a lesson
    private int score;
    private boolean isCompleted;
    
    @Ignore
    private List<Word> words;
    
    @Ignore
    private List<Quiz> quizzes;
    
    @Ignore
    private int currentIndex;
    
    public PracticeSession(int userId, String sessionType) {
        this.userId = userId;
        this.sessionType = sessionType;
        this.startTime = System.currentTimeMillis();
        this.endTime = 0;
        this.totalItems = 0;
        this.correctAnswers = 0;
        this.score = 0;
        this.isCompleted = false;
        this.currentIndex = 0;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getSessionType() {
        return sessionType;
    }
    
    public void setSessionType(String sessionType) {
        this.sessionType = sessionType;
    }
    
    public long getStartTime() {
        return startTime;
    }
    
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
    
    public long getEndTime() {
        return endTime;
    }
    
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
    
    public int getTotalItems() {
        return totalItems;
    }
    
    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }
    
    public int getCorrectAnswers() {
        return correctAnswers;
    }
    
    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }
    
    public int getDeckId() {
        return deckId;
    }
    
    public void setDeckId(int deckId) {
        this.deckId = deckId;
    }
    
    public int getLessonId() {
        return lessonId;
    }
    
    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }
    
    public int getScore() {
        return score;
    }
    
    public void setScore(int score) {
        this.score = score;
    }
    
    public boolean isCompleted() {
        return isCompleted;
    }
    
    public void setCompleted(boolean completed) {
        isCompleted = completed;
        if (completed) {
            endTime = System.currentTimeMillis();
        }
    }
    
    public List<Word> getWords() {
        if (words == null) {
            words = new ArrayList<>();
        }
        return words;
    }
    
    public void setWords(List<Word> words) {
        this.words = words;
        this.totalItems = words != null ? words.size() : 0;
    }
    
    public List<Quiz> getQuizzes() {
        if (quizzes == null) {
            quizzes = new ArrayList<>();
        }
        return quizzes;
    }
    
    public void setQuizzes(List<Quiz> quizzes) {
        this.quizzes = quizzes;
        this.totalItems = quizzes != null ? quizzes.size() : 0;
    }
    
    public int getCurrentIndex() {
        return currentIndex;
    }
    
    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }
    
    public Word getCurrentWord() {
        if (words != null && currentIndex >= 0 && currentIndex < words.size()) {
            return words.get(currentIndex);
        }
        return null;
    }
    
    public Quiz getCurrentQuiz() {
        if (quizzes != null && currentIndex >= 0 && currentIndex < quizzes.size()) {
            return quizzes.get(currentIndex);
        }
        return null;
    }
    
    public boolean moveToNext() {
        currentIndex++;
        if (sessionType.equals("flashcard") || sessionType.equals("pronunciation")) {
            return currentIndex < getWords().size();
        } else {
            return currentIndex < getQuizzes().size();
        }
    }
    
    public boolean moveToPrevious() {
        if (currentIndex > 0) {
            currentIndex--;
            return true;
        }
        return false;
    }
    
    public void recordAnswer(boolean isCorrect) {
        if (isCorrect) {
            correctAnswers++;
        }
    }
    
    public float getAccuracy() {
        if (totalItems == 0) {
            return 0;
        }
        return ((float) correctAnswers / totalItems) * 100;
    }
    
    public long getDuration() {
        if (endTime == 0) {
            return System.currentTimeMillis() - startTime;
        }
        return endTime - startTime;
    }
    
    public String getDurationFormatted() {
        long durationMs = getDuration();
        long seconds = (durationMs / 1000) % 60;
        long minutes = (durationMs / (1000 * 60)) % 60;
        long hours = (durationMs / (1000 * 60 * 60)) % 24;
        
        if (hours > 0) {
            return String.format("%d:%02d:%02d", hours, minutes, seconds);
        } else {
            return String.format("%d:%02d", minutes, seconds);
        }
    }
}