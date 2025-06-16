package com.bhashasetu.app.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.bhashasetu.app.data.model.Category
import com.bhashasetu.app.data.model.Word

/**
 * Junction entity to create many-to-many relationship between Word and Category.
 */
@Entity(
    tableName = "word_category_cross_refs",
    primaryKeys = ["wordId", "categoryId"],
    indices = [
        Index(value = ["wordId"]),
        Index(value = ["categoryId"])
              ],
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