package com.bhashasetu.app.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import com.bhashasetu.app.database.LessonDao;
import com.bhashasetu.app.database.WordDao;
import com.bhashasetu.app.data.relation.LessonWithWords;
import com.bhashasetu.app.model.Lesson;
import com.bhashasetu.app.model.Word;
import com.bhashasetu.app.model.LessonWord;
import javax.inject.Inject;
import javax.inject.Singleton;
import com.bhashasetu.app.database.WordDatabase;

import java.util.List;

public class LessonRepository {
    
    private LessonDao lessonDao;
    private LiveData<List<Lesson>> allLessons;
    
    public LessonRepository(Application application) {
        WordDatabase database = WordDatabase.getInstance(application);
        lessonDao = database.lessonDao();
        allLessons = lessonDao.getAllLessons();
    }
    
    // Methods for Lesson operations
    public void insert(Lesson lesson) {
        new InsertLessonAsyncTask(lessonDao).execute(lesson);
    }
    
    public void update(Lesson lesson) {
        new UpdateLessonAsyncTask(lessonDao).execute(lesson);
    }
    
    public void delete(Lesson lesson) {
        new DeleteLessonAsyncTask(lessonDao).execute(lesson);
    }
    
    public LiveData<List<Lesson>> getAllLessons() {
        return allLessons;
    }
    
    public LiveData<List<Lesson>> getLessonsByCategory(String category) {
        return lessonDao.getLessonsByCategory(category);
    }
    
    public LiveData<List<Lesson>> getLessonsByLevel(String level) {
        return lessonDao.getLessonsByLevel(level);
    }
    
    public LiveData<Lesson> getLessonById(int id) {
        return lessonDao.getLessonById(id);
    }
    
    public LiveData<Lesson> getNextIncompleteLesson() {
        return lessonDao.getNextIncompleteLesson();
    }
    
    public LiveData<List<String>> getAllCategories() {
        return lessonDao.getAllCategories();
    }
    
    public LiveData<List<Lesson>> getLessonsContainingWord(int wordId) {
        return lessonDao.getLessonsContainingWord(wordId);
    }
    
    // Methods for LessonWord junction operations
    public void addWordToLesson(int lessonId, int wordId, int order, String notes) {
        LessonWord lessonWord = new LessonWord(lessonId, wordId, order, notes);
        new InsertLessonWordAsyncTask(lessonDao).execute(lessonWord);
    }
    
    public void removeWordFromLesson(int lessonId, int wordId) {
        new RemoveWordFromLessonAsyncTask(lessonDao, lessonId, wordId).execute();
    }
    
    public boolean lessonContainsWord(int lessonId, int wordId) {
        return lessonDao.lessonContainsWord(lessonId, wordId);
    }
    
    // AsyncTask classes for database operations
    private static class InsertLessonAsyncTask extends AsyncTask<Lesson, Void, Void> {
        private LessonDao lessonDao;
        
        private InsertLessonAsyncTask(LessonDao lessonDao) {
            this.lessonDao = lessonDao;
        }
        
        @Override
        protected Void doInBackground(Lesson... lessons) {
            lessonDao.insert(lessons[0]);
            return null;
        }
    }
    
    private static class UpdateLessonAsyncTask extends AsyncTask<Lesson, Void, Void> {
        private LessonDao lessonDao;
        
        private UpdateLessonAsyncTask(LessonDao lessonDao) {
            this.lessonDao = lessonDao;
        }
        
        @Override
        protected Void doInBackground(Lesson... lessons) {
            lessonDao.update(lessons[0]);
            return null;
        }
    }
    
    private static class DeleteLessonAsyncTask extends AsyncTask<Lesson, Void, Void> {
        private LessonDao lessonDao;
        
        private DeleteLessonAsyncTask(LessonDao lessonDao) {
            this.lessonDao = lessonDao;
        }
        
        @Override
        protected Void doInBackground(Lesson... lessons) {
            lessonDao.delete(lessons[0]);
            return null;
        }
    }
    
    private static class InsertLessonWordAsyncTask extends AsyncTask<LessonWord, Void, Void> {
        private LessonDao lessonDao;
        
        private InsertLessonWordAsyncTask(LessonDao lessonDao) {
            this.lessonDao = lessonDao;
        }
        
        @Override
        protected Void doInBackground(LessonWord... lessonWords) {
            lessonDao.insertLessonWord(lessonWords[0]);
            return null;
        }
    }
    
    private static class RemoveWordFromLessonAsyncTask extends AsyncTask<Void, Void, Void> {
        private LessonDao lessonDao;
        private int lessonId;
        private int wordId;
        
        private RemoveWordFromLessonAsyncTask(LessonDao lessonDao, int lessonId, int wordId) {
            this.lessonDao = lessonDao;
            this.lessonId = lessonId;
            this.wordId = wordId;
        }
        
        @Override
        protected Void doInBackground(Void... voids) {
            lessonDao.removeLessonWord(lessonId, wordId);
            return null;
        }
    }
}