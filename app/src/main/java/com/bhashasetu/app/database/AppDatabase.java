package com.bhashasetu.app.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

//Achievement System Imports (Updated for Consolidation)
// import com.bhashasetu.app.model.Achievement; //Legacy Achievement - removed to avoid import conflict
// import com.bhashasetu.app.data.model.Achievement; //Modern Achievement - removed to avoid import conflict

// Core Entity Imports
import com.bhashasetu.app.model.GameScore;
import com.bhashasetu.app.model.PronunciationSession;
import com.bhashasetu.app.model.User;
import com.bhashasetu.app.model.UserProgress;
import com.bhashasetu.app.model.Word;
import com.bhashasetu.app.model.WordCategory;

// Gamification Imports (NO Achievement conflicts here!)
import com.bhashasetu.app.model.gamification.Badge;
import com.bhashasetu.app.model.gamification.UserLevel;
import com.bhashasetu.app.model.gamification.UserPoints;
import com.bhashasetu.app.model.gamification.UserStats;

// Pronunciation Imports
import com.bhashasetu.app.pronunciation.PronunciationScore;
import com.bhashasetu.app.pronunciation.PronunciationScoreDao;
import com.bhashasetu.app.pronunciation.PronunciationSessionDao;

// Required DAO Imports
import com.bhashasetu.app.database.AchievementDao;
import com.bhashasetu.app.database.ModernAchievementDao;
import com.bhashasetu.app.database.WordDao;
import com.bhashasetu.app.database.WordCategoryDao;
import com.bhashasetu.app.database.UserDao;
import com.bhashasetu.app.database.UserProgressDao;
import com.bhashasetu.app.database.GameScoreDao;
import com.bhashasetu.app.database.BadgeDao;
import com.bhashasetu.app.database.UserLevelDao;
import com.bhashasetu.app.database.UserPointsDao;
import com.bhashasetu.app.database.UserStatsDao;
import com.bhashasetu.app.database.Converters;

// Modern DAO Imports
import com.bhashasetu.app.data.dao.UserGoalDao;
import com.bhashasetu.app.data.dao.LessonDao;

// Migration Import
import com.bhashasetu.app.database.AchievementMigrations;

/**
 * Main Room database class for the application.
 * ✅ ROOM ERROR FIX: Added missing entities and fixed syntax errors
 * Consolidated Achievement System - Two Clean Tables -
 * 1. legacy_achievements (backward compatibility)
 * 2. achievements (modern bilingual system)
 * Defines all entities and provides DAOs for database access.
 */
@Database(entities = {
        // Core Entities
        Word.class,
        WordCategory.class,
        User.class,
        UserProgress.class,
        GameScore.class,

        // Achievement Systems (Consolidated - No More Conflicts)
        com.bhashasetu.app.model.Achievement.class, //Legacy achievement - legacy_achievements
        com.bhashasetu.app.data.model.Achievement.class, //Modern achievement - achievements table

        // Gamification (Achievement conflicts removed)
        Badge.class,
        UserLevel.class,
        UserPoints.class,
        UserStats.class,

        // Pronunciation
        PronunciationSession.class,
        PronunciationScore.class,

        // ✅ ROOM ERROR FIX: Add missing modern entities for lesson_words table and others
        com.bhashasetu.app.data.model.UserGoal.class,
        com.bhashasetu.app.data.model.Lesson.class,
        com.bhashasetu.app.data.model.LessonWord.class,
        com.bhashasetu.app.data.model.Category.class,
        com.bhashasetu.app.data.model.Quiz.class,
        com.bhashasetu.app.data.model.QuizQuestion.class,
        com.bhashasetu.app.data.model.StudySession.class,
        com.bhashasetu.app.data.model.DailyStreak.class,
        com.bhashasetu.app.data.model.AppSettings.class,
        com.bhashasetu.app.data.model.UserProgress.class,
        com.bhashasetu.app.data.model.Word.class,
        com.bhashasetu.app.data.model.WordCategoryCrossRef.class
}, version = 6, exportSchema = false)  // ✅ INCREMENT VERSION FOR SCHEMA CHANGES
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "english_hindi_db";
    private static volatile AppDatabase INSTANCE;

    // DAOs
    // ACHIEVEMENT SYSTEM DAOs (Consolidated)
    public abstract AchievementDao achievementDao(); //Legacy Achievement Dao
    public abstract ModernAchievementDao modernAchievementDao(); // Modern Achievement Dao

    // CORE SYSTEM DAOs
    public abstract WordDao wordDao();
    public abstract WordCategoryDao wordCategoryDao();
    public abstract UserDao userDao();
    public abstract UserProgressDao userProgressDao();
    public abstract GameScoreDao gameScoreDao();

    // GAMIFICATION DAOs (Achievement conflicts removed)
    public abstract BadgeDao badgeDao();
    public abstract UserLevelDao userLevelDao();
    public abstract UserPointsDao userPointsDao();
    public abstract UserStatsDao userStatsDao();

    // PRONUNCIATION DAOs
    public abstract PronunciationSessionDao pronunciationSessionDao();
    public abstract PronunciationScoreDao pronunciationScoreDao();

    // ✅ MODERN SYSTEM DAOs
    public abstract UserGoalDao userGoalDao();
    public abstract LessonDao lessonDao();

    /**
     * Get singleton instance of the database with achievement consolidation migrations.
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
                            // ACHIEVEMENT CONSOLIDATION MIGRATIONS
                            .addMigrations(AchievementMigrations.MIGRATION_3_4) // We'll create this later
                            .fallbackToDestructiveMigration() // Keep as fallback
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}