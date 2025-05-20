package com.example.englishhindi.util;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Enhanced utility class for network operations and connectivity checking.
 * Provides methods to monitor network status, verify server availability,
 * and handle network transitions gracefully.
 */
public class NetworkUtils {
    
    private static final String TAG = "NetworkUtils";
    private static final String PREF_NAME = "network_prefs";
    private static final String KEY_OFFLINE_MODE = "offline_mode";
    private static final String KEY_WIFI_ONLY = "wifi_only";
    private static final String KEY_METERED_ALLOWED = "metered_allowed";
    private static final String KEY_AUTO_OFFLINE = "auto_offline";
    private static final String KEY_LAST_SERVER_CHECK = "last_server_check";
    private static final String KEY_SERVER_AVAILABLE = "server_available";
    private static final String KEY_CONNECTION_QUALITY = "connection_quality";
    
    // Constants for connection quality
    public static final int CONNECTION_QUALITY_UNKNOWN = 0;
    public static final int CONNECTION_QUALITY_POOR = 1;
    public static final int CONNECTION_QUALITY_MODERATE = 2;
    public static final int CONNECTION_QUALITY_GOOD = 3;
    public static final int CONNECTION_QUALITY_EXCELLENT = 4;
    
    // Server check interval (6 hours)
    private static final long SERVER_CHECK_INTERVAL = 6 * 60 * 60 * 1000;
    
    private Context context;
    private SharedPreferences prefs;
    private Executor executor;
    private Handler mainHandler;
    
    private boolean isConnected = false;
    private boolean isWifiConnected = false;
    private boolean isCellularConnected = false;
    private boolean isMeteredConnection = true;
    private int connectionQuality = CONNECTION_QUALITY_UNKNOWN;
    
    private ConnectivityManager.NetworkCallback networkCallback;
    private ConnectivityManager connectivityManager;
    
    private List<NetworkStateListener> networkStateListeners = new ArrayList<>();
    private List<String> serverEndpoints = new ArrayList<>();
    
    private NetworkUtils(Context context) {
        this.context = context.getApplicationContext();
        this.prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.executor = Executors.newSingleThreadExecutor();
        this.mainHandler = new Handler(Looper.getMainLooper());
        
        // Initialize connectivity manager
        connectivityManager = (ConnectivityManager) 
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        
        // Check initial network state
        updateNetworkStatus();
        
        // Register for network callbacks
        registerNetworkCallback();
        
        // Set up default server endpoints
        setupDefaultServerEndpoints();
    }
    
    // Singleton instance
    private static NetworkUtils instance;
    
    /**
     * Get the singleton instance of NetworkUtils
     * @param context Application context
     * @return NetworkUtils instance
     */
    public static synchronized NetworkUtils getInstance(Context context) {
        if (instance == null) {
            instance = new NetworkUtils(context);
        }
        return instance;
    }
    
    /**
     * Set up default server endpoints for availability checking
     */
    private void setupDefaultServerEndpoints() {
        // Add common endpoints that the app might use
        // In real app, these should be the actual API endpoints
        serverEndpoints.add("https://api.example.com/status");
        serverEndpoints.add("https://cdn.example.com/ping");
    }
    
    /**
     * Add a server endpoint to check for availability
     * @param endpoint Server endpoint URL
     */
    public void addServerEndpoint(String endpoint) {
        if (!serverEndpoints.contains(endpoint)) {
            serverEndpoints.add(endpoint);
        }
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
                notifyNetworkStateChanged();
            }
            
            @Override
            public void onLost(@NonNull Network network) {
                updateNetworkStatus();
                notifyNetworkStateChanged();
                
                // If auto-offline is enabled and we lost connection,
                // switch to offline mode automatically
                if (isAutoOfflineModeEnabled() && !isConnected) {
                    setOfflineModeEnabled(true);
                    Log.d(TAG, "Network lost, automatically switched to offline mode");
                }
            }
            
