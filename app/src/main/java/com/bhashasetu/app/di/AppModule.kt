package com.bhashasetu.app.di

import android.content.Context
import androidx.room.Room
import com.bhashasetu.app.data.db.AppDatabase
import com.bhashasetu.app.data.repository.WordRepository
import com.bhashasetu.app.data.repository.WordRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "english_hindi_learning_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideWordRepository(database: AppDatabase): WordRepository {
        return WordRepositoryImpl(database.wordDao())
    }
}