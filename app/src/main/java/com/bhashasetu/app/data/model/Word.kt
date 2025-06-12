package com.bhashasetu.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity representing a vocabulary word with translations in both English and Hindi.
 */
@Entity(tableName = "words")
data class Word(
    @PrimaryKey(autoGenerate = true) 
    val id: Long = 0,
    
    // Core word properties
    val englishText: String,
    val hindiText: String,
    
    // Pronunciation guides
    val englishPronunciation: String? = null,
    val hindiPronunciation: String? = null,
    
    // Example sentences
    val englishExample: String? = null,
    val hindiExample: String? = null,
    
    // Word metadata
    val difficulty: Int = 1, // 1=Beginner, 2=Intermediate, 3=Advanced
    val partOfSpeech: String? = null, // noun, verb, adjective, etc.
    val isFavorite: Boolean = false,
    
    // Audio file paths (if available)
    val englishAudioPath: String? = null,
    val hindiAudioPath: String? = null,
    
    // Timestamps
    val createdAt: Long = System.currentTimeMillis(),
    val lastModifiedAt: Long = System.currentTimeMillis(),
    val lastReviewedAt: Long? = null,
    val nextReviewDate: Long? = null
)