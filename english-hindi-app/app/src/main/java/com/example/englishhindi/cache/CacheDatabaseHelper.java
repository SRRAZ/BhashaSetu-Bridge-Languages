package com.example.englishhindi.cache;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * SQLite database helper for managing cache entries.
 * Provides methods to store, retrieve, and manage cache metadata.
 */
public class CacheDatabaseHelper extends SQLiteOpenHelper {
    
    private static final String DATABASE_NAME = "cache_db";
    private static final int DATABASE_VERSION = 1;
    
    // Table and column names
    private static final String TABLE_CACHE = "cache_entries";
    private static final String COLUMN_URL = "url";
    private static final String COLUMN_FILE_PATH = "file_path";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_TIMESTAMP = "timestamp";
    private static final String COLUMN_LAST_ACCESSED = "last_accessed";
    private static final String COLUMN_PRIORITY = "priority";
    
    // Create table SQL query
    private static final String CREATE_TABLE_CACHE = 
            "CREATE TABLE " + TABLE_CACHE + "("
            + COLUMN_URL + " TEXT PRIMARY KEY,"
            + COLUMN_FILE_PATH + " TEXT,"
            + COLUMN_TYPE + " TEXT,"
            + COLUMN_TIMESTAMP + " INTEGER,"
            + COLUMN_LAST_ACCESSED + " INTEGER,"
            + COLUMN_PRIORITY + " INTEGER"
            + ")";
    
    // Index for faster queries
    private static final String CREATE_INDEX_TYPE = 
            "CREATE INDEX idx_type_priority ON " + TABLE_CACHE + "(" 
            + COLUMN_TYPE + ", " + COLUMN_PRIORITY + ")";
    
