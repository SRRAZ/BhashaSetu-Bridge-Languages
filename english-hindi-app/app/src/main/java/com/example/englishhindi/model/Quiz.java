package com.example.englishhindi.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "quizzes",
        indices = {@Index("lessonId")},
        foreignKeys = @ForeignKey(entity = Lesson.class,
                                parentColumns = "id",
                                childColumns = "lessonId",
                                onDelete = ForeignKey.CASCADE))
public class Quiz {
    
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    private int lessonId;
    private String question;
    private String questionHindi;
    private String correctAnswer;
    private String wrongAnswer1;
    private String wrongAnswer2;
    private String wrongAnswer3;
    private String explanation;
    private String explanationHindi;
    private String type; // multiple_choice, fill_blank, matching
    private int difficulty; // 1-5
    private String mediaUrl; // URL to associated media (image, audio)
    
    public Quiz(int lessonId, String question, String questionHindi, String correctAnswer,
               String wrongAnswer1, String wrongAnswer2, String wrongAnswer3,
               String explanation, String explanationHindi, String type, int difficulty) {
        this.lessonId = lessonId;
        this.question = question;
        this.questionHindi = questionHindi;
        this.correctAnswer = correctAnswer;
        this.wrongAnswer1 = wrongAnswer1;
        this.wrongAnswer2 = wrongAnswer2;
        this.wrongAnswer3 = wrongAnswer3;
        this.explanation = explanation;
        this.explanationHindi = explanationHindi;
        this.type = type;
        this.difficulty = difficulty;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getLessonId() {
        return lessonId;
    }
    
    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }
    
    public String getQuestion() {
        return question;
    }
    
    public void setQuestion(String question) {
        this.question = question;
    }
    
    public String getQuestionHindi() {
        return questionHindi;
    }
    
    public void setQuestionHindi(String questionHindi) {
        this.questionHindi = questionHindi;
    }
    
    public String getCorrectAnswer() {
        return correctAnswer;
    }
    
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
    
    public String getWrongAnswer1() {
        return wrongAnswer1;
    }
    
    public void setWrongAnswer1(String wrongAnswer1) {
        this.wrongAnswer1 = wrongAnswer1;
    }
    
    public String getWrongAnswer2() {
        return wrongAnswer2;
    }
    
    public void setWrongAnswer2(String wrongAnswer2) {
        this.wrongAnswer2 = wrongAnswer2;
    }
    
    public String getWrongAnswer3() {
        return wrongAnswer3;
    }
    
    public void setWrongAnswer3(String wrongAnswer3) {
        this.wrongAnswer3 = wrongAnswer3;
    }
    
    public String getExplanation() {
        return explanation;
    }
    
    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
    
    public String getExplanationHindi() {
        return explanationHindi;
    }
    
    public void setExplanationHindi(String explanationHindi) {
        this.explanationHindi = explanationHindi;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public int getDifficulty() {
        return difficulty;
    }
    
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
    
    public String getMediaUrl() {
        return mediaUrl;
    }
    
    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }
}