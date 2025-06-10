package com.bhashasetu.app.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.bhashasetu.app.R;
import com.bhashasetu.app.ui.OfflineStatusView;
import com.google.android.material.snackbar.Snackbar;

/**
 * Handler for internet connectivity alerts and automatic offline mode switching.
 * Listens to connectivity changes and shows appropriate UI alerts.
 */
public class InternetConnectivityHandler {

    private final Context context;
    private final CoordinatorLayout coordinatorLayout;
    private final View.OnClickListener offlineActionListener;
    private final View.OnClickListener settingsActionListener;
    private Snackbar currentSnackbar;
    private OfflineStatusView offlineStatusView;
    private boolean enabled = true;

    public InternetConnectivityHandler(@NonNull Context context, 
                                    @NonNull CoordinatorLayout coordinatorLayout) {
        this.context = context;
        this.coordinatorLayout = coordinatorLayout;
        
        // Default action listeners
        this.offlineActionListener = v -> {
            // Set the app to offline mode
            // Call your offline mode manager here
            if (currentSnackbar != null) {
                currentSnackbar.dismiss();
            }
        };
        
        this.settingsActionListener = v -> {
            // Open network settings
            Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
            if (context instanceof Activity) {
                ((Activity) context).startActivity(intent);
            } else {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
            
            if (currentSnackbar != null) {
                currentSnackbar.dismiss();
            }
        };
    }

    /**
     * Initialize with lifecycle owner to observe network changes.
     *
     * @param lifecycleOwner The lifecycle owner (usually activity or fragment)
     */
    public void initialize(@NonNull LifecycleOwner lifecycleOwner) {
        if (!enabled) return;
        
        // Observe network connectivity changes
        NetworkConnectionHandler connectionHandler = NetworkConnectionHandler.getInstance(context);
        connectionHandler.getConnectionStatus().observe(lifecycleOwner, connectionStatus -> {
            if (!connectionStatus.isConnected()) {
                showNoConnectionAlert();
            } else {
                dismissCurrentAlert();
                
                // Update offline status view if available
                if (offlineStatusView != null) {
                    offlineStatusView.updateNetworkStatus(connectionStatus);
                }
            }
        });
    }

    /**
     * Set offline status view to update when connectivity changes.
     *
     * @param offlineStatusView The offline status view
     */
    public void setOfflineStatusView(@NonNull OfflineStatusView offlineStatusView) {
        this.offlineStatusView = offlineStatusView;
    }

    /**
     * Set custom action listeners.
     *
     * @param offlineActionListener  Listener for offline mode action
     * @param settingsActionListener Listener for settings action
     */
    public void setActionListeners(@NonNull View.OnClickListener offlineActionListener,
                                 @NonNull View.OnClickListener settingsActionListener) {
        this.offlineActionListener = offlineActionListener;
        this.settingsActionListener = settingsActionListener;
    }

    /**
     * Enable or disable the handler.
     *
     * @param enabled True to enable, false to disable
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (!enabled && currentSnackbar != null) {
            currentSnackbar.dismiss();
            currentSnackbar = null;
        }
    }

    /**
     * Check if the handler is enabled.
     *
     * @return True if enabled, false otherwise
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Show a no connection alert.
     */
    public void showNoConnectionAlert() {
        if (!enabled) return;
        
        // Dismiss any existing alert
        dismissCurrentAlert();
        
        // Inflate custom layout
        View customView = LayoutInflater.from(context).inflate(
                R.layout.alert_no_connection, null);
        
        TextView messageView = customView.findViewById(R.id.no_connection_message);
        Button offlineButton = customView.findViewById(R.id.offline_button);
        Button settingsButton = customView.findViewById(R.id.settings_button);
        
        offlineButton.setOnClickListener(offlineActionListener);
        settingsButton.setOnClickListener(settingsActionListener);
        
        // Create and show Snackbar
        currentSnackbar = Snackbar.make(coordinatorLayout, "", Snackbar.LENGTH_INDEFINITE);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) currentSnackbar.getView();
        
        // Clear default TextViews
        for (int i = 0; i < snackbarLayout.getChildCount(); i++) {
            View child = snackbarLayout.getChildAt(i);
            if (child instanceof TextView) {
                child.setVisibility(View.GONE);
            }
        }
        
        // Add custom view
        snackbarLayout.setPadding(0, 0, 0, 0);
        snackbarLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.offline_status));
        snackbarLayout.addView(customView, 0);
        
        currentSnackbar.show();
    }

    /**
     * Show a metered connection alert.
     */
    public void showMeteredConnectionAlert() {
        if (!enabled) return;
        
        // Dismiss any existing alert
        dismissCurrentAlert();
        
        // Create and show Snackbar
        currentSnackbar = Snackbar.make(coordinatorLayout, 
                R.string.metered_connection_message, Snackbar.LENGTH_LONG)
                .setAction(R.string.disable_data_usage, v -> {
                    // Open data usage settings
                    Intent intent = new Intent(Settings.ACTION_DATA_USAGE_SETTINGS);
                    if (context instanceof Activity) {
                        ((Activity) context).startActivity(intent);
                    } else {
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });
        
        currentSnackbar.show();
    }

    /**
     * Dismiss the current alert if it exists.
     */
    public void dismissCurrentAlert() {
        if (currentSnackbar != null && currentSnackbar.isShown()) {
            currentSnackbar.dismiss();
            currentSnackbar = null;
        }
    }
}