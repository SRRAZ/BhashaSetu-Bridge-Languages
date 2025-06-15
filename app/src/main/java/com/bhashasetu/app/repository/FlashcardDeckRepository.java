package com.bhashasetu.app.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import com.bhashasetu.app.database.FlashcardDeckDao;
import com.bhashasetu.app.database.WordDatabase;
import com.bhashasetu.app.model.DeckWord;
import com.bhashasetu.app.model.FlashcardDeck;
import com.bhashasetu.app.model.Word;


import java.util.List;

public class FlashcardDeckRepository {
    
    private FlashcardDeckDao flashcardDeckDao;
    private LiveData<List<FlashcardDeck>> allDecks;
    
    public FlashcardDeckRepository(Application application) {
        WordDatabase database = WordDatabase.getInstance(application);
        flashcardDeckDao = database.flashcardDeckDao();
        allDecks = flashcardDeckDao.getAllDecks();
    }
    
    // Insert a new deck
    public void insert(FlashcardDeck deck) {
        new InsertDeckAsyncTask(flashcardDeckDao).execute(deck);
    }
    
    // Update an existing deck
    public void update(FlashcardDeck deck) {
        new UpdateDeckAsyncTask(flashcardDeckDao).execute(deck);
    }
    
    // Delete a deck
    public void delete(FlashcardDeck deck) {
        new DeleteDeckAsyncTask(flashcardDeckDao).execute(deck);
    }
    
    // Get all decks
    public LiveData<List<FlashcardDeck>> getAllDecks() {
        return allDecks;
    }
    
    // Get decks by category
    public LiveData<List<FlashcardDeck>> getDecksByCategory(String category) {
        return flashcardDeckDao.getDecksByCategory(category);
    }
    
    // Get favorite decks
    public LiveData<List<FlashcardDeck>> getFavoriteDecks() {
        return flashcardDeckDao.getFavoriteDecks();
    }
    
    // Get decks by maximum difficulty
    public LiveData<List<FlashcardDeck>> getDecksByMaxDifficulty(int maxDifficulty) {
        return flashcardDeckDao.getDecksByMaxDifficulty(maxDifficulty);
    }
    
    // Get a specific deck by ID
    public LiveData<FlashcardDeck> getDeckById(int deckId) {
        return flashcardDeckDao.getDeckById(deckId);
    }
    
    // Add a word to a deck
    public void addWordToDeck(int deckId, int wordId, int order, String notes) {
        DeckWord deckWord = new DeckWord(deckId, wordId, order, notes);
        new InsertDeckWordAsyncTask(flashcardDeckDao).execute(deckWord);
    }
    
    // Remove a word from a deck
    public void removeWordFromDeck(int deckId, int wordId) {
        new RemoveWordFromDeckAsyncTask(flashcardDeckDao, deckId, wordId).execute();
    }
    
    // Get words for a specific deck
    public LiveData<List<Word>> getWordsForDeck(int deckId) {
        return flashcardDeckDao.getWordsForDeck(deckId);
    }
    
    // Get word count for a deck
    public int getWordCountForDeck(int deckId) {
        return flashcardDeckDao.getWordCountForDeck(deckId);
    }
    
    // Get all deck categories
    public LiveData<List<String>> getAllCategories() {
        return flashcardDeckDao.getAllCategories();
    }
    
    // Update last practiced timestamp
    public void updateLastPracticed(int deckId, long timestamp) {
        new UpdateLastPracticedAsyncTask(flashcardDeckDao, deckId, timestamp).execute();
    }
    
    // Update favorite status
    public void updateFavoriteStatus(int deckId, boolean isFavorite) {
        new UpdateFavoriteStatusAsyncTask(flashcardDeckDao, deckId, isFavorite).execute();
    }
    
    // Check if a deck contains a word
    public boolean deckContainsWord(int deckId, int wordId) {
        return flashcardDeckDao.deckContainsWord(deckId, wordId);
    }
    
