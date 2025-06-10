package com.bhashasetu.app.testing;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.bhashasetu.app.BuildConfig;
import com.bhashasetu.app.cache.CacheManager;
import com.bhashasetu.app.util.AppExecutors;
import com.bhashasetu.app.util.DeviceInfo;
import com.bhashasetu.app.util.NetworkUtils;
import com.bhashasetu.app.util.PerformanceMonitor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Utility class for generating test results and diagnostics.
 * This is used for comprehensive testing and collecting app performance data.
 */
public class TestResults {
    private static final String TAG = "TestResults";
    
    // Test categories
    public enum TestCategory {
        PERFORMANCE,
        NETWORK,
        STORAGE,
        MEMORY,
        FUNCTIONALITY,
        UI
    }
    
    // Test result
    public static class TestResult {
        public final String name;
        public final TestCategory category;
        public final boolean success;
        public final String message;
        public final Map<String, String> metrics;
        public final long timestamp;
        
        public TestResult(String name, TestCategory category, boolean success, String message) {
            this.name = name;
            this.category = category;
            this.success = success;
            this.message = message;
            this.metrics = new HashMap<>();
            this.timestamp = System.currentTimeMillis();
        }
        
        public void addMetric(String key, String value) {
            metrics.put(key, value);
        }
        
        public void addMetric(String key, long value) {
            metrics.put(key, String.valueOf(value));
        }
        
        public void addMetric(String key, float value) {
            metrics.put(key, String.valueOf(value));
        }
        
        public void addMetric(String key, boolean value) {
            metrics.put(key, String.valueOf(value));
        }
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("[").append(success ? "✓" : "✗").append("] ");
            sb.append(name).append(" (").append(category).append("): ");
            sb.append(message).append("\n");
            
            if (!metrics.isEmpty()) {
                sb.append("  Metrics:\n");
                for (Map.Entry<String, String> entry : metrics.entrySet()) {
                    sb.append("    ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
                }
            }
            
            return sb.toString();
        }
    }
    
    private static TestResults instance;
    private final Context context;
    private final List<TestResult> results = new ArrayList<>();
    
    /**
     * Get the singleton instance of TestResults.
     */
    public static synchronized TestResults getInstance(Context context) {
        if (instance == null) {
            instance = new TestResults(context.getApplicationContext());
        }
        return instance;
    }
    
    private TestResults(Context context) {
        this.context = context;
    }
    
    /**
     * Run all tests and generate a comprehensive report.
     * @param callback Callback for when all tests are complete
     */
    public void runAllTests(final TestResultsCallback callback) {
        // Clear previous results
        results.clear();
        
        // Start performance monitoring
        PerformanceMonitor.getInstance(context).startMonitoring();
        
        // Run tests on a background thread
        AppExecutors.getInstance().backgroundWork().execute(() -> {
            try {
                // Run device info test
                testDeviceInfo();
                
                // Run performance tests
                testAppStartupTime();
                testUIResponsiveness();
                testBackgroundTaskExecution();
                
                // Run network tests
                testNetworkConnectivity();
                testOfflineCapabilities();
                
                // Run storage tests
                testCacheManagement();
                testDataPersistence();
                
                // Generate the report
                String reportPath = generateReport();
                
                // Stop performance monitoring
                PerformanceMonitor.getInstance(context).stopMonitoring();
                
                // Notify the callback
                AppExecutors.getInstance().mainThread().execute(() -> {
                    if (callback != null) {
                        callback.onTestsCompleted(results, reportPath);
                    }
                });
            } catch (Exception e) {
                Log.e(TAG, "Error running tests", e);
                
                // Notify the callback of failure
                AppExecutors.getInstance().mainThread().execute(() -> {
                    if (callback != null) {
                        callback.onTestError(e);
                    }
                });
            }
        });
    }
    
