package com.example.englishhindi.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Debug;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.collection.LruCache;
import androidx.fragment.app.Fragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Memory optimization utility that helps manage application memory usage efficiently.
 * Provides tools for tracking memory usage, detecting leaks, and optimizing resource usage.
 */
public class MemoryOptimizer {
    private static final String TAG = "MemoryOptimizer";
    
    // Singleton instance
    private static MemoryOptimizer instance;
    
    // Memory tracking
    private final Map<String, Long> memoryUsageMap = new HashMap<>();
    private long baselineMemoryUsage = 0;
    
    // Leak detection
    private final WeakHashMap<Object, LeakTracker> leakTrackers = new WeakHashMap<>();
    
    // Memory thresholds
    private final List<MemoryThresholdListener> memoryThresholdListeners = new ArrayList<>();
    private int currentMemoryLevel = ActivityManager.TRIM_MEMORY_RUNNING_MODERATE;
    
    // Context reference
    private WeakReference<Context> contextReference;
    
    /**
     * Memory threshold listener interface.
     */
    public interface MemoryThresholdListener {
        void onMemoryThresholdReached(int level);
    }
    
    /**
     * Leak tracker to help identify potential memory leaks.
     */
    private static class LeakTracker {
        final String objectType;
        final long creationTime;
        final Exception stackTrace;
        
        LeakTracker(Object obj) {
            this.objectType = obj.getClass().getName();
            this.creationTime = System.currentTimeMillis();
            this.stackTrace = new Exception("Potential leak stack trace");
        }
    }
    
    /**
     * Private constructor for singleton pattern.
     *
     * @param context Application context
     */
    private MemoryOptimizer(Context context) {
        this.contextReference = new WeakReference<>(context.getApplicationContext());
        
        // Record baseline memory usage
        recordBaselineMemoryUsage();
    }
    
    /**
     * Get singleton instance.
     *
     * @param context Context
     * @return MemoryOptimizer instance
     */
    public static MemoryOptimizer getInstance(Context context) {
        if (instance == null) {
            synchronized (MemoryOptimizer.class) {
                if (instance == null) {
                    instance = new MemoryOptimizer(context);
                }
            }
        }
        return instance;
    }
    
    /**
     * Record baseline memory usage at startup.
     */
    private void recordBaselineMemoryUsage() {
        baselineMemoryUsage = getCurrentMemoryUsage();
        Log.d(TAG, "Baseline memory usage: " + formatMemorySize(baselineMemoryUsage));
    }
    
