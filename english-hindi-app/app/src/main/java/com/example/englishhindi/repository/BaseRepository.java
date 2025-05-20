package com.example.englishhindi.repository;

import android.content.Context;
import android.util.Log;

import com.example.englishhindi.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Base repository class that implements offline-first data access pattern.
 * All repositories should extend this class to utilize the offline capabilities.
 * @param <T> The type of data this repository manages
 */
public abstract class BaseRepository<T> {
    private static final String TAG = "BaseRepository";
    
    protected Context context;
    protected NetworkUtils networkUtils;
    protected Executor executor;
    protected List<RepositoryCallback<T>> pendingCallbacks = new ArrayList<>();
    
    // Constructor
    public BaseRepository(Context context) {
        this.context = context.getApplicationContext();
        this.networkUtils = NetworkUtils.getInstance(context);
        this.executor = Executors.newSingleThreadExecutor();
    }
    
    /**
     * Get data with offline-first approach, first checking local storage
     * and then fetching from network if needed and available.
     * @param key The identifier for the data
     * @param callback The callback to receive the data
     * @param forceRefresh Whether to force a refresh from network
     */
    public void getData(String key, RepositoryCallback<T> callback, boolean forceRefresh) {
        // Execute on background thread
        executor.execute(() -> {
            try {
                // First, try to get from local storage
                T localData = getFromLocalStorage(key);
                
                // If we have local data and don't need to refresh, return it
                if (localData != null && !forceRefresh) {
                    callback.onSuccess(localData);
                    return;
                }
                
                // If network is not available, return local data or error
                if (!networkUtils.isNetworkAvailable()) {
                    if (localData != null) {
                        callback.onSuccess(localData);
                    } else {
                        callback.onError(new Exception("No network and no local data"));
                    }
                    return;
                }
                
                // If we get here, we need to fetch from network
                fetchFromNetwork(key, new RepositoryCallback<T>() {
                    @Override
                    public void onSuccess(T data) {
                        // Save fetched data to local storage
                        saveToLocalStorage(key, data);
                        
                        // Return the data
                        callback.onSuccess(data);
                    }
                    
                    @Override
                    public void onError(Exception e) {
                        // If network fetch fails but we have local data, return it
                        if (localData != null) {
                            callback.onSuccess(localData);
                        } else {
                            callback.onError(e);
                        }
                    }
                });
            } catch (Exception e) {
                Log.e(TAG, "Error getting data: " + e.getMessage());
                callback.onError(e);
            }
        });
    }
    
    /**
     * Get all data items with offline-first approach
     * @param callback The callback to receive the data
     * @param forceRefresh Whether to force a refresh from network
     */
    public void getAllData(RepositoryCallback<List<T>> callback, boolean forceRefresh) {
        // Execute on background thread
        executor.execute(() -> {
            try {
                // First, try to get from local storage
                List<T> localData = getAllFromLocalStorage();
                
                // If we have local data and don't need to refresh, return it
                if (localData != null && !localData.isEmpty() && !forceRefresh) {
                    callback.onSuccess(localData);
                    return;
                }
                
                // If network is not available, return local data or error
                if (!networkUtils.isNetworkAvailable()) {
                    if (localData != null) {
                        callback.onSuccess(localData);
                    } else {
                        callback.onError(new Exception("No network and no local data"));
                    }
                    return;
                }
                
                // If we get here, we need to fetch from network
                fetchAllFromNetwork(new RepositoryCallback<List<T>>() {
                    @Override
                    public void onSuccess(List<T> data) {
                        // Save fetched data to local storage
                        saveAllToLocalStorage(data);
                        
                        // Return the data
                        callback.onSuccess(data);
                    }
                    
                    @Override
                    public void onError(Exception e) {
                        // If network fetch fails but we have local data, return it
                        if (localData != null) {
                            callback.onSuccess(localData);
                        } else {
                            callback.onError(e);
                        }
                    }
                });
            } catch (Exception e) {
                Log.e(TAG, "Error getting all data: " + e.getMessage());
                callback.onError(e);
            }
        });
    }
    
