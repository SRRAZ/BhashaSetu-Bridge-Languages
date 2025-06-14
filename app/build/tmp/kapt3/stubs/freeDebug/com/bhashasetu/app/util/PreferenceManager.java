package com.bhashasetu.app.util;

/**
 * Manages user preferences with Kotlin Flow compatibility
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u0000 $2\u00020\u0001:\u0001$B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ\u0018\u0010\r\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\u000e\u001a\u00020\nJ\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\u000e\u001a\u00020\u0010J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\u000e\u001a\u00020\u0012J\u0018\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\u000e\u001a\u00020\u0014J\u0018\u0010\u0015\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\u000e\u001a\u00020\fJ$\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\f0\u00172\u0006\u0010\u000b\u001a\u00020\f2\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\f0\u0017J\u000e\u0010\u0018\u001a\u00020\b2\u0006\u0010\u0019\u001a\u00020\u001aJ\u000e\u0010\u001b\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\fJ\u0016\u0010\u001c\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020\nJ\u0016\u0010\u001e\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020\u0010J\u0016\u0010\u001f\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020\u0012J\u0016\u0010 \u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020\u0014J\u0016\u0010!\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020\fJ\u001c\u0010\"\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\f0\u0017J\u000e\u0010#\u001a\u00020\b2\u0006\u0010\u0019\u001a\u00020\u001aR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006%"}, d2 = {"Lcom/bhashasetu/app/util/PreferenceManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "prefs", "Landroid/content/SharedPreferences;", "clear", "", "contains", "", "key", "", "getBoolean", "defaultValue", "getFloat", "", "getInt", "", "getLong", "", "getString", "getStringSet", "", "registerListener", "listener", "Landroid/content/SharedPreferences$OnSharedPreferenceChangeListener;", "remove", "setBoolean", "value", "setFloat", "setInt", "setLong", "setString", "setStringSet", "unregisterListener", "Companion", "app_freeDebug"})
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2 = {"Lcom/bhashasetu/app/util/PreferenceManager$Companion;", "", "()V", "KEY_DAILY_GOAL", "", "KEY_DAILY_STREAK", "KEY_FIRST_LAUNCH", "KEY_HIDE_MASTERED_WORDS", "KEY_LANGUAGE", "KEY_LAST_ACTIVE_DATE", "KEY_NIGHT_MODE", "KEY_NOTIFICATION_ENABLED", "KEY_NOTIFICATION_TIME", "KEY_PRONUNCIATION_AUDIO_QUALITY", "PREFS_NAME", "app_freeDebug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}