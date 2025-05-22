package com.example.englishhindi.monitoring;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.englishhindi.BuildConfig;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Debug utility for performance monitoring that provides tools for analyzing
 * and debugging performance issues during development.
 * This class is only active in debug builds.
 */
public class PerformanceDebugUtility {
    
    private static final String TAG = "PerfDebugUtility";
    
    // Singleton instance
    private static PerformanceDebugUtility instance;
    
    // Context
    private final Context context;
    
    // References to monitoring components
    private final PerformanceMonitoringManager monitoringManager;
    
    // Debug state
    private boolean isEnabled = false;
    
    /**
     * Private constructor.
     *
     * @param context Application context
     */
    private PerformanceDebugUtility(Context context) {
        this.context = context.getApplicationContext();
        this.monitoringManager = PerformanceMonitoringManager.getInstance(context);
    }
    
    /**
     * Get singleton instance.
     *
     * @param context Context
     * @return PerformanceDebugUtility instance
     */
    public static synchronized PerformanceDebugUtility getInstance(Context context) {
        if (instance == null) {
            instance = new PerformanceDebugUtility(context);
        }
        return instance;
    }
    
    /**
     * Enable debug utility.
     * Only works in debug builds.
     */
    public void enable() {
        if (!BuildConfig.DEBUG) {
            return;
        }
        
        isEnabled = true;
    }
    
    /**
     * Disable debug utility.
     */
    public void disable() {
        isEnabled = false;
    }
    
