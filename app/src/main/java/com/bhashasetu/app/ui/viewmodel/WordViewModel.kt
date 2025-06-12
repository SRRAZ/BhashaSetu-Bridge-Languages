package com.bhashasetu.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhashasetu.app.data.model.Word
import com.bhashasetu.app.data.model.WordCategoryCrossRef
import com.bhashasetu.app.data.relation.WordWithCategories
import com.bhashasetu.app.data.repository.CategoryRepository
import com.bhashasetu.app.data.repository.WordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class WordViewModel @Inject constructor(
    private val wordRepository: WordRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    // All words with their categories
    val allWordsWithCategories: StateFlow<List<WordWithCategories>> = wordRepository.getAllWordsWithCategories()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Favorite words
    val favoriteWords: StateFlow<List<Word>> = wordRepository.favoriteWords
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Recently added words
    val recentWords: StateFlow<List<Word>> = wordRepository.getRecentlyAddedWords(20)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Total word count
    val wordCount: StateFlow<Int> = wordRepository.getWordCount()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    // Search results
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    val searchResults: StateFlow<List<Word>> = _searchQuery
        .debounce(300)
        .flatMapLatest { query ->
            if (query.isBlank()) {
                flowOf(emptyList())
            } else {
                wordRepository.searchWords(query)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    /**
     * Updates the search query
     * @param query The search query string
     */
    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    /**
     * Adds a new word to the database
     * @param word The word to add
     * @param categoryIds List of category IDs to associate with the word
     * @return The ID of the newly added word
     */
    suspend fun addWord(word: Word, categoryIds: List<Long>): Long {
        val wordId = wordRepository.insert(word)
        
        // Associate word with categories
        for (categoryId in categoryIds) {
            categoryRepository.addWordToCategory(wordId, categoryId)
        }
        
        return wordId
    }

    /**
     * Updates an existing word
     * @param word The word with updated values
     * @param categoryIds List of category IDs to associate with the word
     */
    suspend fun updateWord(word: Word, categoryIds: List<Long>) {
        wordRepository.update(word)
        
        // Get current categories
        val currentWord = wordRepository.getWordWithCategories(word.id).first()
        val currentCategoryIds = currentWord.categories.map { it.id }
        
        // Remove word from categories that are no longer associated
        val categoriesToRemove = currentCategoryIds.filter { it !in categoryIds }
        for (categoryId in categoriesToRemove) {
            categoryRepository.removeWordFromCategory(word.id, categoryId)
        }
        
        // Add word to new categories
        val categoriesToAdd = categoryIds.filter { it !in currentCategoryIds }
        for (categoryId in categoriesToAdd) {
            categoryRepository.addWordToCategory(word.id, categoryId)
        }
    }

    /**
     * Deletes a word
     * @param word The word to delete
     */
    fun deleteWord(word: Word) {
        viewModelScope.launch {
            wordRepository.delete(word)
        }
    }

    /**
     * Toggles the favorite status of a word
     * @param wordId The ID of the word
     * @param isFavorite The new favorite status
     */
    fun toggleFavorite(wordId: Long, isFavorite: Boolean) {
        viewModelScope.launch {
            wordRepository.toggleFavorite(wordId, isFavorite)
        }
    }

    /**
     * Gets a specific word by ID with its categories
     * @param wordId The ID of the word to retrieve
     * @return Flow of WordWithCategories for the specified word
     */
    fun getWordById(wordId: Long): Flow<WordWithCategories> {
        return wordRepository.getWordWithCategories(wordId)
    }

    /**
     * Gets words by category ID
     * @param categoryId The ID of the category
     * @return Flow of Word list for the specified category
     */
    fun getWordsByCategoryId(categoryId: Long): Flow<List<Word>> {
        return wordRepository.getWordsByCategoryId(categoryId)
    }

    /**
     * Gets random words for practice
     * @param count The number of words to retrieve
     * @return Flow of random Word list
     */
    fun getRandomWords(count: Int): Flow<List<Word>> {
        return wordRepository.getRandomWords(count)
    }
}