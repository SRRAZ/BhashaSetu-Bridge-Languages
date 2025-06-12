package com.bhashasetu.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity storing application settings with a singleton pattern.
 * Only one row should exist in this table.
 */
@Entity(tableName = "app_settings")
data class AppSettings(
    @PrimaryKey
    val id: Int = 1, // Singleton instance id
    
    // Language settings
    val interfaceLanguage: String = "en", // "en" or "hi"
    val primaryLanguage: String = "en", // Primary learning language
    val secondaryLanguage: String = "hi", // Secondary learning language
    val useDevanagariDigits: Boolean = false, // Use Hindi numerals (१, २, ३)
    
    // Display settings
    val theme: String = "system", // "light", "dark", or "system"
    val fontSizeMultiplier: Float = 1.0f, // Scaling factor for font sizes
    val highContrastMode: Boolean = false,
    
    // Learning settings
    val dailyWordGoal: Int = 5,
    val dailyTimeGoalMinutes: Int = 15,
    val reminderTime: String? = null, // HH:mm format
    val reminderDays: String = "1,2,3,4,5,6,7", // Days of week (1=Monday)
    
    // Audio settings
    val autoPlayPronunciation: Boolean = true,
    val pronunciationSpeed: Float = 1.0f,
    val autoRecordEnabled: Boolean = false,
    
    // Exercise settings
    val flashcardSessionSize: Int = 20, // Number of cards per session
    val quizTimeLimit: Int? = null, // Default time limit for quizzes (null=unlimited)
    val showTranslationHints: Boolean = true,
    
    // Notification settings
    val dailyReminderEnabled: Boolean = true,
    val streakReminderEnabled: Boolean = true,
    val achievementNotificationsEnabled: Boolean = true,
    val quizResultsNotificationsEnabled: Boolean = true,
    
    // User preferences
    val lastSelectedCategoryId: Long? = null,
    val lastSelectedLessonId: Long? = null,
    
    // Tracking
    val installDate: Long = System.currentTimeMillis(),
    val lastUpdatedAt: Long = System.currentTimeMillis()
)