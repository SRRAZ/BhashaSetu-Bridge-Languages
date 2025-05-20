package com.example.englishhindi.monitoring;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Process;
import android.os.SystemClock;
import android.util.Log;
import android.view.Choreographer;

import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;
import androidx.collection.LongSparseArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Comprehensive performance monitoring system for tracking and analyzing application performance.
 * Monitors frame rates, startup time, memory usage, network operations, and more.
 */
public class PerformanceMonitor {
    private static final String TAG = "PerformanceMonitor";
    
    // Singleton instance
    private static PerformanceMonitor instance;
    
    // Context
    private final Context context;
    
    // Background thread for monitoring
    private final HandlerThread monitorThread;
    private final Handler monitorHandler;
    private final Handler mainHandler;
    
    // Monitoring state
    private boolean isMonitoring = false;
    
    // Frame monitoring
    private Choreographer.FrameCallback frameCallback;
    private long lastFrameTimeNanos = 0;
    private AtomicInteger droppedFrames = new AtomicInteger(0);
    private AtomicInteger totalFrames = new AtomicInteger(0);
    private long monitoringStartTimeMs = 0;
    private static final long FRAME_INTERVAL_NS = TimeUnit.SECONDS.toNanos(1) / 60; // 16.7ms for 60fps
    
    // Method tracing
    private final ConcurrentHashMap<String, TraceStat> traceStats = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Long> ongoingTraces = new ConcurrentHashMap<>();
    
    // Network monitoring
    private final ConcurrentHashMap<String, NetworkStat> networkStats = new ConcurrentHashMap<>();
    
    // Memory monitoring
    private AtomicLong lastMemoryUsage = new AtomicLong(0);
    private AtomicLong peakMemoryUsage = new AtomicLong(0);
    private LongSparseArray<Long> memoryTimeline = new LongSparseArray<>();
    private static final long MEMORY_MONITORING_INTERVAL_MS = 1000; // 1 second
    
    // Startup monitoring
    private long appStartTime = 0;
    private long firstFrameTime = 0;
    private final Map<String, Long> screenLoadTimes = new ConcurrentHashMap<>();
    
    // Listeners
    private final List<PerformanceListener> listeners = Collections.synchronizedList(new ArrayList<>());
    
    // Stats aggregation
    private final Map<String, Object> aggregatedStats = new ConcurrentHashMap<>();
    private static final long STATS_AGGREGATION_INTERVAL_MS = 5000; // 5 seconds
    
    /**
     * Interface for performance event listeners.
     */
    public interface PerformanceListener {
        void onPerformanceStats(Map<String, Object> stats);
    }
    
    /**
     * Create a new PerformanceMonitor instance.
     */
    private PerformanceMonitor(Context context) {
        this.context = context.getApplicationContext();
        
        // Create background thread for monitoring
        monitorThread = new HandlerThread("PerformanceMonitorThread", Process.THREAD_PRIORITY_BACKGROUND);
        monitorThread.start();
        monitorHandler = new Handler(monitorThread.getLooper());
        mainHandler = new Handler(Looper.getMainLooper());
        
        // Initialize frame callback
        frameCallback = new Choreographer.FrameCallback() {
            @Override
            public void doFrame(long frameTimeNanos) {
                if (lastFrameTimeNanos > 0) {
                    long frameInterval = frameTimeNanos - lastFrameTimeNanos;
                    totalFrames.incrementAndGet();
                    
                    // If frame took longer than 16.7ms (60fps), consider it dropped
                    if (frameInterval > FRAME_INTERVAL_NS * 2) {
                        // Calculate how many frames were dropped
                        long droppedCount = (frameInterval / FRAME_INTERVAL_NS) - 1;
                        droppedFrames.addAndGet((int) droppedCount);
                    }
                    
                    // Record first frame time if not already set
                    if (firstFrameTime == 0) {
                        firstFrameTime = SystemClock.uptimeMillis();
                    }
                }
                
                lastFrameTimeNanos = frameTimeNanos;
                
                if (isMonitoring) {
                    Choreographer.getInstance().postFrameCallback(this);
                }
            }
        };
        
        // Initialize monitoring
        schedulePeriodic();
    }
    