    // AsyncTask classes for database operations
    private static class InsertDeckAsyncTask extends AsyncTask<FlashcardDeck, Void, Void> {
        private FlashcardDeckDao flashcardDeckDao;
        
        private InsertDeckAsyncTask(FlashcardDeckDao flashcardDeckDao) {
            this.flashcardDeckDao = flashcardDeckDao;
        }
        
        @Override
        protected Void doInBackground(FlashcardDeck... decks) {
            flashcardDeckDao.insert(decks[0]);
            return null;
        }
    }
    
    private static class UpdateDeckAsyncTask extends AsyncTask<FlashcardDeck, Void, Void> {
        private FlashcardDeckDao flashcardDeckDao;
        
        private UpdateDeckAsyncTask(FlashcardDeckDao flashcardDeckDao) {
            this.flashcardDeckDao = flashcardDeckDao;
        }
        
        @Override
        protected Void doInBackground(FlashcardDeck... decks) {
            flashcardDeckDao.update(decks[0]);
            return null;
        }
    }
    
    private static class DeleteDeckAsyncTask extends AsyncTask<FlashcardDeck, Void, Void> {
        private FlashcardDeckDao flashcardDeckDao;
        
        private DeleteDeckAsyncTask(FlashcardDeckDao flashcardDeckDao) {
            this.flashcardDeckDao = flashcardDeckDao;
        }
        
        @Override
        protected Void doInBackground(FlashcardDeck... decks) {
            flashcardDeckDao.delete(decks[0]);
            return null;
        }
    }
    
    private static class InsertDeckWordAsyncTask extends AsyncTask<DeckWord, Void, Void> {
        private FlashcardDeckDao flashcardDeckDao;
        
        private InsertDeckWordAsyncTask(FlashcardDeckDao flashcardDeckDao) {
            this.flashcardDeckDao = flashcardDeckDao;
        }
        
        @Override
        protected Void doInBackground(DeckWord... deckWords) {
            flashcardDeckDao.insertDeckWord(deckWords[0]);
            return null;
        }
    }
    
    private static class RemoveWordFromDeckAsyncTask extends AsyncTask<Void, Void, Void> {
        private FlashcardDeckDao flashcardDeckDao;
        private int deckId;
        private int wordId;
        
        private RemoveWordFromDeckAsyncTask(FlashcardDeckDao flashcardDeckDao, int deckId, int wordId) {
            this.flashcardDeckDao = flashcardDeckDao;
            this.deckId = deckId;
            this.wordId = wordId;
        }
        
        @Override
        protected Void doInBackground(Void... voids) {
            flashcardDeckDao.removeDeckWord(deckId, wordId);
            return null;
        }
    }
    
    private static class UpdateLastPracticedAsyncTask extends AsyncTask<Void, Void, Void> {
        private FlashcardDeckDao flashcardDeckDao;
        private int deckId;
        private long timestamp;
        
        private UpdateLastPracticedAsyncTask(FlashcardDeckDao flashcardDeckDao, int deckId, long timestamp) {
            this.flashcardDeckDao = flashcardDeckDao;
            this.deckId = deckId;
            this.timestamp = timestamp;
        }
        
        @Override
        protected Void doInBackground(Void... voids) {
            flashcardDeckDao.updateLastPracticed(deckId, timestamp);
            return null;
        }
    }
    
    private static class UpdateFavoriteStatusAsyncTask extends AsyncTask<Void, Void, Void> {
        private FlashcardDeckDao flashcardDeckDao;
        private int deckId;
        private boolean isFavorite;
        
        private UpdateFavoriteStatusAsyncTask(FlashcardDeckDao flashcardDeckDao, int deckId, boolean isFavorite) {
            this.flashcardDeckDao = flashcardDeckDao;
            this.deckId = deckId;
            this.isFavorite = isFavorite;
        }
        
        @Override
        protected Void doInBackground(Void... voids) {
            flashcardDeckDao.updateFavoriteStatus(deckId, isFavorite);
            return null;
        }
    }
}