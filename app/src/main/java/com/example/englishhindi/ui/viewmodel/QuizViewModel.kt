package com.example.englishhindi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishhindi.data.model.Quiz
import com.example.englishhindi.data.model.QuizQuestion
import com.example.englishhindi.data.model.UserProgress
import com.example.englishhindi.data.relation.QuizWithQuestions
import com.example.englishhindi.data.repository.QuizRepository
import com.example.englishhindi.data.repository.UserProgressRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val quizRepository: QuizRepository,
    private val userProgressRepository: UserProgressRepository
) : ViewModel() {

    // All quizzes
    val allQuizzes: StateFlow<List<Quiz>> = quizRepository.allQuizzes
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Completed quiz count
    val completedQuizCount: StateFlow<Int> = quizRepository.getCompletedQuizCount()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    // Average quiz score
    val averageQuizScore: StateFlow<Float> = quizRepository.getAverageQuizScore()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0f
        )

    // Currently active quiz
    private val _activeQuiz = MutableStateFlow<QuizWithQuestions?>(null)
    val activeQuiz: StateFlow<QuizWithQuestions?> = _activeQuiz.asStateFlow()

    // Current question index
    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex.asStateFlow()

    // User answers
    private val _userAnswers = MutableStateFlow<MutableMap<Long, String>>(mutableMapOf())
    val userAnswers: StateFlow<Map<Long, String>> = _userAnswers.asStateFlow()

    // Quiz timer
    private val _remainingTimeSeconds = MutableStateFlow<Int?>(null)
    val remainingTimeSeconds: StateFlow<Int?> = _remainingTimeSeconds.asStateFlow()

    // Quiz results
    private val _quizResult = MutableStateFlow<QuizResult?>(null)
    val quizResult: StateFlow<QuizResult?> = _quizResult.asStateFlow()

    /**
     * Starts a quiz
     * @param quizId The ID of the quiz to start
     */
    fun startQuiz(quizId: Long) {
        viewModelScope.launch {
            val quiz = quizRepository.getQuizWithQuestions(quizId).first()
            _activeQuiz.value = quiz
            _currentQuestionIndex.value = 0
            _userAnswers.value = mutableMapOf()
            _quizResult.value = null
            
            // Initialize timer if the quiz has a time limit
            quiz.quiz.timeLimit?.let { timeLimit ->
                _remainingTimeSeconds.value = timeLimit
                // Timer logic would be implemented here in a real app
            }
        }
    }

    /**
     * Selects an answer for the current question
     * @param questionId The ID of the question
     * @param answer The user's answer
     */
    fun selectAnswer(questionId: Long, answer: String) {
        val currentAnswers = _userAnswers.value.toMutableMap()
        currentAnswers[questionId] = answer
        _userAnswers.value = currentAnswers
    }

    /**
     * Moves to the next question
     * @return True if there is a next question, false otherwise
     */
    fun nextQuestion(): Boolean {
        val activeQuiz = _activeQuiz.value ?: return false
        val nextIndex = _currentQuestionIndex.value + 1
        
        return if (nextIndex < activeQuiz.questions.size) {
            _currentQuestionIndex.value = nextIndex
            true
        } else {
            false
        }
    }

    /**
     * Moves to the previous question
     * @return True if there is a previous question, false otherwise
     */
    fun previousQuestion(): Boolean {
        val prevIndex = _currentQuestionIndex.value - 1
        
        return if (prevIndex >= 0) {
            _currentQuestionIndex.value = prevIndex
            true
        } else {
            false
        }
    }

    /**
     * Submits the quiz for grading
     */
    fun submitQuiz() {
        val activeQuiz = _activeQuiz.value ?: return
        val quiz = activeQuiz.quiz
        val questions = activeQuiz.questions
        val userAnswers = _userAnswers.value
        
        // Calculate score
        var correctCount = 0
        val questionResults = questions.map { question ->
            val userAnswer = userAnswers[question.id] ?: ""
            val isCorrect = isAnswerCorrect(question, userAnswer)
            
            if (isCorrect) {
                correctCount++
            }
            
            QuestionResult(
                questionId = question.id,
                userAnswer = userAnswer,
                correctAnswer = question.correctAnswer,
                isCorrect = isCorrect
            )
        }
        
        val scorePercentage = if (questions.isNotEmpty()) {
            (correctCount.toFloat() / questions.size) * 100
        } else {
            0f
        }
        
        // Create quiz result
        _quizResult.value = QuizResult(
            quizId = quiz.id,
            score = scorePercentage.toInt(),
            totalQuestions = questions.size,
            correctAnswers = correctCount,
            completionTime = calculateCompletionTime(quiz.timeLimit),
            questionResults = questionResults
        )
        
        // Save quiz result to database
        viewModelScope.launch {
            quizRepository.updateLastAttemptAndScore(
                quizId = quiz.id,
                lastAttemptDate = Date(),
                score = scorePercentage.toInt()
            )
            
            quizRepository.incrementCompletionCount(quiz.id)
            
            // Update user progress for each word in the quiz
            for (question in questions) {
                question.wordId?.let { wordId ->
                    // If the question was answered correctly
                    if (questionResults.find { it.questionId == question.id }?.isCorrect == true) {
                        userProgressRepository.incrementCorrectAnswers(wordId)
                        
                        // Update last reviewed date
                        userProgressRepository.updateLastReviewed(wordId, Date())
                        
                        // Update quiz score
                        userProgressRepository.updateQuizScore(wordId, scorePercentage.toInt())
                    } else {
                        userProgressRepository.incrementIncorrectAnswers(wordId)
                    }
                }
            }
        }
    }

    /**
     * Checks if an answer is correct
     * @param question The question
     * @param userAnswer The user's answer
     * @return True if the answer is correct, false otherwise
     */
    private fun isAnswerCorrect(question: QuizQuestion, userAnswer: String): Boolean {
        // For multiple choice and true/false questions, exact match
        if (question.questionType == "multiple_choice" || question.questionType == "true_false") {
            return userAnswer == question.correctAnswer
        }
        
        // For translation questions, case-insensitive match
        if (question.questionType == "translation") {
            return userAnswer.trim().equals(question.correctAnswer.trim(), ignoreCase = true)
        }
        
        // For fill_blank questions, exact match
        if (question.questionType == "fill_blank") {
            return userAnswer.trim() == question.correctAnswer.trim()
        }
        
        // For matching questions, exact match
        if (question.questionType == "matching") {
            return userAnswer == question.correctAnswer
        }
        
        // For audio questions, exact match
        if (question.questionType == "audio") {
            return userAnswer.trim().equals(question.correctAnswer.trim(), ignoreCase = true)
        }
        
        return false
    }

    /**
     * Calculates the completion time for a quiz
     * @param timeLimit The time limit of the quiz in seconds
     * @return The completion time in seconds
     */
    private fun calculateCompletionTime(timeLimit: Int?): Int {
        timeLimit ?: return 0
        val remainingTime = _remainingTimeSeconds.value ?: 0
        return timeLimit - remainingTime
    }

    /**
     * Gets a specific quiz by ID with its questions
     * @param quizId The ID of the quiz to retrieve
     * @return Flow of QuizWithQuestions for the specified quiz
     */
    fun getQuizById(quizId: Long): Flow<QuizWithQuestions> {
        return quizRepository.getQuizWithQuestions(quizId)
    }

    /**
     * Gets quizzes by type
     * @param quizType The type of quizzes to retrieve
     * @return Flow of Quiz list for the specified type
     */
    fun getQuizzesByType(quizType: String): Flow<List<Quiz>> {
        return quizRepository.getQuizzesByType(quizType)
    }

    /**
     * Gets quizzes by difficulty
     * @param difficulty The difficulty level of quizzes to retrieve
     * @return Flow of Quiz list for the specified difficulty
     */
    fun getQuizzesByDifficulty(difficulty: Int): Flow<List<Quiz>> {
        return quizRepository.getQuizzesByDifficulty(difficulty)
    }

    /**
     * Gets quizzes by lesson ID
     * @param lessonId The ID of the lesson
     * @return Flow of Quiz list for the specified lesson
     */
    fun getQuizzesByLessonId(lessonId: Long): Flow<List<Quiz>> {
        return quizRepository.getQuizzesByLessonId(lessonId)
    }

    /**
     * Gets quizzes by category ID
     * @param categoryId The ID of the category
     * @return Flow of Quiz list for the specified category
     */
    fun getQuizzesByCategoryId(categoryId: Long): Flow<List<Quiz>> {
        return quizRepository.getQuizzesByCategoryId(categoryId)
    }

    /**
     * Creates a new quiz
     * @param quiz The quiz to create
     * @param questions List of questions for the quiz
     * @return The ID of the newly created quiz
     */
    suspend fun createQuiz(quiz: Quiz, questions: List<QuizQuestion>): Long {
        val quizId = quizRepository.insert(quiz)
        
        // Add questions to the quiz
        for (question in questions) {
            quizRepository.insertQuestion(question.copy(quizId = quizId))
        }
        
        return quizId
    }
}

/**
 * Data class representing the result of a quiz
 */
data class QuizResult(
    val quizId: Long,
    val score: Int,
    val totalQuestions: Int,
    val correctAnswers: Int,
    val completionTime: Int,
    val questionResults: List<QuestionResult>
)

/**
 * Data class representing the result of a single question
 */
data class QuestionResult(
    val questionId: Long,
    val userAnswer: String,
    val correctAnswer: String,
    val isCorrect: Boolean
)