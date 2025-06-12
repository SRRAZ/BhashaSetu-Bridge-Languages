package com.bhashasetu.app.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/*
 * Entity representing a quiz for testing vocabulary knowledge.
 */
@Entity(
    tableName = "quizzes",
    foreignKeys = [
        ForeignKey(
            entity = Lesson::class,
            parentColumns = ["id"], // Corrected parentColumns
            childColumns = ["lessonId"],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"], // Corrected parentColumns
            childColumns = ["categoryId"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class Quiz(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    // Bilingual titles and instructions
    val titleEnglish: String,
    val titleHindi: String,
    val instructionsEnglish: String? = null,
    val instructionsHindi: String? = null,
    
    // Quiz metadata
    val lessonId: Long? = null, // Associated lesson if any
    val categoryId: Long? = null, // Associated category if any
    val difficulty: Int = 1, // 1=Beginner, 2=Intermediate, 3=Advanced
    val questionCount: Int = 10, // Total questions in the quiz
    val timeLimit: Int? = null, // Time limit in seconds, null for unlimited
    val passingScorePercent: Int = 70, // Percentage needed to pass
    
    // Quiz type
    val quizType: Int = 0, // 0=Mixed, 1=Multiple Choice, 2=Fill Blank, etc.
    val directionType: Int = 0, // 0=Both, 1=English->Hindi, 2=Hindi->English
    
    // Progress tracking
    val isCompleted: Boolean = false,
    val lastAttemptScore: Int? = null,
    val bestScore: Int? = null,
    val attemptCount: Int = 0,
    val lastAttemptAt: Long? = null,
    val completedAt: Long? = null,
    
    // Creation metadata
    val createdAt: Long = System.currentTimeMillis(),
    val isSystemQuiz: Boolean = false // Whether it's a pre-installed quiz
)