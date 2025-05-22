package com.example.englishhindi.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * Handler for monitoring network connectivity changes and providing real-time status updates.
 * Uses a callback-based approach to efficiently track connectivity changes.
 */
public class NetworkConnectionHandler {

    public enum ConnectionType {
        NONE,       // No connection
        WIFI,       // WiFi connection
        CELLULAR,   // Mobile data connection
        OTHER       // Other connection types (Ethernet, VPN, etc.)
    }

    public static class ConnectionStatus {
        private final boolean isConnected;
        private final ConnectionType connectionType;
        private final boolean isMetered;

        public ConnectionStatus(boolean isConnected, ConnectionType connectionType, boolean isMetered) {
            this.isConnected = isConnected;
            this.connectionType = connectionType;
            this.isMetered = isMetered;
        }

        public boolean isConnected() {
            return isConnected;
        }

        public ConnectionType getConnectionType() {
            return connectionType;
        }

        public boolean isMetered() {
            return isMetered;
        }
    }

    private static final Object LOCK = new Object();
    private static NetworkConnectionHandler instance;

    private final ConnectivityManager connectivityManager;
    private final MutableLiveData<ConnectionStatus> connectionStatusLiveData;
    private final Handler mainHandler;

    // Default connection status - assume no connection initially until first callback
    private ConnectionStatus currentStatus = new ConnectionStatus(false, ConnectionType.NONE, false);

    private final ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
        @Override
        public void onAvailable(@NonNull Network network) {
            updateConnectionStatus();
        }

        @Override
        public void onLost(@NonNull Network network) {
            updateConnectionStatus();
        }

        @Override
        public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities capabilities) {
            updateConnectionStatus();
        }
    };

    private NetworkConnectionHandler(Context context) {
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        connectionStatusLiveData = new MutableLiveData<>();
        mainHandler = new Handler(Looper.getMainLooper());
        
        // Initialize with current status
        updateConnectionStatus();
        
        // Register for network callbacks
        NetworkRequest networkRequest = new NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build();
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback);
    }

    public static NetworkConnectionHandler getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = new NetworkConnectionHandler(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    /**
     * Get LiveData object for observing connection status changes.
     * @return LiveData with current connection status
     */
    public LiveData<ConnectionStatus> getConnectionStatus() {
        return connectionStatusLiveData;
    }

    /**
     * Get current connection status immediately.
     * @return Current connection status
     */
    public ConnectionStatus getCurrentStatus() {
        return currentStatus;
    }

    /**
     * Check if the device is currently connected to any network.
     * @return true if connected, false otherwise
     */
    public boolean isConnected() {
        return currentStatus.isConnected;
    }

    /**
     * Clean up resources when no longer needed.
     */
    public void cleanup() {
        connectivityManager.unregisterNetworkCallback(networkCallback);
    }

    /**
     * Update the connection status based on current network state.
     */
    private void updateConnectionStatus() {
        // Determine if we're connected to the internet
        boolean isConnected = false;
        boolean isMetered = false;
        ConnectionType connectionType = ConnectionType.NONE;

        if (connectivityManager != null) {
            NetworkCapabilities capabilities = getNetworkCapabilities();
            
            if (capabilities != null) {
                // Check internet connectivity
                isConnected = capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                              capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
                
                // Check if connection is metered
                isMetered = !capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED);
                
                // Determine connection type
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    connectionType = ConnectionType.WIFI;
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    connectionType = ConnectionType.CELLULAR;
                } else if (isConnected) {
                    connectionType = ConnectionType.OTHER;
                }
            }
        }

        // Create new status
        final ConnectionStatus newStatus = new ConnectionStatus(isConnected, connectionType, isMetered);
        
        // Only post value if it's changed from current status
        if (!newStatus.isConnected() != currentStatus.isConnected() || 
            newStatus.getConnectionType() != currentStatus.connectionType ||
            newStatus.isMetered() != currentStatus.isMetered()) {
            
            currentStatus = newStatus;
            
            // Post value on main thread
            mainHandler.post(() -> connectionStatusLiveData.setValue(newStatus));
        }
    }

    /**
     * Get current network capabilities.
     * @return NetworkCapabilities object or null if not available
     */
    private NetworkCapabilities getNetworkCapabilities() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network network = connectivityManager.getActiveNetwork();
            if (network != null) {
                return connectivityManager.getNetworkCapabilities(network);
            }
        } else {
            // Legacy handling for older devices
            android.net.NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                return connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            }
        }
        return null;
    }
}