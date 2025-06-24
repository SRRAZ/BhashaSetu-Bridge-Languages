package com.bhashasetu.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_settings")
data class AppSettings(
    @PrimaryKey
    val id: Int = 1, // Single row for app settings

    // Basic App Settings
    val language: String = "en", // App interface language (en, hi)
    val darkModeEnabled: Boolean = false, // Dark mode preference
    val animationsEnabled: Boolean = true, // Enable/disable animations

    // Learning Preferences
    val difficultyLevel: String = "Beginner", // Learning difficulty level
    val dailyGoalMinutes: Int = 15, // Daily learning goal in minutes
    val autoAdvanceEnabled: Boolean = true, // Auto advance to next lesson
    val repeatIncorrectAnswers: Boolean = true, // Repeat wrong answers in quizzes

    // Notification Settings
    val notificationsEnabled: Boolean = true, // Master notification toggle
    val dailyReminderTime: String = "09:00", // Daily reminder time (HH:MM format)
    val achievementNotificationsEnabled: Boolean = true, // Achievement unlock notifications
    val streakReminderEnabled: Boolean = true, // Streak maintenance reminders
    val weeklyProgressEnabled: Boolean = true, // Weekly progress summaries

    // Audio & Visual Settings
    val audioEnabled: Boolean = true, // Enable audio pronunciations
    val autoPlayAudio: Boolean = false, // Automatically play audio
    val audioVolume: Float = 0.8f, // Audio volume (0.0 to 1.0)
    val speechRate: Float = 1.0f, // Text-to-speech rate
    val visualFeedbackEnabled: Boolean = true, // Visual feedback for interactions
    val highContrastMode: Boolean = false, // High contrast for accessibility

    // Learning Behavior Settings
    val offlineModeEnabled: Boolean = false, // Download content for offline learning
    val autoSyncEnabled: Boolean = true, // Auto sync progress to cloud
    val spacedRepetitionEnabled: Boolean = true, // Use spaced repetition algorithm
    val adaptiveDifficultyEnabled: Boolean = false, // Adjust difficulty based on performance

    // Privacy & Data Settings
    val analyticsEnabled: Boolean = true, // Usage analytics collection
    val crashReportingEnabled: Boolean = true, // Crash reporting
    val personalizedAdsEnabled: Boolean = false, // Personalized advertisements
    val dataCollectionEnabled: Boolean = true, // General data collection

    // Backup & Sync Settings
    val autoBackupEnabled: Boolean = true, // Automatic data backup
    val cloudSyncEnabled: Boolean = false, // Cloud synchronization
    val backupFrequency: String = "daily", // Backup frequency (daily, weekly, monthly)
    val lastBackupTime: Long = 0L, // Last backup timestamp

    // Advanced Settings
    val debugModeEnabled: Boolean = false, // Debug mode for development
    val betaFeaturesEnabled: Boolean = false, // Enable beta features
    val cacheSize: Int = 100, // Cache size in MB
    val maxOfflineContent: Int = 500, // Max offline content in MB

    // Study Session Settings
    val sessionTimeoutMinutes: Int = 30, // Auto-pause after inactivity
    val breakReminderEnabled: Boolean = true, // Remind to take breaks
    val breakIntervalMinutes: Int = 25, // Break reminder interval
    val focusModeEnabled: Boolean = false, // Distraction-free mode

    // Quiz & Assessment Settings
    val quizTimerEnabled: Boolean = true, // Enable quiz timers
    val defaultQuizTimeSeconds: Int = 30, // Default time per quiz question
    val showCorrectAnswers: Boolean = true, // Show correct answers after quiz
    val allowQuizRetakes: Boolean = true, // Allow unlimited quiz retakes
    val minimumQuizScore: Int = 70, // Minimum score to pass quiz

    // Gamification Settings
    val gamificationEnabled: Boolean = true, // Enable gamification features
    val achievementSoundEnabled: Boolean = true, // Achievement unlock sounds
    val streakCelebrationEnabled: Boolean = true, // Streak milestone celebrations
    val leaderboardEnabled: Boolean = false, // Participate in leaderboards
    val pointsSystemEnabled: Boolean = true, // Enable XP/points system

    // Accessibility Settings
    val fontSize: String = "medium", // Font size (small, medium, large, extra_large)
    val screenReaderSupport: Boolean = false, // Enhanced screen reader support
    val reduceMotionEnabled: Boolean = false, // Reduce motion for vestibular disorders
    val colorBlindnessSupport: String = "none", // Color blindness support (none, deuteranopia, protanopia, tritanopia)

    // Content Preferences
    val preferredContentTypes: List<String> = listOf("text", "audio", "visual"), // Preferred learning content types
    val culturalContextEnabled: Boolean = true, // Include cultural context in lessons
    val formalLanguageEnabled: Boolean = false, // Include formal language variants
    val dialectsEnabled: Boolean = false, // Include regional dialects

    // Performance Settings
    val preloadContent: Boolean = true, // Preload next lesson content
    val reducedAnimations: Boolean = false, // Reduce animations for performance
    val lowDataMode: Boolean = false, // Reduce data usage
    val offlineFirstMode: Boolean = false, // Prefer offline content when available

    // Social Features
    val socialFeaturesEnabled: Boolean = false, // Enable social features
    val profileVisibilityPublic: Boolean = false, // Public profile visibility
    val shareProgressEnabled: Boolean = true, // Allow sharing progress
    val friendRequestsEnabled: Boolean = false, // Allow friend requests

    // Content Filtering
    val contentFilterLevel: String = "moderate", // Content filtering (none, light, moderate, strict)
    val adultContentBlocked: Boolean = true, // Block adult content
    val violenceContentBlocked: Boolean = true, // Block violent content
    val profanityFilterEnabled: Boolean = true, // Filter profanity

    // Export/Import Settings
    val dataExportFormat: String = "json", // Preferred export format (json, csv, xml)
    val includePersonalDataInExport: Boolean = false, // Include personal data in exports
    val autoExportEnabled: Boolean = false, // Automatic data export
    val exportFrequency: String = "monthly", // Export frequency

    // Regional Settings
    val region: String = "IN", // User's region/country
    val timeZone: String = "Asia/Kolkata", // User's timezone
    val dateFormat: String = "dd/MM/yyyy", // Preferred date format
    val timeFormat: String = "24h", // Time format (12h, 24h)
    val numberFormat: String = "IN", // Number format locale

    // Experimental Features
    val aiTutorEnabled: Boolean = false, // AI-powered tutoring
    val voiceRecognitionEnabled: Boolean = true, // Voice recognition for pronunciation
    val handwritingRecognitionEnabled: Boolean = false, // Handwriting recognition
    val augmentedRealityEnabled: Boolean = false, // AR features
    val machineTranslationEnabled: Boolean = true, // Machine translation fallback

    // Tracking & Analytics (detailed)
    val sessionTrackingEnabled: Boolean = true, // Track learning sessions
    val performanceTrackingEnabled: Boolean = true, // Track learning performance
    val timeTrackingEnabled: Boolean = true, // Track time spent learning
    val errorTrackingEnabled: Boolean = true, // Track and analyze errors
    val progressTrackingEnabled: Boolean = true, // Track overall progress

    // Settings Metadata
    val settingsVersion: Int = 1, // Settings schema version for migrations
    val lastUpdated: Long = System.currentTimeMillis(), // Last settings update
    val isFirstLaunch: Boolean = true, // First app launch flag
    val setupCompleted: Boolean = false, // Initial setup completion
    val migrationCompleted: Boolean = true // Data migration status
) {
    /**
     * Returns a copy with updated timestamp
     */
    fun withUpdatedTimestamp(): AppSettings {
        return this.copy(lastUpdated = System.currentTimeMillis())
    }

    /**
     * Validates settings and returns corrected version
     */
    fun validated(): AppSettings {
        return this.copy(
            dailyGoalMinutes = dailyGoalMinutes.coerceIn(5, 180),
            audioVolume = audioVolume.coerceIn(0f, 1f),
            speechRate = speechRate.coerceIn(0.5f, 2.0f),
            cacheSize = cacheSize.coerceIn(50, 1000),
            maxOfflineContent = maxOfflineContent.coerceIn(100, 2000),
            sessionTimeoutMinutes = sessionTimeoutMinutes.coerceIn(5, 120),
            breakIntervalMinutes = breakIntervalMinutes.coerceIn(10, 60),
            defaultQuizTimeSeconds = defaultQuizTimeSeconds.coerceIn(10, 300),
            minimumQuizScore = minimumQuizScore.coerceIn(0, 100),
            difficultyLevel = if (difficultyLevel in listOf("Beginner", "Intermediate", "Advanced")) {
                difficultyLevel
            } else {
                "Beginner"
            },
            language = if (language in listOf("en", "hi")) language else "en"
        )
    }

    /**
     * Checks if accessibility features are enabled
     */
    fun hasAccessibilityFeaturesEnabled(): Boolean {
        return screenReaderSupport || reduceMotionEnabled ||
                highContrastMode || colorBlindnessSupport != "none" ||
                fontSize in listOf("large", "extra_large")
    }

    /**
     * Checks if privacy-focused settings are enabled
     */
    fun isPrivacyFocused(): Boolean {
        return !analyticsEnabled || !crashReportingEnabled ||
                !dataCollectionEnabled || !personalizedAdsEnabled
    }

    /**
     * Gets the effective theme mode
     */
    fun getEffectiveTheme(): String {
        return if (darkModeEnabled) "dark" else "light"
    }

    /**
     * Gets notification settings summary
     */
    fun getNotificationSummary(): String {
        if (!notificationsEnabled) return "Disabled"

        val enabledTypes = mutableListOf<String>()
        if (achievementNotificationsEnabled) enabledTypes.add("Achievements")
        if (streakReminderEnabled) enabledTypes.add("Streaks")
        if (weeklyProgressEnabled) enabledTypes.add("Progress")

        return if (enabledTypes.isNotEmpty()) {
            enabledTypes.joinToString(", ")
        } else {
            "Basic only"
        }
    }

    /**
     * Gets learning preferences summary
     */
    fun getLearningPreferencesSummary(): String {
        return buildString {
            append(difficultyLevel)
            append(" • ${dailyGoalMinutes}min/day")
            if (spacedRepetitionEnabled) append(" • Spaced Repetition")
            if (adaptiveDifficultyEnabled) append(" • Adaptive")
        }
    }
}

