package com.example.englishhindi.data.repository

import androidx.lifecycle.LiveData
import com.example.englishhindi.data.dao.WordDao
import com.example.englishhindi.data.model.Word
import com.example.englishhindi.data.relation.WordWithCategories
import kotlinx.coroutines.flow.Flow
import java.util.Date

class WordRepository(private val wordDao: WordDao) {

    val allWords: Flow<List<Word>> = wordDao.getAllWords()
    val favoriteWords: Flow<List<Word>> = wordDao.getFavoriteWords()
    
    suspend fun insert(word: Word): Long {
        return wordDao.insert(word)
    }
    
    suspend fun update(word: Word) {
        wordDao.update(word)
    }
    
    suspend fun delete(word: Word) {
        wordDao.delete(word)
    }
    
    fun getWordById(id: Long): Flow<Word> {
        return wordDao.getWordById(id)
    }
    
    fun getWordsByDifficulty(difficulty: Int): Flow<List<Word>> {
        return wordDao.getWordsByDifficulty(difficulty)
    }
    
    fun searchWords(query: String): Flow<List<Word>> {
        return wordDao.searchWords("%$query%")
    }
    
    fun getWordWithCategories(wordId: Long): Flow<WordWithCategories> {
        return wordDao.getWordWithCategories(wordId)
    }
    
    fun getAllWordsWithCategories(): Flow<List<WordWithCategories>> {
        return wordDao.getAllWordsWithCategories()
    }
    
    suspend fun toggleFavorite(wordId: Long, isFavorite: Boolean) {
        wordDao.toggleFavorite(wordId, isFavorite)
    }
    
    fun getRecentlyAddedWords(limit: Int): Flow<List<Word>> {
        return wordDao.getRecentlyAddedWords(limit)
    }
    
    fun getWordsByCategoryId(categoryId: Long): Flow<List<Word>> {
        return wordDao.getWordsByCategoryId(categoryId)
    }
    
    fun getWordsAddedBetween(startDate: Date, endDate: Date): Flow<List<Word>> {
        return wordDao.getWordsAddedBetween(startDate, endDate)
    }
    
    fun getWordCount(): Flow<Int> {
        return wordDao.getWordCount()
    }
    
    fun getWordCountByDifficulty(difficulty: Int): Flow<Int> {
        return wordDao.getWordCountByDifficulty(difficulty)
    }
    
    fun getRandomWords(count: Int): Flow<List<Word>> {
        return wordDao.getRandomWords(count)
    }
}