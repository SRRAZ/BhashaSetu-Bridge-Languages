package com.bhashasetu.app.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bhashasetu.app.data.model.Category
import com.bhashasetu.app.data.model.WordCategoryCrossRef
import com.bhashasetu.app.data.relation.CategoryWithWords
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the Category entity.
 */
@Dao
interface CategoryDao {
    // Basic CRUD operations
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: Category): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(categories: List<Category>): List<Long>
    
    @Update
    suspend fun update(category: Category)
    
    @Delete
    suspend fun delete(category: Category)
    
    // WordCategory junction operations
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWordToCategory(wordCategoryCrossRef: WordCategoryCrossRef)
    
    @Delete
    suspend fun removeWordFromCategory(wordCategoryCrossRef: WordCategoryCrossRef)
    
    @Query("DELETE FROM word_category_cross_refs WHERE wordId = :wordId AND categoryId = :categoryId")
    suspend fun removeWordFromCategoryById(wordId: Long, categoryId: Long)
    
    @Query("DELETE FROM word_category_cross_refs WHERE categoryId = :categoryId")
    suspend fun removeAllWordsFromCategory(categoryId: Long)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWordsToCategory(wordCategoryCrossRefs: List<WordCategoryCrossRef>)
    
    // Queries
    
    @Query("SELECT * FROM categories WHERE id = :categoryId")
    suspend fun getCategoryById(categoryId: Long): Category?
    
    @Query("SELECT * FROM categories WHERE id = :categoryId")
    fun getCategoryByIdLiveData(categoryId: Long): LiveData<Category?>
    
    @Query("SELECT * FROM categories WHERE id = :categoryId")
    fun getCategoryByIdFlow(categoryId: Long): Flow<Category?>
    
    @Query("SELECT * FROM categories ORDER BY orderIndex ASC, nameEnglish ASC")
    fun getAllCategories(): Flow<List<Category>>
    
    @Query("SELECT * FROM categories ORDER BY orderIndex ASC, nameEnglish ASC")
    fun getAllCategoriesLiveData(): LiveData<List<Category>>
    
    @Query("SELECT * FROM categories WHERE isDefault = 1 ORDER BY orderIndex ASC, nameEnglish ASC")
    fun getDefaultCategories(): Flow<List<Category>>
    
    @Query("SELECT * FROM categories WHERE nameEnglish LIKE '%' || :query || '%' OR nameHindi LIKE '%' || :query || '%' ORDER BY nameEnglish ASC")
    fun searchCategories(query: String): Flow<List<Category>>
    
    // Advanced queries with relationships
    
    @Transaction
    @Query("SELECT * FROM categories WHERE id = :categoryId")
    suspend fun getCategoryWithWords(categoryId: Long): CategoryWithWords?
    
    @Transaction
    @Query("SELECT * FROM categories WHERE id = :categoryId")
    fun getCategoryWithWordsFlow(categoryId: Long): Flow<CategoryWithWords?>
    
    @Transaction
    @Query("SELECT * FROM categories ORDER BY orderIndex ASC, nameEnglish ASC")
    fun getAllCategoriesWithWords(): Flow<List<CategoryWithWords>>
    
    @Query("""
        SELECT c.* FROM categories c
        INNER JOIN word_category_cross_refs wc ON c.id = wc.categoryId
        WHERE wc.wordId = :wordId
        ORDER BY c.orderIndex ASC, c.nameEnglish ASC
    """)
    fun getCategoriesForWord(wordId: Long): Flow<List<Category>>
    
    // Statistics queries
    
    @Query("SELECT COUNT(*) FROM categories")
    fun getCategoryCount(): Flow<Int>
    
    @Query("SELECT COUNT(*) FROM categories WHERE isDefault = 1")
    fun getDefaultCategoryCount(): Flow<Int>
    
    @Query("""
        SELECT COUNT(DISTINCT c.id) FROM categories c
        INNER JOIN word_category_cross_refs wc ON c.id = wc.categoryId
        INNER JOIN user_learning_progress up ON wc.wordId = up.wordId
        WHERE up.totalAttempts > 0
    """)
    fun getStartedCategoryCount(): Flow<Int>
    
    // Utility queries
    
    @Query("SELECT EXISTS(SELECT 1 FROM categories WHERE nameEnglish = :nameEnglish LIMIT 1)")
    suspend fun categoryExistsByName(nameEnglish: String): Boolean
    
    @Query("SELECT MAX(orderIndex) + 1 FROM categories")
    suspend fun getNextOrderIndex(): Int
    
    @Query("UPDATE categories SET orderIndex = :newIndex WHERE id = :categoryId")
    suspend fun updateCategoryOrder(categoryId: Long, newIndex: Int)
    
    @Query("SELECT EXISTS(SELECT 1 FROM word_category_cross_refs WHERE wordId = :wordId AND categoryId = :categoryId LIMIT 1)")
    suspend fun isWordInCategory(wordId: Long, categoryId: Long): Boolean
}