package com.bhashasetu.app.audio;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.util.Log;
import android.util.LruCache;

import androidx.annotation.NonNull;import com.bhashasetu.app.R;
import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Optimized audio manager that efficiently manages audio resources and handles audio focus.
 * It implements proper caching, lazy loading, and resource cleanup to minimize memory usage.
 */
public class OptimizedAudioManager implements AudioManager.OnAudioFocusChangeListener {
    private static final String TAG = "OptimizedAudioManager";
    
    // Instance for singleton pattern
    private static OptimizedAudioManager instance;
    
    // Application context
    private final Context context;
    
    // Audio system service
    private final android.media.AudioManager audioManager;
    
    // Media player instances
    private final Map<String, MediaPlayer> mediaPlayers;
    
    // Cache for audio files
    private final LruCache<String, File> audioCache;
    
    // Thread pool for audio operations
    private final ExecutorService executorService;
    
    // Map of active loading tasks
    private final Map<String, Future<?>> loadingTasks;
    
    // Audio focus management
    private AudioFocusRequest audioFocusRequest;
    private boolean hasAudioFocus = false;
    private MediaPlayer currentlyPlaying;
    private String currentlyPlayingId;
    
    // Callbacks
    private final Map<String, PlaybackCallback> playbackCallbacks;
    
    // Audio file directory
    private File audioCacheDir;
    
    // Handler for main thread operations
    private final Handler mainHandler;
    
    // Battery optimization
    private PowerManager.WakeLock wakeLock;
    
    // Auto release timer
    private final Handler autoReleaseHandler;
    private Runnable autoReleaseRunnable;
    private static final long AUTO_RELEASE_DELAY_MS = 5 * 60 * 1000; // 5 minutes
    
    /**
     * Callback for audio playback events.
     */
    public interface PlaybackCallback {
        void onPrepared(String audioId);
        void onCompletion(String audioId);
        void onError(String audioId, int what, int extra);
        void onProgressUpdate(String audioId, int progress, int duration);
    }
    
    /**
     * Empty implementation of PlaybackCallback for optional usage.
     */
    public static class PlaybackCallbackAdapter implements PlaybackCallback {
        @Override
        public void onPrepared(String audioId) {}
        
        @Override
        public void onCompletion(String audioId) {}
        
        @Override
        public void onError(String audioId, int what, int extra) {}
        
        @Override
        public void onProgressUpdate(String audioId, int progress, int duration) {}
    }
    
