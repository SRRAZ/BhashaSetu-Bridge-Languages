package com.bhashasetu.app.model.exercise;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.bhashasetu.app.database.converters.DateConverter;
import com.bhashasetu.app.database.converters.ExerciseTypeConverter;

import java.util.Date;

/**
 * Base entity class for all exercises in the app
 */
@Entity(tableName = "exercises")
@TypeConverters({DateConverter.class, ExerciseTypeConverter.class})
public class Exercise {
    
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    private String title;
    private String description;
    private ExerciseType type;
    private int difficulty;
    private int wordCount;
    private int points;
    private boolean isCompleted;
    private Date createdAt;
    private Date completedAt;
    private int correctAnswers;
    private int totalQuestions;
    private String lessonId;
    private String category;
    

    public Exercise(String title, String description, ExerciseType type, int difficulty,
                    int wordCount, int points, boolean isCompleted, Date createdAt,
                    Date completedAt, int correctAnswers, int totalQuestions,
                    String lessonId, String category) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.difficulty = difficulty;
        this.wordCount = wordCount;
        this.points = points;
        this.isCompleted = isCompleted;
        this.createdAt = createdAt;
        this.completedAt = completedAt;
        this.correctAnswers = correctAnswers;
        this.totalQuestions = totalQuestions;
        this.lessonId = lessonId;
        this.category = category;
    }

    // --- GETTERS AND SETTERS ---
    // (It's good practice to have getters for all fields Room needs to read,
    // and setters if you need to modify them after creation or if Room needs them)

    public int getId() {
        return id;
    }
    
    public void setId(int id) { // Setter for id is needed if Room is to set it (though autoGenerate handles new ones)
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public ExerciseType getType() {
        return type;
    }
    
    public void setType(ExerciseType type) {
        this.type = type;
    }
    
    public int getDifficulty() {
        return difficulty;
    }
    
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
    
    public int getWordCount() {
        return wordCount;
    }
    
    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }
    
    public int getPoints() {
        return points;
    }
    
    public void setPoints(int points) {
        this.points = points;
    }
    
    public boolean isCompleted() {
        return isCompleted;
    }
    
    public void setCompleted(boolean completed) {
        isCompleted = completed;
        if (completed) {
            // Consider if completedAt should only be set here if it's null
            // If it's passed in the constructor, this might overwrite it.
            // If the constructor always provides a completedAt (even if null),
            // then this logic might only be for when setCompleted is called post-construction.
            if (this.completedAt == null) {
                this.completedAt = new Date();
            }
        }
    }
    
    public Date getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    
    public Date getCompletedAt() {
        return completedAt;
    }
    
    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }
    
    public int getCorrectAnswers() {
        return correctAnswers;
    }
    
    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }
    
    public int getTotalQuestions() {
        return totalQuestions;
    }
    
    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }
    
    public String getLessonId() {
        return lessonId;
    }
    
    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }

    // --- Other methods from your original class ---

    /**
     * Calculate score as a percentage
     * @return percentage score (0-100)
     */
    public int getScorePercentage() {
        if (totalQuestions == 0) {
            return 0;
        }
        return (int) (((float) correctAnswers / totalQuestions) * 100);
    }
    
    /**
     * Calculate earned points based on completion percentage
     * @return actual points earned
     */
    public int getEarnedPoints() {
        return (int) (points * (getScorePercentage() / 100.0f));
    }
    
    /**
     * Record a question answer
     * @param isCorrect whether the answer was correct
     */
    public void recordAnswer(boolean isCorrect) {
        totalQuestions++;
        if (isCorrect) {
            correctAnswers++;
        }
    }
    
    /**
     * Mark the exercise as completed and calculate final score
     */
    public void complete() {
        setCompleted(true);
    }
    
    @NonNull
    @Override
    public String toString() {
        return "Exercise{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", difficulty=" + difficulty +
                ", completed=" + isCompleted +
                ", score=" + getScorePercentage() + "%" +
                '}';
    }
}