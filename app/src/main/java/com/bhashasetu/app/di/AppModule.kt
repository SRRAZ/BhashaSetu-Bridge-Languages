package com.bhashasetu.app.di

import android.content.Context
import com.bhashasetu.app.data.dao.*
import com.bhashasetu.app.data.db.AppDatabase
import com.bhashasetu.app.data.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class ApplicationScope
    
    @Singleton
    @ApplicationScope
    @Provides
    fun provideApplicationScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob())
    }
    
    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context,
        @ApplicationScope scope: CoroutineScope
    ): AppDatabase {
        return AppDatabase.getDatabase(context, scope)
    }
    
    @Singleton
    @Provides
    fun provideWordDao(database: AppDatabase): WordDao {
        return database.wordDao()
    }
    
    @Singleton
    @Provides
    fun provideCategoryDao(database: AppDatabase): CategoryDao {
        return database.categoryDao()
    }
    
    @Singleton
    @Provides
    fun provideUserProgressDao(database: AppDatabase): UserProgressDao {
        return database.userProgressDao()
    }
    
    @Singleton
    @Provides
    fun provideLessonDao(database: AppDatabase): LessonDao {
        return database.lessonDao()
    }
    
    @Singleton
    @Provides
    fun provideQuizDao(database: AppDatabase): QuizDao {
        return database.quizDao()
    }
    
    @Singleton
    @Provides
    fun provideAchievementDao(database: AppDatabase): AchievementDao {
        return database.achievementDao()
    }
    
    @Singleton
    @Provides
    fun provideSettingsDao(database: AppDatabase): SettingsDao {
        return database.settingsDao()
    }
    
    @Singleton
    @Provides
    fun provideStudySessionDao(database: AppDatabase): StudySessionDao {
        return database.studySessionDao()
    }
    
    @Singleton
    @Provides
    fun provideUserGoalDao(database: AppDatabase): UserGoalDao {
        return database.userGoalDao()
    }
    
    @Singleton
    @Provides
    fun provideDailyStreakDao(database: AppDatabase): DailyStreakDao {
        return database.dailyStreakDao()
    }
    
    @Singleton
    @Provides
    fun provideWordRepository(wordDao: WordDao): WordRepository {
        return WordRepository(wordDao)
    }
    
    @Singleton
    @Provides
    fun provideCategoryRepository(categoryDao: CategoryDao): CategoryRepository {
        return CategoryRepository(categoryDao)
    }
    
    @Singleton
    @Provides
    fun provideUserProgressRepository(userProgressDao: UserProgressDao): UserProgressRepository {
        return UserProgressRepository(userProgressDao)
    }
    
    @Singleton
    @Provides
    fun provideLessonRepository(lessonDao: LessonDao): LessonRepository {
        return LessonRepository(lessonDao)
    }
    
    @Singleton
    @Provides
    fun provideQuizRepository(quizDao: QuizDao): QuizRepository {
        return QuizRepository(quizDao)
    }
    
    @Singleton
    @Provides
    fun provideAchievementRepository(achievementDao: AchievementDao): AchievementRepository {
        return AchievementRepository(achievementDao)
    }
    
    @Singleton
    @Provides
    fun provideAppSettingsRepository(settingsDao: SettingsDao): AppSettingsRepository {
        return AppSettingsRepository(settingsDao)
    }
    
    @Singleton
    @Provides
    fun provideStudySessionRepository(studySessionDao: StudySessionDao): StudySessionRepository {
        return StudySessionRepository(studySessionDao)
    }
    
    @Singleton
    @Provides
    fun provideUserGoalRepository(userGoalDao: UserGoalDao): UserGoalRepository {
        return UserGoalRepository(userGoalDao)
    }
    
    @Singleton
    @Provides
    fun provideDailyStreakRepository(dailyStreakDao: DailyStreakDao): DailyStreakRepository {
        return DailyStreakRepository(dailyStreakDao)
    }
}