    /**
     * Get the singleton instance of PerformanceMonitor.
     */
    public static synchronized PerformanceMonitor getInstance(Context context) {
        if (instance == null) {
            instance = new PerformanceMonitor(context);
        }
        return instance;
    }
    
    /**
     * Start performance monitoring.
     */
    public void start() {
        if (isMonitoring) {
            return;
        }
        
        // Reset monitoring state
        resetMonitoringState();
        
        // Start frame monitoring
        monitoringStartTimeMs = SystemClock.uptimeMillis();
        Choreographer.getInstance().postFrameCallback(frameCallback);
        
        // Start memory monitoring
        monitorHandler.post(this::monitorMemory);
        
        // Schedule stats aggregation
        monitorHandler.postDelayed(this::aggregateStats, STATS_AGGREGATION_INTERVAL_MS);
        
        isMonitoring = true;
        Log.d(TAG, "Performance monitoring started");
    }
    
    /**
     * Stop performance monitoring.
     */
    public void stop() {
        if (!isMonitoring) {
            return;
        }
        
        isMonitoring = false;
        monitorHandler.removeCallbacksAndMessages(null);
        Log.d(TAG, "Performance monitoring stopped");
    }
    
    /**
     * Reset monitoring state.
     */
    private void resetMonitoringState() {
        droppedFrames.set(0);
        totalFrames.set(0);
        lastFrameTimeNanos = 0;
        lastMemoryUsage.set(0);
        peakMemoryUsage.set(0);
        memoryTimeline.clear();
        traceStats.clear();
        ongoingTraces.clear();
        networkStats.clear();
    }
    
