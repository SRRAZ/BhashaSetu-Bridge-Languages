package com.example.englishhindi.audio;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import com.example.englishhindi.cache.CacheManager;
import com.example.englishhindi.util.NetworkUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Audio player with caching support for offline playback.
 * Handles playing audio from cache first, then network if needed.
 */
public class CacheAwareAudioPlayer {
    
    private static final String TAG = "CacheAwareAudioPlayer";
    
    private static CacheAwareAudioPlayer instance;
    
    private Context context;
    private CacheManager cacheManager;
    private NetworkUtils networkUtils;
    private Executor executor;
    
    private MediaPlayer mediaPlayer;
    private String currentUrl;
    private boolean isPreparing = false;
    
    // Map of scheduled callbacks for when files are ready
    private Map<String, Set<PlayWhenReadyCallback>> pendingCallbacks = new HashMap<>();
    
    // Listener for playback events
    private AudioPlayerListener listener;
    
    private CacheAwareAudioPlayer(Context context) {
        this.context = context.getApplicationContext();
        this.cacheManager = CacheManager.getInstance(context);
        this.networkUtils = new NetworkUtils(context);
        this.executor = Executors.newSingleThreadExecutor();
        
        // Initialize media player
        initializeMediaPlayer();
    }
    
    /**
     * Get the singleton instance of the audio player
     * @param context Application context
     * @return CacheAwareAudioPlayer instance
     */
    public static synchronized CacheAwareAudioPlayer getInstance(Context context) {
        if (instance == null) {
            instance = new CacheAwareAudioPlayer(context);
        }
        return instance;
    }
    
    /**
     * Initialize the media player with default settings
     */
    private void initializeMediaPlayer() {
        // Release any existing media player
        releaseMediaPlayer();
        
        // Create new media player
        mediaPlayer = new MediaPlayer();
        
        // Set audio attributes
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build();
        mediaPlayer.setAudioAttributes(attributes);
        
        // Set listeners
        mediaPlayer.setOnPreparedListener(mp -> {
            isPreparing = false;
            
            // Start playback
            mediaPlayer.start();
            
            // Notify listener
            if (listener != null) {
                listener.onPlaybackStarted(currentUrl);
            }
        });
        
        mediaPlayer.setOnCompletionListener(mp -> {
            // Notify listener
            if (listener != null) {
                listener.onPlaybackCompleted(currentUrl);
            }
        });
        
        mediaPlayer.setOnErrorListener((mp, what, extra) -> {
            Log.e(TAG, "Media player error: " + what + ", " + extra);
            
            // Reset
            isPreparing = false;
            
            // Notify listener
            if (listener != null) {
                listener.onPlaybackError(currentUrl, what);
            }
            
            return true; // We handled the error
        });
    }
    
    /**
     * Set a listener for playback events
     * @param listener Listener to set
     */
    public void setAudioPlayerListener(AudioPlayerListener listener) {
        this.listener = listener;
    }
    
    /**
     * Play audio from a URL or cache
     * @param url Audio URL
     */
    public void playAudio(String url) {
        if (url == null || url.isEmpty()) {
            return;
        }
        
        // Check if already playing this URL
        if (url.equals(currentUrl) && mediaPlayer != null && mediaPlayer.isPlaying()) {
            return;
        }
        
        // Save current URL
        currentUrl = url;
        
        // First check if audio is cached
        String cachedPath = cacheManager.getCachedAudioPath(url);
        
        if (cachedPath != null) {
            // Play from cache
            playFromFile(cachedPath);
        } else if (networkUtils.isNetworkAvailable()) {
            // Not cached, download and play
            String filename = generateFilenameFromUrl(url);
            
            // Notify listener
            if (listener != null) {
                listener.onPreparingPlayback(url);
            }
            
            // Download and cache the file
            cacheManager.cacheAudioFile(url, filename, 50, (success, filePath) -> {
                if (success && filePath != null) {
                    // Make sure this is still the URL we want to play
                    if (url.equals(currentUrl)) {
                        playFromFile(filePath);
                    }
                    
                    // Notify any pending callbacks
                    notifyFileReady(url, filePath);
                } else {
                    // Caching failed, try streaming if online
                    if (networkUtils.isNetworkAvailable()) {
                        playFromUrl(url);
                    } else {
                        // Notify failure
                        if (listener != null) {
                            listener.onPlaybackError(url, -1);
                        }
                    }
                }
            });
        } else {
            // No cached file and no network
            if (listener != null) {
                listener.onPlaybackError(url, -1);
            }
        }
    }
    
