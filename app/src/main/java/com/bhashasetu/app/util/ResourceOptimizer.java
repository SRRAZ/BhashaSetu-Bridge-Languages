package com.bhashasetu.app.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.util.Log;
import android.util.LruCache;
import android.view.View;

import androidx.annotation.DrawableRes;
import androidx.annotation.RawRes;
import androidx.collection.ArrayMap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Resource optimization utility that efficiently manages app resources like drawables and raw assets.
 * Implements efficient loading, caching, and adaptive quality based on device capabilities.
 */
public class ResourceOptimizer {
    private static final String TAG = "ResourceOptimizer";
    
    // Singleton instance
    private static ResourceOptimizer instance;
    
    // Context reference
    private final WeakReference<Context> contextReference;
    
    // Resource caches
    private final LruCache<Integer, Drawable> drawableCache;
    private final LruCache<String, Bitmap> bitmapCache;
    private final ConcurrentHashMap<Integer, WeakReference<MediaPlayer>> mediaPlayerCache;
    
    // Resource quality settings
    private final ArrayMap<String, Integer> qualitySettings = new ArrayMap<>();
    
    // Background thread for resource loading
    private final Executor backgroundExecutor;
    
    // Resource reference tracking
    private final List<Integer> loadedResources = new ArrayList<>();
    
    // Resource decoders
    private final ArrayMap<String, BitmapFactory.Options> decoderOptions = new ArrayMap<>();
    
    /**
     * Private constructor for singleton pattern.
     *
     * @param context Application context
     */
    private ResourceOptimizer(Context context) {
        this.contextReference = new WeakReference<>(context.getApplicationContext());
        
        // Initialize executor for background loading
        backgroundExecutor = Executors.newFixedThreadPool(2);
        
        // Calculate cache sizes based on available memory
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        
        // Use 1/8th of available memory for drawable cache
        final int drawableCacheSize = maxMemory / 8;
        drawableCache = new LruCache<Integer, Drawable>(drawableCacheSize) {
            @Override
            protected int sizeOf(Integer key, Drawable drawable) {
                if (drawable instanceof BitmapDrawable) {
                    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                    return bitmap == null ? 1 : bitmap.getByteCount() / 1024;
                }
                return 1;
            }
        };
        
        // Use 1/4th of available memory for bitmap cache
        final int bitmapCacheSize = maxMemory / 4;
        bitmapCache = new LruCache<String, Bitmap>(bitmapCacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
            
            @Override
            protected void entryRemoved(boolean evicted, String key, 
                                      Bitmap oldValue, Bitmap newValue) {
                if (evicted && oldValue != null && !oldValue.isRecycled()) {
                    oldValue.recycle();
                }
            }
        };
        
        // Initialize media player cache
        mediaPlayerCache = new ConcurrentHashMap<>();
        
        // Initialize default quality settings
        initDefaultQualitySettings();
        
        // Initialize decoder options
        initDecoderOptions();
    }
    
    /**
     * Get singleton instance.
     *
     * @param context Context
     * @return ResourceOptimizer instance
     */
    public static ResourceOptimizer getInstance(Context context) {
        if (instance == null) {
            synchronized (ResourceOptimizer.class) {
                if (instance == null) {
                    instance = new ResourceOptimizer(context);
                }
            }
        }
        return instance;
    }
    
    /**
     * Initialize default quality settings.
     */
    private void initDefaultQualitySettings() {
        Context context = contextReference.get();
        if (context == null) return;
        
        boolean isLowMemory = MemoryOptimizer.isLowMemoryDevice(context);
        
        // Set default quality levels based on device capabilities
        qualitySettings.put("bitmap_quality", isLowMemory ? 75 : 90);
        qualitySettings.put("audio_quality", isLowMemory ? 1 : 2); // 1=low, 2=high
        qualitySettings.put("max_texture_size", isLowMemory ? 1024 : 2048);
        qualitySettings.put("prefer_rgb565", isLowMemory);
    }
    
