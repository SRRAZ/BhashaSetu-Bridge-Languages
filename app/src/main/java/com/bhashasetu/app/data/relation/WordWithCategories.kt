package com.bhashasetu.app.data.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.bhashasetu.app.data.model.Category
import com.bhashasetu.app.data.model.Word
import com.bhashasetu.app.data.model.WordCategoryCrossRef

/**
 * Represents a Word with its associated Categories.
 * This is a Room relationship helper class for retrieving a word with all its categories.
 */
data class WordWithCategories(
    @Embedded val word: Word,
    
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = WordCategoryCrossRef::class,
            parentColumn = "wordId",
            entityColumn = "categoryId"
        )
    )
    val categories: List<Category>
)