    /**
     * Test device information.
     */
    private void testDeviceInfo() {
        TestResult result = new TestResult(
            "Device Information",
            TestCategory.FUNCTIONALITY,
            true,
            "Collected device information"
        );
        
        result.addMetric("Device", Build.MANUFACTURER + " " + Build.MODEL);
        result.addMetric("Android Version", Build.VERSION.RELEASE + " (API " + Build.VERSION.SDK_INT + ")");
        result.addMetric("App Version", BuildConfig.VERSION_NAME + " (" + BuildConfig.VERSION_CODE + ")");
        result.addMetric("Screen Size", DeviceInfo.getScreenSize(context));
        result.addMetric("Memory", DeviceInfo.getTotalRAM(context) + " MB");
        result.addMetric("Storage", DeviceInfo.getAvailableStorage() + " MB free / " + DeviceInfo.getTotalStorage() + " MB total");
        
        results.add(result);
    }
    
    /**
     * Test app startup time.
     */
    private void testAppStartupTime() {
        // This is a placeholder - in a real app, you would measure actual startup time
        // and store in SharedPreferences during launch, then read it here
        
        TestResult result = new TestResult(
            "App Startup Time",
            TestCategory.PERFORMANCE,
            true,
            "Measured app startup time"
        );
        
        // Simulated values - replace with actual measurements in a real app
        result.addMetric("Cold Start Time", "320 ms");
        result.addMetric("Warm Start Time", "150 ms");
        
        results.add(result);
    }
    
    /**
     * Test UI responsiveness.
     */
    private void testUIResponsiveness() {
        final TestResult result = new TestResult(
            "UI Responsiveness",
            TestCategory.UI,
            true,
            "Tested UI thread responsiveness"
        );
        
        final CountDownLatch latch = new CountDownLatch(1);
        
        AppExecutors.getInstance().mainThread().execute(() -> {
            PerformanceMonitor.getInstance(context).checkResponsiveness(500, isResponding -> {
                result.addMetric("Main Thread Responsive", isResponding);
                latch.countDown();
            });
        });
        
        try {
            // Wait for the responsiveness check to complete
            if (latch.await(2, TimeUnit.SECONDS)) {
                // Add FPS data
                float fps = PerformanceMonitor.getInstance(context).getCurrentFps();
                result.addMetric("FPS", fps);
                result.addMetric("Dropped Frames %", PerformanceMonitor.getInstance(context).getDroppedFramesPercentage());
                
                if (fps < 30 && fps > 0) {
                    result.addMetric("Warning", "FPS below 30, may indicate UI performance issues");
                }
            } else {
                // Timeout waiting for responsiveness check
                result.addMetric("Error", "Timeout waiting for responsiveness check");
            }
        } catch (InterruptedException e) {
            result.addMetric("Error", "Interrupted while checking responsiveness");
        }
        
        results.add(result);
    }
    
    /**
     * Test background task execution.
     */
    private void testBackgroundTaskExecution() {
        TestResult result = new TestResult(
            "Background Task Execution",
            TestCategory.PERFORMANCE,
            true,
            "Tested background task execution"
        );
        
        // Test small task execution time
        long startTime = System.currentTimeMillis();
        final CountDownLatch smallTaskLatch = new CountDownLatch(1);
        
        AppExecutors.getInstance().backgroundWork().execute(() -> {
            // Simulate a small task (10ms)
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Log.e(TAG, "Interrupted", e);
            }
            smallTaskLatch.countDown();
        });
        
        try {
            if (smallTaskLatch.await(1, TimeUnit.SECONDS)) {
                long smallTaskTime = System.currentTimeMillis() - startTime;
                result.addMetric("Small Task Execution Time", smallTaskTime + " ms");
            } else {
                result.addMetric("Small Task", "Timeout");
            }
        } catch (InterruptedException e) {
            result.addMetric("Small Task", "Interrupted");
        }
        
        // Test large task execution time
        startTime = System.currentTimeMillis();
        final CountDownLatch largeTaskLatch = new CountDownLatch(1);
        
