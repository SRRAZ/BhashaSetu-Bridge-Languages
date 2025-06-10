package com.bhashasetu.app;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bhashasetu.app.cache.CacheManager;
import com.bhashasetu.app.database.RepositoryFactory;
import com.bhashasetu.app.manager.SyncManager;
import com.bhashasetu.app.service.SyncService;
import com.bhashasetu.app.ui.OfflineStatusView;
import com.bhashasetu.app.util.FileUtils;
import com.bhashasetu.app.util.NetworkUtils;

/**
 * Activity for managing offline mode settings and cache management.
 * Allows users to configure sync settings, cache limits, and offline behavior.
 */
public class OfflineSettingsActivity extends AppCompatActivity {
    
    private OfflineStatusView offlineStatusView;
    private Switch switchOfflineMode;
    private Switch switchWifiOnly;
    private Switch switchAutoSync;
    
    private TextView tvCacheSize;
    private SeekBar seekMaxStorage;
    private TextView tvMaxStorageValue;
    
    private RadioGroup radioSyncFrequency;
    private RadioButton radioDaily;
    private RadioButton radioWeekly;
    private RadioButton radioMonthly;
    
    private Button btnSyncNow;
    private Button btnClearCache;
    private ProgressBar progressSync;
    private TextView tvSyncStatus;
    
