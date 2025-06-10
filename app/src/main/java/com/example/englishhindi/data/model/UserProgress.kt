package com.bhashasetu.app.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.bhashasetu.app.data.model.Word

/**
 * Entity representing a user's progress with a specific vocabulary word.
 * Implements spaced repetition data model for optimized learning.
 */
@Entity(
    tableName = "user_progress",
    foreignKeys = [
        ForeignKey(
            entity = Word::class,
            parentColumns = ["id"],
            childColumns = ["wordId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class UserProgress(
    @PrimaryKey
    val wordId: Long,
    
    // Mastery metrics
    val proficiencyLevel: Int = 0, // 0-100
    val correctAttempts: Int = 0,
    val totalAttempts: Int = 0,
    
    // Spaced repetition algorithm fields
    val easeFactor: Float = 2.5f, // SM-2 algorithm ease factor
    val intervalDays: Int = 1, // Current interval in days
    val repetitions: Int = 0, // Number of successful reviews
    
    // Study history
    val lastAttemptAt: Long? = null,
    val lastCorrectAt: Long? = null,
    val nextReviewDue: Long? = null,
    
    // Additional tracking
    val timeSpentMs: Long = 0, // Cumulative time spent studying this word
    val lastConfidenceRating: Int = 0 // 0-4, user's self-assessment
)