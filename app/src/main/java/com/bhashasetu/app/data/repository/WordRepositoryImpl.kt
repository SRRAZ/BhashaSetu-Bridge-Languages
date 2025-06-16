package com.bhashasetu.app.data.repository

import kotlinx.coroutines.Dispatchers
import com.bhashasetu.app.data.model.Word
import com.bhashasetu.app.data.dao.WordDao
import com.bhashasetu.app.data.repository.base.BaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of WordRepository using Kotlin Flow for reactive data streams
 */
@Singleton
class WordRepositoryImpl @Inject constructor(
    private val wordDao: WordDao
) : BaseRepository() {
    override fun getAllWords(): Flow<List<Word>> {
        return wordDao.getAllWords().flowOn(Dispatchers.IO)
    }
    
    override fun getAllWords(category: String): Flow<List<Word>> {
        return wordDao.getWordsByCategory(category).flowOn(Dispatchers.IO)
    }

    override fun getFavoriteWords(): Flow<List<Word>> {
        return wordDao.getFavoriteWords().flowOn(Dispatchers.IO)
    }

    override fun searchWords(query: String): Flow<List<Word>> {
        return wordDao.searchWords("%$query%").flowOn(Dispatchers.IO)
    }

    override fun getRecentlyAdded(limit: Int): Flow<List<Word>> {
        return wordDao.getRecentlyAdded(limit).flowOn(Dispatchers.IO)
    }

    override fun getWordById(id: Int): Flow<Word?> {
        return wordDao.getWordById(id).flowOn(Dispatchers.IO)
    }

    override fun getLeastMasteredWords(limit: Int): Flow<List<Word>> {
        return wordDao.getLeastMasteredWords(limit).flowOn(Dispatchers.IO)
    }

    override fun getWordsDueForReview(): Flow<List<Word>> {
        return wordDao.getWordsDueForReview(Date()).flowOn(Dispatchers.IO)
    }

    override suspend fun insertWord(word: Word): Long = withContext(Dispatchers.IO) {
        return@withContext wordDao.insert(word)
    }

    override suspend fun updateWord(word: Word) = withContext(Dispatchers.IO) {
        wordDao.update(word)
    }

    override suspend fun deleteWord(word: Word) = withContext(Dispatchers.IO) {
        wordDao.delete(word)
    }

    override suspend fun updateMasteryLevel(wordId: Int, newLevel: Int) = withContext(Dispatchers.IO) {
        wordDao.updateMasteryLevel(wordId, newLevel)
    }

    override suspend fun toggleFavorite(wordId: Int) = withContext(Dispatchers.IO) {
        // Get current favorite status
        val currentWord = wordDao.getWordByIdSync(wordId)
        currentWord?.let {
            // Toggle favorite status
            wordDao.updateFavoriteStatus(wordId, !it.isFavorite)
        }
    }

    override suspend fun updateLastPracticed(wordId: Int) = withContext(Dispatchers.IO) {
        wordDao.updateLastPracticed(wordId, Date())
    }

    override suspend fun getWordCount(): Int = withContext(Dispatchers.IO) {
        return@withContext wordDao.getWordCount()
    }

    override suspend fun getWordCountByCategory(category: String): Int = withContext(Dispatchers.IO) {
        return@withContext wordDao.getWordCountByCategory(category)
    }

    override fun getWordCategories(): Flow<List<String>> {
        return wordDao.getCategories().flowOn(Dispatchers.IO)
    }
}