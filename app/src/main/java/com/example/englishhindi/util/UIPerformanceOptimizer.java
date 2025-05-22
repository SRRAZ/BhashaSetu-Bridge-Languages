package com.example.englishhindi.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.MessageQueue;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Choreographer;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * UI Performance optimization utility that improves responsiveness and rendering performance.
 * Provides tools for measuring and optimizing UI performance, reducing overdraw,
 * optimizing layouts, and improving animations.
 */
public class UIPerformanceOptimizer {
    private static final String TAG = "UIPerformanceOptimizer";
    
    // Singleton instance
    private static UIPerformanceOptimizer instance;
    
    // Frame timing tracking
    private final AtomicInteger framesOverBudget = new AtomicInteger(0);
    private final AtomicInteger totalFrames = new AtomicInteger(0);
    private long lastFrameTimeNanos = 0;
    
    // Jank tracking
    private boolean isTrackingJank = false;
    private final Handler uiHandler = new Handler(Looper.getMainLooper());
    private Choreographer.FrameCallback frameCallback;
    private static final long FRAME_TIME_MILLIS = 16; // ~60 fps
    
    // Layout tracking
    private final WeakHashMap<ViewGroup, Integer> layoutDepthCache = new WeakHashMap<>();
    private final List<ViewTraversalListener> traversalListeners = new ArrayList<>();
    
    /**
     * Interface for view traversal listeners.
     */
    public interface ViewTraversalListener {
        void onViewVisited(View view, int depth);
    }
    
    /**
     * Private constructor for singleton pattern.
     */
    private UIPerformanceOptimizer() {
        // Initialize frame callback for jank detection
        frameCallback = new Choreographer.FrameCallback() {
            @Override
            public void doFrame(long frameTimeNanos) {
                if (lastFrameTimeNanos > 0) {
                    long durationNanos = frameTimeNanos - lastFrameTimeNanos;
                    totalFrames.incrementAndGet();
                    
                    // If frame took more than 16ms, consider it dropped
                    if (durationNanos > TimeUnit.MILLISECONDS.toNanos(FRAME_TIME_MILLIS)) {
                        framesOverBudget.incrementAndGet();
                    }
                }
                
                lastFrameTimeNanos = frameTimeNanos;
                
                if (isTrackingJank) {
                    Choreographer.getInstance().postFrameCallback(this);
                }
            }
        };
    }
    
    /**
     * Get singleton instance.
     *
     * @return UIPerformanceOptimizer instance
     */
    public static UIPerformanceOptimizer getInstance() {
        if (instance == null) {
            synchronized (UIPerformanceOptimizer.class) {
                if (instance == null) {
                    instance = new UIPerformanceOptimizer();
                }
            }
        }
        return instance;
    }
    
    /**
     * Start tracking jank (dropped frames).
     */
    public void startJankTracking() {
        if (!isTrackingJank) {
            isTrackingJank = true;
            resetJankStats();
            Choreographer.getInstance().postFrameCallback(frameCallback);
        }
    }
    
    /**
     * Stop tracking jank.
     */
    public void stopJankTracking() {
        isTrackingJank = false;
    }
    
    /**
     * Reset jank statistics.
     */
    public void resetJankStats() {
        framesOverBudget.set(0);
        totalFrames.set(0);
        lastFrameTimeNanos = 0;
    }
    
    /**
     * Get jank percentage (dropped frames).
     *
     * @return Percentage of frames that missed vsync
     */
    public float getJankPercentage() {
        int total = totalFrames.get();
        if (total == 0) {
            return 0f;
        }
        return (framesOverBudget.get() * 100f) / total;
    }
    
