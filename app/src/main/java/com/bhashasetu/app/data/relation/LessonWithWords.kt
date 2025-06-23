package com.bhashasetu.app.data.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.bhashasetu.app.model.Lesson  // ✅ Updated import
import com.bhashasetu.app.model.Word   // ✅ Using the fixed Word entity
import com.bhashasetu.app.data.model.LessonWord

/**
 * ✅ FIXED: Represents a Lesson with all its associated Words.
 * Updated to use the correct Word entity that resolves Room build errors.
 * This is a Room relationship helper class for retrieving a lesson with all its vocabulary.
 */
data class LessonWithWords(
    @Embedded val lesson: Lesson,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = LessonWord::class,
            parentColumn = "lessonId",
            entityColumn = "wordId"
        )
    )
    val words: List<Word>, // ✅ Now using the fixed Word entity

    @Relation(
        entity = LessonWord::class,
        parentColumn = "id",
        entityColumn = "lessonId"
    )
    val lessonWords: List<LessonWord>
) {
    /**
     * Returns the words in the order specified in the lesson.
     */
    fun getOrderedWords(): List<Word> {
        // Create a map of wordId to the word
        val wordMap = words.associateBy { it.id }

        // Create a map of wordId to its order in the lesson
        val orderMap = lessonWords.associate { it.wordId to it.orderInLesson }

        // Return words sorted by their order in the lesson
        return words.sortedBy { orderMap[it.id] ?: 0 } // ✅ Added null safety
    }

    /**
     * Returns only the keywords of the lesson.
     */
    fun getKeywords(): List<Word> {
        // Get IDs of keywords
        val keywordIds = lessonWords
            .filter { it.isKeyword }
            .map { it.wordId }
        // Return words that are keywords
        return words.filter { it.id in keywordIds }
    }

    /**
     * Returns the words to be included in the quiz.
     */
    fun getQuizWords(): List<Word> {
        // Get IDs of quiz words
        val quizWordIds = lessonWords
            .filter { it.includeInQuiz }
            .map { it.wordId }
        // Return words to be included in quiz
        return words.filter { it.id in quizWordIds }
    }

    /**
     * ✅ NEW: Returns words filtered by difficulty level.
     */
    fun getWordsByDifficulty(difficulty: Int): List<Word> {
        return words.filter { it.difficulty == difficulty }
    }

    /**
     * ✅ NEW: Returns favorite words in this lesson.
     */
    fun getFavoriteWords(): List<Word> {
        return words.filter { it.isFavorite }
    }

    /**
     * ✅ NEW: Returns words that need practice (low mastery level).
     */
    fun getWordsNeedingPractice(maxMasteryLevel: Int = 2): List<Word> {
        return words.filter { it.masteryLevel <= maxMasteryLevel }
    }

    /**
     * ✅ NEW: Returns words with audio available.
     */
    fun getWordsWithAudio(): List<Word> {
        return words.filter { it.hasEnglishAudio && it.hasHindiAudio }
    }

    /**
     * ✅ NEW: Returns words with images available.
     */
    fun getWordsWithImages(): List<Word> {
        return words.filter { it.hasImage() }
    }

    /**
     * ✅ NEW: Returns statistics about this lesson.
     */
    fun getLessonStats(): LessonStats {
        return LessonStats(
            totalWords = words.size,
            keywordCount = getKeywords().size,
            quizWordCount = getQuizWords().size,
            averageMastery = words.map { it.masteryLevel }.average().takeIf { words.isNotEmpty() } ?: 0.0,
            wordsWithAudio = getWordsWithAudio().size,
            wordsWithImages = getWordsWithImages().size,
            favoriteWords = getFavoriteWords().size
        )
    }
}

/**
 * ✅ NEW: Data class for lesson statistics.
 */
data class LessonStats(
    val totalWords: Int,
    val keywordCount: Int,
    val quizWordCount: Int,
    val averageMastery: Double,
    val wordsWithAudio: Int,
    val wordsWithImages: Int,
    val favoriteWords: Int
)