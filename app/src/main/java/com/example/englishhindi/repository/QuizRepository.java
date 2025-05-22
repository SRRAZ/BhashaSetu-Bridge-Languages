package com.example.englishhindi.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.englishhindi.database.QuizDao;
import com.example.englishhindi.database.WordDatabase;
import com.example.englishhindi.model.Quiz;

import java.util.List;

public class QuizRepository {
    
    private QuizDao quizDao;
    
    public QuizRepository(Application application) {
        WordDatabase database = WordDatabase.getInstance(application);
        quizDao = database.quizDao();
    }
    
    // Insert a new quiz
    public void insert(Quiz quiz) {
        new InsertQuizAsyncTask(quizDao).execute(quiz);
    }
    
    // Update an existing quiz
    public void update(Quiz quiz) {
        new UpdateQuizAsyncTask(quizDao).execute(quiz);
    }
    
    // Delete a quiz
    public void delete(Quiz quiz) {
        new DeleteQuizAsyncTask(quizDao).execute(quiz);
    }
    
    // Get quizzes for a specific lesson
    public LiveData<List<Quiz>> getQuizzesByLesson(int lessonId) {
        return quizDao.getQuizzesByLesson(lessonId);
    }
    
    // Get random quizzes
    public LiveData<List<Quiz>> getRandomQuizzes(int limit) {
        return quizDao.getRandomQuizzes(limit);
    }
    
    // Get quizzes by difficulty
    public LiveData<List<Quiz>> getQuizzesByDifficulty(int maxDifficulty, int limit) {
        return quizDao.getQuizzesByDifficulty(maxDifficulty, limit);
    }
    
    // Get a specific quiz by ID
    public LiveData<Quiz> getQuizById(int quizId) {
        return quizDao.getQuizById(quizId);
    }
    
    // Get quiz count for a lesson
    public int getQuizCountForLesson(int lessonId) {
        return quizDao.getQuizCountForLesson(lessonId);
    }
    
    // Get quizzes by type
    public LiveData<List<Quiz>> getQuizzesByType(String quizType, int limit) {
        return quizDao.getQuizzesByType(quizType, limit);
    }
    
    // Insert quiz with details
    public void insertQuizDetails(int lessonId, String question, String questionHindi, 
                                 String correctAnswer, String wrongAnswer1, 
                                 String wrongAnswer2, String wrongAnswer3,
                                 String explanation, String explanationHindi, 
                                 String type, int difficulty) {
        new InsertQuizDetailsAsyncTask(quizDao, lessonId, question, questionHindi,
                                       correctAnswer, wrongAnswer1, wrongAnswer2, wrongAnswer3,
                                       explanation, explanationHindi, type, difficulty).execute();
    }
    
    // AsyncTask classes for database operations
    private static class InsertQuizAsyncTask extends AsyncTask<Quiz, Void, Void> {
        private QuizDao quizDao;
        
        private InsertQuizAsyncTask(QuizDao quizDao) {
            this.quizDao = quizDao;
        }
        
        @Override
        protected Void doInBackground(Quiz... quizzes) {
            quizDao.insert(quizzes[0]);
            return null;
        }
    }
    
    private static class UpdateQuizAsyncTask extends AsyncTask<Quiz, Void, Void> {
        private QuizDao quizDao;
        
        private UpdateQuizAsyncTask(QuizDao quizDao) {
            this.quizDao = quizDao;
        }
        
        @Override
        protected Void doInBackground(Quiz... quizzes) {
            quizDao.update(quizzes[0]);
            return null;
        }
    }
    
    private static class DeleteQuizAsyncTask extends AsyncTask<Quiz, Void, Void> {
        private QuizDao quizDao;
        
        private DeleteQuizAsyncTask(QuizDao quizDao) {
            this.quizDao = quizDao;
        }
        
        @Override
        protected Void doInBackground(Quiz... quizzes) {
            quizDao.delete(quizzes[0]);
            return null;
        }
    }
    
    private static class InsertQuizDetailsAsyncTask extends AsyncTask<Void, Void, Void> {
        private QuizDao quizDao;
        private int lessonId;
        private String question;
        private String questionHindi;
        private String correctAnswer;
        private String wrongAnswer1;
        private String wrongAnswer2;
        private String wrongAnswer3;
        private String explanation;
        private String explanationHindi;
        private String type;
        private int difficulty;
        
        private InsertQuizDetailsAsyncTask(QuizDao quizDao, int lessonId, String question, 
                                          String questionHindi, String correctAnswer, 
                                          String wrongAnswer1, String wrongAnswer2, 
                                          String wrongAnswer3, String explanation, 
                                          String explanationHindi, String type, int difficulty) {
            this.quizDao = quizDao;
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
        
        @Override
        protected Void doInBackground(Void... voids) {
            quizDao.insertQuizDetails(lessonId, question, questionHindi, correctAnswer,
                                     wrongAnswer1, wrongAnswer2, wrongAnswer3,
                                     explanation, explanationHindi, type, difficulty);
            return null;
        }
    }
}