    /**
     * Private constructor for singleton pattern.
     *
     * @param context Application context
     */
    private OptimizedAudioManager(Context context) {
        this.context = context.getApplicationContext();
        this.audioManager = (android.media.AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        this.mediaPlayers = new ConcurrentHashMap<>();
        this.loadingTasks = new ConcurrentHashMap<>();
        this.playbackCallbacks = new ConcurrentHashMap<>();
        this.mainHandler = new Handler(Looper.getMainLooper());
        this.autoReleaseHandler = new Handler(Looper.getMainLooper());
        
        // Create thread pool
        this.executorService = Executors.newFixedThreadPool(Math.min(4, 
                             Runtime.getRuntime().availableProcessors()));
        
        // Initialize audio cache (4MB max)
        this.audioCache = new LruCache<String, File>(4 * 1024 * 1024) {
            @Override
            protected int sizeOf(String key, File file) {
                return (int) file.length();
            }
        };
        
        // Initialize cache directory
        initCacheDirectory();
        
        // Initialize audio focus request for Android O and higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .build();
            
            audioFocusRequest = new AudioFocusRequest.Builder(
                    android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK)
                    .setAudioAttributes(audioAttributes)
                    .setAcceptsDelayedFocusGain(true)
                    .setOnAudioFocusChangeListener(this)
                    .build();
        }
        
        // Initialize wake lock for audio playback
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, 
                                          "EnglishHindi:AudioWakeLock");
        wakeLock.setReferenceCounted(false);
    }
    
    /**
     * Get singleton instance.
     *
     * @param context Context
     * @return OptimizedAudioManager instance
     */
    public static OptimizedAudioManager getInstance(Context context) {
        if (instance == null) {
            synchronized (OptimizedAudioManager.class) {
                if (instance == null) {
                    instance = new OptimizedAudioManager(context);
                }
            }
        }
        return instance;
    }
    
    /**
     * Initialize cache directory.
     */
    private void initCacheDirectory() {
        try {
            // Check if external cache is available
            File cacheDir = context.getExternalCacheDir();
            if (cacheDir == null) {
                cacheDir = context.getCacheDir();
            }
            
            audioCacheDir = new File(cacheDir, "audio");
            if (!audioCacheDir.exists()) {
                audioCacheDir.mkdirs();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error initializing cache directory", e);
            audioCacheDir = null;
        }
    }
    
    /**
     * Request audio focus.
     *
     * @return true if focus was granted, false otherwise
     */
    private boolean requestAudioFocus() {
        if (hasAudioFocus) {
            return true;
        }
        
        int result;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            result = audioManager.requestAudioFocus(audioFocusRequest);
        } else {
            result = audioManager.requestAudioFocus(this,
                    android.media.AudioManager.STREAM_MUSIC,
                    android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK);
        }
        
        hasAudioFocus = (result == android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED);
        return hasAudioFocus;
    }
    
    /**
     * Abandon audio focus.
     */
    private void abandonAudioFocus() {
        if (!hasAudioFocus) {
            return;
        }
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            audioManager.abandonAudioFocusRequest(audioFocusRequest);
        } else {
            audioManager.abandonAudioFocus(this);
        }
        
        hasAudioFocus = false;
    }
    
    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
            case android.media.AudioManager.AUDIOFOCUS_GAIN:
                // Resume playback
                if (currentlyPlaying != null && !currentlyPlaying.isPlaying()) {
                    currentlyPlaying.start();
                    acquireWakeLock();
                }
                // Restore volume
                if (currentlyPlaying != null) {
                    currentlyPlaying.setVolume(1.0f, 1.0f);
                }
                hasAudioFocus = true;
                break;
            case android.media.AudioManager.AUDIOFOCUS_LOSS:
                // Lost focus for an unbounded amount of time: stop playback and release media player
                if (currentlyPlaying != null) {
                    if (currentlyPlaying.isPlaying()) {
                        currentlyPlaying.stop();
                    }
                    releaseMediaPlayer(currentlyPlayingId);
                    currentlyPlaying = null;
                    currentlyPlayingId = null;
                }
                hasAudioFocus = false;
                releaseWakeLock();
                break;
            case android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                // Lost focus for a short time, but we have to stop playback
                if (currentlyPlaying != null && currentlyPlaying.isPlaying()) {
                    currentlyPlaying.pause();
                    releaseWakeLock();
                }
                break;
            case android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                // Lost focus for a short time, but we can duck (play at lower volume)
                if (currentlyPlaying != null && currentlyPlaying.isPlaying()) {
                    currentlyPlaying.setVolume(0.3f, 0.3f);
                }
                break;
        }
    }
    
    /**
     * Acquire wake lock to prevent CPU from sleeping during playback.
     */
    private void acquireWakeLock() {
        if (!wakeLock.isHeld()) {
            wakeLock.acquire(10 * 60 * 1000L /*10 minutes*/);
        }
    }
    
    /**
     * Release wake lock when playback is done.
     */
    private void releaseWakeLock() {
        if (wakeLock.isHeld()) {
            wakeLock.release();
        }
    }
    
    /**
     * Generate unique audio ID.
     *
     * @return Unique ID
     */
    private String generateAudioId() {
        return UUID.randomUUID().toString();
    }
    
    /**
     * Get cache key for URL.
     *
     * @param url URL
     * @return Cache key
     */
    private String getCacheKey(String url) {
        return String.valueOf(url.hashCode());
    }
    
    /**
     * Get cache file for key.
     *
     * @param key Cache key
     * @return Cache file
     */
    private File getCacheFile(String key) {
        if (audioCacheDir == null) {
            return null;
        }
        return new File(audioCacheDir, key);
    }
    
    /**
     * Check if audio is cached.
     *
     * @param url Audio URL
     * @return true if cached, false otherwise
     */
    public boolean isAudioCached(String url) {
        if (url == null) return false;
        
        String key = getCacheKey(url);
        
        // Check memory cache first
        if (audioCache.get(key) != null) {
            return true;
        }
        
        // Check disk cache
        File cacheFile = getCacheFile(key);
        return cacheFile != null && cacheFile.exists() && cacheFile.length() > 0;
    }
    
    /**
     * Load audio file from URL.
     *
     * @param url      Audio URL
     * @param callback Callback for loading completion
     * @return Audio ID
     */
    public String load(@NonNull String url, @Nullable PlaybackCallback callback) {
        final String audioId = generateAudioId();
        final String key = getCacheKey(url);
        
        // Register callback if provided
        if (callback != null) {
            playbackCallbacks.put(audioId, callback);
        }
        
        // Check memory cache first
        File cachedFile = audioCache.get(key);
        if (cachedFile != null && cachedFile.exists() && cachedFile.length() > 0) {
            prepareMediaPlayer(audioId, cachedFile);
            return audioId;
        }
        
        // Check disk cache
        File cacheFile = getCacheFile(key);
        if (cacheFile != null && cacheFile.exists() && cacheFile.length() > 0) {
            // Update memory cache
            audioCache.put(key, cacheFile);
            prepareMediaPlayer(audioId, cacheFile);
            return audioId;
        }
        
        // Download file in background
        Future<?> future = executorService.submit(() -> {
            try {
                File downloadedFile = downloadAudioFile(url, key);
                if (downloadedFile != null) {
                    // Update memory cache
                    audioCache.put(key, downloadedFile);
                    prepareMediaPlayer(audioId, downloadedFile);
                } else {
                    notifyError(audioId, MediaPlayer.MEDIA_ERROR_UNKNOWN, 0);
                }
            } catch (Exception e) {
                Log.e(TAG, "Error downloading audio: " + url, e);
                notifyError(audioId, MediaPlayer.MEDIA_ERROR_UNKNOWN, 0);
            } finally {
                loadingTasks.remove(audioId);
            }
        });
        
        loadingTasks.put(audioId, future);
        
        return audioId;
    }
    
    /**
     * Download audio file from URL.
     *
     * @param url URL to download from
     * @param key Cache key
     * @return Downloaded file or null on failure
     */
    private File downloadAudioFile(String url, String key) {
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        
        try {
            // Create cache file
            File cacheFile = getCacheFile(key);
            if (cacheFile == null) {
                return null;
            }
            
            // Create parent directories if needed
            if (!cacheFile.getParentFile().exists()) {
                cacheFile.getParentFile().mkdirs();
            }
            
            // Download file
            URL audioUrl = new URL(url);
            urlConnection = (HttpURLConnection) audioUrl.openConnection();
            urlConnection.setConnectTimeout(15000);
            urlConnection.setReadTimeout(15000);
            
            int responseCode = urlConnection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                return null;
            }
            
            inputStream = urlConnection.getInputStream();
            outputStream = new FileOutputStream(cacheFile);
            
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            
            return cacheFile;
        } catch (IOException e) {
            Log.e(TAG, "Error downloading audio file: " + url, e);
            return null;
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            } catch (IOException e) {
                Log.e(TAG, "Error closing streams", e);
            }
        }
    }
    
    /**
     * Prepare media player from file.
     *
     * @param audioId Audio ID
     * @param file    Audio file
     */
    private void prepareMediaPlayer(final String audioId, final File file) {
        mainHandler.post(() -> {
            try {
                MediaPlayer mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioAttributes(
                        new AudioAttributes.Builder()
                                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                                .setUsage(AudioAttributes.USAGE_MEDIA)
                                .build()
                );
                
                mediaPlayer.setOnPreparedListener(mp -> notifyPrepared(audioId));
                mediaPlayer.setOnCompletionListener(mp -> {
                    notifyCompletion(audioId);
                    releaseWakeLock();
                });
                mediaPlayer.setOnErrorListener((mp, what, extra) -> {
                    notifyError(audioId, what, extra);
                    releaseWakeLock();
                    return true;
                });
                
                mediaPlayer.setDataSource(context, Uri.fromFile(file));
                mediaPlayer.prepareAsync();
                
                // Store media player
                mediaPlayers.put(audioId, mediaPlayer);
                
                // Schedule auto-release
                scheduleAutoRelease();
            } catch (IOException e) {
                Log.e(TAG, "Error preparing media player", e);
                notifyError(audioId, MediaPlayer.MEDIA_ERROR_UNKNOWN, 0);
            }
        });
    }
    
    /**
     * Load audio from raw resource.
     *
     * @param resId    Resource ID
     * @param callback Callback for loading completion
     * @return Audio ID
     */
    public String loadFromResource(int resId, @Nullable PlaybackCallback callback) {
        final String audioId = generateAudioId();
        
        // Register callback if provided
        if (callback != null) {
            playbackCallbacks.put(audioId, callback);
        }
        
        mainHandler.post(() -> {
            try {
                MediaPlayer mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioAttributes(
                        new AudioAttributes.Builder()
                                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                                .setUsage(AudioAttributes.USAGE_MEDIA)
                                .build()
                );
                
                mediaPlayer.setOnPreparedListener(mp -> notifyPrepared(audioId));
                mediaPlayer.setOnCompletionListener(mp -> {
                    notifyCompletion(audioId);
                    releaseWakeLock();
                });
                mediaPlayer.setOnErrorListener((mp, what, extra) -> {
                    notifyError(audioId, what, extra);
                    releaseWakeLock();
                    return true;
                });
                
                // Load from resource
                mediaPlayer.setDataSource(context.getResources().openRawResourceFd(resId));
                mediaPlayer.prepareAsync();
                
                // Store media player
                mediaPlayers.put(audioId, mediaPlayer);
                
                // Schedule auto-release
                scheduleAutoRelease();
            } catch (IOException e) {
                Log.e(TAG, "Error preparing media player from resource", e);
                notifyError(audioId, MediaPlayer.MEDIA_ERROR_UNKNOWN, 0);
            }
        });
        
        return audioId;
    }
    
    /**
     * Schedule automatic release of resources after inactivity.
     */
    private void scheduleAutoRelease() {
        // Cancel any existing auto-release
        if (autoReleaseRunnable != null) {
            autoReleaseHandler.removeCallbacks(autoReleaseRunnable);
        }
        
        // Schedule new auto-release
        autoReleaseRunnable = this::releaseUnusedResources;
        autoReleaseHandler.postDelayed(autoReleaseRunnable, AUTO_RELEASE_DELAY_MS);
    }
    
    /**
     * Release unused resources to free memory.
     */
    public void releaseUnusedResources() {
        // Only release resources if no playback is active
        if (currentlyPlaying == null || !currentlyPlaying.isPlaying()) {
            // Release all media players except currently playing
            for (Map.Entry<String, MediaPlayer> entry : mediaPlayers.entrySet()) {
                if (!entry.getKey().equals(currentlyPlayingId)) {
                    MediaPlayer mediaPlayer = entry.getValue();
                    if (mediaPlayer != null) {
                        mediaPlayer.release();
                    }
                }
            }
            
            // Clear media players map except currently playing
            if (currentlyPlayingId != null) {
                MediaPlayer current = mediaPlayers.get(currentlyPlayingId);
                mediaPlayers.clear();
                if (current != null) {
                    mediaPlayers.put(currentlyPlayingId, current);
                }
            } else {
                mediaPlayers.clear();
            }
            
            // Cancel loading tasks
            for (Future<?> future : loadingTasks.values()) {
                future.cancel(true);
            }
            loadingTasks.clear();
            
            // Trim memory cache
            audioCache.trimToSize(audioCache.size() / 2);
            
            // Release audio focus if not playing
            if (currentlyPlaying == null || !currentlyPlaying.isPlaying()) {
                abandonAudioFocus();
            }
            
            Log.d(TAG, "Released unused audio resources");
        }
    }
    
    /**
     * Play audio by ID.
     *
     * @param audioId Audio ID
     * @return true if playback started, false otherwise
     */
    public boolean play(String audioId) {
        if (audioId == null) {
            return false;
        }
        
        MediaPlayer mediaPlayer = mediaPlayers.get(audioId);
        if (mediaPlayer == null) {
            return false;
        }
        
        // Request audio focus
        if (!requestAudioFocus()) {
            return false;
        }
        
        // Stop current playback if different
        if (currentlyPlaying != null && currentlyPlaying.isPlaying() && 
                !audioId.equals(currentlyPlayingId)) {
            currentlyPlaying.stop();
            try {
                currentlyPlaying.prepare();
            } catch (IOException e) {
                Log.e(TAG, "Error preparing media player", e);
            }
        }
        
        try {
            mediaPlayer.start();
            currentlyPlaying = mediaPlayer;
            currentlyPlayingId = audioId;
            
            // Start progress updates
            startProgressUpdates(audioId);
            
            // Acquire wake lock to prevent CPU from sleeping during playback
            acquireWakeLock();
            
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error playing audio", e);
            notifyError(audioId, MediaPlayer.MEDIA_ERROR_UNKNOWN, 0);
            return false;
        }
    }
    
    /**
     * Start sending progress updates to callback.
     *
     * @param audioId Audio ID
     */
    private void startProgressUpdates(final String audioId) {
        final MediaPlayer mediaPlayer = mediaPlayers.get(audioId);
        if (mediaPlayer == null) {
            return;
        }
        
        // Create update runnable
        Runnable progressUpdater = new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer.isPlaying()) {
                    int progress = mediaPlayer.getCurrentPosition();
                    int duration = mediaPlayer.getDuration();
                    notifyProgressUpdate(audioId, progress, duration);
                    
                    // Schedule next update
                    mainHandler.postDelayed(this, 100);
                }
            }
        };
        
        // Start updates
        mainHandler.post(progressUpdater);
    }
    
    /**
     * Pause audio playback.
     *
     * @param audioId Audio ID
     * @return true if paused, false otherwise
     */
    public boolean pause(String audioId) {
        if (audioId == null) {
            return false;
        }
        
        MediaPlayer mediaPlayer = mediaPlayers.get(audioId);
        if (mediaPlayer == null || !mediaPlayer.isPlaying()) {
            return false;
        }
        
        try {
            mediaPlayer.pause();
            releaseWakeLock();
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error pausing audio", e);
            return false;
        }
    }
    
    /**
     * Stop audio playback.
     *
     * @param audioId Audio ID
     * @return true if stopped, false otherwise
     */
    public boolean stop(String audioId) {
        if (audioId == null) {
            return false;
        }
        
        MediaPlayer mediaPlayer = mediaPlayers.get(audioId);
        if (mediaPlayer == null) {
            return false;
        }
        
        try {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.prepare();  // Prepare for future playback
            }
            
            if (audioId.equals(currentlyPlayingId)) {
                currentlyPlayingId = null;
                currentlyPlaying = null;
                abandonAudioFocus();
            }
            
            releaseWakeLock();
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error stopping audio", e);
            return false;
        }
    }
    
    /**
     * Seek to position.
     *
     * @param audioId  Audio ID
     * @param position Position in milliseconds
     * @return true if successful, false otherwise
     */
    public boolean seekTo(String audioId, int position) {
        if (audioId == null) {
            return false;
        }
        
        MediaPlayer mediaPlayer = mediaPlayers.get(audioId);
        if (mediaPlayer == null) {
            return false;
        }
        
        try {
            mediaPlayer.seekTo(position);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error seeking", e);
            return false;
        }
    }
    
    /**
     * Get current position.
     *
     * @param audioId Audio ID
     * @return Current position in milliseconds, or -1 if not playing
     */
    public int getCurrentPosition(String audioId) {
        if (audioId == null) {
            return -1;
        }
        
        MediaPlayer mediaPlayer = mediaPlayers.get(audioId);
        if (mediaPlayer == null) {
            return -1;
        }
        
        try {
            return mediaPlayer.getCurrentPosition();
        } catch (Exception e) {
            Log.e(TAG, "Error getting position", e);
            return -1;
        }
    }
    
    /**
     * Get audio duration.
     *
     * @param audioId Audio ID
     * @return Duration in milliseconds, or -1 if not available
     */
    public int getDuration(String audioId) {
        if (audioId == null) {
            return -1;
        }
        
        MediaPlayer mediaPlayer = mediaPlayers.get(audioId);
        if (mediaPlayer == null) {
            return -1;
        }
        
        try {
            return mediaPlayer.getDuration();
        } catch (Exception e) {
            Log.e(TAG, "Error getting duration", e);
            return -1;
        }
    }
    
    /**
     * Check if audio is playing.
     *
     * @param audioId Audio ID
     * @return true if playing, false otherwise
     */
    public boolean isPlaying(String audioId) {
        if (audioId == null) {
            return false;
        }
        
        MediaPlayer mediaPlayer = mediaPlayers.get(audioId);
        if (mediaPlayer == null) {
            return false;
        }
        
        try {
            return mediaPlayer.isPlaying();
        } catch (Exception e) {
            Log.e(TAG, "Error checking playback state", e);
            return false;
        }
    }
    
    /**
     * Set playback speed.
     *
     * @param audioId Audio ID
     * @param speed   Playback speed (0.5 to 2.0)
     * @return true if successful, false otherwise
     */
    public boolean setPlaybackSpeed(String audioId, float speed) {
        if (audioId == null) {
            return false;
        }
        
        MediaPlayer mediaPlayer = mediaPlayers.get(audioId);
        if (mediaPlayer == null) {
            return false;
        }
        
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // Clamp speed to valid range
                float clampedSpeed = Math.max(0.5f, Math.min(2.0f, speed));
                mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(clampedSpeed));
                return true;
            } else {
                // Playback speed not supported on older versions
                return false;
            }
        } catch (Exception e) {
            Log.e(TAG, "Error setting playback speed", e);
            return false;
        }
    }
    
    /**
     * Release media player resources.
     *
     * @param audioId Audio ID
     */
    public void release(String audioId) {
        if (audioId == null) {
            return;
        }
        
        // Cancel any loading task
        Future<?> loadingTask = loadingTasks.get(audioId);
        if (loadingTask != null) {
            loadingTask.cancel(true);
            loadingTasks.remove(audioId);
        }
        
        // Release media player
        releaseMediaPlayer(audioId);
        
        // Remove callback
        playbackCallbacks.remove(audioId);
    }
    
    /**
     * Release media player for audio ID.
     *
     * @param audioId Audio ID
     */
    private void releaseMediaPlayer(String audioId) {
        if (audioId == null) {
            return;
        }
        
        MediaPlayer mediaPlayer = mediaPlayers.get(audioId);
        if (mediaPlayer != null) {
            try {
                mediaPlayer.release();
            } catch (Exception e) {
                Log.e(TAG, "Error releasing media player", e);
            }
            mediaPlayers.remove(audioId);
        }
        
        // Update currently playing reference
        if (audioId.equals(currentlyPlayingId)) {
            currentlyPlaying = null;
            currentlyPlayingId = null;
            abandonAudioFocus();
            releaseWakeLock();
        }
    }
    
    /**
     * Notify prepared callback.
     *
     * @param audioId Audio ID
     */
    private void notifyPrepared(final String audioId) {
        PlaybackCallback callback = playbackCallbacks.get(audioId);
        if (callback != null) {
            mainHandler.post(() -> callback.onPrepared(audioId));
        }
    }
    
    /**
     * Notify completion callback.
     *
     * @param audioId Audio ID
     */
    private void notifyCompletion(final String audioId) {
        PlaybackCallback callback = playbackCallbacks.get(audioId);
        if (callback != null) {
            mainHandler.post(() -> callback.onCompletion(audioId));
        }
        
        // Update currently playing reference
        if (audioId.equals(currentlyPlayingId)) {
            currentlyPlaying = null;
            currentlyPlayingId = null;
            abandonAudioFocus();
        }
    }
    
    /**
     * Notify error callback.
     *
     * @param audioId Audio ID
     * @param what    Error type
     * @param extra   Extra error information
     */
    private void notifyError(final String audioId, final int what, final int extra) {
        PlaybackCallback callback = playbackCallbacks.get(audioId);
        if (callback != null) {
            mainHandler.post(() -> callback.onError(audioId, what, extra));
        }
        
        // Update currently playing reference
        if (audioId.equals(currentlyPlayingId)) {
            currentlyPlaying = null;
            currentlyPlayingId = null;
            abandonAudioFocus();
        }
    }
    
    /**
     * Notify progress update callback.
     *
     * @param audioId  Audio ID
     * @param progress Current position
     * @param duration Total duration
     */
    private void notifyProgressUpdate(final String audioId, final int progress, final int duration) {
        PlaybackCallback callback = playbackCallbacks.get(audioId);
        if (callback != null) {
            mainHandler.post(() -> callback.onProgressUpdate(audioId, progress, duration));
        }
    }
    
    /**
     * Clear all caches and release all resources.
     */
    public void releaseAll() {
        // Cancel any loading tasks
        for (Future<?> future : loadingTasks.values()) {
            future.cancel(true);
        }
        loadingTasks.clear();
        
        // Release all media players
        for (MediaPlayer mediaPlayer : mediaPlayers.values()) {
            try {
                if (mediaPlayer != null) {
                    mediaPlayer.release();
                }
            } catch (Exception e) {
                Log.e(TAG, "Error releasing media player", e);
            }
        }
        mediaPlayers.clear();
        
        // Clear references
        currentlyPlaying = null;
        currentlyPlayingId = null;
        
        // Clear callbacks
        playbackCallbacks.clear();
        
        // Clear caches
        audioCache.evictAll();
        
        // Abandon audio focus
        abandonAudioFocus();
        
        // Release wake lock
        releaseWakeLock();
        
        // Cancel auto-release task
        if (autoReleaseRunnable != null) {
            autoReleaseHandler.removeCallbacks(autoReleaseRunnable);
            autoReleaseRunnable = null;
        }
        
        // Shutdown executor service
        try {
            executorService.shutdown();
            executorService.awaitTermination(500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Log.e(TAG, "Error shutting down executor service", e);
        } finally {
            if (!executorService.isTerminated()) {
                executorService.shutdownNow();
            }
        }
    }
    
    /**
     * Clear audio cache.
     */
    public void clearCache() {
        // Clear memory cache
        audioCache.evictAll();
        
        // Clear disk cache
        if (audioCacheDir != null && audioCacheDir.exists()) {
            File[] files = audioCacheDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
        }
    }
    
    /**
     * Get cache size in bytes.
     *
     * @return Cache size
     */
    public long getCacheSize() {
        long size = 0;
        
        if (audioCacheDir != null && audioCacheDir.exists()) {
            File[] files = audioCacheDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    size += file.length();
                }
            }
        }
        
        return size;
    }
    
    /**
     * Preload audio file.
     *
     * @param url URL to preload
     * @return true if preloading started, false otherwise
     */
    public boolean preload(String url) {
        if (url == null) {
            return false;
        }
        
        final String key = getCacheKey(url);
        
        // Check if already cached
        if (isAudioCached(url)) {
            return true;
        }
        
        // Download in background
        executorService.submit(() -> {
            try {
                downloadAudioFile(url, key);
            } catch (Exception e) {
                Log.e(TAG, "Error preloading audio: " + url, e);
            }
        });
        
        return true;
    }
    
    /**
     * Preload multiple audio files.
     *
     * @param urls URLs to preload
     */
    public void preloadBatch(String[] urls) {
        if (urls == null || urls.length == 0) {
            return;
        }
        
        for (String url : urls) {
            preload(url);
        }
    }
    
    /**
     * Get statistics about audio cache and playback.
     *
     * @return Map of statistics
     */
    public Map<String, String> getStatistics() {
        Map<String, String> stats = new HashMap<>();
        
        stats.put("Media Players", String.valueOf(mediaPlayers.size()));
        stats.put("Loading Tasks", String.valueOf(loadingTasks.size()));
        stats.put("Memory Cache Size", String.valueOf(audioCache.size()));
        stats.put("Disk Cache Size", String.valueOf(getCacheSize() / 1024) + " KB");
        stats.put("Has Audio Focus", String.valueOf(hasAudioFocus));
        stats.put("Is Playing", String.valueOf(currentlyPlaying != null && currentlyPlaying.isPlaying()));
        
        return stats;
    }
}