package com.bhashasetu.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity representing a category for grouping vocabulary words.
 */
@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true) 
    val id: Long = 0,
    
    // Bilingual category names
    val nameEnglish: String,
    val nameHindi: String,
    
    // Optional category description
    val descriptionEnglish: String? = null,
    val descriptionHindi: String? = null,
    
    // Visual properties
    val iconResId: Int? = null,
    val colorHex: String? = null,
    
    // Metadata
    val orderIndex: Int = 0, // For custom ordering
    val isDefault: Boolean = false, // Whether it's a system-provided category
    val createdAt: Long = System.currentTimeMillis()
)