package com.bhashasetu.app.util;

import android.content.Context;
import android.os.Build;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Choreographer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Utility class for monitoring application performance metrics.
 * Provides tools for tracking frame rates, memory usage, and operation timing.
 */
public class PerformanceMonitor {
    private static final String TAG = "PerformanceMonitor";
    private static PerformanceMonitor instance;
    
    private final Context context;
    private Handler mainHandler;
    private boolean isMonitoring = false;
    
    // Frame time tracking
    private long lastFrameTimeNanos;
    private int frameCounter;
    private long totalFrameTimeNanos;
    private int droppedFrames;
    private final long targetFrameTimeNanos = TimeUnit.SECONDS.toNanos(1) / 60; // ~16.7ms for 60fps
    
    // Operation timing
    private Map<String, Long> operationStartTimes = new HashMap<>();
    private Map<String, Long> operationDurations = new HashMap<>();
    
    /**
     * Get the singleton instance of PerformanceMonitor.
     */
    public static synchronized PerformanceMonitor getInstance(Context context) {
        if (instance == null) {
            instance = new PerformanceMonitor(context.getApplicationContext());
        }
        return instance;
    }
    
    private PerformanceMonitor(Context context) {
        this.context = context;
        this.mainHandler = new Handler(Looper.getMainLooper());
    }
    
