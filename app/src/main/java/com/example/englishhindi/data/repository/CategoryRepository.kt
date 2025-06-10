package com.bhashasetu.app.data.repository

import androidx.lifecycle.LiveData
import com.bhashasetu.app.data.dao.CategoryDao
import com.bhashasetu.app.data.model.Category
import com.bhashasetu.app.data.model.WordCategoryCrossRef
import com.bhashasetu.app.data.relation.CategoryWithWords
import kotlinx.coroutines.flow.Flow

class CategoryRepository(private val categoryDao: CategoryDao) {

    val allCategories: Flow<List<Category>> = categoryDao.getAllCategories()
    
    suspend fun insert(category: Category): Long {
        return categoryDao.insert(category)
    }
    
    suspend fun insertAll(categories: List<Category>) {
        categoryDao.insertAll(categories)
    }
    
    suspend fun update(category: Category) {
        categoryDao.update(category)
    }
    
    suspend fun delete(category: Category) {
        categoryDao.delete(category)
    }
    
    fun getCategoryById(id: Long): Flow<Category> {
        return categoryDao.getCategoryById(id)
    }
    
    fun getCategoriesByLevel(level: Int): Flow<List<Category>> {
        return categoryDao.getCategoriesByLevel(level)
    }
    
    fun searchCategories(query: String): Flow<List<Category>> {
        return categoryDao.searchCategories("%$query%")
    }
    
    fun getCategoryWithWords(categoryId: Long): Flow<CategoryWithWords> {
        return categoryDao.getCategoryWithWords(categoryId)
    }
    
    fun getAllCategoriesWithWords(): Flow<List<CategoryWithWords>> {
        return categoryDao.getAllCategoriesWithWords()
    }
    
    suspend fun addWordToCategory(wordId: Long, categoryId: Long) {
        categoryDao.insertWordCategoryCrossRef(WordCategoryCrossRef(wordId, categoryId))
    }
    
    suspend fun removeWordFromCategory(wordId: Long, categoryId: Long) {
        categoryDao.deleteWordCategoryCrossRef(WordCategoryCrossRef(wordId, categoryId))
    }
    
    fun getCategoryCount(): Flow<Int> {
        return categoryDao.getCategoryCount()
    }
    
    fun getWordCountInCategory(categoryId: Long): Flow<Int> {
        return categoryDao.getWordCountInCategory(categoryId)
    }
}