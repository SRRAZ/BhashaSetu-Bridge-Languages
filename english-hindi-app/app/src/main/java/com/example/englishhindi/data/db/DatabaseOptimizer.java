package com.example.englishhindi.data.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for optimizing database operations and performance.
 * Provides methods for analyzing and optimizing query performance, creating indexes,
 * and managing database resources efficiently.
 */
public class DatabaseOptimizer {
    private static final String TAG = "DatabaseOptimizer";

    /**
     * Analyze the database and create missing indexes to improve query performance.
     * This method examines slow queries and creates appropriate indexes.
     *
     * @param database The Room database to optimize
     */
    public static void optimizeDatabaseIndexes(RoomDatabase database) {
        SupportSQLiteDatabase db = database.getOpenHelper().getWritableDatabase();
        
        try {
            // Get list of existing indexes
            List<String> existingIndexes = getExistingIndexes(db);
            
            // Check and create indexes for commonly queried tables/columns
            createIndexIfNotExists(db, existingIndexes, "words", "difficulty_level");
            createIndexIfNotExists(db, existingIndexes, "lessons", "category_id");
            createIndexIfNotExists(db, existingIndexes, "user_progress", "user_id", "word_id");
            createIndexIfNotExists(db, existingIndexes, "user_history", "timestamp");
            createIndexIfNotExists(db, existingIndexes, "flashcards", "last_reviewed");
            
            Log.d(TAG, "Database indexes optimized");
        } catch (Exception e) {
            Log.e(TAG, "Error optimizing database indexes", e);
        }
    }