            @Override
            public void onCapabilitiesChanged(@NonNull Network network, 
                                             @NonNull NetworkCapabilities capabilities) {
                boolean prevWifiState = isWifiConnected;
                boolean prevMeteredState = isMeteredConnection;
                
                updateNetworkStatus();
                
                // If wifi state changed, notify listeners
                if (prevWifiState != isWifiConnected || prevMeteredState != isMeteredConnection) {
                    notifyNetworkStateChanged();
                }
                
                // Check connection quality
                assessConnectionQuality(capabilities);
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
     * Assess the quality of the current network connection
     * @param capabilities Network capabilities
     */
    private void assessConnectionQuality(NetworkCapabilities capabilities) {
        if (capabilities == null) {
            connectionQuality = CONNECTION_QUALITY_UNKNOWN;
            return;
        }
        
        // Start with unknown quality
        int quality = CONNECTION_QUALITY_UNKNOWN;
        
        // Check link speed/bandwidth capability
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // On Android 10+, we can check the downstream bandwidth
            int bandwidth = capabilities.getLinkDownstreamBandwidthKbps();
            
            if (bandwidth >= 10000) { // 10 Mbps
                quality = CONNECTION_QUALITY_EXCELLENT;
            } else if (bandwidth >= 5000) { // 5 Mbps
                quality = CONNECTION_QUALITY_GOOD;
            } else if (bandwidth >= 1000) { // 1 Mbps
                quality = CONNECTION_QUALITY_MODERATE;
            } else if (bandwidth > 0) {
                quality = CONNECTION_QUALITY_POOR;
            }
        } else {
            // On older Android versions, make educated guesses based on transport type
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                // WiFi or Ethernet is usually good
                quality = CONNECTION_QUALITY_GOOD;
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                // Cellular could be anything from poor to excellent
                // Default to moderate without more info
                quality = CONNECTION_QUALITY_MODERATE;
            }
        }
        