    private NetworkUtils networkUtils;
    private SyncManager syncManager;
    private CacheManager cacheManager;
    private RepositoryFactory repositoryFactory;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_settings);
        
        // Set up action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.offline_settings);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        
        // Initialize dependencies
        networkUtils = new NetworkUtils(this);
        syncManager = SyncManager.getInstance(this);
        cacheManager = CacheManager.getInstance(this);
        repositoryFactory = RepositoryFactory.getInstance(this);
        
        // Initialize views
        initializeViews();
        
        // Set up listeners
        setupListeners();
        
        // Load current settings
        loadSettings();
        
        // Update cache size
        updateCacheSize();
    }
    
    private void initializeViews() {
        offlineStatusView = findViewById(R.id.offline_status_view);
        switchOfflineMode = findViewById(R.id.switch_offline_mode);
        switchWifiOnly = findViewById(R.id.switch_wifi_only);
        switchAutoSync = findViewById(R.id.switch_auto_sync);
        
        tvCacheSize = findViewById(R.id.tv_cache_size);
        seekMaxStorage = findViewById(R.id.seek_max_storage);
        tvMaxStorageValue = findViewById(R.id.tv_max_storage_value);
        
        radioSyncFrequency = findViewById(R.id.radio_sync_frequency);
        radioDaily = findViewById(R.id.radio_daily);
        radioWeekly = findViewById(R.id.radio_weekly);
        radioMonthly = findViewById(R.id.radio_monthly);
        
        btnSyncNow = findViewById(R.id.btn_sync_now);
        btnClearCache = findViewById(R.id.btn_clear_cache);
        progressSync = findViewById(R.id.progress_sync);
        tvSyncStatus = findViewById(R.id.tv_sync_status);
    }
    
    private void setupListeners() {
        // Offline mode switch
        switchOfflineMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            networkUtils.setOfflineModeEnabled(isChecked);
            offlineStatusView.updateStatus();
            updateSyncControls();
        });
        
        // WiFi only switch
        switchWifiOnly.setOnCheckedChangeListener((buttonView, isChecked) -> {
            networkUtils.setWifiOnlyModeEnabled(isChecked);
            syncManager.setWifiOnlySyncEnabled(isChecked);
            offlineStatusView.updateStatus();
        });
        
        // Auto sync switch
        switchAutoSync.setOnCheckedChangeListener((buttonView, isChecked) -> {
            syncManager.setAutoSyncEnabled(isChecked);
            updateSyncControls();
        });
        
        // Max storage seek bar
        seekMaxStorage.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Convert to MB (100-2000 MB)
                int maxStorageMB = 100 + (progress * 19);
                tvMaxStorageValue.setText(getString(R.string.max_storage_value, maxStorageMB));
                
                // Save the setting
                if (fromUser) {
                    saveMaxStorageSetting(maxStorageMB);
                }
            }
            
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        
        // Sync frequency radio group
        radioSyncFrequency.setOnCheckedChangeListener((group, checkedId) -> {
            int syncIntervalHours;
            
            if (checkedId == R.id.radio_daily) {
                syncIntervalHours = 24;
            } else if (checkedId == R.id.radio_weekly) {
                syncIntervalHours = 24 * 7;
            } else if (checkedId == R.id.radio_monthly) {
                syncIntervalHours = 24 * 30;
            } else {
                syncIntervalHours = 24;
            }
            
            syncManager.setSyncIntervalHours(syncIntervalHours);
        });
        
        // Sync now button
        btnSyncNow.setOnClickListener(v -> {
            // Check network
            if (!networkUtils.isNetworkAvailable()) {
                Toast.makeText(this, R.string.no_network_for_sync, Toast.LENGTH_SHORT).show();
                return;
            }
            
            // Start sync
            startSync();
        });
        
        // Clear cache button
        btnClearCache.setOnClickListener(v -> {
            clearCache();
        });
        
        // Offline status view
        offlineStatusView.setOnClickListener(v -> {
            boolean isOffline = offlineStatusView.toggleOfflineMode();
            switchOfflineMode.setChecked(isOffline);
        });
    }
    
    private void loadSettings() {
        // Set switches based on current settings
        switchOfflineMode.setChecked(networkUtils.isOfflineModeEnabled());
        switchWifiOnly.setChecked(networkUtils.isWifiOnlyModeEnabled());
        switchAutoSync.setChecked(syncManager.isAutoSyncEnabled());
        
        // Set max storage seek bar
        int maxStorageMB = getMaxStorageSetting();
        int progress = (maxStorageMB - 100) / 19;
        seekMaxStorage.setProgress(progress);
        tvMaxStorageValue.setText(getString(R.string.max_storage_value, maxStorageMB));
        
        // Set sync frequency radio button
        int syncIntervalHours = syncManager.getSyncIntervalHours();
        if (syncIntervalHours <= 24) {
            radioDaily.setChecked(true);
        } else if (syncIntervalHours <= 24 * 7) {
            radioWeekly.setChecked(true);
        } else {
            radioMonthly.setChecked(true);
        }
        
        // Set last sync time
        String lastSyncTime = syncManager.getFormattedLastSyncTime();
        tvSyncStatus.setText(getString(R.string.last_sync, lastSyncTime));
        
        // Update offline status view
        offlineStatusView.updateStatus();
        
        // Update sync controls based on current state
        updateSyncControls();
    }
    
    private void updateCacheSize() {
        long cacheSize = cacheManager.getTotalCacheSize();
        String readableCacheSize = FileUtils.getReadableFileSize(cacheSize);
        tvCacheSize.setText(getString(R.string.cache_size, readableCacheSize));
    }
    
    private void updateSyncControls() {
        boolean offlineMode = networkUtils.isOfflineModeEnabled();
        boolean autoSync = syncManager.isAutoSyncEnabled();
        
        // Disable sync controls if offline mode is enabled
        radioSyncFrequency.setEnabled(!offlineMode && autoSync);
        radioDaily.setEnabled(!offlineMode && autoSync);
        radioWeekly.setEnabled(!offlineMode && autoSync);
        radioMonthly.setEnabled(!offlineMode && autoSync);
        
        // Disable auto-sync switch if offline mode is enabled
        switchAutoSync.setEnabled(!offlineMode);
        
        // Disable sync now button if offline mode is enabled
        btnSyncNow.setEnabled(!offlineMode && networkUtils.isNetworkAvailable());
    }
    
    private void startSync() {
        // Show progress
        progressSync.setVisibility(View.VISIBLE);
        btnSyncNow.setEnabled(false);
        tvSyncStatus.setText(R.string.syncing);
        
        // Set sync listener
        syncManager.setSyncStatusListener((progress, max, status, isComplete) -> {
            if (isComplete) {
                // Hide progress
                progressSync.setVisibility(View.GONE);
                btnSyncNow.setEnabled(true);
                
                // Update last sync time
                String lastSyncTime = syncManager.getFormattedLastSyncTime();
                tvSyncStatus.setText(getString(R.string.last_sync, lastSyncTime));
                
                // Update cache size
                updateCacheSize();
                
                // Show success message
                Toast.makeText(this, R.string.sync_complete, Toast.LENGTH_SHORT).show();
            } else {
                // Update progress
                if (max > 0) {
                    progressSync.setIndeterminate(false);
                    progressSync.setMax(max);
                    progressSync.setProgress(progress);
                } else {
                    progressSync.setIndeterminate(true);
                }
                
                // Update status
                tvSyncStatus.setText(status);
            }
        });
        
        // Start sync service
        syncManager.startEssentialSync(-1);
    }
    
    private void clearCache() {
        // Show confirmation dialog
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle(R.string.clear_cache)
                .setMessage(R.string.clear_cache_confirm)
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    // Clear cache
                    cacheManager.clearOldCache(0);
                    
                    // Update cache size after a delay
                    findViewById(android.R.id.content).postDelayed(() -> {
                        updateCacheSize();
                        Toast.makeText(this, R.string.cache_cleared, Toast.LENGTH_SHORT).show();
                    }, 500);
                })
                .setNegativeButton(R.string.no, null)
                .show();
    }
    
    private int getMaxStorageSetting() {
        // Load from SharedPreferences
        return getSharedPreferences("cache_prefs", MODE_PRIVATE)
                .getInt("max_storage_mb", 500);
    }
    
    private void saveMaxStorageSetting(int maxStorageMB) {
        // Save to SharedPreferences
        getSharedPreferences("cache_prefs", MODE_PRIVATE)
                .edit()
                .putInt("max_storage_mb", maxStorageMB)
                .apply();
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}