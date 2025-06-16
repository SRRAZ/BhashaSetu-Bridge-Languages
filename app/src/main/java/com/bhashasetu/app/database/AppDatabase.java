package com.bhashasetu.app.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

//import com.bhashasetu.app.data.model.Achievement as ModernAchievement;
import com.bhashasetu.app.model.Achievement;
import com.bhashasetu.app.model.GameScore;
import com.bhashasetu.app.model.PronunciationSession;
import com.bhashasetu.app.model.User;
import com.bhashasetu.app.model.UserProgress;
import com.bhashasetu.app.model.Word;
import com.bhashasetu.app.model.WordCategory;
import com.bhashasetu.app.model.gamification.Badge;
import com.bhashasetu.app.model.gamification.UserLevel;
import com.bhashasetu.app.model.gamification.UserPoints;
import com.bhashasetu.app.model.gamification.UserStats;
import com.bhashasetu.app.pronunciation.PronunciationScore;
import com.bhashasetu.app.pronunciation.PronunciationScoreDao;
import com.bhashasetu.app.pronunciation.PronunciationSessionDao;

/**
 * Main Room database class for the application.
 * Defines all entities and provides DAOs for database access.
 */
@Database(entities = {
        Word.class,
        WordCategory.class,
        User.class,
        UserProgress.class,
        GameScore.class,
        Achievement.class,
        ModernAchievement.class,
        Badge.class,
        UserLevel.class,
        UserPoints.class,
        UserStats.class,
        PronunciationSession.class,
        PronunciationScore.class
}, version = 3, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "english_hindi_db";
    private static volatile AppDatabase INSTANCE;

    // DAOs
    public abstract WordDao wordDao();
    public abstract WordCategoryDao wordCategoryDao();
    public abstract UserDao userDao();
    public abstract UserProgressDao userProgressDao();
    public abstract GameScoreDao gameScoreDao();
    public abstract AchievementDao achievementDao();
    public abstract ModernAchievementDao modernAchievementDao();
    public abstract BadgeDao badgeDao();
    public abstract UserLevelDao userLevelDao();
    public abstract UserPointsDao userPointsDao();
    public abstract UserStatsDao userStatsDao();
    public abstract PronunciationSessionDao pronunciationSessionDao();
    public abstract PronunciationScoreDao pronunciationScoreDao();

    /**
     * Get singleton instance of the database
     * @param context Application context
     * @return The AppDatabase instance
     */
    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}