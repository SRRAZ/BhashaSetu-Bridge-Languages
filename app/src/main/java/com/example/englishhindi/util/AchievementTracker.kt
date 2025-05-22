package com.example.englishhindi.util

import android.util.Log
import com.example.englishhindi.data.model.Achievement
import com.example.englishhindi.data.repository.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Tracks user progress and unlocks achievements when criteria are met
 */
@Singleton
class AchievementTracker @Inject constructor(
    private val achievementRepository: AchievementRepository,
    private val wordRepository: WordRepository,
    private val userProgressRepository: UserProgressRepository,
    private val lessonRepository: LessonRepository,
    private val quizRepository: QuizRepository,
    private val dailyStreakRepository: DailyStreakRepository
) {
    
    /**
     * Checks for and unlocks any achievements the user has earned
     * Should be called after significant user actions (lesson completion, quiz completion, etc.)
     * 
     * @param scope The coroutine scope to use for database operations
     * @return List of newly unlocked achievements (if any)
     */
    suspend fun checkForUnlockedAchievements(scope: CoroutineScope): List<Achievement> {
        return withContext(Dispatchers.IO) {
            val unlockedAchievements = mutableListOf<Achievement>()
            
            // Get all locked achievements
            val lockedAchievements = achievementRepository.lockedAchievements.first()
            
            for (achievement in lockedAchievements) {
                val isUnlocked = when (achievement.achievementType) {
                    "lessons_completed" -> checkLessonsCompletedAchievement(achievement)
                    "words_learned" -> checkWordsLearnedAchievement(achievement)
                    "quiz_score" -> checkQuizScoreAchievement(achievement)
                    "streak_days" -> checkStreakDaysAchievement(achievement)
                    "study_time" -> checkStudyTimeAchievement(achievement)
                    "average_quiz_score" -> checkAverageQuizScoreAchievement(achievement)
                    else -> false
                }
                
                if (isUnlocked) {
                    // Unlock the achievement
                    achievementRepository.unlockAchievement(achievement.id, Date().time)
                    unlockedAchievements.add(achievement)
                    
                    Log.d(TAG, "Achievement unlocked: ${achievement.title}")
                } else {
                    // Update achievement progress
                    updateAchievementProgress(achievement, scope)
                }
            }
            
            unlockedAchievements
        }
    }
    
    /**
     * Checks if a lessons_completed achievement should be unlocked
     */
    private suspend fun checkLessonsCompletedAchievement(achievement: Achievement): Boolean {
        val completedLessonCount = lessonRepository.getCompletedLessonCount().first()
        return completedLessonCount >= achievement.requiredValue
    }
    
    /**
     * Checks if a words_learned achievement should be unlocked
     */
    private suspend fun checkWordsLearnedAchievement(achievement: Achievement): Boolean {
        // Consider a word "learned" if proficiency level is at least 6
        val learnedWordsCount = userProgressRepository.getLearnedWordsCount(6).first()
        return learnedWordsCount >= achievement.requiredValue
    }
    
    /**
     * Checks if a quiz_score achievement should be unlocked
     */
    private suspend fun checkQuizScoreAchievement(achievement: Achievement): Boolean {
        // For perfect quiz scores (100%), check if any quiz has that score
        if (achievement.requiredValue == 100) {
            val quizzes = quizRepository.allQuizzes.first()
            return quizzes.any { it.bestScore == 100 }
        } 
        
        // For other quiz scores, check if at least one quiz has been completed
        return quizRepository.getCompletedQuizCount().first() > 0
    }
    
    /**
     * Checks if a streak_days achievement should be unlocked
     */
    private suspend fun checkStreakDaysAchievement(achievement: Achievement): Boolean {
        val dailyStreak = dailyStreakRepository.dailyStreak.first()
        return dailyStreak != null && dailyStreak.currentStreak >= achievement.requiredValue
    }
    
    /**
     * Checks if a study_time achievement should be unlocked
     */
    private suspend fun checkStudyTimeAchievement(achievement: Achievement): Boolean {
        // Not implemented in current version - placeholder
        return false
    }
    
    /**
     * Checks if an average_quiz_score achievement should be unlocked
     */
    private suspend fun checkAverageQuizScoreAchievement(achievement: Achievement): Boolean {
        val averageScore = quizRepository.getAverageQuizScore().first()
        val completedQuizCount = quizRepository.getCompletedQuizCount().first()
        
        // Require at least 5 completed quizzes for average score achievements
        return completedQuizCount >= 5 && averageScore >= achievement.requiredValue
    }
    
    /**
     * Updates the progress value for an achievement
     */
    private suspend fun updateAchievementProgress(achievement: Achievement, scope: CoroutineScope) {
        val currentProgress = when (achievement.achievementType) {
            "lessons_completed" -> lessonRepository.getCompletedLessonCount().first()
            "words_learned" -> userProgressRepository.getLearnedWordsCount(6).first()
            "streak_days" -> {
                val dailyStreak = dailyStreakRepository.dailyStreak.first()
                dailyStreak?.currentStreak ?: 0
            }
            "quiz_score" -> {
                // For quiz score achievements, progress is the highest quiz score
                val quizzes = quizRepository.allQuizzes.first()
                quizzes.maxOfOrNull { it.bestScore } ?: 0
            }
            "average_quiz_score" -> {
                val averageScore = quizRepository.getAverageQuizScore().first()
                averageScore.toInt()
            }
            else -> 0
        }
        
        // If progress has changed, update the achievement record
        if (achievement.progress != currentProgress) {
            scope.launch(Dispatchers.IO) {
                achievementRepository.update(achievement.copy(progress = currentProgress))
            }
        }
    }
    
    companion object {
        private const val TAG = "AchievementTracker"
    }
}