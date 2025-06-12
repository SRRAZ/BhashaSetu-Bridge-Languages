package com.bhashasetu.app.data.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.bhashasetu.app.data.model.Category
import com.bhashasetu.app.data.model.Word
import com.bhashasetu.app.data.model.WordCategoryCrossRef

/**
 * Represents a Category with all its associated Words.
 * This is a Room relationship helper class for retrieving a category with all its words.
 */
data class CategoryWithWords(
    @Embedded val category: Category,
    
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = WordCategoryCrossRef::class,
            parentColumn = "categoryId",
            entityColumn = "wordId"
        )
    )
    val words: List<Word>
)