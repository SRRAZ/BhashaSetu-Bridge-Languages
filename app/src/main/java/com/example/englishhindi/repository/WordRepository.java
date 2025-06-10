package com.bhashasetu.app.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.bhashasetu.app.database.WordDao;
import com.bhashasetu.app.database.WordDatabase;
import com.bhashasetu.app.model.Word;

import java.util.List;

public class WordRepository {
    
    private WordDao wordDao;
    private LiveData<List<Word>> allWords;
    private LiveData<List<Word>> favoriteWords;
    
    public WordRepository(Application application) {
        WordDatabase database = WordDatabase.getInstance(application);
        wordDao = database.wordDao();
        allWords = wordDao.getAllWords();
        favoriteWords = wordDao.getFavoriteWords();
    }
    
    // Get the total number of words
    public int getWordCount() {
        return wordDao.getWordCount();
    }
    
    // Insert a new word
    public void insert(Word word) {
        new InsertWordAsyncTask(wordDao).execute(word);
    }
    
    // Update an existing word
    public void update(Word word) {
        new UpdateWordAsyncTask(wordDao).execute(word);
    }
    
    // Delete a word
    public void delete(Word word) {
        new DeleteWordAsyncTask(wordDao).execute(word);
    }
    
    // Delete all words
    public void deleteAllWords() {
        new DeleteAllWordsAsyncTask(wordDao).execute();
    }
    
    // Get all words
    public LiveData<List<Word>> getAllWords() {
        return allWords;
    }
    
    // Get words by category
    public LiveData<List<Word>> getWordsByCategory(String category) {
        return wordDao.getWordsByCategory(category);
    }
    
    // Get favorite words
    public LiveData<List<Word>> getFavoriteWords() {
        return favoriteWords;
    }
    
    // Search words
    public LiveData<List<Word>> searchWords(String query) {
        return wordDao.searchWords(query);
    }
    
    // Get least mastered words
    public LiveData<List<Word>> getLeastMasteredWords(int limit) {
        return wordDao.getLeastMasteredWords(limit);
    }
    
    // Get words by IDs
    public LiveData<List<Word>> getWordsByIds(List<Integer> wordIds) {
        return wordDao.getWordsByIds(wordIds);
    }
    
    // Get random words by difficulty
    public LiveData<List<Word>> getRandomWordsByDifficulty(int maxDifficulty, int limit) {
        return wordDao.getRandomWordsByDifficulty(maxDifficulty, limit);
    }
    
    // Get a specific word by ID
    public LiveData<Word> getWordById(int wordId) {
        return wordDao.getWordById(wordId);
    }
    
    // Get all categories
    public LiveData<List<String>> getAllCategories() {
        return wordDao.getAllCategories();
    }
    
    // Get words for a specific lesson
    public LiveData<List<Word>> getWordsForLesson(int lessonId) {
        return wordDao.getWordsForLesson(lessonId);
    }
    
    // AsyncTask classes for database operations
    private static class InsertWordAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao wordDao;
        
        private InsertWordAsyncTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }
        
        @Override
        protected Void doInBackground(Word... words) {
            wordDao.insert(words[0]);
            return null;
        }
    }
    
    private static class UpdateWordAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao wordDao;
        
        private UpdateWordAsyncTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }
        
        @Override
        protected Void doInBackground(Word... words) {
            wordDao.update(words[0]);
            return null;
        }
    }
    
    private static class DeleteWordAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao wordDao;
        
        private DeleteWordAsyncTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }
        
        @Override
        protected Void doInBackground(Word... words) {
            wordDao.delete(words[0]);
            return null;
        }
    }
    
    private static class DeleteAllWordsAsyncTask extends AsyncTask<Void, Void, Void> {
        private WordDao wordDao;
        
        private DeleteAllWordsAsyncTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }
        
        @Override
        protected Void doInBackground(Void... voids) {
            wordDao.deleteAllWords();
            return null;
        }
    }
}