package com.bhashasetu.app.monitoring;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Central performance monitoring manager that integrates all monitoring components.
 * This class provides a unified interface to enable, disable, and manage all performance
 * monitoring tools in the application.
 */
public class PerformanceMonitoringManager {
    private static final String TAG = "PerfMonitoringManager";
    
    // Singleton instance
    private static PerformanceMonitoringManager instance;
    
    // Context
    private final Context context;
    
    // Monitoring components
    private final PerformanceMonitor performanceMonitor;
    private final MemoryMonitor memoryMonitor;
    private final NetworkMonitor networkMonitor;
    private final AnalyticsManager analyticsManager;
    private final CrashReporter crashReporter;
    
    // Configuration
    private boolean isEnabled = false;
    private final Handler mainHandler;
    
    // Monitoring interval
    private static final long MONITORING_INTERVAL_MS = TimeUnit.MINUTES.toMillis(1); // 1 minute
    
    // Monitoring levels
    public enum MonitoringLevel {
        MINIMAL,   // Only crash reporting and basic performance
        STANDARD,  // Regular monitoring suitable for most use cases
        DETAILED   // Comprehensive monitoring with more overhead
    }
    
    private MonitoringLevel currentLevel = MonitoringLevel.STANDARD;
    
    /**
     * Private constructor to initialize all monitoring components.
     *
     * @param context Application context
     */
    private PerformanceMonitoringManager(Context context) {
        this.context = context.getApplicationContext();
        this.mainHandler = new Handler(Looper.getMainLooper());
        
        // Initialize monitoring components
        performanceMonitor = PerformanceMonitor.getInstance(context);
        memoryMonitor = MemoryMonitor.getInstance(context);
        networkMonitor = NetworkMonitor.getInstance(context);
        analyticsManager = AnalyticsManager.getInstance(context);
        crashReporter = CrashReporter.getInstance(context);
        
        // Register for cross-component events
        registerEventListeners();
    }
    
    /**
     * Get singleton instance.
     *
     * @param context Context
     * @return PerformanceMonitoringManager instance
     */
    public static synchronized PerformanceMonitoringManager getInstance(Context context) {
        if (instance == null) {
            instance = new PerformanceMonitoringManager(context);
        }
        return instance;
    }
    
    /**
     * Register listeners for cross-component events.
     */
    private void registerEventListeners() {
        // Listen for performance metrics and forward to analytics
        performanceMonitor.addListener(stats -> {
            if (isEnabled && (currentLevel == MonitoringLevel.DETAILED || 
                    (currentLevel == MonitoringLevel.STANDARD && shouldSampleEvent()))) {
                analyticsManager.logPerformanceEvent("performance_metrics", stats);
            }
        });
        
        // Listen for memory warnings
        memoryMonitor.addListener(new MemoryMonitor.MemoryListener() {
            @Override
            public void onMemoryWarning(int level, Map<String, Object> memoryInfo) {
                // Always log memory warnings regardless of sampling
                if (isEnabled) {
                    analyticsManager.logPerformanceEvent("memory_warning", memoryInfo);
                }
            }
            
            @Override
            public void onPotentialLeak(String source, float growthRate) {
                // Always log potential leaks
                if (isEnabled) {
                    Map<String, Object> leakInfo = new HashMap<>();
                    leakInfo.put("source", source);
                    leakInfo.put("growth_rate", growthRate);
                    analyticsManager.logPerformanceEvent("memory_leak", leakInfo);
                }
            }
        });
        
        // Listen for network state changes
        networkMonitor.addListener(new NetworkMonitor.NetworkListener() {
            @Override
            public void onNetworkStateChanged(NetworkMonitor.NetworkState state) {
                // Log network state changes
                if (isEnabled && currentLevel != MonitoringLevel.MINIMAL) {
                    Map<String, Object> stateInfo = new HashMap<>();
                    stateInfo.put("available", state.isAvailable());
                    stateInfo.put("type", state.getTypeName());
                    stateInfo.put("is_wifi", state.isWifi());
                    stateInfo.put("is_metered", state.isMetered());
                    analyticsManager.logPerformanceEvent("network_state_changed", stateInfo);
                }
            }
            
            @Override
            public void onNetworkMetricsUpdated(Map<String, Object> metrics) {
                // Only log network metrics periodically to reduce overhead
                if (isEnabled && currentLevel == MonitoringLevel.DETAILED && shouldSampleEvent()) {
                    analyticsManager.logPerformanceEvent("network_metrics", metrics);
                }
            }
        });
    }
    
