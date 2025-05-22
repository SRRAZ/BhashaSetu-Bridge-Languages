package com.example.englishhindi.cache;

/**
 * Enhanced model class for a cache entry with additional tracking fields.
 */
public class CacheEntry {
    
    // Cache entry types
    public static final String TYPE_AUDIO = "audio";
    public static final String TYPE_IMAGE = "image";
    public static final String TYPE_DATA = "data";
    
    // Default expiration time: 30 days
    private static final long DEFAULT_EXPIRATION_TIME = 30L * 24 * 60 * 60 * 1000;
    
    private String url;
    private String filePath;
    private String type;
    private long timestamp;
    private long lastAccessed;
    private int priority;
    private int usageCount;
    private boolean validated;
    
    /**
     * Create a new cache entry
     * @param url Original URL
     * @param filePath Local file path
     * @param type Type of cache (audio, image, data)
     * @param timestamp Creation timestamp
     * @param priority Priority level
     */
    public CacheEntry(String url, String filePath, String type, long timestamp, int priority) {
        this.url = url;
        this.filePath = filePath;
        this.type = type;
        this.timestamp = timestamp;
        this.lastAccessed = timestamp; // Initially same as timestamp
        this.priority = priority;
        this.usageCount = 0;
        this.validated = true; // New entries are considered validated
    }
    
    /**
     * Check if this cache entry is expired
     * @return true if expired
     */
    public boolean isExpired() {
        // Higher priority items expire more slowly
        long expirationTime = DEFAULT_EXPIRATION_TIME;
        
        // Adjust expiration time based on priority and usage
        if (priority > 75) {
            // Very high priority - 60 days for frequent items
            expirationTime = 60L * 24 * 60 * 60 * 1000;
        } else if (priority > 50) {
            // High priority - 45 days
            expirationTime = 45L * 24 * 60 * 60 * 1000;
        } else if (priority < 25) {
            // Low priority - 15 days
            expirationTime = 15L * 24 * 60 * 60 * 1000;
        }
        
        // Adjust for usage - used items expire more slowly
        if (usageCount > 10) {
            expirationTime *= 1.5;
        } else if (usageCount > 5) {
            expirationTime *= 1.25;
        }
        
        return System.currentTimeMillis() - timestamp > expirationTime;
    }
    
    /**
     * Check if this entry needs validation
     * @return true if validation is needed
     */
    public boolean needsValidation() {
        // If not validated, we need validation
        if (!validated) {
            return true;
        }
        
        // If it's been more than 7 days since last validation, we need validation
        return System.currentTimeMillis() - timestamp > 7 * 24 * 60 * 60 * 1000;
    }
    
    // Getters and setters
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getFilePath() {
        return filePath;
    }
    
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public long getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    
    public long getLastAccessed() {
        return lastAccessed;
    }
    
    public void setLastAccessed(long lastAccessed) {
        this.lastAccessed = lastAccessed;
    }
    
    public int getPriority() {
        return priority;
    }
    
    public void setPriority(int priority) {
        this.priority = priority;
    }
    
    public int getUsageCount() {
        return usageCount;
    }
    
    public void setUsageCount(int usageCount) {
        this.usageCount = usageCount;
    }
    
    public boolean isValidated() {
        return validated;
    }
    
    public void setValidated(boolean validated) {
        this.validated = validated;
    }
    
    /**
     * Increment the usage count
     */
    public void incrementUsageCount() {
        this.usageCount++;
    }
    
    /**
     * Calculate the overall importance of this cache entry
     * based on priority, recency, and usage
     * @return Importance score (higher = more important)
     */
    public int calculateImportance() {
        // Start with base priority
        int importance = priority;
        
        // Add usage factor (each use adds up to 20 points)
        importance += Math.min(20, usageCount * 2);
        
        // Add recency factor (more recent = more important)
        long ageHours = (System.currentTimeMillis() - lastAccessed) / (1000 * 60 * 60);
        if (ageHours < 24) {
            // Used in last 24 hours - add up to 30 points
            importance += 30 - (int)(ageHours * 1.25);
        } else if (ageHours < 168) { // 7 days
            // Used in last week - add up to 15 points
            importance += 15 - (int)((ageHours - 24) / 12);
        }
        
        return importance;
    }
}