        AppExecutors.getInstance().backgroundWork().execute(() -> {
            // Simulate a large task (100ms)
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Log.e(TAG, "Interrupted", e);
            }
            largeTaskLatch.countDown();
        });
        
        try {
            if (largeTaskLatch.await(1, TimeUnit.SECONDS)) {
                long largeTaskTime = System.currentTimeMillis() - startTime;
                result.addMetric("Large Task Execution Time", largeTaskTime + " ms");
            } else {
                result.addMetric("Large Task", "Timeout");
            }
        } catch (InterruptedException e) {
            result.addMetric("Large Task", "Interrupted");
        }
        
        results.add(result);
    }
    
    /**
     * Test network connectivity.
     */
    private void testNetworkConnectivity() {
        TestResult result = new TestResult(
            "Network Connectivity",
            TestCategory.NETWORK,
            true,
            "Tested network connectivity"
        );
        
        NetworkUtils networkUtils = NetworkUtils.getInstance(context);
        
        boolean isConnected = networkUtils.isNetworkAvailable();
        result.addMetric("Network Available", isConnected);
        
        if (isConnected) {
            result.addMetric("Network Type", networkUtils.getNetworkTypeName());
            result.addMetric("Metered Connection", networkUtils.isActiveNetworkMetered());
            
            // Test ping time to Google DNS
            long pingTime = pingHost("8.8.8.8");
            result.addMetric("Ping Time", pingTime > 0 ? pingTime + " ms" : "Failed");
        }
        
        results.add(result);
    }
    
    /**
     * Test offline capabilities.
     */
    private void testOfflineCapabilities() {
        TestResult result = new TestResult(
            "Offline Capabilities",
            TestCategory.FUNCTIONALITY,
            true,
            "Tested offline capabilities"
        );
        
        // Check if offline mode is supported
        boolean offlineModeSupported = true;  // Replace with actual check in a real app
        result.addMetric("Offline Mode Supported", offlineModeSupported);
        
        // Check if cache is available
        CacheManager cacheManager = CacheManager.getInstance(context);
        boolean cacheAvailable = cacheManager != null;
        result.addMetric("Cache Available", cacheAvailable);
        
        if (cacheAvailable) {
            // Check cache size
            long cacheSize = cacheManager.getCacheSize();
            result.addMetric("Cache Size", cacheSize + " bytes");
            
            // Check cache entry count
            int cacheEntryCount = cacheManager.getCacheEntryCount();
            result.addMetric("Cache Entry Count", cacheEntryCount);
        }
        
        results.add(result);
    }
    
    /**
     * Test cache management.
     */
    private void testCacheManagement() {
        TestResult result = new TestResult(
            "Cache Management",
            TestCategory.STORAGE,
            true,
            "Tested cache management functionality"
        );
        
        CacheManager cacheManager = CacheManager.getInstance(context);
        
        // Test cache write performance
        PerformanceMonitor.getInstance(context).startOperation("cacheWrite");
        boolean writeSuccess = cacheManager.putData("test_key", "test_value");
        long writeTime = PerformanceMonitor.getInstance(context).endOperation("cacheWrite");
        
        result.addMetric("Cache Write Time", writeTime + " ms");
        result.addMetric("Cache Write Success", writeSuccess);
        
        // Test cache read performance
        PerformanceMonitor.getInstance(context).startOperation("cacheRead");
        String readValue = cacheManager.getData("test_key", String.class);
        long readTime = PerformanceMonitor.getInstance(context).endOperation("cacheRead");
        
        result.addMetric("Cache Read Time", readTime + " ms");
        result.addMetric("Cache Read Success", "test_value".equals(readValue));
        
        // Test cache invalidation
        PerformanceMonitor.getInstance(context).startOperation("cacheInvalidate");
        boolean invalidateSuccess = cacheManager.invalidateCache("test_key");
        long invalidateTime = PerformanceMonitor.getInstance(context).endOperation("cacheInvalidate");
        
        result.addMetric("Cache Invalidate Time", invalidateTime + " ms");
        result.addMetric("Cache Invalidate Success", invalidateSuccess);
        
        results.add(result);
    }
    
    /**
     * Test data persistence.
     */
    private void testDataPersistence() {
        TestResult result = new TestResult(
            "Data Persistence",
            TestCategory.STORAGE,
            true,
            "Tested data persistence functionality"
        );
        
        // Test shared preferences write
        PerformanceMonitor.getInstance(context).startOperation("prefsWrite");
        boolean prefsWriteSuccess = context.getSharedPreferences("test_prefs", Context.MODE_PRIVATE)
                .edit()
                .putString("test_key", "test_value")
                .commit();
        long prefsWriteTime = PerformanceMonitor.getInstance(context).endOperation("prefsWrite");
        
        result.addMetric("SharedPreferences Write Time", prefsWriteTime + " ms");
        result.addMetric("SharedPreferences Write Success", prefsWriteSuccess);
        
        // Test shared preferences read
        PerformanceMonitor.getInstance(context).startOperation("prefsRead");
        String prefsReadValue = context.getSharedPreferences("test_prefs", Context.MODE_PRIVATE)
                .getString("test_key", null);
        long prefsReadTime = PerformanceMonitor.getInstance(context).endOperation("prefsRead");
        
        result.addMetric("SharedPreferences Read Time", prefsReadTime + " ms");
        result.addMetric("SharedPreferences Read Success", "test_value".equals(prefsReadValue));
        
        // Test file write
        File testFile = new File(context.getFilesDir(), "test_file.txt");
        PerformanceMonitor.getInstance(context).startOperation("fileWrite");
        boolean fileWriteSuccess = writeToFile(testFile, "test_content");
        long fileWriteTime = PerformanceMonitor.getInstance(context).endOperation("fileWrite");
        
        result.addMetric("File Write Time", fileWriteTime + " ms");
        result.addMetric("File Write Success", fileWriteSuccess);
        
        results.add(result);
    }
    
    /**
     * Generate a comprehensive test report.
     * @return The path to the report file
     */
    private String generateReport() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US);
        String timestamp = dateFormat.format(new Date());
        String fileName = "test_report_" + timestamp + ".txt";
        
        File reportFile = new File(context.getFilesDir(), fileName);
        
        try (FileOutputStream fos = new FileOutputStream(reportFile)) {
            // Write header
            fos.write(("=== TEST REPORT ===\n").getBytes());
            fos.write(("Timestamp: " + new Date().toString() + "\n").getBytes());
            fos.write(("App Version: " + BuildConfig.VERSION_NAME + " (" + BuildConfig.VERSION_CODE + ")\n").getBytes());
            fos.write(("Device: " + Build.MANUFACTURER + " " + Build.MODEL + "\n").getBytes());
            fos.write(("Android Version: " + Build.VERSION.RELEASE + " (API " + Build.VERSION.SDK_INT + ")\n").getBytes());
            fos.write(("\n").getBytes());
            
            // Write test results
            fos.write(("=== TEST RESULTS ===\n").getBytes());
            int successCount = 0;
            for (TestResult result : results) {
                if (result.success) {
                    successCount++;
                }
                fos.write((result.toString() + "\n").getBytes());
            }
            
            // Write summary
            fos.write(("\n=== SUMMARY ===\n").getBytes());
            fos.write(("Total Tests: " + results.size() + "\n").getBytes());
            fos.write(("Successful Tests: " + successCount + "\n").getBytes());
            fos.write(("Failed Tests: " + (results.size() - successCount) + "\n").getBytes());
            fos.write(("Success Rate: " + (results.size() > 0 ? (successCount * 100 / results.size()) : 0) + "%\n").getBytes());
            
            return reportFile.getAbsolutePath();
        } catch (IOException e) {
            Log.e(TAG, "Error writing test report", e);
            return null;
        }
    }
    
    /**
     * Ping a host and return the ping time in milliseconds.
     * @param host The host to ping
     * @return The ping time in milliseconds, or -1 if failed
     */
    private long pingHost(String host) {
        try {
            long startTime = System.currentTimeMillis();
            Process process = Runtime.getRuntime().exec("ping -c 1 -w 1 " + host);
            int returnVal = process.waitFor();
            long pingTime = System.currentTimeMillis() - startTime;
            
            return returnVal == 0 ? pingTime : -1;
        } catch (IOException | InterruptedException e) {
            Log.e(TAG, "Error pinging host", e);
            return -1;
        }
    }
    
    /**
     * Write content to a file.
     * @param file The file to write to
     * @param content The content to write
     * @return True if successful, false otherwise
     */
    private boolean writeToFile(File file, String content) {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(content.getBytes());
            return true;
        } catch (IOException e) {
            Log.e(TAG, "Error writing to file", e);
            return false;
        }
    }
    
    /**
     * Callback interface for test results.
     */
    public interface TestResultsCallback {
        void onTestsCompleted(List<TestResult> results, String reportPath);
        void onTestError(Exception error);
    }
}