    /**
     * Get current memory usage in bytes.
     *
     * @return Memory usage in bytes
     */
    public long getCurrentMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }
    
    /**
     * Record memory usage for a specific component.
     *
     * @param tag              Tag to identify the component
     * @param startMemoryUsage Starting memory usage in bytes
     */
    public void recordMemoryUsage(String tag, long startMemoryUsage) {
        long currentUsage = getCurrentMemoryUsage();
        long difference = currentUsage - startMemoryUsage;
        
        memoryUsageMap.put(tag, difference);
        
        Log.d(TAG, "Memory usage for " + tag + ": " + 
               formatMemorySize(difference));
    }
    
    /**
     * Record memory usage before an operation.
     *
     * @param tag Tag to identify the operation
     * @return Current memory usage in bytes
     */
    public long recordMemoryBefore(String tag) {
        return getCurrentMemoryUsage();
    }
    
    /**
     * Record memory usage after an operation and log the difference.
     *
     * @param tag               Tag to identify the operation
     * @param startMemoryUsage  Memory usage before the operation
     * @return Memory difference in bytes
     */
    public long recordMemoryAfter(String tag, long startMemoryUsage) {
        long currentUsage = getCurrentMemoryUsage();
        long difference = currentUsage - startMemoryUsage;
        
        memoryUsageMap.put(tag, difference);
        
        Log.d(TAG, "Memory usage for " + tag + ": " + 
               formatMemorySize(difference));
        
        return difference;
    }
    
    /**
     * Format memory size to human-readable format.
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
     * Get memory usage summary for recorded components.
     *
     * @return Map of component tags to memory usage
     */
    public Map<String, String> getMemoryUsageSummary() {
        Map<String, String> summary = new HashMap<>();
        
        // Add total usage
        long currentUsage = getCurrentMemoryUsage();
        summary.put("Total Memory Usage", formatMemorySize(currentUsage));
        summary.put("Baseline Memory Usage", formatMemorySize(baselineMemoryUsage));
        summary.put("Increase Since Baseline", formatMemorySize(currentUsage - baselineMemoryUsage));
        
        // Add per-component usage
        for (Map.Entry<String, Long> entry : memoryUsageMap.entrySet()) {
            summary.put(entry.getKey(), formatMemorySize(entry.getValue()));
        }
        
        return summary;
    }
    
    /**
     * Register a memory threshold listener.
     *
     * @param listener Listener to register
     */
    public void registerMemoryThresholdListener(MemoryThresholdListener listener) {
        if (listener != null && !memoryThresholdListeners.contains(listener)) {
            memoryThresholdListeners.add(listener);
        }
    }
    
    /**
     * Unregister a memory threshold listener.
     *
     * @param listener Listener to unregister
     */
    public void unregisterMemoryThresholdListener(MemoryThresholdListener listener) {
        memoryThresholdListeners.remove(listener);
    }
    
    /**
     * Notify memory threshold reached.
     *
     * @param level Memory level
     */
    public void notifyMemoryThresholdReached(int level) {
        // Only notify if level has changed to a worse state
        if (level > currentMemoryLevel) {
            currentMemoryLevel = level;
            
            for (MemoryThresholdListener listener : memoryThresholdListeners) {
                listener.onMemoryThresholdReached(level);
            }
        }
    }
    
    /**
     * Handle memory trim notification from the system.
     *
     * @param level Memory trim level
     */
    public void handleTrimMemory(int level) {
        // Update current memory level
        currentMemoryLevel = level;
        
        // Log memory trim event
        Log.d(TAG, "onTrimMemory called with level: " + level);
        
        // Implement graduated response to memory pressure
        if (level >= ActivityManager.TRIM_MEMORY_RUNNING_CRITICAL) {
            // Critical memory situation
            clearAllCaches();
            clearNonEssentialResources();
            
            // Notify listeners
            notifyMemoryThresholdReached(level);
        } else if (level >= ActivityManager.TRIM_MEMORY_RUNNING_LOW) {
            // Low memory situation
            clearNonEssentialResources();
            
            // Notify listeners
            notifyMemoryThresholdReached(level);
        } else if (level >= ActivityManager.TRIM_MEMORY_RUNNING_MODERATE) {
            // Moderate memory situation
            
            // Notify listeners
            notifyMemoryThresholdReached(level);
        }
    }
    
    /**
     * Clear all memory caches.
     */
    private void clearAllCaches() {
        // Clear cached objects
        MemoryCache.getInstance().clearAll();
        
        // Suggest garbage collection
        System.gc();
    }
    
    /**
     * Clear non-essential resources to free memory.
     */
    private void clearNonEssentialResources() {
        // Clear non-essential cached objects
        MemoryCache.getInstance().clearNonEssential();
    }
    
    /**
     * Register an object for leak tracking.
     *
     * @param obj Object to track
     */
    public void registerForLeakTracking(Object obj) {
        if (obj != null) {
            leakTrackers.put(obj, new LeakTracker(obj));
        }
    }
    
    /**
     * Unregister an object from leak tracking.
     *
     * @param obj Object to unregister
     */
    public void unregisterFromLeakTracking(Object obj) {
        if (obj != null) {
            leakTrackers.remove(obj);
        }
    }
    
    /**
     * Check for potential memory leaks.
     */
    public void checkForLeaks() {
        // Force garbage collection to help identify leaks
        // This is only for debugging purposes and should not be used in production
        // System.gc() is generally discouraged in production code
        System.gc();
        System.runFinalization();
        
        // Wait a bit for GC to complete
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Check for potential leaks
        Iterator<Map.Entry<Object, LeakTracker>> iterator = leakTrackers.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Object, LeakTracker> entry = iterator.next();
            LeakTracker tracker = entry.getValue();
            
            // Check age of object
            long age = System.currentTimeMillis() - tracker.creationTime;
            if (age > 5 * 60 * 1000) { // 5 minutes
                // This object has been alive for a long time and might be leaked
                Log.w(TAG, "Potential memory leak detected: " + tracker.objectType + 
                        " has been alive for " + (age / 1000) + " seconds");
                Log.w(TAG, "Creation stack trace:", tracker.stackTrace);
            }
        }
    }
    
    /**
     * Calculate the optimal bitmap sample size for loading.
     *
     * @param options   BitmapFactory options
     * @param reqWidth  Required width
     * @param reqHeight Required height
     * @return Sample size
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, 
                                          int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight && 
                   (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        
        return inSampleSize;
    }
    
    /**
     * Load a bitmap efficiently with memory considerations.
     *
     * @param context   Context
     * @param resId     Resource ID
     * @param reqWidth  Required width
     * @param reqHeight Required height
     * @return Decoded bitmap or null if failed
     */
    public static Bitmap decodeSampledBitmap(Context context, int resId, 
                                           int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), resId, options);
        
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        
        // Use appropriate bitmap config based on device memory class
        if (isLowMemoryDevice(context)) {
            options.inPreferredConfig = Bitmap.Config.RGB_565; // Uses less memory
        } else {
            options.inPreferredConfig = Bitmap.Config.ARGB_8888; // Higher quality
        }
        
        return BitmapFactory.decodeResource(context.getResources(), resId, options);
    }
    
    /**
     * Check if device is a low-memory device.
     *
     * @param context Context
     * @return true if device has low memory
     */
    public static boolean isLowMemoryDevice(Context context) {
        ActivityManager activityManager = 
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return activityManager.isLowRamDevice();
        } else {
            // For older devices, check memory class
            return activityManager.getMemoryClass() <= 64;
        }
    }
    
    /**
     * Get optimal cache size based on device memory.
     *
     * @param context Context
     * @param percentage Percentage of available memory to use (1-100)
     * @return Cache size in bytes
     */
    public static int getOptimalCacheSize(Context context, int percentage) {
        ActivityManager activityManager = 
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        
        int memoryClass = activityManager.getMemoryClass();
        
        // Use large heap if available and enabled
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if ((context.getApplicationInfo().flags & 
                 android.content.pm.ApplicationInfo.FLAG_LARGE_HEAP) != 0) {
                memoryClass = activityManager.getLargeMemoryClass();
            }
        }
        
        // Calculate cache size based on percentage of available memory
        // Ensure percentage is within valid range
        int validPercentage = Math.max(1, Math.min(100, percentage));
        
        return (memoryClass * 1024 * 1024) * validPercentage / 100;
    }
    
    /**
     * Get device memory information.
     *
     * @param context Context
     * @return Map of memory information
     */
    public static Map<String, String> getDeviceMemoryInfo(Context context) {
        Map<String, String> memoryInfo = new HashMap<>();
        
        Runtime runtime = Runtime.getRuntime();
        ActivityManager activityManager = 
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        
        // Get memory class
        int memoryClass = activityManager.getMemoryClass();
        memoryInfo.put("Memory Class", memoryClass + " MB");
        
        // Get large memory class if available
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            int largeMemoryClass = activityManager.getLargeMemoryClass();
            memoryInfo.put("Large Memory Class", largeMemoryClass + " MB");
        }
        
        // Check if low RAM device
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            boolean isLowRamDevice = activityManager.isLowRamDevice();
            memoryInfo.put("Low RAM Device", String.valueOf(isLowRamDevice));
        }
        
        // Get runtime memory info
        memoryInfo.put("Max Memory", formatMemorySize(runtime.maxMemory()));
        memoryInfo.put("Total Memory", formatMemorySize(runtime.totalMemory()));
        memoryInfo.put("Free Memory", formatMemorySize(runtime.freeMemory()));
        memoryInfo.put("Used Memory", 
                formatMemorySize(runtime.totalMemory() - runtime.freeMemory()));
        
        // Get native heap info
        Debug.MemoryInfo memInfo = new Debug.MemoryInfo();
        Debug.getMemoryInfo(memInfo);
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            memoryInfo.put("Java Heap", formatMemorySize(memInfo.getMemoryStat("summary.java-heap")));
            memoryInfo.put("Native Heap", formatMemorySize(memInfo.getMemoryStat("summary.native-heap")));
            memoryInfo.put("Graphics", formatMemorySize(memInfo.getMemoryStat("summary.graphics")));
            memoryInfo.put("Stack", formatMemorySize(memInfo.getMemoryStat("summary.stack")));
        } else {
            memoryInfo.put("PSS Total", formatMemorySize(memInfo.getTotalPss() * 1024L));
            memoryInfo.put("Private Dirty", formatMemorySize(memInfo.getTotalPrivateDirty() * 1024L));
            memoryInfo.put("Private Clean", formatMemorySize(memInfo.getTotalPrivateClean() * 1024L));
        }
        
        return memoryInfo;
    }
    
    /**
     * Optimize memory for an activity.
     *
     * @param activity Activity to optimize
     */
    public void optimizeActivity(Activity activity) {
        if (activity == null) {
            return;
        }
        
        // Register for leak tracking
        registerForLeakTracking(activity);
        
        // Set optimal bitmap density based on screen size
        optimizeBitmapDensity(activity);
    }
    
    /**
     * Optimize bitmap density for current device.
     *
     * @param context Context
     */
    private void optimizeBitmapDensity(Context context) {
        if (context == null) {
            return;
        }
        
        // Check if this is a tablet or large screen device
        boolean isTablet = (context.getResources().getConfiguration().screenLayout & 
                           Configuration.SCREENLAYOUT_SIZE_MASK) >= 
                           Configuration.SCREENLAYOUT_SIZE_LARGE;
        
        // Get display metrics
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        
        // For lower memory devices or very high-density screens, use lower bitmap density
        if (isLowMemoryDevice(context) || metrics.densityDpi >= DisplayMetrics.DENSITY_XXHIGH) {
            // Reduces memory usage for bitmaps at the cost of some visual quality
            BitmapFactory.Options.class.cast(new BitmapFactory.Options()).inPreferredConfig = 
                    Bitmap.Config.RGB_565;
        }
    }
    
    /**
     * Clean up resources when a fragment is destroyed.
     *
     * @param fragment Fragment being destroyed
     */
    public void cleanupFragment(Fragment fragment) {
        if (fragment == null) {
            return;
        }
        
        // Unregister from leak tracking
        unregisterFromLeakTracking(fragment);
        
        // Clean up view references
        cleanupView(fragment.getView());
    }
    
    /**
     * Clean up view references to prevent leaks.
     *
     * @param view View to clean up
     */
    public void cleanupView(View view) {
        if (view == null) {
            return;
        }
        
        // Clear view's tag
        view.setTag(null);
        
        // Clean up view group
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            
            // Clean up all child views
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                cleanupView(viewGroup.getChildAt(i));
            }
            
            // Clear view group's adapter if applicable
            if (view instanceof android.widget.AdapterView) {
                ((android.widget.AdapterView<?>) view).setAdapter(null);
            } else if (view instanceof androidx.recyclerview.widget.RecyclerView) {
                ((androidx.recyclerview.widget.RecyclerView) view).setAdapter(null);
            }
        }
        
        // Clear background
        view.setBackground(null);
    }
    
    /**
     * Get the current memory status.
     *
     * @param context Context
     * @return Memory status information
     */
    public String getMemoryStatus(@NonNull Context context) {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = 
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(memoryInfo);
        
        Runtime runtime = Runtime.getRuntime();
        
        return "Available memory: " + formatMemorySize(memoryInfo.availMem) +
               "\nTotal memory: " + formatMemorySize(memoryInfo.totalMem) +
               "\nLow memory: " + memoryInfo.lowMemory +
               "\nThreshold: " + formatMemorySize(memoryInfo.threshold) +
               "\nRuntime max: " + formatMemorySize(runtime.maxMemory()) +
               "\nRuntime total: " + formatMemorySize(runtime.totalMemory()) +
               "\nRuntime free: " + formatMemorySize(runtime.freeMemory());
    }
    
    /**
     * Generic memory cache implementation.
     */
    public static class MemoryCache {
        private static MemoryCache instance;
        
        // LRU Cache for different types
        private final LruCache<String, Object> objectCache;
        private final LruCache<String, Bitmap> bitmapCache;
        
        // Priority tracking (higher number = higher priority)
        private final Map<String, Integer> objectPriorities = new HashMap<>();
        
        /**
         * Private constructor.
         */
        private MemoryCache() {
            // Use 1/8 of available memory for object cache
            int objectCacheSize = (int) (Runtime.getRuntime().maxMemory() / 8);
            objectCache = new LruCache<>(objectCacheSize);
            
            // Use 1/4 of available memory for bitmap cache
            int bitmapCacheSize = (int) (Runtime.getRuntime().maxMemory() / 4);
            bitmapCache = new LruCache<String, Bitmap>(bitmapCacheSize) {
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    // The real size of the bitmap in bytes
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        return bitmap.getAllocationByteCount();
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
                        return bitmap.getByteCount();
                    } else {
                        return bitmap.getRowBytes() * bitmap.getHeight();
                    }
                }
            };
        }
        
        /**
         * Get singleton instance.
         *
         * @return MemoryCache instance
         */
        public static MemoryCache getInstance() {
            if (instance == null) {
                synchronized (MemoryCache.class) {
                    if (instance == null) {
                        instance = new MemoryCache();
                    }
                }
            }
            return instance;
        }
        
        /**
         * Put object in cache with priority.
         *
         * @param key      Cache key
         * @param object   Object to cache
         * @param priority Priority (0-100, higher = higher priority)
         */
        public void putObject(String key, Object object, int priority) {
            objectCache.put(key, object);
            objectPriorities.put(key, priority);
        }
        
        /**
         * Get object from cache.
         *
         * @param key Cache key
         * @return Cached object or null if not found
         */
        public Object getObject(String key) {
            return objectCache.get(key);
        }
        
        /**
         * Put bitmap in cache.
         *
         * @param key    Cache key
         * @param bitmap Bitmap to cache
         */
        public void putBitmap(String key, Bitmap bitmap) {
            bitmapCache.put(key, bitmap);
        }
        
        /**
         * Get bitmap from cache.
         *
         * @param key Cache key
         * @return Cached bitmap or null if not found
         */
        public Bitmap getBitmap(String key) {
            return bitmapCache.get(key);
        }
        
        /**
         * Clear all caches.
         */
        public void clearAll() {
            objectCache.evictAll();
            bitmapCache.evictAll();
            objectPriorities.clear();
        }
        
        /**
         * Clear non-essential cached items.
         */
        public void clearNonEssential() {
            // Clear low-priority objects (below 50)
            List<String> keysToRemove = new ArrayList<>();
            
            for (Map.Entry<String, Integer> entry : objectPriorities.entrySet()) {
                if (entry.getValue() < 50) {
                    keysToRemove.add(entry.getKey());
                }
            }
            
            for (String key : keysToRemove) {
                objectCache.remove(key);
                objectPriorities.remove(key);
            }
            
            // Clear some of the bitmap cache (trim to 50%)
            int maxSize = bitmapCache.maxSize();
            bitmapCache.resize(maxSize / 2);
        }
    }
}