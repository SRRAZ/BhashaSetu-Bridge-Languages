package com.bhashasetu.app.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Index
import androidx.room.Ignore

/**
 * Entity representing a user-defined learning goal.
 *
 * ✅ ROOM ERROR FIX: Removed @Ignore field conflicts and constructor parameter mismatches
 * - Removed duplicate field purposes (progress/currentValue, targetDate/endDate, etc.)
 * - Simplified constructor to avoid Room confusion
 * - Used consistent field names throughout
 */
@Entity(
    tableName = "user_goals",
    indices = [
        Index(value = ["categoryId"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class UserGoal(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    // Goal details
    val title: String,
    val description: String? = null,
    val goalType: String, // "words", "time", "lessons", "streak", "custom"

    // Target metrics (using consistent naming)
    val targetValue: Int, // Number to reach (words, minutes, etc.)
    val currentValue: Int = 0, // Current progress (renamed from conflicting fields)
    val categoryId: Long? = null, // If goal is specific to a category

    // Time period (using consistent naming)
    val periodType: String, // "daily", "weekly", "monthly", "total"
    val startDate: Long = System.currentTimeMillis(),
    val endDate: Long? = null, // Renamed from targetDate to avoid conflicts
    val repeatDaily: Boolean = false, // Whether goal resets daily

    // Reminders
    val reminderEnabled: Boolean = false,
    val reminderTime: String? = null, // HH:mm format

    // Status (using consistent naming)
    val completed: Boolean = false, // Renamed from isCompleted to avoid getter conflicts
    val completedAt: Long? = null, // Renamed from completedDate to avoid conflicts
    val isActive: Boolean = true,

    // Creation metadata
    val createdAt: Long = System.currentTimeMillis(),
    val lastUpdatedAt: Long = System.currentTimeMillis()
) {
    // ✅ COMPUTED PROPERTIES (using @get:Ignore to avoid Room confusion)
    @get:Ignore
    val progress: Double
        get() = if (targetValue == 0) 0.0 else (currentValue.toDouble() / targetValue) * 100.0

    @get:Ignore
    val isCompleted: Boolean
        get() = completed

    @get:Ignore
    val progressPercentage: Int
        get() = progress.toInt().coerceIn(0, 100)

    @get:Ignore
    val isNearCompletion: Boolean
        get() = progress >= 80.0 && !completed

    @get:Ignore
    val canBeCompleted: Boolean
        get() = currentValue >= targetValue && !completed
}