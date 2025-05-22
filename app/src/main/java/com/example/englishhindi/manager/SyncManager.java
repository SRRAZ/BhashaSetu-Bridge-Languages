package com.example.englishhindi.manager;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import com.example.englishhindi.cache.CacheManager;
import com.example.englishhindi.database.WordRepository;
import com.example.englishhindi.model.Word;
import com.example.englishhindi.service.SyncService;
import com.example.englishhindi.util.NetworkUtils;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Manager class to handle sync operations and scheduling.
 * Coordinates with SyncService for background operations.
 */
public class SyncManager {
    
    private static final String TAG = "SyncManager";
    private static final String PREF_NAME = "sync_prefs";
    private static final String KEY_LAST_SYNC_TIME = "last_sync_time";
    private static final String KEY_LAST_SYNC_TYPE = "last_sync_type";
    private static final String KEY_AUTO_SYNC_ENABLED = "auto_sync_enabled";
    private static final String KEY_WIFI_ONLY_SYNC = "wifi_only_sync";
    private static final String KEY_SYNC_INTERVAL_HOURS = "sync_interval_hours";
    
    private static SyncManager instance;
    
    private Context context;
    private SharedPreferences prefs;
    private CacheManager cacheManager;
    private WordRepository wordRepository;
    private NetworkUtils networkUtils;
    
    private SyncService syncService;
    private boolean isBound = false;
    
    private SyncStatusListener statusListener;
    
    // Service connection for binding to SyncService
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            SyncService.LocalBinder binder = (SyncService.LocalBinder) service;
            syncService = binder.getService();
            syncService.setSyncProgressListener(
                    (progress, max, status, isComplete) -> {
                        if (statusListener != null) {
                            statusListener.onSyncStatusChanged(progress, max, status, isComplete);
                        }
                        
                        if (isComplete) {
                            unbindService();
                        }
                    });
            isBound = true;
            
