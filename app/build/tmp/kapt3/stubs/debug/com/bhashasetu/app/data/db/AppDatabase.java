package com.bhashasetu.app.data.db;

@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B\u0007\u00a2\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0007H&J\b\u0010\b\u001a\u00020\tH&J\b\u0010\n\u001a\u00020\u000bH&J\b\u0010\f\u001a\u00020\rH&J\b\u0010\u000e\u001a\u00020\u000fH&J\b\u0010\u0010\u001a\u00020\u0011H&J\b\u0010\u0012\u001a\u00020\u0013H&J\b\u0010\u0014\u001a\u00020\u0015H&J\b\u0010\u0016\u001a\u00020\u0017H&\u00a8\u0006\u0019"}, d2 = {"Lcom/bhashasetu/app/data/db/AppDatabase;", "Landroidx/room/RoomDatabase;", "<init>", "()V", "wordDao", "Lcom/bhashasetu/app/data/dao/WordDao;", "categoryDao", "Lcom/bhashasetu/app/data/dao/CategoryDao;", "userProgressDao", "Lcom/bhashasetu/app/data/dao/UserProgressDao;", "lessonDao", "Lcom/bhashasetu/app/data/dao/LessonDao;", "quizDao", "Lcom/bhashasetu/app/data/dao/QuizDao;", "achievementDao", "Lcom/bhashasetu/app/data/dao/AchievementDao;", "settingsDao", "Lcom/bhashasetu/app/data/dao/SettingsDao;", "studySessionDao", "Lcom/bhashasetu/app/data/dao/StudySessionDao;", "userGoalDao", "Lcom/bhashasetu/app/data/dao/UserGoalDao;", "dailyStreakDao", "Lcom/bhashasetu/app/data/dao/DailyStreakDao;", "Companion", "app_debug"})
@androidx.room.Database(entities = {com.bhashasetu.app.data.model.Word.class, com.bhashasetu.app.data.model.Category.class, com.bhashasetu.app.data.model.WordCategoryCrossRef.class, com.bhashasetu.app.data.model.UserProgress.class, com.bhashasetu.app.data.model.Lesson.class, com.bhashasetu.app.data.model.LessonWord.class, com.bhashasetu.app.data.model.Quiz.class, com.bhashasetu.app.data.model.QuizQuestion.class, com.bhashasetu.app.data.model.Achievement.class, com.bhashasetu.app.data.model.AppSettings.class, com.bhashasetu.app.data.model.StudySession.class, com.bhashasetu.app.data.model.UserGoal.class, com.bhashasetu.app.data.model.DailyStreak.class}, version = 2, exportSchema = true)
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
    
    @kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001:\u0001\u000fB\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003J\u0016\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0005H\u0082@\u00a2\u0006\u0002\u0010\u000eR\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lcom/bhashasetu/app/data/db/AppDatabase$Companion;", "", "<init>", "()V", "INSTANCE", "Lcom/bhashasetu/app/data/db/AppDatabase;", "getDatabase", "context", "Landroid/content/Context;", "scope", "Lkotlinx/coroutines/CoroutineScope;", "prePopulateDatabase", "", "database", "(Lcom/bhashasetu/app/data/db/AppDatabase;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "AppDatabaseCallback", "app_debug"})
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
        
        @kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/bhashasetu/app/data/db/AppDatabase$Companion$AppDatabaseCallback;", "Landroidx/room/RoomDatabase$Callback;", "scope", "Lkotlinx/coroutines/CoroutineScope;", "<init>", "(Lkotlinx/coroutines/CoroutineScope;)V", "onCreate", "", "db", "Landroidx/sqlite/db/SupportSQLiteDatabase;", "app_debug"})
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