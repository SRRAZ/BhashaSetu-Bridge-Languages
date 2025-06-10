package com.bhashasetu.app.monitoring;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Specialized memory monitoring system that tracks memory usage patterns,
 * detects potential memory leaks, and helps optimize memory utilization.
 */
public class MemoryMonitor {
    private static final String TAG = "MemoryMonitor";
    
    // Singleton instance
    private static MemoryMonitor instance;
    
    // Context
    private final Context context;
    
    // Handler for periodic checks
    private final Handler handler;
    
    // Memory data
    private long baselineMemoryUsage = 0;
    private long peakMemoryUsage = 0;
    private long lastMemoryUsage = 0;
    private long allocationCount = 0;
    private long deallocationCount = 0;
    
    // Memory dumps for leak analysis
    private final List<MemoryDump> memoryDumps = new ArrayList<>();
    private static final int MAX_MEMORY_DUMPS = 5;
    
    // Monitoring state
    private final AtomicBoolean isMonitoring = new AtomicBoolean(false);
    
    // Data points for trend analysis
    private final List<MemoryDataPoint> dataPoints = new ArrayList<>();
    private static final int MAX_DATA_POINTS = 60; // Store up to 60 data points
    
    // Listeners
    private final List<MemoryListener> listeners = new ArrayList<>();
    
    // Constants
    private static final long DEFAULT_INTERVAL_MS = 10000; // 10 seconds
    private static final float LEAK_THRESHOLD_PERCENT = 5.0f; // 5% growth indicates potential leak
    private static final float CRITICAL_MEMORY_THRESHOLD = 0.8f; // 80% of max memory
    
    /**
     * Memory monitoring listener.
     */
    public interface MemoryListener {
        void onMemoryWarning(int level, Map<String, Object> memoryInfo);
        void onPotentialLeak(String source, float growthRate);
    }
    
    /**
     * Memory data point for tracking memory usage over time.
     */
    private static class MemoryDataPoint {
        final long timestamp;
        final long usedMemory;
        final long totalMemory;
        final long nativeHeapSize;
        final int objectCount;
        
        MemoryDataPoint(long timestamp, long usedMemory, long totalMemory, long nativeHeapSize, int objectCount) {
            this.timestamp = timestamp;
            this.usedMemory = usedMemory;
            this.totalMemory = totalMemory;
            this.nativeHeapSize = nativeHeapSize;
            this.objectCount = objectCount;
        }
    }
    
    /**
     * Memory dump for deeper analysis.
     */
    private static class MemoryDump {
        final long timestamp;
        final String trigger;
        final File dumpFile;
        
        MemoryDump(long timestamp, String trigger, File dumpFile) {
            this.timestamp = timestamp;
            this.trigger = trigger;
            this.dumpFile = dumpFile;
        }
    }
    
    /**
     * Private constructor.
     *
     * @param context Application context
     */
    private MemoryMonitor(Context context) {
        this.context = context.getApplicationContext();
        this.handler = new Handler(Looper.getMainLooper());
    }
    
    /**
     * Get singleton instance.
     *
     * @param context Context
     * @return MemoryMonitor instance
     */
    public static synchronized MemoryMonitor getInstance(Context context) {
        if (instance == null) {
            instance = new MemoryMonitor(context);
        }
        return instance;
    }
    
    /**
     * Start memory monitoring.
     */
    public void start() {
        start(DEFAULT_INTERVAL_MS);
    }
    