            Log.d(TAG, "Bound to SyncService");
        }
        
        @Override
        public void onServiceDisconnected(ComponentName name) {
            syncService = null;
            isBound = false;
            
            Log.d(TAG, "Disconnected from SyncService");
        }
    };
    
    private SyncManager(Context context) {
        this.context = context.getApplicationContext();
        this.prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.cacheManager = CacheManager.getInstance(context);
        this.wordRepository = new WordRepository(context);
        this.networkUtils = new NetworkUtils(context);
    }
    
    /**
     * Get the singleton instance of the sync manager
     * @param context Application context
     * @return SyncManager instance
     */
    public static synchronized SyncManager getInstance(Context context) {
        if (instance == null) {
            instance = new SyncManager(context);
        }
        return instance;
    }
    
    /**
     * Set a listener for sync status updates
     * @param listener Listener to set
     */
    public void setSyncStatusListener(SyncStatusListener listener) {
        this.statusListener = listener;
    }
    
    /**
     * Start a full content sync
     * @param maxItems Maximum number of items to sync, or -1 for all
     */
    public void startFullSync(int maxItems) {
        startSync(SyncService.SYNC_TYPE_FULL, maxItems);
    }
    
    /**
     * Start a sync of essential content only
     * @param maxItems Maximum number of items to sync, or -1 for all essential items
     */
    public void startEssentialSync(int maxItems) {
        startSync(SyncService.SYNC_TYPE_ESSENTIAL, maxItems);
    }
    
    /**
     * Start a sync of recent content
     * @param maxItems Maximum number of items to sync, or -1 for all recent items
     */
    public void startRecentSync(int maxItems) {
        startSync(SyncService.SYNC_TYPE_RECENT, maxItems);
    }
    
    /**
     * Start a sync operation with the specified parameters
     * @param syncType Type of sync to perform
     * @param maxItems Maximum number of items to sync
     */
    private void startSync(String syncType, int maxItems) {
        // Create intent for service
        Intent intent = new Intent(context, SyncService.class);
        intent.putExtra(SyncService.EXTRA_SYNC_TYPE, syncType);
        intent.putExtra(SyncService.EXTRA_MAX_ITEMS, maxItems);
        intent.putExtra(SyncService.EXTRA_WIFI_ONLY, isWifiOnlySyncEnabled());
        
        // Start and bind to the service
        context.startService(intent);
        
        if (!isBound) {
            context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        }
        
        // Update last sync time and type
        updateLastSyncInfo(syncType);
    }
    
    /**
     * Stop any ongoing sync
     */
    public void stopSync() {
        // Stop the service
        context.stopService(new Intent(context, SyncService.class));
        
        // Unbind if bound
        unbindService();
    }
    
    /**
     * Unbind from the sync service
     */
    private void unbindService() {
        if (isBound) {
            context.unbindService(serviceConnection);
            isBound = false;
        }
    }
    
    /**
     * Update last sync information
     * @param syncType Type of sync performed
     */
    private void updateLastSyncInfo(String syncType) {
        prefs.edit()
            .putLong(KEY_LAST_SYNC_TIME, System.currentTimeMillis())
            .putString(KEY_LAST_SYNC_TYPE, syncType)
            .apply();
    }
    
    /**
     * Check if a sync is currently in progress
     * @return true if syncing
     */
    public boolean isSyncing() {
        return isBound && syncService != null;
    }
    
    /**
     * Get the time of the last sync
     * @return Timestamp of last sync, or 0 if never synced
     */
    public long getLastSyncTime() {
        return prefs.getLong(KEY_LAST_SYNC_TIME, 0);
    }
    
    /**
     * Get the type of the last sync
     * @return Type of last sync, or null if never synced
     */
    public String getLastSyncType() {
        return prefs.getString(KEY_LAST_SYNC_TYPE, null);
    }
    
    /**
     * Check if auto-sync is enabled
     * @return true if enabled
     */
    public boolean isAutoSyncEnabled() {
        return prefs.getBoolean(KEY_AUTO_SYNC_ENABLED, true);
    }
    
    /**
     * Enable or disable auto-sync
     * @param enabled true to enable
     */
    public void setAutoSyncEnabled(boolean enabled) {
        prefs.edit().putBoolean(KEY_AUTO_SYNC_ENABLED, enabled).apply();
    }
    
    /**
     * Check if WiFi-only sync is enabled
     * @return true if enabled
     */
    public boolean isWifiOnlySyncEnabled() {
        return prefs.getBoolean(KEY_WIFI_ONLY_SYNC, true);
    }
    
    /**
     * Enable or disable WiFi-only sync
     * @param enabled true to enable
     */
    public void setWifiOnlySyncEnabled(boolean enabled) {
        prefs.edit().putBoolean(KEY_WIFI_ONLY_SYNC, enabled).apply();
    }
    
    /**
     * Get the auto-sync interval in hours
     * @return Interval in hours
     */
    public int getSyncIntervalHours() {
        return prefs.getInt(KEY_SYNC_INTERVAL_HOURS, 24);
    }
    
    /**
     * Set the auto-sync interval in hours
     * @param hours Interval in hours
     */
    public void setSyncIntervalHours(int hours) {
        prefs.edit().putInt(KEY_SYNC_INTERVAL_HOURS, hours).apply();
    }
    
    /**
     * Check if a sync is needed based on last sync time and interval
     * @return true if sync needed
     */
    public boolean isSyncNeeded() {
        if (!isAutoSyncEnabled()) {
            return false;
        }
        
        long lastSyncTime = getLastSyncTime();
        long currentTime = System.currentTimeMillis();
        long intervalMillis = TimeUnit.HOURS.toMillis(getSyncIntervalHours());
        
        return (currentTime - lastSyncTime) >= intervalMillis;
    }
    
    /**
     * Check if the device is ready for a sync
     * @return true if ready (network available, etc.)
     */
    public boolean isReadyForSync() {
        if (isWifiOnlySyncEnabled() && !networkUtils.isWifiConnected()) {
            return false;
        }
        
        return networkUtils.isNetworkAvailable();
    }
    
    /**
     * Prefetch content for offline use
     * @param words List of words to prefetch
     */
    public void prefetchContent(List<Word> words) {
        if (words == null || words.isEmpty()) {
            return;
        }
        
        if (!isReadyForSync()) {
            // Can't prefetch without network
            return;
        }
        
        // Prefetch word audio and images
        cacheManager.prefetchWordAudio(words);
        cacheManager.prefetchWordImages(words);
    }
    
    /**
     * Get the time until the next sync is scheduled
     * @return Time in milliseconds, or -1 if auto-sync is disabled
     */
    public long getTimeUntilNextSync() {
        if (!isAutoSyncEnabled()) {
            return -1;
        }
        
        long lastSyncTime = getLastSyncTime();
        long currentTime = System.currentTimeMillis();
        long intervalMillis = TimeUnit.HOURS.toMillis(getSyncIntervalHours());
        long nextSyncTime = lastSyncTime + intervalMillis;
        
        return Math.max(0, nextSyncTime - currentTime);
    }
    
    /**
     * Format the last sync time as a readable string
     * @return Formatted time string
     */
    public String getFormattedLastSyncTime() {
        long lastSyncTime = getLastSyncTime();
        
        if (lastSyncTime == 0) {
            return "Never";
        }
        
        Date date = new Date(lastSyncTime);
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(
                "MMM d, yyyy 'at' h:mm a", java.util.Locale.getDefault());
        
        return format.format(date);
    }
    
    /**
     * Interface for sync status updates
     */
    public interface SyncStatusListener {
        void onSyncStatusChanged(int progress, int max, String status, boolean isComplete);
    }
}