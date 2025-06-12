package com.bhashasetu.app.monitoring;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Comprehensive network monitoring system that tracks network performance,
 * bandwidth usage, and helps optimize network operations.
 */
public class NetworkMonitor {
    private static final String TAG = "NetworkMonitor";
    
    // Singleton instance
    private static NetworkMonitor instance;
    
    // Context
    private final Context context;
    
    // Handler for main thread operations
    private final Handler mainHandler;
    
    // Background thread for network operations
    private final ExecutorService executor;
    
    // Network state
    private boolean isNetworkAvailable = false;
    private boolean isWifi = false;
    private boolean isMobile = false;
    private boolean isMetered = true;
    private int networkType = ConnectivityManager.TYPE_MOBILE;
    private String networkTypeName = "unknown";
    private int networkSubtype = 0;
    private int signalStrength = -1;
    
    // Network performance metrics
    private final AtomicLong totalBytesReceived = new AtomicLong(0);
    private final AtomicLong totalBytesSent = new AtomicLong(0);
    private final AtomicInteger totalRequests = new AtomicInteger(0);
    private final AtomicInteger failedRequests = new AtomicInteger(0);
    private final AtomicLong totalResponseTime = new AtomicLong(0);
    
    // Request timing cache
    private final Map<String, RequestTiming> requestTimings = Collections.synchronizedMap(
            new LinkedHashMap<String, RequestTiming>(100, 0.75f, true) {
                @Override
                protected boolean removeEldestEntry(Map.Entry<String, RequestTiming> eldest) {
                    return size() > 100; // Keep last 100 request timings
                }
            });
    
    // Domain statistics
    private final Map<String, DomainStat> domainStats = Collections.synchronizedMap(new HashMap<>());
    
    // Bandwidth samples for estimation
    private final List<BandwidthSample> bandwidthSamples = new ArrayList<>();
    private static final int MAX_BANDWIDTH_SAMPLES = 20;
    
    // Speed test results
    private long lastSpeedTestTime = 0;
    private float downloadSpeedMbps = 0;
    private float uploadSpeedMbps = 0;
    private int pingMs = 0;
    
    // Listeners
    private final List<NetworkListener> listeners = Collections.synchronizedList(new ArrayList<>());
    
    // Network callback
    private ConnectivityManager.NetworkCallback networkCallback;
    
    /**
     * Network event listener.
     */
    public interface NetworkListener {
        void onNetworkStateChanged(NetworkState state);
        void onNetworkMetricsUpdated(Map<String, Object> metrics);
    }
    
    /**
     * Network state information.
     */
    public static class NetworkState {
        private final boolean available;
        private final boolean wifi;
        private final boolean mobile;
        private final boolean metered;
        private final String typeName;
        private final int type;
        private final int subtype;
        private final int signalStrength;
        
        NetworkState(boolean available, boolean wifi, boolean mobile, boolean metered,
                  String typeName, int type, int subtype, int signalStrength) {
            this.available = available;
            this.wifi = wifi;
            this.mobile = mobile;
            this.metered = metered;
            this.typeName = typeName;
            this.type = type;
            this.subtype = subtype;
            this.signalStrength = signalStrength;
        }
        
        public boolean isAvailable() {
            return available;
        }
        
        public boolean isWifi() {
            return wifi;
        }
        
        public boolean isMobile() {
            return mobile;
        }
        
        public boolean isMetered() {
            return metered;
        }
        
        public String getTypeName() {
            return typeName;
        }
        
        public int getType() {
            return type;
        }
        
        public int getSubtype() {
            return subtype;
        }
        
        public int getSignalStrength() {
            return signalStrength;
        }
        
        @Override
        public String toString() {
            return "NetworkState{" +
                    "available=" + available +
                    ", wifi=" + wifi +
                    ", mobile=" + mobile +
                    ", metered=" + metered +
                    ", typeName='" + typeName + '\'' +
                    ", type=" + type +
                    ", subtype=" + subtype +
                    ", signalStrength=" + signalStrength +
                    '}';
        }
    }
    