    /**
     * Schedule periodic tasks.
     */
    private void schedulePeriodic() {
        // Aggregate stats periodically
        monitorHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isMonitoring) {
                    aggregateStats();
                    monitorHandler.postDelayed(this, STATS_AGGREGATION_INTERVAL_MS);
                }
            }
        }, STATS_AGGREGATION_INTERVAL_MS);
    }
    
    /**
     * Monitor memory usage.
     */
    private void monitorMemory() {
        if (!isMonitoring) {
            return;
        }
        
        // Get current memory usage
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        
        // Update peak memory usage
        if (usedMemory > peakMemoryUsage.get()) {
            peakMemoryUsage.set(usedMemory);
        }
        
        // Update last memory usage
        lastMemoryUsage.set(usedMemory);
        
        // Add to timeline
        long timeMs = SystemClock.uptimeMillis() - monitoringStartTimeMs;
        synchronized (memoryTimeline) {
            memoryTimeline.put(timeMs, usedMemory);
        }
        
        // Schedule next memory monitoring
        monitorHandler.postDelayed(this::monitorMemory, MEMORY_MONITORING_INTERVAL_MS);
    }
    
    /**
     * Record app start time.
     */
    public void recordAppStart() {
        appStartTime = SystemClock.uptimeMillis();
        Log.d(TAG, "App start time recorded: " + appStartTime);
    }
    
    /**
     * Start tracing a method or operation.
     * 
     * @param traceName Name of the trace
     * @return Trace ID for stopping
     */
    public String startTrace(String traceName) {
        if (!isMonitoring) {
            return "";
        }
        
        // Generate a unique trace ID
        String traceId = traceName + "-" + System.nanoTime();
        
        // Record start time
        ongoingTraces.put(traceId, System.nanoTime());
        
        return traceId;
    }
    
    /**
     * Stop a trace.
     * 
     * @param traceId Trace ID from startTrace
     */
    public void stopTrace(String traceId) {
        if (!isMonitoring || traceId == null || traceId.isEmpty()) {
            return;
        }
        
        // Get trace start time
        Long startTime = ongoingTraces.remove(traceId);
        if (startTime == null) {
            return;
        }
        
        // Calculate duration
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        
        // Extract trace name from ID
        String traceName = traceId.substring(0, traceId.lastIndexOf('-'));
        
        // Update trace stats
        TraceStat stat = traceStats.get(traceName);
        if (stat == null) {
            stat = new TraceStat(traceName);
            traceStats.put(traceName, stat);
        }
        stat.addSample(duration);
    }
    
    /**
     * Record screen load time.
     * 
     * @param screenName Name of the screen
     * @param loadTime Load time in milliseconds
     */
    public void recordScreenLoad(String screenName, long loadTime) {
        if (!isMonitoring) {
            return;
        }
        
        screenLoadTimes.put(screenName, loadTime);
        Log.d(TAG, String.format("Screen load: %s took %d ms", screenName, loadTime));
    }
    
    /**
     * Record network operation.
     * 
     * @param url URL being accessed
     * @param size Response size in bytes
     * @param duration Operation duration in milliseconds
     * @param statusCode HTTP status code (or 0 for non-HTTP)
     */
    public void recordNetworkOperation(String url, long size, long duration, int statusCode) {
        if (!isMonitoring) {
            return;
        }
        
        // Extract domain from URL
        String domain = extractDomain(url);
        
        // Update network stats
        NetworkStat stat = networkStats.get(domain);
        if (stat == null) {
            stat = new NetworkStat(domain);
            networkStats.put(domain, stat);
        }
        stat.addRequest(size, duration, statusCode);
    }
    
    /**
     * Extract domain from URL.
     */
    private String extractDomain(String url) {
        if (url == null || url.isEmpty()) {
            return "unknown";
        }
        
        try {
            java.net.URL parsedUrl = new java.net.URL(url);
            return parsedUrl.getHost();
        } catch (Exception e) {
            return "invalid-url";
        }
    }
    
    /**
     * Get current FPS.
     * 
     * @return Current frames per second
     */
    public float getCurrentFps() {
        if (monitoringStartTimeMs == 0) {
            return 0;
        }
        
        long elapsedMs = SystemClock.uptimeMillis() - monitoringStartTimeMs;
        if (elapsedMs <= 0) {
            return 0;
        }
        
        return (totalFrames.get() * 1000f) / elapsedMs;
    }
    
    /**
     * Get percentage of dropped frames.
     * 
     * @return Percentage of dropped frames (0-100)
     */
    public float getDroppedFramesPercentage() {
        int total = totalFrames.get();
        if (total == 0) {
            return 0;
        }
        
        return (droppedFrames.get() * 100f) / total;
    }
    
    /**
     * Get app startup time.
     * 
     * @return Time from app start to first frame in milliseconds
     */
    public long getStartupTime() {
        if (appStartTime == 0 || firstFrameTime == 0) {
            return 0;
        }
        
        return firstFrameTime - appStartTime;
    }
    
    /**
     * Add a performance listener.
     * 
     * @param listener Listener to add
     */
    public void addListener(PerformanceListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }
    
    /**
     * Remove a performance listener.
     * 
     * @param listener Listener to remove
     */
    public void removeListener(PerformanceListener listener) {
        listeners.remove(listener);
    }
    
    /**
     * Aggregate stats and notify listeners.
     */
    private void aggregateStats() {
        if (!isMonitoring) {
            return;
        }
        
        Map<String, Object> stats = new HashMap<>();
        
        // Frame stats
        stats.put("fps", getCurrentFps());
        stats.put("droppedFrames", droppedFrames.get());
        stats.put("totalFrames", totalFrames.get());
        stats.put("droppedFramesPercentage", getDroppedFramesPercentage());
        
        // Memory stats
        stats.put("currentMemoryUsage", lastMemoryUsage.get());
        stats.put("peakMemoryUsage", peakMemoryUsage.get());
        
        // Startup time
        stats.put("startupTime", getStartupTime());
        
        // Trace stats
        Map<String, Object> traceMap = new ArrayMap<>();
        for (TraceStat stat : traceStats.values()) {
            Map<String, Object> traceData = new ArrayMap<>();
            traceData.put("min", stat.getMin());
            traceData.put("max", stat.getMax());
            traceData.put("avg", stat.getAverage());
            traceData.put("count", stat.getCount());
            traceMap.put(stat.getName(), traceData);
        }
        stats.put("traces", traceMap);
        
        // Network stats
        Map<String, Object> networkMap = new ArrayMap<>();
        for (NetworkStat stat : networkStats.values()) {
            Map<String, Object> netData = new ArrayMap<>();
            netData.put("requests", stat.getRequestCount());
            netData.put("avgDuration", stat.getAverageDuration());
            netData.put("totalBytes", stat.getTotalBytes());
            netData.put("errors", stat.getErrorCount());
            networkMap.put(stat.getDomain(), netData);
        }
        stats.put("network", networkMap);
        
        // Screen load times
        stats.put("screenLoadTimes", new HashMap<>(screenLoadTimes));
        
        // Update aggregated stats
        aggregatedStats.clear();
        aggregatedStats.putAll(stats);
        
        // Notify listeners on main thread
        final Map<String, Object> finalStats = stats;
        mainHandler.post(() -> {
            for (PerformanceListener listener : new ArrayList<>(listeners)) {
                listener.onPerformanceStats(finalStats);
            }
        });
    }
    
    /**
     * Get the last aggregated stats.
     * 
     * @return Map of performance stats
     */
    public Map<String, Object> getStats() {
        return new HashMap<>(aggregatedStats);
    }
    
    /**
     * Format memory size for display.
     * 
     * @param bytes Memory size in bytes
     * @return Formatted string (KB, MB, etc.)
     */
    public static String formatMemorySize(long bytes) {
        if (bytes < 1024) {
            return bytes + " B";
        } else if (bytes < 1024 * 1024) {
            return String.format(Locale.US, "%.2f KB", bytes / 1024.0);
        } else if (bytes < 1024 * 1024 * 1024) {
            return String.format(Locale.US, "%.2f MB", bytes / (1024.0 * 1024.0));
        } else {
            return String.format(Locale.US, "%.2f GB", bytes / (1024.0 * 1024.0 * 1024.0));
        }
    }
    
    /**
     * Format time for display.
     * 
     * @param nanos Time in nanoseconds
     * @return Formatted string (μs, ms, s)
     */
    public static String formatTime(long nanos) {
        if (nanos < 1000) {
            return nanos + " ns";
        } else if (nanos < 1000 * 1000) {
            return String.format(Locale.US, "%.2f μs", nanos / 1000.0);
        } else if (nanos < 1000 * 1000 * 1000) {
            return String.format(Locale.US, "%.2f ms", nanos / (1000.0 * 1000.0));
        } else {
            return String.format(Locale.US, "%.2f s", nanos / (1000.0 * 1000.0 * 1000.0));
        }
    }
    
    /**
     * Release resources.
     */
    public void release() {
        stop();
        monitorThread.quitSafely();
        listeners.clear();
    }
    
    /**
     * Method trace statistics.
     */
    private static class TraceStat {
        private final String name;
        private long min = Long.MAX_VALUE;
        private long max = 0;
        private long total = 0;
        private int count = 0;
        
        TraceStat(String name) {
            this.name = name;
        }
        
        void addSample(long duration) {
            min = Math.min(min, duration);
            max = Math.max(max, duration);
            total += duration;
            count++;
        }
        
        String getName() {
            return name;
        }
        
        long getMin() {
            return min == Long.MAX_VALUE ? 0 : min;
        }
        
        long getMax() {
            return max;
        }
        
        long getAverage() {
            return count > 0 ? total / count : 0;
        }
        
        int getCount() {
            return count;
        }
    }
    
    /**
     * Network operation statistics.
     */
    private static class NetworkStat {
        private final String domain;
        private long totalBytes = 0;
        private long totalDuration = 0;
        private int requestCount = 0;
        private int errorCount = 0;
        
        NetworkStat(String domain) {
            this.domain = domain;
        }
        
        void addRequest(long bytes, long duration, int statusCode) {
            totalBytes += bytes;
            totalDuration += duration;
            requestCount++;
            
            // Consider HTTP status codes >= 400 as errors
            if (statusCode >= 400) {
                errorCount++;
            }
        }
        
        String getDomain() {
            return domain;
        }
        
        long getTotalBytes() {
            return totalBytes;
        }
        
        long getAverageDuration() {
            return requestCount > 0 ? totalDuration / requestCount : 0;
        }
        
        int getRequestCount() {
            return requestCount;
        }
        
        int getErrorCount() {
            return errorCount;
        }
    }
}