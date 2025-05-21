package com.example.englishhindi.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

/**
 * Manages user preferences with Kotlin Flow compatibility
 */
class PreferenceManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    
    /**
     * Save a boolean preference
     */
    fun setBoolean(key: String, value: Boolean) = prefs.edit { putBoolean(key, value) }
    
    /**
     * Get a boolean preference with default value
     */
    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean = prefs.getBoolean(key, defaultValue)
    
    /**
     * Save a string preference
     */
    fun setString(key: String, value: String) = prefs.edit { putString(key, value) }
    
    /**
     * Get a string preference with default value
     */
    fun getString(key: String, defaultValue: String = ""): String = prefs.getString(key, defaultValue) ?: defaultValue
    
    /**
     * Save an integer preference
     */
    fun setInt(key: String, value: Int) = prefs.edit { putInt(key, value) }
    
    /**
     * Get an integer preference with default value
     */
    fun getInt(key: String, defaultValue: Int = 0): Int = prefs.getInt(key, defaultValue)
    
    /**
     * Save a long preference
     */
    fun setLong(key: String, value: Long) = prefs.edit { putLong(key, value) }
    
    /**
     * Get a long preference with default value
     */
    fun getLong(key: String, defaultValue: Long = 0L): Long = prefs.getLong(key, defaultValue)
    
    /**
     * Save a float preference
     */
    fun setFloat(key: String, value: Float) = prefs.edit { putFloat(key, value) }
    
    /**
     * Get a float preference with default value
     */
    fun getFloat(key: String, defaultValue: Float = 0f): Float = prefs.getFloat(key, defaultValue)
    
    /**
     * Save a string set preference
     */
    fun setStringSet(key: String, value: Set<String>) = prefs.edit { putStringSet(key, value) }
    
    /**
     * Get a string set preference with default value
     */
    fun getStringSet(key: String, defaultValue: Set<String> = emptySet()): Set<String> = 
        prefs.getStringSet(key, defaultValue) ?: defaultValue
    
    /**
     * Remove a preference
     */
    fun remove(key: String) = prefs.edit { remove(key) }
    
    /**
     * Clear all preferences
     */
    fun clear() = prefs.edit { clear() }
    
    /**
     * Check if a preference exists
     */
    fun contains(key: String): Boolean = prefs.contains(key)
    
    /**
     * Register a shared preferences change listener
     */
    fun registerListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        prefs.registerOnSharedPreferenceChangeListener(listener)
    }
    
    /**
     * Unregister a shared preferences change listener
     */
    fun unregisterListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        prefs.unregisterOnSharedPreferenceChangeListener(listener)
    }
    
    companion object {
        private const val PREFS_NAME = "english_hindi_prefs"
        
        // Preference keys
        const val KEY_LANGUAGE = "language_preference"
        const val KEY_NIGHT_MODE = "night_mode"
        const val KEY_FIRST_LAUNCH = "first_launch"
        const val KEY_DAILY_STREAK = "daily_streak"
        const val KEY_LAST_ACTIVE_DATE = "last_active_date"
        const val KEY_DAILY_GOAL = "daily_goal"
        const val KEY_NOTIFICATION_ENABLED = "notifications_enabled"
        const val KEY_NOTIFICATION_TIME = "notification_time"
        const val KEY_PRONUNCIATION_AUDIO_QUALITY = "pronunciation_audio_quality"
        const val KEY_HIDE_MASTERED_WORDS = "hide_mastered_words"
    }
}