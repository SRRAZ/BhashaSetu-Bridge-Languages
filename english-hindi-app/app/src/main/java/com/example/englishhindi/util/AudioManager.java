package com.example.englishhindi.util;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Manager class to handle audio playback for word pronunciations
 */
public class AudioManager {
    
    private static final String TAG = "AudioManager";
    
    private static AudioManager instance;
    private final Context context;
    private MediaPlayer mediaPlayer;
    private final Map<String, Uri> audioCache;
    private boolean isPlaying = false;
    private OnAudioCompletionListener completionListener;
    
    private AudioManager(Context context) {
        this.context = context.getApplicationContext();
        this.audioCache = new HashMap<>();
        initializeMediaPlayer();
    }
    
    public static synchronized AudioManager getInstance(Context context) {
        if (instance == null) {
            instance = new AudioManager(context);
        }
        return instance;
    }
    
    private void initializeMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        
        // Configure media player
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build());
        }
        
        // Set listener for playback completion
        mediaPlayer.setOnCompletionListener(mp -> {
            isPlaying = false;
            if (completionListener != null) {
                completionListener.onAudioCompleted();
            }
        });
        
        // Handle errors
        mediaPlayer.setOnErrorListener((mp, what, extra) -> {
            Log.e(TAG, "Media player error: " + what + ", " + extra);
            isPlaying = false;
            if (completionListener != null) {
                completionListener.onAudioError();
            }
            return true;
        });
    }
    
    /**
     * Play a pronunciation audio file from the app's assets
     * @param assetPath path to audio file in assets folder (e.g., "audio/hello.mp3")
     */
    public void playFromAsset(String assetPath) {
        if (isPlaying) {
            stop();
        }
        
        try {
            mediaPlayer.reset();
            
            // Load from asset
            AssetFileDescriptor descriptor = context.getAssets().openFd(assetPath);
            mediaPlayer.setDataSource(descriptor.getFileDescriptor(), 
                    descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();
            
            mediaPlayer.prepare();
            mediaPlayer.start();
            isPlaying = true;
            
        } catch (IOException e) {
            Log.e(TAG, "Error playing audio from asset: " + assetPath, e);
            if (completionListener != null) {
                completionListener.onAudioError();
            }
        }
    }
    
    /**
     * Play audio from a file in the app's private storage
     * @param fileName name of the audio file
     */
    public void playFromFile(String fileName) {
        if (isPlaying) {
            stop();
        }
        
        File audioFile = new File(context.getFilesDir(), "audio/" + fileName);
        
        if (!audioFile.exists()) {
            Log.e(TAG, "Audio file not found: " + audioFile.getPath());
            if (completionListener != null) {
                completionListener.onAudioError();
            }
            return;
        }
        
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(context, Uri.fromFile(audioFile));
            mediaPlayer.prepare();
            mediaPlayer.start();
            isPlaying = true;
            
        } catch (IOException e) {
            Log.e(TAG, "Error playing audio from file: " + fileName, e);
            if (completionListener != null) {
                completionListener.onAudioError();
            }
        }
    }
    
    /**
     * Play audio from a remote URL
     * @param url URL of the audio file
     */
    public void playFromUrl(String url) {
        if (isPlaying) {
            stop();
        }
        
        // Check if URL is already cached
        Uri uri = audioCache.get(url);
        if (uri == null) {
            uri = Uri.parse(url);
            audioCache.put(url, uri);
        }
        
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(context, uri);
            mediaPlayer.prepareAsync();
            
            mediaPlayer.setOnPreparedListener(mp -> {
                mp.start();
                isPlaying = true;
            });
            
        } catch (IOException e) {
            Log.e(TAG, "Error playing audio from URL: " + url, e);
            if (completionListener != null) {
                completionListener.onAudioError();
            }
        }
    }
    
    /**
     * Stop current audio playback
     */
    public void stop() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        isPlaying = false;
    }
    
    /**
     * Check if audio is currently playing
     * @return true if audio is playing
     */
    public boolean isPlaying() {
        return isPlaying;
    }
    
    /**
     * Set a listener for audio completion events
     * @param listener the listener to set
     */
    public void setOnAudioCompletionListener(OnAudioCompletionListener listener) {
        this.completionListener = listener;
    }
    
    /**
     * Interface for audio playback completion callbacks
     */
    public interface OnAudioCompletionListener {
        void onAudioCompleted();
        void onAudioError();
    }
    
    /**
     * Release media player resources
     * Should be called when the app is being closed
     */
    public void release() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
        isPlaying = false;
    }
}