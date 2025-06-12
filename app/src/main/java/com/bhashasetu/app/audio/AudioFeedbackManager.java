package com.bhashasetu.app.audio;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.SparseIntArray;

import com.bhashasetu.app.R;

/**
 * Manages audio feedback sounds for the app.
 * Provides sound effects for correct/incorrect answers, achievements, etc.
 */
public class AudioFeedbackManager {
    private static final String TAG = "AudioFeedbackManager";
    
    // Feedback sound types
    public static final int SOUND_CORRECT = 1;
    public static final int SOUND_INCORRECT = 2;
    public static final int SOUND_ACHIEVEMENT = 3;
    public static final int SOUND_LEVEL_UP = 4;
    public static final int SOUND_TAP = 5;
    
    private static AudioFeedbackManager instance;
    
    private Context context;
    private SoundPool soundPool;
    private SparseIntArray soundMap;
    private boolean soundsLoaded;
    private Handler handler;
    
    private AudioFeedbackManager(Context context) {
        this.context = context.getApplicationContext();
        this.soundMap = new SparseIntArray();
        this.handler = new Handler(Looper.getMainLooper());
        
        // Initialize SoundPool
        initSoundPool();
    }
    
    /**
     * Get the singleton instance of AudioFeedbackManager.
     * 
     * @param context The application context
     * @return The AudioFeedbackManager instance
     */
    public static synchronized AudioFeedbackManager getInstance(Context context) {
        if (instance == null) {
            instance = new AudioFeedbackManager(context);
        }
        return instance;
    }
    
    /**
     * Initialize the SoundPool.
     */
    private void initSoundPool() {
        // Create SoundPool with audio attributes
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        
        soundPool = new SoundPool.Builder()
                .setMaxStreams(5)
                .setAudioAttributes(audioAttributes)
                .build();
        
        // Set up load listener
        soundPool.setOnLoadCompleteListener((soundPool, sampleId, status) -> {
            soundsLoaded = true;
        });
        
        // Load sound resources
        loadSounds();
    }
    
    /**
     * Load sound effects from resources.
     */
    private void loadSounds() {
        // Load sound resources and map them to sound type constants
        try {
            int correctSoundId = soundPool.load(context, R.raw.sound_correct, 1);
            soundMap.put(SOUND_CORRECT, correctSoundId);
            
            int incorrectSoundId = soundPool.load(context, R.raw.sound_incorrect, 1);
            soundMap.put(SOUND_INCORRECT, incorrectSoundId);
            
            int achievementSoundId = soundPool.load(context, R.raw.sound_achievement, 1);
            soundMap.put(SOUND_ACHIEVEMENT, achievementSoundId);
            
            int levelUpSoundId = soundPool.load(context, R.raw.sound_level_up, 1);
            soundMap.put(SOUND_LEVEL_UP, levelUpSoundId);
            
            int tapSoundId = soundPool.load(context, R.raw.sound_tap, 1);
            soundMap.put(SOUND_TAP, tapSoundId);
        } catch (Exception e) {
            Log.e(TAG, "Error loading sounds", e);
        }
    }
    
    /**
     * Play a feedback sound.
     * 
     * @param soundType The sound type to play
     */
    public void playSound(int soundType) {
        if (!soundsLoaded) {
            // If sounds are still loading, try again after a delay
            handler.postDelayed(() -> playSound(soundType), 300);
            return;
        }
        
        int soundId = soundMap.get(soundType, -1);
        if (soundId != -1) {
            soundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f);
        }
    }
    
    /**
     * Play the correct answer sound.
     */
    public void playCorrectSound() {
        playSound(SOUND_CORRECT);
    }
    
    /**
     * Play the incorrect answer sound.
     */
    public void playIncorrectSound() {
        playSound(SOUND_INCORRECT);
    }
    
    /**
     * Play the achievement sound.
     */
    public void playAchievementSound() {
        playSound(SOUND_ACHIEVEMENT);
    }
    
    /**
     * Play the level up sound.
     */
    public void playLevelUpSound() {
        playSound(SOUND_LEVEL_UP);
    }
    
    /**
     * Play the tap sound.
     */
    public void playTapSound() {
        playSound(SOUND_TAP);
    }
    
    /**
     * Play a sequence of sounds with delays between them.
     * 
     * @param soundTypes Array of sound types to play
     * @param delays Array of delays in milliseconds between sounds
     */
    public void playSoundSequence(int[] soundTypes, int[] delays) {
        if (soundTypes == null || delays == null || soundTypes.length == 0) {
            return;
        }
        
        // Play the first sound immediately
        playSound(soundTypes[0]);
        
        // Schedule the rest with delays
        for (int i = 1; i < soundTypes.length; i++) {
            final int index = i;
            int cumulativeDelay = 0;
            
            // Calculate cumulative delay
            for (int j = 0; j < i; j++) {
                cumulativeDelay += delays[j];
            }
            
            handler.postDelayed(() -> playSound(soundTypes[index]), cumulativeDelay);
        }
    }
    
    /**
     * Release resources.
     */
    public void release() {
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
        
        soundMap.clear();
        soundsLoaded = false;
        instance = null;
    }
}