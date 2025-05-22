package com.example.englishhindi.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.englishhindi.model.FlashcardDeck;
import com.example.englishhindi.model.Word;
import com.example.englishhindi.repository.FlashcardDeckRepository;

import java.util.List;

public class FlashcardDeckViewModel extends AndroidViewModel {
    
    private FlashcardDeckRepository repository;
    private LiveData<List<FlashcardDeck>> allDecks;
    
    public FlashcardDeckViewModel(@NonNull Application application) {
        super(application);
        repository = new FlashcardDeckRepository(application);
        allDecks = repository.getAllDecks();
    }
    
    // Insert a new deck
    public void insert(FlashcardDeck deck) {
        repository.insert(deck);
    }
    
    // Update an existing deck
    public void update(FlashcardDeck deck) {
        repository.update(deck);
    }
    
    // Delete a deck
    public void delete(FlashcardDeck deck) {
        repository.delete(deck);
    }
    
    // Get all decks
    public LiveData<List<FlashcardDeck>> getAllDecks() {
        return allDecks;
    }
    
    // Get decks by category
    public LiveData<List<FlashcardDeck>> getDecksByCategory(String category) {
        return repository.getDecksByCategory(category);
    }
    
    // Get favorite decks
    public LiveData<List<FlashcardDeck>> getFavoriteDecks() {
        return repository.getFavoriteDecks();
    }
    
    // Get decks by maximum difficulty
    public LiveData<List<FlashcardDeck>> getDecksByMaxDifficulty(int maxDifficulty) {
        return repository.getDecksByMaxDifficulty(maxDifficulty);
    }
    
    // Get a specific deck by ID
    public LiveData<FlashcardDeck> getDeckById(int deckId) {
        return repository.getDeckById(deckId);
    }
    
    // Add a word to a deck
    public void addWordToDeck(int deckId, int wordId, int order, String notes) {
        repository.addWordToDeck(deckId, wordId, order, notes);
    }
    
    // Remove a word from a deck
    public void removeWordFromDeck(int deckId, int wordId) {
        repository.removeWordFromDeck(deckId, wordId);
    }
    
    // Get words for a specific deck
    public LiveData<List<Word>> getWordsForDeck(int deckId) {
        return repository.getWordsForDeck(deckId);
    }
    
    // Get word count for a deck
    public int getWordCountForDeck(int deckId) {
        return repository.getWordCountForDeck(deckId);
    }
    
    // Get all deck categories
    public LiveData<List<String>> getAllCategories() {
        return repository.getAllCategories();
    }
    
    // Update last practiced timestamp
    public void updateLastPracticed(int deckId) {
        repository.updateLastPracticed(deckId, System.currentTimeMillis());
    }
    
    // Toggle favorite status
    public void toggleFavorite(int deckId, boolean currentStatus) {
        repository.updateFavoriteStatus(deckId, !currentStatus);
    }
    
    // Check if a deck contains a word
    public boolean deckContainsWord(int deckId, int wordId) {
        return repository.deckContainsWord(deckId, wordId);
    }
}