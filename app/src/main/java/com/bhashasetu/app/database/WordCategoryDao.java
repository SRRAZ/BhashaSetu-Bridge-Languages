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
    void insert(WordCategory wordCategory);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<WordCategory> wordCategories);

    @Update
    void update(WordCategory wordCategory);

    @Delete
    void delete(WordCategory wordCategory);

    @Query("SELECT * FROM word_categories ORDER BY name")
    LiveData<List<WordCategory>> getAllCategories();

    @Query("SELECT * FROM word_categories WHERE isActive = 1 ORDER BY name")
    LiveData<List<WordCategory>> getActiveCategories();

    @Query("SELECT * FROM word_categories WHERE id = :id")
    LiveData<WordCategory> getCategoryById(int id);

    @Query("SELECT * FROM word_categories WHERE id = :id")
    WordCategory getCategoryByIdSync(int id);

    @Query("SELECT * FROM word_categories WHERE name = :name")
    LiveData<WordCategory> getCategoryByName(String name);

    @Query("SELECT COUNT(*) FROM word_categories")
    LiveData<Integer> getTotalCount();

    @Query("DELETE FROM word_categories")
    void deleteAll();
}