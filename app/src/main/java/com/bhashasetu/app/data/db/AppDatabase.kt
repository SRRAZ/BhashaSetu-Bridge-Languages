package com.bhashasetu.app.data.db

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Data Access Objects
import com.bhashasetu.app.data.dao.*
import com.bhashasetu.app.database.*

// Modern Kotlin Models
import com.bhashasetu.app.data.model.*

// Legacy Java Models (for backward compatibility)
import com.bhashasetu.app.model.User
import com.bhashasetu.app.model.Word as LegacyWord
import com.bhashasetu.app.model.WordCategory
import com.bhashasetu.app.model.Lesson as LegacyLesson
import com.bhashasetu.app.model.UserProgress as LegacyUserProgress
import com.bhashasetu.app.model.GameScore
import com.bhashasetu.app.model.LessonWord as LegacyLessonWord
import com.bhashasetu.app.model.PronunciationSession
import com.bhashasetu.app.model.Achievement as LegacyAchievement

// Gamification Models
import com.bhashasetu.app.model.gamification.Badge
import com.bhashasetu.app.model.gamification.UserLevel
import com.bhashasetu.app.model.gamification.UserPoints
import com.bhashasetu.app.model.gamification.UserStats

// Pronunciation Models
import com.bhashasetu.app.pronunciation.PronunciationProgressStats
import com.bhashasetu.app.pronunciation.PronunciationScore

// Converters
import com.bhashasetu.app.data.util.DateConverter
import com.bhashasetu.app.database.Converters

/**
 * Comprehensive AppDatabase that consolidates all functionality
 *
 * This database includes:
 * - Modern Kotlin entities with coroutines support
 * - Legacy Java entities for backward compatibility
 * - Proper migration system
 * - Achievement system (both legacy and modern)
 * - Gamification features
 * - Pronunciation tracking
 * - Offline content management
 * - User progress and analytics
 */
@Database(
    entities = [
        // Modern Kotlin Entities (Primary)
        Word::class,
        Category::class,
        WordCategoryCrossRef::class,
        UserProgress::class,
        Lesson::class,
        LessonWord::class,
        Quiz::class,
        QuizQuestion::class,
        Achievement::class,
        AppSettings::class,
        StudySession::class,
        UserGoal::class,
        DailyStreak::class,

        // Legacy Java Entities (Backward Compatibility)
        User::class,
        LegacyWord::class,
        WordCategory::class,
        LegacyLesson::class,
        LegacyUserProgress::class,
        GameScore::class,
        LegacyLessonWord::class,
        PronunciationProgressStats::class,
        LegacyAchievement::class,

        // Gamification Entities
        Badge::class,
        UserLevel::class,
        UserPoints::class,
        UserStats::class,

        // Pronunciation Entities
        PronunciationSession::class,
        PronunciationScore::class
    ],
    version = 10,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 8, to = 9),
        AutoMigration(from = 9, to = 10)
    ]
)
@TypeConverters(DateConverter::class, Converters::class)
abstract class AppDatabase : RoomDatabase() {

    // Modern Kotlin DAOs (Primary)
    abstract fun wordDao(): WordDao
    abstract fun categoryDao(): CategoryDao
    abstract fun userProgressDao(): UserProgressDao
    abstract fun lessonDao(): LessonDao
    abstract fun quizDao(): QuizDao
    abstract fun achievementDao(): AchievementDao
    abstract fun settingsDao(): SettingsDao
    abstract fun studySessionDao(): StudySessionDao
    abstract fun userGoalDao(): UserGoalDao
    abstract fun dailyStreakDao(): DailyStreakDao
    abstract fun appSettingsDao(): AppSettingsDao

    // Legacy Java DAOs (Backward Compatibility)
    abstract fun legacyWordDao(): com.bhashasetu.app.database.WordDao
    abstract fun wordCategoryDao(): WordCategoryDao
    abstract fun userDao(): UserDao
    abstract fun legacyUserProgressDao(): com.bhashasetu.app.database.UserProgressDao
    abstract fun gameScoreDao(): GameScoreDao
    abstract fun legacyAchievementDao(): com.bhashasetu.app.database.AchievementDao
    abstract fun modernAchievementDao(): ModernAchievementDao

