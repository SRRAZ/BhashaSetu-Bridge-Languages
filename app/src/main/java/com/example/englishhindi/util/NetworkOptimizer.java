package com.bhashasetu.app.util;

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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Network optimization utility that improves efficiency of network operations.
 * Implements batching, compression, caching, and adaptive retry strategies.
 */
public class NetworkOptimizer {
    private static final String TAG = "NetworkOptimizer";
    
    // Singleton instance
    private static NetworkOptimizer instance;
    
    // Application context
    private final Context context;
    
    // Network capability tracking
    private boolean isNetworkMetered = false;
    private boolean isLowDataMode = false;
    private boolean isFastConnection = false;
    
    // Network request tracking for rate limiting
    private final Map<String, AtomicInteger> requestCounters = Collections.synchronizedMap(new HashMap<>());
    private final Map<String, Long> lastRequestTimes = Collections.synchronizedMap(new HashMap<>());
    
    // Background handler for cleanup
    private final Handler cleanupHandler = new Handler(Looper.getMainLooper());
    private static final long CLEANUP_INTERVAL_MS = 30 * 60 * 1000; // 30 minutes
    
    // Maximum concurrent requests per host
    private static final int MAX_CONCURRENT_REQUESTS = 4;
    
    // Default cache control parameters
    private static final int DEFAULT_CACHE_SECONDS = 60 * 60; // 1 hour
    private static final int LOW_PRIORITY_CACHE_SECONDS = 24 * 60 * 60; // 1 day
    
    /**
     * Private constructor for singleton pattern.
     *
     * @param context Application context
     */
    private NetworkOptimizer(Context context) {
        this.context = context.getApplicationContext();
        
        // Start tracking network capabilities
        trackNetworkCapabilities();
        
        // Schedule periodic cleanup
        scheduleCleanup();
    }
    
    /**
     * Get singleton instance.
     *
     * @param context Context
     * @return NetworkOptimizer instance
     */
    public static NetworkOptimizer getInstance(Context context) {
        if (instance == null) {
            synchronized (NetworkOptimizer.class) {
                if (instance == null) {
                    instance = new NetworkOptimizer(context);
                }
            }
        }
        return instance;
    }
    
