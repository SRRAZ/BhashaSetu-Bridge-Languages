package com.example.englishhindi.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity representing the user's daily streak for continuous learning.
 */
@Entity(tableName = "daily_streak")
data class DailyStreak(
    @PrimaryKey
    val id: Int = 1, // Singleton instance ID
    
    // Streak metrics
    val currentStreak: Int = 0, // Current consecutive days
    val longestStreak: Int = 0, // Longest streak achieved
    val totalDaysActive: Int = 0, // Total active days (not necessarily consecutive)
    
    // Today's status
    val lastActiveDate: Long? = null, // Last date user was active
    val isTodayCompleted: Boolean = false, // Whether today's goal is completed
    
    // Maintenance
    val streakFreezeAvailable: Int = 0, // Number of available streak freezes
    val streakFreezeUsed: Int = 0, // Number of streak freezes used
    val lastStreakFreezeDate: Long? = null, // Last date streak freeze was used
    
    // Milestone tracking
    val nextMilestone: Int = 7, // Next streak milestone to unlock achievement
    val milestoneReached: Int = 0, // Highest milestone achieved
    
    // History tracking
    val weeklyActivity: String = "", // Binary string representing last 7 days (1=active)
    val monthlyActivity: String = "" // Binary string representing last 30 days (1=active)
)