package com.bhashasetu.app.data.repository

import androidx.lifecycle.LiveData
import com.bhashasetu.app.data.dao.SettingsDao
import com.bhashasetu.app.data.model.AppSettings
import kotlinx.coroutines.flow.Flow

class AppSettingsRepository(private val settingsDao: SettingsDao) {

    val settings: Flow<AppSettings> = settingsDao.getSettings()
    
    suspend fun insert(settings: AppSettings) {
        settingsDao.insert(settings)
    }
    
    suspend fun update(settings: AppSettings) {
        settingsDao.update(settings)
    }
    
    suspend fun updateLanguage(language: String) {
        settingsDao.updateLanguage(language)
    }
    
    suspend fun updateNotificationsEnabled(enabled: Boolean) {
        settingsDao.updateNotificationsEnabled(enabled)
    }
    
    suspend fun updateDailyReminderTime(time: String) {
        settingsDao.updateDailyReminderTime(time)
    }
    
    suspend fun updateSoundEnabled(enabled: Boolean) {
        settingsDao.updateSoundEnabled(enabled)
    }
    
    suspend fun updateVibrationEnabled(enabled: Boolean) {
        settingsDao.updateVibrationEnabled(enabled)
    }
}