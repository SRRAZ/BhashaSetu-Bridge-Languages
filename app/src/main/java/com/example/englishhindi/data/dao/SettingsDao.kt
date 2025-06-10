package com.bhashasetu.app.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bhashasetu.app.data.model.AppSettings
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(settings: AppSettings)

    @Update
    suspend fun update(settings: AppSettings)

    @Query("SELECT * FROM app_settings WHERE id = 1")
    fun getSettings(): Flow<AppSettings>

    @Query("UPDATE app_settings SET language = :language WHERE id = 1")
    suspend fun updateLanguage(language: String)

    @Query("UPDATE app_settings SET notificationsEnabled = :enabled WHERE id = 1")
    suspend fun updateNotificationsEnabled(enabled: Boolean)

    @Query("UPDATE app_settings SET dailyReminderTime = :time WHERE id = 1")
    suspend fun updateDailyReminderTime(time: String)

    @Query("UPDATE app_settings SET soundEnabled = :enabled WHERE id = 1")
    suspend fun updateSoundEnabled(enabled: Boolean)

    @Query("UPDATE app_settings SET vibrationEnabled = :enabled WHERE id = 1")
    suspend fun updateVibrationEnabled(enabled: Boolean)
}