package com.bhashasetu.app.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.bhashasetu.app.model.User;

import java.util.List;

/**
 * Data Access Object for User entities.
 */
@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<User> users);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM users ORDER BY username")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM users WHERE id = :id")
    LiveData<User> getUserById(int id);

    @Query("SELECT * FROM users WHERE id = :id")
    User getUserByIdSync(int id);

    @Query("SELECT * FROM users WHERE username = :username")
    LiveData<User> getUserByUsername(String username);

    @Query("SELECT * FROM users WHERE email = :email")
    LiveData<User> getUserByEmail(String email);

    @Query("SELECT COUNT(*) > 0 FROM users WHERE username = :username")
    boolean usernameExists(String username);

    @Query("SELECT COUNT(*) > 0 FROM users WHERE email = :email")
    boolean emailExists(String email);

    @Query("SELECT * FROM users WHERE preferredLanguage = :language ORDER BY username")
    LiveData<List<User>> getUsersByLanguage(String language);

    @Query("SELECT * FROM users ORDER BY totalPoints DESC LIMIT :limit")
    LiveData<List<User>> getTopUsersByPoints(int limit);

    @Query("SELECT COUNT(*) FROM users")
    LiveData<Integer> getTotalCount();

    @Query("DELETE FROM users")
    void deleteAll();
}