    /**
     * Initialize decoder options for different resource types.
     */
    private void initDecoderOptions() {
        // Options for standard images
        BitmapFactory.Options standardOptions = new BitmapFactory.Options();
        standardOptions.inPreferredConfig = 
                getBooleanQuality("prefer_rgb565") ? Bitmap.Config.RGB_565 : Bitmap.Config.ARGB_8888;
        decoderOptions.put("standard", standardOptions);
        
        // Options for UI icons (always high quality)
        BitmapFactory.Options iconOptions = new BitmapFactory.Options();
        iconOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
        decoderOptions.put("icon", iconOptions);
        
        // Options for thumbnails (lower quality acceptable)
        BitmapFactory.Options thumbnailOptions = new BitmapFactory.Options();
        thumbnailOptions.inPreferredConfig = Bitmap.Config.RGB_565;
        thumbnailOptions.inSampleSize = 2;
        decoderOptions.put("thumbnail", thumbnailOptions);
    }
    
    /**
     * Get integer quality setting.
     *
     * @param key Setting key
     * @return Quality value
     */
    public int getIntQuality(String key) {
        Integer value = qualitySettings.get(key);
        return value != null ? value : 0;
    }
    
    /**
     * Get boolean quality setting.
     *
     * @param key Setting key
     * @return Quality value
     */
    public boolean getBooleanQuality(String key) {
        Integer value = qualitySettings.get(key);
        return value != null && value == 1;
    }
    
    /**
     * Set quality setting.
     *
     * @param key   Setting key
     * @param value Quality value
     */
    public void setQuality(String key, int value) {
        qualitySettings.put(key, value);
    }
    
