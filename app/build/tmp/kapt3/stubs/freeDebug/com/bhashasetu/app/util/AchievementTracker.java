package com.bhashasetu.app.util;

/**
 * Tracks user progress and unlocks achievements when criteria are met
 */
@error.NonExistentClass()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000 !2\u00020\u0001:\u0001!B7\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u00a2\u0006\u0002\u0010\u000eJ\u0016\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0082@\u00a2\u0006\u0002\u0010\u0013J\u001c\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00120\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0086@\u00a2\u0006\u0002\u0010\u0018J\u0016\u0010\u0019\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0082@\u00a2\u0006\u0002\u0010\u0013J\u0016\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0082@\u00a2\u0006\u0002\u0010\u0013J\u0016\u0010\u001b\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0082@\u00a2\u0006\u0002\u0010\u0013J\u0016\u0010\u001c\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0082@\u00a2\u0006\u0002\u0010\u0013J\u0016\u0010\u001d\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0082@\u00a2\u0006\u0002\u0010\u0013J\u001e\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u0017H\u0082@\u00a2\u0006\u0002\u0010 R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\""}, d2 = {"Lcom/bhashasetu/app/util/AchievementTracker;", "", "achievementRepository", "Lcom/bhashasetu/app/data/repository/AchievementRepository;", "wordRepository", "Lcom/bhashasetu/app/data/repository/WordRepository;", "userProgressRepository", "Lcom/bhashasetu/app/data/repository/UserProgressRepository;", "lessonRepository", "Lcom/bhashasetu/app/data/repository/LessonRepository;", "quizRepository", "Lcom/bhashasetu/app/data/repository/QuizRepository;", "dailyStreakRepository", "Lcom/bhashasetu/app/data/repository/DailyStreakRepository;", "(Lcom/bhashasetu/app/data/repository/AchievementRepository;Lcom/bhashasetu/app/data/repository/WordRepository;Lcom/bhashasetu/app/data/repository/UserProgressRepository;Lcom/bhashasetu/app/data/repository/LessonRepository;Lcom/bhashasetu/app/data/repository/QuizRepository;Lcom/bhashasetu/app/data/repository/DailyStreakRepository;)V", "checkAverageQuizScoreAchievement", "", "achievement", "Lcom/bhashasetu/app/data/model/Achievement;", "(Lcom/bhashasetu/app/data/model/Achievement;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "checkForUnlockedAchievements", "", "scope", "Lkotlinx/coroutines/CoroutineScope;", "(Lkotlinx/coroutines/CoroutineScope;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "checkLessonsCompletedAchievement", "checkQuizScoreAchievement", "checkStreakDaysAchievement", "checkStudyTimeAchievement", "checkWordsLearnedAchievement", "updateAchievementProgress", "", "(Lcom/bhashasetu/app/data/model/Achievement;Lkotlinx/coroutines/CoroutineScope;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "app_freeDebug"})
public final class AchievementTracker {
    @org.jetbrains.annotations.NotNull()
    private final com.bhashasetu.app.data.repository.AchievementRepository achievementRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.bhashasetu.app.data.repository.WordRepository wordRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.bhashasetu.app.data.repository.UserProgressRepository userProgressRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.bhashasetu.app.data.repository.LessonRepository lessonRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.bhashasetu.app.data.repository.QuizRepository quizRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.bhashasetu.app.data.repository.DailyStreakRepository dailyStreakRepository = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "AchievementTracker";
    @org.jetbrains.annotations.NotNull()
    public static final com.bhashasetu.app.util.AchievementTracker.Companion Companion = null;
    
    @error.NonExistentClass()
    public AchievementTracker(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.repository.AchievementRepository achievementRepository, @org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.repository.WordRepository wordRepository, @org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.repository.UserProgressRepository userProgressRepository, @org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.repository.LessonRepository lessonRepository, @org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.repository.QuizRepository quizRepository, @org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.repository.DailyStreakRepository dailyStreakRepository) {
        super();
    }
    
    /**
     * Checks for and unlocks any achievements the user has earned
     * Should be called after significant user actions (lesson completion, quiz completion, etc.)
     *
     * @param scope The coroutine scope to use for database operations
     * @return List of newly unlocked achievements (if any)
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object checkForUnlockedAchievements(@org.jetbrains.annotations.NotNull()
    kotlinx.coroutines.CoroutineScope scope, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.bhashasetu.app.data.model.Achievement>> $completion) {
        return null;
    }
    
    /**
     * Checks if a lessons_completed achievement should be unlocked
     */
    private final java.lang.Object checkLessonsCompletedAchievement(com.bhashasetu.app.data.model.Achievement achievement, kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    /**
     * Checks if a words_learned achievement should be unlocked
     */
    private final java.lang.Object checkWordsLearnedAchievement(com.bhashasetu.app.data.model.Achievement achievement, kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    /**
     * Checks if a quiz_score achievement should be unlocked
     */
    private final java.lang.Object checkQuizScoreAchievement(com.bhashasetu.app.data.model.Achievement achievement, kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    /**
     * Checks if a streak_days achievement should be unlocked
     */
    private final java.lang.Object checkStreakDaysAchievement(com.bhashasetu.app.data.model.Achievement achievement, kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    /**
     * Checks if a study_time achievement should be unlocked
     */
    private final java.lang.Object checkStudyTimeAchievement(com.bhashasetu.app.data.model.Achievement achievement, kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    /**
     * Checks if an average_quiz_score achievement should be unlocked
     */
    private final java.lang.Object checkAverageQuizScoreAchievement(com.bhashasetu.app.data.model.Achievement achievement, kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    /**
     * Updates the progress value for an achievement
     */
    private final java.lang.Object updateAchievementProgress(com.bhashasetu.app.data.model.Achievement achievement, kotlinx.coroutines.CoroutineScope scope, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/bhashasetu/app/util/AchievementTracker$Companion;", "", "()V", "TAG", "", "app_freeDebug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}