package com.bhashasetu.app.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.bhashasetu.app.database.AppDatabase
import com.bhashasetu.app.database.AchievementDao as LegacyAchievementDao
import com.bhashasetu.app.database.ModernAchievementDao
import com.bhashasetu.app.model.Achievement as LegacyAchievement
import com.bhashasetu.app.data.model.Achievement as ModernAchievement
import kotlinx.coroutines.flow.Flow
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

/**
 * Repository for managing both Legacy and Modern Achievement systems.
 *
 * DUAL ACHIEVEMENT SYSTEM:
 * - Legacy System: Uses legacy_achievements table for backward compatibility
 * - Modern System: Uses achievements table for new bilingual features
 *
 * USAGE GUIDELINES:
 * - Use Legacy methods for existing achievement functionality
 * - Use Modern methods for new features (bilingual, categories, analytics)
 * - Use Unified methods when you need to work with both systems together
 */
@Singleton
class AchievementRepository {

    private val legacyDao: LegacyAchievementDao
    private val modernDao: ModernAchievementDao

    // ===============================
    // CONSTRUCTORS
    // ===============================

    /**
     * Primary constructor - takes Application context
     */
    constructor(application: Application) {
        val database = AppDatabase.getInstance(application)
        legacyDao = database.achievementDao()
        modernDao = database.modernAchievementDao()
    }

    /**
     * Alternative constructor - takes both DAOs directly (for testing)
     */
    constructor(legacyDao: LegacyAchievementDao, modernDao: ModernAchievementDao) {
        this.legacyDao = legacyDao
        this.modernDao = modernDao
    }

    // ===============================
    // LEGACY ACHIEVEMENT SYSTEM (Backward Compatibility)
    // ===============================

    /**
     * Get all legacy achievements
     */
    val allLegacyAchievements: LiveData<List<LegacyAchievement>>
        get() = legacyDao.getAllAchievements()

    /**
     * Get unlocked legacy achievements
     */
    val unlockedLegacyAchievements: LiveData<List<LegacyAchievement>>
        get() = legacyDao.getUnlockedAchievements()

    /**
     * Get locked legacy achievements
     */
    val lockedLegacyAchievements: LiveData<List<LegacyAchievement>>
        get() = legacyDao.getAchievementsByUnlockStatus(false)

    /**
     * Get count of unlocked legacy achievements
     */
    val unlockedLegacyAchievementCount: LiveData<Int>
        get() = legacyDao.getUnlockedCount()

    /**
     * Get total points from legacy achievements
     */
    val totalLegacyPoints: LiveData<Int>
        get() = legacyDao.getTotalPoints()

    // Legacy Achievement Operations
    suspend fun insertLegacyAchievement(achievement: LegacyAchievement) {
        legacyDao.insert(achievement)
    }

    suspend fun updateLegacyAchievement(achievement: LegacyAchievement) {
        legacyDao.update(achievement)
    }

    suspend fun deleteLegacyAchievement(achievement: LegacyAchievement) {
        legacyDao.delete(achievement)
    }

    fun getLegacyAchievementById(id: Long): LiveData<LegacyAchievement> {
        return legacyDao.getAchievementById(id)
    }

    suspend fun unlockLegacyAchievement(achievementId: Long, timestamp: Long = System.currentTimeMillis()) {
        legacyDao.unlockAchievement(achievementId, timestamp)
    }

    suspend fun updateLegacyAchievementProgress(achievementId: Long, progress: Int) {
        legacyDao.updateProgress(achievementId, progress)
    }

    fun getLegacyAchievementsByType(type: String): LiveData<List<LegacyAchievement>> {
        return legacyDao.getAchievementsByType(type)
    }

    // ===============================
    // MODERN ACHIEVEMENT SYSTEM (New Features)
    // ===============================

    /**
     * Get all modern achievements
     */
    val allModernAchievements: LiveData<List<ModernAchievement>>
        get() = modernDao.getAllAchievements()

    /**
     * Get unlocked modern achievements
     */
    val unlockedModernAchievements: LiveData<List<ModernAchievement>>
        get() = modernDao.getUnlockedAchievements()

    /**
     * Get locked visible modern achievements
     */
    val lockedModernAchievements: LiveData<List<ModernAchievement>>
        get() = modernDao.getLockedVisibleAchievements()

    /**
     * Get all visible modern achievements (not hidden)
     */
    val visibleModernAchievements: LiveData<List<ModernAchievement>>
        get() = modernDao.getVisibleAchievements()

    /**
     * Get count of unlocked modern achievements
     */
    val unlockedModernAchievementCount: LiveData<Int>
        get() = modernDao.getUnlockedCount()

    /**
     * Get total points from modern achievements
     */
    val totalModernPoints: LiveData<Int>
        get() = modernDao.getTotalPointsFromAchievements()

    /**
     * Get completion percentage for modern achievements
     */
    val modernCompletionPercentage: LiveData<Double>
        get() = modernDao.getCompletionPercentage()

    /**
     * Get all categories from modern achievements
     */
    val allModernCategories: LiveData<List<String>>
        get() = modernDao.getAllCategories()

    // Modern Achievement Operations
    suspend fun insertModernAchievement(achievement: ModernAchievement) {
        modernDao.insert(achievement)
    }

    suspend fun updateModernAchievement(achievement: ModernAchievement) {
        modernDao.update(achievement)
    }

    suspend fun deleteModernAchievement(achievement: ModernAchievement) {
        modernDao.delete(achievement)
    }

    fun getModernAchievementById(id: String): LiveData<ModernAchievement> {
        return modernDao.getAchievementById(id)
    }