    /**
     * Start memory monitoring with custom interval.
     *
     * @param intervalMs Monitoring interval in milliseconds
     */
    public void start(long intervalMs) {
        if (isMonitoring.getAndSet(true)) {
            return; // Already monitoring
        }
        
        // Record baseline memory usage
        recordBaselineMemory();
        
        // Schedule periodic checks
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isMonitoring.get()) {
                    checkMemory();
                    handler.postDelayed(this, intervalMs);
                }
            }
        }, intervalMs);
        
        Log.d(TAG, "Memory monitoring started with interval: " + intervalMs + "ms");
    }
    
    /**
     * Stop memory monitoring.
     */
    public void stop() {
        if (!isMonitoring.getAndSet(false)) {
            return; // Not monitoring
        }
        
        handler.removeCallbacksAndMessages(null);
        Log.d(TAG, "Memory monitoring stopped");
    }
    
    /**
     * Record baseline memory usage.
     */
    private void recordBaselineMemory() {
        Runtime runtime = Runtime.getRuntime();
        baselineMemoryUsage = runtime.totalMemory() - runtime.freeMemory();
        lastMemoryUsage = baselineMemoryUsage;
        peakMemoryUsage = baselineMemoryUsage;
        
        // Record initial data point
        recordDataPoint();
        
        Log.d(TAG, "Baseline memory usage: " + formatMemorySize(baselineMemoryUsage));
    }
    
    /**
     * Check current memory usage.
     */
    private void checkMemory() {
        // Get current memory stats
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        long maxMemory = runtime.maxMemory();
        
        // Update memory stats
        if (usedMemory > peakMemoryUsage) {
            peakMemoryUsage = usedMemory;
        }
        
        // Record data point
        recordDataPoint();
        
        // Check for memory warnings
        float memoryUsageRatio = (float) usedMemory / maxMemory;
        
        if (memoryUsageRatio > CRITICAL_MEMORY_THRESHOLD) {
            // Critical memory usage - report and take action
            Map<String, Object> memoryInfo = getDetailedMemoryInfo();
            notifyMemoryWarning(ActivityManager.OnTrimMemoryListener.TRIM_MEMORY_RUNNING_CRITICAL, memoryInfo);
            
            // Dump memory for analysis
            createMemoryDump("critical_memory");
        } else if (memoryUsageRatio > 0.7f) {
            // High memory usage - report
            Map<String, Object> memoryInfo = getDetailedMemoryInfo();
            notifyMemoryWarning(ActivityManager.OnTrimMemoryListener.TRIM_MEMORY_RUNNING_LOW, memoryInfo);
        }
        
        // Check for memory leaks (consistent growth)
        checkForLeaks();
        
        // Update last memory usage
        lastMemoryUsage = usedMemory;
    }
    
    /**
     * Record memory data point.
     */
    private void recordDataPoint() {
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        
        // Get additional memory metrics
        long nativeHeapSize = 0;
        int objectCount = 0;
        
        try {
            Debug.MemoryInfo memoryInfo = new Debug.MemoryInfo();
            Debug.getMemoryInfo(memoryInfo);
            
            // Get native heap size - API dependent
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                nativeHeapSize = memoryInfo.getTotalPss() * 1024; // Convert to bytes
            } else {
                nativeHeapSize = memoryInfo.getTotalPss() * 1024;
            }
            
            // We can't reliably get object count, but we could estimate based on allocated memory
            // This is a very rough approximation
            objectCount = (int) (usedMemory / 1024); // Assume ~1KB per object
        } catch (Exception e) {
            Log.e(TAG, "Error getting detailed memory info", e);
        }
        
        // Create data point
        MemoryDataPoint dataPoint = new MemoryDataPoint(
                System.currentTimeMillis(),
                usedMemory,
                totalMemory,
                nativeHeapSize,
                objectCount
        );
        
        // Add to list, maintaining max size
        synchronized (dataPoints) {
            dataPoints.add(dataPoint);
            if (dataPoints.size() > MAX_DATA_POINTS) {
                dataPoints.remove(0);
            }
        }
    }
    
    /**
     * Check for potential memory leaks.
     */
    private void checkForLeaks() {
        if (dataPoints.size() < 10) {
            return; // Need more data for analysis
        }
        
        // Analyze memory growth trend
        // Get first and last data points for the trend
        MemoryDataPoint first, last;
        synchronized (dataPoints) {
            first = dataPoints.get(0);
            last = dataPoints.get(dataPoints.size() - 1);
        }
        
        // Calculate growth rate (% per hour)
        float elapsedHours = (last.timestamp - first.timestamp) / (float) TimeUnit.HOURS.toMillis(1);
        if (elapsedHours < 0.01f) { // At least 0.01 hours (36 seconds)
            return;
        }
        
        float memoryGrowthPercent = ((last.usedMemory - first.usedMemory) / (float) first.usedMemory) * 100f / elapsedHours;
        float objectGrowthPercent = ((last.objectCount - first.objectCount) / (float) first.objectCount) * 100f / elapsedHours;
        
        // Check for concerning growth rates
        if (memoryGrowthPercent > LEAK_THRESHOLD_PERCENT) {
            // Potential memory leak detected
            String source = "JAVA_HEAP";
            
            // Create memory dump for analysis
            createMemoryDump("memory_growth");
            
            // Notify listeners
            for (MemoryListener listener : new ArrayList<>(listeners)) {
                listener.onPotentialLeak(source, memoryGrowthPercent);
            }
            
            Log.w(TAG, String.format("Potential memory leak detected (%s): %.2f%% growth per hour", 
                    source, memoryGrowthPercent));
        }
        
        // Also check native heap growth
        float nativeGrowthPercent = ((last.nativeHeapSize - first.nativeHeapSize) / (float) first.nativeHeapSize) * 100f / elapsedHours;
        if (nativeGrowthPercent > LEAK_THRESHOLD_PERCENT) {
            // Potential native memory leak
            String source = "NATIVE_HEAP";
            
            // Notify listeners
            for (MemoryListener listener : new ArrayList<>(listeners)) {
                listener.onPotentialLeak(source, nativeGrowthPercent);
            }
            
            Log.w(TAG, String.format("Potential native memory leak detected: %.2f%% growth per hour", 
                    nativeGrowthPercent));
        }
    }
    
    /**
     * Create memory dump for leak analysis.
     *
     * @param trigger Reason for the dump
     */
    private void createMemoryDump(String trigger) {
        try {
            // Create dump directory if it doesn't exist
            File dumpDir = new File(context.getFilesDir(), "memory_dumps");
            if (!dumpDir.exists()) {
                dumpDir.mkdirs();
            }
            
            // Create dump file
            long timestamp = System.currentTimeMillis();
            File dumpFile = new File(dumpDir, "dump_" + timestamp + ".hprof");
            
            // Dump memory
            Debug.dumpHprofData(dumpFile.getAbsolutePath());
            
            // Record dump
            MemoryDump dump = new MemoryDump(timestamp, trigger, dumpFile);
            synchronized (memoryDumps) {
                memoryDumps.add(dump);
                
                // Limit number of dumps
                if (memoryDumps.size() > MAX_MEMORY_DUMPS) {
                    MemoryDump oldestDump = memoryDumps.remove(0);
                    oldestDump.dumpFile.delete();
                }
            }
            
            Log.d(TAG, "Memory dump created: " + dumpFile.getAbsolutePath());
        } catch (IOException e) {
            Log.e(TAG, "Error creating memory dump", e);
        }
    }
    
    /**
     * Notify listeners of memory warning.
     *
     * @param level Memory warning level
     * @param memoryInfo Detailed memory information
     */
    private void notifyMemoryWarning(int level, Map<String, Object> memoryInfo) {
        // Notify listeners on main thread
        handler.post(() -> {
            for (MemoryListener listener : new ArrayList<>(listeners)) {
                listener.onMemoryWarning(level, memoryInfo);
            }
        });
    }
    
    /**
     * Get detailed memory information.
     *
     * @return Map of memory metrics
     */
    public Map<String, Object> getDetailedMemoryInfo() {
        Map<String, Object> info = new HashMap<>();
        
        // Runtime memory
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        long maxMemory = runtime.maxMemory();
        
        info.put("used_memory", usedMemory);
        info.put("total_memory", totalMemory);
        info.put("free_memory", freeMemory);
        info.put("max_memory", maxMemory);
        info.put("usage_percent", (usedMemory * 100f) / maxMemory);
        
        // System memory info
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            activityManager.getMemoryInfo(memoryInfo);
            
            info.put("system_available", memoryInfo.availMem);
            info.put("system_total", memoryInfo.totalMem);
            info.put("system_low_memory", memoryInfo.lowMemory);
            info.put("system_threshold", memoryInfo.threshold);
        } catch (Exception e) {
            Log.e(TAG, "Error getting system memory info", e);
        }
        
        // Native memory info
        try {
            Debug.MemoryInfo debugMemoryInfo = new Debug.MemoryInfo();
            Debug.getMemoryInfo(debugMemoryInfo);
            
            // Add PSS memory information
            info.put("pss_total", debugMemoryInfo.getTotalPss());
            info.put("pss_private", debugMemoryInfo.getTotalPrivateDirty());
            info.put("pss_shared", debugMemoryInfo.getTotalSharedDirty());
            
            // API 23+ memory stats
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                Map<String, String> memoryStats = debugMemoryInfo.getMemoryStats();
                for (Map.Entry<String, String> entry : memoryStats.entrySet()) {
                    try {
                        // Try to parse as number
                        info.put(entry.getKey(), Integer.parseInt(entry.getValue()));
                    } catch (NumberFormatException e) {
                        // Store as string if not a number
                        info.put(entry.getKey(), entry.getValue());
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error getting native memory info", e);
        }
        
        // Add historical data
        info.put("baseline_memory", baselineMemoryUsage);
        info.put("peak_memory", peakMemoryUsage);
        info.put("growth_from_baseline_percent", 
                ((usedMemory - baselineMemoryUsage) * 100f) / baselineMemoryUsage);
        
        return info;
    }
    
    /**
     * Add memory listener.
     *
     * @param listener Listener to add
     */
    public void addListener(MemoryListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }
    
    /**
     * Remove memory listener.
     *
     * @param listener Listener to remove
     */
    public void removeListener(MemoryListener listener) {
        listeners.remove(listener);
    }
    
    /**
     * Format memory size to readable string.
     *
     * @param bytes Memory size in bytes
     * @return Formatted string
     */
    public static String formatMemorySize(long bytes) {
        if (bytes < 1024) {
            return bytes + " B";
        } else if (bytes < 1024 * 1024) {
            return String.format("%.2f KB", bytes / 1024.0);
        } else if (bytes < 1024 * 1024 * 1024) {
            return String.format("%.2f MB", bytes / (1024.0 * 1024.0));
        } else {
            return String.format("%.2f GB", bytes / (1024.0 * 1024.0 * 1024.0));
        }
    }
    
    /**
     * Get memory trend data.
     *
     * @return List of memory data points
     */
    public List<Map<String, Object>> getMemoryTrend() {
        List<Map<String, Object>> trend = new ArrayList<>();
        
        synchronized (dataPoints) {
            for (MemoryDataPoint point : dataPoints) {
                Map<String, Object> data = new HashMap<>();
                data.put("timestamp", point.timestamp);
                data.put("used_memory", point.usedMemory);
                data.put("total_memory", point.totalMemory);
                data.put("native_heap", point.nativeHeapSize);
                data.put("object_count", point.objectCount);
                trend.add(data);
            }
        }
        
        return trend;
    }
    
    /**
     * Get summary memory statistics.
     *
     * @return Map of statistics
     */
    public Map<String, Object> getMemoryStats() {
        Map<String, Object> stats = new HashMap<>();
        
        // Current memory state
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        long maxMemory = runtime.maxMemory();
        
        stats.put("current_usage", usedMemory);
        stats.put("peak_usage", peakMemoryUsage);
        stats.put("baseline_usage", baselineMemoryUsage);
        stats.put("available_memory", maxMemory);
        stats.put("usage_ratio", (float) usedMemory / maxMemory);
        
        // Calculate growth rate
        if (dataPoints.size() >= 2) {
            MemoryDataPoint first = dataPoints.get(0);
            MemoryDataPoint last = dataPoints.get(dataPoints.size() - 1);
            
            float elapsedHours = (last.timestamp - first.timestamp) / (float) TimeUnit.HOURS.toMillis(1);
            if (elapsedHours >= 0.01f) {
                float growthRate = ((last.usedMemory - first.usedMemory) / (float) first.usedMemory) * 100f / elapsedHours;
                stats.put("growth_rate_percent_per_hour", growthRate);
            }
        }
        
        // Memory dumps info
        List<Map<String, Object>> dumps = new ArrayList<>();
        synchronized (memoryDumps) {
            for (MemoryDump dump : memoryDumps) {
                Map<String, Object> dumpInfo = new HashMap<>();
                dumpInfo.put("timestamp", dump.timestamp);
                dumpInfo.put("trigger", dump.trigger);
                dumpInfo.put("file_path", dump.dumpFile.getAbsolutePath());
                dumpInfo.put("file_size", dump.dumpFile.length());
                dumps.add(dumpInfo);
            }
        }
        stats.put("memory_dumps", dumps);
        
        return stats;
    }
    
    /**
     * Force a garbage collection.
     * Note: This is generally discouraged in production code.
     */
    public void forceGarbageCollection() {
        Log.d(TAG, "Manually triggering garbage collection");
        
        // Record memory before
        Runtime runtime = Runtime.getRuntime();
        long beforeFreeMemory = runtime.freeMemory();
        
        // Request garbage collection
        System.gc();
        
        // Wait a bit for GC to complete
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Record memory after
        long afterFreeMemory = runtime.freeMemory();
        long freedMemory = afterFreeMemory - beforeFreeMemory;
        
        Log.d(TAG, "Garbage collection freed approximately: " + formatMemorySize(freedMemory));
    }
    
    /**
     * Clear all memory dump files.
     */
    public void clearMemoryDumps() {
        synchronized (memoryDumps) {
            for (MemoryDump dump : memoryDumps) {
                dump.dumpFile.delete();
            }
            memoryDumps.clear();
        }
        
        Log.d(TAG, "Memory dumps cleared");
    }
    
    /**
     * Release resources.
     */
    public void release() {
        stop();
        listeners.clear();
    }
}