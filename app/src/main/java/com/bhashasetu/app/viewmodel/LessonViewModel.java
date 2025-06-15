package com.bhashasetu.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bhashasetu.app.model.Lesson;
import com.bhashasetu.app.repository.LessonRepository;

import java.util.List;

public class LessonViewModel extends AndroidViewModel {
    
    private LessonRepository repository;
    private LiveData<List<Lesson>> allLessons;
    
    public LessonViewModel(@NonNull Application application) {
        super(application);
        repository = new LessonRepository(application);
        allLessons = repository.getAllLessons();
    }
    
    // Methods to expose repository functionality
    public void insert(Lesson lesson) {
        repository.insert(lesson);
    }
    
    public void update(Lesson lesson) {
        repository.update(lesson);
    }
    
    public void delete(Lesson lesson) {
        repository.delete(lesson);
    }
    
    public LiveData<List<Lesson>> getAllLessons() {
        return allLessons;
    }
    
    public LiveData<List<Lesson>> getLessonsByCategory(String category) {
        return repository.getLessonsByCategory(category);
    }
    
    public LiveData<List<Lesson>> getLessonsByLevel(String level) {
        return repository.getLessonsByLevel(level);
    }
    
    public LiveData<Lesson> getLessonById(int id) {
        return repository.getLessonById(id);
    }
    
    public LiveData<Lesson> getNextIncompleteLesson() {
        return repository.getNextIncompleteLesson();
    }
    
    public LiveData<List<String>> getAllCategories() {
        return repository.getAllCategories();
    }
    
    public LiveData<List<Lesson>> getLessonsContainingWord(int wordId) {
        return repository.getLessonsContainingWord(wordId);
    }
    
    // Methods for LessonWord junction operations
    public void addWordToLesson(int lessonId, int wordId, int order, String notes) {
        repository.addWordToLesson(lessonId, wordId, order, notes);
    }
    
    public void removeWordFromLesson(int lessonId, int wordId) {
        repository.removeWordFromLesson(lessonId, wordId);
    }
    
    public boolean lessonContainsWord(int lessonId, int wordId) {
        return repository.lessonContainsWord(lessonId, wordId);
    }
}