    /**
     * Ensure an audio file is cached and ready for playback
     * @param url Audio URL
     * @param callback Callback when ready
     */
    public void ensureAudioReady(String url, PlayWhenReadyCallback callback) {
        if (url == null || url.isEmpty() || callback == null) {
            return;
        }
        
        // Check if already cached
        String cachedPath = cacheManager.getCachedAudioPath(url);
        
        if (cachedPath != null) {
            // Already cached, notify immediately
            callback.onAudioReady(url, cachedPath);
            return;
        }
        
        // Add to pending callbacks
        addPendingCallback(url, callback);
        
        // Check if we can download
        if (!networkUtils.isNetworkAvailable()) {
            // No network, can't prepare
            removePendingCallback(url, callback);
            callback.onAudioError(url, -1);
            return;
        }
        
        // Download and cache the file
        String filename = generateFilenameFromUrl(url);
        cacheManager.cacheAudioFile(url, filename, 50, (success, filePath) -> {
            if (success && filePath != null) {
                // Notify callbacks
                notifyFileReady(url, filePath);
            } else {
                // Notify error
                notifyFileError(url, -1);
            }
        });
    }
    
    /**
     * Prefetch an audio file for caching
     * @param url Audio URL
     * @param priority Caching priority
     */
    public void prefetchAudio(String url, int priority) {
        if (url == null || url.isEmpty()) {
            return;
        }
        
        executor.execute(() -> {
            // Check if already cached
            String cachedPath = cacheManager.getCachedAudioPath(url);
            if (cachedPath != null) {
                // Already cached
                return;
            }
            
            // If not cached and we have network, download
            if (networkUtils.isNetworkAvailable()) {
                String filename = generateFilenameFromUrl(url);
                cacheManager.cacheAudioFile(url, filename, priority, null);
            }
        });
    }
    
    /**
     * Add a pending callback for when an audio file is ready
     * @param url Audio URL
     * @param callback Callback to add
     */
    private void addPendingCallback(String url, PlayWhenReadyCallback callback) {
        synchronized (pendingCallbacks) {
            Set<PlayWhenReadyCallback> callbacks = pendingCallbacks.get(url);
            if (callbacks == null) {
                callbacks = new HashSet<>();
                pendingCallbacks.put(url, callbacks);
            }
            callbacks.add(callback);
        }
    }
    
    /**
     * Remove a pending callback
     * @param url Audio URL
     * @param callback Callback to remove
     */
    private void removePendingCallback(String url, PlayWhenReadyCallback callback) {
        synchronized (pendingCallbacks) {
            Set<PlayWhenReadyCallback> callbacks = pendingCallbacks.get(url);
            if (callbacks != null) {
                callbacks.remove(callback);
                if (callbacks.isEmpty()) {
                    pendingCallbacks.remove(url);
                }
            }
        }
    }
    
    /**
     * Notify all pending callbacks that a file is ready
     * @param url Audio URL
     * @param filePath Path to the cached file
     */
    private void notifyFileReady(String url, String filePath) {
        synchronized (pendingCallbacks) {
            Set<PlayWhenReadyCallback> callbacks = pendingCallbacks.get(url);
            if (callbacks != null) {
                for (PlayWhenReadyCallback callback : callbacks) {
                    callback.onAudioReady(url, filePath);
                }
                pendingCallbacks.remove(url);
            }
        }
    }
    
    /**
     * Notify all pending callbacks of an error
     * @param url Audio URL
     * @param errorCode Error code
     */
    private void notifyFileError(String url, int errorCode) {
        synchronized (pendingCallbacks) {
            Set<PlayWhenReadyCallback> callbacks = pendingCallbacks.get(url);
            if (callbacks != null) {
                for (PlayWhenReadyCallback callback : callbacks) {
                    callback.onAudioError(url, errorCode);
                }
                pendingCallbacks.remove(url);
            }
        }
    }
    
    /**
     * Play audio from a local file
     * @param filePath Path to the audio file
     */
    private void playFromFile(String filePath) {
        try {
            // Reset media player
            resetMediaPlayer();
            
            // Set data source
            mediaPlayer.setDataSource(filePath);
            
            // Prepare and play
            isPreparing = true;
            mediaPlayer.prepareAsync();
            
            // Notify listener
            if (listener != null) {
                listener.onPreparingPlayback(currentUrl);
            }
        } catch (IOException e) {
            Log.e(TAG, "Error playing from file: " + filePath, e);
            
            // Notify listener
            if (listener != null) {
                listener.onPlaybackError(currentUrl, -1);
            }
        }
    }
    
