package com.bhashasetu.app.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.bhashasetu.app.util.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Helper class to manage offline operation queues for repositories.
 * This class provides a standardized way to store and process operations
 * that need to be executed when network connectivity is restored.
 */
public class OfflineQueueHelper {
    private static final String TAG = "OfflineQueueHelper";
    
    private static final String PREF_NAME = "offline_queue";
    private static final String KEY_NEXT_ID = "next_id";
    
    private final Context context;
    private final SharedPreferences prefs;
    private final Executor executor;
    private final NetworkUtils networkUtils;
    
    private static OfflineQueueHelper instance;
    
    /**
     * Get the singleton instance of OfflineQueueHelper
     * @param context Application context
     * @return OfflineQueueHelper instance
     */
    public static synchronized OfflineQueueHelper getInstance(Context context) {
        if (instance == null) {
            instance = new OfflineQueueHelper(context);
        }
        return instance;
    }
    
    private OfflineQueueHelper(Context context) {
        this.context = context.getApplicationContext();
        this.prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.executor = Executors.newSingleThreadExecutor();
        this.networkUtils = NetworkUtils.getInstance(context);
        
        // Register for network changes to process queue when connection is restored
 networkUtils.addNetworkStateListener((isConnected, isWifi, isMetered) -> {
            if (isConnected) {
                processAllQueues();
            }
        });
    }
    
    /**
     * Add an operation to the offline queue
     * @param queueName Name of the queue (usually repository name)
     * @param operationType Type of operation (SAVE, UPDATE, DELETE)
     * @param data JSON data for the operation
     * @return ID of the queued operation
     */
    public long queueOperation(String queueName, String operationType, JSONObject data) {
        long operationId = getNextOperationId();
        
        try {
            // Create operation object
            JSONObject operation = new JSONObject();
            operation.put("id", operationId);
            operation.put("type", operationType);
            operation.put("data", data);
            operation.put("timestamp", System.currentTimeMillis());
            
            // Get current queue
            JSONArray queue = getQueue(queueName);
            queue.put(operation);
            
            // Save updated queue
            saveQueue(queueName, queue);
            
            Log.d(TAG, "Queued " + operationType + " operation with ID " + operationId + " for " + queueName);
            return operationId;
        } catch (JSONException e) {
            Log.e(TAG, "Error queuing operation: " + e.getMessage());
            return -1;
        }
    }
    
    /**
     * Get all operations in a queue
     * @param queueName Name of the queue
     * @return List of operation JSONObjects
     */
    public List<JSONObject> getOperations(String queueName) {
        List<JSONObject> operations = new ArrayList<>();
        
        try {
            JSONArray queue = getQueue(queueName);
            for (int i = 0; i < queue.length(); i++) {
                operations.add(queue.getJSONObject(i));
            }
        } catch (JSONException e) {
            Log.e(TAG, "Error getting operations: " + e.getMessage());
        }
        
        return operations;
    }
    
    /**
     * Remove an operation from the queue
     * @param queueName Name of the queue
     * @param operationId ID of the operation to remove
     * @return true if removed, false if not found
     */
    public boolean removeOperation(String queueName, long operationId) {
        try {
            JSONArray queue = getQueue(queueName);
            JSONArray updatedQueue = new JSONArray();
            boolean found = false;
            
            for (int i = 0; i < queue.length(); i++) {
                JSONObject operation = queue.getJSONObject(i);
                if (operation.getLong("id") != operationId) {
                    updatedQueue.put(operation);
                } else {
                    found = true;
                }
            }
            
            if (found) {
                saveQueue(queueName, updatedQueue);
                Log.d(TAG, "Removed operation " + operationId + " from queue " + queueName);
            }
            
            return found;
        } catch (JSONException e) {
            Log.e(TAG, "Error removing operation: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Process all queues when network is available
     */
    public void processAllQueues() {
        executor.execute(() -> {
            if (!networkUtils.isNetworkAvailable()) {
                Log.d(TAG, "Network not available, skipping queue processing");
                return;
            }
            
            // Notify all repositories to process their queues
            // This is just a notification mechanism - actual processing is done by the repositories
            context.sendBroadcast(new android.content.Intent("com.example.englishhindi.PROCESS_OFFLINE_QUEUE"));
            
            Log.d(TAG, "Notified repositories to process offline queues");
        });
    }
    
    /**
     * Clear a queue
     * @param queueName Name of the queue to clear
     */
    public void clearQueue(String queueName) {
        saveQueue(queueName, new JSONArray());
        Log.d(TAG, "Cleared queue " + queueName);
    }
    
    /**
     * Check if a queue has any pending operations
     * @param queueName Name of the queue
     * @return true if queue has operations
     */
    public boolean hasOperations(String queueName) {
        return getQueue(queueName).length() > 0;
    }
    
    /**
     * Get the number of operations in a queue
     * @param queueName Name of the queue
     * @return Number of operations
     */
    public int getOperationCount(String queueName) {
        return getQueue(queueName).length();
    }
    
    /**
     * Get the next unique operation ID
     * @return Next ID
     */
    private long getNextOperationId() {
        long id = prefs.getLong(KEY_NEXT_ID, 1);
        prefs.edit().putLong(KEY_NEXT_ID, id + 1).apply();
        return id;
    }
    
    /**
     * Get a queue by name
     * @param queueName Name of the queue
     * @return JSONArray queue
     */
    private JSONArray getQueue(String queueName) {
        String queueStr = prefs.getString(queueName, "[]");
        try {
            return new JSONArray(queueStr);
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing queue: " + e.getMessage());
            return new JSONArray();
        }
    }
    
    /**
     * Save a queue
     * @param queueName Name of the queue
     * @param queue Queue to save
     */
    private void saveQueue(String queueName, JSONArray queue) {
        prefs.edit().putString(queueName, queue.toString()).apply();
    }
}