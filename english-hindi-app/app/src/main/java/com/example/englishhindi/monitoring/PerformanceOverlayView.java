package com.example.englishhindi.monitoring;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Debug overlay view that displays performance metrics for development and testing.
 * This view shows real-time information about FPS, memory usage, and other
 * performance metrics on top of the UI for easy debugging.
 */
public class PerformanceOverlayView extends View {
    
    // Performance data
    private float fps = 0;
    private float droppedFramesPercent = 0;
    private long memoryUsage = 0;
    private long memoryTotal = 0;
    private String networkType = "";
    private float downloadSpeed = 0;
    private List<String> customMetrics = new ArrayList<>();
    
    // UI elements
    private final Paint textPaint = new Paint();
    private final Paint backgroundPaint = new Paint();
    private final Rect textBounds = new Rect();
    
    // Update handler
    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable updateRunnable;
    private static final int UPDATE_INTERVAL_MS = 1000; // 1 second
    
    // Display state
    private boolean isExpanded = false;
    
    /**
     * Create a new performance overlay view.
     *
     * @param context Context
     */
    public PerformanceOverlayView(Context context) {
        super(context);
        init();
    }
    
    /**
     * Create a new performance overlay view.
     *
     * @param context Context
     * @param attrs Attribute set
     */
    public PerformanceOverlayView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    /**
     * Create a new performance overlay view.
     *
     * @param context Context
     * @param attrs Attribute set
     * @param defStyleAttr Default style attribute
     */
    public PerformanceOverlayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    
    /**
     * Initialize the view.
     */
    private void init() {
        // Configure text paint
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(40);
        textPaint.setAntiAlias(true);
        textPaint.setTypeface(Typeface.MONOSPACE);
        
        // Configure background paint
        backgroundPaint.setColor(Color.argb(160, 0, 0, 0));
        
        // Start updates
        startUpdates();
    }
    
    /**
     * Start periodic updates.
     */
    private void startUpdates() {
        updateRunnable = new Runnable() {
            @Override
            public void run() {
                // Get performance data
                updatePerformanceData();
                
                // Redraw
                invalidate();
                
                // Schedule next update
                handler.postDelayed(this, UPDATE_INTERVAL_MS);
            }
        };
        
        handler.post(updateRunnable);
    }
    
    /**
     * Stop updates when the view is detached.
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeCallbacks(updateRunnable);
    }
    
    /**
     * Update performance data from monitoring components.
     */
    private void updatePerformanceData() {
        Context context = getContext();
        
        PerformanceMonitoringManager monitoringManager = 
                PerformanceMonitoringManager.getInstance(context);
        
        if (!monitoringManager.isEnabled()) {
            return;
        }
        
        // Get performance data
        PerformanceMonitor performanceMonitor = monitoringManager.getPerformanceMonitor();
        MemoryMonitor memoryMonitor = monitoringManager.getMemoryMonitor();
        NetworkMonitor networkMonitor = monitoringManager.getNetworkMonitor();
        
        // Update metrics
        fps = performanceMonitor.getCurrentFps();
        droppedFramesPercent = performanceMonitor.getDroppedFramesPercentage();
        
        // Get memory info
        Map<String, Object> memoryInfo = memoryMonitor.getDetailedMemoryInfo();
        if (memoryInfo != null) {
            memoryUsage = (Long) memoryInfo.getOrDefault("used_memory", 0L);
            memoryTotal = (Long) memoryInfo.getOrDefault("max_memory", 0L);
        }
        
        // Get network info
        NetworkMonitor.NetworkState networkState = networkMonitor.getNetworkState();
        networkType = networkState.getTypeName();
        
        Map<String, Object> networkMetrics = networkMonitor.getNetworkMetrics();
        if (networkMetrics != null && networkMetrics.containsKey("estimated_download_mbps")) {
            downloadSpeed = ((Number) networkMetrics.get("estimated_download_mbps")).floatValue();
        }
        
        // Clear custom metrics
        customMetrics.clear();
        
        // Add detailed metrics if expanded
        if (isExpanded) {
            // Performance metrics
            Map<String, Object> perfStats = performanceMonitor.getStats();
            
            // Add startup time if available
            long startupTime = performanceMonitor.getStartupTime();
            if (startupTime > 0) {
                customMetrics.add("Startup: " + startupTime + " ms");
            }
            
            // Add memory details
            float memPercentage = memoryTotal > 0 ? (memoryUsage * 100f / memoryTotal) : 0;
            customMetrics.add(String.format(Locale.US, "Mem: %.1f%%", memPercentage));
            
            // Get peak memory
            if (memoryInfo != null && memoryInfo.containsKey("peak_memory")) {
                long peakMemory = (Long) memoryInfo.get("peak_memory");
                customMetrics.add("Peak: " + formatMemorySize(peakMemory));
            }
            
            // Add network details
            customMetrics.add("Net: " + (networkState.isAvailable() ? "Up" : "Down") + 
                    " (" + (networkState.isWifi() ? "WiFi" : "Mobile") + ")");
            
            if (networkMetrics != null) {
                int requests = ((Number) networkMetrics.getOrDefault("total_requests", 0)).intValue();
                int errors = ((Number) networkMetrics.getOrDefault("failed_requests", 0)).intValue();
                customMetrics.add("Req: " + requests + " Err: " + errors);
            }
        }
    }
    
    /**
     * Draw the overlay.
     *
     * @param canvas Canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        int width = getWidth();
        int height = getHeight();
        
        // Background
        int overlayHeight = isExpanded ? 300 : 100;
        canvas.drawRect(0, 0, width, overlayHeight, backgroundPaint);
        
        // Draw performance metrics
        int y = 50;
        int padding = 10;
        
        // FPS
        String fpsText = String.format(Locale.US, "%.1f FPS", fps);
        textPaint.getTextBounds(fpsText, 0, fpsText.length(), textBounds);
        canvas.drawText(fpsText, padding, y, textPaint);
        
        // Memory
        String memoryText = formatMemorySize(memoryUsage);
        textPaint.getTextBounds(memoryText, 0, memoryText.length(), textBounds);
        int memX = width - textBounds.width() - padding;
        canvas.drawText(memoryText, memX, y, textPaint);
        
        // Network
        if (isExpanded) {
            y += 60;
            String networkText = networkType + " " + String.format(Locale.US, "%.1f Mbps", downloadSpeed);
            canvas.drawText(networkText, padding, y, textPaint);
            
            // Draw custom metrics
            for (String metric : customMetrics) {
                y += 50;
                canvas.drawText(metric, padding, y, textPaint);
            }
        }
    }
    
    /**
     * Toggle expanded state.
     */
    public void toggleExpanded() {
        isExpanded = !isExpanded;
        invalidate();
    }
    
    /**
     * Set expanded state.
     *
     * @param expanded true to expand, false to collapse
     */
    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
        invalidate();
    }
    
    /**
     * Format memory size for display.
     *
     * @param bytes Memory size in bytes
     * @return Formatted string
     */
    private String formatMemorySize(long bytes) {
        if (bytes < 1024) {
            return bytes + " B";
        } else if (bytes < 1024 * 1024) {
            return String.format(Locale.US, "%.1f KB", bytes / 1024f);
        } else if (bytes < 1024 * 1024 * 1024) {
            return String.format(Locale.US, "%.1f MB", bytes / (1024f * 1024f));
        } else {
            return String.format(Locale.US, "%.1f GB", bytes / (1024f * 1024f * 1024f));
        }
    }
}