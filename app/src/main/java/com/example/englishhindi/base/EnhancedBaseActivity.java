package com.bhashasetu.app.base;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.bhashasetu.app.R;
import com.bhashasetu.app.receiver.NetworkStateReceiver;
import com.bhashasetu.app.ui.LoadingStateView;
import com.bhashasetu.app.ui.OfflineStatusView;
import com.bhashasetu.app.util.ActivityTransitionUtil;
import com.bhashasetu.app.util.ErrorHandler;
import com.bhashasetu.app.util.NetworkConnectionHandler;

/**
 * Enhanced base activity that handles common functionality across all activities.
 * Includes loading states, error handling, network status monitoring, and transitions.
 */
public abstract class EnhancedBaseActivity extends AppCompatActivity {

    protected LoadingStateView loadingStateView;
    protected OfflineStatusView offlineStatusView;
    private NetworkStateReceiver networkStateReceiver;
    private boolean networkReceiverRegistered = false;
    private boolean showNetworkStatusChanges = true;
    private NetworkConnectionHandler.ConnectionStatus lastConnectionStatus;
    private Observer<NetworkConnectionHandler.ConnectionStatus> connectionObserver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Initialize NetworkConnectionHandler
        lastConnectionStatus = NetworkConnectionHandler.getInstance(this).getCurrentStatus();
        
        // Set up connection status observer
        connectionObserver = connectionStatus -> {
            if (lastConnectionStatus != null && 
                    connectionStatus.isConnected() != lastConnectionStatus.isConnected()) {
                
                if (connectionStatus.isConnected()) {
                    onNetworkConnected();
                } else {
                    onNetworkDisconnected();
                }
            }
            
            lastConnectionStatus = connectionStatus;
            
            // Update UI if needed
            if (offlineStatusView != null) {
                offlineStatusView.updateNetworkStatus(connectionStatus);
            }
        };
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        setupViews();
    }

    @Override
    public void setContentView(android.view.View view) {
        super.setContentView(view);
        setupViews();
    }

    @Override
    public void setContentView(android.view.View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        setupViews();
    }

    /**
     * Setup common views like loading state and offline status views.
     */
    private void setupViews() {
        // Find loading state view if it exists
        loadingStateView = findViewById(R.id.loading_state_view);
        
        // Find offline status view if it exists
        offlineStatusView = findViewById(R.id.offline_status_view);
        
        // Initialize offline status view if it exists
        if (offlineStatusView != null) {
            NetworkConnectionHandler connectionHandler = NetworkConnectionHandler.getInstance(this);
            offlineStatusView.updateNetworkStatus(connectionHandler.getCurrentStatus());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        
        // Register network receiver
        if (showNetworkStatusChanges && !networkReceiverRegistered) {
            networkStateReceiver = new NetworkStateReceiver();
            IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(networkStateReceiver, filter);
            networkReceiverRegistered = true;
        }
        
        // Observe connection status changes
        NetworkConnectionHandler.getInstance(this)
                .getConnectionStatus()
                .observe(this, connectionObserver);
    }

    @Override
    protected void onStop() {
        super.onStop();
        
        // Unregister network receiver
        if (networkReceiverRegistered) {
            unregisterReceiver(networkStateReceiver);
            networkReceiverRegistered = false;
        }
    }

    /**
     * Called when network is connected.
     * Override to handle specific behavior.
     */
    protected void onNetworkConnected() {
        if (showNetworkStatusChanges) {
            Toast.makeText(this, R.string.message_network_connected, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Called when network is disconnected.
     * Override to handle specific behavior.
     */
    protected void onNetworkDisconnected() {
        if (showNetworkStatusChanges) {
            Toast.makeText(this, R.string.message_network_disconnected, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Show a loading state with a custom message.
     *
     * @param message Custom loading message
     */
    protected void showLoading(String message) {
        if (loadingStateView != null) {
            loadingStateView.setLoadingMessage(message);
            loadingStateView.setState(LoadingStateView.State.LOADING);
        }
    }

    /**
     * Show a loading state with the default message.
     */
    protected void showLoading() {
        if (loadingStateView != null) {
            loadingStateView.setState(LoadingStateView.State.LOADING);
        }
    }

    /**
     * Show content (hide loading state).
     */
    protected void showContent() {
        if (loadingStateView != null) {
            loadingStateView.setState(LoadingStateView.State.CONTENT);
        }
    }

    /**
     * Show an error state with a custom message and retry action.
     *
     * @param message     Error message
     * @param retryAction Action to perform on retry
     */
    protected void showError(String message, Runnable retryAction) {
        if (loadingStateView != null) {
            loadingStateView.setErrorMessage(message);
            loadingStateView.setOnRetryClickListener(() -> retryAction.run());
            loadingStateView.setState(LoadingStateView.State.ERROR);
        }
    }

    /**
     * Show an error state for an exception with retry action.
     *
     * @param throwable   The exception
     * @param retryAction Action to perform on retry
     */
    protected void showError(Throwable throwable, Runnable retryAction) {
        String errorMessage = ErrorHandler.getErrorMessage(this, throwable);
        showError(errorMessage, retryAction);
    }

    /**
     * Show an empty state with a custom message.
     *
     * @param message Empty state message
     */
    protected void showEmpty(String message) {
        if (loadingStateView != null) {
            loadingStateView.setEmptyMessage(message);
            loadingStateView.setState(LoadingStateView.State.EMPTY);
        }
    }

    /**
     * Set whether to show network status change messages.
     *
     * @param show True to show network status changes
     */
    protected void setShowNetworkStatusChanges(boolean show) {
        this.showNetworkStatusChanges = show;
    }

    /**
     * Finish activity with a specific transition animation.
     *
     * @param transitionType Transition animation type
     */
    protected void finishWithTransition(ActivityTransitionUtil.TransitionType transitionType) {
        ActivityTransitionUtil.finishActivity(this, transitionType);
    }
}