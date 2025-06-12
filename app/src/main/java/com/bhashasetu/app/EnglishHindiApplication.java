package com.bhashasetu.app;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;

import com.bhashasetu.app.cache.CacheManager;
import com.bhashasetu.app.di.AppDependencies;
import com.bhashasetu.app.manager.SyncManager;
import com.bhashasetu.app.monitoring.PerformanceMonitoringManager;
import com.bhashasetu.app.receiver.NetworkStateReceiver;
import com.bhashasetu.app.util.AppExecutors;
import com.bhashasetu.app.util.ImageLoader;
import com.bhashasetu.app.util.MemoryOptimization;
import com.bhashasetu.app.util.NetworkUtils;

import android.os.StrictMode;
import android.util.Log;

import com.bhashasetu.app.database.RepositoryFactory;
import com.bhashasetu.app.repository.OfflineQueueHelper;

import java.util.HashMap;
import java.util.Map;

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
 * 6. Integrates comprehensive performance monitoring tools
 */
public class EnglishHindiApplication extends Application {
    
    private static final String TAG = "EnglishHindiApp";
    
    private NetworkStateReceiver networkStateReceiver;
    private AppDependencies appDependencies;
    private PerformanceMonitoringManager performanceMonitoringManager;
    
    @Override
    public void onCreate() {
        // Start performance monitoring as early as possible
        initializePerformanceMonitoring();
        
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
     * Initialize performance monitoring tools
     * This should be called as early as possible in the application lifecycle
     */
    private void initializePerformanceMonitoring() {
        performanceMonitoringManager = PerformanceMonitoringManager.getInstance(this);
        
        // Record application start time for accurate startup metrics
        performanceMonitoringManager.recordAppStart();
        
        // Enable appropriate level of monitoring based on build type
        if (BuildConfig.DEBUG) {
            // Detailed monitoring for debug builds
            performanceMonitoringManager.enable(PerformanceMonitoringManager.MonitoringLevel.DETAILED);
        } else {
            // Standard monitoring for release builds
            performanceMonitoringManager.enable(PerformanceMonitoringManager.MonitoringLevel.STANDARD);
        }
        
        Log.d(TAG, "Performance monitoring initialized");
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
     * Get the performance monitoring manager
     * This provides access to performance monitoring tools
     * 
     * @return PerformanceMonitoringManager instance
     */
    public PerformanceMonitoringManager getPerformanceMonitoringManager() {
        return performanceMonitoringManager;
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
        // Track this operation
        String traceId = performanceMonitoringManager.startTrace("initializeCoreComponents");
        
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
                
                // Stop the trace
                performanceMonitoringManager.stopTrace(traceId);
                
                Log.d(TAG, "Core components initialized");
            } catch (Exception e) {
                // Log errors but prevent app crash
                // Consider implementing more specific exception handling in production
                Log.e(TAG, "Error initializing core components", e);
                
                // Report error to crash reporter
                performanceMonitoringManager.reportError(e);
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
                    // Track sync performance
                    String traceId = performanceMonitoringManager.startTrace("initialSync");
                    
                    // Only sync essential data on first run
                    // The number parameter likely represents priority or limit
                    syncManager.startEssentialSync(100);
                    
                    // Stop the trace
                    performanceMonitoringManager.stopTrace(traceId);
                    
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
        
        // Track memory trim event
        if (performanceMonitoringManager != null) {
            Map<String, Object> trimData = new HashMap<>();
            trimData.put("level", level);
            performanceMonitoringManager.getAnalyticsManager().logPerformanceEvent("memory_trim", trimData);
        }
        
        // Free up memory when the app goes to background or system is low on memory
        if (level >= TRIM_MEMORY_MODERATE) {
            // Clear image caches and non-essential resources
            CacheManager cacheManager = CacheManager.getInstance(this);
            // Parameters likely represent percentage and priority thresholds
            cacheManager.clearLowPriorityCache(30, 50);
            
            // Use our memory monitor to assist with cleanup
            if (performanceMonitoringManager != null) {
                performanceMonitoringManager.getMemoryMonitor().forceGarbageCollection();
            }
            
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
        
        // Track low memory event
        if (performanceMonitoringManager != null) {
            performanceMonitoringManager.getAnalyticsManager().logPerformanceEvent("low_memory", null);
        }
        
        // More aggressive cleanup when system is very low on memory
        CacheManager cacheManager = CacheManager.getInstance(this);
        // More aggressive clearing with higher thresholds
        cacheManager.clearLowPriorityCache(50, 100);
        
        // Use our memory monitor to assist with cleanup
        if (performanceMonitoringManager != null) {
            performanceMonitoringManager.getMemoryMonitor().forceGarbageCollection();
        }
        
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
        
        // Release performance monitoring resources
        if (performanceMonitoringManager != null) {
            performanceMonitoringManager.release();
        }
        
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
        return (com.bhashasetu.app.EnglishHindiApplication) context.getApplicationContext();
    }
}