// Extension functions for settings groups
fun AppSettings.getNotificationSettings(): NotificationSettings {
    return NotificationSettings(
        enabled = notificationsEnabled,
        achievementNotifications = achievementNotificationsEnabled,
        dailyReminderTime = dailyReminderTime,
        soundEnabled = audioEnabled
    )
}

fun AppSettings.getLearningSettings(): LearningSettings {
    return LearningSettings(
        difficultyLevel = difficultyLevel,
        dailyGoalMinutes = dailyGoalMinutes,
        language = language,
        offlineModeEnabled = offlineModeEnabled
    )
}

fun AppSettings.getPrivacySettings(): PrivacySettings {
    return PrivacySettings(
        analyticsEnabled = analyticsEnabled,
        crashReportingEnabled = crashReportingEnabled,
        personalizedAdsEnabled = personalizedAdsEnabled
    )
}

// Settings validation utilities
object AppSettingsValidator {
    fun validate(settings: AppSettings): List<String> {
        val errors = mutableListOf<String>()

        if (settings.dailyGoalMinutes !in 5..180) {
            errors.add("Daily goal must be between 5 and 180 minutes")
        }

        if (settings.audioVolume !in 0f..1f) {
            errors.add("Audio volume must be between 0.0 and 1.0")
        }

        if (settings.language !in listOf("en", "hi")) {
            errors.add("Unsupported language: ${settings.language}")
        }

        if (settings.difficultyLevel !in listOf("Beginner", "Intermediate", "Advanced")) {
            errors.add("Invalid difficulty level: ${settings.difficultyLevel}")
        }

        return errors
    }

    fun isValid(settings: AppSettings): Boolean {
        return validate(settings).isEmpty()
    }
}