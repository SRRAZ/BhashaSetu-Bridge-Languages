package com.bhashasetu.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhashasetu.app.data.model.AppSettings
import com.bhashasetu.app.data.repository.AppSettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppSettingsViewModel @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository
) : ViewModel() {

    // Main settings flow
    val settings = appSettingsRepository.getSettings()
        .catch { e ->
            _error.value = "Failed to load settings: ${e.message}"
            emit(AppSettings()) // Emit default settings on error
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AppSettings()
        )

    // Error handling
    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    // Loading states
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isSaving = MutableStateFlow(false)
    val isSaving = _isSaving.asStateFlow()

    // Derived settings for specific UI components
    val notificationSettings = settings.map { settings ->
        NotificationSettings(
            enabled = settings.notificationsEnabled,
            achievementNotifications = settings.achievementNotificationsEnabled,
            dailyReminderTime = settings.dailyReminderTime,
            soundEnabled = settings.audioEnabled
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = NotificationSettings()
    )

    val learningSettings = settings.map { settings ->
        LearningSettings(
            difficultyLevel = settings.difficultyLevel,
            dailyGoalMinutes = settings.dailyGoalMinutes,
            language = settings.language,
            offlineModeEnabled = settings.offlineModeEnabled
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = LearningSettings()
    )

    val privacySettings = settings.map { settings ->
        PrivacySettings(
            analyticsEnabled = settings.analyticsEnabled,
            crashReportingEnabled = settings.crashReportingEnabled,
            personalizedAdsEnabled = settings.personalizedAdsEnabled
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PrivacySettings()
    )

    /**
     * Updates the app language setting
     * @param language The language code ("en", "hi", etc.)
     */
    fun updateLanguage(language: String) {
        updateSettingsField { currentSettings ->
            currentSettings.copy(language = language)
        }
    }

    /**
     * Updates the daily goal minutes setting
     * @param minutes The daily goal in minutes
     */
    fun updateDailyGoalMinutes(minutes: Int) {
        updateSettingsField { currentSettings ->
            currentSettings.copy(dailyGoalMinutes = minutes)
        }
    }

    /**
     * Updates the difficulty level setting
     * @param level The difficulty level ("Beginner", "Intermediate", "Advanced")
     */
    fun updateDifficultyLevel(level: String) {
        updateSettingsField { currentSettings ->
            currentSettings.copy(difficultyLevel = level)
        }
    }

    /**
     * Updates the dark mode setting
     * @param enabled Whether dark mode should be enabled
     */
    fun updateDarkMode(enabled: Boolean) {
        updateSettingsField { currentSettings ->
            currentSettings.copy(darkModeEnabled = enabled)
        }
    }

    /**
     * Updates the notification setting
     * @param enabled Whether notifications should be enabled
     */
    fun updateNotifications(enabled: Boolean) {
        updateSettingsField { currentSettings ->
            currentSettings.copy(notificationsEnabled = enabled)
        }
    }

    /**
     * Updates the achievement notifications setting
     * @param enabled Whether achievement notifications should be enabled
     */
    fun updateAchievementNotifications(enabled: Boolean) {
        updateSettingsField { currentSettings ->
            currentSettings.copy(achievementNotificationsEnabled = enabled)
        }
    }

    /**
     * Updates the offline mode setting
     * @param enabled Whether offline mode should be enabled
     */
    fun updateOfflineMode(enabled: Boolean) {
        updateSettingsField { currentSettings ->
            currentSettings.copy(offlineModeEnabled = enabled)
        }
    }

    /**
     * Updates the analytics setting
     * @param enabled Whether analytics should be enabled
     */
    fun updateAnalytics(enabled: Boolean) {
        updateSettingsField { currentSettings ->
            currentSettings.copy(analyticsEnabled = enabled)
        }
    }

    /**
     * Updates the audio enabled setting
     * @param enabled Whether audio should be enabled
     */
    fun updateAudioEnabled(enabled: Boolean) {
        updateSettingsField { currentSettings ->
            currentSettings.copy(audioEnabled = enabled)
        }
    }

    /**
     * Updates the animations setting
     * @param enabled Whether animations should be enabled
     */
    fun updateAnimations(enabled: Boolean) {
        updateSettingsField { currentSettings ->
            currentSettings.copy(animationsEnabled = enabled)
        }
    }

    /**
     * Updates the reminder time setting
     * @param time The reminder time (format: "HH:MM")
     */
    fun updateReminderTime(time: String) {
        updateSettingsField { currentSettings ->
            currentSettings.copy(dailyReminderTime = time)
        }
    }

    /**
     * Updates the crash reporting setting
     * @param enabled Whether crash reporting should be enabled
     */
    fun updateCrashReporting(enabled: Boolean) {
        updateSettingsField { currentSettings ->
            currentSettings.copy(crashReportingEnabled = enabled)
        }
    }

    /**
     * Updates the personalized ads setting
     * @param enabled Whether personalized ads should be enabled
     */
    fun updatePersonalizedAds(enabled: Boolean) {
        updateSettingsField { currentSettings ->
            currentSettings.copy(personalizedAdsEnabled = enabled)
        }
    }

    /**
     * Updates the auto backup setting
     * @param enabled Whether auto backup should be enabled
     */
    fun updateAutoBackup(enabled: Boolean) {
        updateSettingsField { currentSettings ->
            currentSettings.copy(autoBackupEnabled = enabled)
        }
    }

    /**
     * Updates the cloud sync setting
     * @param enabled Whether cloud sync should be enabled
     */
    fun updateCloudSync(enabled: Boolean) {
        updateSettingsField { currentSettings ->
            currentSettings.copy(cloudSyncEnabled = enabled)
        }
    }

    /**
     * Updates multiple settings at once
     * @param updates A map of setting keys to their new values
     */
    fun updateMultipleSettings(updates: Map<String, Any>) {
        updateSettingsField { currentSettings ->
            var updatedSettings = currentSettings

            updates.forEach { (key, value) ->
                updatedSettings = when (key) {
                    "language" -> updatedSettings.copy(language = value as String)
                    "darkMode" -> updatedSettings.copy(darkModeEnabled = value as Boolean)
                    "notifications" -> updatedSettings.copy(notificationsEnabled = value as Boolean)
                    "audio" -> updatedSettings.copy(audioEnabled = value as Boolean)
                    "animations" -> updatedSettings.copy(animationsEnabled = value as Boolean)
                    "analytics" -> updatedSettings.copy(analyticsEnabled = value as Boolean)
                    "offlineMode" -> updatedSettings.copy(offlineModeEnabled = value as Boolean)
                    "dailyGoal" -> updatedSettings.copy(dailyGoalMinutes = value as Int)
                    "difficulty" -> updatedSettings.copy(difficultyLevel = value as String)
                    "reminderTime" -> updatedSettings.copy(dailyReminderTime = value as String)
                    else -> updatedSettings
                }
            }

            updatedSettings
        }
    }

    /**
     * Resets all settings to their default values
     */
    fun resetToDefaults() {
        updateSettingsField {
            AppSettings() // Return default AppSettings instance
        }
    }

    /**
     * Exports current settings as JSON string
     * @return JSON representation of current settings
     */
    fun exportSettings(): Flow<String> {
        return settings.map { currentSettings ->
            // TODO: Implement JSON serialization
            // For now, return a simple string representation
            buildString {
                appendLine("BhashaSetu Settings Export")
                appendLine("Language: ${currentSettings.language}")
                appendLine("Difficulty: ${currentSettings.difficultyLevel}")
                appendLine("Daily Goal: ${currentSettings.dailyGoalMinutes} minutes")
                appendLine("Dark Mode: ${currentSettings.darkModeEnabled}")
                appendLine("Notifications: ${currentSettings.notificationsEnabled}")
                appendLine("Audio: ${currentSettings.audioEnabled}")
                appendLine("Analytics: ${currentSettings.analyticsEnabled}")
                appendLine("Offline Mode: ${currentSettings.offlineModeEnabled}")
            }
        }
    }

    /**
     * Imports settings from JSON string
     * @param jsonString JSON representation of settings
     */
    fun importSettings(jsonString: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                // TODO: Implement JSON deserialization and validation
                // For now, just reset to defaults
                resetToDefaults()
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Failed to import settings: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Validates current settings and fixes any issues
     */
    fun validateAndFixSettings() {
        updateSettingsField { currentSettings ->
            currentSettings.copy(
                dailyGoalMinutes = currentSettings.dailyGoalMinutes.coerceIn(5, 120),
                difficultyLevel = if (currentSettings.difficultyLevel in listOf("Beginner", "Intermediate", "Advanced")) {
                    currentSettings.difficultyLevel
                } else {
                    "Beginner"
                },
                language = if (currentSettings.language in listOf("en", "hi")) {
                    currentSettings.language
                } else {
                    "en"
                }
            )
        }
    }

    /**
     * Gets setting recommendations based on user behavior
     * @return Flow of setting recommendations
     */
    fun getSettingRecommendations(): Flow<List<SettingRecommendation>> {
        return settings.map { currentSettings ->
            val recommendations = mutableListOf<SettingRecommendation>()

            // Recommend enabling notifications if disabled
            if (!currentSettings.notificationsEnabled) {
                recommendations.add(
                    SettingRecommendation(
                        title = "Enable Notifications",
                        description = "Get daily reminders to maintain your learning streak",
                        action = "notifications",
                        priority = SettingRecommendation.Priority.MEDIUM
                    )
                )
            }

            // Recommend offline mode for better experience
            if (!currentSettings.offlineModeEnabled) {
                recommendations.add(
                    SettingRecommendation(
                        title = "Enable Offline Mode",
                        description = "Download content to learn anywhere, anytime",
                        action = "offlineMode",
                        priority = SettingRecommendation.Priority.LOW
                    )
                )
            }

            // Recommend higher daily goal if current is very low
            if (currentSettings.dailyGoalMinutes < 10) {
                recommendations.add(
                    SettingRecommendation(
                        title = "Increase Daily Goal",
                        description = "Aim for at least 15 minutes daily for better progress",
                        action = "dailyGoal",
                        priority = SettingRecommendation.Priority.HIGH
                    )
                )
            }

            recommendations
        }
    }

    /**
     * Clears current error
     */
    fun clearError() {
        _error.value = null
    }

    /**
     * Generic function to update any settings field
     * @param update Function that returns updated AppSettings
     */
    private fun updateSettingsField(update: (AppSettings) -> AppSettings) {
        viewModelScope.launch {
            try {
                _isSaving.value = true
                val currentSettings = settings.value
                val updatedSettings = update(currentSettings)
                appSettingsRepository.updateSettings(updatedSettings)
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Failed to update settings: ${e.message}"
            } finally {
                _isSaving.value = false
            }
        }
    }
}

// Data classes for grouped settings
data class NotificationSettings(
    val enabled: Boolean = true,
    val achievementNotifications: Boolean = true,
    val dailyReminderTime: String = "09:00",
    val soundEnabled: Boolean = true
)

data class LearningSettings(
    val difficultyLevel: String = "Beginner",
    val dailyGoalMinutes: Int = 15,
    val language: String = "en",
    val offlineModeEnabled: Boolean = false
)

data class PrivacySettings(
    val analyticsEnabled: Boolean = true,
    val crashReportingEnabled: Boolean = true,
    val personalizedAdsEnabled: Boolean = false
)

data class SettingRecommendation(
    val title: String,
    val description: String,
    val action: String,
    val priority: Priority
) {
    enum class Priority {
        LOW, MEDIUM, HIGH
    }
}