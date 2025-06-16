package com.bhashasetu.app.util;

/**
 * Manages application language settings with Kotlin Flow support
 */
@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 \u001f2\u00020\u0001:\u0001\u001fB\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u000e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\nJ\u0006\u0010\u0012\u001a\u00020\nJ\u0006\u0010\u0013\u001a\u00020\u0014J\u0006\u0010\u0015\u001a\u00020\u0010J\u000e\u0010\u0016\u001a\u00020\u00032\u0006\u0010\u0017\u001a\u00020\u0003J\u0010\u0010\u0018\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\nH\u0003J\u0006\u0010\u0019\u001a\u00020\u001aJ\u0006\u0010\u001b\u001a\u00020\u0014J\u0016\u0010\u001c\u001a\u00020\n2\u0006\u0010\u001d\u001a\u00020\n2\u0006\u0010\u001e\u001a\u00020\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u00a8\u0006 "}, d2 = {"Lcom/bhashasetu/app/util/LanguageManager;", "", "context", "Landroid/content/Context;", "<init>", "(Landroid/content/Context;)V", "preferences", "Lcom/bhashasetu/app/util/PreferenceManager;", "_currentLanguage", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "currentLanguageFlow", "Lkotlinx/coroutines/flow/StateFlow;", "getCurrentLanguageFlow", "()Lkotlinx/coroutines/flow/StateFlow;", "setLanguage", "", "languageCode", "getCurrentLanguage", "isHindiMode", "", "toggleLanguage", "createContextWithLanguage", "baseContext", "updateResources", "getSystemLocale", "Ljava/util/Locale;", "isSystemLocaleHindi", "getLocalizedString", "english", "hindi", "Companion", "app_debug"})
public final class LanguageManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.bhashasetu.app.util.PreferenceManager preferences = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _currentLanguage = null;
    
    /**
     * Language state flow that can be observed
     */
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> currentLanguageFlow = null;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String LANGUAGE_HINDI = "hi";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String LANGUAGE_ENGLISH = "en";
    @org.jetbrains.annotations.NotNull()
    public static final com.bhashasetu.app.util.LanguageManager.Companion Companion = null;
    
    public LanguageManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    /**
     * Language state flow that can be observed
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getCurrentLanguageFlow() {
        return null;
    }
    
    /**
     * Set application language
     */
    public final void setLanguage(@org.jetbrains.annotations.NotNull()
    java.lang.String languageCode) {
    }
    
    /**
     * Get current language
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCurrentLanguage() {
        return null;
    }
    
    /**
     * Check if current language is Hindi
     */
    public final boolean isHindiMode() {
        return false;
    }
    
    /**
     * Toggle between English and Hindi
     */
    public final void toggleLanguage() {
    }
    
    /**
     * Create a context with updated language configuration
     */
    @org.jetbrains.annotations.NotNull()
    public final android.content.Context createContextWithLanguage(@org.jetbrains.annotations.NotNull()
    android.content.Context baseContext) {
        return null;
    }
    
    /**
     * Update resources with new language
     */
    @android.annotation.SuppressLint(value = {"NewApi"})
    private final void updateResources(java.lang.String languageCode) {
    }
    
    /**
     * Get the appropriate system locale
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.Locale getSystemLocale() {
        return null;
    }
    
    /**
     * Detect if the system locale is Hindi
     */
    public final boolean isSystemLocaleHindi() {
        return false;
    }
    
    /**
     * Get a localized string based on current language
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLocalizedString(@org.jetbrains.annotations.NotNull()
    java.lang.String english, @org.jetbrains.annotations.NotNull()
    java.lang.String hindi) {
        return null;
    }
    
    @kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/bhashasetu/app/util/LanguageManager$Companion;", "", "<init>", "()V", "LANGUAGE_HINDI", "", "LANGUAGE_ENGLISH", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}