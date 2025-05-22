package com.example.englishhindi.data.repository

import androidx.lifecycle.LiveData
import com.example.englishhindi.data.dao.AchievementDao
import com.example.englishhindi.data.model.Achievement
import kotlinx.coroutines.flow.Flow

class AchievementRepository(private val achievementDao: AchievementDao) {

    val allAchievements: Flow<List<Achievement>> = achievementDao.getAllAchievements()
    val unlockedAchievements: Flow<List<Achievement>> = achievementDao.getUnlockedAchievements()
    val lockedAchievements: Flow<List<Achievement>> = achievementDao.getLockedAchievements()
    val unlockedAchievementCount: Flow<Int> = achievementDao.getUnlockedAchievementCount()
    
    suspend fun insert(achievement: Achievement) {
        achievementDao.insert(achievement)
    }
    
    suspend fun update(achievement: Achievement) {
        achievementDao.update(achievement)
    }
    
    suspend fun delete(achievement: Achievement) {
        achievementDao.delete(achievement)
    }
    
    fun getAchievementById(id: Int): Flow<Achievement> {
        return achievementDao.getAchievementById(id)
    }
    
    suspend fun unlockAchievement(achievementId: Int, timestamp: Long) {
        achievementDao.unlockAchievement(achievementId, timestamp)
    }
}