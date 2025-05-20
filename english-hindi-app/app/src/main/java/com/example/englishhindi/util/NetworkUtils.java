package com.example.englishhindi.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Utility class for network operations and connectivity checking.
 * Provides methods to monitor network status and verify server availability.
 */
public class NetworkUtils {
    
    private static final String PREF_NAME = "network_prefs";
    private static final String KEY_OFFLINE_MODE = "offline_mode";
    private static final String KEY_WIFI_ONLY = "wifi_only";
    
    private Context context;
    private SharedPreferences prefs;
    private Executor executor;
    
    private boolean isConnected = false;
    private boolean isWifiConnected = false;
    private boolean isCellularConnected = false;
    
    private ConnectivityManager.NetworkCallback networkCallback;
    private ConnectivityManager connectivityManager;
    
    public NetworkUtils(Context context) {
        this.context = context.getApplicationContext();
        this.prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.executor = Executors.newSingleThreadExecutor();
        
        // Initialize connectivity manager
        connectivityManager = (ConnectivityManager) 
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        
        // Check initial network state
        updateNetworkStatus();
        
        // Register for network callbacks
        registerNetworkCallback();
    }
    
    /**
     * Register for network connectivity changes
     */
    private void registerNetworkCallback() {
        if (connectivityManager == null) {
            return;
        }
        
        // Unregister any existing callback
        unregisterNetworkCallback();
        
        // Create a new callback
        networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                updateNetworkStatus();
            }
            
            @Override
            public void onLost(@NonNull Network network) {
                updateNetworkStatus();
            }
            
            @Override
            public void onCapabilitiesChanged(@NonNull Network network, 
                                             @NonNull NetworkCapabilities capabilities) {
                updateNetworkStatus();
            }
        };
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback);
        } else {
            NetworkRequest request = new NetworkRequest.Builder()
                    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    .build();
            connectivityManager.registerNetworkCallback(request, networkCallback);
        }
    }
    
    /**
     * Unregister network callback
     */
    private void unregisterNetworkCallback() {
        if (connectivityManager != null && networkCallback != null) {
            try {
                connectivityManager.unregisterNetworkCallback(networkCallback);
            } catch (IllegalArgumentException e) {
                // Callback was not registered
            }
        }
    }
    
    /**
     * Update the current network status flags
     */
    private void updateNetworkStatus() {
        isConnected = false;
        isWifiConnected = false;
        isCellularConnected = false;
        
        if (connectivityManager == null) {
            return;
        }
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network network = connectivityManager.getActiveNetwork();
            if (network != null) {
                NetworkCapabilities capabilities = 
                        connectivityManager.getNetworkCapabilities(network);
                
                if (capabilities != null) {
                    isConnected = capabilities.hasCapability(
                            NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                            capabilities.hasCapability(
                                    NetworkCapabilities.NET_CAPABILITY_VALIDATED);
                    
                    isWifiConnected = capabilities.hasTransport(
                            NetworkCapabilities.TRANSPORT_WIFI) ||
                            capabilities.hasTransport(
                                    NetworkCapabilities.TRANSPORT_ETHERNET);
                    
                    isCellularConnected = capabilities.hasTransport(
                            NetworkCapabilities.TRANSPORT_CELLULAR);
                }
            }
        } else {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnected()) {
                isConnected = true;
                
                int type = activeNetwork.getType();
                isWifiConnected = type == ConnectivityManager.TYPE_WIFI || 
                        type == ConnectivityManager.TYPE_ETHERNET;
                isCellularConnected = type == ConnectivityManager.TYPE_MOBILE;
            }
        }
    }
    
    /**
     * Check if a network connection is available
     * @return true if connected
     */
    public boolean isNetworkAvailable() {
        // If offline mode is enabled, always return false
        if (isOfflineModeEnabled()) {
            return false;
        }
        
        // If wifi-only mode is enabled, only return true if connected to wifi
        if (isWifiOnlyModeEnabled()) {
            return isWifiConnected;
        }
        
        return isConnected;
    }
    
    /**
     * Check if connected to a wifi network
     * @return true if connected to wifi
     */
    public boolean isWifiConnected() {
        return isWifiConnected;
    }
    
    /**
     * Check if connected to a cellular network
     * @return true if connected to cellular
     */
    public boolean isCellularConnected() {
        return isCellularConnected;
    }
    
    /**
     * Check if the device is in offline mode
     * @return true if offline mode is enabled
     */
    public boolean isOfflineModeEnabled() {
        return prefs.getBoolean(KEY_OFFLINE_MODE, false);
    }
    
    /**
     * Enable or disable offline mode
     * @param enabled true to enable offline mode
     */
    public void setOfflineModeEnabled(boolean enabled) {
        prefs.edit().putBoolean(KEY_OFFLINE_MODE, enabled).apply();
    }
    
    /**
     * Check if the app is in wifi-only mode
     * @return true if wifi-only mode is enabled
     */
    public boolean isWifiOnlyModeEnabled() {
        return prefs.getBoolean(KEY_WIFI_ONLY, false);
    }
    
    /**
     * Enable or disable wifi-only mode
     * @param enabled true to enable wifi-only mode
     */
    public void setWifiOnlyModeEnabled(boolean enabled) {
        prefs.edit().putBoolean(KEY_WIFI_ONLY, enabled).apply();
    }
    
    /**
     * Check if a URL is reachable
     * @param url URL to check
     * @param listener Listener for result
     */
    public void isServerReachable(String url, final ServerReachableListener listener) {
        // Only perform check if we have a network connection
        if (!isNetworkAvailable()) {
            if (listener != null) {
                listener.onServerReachableResult(false);
            }
            return;
        }
        
        executor.execute(() -> {
            boolean isReachable = false;
            
            try {
                URL serverUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) serverUrl.openConnection();
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                connection.setRequestMethod("HEAD");
                int responseCode = connection.getResponseCode();
                isReachable = (responseCode == HttpURLConnection.HTTP_OK);
            } catch (IOException e) {
                // Server is not reachable
            }
            
            final boolean finalResult = isReachable;
            
            // Notify on main thread
            if (listener != null) {
                android.os.Handler mainHandler = new android.os.Handler(context.getMainLooper());
                mainHandler.post(() -> listener.onServerReachableResult(finalResult));
            }
        });
    }
    
    /**
     * Interface for server reachability check callbacks
     */
    public interface ServerReachableListener {
        void onServerReachableResult(boolean isReachable);
    }
    
    /**
     * Release resources
     */
    public void release() {
        unregisterNetworkCallback();
    }
}