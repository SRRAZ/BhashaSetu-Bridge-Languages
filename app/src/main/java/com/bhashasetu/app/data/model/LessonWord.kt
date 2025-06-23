package com.bhashasetu.app.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.bhashasetu.app.data.model.Lesson

/**
 * Junction entity creating many-to-many relationship between Lesson and Word.
 * ✅ ROOM ERROR FIX: Updated Word foreign key to use Java Word entity
 */
@Entity(
    tableName = "lesson_words",
    primaryKeys = ["lessonId", "wordId"],
    indices = [
        Index(value = ["lessonId"]),
        Index(value = ["wordId"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = Lesson::class,
            parentColumns = ["id"],
            childColumns = ["lessonId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = com.bhashasetu.app.model.Word::class, // ✅ Fixed: Use Java Word entity
            parentColumns = ["id"],
            childColumns = ["wordId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class LessonWord(
    val lessonId: Long,
    val wordId: Int, // ✅ Fixed: Changed to Int to match Java Word entity

    // Word positioning and metadata
    val orderInLesson: Int, // Position of word in the lesson
    val isKeyword: Boolean = false,
    val notes: String? = null,

    // Content flags
    val includeInQuiz: Boolean = true,
    val highlightInContent: Boolean = false
)