    /**
     * Structure to track request timing information.
     */
    private static class RequestTiming {
        final String url;
        final long startTime;
        long endTime;
        long bytes;
        int statusCode;
        boolean successful;
        String errorMessage;
        
        RequestTiming(String url) {
            this.url = url;
            this.startTime = System.currentTimeMillis();
            this.endTime = 0;
            this.bytes = 0;
            this.statusCode = -1;
            this.successful = false;
            this.errorMessage = null;
        }
        
        long getDuration() {
            if (endTime == 0) {
                return System.currentTimeMillis() - startTime;
            }
            return endTime - startTime;
        }
    }
    
    /**
     * Structure for domain statistics.
     */
    private static class DomainStat {
        final String domain;
        final AtomicLong bytesReceived = new AtomicLong(0);
        final AtomicLong totalTime = new AtomicLong(0);
        final AtomicInteger requestCount = new AtomicInteger(0);
        final AtomicInteger errorCount = new AtomicInteger(0);
        
        DomainStat(String domain) {
            this.domain = domain;
        }
        
        float getAverageResponseTime() {
            int count = requestCount.get();
            if (count == 0) {
                return 0;
            }
            return totalTime.get() / (float) count;
        }
        
        float getErrorRate() {
            int count = requestCount.get();
            if (count == 0) {
                return 0;
            }
            return (errorCount.get() * 100f) / count;
        }
        
        void addRequest(long bytes, long time, boolean successful) {
            bytesReceived.addAndGet(bytes);
            totalTime.addAndGet(time);
            requestCount.incrementAndGet();
            if (!successful) {
                errorCount.incrementAndGet();
            }
        }
    }
    
    /**
     * Bandwidth sample for connection speed estimation.
     */
    private static class BandwidthSample {
        final long timestamp;
        final long bytes;
        final long durationMs;
        final boolean isDownload;
        
        BandwidthSample(long bytes, long durationMs, boolean isDownload) {
            this.timestamp = System.currentTimeMillis();
            this.bytes = bytes;
            this.durationMs = durationMs;
            this.isDownload = isDownload;
        }
        
        float getMbps() {
            if (durationMs <= 0) {
                return 0;
            }
            
            // Convert bytes to bits and duration to seconds
            float bits = bytes * 8f;
            float seconds = durationMs / 1000f;
            
            // Calculate Mbps
            return (bits / 1_000_000f) / seconds;
        }
    }
    
    /**
     * Private constructor.
     *
     * @param context Application context
     */
    private NetworkMonitor(Context context) {
        this.context = context.getApplicationContext();
        this.mainHandler = new Handler(Looper.getMainLooper());
        this.executor = Executors.newSingleThreadExecutor();
        
        // Initialize network state
        initNetworkState();
        
        // Register network callback
        registerNetworkCallback();
    }
    
    /**
     * Get singleton instance.
     *
     * @param context Context
     * @return NetworkMonitor instance
     */
    public static synchronized NetworkMonitor getInstance(Context context) {
        if (instance == null) {
            instance = new NetworkMonitor(context);
        }
        return instance;
    }
    
