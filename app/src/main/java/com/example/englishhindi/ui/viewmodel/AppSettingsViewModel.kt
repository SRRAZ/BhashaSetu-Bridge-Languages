package com.bhashasetu.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhashasetu.app.data.model.AppSettings
import com.bhashasetu.app.data.repository.AppSettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppSettingsViewModel @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository
) : ViewModel() {

    val settings: StateFlow<AppSettings?> = appSettingsRepository.settings
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    /**
     * Updates the application language setting
     * @param language The language code to set ("en" or "hi")
     */
    fun updateLanguage(language: String) {
        viewModelScope.launch {
            appSettingsRepository.updateLanguage(language)
        }
    }

    /**
     * Updates the notifications enabled setting
     * @param enabled Whether notifications should be enabled
     */
    fun updateNotificationsEnabled(enabled: Boolean) {
        viewModelScope.launch {
            appSettingsRepository.updateNotificationsEnabled(enabled)
        }
    }

    /**
     * Updates the daily reminder time setting
     * @param time The time for daily reminders (format: "HH:MM")
     */
    fun updateDailyReminderTime(time: String) {
        viewModelScope.launch {
            appSettingsRepository.updateDailyReminderTime(time)
        }
    }

    /**
     * Updates the sound enabled setting
     * @param enabled Whether sound should be enabled
     */
    fun updateSoundEnabled(enabled: Boolean) {
        viewModelScope.launch {
            appSettingsRepository.updateSoundEnabled(enabled)
        }
    }

    /**
     * Updates the vibration enabled setting
     * @param enabled Whether vibration should be enabled
     */
    fun updateVibrationEnabled(enabled: Boolean) {
        viewModelScope.launch {
            appSettingsRepository.updateVibrationEnabled(enabled)
        }
    }

    /**
     * Updates all app settings at once
     * @param settings The settings object with new values
     */
    fun updateSettings(settings: AppSettings) {
        viewModelScope.launch {
            appSettingsRepository.update(settings)
        }
    }
}