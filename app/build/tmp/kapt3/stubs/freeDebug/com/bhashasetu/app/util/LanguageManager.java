package com.bhashasetu.app.util;

/**
 * Manages application language settings with Kotlin Flow support
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u0000 \u001e2\u00020\u0001:\u0001\u001eB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0003J\u0006\u0010\u0010\u001a\u00020\u0007J\u0016\u0010\u0011\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\u0007J\u0006\u0010\u0014\u001a\u00020\u0015J\u0006\u0010\u0016\u001a\u00020\u0017J\u0006\u0010\u0018\u001a\u00020\u0017J\u000e\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u0007J\u0006\u0010\u001c\u001a\u00020\u001aJ\u0010\u0010\u001d\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u0007H\u0003R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2 = {"Lcom/bhashasetu/app/util/LanguageManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "_currentLanguage", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "currentLanguageFlow", "Lkotlinx/coroutines/flow/StateFlow;", "getCurrentLanguageFlow", "()Lkotlinx/coroutines/flow/StateFlow;", "preferences", "Lcom/bhashasetu/app/util/PreferenceManager;", "createContextWithLanguage", "baseContext", "getCurrentLanguage", "getLocalizedString", "english", "hindi", "getSystemLocale", "Ljava/util/Locale;", "isHindiMode", "", "isSystemLocaleHindi", "setLanguage", "", "languageCode", "toggleLanguage", "updateResources", "Companion", "app_freeDebug"})
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/bhashasetu/app/util/LanguageManager$Companion;", "", "()V", "LANGUAGE_ENGLISH", "", "LANGUAGE_HINDI", "app_freeDebug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}