    /**
     * Load drawable resource efficiently.
     *
     * @param resId Resource ID
     * @return Drawable or null if resource not found
     */
    public Drawable loadDrawable(@DrawableRes int resId) {
        // Check cache first
        Drawable cached = drawableCache.get(resId);
        if (cached != null) {
            return cached;
        }
        
        Context context = contextReference.get();
        if (context == null) return null;
        
        try {
            // Load from resources
            Drawable drawable;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                drawable = context.getDrawable(resId);
            } else {
                drawable = context.getResources().getDrawable(resId);
            }
            
            // Cache for future use
            if (drawable != null) {
                drawableCache.put(resId, drawable);
                trackResourceLoaded(resId);
            }
            
            return drawable;
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Resource not found: " + resId, e);
            return null;
        }
    }
    
    /**
     * Track resource loaded for memory usage analysis.
     *
     * @param resId Resource ID
     */
    private void trackResourceLoaded(int resId) {
        synchronized (loadedResources) {
            if (!loadedResources.contains(resId)) {
                loadedResources.add(resId);
            }
        }
    }
    
    /**
     * Load a bitmap resource efficiently with adaptive quality.
     *
     * @param resId Resource ID
     * @param type  Resource type (standard, icon, thumbnail)
     * @return Bitmap or null if resource not found
     */
    public Bitmap loadBitmap(@DrawableRes int resId, String type) {
        // Generate cache key
        String cacheKey = resId + "_" + type;
        
        // Check cache first
        Bitmap cached = bitmapCache.get(cacheKey);
        if (cached != null && !cached.isRecycled()) {
            return cached;
        }
        
        Context context = contextReference.get();
        if (context == null) return null;
        
        try {
            // Get appropriate decoder options
            BitmapFactory.Options options = decoderOptions.get(type);
            if (options == null) {
                options = decoderOptions.get("standard");
            }
            
            // Load bitmap with options
            Bitmap bitmap = BitmapFactory.decodeResource(
                    context.getResources(), resId, options);
            
            // Cache for future use
            if (bitmap != null) {
                bitmapCache.put(cacheKey, bitmap);
                trackResourceLoaded(resId);
            }
            
            return bitmap;
        } catch (Exception e) {
            Log.e(TAG, "Error loading bitmap resource: " + resId, e);
            return null;
        }
    }
    
    /**
     * Load a bitmap resource efficiently with specified dimensions.
     *
     * @param resId     Resource ID
     * @param reqWidth  Required width
     * @param reqHeight Required height
     * @return Bitmap or null if resource not found
     */
    public Bitmap loadScaledBitmap(@DrawableRes int resId, int reqWidth, int reqHeight) {
        // Generate cache key
        String cacheKey = resId + "_" + reqWidth + "x" + reqHeight;
        
        // Check cache first
        Bitmap cached = bitmapCache.get(cacheKey);
        if (cached != null && !cached.isRecycled()) {
            return cached;
        }
        
        Context context = contextReference.get();
        if (context == null) return null;
        
        try {
            // First decode with inJustDecodeBounds=true to check dimensions
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(context.getResources(), resId, options);
            
            // Calculate inSampleSize
            options.inSampleSize = MemoryOptimizer.calculateInSampleSize(
                    options, reqWidth, reqHeight);
            
            // Use appropriate bitmap config based on device memory class
            options.inPreferredConfig = 
                    getBooleanQuality("prefer_rgb565") ? Bitmap.Config.RGB_565 : Bitmap.Config.ARGB_8888;
            
            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeResource(
                    context.getResources(), resId, options);
            
            // Cache for future use
            if (bitmap != null) {
                bitmapCache.put(cacheKey, bitmap);
                trackResourceLoaded(resId);
            }
            
            return bitmap;
        } catch (Exception e) {
            Log.e(TAG, "Error loading scaled bitmap resource: " + resId, e);
            return null;
        }
    }
    
    /**
     * Load a bitmap resource asynchronously.
     *
     * @param resId     Resource ID
     * @param reqWidth  Required width
     * @param reqHeight Required height
     * @param callback  Callback to handle loaded bitmap
     */
    public void loadBitmapAsync(@DrawableRes final int resId, 
                              final int reqWidth, final int reqHeight,
                              final BitmapCallback callback) {
        // Generate cache key
        final String cacheKey = resId + "_" + reqWidth + "x" + reqHeight;
        
        // Check cache first
        Bitmap cached = bitmapCache.get(cacheKey);
        if (cached != null && !cached.isRecycled()) {
            callback.onBitmapLoaded(cached);
            return;
        }
        
        // Load in background
        backgroundExecutor.execute(() -> {
            final Bitmap bitmap = loadScaledBitmap(resId, reqWidth, reqHeight);
            
            // Post result to main thread
            new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                if (bitmap != null) {
                    callback.onBitmapLoaded(bitmap);
                } else {
                    callback.onBitmapFailed(new Exception("Failed to load bitmap"));
                }
            });
        });
    }
    
    /**
     * Callback interface for asynchronous bitmap loading.
     */
    public interface BitmapCallback {
        void onBitmapLoaded(Bitmap bitmap);
        void onBitmapFailed(Exception e);
    }
    
    /**
     * Create a media player for a raw resource.
     *
     * @param resId    Resource ID
     * @param looping  Whether to loop playback
     * @param callback Callback to handle prepared media player
     */
    public void createMediaPlayer(@RawRes final int resId, 
                                final boolean looping,
                                final MediaPlayerCallback callback) {
        // Check cache first
        WeakReference<MediaPlayer> cachedRef = mediaPlayerCache.get(resId);
        if (cachedRef != null) {
            MediaPlayer cached = cachedRef.get();
            if (cached != null) {
                try {
                    cached.seekTo(0);
                    cached.setLooping(looping);
                    callback.onMediaPlayerPrepared(cached);
                    return;
                } catch (Exception e) {
                    // Media player in bad state, create new one
                    mediaPlayerCache.remove(resId);
                }
            }
        }
        
        // Get context
        final Context context = contextReference.get();
        if (context == null) {
            callback.onMediaPlayerFailed(new Exception("Context not available"));
            return;
        }
        
        // Create in background
        backgroundExecutor.execute(() -> {
            try {
                MediaPlayer mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(context.getResources()
                        .openRawResourceFd(resId));
                mediaPlayer.setLooping(looping);
                mediaPlayer.prepare();
                
                // Set audio quality based on settings
                int audioQuality = getIntQuality("audio_quality");
                if (audioQuality == 1) {
                    // Lower quality (saves resources)
                    mediaPlayer.setVolume(0.8f, 0.8f);
                }
                
                // Cache for future use
                mediaPlayerCache.put(resId, new WeakReference<>(mediaPlayer));
                trackResourceLoaded(resId);
                
                // Post result to main thread
                new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> 
                    callback.onMediaPlayerPrepared(mediaPlayer)
                );
            } catch (Exception e) {
                Log.e(TAG, "Error creating media player for resource: " + resId, e);
                new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> 
                    callback.onMediaPlayerFailed(e)
                );
            }
        });
    }
    
    /**
     * Callback interface for asynchronous media player creation.
     */
    public interface MediaPlayerCallback {
        void onMediaPlayerPrepared(MediaPlayer mediaPlayer);
        void onMediaPlayerFailed(Exception e);
    }
    
    /**
     * Extract a raw resource to a file for efficient access.
     *
     * @param resId    Resource ID
     * @param fileName Target file name
     * @return File object or null if extraction failed
     */
    public File extractRawResource(@RawRes int resId, String fileName) {
        Context context = contextReference.get();
        if (context == null) return null;
        
        // Create file in cache directory
        File outputFile = new File(context.getCacheDir(), fileName);
        
        // Check if file already exists
        if (outputFile.exists() && outputFile.length() > 0) {
            return outputFile;
        }
        
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        
        try {
            // Open raw resource
            inputStream = context.getResources().openRawResource(resId);
            outputStream = new FileOutputStream(outputFile);
            
            // Copy data
            byte[] buffer = new byte[8192];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            outputStream.flush();
            
            return outputFile;
        } catch (IOException e) {
            Log.e(TAG, "Error extracting raw resource: " + resId, e);
            return null;
        } finally {
            // Close streams
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                Log.e(TAG, "Error closing streams", e);
            }
        }
    }
    
    /**
     * Preload resources for faster access later.
     *
     * @param resIds Array of resource IDs to preload
     */
    public void preloadResources(final int[] resIds) {
        if (resIds == null || resIds.length == 0) {
            return;
        }
        
        // Load in background
        backgroundExecutor.execute(() -> {
            for (int resId : resIds) {
                try {
                    // Skip if already loaded
                    if (drawableCache.get(resId) != null || 
                        bitmapCache.get(String.valueOf(resId)) != null) {
                        continue;
                    }
                    
                    // Try to determine resource type
                    Context context = contextReference.get();
                    if (context == null) break;
                    
                    String resourceTypeName = context.getResources()
                            .getResourceTypeName(resId);
                    
                    // Handle different resource types
                    if ("drawable".equals(resourceTypeName) || 
                        "mipmap".equals(resourceTypeName)) {
                        // Load as drawable
                        loadDrawable(resId);
                    } else if ("raw".equals(resourceTypeName)) {
                        // Do nothing for raw resources, as they're expensive
                        // Just track that we've seen it
                        trackResourceLoaded(resId);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error preloading resource: " + resId, e);
                }
            }
        });
    }
    
    /**
     * Release all resources.
     */
    public void releaseAll() {
        // Clear caches
        drawableCache.evictAll();
        
        // Recycle all bitmaps
        for (String key : bitmapCache.snapshot().keySet()) {
            Bitmap bitmap = bitmapCache.get(key);
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
        bitmapCache.evictAll();
        
        // Release all media players
        for (Integer resId : mediaPlayerCache.keySet()) {
            WeakReference<MediaPlayer> ref = mediaPlayerCache.get(resId);
            if (ref != null) {
                MediaPlayer mediaPlayer = ref.get();
                if (mediaPlayer != null) {
                    try {
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.stop();
                        }
                        mediaPlayer.release();
                    } catch (Exception e) {
                        Log.e(TAG, "Error releasing media player", e);
                    }
                }
            }
        }
        mediaPlayerCache.clear();
        
        // Clear loaded resources tracking
        synchronized (loadedResources) {
            loadedResources.clear();
        }
    }
    
    /**
     * Release a specific resource.
     *
     * @param resId Resource ID
     */
    public void releaseResource(int resId) {
        // Release from drawable cache
        drawableCache.remove(resId);
        
        // Release from bitmap cache (all variations)
        for (String key : new ArrayList<>(bitmapCache.snapshot().keySet())) {
            if (key.startsWith(resId + "_")) {
                Bitmap bitmap = bitmapCache.get(key);
                if (bitmap != null && !bitmap.isRecycled()) {
                    bitmap.recycle();
                }
                bitmapCache.remove(key);
            }
        }
        
        // Release from media player cache
        WeakReference<MediaPlayer> ref = mediaPlayerCache.get(resId);
        if (ref != null) {
            MediaPlayer mediaPlayer = ref.get();
            if (mediaPlayer != null) {
                try {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                    }
                    mediaPlayer.release();
                } catch (Exception e) {
                    Log.e(TAG, "Error releasing media player", e);
                }
            }
            mediaPlayerCache.remove(resId);
        }
        
        // Remove from loaded resources tracking
        synchronized (loadedResources) {
            loadedResources.remove(Integer.valueOf(resId));
        }
    }
    
    /**
     * Trim memory usage to the given level.
     *
     * @param level Memory trim level
     */
    public void trimMemory(int level) {
        if (level >= android.content.ComponentCallbacks2.TRIM_MEMORY_MODERATE) {
            // Aggressive trimming, release most non-visible resources
            bitmapCache.trimToSize(bitmapCache.size() / 4);
            drawableCache.trimToSize(drawableCache.size() / 2);
            
            // Release media players not currently playing
            for (Integer resId : mediaPlayerCache.keySet()) {
                WeakReference<MediaPlayer> ref = mediaPlayerCache.get(resId);
                if (ref != null) {
                    MediaPlayer mediaPlayer = ref.get();
                    if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                        mediaPlayer.release();
                        mediaPlayerCache.remove(resId);
                    }
                }
            }
        } else if (level >= android.content.ComponentCallbacks2.TRIM_MEMORY_BACKGROUND) {
            // Moderate trimming, release some resources
            bitmapCache.trimToSize(bitmapCache.size() / 2);
        }
    }
    
    /**
     * Get statistics about resource usage.
     *
     * @return Map of resource usage statistics
     */
    public ArrayMap<String, String> getResourceStats() {
        ArrayMap<String, String> stats = new ArrayMap<>();
        
        stats.put("Drawables cached", String.valueOf(drawableCache.size()));
        stats.put("Bitmaps cached", String.valueOf(bitmapCache.size()));
        stats.put("Media players cached", String.valueOf(mediaPlayerCache.size()));
        
        synchronized (loadedResources) {
            stats.put("Total resources loaded", String.valueOf(loadedResources.size()));
        }
        
        Context context = contextReference.get();
        if (context != null) {
            stats.put("Low memory device", String.valueOf(MemoryOptimizer.isLowMemoryDevice(context)));
        }
        
        for (String key : qualitySettings.keySet()) {
            stats.put("Quality: " + key, String.valueOf(qualitySettings.get(key)));
        }
        
        return stats;
    }
    
    /**
     * Apply optimizations to a view based on type.
     *
     * @param view View to optimize
     */
    public void optimizeView(View view) {
        if (view == null) {
            return;
        }
        
        if (getBooleanQuality("prefer_rgb565")) {
            // For low-memory devices, disable hardware acceleration on complex views
            // to reduce memory usage (at the cost of some performance)
            if (view instanceof android.webkit.WebView) {
                view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            }
        }
    }
}