package com.bhashasetu.app.data.repository

import androidx.lifecycle.LiveData
import com.bhashasetu.app.data.dao.DailyStreakDao
import com.bhashasetu.app.data.model.DailyStreak
import kotlinx.coroutines.flow.Flow
import java.util.Date

class DailyStreakRepository(private val dailyStreakDao: DailyStreakDao) {

    val dailyStreak: Flow<DailyStreak?> = dailyStreakDao.getDailyStreak()
    val hasCheckedInToday: Flow<Boolean> = dailyStreakDao.hasCheckedInToday()
    
    suspend fun insert(dailyStreak: DailyStreak) {
        dailyStreakDao.insert(dailyStreak)
    }
    
    suspend fun update(dailyStreak: DailyStreak) {
        dailyStreakDao.update(dailyStreak)
    }
    
    suspend fun updateStreak(newStreak: Int, checkInDate: Date) {
        dailyStreakDao.updateStreak(newStreak, checkInDate)
    }
    
    suspend fun updateLongestStreak(longestStreak: Int) {
        dailyStreakDao.updateLongestStreak(longestStreak)
    }
    
    suspend fun resetStreak(checkInDate: Date) {
        dailyStreakDao.resetStreak(checkInDate)
    }
}