package com.bhashasetu.app.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bhashasetu.app.data.model.Achievement
import kotlinx.coroutines.flow.Flow

@Dao
interface AchievementDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(achievement: Achievement)

    @Update
    suspend fun update(achievement: Achievement)

    @Delete
    suspend fun delete(achievement: Achievement)

    @Query("SELECT * FROM achievements")
    fun getAllAchievements(): Flow<List<Achievement>>

    @Query("SELECT * FROM achievements WHERE id = :id")
    fun getAchievementById(id: Int): Flow<Achievement>

    @Query("SELECT * FROM achievements WHERE unlocked = 1")
    fun getUnlockedAchievements(): Flow<List<Achievement>>

    @Query("SELECT * FROM achievements WHERE unlocked = 0")
    fun getLockedAchievements(): Flow<List<Achievement>>

    @Query("UPDATE achievements SET unlocked = 1, unlockedDate = :timestamp WHERE id = :achievementId")
    suspend fun unlockAchievement(achievementId: Int, timestamp: Long)

    @Query("SELECT COUNT(*) FROM achievements WHERE unlocked = 1")
    fun getUnlockedAchievementCount(): Flow<Int>
}