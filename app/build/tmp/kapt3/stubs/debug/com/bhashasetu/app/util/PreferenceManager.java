package com.bhashasetu.app.util;

/**
 * Manages user preferences with Kotlin Flow compatibility
 */
@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 %2\u00020\u0001:\u0001%B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0016\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rJ\u0018\u0010\u000e\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\u000f\u001a\u00020\rJ\u0016\u0010\u0010\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bJ\u0018\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\u000f\u001a\u00020\u000bJ\u0016\u0010\u0012\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0013J\u0018\u0010\u0014\u001a\u00020\u00132\u0006\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\u000f\u001a\u00020\u0013J\u0016\u0010\u0015\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0016J\u0018\u0010\u0017\u001a\u00020\u00162\u0006\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\u000f\u001a\u00020\u0016J\u0016\u0010\u0018\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0019J\u0018\u0010\u001a\u001a\u00020\u00192\u0006\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\u000f\u001a\u00020\u0019J\u001c\u0010\u001b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\u001cJ$\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u000b0\u001c2\u0006\u0010\n\u001a\u00020\u000b2\u000e\b\u0002\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000b0\u001cJ\u000e\u0010\u001e\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ\u0006\u0010\u001f\u001a\u00020\tJ\u000e\u0010 \u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u000bJ\u000e\u0010!\u001a\u00020\t2\u0006\u0010\"\u001a\u00020#J\u000e\u0010$\u001a\u00020\t2\u0006\u0010\"\u001a\u00020#R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006&"}, d2 = {"Lcom/bhashasetu/app/util/PreferenceManager;", "", "context", "Landroid/content/Context;", "<init>", "(Landroid/content/Context;)V", "prefs", "Landroid/content/SharedPreferences;", "setBoolean", "", "key", "", "value", "", "getBoolean", "defaultValue", "setString", "getString", "setInt", "", "getInt", "setLong", "", "getLong", "setFloat", "", "getFloat", "setStringSet", "", "getStringSet", "remove", "clear", "contains", "registerListener", "listener", "Landroid/content/SharedPreferences$OnSharedPreferenceChangeListener;", "unregisterListener", "Companion", "app_debug"})
public final class PreferenceManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.SharedPreferences prefs = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String PREFS_NAME = "english_hindi_prefs";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_LANGUAGE = "language_preference";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_NIGHT_MODE = "night_mode";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_FIRST_LAUNCH = "first_launch";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_DAILY_STREAK = "daily_streak";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_LAST_ACTIVE_DATE = "last_active_date";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_DAILY_GOAL = "daily_goal";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_NOTIFICATION_ENABLED = "notifications_enabled";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_NOTIFICATION_TIME = "notification_time";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_PRONUNCIATION_AUDIO_QUALITY = "pronunciation_audio_quality";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_HIDE_MASTERED_WORDS = "hide_mastered_words";
    @org.jetbrains.annotations.NotNull()
    public static final com.bhashasetu.app.util.PreferenceManager.Companion Companion = null;
    
    public PreferenceManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    /**
     * Save a boolean preference
     */
    public final void setBoolean(@org.jetbrains.annotations.NotNull()
    java.lang.String key, boolean value) {
    }
    
    /**
     * Get a boolean preference with default value
     */
    public final boolean getBoolean(@org.jetbrains.annotations.NotNull()
    java.lang.String key, boolean defaultValue) {
        return false;
    }
    
    /**
     * Save a string preference
     */
    public final void setString(@org.jetbrains.annotations.NotNull()
    java.lang.String key, @org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    /**
     * Get a string preference with default value
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getString(@org.jetbrains.annotations.NotNull()
    java.lang.String key, @org.jetbrains.annotations.NotNull()
    java.lang.String defaultValue) {
        return null;
    }
    
    /**
     * Save an integer preference
     */
    public final void setInt(@org.jetbrains.annotations.NotNull()
    java.lang.String key, int value) {
    }
    
    /**
     * Get an integer preference with default value
     */
    public final int getInt(@org.jetbrains.annotations.NotNull()
    java.lang.String key, int defaultValue) {
        return 0;
    }
    
    /**
     * Save a long preference
     */
    public final void setLong(@org.jetbrains.annotations.NotNull()
    java.lang.String key, long value) {
    }
    
    /**
     * Get a long preference with default value
     */
    public final long getLong(@org.jetbrains.annotations.NotNull()
    java.lang.String key, long defaultValue) {
        return 0L;
    }
    
    /**
     * Save a float preference
     */
    public final void setFloat(@org.jetbrains.annotations.NotNull()
    java.lang.String key, float value) {
    }
    
    /**
     * Get a float preference with default value
     */
    public final float getFloat(@org.jetbrains.annotations.NotNull()
    java.lang.String key, float defaultValue) {
        return 0.0F;
    }
    
    /**
     * Save a string set preference
     */
    public final void setStringSet(@org.jetbrains.annotations.NotNull()
    java.lang.String key, @org.jetbrains.annotations.NotNull()
    java.util.Set<java.lang.String> value) {
    }
    
    /**
     * Get a string set preference with default value
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.Set<java.lang.String> getStringSet(@org.jetbrains.annotations.NotNull()
    java.lang.String key, @org.jetbrains.annotations.NotNull()
    java.util.Set<java.lang.String> defaultValue) {
        return null;
    }
    
    /**
     * Remove a preference
     */
    public final void remove(@org.jetbrains.annotations.NotNull()
    java.lang.String key) {
    }
    
    /**
     * Clear all preferences
     */
    public final void clear() {
    }
    
    /**
     * Check if a preference exists
     */
    public final boolean contains(@org.jetbrains.annotations.NotNull()
    java.lang.String key) {
        return false;
    }
    
    /**
     * Register a shared preferences change listener
     */
    public final void registerListener(@org.jetbrains.annotations.NotNull()
    android.content.SharedPreferences.OnSharedPreferenceChangeListener listener) {
    }
    
    /**
     * Unregister a shared preferences change listener
     */
    public final void unregisterListener(@org.jetbrains.annotations.NotNull()
    android.content.SharedPreferences.OnSharedPreferenceChangeListener listener) {
    }
    
    @kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u000b\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lcom/bhashasetu/app/util/PreferenceManager$Companion;", "", "<init>", "()V", "PREFS_NAME", "", "KEY_LANGUAGE", "KEY_NIGHT_MODE", "KEY_FIRST_LAUNCH", "KEY_DAILY_STREAK", "KEY_LAST_ACTIVE_DATE", "KEY_DAILY_GOAL", "KEY_NOTIFICATION_ENABLED", "KEY_NOTIFICATION_TIME", "KEY_PRONUNCIATION_AUDIO_QUALITY", "KEY_HIDE_MASTERED_WORDS", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}