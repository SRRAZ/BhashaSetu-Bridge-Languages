package com.bhashasetu.app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.bhashasetu.app.data.model.Word
import com.bhashasetu.app.data.relation.WordWithCategories
import kotlinx.coroutines.flow.Flow
import java.util.Date

/**
 * Data Access Object for Word entity using Kotlin Flow
 */
@Dao
interface WordDaoFlow {
    /**
     * Insert a new word
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: Word): Long
    
    /**
     * Update an existing word
     */
    @Update
    suspend fun update(word: Word)
    
    /**
     * Delete a word
     */
    @Delete
    suspend fun delete(word: Word)
    
    /**
     * Get all words
     */
    @Query("SELECT * FROM words ORDER BY englishWord")
    fun getAllWords(): Flow<List<Word>>
    
    /**
     * Get word by ID
     */
    @Query("SELECT * FROM words WHERE id = :id")
    fun getWordById(id: Int): Flow<Word?>
    
    /**
     * Get word by ID (synchronous for internal use)
     */
    @Query("SELECT * FROM words WHERE id = :id")
    suspend fun getWordByIdSync(id: Int): Word?
    
    /**
     * Get words by category
     */
    @Query("SELECT * FROM words WHERE category = :category ORDER BY englishWord")
    fun getWordsByCategory(category: String): Flow<List<Word>>
    
    /**
     * Get favorite words
     */
    @Query("SELECT * FROM words WHERE isFavorite = 1 ORDER BY englishWord")
    fun getFavoriteWords(): Flow<List<Word>>
    
    /**
     * Search words by English or Hindi text
     */
    @Query("SELECT * FROM words WHERE englishWord LIKE :query OR hindiWord LIKE :query ORDER BY englishWord")
    fun searchWords(query: String): Flow<List<Word>>
    
    /**
     * Get recently added words
     */
    @Query("SELECT * FROM words ORDER BY timeAdded DESC LIMIT :limit")
    fun getRecentlyAdded(limit: Int): Flow<List<Word>>
    
    /**
     * Get least mastered words
     */
    @Query("SELECT * FROM words ORDER BY masteryLevel ASC LIMIT :limit")
    fun getLeastMasteredWords(limit: Int): Flow<List<Word>>
    
    /**
     * Get words that are due for review
     */
    @Query("SELECT * FROM words WHERE lastPracticed IS NULL OR lastPracticed < :currentDate")
    fun getWordsDueForReview(currentDate: Date): Flow<List<Word>>
    
    /**
     * Get total word count
     */
    @Query("SELECT COUNT(*) FROM words")
    suspend fun getWordCount(): Int
    
    /**
     * Get word count by category
     */
    @Query("SELECT COUNT(*) FROM words WHERE category = :category")
    suspend fun getWordCountByCategory(category: String): Int
    
    /**
     * Update mastery level
     */
    @Query("UPDATE words SET masteryLevel = :level WHERE id = :wordId")
    suspend fun updateMasteryLevel(wordId: Int, level: Int)
    
    /**
     * Update favorite status
     */
    @Query("UPDATE words SET isFavorite = :isFavorite WHERE id = :wordId")
    suspend fun updateFavoriteStatus(wordId: Int, isFavorite: Boolean)
    
    /**
     * Update last practiced timestamp
     */
    @Query("UPDATE words SET lastPracticed = :date WHERE id = :wordId")
    suspend fun updateLastPracticed(wordId: Int, date: Date)
    
    /**
     * Get all word categories
     */
    @Query("SELECT DISTINCT category FROM words ORDER BY category")
    fun getCategories(): Flow<List<String>>
    
    /**
     * Get word with its categories (relation)
     */
    @Transaction
    @Query("SELECT * FROM words WHERE id = :wordId")
    fun getWordWithCategories(wordId: Int): Flow<WordWithCategories?>
}