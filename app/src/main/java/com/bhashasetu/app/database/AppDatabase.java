package com.bhashasetu.app.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.TypeConverters;

//Achievement System Imports (Updated for Consolidation)
// import com.bhashasetu.app.model.Achievement; //Legacy Achievement - removed to avoid import conflict
// import com.bhashasetu.app.data.model.Achievement; //Modern Achievement - removed to avoid import conflict

// Core Entity Imports
import com.bhashasetu.app.model.LessonWord;
import com.bhashasetu.app.model.User;
import com.bhashasetu.app.model.Word;
import com.bhashasetu.app.model.WordCategory;
import com.bhashasetu.app.model.Lesson;
import com.bhashasetu.app.model.UserProgress;
import com.bhashasetu.app.model.GameScore;
import com.bhashasetu.app.pronunciation.PronunciationProgressStats;
import com.bhashasetu.app.model.PronunciationSession;

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
        User.class,
        Word.class,
//        com.bhashasetu.app.model.Word.class,  // ✅ Use the fixed one
        WordCategory.class,
        Lesson.class,
        UserProgress.class,
        GameScore.class,
        LessonWord.class,
        PronunciationProgressStats.class,
        // DON'T include com.bhashasetu.app.data.model.Word

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
},
        version = 8, exportSchema = true)  // ✅ INCREMENT VERSION FOR SCHEMA CHANGES
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
    public abstract PronunciationProgressStatsDao pronunciationProgressStatsDao();

    // ✅ MODERN SYSTEM DAOs
    public abstract UserGoalDao userGoalDao();
    public abstract LessonDao lessonDao();
    public abstract appSettingsDao appSettingsDao();

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
                            .fallbackToDestructiveMigration() // Keep as fallback -Use with caution in production
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // Migration from version 3 to 4
    private static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Handle table name changes to resolve conflicts
            // 1. Rename old words table if it exists
            database.execSQL("ALTER TABLE words RENAME TO words_old");

            // 2. Create new vocabulary_words table
            database.execSQL("CREATE TABLE IF NOT EXISTS `vocabulary_words` (" +
                    "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "`englishText` TEXT, " +
                    "`localText` TEXT, " +
                    "`pronunciation` TEXT, " +
                    "`phonetic` TEXT, " +
                    "`audioUrl` TEXT, " +
                    "`imageUrl` TEXT, " +
                    "`definition` TEXT, " +
                    "`exampleSentence` TEXT, " +
                    "`exampleTranslation` TEXT, " +
                    "`categoryId` INTEGER NOT NULL, " +
                    "`lessonId` INTEGER NOT NULL, " +
                    "`difficultyLevel` INTEGER NOT NULL, " +
                    "`orderInLesson` INTEGER NOT NULL, " +
                    "`isActive` INTEGER NOT NULL, " +
                    "`createdAt` INTEGER NOT NULL, " +
                    "`updatedAt` INTEGER NOT NULL, " +
                    "`partOfSpeech` TEXT, " +
                    "`synonyms` TEXT, " +
                    "`antonyms` TEXT, " +
                    "`frequency` INTEGER NOT NULL, " +
                    "`languageCode` TEXT, " +
                    "FOREIGN KEY(`categoryId`) REFERENCES `word_categories`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE)");

            // 3. Create indexes for vocabulary_words
            database.execSQL("CREATE INDEX IF NOT EXISTS `index_vocabulary_words_categoryId` ON `vocabulary_words` (`categoryId`)");
            database.execSQL("CREATE INDEX IF NOT EXISTS `index_vocabulary_words_lessonId` ON `vocabulary_words` (`lessonId`)");
            database.execSQL("CREATE INDEX IF NOT EXISTS `index_vocabulary_words_englishText` ON `vocabulary_words` (`englishText`)");

            // 4. Migrate data from old table to new table
            database.execSQL("INSERT INTO vocabulary_words (id, englishText, localText, pronunciation, definition, categoryId, lessonId, difficultyLevel, orderInLesson, isActive, createdAt, updatedAt, frequency) " +
                    "SELECT id, " +
                    "COALESCE(englishText, text, word) as englishText, " +
                    "COALESCE(localText, translation, localTranslation) as localText, " +
                    "pronunciation, " +
                    "COALESCE(definition, meaning) as definition, " +
                    "COALESCE(categoryId, category_id, 1) as categoryId, " +
                    "COALESCE(lessonId, lesson_id, 1) as lessonId, " +
                    "COALESCE(difficultyLevel, difficulty, 1) as difficultyLevel, " +
                    "COALESCE(orderInLesson, order_in_lesson, 0) as orderInLesson, " +
                    "COALESCE(isActive, is_active, 1) as isActive, " +
                    "COALESCE(createdAt, created_at, strftime('%s', 'now') * 1000) as createdAt, " +
                    "COALESCE(updatedAt, updated_at, strftime('%s', 'now') * 1000) as updatedAt, " +
                    "COALESCE(frequency, 1) as frequency " +
                    "FROM words_old");

            // 5. Drop old table
            database.execSQL("DROP TABLE words_old");

            // 6. Handle user_progress table rename
            database.execSQL("ALTER TABLE user_progress RENAME TO user_progress_old");

            // 7. Create new user_learning_progress table
            database.execSQL("CREATE TABLE IF NOT EXISTS `user_learning_progress` (" +
                    "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "`userId` INTEGER NOT NULL, " +
                    "`wordId` INTEGER NOT NULL, " +
                    "`lessonId` INTEGER NOT NULL, " +
                    "`masteryLevel` INTEGER NOT NULL, " +
                    "`correctAttempts` INTEGER NOT NULL, " +
                    "`totalAttempts` INTEGER NOT NULL, " +
                    "`accuracy` REAL NOT NULL, " +
                    "`firstLearnedAt` INTEGER NOT NULL, " +
                    "`lastReviewedAt` INTEGER NOT NULL, " +
                    "`nextReviewAt` INTEGER NOT NULL, " +
                    "`isCompleted` INTEGER NOT NULL, " +
                    "`isMastered` INTEGER NOT NULL, " +
                    "`streakCount` INTEGER NOT NULL, " +
                    "`reviewCount` INTEGER NOT NULL, " +
                    "`learningStage` TEXT, " +
                    "`totalTimeSpent` INTEGER NOT NULL, " +
                    "`difficulty` TEXT, " +
                    "FOREIGN KEY(`userId`) REFERENCES `users`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE, " +
                    "FOREIGN KEY(`wordId`) REFERENCES `vocabulary_words`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE)");

            // 8. Create indexes for user_learning_progress
            database.execSQL("CREATE INDEX IF NOT EXISTS `index_user_learning_progress_userId` ON `user_learning_progress` (`userId`)");
            database.execSQL("CREATE INDEX IF NOT EXISTS `index_user_learning_progress_wordId` ON `user_learning_progress` (`wordId`)");
            database.execSQL("CREATE INDEX IF NOT EXISTS `index_user_learning_progress_lessonId` ON `user_learning_progress` (`lessonId`)");

            // 9. Migrate user progress data
            database.execSQL("INSERT INTO user_learning_progress (id, userId, wordId, lessonId, masteryLevel, correctAttempts, totalAttempts, accuracy, firstLearnedAt, lastReviewedAt, nextReviewAt, isCompleted, isMastered, streakCount, reviewCount, learningStage, totalTimeSpent, difficulty) " +
                    "SELECT id, " +
                    "userId, " +
                    "wordId, " +
                    "COALESCE(lessonId, 1) as lessonId, " +
                    "COALESCE(masteryLevel, progress, 0) as masteryLevel, " +
                    "COALESCE(correctAttempts, correct_attempts, 0) as correctAttempts, " +
                    "COALESCE(totalAttempts, total_attempts, 0) as totalAttempts, " +
                    "COALESCE(accuracy, 0.0) as accuracy, " +
                    "COALESCE(firstLearnedAt, first_learned_at, created_at, strftime('%s', 'now') * 1000) as firstLearnedAt, " +
                    "COALESCE(lastReviewedAt, last_reviewed_at, updated_at, strftime('%s', 'now') * 1000) as lastReviewedAt, " +
                    "COALESCE(nextReviewAt, strftime('%s', 'now') * 1000 + 86400000) as nextReviewAt, " +
                    "COALESCE(isCompleted, is_completed, 0) as isCompleted, " +
                    "COALESCE(isMastered, is_mastered, 0) as isMastered, " +
                    "COALESCE(streakCount, streak_count, 0) as streakCount, " +
                    "COALESCE(reviewCount, review_count, 0) as reviewCount, " +
                    "COALESCE(learningStage, 'learning') as learningStage, " +
                    "COALESCE(totalTimeSpent, total_time_spent, 0) as totalTimeSpent, " +
                    "COALESCE(difficulty, 'beginner') as difficulty " +
                    "FROM user_progress_old");

            // 10. Drop old user_progress table
            database.execSQL("DROP TABLE user_progress_old");

            // 11. Add missing indexes for game_scores
            database.execSQL("CREATE INDEX IF NOT EXISTS `index_game_scores_userId` ON `game_scores` (`userId`)");
            database.execSQL("CREATE INDEX IF NOT EXISTS `index_game_scores_gameType` ON `game_scores` (`gameType`)");
        }
    };
}