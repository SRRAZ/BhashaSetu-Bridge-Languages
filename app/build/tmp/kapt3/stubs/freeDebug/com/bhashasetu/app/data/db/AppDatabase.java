package com.bhashasetu.app.data.db;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&J\b\u0010\t\u001a\u00020\nH&J\b\u0010\u000b\u001a\u00020\fH&J\b\u0010\r\u001a\u00020\u000eH&J\b\u0010\u000f\u001a\u00020\u0010H&J\b\u0010\u0011\u001a\u00020\u0012H&J\b\u0010\u0013\u001a\u00020\u0014H&J\b\u0010\u0015\u001a\u00020\u0016H&\u00a8\u0006\u0018"}, d2 = {"Lcom/bhashasetu/app/data/db/AppDatabase;", "Landroidx/room/RoomDatabase;", "()V", "achievementDao", "Lcom/bhashasetu/app/data/dao/AchievementDao;", "categoryDao", "Lcom/bhashasetu/app/data/dao/CategoryDao;", "dailyStreakDao", "Lcom/bhashasetu/app/data/dao/DailyStreakDao;", "lessonDao", "Lcom/bhashasetu/app/data/dao/LessonDao;", "quizDao", "Lcom/bhashasetu/app/data/dao/QuizDao;", "settingsDao", "Lcom/bhashasetu/app/data/dao/SettingsDao;", "studySessionDao", "Lcom/bhashasetu/app/data/dao/StudySessionDao;", "userGoalDao", "Lcom/bhashasetu/app/data/dao/UserGoalDao;", "userProgressDao", "Lcom/bhashasetu/app/data/dao/UserProgressDao;", "wordDao", "Lcom/bhashasetu/app/data/dao/WordDao;", "Companion", "app_freeDebug"})
@androidx.room.Database(entities = {com.bhashasetu.app.data.model.Word.class, com.bhashasetu.app.data.model.Category.class, com.bhashasetu.app.data.model.WordCategoryCrossRef.class, com.bhashasetu.app.data.model.UserProgress.class, com.bhashasetu.app.data.model.Lesson.class, com.bhashasetu.app.data.model.LessonWord.class, com.bhashasetu.app.data.model.Quiz.class, com.bhashasetu.app.data.model.QuizQuestion.class, com.bhashasetu.app.data.model.Achievement.class, com.bhashasetu.app.data.model.AppSettings.class, com.bhashasetu.app.data.model.StudySession.class, com.bhashasetu.app.data.model.UserGoal.class, com.bhashasetu.app.data.model.DailyStreak.class}, version = 1, exportSchema = true)
@androidx.room.TypeConverters(value = {com.bhashasetu.app.data.util.DateConverter.class})
public abstract class AppDatabase extends androidx.room.RoomDatabase {
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private static volatile com.bhashasetu.app.data.db.AppDatabase INSTANCE;
    @org.jetbrains.annotations.NotNull()
    public static final com.bhashasetu.app.data.db.AppDatabase.Companion Companion = null;
    
    public AppDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.bhashasetu.app.data.dao.WordDao wordDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.bhashasetu.app.data.dao.CategoryDao categoryDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.bhashasetu.app.data.dao.UserProgressDao userProgressDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.bhashasetu.app.data.dao.LessonDao lessonDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.bhashasetu.app.data.dao.QuizDao quizDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.bhashasetu.app.data.dao.AchievementDao achievementDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.bhashasetu.app.data.dao.SettingsDao settingsDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.bhashasetu.app.data.dao.StudySessionDao studySessionDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.bhashasetu.app.data.dao.UserGoalDao userGoalDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.bhashasetu.app.data.dao.DailyStreakDao dailyStreakDao();
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001:\u0001\u000eB\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ\u0016\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0004H\u0082@\u00a2\u0006\u0002\u0010\rR\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2 = {"Lcom/bhashasetu/app/data/db/AppDatabase$Companion;", "", "()V", "INSTANCE", "Lcom/bhashasetu/app/data/db/AppDatabase;", "getDatabase", "context", "Landroid/content/Context;", "scope", "Lkotlinx/coroutines/CoroutineScope;", "prePopulateDatabase", "", "database", "(Lcom/bhashasetu/app/data/db/AppDatabase;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "AppDatabaseCallback", "app_freeDebug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.bhashasetu.app.data.db.AppDatabase getDatabase(@org.jetbrains.annotations.NotNull()
        android.content.Context context, @org.jetbrains.annotations.NotNull()
        kotlinx.coroutines.CoroutineScope scope) {
            return null;
        }
        
        private final java.lang.Object prePopulateDatabase(com.bhashasetu.app.data.db.AppDatabase database, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
            return null;
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/bhashasetu/app/data/db/AppDatabase$Companion$AppDatabaseCallback;", "Landroidx/room/RoomDatabase$Callback;", "scope", "Lkotlinx/coroutines/CoroutineScope;", "(Lkotlinx/coroutines/CoroutineScope;)V", "onCreate", "", "db", "Landroidx/sqlite/db/SupportSQLiteDatabase;", "app_freeDebug"})
        static final class AppDatabaseCallback extends androidx.room.RoomDatabase.Callback {
            @org.jetbrains.annotations.NotNull()
            private final kotlinx.coroutines.CoroutineScope scope = null;
            
            public AppDatabaseCallback(@org.jetbrains.annotations.NotNull()
            kotlinx.coroutines.CoroutineScope scope) {
                super();
            }
            
            @java.lang.Override()
            public void onCreate(@org.jetbrains.annotations.NotNull()
            androidx.sqlite.db.SupportSQLiteDatabase db) {
            }
        }
    }
}