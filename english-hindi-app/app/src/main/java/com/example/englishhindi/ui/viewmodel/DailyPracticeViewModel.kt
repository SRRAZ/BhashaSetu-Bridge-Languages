package com.example.englishhindi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishhindi.data.model.AppSettings
import com.example.englishhindi.data.model.Word
import com.example.englishhindi.data.repository.AppSettingsRepository
import com.example.englishhindi.data.repository.WordRepository
import com.example.englishhindi.util.SpacedRepetitionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

/**
 * ViewModel for the daily practice feature
 */
@HiltViewModel
class DailyPracticeViewModel @Inject constructor(
    private val wordRepository: WordRepository,
    private val settingsRepository: AppSettingsRepository,
    private val spacedRepetitionManager: SpacedRepetitionManager
) : ViewModel() {

    // App settings
    val settings: StateFlow<AppSettings?> = settingsRepository.settings
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )
    
    // Words due for review today
    private val _dueForReviewWords = MutableStateFlow<List<Word>>(emptyList())
    val dueForReviewWords: StateFlow<List<Word>> = _dueForReviewWords.asStateFlow()
    
    // Today's new words to learn
    private val _dailyNewWords = MutableStateFlow<List<Word>>(emptyList())
    val dailyNewWords: StateFlow<List<Word>> = _dailyNewWords.asStateFlow()
    
    // Combined list of all words for today's practice
    val todaysPracticeWords: StateFlow<List<Word>> = combine(
        dueForReviewWords,
        dailyNewWords
    ) { reviewWords, newWords ->
        // Combine and shuffle the lists to create a mixed practice
        (reviewWords + newWords).shuffled()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    
    // Current practice session state
    private val _currentPracticeState = MutableStateFlow<PracticeState>(PracticeState.NotStarted)
    val currentPracticeState: StateFlow<PracticeState> = _currentPracticeState.asStateFlow()
    
    // Current word being practiced
    private val _currentWordIndex = MutableStateFlow(0)
    private val _currentWord = MutableStateFlow<Word?>(null)
    val currentWord: StateFlow<Word?> = _currentWord.asStateFlow()
    
    /**
     * Loads words for today's practice session
     */
    fun loadTodaysPracticeWords() {
        viewModelScope.launch {
            // Load words due for review based on spaced repetition algorithm
            val dueWordIds = spacedRepetitionManager.getDueForReviewWords()
            val reviewWords = dueWordIds.mapNotNull { wordId ->
                wordRepository.getWordById(wordId).first()
            }
            _dueForReviewWords.value = reviewWords
            
            // Load new words for today based on daily word count setting
            val settings = settingsRepository.settings.first()
            val dailyWordCount = settings?.dailyWordCount ?: 5
            
            // Get recent words to exclude
            val recentWords = wordRepository.getRecentlyAddedWords(100).first()
            val recentWordIds = recentWords.map { it.id }.toSet()
            
            // Get random words that haven't been recently studied
            val allWords = wordRepository.allWords.first()
            val candidateWords = allWords.filter { it.id !in recentWordIds }
            
            // Pick random words from candidates, up to dailyWordCount
            val newWordsCount = minOf(dailyWordCount, candidateWords.size)
            _dailyNewWords.value = if (candidateWords.isNotEmpty()) {
                candidateWords.shuffled().take(newWordsCount)
            } else {
                // If no candidates, just pick random words
                allWords.shuffled().take(newWordsCount)
            }
            
            // Reset practice state
            _currentPracticeState.value = PracticeState.Ready
        }
    }
    
    /**
     * Starts a new practice session
     */
    fun startPractice() {
        if (todaysPracticeWords.value.isNotEmpty()) {
            _currentPracticeState.value = PracticeState.InProgress
            _currentWordIndex.value = 0
            _currentWord.value = todaysPracticeWords.value.getOrNull(0)
        }
    }
    
    /**
     * Moves to the next word in practice
     * @return True if there are more words, False if practice is complete
     */
    fun nextWord(): Boolean {
        val nextIndex = _currentWordIndex.value + 1
        return if (nextIndex < todaysPracticeWords.value.size) {
            _currentWordIndex.value = nextIndex
            _currentWord.value = todaysPracticeWords.value[nextIndex]
            true
        } else {
            _currentPracticeState.value = PracticeState.Completed
            false
        }
    }
    
    /**
     * Records a user's performance on the current word
     * @param performance Performance rating (0-5)
     */
    fun recordWordPerformance(performance: Int) {
        viewModelScope.launch {
            currentWord.value?.let { word ->
                spacedRepetitionManager.processWordReview(word.id, performance)
            }
        }
    }
    
    /**
     * Returns the total number of words for today's practice
     */
    fun getTotalWordCount(): Int = todaysPracticeWords.value.size
    
    /**
     * Returns the current word index (1-based for display)
     */
    fun getCurrentWordNumber(): Int = _currentWordIndex.value + 1
    
    /**
     * Practice session state
     */
    sealed class PracticeState {
        object NotStarted : PracticeState()
        object Ready : PracticeState()
        object InProgress : PracticeState()
        object Completed : PracticeState()
    }
}