package com.bhashasetu.app.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.bhashasetu.app.data.model.Category

/**
 * Entity representing a user-defined learning goal.
 */
@Entity(
    tableName = "user_goals",
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
    
    // Target metrics
    val targetValue: Int, // Number to reach (words, minutes, etc.)
    val currentValue: Int = 0, // Current progress
    val categoryId: Long? = null, // If goal is specific to a category
    
    // Time period
    val periodType: String, // "daily", "weekly", "monthly", "total"
    val startDate: Long = System.currentTimeMillis(),
    val endDate: Long? = null, // Set for time-bound goals
    val repeatDaily: Boolean = false, // Whether goal resets daily
    
    // Reminders
    val reminderEnabled: Boolean = false,
    val reminderTime: String? = null, // HH:mm format
    
    // Status
    val isCompleted: Boolean = false,
    val completedAt: Long? = null,
    val isActive: Boolean = true,
    
    // Creation metadata
    val createdAt: Long = System.currentTimeMillis(),
    val lastUpdatedAt: Long = System.currentTimeMillis()
)