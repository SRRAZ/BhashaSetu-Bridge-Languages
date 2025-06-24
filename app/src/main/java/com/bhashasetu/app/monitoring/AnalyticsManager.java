package com.bhashasetu.app.monitoring;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bhashasetu.app.monitoring.MemoryMonitor;
import com.bhashasetu.app.monitoring.PerformanceMonitor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * In-app analytics system for tracking usage, performance, and user behavior.
 * Collects and manages analytics data for monitoring application performance and usage.
 */
public class AnalyticsManager {
    private static final String TAG = "AnalyticsManager";
    
    // Singleton instance
    private static AnalyticsManager instance;
    
    // Context
    private final Context context;
    
    // Handler for main thread operations
    private final Handler mainHandler;
    
    // Background thread for I/O operations
    private final ExecutorService executor;
    
    // Cached application information
    private final Map<String, String> appInfo;
    
    // Event storage
    private final List<AnalyticsEvent> eventBuffer = Collections.synchronizedList(new ArrayList<>());
    private static final int MAX_BUFFER_SIZE = 100;
    
    // Session tracking
    private String sessionId;
    private long sessionStartTime;
    
    // User properties
    private final Map<String, Object> userProperties = new HashMap<>();
    
    // Preferences for configuration
    private final SharedPreferences preferences;
    private static final String PREFS_NAME = "analytics_prefs";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_OPT_OUT = "opt_out";
    
    // Storage
    private File eventsDir;
    private static final long MAX_STORAGE_BYTES = 10 * 1024 * 1024; // 10MB
    
    // Integration with performance monitors
    private final PerformanceMonitor performanceMonitor;
    private final MemoryMonitor memoryMonitor;
    
    // Screen tracking
    private String currentScreen = "";
    
    // Constants
    private static final String EVENT_TYPE_SCREEN_VIEW = "screen_view";
    private static final String EVENT_TYPE_USER_ACTION = "user_action";
    private static final String EVENT_TYPE_ERROR = "error";
    private static final String EVENT_TYPE_PERFORMANCE = "performance";
    private static final String EVENT_TYPE_NETWORK = "network";
    
    /**
     * Analytics event representation.
     */
    private static class AnalyticsEvent {
        final String id;
        final String type;
        final String name;
        final Map<String, Object> params;
        final long timestamp;
        final String sessionId;
        
        AnalyticsEvent(String type, String name, Map<String, Object> params, long timestamp, String sessionId) {
            this.id = UUID.randomUUID().toString();
            this.type = type;
            this.name = name;
            this.params = params != null ? new HashMap<>(params) : new HashMap<>();
            this.timestamp = timestamp;
            this.sessionId = sessionId;
        }
        
        /**
         * Convert event to JSON.
         *
         * @return JSON representation
         * @throws JSONException If JSON conversion fails
         */
        JSONObject toJson() throws JSONException {
            JSONObject json = new JSONObject();
            json.put("id", id);
            json.put("type", type);
            json.put("name", name);
            json.put("timestamp", timestamp);
            json.put("session_id", sessionId);
            
            // Convert params to JSON
            JSONObject paramsJson = new JSONObject();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                paramsJson.put(entry.getKey(), entry.getValue());
            }
            json.put("params", paramsJson);
            
            return json;
        }
        
        /**
         * Create event from JSON.
         *
         * @param json JSON representation
         * @return AnalyticsEvent
         * @throws JSONException If JSON parsing fails
         */
        static AnalyticsEvent fromJson(JSONObject json) throws JSONException {
            String type = json.getString("type");
            String name = json.getString("name");
            long timestamp = json.getLong("timestamp");
            String sessionId = json.getString("session_id");
            
            // Parse params
            Map<String, Object> params = new HashMap<>();
            JSONObject paramsJson = json.getJSONObject("params");
            Iterator<String> keys = paramsJson.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                params.put(key, paramsJson.get(key));
            }
            