    /**
     * Get a list of existing database indexes.
     *
     * @param db The SQLite database
     * @return List of index names
     */
    private static List<String> getExistingIndexes(SupportSQLiteDatabase db) {
        List<String> indexes = new ArrayList<>();
        
        Cursor cursor = null;
        try {
            cursor = db.query("SELECT name FROM sqlite_master WHERE type = 'index'");
            
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    indexes.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, "Error getting existing indexes", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        
        return indexes;
    }

    /**
     * Create an index if it doesn't already exist.
     *
     * @param db The SQLite database
     * @param existingIndexes List of existing index names
     * @param table Table name
     * @param column Column name
     */
    private static void createIndexIfNotExists(SupportSQLiteDatabase db, 
                                              List<String> existingIndexes,
                                              String table, 
                                              String column) {
        String indexName = "idx_" + table + "_" + column;
        
        if (!existingIndexes.contains(indexName)) {
            try {
                String sql = "CREATE INDEX " + indexName + " ON " + table + "(" + column + ")";
                db.execSQL(sql);
                Log.d(TAG, "Created index: " + indexName);
            } catch (Exception e) {
                Log.e(TAG, "Error creating index: " + indexName, e);
            }
        }
    }

    /**
     * Create a compound index if it doesn't already exist.
     *
     * @param db The SQLite database
     * @param existingIndexes List of existing index names
     * @param table Table name
     * @param column1 First column name
     * @param column2 Second column name
     */
    private static void createIndexIfNotExists(SupportSQLiteDatabase db, 
                                              List<String> existingIndexes,
                                              String table, 
                                              String column1, 
                                              String column2) {
        String indexName = "idx_" + table + "_" + column1 + "_" + column2;
        
        if (!existingIndexes.contains(indexName)) {
            try {
                String sql = "CREATE INDEX " + indexName + " ON " + table + 
                           "(" + column1 + ", " + column2 + ")";
                db.execSQL(sql);
                Log.d(TAG, "Created compound index: " + indexName);
            } catch (Exception e) {
                Log.e(TAG, "Error creating compound index: " + indexName, e);
            }
        }
    }

    /**
     * Optimize database connection pool for better performance.
     * Adjusts SQLite WAL mode and journal size for improved write performance.
     *
     * @param database The Room database to optimize
     */
    public static void optimizeConnectionPool(RoomDatabase database) {
        SupportSQLiteDatabase db = database.getOpenHelper().getWritableDatabase();
        
        try {
            // Enable Write-Ahead Logging for better concurrency
            db.execSQL("PRAGMA journal_mode = WAL");
            
            // Set journal size limit to 1MB
            db.execSQL("PRAGMA journal_size_limit = 1048576");
            
            // Set synchronous mode to NORMAL for better performance
            // FULL would be safer but slower
            db.execSQL("PRAGMA synchronous = NORMAL");
            
            // Use memory for temp store
            db.execSQL("PRAGMA temp_store = MEMORY");
            
            Log.d(TAG, "Database connection pool optimized");
        } catch (Exception e) {
            Log.e(TAG, "Error optimizing database connection pool", e);
        }
    }

    /**
     * Analyze the database to update statistics used by the query optimizer.
     *
     * @param database The Room database to analyze
     */
    public static void analyzeDatabase(RoomDatabase database) {
        SupportSQLiteDatabase db = database.getOpenHelper().getWritableDatabase();
        
        try {
            db.execSQL("ANALYZE");
            Log.d(TAG, "Database analyzed for query optimization");
        } catch (Exception e) {
            Log.e(TAG, "Error analyzing database", e);
        }
    }

    /**
     * Execute cleanup operations to maintain database performance.
     * This includes routine vacuuming to reclaim space and defragment the database.
     *
     * @param database The Room database to maintain
     * @return The size reduction in bytes, or -1 if operation failed
     */
    public static long performMaintenance(RoomDatabase database) {
        SupportSQLiteDatabase db = database.getOpenHelper().getWritableDatabase();
        
        try {
            // Get current size
            long sizeBefore = getDatabaseSize(db);
            
            // Run vacuum to defragment the database
            db.execSQL("VACUUM");
            
            // Get new size
            long sizeAfter = getDatabaseSize(db);
            
            long reduction = sizeBefore - sizeAfter;
            Log.d(TAG, "Database maintenance complete. Reduced by " + 
                  reduction + " bytes");
            
            return reduction;
        } catch (Exception e) {
            Log.e(TAG, "Error performing database maintenance", e);
            return -1;
        }
    }

    /**
     * Get the current database size.
     *
     * @param db The SQLite database
     * @return Size in bytes, or -1 if unable to determine
     */
    private static long getDatabaseSize(SupportSQLiteDatabase db) {
        Cursor cursor = null;
        try {
            cursor = db.query("PRAGMA page_count");
            if (cursor != null && cursor.moveToFirst()) {
                int pageCount = cursor.getInt(0);
                cursor.close();
                
                cursor = db.query("PRAGMA page_size");
                if (cursor != null && cursor.moveToFirst()) {
                    int pageSize = cursor.getInt(0);
                    return (long) pageCount * pageSize;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error getting database size", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        
        return -1;
    }

    /**
     * Check if a table contains too many rows and recommend optimization.
     *
     * @param database The Room database to check
     * @param tableName Table name to check
     * @param threshold Row count threshold to trigger warning
     * @return true if table exceeds threshold
     */
    public static boolean checkTableSize(RoomDatabase database, String tableName, int threshold) {
        SupportSQLiteDatabase db = database.getOpenHelper().getReadableDatabase();
        Cursor cursor = null;
        
        try {
            String query = "SELECT COUNT(*) FROM " + tableName;
            cursor = db.query(query);
            
            if (cursor != null && cursor.moveToFirst()) {
                int count = cursor.getInt(0);
                boolean exceedsThreshold = count > threshold;
                
                if (exceedsThreshold) {
                    Log.w(TAG, "Table " + tableName + " contains " + count + 
                         " rows, exceeding threshold of " + threshold + 
                         ". Consider implementing pagination or data purging.");
                }
                
                return exceedsThreshold;
            }
        } catch (Exception e) {
            Log.e(TAG, "Error checking table size: " + tableName, e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        
        return false;
    }
}