package com.bhashasetu.app.data;

import android.database.sqlite.SQLiteException;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Base interface for Data Access Objects with performance optimizations.
 * This interface provides optimized query methods and performance monitoring.
 *
 * @param <T> Entity type
 */
public interface OptimizedDao<T> {
    
    String TAG = "OptimizedDao";
    
    /**
     * Get the entity's table name.
     *
     * @return Table name
     */
    String getTableName();
    
    /**
     * Execute a query with performance monitoring.
     *
     * @param callable    Query callable
     * @param queryName   Query name for logging
     * @param <R>         Result type
     * @return Query result
     */
    default <R> R executeWithPerformance(Callable<R> callable, String queryName) {
        long startTime = System.nanoTime();
        try {
            return callable.call();
        } catch (Exception e) {
            Log.e(TAG, "Error executing " + queryName, e);
            return null;
        } finally {
            long endTime = System.nanoTime();
            long durationMs = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
            
            // Log slow queries
            if (durationMs > 100) {
                Log.w(TAG, "Slow query: " + queryName + " took " + durationMs + "ms");
            }
        }
    }
    
    /**
     * Execute a batched operation with performance monitoring.
     *
     * @param items       Items to process
     * @param batchSize   Batch size
     * @param operation   Operation to perform on each batch
     * @param <R>         Item type
     */
    default <R> void executeBatched(List<R> items, int batchSize, BatchOperation<R> operation) {
        if (items == null || items.isEmpty()) {
            return;
        }
        
        int totalItems = items.size();
        int batches = (totalItems + batchSize - 1) / batchSize; // Ceiling division
        
        for (int i = 0; i < batches; i++) {
            int start = i * batchSize;
            int end = Math.min(start + batchSize, totalItems);
            List<R> batch = items.subList(start, end);
            
            long startTime = System.nanoTime();
            try {
                operation.processBatch(batch);
            } catch (Exception e) {
                Log.e(TAG, "Error processing batch " + i, e);
            }
            long endTime = System.nanoTime();
            long durationMs = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
            
            // Log slow batch operations
            if (durationMs > 500) {
                Log.w(TAG, "Slow batch operation: batch " + i + " took " + durationMs + "ms");
            }
        }
    }
    
    /**
     * Batch operation interface.
     *
     * @param <T> Item type
     */
    interface BatchOperation<T> {
        void processBatch(List<T> batch) throws Exception;
    }
    
    /**
     * Execute a query asynchronously on a background thread.
     *
     * @param executor    Executor service
     * @param callable    Query callable
     * @param queryName   Query name for logging
     * @param callback    Callback to handle result
     * @param <R>         Result type
     */
    default <R> void executeAsync(ExecutorService executor, 
                                Callable<R> callable, 
                                String queryName,
                                QueryCallback<R> callback) {
        executor.execute(() -> {
            long startTime = System.nanoTime();
            try {
                R result = callable.call();
                if (callback != null) {
                    callback.onSuccess(result);
                }
            } catch (Exception e) {
                Log.e(TAG, "Error executing async " + queryName, e);
                if (callback != null) {
                    callback.onError(e);
                }
            } finally {
                long endTime = System.nanoTime();
                long durationMs = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
                
                // Log slow queries
                if (durationMs > 100) {
                    Log.w(TAG, "Slow async query: " + queryName + " took " + durationMs + "ms");
                }
            }
        });
    }
    
    /**
     * Query callback interface.
     *
     * @param <T> Result type
     */
    interface QueryCallback<T> {
        void onSuccess(T result);
        void onError(Exception e);
    }
    
    /**
     * Get a paged list for efficient handling of large data sets.
     *
     * @param query SQL query
     * @return DataSource.Factory for paging
     */
    default DataSource.Factory<Integer, T> getPagedList(String query) {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    /**
     * Check if a query would return results without executing the full query.
     * Useful for existence checks without loading all data.
     *
     * @param query SQL query
     * @return true if data exists
     */
    default boolean exists(String query) {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    /**
     * Optimize a table by analyzing it and optimizing indexes.
     */
    default void optimizeTable() {
        try {
            // Basic implementation that can be extended in concrete DAOs
            Log.d(TAG, "Optimizing table: " + getTableName());
        } catch (Exception e) {
            Log.e(TAG, "Error optimizing table: " + getTableName(), e);
        }
    }
    
    /**
     * Check table size and warn if it's getting too large.
     *
     * @param warnThreshold Row count threshold to trigger warning
     * @return Current row count
     */
    default int checkTableSize(int warnThreshold) {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    /**
     * Clean up old data to prevent database bloat.
     *
     * @param olderThan Timestamp for data to clean up
     * @return Number of rows deleted
     */
    default int cleanupOldData(long olderThan) {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    /**
     * Attempt a query with automatic retry for transient errors.
     *
     * @param callable  Query callable
     * @param queryName Query name for logging
     * @param maxRetries Maximum number of retries
     * @param <R>       Result type
     * @return Query result
     */
    default <R> R retryableQuery(Callable<R> callable, String queryName, int maxRetries) {
        int attempts = 0;
        while (attempts <= maxRetries) {
            try {
                return callable.call();
            } catch (SQLiteException e) {
                // Only retry specific SQLite errors that might be transient
                if (e.getMessage() != null && 
                    (e.getMessage().contains("database is locked") || 
                     e.getMessage().contains("busy"))) {
                    attempts++;
                    if (attempts <= maxRetries) {
                        // Exponential backoff
                        long sleepTime = (long) Math.pow(2, attempts) * 50;
                        try {
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                            break;
                        }
                        Log.w(TAG, "Retrying query " + queryName + " (attempt " + attempts + ")");
                    } else {
                        Log.e(TAG, "Query " + queryName + " failed after " + maxRetries + " retries", e);
                        throw new RuntimeException("Failed after " + maxRetries + " retries", e);
                    }
                } else {
                    // Non-retryable error
                    Log.e(TAG, "Non-retryable error in query " + queryName, e);
                    throw new RuntimeException(e);
                }
            } catch (Exception e) {
                Log.e(TAG, "Error in query " + queryName, e);
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("Failed to execute query " + queryName);
    }
    
    /**
     * Get the count of rows in a table.
     *
     * @return Row count
     */
    default int getCount() {
        throw new UnsupportedOperationException("Not implemented");
    }
}