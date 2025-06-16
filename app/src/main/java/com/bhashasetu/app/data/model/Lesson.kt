package com.bhashasetu.app.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Index
import com.bhashasetu.app.data.model.Category

/**
 * Entity representing a learning lesson containing vocabulary words and content.
 */
@Entity(
    tableName = "lessons",
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
data class Lesson(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    // Bilingual titles and content
    val titleEnglish: String,
    val titleHindi: String,
    val descriptionEnglish: String? = null,
    val descriptionHindi: String? = null,
    val contentEnglish: String? = null, // Can contain HTML formatting
    val contentHindi: String? = null, // Can contain HTML formatting
    
    // Lesson metadata
    val categoryId: Long? = null,
    val difficulty: Int = 1, // 1=Beginner, 2=Intermediate, 3=Advanced
    val orderInCategory: Int = 0, // Position within the category
    val estimatedDurationMinutes: Int = 10,
    
    // Progress tracking
    val isCompleted: Boolean = false,
    val completedAt: Long? = null,
    val lastAccessedAt: Long? = null,
    
    // Creation metadata
    val createdAt: Long = System.currentTimeMillis(),
    val lastModifiedAt: Long = System.currentTimeMillis(),
    val isSystemLesson: Boolean = false // Whether it's a pre-installed lesson
)