        // If quality changed, save it and notify listeners
        if (quality != connectionQuality) {
            connectionQuality = quality;
            prefs.edit().putInt(KEY_CONNECTION_QUALITY, connectionQuality).apply();
            notifyNetworkStateChanged();
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
        isMeteredConnection = true;
        
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
                    
                    // Check if the connection is metered
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        isMeteredConnection = !capabilities.hasCapability(
                                NetworkCapabilities.NET_CAPABILITY_NOT_METERED);
                    } else {
                        isMeteredConnection = connectivityManager.isActiveNetworkMetered();
                    }
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
                isMeteredConnection = connectivityManager.isActiveNetworkMetered();
            }
        }
    }
    
    /**
     * Add a listener for network state changes
     * @param listener Listener to add
     */
    public void addNetworkStateListener(NetworkStateListener listener) {
        if (!networkStateListeners.contains(listener)) {
            networkStateListeners.add(listener);
        }
    }
    
    /**
     * Remove a network state listener
     * @param listener Listener to remove
     */
    public void removeNetworkStateListener(NetworkStateListener listener) {
        networkStateListeners.remove(listener);
    }
    
    /**
     * Notify all listeners about network state changes
     */
    private void notifyNetworkStateChanged() {
        mainHandler.post(() -> {
            for (NetworkStateListener listener : networkStateListeners) {
                try {
                    listener.onNetworkStateChanged(
                            isConnected, isWifiConnected, isMeteredConnection, connectionQuality);
                } catch (Exception e) {
                    Log.e(TAG, "Error notifying network state listener", e);
                }
            }
        });
    }
    
    /**
     * Check if a network connection is available based on current settings
     * @return true if connected and allowed by settings
     */
    public boolean isNetworkAvailable() {
        // If offline mode is enabled, always return false
        if (isOfflineModeEnabled()) {
            return false;
        }
        
        // Not connected at all
        if (!isConnected) {
            return false;
        }
        
        // If wifi-only mode is enabled, only return true if connected to wifi
        if (isWifiOnlyModeEnabled() && !isWifiConnected) {
            return false;
        }
        
        // If metered connections aren't allowed, check that
        if (!isMeteredConnectionsAllowed() && isMeteredConnection) {
            return false;
        }
        
        return true;
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
     * Check if the current connection is metered
     * @return true if the connection is metered
     */
    public boolean isMeteredConnection() {
        return isMeteredConnection;
    }
    
    /**
     * Get the current connection quality
     * @return Connection quality value
     */
    public int getConnectionQuality() {
        return connectionQuality;
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
        notifyNetworkStateChanged();
    }
    
    /**
     * Toggle offline mode
     * @return New offline mode state
     */
    public boolean toggleOfflineMode() {
        boolean newState = !isOfflineModeEnabled();
        setOfflineModeEnabled(newState);
        return newState;
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
        notifyNetworkStateChanged();
    }
    
    /**
     * Check if metered connections are allowed
     * @return true if metered connections are allowed
     */
    public boolean isMeteredConnectionsAllowed() {
        return prefs.getBoolean(KEY_METERED_ALLOWED, true);
    }
    
    /**
     * Enable or disable metered connections
     * @param allowed true to allow metered connections
     */
    public void setMeteredConnectionsAllowed(boolean allowed) {
        prefs.edit().putBoolean(KEY_METERED_ALLOWED, allowed).apply();
        notifyNetworkStateChanged();
    }
    
    /**
     * Check if auto-offline mode is enabled
     * @return true if auto-offline is enabled
     */
    public boolean isAutoOfflineModeEnabled() {
        return prefs.getBoolean(KEY_AUTO_OFFLINE, true);
    }
    
    /**
     * Enable or disable auto-offline mode
     * @param enabled true to enable auto-offline
     */
    public void setAutoOfflineModeEnabled(boolean enabled) {
        prefs.edit().putBoolean(KEY_AUTO_OFFLINE, enabled).apply();
    }
    
    /**
     * Check if the server is available
     * @param timeoutMs Connection timeout in milliseconds
     * @param listener Listener for the result
     */
    public void checkServerAvailability(final int timeoutMs, final ServerReachableListener listener) {
        // Check cache first if the check was performed recently
        long lastCheck = prefs.getLong(KEY_LAST_SERVER_CHECK, 0);
        if (System.currentTimeMillis() - lastCheck < TimeUnit.MINUTES.toMillis(5)) {
            boolean cachedResult = prefs.getBoolean(KEY_SERVER_AVAILABLE, false);
            if (listener != null) {
                listener.onServerReachableResult(cachedResult);
            }
            return;
        }
        
        // Only perform check if we have a network connection
        if (!isNetworkAvailable()) {
            if (listener != null) {
                listener.onServerReachableResult(false);
            }
            saveServerStatus(false);
            return;
        }
        
        executor.execute(() -> {
            // Try to reach each endpoint until one succeeds
            boolean isAnyReachable = false;
            
            for (String endpoint : serverEndpoints) {
                try {
                    URL serverUrl = new URL(endpoint);
                    HttpURLConnection connection = (HttpURLConnection) serverUrl.openConnection();
                    connection.setConnectTimeout(timeoutMs);
                    connection.setReadTimeout(timeoutMs);
                    connection.setRequestMethod("HEAD");
                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        isAnyReachable = true;
                        break;
                    }
                } catch (IOException e) {
                    // Server is not reachable, try next
                }
            }
            
            // Save result
            saveServerStatus(isAnyReachable);
            
            // Notify on main thread
            if (listener != null) {
                final boolean finalResult = isAnyReachable;
                mainHandler.post(() -> listener.onServerReachableResult(finalResult));
            }
        });
    }
    
    /**
     * Save server availability status
     * @param available Whether the server is available
     */
    private void saveServerStatus(boolean available) {
        prefs.edit()
                .putLong(KEY_LAST_SERVER_CHECK, System.currentTimeMillis())
                .putBoolean(KEY_SERVER_AVAILABLE, available)
                .apply();
    }
    
    /**
     * Check if the battery is low
     * @return true if battery is below 15%
     */
    public boolean isBatteryLow() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, filter);
        
        if (batteryStatus == null) {
            return false;
        }
        
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        
        float batteryPercent = level * 100 / (float) scale;
        return batteryPercent < 15;
    }
    
    /**
     * Check if the device is charging
     * @return true if charging
     */
    public boolean isCharging() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, filter);
        
        if (batteryStatus == null) {
            return false;
        }
        
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        return status == BatteryManager.BATTERY_STATUS_CHARGING || 
               status == BatteryManager.BATTERY_STATUS_FULL;
    }
    
    /**
     * Get a description of the current network state
     * @return Human-readable network state
     */
    public String getNetworkStateDescription() {
        if (isOfflineModeEnabled()) {
            return "Offline Mode";
        }
        
        if (!isConnected) {
            return "No Connection";
        }
        
        StringBuilder description = new StringBuilder();
        
        if (isWifiConnected) {
            description.append("WiFi");
        } else if (isCellularConnected) {
            description.append("Cellular");
        } else {
            description.append("Connected");
        }
        
        // Add quality indicator
        switch (connectionQuality) {
            case CONNECTION_QUALITY_POOR:
                description.append(" (Poor)");
                break;
            case CONNECTION_QUALITY_MODERATE:
                description.append(" (Moderate)");
                break;
            case CONNECTION_QUALITY_GOOD:
                description.append(" (Good)");
                break;
            case CONNECTION_QUALITY_EXCELLENT:
                description.append(" (Excellent)");
                break;
        }
        
        return description.toString();
    }
    
    /**
     * Release resources
     */
    public void release() {
        unregisterNetworkCallback();
        networkStateListeners.clear();
    }
    
    /**
     * Interface for server reachability check callbacks
     */
    public interface ServerReachableListener {
        void onServerReachableResult(boolean isReachable);
    }
    
    /**
     * Interface for network state change listeners
     */
    public interface NetworkStateListener {
        void onNetworkStateChanged(boolean isConnected, boolean isWifi, 
                                    boolean isMetered, int connectionQuality);
    }
}