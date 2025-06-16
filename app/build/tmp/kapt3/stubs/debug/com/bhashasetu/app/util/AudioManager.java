package com.bhashasetu.app.util;

/**
 * Manages audio playback for word pronunciations and other sounds
 */
@javax.inject.Singleton()
@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0007\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB\t\b\u0007\u00a2\u0006\u0004\b\u0002\u0010\u0003JL\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0010\b\u0002\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\r2\u001a\b\u0002\u0010\u000e\u001a\u0014\u0012\b\u0012\u00060\u0010j\u0002`\u0011\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u000fH\u0086@\u00a2\u0006\u0002\u0010\u0012JN\u0010\u0013\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\b\u0010\u0014\u001a\u0004\u0018\u00010\u000b2\u0010\b\u0002\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\r2\u001a\b\u0002\u0010\u000e\u001a\u0014\u0012\b\u0012\u00060\u0010j\u0002`\u0011\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u000fH\u0086@\u00a2\u0006\u0002\u0010\u0012J\u0016\u0010\u0015\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0086@\u00a2\u0006\u0002\u0010\u0016J\u0016\u0010\u0017\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0086@\u00a2\u0006\u0002\u0010\u0016J\u0016\u0010\u0018\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0086@\u00a2\u0006\u0002\u0010\u0016J\u0006\u0010\u0019\u001a\u00020\u0007J\b\u0010\u001a\u001a\u00020\u0007H\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2 = {"Lcom/bhashasetu/app/util/AudioManager;", "", "<init>", "()V", "mediaPlayer", "Landroid/media/MediaPlayer;", "playAudioFromAssets", "", "context", "Landroid/content/Context;", "fileName", "", "onCompletion", "Lkotlin/Function0;", "onError", "Lkotlin/Function1;", "Ljava/lang/Exception;", "Lkotlin/Exception;", "(Landroid/content/Context;Ljava/lang/String;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "playWordPronunciation", "wordAudioFileName", "playSuccessSound", "(Landroid/content/Context;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "playErrorSound", "playCompletionSound", "stopAudio", "releaseMediaPlayer", "Companion", "app_debug"})
public final class AudioManager {
    @org.jetbrains.annotations.Nullable()
    private android.media.MediaPlayer mediaPlayer;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "AppAudioManager";
    @org.jetbrains.annotations.NotNull()
    public static final com.bhashasetu.app.util.AudioManager.Companion Companion = null;
    
    @javax.inject.Inject()
    public AudioManager() {
        super();
    }
    
    /**
     * Plays an audio file from the assets folder
     *
     * @param context The application context
     * @param fileName The name of the audio file in the assets folder
     * @param onCompletion Optional callback when audio playback completes
     * @param onError Optional callback when an error occurs
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object playAudioFromAssets(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String fileName, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function0<kotlin.Unit> onCompletion, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function1<? super java.lang.Exception, kotlin.Unit> onError, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Plays a word pronunciation
     *
     * @param context The application context
     * @param wordAudioFileName The audio file name for the word
     * @param onCompletion Optional callback when audio playback completes
     * @param onError Optional callback when an error occurs
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object playWordPronunciation(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    java.lang.String wordAudioFileName, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function0<kotlin.Unit> onCompletion, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function1<? super java.lang.Exception, kotlin.Unit> onError, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Plays a success sound
     *
     * @param context The application context
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object playSuccessSound(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Plays an error sound
     *
     * @param context The application context
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object playErrorSound(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Plays a completion sound
     *
     * @param context The application context
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object playCompletionSound(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Stops any currently playing audio
     */
    public final void stopAudio() {
    }
    
    /**
     * Releases the MediaPlayer resources
     */
    private final void releaseMediaPlayer() {
    }
    
    @kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/bhashasetu/app/util/AudioManager$Companion;", "", "<init>", "()V", "TAG", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}