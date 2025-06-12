package com.bhashasetu.app.monitoring;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

/**
 * Activity performance tracker for monitoring UI rendering performance.
 * Tracks activity and fragment lifecycle events to measure loading times
 * and initialize performance monitoring tools.
 */
public class ActivityPerformanceTracker {
    
    // Start time tracking
    private long activityCreateTime = 0;
    private long activityResumeTime = 0;
    private long drawStartTime = 0;
    
    // Reference to monitoring manager
    private final PerformanceMonitoringManager monitoringManager;
    
    // Activity reference
    private final Activity activity;
    
    // Handler for main thread operations
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    
    // Performance overlay view
    private PerformanceOverlayView overlayView;
    private boolean overlayEnabled = false;
    
    /**
     * Create a new activity performance tracker.
     *
     * @param activity Activity to track
     */
    public ActivityPerformanceTracker(@NonNull Activity activity) {
        this.activity = activity;
        this.monitoringManager = PerformanceMonitoringManager.getInstance(activity);
    }
    
    /**
     * Track activity onCreate.
     *
     * @param savedInstanceState Saved instance state
     */
    public void trackOnCreate(@Nullable Bundle savedInstanceState) {
        activityCreateTime = SystemClock.elapsedRealtime();
        
        // Start tracing activity creation
        String traceName = activity.getClass().getSimpleName() + ".onCreate";
        String traceId = monitoringManager.startTrace(traceName);
        
        // Set up rendering callback
        setupFirstDrawListener();
        
        // Track fragment lifecycle if this is a FragmentActivity
        if (activity instanceof FragmentActivity) {
            trackFragments((FragmentActivity) activity);
        }
        
        // Complete trace in a delayed callback
        mainHandler.post(() -> monitoringManager.stopTrace(traceId));
    }
    
    /**
     * Track activity onResume.
     */
    public void trackOnResume() {
        activityResumeTime = SystemClock.elapsedRealtime();
        
        // Start tracing activity resume
        String traceName = activity.getClass().getSimpleName() + ".onResume";
        String traceId = monitoringManager.startTrace(traceName);
        
        // Complete trace in a delayed callback
        mainHandler.post(() -> monitoringManager.stopTrace(traceId));
    }
    
    /**
     * Track activity onPause.
     */
    public void trackOnPause() {
        // Start tracing activity pause
        String traceName = activity.getClass().getSimpleName() + ".onPause";
        String traceId = monitoringManager.startTrace(traceName);
        
        // Complete trace in a delayed callback
        mainHandler.post(() -> monitoringManager.stopTrace(traceId));
    }
    
    /**
     * Track activity onStop.
     */
    public void trackOnStop() {
        // Not implementing specific tracking for onStop
    }
    
    /**
     * Track activity onDestroy.
     */
    public void trackOnDestroy() {
        // Remove overlay if it exists
        if (overlayView != null && overlayView.getParent() != null) {
            activity.getWindow().getDecorView()
                    .getRootView()
                    .findViewById(android.R.id.content)
                    .getRootView()
                    .removeView(overlayView);
            overlayView = null;
        }
    }
    
    /**
     * Setup first draw listener to measure initial render time.
     */
    private void setupFirstDrawListener() {
        drawStartTime = SystemClock.elapsedRealtime();
        
        final View contentView = activity.getWindow().getDecorView()
                .findViewById(android.R.id.content);
        
        contentView.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        contentView.getViewTreeObserver().removeOnPreDrawListener(this);
                        
                        long drawTime = SystemClock.elapsedRealtime();
                        long timeToFirstRender = drawTime - drawStartTime;
                        long timeFromCreate = drawTime - activityCreateTime;
                        
                        // Record screen load time
                        monitoringManager.recordScreenLoad(
                                activity.getClass().getSimpleName(),
                                timeFromCreate);
                        
                        return true;
                    }
                });
    }
    
    /**
     * Track fragment lifecycle events.
     *
     * @param fragmentActivity Fragment activity
     */
    private void trackFragments(FragmentActivity fragmentActivity) {
        FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
        
        fragmentManager.registerFragmentLifecycleCallbacks(
                new FragmentManager.FragmentLifecycleCallbacks() {
                    @Override
                    public void onFragmentCreated(@NonNull FragmentManager fm,
                                                 @NonNull Fragment fragment,
                                                 @Nullable Bundle savedInstanceState) {
                        // Start tracing fragment creation
                        String traceName = fragment.getClass().getSimpleName() + ".onCreate";
                        String traceId = monitoringManager.startTrace(traceName);
                        
                        // Complete trace in a delayed callback
                        mainHandler.post(() -> monitoringManager.stopTrace(traceId));
                    }
                    
                    @Override
                    public void onFragmentResumed(@NonNull FragmentManager fm,
                                                 @NonNull Fragment fragment) {
                        // Start tracing fragment resume
                        String traceName = fragment.getClass().getSimpleName() + ".onResume";
                        String traceId = monitoringManager.startTrace(traceName);
                        
                        // Complete trace in a delayed callback
                        mainHandler.post(() -> monitoringManager.stopTrace(traceId));
                    }
                    
                    @Override
                    public void onFragmentViewCreated(@NonNull FragmentManager fm,
                                                     @NonNull Fragment fragment,
                                                     @NonNull View view,
                                                     @Nullable Bundle savedInstanceState) {
                        // Setup view draw tracing
                        view.getViewTreeObserver().addOnPreDrawListener(
                                new ViewTreeObserver.OnPreDrawListener() {
                                    @Override
                                    public boolean onPreDraw() {
                                        view.getViewTreeObserver().removeOnPreDrawListener(this);
                                        
                                        // Record fragment view render time
                                        String traceName = fragment.getClass().getSimpleName() + ".viewRender";
                                        monitoringManager.stopTrace(traceName);
                                        
                                        return true;
                                    }
                                });
                    }
                }, true); // recursive = true for tracking child fragments
    }
    
    /**
     * Show or hide performance overlay for debugging.
     * This should only be used in debug builds.
     *
     * @param enable true to show, false to hide
     */
    public void showPerformanceOverlay(boolean enable) {
        if (enable == overlayEnabled) {
            return; // Already in the desired state
        }
        
        overlayEnabled = enable;
        
        if (enable) {
            // Create and add overlay view
            Window window = activity.getWindow();
            View decorView = window.getDecorView();
            View contentView = decorView.findViewById(android.R.id.content).getRootView();
            
            overlayView = new PerformanceOverlayView(activity);
            
            // Add to content view
            android.widget.FrameLayout.LayoutParams params = new android.widget.FrameLayout.LayoutParams(
                    android.widget.FrameLayout.LayoutParams.MATCH_PARENT,
                    android.widget.FrameLayout.LayoutParams.WRAP_CONTENT);
            
            if (contentView instanceof android.widget.FrameLayout) {
                ((android.widget.FrameLayout) contentView).addView(overlayView, params);
            }
            
            // Set up click to toggle expanded view
            overlayView.setOnClickListener(v -> {
                if (overlayView != null) {
                    overlayView.toggleExpanded();
                }
            });
        } else {
            // Remove overlay
            if (overlayView != null && overlayView.getParent() != null) {
                ((android.view.ViewGroup) overlayView.getParent()).removeView(overlayView);
                overlayView = null;
            }
        }
    }
}