            return new AnalyticsEvent(type, name, params, timestamp, sessionId);
        }
    }
    
    /**
     * Private constructor.
     *
     * @param context Application context
     */
    private AnalyticsManager(Context context) {
        this.context = context.getApplicationContext();
        this.mainHandler = new Handler(Looper.getMainLooper());
        this.executor = Executors.newSingleThreadExecutor();
        
        // Initialize preferences
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        
        // Initialize app info
        appInfo = collectAppInfo();
        
        // Initialize performance monitors
        performanceMonitor = PerformanceMonitor.getInstance(context);
        memoryMonitor = MemoryMonitor.getInstance(context);
        
        // Initialize storage
        eventsDir = new File(context.getFilesDir(), "analytics_events");
        if (!eventsDir.exists()) {
            eventsDir.mkdirs();
        }
        
        // Start a new session
        startNewSession();
        
        // Load previously stored events
        loadEvents();
        
        // Register for performance updates
        registerPerformanceListener();
        
        // Schedule periodic flush
        schedulePeriodicFlush();
    }
    
    /**
     * Get singleton instance.
     *
     * @param context Context
     * @return AnalyticsManager instance
     */
    public static synchronized AnalyticsManager getInstance(Context context) {
        if (instance == null) {
            instance = new AnalyticsManager(context);
        }
        return instance;
    }
    
    /**
     * Collect application information.
     *
     * @return Map of app info
     */
    private Map<String, String> collectAppInfo() {
        Map<String, String> info = new HashMap<>();
        
        // App version info
        try {
            String packageName = context.getPackageName();
            int versionCode = context.getPackageManager().getPackageInfo(packageName, 0).versionCode;
            String versionName = context.getPackageManager().getPackageInfo(packageName, 0).versionName;
            
            info.put("package_name", packageName);
            info.put("version_code", String.valueOf(versionCode));
            info.put("version_name", versionName);
        } catch (Exception e) {
            Log.e(TAG, "Error getting app version info", e);
        }
        
        // Device info
        info.put("device_manufacturer", Build.MANUFACTURER);
        info.put("device_model", Build.MODEL);
        info.put("os_version", Build.VERSION.RELEASE);
        info.put("api_level", String.valueOf(Build.VERSION.SDK_INT));
        info.put("device_locale", context.getResources().getConfiguration().locale.toString());
        
        return info;
    }
    
    /**
     * Start a new analytics session.
     */
    public void startNewSession() {
        sessionId = UUID.randomUUID().toString();
        sessionStartTime = System.currentTimeMillis();
        
        // Log session start event
        Map<String, Object> params = new HashMap<>();
        params.put("session_id", sessionId);
        logEvent("session_start", params);
        
        Log.d(TAG, "Started new analytics session: " + sessionId);
    }
    
    /**
     * Load persisted events from storage.
     */
    private void loadEvents() {
        executor.submit(() -> {
            try {
                // Clear storage if it exceeds maximum size
                if (getStorageSize() > MAX_STORAGE_BYTES) {
                    clearStorage();
                }
                
                // Load events from each file
                for (File file : eventsDir.listFiles()) {
                    if (file.getName().endsWith(".json")) {
                        loadEventsFromFile(file);
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "Error loading events", e);
            }
        });
    }
    
    /**
     * Calculate size of all storage files.
     *
     * @return Size in bytes
     */
    private long getStorageSize() {
        long size = 0;
        for (File file : eventsDir.listFiles()) {
            size += file.length();
        }
        return size;
    }
    
    /**
     * Clear all stored events.
     */
    private void clearStorage() {
        for (File file : eventsDir.listFiles()) {
            file.delete();
        }
        Log.d(TAG, "Cleared analytics storage");
    }
    
    /**
     * Load events from a JSON file.
     *
     * @param file File to load
     */
    private void loadEventsFromFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
            
            JSONArray eventsArray = new JSONArray(json.toString());
            
            for (int i = 0; i < eventsArray.length(); i++) {
                JSONObject eventJson = eventsArray.getJSONObject(i);
                AnalyticsEvent event = AnalyticsEvent.fromJson(eventJson);
                
                // Add to buffer if there's space
                if (eventBuffer.size() < MAX_BUFFER_SIZE) {
                    eventBuffer.add(event);
                }
            }
            
            // Delete file after successful loading
            file.delete();
            
            Log.d(TAG, "Loaded " + eventsArray.length() + " events from " + file.getName());
        } catch (Exception e) {
            Log.e(TAG, "Error loading events from file: " + file.getName(), e);
        }
    }
    
    /**
     * Register performance monitoring listener.
     */
    private void registerPerformanceListener() {
        performanceMonitor.addListener(new PerformanceMonitor.PerformanceListener() {
            @Override
            public void onPerformanceStats(Map<String, Object> stats) {
                // Log performance data periodically (not every update)
                if (Math.random() < 0.1) { // 10% sample rate
                    logPerformanceEvent("performance_snapshot", stats);
                }
            }
        });
        
        memoryMonitor.addListener(new MemoryMonitor.MemoryListener() {
            @Override
            public void onMemoryWarning(int level, Map<String, Object> memoryInfo) {
                // Always log memory warnings
                logPerformanceEvent("memory_warning", memoryInfo);
            }
            
            @Override
            public void onPotentialLeak(String source, float growthRate) {
                // Always log potential memory leaks
                Map<String, Object> params = new HashMap<>();
                params.put("source", source);
                params.put("growth_rate", growthRate);
                logPerformanceEvent("memory_leak_detected", params);
            }
        });
    }
    
    /**
     * Schedule periodic event flushing.
     */
    private void schedulePeriodicFlush() {
        mainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Flush events if there are any to flush
                if (!eventBuffer.isEmpty()) {
                    flushEvents();
                }
                
                // Reschedule
                mainHandler.postDelayed(this, TimeUnit.MINUTES.toMillis(5)); // Every 5 minutes
            }
        }, TimeUnit.MINUTES.toMillis(5));
    }
    
    /**
     * Log an analytics event.
     *
     * @param eventName Event name
     */
    public void logEvent(String eventName) {
        logEvent(eventName, null);
    }
    
    /**
     * Log an analytics event with parameters.
     *
     * @param eventName Event name
     * @param params Event parameters
     */
    public void logEvent(String eventName, @Nullable Map<String, Object> params) {
        // Don't log if opted out
        if (isOptedOut()) {
            return;
        }
        
        long timestamp = System.currentTimeMillis();
        AnalyticsEvent event = new AnalyticsEvent(EVENT_TYPE_USER_ACTION, eventName, params, timestamp, sessionId);
        
        // Add to buffer
        synchronized (eventBuffer) {
            eventBuffer.add(event);
            
            // Flush if buffer is full
            if (eventBuffer.size() >= MAX_BUFFER_SIZE) {
                flushEvents();
            }
        }
    }
    
    /**
     * Log screen view.
     *
     * @param screenName Screen name
     */
    public void logScreenView(String screenName) {
        // Don't log if opted out
        if (isOptedOut()) {
            return;
        }
        
        // Skip if it's the same screen
        if (screenName.equals(currentScreen)) {
            return;
        }
        
        // Update current screen
        currentScreen = screenName;
        
        // Create screen view event
        Map<String, Object> params = new HashMap<>();
        params.put("screen_name", screenName);
        
        long timestamp = System.currentTimeMillis();
        AnalyticsEvent event = new AnalyticsEvent(EVENT_TYPE_SCREEN_VIEW, "screen_view", params, timestamp, sessionId);
        
        // Add to buffer
        synchronized (eventBuffer) {
            eventBuffer.add(event);
        }
    }
    
    /**
     * Log error event.
     *
     * @param errorName Error name or type
     * @param errorMessage Error message
     * @param stackTrace Stack trace or null
     */
    public void logError(String errorName, String errorMessage, @Nullable String stackTrace) {
        // Don't log if opted out
        if (isOptedOut()) {
            return;
        }
        
        Map<String, Object> params = new HashMap<>();
        params.put("error_name", errorName);
        params.put("error_message", errorMessage);
        
        if (stackTrace != null) {
            params.put("stack_trace", stackTrace);
        }
        
        long timestamp = System.currentTimeMillis();
        AnalyticsEvent event = new AnalyticsEvent(EVENT_TYPE_ERROR, "app_error", params, timestamp, sessionId);
        
        // Add to buffer
        synchronized (eventBuffer) {
            eventBuffer.add(event);
            
            // Always flush error events immediately
            flushEvents();
        }
    }
    
    /**
     * Log performance event.
     *
     * @param eventName Event name
     * @param metrics Performance metrics
     */
    public void logPerformanceEvent(String eventName, Map<String, Object> metrics) {
        // Don't log if opted out
        if (isOptedOut()) {
            return;
        }
        
        long timestamp = System.currentTimeMillis();
        AnalyticsEvent event = new AnalyticsEvent(EVENT_TYPE_PERFORMANCE, eventName, metrics, timestamp, sessionId);
        
        // Add to buffer
        synchronized (eventBuffer) {
            eventBuffer.add(event);
        }
    }
    
    /**
     * Log network event.
     *
     * @param url Request URL
     * @param statusCode HTTP status code
     * @param bytes Bytes transferred
     * @param durationMs Request duration in milliseconds
     */
    public void logNetworkEvent(String url, int statusCode, long bytes, long durationMs) {
        // Don't log if opted out
        if (isOptedOut()) {
            return;
        }
        
        Map<String, Object> params = new HashMap<>();
        params.put("url", url);
        params.put("status_code", statusCode);
        params.put("bytes", bytes);
        params.put("duration_ms", durationMs);
        
        long timestamp = System.currentTimeMillis();
        AnalyticsEvent event = new AnalyticsEvent(EVENT_TYPE_NETWORK, "network_request", params, timestamp, sessionId);
        
        // Add to buffer (only if significant size or duration)
        if (bytes > 50 * 1024 || durationMs > 1000) { // 50KB or 1 second
            synchronized (eventBuffer) {
                eventBuffer.add(event);
            }
        }
    }
    
    /**
     * Set user property.
     *
     * @param key Property key
     * @param value Property value
     */
    public void setUserProperty(String key, Object value) {
        // Don't set if opted out
        if (isOptedOut()) {
            return;
        }
        
        synchronized (userProperties) {
            userProperties.put(key, value);
        }
    }
    
    /**
     * Get user property.
     *
     * @param key Property key
     * @return Property value or null if not set
     */
    @Nullable
    public Object getUserProperty(String key) {
        synchronized (userProperties) {
            return userProperties.get(key);
        }
    }
    
    /**
     * Get or create user ID.
     *
     * @return User ID
     */
    private String getUserId() {
        String userId = preferences.getString(KEY_USER_ID, null);
        if (userId == null) {
            userId = UUID.randomUUID().toString();
            preferences.edit().putString(KEY_USER_ID, userId).apply();
        }
        return userId;
    }
    
    /**
     * Check if user has opted out of analytics.
     *
     * @return true if opted out
     */
    public boolean isOptedOut() {
        return preferences.getBoolean(KEY_OPT_OUT, false);
    }
    
    /**
     * Set opt out status.
     *
     * @param optOut true to opt out, false to opt in
     */
    public void setOptOut(boolean optOut) {
        preferences.edit().putBoolean(KEY_OPT_OUT, optOut).apply();
        
        if (optOut) {
            // Clear existing data
            synchronized (eventBuffer) {
                eventBuffer.clear();
            }
            
            synchronized (userProperties) {
                userProperties.clear();
            }
            
            // Clear storage
            executor.submit(this::clearStorage);
        }
    }
    
    /**
     * Flush events to storage.
     */
    public void flushEvents() {
        // Don't flush if opted out
        if (isOptedOut()) {
            return;
        }
        
        executor.submit(() -> {
            List<AnalyticsEvent> events;
            
            // Create a copy of the event buffer
            synchronized (eventBuffer) {
                if (eventBuffer.isEmpty()) {
                    return;
                }
                
                events = new ArrayList<>(eventBuffer);
                eventBuffer.clear();
            }
            
            // Write events to file
            try {
                saveEventsToFile(events);
            } catch (Exception e) {
                Log.e(TAG, "Error flushing events", e);
                
                // Put events back in buffer
                synchronized (eventBuffer) {
                    eventBuffer.addAll(0, events);
                }
            }
        });
    }
    
    /**
     * Save events to a JSON file.
     *
     * @param events Events to save
     * @throws JSONException If JSON conversion fails
     * @throws IOException If file writing fails
     */
    private void saveEventsToFile(List<AnalyticsEvent> events) throws JSONException, IOException {
        if (events.isEmpty()) {
            return;
        }
        
        // Create a JSON array of events
        JSONArray eventsArray = new JSONArray();
        for (AnalyticsEvent event : events) {
            eventsArray.put(event.toJson());
        }
        
        // Create a new file
        String fileName = "events_" + System.currentTimeMillis() + ".json";
        File file = new File(eventsDir, fileName);
        
        // Write to file
        try (FileOutputStream out = new FileOutputStream(file)) {
            out.write(eventsArray.toString().getBytes());
        }
        
        Log.d(TAG, "Saved " + events.size() + " events to " + fileName);
    }
    
    /**
     * Get all current analytics data.
     *
     * @return Map of analytics data
     */
    public Map<String, Object> getAnalyticsData() {
        Map<String, Object> data = new HashMap<>();
        
        // App info
        data.put("app_info", new HashMap<>(appInfo));
        
        // User info
        data.put("user_id", getUserId());
        
        // User properties
        synchronized (userProperties) {
            data.put("user_properties", new HashMap<>(userProperties));
        }
        
        // Session info
        Map<String, Object> sessionInfo = new HashMap<>();
        sessionInfo.put("session_id", sessionId);
        sessionInfo.put("session_start_time", sessionStartTime);
        sessionInfo.put("session_duration_ms", System.currentTimeMillis() - sessionStartTime);
        data.put("session", sessionInfo);
        
        // Event count
        synchronized (eventBuffer) {
            data.put("pending_events", eventBuffer.size());
        }
        
        // Opt out status
        data.put("opted_out", isOptedOut());
        
        return data;
    }
    
    /**
     * Release resources.
     */
    public void release() {
        // Flush any pending events
        flushEvents();
        
        // Shutdown executor
        executor.shutdown();
        
        // Remove all callbacks
        mainHandler.removeCallbacksAndMessages(null);
    }
}