    /**
     * Play audio directly from a URL (streaming)
     * @param url Audio URL
     */
    private void playFromUrl(String url) {
        try {
            // Reset media player
            resetMediaPlayer();
            
            // Set data source
            mediaPlayer.setDataSource(context, Uri.parse(url));
            
            // Prepare and play
            isPreparing = true;
            mediaPlayer.prepareAsync();
            
            // Notify listener
            if (listener != null) {
                listener.onPreparingPlayback(url);
            }
        } catch (IOException e) {
            Log.e(TAG, "Error playing from URL: " + url, e);
            
            // Notify listener
            if (listener != null) {
                listener.onPlaybackError(url, -1);
            }
        }
    }
    
    /**
     * Reset the media player
     */
    private void resetMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.reset();
        }
    }
    
    /**
     * Release the media player resources
     */
    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.stop();
            } catch (IllegalStateException e) {
                // Ignore - player wasn't in a valid state
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    
    /**
     * Check if audio is currently playing
     * @return true if playing
     */
    public boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }
    
    /**
     * Check if audio is currently preparing
     * @return true if preparing
     */
    public boolean isPreparing() {
        return isPreparing;
    }
    
    /**
     * Pause playback
     */
    public void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            
            // Notify listener
            if (listener != null) {
                listener.onPlaybackPaused(currentUrl);
            }
        }
    }
    
    /**
     * Resume playback
     */
    public void resume() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying() && !isPreparing) {
            mediaPlayer.start();
            
            // Notify listener
            if (listener != null) {
                listener.onPlaybackStarted(currentUrl);
            }
        }
    }
    
    /**
     * Stop playback
     */
    public void stop() {
        if (mediaPlayer != null) {
            try {
                if (mediaPlayer.isPlaying() || isPreparing) {
                    mediaPlayer.stop();
                    
                    // Notify listener
                    if (listener != null) {
                        listener.onPlaybackStopped(currentUrl);
                    }
                }
                
                resetMediaPlayer();
                isPreparing = false;
            } catch (IllegalStateException e) {
                // Ignore - player wasn't in a valid state
            }
        }
    }
    
    /**
     * Get the current playback position
     * @return Current position in milliseconds
     */
    public int getCurrentPosition() {
        if (mediaPlayer != null) {
            try {
                return mediaPlayer.getCurrentPosition();
            } catch (IllegalStateException e) {
                // Ignore - player wasn't in a valid state
            }
        }
        return 0;
    }
    
    /**
     * Get the total duration of the current audio
     * @return Duration in milliseconds
     */
    public int getDuration() {
        if (mediaPlayer != null) {
            try {
                return mediaPlayer.getDuration();
            } catch (IllegalStateException e) {
                // Ignore - player wasn't in a valid state
            }
        }
        return 0;
    }
    
    /**
     * Seek to a position in the audio
     * @param position Position in milliseconds
     */
    public void seekTo(int position) {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.seekTo(position);
            } catch (IllegalStateException e) {
                // Ignore - player wasn't in a valid state
            }
        }
    }
    
    /**
     * Generate a consistent filename from a URL
     * @param url Audio URL
     * @return Filename for caching
     */
    private String generateFilenameFromUrl(String url) {
        // Create a consistent filename based on URL hash
        int urlHash = url.hashCode();
        String extension = "mp3"; // Default extension
        
        // Try to extract extension from URL
        int lastDotIndex = url.lastIndexOf(".");
        if (lastDotIndex > 0 && lastDotIndex < url.length() - 1) {
            String urlExtension = url.substring(lastDotIndex + 1);
            if (urlExtension.length() <= 4) {
                extension = urlExtension;
            }
        }
        
        return "audio_" + Math.abs(urlHash) + "." + extension;
    }
    
    /**
     * Release resources when no longer needed
     */
    public void release() {
        releaseMediaPlayer();
        
        // Clear all callbacks
        synchronized (pendingCallbacks) {
            pendingCallbacks.clear();
        }
    }
    
    /**
     * Interface for audio playback event callbacks
     */
    public interface AudioPlayerListener {
        void onPreparingPlayback(String url);
        void onPlaybackStarted(String url);
        void onPlaybackPaused(String url);
        void onPlaybackStopped(String url);
        void onPlaybackCompleted(String url);
        void onPlaybackError(String url, int errorCode);
    }
    
    /**
     * Interface for audio ready callbacks
     */
    public interface PlayWhenReadyCallback {
        void onAudioReady(String url, String filePath);
        void onAudioError(String url, int errorCode);
    }
}