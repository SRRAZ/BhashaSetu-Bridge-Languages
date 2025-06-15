package com.bhashasetu.app.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.LruCache;
import android.util.SparseArray;
import android.util.SparseIntArray;

import java.lang.ref.WeakReference;

/**
 * Utility class providing memory optimization techniques for the application.
 * Implements best practices for efficient memory usage on Android.
 */
public class MemoryOptimization {

    // Memory cache for bitmaps to prevent repeated decoding
    private static LruCache<String, Bitmap> memoryCache;
    
    /**
     * Initializes the memory optimization components.
     * Should be called during application startup.
     */
    public static void initialize(Context context) {
        // Use 1/8th of the available memory for this memory cache
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        
        memoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes
                return getBitmapSize(bitmap) / 1024;
            }
        };
    }
    
    /**
     * Gets the size of a bitmap in bytes.
     */
    public static int getBitmapSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return bitmap.getAllocationByteCount();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return bitmap.getByteCount();
        } else {
            return bitmap.getRowBytes() * bitmap.getHeight();
        }
    }
    
    /**
     * Adds a bitmap to the memory cache.
     */
    public static void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemoryCache(key) == null && bitmap != null && !bitmap.isRecycled()) {
            memoryCache.put(key, bitmap);
        }
    }
    
    /**
     * Gets a bitmap from the memory cache.
     */
    public static Bitmap getBitmapFromMemoryCache(String key) {
        return memoryCache != null ? memoryCache.get(key) : null;
    }
    
    /**
     * Clears the memory cache.
     */
    public static void clearMemoryCache() {
        if (memoryCache != null) {
            memoryCache.evictAll();
        }
    }
    
    /**
     * Optimized data structure for mapping integers to objects.
     * More memory efficient than HashMap<Integer, E> for primitive int keys.
     */
    public static <E> SparseArray<E> createSparseArray() {
        return new SparseArray<>();
    }
    
    /**
     * Optimized data structure for mapping integers to integers.
     * More memory efficient than HashMap<Integer, Integer>.
     */
    public static SparseIntArray createSparseIntArray() {
        return new SparseIntArray();
    }
    
    /**
     * Creates a weak reference to an object to prevent memory leaks.
     * Useful for storing references to Context or Activity.
     */
    public static <T> WeakReference<T> createWeakReference(T referent) {
        return new WeakReference<>(referent);
    }
    
    /**
     * Trim memory based on the level provided by the system.
     * Should be called from Application.onTrimMemory().
     */
    public static void trimMemory(int level) {
        if (level >= android.content.ComponentCallbacks2.TRIM_MEMORY_MODERATE) {
            // Clear the memory cache completely
            clearMemoryCache();
        } else if (level >= android.content.ComponentCallbacks2.TRIM_MEMORY_BACKGROUND) {
            // Clear half of the memory cache
            if (memoryCache != null) {
                memoryCache.trimToSize(memoryCache.size() / 2);
            }
        }
    }
}