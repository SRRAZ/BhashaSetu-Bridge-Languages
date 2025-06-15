package com.bhashasetu.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bhashasetu.app.model.Quiz;
import com.bhashasetu.app.repository.QuizRepository;

import java.util.List;

public class QuizViewModel extends AndroidViewModel {
    
    private QuizRepository repository;
    
    public QuizViewModel(@NonNull Application application) {
        super(application);
        repository = new QuizRepository(application);
    }
    
    // Insert a new quiz
    public void insert(Quiz quiz) {
        repository.insert(quiz);
    }
    
    // Update an existing quiz
    public void update(Quiz quiz) {
        repository.update(quiz);
    }
    
    // Delete a quiz
    public void delete(Quiz quiz) {
        repository.delete(quiz);
    }
    
    // Get quizzes for a specific lesson
    public LiveData<List<Quiz>> getQuizzesByLesson(int lessonId) {
        return repository.getQuizzesByLesson(lessonId);
    }
    
    // Get random quizzes
    public LiveData<List<Quiz>> getRandomQuizzes(int limit) {
        return repository.getRandomQuizzes(limit);
    }
    
    // Get quizzes by difficulty
    public LiveData<List<Quiz>> getQuizzesByDifficulty(int maxDifficulty, int limit) {
        return repository.getQuizzesByDifficulty(maxDifficulty, limit);
    }
    
    // Get a specific quiz by ID
    public LiveData<Quiz> getQuizById(int quizId) {
        return repository.getQuizById(quizId);
    }
    
    // Get quiz count for a lesson
    public int getQuizCountForLesson(int lessonId) {
        return repository.getQuizCountForLesson(lessonId);
    }
    
    // Get quizzes by type
    public LiveData<List<Quiz>> getQuizzesByType(String quizType, int limit) {
        return repository.getQuizzesByType(quizType, limit);
    }
    
    // Insert quiz with details
    public void insertQuizDetails(int lessonId, String question, String questionHindi, 
                                 String correctAnswer, String wrongAnswer1, 
                                 String wrongAnswer2, String wrongAnswer3,
                                 String explanation, String explanationHindi, 
                                 String type, int difficulty) {
        repository.insertQuizDetails(lessonId, question, questionHindi, correctAnswer,
                                    wrongAnswer1, wrongAnswer2, wrongAnswer3,
                                    explanation, explanationHindi, type, difficulty);
    }
}