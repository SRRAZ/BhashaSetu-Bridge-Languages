package com.example.englishhindi.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.englishhindi.model.Achievement;
import com.example.englishhindi.model.GameScore;
import com.example.englishhindi.model.PronunciationSession;
import com.example.englishhindi.model.User;
import com.example.englishhindi.model.UserProgress;
import com.example.englishhindi.model.Word;
import com.example.englishhindi.model.WordCategory;
import com.example.englishhindi.pronunciation.PronunciationScore;
import com.example.englishhindi.pronunciation.PronunciationScoreDao;
import com.example.englishhindi.pronunciation.PronunciationSessionDao;

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
        PronunciationSession.class,
        PronunciationScore.class
}, version = 1, exportSchema = false)
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