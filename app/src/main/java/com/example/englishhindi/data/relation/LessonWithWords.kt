package com.bhashasetu.app.data.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.bhashasetu.app.data.model.Lesson
import com.bhashasetu.app.data.model.LessonWord
import com.bhashasetu.app.data.model.Word

/**
 * Represents a Lesson with all its associated Words.
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
    val words: List<Word>,
    
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
        return words.sortedBy { orderMap[it.id] }
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
}