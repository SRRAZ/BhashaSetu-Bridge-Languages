package com.bhashasetu.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.bhashasetu.app.repository.OfflineQueueHelper;
import com.bhashasetu.app.util.AppExecutors;
import com.bhashasetu.app.util.NetworkUtils;

/**
 * Broadcast receiver for handling network state changes.
 * Coordinates with NetworkUtils to notify app components and
 * triggers processing of offline operations when network becomes available.
 */
public class NetworkStateReceiver extends BroadcastReceiver {
    private static final String TAG = "NetworkStateReceiver";
    
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() == null) {
            return;
        }
        
        if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
            final NetworkUtils networkUtils = NetworkUtils.getInstance(context);
            final boolean isConnected = networkUtils.isNetworkAvailable();
            
            Log.d(TAG, "Network state changed, connected: " + isConnected);
            
            // Process offline operations if network is available
            if (isConnected) {
                AppExecutors.getInstance().backgroundTask().execute(() -> {
                    try {
                        // Get the offline queue helper and process any pending operations
                        OfflineQueueHelper queueHelper = OfflineQueueHelper.getInstance(context);
                        queueHelper.processAllQueues();
                        
                        Log.d(TAG, "Triggered offline queue processing");
                    } catch (Exception e) {
                        Log.e(TAG, "Error processing offline queue", e);
                    }
                });
            }
        } else if (intent.getAction().equals("com.bhashasetu.app.PROCESS_OFFLINE_QUEUE")) {
            // Handle explicit request to process offline queue (e.g., from user action)
            AppExecutors.getInstance().backgroundTask().execute(() -> {
                try {
                    OfflineQueueHelper queueHelper = OfflineQueueHelper.getInstance(context);
                    queueHelper.processAllQueues();
                    
                    Log.d(TAG, "Processed offline queue by explicit request");
                } catch (Exception e) {
                    Log.e(TAG, "Error processing offline queue", e);
                }
            });
        }
    }
}