    // Gamification DAOs
    abstract fun badgeDao(): BadgeDao
    abstract fun userLevelDao(): UserLevelDao
    abstract fun userPointsDao(): UserPointsDao
    abstract fun userStatsDao(): UserStatsDao

    // Pronunciation DAOs
    abstract fun pronunciationSessionDao(): com.bhashasetu.app.pronunciation.PronunciationSessionDao
    abstract fun pronunciationScoreDao(): com.bhashasetu.app.pronunciation.PronunciationScoreDao
    abstract fun pronunciationProgressStatsDao(): PronunciationProgressStatsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        private const val DATABASE_NAME = "bhashasetu_comprehensive_db"

        /**
         * Get database instance with proper initialization
         */
        fun getDatabase(
            context: Context,
            scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
        ): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                )
                    .addMigrations(
                        MIGRATION_7_8,
                        MIGRATION_8_9,
                        MIGRATION_9_10
                    )
                    .addCallback(AppDatabaseCallback(scope))
                    .fallbackToDestructiveMigration() // Only for development
                    .build()
                INSTANCE = instance
                instance
            }
        }

        /**
         * Alternative method for Java compatibility
         */
        fun getInstance(context: Context): AppDatabase {
            return getDatabase(context)
        }

        /**
         * Migration from version 7 to 8 - Achievement system consolidation
         */
        private val MIGRATION_7_8 = object : Migration(7, 8) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Create new achievement tables to resolve conflicts
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS `modern_achievements` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `titleEn` TEXT NOT NULL,
                        `titleHi` TEXT NOT NULL,
                        `descriptionEn` TEXT NOT NULL,
                        `descriptionHi` TEXT NOT NULL,
                        `iconName` TEXT NOT NULL,
                        `type` TEXT NOT NULL,
                        `targetValue` INTEGER NOT NULL,
                        `currentValue` INTEGER NOT NULL DEFAULT 0,
                        `isUnlocked` INTEGER NOT NULL DEFAULT 0,
                        `unlockedAt` INTEGER,
                        `category` TEXT NOT NULL,
                        `difficulty` TEXT NOT NULL,
                        `xpReward` INTEGER NOT NULL DEFAULT 0,
                        `createdAt` INTEGER NOT NULL,
                        `updatedAt` INTEGER NOT NULL
                    )
                """.trimIndent())

                // Migrate existing achievements if they exist
                database.execSQL("""
                    INSERT INTO modern_achievements (titleEn, titleHi, descriptionEn, descriptionHi, iconName, type, targetValue, category, difficulty, xpReward, createdAt, updatedAt)
                    SELECT title, title, description, description, 'default', 'progress', 100, 'general', 'easy', 50, 
                           strftime('%s', 'now') * 1000, strftime('%s', 'now') * 1000
                    FROM achievements WHERE achievements.id IS NOT NULL
                """.trimIndent())
            }
        }

        /**
         * Migration from version 8 to 9 - Enhanced word tracking
         */
        private val MIGRATION_8_9 = object : Migration(8, 9) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Add new columns for better word tracking
                database.execSQL("ALTER TABLE words ADD COLUMN `difficultyScore` REAL NOT NULL DEFAULT 0.0")
                database.execSQL("ALTER TABLE words ADD COLUMN `lastPracticed` INTEGER DEFAULT NULL")
                database.execSQL("ALTER TABLE words ADD COLUMN `masteryLevel` INTEGER NOT NULL DEFAULT 0")
                database.execSQL("ALTER TABLE words ADD COLUMN `practiceCount` INTEGER NOT NULL DEFAULT 0")
                database.execSQL("ALTER TABLE words ADD COLUMN `correctAnswers` INTEGER NOT NULL DEFAULT 0")
                database.execSQL("ALTER TABLE words ADD COLUMN `totalAnswers` INTEGER NOT NULL DEFAULT 0")
            }
        }

        /**
         * Migration from version 9 to 10 - Settings and streaks
         */
        private val MIGRATION_9_10 = object : Migration(9, 10) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Create daily streaks table
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS `daily_streaks` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `date` TEXT NOT NULL,
                        `studyTimeMinutes` INTEGER NOT NULL DEFAULT 0,
                        `wordsLearned` INTEGER NOT NULL DEFAULT 0,
                        `lessonsCompleted` INTEGER NOT NULL DEFAULT 0,
                        `quizzesTaken` INTEGER NOT NULL DEFAULT 0,
                        `pronunciationPracticed` INTEGER NOT NULL DEFAULT 0,
                        `streakCount` INTEGER NOT NULL DEFAULT 0,
                        `isGoalMet` INTEGER NOT NULL DEFAULT 0,
                        `createdAt` INTEGER NOT NULL
                    )
                """.trimIndent())

                // Create unique index on date
                database.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_daily_streaks_date` ON `daily_streaks` (`date`)")

                // Enhance app settings
                database.execSQL("ALTER TABLE app_settings ADD COLUMN `achievementNotifications` INTEGER NOT NULL DEFAULT 1")
                database.execSQL("ALTER TABLE app_settings ADD COLUMN `difficultyLevel` TEXT NOT NULL DEFAULT 'Beginner'")
                database.execSQL("ALTER TABLE app_settings ADD COLUMN `dailyGoalMinutes` INTEGER NOT NULL DEFAULT 15")
                database.execSQL("ALTER TABLE app_settings ADD COLUMN `offlineModeEnabled` INTEGER NOT NULL DEFAULT 0")
                database.execSQL("ALTER TABLE app_settings ADD COLUMN `analyticsEnabled` INTEGER NOT NULL DEFAULT 1")
            }
        }

        /**
         * Database callback for initialization
         */
        private class AppDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch {
                        prePopulateDatabase(database)
                    }
                }
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                // Ensure foreign key constraints are enabled
                db.execSQL("PRAGMA foreign_keys=ON;")
            }
        }

        /**
         * Pre-populate database with initial data
         */
        private suspend fun prePopulateDatabase(database: AppDatabase) {
            try {
                // Add default categories
                val categoryDao = database.categoryDao()
                val categories = listOf(
                    Category(
                        id = 1,
                        name = "Basic Words",
                        nameHindi = "बुनियादी शब्द",
                        description = "Essential vocabulary for beginners",
                        descriptionHindi = "शुरुआती लोगों के लिए आवश्यक शब्दावली",
                        colorCode = "#FF6B6B",
                        iconName = "basic"
                    ),
                    Category(
                        id = 2,
                        name = "Food & Drink",
                        nameHindi = "खाना और पेय",
                        description = "Food and beverage vocabulary",
                        descriptionHindi = "खाने-पीने की शब्दावली",
                        colorCode = "#4ECDC4",
                        iconName = "food"
                    ),
                    Category(
                        id = 3,
                        name = "Numbers",
                        nameHindi = "संख्याएं",
                        description = "Numbers and counting",
                        descriptionHindi = "संख्या और गिनती",
                        colorCode = "#45B7D1",
                        iconName = "numbers"
                    ),
                    Category(
                        id = 4,
                        name = "Family",
                        nameHindi = "परिवार",
                        description = "Family relationships and terms",
                        descriptionHindi = "पारिवारिक रिश्ते और शब्द",
                        colorCode = "#F7DC6F",
                        iconName = "family"
                    ),
                    Category(
                        id = 5,
                        name = "Colors",
                        nameHindi = "रंग",
                        description = "Colors and shades",
                        descriptionHindi = "रंग और उनके शेड्स",
                        colorCode = "#BB8FCE",
                        iconName = "colors"
                    )
                )
                categoryDao.insertAll(categories)

                // Add default app settings
                val settingsDao = database.appSettingsDao()
                val defaultSettings = AppSettings(
                    id = 1,
                    language = "en",
                    notificationsEnabled = true,
                    dailyReminderTime = "09:00",
                    darkModeEnabled = false,
                    soundEnabled = true,
                    vibrationEnabled = true,
                    difficultyLevel = "Beginner",
                    dailyGoalMinutes = 15,
                    offlineModeEnabled = false,
                    analyticsEnabled = true,
                    achievementNotifications = true
                )
                settingsDao.insert(defaultSettings)

                // Add sample words for each category
                val wordDao = database.wordDao()
                val sampleWords = listOf(
                    // Basic Words
                    Word(
                        id = 1,
                        english = "Hello",
                        hindi = "नमस्ते",
                        pronunciation = "namaste",
                        phonetic = "/nə-məs-ˈtā/",
                        definition = "A greeting used when meeting someone",
                        definitionHindi = "किसी से मिलते समय प्रयोग होने वाला अभिवादन",
                        exampleSentence = "Hello, how are you?",
                        exampleSentenceHindi = "नमस्ते, आप कैसे हैं?",
                        categoryId = 1,
                        difficultyLevel = 1,
                        isActive = true
                    ),
                    Word(
                        id = 2,
                        english = "Thank you",
                        hindi = "धन्यवाद",
                        pronunciation = "dhanyawad",
                        phonetic = "/dʰən-jə-ˈwɑːd/",
                        definition = "An expression of gratitude",
                        definitionHindi = "कृतज्ञता का प्रकाशन",
                        exampleSentence = "Thank you for your help",
                        exampleSentenceHindi = "आपकी सहायता के लिए धन्यवाद",
                        categoryId = 1,
                        difficultyLevel = 1,
                        isActive = true
                    ),
                    // Food Words
                    Word(
                        id = 3,
                        english = "Water",
                        hindi = "पानी",
                        pronunciation = "paani",
                        phonetic = "/pɑː-niː/",
                        definition = "A clear liquid essential for life",
                        definitionHindi = "जीवन के लिए आवश्यक एक स्वच्छ तरल",
                        exampleSentence = "I drink water every day",
                        exampleSentenceHindi = "मैं हर दिन पानी पीता हूं",
                        categoryId = 2,
                        difficultyLevel = 1,
                        isActive = true
                    )
                )
                wordDao.insertAll(sampleWords)

                // Add initial achievements
                val achievementDao = database.achievementDao()
                val initialAchievements = listOf(
                    Achievement(
                        id = 1,
                        titleEn = "First Steps",
                        titleHi = "पहले कदम",
                        descriptionEn = "Learn your first 5 words",
                        descriptionHi = "अपने पहले 5 शब्द सीखें",
                        iconName = "first_steps",
                        type = "word_count",
                        targetValue = 5,
                        category = "learning",
                        difficulty = "easy",
                        xpReward = 100
                    ),
                    Achievement(
                        id = 2,
                        titleEn = "Practice Makes Perfect",
                        titleHi = "अभ्यास से सफलता",
                        descriptionEn = "Complete 7 days of practice",
                        descriptionHi = "7 दिन अभ्यास पूरा करें",
                        iconName = "practice_streak",
                        type = "streak",
                        targetValue = 7,
                        category = "consistency",
                        difficulty = "medium",
                        xpReward = 250
                    )
                )
                achievementDao.insertAll(initialAchievements)

            } catch (e: Exception) {
                // Log error but don't crash the app
                android.util.Log.e("AppDatabase", "Error pre-populating database", e)
            }
        }

        /**
         * Clear all data (for testing or reset)
         */
        suspend fun clearAllData(database: AppDatabase) {
            database.clearAllTables()
        }

        /**
         * Export database (for backup)
         */
        fun exportDatabase(context: Context): String? {
            return try {
                val dbFile = context.getDatabasePath(DATABASE_NAME)
                dbFile.absolutePath
            } catch (e: Exception) {
                null
            }
        }
    }
}