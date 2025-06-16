package com.bhashasetu.app.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bhashasetu.app.data.model.DailyStreak
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface DailyStreakDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dailyStreak: DailyStreak)

    @Update
    suspend fun update(dailyStreak: DailyStreak)

    @Query("SELECT * FROM daily_streaks WHERE id = 1")
    fun getDailyStreak(): Flow<DailyStreak?>

    @Query("UPDATE daily_streaks SET currentStreak = :newStreak, lastActiveDate = :checkInDate WHERE id = 1")
    suspend fun updateStreak(newStreak: Int, checkInDate: Date)

    @Query("UPDATE daily_streaks SET longestStreak = :longestStreak WHERE id = 1 AND longestStreak < :longestStreak")
    suspend fun updateLongestStreak(longestStreak: Int)

    @Query("UPDATE daily_streaks SET currentStreak = 1, lastActiveDate = :checkInDate WHERE id = 1")
    suspend fun resetStreak(checkInDate: Date)

    @Query("SELECT EXISTS(SELECT 1 FROM daily_streaks WHERE id = 1 AND date(lastActiveDate/1000, 'unixepoch') = date('now'))")
    fun hasCheckedInToday(): Flow<Boolean>
}