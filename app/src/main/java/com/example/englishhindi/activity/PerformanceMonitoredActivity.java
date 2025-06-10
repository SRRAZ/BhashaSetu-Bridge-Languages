package com.bhashasetu.app.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bhashasetu.app.BuildConfig;
import com.bhashasetu.app.monitoring.ActivityPerformanceTracker;
import com.bhashasetu.app.monitoring.PerformanceMonitoringManager;

/**
 * Base activity class that integrates performance monitoring tools.
 * All activities in the application should extend this class to benefit from
 * automatic performance tracking and optimizations.
 */
public abstract class PerformanceMonitoredActivity extends AppCompatActivity {
    
    private static final String TAG = "PerfMonitoredActivity";
    
    // Performance tracking
    private ActivityPerformanceTracker performanceTracker;
    private PerformanceMonitoringManager monitoringManager;
    
    // Activity lifecycle timing
    private long createStartTime;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // Record start time before any initialization
        createStartTime = SystemClock.elapsedRealtime();
        
        // Initialize monitoring manager
        monitoringManager = PerformanceMonitoringManager.getInstance(this);
        
        // Initialize performance tracker
        performanceTracker = new ActivityPerformanceTracker(this);
        
        // Call super after initializing monitoring to ensure we capture everything
        super.onCreate(savedInstanceState);
        
        // Track onCreate performance
        performanceTracker.trackOnCreate(savedInstanceState);
        
        // Show performance overlay in debug builds
        if (BuildConfig.DEBUG) {
            performanceTracker.showPerformanceOverlay(true);
        }
        
        Log.d(TAG, getClass().getSimpleName() + " onCreate took " +
                (SystemClock.elapsedRealtime() - createStartTime) + "ms");
    }
    
    @Override
    protected void onStart() {
        long startTime = SystemClock.elapsedRealtime();
        super.onStart();
        Log.d(TAG, getClass().getSimpleName() + " onStart took " +
                (SystemClock.elapsedRealtime() - startTime) + "ms");
    }
    
    @Override
    protected void onResume() {
        long startTime = SystemClock.elapsedRealtime();
        super.onResume();
        performanceTracker.trackOnResume();
        Log.d(TAG, getClass().getSimpleName() + " onResume took " +
                (SystemClock.elapsedRealtime() - startTime) + "ms");
    }
    
    @Override
    protected void onPause() {
        performanceTracker.trackOnPause();
        super.onPause();
    }
    
    @Override
    protected void onStop() {
        performanceTracker.trackOnStop();
        super.onStop();
    }
    
    @Override
    protected void onDestroy() {
        performanceTracker.trackOnDestroy();
        super.onDestroy();
    }
    
    /**
     * Get the performance monitoring manager instance.
     *
     * @return PerformanceMonitoringManager instance
     */
    protected PerformanceMonitoringManager getMonitoringManager() {
        return monitoringManager;
    }
    
    /**
     * Start tracing a method or operation.
     *
     * @param traceName Name of the trace
     * @return Trace ID to use when stopping the trace
     */
    protected String startTrace(String traceName) {
        return monitoringManager.startTrace(traceName);
    }
    
    /**
     * Stop a previously started trace.
     *
     * @param traceId Trace ID from startTrace
     */
    protected void stopTrace(String traceId) {
        monitoringManager.stopTrace(traceId);
    }
    
    /**
     * Show or hide the performance overlay.
     * This is primarily for debugging and should only be used in debug builds.
     *
     * @param show true to show, false to hide
     */
    protected void showPerformanceOverlay(boolean show) {
        performanceTracker.showPerformanceOverlay(show);
    }
}