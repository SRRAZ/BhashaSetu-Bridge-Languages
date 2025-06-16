package com.bhashasetu.app.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.bhashasetu.app.data.model.Lesson
import com.bhashasetu.app.data.model.Word

/**
 * Junction entity creating many-to-many relationship between Lesson and Word.
 * Includes additional metadata about the word's position in the lesson.
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
            entity = Word::class,
            parentColumns = ["id"],
            childColumns = ["wordId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class LessonWord(
    val lessonId: Long,
    val wordId: Long,

    // Word positioning and metadata
    val orderInLesson: Int, // Position of word in the lesson
    val isKeyword: Boolean = false, // Whether it's a focus word for the lesson
    val notes: String? = null, // Additional context for this word in this lesson

    // Content flags
    val includeInQuiz: Boolean = true, // Whether to include in lesson quiz
    val highlightInContent: Boolean = false // Whether to highlight in lesson content
)