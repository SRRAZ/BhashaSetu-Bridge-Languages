package com.bhashasetu.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity representing a modern bilingual achievement that can be unlocked by users.
 */
@Entity(tableName = "achievements")
data class Achievement(
    @PrimaryKey
    val id: String, // Using string ID for predefined achievements

    // Bilingual names and descriptions
    val titleEnglish: String,
    val titleHindi: String,
    val descriptionEnglish: String,
    val descriptionHindi: String,

    // Visual elements
    val iconResId: Int,
    val badgeImageResId: Int? = null,
    val colorHex: String? = null,

    // Achievement type and progression
    val category: String, // e.g., "Learning", "Vocabulary", "Streak"
    val type: String, // e.g., "milestone", "skill", "collection"
    val maxProgress: Int = 100, // Total progress needed to unlock
    val currentProgress: Int = 0, // Current progress towards unlocking
    val pointsRewarded: Int = 0, // Points awarded when unlocked

    // Unlock status
    val isUnlocked: Boolean = false,
    val unlockedAt: Long? = null, // Timestamp when unlocked

    // Secret achievement
    val isHidden: Boolean = false, // Hidden until unlocked
    val unlockMessage: String? = null, // Custom message shown when unlocked

    // Advanced features
    val triggerConditions: String? = null // JSON string with trigger conditions
) {
    // Kotlin data classes automatically generate proper getters/setters for Room
    // No @Ignore annotations needed for computed properties in Kotlin

    fun getProgressPercentage(): Double {
        return if (maxProgress == 0) 0.0 else (currentProgress.toDouble() / maxProgress) * 100.0
    }

    fun isNearCompletion(threshold: Double = 80.0): Boolean {
        return getProgressPercentage() >= threshold && !isUnlocked
    }

    fun canBeUnlocked(): Boolean {
        return currentProgress >= maxProgress && !isUnlocked
    }
}