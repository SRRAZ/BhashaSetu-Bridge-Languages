package com.bhashasetu.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhashasetu.app.data.model.Word
import com.bhashasetu.app.data.repository.WordRepository
import com.bhashasetu.app.data.repository.UserProgressRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordViewModel @Inject constructor(
    private val wordRepository: WordRepository,
    private val userProgressRepository: UserProgressRepository
) : ViewModel() {

    // Existing properties
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _selectedDifficulty = MutableStateFlow(0)
    val selectedDifficulty = _selectedDifficulty.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    // Enhanced flows for different word collections
    val allWords = wordRepository.getAllWords()
        .catch { e ->
            _error.value = "Failed to load words: ${e.message}"
            emit(emptyList())
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val favoriteWords = wordRepository.getFavoriteWords()
        .catch { e ->
            _error.value = "Failed to load favorite words: ${e.message}"
            emit(emptyList())
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val recentWords = wordRepository.getRecentWords()
        .catch { e ->
            _error.value = "Failed to load recent words: ${e.message}"
            emit(emptyList())
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Search and filter functionality
    val filteredWords = combine(
        allWords,
        searchQuery,
        selectedDifficulty
    ) { words, query, difficulty ->
        words.filter { word ->
            val matchesQuery = if (query.isEmpty()) {
                true
            } else {
                word.english.contains(query, ignoreCase = true) ||
                        word.hindi.contains(query, ignoreCase = true)
            }

            val matchesDifficulty = if (difficulty == 0) {
                true
            } else {
                word.difficulty_level == difficulty
            }

            matchesQuery && matchesDifficulty
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Word statistics
    val wordStats = allWords.map { words ->
        WordStats(
            total = words.size,
            beginner = words.count { it.difficulty_level == 1 },
            intermediate = words.count { it.difficulty_level == 2 },
            advanced = words.count { it.difficulty_level == 3 },
            favorites = favoriteWords.value.size
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = WordStats()
    )

    /**
     * Gets all words from the repository
     * @return Flow of Word list
     */
    fun getAllWords(): Flow<List<Word>> {
        return allWords
    }

    /**
     * Gets words by difficulty level
     * @param difficulty The difficulty level (1-3)
     * @return Flow of Word list filtered by difficulty
     */
    fun getWordsByDifficulty(difficulty: Int): Flow<List<Word>> {
        return allWords.map { words ->
            words.filter { it.difficulty_level == difficulty }
        }
    }

    /**
     * Gets words for a specific lesson
     * @param lessonId The ID of the lesson
     * @return Flow of Word list for the specified lesson
     */
    fun wordsForLesson(lessonId: String): Flow<List<Word>> {
        return allWords.map { words ->
            // For now, return words based on difficulty level matching the lesson
            // In a real implementation, you'd have a lesson-word relationship
            val difficulty = when (lessonId) {
                "1", "2" -> 1 // Basic lessons
                "3", "4" -> 2 // Intermediate lessons
                "5" -> 3      // Advanced lessons
                else -> lessonId.toIntOrNull() ?: 1
            }

            words.filter { it.difficulty_level == difficulty }.take(10)
        }
    }

    /**
     * Gets lesson progress
     * @param lessonId The ID of the lesson
     * @return Flow of progress percentage (0.0 to 1.0)
     */
    fun getLessonProgress(lessonId: String): Flow<Float> {
        return userProgressRepository.getLessonProgress(lessonId)
    }

    /**
     * Gets a specific word by string ID
     * @param wordId The string ID of the word
     * @return Flow of Word or null
     */
    fun getWord(wordId: String): Flow<Word?> {
        val id = wordId.toLongOrNull() ?: return flowOf(null)
        return wordRepository.getWordById(id)
    }

    /**
     * Gets a word by its ID
     * @param wordId The ID of the word
     * @return Flow of Word or null
     */
    fun getWordById(wordId: Long): Flow<Word?> {
        return wordRepository.getWordById(wordId)
    }

    /**
     * Gets random words for practice
     * @param count Number of words to return
     * @param difficulty Optional difficulty filter
     * @return Flow of random Word list
     */
    fun getRandomWords(count: Int = 10, difficulty: Int? = null): Flow<List<Word>> {
        return allWords.map { words ->
            val filteredWords = if (difficulty != null) {
                words.filter { it.difficulty_level == difficulty }
            } else {
                words
            }
            filteredWords.shuffled().take(count)
        }
    }

    /**
     * Gets words that need review (based on spaced repetition)
     * @return Flow of Word list that need review
     */
    fun getWordsForReview(): Flow<List<Word>> {
        return wordRepository.getWordsForReview()
    }

    /**
     * Gets words by category
     * @param category The category to filter by
     * @return Flow of Word list in the specified category
     */
    fun getWordsByCategory(category: String): Flow<List<Word>> {
        return allWords.map { words ->
            words.filter { it.category == category }
        }
    }

    /**
     * Toggles favorite status of a word
     * @param word The word to toggle favorite status
     */
    fun toggleFavorite(word: Word) {
        viewModelScope.launch {
            try {
                val updatedWord = word.copy(is_favorite = !word.is_favorite)
                wordRepository.updateWord(updatedWord)
            } catch (e: Exception) {
                _error.value = "Failed to update favorite: ${e.message}"
            }
        }
    }

    /**
     * Marks a word as learned
     * @param word The word to mark as learned
     */
    fun markWordAsLearned(word: Word) {
        viewModelScope.launch {
            try {
                val updatedWord = word.copy(is_learned = true)
                wordRepository.updateWord(updatedWord)
                userProgressRepository.incrementWordsLearned()
            } catch (e: Exception) {
                _error.value = "Failed to mark word as learned: ${e.message}"
            }
        }
    }

    /**
     * Records word practice (for spaced repetition)
     * @param word The word that was practiced
     * @param correct Whether the practice was correct
     */
    fun recordWordPractice(word: Word, correct: Boolean) {
        viewModelScope.launch {
            try {
                wordRepository.recordWordPractice(word.id, correct)
                if (correct) {
                    userProgressRepository.addXP(5) // 5 XP for correct practice
                }
            } catch (e: Exception) {
                _error.value = "Failed to record practice: ${e.message}"
            }
        }
    }

    /**
     * Updates search query
     * @param query The search query
     */
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    /**
     * Updates difficulty filter
     * @param difficulty The difficulty level (0 for all, 1-3 for specific levels)
     */
    fun updateDifficultyFilter(difficulty: Int) {
        _selectedDifficulty.value = difficulty
    }

    /**
     * Clears current error
     */
    fun clearError() {
        _error.value = null
    }

    /**
     * Refreshes word data
     */
    fun refreshWords() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                wordRepository.refreshWords()
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Failed to refresh: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Gets pronunciation practice words
     * @param count Number of words to return
     * @return Flow of Word list suitable for pronunciation practice
     */
    fun getPronunciationWords(count: Int = 8): Flow<List<Word>> {
        return allWords.map { words ->
            words.filter { !it.pronunciation.isNullOrBlank() }
                .shuffled()
                .take(count)
        }
    }

    /**
     * Gets quiz words for a specific difficulty
     * @param difficulty The difficulty level
     * @param count Number of words to return
     * @return Flow of Word list for quiz
     */
    fun getQuizWords(difficulty: Int, count: Int = 5): Flow<List<Word>> {
        return getWordsByDifficulty(difficulty).map { words ->
            words.shuffled().take(count)
        }
    }

    /**
     * Gets word learning streaks and statistics
     * @return Flow of learning statistics
     */
    fun getLearningStats(): Flow<LearningStats> {
        return combine(
            allWords,
            favoriteWords,
            userProgressRepository.getCurrentStreak(),
            userProgressRepository.getWordsLearned()
        ) { all, favorites, streak, learned ->
            LearningStats(
                totalWords = all.size,
                learnedWords = learned,
                favoriteWords = favorites.size,
                currentStreak = streak,
                learningProgress = if (all.isNotEmpty()) learned.toFloat() / all.size else 0f
            )
        }
    }

    /**
     * Imports words from a predefined dataset
     * @param category The category of words to import
     */
    fun importWords(category: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                wordRepository.importWordsFromCategory(category)
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Failed to import words: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Gets words that match a specific pattern (for advanced search)
     * @param pattern The regex pattern to match
     * @return Flow of matching words
     */
    fun getWordsByPattern(pattern: String): Flow<List<Word>> {
        return allWords.map { words ->
            try {
                val regex = Regex(pattern, RegexOption.IGNORE_CASE)
                words.filter { word ->
                    regex.containsMatchIn(word.english) || regex.containsMatchIn(word.hindi)
                }
            } catch (e: Exception) {
                emptyList()
            }
        }
    }
}

// Data classes for statistics
data class WordStats(
    val total: Int = 0,
    val beginner: Int = 0,
    val intermediate: Int = 0,
    val advanced: Int = 0,
    val favorites: Int = 0
)

data class LearningStats(
    val totalWords: Int = 0,
    val learnedWords: Int = 0,
    val favoriteWords: Int = 0,
    val currentStreak: Int = 0,
    val learningProgress: Float = 0f
)