    /**
     * Enable hardware acceleration for a view.
     *
     * @param view View to optimize
     */
    public void enableHardwareAcceleration(View view) {
        if (view != null) {
            view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
    }
    
    /**
     * Disable hardware acceleration for a view (for views with issues).
     *
     * @param view View to modify
     */
    public void disableHardwareAcceleration(View view) {
        if (view != null) {
            view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }
    
    /**
     * Apply performance optimizations to RecyclerView.
     *
     * @param recyclerView RecyclerView to optimize
     */
    public void optimizeRecyclerView(RecyclerView recyclerView) {
        if (recyclerView == null) {
            return;
        }
        
        // Set fixed size if content doesn't change size
        recyclerView.setHasFixedSize(true);
        
        // Optimize item animations to reduce jank
        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        if (animator != null) {
            animator.setChangeDuration(150);
            animator.setMoveDuration(150);
            animator.setAddDuration(150);
            animator.setRemoveDuration(150);
        }
        
        // Enable item prefetch
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager != null) {
            layoutManager.setItemPrefetchEnabled(true);
        }
        
        // Add scroll listener to disable animations during fast scrolling
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private boolean isFastScrolling = false;
            
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING || 
                    newState == RecyclerView.SCROLL_STATE_SETTLING) {
                    if (!isFastScrolling) {
                        isFastScrolling = true;
                        // Disable animations during fast scrolling
                        recyclerView.setItemAnimator(null);
                    }
                } else if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (isFastScrolling) {
                        isFastScrolling = false;
                        // Re-enable animations when idle
                        recyclerView.setItemAnimator(new androidx.recyclerview.widget.DefaultItemAnimator());
                    }
                }
            }
        });
    }
    
    /**
     * Apply performance optimizations to an AbsListView (ListView/GridView).
     *
     * @param listView ListView or GridView to optimize
     */
    public void optimizeListView(AbsListView listView) {
        if (listView == null) {
            return;
        }
        
        // Enable drawing cache and disable clip to padding
        listView.setScrollingCacheEnabled(false);
        listView.setClipToPadding(false);
        
        // Optimize scroll listener for smoother scrolling
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    // During fast fling, disable image loading to reduce jank
                    // This assumes the app uses an image loading library
                    disableImageLoading(view, true);
                } else {
                    // Re-enable image loading when scrolling stops
                    disableImageLoading(view, false);
                }
            }
            
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, 
                               int visibleItemCount, int totalItemCount) {
                // Nothing to do here
            }
        });
    }
    
    /**
     * Disable image loading during fast scrolling.
     *
     * @param viewGroup Parent view group
     * @param disable   Whether to disable loading
     */
    private void disableImageLoading(ViewGroup viewGroup, boolean disable) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            
            if (child instanceof ImageView) {
                // If using a tag to mark images for loading, can control it here
                child.setTag(disable ? "pause_loading" : null);
            } else if (child instanceof ViewGroup) {
                disableImageLoading((ViewGroup) child, disable);
            }
        }
    }
    
    /**
     * Remove window background to reduce overdraw if window content covers entire window.
     *
     * @param activity Activity to optimize
     */
    public void optimizeActivityOverdraw(Activity activity) {
        if (activity == null) {
            return;
        }
        
        Window window = activity.getWindow();
        if (window != null) {
            // Only remove window background if we're confident that the window content
            // will fill the entire window to avoid visual artifacts
            View contentView = window.getDecorView().findViewById(android.R.id.content);
            if (contentView instanceof ViewGroup) {
                ViewGroup content = (ViewGroup) contentView;
                
                // Check if the content has background
                if (content.getChildCount() > 0 && 
                    content.getChildAt(0).getBackground() != null) {
                    // Remove window background to reduce overdraw
                    window.setBackgroundDrawable(null);
                }
            }
        }
    }
    
    /**
     * Schedule a task to run when UI thread is idle.
     *
     * @param task Task to run
     */
    public void runWhenIdle(Runnable task) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Looper.getMainLooper().getQueue().addIdleHandler(new MessageQueue.IdleHandler() {
                @Override
                public boolean queueIdle() {
                    task.run();
                    return false; // Don't keep executing
                }
            });
        } else {
            // Fallback for older versions
            uiHandler.post(task);
        }
    }
    
    /**
     * Optimize animations for a view by setting appropriate layer type.
     *
     * @param view           View to optimize
     * @param animationDurationMs Animation duration in milliseconds
     */
    public void optimizeAnimation(View view, long animationDurationMs) {
        if (view == null) {
            return;
        }
        
        // Enable hardware acceleration during animation
        view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        
        // Reset layer type after animation completes
        uiHandler.postDelayed(() -> {
            if (view.isAttachedToWindow()) {
                view.setLayerType(View.LAYER_TYPE_NONE, null);
            }
        }, animationDurationMs + 50); // Add buffer
    }
    
    /**
     * Add padding to a view without affecting the view's measured dimensions.
     * This avoids layout passes when toggling padding.
     *
     * @param view    View to optimize
     * @param padding Padding to apply in pixels
     */
    public void setOptimizedPadding(View view, int padding) {
        if (view == null) {
            return;
        }
        
        // Using insets instead of padding to avoid measure/layout passes
        ViewCompat.setOnApplyWindowInsetsListener(view, (v, insets) -> {
            WindowInsetsCompat.Builder builder = new WindowInsetsCompat.Builder(insets);
            
            // Apply padding as insets
            Rect rect = new Rect(padding, padding, padding, padding);
            builder.setSystemWindowInsets(rect);
            
            return builder.build();
        });
    }
    
    /**
     * Calculate and cache view hierarchy depth.
     *
     * @param viewGroup ViewGroup to analyze
     * @return View hierarchy depth
     */
    public int getViewHierarchyDepth(ViewGroup viewGroup) {
        if (viewGroup == null) {
            return 0;
        }
        
        // Check cache first
        Integer cachedDepth = layoutDepthCache.get(viewGroup);
        if (cachedDepth != null) {
            return cachedDepth;
        }
        
        // Calculate depth
        int maxDepth = 0;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            int childDepth = 1;
            
            if (child instanceof ViewGroup) {
                childDepth += getViewHierarchyDepth((ViewGroup) child);
            }
            
            maxDepth = Math.max(maxDepth, childDepth);
        }
        
        // Cache result
        layoutDepthCache.put(viewGroup, maxDepth);
        return maxDepth;
    }
    
    /**
     * Add view traversal listener.
     *
     * @param listener Listener to add
     */
    public void addViewTraversalListener(ViewTraversalListener listener) {
        if (listener != null && !traversalListeners.contains(listener)) {
            traversalListeners.add(listener);
        }
    }
    
    /**
     * Remove view traversal listener.
     *
     * @param listener Listener to remove
     */
    public void removeViewTraversalListener(ViewTraversalListener listener) {
        traversalListeners.remove(listener);
    }
    
    /**
     * Traverse view hierarchy and notify listeners.
     *
     * @param root Root view to traverse
     */
    public void traverseViewHierarchy(View root) {
        traverseViewHierarchy(root, 0);
    }
    
    /**
     * Recursive implementation of view hierarchy traversal.
     *
     * @param view  Current view
     * @param depth Current depth
     */
    private void traverseViewHierarchy(View view, int depth) {
        if (view == null) {
            return;
        }
        
        // Notify listeners
        for (ViewTraversalListener listener : traversalListeners) {
            listener.onViewVisited(view, depth);
        }
        
        // Traverse children
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                traverseViewHierarchy(viewGroup.getChildAt(i), depth + 1);
            }
        }
    }
    
    /**
     * Calculate the approximate memory consumption of a view hierarchy.
     *
     * @param root Root view
     * @return Approximate memory usage in bytes
     */
    public long estimateViewMemoryUsage(View root) {
        if (root == null) {
            return 0;
        }
        
        // Basic view overhead
        long memoryUsage = 500; // Base memory overhead per view
        
        // Add memory for view's drawable, if any
        if (root.getBackground() != null) {
            memoryUsage += estimateDrawableMemoryUsage(root.getWidth(), root.getHeight());
        }
        
        // Special handling for ImageView
        if (root instanceof ImageView) {
            ImageView imageView = (ImageView) root;
            
            // If image dimensions are known, estimate based on size
            if (imageView.getDrawable() != null) {
                memoryUsage += estimateDrawableMemoryUsage(
                        imageView.getDrawable().getIntrinsicWidth(),
                        imageView.getDrawable().getIntrinsicHeight());
            } else {
                // Assume a typical image size
                memoryUsage += 200 * 1024; // Assume 200KB per image
            }
        }
        
        // Recursively process child views
        if (root instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) root;
            for (int i = 0; i < group.getChildCount(); i++) {
                memoryUsage += estimateViewMemoryUsage(group.getChildAt(i));
            }
        }
        
        return memoryUsage;
    }
    
    /**
     * Estimate drawable memory usage.
     *
     * @param width  Width in pixels
     * @param height Height in pixels
     * @return Estimated memory usage in bytes
     */
    private long estimateDrawableMemoryUsage(int width, int height) {
        if (width <= 0 || height <= 0) {
            return 0;
        }
        
        // Each pixel typically uses 4 bytes (ARGB_8888)
        return (long) width * height * 4;
    }
    
    /**
     * Detect and log UI elements that might be causing jank.
     *
     * @param rootView Root view to analyze
     * @param context  Context
     */
    public void detectJankSources(View rootView, Context context) {
        if (rootView == null || context == null) {
            return;
        }
        
        // Get screen dimensions
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        final int screenWidth = metrics.widthPixels;
        final int screenHeight = metrics.heightPixels;
        
        // Listen for layout passes
        final int[] layoutPassCount = {0};
        
        ViewTreeObserver observer = rootView.getViewTreeObserver();
        if (observer.isAlive()) {
            observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    layoutPassCount[0]++;
                    
                    // If too many layout passes occur, log potential issues
                    if (layoutPassCount[0] > 5) {
                        traverseViewHierarchy(rootView, 0);
                        rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            });
        }
        
        // Add traversal listener to detect potential jank sources
        addViewTraversalListener(new ViewTraversalListener() {
            @Override
            public void onViewVisited(View view, int depth) {
                // Check for deep hierarchies
                if (depth > 10) {
                    Log.w(TAG, "Deep view hierarchy detected: depth " + depth + 
                               " for " + view.getClass().getSimpleName());
                }
                
                // Check for oversized views
                if (view.getWidth() > screenWidth * 2 || view.getHeight() > screenHeight * 2) {
                    Log.w(TAG, "Oversized view detected: " + 
                               view.getWidth() + "x" + view.getHeight() + 
                               " for " + view.getClass().getSimpleName());
                }
                
                // Check for hardware acceleration layer issues
                if (view.getLayerType() == View.LAYER_TYPE_HARDWARE && view instanceof ViewGroup) {
                    ViewGroup group = (ViewGroup) view;
                    if (group.getChildCount() > 10) {
                        Log.w(TAG, "Hardware accelerated ViewGroup with many children: " + 
                                   group.getChildCount() + " children");
                    }
                }
                
                // Check for inefficient ViewGroups
                if (view instanceof ViewGroup) {
                    ViewGroup group = (ViewGroup) view;
                    
                    if (group.getChildCount() == 1) {
                        // Single-child ViewGroups can often be eliminated
                        Log.d(TAG, "Potential unnecessary ViewGroup with single child: " + 
                                   group.getClass().getSimpleName());
                    }
                }
            }
        });
        
        // Traverse view hierarchy to detect issues
        traverseViewHierarchy(rootView);
    }
    
    /**
     * Apply optimizations for a heavy activity that needs to start quickly.
     *
     * @param activity          Activity to optimize
     * @param contentLayoutResId Layout resource ID or 0 if already set
     */
    public void optimizeActivityLaunch(Activity activity, int contentLayoutResId) {
        if (activity == null) {
            return;
        }
        
        // Set content view with optimizations
        if (contentLayoutResId != 0) {
            activity.setContentView(contentLayoutResId);
        }
        
        // Find the root view
        ViewGroup rootView = activity.findViewById(android.R.id.content);
        if (rootView == null || rootView.getChildCount() == 0) {
            return;
        }
        
        View contentView = rootView.getChildAt(0);
        
        // Optimize window background for performance
        optimizeActivityOverdraw(activity);
        
        // Enable concurrent rendering (API 21+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            contentView.setThreadedRenderingEnabled(true);
        }
        
        // Optimize view inflation by setting a few key flags after the view is rendered
        contentView.post(() -> {
            // Disable unnecessary animations
            activity.getWindow().setWindowAnimations(0);
            
            // Optimize visually expensive views
            optimizeExpensiveViews(contentView);
        });
    }
    
    /**
     * Optimize known expensive views (RecyclerView, etc).
     *
     * @param rootView Root view to analyze
     */
    private void optimizeExpensiveViews(View rootView) {
        if (rootView instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) rootView;
            
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                
                if (child instanceof RecyclerView) {
                    optimizeRecyclerView((RecyclerView) child);
                } else if (child instanceof AbsListView) {
                    optimizeListView((AbsListView) child);
                } else if (child instanceof ViewGroup) {
                    optimizeExpensiveViews(child);
                }
            }
        }
    }
    
    /**
     * Set a view to render in hardware layer during an animation.
     *
     * @param view          View to animate
     * @param durationMillis Animation duration in milliseconds
     * @param callback       Optional callback when animation completes
     */
    public void setHardwareLayerForAnimation(View view, long durationMillis, 
                                          @Nullable Runnable callback) {
        if (view == null) {
            return;
        }
        
        // Set hardware layer type
        view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        
        // Reset layer type after animation
        uiHandler.postDelayed(() -> {
            if (view.isAttachedToWindow()) {
                view.setLayerType(View.LAYER_TYPE_NONE, null);
                
                if (callback != null) {
                    callback.run();
                }
            }
        }, durationMillis);
    }
    
    /**
     * Force render hardware layers immediately.
     *
     * @param view View to render
     */
    public void forceRenderHardwareLayer(View view) {
        if (view != null && view.getLayerType() == View.LAYER_TYPE_HARDWARE) {
            view.buildLayer();
        }
    }
}