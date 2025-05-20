package com.example.englishhindi.cache;

/**
 * Represents a cached file entry in the database.
 * Contains metadata about the cache file including type, URL, and timestamps.
 */
public class CacheEntry {
    
    // Cache entry types
    public static final String TYPE_AUDIO = "audio";
    public static final String TYPE_IMAGE = "image";
    public static final String TYPE_DATA = "data";
    
    // Default expiration time (7 days)
    private static final long DEFAULT_EXPIRATION_TIME = 7 * 24 * 60 * 60 * 1000;
    
    // Cache entry properties
    private String url;
    private String filePath;
    private String type;
    private long timestamp;
    private long lastAccessed;
    private int priority;
    
    /**
     * Create a new cache entry
     * @param url Source URL
     * @param filePath Local file path
     * @param type Content type (audio, image, data)
     * @param timestamp Creation timestamp
     * @param priority Priority level (higher = more important)
     */
    public CacheEntry(String url, String filePath, String type, long timestamp, int priority) {
        this.url = url;
        this.filePath = filePath;
        this.type = type;
        this.timestamp = timestamp;
        this.lastAccessed = timestamp;
        this.priority = priority;
    }
    
    /**
     * Check if this cache entry is expired
     * @return true if expired
     */
    public boolean isExpired() {
        long now = System.currentTimeMillis();
        long age = now - timestamp;
        
        // Different expiration times based on type and priority
        long expirationTime;
        
        if (priority >= 80) {
            // High priority items expire after a longer time
            expirationTime = DEFAULT_EXPIRATION_TIME * 2;
        } else if (priority >= 50) {
            // Medium priority items use default expiration
            expirationTime = DEFAULT_EXPIRATION_TIME;
        } else {
            // Low priority items expire sooner
            expirationTime = DEFAULT_EXPIRATION_TIME / 2;
        }
        
        return age > expirationTime;
    }
    
    /**
     * Get the source URL
     * @return URL string
     */
    public String getUrl() {
        return url;
    }
    
    /**
     * Set the source URL
     * @param url URL string
     */
    public void setUrl(String url) {
        this.url = url;
    }
    
    /**
     * Get the local file path
     * @return File path
     */
    public String getFilePath() {
        return filePath;
    }
    
    /**
     * Set the local file path
     * @param filePath File path
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    /**
     * Get the content type
     * @return Content type (audio, image, data)
     */
    public String getType() {
        return type;
    }
    
    /**
     * Set the content type
     * @param type Content type
     */
    public void setType(String type) {
        this.type = type;
    }
    
    /**
     * Get the creation timestamp
     * @return Timestamp in milliseconds
     */
    public long getTimestamp() {
        return timestamp;
    }
    
    /**
     * Set the creation timestamp
     * @param timestamp Timestamp in milliseconds
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    
    /**
     * Get the last accessed timestamp
     * @return Timestamp in milliseconds
     */
    public long getLastAccessed() {
        return lastAccessed;
    }
    
    /**
     * Set the last accessed timestamp
     * @param lastAccessed Timestamp in milliseconds
     */
    public void setLastAccessed(long lastAccessed) {
        this.lastAccessed = lastAccessed;
    }
    
    /**
     * Get the priority level
     * @return Priority level (higher = more important)
     */
    public int getPriority() {
        return priority;
    }
    
    /**
     * Set the priority level
     * @param priority Priority level
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }
}