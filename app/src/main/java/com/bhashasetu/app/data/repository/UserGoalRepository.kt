package com.bhashasetu.app.data.repository

import androidx.lifecycle.LiveData
import com.bhashasetu.app.data.dao.UserGoalDao
import com.bhashasetu.app.data.model.UserGoal
import kotlinx.coroutines.flow.Flow
import java.util.Date

class UserGoalRepository(private val userGoalDao: UserGoalDao) {

    val allUserGoals: Flow<List<UserGoal>> = userGoalDao.getAllUserGoals()
    val activeUserGoals: Flow<List<UserGoal>> = userGoalDao.getActiveUserGoals()
    val completedUserGoals: Flow<List<UserGoal>> = userGoalDao.getCompletedUserGoals()
    
    suspend fun insert(userGoal: UserGoal): Long {
        return userGoalDao.insert(userGoal)
    }
    
    suspend fun update(userGoal: UserGoal) {
        userGoalDao.update(userGoal)
    }
    
    suspend fun delete(userGoal: UserGoal) {
        userGoalDao.delete(userGoal)
    }
    
    fun getUserGoalById(id: Long): Flow<UserGoal> {
        return userGoalDao.getUserGoalById(id)
    }
    
    suspend fun markGoalAsCompleted(goalId: Long, timestamp: Long) {
        userGoalDao.markGoalAsCompleted(goalId, timestamp)
    }
    
    suspend fun updateGoalProgress(goalId: Long, progress: Int) {
        userGoalDao.updateGoalProgress(goalId, progress)
    }
    
    fun getActiveGoalByType(goalType: String): Flow<UserGoal?> {
        return userGoalDao.getActiveGoalByType(goalType)
    }
}