    suspend fun unlockModernAchievement(achievementId: String, timestamp: Long = System.currentTimeMillis()) {
        modernDao.unlockAchievement(achievementId, timestamp)
    }

    suspend fun incrementModernAchievementProgress(achievementId: String, increment: Int) {
        modernDao.incrementProgress(achievementId, increment)
    }

    suspend fun setModernAchievementProgress(achievementId: String, progress: Int) {
        modernDao.setProgress(achievementId, progress)
    }

    fun getModernAchievementsByCategory(category: String): LiveData<List<ModernAchievement>> {
        return modernDao.getAchievementsByCategory(category)
    }

    fun getModernAchievementsByType(type: String): LiveData<List<ModernAchievement>> {
        return modernDao.getAchievementsByType(type)
    }

    fun searchModernAchievements(query: String): LiveData<List<ModernAchievement>> {
        return modernDao.searchAchievements(query)
    }

    fun getModernAchievementsNearCompletion(percentage: Double = 80.0): LiveData<List<ModernAchievement>> {
        return modernDao.getAchievementsNearCompletion(percentage)
    }

    suspend fun getModernAchievementsReadyToUnlock(): List<ModernAchievement> {
        return modernDao.getAchievementsReadyToUnlock()
    }

    // ===============================
    // UNIFIED METHODS (Work with Both Systems)
    // ===============================

    /**
     * Get total points from both achievement systems combined
     */
    fun getTotalCombinedPoints(): LiveData<Int> {
        return combine(
            totalLegacyPoints,
            totalModernPoints
        ) { legacy, modern ->
            (legacy ?: 0) + (modern ?: 0)
        }
    }

    /**
     * Get total unlocked achievements from both systems
     */
    fun getTotalUnlockedCount(): LiveData<Int> {
        return combine(
            unlockedLegacyAchievementCount,
            unlockedModernAchievementCount
        ) { legacy, modern ->
            (legacy ?: 0) + (modern ?: 0)
        }
    }

    /**
     * Check if user has any achievements unlocked (either system)
     */
    fun hasAnyAchievements(): LiveData<Boolean> {
        return getTotalUnlockedCount().map { count -> count > 0 }
    }

    /**
     * Get recent achievements from both systems (combined)
     */
    fun getRecentAchievements(limit: Int = 5): LiveData<RecentAchievementsResult> {
        return combine(
            legacyDao.getRecentlyUnlocked(System.currentTimeMillis() - 86400000L, limit), // Last 24 hours
            modernDao.getRecentUnlockedWithMessages(limit)
        ) { legacy, modern ->
            RecentAchievementsResult(
                legacyAchievements = legacy,
                modernAchievements = modern
            )
        }
    }

    // ===============================
    // MAINTENANCE AND UTILITY METHODS
    // ===============================

    /**
     * Clear all achievement data (both systems) - for testing only
     */
    suspend fun clearAllAchievements() {
        legacyDao.deleteAll()
        modernDao.deleteAll()
    }

    /**
     * Reset all achievement progress (both systems) - for testing only
     */
    suspend fun resetAllProgress() {
        modernDao.resetAllProgress()
        // Legacy system doesn't have reset method, would need to update each individually
    }

    // ===============================
    // BACKWARD COMPATIBILITY METHODS
    // ===============================

    /**
     * Maintain backward compatibility with your existing code
     * These methods work with the modern system but keep the same interface
     */

    @Deprecated("Use allModernAchievements for new code", ReplaceWith("allModernAchievements"))
    val allAchievements: LiveData<List<ModernAchievement>>
        get() = allModernAchievements

    @Deprecated("Use unlockedModernAchievements for new code", ReplaceWith("unlockedModernAchievements"))
    val unlockedAchievements: LiveData<List<ModernAchievement>>
        get() = unlockedModernAchievements

    @Deprecated("Use lockedModernAchievements for new code", ReplaceWith("lockedModernAchievements"))
    val lockedAchievements: LiveData<List<ModernAchievement>>
        get() = lockedModernAchievements

    @Deprecated("Use unlockedModernAchievementCount for new code", ReplaceWith("unlockedModernAchievementCount"))
    val unlockedAchievementCount: LiveData<Int>
        get() = unlockedModernAchievementCount

    @Deprecated("Use insertModernAchievement for new code", ReplaceWith("insertModernAchievement(achievement)"))
    suspend fun insert(achievement: ModernAchievement) {
        insertModernAchievement(achievement)
    }

    @Deprecated("Use updateModernAchievement for new code", ReplaceWith("updateModernAchievement(achievement)"))
    suspend fun update(achievement: ModernAchievement) {
        updateModernAchievement(achievement)
    }

    @Deprecated("Use deleteModernAchievement for new code", ReplaceWith("deleteModernAchievement(achievement)"))
    suspend fun delete(achievement: ModernAchievement) {
        deleteModernAchievement(achievement)
    }

    // Note: This method signature is different due to ID type change (Int vs String)
    // You'll need to update calling code to use String IDs for modern achievements
    fun getAchievementById(id: String): LiveData<ModernAchievement> {
        return getModernAchievementById(id)
    }

    // Note: This method signature is different due to ID type change
    // You'll need to update calling code to use String IDs for modern achievements
    suspend fun unlockAchievement(achievementId: String, timestamp: Long = System.currentTimeMillis()) {
        unlockModernAchievement(achievementId, timestamp)
    }

    // ===============================
    // DATA CLASSES
    // ===============================

    /**
     * Result class for combined recent achievements
     */
    data class RecentAchievementsResult(
        val legacyAchievements: List<LegacyAchievement>,
        val modernAchievements: List<ModernAchievement>
    )
}