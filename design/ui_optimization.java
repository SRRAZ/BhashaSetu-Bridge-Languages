package com.example.englishhindi.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

/**
 * Utility class for UI optimizations throughout the application.
 * Provides methods to improve rendering performance and optimize view hierarchies.
 */
public class UIOptimization {

    /**
     * Enables hardware acceleration for a view if available.
     * Should be used for views with complex rendering.
     */
    public static void enableHardwareAcceleration(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
    }
    
    /**
     * Disables hardware acceleration for a view.
     * Should be used for views that don't render properly with hardware acceleration.
     */
    public static void disableHardwareAcceleration(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }
    
    /**
     * Sets a view to hardware or software rendering based on the rendering type.
     */
    public static void setRenderingType(View view, boolean useHardwareAcceleration) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            view.setLayerType(useHardwareAcceleration ? View.LAYER_TYPE_HARDWARE : View.LAYER_TYPE_SOFTWARE, null);
        }
    }
    
    /**
     * Optimizes a paint object for hardware acceleration.
     */
    public static void optimizePaint(Paint paint) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            paint.setAntiAlias(true);
            paint.setFilterBitmap(true);
            paint.setDither(true);
        }
    }
    
    /**
     * Enables view recycling flag for AdapterViews.
     * @param rootView The root view to scan for AdapterViews
     */
    public static void enableViewRecycling(ViewGroup rootView) {
        for (int i = 0; i < rootView.getChildCount(); i++) {
            View child = rootView.getChildAt(i);
            if (child instanceof android.widget.AbsListView) {
                ((android.widget.AbsListView) child).setRecyclerListener(
                        view -> {
                            // Clean up any references to the recycled view
                            if (view.getTag() instanceof ViewHolder) {
                                ((ViewHolder) view.getTag()).cleanup();
                            }
                        });
            } else if (child instanceof ViewGroup) {
                enableViewRecycling((ViewGroup) child);
            }
        }
    }
    
    /**
     * Interface for implementing the ViewHolder pattern in custom adapters.
     */
    public interface ViewHolder {
        void cleanup();
    }
    
    /**
     * Flattens a view hierarchy where possible.
     * @param context The context
     * @param originalView The original view hierarchy
     * @return A flattened view hierarchy
     */
    public static View flattenViewHierarchy(Context context, ViewGroup originalView) {
        // This is a placeholder for a more complex implementation
        // In a real implementation, you would analyze the view hierarchy
        // and merge unnecessary nested layouts
        
        // For demonstration purposes, just return the original view
        return originalView;
    }
    
    /**
     * Checks if a view is hardware accelerated.
     */
    public static boolean isHardwareAccelerated(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return view.isHardwareAccelerated();
        }
        return false;
    }
    
    /**
     * Checks if a canvas is hardware accelerated.
     */
    public static boolean isHardwareAccelerated(Canvas canvas) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return canvas.isHardwareAccelerated();
        }
        return false;
    }
    
    /**
     * Enables or disables clipping for a ViewGroup.
     * Disabling clipping can improve performance for views that don't need it.
     */
    public static void setClipChildren(ViewGroup viewGroup, boolean clip) {
        viewGroup.setClipChildren(clip);
        viewGroup.setClipToPadding(clip);
    }
    
    /**
     * Registers a callback to detect when a view is drawn.
     * Useful for measuring rendering performance.
     */
    public static void detectDrawTime(final View view, final DrawCallback callback) {
        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            private long startTime;
            
            @Override
            public boolean onPreDraw() {
                startTime = System.nanoTime();
                view.getViewTreeObserver().removeOnPreDrawListener(this);
                view.getViewTreeObserver().addOnDrawListener(new ViewTreeObserver.OnDrawListener() {
                    @Override
                    public void onDraw() {
                        long endTime = System.nanoTime();
                        long drawTimeMicros = (endTime - startTime) / 1000;
                        callback.onDrawComplete(drawTimeMicros);
                        view.getViewTreeObserver().removeOnDrawListener(this);
                    }
                });
                return true;
            }
        });
    }
    
    /**
     * Callback interface for draw time detection.
     */
    public interface DrawCallback {
        void onDrawComplete(long drawTimeMicros);
    }
}