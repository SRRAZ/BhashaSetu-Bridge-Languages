package com.example.englishhindi.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.englishhindi.EnglishHindiApplication;
import com.example.englishhindi.util.MemoryOptimizer;
import com.example.englishhindi.util.UIPerformanceOptimizer;

/**
 * Base activity class with built-in performance optimizations.
 * Extends this class to get automatic performance optimizations for your activities.
 */
public abstract class PerformanceOptimizedActivity extends AppCompatActivity {
    private static final String TAG = "PerfOptimizedActivity";
    
    // Track activity lifecycle for performance monitoring
    private long activityStartTime;
    private long layoutInflationTime;
    private boolean isActivityResumed = false;
    
    // Memory tracking
    private long startMemory;
    
    // UI optimizations
    private UIPerformanceOptimizer uiOptimizer;
    
    // Handler for delayed operations
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // Track start time for performance metrics
        activityStartTime = System.currentTimeMillis();
        
        // Pre-onCreate optimizations
        enableWindowOptimizations();
        
        super.onCreate(savedInstanceState);
        
        // Initialize performance helpers
        uiOptimizer = UIPerformanceOptimizer.getInstance();
        
        // Track memory usage
        startMemory = MemoryOptimizer.getInstance(this).getCurrentMemoryUsage();
        
        // Add layout observer for measuring inflation time
        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (layoutInflationTime == 0) {
                    layoutInflationTime = System.currentTimeMillis();
                    
                    // Remove listener after first layout
                    getWindow().getDecorView().getViewTreeObserver()
                            .removeOnGlobalLayoutListener(this);
                    
                    // Log performance metrics
                    logStartupMetrics();
                    
                    // Perform post-layout optimizations
                    optimizeAfterLayout();
                }
            }
        });
    }
    
    /**
     * Set content view with performance optimizations.
     *
     * @param layoutResID Layout resource ID
     */
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        
        // Optimize content view
        handler.post(this::optimizeViews);
    }
    
    /**
     * Set content view with performance optimizations.
     *
     * @param view Content view
     */
    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        
        // Optimize content view
        handler.post(this::optimizeViews);
    }
    
    /**
     * Set content view with performance optimizations.
     *
     * @param view   Content view
     * @param params Layout parameters
     */
    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        
        // Optimize content view
        handler.post(this::optimizeViews);
    }
    
    /**
     * Apply window optimizations.
     */
    private void enableWindowOptimizations() {
        // Request clean status bar if needed
        Window window = getWindow();
        if (window != null) {
            // Enable hardware acceleration
            window.setFlags(
                    android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                    android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        }
    }
    
    /**
     * Optimize views after they've been added.
     */
    private void optimizeViews() {
        try {
            // Get root view
            View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
            
            // Remove excess backgrounds to reduce overdraw
            uiOptimizer.optimizeActivityOverdraw(this);
            
            // Detect potential jank sources if debugging
            if (isDebuggingEnabled()) {
                uiOptimizer.detectJankSources(rootView, this);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error optimizing views", e);
        }
    }
    
    /**
     * Check if debugging is enabled.
     *
     * @return true if debugging is enabled
     */
    protected boolean isDebuggingEnabled() {
        return false; // Override in subclasses if needed
    }
    
    /**
     * Log activity startup metrics.
     */
    private void logStartupMetrics() {
        long inflationDuration = layoutInflationTime - activityStartTime;
        Log.d(TAG, getClass().getSimpleName() + " inflation took " + 
                   inflationDuration + "ms");
        
        // Record detailed layout metrics if debugging
        if (isDebuggingEnabled()) {
            View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
            if (rootView instanceof ViewGroup) {
                int depth = uiOptimizer.getViewHierarchyDepth((ViewGroup) rootView);
                Log.d(TAG, "View hierarchy depth: " + depth);
                
                long memoryUsage = uiOptimizer.estimateViewMemoryUsage(rootView);
                Log.d(TAG, "Estimated view memory usage: " + 
                        MemoryOptimizer.formatMemorySize(memoryUsage));
            }
        }
    }
    
    /**
     * Apply optimizations after layout is complete.
     */
    protected void optimizeAfterLayout() {
        // Start jank tracking if debugging
        if (isDebuggingEnabled()) {
            uiOptimizer.startJankTracking();
        }
    }
    
    @Override
    protected void onStart() {
        super.onStart();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        isActivityResumed = true;
        
        // Submit memory usage analysis on resume
        if (isDebuggingEnabled()) {
            handler.postDelayed(() -> {
                if (isActivityResumed) {
                    long currentMemory = MemoryOptimizer.getInstance(this).getCurrentMemoryUsage();
                    long diff = currentMemory - startMemory;
                    
                    Log.d(TAG, getClass().getSimpleName() + " memory usage: " + 
                              MemoryOptimizer.formatMemorySize(diff));
                }
            }, 1000);
        }
    }
    
    @Override
    protected void onPause() {
        isActivityResumed = false;
        super.onPause();
    }
    
    @Override
    protected void onStop() {
        // Stop jank tracking if debugging
        if (isDebuggingEnabled()) {
            uiOptimizer.stopJankTracking();
            
            float jankPercentage = uiOptimizer.getJankPercentage();
            Log.d(TAG, getClass().getSimpleName() + " jank percentage: " + 
                      jankPercentage + "%");
        }
        
        super.onStop();
    }
    
    @Override
    protected void onDestroy() {
        // Clean up resources
        cleanupResources();
        
        super.onDestroy();
    }
    
    /**
     * Clean up resources to prevent memory leaks.
     */
    protected void cleanupResources() {
        // Override in subclasses to clean up specific resources
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        
        // Re-optimize when configuration changes
        handler.post(this::optimizeViews);
    }
    
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        
        // Handle memory pressure
        MemoryOptimizer.getInstance(this).handleTrimMemory(level);
    }
    
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        
        // Handle low memory condition
        MemoryOptimizer.getInstance(this).handleTrimMemory(
                android.content.ComponentCallbacks2.TRIM_MEMORY_COMPLETE);
    }
    
    /**
     * Get the application dependencies.
     *
     * @return AppDependencies instance
     */
    protected com.example.englishhindi.di.AppDependencies getDependencies() {
        return EnglishHindiApplication.from(this).getDependencies();
    }
    
    /**
     * Get the UI performance optimizer.
     *
     * @return UIPerformanceOptimizer instance
     */
    protected UIPerformanceOptimizer getUiOptimizer() {
        return uiOptimizer;
    }
}