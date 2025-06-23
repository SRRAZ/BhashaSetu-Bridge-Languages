package com.bhashasetu.app;

/**
 * Application class with Compose implementation
 */
@dagger.hilt.android.HiltAndroidApp()
@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\b\u0007\u0018\u0000 (2\u00020\u0001:\u0001(B\u0007\u00a2\u0006\u0004\b\u0002\u0010\u0003J\b\u0010!\u001a\u00020\"H\u0016J\b\u0010#\u001a\u00020\"H\u0002J\b\u0010$\u001a\u00020\"H\u0002J\u000e\u0010%\u001a\u00020\"H\u0082@\u00a2\u0006\u0002\u0010&J\b\u0010\'\u001a\u00020\"H\u0016R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u000bX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0016\u001a\u00020\u0017X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u0016\u0010\u001c\u001a\n \u001e*\u0004\u0018\u00010\u001d0\u001dX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020 X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006)"}, d2 = {"Lcom/bhashasetu/app/EnglishHindiComposeApplication;", "Landroid/app/Application;", "<init>", "()V", "wordRepository", "Lcom/bhashasetu/app/data/repository/WordRepository;", "getWordRepository", "()Lcom/bhashasetu/app/data/repository/WordRepository;", "setWordRepository", "(Lcom/bhashasetu/app/data/repository/WordRepository;)V", "database", "Lcom/bhashasetu/app/data/db/AppDatabase;", "getDatabase", "()Lcom/bhashasetu/app/data/db/AppDatabase;", "setDatabase", "(Lcom/bhashasetu/app/data/db/AppDatabase;)V", "languageManager", "Lcom/bhashasetu/app/util/LanguageManager;", "getLanguageManager", "()Lcom/bhashasetu/app/util/LanguageManager;", "setLanguageManager", "(Lcom/bhashasetu/app/util/LanguageManager;)V", "preferenceManager", "Lcom/bhashasetu/app/util/PreferenceManager;", "getPreferenceManager", "()Lcom/bhashasetu/app/util/PreferenceManager;", "setPreferenceManager", "(Lcom/bhashasetu/app/util/PreferenceManager;)V", "executor", "Ljava/util/concurrent/ExecutorService;", "kotlin.jvm.PlatformType", "applicationScope", "Lkotlinx/coroutines/CoroutineScope;", "onCreate", "", "initializeStrictMode", "preloadData", "seedSampleData", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onTerminate", "Companion", "app_debug"})
public final class EnglishHindiComposeApplication extends android.app.Application {
    public com.bhashasetu.app.data.repository.WordRepository wordRepository;
    public com.bhashasetu.app.data.db.AppDatabase database;
    public com.bhashasetu.app.util.LanguageManager languageManager;
    public com.bhashasetu.app.util.PreferenceManager preferenceManager;
    private final java.util.concurrent.ExecutorService executor = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope applicationScope = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "EnglishHindiApp";
    @org.jetbrains.annotations.NotNull()
    public static final com.bhashasetu.app.EnglishHindiComposeApplication.Companion Companion = null;
    
    public EnglishHindiComposeApplication() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.bhashasetu.app.data.repository.WordRepository getWordRepository() {
        return null;
    }
    
    public final void setWordRepository(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.repository.WordRepository p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.bhashasetu.app.data.db.AppDatabase getDatabase() {
        return null;
    }
    
    public final void setDatabase(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.db.AppDatabase p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.bhashasetu.app.util.LanguageManager getLanguageManager() {
        return null;
    }
    
    public final void setLanguageManager(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.util.LanguageManager p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.bhashasetu.app.util.PreferenceManager getPreferenceManager() {
        return null;
    }
    
    public final void setPreferenceManager(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.util.PreferenceManager p0) {
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    /**
     * Sets up StrictMode for catching potential issues in development
     */
    private final void initializeStrictMode() {
    }
    
    /**
     * Preloads essential data in the background
     */
    private final void preloadData() {
    }
    
    /**
     * Seeds some sample data for first-time users
     */
    private final java.lang.Object seedSampleData(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    public void onTerminate() {
    }
    
    @kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/bhashasetu/app/EnglishHindiComposeApplication$Companion;", "", "<init>", "()V", "TAG", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}