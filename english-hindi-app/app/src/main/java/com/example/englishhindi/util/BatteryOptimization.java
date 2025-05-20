package com.example.englishhindi.util;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.util.Log;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Utility class for monitoring battery status and optimizing operations
 * based on current battery level and charging state.
 */
public class BatteryOptimization {
    private static final String TAG = "BatteryOptimization";
    
    // Battery thresholds
    private static final int BATTERY_LOW_THRESHOLD = 15;
    private static final int BATTERY_MEDIUM_THRESHOLD = 30;
    
    private static BatteryOptimization instance;
    private final Context context;
    
    // Battery state
    private int batteryLevel = 100;
    private boolean isCharging = false;
    private boolean isPowerSaveMode = false;
    
    /**
     * Get the singleton instance of BatteryOptimization.
     */
    public static synchronized BatteryOptimization getInstance(Context context) {
        if (instance == null) {
            instance = new BatteryOptimization(context.getApplicationContext());
        }
        return instance;
    }
    
    private BatteryOptimization(Context context) {
        this.context = context;
        updateBatteryStatus();
    }
    
    /**
     * Update the current battery status.
     */
    public void updateBatteryStatus() {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);
        
        if (batteryStatus != null) {
            // Get battery level
            int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            
            if (level != -1 && scale != -1) {
                batteryLevel = Math.round((level / (float) scale) * 100);
            }
            
            // Get charging state
            int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || 
                         status == BatteryManager.BATTERY_STATUS_FULL;
            
            // Get power save mode (API 21+)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                isPowerSaveMode = false; // This requires a PowerManager check, simplified for now
            }
            
            Log.d(TAG, "Battery updated: level=" + batteryLevel + "%, charging=" + isCharging + 
                  ", powerSaveMode=" + isPowerSaveMode);
        }
    }
    
    /**
     * Check if the battery is currently low.
     */
    public boolean isBatteryLow() {
        updateBatteryStatus();
        return batteryLevel <= BATTERY_LOW_THRESHOLD;
    }
    
    /**
     * Check if the device is currently charging.
     */
    public boolean isCharging() {
        updateBatteryStatus();
        return isCharging;
    }
    
    /**
     * Check if power save mode is enabled.
     */
    public boolean isPowerSaveMode() {
        updateBatteryStatus();
        return isPowerSaveMode;
    }
    
    /**
     * Get the current battery level (0-100).
     */
    public int getBatteryLevel() {
        updateBatteryStatus();
        return batteryLevel;
    }
    
    /**
     * Determine if background operations should be deferred based on battery status.
     */
    public boolean shouldDeferBackgroundOperation() {
        updateBatteryStatus();
        
        // Defer if battery is critically low and not charging
        if (batteryLevel <= BATTERY_LOW_THRESHOLD && !isCharging) {
            return true;
        }
        
        // Defer if in power save mode and not charging
        if (isPowerSaveMode && !isCharging) {
            return true;
        }
        
        return false;
    }
    
    /**
     * Get the recommended sync interval based on battery status.
     * @return The recommended interval in minutes
     */
    public int getRecommendedSyncInterval() {
        updateBatteryStatus();
        
        if (isCharging) {
            // More frequent syncs when charging
            return 15;
        } else if (batteryLevel <= BATTERY_LOW_THRESHOLD) {
            // Very infrequent syncs when battery is low
            return 120;
        } else if (batteryLevel <= BATTERY_MEDIUM_THRESHOLD || isPowerSaveMode) {
            // Less frequent syncs when battery is medium or power save mode
            return 60;
        } else {
            // Normal sync interval
            return 30;
        }
    }
    
    /**
     * Run a battery-intensive operation with optimization.
     * If the battery is low, the operation will be deferred unless it's critical.
     * @param operation The operation to run
     * @param isCritical Whether the operation is critical and should run regardless of battery state
     * @return True if the operation was run, false if it was deferred
     */
    public boolean runOptimized(Runnable operation, boolean isCritical) {
        if (isCritical || !shouldDeferBackgroundOperation()) {
            // Run the operation
            AppExecutors.getInstance().backgroundWork().execute(operation);
            return true;
        }
        
        // Operation was deferred
        Log.d(TAG, "Operation deferred due to battery optimization");
        return false;
    }
    
    /**
     * Run an operation with throttling based on battery status.
     * @param operationId A unique identifier for this operation type
     * @param operation The operation to run
     * @param minimumIntervalMs Minimum interval between operations in milliseconds
     * @return True if the operation was run, false if it was throttled
     */
    public boolean runWithThrottling(String operationId, Runnable operation, long minimumIntervalMs) {
        final AtomicBoolean wasExecuted = new AtomicBoolean(false);
        
        // Apply battery-based throttling
        if (batteryLevel <= BATTERY_LOW_THRESHOLD && !isCharging) {
            minimumIntervalMs *= 3; // Triple the interval when battery is low
        } else if (batteryLevel <= BATTERY_MEDIUM_THRESHOLD || isPowerSaveMode) {
            minimumIntervalMs *= 2; // Double the interval when battery is medium or power save mode
        }
        
        // Execute with throttling
        ThrottledExecutor.getInstance().execute(operationId, () -> {
            operation.run();
            wasExecuted.set(true);
        }, minimumIntervalMs);
        
        return wasExecuted.get();
    }
    
    /**
     * Get a recommended batch size for operations based on battery status.
     * This helps optimize operations by processing more items at once when conditions allow.
     * @param defaultBatchSize The default batch size
     * @return The recommended batch size
     */
    public int getRecommendedBatchSize(int defaultBatchSize) {
        if (isCharging) {
            // Process more items when charging
            return defaultBatchSize * 2;
        } else if (batteryLevel <= BATTERY_LOW_THRESHOLD) {
            // Process fewer items when battery is low
            return Math.max(1, defaultBatchSize / 3);
        } else if (batteryLevel <= BATTERY_MEDIUM_THRESHOLD || isPowerSaveMode) {
            // Process fewer items when battery is medium or power save mode
            return Math.max(1, defaultBatchSize / 2);
        } else {
            // Use default batch size
            return defaultBatchSize;
        }
    }
    
    /**
     * Utility class for throttling operations.
     */
    private static class ThrottledExecutor {
        private static ThrottledExecutor instance;
        private final java.util.Map<String, Long> lastExecutionTimes = new java.util.HashMap<>();
        
        /**
         * Get the singleton instance of ThrottledExecutor.
         */
        public static synchronized ThrottledExecutor getInstance() {
            if (instance == null) {
                instance = new ThrottledExecutor();
            }
            return instance;
        }
        
        /**
         * Execute an operation with throttling.
         * @param operationId A unique identifier for this operation type
         * @param operation The operation to run
         * @param minimumIntervalMs Minimum interval between operations in milliseconds
         */
        public void execute(String operationId, Runnable operation, long minimumIntervalMs) {
            long currentTime = System.currentTimeMillis();
            Long lastExecutionTime = lastExecutionTimes.get(operationId);
            
            if (lastExecutionTime == null || currentTime - lastExecutionTime >= minimumIntervalMs) {
                // Execute the operation
                AppExecutors.getInstance().backgroundWork().execute(operation);
                lastExecutionTimes.put(operationId, currentTime);
            }
        }
    }
}