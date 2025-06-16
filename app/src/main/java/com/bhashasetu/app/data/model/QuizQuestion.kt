package com.bhashasetu.app.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Index
import com.bhashasetu.app.data.model.Word

/**
 * Entity representing a question in a quiz.
 */
@Entity(
    tableName = "quiz_questions",
    indices = [
        Index(value = ["quizId"]),
        Index(value = ["wordId"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = Quiz::class,
            parentColumns = ["id"],
            childColumns = ["quizId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Word::class,
            parentColumns = ["id"],
            childColumns = ["wordId"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class QuizQuestion(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    // Associated quiz and word
    val quizId: Long,
    val wordId: Long? = null, // Associated word if any
    
    // Question content
    val questionTextEnglish: String,
    val questionTextHindi: String,
    
    // Question details
    val questionType: Int, // 1=Multiple choice, 2=True/False, 3=Fill-in-blank, etc.
    val correctAnswer: String,
    val options: String? = null, // JSON array of options for multiple choice
    val imageUrl: String? = null, // Optional image for the question
    val audioUrl: String? = null, // Optional audio for the question
    
    // Educational content
    val explanationEnglish: String? = null,
    val explanationHindi: String? = null,
    
    // Metadata
    val difficulty: Int = 1, // 1=Easy, 2=Medium, 3=Hard
    val points: Int = 1, // Points awarded for correct answer
    val orderInQuiz: Int = 0, // Position in the quiz
    
    // Statistics
    val correctAttempts: Int = 0,
    val totalAttempts: Int = 0
)