    /**
     * Track network capabilities to adapt optimization strategies.
     */
    private void trackNetworkCapabilities() {
        ConnectivityManager connectivityManager = 
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        
        if (connectivityManager == null) {
            return;
        }
        
        // Check initial state
        updateNetworkCapabilities(connectivityManager);
        
        // Register for network callbacks if supported
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            NetworkRequest request = new NetworkRequest.Builder()
                    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    .build();
            
            connectivityManager.registerNetworkCallback(request, 
                    new ConnectivityManager.NetworkCallback() {
                @Override
                public void onCapabilitiesChanged(@NonNull Network network, 
                                                @NonNull NetworkCapabilities capabilities) {
                    updateNetworkCapabilitiesFromNetwork(capabilities);
                }
                
                @Override
                public void onLost(@NonNull Network network) {
                    // Reset to conservative defaults on network loss
                    isNetworkMetered = true;
                    isLowDataMode = true;
                    isFastConnection = false;
                }
            });
        }
    }
    
    /**
     * Update network capabilities based on current connection.
     *
     * @param connectivityManager Connectivity manager
     */
    private void updateNetworkCapabilities(ConnectivityManager connectivityManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network network = connectivityManager.getActiveNetwork();
            if (network != null) {
                NetworkCapabilities capabilities = 
                        connectivityManager.getNetworkCapabilities(network);
                if (capabilities != null) {
                    updateNetworkCapabilitiesFromNetwork(capabilities);
                    return;
                }
            }
        }
        
        // Fallback for older devices
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            isNetworkMetered = connectivityManager.isActiveNetworkMetered();
            isLowDataMode = false; // Can't detect on older devices
            isFastConnection = isConnectionFast(networkInfo);
        } else {
            isNetworkMetered = true;
            isLowDataMode = true;
            isFastConnection = false;
        }
    }
    
    /**
     * Update network capabilities from NetworkCapabilities.
     *
     * @param capabilities Network capabilities
     */
    private void updateNetworkCapabilitiesFromNetwork(NetworkCapabilities capabilities) {
        if (capabilities == null) {
            return;
        }
        
        // Check if network is metered
        isNetworkMetered = !capabilities.hasCapability(
                NetworkCapabilities.NET_CAPABILITY_NOT_METERED);
        
        // Check for low data mode on Android 10+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            isLowDataMode = !capabilities.hasCapability(
                    NetworkCapabilities.NET_CAPABILITY_NOT_RESTRICTED);
        }
        
        // Check for fast connection
        isFastConnection = capabilities.hasCapability(
                NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                 capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
                 (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) &&
                  (capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_CONGESTED) ||
                   isHighBandwidthCellular(capabilities))));
    }
    
    /**
     * Check if cellular connection has high bandwidth.
     *
     * @param capabilities Network capabilities
     * @return true if high bandwidth
     */
    private boolean isHighBandwidthCellular(NetworkCapabilities capabilities) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Android 10+ can report bandwidth
            return capabilities.getLinkDownstreamBandwidthKbps() >= 5000;
        }
        return false;
    }
    
    /**
     * Check if connection is fast based on network type.
     *
     * @param networkInfo Network info
     * @return true if connection is fast
     */
    private boolean isConnectionFast(NetworkInfo networkInfo) {
        switch (networkInfo.getType()) {
            case ConnectivityManager.TYPE_WIFI:
            case ConnectivityManager.TYPE_ETHERNET:
                return true;
            case ConnectivityManager.TYPE_MOBILE:
                switch (networkInfo.getSubtype()) {
                    // 4G
                    case 13: // LTE
                    case 14: // HSPAP
                    case 19: // LTE_CA
                        return true;
                    default:
                        return false;
                }
            default:
                return false;
        }
    }
    
    /**
     * Schedule periodic cleanup of tracking data.
     */
    private void scheduleCleanup() {
        cleanupHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                synchronized (requestCounters) {
                    requestCounters.clear();
                }
                
                synchronized (lastRequestTimes) {
                    lastRequestTimes.clear();
                }
                
                // Schedule next cleanup
                cleanupHandler.postDelayed(this, CLEANUP_INTERVAL_MS);
            }
        }, CLEANUP_INTERVAL_MS);
    }
    
    /**
     * Get OkHttpClient.Builder configured with optimization interceptors.
     *
     * @return Configured OkHttpClient.Builder
     */
    public OkHttpClient.Builder getOptimizedClientBuilder() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        
        // Set timeouts based on network conditions
        int connectTimeout = isFastConnection ? 10 : 20; // seconds
        int readTimeout = isFastConnection ? 15 : 30; // seconds
        
        builder.connectTimeout(connectTimeout, TimeUnit.SECONDS)
               .readTimeout(readTimeout, TimeUnit.SECONDS)
               .writeTimeout(readTimeout, TimeUnit.SECONDS);
        
        // Add cache control interceptor
        builder.addInterceptor(getCacheControlInterceptor());
        
        // Add compression interceptor
        builder.addInterceptor(getCompressionInterceptor());
        
        // Add rate limiting interceptor
        builder.addInterceptor(getRateLimitingInterceptor());
        
        // Set connection pool optimizations
        builder.retryOnConnectionFailure(true);
        
        return builder;
    }
    
    /**
     * Create cache control interceptor.
     *
     * @return Cache control interceptor
     */
    private Interceptor getCacheControlInterceptor() {
        return chain -> {
            Request request = chain.request();
            
            // Skip if request already has cache control
            if (request.header("Cache-Control") != null) {
                return chain.proceed(request);
            }
            
            // Apply different cache policies based on network type and endpoint priority
            int cacheSeconds = DEFAULT_CACHE_SECONDS;
            
            // For low priority endpoints, cache longer when on metered connection
            String url = request.url().toString();
            boolean isLowPriorityEndpoint = isLowPriorityUrl(url);
            
            if (isNetworkMetered && isLowPriorityEndpoint) {
                cacheSeconds = LOW_PRIORITY_CACHE_SECONDS;
            }
            
            // Build cache control header
            String cacheHeader = new CacheControl.Builder()
                    .maxAge(cacheSeconds, TimeUnit.SECONDS)
                    .build()
                    .toString();
            
            // Add cache control header to request
            Request modifiedRequest = request.newBuilder()
                    .header("Cache-Control", cacheHeader)
                    .build();
            
            Response response = chain.proceed(modifiedRequest);
            
            // If not cacheable response, use cache control for stale responses
            if (!isCacheableResponse(response) && isNetworkMetered) {
                return response.newBuilder()
                        .header("Cache-Control", "public, max-stale=" + (60 * 60 * 24))
                        .build();
            }
            
            return response;
        };
    }
    
    /**
     * Check if URL is low priority for caching purposes.
     *
     * @param url URL to check
     * @return true if low priority
     */
    private boolean isLowPriorityUrl(String url) {
        // Static content is generally low priority for real-time updates
        return url.contains("/static/") || 
               url.contains("/images/") || 
               url.contains("/assets/");
    }
    
    /**
     * Check if response is cacheable.
     *
     * @param response HTTP response
     * @return true if cacheable
     */
    private boolean isCacheableResponse(Response response) {
        // Check response code
        int code = response.code();
        if (code != 200 && code != 203 && code != 300 && 
            code != 301 && code != 410) {
            return false;
        }
        
        // Check cache control headers
        String cacheControl = response.header("Cache-Control");
        if (cacheControl != null && 
            (cacheControl.contains("no-store") || 
             cacheControl.contains("no-cache") ||
             cacheControl.contains("must-revalidate") || 
             cacheControl.contains("max-age=0"))) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Create compression interceptor.
     *
     * @return Compression interceptor
     */
    private Interceptor getCompressionInterceptor() {
        return chain -> {
            Request request = chain.request();
            
            // Add Accept-Encoding if not present
            if (request.header("Accept-Encoding") == null) {
                request = request.newBuilder()
                        .header("Accept-Encoding", "gzip, deflate")
                        .build();
            }
            
            return chain.proceed(request);
        };
    }
    
    /**
     * Create rate limiting interceptor.
     *
     * @return Rate limiting interceptor
     */
    private Interceptor getRateLimitingInterceptor() {
        return chain -> {
            Request request = chain.request();
            String host = request.url().host();
            
            // Track request count per host
            AtomicInteger counter = requestCounters.computeIfAbsent(
                    host, k -> new AtomicInteger(0));
            
            // Check rate limit
            int currentCount = counter.incrementAndGet();
            
            // If we're over the limit, apply rate limiting
            if (currentCount > MAX_CONCURRENT_REQUESTS) {
                // Get the last request time
                Long lastRequestTime = lastRequestTimes.get(host);
                long now = System.currentTimeMillis();
                
                // If we've made a request recently, add delay
                if (lastRequestTime != null && now - lastRequestTime < 1000) {
                    try {
                        // Adaptive delay based on how many requests over limit
                        int delay = Math.min(500, 100 * (currentCount - MAX_CONCURRENT_REQUESTS));
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
            
            // Update last request time
            lastRequestTimes.put(host, System.currentTimeMillis());
            
            try {
                // Proceed with request
                return chain.proceed(request);
            } finally {
                // Decrement counter after request completes
                counter.decrementAndGet();
            }
        };
    }
    
    /**
     * Get the appropriate cache control policy based on network state and request importance.
     *
     * @param isEssential Whether this request is essential/high-priority
     * @return CacheControl policy
     */
    public CacheControl getCachePolicy(boolean isEssential) {
        CacheControl.Builder builder = new CacheControl.Builder();
        
        if (isNetworkMetered || isLowDataMode) {
            if (isEssential) {
                // Essential data on metered connection - moderate caching
                builder.maxAge(1, TimeUnit.HOURS)
                       .maxStale(1, TimeUnit.DAYS);
            } else {
                // Non-essential data on metered connection - aggressive caching
                builder.maxAge(1, TimeUnit.DAYS)
                       .maxStale(7, TimeUnit.DAYS);
            }
        } else {
            if (isEssential) {
                // Essential data on unmetered connection - minimal caching
                builder.maxAge(5, TimeUnit.MINUTES)
                       .maxStale(1, TimeUnit.HOURS);
            } else {
                // Non-essential data on unmetered connection - standard caching
                builder.maxAge(1, TimeUnit.HOURS)
                       .maxStale(1, TimeUnit.DAYS);
            }
        }
        
        return builder.build();
    }
    
    /**
     * Check if fast connection is available.
     *
     * @return true if connection is fast
     */
    public boolean isFastConnection() {
        return isFastConnection;
    }
    
    /**
     * Check if network is metered.
     *
     * @return true if network is metered
     */
    public boolean isNetworkMetered() {
        return isNetworkMetered;
    }
    
    /**
     * Check if device is in low data mode.
     *
     * @return true if in low data mode
     */
    public boolean isLowDataMode() {
        return isLowDataMode;
    }
    
    /**
     * Get recommended image quality based on network conditions.
     *
     * @return Image quality (0-100)
     */
    public int getRecommendedImageQuality() {
        if (isLowDataMode) {
            return 60; // Very compressed
        } else if (isNetworkMetered) {
            return 75; // Moderately compressed
        } else if (isFastConnection) {
            return 95; // High quality
        } else {
            return 85; // Balanced
        }
    }
    
    /**
     * Get recommended batch size for network requests.
     *
     * @return Recommended batch size
     */
    public int getRecommendedBatchSize() {
        if (isLowDataMode) {
            return 20; // Larger batches to reduce overhead
        } else if (isNetworkMetered) {
            return 15;
        } else if (isFastConnection) {
            return 5; // Smaller batches for fresher data
        } else {
            return 10;
        }
    }
    
    /**
     * Get recommended image resolution adjustment factor based on network conditions.
     *
     * @return Resolution factor (0.0-1.0)
     */
    public float getRecommendedResolutionFactor() {
        if (isLowDataMode) {
            return 0.5f; // Half resolution
        } else if (isNetworkMetered) {
            return 0.75f; // 75% resolution
        } else {
            return 1.0f; // Full resolution
        }
    }
    
    /**
     * Release resources.
     */
    public void release() {
        cleanupHandler.removeCallbacksAndMessages(null);
    }
}