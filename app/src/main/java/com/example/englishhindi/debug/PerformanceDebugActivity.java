package com.example.englishhindi.debug;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.englishhindi.activity.PerformanceMonitoredActivity;
import com.example.englishhindi.monitoring.PerformanceDebugUtility;
import com.example.englishhindi.monitoring.PerformanceMonitoringManager;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Debug activity for displaying performance metrics.
 * This activity is only for development and testing purposes.
 */
public class PerformanceDebugActivity extends PerformanceMonitoredActivity {
    
    private TextView tvFpsValue;
    private TextView tvMemoryValue;
    private TextView tvNetworkValue;
    private TextView tvDetailedMetrics;
    
    private Button btnTakeSnapshot;
    private Button btnRunStressTest;
    private Button btnToggleOverlay;
    
    private Handler refreshHandler = new Handler(Looper.getMainLooper());
    private Runnable refreshRunnable;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance_debug);
        
        initViews();
        setupButtons();
        setupPeriodicRefresh();
    }
    
    private void initViews() {
        tvFpsValue = findViewById(R.id.tv_fps_value);
        tvMemoryValue = findViewById(R.id.tv_memory_value);
        tvNetworkValue = findViewById(R.id.tv_network_value);
        tvDetailedMetrics = findViewById(R.id.tv_detailed_metrics);
        
        btnTakeSnapshot = findViewById(R.id.btn_take_snapshot);
        btnRunStressTest = findViewById(R.id.btn_run_stress_test);
        btnToggleOverlay = findViewById(R.id.btn_toggle_overlay);
    }
    
    private void setupButtons() {
        btnTakeSnapshot.setOnClickListener(v -> {
            PerformanceDebugUtility debugUtil = PerformanceDebugUtility.getInstance(this);
            debugUtil.enable();
            String snapshotPath = debugUtil.capturePerformanceSnapshot();
            
            if (snapshotPath != null) {
                tvDetailedMetrics.append("\nSnapshot saved to: " + snapshotPath);
            }
        });
        
        btnRunStressTest.setOnClickListener(v -> {
            PerformanceDebugUtility debugUtil = PerformanceDebugUtility.getInstance(this);
            debugUtil.enable();
            debugUtil.runPerformanceStressTest(TimeUnit.SECONDS.toMillis(10)); // 10 seconds
            
            tvDetailedMetrics.append("\nRunning stress test for 10 seconds...");
        });
        
        btnToggleOverlay.setOnClickListener(v -> {
            // Toggle overlay
            boolean currentState = getPreferences(MODE_PRIVATE).getBoolean("overlay_visible", true);
            boolean newState = !currentState;
            
            showPerformanceOverlay(newState);
            getPreferences(MODE_PRIVATE).edit().putBoolean("overlay_visible", newState).apply();
            
            btnToggleOverlay.setText(newState ? "Hide Overlay" : "Show Overlay");
        });
    }
    
    private void setupPeriodicRefresh() {
        refreshRunnable = new Runnable() {
            @Override
            public void run() {
                updateMetrics();
                refreshHandler.postDelayed(this, 1000); // Update every second
            }
        };
        
        refreshHandler.post(refreshRunnable);
    }
    
    private void updateMetrics() {
        PerformanceMonitoringManager monitoringManager = getMonitoringManager();
        
        // Get performance metrics
        Map<String, Object> perfStats = monitoringManager.getPerformanceMonitor().getStats();
        float fps = ((Number) perfStats.getOrDefault("fps", 0)).floatValue();
        float droppedFramesPct = ((Number) perfStats.getOrDefault("droppedFramesPercentage", 0)).floatValue();
        
        tvFpsValue.setText(String.format(Locale.US, "%.1f FPS (%.1f%% dropped)", fps, droppedFramesPct));
        
        // Get memory metrics
        Map<String, Object> memoryInfo = monitoringManager.getMemoryMonitor().getDetailedMemoryInfo();
        long usedMemory = ((Number) memoryInfo.getOrDefault("used_memory", 0)).longValue();
        long totalMemory = ((Number) memoryInfo.getOrDefault("total_memory", 0)).longValue();
        float memUsagePct = (usedMemory * 100f) / totalMemory;
        
        tvMemoryValue.setText(String.format(Locale.US, "%s / %s (%.1f%%)",
                formatMemorySize(usedMemory),
                formatMemorySize(totalMemory),
                memUsagePct));
        
        // Get network metrics
        Map<String, Object> networkMetrics = monitoringManager.getNetworkMonitor().getNetworkMetrics();
        int totalRequests = ((Number) networkMetrics.getOrDefault("total_requests", 0)).intValue();
        int failedRequests = ((Number) networkMetrics.getOrDefault("failed_requests", 0)).intValue();
        float avgResponseTime = ((Number) networkMetrics.getOrDefault("avg_response_time_ms", 0)).floatValue();
        
        tvNetworkValue.setText(String.format(Locale.US, "%d requests, %d failed, %.1f ms avg",
                totalRequests, failedRequests, avgResponseTime));
        
        // Build detailed metrics
        StringBuilder detailedMetrics = new StringBuilder();
        
        // Startup time
        long startupTime = monitoringManager.getPerformanceMonitor().getStartupTime();
        if (startupTime > 0) {
            detailedMetrics.append("App startup time: ").append(startupTime).append(" ms\n\n");
        }
        
        // Memory details
        detailedMetrics.append("Memory Details:\n");
        detailedMetrics.append("Peak memory: ").append(formatMemorySize((Long) memoryInfo.getOrDefault("peak_memory", 0L))).append("\n");
        detailedMetrics.append("Growth from baseline: ").append(String.format(Locale.US, "%.1f%%", 
                (Number) memoryInfo.getOrDefault("growth_from_baseline_percent", 0))).append("\n\n");
        
        // Network details
        detailedMetrics.append("Network Details:\n");
        detailedMetrics.append("Total bytes received: ").append(formatMemorySize((Long) networkMetrics.getOrDefault("bytes_received", 0L))).append("\n");
        detailedMetrics.append("Error rate: ").append(String.format(Locale.US, "%.1f%%", 
                (Number) networkMetrics.getOrDefault("error_rate_percent", 0))).append("\n");
        detailedMetrics.append("Est. download speed: ").append(String.format(Locale.US, "%.2f Mbps", 
                (Number) networkMetrics.getOrDefault("estimated_download_mbps", 0))).append("\n");
        
        tvDetailedMetrics.setText(detailedMetrics.toString());
    }
    
    @Override
    protected void onDestroy() {
        refreshHandler.removeCallbacks(refreshRunnable);
        super.onDestroy();
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