    public CacheDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables and indexes
        db.execSQL(CREATE_TABLE_CACHE);
        db.execSQL(CREATE_INDEX_TYPE);
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // On upgrade, drop and recreate tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CACHE);
        onCreate(db);
    }
    
    /**
     * Insert a new cache entry or update if it already exists
     * @param entry The cache entry to insert
     * @return true if successful
     */
    public boolean insertOrUpdateCacheEntry(CacheEntry entry) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        
        values.put(COLUMN_URL, entry.getUrl());
        values.put(COLUMN_FILE_PATH, entry.getFilePath());
        values.put(COLUMN_TYPE, entry.getType());
        values.put(COLUMN_TIMESTAMP, entry.getTimestamp());
        values.put(COLUMN_LAST_ACCESSED, entry.getLastAccessed());
        values.put(COLUMN_PRIORITY, entry.getPriority());
        
        // Try to insert, if fails then update
        long result = db.insertWithOnConflict(TABLE_CACHE, null, values, 
                SQLiteDatabase.CONFLICT_REPLACE);
        
        db.close();
        return result != -1;
    }
    
    /**
     * Get a cache entry by its URL
     * @param url The source URL
     * @return Cache entry or null if not found
     */
    public CacheEntry getCacheEntry(String url) {
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.query(TABLE_CACHE, 
                new String[] { COLUMN_URL, COLUMN_FILE_PATH, COLUMN_TYPE, 
                        COLUMN_TIMESTAMP, COLUMN_LAST_ACCESSED, COLUMN_PRIORITY }, 
                COLUMN_URL + "=?", 
                new String[] { url }, 
                null, null, null, null);
        
        CacheEntry entry = null;
        
        if (cursor != null && cursor.moveToFirst()) {
            String filePath = cursor.getString(cursor.getColumnIndex(COLUMN_FILE_PATH));
            String type = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE));
            long timestamp = cursor.getLong(cursor.getColumnIndex(COLUMN_TIMESTAMP));
            long lastAccessed = cursor.getLong(cursor.getColumnIndex(COLUMN_LAST_ACCESSED));
            int priority = cursor.getInt(cursor.getColumnIndex(COLUMN_PRIORITY));
            
            entry = new CacheEntry(url, filePath, type, timestamp, priority);
            entry.setLastAccessed(lastAccessed);
            
            cursor.close();
        }
        
        db.close();
        return entry;
    }
    
    /**
     * Delete a cache entry by its URL
     * @param url The source URL
     * @return true if successful
     */
    public boolean deleteCacheEntry(String url) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_CACHE, COLUMN_URL + "=?", new String[] { url });
        db.close();
        return result > 0;
    }
    
    /**
     * Update the last accessed time for a cache entry
     * @param url The source URL
     * @param lastAccessed New last accessed timestamp
     * @return true if successful
     */
    public boolean updateLastAccessedTime(String url, long lastAccessed) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_LAST_ACCESSED, lastAccessed);
        
        int result = db.update(TABLE_CACHE, values, COLUMN_URL + "=?", new String[] { url });
        db.close();
        return result > 0;
    }
    
    /**
     * Update the timestamp for a cache entry
     * @param url The source URL
     * @param timestamp New timestamp
     * @return true if successful
     */
    public boolean updateTimestamp(String url, long timestamp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TIMESTAMP, timestamp);
        
        int result = db.update(TABLE_CACHE, values, COLUMN_URL + "=?", new String[] { url });
        db.close();
        return result > 0;
    }
    
    /**
     * Get all cache entries older than a specific time
     * @param timestamp Cutoff timestamp
     * @return List of old cache entries
     */
    public List<CacheEntry> getEntriesOlderThan(long timestamp) {
        List<CacheEntry> entries = new ArrayList<>();
        
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CACHE, 
                new String[] { COLUMN_URL, COLUMN_FILE_PATH, COLUMN_TYPE, 
                        COLUMN_TIMESTAMP, COLUMN_LAST_ACCESSED, COLUMN_PRIORITY }, 
                COLUMN_TIMESTAMP + " < ?", 
                new String[] { String.valueOf(timestamp) }, 
                null, null, null);
        
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String url = cursor.getString(cursor.getColumnIndex(COLUMN_URL));
                String filePath = cursor.getString(cursor.getColumnIndex(COLUMN_FILE_PATH));
                String type = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE));
                long entryTimestamp = cursor.getLong(cursor.getColumnIndex(COLUMN_TIMESTAMP));
                long lastAccessed = cursor.getLong(cursor.getColumnIndex(COLUMN_LAST_ACCESSED));
                int priority = cursor.getInt(cursor.getColumnIndex(COLUMN_PRIORITY));
                
                CacheEntry entry = new CacheEntry(url, filePath, type, entryTimestamp, priority);
                entry.setLastAccessed(lastAccessed);
                
                entries.add(entry);
            } while (cursor.moveToNext());
            
            cursor.close();
        }
        
        db.close();
        return entries;
    }
    
    /**
     * Get entries with priority lower than specified
     * @param minPriority Minimum priority to exclude
     * @param limit Maximum number of entries to return
     * @return List of low priority cache entries
     */
    public List<CacheEntry> getLowPriorityEntries(int minPriority, int limit) {
        List<CacheEntry> entries = new ArrayList<>();
        
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CACHE, 
                new String[] { COLUMN_URL, COLUMN_FILE_PATH, COLUMN_TYPE, 
                        COLUMN_TIMESTAMP, COLUMN_LAST_ACCESSED, COLUMN_PRIORITY }, 
                COLUMN_PRIORITY + " < ?", 
                new String[] { String.valueOf(minPriority) }, 
                null, null, COLUMN_PRIORITY + " ASC, " + COLUMN_LAST_ACCESSED + " ASC", 
                String.valueOf(limit));
        
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String url = cursor.getString(cursor.getColumnIndex(COLUMN_URL));
                String filePath = cursor.getString(cursor.getColumnIndex(COLUMN_FILE_PATH));
                String type = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE));
                long timestamp = cursor.getLong(cursor.getColumnIndex(COLUMN_TIMESTAMP));
                long lastAccessed = cursor.getLong(cursor.getColumnIndex(COLUMN_LAST_ACCESSED));
                int priority = cursor.getInt(cursor.getColumnIndex(COLUMN_PRIORITY));
                
                CacheEntry entry = new CacheEntry(url, filePath, type, timestamp, priority);
                entry.setLastAccessed(lastAccessed);
                
                entries.add(entry);
            } while (cursor.moveToNext());
            
            cursor.close();
        }
        
        db.close();
        return entries;
    }
    
    /**
     * Get all cache entries
     * @return List of all cache entries
     */
    public List<CacheEntry> getAllCacheEntries() {
        List<CacheEntry> entries = new ArrayList<>();
        
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CACHE, 
                new String[] { COLUMN_URL, COLUMN_FILE_PATH, COLUMN_TYPE, 
                        COLUMN_TIMESTAMP, COLUMN_LAST_ACCESSED, COLUMN_PRIORITY }, 
                null, null, null, null, null);
        
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String url = cursor.getString(cursor.getColumnIndex(COLUMN_URL));
                String filePath = cursor.getString(cursor.getColumnIndex(COLUMN_FILE_PATH));
                String type = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE));
                long timestamp = cursor.getLong(cursor.getColumnIndex(COLUMN_TIMESTAMP));
                long lastAccessed = cursor.getLong(cursor.getColumnIndex(COLUMN_LAST_ACCESSED));
                int priority = cursor.getInt(cursor.getColumnIndex(COLUMN_PRIORITY));
                
                CacheEntry entry = new CacheEntry(url, filePath, type, timestamp, priority);
                entry.setLastAccessed(lastAccessed);
                
                entries.add(entry);
            } while (cursor.moveToNext());
            
            cursor.close();
        }
        
        db.close();
        return entries;
    }
    
    /**
     * Get cache entries of a specific type
     * @param type Content type (audio, image, data)
     * @return List of cache entries of the specified type
     */
    public List<CacheEntry> getCacheEntriesByType(String type) {
        List<CacheEntry> entries = new ArrayList<>();
        
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CACHE, 
                new String[] { COLUMN_URL, COLUMN_FILE_PATH, COLUMN_TYPE, 
                        COLUMN_TIMESTAMP, COLUMN_LAST_ACCESSED, COLUMN_PRIORITY }, 
                COLUMN_TYPE + "=?", 
                new String[] { type }, 
                null, null, null);
        
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String url = cursor.getString(cursor.getColumnIndex(COLUMN_URL));
                String filePath = cursor.getString(cursor.getColumnIndex(COLUMN_FILE_PATH));
                long timestamp = cursor.getLong(cursor.getColumnIndex(COLUMN_TIMESTAMP));
                long lastAccessed = cursor.getLong(cursor.getColumnIndex(COLUMN_LAST_ACCESSED));
                int priority = cursor.getInt(cursor.getColumnIndex(COLUMN_PRIORITY));
                
                CacheEntry entry = new CacheEntry(url, filePath, type, timestamp, priority);
                entry.setLastAccessed(lastAccessed);
                
                entries.add(entry);
            } while (cursor.moveToNext());
            
            cursor.close();
        }
        
        db.close();
        return entries;
    }
    
    /**
     * Count cache entries of a specific type
     * @param type Content type (audio, image, data)
     * @return Number of entries
     */
    public int countCacheEntriesByType(String type) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_CACHE + 
                " WHERE " + COLUMN_TYPE + "=?", new String[] { type });
        
        int count = 0;
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
            cursor.close();
        }
        
        db.close();
        return count;
    }
    
    /**
     * Get the total count of cache entries
     * @return Number of entries
     */
    public int getTotalCacheEntryCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_CACHE, null);
        
        int count = 0;
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
            cursor.close();
        }
        
        db.close();
        return count;
    }
}