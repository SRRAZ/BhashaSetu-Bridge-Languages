package com.example.englishhindi.util

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manages audio playback for word pronunciations and other sounds
 */
@Singleton
class AudioManager @Inject constructor() {
    
    private var mediaPlayer: MediaPlayer? = null
    
    /**
     * Plays an audio file from the assets folder
     * 
     * @param context The application context
     * @param fileName The name of the audio file in the assets folder
     * @param onCompletion Optional callback when audio playback completes
     * @param onError Optional callback when an error occurs
     */
    suspend fun playAudioFromAssets(
        context: Context,
        fileName: String,
        onCompletion: (() -> Unit)? = null,
        onError: ((Exception) -> Unit)? = null
    ) {
        withContext(Dispatchers.IO) {
            try {
                // Release any existing MediaPlayer
                releaseMediaPlayer()
                
                // Create a new MediaPlayer
                mediaPlayer = MediaPlayer().apply {
                    context.assets.openFd("audio/$fileName").use { descriptor ->
                        setDataSource(descriptor.fileDescriptor, descriptor.startOffset, descriptor.length)
                        prepare()
                        
                        // Set on completion listener
                        setOnCompletionListener {
                            releaseMediaPlayer()
                            onCompletion?.invoke()
                        }
                        
                        // Start playback
                        start()
                    }
                }
            } catch (e: IOException) {
                Log.e(TAG, "Error playing audio: $fileName", e)
                onError?.invoke(e)
            } catch (e: IllegalStateException) {
                Log.e(TAG, "MediaPlayer in invalid state", e)
                onError?.invoke(e)
            } catch (e: IllegalArgumentException) {
                Log.e(TAG, "Invalid arguments for MediaPlayer", e)
                onError?.invoke(e)
            } catch (e: Exception) {
                Log.e(TAG, "Unexpected error playing audio", e)
                onError?.invoke(e)
            }
        }
    }
    
    /**
     * Plays a word pronunciation
     * 
     * @param context The application context
     * @param wordAudioFileName The audio file name for the word
     * @param onCompletion Optional callback when audio playback completes
     * @param onError Optional callback when an error occurs
     */
    suspend fun playWordPronunciation(
        context: Context,
        wordAudioFileName: String?,
        onCompletion: (() -> Unit)? = null,
        onError: ((Exception) -> Unit)? = null
    ) {
        if (wordAudioFileName.isNullOrEmpty()) {
            onError?.invoke(IOException("No audio file provided"))
            return
        }
        
        playAudioFromAssets(context, wordAudioFileName, onCompletion, onError)
    }
    
    /**
     * Plays a success sound
     * 
     * @param context The application context
     */
    suspend fun playSuccessSound(context: Context) {
        playAudioFromAssets(context, "ui/success.mp3")
    }
    
    /**
     * Plays an error sound
     * 
     * @param context The application context
     */
    suspend fun playErrorSound(context: Context) {
        playAudioFromAssets(context, "ui/error.mp3")
    }
    
    /**
     * Plays a completion sound
     * 
     * @param context The application context
     */
    suspend fun playCompletionSound(context: Context) {
        playAudioFromAssets(context, "ui/completion.mp3")
    }
    
    /**
     * Stops any currently playing audio
     */
    fun stopAudio() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
            }
            releaseMediaPlayer()
        }
    }
    
    /**
     * Releases the MediaPlayer resources
     */
    private fun releaseMediaPlayer() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
            }
            it.release()
            mediaPlayer = null
        }
    }
    
    companion object {
        private const val TAG = "AudioManager"
    }
}