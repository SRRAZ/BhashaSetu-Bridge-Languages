package com.example.englishhindi.cache;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Enhanced database helper for cache entries with additional queries
 * for optimized cache management.
 */
public class CacheDatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "CacheDatabaseHelper";

    private static final String DATABASE_NAME = "cache.db";
    private static final int DATABASE_VERSION = 2; // Incremented version for new features

    private static final String TABLE_CACHE = "cache_entries";
    private static final String COLUMN_URL = "url";
    private static final String COLUMN_FILE_PATH = "file_path";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_TIMESTAMP = "timestamp";
    private static final String COLUMN_LAST_ACCESSED = "last_accessed";
    private static final String COLUMN_PRIORITY = "priority";
    // New column for tracking usage count
    private static final String COLUMN_USAGE_COUNT = "usage_count";
    // New column for validation status
    private static final String COLUMN_VALIDATED = "validated";

    public CacheDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_CACHE + " (" +
                COLUMN_URL + " TEXT PRIMARY KEY, " +
                COLUMN_FILE_PATH + " TEXT NOT NULL, " +
                COLUMN_TYPE + " TEXT NOT NULL, " +
                COLUMN_TIMESTAMP + " INTEGER NOT NULL, " +
                COLUMN_LAST_ACCESSED + " INTEGER NOT NULL, " +
                COLUMN_PRIORITY + " INTEGER DEFAULT 0, " +
                COLUMN_USAGE_COUNT + " INTEGER DEFAULT 0, " +
                COLUMN_VALIDATED + " INTEGER DEFAULT 0" +
                ");";
        
        db.execSQL(createTableQuery);
        
        // Create indexes for faster queries
        db.execSQL("CREATE INDEX idx_cache_type ON " + TABLE_CACHE + " (" + COLUMN_TYPE + ");");
        db.execSQL("CREATE INDEX idx_cache_priority ON " + TABLE_CACHE + " (" + COLUMN_PRIORITY + ");");
        db.execSQL("CREATE INDEX idx_cache_last_accessed ON " + TABLE_CACHE + " (" + COLUMN_LAST_ACCESSED + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // Add new columns for v2
            db.execSQL("ALTER TABLE " + TABLE_CACHE + " ADD COLUMN " + COLUMN_USAGE_COUNT + " INTEGER DEFAULT 0;");
            db.execSQL("ALTER TABLE " + TABLE_CACHE + " ADD COLUMN " + COLUMN_VALIDATED + " INTEGER DEFAULT 0;");
            
            // Create new indexes
            db.execSQL("CREATE INDEX idx_cache_type ON " + TABLE_CACHE + " (" + COLUMN_TYPE + ");");
            db.execSQL("CREATE INDEX idx_cache_priority ON " + TABLE_CACHE + " (" + COLUMN_PRIORITY + ");");
            db.execSQL("CREATE INDEX idx_cache_last_accessed ON " + TABLE_CACHE + " (" + COLUMN_LAST_ACCESSED + ");");
        }
    }

    /**
     * Insert or update a cache entry
     * @param entry The cache entry
     * @return true if successful
     */
    public boolean insertOrUpdateCacheEntry(CacheEntry entry) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            
            ContentValues values = new ContentValues();
            values.put(COLUMN_FILE_PATH, entry.getFilePath());
            values.put(COLUMN_TYPE, entry.getType());
            values.put(COLUMN_TIMESTAMP, entry.getTimestamp());
            values.put(COLUMN_LAST_ACCESSED, entry.getLastAccessed());
            values.put(COLUMN_PRIORITY, entry.getPriority());
            values.put(COLUMN_VALIDATED, 1); // New entries are validated
            
            // Check if entry exists
            Cursor cursor = db.query(
                    TABLE_CACHE,
                    new String[]{COLUMN_URL, COLUMN_USAGE_COUNT},
                    COLUMN_URL + " = ?",
                    new String[]{entry.getUrl()},
                    null, null, null);
            
            boolean exists = cursor.moveToFirst();
            
            if (exists) {
                // Update existing entry
                int usageCount = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USAGE_COUNT));
                values.put(COLUMN_USAGE_COUNT, usageCount + 1); // Increment usage count
                
                db.update(
                        TABLE_CACHE,
                        values,
                        COLUMN_URL + " = ?",
                        new String[]{entry.getUrl()});
            } else {
                // Insert new entry
                values.put(COLUMN_URL, entry.getUrl());
                values.put(COLUMN_USAGE_COUNT, 1); // Initial usage count
                
                db.insert(TABLE_CACHE, null, values);
            }
            
            cursor.close();
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error inserting/updating cache entry", e);
            return false;
        }
    }

    /**
     * Get a cache entry by URL
     * @param url The URL
     * @return The cache entry, or null if not found
     */
    public CacheEntry getCacheEntry(String url) {
        SQLiteDatabase db = getReadableDatabase();
        
        Cursor cursor = db.query(
                TABLE_CACHE,
                null,
                COLUMN_URL + " = ?",
                new String[]{url},
                null, null, null);
        
        CacheEntry entry = null;
        
        if (cursor.moveToFirst()) {
            entry = cursorToCacheEntry(cursor);
            
            // Increment usage count
            ContentValues values = new ContentValues();
            values.put(COLUMN_USAGE_COUNT, entry.getUsageCount() + 1);
            
            db.update(
                    TABLE_CACHE,
                    values,
                    COLUMN_URL + " = ?",
                    new String[]{url});
        }
        
        cursor.close();
        return entry;
    }

    /**
     * Get all cache entries
     * @return List of all cache entries
     */
    public List<CacheEntry> getAllCacheEntries() {
        List<CacheEntry> entries = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        
        Cursor cursor = db.query(
                TABLE_CACHE,
                null,
                null, null, null, null, null);
        
        if (cursor.moveToFirst()) {
            do {
                entries.add(cursorToCacheEntry(cursor));
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        return entries;
    }

    /**
     * Get entries of a specific type
     * @param type The cache entry type
     * @return List of cache entries of the specified type
     */
    public List<CacheEntry> getEntriesByType(String type) {
        List<CacheEntry> entries = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        
        Cursor cursor = db.query(
                TABLE_CACHE,
                null,
                COLUMN_TYPE + " = ?",
                new String[]{type},
                null, null, null);
        
        if (cursor.moveToFirst()) {
            do {
                entries.add(cursorToCacheEntry(cursor));
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        return entries;
    }

    /**
     * Get entries older than a specific timestamp
     * @param timestamp Entries older than this timestamp will be returned
     * @return List of old cache entries
     */
    public List<CacheEntry> getEntriesOlderThan(long timestamp) {
        List<CacheEntry> entries = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        
        Cursor cursor = db.query(
                TABLE_CACHE,
                null,
                COLUMN_TIMESTAMP + " < ?",
                new String[]{String.valueOf(timestamp)},
                null, null, null);
        
        if (cursor.moveToFirst()) {
            do {
                entries.add(cursorToCacheEntry(cursor));
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        return entries;
    }

    /**
     * Get low priority entries
     * @param maxPriority Maximum priority to include
     * @param limit Maximum number of entries to return
     * @return List of low priority cache entries
     */
    public List<CacheEntry> getLowPriorityEntries(int maxPriority, int limit) {
        List<CacheEntry> entries = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        
        Cursor cursor = db.query(
                TABLE_CACHE,
                null,
                COLUMN_PRIORITY + " < ?",
                new String[]{String.valueOf(maxPriority)},
                null, null,
                COLUMN_PRIORITY + " ASC, " + COLUMN_LAST_ACCESSED + " ASC",
                String.valueOf(limit));
        
        if (cursor.moveToFirst()) {
            do {
                entries.add(cursorToCacheEntry(cursor));
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        return entries;
    }

    /**
     * Get least recently used entries
     * @param limit Maximum number of entries to return
     * @return List of least recently used cache entries
     */
    public List<CacheEntry> getLeastRecentlyUsedEntries(int limit) {
        List<CacheEntry> entries = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        
        Cursor cursor = db.query(
                TABLE_CACHE,
                null,
                null, null, null, null,
                COLUMN_LAST_ACCESSED + " ASC, " + COLUMN_USAGE_COUNT + " ASC",
                String.valueOf(limit));
        
        if (cursor.moveToFirst()) {
            do {
                entries.add(cursorToCacheEntry(cursor));
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        return entries;
    }

    /**
     * Get entries that haven't been validated recently
     * @param limit Maximum number of entries to return
     * @return List of unvalidated cache entries
     */
    public List<CacheEntry> getUnvalidatedEntries(int limit) {
        List<CacheEntry> entries = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        
        Cursor cursor = db.query(
                TABLE_CACHE,
                null,
                COLUMN_VALIDATED + " = 0",
                null, null, null,
                COLUMN_PRIORITY + " DESC",
                String.valueOf(limit));
        
        if (cursor.moveToFirst()) {
            do {
                entries.add(cursorToCacheEntry(cursor));
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        return entries;
    }

    /**
     * Update last accessed time for a cache entry
     * @param url The URL
     * @param lastAccessed New last accessed timestamp
     */
    public void updateLastAccessedTime(String url, long lastAccessed) {
        SQLiteDatabase db = getWritableDatabase();
        
        ContentValues values = new ContentValues();
        values.put(COLUMN_LAST_ACCESSED, lastAccessed);
        
        db.update(
                TABLE_CACHE,
                values,
                COLUMN_URL + " = ?",
                new String[]{url});
    }

    /**
     * Update timestamp for a cache entry
     * @param url The URL
     * @param timestamp New timestamp
     */
    public void updateTimestamp(String url, long timestamp) {
        SQLiteDatabase db = getWritableDatabase();
        
        ContentValues values = new ContentValues();
        values.put(COLUMN_TIMESTAMP, timestamp);
        values.put(COLUMN_VALIDATED, 1); // Mark as validated
        
        db.update(
                TABLE_CACHE,
                values,
                COLUMN_URL + " = ?",
                new String[]{url});
    }

    /**
     * Mark a cache entry as validated
     * @param url The URL
     */
    public void markAsValidated(String url) {
        SQLiteDatabase db = getWritableDatabase();
        
        ContentValues values = new ContentValues();
        values.put(COLUMN_VALIDATED, 1);
        
        db.update(
                TABLE_CACHE,
                values,
                COLUMN_URL + " = ?",
                new String[]{url});
    }

    /**
     * Update priority for a cache entry
     * @param url The URL
     * @param priority New priority
     */
    public void updatePriority(String url, int priority) {
        SQLiteDatabase db = getWritableDatabase();
        
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRIORITY, priority);
        
        db.update(
                TABLE_CACHE,
                values,
                COLUMN_URL + " = ?",
                new String[]{url});
    }

    /**
     * Delete a cache entry
     * @param url The URL
     */
    public void deleteCacheEntry(String url) {
        SQLiteDatabase db = getWritableDatabase();
        
        db.delete(
                TABLE_CACHE,
                COLUMN_URL + " = ?",
                new String[]{url});
    }

    /**
     * Delete all cache entries
     */
    public void deleteAllEntries() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_CACHE, null, null);
    }

    /**
     * Convert a cursor to a cache entry
     * @param cursor Database cursor
     * @return Cache entry
     */
    private CacheEntry cursorToCacheEntry(Cursor cursor) {
        String url = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_URL));
        String filePath = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FILE_PATH));
        String type = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE));
        long timestamp = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_TIMESTAMP));
        long lastAccessed = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_LAST_ACCESSED));
        int priority = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRIORITY));
        
        // Get additional fields
        int usageCount = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USAGE_COUNT));
        boolean validated = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_VALIDATED)) == 1;
        
        CacheEntry entry = new CacheEntry(url, filePath, type, timestamp, priority);
        entry.setLastAccessed(lastAccessed);
        entry.setUsageCount(usageCount);
        entry.setValidated(validated);
        
        return entry;
    }
}