    /**
     * Simple helper to sample events to reduce overhead.
     * Returns true approximately 10% of the time.
     *
     * @return true if event should be sampled
     */
    private boolean shouldSampleEvent() {
        return Math.random() < 0.1; // 10% sample rate
    }
    
    /**
     * Enable performance monitoring.
     *
     * @param level Monitoring level
     */
    public void enable(MonitoringLevel level) {
        if (isEnabled && level == currentLevel) {
            return; // Already enabled at this level
        }
        
        this.currentLevel = level;
        
        // Start all monitoring components based on level
        switch (level) {
            case MINIMAL:
                enableMinimalMonitoring();
                break;
            case STANDARD:
                enableStandardMonitoring();
                break;
            case DETAILED:
                enableDetailedMonitoring();
                break;
        }
        
        isEnabled = true;
        
        Log.i(TAG, "Performance monitoring enabled at level: " + level);
    }
    
    /**
     * Enable minimal monitoring (crash reporting and basic performance).
     */
    private void enableMinimalMonitoring() {
        // Always install crash reporter
        crashReporter.install();
        
        // Start performance monitor with basic settings
        performanceMonitor.start();
        
        // Start memory monitor with longer intervals
        memoryMonitor.start(TimeUnit.MINUTES.toMillis(5)); // Check every 5 minutes
        
        // Basic network monitoring
        // Don't start additional components
    }
    
    /**
     * Enable standard monitoring (suitable for most use cases).
     */
    private void enableStandardMonitoring() {
        // First enable minimal monitoring
        enableMinimalMonitoring();
        
        // Then add standard monitoring
        memoryMonitor.start(TimeUnit.MINUTES.toMillis(2)); // More frequent checks
        
        // Enable ANR detection
        crashReporter.setAnrDetectionEnabled(true);
        
        // Schedule periodic performance snapshots
        schedulePeriodicMonitoring();
    }
    
    /**
     * Enable detailed monitoring (comprehensive with more overhead).
     */
    private void enableDetailedMonitoring() {
        // First enable standard monitoring
        enableStandardMonitoring();
        
        // Then add detailed monitoring
        memoryMonitor.start(TimeUnit.MINUTES.toMillis(1)); // Most frequent checks
        
        // More frequent performance snapshots scheduled in the method below
        schedulePeriodicMonitoring();
    }
    
    /**
     * Schedule periodic monitoring tasks.
     */
    private void schedulePeriodicMonitoring() {
        // Remove any existing callbacks
        mainHandler.removeCallbacksAndMessages(null);
        
        // Schedule periodic task
        mainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isEnabled) {
                    return;
                }
                
                // Take a memory snapshot
                Map<String, Object> memoryStats = memoryMonitor.getDetailedMemoryInfo();
                
                // Take a performance snapshot
                Map<String, Object> perfStats = performanceMonitor.getStats();
                
                // Take a network snapshot
                Map<String, Object> networkStats = networkMonitor.getNetworkMetrics();
                
                // Log comprehensive snapshot if using detailed monitoring
                if (currentLevel == MonitoringLevel.DETAILED) {
                    Map<String, Object> combinedStats = new HashMap<>();
                    combinedStats.put("memory", memoryStats);
                    combinedStats.put("performance", perfStats);
                    combinedStats.put("network", networkStats);
                    
                    analyticsManager.logPerformanceEvent("monitoring_snapshot", combinedStats);
                }
                
