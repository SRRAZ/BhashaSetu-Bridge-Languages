package com.example.englishhindi.util

import com.example.englishhindi.data.model.UserProgress
import com.example.englishhindi.data.repository.UserProgressRepository
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manages spaced repetition algorithm for optimizing word memorization
 * Uses a modified version of the SuperMemo SM-2 algorithm
 */
@Singleton
class SpacedRepetitionManager @Inject constructor(
    private val userProgressRepository: UserProgressRepository
) {
    
    /**
     * Updates a word's spaced repetition data based on user performance
     * 
     * @param wordId The ID of the word
     * @param performance The user's performance score (0-5 scale)
     *                   0-2: Incorrect/difficult recall
     *                   3-5: Correct recall with varying degrees of difficulty
     */
    suspend fun processWordReview(wordId: Long, performance: Int) {
        // Get current progress for the word
        val currentProgress = userProgressRepository.getUserProgressByWordId(wordId).value ?: return
        
        // Calculate new spaced repetition parameters
        val (newEfFactor, newInterval, newMemorizationStatus) = calculateSpacedRepetitionParams(
            currentProgress,
            performance
        )
        
        // Calculate next review date
        val now = Date()
        val nextReviewDate = Calendar.getInstance().apply {
            time = now
            add(Calendar.DAY_OF_YEAR, newInterval)
        }.time
        
        // Update proficiency level
        val newProficiencyLevel = calculateNewProficiencyLevel(
            currentProgress.proficiencyLevel,
            performance,
            currentProgress.exposureCount + 1
        )
        
        // Update correct/incorrect answers count
        if (performance >= 3) {
            userProgressRepository.incrementCorrectAnswers(wordId)
        } else {
            userProgressRepository.incrementIncorrectAnswers(wordId)
        }
        
        // Update the progress record
        userProgressRepository.updateLastReviewed(wordId, now)
        userProgressRepository.updateNextReviewDate(wordId, nextReviewDate)
        userProgressRepository.incrementExposureCount(wordId)
        userProgressRepository.updateMemorizationStatus(wordId, newMemorizationStatus)
        userProgressRepository.updateProficiencyLevel(wordId, newProficiencyLevel)
        
        // Update EF factor in the database
        // Note: Since there's no direct method for updating EF factor in the DAO/Repository,
        // we would either need to add this method or update the entire UserProgress object
        // For now, we'll use a workaround by fetching and updating the full object
        val updatedProgress = userProgressRepository.getUserProgressByWordId(wordId).value
        updatedProgress?.let {
            userProgressRepository.update(it.copy(efFactor = newEfFactor))
        }
    }
    
    /**
     * Calculates spaced repetition parameters using a modified SM-2 algorithm
     * 
     * @param currentProgress The current user progress for the word
     * @param performance The user's performance score (0-5 scale)
     * @return Triple of (new EF factor, new interval in days, new memorization status)
     */
    private fun calculateSpacedRepetitionParams(
        currentProgress: UserProgress,
        performance: Int
    ): Triple<Float, Int, Int> {
        // Get current values
        var efFactor = currentProgress.efFactor
        var interval = calculateCurrentInterval(currentProgress)
        val memorizationStatus = currentProgress.memorizationStatus
        
        // Adjust EF factor based on performance
        // SM-2 formula: EF' = EF + (0.1 - (5 - q) * (0.08 + (5 - q) * 0.02))
        // where q is the performance (0-5)
        if (performance >= 3) {
            // Only update EF if the answer was correct (performance >= 3)
            efFactor += (0.1 - (5 - performance) * (0.08 + (5 - performance) * 0.02)).toFloat()
            // Ensure EF doesn't go below 1.3 (SM-2 minimum value)
            efFactor = efFactor.coerceAtLeast(1.3f)
        }
        
        // Calculate next interval
        val newInterval = if (performance < 3) {
            // If performance was poor, reset the interval to 1 day (re-learn)
            1
        } else {
            // For correct responses, calculate the next interval based on the SM-2 algorithm
            when (memorizationStatus) {
                0, 1 -> 1 // First time correct or learning phase: 1 day
                2 -> 6 // Second time correct: 6 days
                else -> (interval * efFactor).toInt() // Third time onwards: I(n+1) = I(n) * EF
            }
        }
        
        // Determine new memorization status
        val newMemorizationStatus = when {
            performance < 3 -> maxOf(1, memorizationStatus - 1) // Regress on poor performance, but not below 1
            memorizationStatus >= 3 && performance == 5 -> 3 // Stay at mastered if already mastered and perfect recall
            performance >= 3 -> minOf(3, memorizationStatus + 1) // Progress on good performance, but not above 3
            else -> memorizationStatus // Maintain current status
        }
        
        return Triple(efFactor, newInterval, newMemorizationStatus)
    }
    
    /**
     * Calculates the current interval for a word based on its last review and next review dates
     * 
     * @param progress The user progress for the word
     * @return The current interval in days
     */
    private fun calculateCurrentInterval(progress: UserProgress): Int {
        val lastReviewed = progress.lastReviewed
        val nextReviewDate = progress.nextReviewDate
        
        return if (lastReviewed != null && nextReviewDate != null) {
            val diffInMillis = nextReviewDate.time - lastReviewed.time
            TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS).toInt()
        } else {
            // Default interval for new words
            1
        }
    }
    
    /**
     * Calculates a new proficiency level based on performance and exposure count
     * 
     * @param currentProficiency The current proficiency level (0-10 scale)
     * @param performance The user's performance score (0-5 scale)
     * @param exposureCount The number of times the word has been shown
     * @return New proficiency level (0-10 scale)
     */
    private fun calculateNewProficiencyLevel(
        currentProficiency: Int,
        performance: Int,
        exposureCount: Int
    ): Int {
        // Map performance (0-5) to proficiency change
        val proficiencyChange = when (performance) {
            0 -> -2 // Very poor recall
            1 -> -1 // Poor recall
            2 -> 0 // Difficult recall
            3 -> 1 // Correct recall with difficulty
            4 -> 2 // Good recall
            5 -> 3 // Perfect recall
            else -> 0
        }
        
        // Adjust the impact based on exposure count (diminishing returns)
        val exposureFactor = when {
            exposureCount <= 3 -> 1.0f // Full impact for first few exposures
            exposureCount <= 10 -> 0.7f // Reduced impact
            exposureCount <= 20 -> 0.5f // Further reduced impact
            else -> 0.3f // Minimal impact for many exposures
        }
        
        // Calculate new proficiency level with adjustment
        val adjustedChange = (proficiencyChange * exposureFactor).toInt()
        
        // Ensure proficiency stays within 0-10 range
        return (currentProficiency + adjustedChange).coerceIn(0, 10)
    }
    
    /**
     * Gets a list of words due for review today
     * 
     * @return List of word IDs that are due for review
     */
    suspend fun getDueForReviewWords(): List<Long> {
        val now = Date()
        return userProgressRepository.getWordsForReview(now).value?.map { it.wordId } ?: emptyList()
    }
}