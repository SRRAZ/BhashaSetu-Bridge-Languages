package com.example.englishhindi.data.model

import androidx.room.Entity
import androidx.room.ForeignKey

/**
 * Junction entity to create many-to-many relationship between Word and Category.
 */
@Entity(
    tableName = "word_category_cross_refs",
    primaryKeys = ["wordId", "categoryId"],
    foreignKeys = [
        ForeignKey(
            entity = Word::class,
            parentColumns = ["id"],
            childColumns = ["wordId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class WordCategoryCrossRef(
    val wordId: Long,
    val categoryId: Long,
    val addedAt: Long = System.currentTimeMillis()
)