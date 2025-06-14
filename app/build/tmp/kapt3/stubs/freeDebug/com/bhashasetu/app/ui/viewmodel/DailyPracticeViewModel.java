package com.bhashasetu.app.ui.viewmodel;

/**
 * ViewModel for the daily practice feature
 */
@error.NonExistentClass()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001:\u0001+B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0006\u0010\"\u001a\u00020\u000fJ\u0006\u0010#\u001a\u00020\u000fJ\u0006\u0010$\u001a\u00020%J\u0006\u0010&\u001a\u00020\'J\u000e\u0010(\u001a\u00020%2\u0006\u0010)\u001a\u00020\u000fJ\u0006\u0010*\u001a\u00020%R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u00110\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u00110\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0019\u0010\u0017\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0016R\u001d\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u00110\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0016R\u001d\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u00110\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0016R\u0019\u0010\u001d\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001e0\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010 \u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u00110\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006,"}, d2 = {"Lcom/bhashasetu/app/ui/viewmodel/DailyPracticeViewModel;", "Landroidx/lifecycle/ViewModel;", "wordRepository", "Lcom/bhashasetu/app/data/repository/WordRepository;", "settingsRepository", "Lcom/bhashasetu/app/data/repository/AppSettingsRepository;", "spacedRepetitionManager", "Lcom/bhashasetu/app/util/SpacedRepetitionManager;", "(Lcom/bhashasetu/app/data/repository/WordRepository;Lcom/bhashasetu/app/data/repository/AppSettingsRepository;Lcom/bhashasetu/app/util/SpacedRepetitionManager;)V", "_currentPracticeState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/bhashasetu/app/ui/viewmodel/DailyPracticeViewModel$PracticeState;", "_currentWord", "Lcom/bhashasetu/app/data/model/Word;", "_currentWordIndex", "", "_dailyNewWords", "", "_dueForReviewWords", "currentPracticeState", "Lkotlinx/coroutines/flow/StateFlow;", "getCurrentPracticeState", "()Lkotlinx/coroutines/flow/StateFlow;", "currentWord", "getCurrentWord", "dailyNewWords", "getDailyNewWords", "dueForReviewWords", "getDueForReviewWords", "settings", "Lcom/bhashasetu/app/data/model/AppSettings;", "getSettings", "todaysPracticeWords", "getTodaysPracticeWords", "getCurrentWordNumber", "getTotalWordCount", "loadTodaysPracticeWords", "", "nextWord", "", "recordWordPerformance", "performance", "startPractice", "PracticeState", "app_freeDebug"})
public final class DailyPracticeViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.bhashasetu.app.data.repository.WordRepository wordRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.bhashasetu.app.data.repository.AppSettingsRepository settingsRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.bhashasetu.app.util.SpacedRepetitionManager spacedRepetitionManager = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.bhashasetu.app.data.model.AppSettings> settings = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.bhashasetu.app.data.model.Word>> _dueForReviewWords = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.bhashasetu.app.data.model.Word>> dueForReviewWords = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.bhashasetu.app.data.model.Word>> _dailyNewWords = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.bhashasetu.app.data.model.Word>> dailyNewWords = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.bhashasetu.app.data.model.Word>> todaysPracticeWords = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.bhashasetu.app.ui.viewmodel.DailyPracticeViewModel.PracticeState> _currentPracticeState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.bhashasetu.app.ui.viewmodel.DailyPracticeViewModel.PracticeState> currentPracticeState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Integer> _currentWordIndex = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.bhashasetu.app.data.model.Word> _currentWord = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.bhashasetu.app.data.model.Word> currentWord = null;
    
    @error.NonExistentClass()
    public DailyPracticeViewModel(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.repository.WordRepository wordRepository, @org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.repository.AppSettingsRepository settingsRepository, @org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.util.SpacedRepetitionManager spacedRepetitionManager) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.bhashasetu.app.data.model.AppSettings> getSettings() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.bhashasetu.app.data.model.Word>> getDueForReviewWords() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.bhashasetu.app.data.model.Word>> getDailyNewWords() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.bhashasetu.app.data.model.Word>> getTodaysPracticeWords() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.bhashasetu.app.ui.viewmodel.DailyPracticeViewModel.PracticeState> getCurrentPracticeState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.bhashasetu.app.data.model.Word> getCurrentWord() {
        return null;
    }
    
    /**
     * Loads words for today's practice session
     */
    public final void loadTodaysPracticeWords() {
    }
    
    /**
     * Starts a new practice session
     */
    public final void startPractice() {
    }
    
    /**
     * Moves to the next word in practice
     * @return True if there are more words, False if practice is complete
     */
    public final boolean nextWord() {
        return false;
    }
    
    /**
     * Records a user's performance on the current word
     * @param performance Performance rating (0-5)
     */
    public final void recordWordPerformance(int performance) {
    }
    
    /**
     * Returns the total number of words for today's practice
     */
    public final int getTotalWordCount() {
        return 0;
    }
    
    /**
     * Returns the current word index (1-based for display)
     */
    public final int getCurrentWordNumber() {
        return 0;
    }
    
    /**
     * Practice session state
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0004\u0003\u0004\u0005\u0006B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0004\u0007\b\t\n\u00a8\u0006\u000b"}, d2 = {"Lcom/bhashasetu/app/ui/viewmodel/DailyPracticeViewModel$PracticeState;", "", "()V", "Completed", "InProgress", "NotStarted", "Ready", "Lcom/bhashasetu/app/ui/viewmodel/DailyPracticeViewModel$PracticeState$Completed;", "Lcom/bhashasetu/app/ui/viewmodel/DailyPracticeViewModel$PracticeState$InProgress;", "Lcom/bhashasetu/app/ui/viewmodel/DailyPracticeViewModel$PracticeState$NotStarted;", "Lcom/bhashasetu/app/ui/viewmodel/DailyPracticeViewModel$PracticeState$Ready;", "app_freeDebug"})
    public static abstract class PracticeState {
        
        private PracticeState() {
            super();
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/bhashasetu/app/ui/viewmodel/DailyPracticeViewModel$PracticeState$Completed;", "Lcom/bhashasetu/app/ui/viewmodel/DailyPracticeViewModel$PracticeState;", "()V", "app_freeDebug"})
        public static final class Completed extends com.bhashasetu.app.ui.viewmodel.DailyPracticeViewModel.PracticeState {
            @org.jetbrains.annotations.NotNull()
            public static final com.bhashasetu.app.ui.viewmodel.DailyPracticeViewModel.PracticeState.Completed INSTANCE = null;
            
            private Completed() {
            }
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/bhashasetu/app/ui/viewmodel/DailyPracticeViewModel$PracticeState$InProgress;", "Lcom/bhashasetu/app/ui/viewmodel/DailyPracticeViewModel$PracticeState;", "()V", "app_freeDebug"})
        public static final class InProgress extends com.bhashasetu.app.ui.viewmodel.DailyPracticeViewModel.PracticeState {
            @org.jetbrains.annotations.NotNull()
            public static final com.bhashasetu.app.ui.viewmodel.DailyPracticeViewModel.PracticeState.InProgress INSTANCE = null;
            
            private InProgress() {
            }
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/bhashasetu/app/ui/viewmodel/DailyPracticeViewModel$PracticeState$NotStarted;", "Lcom/bhashasetu/app/ui/viewmodel/DailyPracticeViewModel$PracticeState;", "()V", "app_freeDebug"})
        public static final class NotStarted extends com.bhashasetu.app.ui.viewmodel.DailyPracticeViewModel.PracticeState {
            @org.jetbrains.annotations.NotNull()
            public static final com.bhashasetu.app.ui.viewmodel.DailyPracticeViewModel.PracticeState.NotStarted INSTANCE = null;
            
            private NotStarted() {
            }
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/bhashasetu/app/ui/viewmodel/DailyPracticeViewModel$PracticeState$Ready;", "Lcom/bhashasetu/app/ui/viewmodel/DailyPracticeViewModel$PracticeState;", "()V", "app_freeDebug"})
        public static final class Ready extends com.bhashasetu.app.ui.viewmodel.DailyPracticeViewModel.PracticeState {
            @org.jetbrains.annotations.NotNull()
            public static final com.bhashasetu.app.ui.viewmodel.DailyPracticeViewModel.PracticeState.Ready INSTANCE = null;
            
            private Ready() {
            }
        }
    }
}