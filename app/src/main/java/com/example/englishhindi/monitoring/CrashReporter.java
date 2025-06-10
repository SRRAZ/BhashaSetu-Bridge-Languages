package com.bhashasetu.app.monitoring;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Crash reporting system that captures and logs application crashes and ANRs.
 * Provides tools for analyzing crash patterns and improving app stability.
 */
public class CrashReporter implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "CrashReporter";
    
    // Singleton instance
    private static CrashReporter instance;
    
    // Context
    private final Context context;
    
    // Original uncaught exception handler
    private final Thread.UncaughtExceptionHandler originalHandler;
    
    // Background executor for writing crash reports
    private final ExecutorService executor;
    
    // Directory for crash reports
    private final File crashDir;
    
    // Application state tracking
    private boolean isAppInForeground = false;
    private String currentActivity = "";
    private long appStartTime = 0;
    
    // ANR detection
    private final Handler mainHandler;
    private boolean anrDetectionEnabled = true;
    private Runnable anrDetectionRunnable;
    private static final int ANR_TIMEOUT_MS = 5000; // 5 seconds
    
    /**
     * Private constructor.
     *
     * @param context Application context
     */
    @SuppressLint("StaticFieldLeak") // Using application context, so no leak
    private CrashReporter(Context context) {
        this.context = context.getApplicationContext();
        this.originalHandler = Thread.getDefaultUncaughtExceptionHandler();
        this.executor = Executors.newSingleThreadExecutor();
        this.mainHandler = new Handler(Looper.getMainLooper());
        
        // Create crash directory
        crashDir = new File(context.getFilesDir(), "crash_reports");
        if (!crashDir.exists()) {
            crashDir.mkdirs();
        }
        
        // Record app start time
        appStartTime = System.currentTimeMillis();
        
        // Set up ANR detection
        setupAnrDetection();
        
        // Register activity lifecycle callbacks
        if (context instanceof Application) {
            registerActivityCallbacks((Application) context);
        }
    }
    
    /**
     * Get singleton instance.
     *
     * @param context Context
     * @return CrashReporter instance
     */
    public static synchronized CrashReporter getInstance(Context context) {
        if (instance == null) {
            instance = new CrashReporter(context);
        }
        return instance;
    }
    
    /**
     * Set up ANR detection.
     */
    private void setupAnrDetection() {
        anrDetectionRunnable = new Runnable() {
            @Override
            public void run() {
                // This runnable should execute regularly on the main thread
                // If it doesn't, we may be experiencing an ANR
                
                if (anrDetectionEnabled) {
                    // Schedule next check
                    mainHandler.postDelayed(this, 1000); // Every second
                }
            }
        };
        
        // Start ANR detection
        mainHandler.post(anrDetectionRunnable);
        
        // Set up background thread to monitor main thread
        executor.submit(new Runnable() {
            @Override
            public void run() {
                while (anrDetectionEnabled) {
                    try {
                        // Create a flag to track response
                        final boolean[] responded = {false};
                        
                        // Post a task to main thread
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                responded[0] = true;
                            }
                        });
                        
                        // Wait for response
                        Thread.sleep(ANR_TIMEOUT_MS);
                        
                        // Check if main thread responded
                        if (!responded[0] && isAppInForeground) {
                            // Potential ANR detected
                            reportAnr();
                        }
                        
                        // Wait before next check
                        Thread.sleep(5000); // Check every 5 seconds
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    } catch (Exception e) {
                        Log.e(TAG, "Error in ANR detection", e);
                    }
                }
            }
        });
    }
    
    /**
     * Register activity lifecycle callbacks.
     *
     * @param application Application
     */
    private void registerActivityCallbacks(Application application) {
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            private int activityCount = 0;
            
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                currentActivity = activity.getClass().getName();
            }
            
            @Override
            public void onActivityStarted(Activity activity) {
                activityCount++;
                if (activityCount == 1) {
                    isAppInForeground = true;
                }
            }
            
            @Override
            public void onActivityResumed(Activity activity) {
                currentActivity = activity.getClass().getName();
            }
            
            @Override
            public void onActivityPaused(Activity activity) {
                // Not used
            }
            
            @Override
            public void onActivityStopped(Activity activity) {
                activityCount--;
                if (activityCount == 0) {
                    isAppInForeground = false;
                }
            }
            
            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                // Not used
            }
            
            @Override
            public void onActivityDestroyed(Activity activity) {
                // Not used
            }
        });
    }
    
    /**
     * Install crash reporter as the default uncaught exception handler.
     */
    public void install() {
        Thread.setDefaultUncaughtExceptionHandler(this);
        Log.i(TAG, "Crash reporter installed");
    }
    
    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        // Report the crash
        reportCrash(throwable);
        
        // Forward to original handler
        if (originalHandler != null) {
            originalHandler.uncaughtException(thread, throwable);
        } else {
            // If no original handler, terminate the process
            Process.killProcess(Process.myPid());
            System.exit(10);
        }
    }
    
    /**
     * Report an application not responding (ANR).
     */
    private void reportAnr() {
        String anrMessage = "Application Not Responding (ANR) detected";
        Exception anrException = new Exception(anrMessage);
        
        // Create stack trace for all threads
        Map<String, String> threadDumps = getThreadDumps();
        
        // Create metadata for crash report
        Map<String, Object> metadata = createCrashMetadata("ANR", anrException);
        metadata.put("thread_dumps", threadDumps);
        
        // Write crash report
        writeCrashReport("anr", metadata, anrException);
        
        Log.e(TAG, "ANR detected and reported");
    }
    
    /**
     * Report a crash.
     *
     * @param throwable Throwable causing the crash
     */
    private void reportCrash(Throwable throwable) {
        // Create metadata for crash report
        Map<String, Object> metadata = createCrashMetadata("CRASH", throwable);
        
        // Create stack trace for all threads
        Map<String, String> threadDumps = getThreadDumps();
        metadata.put("thread_dumps", threadDumps);
        
        // Write crash report
        writeCrashReport("crash", metadata, throwable);
    }
    
    /**
     * Manually report a non-fatal error.
     *
     * @param error Error to report
     */
    public void reportError(Throwable error) {
        // Create metadata for error report
        Map<String, Object> metadata = createCrashMetadata("ERROR", error);
        
        // Write error report
        writeCrashReport("error", metadata, error);
    }
    
    /**
     * Create metadata for crash report.
     *
     * @param type Crash type
     * @param throwable Throwable
     * @return Map of metadata
     */
    private Map<String, Object> createCrashMetadata(String type, Throwable throwable) {
        Map<String, Object> metadata = new HashMap<>();
        
        // Basic info
        metadata.put("type", type);
        metadata.put("timestamp", System.currentTimeMillis());
        metadata.put("formatted_timestamp", formatTimestamp(System.currentTimeMillis()));
        
        // App state
        metadata.put("app_in_foreground", isAppInForeground);
        metadata.put("current_activity", currentActivity);
        metadata.put("app_uptime_ms", System.currentTimeMillis() - appStartTime);
        
        // Error details
        metadata.put("exception_class", throwable.getClass().getName());
        metadata.put("exception_message", throwable.getMessage());
        metadata.put("exception_stack_trace", getStackTraceString(throwable));
        
        // Basic device info
        metadata.put("os_version", Build.VERSION.RELEASE);
        metadata.put("api_level", Build.VERSION.SDK_INT);
        metadata.put("device_manufacturer", Build.MANUFACTURER);
        metadata.put("device_model", Build.MODEL);
        
        // App info
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            
            metadata.put("app_version_name", packageInfo.versionName);
            metadata.put("app_version_code", packageInfo.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Error getting package info", e);
        }
        
        return metadata;
    }
    
    /**
     * Get stack trace as a string.
     *
     * @param throwable Throwable
     * @return Stack trace string
     */
    private String getStackTraceString(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);
        return stringWriter.toString();
    }
    
    /**
     * Format timestamp as ISO 8601 string.
     *
     * @param timestamp Timestamp in milliseconds
     * @return Formatted timestamp
     */
    private String formatTimestamp(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(new Date(timestamp));
    }
    
    /**
     * Get dumps of all threads.
     *
     * @return Map of thread names to stack traces
     */
    private Map<String, String> getThreadDumps() {
        Map<String, String> dumps = new HashMap<>();
        
        Map<Thread, StackTraceElement[]> stackTraces = Thread.getAllStackTraces();
        for (Map.Entry<Thread, StackTraceElement[]> entry : stackTraces.entrySet()) {
            Thread thread = entry.getKey();
            StackTraceElement[] stackTrace = entry.getValue();
            
            // Build thread info string
            StringBuilder sb = new StringBuilder();
            sb.append("\"").append(thread.getName()).append("\" ");
            sb.append("id=").append(thread.getId()).append(" ");
            sb.append("state=").append(thread.getState()).append("\n");
            
            // Add stack trace
            for (StackTraceElement element : stackTrace) {
                sb.append("\tat ").append(element.toString()).append("\n");
            }
            
            dumps.put(thread.getName(), sb.toString());
        }
        
        return dumps;
    }
    
    /**
     * Write crash report to file.
     *
     * @param type Crash type
     * @param metadata Crash metadata
     * @param throwable Throwable
     */
    private void writeCrashReport(String type, Map<String, Object> metadata, Throwable throwable) {
        executor.submit(() -> {
            try {
                // Create JSON object from metadata
                JSONObject json = new JSONObject();
                for (Map.Entry<String, Object> entry : metadata.entrySet()) {
                    try {
                        if (entry.getValue() instanceof Map) {
                            // Convert nested map to JSON object
                            Map<?, ?> nestedMap = (Map<?, ?>) entry.getValue();
                            JSONObject nestedJson = new JSONObject();
                            
                            for (Map.Entry<?, ?> nestedEntry : nestedMap.entrySet()) {
                                if (nestedEntry.getKey() instanceof String) {
                                    nestedJson.put((String) nestedEntry.getKey(), nestedEntry.getValue());
                                }
                            }
                            
                            json.put(entry.getKey(), nestedJson);
                        } else {
                            json.put(entry.getKey(), entry.getValue());
                        }
                    } catch (JSONException e) {
                        Log.e(TAG, "Error adding field to JSON: " + entry.getKey(), e);
                    }
                }
                
                // Create filename with timestamp
                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
                String errorType = throwable.getClass().getSimpleName();
                if (TextUtils.isEmpty(errorType)) {
                    errorType = "Unknown";
                }
                
                String filename = type + "_" + timestamp + "_" + errorType + ".json";
                File reportFile = new File(crashDir, filename);
                
                // Write JSON to file
                try (FileOutputStream fos = new FileOutputStream(reportFile)) {
                    fos.write(json.toString(2).getBytes());
                }
                
                Log.i(TAG, "Crash report written to " + reportFile.getAbsolutePath());
                
                // Notify AnalyticsManager about the crash
                try {
                    com.bhashasetu.app.monitoring.AnalyticsManager.getInstance(context).logError(
                            throwable.getClass().getName(),
                            throwable.getMessage(),
                            getStackTraceString(throwable)
                    );
                } catch (Exception e) {
                    Log.e(TAG, "Error notifying AnalyticsManager", e);
                }
            } catch (Exception e) {
                Log.e(TAG, "Error writing crash report", e);
            }
        });
    }
    
    /**
     * Enable or disable ANR detection.
     *
     * @param enabled true to enable, false to disable
     */
    public void setAnrDetectionEnabled(boolean enabled) {
        this.anrDetectionEnabled = enabled;
        
        if (enabled && !mainHandler.hasCallbacks(anrDetectionRunnable)) {
            mainHandler.post(anrDetectionRunnable);
        } else if (!enabled) {
            mainHandler.removeCallbacks(anrDetectionRunnable);
        }
    }
    
    /**
     * Get all crash reports.
     *
     * @return Array of crash report files
     */
    public File[] getCrashReports() {
        return crashDir.listFiles();
    }
    
    /**
     * Clear all crash reports.
     */
    public void clearCrashReports() {
        executor.submit(() -> {
            File[] files = crashDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
            Log.i(TAG, "Crash reports cleared");
        });
    }
    
    /**
     * Uninstall crash reporter.
     */
    public void uninstall() {
        // Restore original handler
        Thread.setDefaultUncaughtExceptionHandler(originalHandler);
        
        // Disable ANR detection
        setAnrDetectionEnabled(false);
        
        // Shutdown executor
        executor.shutdown();
        
        Log.i(TAG, "Crash reporter uninstalled");
    }
    
    /**
     * Test crash reporter with a simulated crash.
     * THIS SHOULD ONLY BE USED IN DEBUG BUILDS!
     */
    public void testCrashReporter() {
        throw new RuntimeException("Test crash");
    }
    
    /**
     * Test ANR detection with a simulated ANR.
     * THIS SHOULD ONLY BE USED IN DEBUG BUILDS!
     */
    public void testAnrDetection() {
        try {
            // Block main thread to simulate ANR
            Thread.sleep(ANR_TIMEOUT_MS * 2);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}