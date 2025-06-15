package com.bhashasetu.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bhashasetu.app.model.Word;
import com.bhashasetu.app.repository.WordRepository;

import java.util.List;

public class WordViewModel extends AndroidViewModel {
    
    private WordRepository repository;
    private LiveData<List<Word>> allWords;
    private LiveData<List<Word>> favoriteWords;
    
    public WordViewModel(@NonNull Application application) {
        super(application);
        repository = new WordRepository(application);
        allWords = repository.getAllWords();
        favoriteWords = repository.getFavoriteWords();
    }
    
    // Get word count
    public int getWordCount() {
        return repository.getWordCount();
    }
    
    // Insert a new word
    public void insert(Word word) {
        repository.insert(word);
    }
    
    // Update an existing word
    public void update(Word word) {
        repository.update(word);
    }
    
    // Delete a word
    public void delete(Word word) {
        repository.delete(word);
    }
    
    // Delete all words
    public void deleteAllWords() {
        repository.deleteAllWords();
    }
    
    // Get all words
    public LiveData<List<Word>> getAllWords() {
        return allWords;
    }
    
    // Get words by category
    public LiveData<List<Word>> getWordsByCategory(String category) {
        return repository.getWordsByCategory(category);
    }
    
    // Get favorite words
    public LiveData<List<Word>> getFavoriteWords() {
        return favoriteWords;
    }
    
    // Search words
    public LiveData<List<Word>> searchWords(String query) {
        return repository.searchWords(query);
    }
    
    // Get least mastered words
    public LiveData<List<Word>> getLeastMasteredWords(int limit) {
        return repository.getLeastMasteredWords(limit);
    }
    
    // Get words by IDs
    public LiveData<List<Word>> getWordsByIds(List<Integer> wordIds) {
        return repository.getWordsByIds(wordIds);
    }
    
    // Get random words by difficulty
    public LiveData<List<Word>> getRandomWordsByDifficulty(int maxDifficulty, int limit) {
        return repository.getRandomWordsByDifficulty(maxDifficulty, limit);
    }
    
    // Get a specific word by ID
    public LiveData<Word> getWordById(int wordId) {
        return repository.getWordById(wordId);
    }
    
    // Get all categories
    public LiveData<List<String>> getAllCategories() {
        return repository.getAllCategories();
    }
    
    // Get words for a specific lesson
    public LiveData<List<Word>> getWordsForLesson(int lessonId) {
        return repository.getWordsForLesson(lessonId);
    }
    
    // Toggle favorite status of a word
    public void toggleFavorite(Word word) {
        word.setFavorite(!word.isFavorite());
        update(word);
    }
    
    // Update mastery level of a word
    public void updateMasteryLevel(Word word, int newLevel) {
        word.setMasteryLevel(Math.min(100, Math.max(0, newLevel)));
        update(word);
    }
}