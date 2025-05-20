package com.example.englishhindi.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.englishhindi.data.dao.*
import com.example.englishhindi.data.model.*
import com.example.englishhindi.data.util.DateConverter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
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
        DailyStreak::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

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

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "english_hindi_database"
                )
                    .addCallback(AppDatabaseCallback(scope))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class AppDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        // Prepopulate database with initial data here
                        prePopulateDatabase(database)
                    }
                }
            }
        }

        private suspend fun prePopulateDatabase(database: AppDatabase) {
            // Add default categories
            val categoryDao = database.categoryDao()
            val basicCategory = Category(id = 1, name = "Basic", nameHindi = "बुनियादी", description = "Basic vocabulary for beginners", descriptionHindi = "शुरुआती लोगों के लिए बुनियादी शब्दावली")
            val foodCategory = Category(id = 2, name = "Food", nameHindi = "खाना", description = "Food related vocabulary", descriptionHindi = "खाने से संबंधित शब्दावली")
            val numbersCategory = Category(id = 3, name = "Numbers", nameHindi = "संख्या", description = "Numbers and counting", descriptionHindi = "संख्या और गिनती")
            
            categoryDao.insertAll(listOf(basicCategory, foodCategory, numbersCategory))
            
            // Add default settings
            val settingsDao = database.settingsDao()
            settingsDao.insert(AppSettings(id = 1, language = "en", notificationsEnabled = true, dailyReminderTime = "09:00", soundEnabled = true, vibrationEnabled = true))
        }
    }
}