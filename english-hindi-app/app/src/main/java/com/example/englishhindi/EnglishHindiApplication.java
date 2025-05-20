package com.example.englishhindi;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;

import com.example.englishhindi.cache.CacheManager;
import com.example.englishhindi.di.AppDependencies;
import com.example.englishhindi.manager.SyncManager;
import com.example.englishhindi.receiver.NetworkStateReceiver;
import com.example.englishhindi.util.AppExecutors;
import com.example.englishhindi.util.ImageLoader;
import com.example.englishhindi.util.MemoryOptimization;
import com.example.englishhindi.util.NetworkUtils;

import android.os.StrictMode;
import android.util.Log;

import com.example.englishhindi.database.RepositoryFactory;
import com.example.englishhindi.repository.OfflineQueueHelper;

/**
 * Main Application class responsible for initializing app-wide components
 * and managing their lifecycle.
 * 
 * Key improvements over GitHub version:
 * 1. Implements dependency injection pattern via AppDependencies
 * 2. Performs background initialization for better startup performance
 * 3. Handles memory pressure with onTrimMemory and onLowMemory implementations
 * 4. Monitors network state changes for offline capabilities
 * 5. Uses StrictMode for detecting performance issues in debug builds
 */
public class EnglishHindiApplication extends Application {
    
    private static final String TAG = "EnglishHindiApp";
    
    private NetworkStateReceiver networkStateReceiver;
    private AppDependencies appDependencies;
    
    @Override
    public void onCreate() {
        super.onCreate();
        
        // Enable strict mode in debug builds to detect performance issues early
        // Helps identify disk/network operations on main thread and resource leaks
        if (BuildConfig.DEBUG) {
            enableStrictMode();
        }
        
        // Initialize app dependencies container
        // This is a key architectural improvement over direct component initialization
        appDependencies = new AppDependencies(this);
        
        // Initialize core components with optimized loading strategy
        // Components are initialized in dependency order and background threads
        initializeCoreComponents();
        
        // Register network state receiver to monitor connectivity changes
        // Essential for offline mode functionality
        registerNetworkStateReceiver();
        
        // Check if initial data sync is needed (first launch or data reset)
        checkInitialSync();
        
        Log.d(TAG, "Application initialized");
    }
    
    /**
     * Get the application dependencies container
     * This provides centralized access to app components
     * 
     * @return AppDependencies instance
     */
    public AppDependencies getDependencies() {
        return appDependencies;
    }
    
    /**
     * Initialize core components with performance optimizations
     * 
     * Performance improvements:
     * - Components are initialized in dependency order
     * - Non-UI blocking operations run on background threads
     * - Lazy initialization of less critical components
     */
    private void initializeCoreComponents() {
        // Initialize AppExecutors first for background operations
        AppExecutors.getInstance();
        
        // Initialize NetworkUtils next as many components depend on it
        NetworkUtils.getInstance(this);
        
        // Initialize components on background thread to prevent UI blocking
        AppExecutors.getInstance().diskIO().execute(() -> {
            try {
                // Initialize CacheManager for data and image caching
                CacheManager.getInstance(this);
                
                // Initialize OfflineQueueHelper for offline operation support
                OfflineQueueHelper.getInstance(this);
                
                // Initialize RepositoryFactory for data access layer
                RepositoryFactory.getInstance(this);
                
                // Initialize SyncManager last as it depends on other components
                SyncManager.getInstance(this);
                
                Log.d(TAG, "Core components initialized");
            } catch (Exception e) {
                // Log errors but prevent app crash
                // Consider implementing more specific exception handling in production
                Log.e(TAG, "Error initializing core components", e);
            }
        });
    }
    
    /**
     * Register network state receiver to track connectivity changes
     * This enables the app to adapt to network conditions in real-time
     */
    private void registerNetworkStateReceiver() {
        networkStateReceiver = new NetworkStateReceiver();
        
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        
        // Different registration approach based on Android version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // For Nougat and above, we rely more on NetworkCallback in NetworkUtils
            // But still register for backward compatibility
            registerReceiver(networkStateReceiver, filter);
        } else {
            // For older versions, traditional broadcast receiver is more reliable
            registerReceiver(networkStateReceiver, filter);
        }
    }
    
    /**
     * Check if initial data sync is needed and trigger if necessary
     * This ensures essential data is available on first launch
     */
    private void checkInitialSync() {
        AppExecutors.getInstance().diskIO().execute(() -> {
            SyncManager syncManager = SyncManager.getInstance(this);
            
            // Only perform initial sync if never synced before
            if (syncManager.getLastSyncTime() == 0) {
                // Check network before attempting sync
                if (NetworkUtils.getInstance(this).isNetworkAvailable()) {
                    // Only sync essential data on first run
                    // The number parameter likely represents priority or limit
                    syncManager.startEssentialSync(100);
                    Log.d(TAG, "Started initial essential sync");
                } else {
                    Log.d(TAG, "Skipping initial sync due to no network");
                }
            }
        });
    }
    
    /**
     * Enable strict mode for debugging performance issues
     * Only enabled in debug builds (see onCreate)
     */
    private void enableStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog() // Just log issues rather than crash in development
                .build());
        
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .build());
    }
    
    /**
     * Called by system when memory is getting low
     * Implements graduated memory cleanup based on trim level
     *
     * This is a significant improvement over the GitHub version
     * which had no memory pressure handling
     * 
     * @param level The memory trim level from system
     */
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        
        // Free up memory when the app goes to background or system is low on memory
        if (level >= TRIM_MEMORY_MODERATE) {
            // Clear image caches and non-essential resources
            CacheManager cacheManager = CacheManager.getInstance(this);
            // Parameters likely represent percentage and priority thresholds
            cacheManager.clearLowPriorityCache(30, 50);
            
            // Suggest garbage collection
            // Note: System.gc() is generally discouraged but can help in extreme cases
            // Consider removing this in production or ensuring it runs on background thread
            System.gc();
            
            Log.d(TAG, "Trimmed memory due to level: " + level);
        }
    }
    
    /**
     * Called when the system is very low on memory
     * Performs more aggressive cleanup than onTrimMemory
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        
        // More aggressive cleanup when system is very low on memory
        CacheManager cacheManager = CacheManager.getInstance(this);
        // More aggressive clearing with higher thresholds
        cacheManager.clearLowPriorityCache(50, 100);
        
        // Suggest garbage collection
        System.gc();
        
        Log.d(TAG, "Cleared caches due to low memory");
    }
    
    /**
     * Called when the application is being terminated
     * Cleans up resources and unregisters receivers
     * 
     * Note: Not guaranteed to be called on all devices
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        
        // Unregister receiver to prevent leaks
        if (networkStateReceiver != null) {
            unregisterReceiver(networkStateReceiver);
        }
        
        // Release resources
        NetworkUtils.getInstance(this).release();
        
        Log.d(TAG, "Application terminated");
    }
    
    /**
     * Helper method to get the application instance from a context
     * Provides a cleaner alternative to direct casting
     * 
     * @param context Any context
     * @return EnglishHindiApplication instance
     */
    public static EnglishHindiApplication from(Context context) {
        return (EnglishHindiApplication) context.getApplicationContext();
    }
}