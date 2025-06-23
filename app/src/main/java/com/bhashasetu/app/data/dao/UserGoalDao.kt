package com.bhashasetu.app.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bhashasetu.app.data.model.UserGoal
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for UserGoal entities.
 * ✅ ROOM ERROR FIX: Updated all column references to match UserGoal entity fields
 */
@Dao
interface UserGoalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userGoal: UserGoal): Long

    @Update
    suspend fun update(userGoal: UserGoal)

    @Delete
    suspend fun delete(userGoal: UserGoal)

    @Query("SELECT * FROM user_goals ORDER BY endDate ASC")
    fun getAllUserGoals(): Flow<List<UserGoal>>

    @Query("SELECT * FROM user_goals WHERE id = :id")
    fun getUserGoalById(id: Long): Flow<UserGoal>

    @Query("SELECT * FROM user_goals WHERE completed = 0 ORDER BY endDate ASC")
    fun getActiveUserGoals(): Flow<List<UserGoal>>

    @Query("SELECT * FROM user_goals WHERE completed = 1 ORDER BY completedAt DESC")
    fun getCompletedUserGoals(): Flow<List<UserGoal>>

    @Query("UPDATE user_goals SET completed = 1, completedAt = :timestamp, currentValue = targetValue WHERE id = :goalId")
    suspend fun markGoalAsCompleted(goalId: Long, timestamp: Long)

    @Query("UPDATE user_goals SET currentValue = :progress WHERE id = :goalId")
    suspend fun updateGoalProgress(goalId: Long, progress: Int)

    @Query("SELECT * FROM user_goals WHERE goalType = :goalType AND completed = 0 LIMIT 1")
    fun getActiveGoalByType(goalType: String): Flow<UserGoal?>

    // Additional useful queries
    @Query("SELECT * FROM user_goals WHERE periodType = :periodType AND isActive = 1")
    fun getGoalsByPeriod(periodType: String): Flow<List<UserGoal>>

    @Query("SELECT * FROM user_goals WHERE categoryId = :categoryId AND isActive = 1")
    fun getGoalsByCategory(categoryId: Long): Flow<List<UserGoal>>

    @Query("UPDATE user_goals SET isActive = 0 WHERE id = :goalId")
    suspend fun deactivateGoal(goalId: Long)

    @Query("SELECT COUNT(*) FROM user_goals WHERE completed = 1")
    fun getCompletedGoalCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM user_goals WHERE completed = 0 AND isActive = 1")
    fun getActiveGoalCount(): Flow<Int>
}