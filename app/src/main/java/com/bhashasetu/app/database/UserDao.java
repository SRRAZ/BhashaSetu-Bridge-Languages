package com.bhashasetu.app.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.bhashasetu.app.model.User;
import java.util.List;

/**
 * Data Access Object for User entities.
 */
@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertUser(User user);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("SELECT * FROM users WHERE id = :userId")
    LiveData<User> getUserById(long userId);

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    LiveData<User> getUserByEmail(String email);

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    LiveData<User> getUserByUsername(String username);

    @Query("SELECT * FROM users WHERE isActive = 1 ORDER BY totalScore DESC")
    LiveData<List<User>> getAllActiveUsers();

    @Query("SELECT * FROM users ORDER BY totalScore DESC LIMIT :limit")
    LiveData<List<User>> getTopUsers(int limit);

    // ✅ Updated methods for new fields
    @Query("UPDATE users SET lastActiveAt = :timestamp WHERE id = :userId")
    void updateLastActive(long userId, long timestamp);

    @Query("UPDATE users SET totalScore = :score, totalPoints = :points WHERE id = :userId")
    void updateUserScoreAndPoints(long userId, int score, int points);

    @Query("UPDATE users SET level = :level WHERE id = :userId")
    void updateUserLevel(long userId, int level);

    @Query("UPDATE users SET currentStreak = :streak, lastStreakDate = :date WHERE id = :userId")
    void updateStreak(long userId, int streak, long date);

    @Query("UPDATE users SET maxStreak = :maxStreak WHERE id = :userId")
    void updateMaxStreak(long userId, int maxStreak);

    @Query("UPDATE users SET fullName = :fullName WHERE id = :userId")
    void updateFullName(long userId, String fullName);

    @Query("UPDATE users SET learningGoal = :goal WHERE id = :userId")
    void updateLearningGoal(long userId, String goal);

    @Query("UPDATE users SET totalStudyTime = totalStudyTime + :additionalTime WHERE id = :userId")
    void addStudyTime(long userId, long additionalTime);

    @Query("UPDATE users SET wordsLearned = :count WHERE id = :userId")
    void updateWordsLearned(long userId, int count);

    @Query("UPDATE users SET lessonsCompleted = :count WHERE id = :userId")
    void updateLessonsCompleted(long userId, int count);

    // ✅ Query methods for streak and learning analytics
    @Query("SELECT * FROM users ORDER BY currentStreak DESC LIMIT :limit")
    LiveData<List<User>> getUsersByCurrentStreak(int limit);

    @Query("SELECT * FROM users ORDER BY maxStreak DESC LIMIT :limit")
    LiveData<List<User>> getUsersByMaxStreak(int limit);

    @Query("SELECT * FROM users WHERE learningGoal = :goal")
    LiveData<List<User>> getUsersByLearningGoal(String goal);

    @Query("SELECT AVG(currentStreak) FROM users WHERE isActive = 1")
    LiveData<Double> getAverageCurrentStreak();

    @Query("SELECT AVG(totalPoints) FROM users WHERE isActive = 1")
    LiveData<Double> getAverageTotalPoints();

    @Query("SELECT COUNT(*) FROM users WHERE currentStreak >= :minStreak")
    LiveData<Integer> getUsersWithMinStreak(int minStreak);

    @Query("SELECT SUM(totalStudyTime) FROM users WHERE id = :userId")
    LiveData<Long> getTotalStudyTime(long userId);
}