    /**
     * Start monitoring performance metrics.
     */
    public void startMonitoring() {
        if (isMonitoring) {
            return;
        }
        
        isMonitoring = true;
        resetMetrics();
        
        // Start frame monitoring using Choreographer
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
                @Override
                public void doFrame(long frameTimeNanos) {
                    if (isMonitoring) {
                        trackFrame(frameTimeNanos);
                        Choreographer.getInstance().postFrameCallback(this);
                    }
                }
            });
        }
        
        Log.d(TAG, "Performance monitoring started");
    }
    
    /**
     * Stop monitoring performance metrics.
     */
    public void stopMonitoring() {
        isMonitoring = false;
        Log.d(TAG, "Performance monitoring stopped");
    }
    
    /**
     * Reset all performance metrics.
     */
    public void resetMetrics() {
        frameCounter = 0;
        totalFrameTimeNanos = 0;
        droppedFrames = 0;
        lastFrameTimeNanos = 0;
        operationStartTimes.clear();
        operationDurations.clear();
    }
    
    /**
     * Track a rendered frame.
     */
    private void trackFrame(long frameTimeNanos) {
        if (lastFrameTimeNanos != 0) {
            long frameDuration = frameTimeNanos - lastFrameTimeNanos;
            totalFrameTimeNanos += frameDuration;
            frameCounter++;
            
            // Consider a frame dropped if it took longer than the target time
            if (frameDuration > (targetFrameTimeNanos * 1.5)) {
                droppedFrames++;
            }
        }
        
        lastFrameTimeNanos = frameTimeNanos;
    }
    
    /**
     * Start timing an operation.
     * @param operationName A unique name for the operation
     */
    public void startOperation(String operationName) {
        operationStartTimes.put(operationName, System.nanoTime());
    }
    
    /**
     * End timing an operation.
     * @param operationName The name of the operation
     * @return The duration of the operation in milliseconds
     */
    public long endOperation(String operationName) {
        Long startTime = operationStartTimes.remove(operationName);
        if (startTime == null) {
            Log.w(TAG, "No start time found for operation: " + operationName);
            return 0;
        }
        
        long duration = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
        operationDurations.put(operationName, duration);
        
        Log.d(TAG, "Operation " + operationName + " took " + duration + "ms");
        return duration;
    }
    
    /**
     * Get the duration of a completed operation.
     * @param operationName The name of the operation
     * @return The duration in milliseconds, or 0 if not found
     */
    public long getOperationDuration(String operationName) {
        Long duration = operationDurations.get(operationName);
        return duration != null ? duration : 0;
    }
    
    /**
     * Get the current frames per second (FPS).
     * @return The FPS, or 0 if not enough frames have been recorded
     */
    public float getCurrentFps() {
        if (frameCounter < 10) {
            return 0;
        }
        
        float seconds = (float) totalFrameTimeNanos / TimeUnit.SECONDS.toNanos(1);
        return seconds > 0 ? frameCounter / seconds : 0;
    }
    
    /**
     * Get the percentage of dropped frames.
     * @return The percentage (0-100), or 0 if not enough frames have been recorded
     */
    public float getDroppedFramesPercentage() {
        if (frameCounter < 10) {
            return 0;
        }
        
        return (float) droppedFrames / frameCounter * 100;
    }
    
    /**
     * Get the current memory usage.
     * @return The memory usage in megabytes
     */
    public float getMemoryUsageMb() {
        Runtime runtime = Runtime.getRuntime();
        long usedMemory = runtime.totalMemory() - runtime.freeMemory();
        return usedMemory / (1024f * 1024f);
    }
    
    /**
     * Generate a performance report and save it to a file.
     * @return The path to the report file, or null if an error occurred
     */
    public String generatePerformanceReport() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US);
        String timestamp = dateFormat.format(new Date());
        String fileName = "performance_report_" + timestamp + ".txt";
        
        File reportFile = new File(context.getFilesDir(), fileName);
        
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(reportFile))) {
            writer.println("--- PERFORMANCE REPORT ---");
            writer.println("Date: " + new Date().toString());
            
            writer.println("\n--- FRAME METRICS ---");
            writer.println("Current FPS: " + getCurrentFps());
            writer.println("Dropped Frames: " + droppedFrames + " (" + getDroppedFramesPercentage() + "%)");
            writer.println("Total Frames: " + frameCounter);
            
            writer.println("\n--- MEMORY METRICS ---");
            Runtime runtime = Runtime.getRuntime();
            writer.println("Used Memory: " + getMemoryUsageMb() + " MB");
            writer.println("Total Memory: " + (runtime.totalMemory() / (1024 * 1024)) + " MB");
            writer.println("Max Memory: " + (runtime.maxMemory() / (1024 * 1024)) + " MB");
            
            writer.println("\n--- OPERATION TIMINGS ---");
            for (Map.Entry<String, Long> entry : operationDurations.entrySet()) {
                writer.println(entry.getKey() + ": " + entry.getValue() + " ms");
            }
            
            return reportFile.getAbsolutePath();
        } catch (IOException e) {
            Log.e(TAG, "Error writing performance report", e);
            return null;
        }
    }
    
    /**
     * Check if the app is responding properly.
     * @param timeoutMs Timeout in milliseconds
     * @param callback Callback to notify if the app is responding
     */
    public void checkResponsiveness(long timeoutMs, ResponsivenessCallback callback) {
        final boolean[] responded = {false};
        
        // Post a delayed runnable to check if the main thread is blocked
        mainHandler.postDelayed(() -> {
            responded[0] = true;
            if (callback != null) {
                callback.onResponsivenessCheck(true);
            }
        }, 0);
        
        // Check if the first runnable was executed within the timeout
        mainHandler.postDelayed(() -> {
            if (!responded[0] && callback != null) {
                callback.onResponsivenessCheck(false);
                
                // Try to diagnose the issue by taking a stack trace of the main thread
                Thread mainThread = Looper.getMainLooper().getThread();
                StackTraceElement[] stackTrace = mainThread.getStackTrace();
                
                StringBuilder sb = new StringBuilder();
                sb.append("Main thread appears to be blocked. Stack trace:\n");
                for (StackTraceElement element : stackTrace) {
                    sb.append("    at ").append(element.toString()).append("\n");
                }
                
                Log.w(TAG, sb.toString());
            }
        }, timeoutMs);
    }
    
    /**
     * Callback for responsiveness checks.
     */
    public interface ResponsivenessCallback {
        void onResponsivenessCheck(boolean isResponding);
    }
}