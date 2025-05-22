package com.example.englishhindi.data.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.englishhindi.data.model.Quiz
import com.example.englishhindi.data.model.QuizQuestion

/**
 * Represents a Quiz with all its associated Questions.
 * This is a Room relationship helper class for retrieving a quiz with all its questions.
 */
data class QuizWithQuestions(
    @Embedded val quiz: Quiz,
    
    @Relation(
        parentColumn = "id",
        entityColumn = "quizId"
    )
    val questions: List<QuizQuestion>
) {
    /**
     * Returns the questions in the order specified in the quiz.
     */
    fun getOrderedQuestions(): List<QuizQuestion> {
        return questions.sortedBy { it.orderInQuiz }
    }
    
    /**
     * Returns the number of questions in the quiz.
     */
    fun getQuestionCount(): Int = questions.size
    
    /**
     * Returns the total possible score for the quiz.
     */
    fun getTotalPossibleScore(): Int = questions.sumOf { it.points }
    
    /**
     * Returns the expected duration of the quiz in seconds.
     * Uses estimated time per question based on question type.
     */
    fun getEstimatedDurationSeconds(): Int {
        // If quiz has a time limit, return that
        quiz.timeLimit?.let { return it }
        
        // Otherwise estimate based on question types
        return questions.sumOf { question ->
            when (question.questionType) {
                1 -> 20 // Multiple choice: 20 seconds
                2 -> 15 // True/False: 15 seconds
                3 -> 30 // Fill-in-blank: 30 seconds
                else -> 25 // Default: 25 seconds
            }
        }
    }
    
    /**
     * Returns whether the quiz is passing based on the last attempt score.
     */
    fun isPassing(): Boolean {
        val lastScore = quiz.lastAttemptScore ?: return false
        val totalPossible = getTotalPossibleScore()
        if (totalPossible == 0) return false
        
        val percentage = (lastScore * 100) / totalPossible
        return percentage >= quiz.passingScorePercent
    }
}