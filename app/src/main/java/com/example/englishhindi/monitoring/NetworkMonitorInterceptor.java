package com.example.englishhindi.monitoring;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;

/**
 * Network interceptor for OkHttp that monitors network calls and reports metrics
 * to the performance monitoring system. This interceptor should be added to the
 * OkHttpClient to track all network requests.
 */
public class NetworkMonitorInterceptor implements okhttp3.Interceptor {
    
    private static final String TAG = "NetworkMonitorInterceptor";
    
    // Reference to monitoring manager
    private final PerformanceMonitoringManager monitoringManager;
    
    /**
     * Create a new network monitor interceptor.
     *
     * @param context Context
     */
    public NetworkMonitorInterceptor(Context context) {
        this.monitoringManager = PerformanceMonitoringManager.getInstance(context);
    }
    
    @NonNull
    @Override
    public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
        okhttp3.Request request = chain.request();
        String url = request.url().toString();
        
        // Record request start
        String requestId = monitoringManager.getNetworkMonitor().recordRequestStart(url);
        
        long startTime = System.currentTimeMillis();
        okhttp3.Response response;
        
        try {
            // Execute the request
            response = chain.proceed(request);
            
            // Calculate request duration
            long duration = System.currentTimeMillis() - startTime;
            
            // Get response size if available
            long responseSize = 0;
            okhttp3.ResponseBody responseBody = response.body();
            if (responseBody != null) {
                okhttp3.MediaType contentType = responseBody.contentType();
                if (contentType != null && response.header("Content-Length") != null) {
                    try {
                        responseSize = Long.parseLong(response.header("Content-Length", "0"));
                    } catch (NumberFormatException e) {
                        // Ignore parse error
                    }
                }
            }
            
            // Record successful request
            monitoringManager.recordNetworkRequest(
                    url,
                    response.code(),
                    responseSize,
                    duration);
            
            // Record in network monitor
            monitoringManager.getNetworkMonitor().recordRequestComplete(
                    requestId,
                    responseSize,
                    response.code(),
                    response.isSuccessful());
            
            return response;
            
        } catch (IOException e) {
            // Calculate request duration
            long duration = System.currentTimeMillis() - startTime;
            
            // Record failed request
            monitoringManager.recordNetworkError(url, e.getMessage());
            
            // Record in network monitor
            monitoringManager.getNetworkMonitor().recordRequestError(requestId, e.getMessage());
            
            throw e;
        }
    }
}