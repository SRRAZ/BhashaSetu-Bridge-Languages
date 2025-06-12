package com.bhashasetu.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity representing an achievement that can be unlocked by users.
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
    
    // Secret achievement
    val isHidden: Boolean = false, // Whether achievement is visible before unlocking
    
    // Trigger conditions (stored as JSON)
    val triggerConditions: String? = null,
    
    // Rewards
    val pointsRewarded: Int = 0,
    val unlockMessage: String? = null,
    
    // Progress tracking
    val isUnlocked: Boolean = false,
    val currentProgress: Int = 0, // 0-maxProgress
    val unlockedAt: Long? = null
)