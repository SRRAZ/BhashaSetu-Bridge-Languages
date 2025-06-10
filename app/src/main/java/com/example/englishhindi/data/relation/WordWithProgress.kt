package com.bhashasetu.app.data.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.bhashasetu.app.data.model.UserProgress
import com.bhashasetu.app.data.model.Word

/**
 * Represents a Word with its associated UserProgress.
 * This is a Room relationship helper class for retrieving a word with its learning progress.
 */
data class WordWithProgress(
    @Embedded val word: Word,
    
    @Relation(
        parentColumn = "id",
        entityColumn = "wordId"
    )
    val progress: UserProgress?
) {
    /**
     * Returns the word's proficiency level (0-100) or 0 if no progress exists.
     */
    fun getProficiencyLevel(): Int = progress?.proficiencyLevel ?: 0
    
    /**
     * Returns whether the word is due for review based on next review date.
     */
    fun isDueForReview(): Boolean {
        val nextReview = progress?.nextReviewDue ?: return true
        return System.currentTimeMillis() >= nextReview
    }
    
    /**
     * Returns whether the word has been started (at least one attempt).
     */
    fun hasBeenStarted(): Boolean = progress != null && progress.totalAttempts > 0
    
    /**
     * Returns the mastery status of the word.
     * @return One of: "new", "learning", "reviewing", "mastered"
     */
    fun getMasteryStatus(): String {
        if (progress == null || progress.totalAttempts == 0) return "new"
        
        return when (progress.proficiencyLevel) {
            in 0..25 -> "learning"
            in 26..85 -> "reviewing"
            else -> "mastered"
        }
    }
}