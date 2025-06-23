package com.bhashasetu.app.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

//Achievement System Imports (Updated for Consolidation)
import com.bhashasetu.app.model.Achievement; //Legacy Achievement
import com.bhashasetu.app.data.model.Achievement as ModernAchievement; //Modern Achievement

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

// Migration Import (We'll Create this file next)
import com.bhashasetu.app.database.AchievementMigrations;
/**
 * Main Room database class for the application.
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
        Achievement.class, //Legacy achievement - legacy_achievements
        ModernAchievement.class, //Modern achievement - achievements table

        // Gamification (Achievement conflicts removed)
        Badge.class,
        UserLevel.class,
        UserPoints.class,
        UserStats.class,

        // Pronunciation
        PronunciationSession.class,
        PronunciationScore.class
}, version = 4, exportSchema = false)  // INCREMENT VERSION FOR EACH CHANGE
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

    /**
     * Get singleton instance of the database with achievement consolidation migrations.
     * @param context Application context
     * @return The AppDatabase instance
     */
    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
            INSTANCE == Room.databaseBuilder(
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