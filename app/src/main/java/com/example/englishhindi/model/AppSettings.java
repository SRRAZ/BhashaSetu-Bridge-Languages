package com.example.englishhindi.model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class AppSettings {
    
    private static AppSettings instance;
    
    private SharedPreferences preferences;
    
    // Private constructor for singleton pattern
    private AppSettings(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }
    
    // Get singleton instance
    public static synchronized AppSettings getInstance(Context context) {
        if (instance == null) {
            instance = new AppSettings(context.getApplicationContext());
        }
        return instance;
    }
    
    // Whether to use Hindi as the primary interface language
    public boolean useHindiInterface() {
        return preferences.getBoolean("use_hindi_interface", false);
    }
    
    public void setUseHindiInterface(boolean useHindi) {
        preferences.edit().putBoolean("use_hindi_interface", useHindi).apply();
    }
    
    // Whether audio is enabled
    public boolean isAudioEnabled() {
        return preferences.getBoolean("audio_enabled", true);
    }
    
    public void setAudioEnabled(boolean enabled) {
        preferences.edit().putBoolean("audio_enabled", enabled).apply();
    }
    
    // Whether daily reminders are enabled
    public boolean isDailyReminderEnabled() {
        return preferences.getBoolean("daily_reminder", false);
    }
    
    public void setDailyReminderEnabled(boolean enabled) {
        preferences.edit().putBoolean("daily_reminder", enabled).apply();
    }
    
    // Get daily reminder time (stored as minutes from midnight)
    public int getDailyReminderTime() {
        return preferences.getInt("reminder_time", 9 * 60); // Default to 9:00 AM
    }
    
    public void setDailyReminderTime(int minutesFromMidnight) {
        preferences.edit().putInt("reminder_time", minutesFromMidnight).apply();
    }
    
    // Get the user ID (for future multi-user support)
    public int getUserId() {
        return preferences.getInt("user_id", 1);
    }
    
    public void setUserId(int userId) {
        preferences.edit().putInt("user_id", userId).apply();
    }
    
    // Get the last viewed lesson ID
    public int getLastViewedLessonId() {
        return preferences.getInt("last_lesson_id", -1);
    }
    
    public void setLastViewedLessonId(int lessonId) {
        preferences.edit().putInt("last_lesson_id", lessonId).apply();
    }
    
    // Get the number of words to show in practice sessions
    public int getPracticeSessionSize() {
        return preferences.getInt("practice_session_size", 10);
    }
    
    public void setPracticeSessionSize(int size) {
        preferences.edit().putInt("practice_session_size", size).apply();
    }
    
    // Clear all user progress (for testing or resetting the app)
    public void clearAllProgress() {
        preferences.edit()
                .remove("last_lesson_id")
                .apply();
        // Note: this doesn't clear the database, just the preferences
    }
}