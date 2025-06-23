package com.bhashasetu.app.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.bhashasetu.app.model.Lesson
import com.bhashasetu.app.model.Word

/**
 * ✅ FIXED: Junction table for many-to-many relationship between Lessons and Words.
 * Uses correct entity references to resolve build conflicts.
 */
@Entity(
    tableName = "lesson_words",
    indices = [
        Index(value = ["lessonId"]),
        Index(value = ["wordId"]),
        Index(value = ["lessonId", "wordId"], unique = true)
    ],
    foreignKeys = [
        ForeignKey(
            entity = Lesson::class,
            parentColumns = ["id"],
            childColumns = ["lessonId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Word::class, // ✅ References the fixed Word entity
            parentColumns = ["id"],
            childColumns = ["wordId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class LessonWord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val lessonId: Long,
    val wordId: Long,
    val orderInLesson: Int = 0,
    val isKeyword: Boolean = false,
    val includeInQuiz: Boolean = true,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)