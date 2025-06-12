package com.bhashasetu.app.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.bhashasetu.app.model.DeckWord;
import com.bhashasetu.app.model.FlashcardDeck;
import com.bhashasetu.app.model.Word;

import java.util.List;

@Dao
public interface FlashcardDeckDao {
    
    @Insert
    long insert(FlashcardDeck deck);
    
    @Update
    void update(FlashcardDeck deck);
    
    @Delete
    void delete(FlashcardDeck deck);
    
    @Query("SELECT * FROM flashcard_decks ORDER BY name ASC")
    LiveData<List<FlashcardDeck>> getAllDecks();
    
    @Query("SELECT * FROM flashcard_decks WHERE category = :category ORDER BY name ASC")
    LiveData<List<FlashcardDeck>> getDecksByCategory(String category);
    
    @Query("SELECT * FROM flashcard_decks WHERE isFavorite = 1 ORDER BY name ASC")
    LiveData<List<FlashcardDeck>> getFavoriteDecks();
    
    @Query("SELECT * FROM flashcard_decks WHERE difficulty <= :maxDifficulty ORDER BY name ASC")
    LiveData<List<FlashcardDeck>> getDecksByMaxDifficulty(int maxDifficulty);
    
    @Query("SELECT * FROM flashcard_decks WHERE id = :deckId")
    LiveData<FlashcardDeck> getDeckById(int deckId);
    
    @Insert
    void insertDeckWord(DeckWord deckWord);
    
    @Query("DELETE FROM deck_words WHERE deckId = :deckId AND wordId = :wordId")
    void removeDeckWord(int deckId, int wordId);
    
    @Transaction
    @Query("SELECT w.* FROM words w " +
           "INNER JOIN deck_words dw ON w.id = dw.wordId " +
           "WHERE dw.deckId = :deckId ORDER BY dw.`order` ASC")
    LiveData<List<Word>> getWordsForDeck(int deckId);
    
    @Query("SELECT COUNT(*) FROM deck_words WHERE deckId = :deckId")
    int getWordCountForDeck(int deckId);
    
    @Query("SELECT DISTINCT category FROM flashcard_decks ORDER BY category ASC")
    LiveData<List<String>> getAllCategories();
    
    @Query("UPDATE flashcard_decks SET lastPracticed = :timestamp WHERE id = :deckId")
    void updateLastPracticed(int deckId, long timestamp);
    
    @Query("UPDATE flashcard_decks SET isFavorite = :isFavorite WHERE id = :deckId")
    void updateFavoriteStatus(int deckId, boolean isFavorite);
    
    @Transaction
    @Query("SELECT EXISTS(SELECT 1 FROM deck_words WHERE deckId = :deckId AND wordId = :wordId)")
    boolean deckContainsWord(int deckId, int wordId);
}