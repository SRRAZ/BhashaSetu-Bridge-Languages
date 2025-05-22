package com.example.englishhindi.data.repository

import androidx.lifecycle.LiveData
import com.example.englishhindi.data.dao.SettingsDao
import com.example.englishhindi.data.model.AppSettings
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