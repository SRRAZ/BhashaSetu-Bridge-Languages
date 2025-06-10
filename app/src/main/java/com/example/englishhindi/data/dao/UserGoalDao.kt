package com.bhashasetu.app.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bhashasetu.app.data.model.UserGoal
import kotlinx.coroutines.flow.Flow

@Dao
interface UserGoalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userGoal: UserGoal): Long

    @Update
    suspend fun update(userGoal: UserGoal)

    @Delete
    suspend fun delete(userGoal: UserGoal)

    @Query("SELECT * FROM user_goals ORDER BY targetDate ASC")
    fun getAllUserGoals(): Flow<List<UserGoal>>

    @Query("SELECT * FROM user_goals WHERE id = :id")
    fun getUserGoalById(id: Long): Flow<UserGoal>

    @Query("SELECT * FROM user_goals WHERE completed = 0 ORDER BY targetDate ASC")
    fun getActiveUserGoals(): Flow<List<UserGoal>>

    @Query("SELECT * FROM user_goals WHERE completed = 1 ORDER BY completedDate DESC")
    fun getCompletedUserGoals(): Flow<List<UserGoal>>

    @Query("UPDATE user_goals SET completed = 1, completedDate = :timestamp, progress = targetValue WHERE id = :goalId")
    suspend fun markGoalAsCompleted(goalId: Long, timestamp: Long)

    @Query("UPDATE user_goals SET progress = :progress WHERE id = :goalId")
    suspend fun updateGoalProgress(goalId: Long, progress: Int)

    @Query("SELECT * FROM user_goals WHERE goalType = :goalType AND completed = 0 LIMIT 1")
    fun getActiveGoalByType(goalType: String): Flow<UserGoal?>
}