    /**
     * Save data with optimistic UI update and offline queue
     * @param data The data to save
     * @param callback The callback to receive result
     */
    public void saveData(T data, RepositoryCallback<Boolean> callback) {
        // Execute on background thread
        executor.execute(() -> {
            try {
                // First, save to local storage for optimistic update
                saveToLocalStorage(getKeyForData(data), data);
                
                // If network is not available, queue the operation
                if (!networkUtils.isNetworkAvailable()) {
                    queueOfflineOperation(OfflineOperation.SAVE, data);
                    callback.onSuccess(true);
                    return;
                }
                
                // If network is available, save to remote
                saveToNetwork(data, new RepositoryCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean result) {
                        callback.onSuccess(result);
                    }
                    
                    @Override
                    public void onError(Exception e) {
                        // If network save fails, queue the operation
                        queueOfflineOperation(OfflineOperation.SAVE, data);
                        // Still report success since we saved locally
                        callback.onSuccess(true);
                    }
                });
            } catch (Exception e) {
                Log.e(TAG, "Error saving data: " + e.getMessage());
                callback.onError(e);
            }
        });
    }
    
    /**
     * Delete data with optimistic UI update and offline queue
     * @param key The key of the data to delete
     * @param callback The callback to receive result
     */
    public void deleteData(String key, RepositoryCallback<Boolean> callback) {
        // Execute on background thread
        executor.execute(() -> {
            try {
                // Get the data before deleting for potential rollback
                T dataToDelete = getFromLocalStorage(key);
                
                // First, delete from local storage for optimistic update
                deleteFromLocalStorage(key);
                
                // If network is not available, queue the operation
                if (!networkUtils.isNetworkAvailable()) {
                    queueOfflineOperation(OfflineOperation.DELETE, dataToDelete);
                    callback.onSuccess(true);
                    return;
                }
                
                // If network is available, delete from remote
                deleteFromNetwork(key, new RepositoryCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean result) {
                        callback.onSuccess(result);
                    }
                    
                    @Override
                    public void onError(Exception e) {
                        // If network delete fails, queue the operation
                        queueOfflineOperation(OfflineOperation.DELETE, dataToDelete);
                        // Still report success since we deleted locally
                        callback.onSuccess(true);
                    }
                });
            } catch (Exception e) {
                Log.e(TAG, "Error deleting data: " + e.getMessage());
                callback.onError(e);
            }
        });
    }
    
    /**
     * Process any pending offline operations when network becomes available
     */
    public void processOfflineQueue() {
        // Execute on background thread
        executor.execute(() -> {
            try {
                // Check if network is available
                if (!networkUtils.isNetworkAvailable()) {
                    Log.d(TAG, "Network not available, cannot process offline queue");
                    return;
                }
                
                // Get pending operations
                List<PendingOperation<T>> pendingOperations = getPendingOperations();
                if (pendingOperations.isEmpty()) {
                    Log.d(TAG, "No pending operations to process");
                    return;
                }
                
                Log.d(TAG, "Processing " + pendingOperations.size() + " pending operations");
                
                // Process each operation
                for (PendingOperation<T> operation : pendingOperations) {
                    switch (operation.getType()) {
                        case SAVE:
                            saveToNetwork(operation.getData(), new RepositoryCallback<Boolean>() {
                                @Override
                                public void onSuccess(Boolean result) {
                                    removePendingOperation(operation.getId());
                                }
                                
                                @Override
                                public void onError(Exception e) {
                                    // Leave in queue to try again later
                                    Log.e(TAG, "Error processing save operation: " + e.getMessage());
                                }
                            });
                            break;
                            
                        case DELETE:
                            deleteFromNetwork(getKeyForData(operation.getData()), new RepositoryCallback<Boolean>() {
                                @Override
                                public void onSuccess(Boolean result) {
                                    removePendingOperation(operation.getId());
                                }
                                
                                @Override
                                public void onError(Exception e) {
                                    // Leave in queue to try again later
                                    Log.e(TAG, "Error processing delete operation: " + e.getMessage());
                                }
                            });
                            break;
                            
                        case UPDATE:
                            updateInNetwork(operation.getData(), new RepositoryCallback<Boolean>() {
                                @Override
                                public void onSuccess(Boolean result) {
                                    removePendingOperation(operation.getId());
                                }
                                
                                @Override
                                public void onError(Exception e) {
                                    // Leave in queue to try again later
                                    Log.e(TAG, "Error processing update operation: " + e.getMessage());
                                }
                            });
                            break;
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "Error processing offline queue: " + e.getMessage());
            }
        });
    }
    
    /**
     * The types of offline operations that can be queued
     */
    protected enum OfflineOperation {
        SAVE,
        UPDATE,
        DELETE
    }
    
    /**
     * Class to represent a pending operation for offline queue
     */
    protected static class PendingOperation<T> {
        private final long id;
        private final OfflineOperation type;
        private final T data;
        private final long timestamp;
        
        public PendingOperation(long id, OfflineOperation type, T data) {
            this.id = id;
            this.type = type;
            this.data = data;
            this.timestamp = System.currentTimeMillis();
        }
        
        public long getId() {
            return id;
        }
        
        public OfflineOperation getType() {
            return type;
        }
        
        public T getData() {
            return data;
        }
        
        public long getTimestamp() {
            return timestamp;
        }
    }
    
    /**
     * Interface for repository operation callbacks
     */
    public interface RepositoryCallback<T> {
        void onSuccess(T result);
        void onError(Exception e);
    }
    
    /**
     * Get data from local storage
     * @param key The key to retrieve
     * @return The data, or null if not found
     */
    protected abstract T getFromLocalStorage(String key);
    
    /**
     * Save data to local storage
     * @param key The key to save under
     * @param data The data to save
     */
    protected abstract void saveToLocalStorage(String key, T data);
    
    /**
     * Delete data from local storage
     * @param key The key to delete
     */
    protected abstract void deleteFromLocalStorage(String key);
    
    /**
     * Get all data from local storage
     * @return List of all data items
     */
    protected abstract List<T> getAllFromLocalStorage();
    
    /**
     * Save all data to local storage
     * @param data The list of data to save
     */
    protected abstract void saveAllToLocalStorage(List<T> data);
    
    /**
     * Fetch data from network
     * @param key The key to fetch
     * @param callback The callback to receive the data
     */
    protected abstract void fetchFromNetwork(String key, RepositoryCallback<T> callback);
    
    /**
     * Fetch all data from network
     * @param callback The callback to receive the data
     */
    protected abstract void fetchAllFromNetwork(RepositoryCallback<List<T>> callback);
    
    /**
     * Save data to network
     * @param data The data to save
     * @param callback The callback to receive result
     */
    protected abstract void saveToNetwork(T data, RepositoryCallback<Boolean> callback);
    
    /**
     * Update data in network
     * @param data The data to update
     * @param callback The callback to receive result
     */
    protected abstract void updateInNetwork(T data, RepositoryCallback<Boolean> callback);
    
    /**
     * Delete data from network
     * @param key The key to delete
     * @param callback The callback to receive result
     */
    protected abstract void deleteFromNetwork(String key, RepositoryCallback<Boolean> callback);
    
    /**
     * Get the key for a data item
     * @param data The data item
     * @return The key
     */
    protected abstract String getKeyForData(T data);
    
    /**
     * Queue an operation for offline processing
     * @param type The operation type
     * @param data The data for the operation
     */
    protected abstract void queueOfflineOperation(OfflineOperation type, T data);
    
    /**
     * Get all pending operations
     * @return List of pending operations
     */
    protected abstract List<PendingOperation<T>> getPendingOperations();
    
    /**
     * Remove a pending operation
     * @param id The operation ID
     */
    protected abstract void removePendingOperation(long id);
}