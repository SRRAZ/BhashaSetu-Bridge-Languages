package com.bhashasetu.app.data.repository

import androidx.lifecycle.LiveData
import com.bhashasetu.app.database.AppDatabase
import com.bhashasetu.app.data.dao.AchievementDao as LegacyAchievementDao
import com.bhashasetu.app.database.ModernAchievementDao
import com.bhashasetu.app.model.Achievement as LegacyAchievement
import com.bhashasetu.app.data.model.Achievement as ModernAchievement
import com.bhashasetu.app.data.model.Achievement
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.flow

class AchievementRepository_BACKUP(private val achievementDao: AchievementDao) {

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