    /**
     * Capture a performance snapshot and save to file.
     * This can be useful for debugging performance issues.
     *
     * @return Path to the saved file, or null if saving failed
     */
    public String capturePerformanceSnapshot() {
        if (!isEnabled) {
            return null;
        }
        
        // Collect snapshot data
        Map<String, Object> snapshot = collectPerformanceData();
        
        // Convert to JSON
        JSONObject json = new JSONObject();
        try {
            // Convert map to JSON
            for (Map.Entry<String, Object> entry : snapshot.entrySet()) {
                if (entry.getValue() instanceof Map) {
                    // Handle nested maps
                    JSONObject nested = new JSONObject();
                    Map<?, ?> nestedMap = (Map<?, ?>) entry.getValue();
                    
                    for (Map.Entry<?, ?> nestedEntry : nestedMap.entrySet()) {
                        if (nestedEntry.getKey() instanceof String) {
                            nested.put((String) nestedEntry.getKey(), nestedEntry.getValue());
                        }
                    }
                    
                    json.put(entry.getKey(), nested);
                } else {
                    json.put(entry.getKey(), entry.getValue());
                }
            }
            
            // Save to file
            File debugDir = new File(context.getFilesDir(), "performance_debug");
            if (!debugDir.exists()) {
                debugDir.mkdirs();
            }
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US);
            String timestamp = dateFormat.format(new Date());
            
            File snapshotFile = new File(debugDir, "perf_snapshot_" + timestamp + ".json");
            
            try (FileOutputStream fos = new FileOutputStream(snapshotFile)) {
                fos.write(json.toString(2).getBytes());
            }
            
            // Show toast on main thread
            new Handler(Looper.getMainLooper()).post(() -> {
                Toast.makeText(context, "Performance snapshot saved to " + snapshotFile.getName(),
                        Toast.LENGTH_SHORT).show();
            });
            
            return snapshotFile.getAbsolutePath();
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Create an intent to display performance data in a debug activity.
     * The app should have a dedicated performance debug activity to handle this intent.
     *
     * @return Intent with performance data
     */
    public Intent createPerformanceDebugIntent() {
        if (!isEnabled) {
            return null;
        }
        
        // Create an intent for a debug activity
        Intent intent = new Intent();
        intent.setAction("com.example.englishhindi.DEBUG_PERFORMANCE");
        
        // Add performance data as extras
        Bundle extras = new Bundle();
        
        // Performance metrics
        Map<String, Object> perfStats = monitoringManager.getPerformanceMonitor().getStats();
        extras.putFloat("fps", ((Number) perfStats.getOrDefault("fps", 0)).floatValue());
        extras.putInt("dropped_frames", ((Number) perfStats.getOrDefault("droppedFrames", 0)).intValue());
        extras.putFloat("dropped_frames_pct", ((Number) perfStats.getOrDefault("droppedFramesPercentage", 0)).floatValue());
        
        // Memory metrics
        Map<String, Object> memoryInfo = monitoringManager.getMemoryMonitor().getDetailedMemoryInfo();
        extras.putLong("used_memory", ((Number) memoryInfo.getOrDefault("used_memory", 0)).longValue());
        extras.putLong("total_memory", ((Number) memoryInfo.getOrDefault("total_memory", 0)).longValue());
        extras.putLong("free_memory", ((Number) memoryInfo.getOrDefault("free_memory", 0)).longValue());
        
        // Network metrics
        Map<String, Object> networkMetrics = monitoringManager.getNetworkMonitor().getNetworkMetrics();
        extras.putInt("total_requests", ((Number) networkMetrics.getOrDefault("total_requests", 0)).intValue());
        extras.putInt("failed_requests", ((Number) networkMetrics.getOrDefault("failed_requests", 0)).intValue());
        extras.putFloat("avg_response_time", ((Number) networkMetrics.getOrDefault("avg_response_time_ms", 0)).floatValue());
        
        intent.putExtras(extras);
        return intent;
    }
    
    /**
     * Collect performance data from all monitoring components.
     *
     * @return Map of performance data
     */
    private Map<String, Object> collectPerformanceData() {
        Map<String, Object> data = new HashMap<>();
        
        // Device info
        Map<String, Object> deviceInfo = new HashMap<>();
        deviceInfo.put("device_model", Build.MODEL);
        deviceInfo.put("device_manufacturer", Build.MANUFACTURER);
        deviceInfo.put("api_level", Build.VERSION.SDK_INT);
        deviceInfo.put("os_version", Build.VERSION.RELEASE);
        data.put("device", deviceInfo);
        
        // Application info
        Map<String, Object> appInfo = new HashMap<>();
        appInfo.put("version_name", BuildConfig.VERSION_NAME);
        appInfo.put("version_code", BuildConfig.VERSION_CODE);
        appInfo.put("build_type", BuildConfig.BUILD_TYPE);
        appInfo.put("timestamp", System.currentTimeMillis());
        data.put("app", appInfo);
        
        // Performance metrics
        data.put("performance", monitoringManager.getPerformanceMonitor().getStats());
        
        // Memory metrics
        data.put("memory", monitoringManager.getMemoryMonitor().getDetailedMemoryInfo());
        
        // Network metrics
        data.put("network", monitoringManager.getNetworkMonitor().getNetworkMetrics());
        
        return data;
    }
    
    /**
     * Run a stress test to evaluate app performance under load.
     * This method creates artificial load on the app to test its performance.
     * ONLY FOR DEBUG USE!
     *
     * @param durationMs Duration of the stress test in milliseconds
     */
    public void runPerformanceStressTest(final long durationMs) {
        if (!isEnabled || !BuildConfig.DEBUG) {
            return; // Only allow in debug builds
        }
        
        // Show toast
        new Handler(Looper.getMainLooper()).post(() -> {
            Toast.makeText(context, "Starting performance stress test for " + (durationMs / 1000) + " seconds",
                    Toast.LENGTH_SHORT).show();
        });
        
        // Create artificial load in a background thread
        new Thread(() -> {
            // Record start time
            long startTime = System.currentTimeMillis();
            long endTime = startTime + durationMs;
            
            // Create memory pressure
            List<byte[]> memoryBlocks = new ArrayList<>();
            
            // Run until duration expires
            while (System.currentTimeMillis() < endTime) {
                try {
                    // Allocate some memory
                    if (Math.random() < 0.3) { // 30% chance
                        memoryBlocks.add(new byte[500 * 1024]); // 500KB blocks
                        
                        // Don't let it grow too large
                        if (memoryBlocks.size() > 20) {
                            memoryBlocks.remove(0);
                        }
                    }
                    
                    // Create some CPU load
                    long n = (long) (Math.random() * 10000);
                    for (long i = 0; i < n; i++) {
                        Math.sqrt(i);
                    }
                    
                    // Sleep a bit to prevent CPU hogging
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    break;
                }
            }
            
            // Clean up
            memoryBlocks.clear();
            
            // Show completion toast
            new Handler(Looper.getMainLooper()).post(() -> {
                Toast.makeText(context, "Performance stress test completed",
                        Toast.LENGTH_SHORT).show();
            });
        }).start();
    }
}