                // Reschedule
                long interval = (currentLevel == MonitoringLevel.DETAILED) ? 
                        MONITORING_INTERVAL_MS / 2 : MONITORING_INTERVAL_MS;
                mainHandler.postDelayed(this, interval);
            }
        }, MONITORING_INTERVAL_MS);
    }
    
    /**
     * Disable performance monitoring.
     */
    public void disable() {
        if (!isEnabled) {
            return;
        }
        
        // Stop all monitoring components
        performanceMonitor.stop();
        memoryMonitor.stop();
        
        // Remove scheduled tasks
        mainHandler.removeCallbacksAndMessages(null);
        
        isEnabled = false;
        
        Log.i(TAG, "Performance monitoring disabled");
    }
    
    /**
     * Get the current monitoring level.
     *
     * @return Current monitoring level
     */
    public MonitoringLevel getCurrentLevel() {
        return currentLevel;
    }
    
    /**
     * Check if monitoring is enabled.
     *
     * @return true if enabled
     */
    public boolean isEnabled() {
        return isEnabled;
    }
    
    /**
     * Record app start for accurate startup timing.
     * Should be called as early as possible in application onCreate.
     */
    public void recordAppStart() {
        performanceMonitor.recordAppStart();
    }
    
    /**
     * Record screen load time.
     *
     * @param screenName Name of the screen
     * @param loadTimeMs Load time in milliseconds
     */
    public void recordScreenLoad(@NonNull String screenName, long loadTimeMs) {
        if (!isEnabled) {
            return;
        }
        
        performanceMonitor.recordScreenLoad(screenName, loadTimeMs);
        analyticsManager.logScreenView(screenName);
    }
    
    /**
     * Start tracing a method or operation.
     *
     * @param traceName Name of the trace
     * @return Trace ID for stopping
     */
    public String startTrace(@NonNull String traceName) {
        if (!isEnabled) {
            return "";
        }
        
        return performanceMonitor.startTrace(traceName);
    }
    
    /**
     * Stop a trace.
     *
     * @param traceId Trace ID from startTrace
     */
    public void stopTrace(String traceId) {
        if (!isEnabled || traceId == null || traceId.isEmpty()) {
            return;
        }
        
        performanceMonitor.stopTrace(traceId);
    }
    
    /**
     * Record a network request.
     *
     * @param url Request URL
     * @param statusCode HTTP status code
     * @param bytes Bytes transferred
     * @param durationMs Request duration
     */
    public void recordNetworkRequest(@NonNull String url, int statusCode, long bytes, long durationMs) {
        if (!isEnabled) {
            return;
        }
        
        networkMonitor.recordRequestComplete(url, bytes, statusCode, statusCode >= 200 && statusCode < 300);
        analyticsManager.logNetworkEvent(url, statusCode, bytes, durationMs);
    }
    
    /**
     * Record a network error.
     *
     * @param url Request URL
     * @param errorMessage Error message
     */
    public void recordNetworkError(@NonNull String url, String errorMessage) {
        if (!isEnabled) {
            return;
        }
        
        networkMonitor.recordRequestError(url, errorMessage);
        
        Map<String, Object> errorData = new HashMap<>();
        errorData.put("url", url);
        errorData.put("error", errorMessage);
        analyticsManager.logPerformanceEvent("network_error", errorData);
    }
    
    /**
     * Manually report an error.
     *
     * @param error Error to report
     */
    public void reportError(Throwable error) {
        crashReporter.reportError(error);
    }
    
    /**
     * Force a garbage collection.
     * Only use this method in extreme circumstances.
     */
    public void forceGarbageCollection() {
        if (!isEnabled) {
            return;
        }
        
        memoryMonitor.forceGarbageCollection();
    }
    
    /**
     * Run a network speed test.
     *
     * @param listener Listener for results
     */
    public void runNetworkSpeedTest(NetworkMonitor.SpeedTestListener listener) {
        networkMonitor.performSpeedTest(listener);
    }
    
    /**
     * Get all components for direct access if needed.
     * Generally, it's better to use the methods in this class.
     */
    public PerformanceMonitor getPerformanceMonitor() {
        return performanceMonitor;
    }
    
    public MemoryMonitor getMemoryMonitor() {
        return memoryMonitor;
    }
    
    public NetworkMonitor getNetworkMonitor() {
        return networkMonitor;
    }
    
    public AnalyticsManager getAnalyticsManager() {
        return analyticsManager;
    }
    
    public CrashReporter getCrashReporter() {
        return crashReporter;
    }
    
    /**
     * Release resources.
     * Should be called when the application is terminating.
     */
    public void release() {
        disable();
        
        performanceMonitor.release();
        memoryMonitor.release();
        networkMonitor.release();
        analyticsManager.release();
        crashReporter.uninstall();
    }
}