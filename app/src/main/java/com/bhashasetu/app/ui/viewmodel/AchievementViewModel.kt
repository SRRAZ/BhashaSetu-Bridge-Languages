package com.bhashasetu.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhashasetu.app.data.model.Achievement
import com.bhashasetu.app.data.repository.AchievementRepository
import com.bhashasetu.app.data.repository.UserProgressRepository
import com.bhashasetu.app.ui.screens.UserStats
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AchievementViewModel @Inject constructor(
    private val achievementRepository: AchievementRepository,
    private val userProgressRepository: UserProgressRepository
) : ViewModel() {

    // Achievement data flows
    val achievements = achievementRepository.getAllAchievements()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = generateSampleAchievements()
        )

    val unlockedAchievements = achievements
        .map { list -> list.filter { it.isUnlocked } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val userStats = combine(
        userProgressRepository.getUserLevel(),
        userProgressRepository.getTotalXP(),
        userProgressRepository.getCurrentStreak(),
        userProgressRepository.getQuizzesCompleted(),
        userProgressRepository.getWordsLearned(),
        unlockedAchievements
    ) { level, totalXP, streak, quizzes, words, achievements ->
        UserStats(
            level = level,
            totalXP = totalXP,
            levelProgress = calculateLevelProgress(level, totalXP),
            achievementsUnlocked = achievements.size,
            currentStreak = streak,
            quizzesCompleted = quizzes,
            wordsLearned = words
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UserStats()
    )

    // Achievement categories
    val achievementsByCategory = achievements
        .map { list ->
            list.groupBy { it.category }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyMap()
        )

    /**
     * Unlocks an achievement if conditions are met
     * @param achievementId The ID of the achievement to unlock
     */
    fun unlockAchievement(achievementId: String) {
        viewModelScope.launch {
            try {
                achievementRepository.unlockAchievement(achievementId)
                // TODO: Show achievement unlock notification
                // TODO: Play achievement sound effect
                // TODO: Update user XP for achievement unlock
            } catch (e: Exception) {
                // TODO: Handle error (show snackbar, log, etc.)
            }
        }
    }

    /**
     * Updates achievement progress
     * @param achievementId The ID of the achievement
     * @param progress New progress value
     */
    fun updateAchievementProgress(achievementId: String, progress: Int) {
        viewModelScope.launch {
            try {
                achievementRepository.updateAchievementProgress(achievementId, progress)

                // Check if achievement should be unlocked
                val achievement = achievements.value.find { it.id == achievementId }
                if (achievement != null && progress >= achievement.target && !achievement.isUnlocked) {
                    unlockAchievement(achievementId)
                }
            } catch (e: Exception) {
                // TODO: Handle error
            }
        }
    }

    /**
     * Checks and updates achievements based on user actions
     * @param action The action performed by the user
     * @param value Additional value for the action (optional)
     */
    fun checkAchievements(action: AchievementAction, value: Int = 1) {
        viewModelScope.launch {
            when (action) {
                AchievementAction.COMPLETED_FIRST_LESSON -> {
                    updateAchievementProgress("first_lesson", 1)
                }
                AchievementAction.MAINTAINED_STREAK -> {
                    updateAchievementProgress("streak_3", value)
                    updateAchievementProgress("streak_7", value)
                    updateAchievementProgress("streak_30", value)
                }
                AchievementAction.PERFECT_QUIZ -> {
                    updateAchievementProgress("perfect_quiz", 1)
                }
                AchievementAction.COMPLETED_QUIZ -> {
                    updateAchievementProgress("quiz_master", 1)
                }
                AchievementAction.LEARNED_WORDS -> {
                    updateAchievementProgress("word_collector", value)
                }
                AchievementAction.PRONUNCIATION_PRACTICE -> {
                    updateAchievementProgress("pronunciation_expert", 1)
                }
                AchievementAction.FAST_COMPLETION -> {
                    updateAchievementProgress("speed_learner", 1)
                }
                AchievementAction.NIGHT_STUDY -> {
                    updateAchievementProgress("night_owl", 1)
                }
                AchievementAction.MORNING_STUDY -> {
                    updateAchievementProgress("early_bird", 1)
                }
            }
        }
    }

    /**
     * Gets achievements filtered by category
     * @param category The category to filter by
     * @return Flow of achievements in the specified category
     */
    fun getAchievementsByCategory(category: String): Flow<List<Achievement>> {
        return achievements.map { list ->
            if (category == "All") list else list.filter { it.category == category }
        }
    }

    /**
     * Gets the user's achievement statistics
     * @return Flow of achievement statistics
     */
    fun getAchievementStats(): Flow<AchievementStats> {
        return achievements.map { list ->
            AchievementStats(
                total = list.size,
                unlocked = list.count { it.isUnlocked },
                common = list.count { it.rarity == "Common" && it.isUnlocked },
                rare = list.count { it.rarity == "Rare" && it.isUnlocked },
                epic = list.count { it.rarity == "Epic" && it.isUnlocked },
                legendary = list.count { it.rarity == "Legendary" && it.isUnlocked }
            )
        }
    }

    /**
     * Resets all achievement progress (for testing/development)
     */
    fun resetAchievements() {
        viewModelScope.launch {
            try {
                achievementRepository.resetAllAchievements()
            } catch (e: Exception) {
                // TODO: Handle error
            }
        }
    }

    private fun calculateLevelProgress(level: Int, totalXP: Int): Float {
        val xpForCurrentLevel = (level - 1) * 100 // Each level requires 100 more XP
        val xpForNextLevel = level * 100
        val currentLevelXP = totalXP - xpForCurrentLevel
        val xpNeededForNextLevel = xpForNextLevel - xpForCurrentLevel

        return if (xpNeededForNextLevel > 0) {
            (currentLevelXP.toFloat() / xpNeededForNextLevel).coerceIn(0f, 1f)
        } else {
            1f
        }
    }

    private fun generateSampleAchievements(): List<Achievement> {
        return listOf(
            // Learning Achievements
            Achievement(
                id = "first_lesson",
                title = "First Steps",
                description = "Complete your first lesson",
                category = "Learning",
                type = "first_lesson",
                target = 1,
                progress = 0,
                isUnlocked = false,
                rarity = "Common",
                xpReward = 50
            ),
            Achievement(
                id = "word_collector",
                title = "Word Collector",
                description = "Learn 100 new words",
                category = "Learning",
                type = "word_collector",
                target = 100,
                progress = 0,
                isUnlocked = false,
                rarity = "Rare",
                xpReward = 200
            ),

            // Streak Achievements
            Achievement(
                id = "streak_3",
                title = "Getting Started",
                description = "Maintain a 3-day learning streak",
                category = "Streak",
                type = "streak_3",
                target = 3,
                progress = 0,
                isUnlocked = false,
                rarity = "Common",
                xpReward = 75
            ),
            Achievement(
                id = "streak_7",
                title = "Week Warrior",
                description = "Maintain a 7-day learning streak",
                category = "Streak",
                type = "streak_7",
                target = 7,
                progress = 0,
                isUnlocked = false,
                rarity = "Rare",
                xpReward = 150
            ),
            Achievement(
                id = "streak_30",
                title = "Consistency Master",
                description = "Maintain a 30-day learning streak",
                category = "Streak",
                type = "streak_30",
                target = 30,
                progress = 0,
                isUnlocked = false,
                rarity = "Legendary",
                xpReward = 500
            ),

            // Quiz Achievements
            Achievement(
                id = "perfect_quiz",
                title = "Perfect Score",
                description = "Score 100% on any quiz",
                category = "Quiz",
                type = "perfect_quiz",
                target = 1,
                progress = 0,
                isUnlocked = false,
                rarity = "Rare",
                xpReward = 100
            ),
            Achievement(
                id = "quiz_master",
                title = "Quiz Master",
                description = "Complete 50 quizzes",
                category = "Quiz",
                type = "quiz_master",
                target = 50,
                progress = 0,
                isUnlocked = false,
                rarity = "Epic",
                xpReward = 300
            ),

            // Special Achievements
            Achievement(
                id = "pronunciation_expert",
                title = "Pronunciation Expert",
                description = "Practice pronunciation 25 times",
                category = "Special",
                type = "pronunciation_expert",
                target = 25,
                progress = 0,
                isUnlocked = false,
                rarity = "Epic",
                xpReward = 250
            ),
            Achievement(
                id = "speed_learner",
                title = "Speed Learner",
                description = "Complete a lesson in under 5 minutes",
                category = "Special",
                type = "speed_learner",
                target = 1,
                progress = 0,
                isUnlocked = false,
                rarity = "Rare",
                xpReward = 150
            ),
            Achievement(
                id = "night_owl",
                title = "Night Owl",
                description = "Study after 10 PM",
                category = "Special",
                type = "night_owl",
                target = 1,
                progress = 0,
                isUnlocked = false,
                rarity = "Common",
                xpReward = 50
            ),
            Achievement(
                id = "early_bird",
                title = "Early Bird",
                description = "Study before 7 AM",
                category = "Special",
                type = "early_bird",
                target = 1,
                progress = 0,
                isUnlocked = false,
                rarity = "Common",
                xpReward = 50
            )
        )
    }
}

// Enums and Data Classes
enum class AchievementAction {
    COMPLETED_FIRST_LESSON,
    MAINTAINED_STREAK,
    PERFECT_QUIZ,
    COMPLETED_QUIZ,
    LEARNED_WORDS,
    PRONUNCIATION_PRACTICE,
    FAST_COMPLETION,
    NIGHT_STUDY,
    MORNING_STUDY
}

data class AchievementStats(
    val total: Int = 0,
    val unlocked: Int = 0,
    val common: Int = 0,
    val rare: Int = 0,
    val epic: Int = 0,
    val legendary: Int = 0
) {
    val completionPercentage: Float
        get() = if (total > 0) unlocked.toFloat() / total else 0f
}