    /**
     * Initialize network state.
     */
    private void initNetworkState() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network network = cm.getActiveNetwork();
            if (network != null) {
                NetworkCapabilities capabilities = cm.getNetworkCapabilities(network);
                updateNetworkState(network, capabilities);
                return;
            }
        }
        
        // Fallback for older Android versions
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            isNetworkAvailable = true;
            networkType = networkInfo.getType();
            networkSubtype = networkInfo.getSubtype();
            networkTypeName = networkInfo.getTypeName();
            
            isWifi = networkType == ConnectivityManager.TYPE_WIFI;
            isMobile = networkType == ConnectivityManager.TYPE_MOBILE;
            isMetered = cm.isActiveNetworkMetered();
            
            // Notify network state change
            notifyNetworkStateChanged();
        } else {
            isNetworkAvailable = false;
            isWifi = false;
            isMobile = false;
            isMetered = true;
            
            // Notify network state change
            notifyNetworkStateChanged();
        }
    }
    
    /**
     * Register network callback for monitoring.
     */
    private void registerNetworkCallback() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // Unregister previous callback if any
            if (networkCallback != null) {
                try {
                    cm.unregisterNetworkCallback(networkCallback);
                } catch (Exception e) {
                    Log.e(TAG, "Error unregistering network callback", e);
                }
            }
            
            // Create new callback
            networkCallback = new ConnectivityManager.NetworkCallback() {
                @Override
                public void onAvailable(@NonNull Network network) {
                    NetworkCapabilities capabilities = cm.getNetworkCapabilities(network);
                    updateNetworkState(network, capabilities);
                }
                
                @Override
                public void onLost(@NonNull Network network) {
                    isNetworkAvailable = false;
                    notifyNetworkStateChanged();
                }
                
                @Override
                public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities capabilities) {
                    updateNetworkState(network, capabilities);
                }
            };
            
            // Register callback
            NetworkRequest request = new NetworkRequest.Builder()
                    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    .build();
            
            cm.registerNetworkCallback(request, networkCallback);
        }
    }
    
    /**
     * Update network state based on capabilities.
     *
     * @param network Network
     * @param capabilities Network capabilities
     */
    private void updateNetworkState(Network network, NetworkCapabilities capabilities) {
        if (capabilities == null) {
            isNetworkAvailable = false;
            notifyNetworkStateChanged();
            return;
        }
        
        isNetworkAvailable = capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
        
        isWifi = capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
        isMobile = capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR);
        
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        
        // Get network type and subtype
        if (isWifi) {
            networkType = ConnectivityManager.TYPE_WIFI;
            networkTypeName = "WIFI";
            networkSubtype = 0;
        } else if (isMobile) {
            networkType = ConnectivityManager.TYPE_MOBILE;
            networkTypeName = "MOBILE";
            networkSubtype = 0; // Can't reliably get subtype with modern APIs
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
            networkType = ConnectivityManager.TYPE_ETHERNET;
            networkTypeName = "ETHERNET";
            networkSubtype = 0;
        } else {
            networkType = -1;
            networkTypeName = "UNKNOWN";
            networkSubtype = 0;
        }
        
        // Check if network is metered
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            isMetered = !capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED);
        } else {
            isMetered = cm.isActiveNetworkMetered();
        }
        
        // Estimate signal strength
        signalStrength = -1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Signal strength can be estimated from bandwidth
            int downstreamBandwidth = capabilities.getLinkDownstreamBandwidthKbps();
            
            if (isWifi) {
                // Wi-Fi signal strength estimation
                if (downstreamBandwidth > 50000) {
                    signalStrength = 4; // Excellent
                } else if (downstreamBandwidth > 20000) {
                    signalStrength = 3; // Good
                } else if (downstreamBandwidth > 5000) {
                    signalStrength = 2; // Fair
                } else {
                    signalStrength = 1; // Poor
                }
            } else if (isMobile) {
                // Mobile signal strength estimation
                if (downstreamBandwidth > 10000) {
                    signalStrength = 4; // Excellent
                } else if (downstreamBandwidth > 5000) {
                    signalStrength = 3; // Good
                } else if (downstreamBandwidth > 1000) {
                    signalStrength = 2; // Fair
                } else {
                    signalStrength = 1; // Poor
                }
            }
        }
        
        // Notify network state change
        notifyNetworkStateChanged();
    }
    
    /**
     * Notify listeners of network state change.
     */
    private void notifyNetworkStateChanged() {
        NetworkState state = new NetworkState(
                isNetworkAvailable,
                isWifi,
                isMobile,
                isMetered,
                networkTypeName,
                networkType,
                networkSubtype,
                signalStrength
        );
        
        Log.d(TAG, "Network state changed: " + state);
        
        // Notify on main thread
        mainHandler.post(() -> {
            for (NetworkListener listener : new ArrayList<>(listeners)) {
                listener.onNetworkStateChanged(state);
            }
        });
    }
    
    /**
     * Get current network state.
     *
     * @return NetworkState
     */
    public NetworkState getNetworkState() {
        return new NetworkState(
                isNetworkAvailable,
                isWifi,
                isMobile,
                isMetered,
                networkTypeName,
                networkType,
                networkSubtype,
                signalStrength
        );
    }
    
    /**
     * Record start of a network request.
     *
     * @param url Request URL
     * @return Request ID for tracking
     */
    public String recordRequestStart(String url) {
        if (url == null || url.isEmpty()) {
            return null;
        }
        
        String requestId = url + "-" + System.currentTimeMillis();
        RequestTiming timing = new RequestTiming(url);
        requestTimings.put(requestId, timing);
        
        return requestId;
    }
    
    /**
     * Record completion of a network request.
     *
     * @param requestId Request ID from recordRequestStart
     * @param bytes Bytes received
     * @param statusCode HTTP status code
     * @param successful Whether request was successful
     */
    public void recordRequestComplete(String requestId, long bytes, int statusCode, boolean successful) {
        if (requestId == null) {
            return;
        }
        
        RequestTiming timing = requestTimings.get(requestId);
        if (timing == null) {
            return;
        }
        
        // Update timing
        timing.endTime = System.currentTimeMillis();
        timing.bytes = bytes;
        timing.statusCode = statusCode;
        timing.successful = successful;
        
        // Update global stats
        totalBytesReceived.addAndGet(bytes);
        totalRequests.incrementAndGet();
        totalResponseTime.addAndGet(timing.getDuration());
        
        if (!successful) {
            failedRequests.incrementAndGet();
        }
        
        // Update domain stats
        String domain = extractDomain(timing.url);
        DomainStat stat = domainStats.get(domain);
        if (stat == null) {
            stat = new DomainStat(domain);
            domainStats.put(domain, stat);
        }
        stat.addRequest(bytes, timing.getDuration(), successful);
        
        // Add bandwidth sample if large enough for meaningful measurement
        if (bytes > 20 * 1024) { // Only consider samples > 20KB
            BandwidthSample sample = new BandwidthSample(bytes, timing.getDuration(), true);
            synchronized (bandwidthSamples) {
                bandwidthSamples.add(sample);
                if (bandwidthSamples.size() > MAX_BANDWIDTH_SAMPLES) {
                    bandwidthSamples.remove(0);
                }
            }
        }
        
        // Notify metrics update
        notifyMetricsUpdated();
    }
    
    /**
     * Record error in a network request.
     *
     * @param requestId Request ID from recordRequestStart
     * @param errorMessage Error message
     */
    public void recordRequestError(String requestId, String errorMessage) {
        if (requestId == null) {
            return;
        }
        
        RequestTiming timing = requestTimings.get(requestId);
        if (timing == null) {
            return;
        }
        
        // Update timing
        timing.endTime = System.currentTimeMillis();
        timing.successful = false;
        timing.errorMessage = errorMessage;
        
        // Update global stats
        totalRequests.incrementAndGet();
        failedRequests.incrementAndGet();
        totalResponseTime.addAndGet(timing.getDuration());
        
        // Update domain stats
        String domain = extractDomain(timing.url);
        DomainStat stat = domainStats.get(domain);
        if (stat == null) {
            stat = new DomainStat(domain);
            domainStats.put(domain, stat);
        }
        stat.addRequest(0, timing.getDuration(), false);
        
        // Notify metrics update
        notifyMetricsUpdated();
    }
    
    /**
     * Extract domain from URL.
     *
     * @param url URL
     * @return Domain
     */
    private String extractDomain(String url) {
        if (url == null || url.isEmpty()) {
            return "unknown";
        }
        
        try {
            URL parsedUrl = new URL(url);
            return parsedUrl.getHost();
        } catch (Exception e) {
            return "invalid";
        }
    }
    
    /**
     * Notify listeners of metrics update.
     */
    private void notifyMetricsUpdated() {
        Map<String, Object> metrics = getNetworkMetrics();
        
        // Notify on main thread
        mainHandler.post(() -> {
            for (NetworkListener listener : new ArrayList<>(listeners)) {
                listener.onNetworkMetricsUpdated(metrics);
            }
        });
    }
    
    /**
     * Get network metrics.
     *
     * @return Map of metrics
     */
    public Map<String, Object> getNetworkMetrics() {
        Map<String, Object> metrics = new HashMap<>();
        
        // Basic metrics
        metrics.put("bytes_received", totalBytesReceived.get());
        metrics.put("bytes_sent", totalBytesSent.get());
        metrics.put("total_requests", totalRequests.get());
        metrics.put("failed_requests", failedRequests.get());
        
        // Calculate average response time
        int requestCount = totalRequests.get();
        float avgResponseTime = requestCount > 0 ? 
                              totalResponseTime.get() / (float) requestCount : 0;
        metrics.put("avg_response_time_ms", avgResponseTime);
        
        // Calculate error rate
        float errorRate = requestCount > 0 ? 
                        (failedRequests.get() * 100f) / requestCount : 0;
        metrics.put("error_rate_percent", errorRate);
        
        // Network type
        metrics.put("network_type", networkTypeName);
        metrics.put("is_wifi", isWifi);
        metrics.put("is_mobile", isMobile);
        metrics.put("is_metered", isMetered);
        
        // Speed estimates
        metrics.put("estimated_download_mbps", estimateDownloadSpeed());
        metrics.put("estimated_upload_mbps", estimateUploadSpeed());
        
        // Recent requests
        List<Map<String, Object>> recentRequests = new ArrayList<>();
        for (RequestTiming timing : new ArrayList<>(requestTimings.values())) {
            Map<String, Object> request = new HashMap<>();
            request.put("url", timing.url);
            request.put("start_time", timing.startTime);
            request.put("duration_ms", timing.getDuration());
            request.put("bytes", timing.bytes);
            request.put("status_code", timing.statusCode);
            request.put("successful", timing.successful);
            request.put("error", timing.errorMessage);
            
            recentRequests.add(request);
            
            // Limit to last 10 requests for brevity
            if (recentRequests.size() >= 10) {
                break;
            }
        }
        metrics.put("recent_requests", recentRequests);
        
        // Domain statistics
        Map<String, Object> domainMetrics = new HashMap<>();
        for (DomainStat stat : domainStats.values()) {
            Map<String, Object> domainInfo = new HashMap<>();
            domainInfo.put("requests", stat.requestCount.get());
            domainInfo.put("errors", stat.errorCount.get());
            domainInfo.put("bytes", stat.bytesReceived.get());
            domainInfo.put("avg_response_time_ms", stat.getAverageResponseTime());
            domainInfo.put("error_rate_percent", stat.getErrorRate());
            
            domainMetrics.put(stat.domain, domainInfo);
        }
        metrics.put("domains", domainMetrics);
        
        // Speed test results
        if (lastSpeedTestTime > 0) {
            Map<String, Object> speedTest = new HashMap<>();
            speedTest.put("download_mbps", downloadSpeedMbps);
            speedTest.put("upload_mbps", uploadSpeedMbps);
            speedTest.put("ping_ms", pingMs);
            speedTest.put("timestamp", lastSpeedTestTime);
            metrics.put("speed_test", speedTest);
        }
        
        return metrics;
    }
    
    /**
     * Estimate download speed based on recent network requests.
     *
     * @return Estimated speed in Mbps
     */
    private float estimateDownloadSpeed() {
        synchronized (bandwidthSamples) {
            if (bandwidthSamples.isEmpty()) {
                return 0;
            }
            
            // Use only recent samples and only download samples
            long now = System.currentTimeMillis();
            List<BandwidthSample> validSamples = new ArrayList<>();
            
            for (BandwidthSample sample : bandwidthSamples) {
                if (sample.isDownload && now - sample.timestamp < TimeUnit.MINUTES.toMillis(5)) {
                    validSamples.add(sample);
                }
            }
            
            if (validSamples.isEmpty()) {
                return 0;
            }
            
            // Calculate average
            float totalMbps = 0;
            for (BandwidthSample sample : validSamples) {
                totalMbps += sample.getMbps();
            }
            
            return totalMbps / validSamples.size();
        }
    }
    
    /**
     * Estimate upload speed based on recent network requests.
     *
     * @return Estimated speed in Mbps
     */
    private float estimateUploadSpeed() {
        synchronized (bandwidthSamples) {
            if (bandwidthSamples.isEmpty()) {
                return 0;
            }
            
            // Use only recent samples and only upload samples
            long now = System.currentTimeMillis();
            List<BandwidthSample> validSamples = new ArrayList<>();
            
            for (BandwidthSample sample : bandwidthSamples) {
                if (!sample.isDownload && now - sample.timestamp < TimeUnit.MINUTES.toMillis(5)) {
                    validSamples.add(sample);
                }
            }
            
            if (validSamples.isEmpty()) {
                // If no upload samples, estimate based on download speed
                return estimateDownloadSpeed() * 0.3f; // Upload is typically slower
            }
            
            // Calculate average
            float totalMbps = 0;
            for (BandwidthSample sample : validSamples) {
                totalMbps += sample.getMbps();
            }
            
            return totalMbps / validSamples.size();
        }
    }
    
    /**
     * Perform a network speed test.
     *
     * @param listener Listener for results
     */
    public void performSpeedTest(@Nullable final SpeedTestListener listener) {
        if (!isNetworkAvailable) {
            if (listener != null) {
                mainHandler.post(() -> listener.onSpeedTestFailed("No network connection"));
            }
            return;
        }
        
        // Speed tests should be run in background
        executor.submit(() -> {
            try {
                // Notify start
                if (listener != null) {
                    mainHandler.post(() -> listener.onSpeedTestStarted());
                }
                
                // Measure ping
                int ping = measurePing();
                
                // Measure download speed
                float downloadSpeed = measureDownloadSpeed();
                
                // Measure upload speed
                float uploadSpeed = measureUploadSpeed();
                
                // Store results
                pingMs = ping;
                downloadSpeedMbps = downloadSpeed;
                uploadSpeedMbps = uploadSpeed;
                lastSpeedTestTime = System.currentTimeMillis();
                
                // Notify result
                if (listener != null) {
                    final float finalDownloadSpeed = downloadSpeed;
                    final float finalUploadSpeed = uploadSpeed;
                    final int finalPing = ping;
                    
                    mainHandler.post(() -> listener.onSpeedTestCompleted(
                            finalDownloadSpeed, finalUploadSpeed, finalPing));
                }
                
                // Notify metrics update
                notifyMetricsUpdated();
            } catch (Exception e) {
                Log.e(TAG, "Speed test failed", e);
                
                if (listener != null) {
                    final String errorMessage = e.getMessage();
                    mainHandler.post(() -> listener.onSpeedTestFailed(errorMessage));
                }
            }
        });
    }
    
    /**
     * Measure ping to a known server.
     *
     * @return Ping time in milliseconds
     * @throws IOException If ping fails
     */
    private int measurePing() throws IOException {
        String pingUrl = "https://www.google.com";
        
        long start = System.currentTimeMillis();
        
        URL url = new URL(pingUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("HEAD");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        
        try {
            connection.connect();
            int responseCode = connection.getResponseCode();
            
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new IOException("Ping failed with response code: " + responseCode);
            }
            
            long end = System.currentTimeMillis();
            return (int) (end - start);
        } finally {
            connection.disconnect();
        }
    }
    
    /**
     * Measure download speed.
     *
     * @return Download speed in Mbps
     * @throws IOException If test fails
     */
    private float measureDownloadSpeed() throws IOException {
        // This is a simplistic implementation
        // In a real app, you'd use a more sophisticated test
        
        // Use a test file of reasonable size (1MB)
        String testUrl = "https://speed.cloudflare.com/cdn-cgi/trace";
        
        URL url = new URL(testUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);
        
        try {
            connection.connect();
            
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new IOException("Download test failed with response code: " + responseCode);
            }
            
            // Get file size
            int contentLength = connection.getContentLength();
            if (contentLength <= 0) {
                contentLength = 1000000; // Default to 1MB if size unknown
            }
            
            // Download the file and measure time
            long start = System.currentTimeMillis();
            
            byte[] buffer = new byte[8192];
            int bytesRead;
            long totalBytesRead = 0;
            
            try (java.io.InputStream inputStream = connection.getInputStream()) {
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    totalBytesRead += bytesRead;
                }
            }
            
            long end = System.currentTimeMillis();
            long duration = end - start;
            
            if (duration <= 0) {
                duration = 1; // Avoid division by zero
            }
            
            // Calculate speed in Mbps
            float speedMbps = ((float) totalBytesRead * 8 / 1_000_000) / (duration / 1000f);
            
            // Add as bandwidth sample
            BandwidthSample sample = new BandwidthSample(totalBytesRead, duration, true);
            synchronized (bandwidthSamples) {
                bandwidthSamples.add(sample);
                if (bandwidthSamples.size() > MAX_BANDWIDTH_SAMPLES) {
                    bandwidthSamples.remove(0);
                }
            }
            
            return speedMbps;
        } finally {
            connection.disconnect();
        }
    }
    
    /**
     * Measure upload speed.
     *
     * @return Upload speed in Mbps
     * @throws IOException If test fails
     */
    private float measureUploadSpeed() throws IOException {
        // This is a simplistic implementation
        // In a real app, you'd use a more sophisticated test
        
        // For demo purposes, we'll simulate an upload by estimating
        return estimateDownloadSpeed() * 0.4f; // Upload typically slower than download
    }
    
    /**
     * Speed test listener interface.
     */
    public interface SpeedTestListener {
        void onSpeedTestStarted();
        void onSpeedTestCompleted(float downloadMbps, float uploadMbps, int pingMs);
        void onSpeedTestFailed(String error);
    }
    
    /**
     * Add network listener.
     *
     * @param listener Listener to add
     */
    public void addListener(NetworkListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
            
            // Immediately notify of current state
            NetworkState state = getNetworkState();
            Map<String, Object> metrics = getNetworkMetrics();
            
            mainHandler.post(() -> {
                listener.onNetworkStateChanged(state);
                listener.onNetworkMetricsUpdated(metrics);
            });
        }
    }
    
    /**
     * Remove network listener.
     *
     * @param listener Listener to remove
     */
    public void removeListener(NetworkListener listener) {
        listeners.remove(listener);
    }
    
    /**
     * Release resources.
     */
    public void release() {
        // Unregister network callback
        if (networkCallback != null) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            try {
                cm.unregisterNetworkCallback(networkCallback);
            } catch (Exception e) {
                Log.e(TAG, "Error unregistering network callback", e);
            }
            networkCallback = null;
        }
        
        // Clear listeners
        listeners.clear();
        
        // Shutdown executor
        executor.shutdown();
    }
}