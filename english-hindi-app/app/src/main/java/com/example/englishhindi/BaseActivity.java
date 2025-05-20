package com.example.englishhindi;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.englishhindi.di.AppDependencies;
import com.example.englishhindi.ui.OfflineStatusView;
import com.example.englishhindi.util.NetworkUtils;
import com.google.android.material.snackbar.Snackbar;

/**
 * Base activity class that all activities should extend.
 * Provides common functionality like offline state handling,
 * error reporting, and dependency access.
 * 
 * Major improvements over GitHub version:
 * 1. Network state monitoring and offline mode handling
 * 2. Standardized error display with multiple options
 * 3. Empty state management for consistent UI
 * 4. Integration with dependency injection pattern
 * 5. Simplified navigation between activities
 */
public abstract class BaseActivity extends AppCompatActivity {
    
    // Access to app-wide dependencies through DI pattern
    protected AppDependencies appDependencies;
    // Network utilities for connectivity monitoring
    protected NetworkUtils networkUtils;
    
    // UI component that shows offline status to the user
    private OfflineStatusView offlineStatusView;
    // Flag to prevent showing repeated offline warnings
    private boolean offlineWarningShown = false;
    
    /**
     * Initialize activity and core dependencies
     * This is called early in the activity lifecycle
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Get dependencies from application using DI pattern
        // This is a cleaner approach than using static singletons
        appDependencies = EnglishHindiApplication.from(this).getDependencies();
        networkUtils = appDependencies.getNetworkUtils();
        
        // Register for network state changes
        // Uses method reference for cleaner implementation
        networkUtils.addNetworkStateListener(this::onNetworkStateChanged);
    }
    
    /**
     * Called after onCreate when activity start-up is complete
     * Used to initialize views that depend on the content layout being set
     */
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        
        // Find offline status view if present in the layout
        // Not all layouts will include this view
        offlineStatusView = findViewById(R.id.offline_status_view);
        
        // Check initial network state to show warnings if needed
        checkInitialNetworkState();
    }
    
    /**
     * Clean up resources when activity is destroyed
     * Prevents memory leaks by removing listeners
     */
    @Override
    protected void onDestroy() {
        // Remove network state listener to prevent memory leaks
        networkUtils.removeNetworkStateListener(this::onNetworkStateChanged);
        
        super.onDestroy();
    }
    
    /**
     * Check initial network state and show warning if offline
     * Called during activity initialization
     */
    private void checkInitialNetworkState() {
        if (!networkUtils.isNetworkAvailable() && !offlineWarningShown) {
            showOfflineWarning();
            offlineWarningShown = true;
        }
    }
    
    /**
     * Handle network state changes with detailed connectivity information
     * Called by NetworkUtils when connectivity changes
     * 
     * @param isConnected Whether connected to any network
     * @param isWifi Whether connected to WiFi network
     * @param isMetered Whether on metered connection (important for data usage)
     * @param connectionQuality Quality of connection (higher is better)
     */
    protected void onNetworkStateChanged(boolean isConnected, boolean isWifi, 
                                        boolean isMetered, int connectionQuality) {
        // Update offline status view if present
        if (offlineStatusView != null) {
            offlineStatusView.updateStatus();
        }
        
        // Show offline warning if just went offline
        if (!isConnected && !offlineWarningShown) {
            showOfflineWarning();
            offlineWarningShown = true;
        } else if (isConnected) {
            // Reset warning flag if connected
            // This allows warning to show again if connection drops later
            offlineWarningShown = false;
        }
        
        // Call simpler version for subclasses that don't need detailed info
        onNetworkStateChanged();
    }
    
    /**
     * Simplified network state changed callback for subclasses
     * Subclasses can override this without needing to handle all parameters
     * from the detailed version
     */
    protected void onNetworkStateChanged() {
        // Intentionally empty - subclasses should override as needed
        // This allows activities to react to connectivity changes
    }
    
    /**
     * Show offline warning message with action to disable offline mode
     * Uses non-intrusive Snackbar to inform user
     */
    protected void showOfflineWarning() {
        View rootView = findViewById(android.R.id.content);
        if (rootView != null) {
            Snackbar snackbar = Snackbar.make(
                    rootView,
                    R.string.offline_mode_active,
                    Snackbar.LENGTH_LONG);
            
            // Add action button to disable offline mode
            snackbar.setAction(R.string.disable, v -> {
                // Disable offline mode when user clicks
                networkUtils.setOfflineModeEnabled(false);
                
                // Update offline status view if present
                if (offlineStatusView != null) {
                    offlineStatusView.updateStatus();
                }
                
                // Confirm to user that offline mode is disabled
                Toast.makeText(this, R.string.offline_mode_disabled, Toast.LENGTH_SHORT).show();
            });
            
            snackbar.show();
        }
    }
    
    /**
     * Show error message using Snackbar (or Toast as fallback)
     * Provides consistent error display throughout the app
     * 
     * @param errorMessage Error message to display
     */
    protected void showError(String errorMessage) {
        View rootView = findViewById(android.R.id.content);
        if (rootView != null) {
            // Prefer Snackbar for non-intrusive error message
            Snackbar.make(rootView, errorMessage, Snackbar.LENGTH_LONG).show();
        } else {
            // Fallback to Toast if view not available
            // This should rarely happen but provides a safety net
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }
    
    /**
     * Show error message from string resource
     * Convenience method for resource-based error messages
     * 
     * @param errorResId Error message resource ID
     */
    protected void showError(int errorResId) {
        showError(getString(errorResId));
    }
    
    /**
     * Set empty state view visibility
     * Provides consistent empty state handling across the app
     * 
     * @param emptyView Empty state view to show/hide
     * @param isEmpty Whether the data is empty
     */
    protected void setEmptyState(View emptyView, boolean isEmpty) {
        if (emptyView != null) {
            emptyView.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
        }
    }
    
    /**
     * Set empty state view visibility with custom message
     * Enhanced version with custom message support
     * 
     * @param emptyView Empty state view to show/hide
     * @param isEmpty Whether the data is empty
     * @param emptyText TextView to display custom message
     * @param message Custom message to display
     */
    protected void setEmptyState(View emptyView, boolean isEmpty, TextView emptyText, String message) {
        if (emptyView != null) {
            emptyView.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
            
            // Set custom message if view is visible and text view exists
            if (isEmpty && emptyText != null) {
                emptyText.setText(message);
            }
        }
    }
    
    /**
     * Navigate to another activity
     * Standardizes activity navigation across the app
     * 
     * @param activityClass Activity class to navigate to
     */
    protected void navigateTo(Class<?> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }
    
    /**
     * Navigate to another activity with extras
     * Enhanced version with support for passing data
     * 
     * @param activityClass Activity class to navigate to
     * @param extras Bundle of extras to pass to the activity
     */
    protected void navigateTo(Class<?> activityClass, Bundle extras) {
        Intent intent = new Intent(this, activityClass);
        if (extras != null) {
            intent.putExtras(extras);
        }
        startActivity(intent);
    }
    
    /**
     * Handle menu item selections
     * Particularly handles the up/back button in action bar
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Handle up button by treating it as back press
            // This provides consistent back navigation behavior
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}