package com.bhashasetu.app.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.bhashasetu.app.model.WordCategory;
import java.util.List;

/**
 * Data Access Object for WordCategory entities.
 */
@Dao
public interface WordCategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertCategory(WordCategory category);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<WordCategory> categories);

    @Update
    void updateCategory(WordCategory category);

    @Delete
    void deleteCategory(WordCategory category);

    @Query("SELECT * FROM word_categories ORDER BY 'order' ASC")
    LiveData<List<WordCategory>> getAllCategories();

    @Query("SELECT * FROM word_categories WHERE isActive = 1 ORDER BY 'order' ASC")
    LiveData<List<WordCategory>> getAllActiveCategories();

    @Query("SELECT * FROM word_categories WHERE id = :categoryId")
    LiveData<WordCategory> getCategoryById(long categoryId);

    @Query("UPDATE word_categories SET `order` = :newOrder WHERE id = :categoryId")
    void updateCategoryOrder(long categoryId, int newOrder);

    @Query("SELECT * FROM word_categories WHERE id = :id")
    WordCategory getCategoryByIdSync(int id);

    @Query("SELECT * FROM word_categories WHERE name = :name")
    LiveData<WordCategory> getCategoryByName(String name);

    @Query("SELECT COUNT(*) FROM word_categories")
    LiveData<Integer> getTotalCount();

    @Query("DELETE FROM word_categories")
    void deleteAll();
}