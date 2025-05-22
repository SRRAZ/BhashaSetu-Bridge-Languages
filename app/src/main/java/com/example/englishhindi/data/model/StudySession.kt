package com.example.englishhindi.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Entity representing a study session with tracking for analytics.
 */
@Entity(
    tableName = "study_sessions",
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = Lesson::class,
            parentColumns = ["id"],
            childColumns = ["lessonId"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class StudySession(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    // Session context
    val sessionType: String, // "flashcard", "quiz", "lesson", "game", etc.
    val categoryId: Long? = null,
    val lessonId: Long? = null,
    
    // Session details
    val startTime: Long = System.currentTimeMillis(),
    val endTime: Long? = null,
    val durationMs: Long? = null, // Calculated duration
    
    // Performance metrics
    val itemCount: Int = 0, // Number of words/questions studied
    val correctCount: Int = 0,
    val score: Int? = null, // Final score if applicable
    
    // Technical details
    val deviceInfo: String? = null, // Basic device info for analytics
    val interfaceLanguage: String? = null, // Language used during session
    
    // Flags
    val isCompleted: Boolean = false,
    val wasPaused: Boolean = false
)