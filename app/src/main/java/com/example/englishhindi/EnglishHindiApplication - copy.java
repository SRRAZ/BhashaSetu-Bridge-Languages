package com.example.englishhindi;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.example.englishhindi.cache.CacheManager;
import com.example.englishhindi.database.AppDatabase;
import com.example.englishhindi.database.RepositoryFactory;
import com.example.englishhindi.manager.SyncManager;
import com.example.englishhindi.manager.UserProgressManager;
import com.example.englishhindi.model.DifficultyManager;
import com.example.englishhindi.service.PerformanceMonitor;
import com.example.englishhindi.util.AppExecutors;
import com.example.englishhindi.util.NetworkUtils;
import com.example.englishhindi.util.PreferenceManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Main Application class for the English-Hindi app
 * Handles application-wide initialization and dependency management
 */
public class EnglishHindiApplication extends Application {
    private static final String TAG = "EnglishHindiApp";

    // Singleton instance
    private static EnglishHindiApplication instance;

    // Core app components
    private AppDatabase database;
    private PreferenceManager preferenceManager;
    private CacheManager cacheManager;
    private SyncManager syncManager;
    private UserProgressManager userProgressManager;
    private DifficultyManager difficultyManager;
    private RepositoryFactory repositoryFactory;
    private AppExecutors appExecutors;
    private PerformanceMonitor performanceMonitor;
    
    // Application lifecycle tracking
    private AppLifecycleObserver lifecycleObserver;

    @Override
    public void onCreate() {
        super.onCreate();
        
        long startTime = System.currentTimeMillis();
        
        // Initialize singleton instance
        instance = this;
        
        // Initialize core executors for background processing
        appExecutors = new AppExecutors();
        
        // Initialize performance monitoring
        performanceMonitor = new PerformanceMonitor();
        
        // Initialize core components
        initializeComponents();
        
        // Register lifecycle observer
        lifecycleObserver = new AppLifecycleObserver(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(lifecycleObserver);
        
        // Log startup time
        long initTime = System.currentTimeMillis() - startTime;
        Log.d(TAG, "Application initialized in " + initTime + "ms");
    }

    private void initializeComponents() {
        // Initialize database
        database = AppDatabase.getInstance(this);
        
        // Initialize preferences
        preferenceManager = new PreferenceManager(this);
        
        // Initialize cache manager
        cacheManager = new CacheManager(this);
        
        // Initialize repository factory
        repositoryFactory = new RepositoryFactory(this, database, cacheManager);
        
        // Initialize difficulty manager
        difficultyManager = new DifficultyManager(preferenceManager);
        
        // Initialize user progress manager
        userProgressManager = new UserProgressManager(
                repositoryFactory.getUserProgressRepository(),
                difficultyManager);
        
        // Initialize sync manager (after other components)
        syncManager = new SyncManager(this, cacheManager, userProgressManager);
        
        // Schedule initial sync if needed
        syncManager.scheduleInitialSync();
    }

    @Override
    public void onTerminate() {
        // Clean up resources
        appExecutors.shutdown();
        cacheManager.shutdown();
        
        // Unregister lifecycle observer
        ProcessLifecycleOwner.get().getLifecycle().removeObserver(lifecycleObserver);
        
        super.onTerminate();
    }

    /**
     * Get the application instance
     */
    public static EnglishHindiApplication getInstance() {
        return instance;
    }

    /**
     * Get the app database instance
     */
    public AppDatabase getDatabase() {
        return database;
    }

    /**
     * Get the preference manager
     */
    public PreferenceManager getPreferenceManager() {
        return preferenceManager;
    }

    /**
     * Get the cache manager
     */
    public CacheManager getCacheManager() {
        return cacheManager;
    }

    /**
     * Get the sync manager
     */
    public SyncManager getSyncManager() {
        return syncManager;
    }

    /**
     * Get the user progress manager
     */
    public UserProgressManager getUserProgressManager() {
        return userProgressManager;
    }

    /**
     * Get the difficulty manager
     */
    public DifficultyManager getDifficultyManager() {
        return difficultyManager;
    }

    /**
     * Get the repository factory
     */
    public RepositoryFactory getRepositoryFactory() {
        return repositoryFactory;
    }

    /**
     * Get application executors for background tasks
     */
    public AppExecutors getAppExecutors() {
        return appExecutors;
    }

    /**
     * Get performance monitor
     */
    public PerformanceMonitor getPerformanceMonitor() {
        return performanceMonitor;
    }
}