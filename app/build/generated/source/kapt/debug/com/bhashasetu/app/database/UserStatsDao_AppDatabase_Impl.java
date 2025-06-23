package com.bhashasetu.app.database;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeleteOrUpdateAdapter;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteStatement;
import com.bhashasetu.app.database.converters.DateConverter;
import com.bhashasetu.app.model.gamification.UserStats;
import java.lang.Class;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation", "removal"})
public final class UserStatsDao_AppDatabase_Impl implements UserStatsDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<UserStats> __insertAdapterOfUserStats;

  private final EntityDeleteOrUpdateAdapter<UserStats> __updateAdapterOfUserStats;

  public UserStatsDao_AppDatabase_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfUserStats = new EntityInsertAdapter<UserStats>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `user_stats` (`id`,`userId`,`wordsLearned`,`exercisesCompleted`,`dailyStreak`,`longestStreak`,`perfectScores`,`totalTimeSpent`,`registeredAt`,`lastActive`,`categoriesUnlocked`,`achievementsUnlocked`,`badgesEarned`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final UserStats entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getUserId());
        statement.bindLong(3, entity.getWordsLearned());
        statement.bindLong(4, entity.getExercisesCompleted());
        statement.bindLong(5, entity.getDailyStreak());
        statement.bindLong(6, entity.getLongestStreak());
        statement.bindLong(7, entity.getPerfectScores());
        statement.bindLong(8, entity.getTotalTimeSpent());
        final Long _tmp = DateConverter.dateToTimestamp(entity.getRegisteredAt());
        if (_tmp == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, _tmp);
        }
        final Long _tmp_1 = DateConverter.dateToTimestamp(entity.getLastActive());
        if (_tmp_1 == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, _tmp_1);
        }
        statement.bindLong(11, entity.getCategoriesUnlocked());
        statement.bindLong(12, entity.getAchievementsUnlocked());
        statement.bindLong(13, entity.getBadgesEarned());
      }
    };
    this.__updateAdapterOfUserStats = new EntityDeleteOrUpdateAdapter<UserStats>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `user_stats` SET `id` = ?,`userId` = ?,`wordsLearned` = ?,`exercisesCompleted` = ?,`dailyStreak` = ?,`longestStreak` = ?,`perfectScores` = ?,`totalTimeSpent` = ?,`registeredAt` = ?,`lastActive` = ?,`categoriesUnlocked` = ?,`achievementsUnlocked` = ?,`badgesEarned` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final UserStats entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getUserId());
        statement.bindLong(3, entity.getWordsLearned());
        statement.bindLong(4, entity.getExercisesCompleted());
        statement.bindLong(5, entity.getDailyStreak());
        statement.bindLong(6, entity.getLongestStreak());
        statement.bindLong(7, entity.getPerfectScores());
        statement.bindLong(8, entity.getTotalTimeSpent());
        final Long _tmp = DateConverter.dateToTimestamp(entity.getRegisteredAt());
        if (_tmp == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, _tmp);
        }
        final Long _tmp_1 = DateConverter.dateToTimestamp(entity.getLastActive());
        if (_tmp_1 == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, _tmp_1);
        }
        statement.bindLong(11, entity.getCategoriesUnlocked());
        statement.bindLong(12, entity.getAchievementsUnlocked());
        statement.bindLong(13, entity.getBadgesEarned());
        statement.bindLong(14, entity.getId());
      }
    };
  }

  @Override
  public long insert(final UserStats userStats) {
    return DBUtil.performBlocking(__db, false, true, (_connection) -> {
      return __insertAdapterOfUserStats.insertAndReturnId(_connection, userStats);
    });
  }

  @Override
  public void update(final UserStats userStats) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __updateAdapterOfUserStats.handle(_connection, userStats);
      return null;
    });
  }

  @Override
  public UserStats getUserStats(final int userId) {
    final String _sql = "SELECT * FROM user_stats WHERE userId = ?";
    return DBUtil.performBlocking(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, userId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfUserId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "userId");
        final int _columnIndexOfWordsLearned = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wordsLearned");
        final int _columnIndexOfExercisesCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "exercisesCompleted");
        final int _columnIndexOfDailyStreak = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "dailyStreak");
        final int _columnIndexOfLongestStreak = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "longestStreak");
        final int _columnIndexOfPerfectScores = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "perfectScores");
        final int _columnIndexOfTotalTimeSpent = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalTimeSpent");
        final int _columnIndexOfRegisteredAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "registeredAt");
        final int _columnIndexOfLastActive = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastActive");
        final int _columnIndexOfCategoriesUnlocked = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "categoriesUnlocked");
        final int _columnIndexOfAchievementsUnlocked = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "achievementsUnlocked");
        final int _columnIndexOfBadgesEarned = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "badgesEarned");
        final UserStats _result;
        if (_stmt.step()) {
          _result = new UserStats();
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _result.setId(_tmpId);
          final int _tmpUserId;
          _tmpUserId = (int) (_stmt.getLong(_columnIndexOfUserId));
          _result.setUserId(_tmpUserId);
          final int _tmpWordsLearned;
          _tmpWordsLearned = (int) (_stmt.getLong(_columnIndexOfWordsLearned));
          _result.setWordsLearned(_tmpWordsLearned);
          final int _tmpExercisesCompleted;
          _tmpExercisesCompleted = (int) (_stmt.getLong(_columnIndexOfExercisesCompleted));
          _result.setExercisesCompleted(_tmpExercisesCompleted);
          final int _tmpDailyStreak;
          _tmpDailyStreak = (int) (_stmt.getLong(_columnIndexOfDailyStreak));
          _result.setDailyStreak(_tmpDailyStreak);
          final int _tmpLongestStreak;
          _tmpLongestStreak = (int) (_stmt.getLong(_columnIndexOfLongestStreak));
          _result.setLongestStreak(_tmpLongestStreak);
          final int _tmpPerfectScores;
          _tmpPerfectScores = (int) (_stmt.getLong(_columnIndexOfPerfectScores));
          _result.setPerfectScores(_tmpPerfectScores);
          final long _tmpTotalTimeSpent;
          _tmpTotalTimeSpent = _stmt.getLong(_columnIndexOfTotalTimeSpent);
          _result.setTotalTimeSpent(_tmpTotalTimeSpent);
          final Date _tmpRegisteredAt;
          final Long _tmp;
          if (_stmt.isNull(_columnIndexOfRegisteredAt)) {
            _tmp = null;
          } else {
            _tmp = _stmt.getLong(_columnIndexOfRegisteredAt);
          }
          _tmpRegisteredAt = DateConverter.fromTimestamp(_tmp);
          _result.setRegisteredAt(_tmpRegisteredAt);
          final Date _tmpLastActive;
          final Long _tmp_1;
          if (_stmt.isNull(_columnIndexOfLastActive)) {
            _tmp_1 = null;
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfLastActive);
          }
          _tmpLastActive = DateConverter.fromTimestamp(_tmp_1);
          _result.setLastActive(_tmpLastActive);
          final int _tmpCategoriesUnlocked;
          _tmpCategoriesUnlocked = (int) (_stmt.getLong(_columnIndexOfCategoriesUnlocked));
          _result.setCategoriesUnlocked(_tmpCategoriesUnlocked);
          final int _tmpAchievementsUnlocked;
          _tmpAchievementsUnlocked = (int) (_stmt.getLong(_columnIndexOfAchievementsUnlocked));
          _result.setAchievementsUnlocked(_tmpAchievementsUnlocked);
          final int _tmpBadgesEarned;
          _tmpBadgesEarned = (int) (_stmt.getLong(_columnIndexOfBadgesEarned));
          _result.setBadgesEarned(_tmpBadgesEarned);
        } else {
          _result = null;
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<UserStats> getUserStatsLive(final int userId) {
    final String _sql = "SELECT * FROM user_stats WHERE userId = ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"user_stats"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, userId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfUserId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "userId");
        final int _columnIndexOfWordsLearned = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wordsLearned");
        final int _columnIndexOfExercisesCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "exercisesCompleted");
        final int _columnIndexOfDailyStreak = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "dailyStreak");
        final int _columnIndexOfLongestStreak = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "longestStreak");
        final int _columnIndexOfPerfectScores = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "perfectScores");
        final int _columnIndexOfTotalTimeSpent = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalTimeSpent");
        final int _columnIndexOfRegisteredAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "registeredAt");
        final int _columnIndexOfLastActive = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastActive");
        final int _columnIndexOfCategoriesUnlocked = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "categoriesUnlocked");
        final int _columnIndexOfAchievementsUnlocked = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "achievementsUnlocked");
        final int _columnIndexOfBadgesEarned = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "badgesEarned");
        final UserStats _result;
        if (_stmt.step()) {
          _result = new UserStats();
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _result.setId(_tmpId);
          final int _tmpUserId;
          _tmpUserId = (int) (_stmt.getLong(_columnIndexOfUserId));
          _result.setUserId(_tmpUserId);
          final int _tmpWordsLearned;
          _tmpWordsLearned = (int) (_stmt.getLong(_columnIndexOfWordsLearned));
          _result.setWordsLearned(_tmpWordsLearned);
          final int _tmpExercisesCompleted;
          _tmpExercisesCompleted = (int) (_stmt.getLong(_columnIndexOfExercisesCompleted));
          _result.setExercisesCompleted(_tmpExercisesCompleted);
          final int _tmpDailyStreak;
          _tmpDailyStreak = (int) (_stmt.getLong(_columnIndexOfDailyStreak));
          _result.setDailyStreak(_tmpDailyStreak);
          final int _tmpLongestStreak;
          _tmpLongestStreak = (int) (_stmt.getLong(_columnIndexOfLongestStreak));
          _result.setLongestStreak(_tmpLongestStreak);
          final int _tmpPerfectScores;
          _tmpPerfectScores = (int) (_stmt.getLong(_columnIndexOfPerfectScores));
          _result.setPerfectScores(_tmpPerfectScores);
          final long _tmpTotalTimeSpent;
          _tmpTotalTimeSpent = _stmt.getLong(_columnIndexOfTotalTimeSpent);
          _result.setTotalTimeSpent(_tmpTotalTimeSpent);
          final Date _tmpRegisteredAt;
          final Long _tmp;
          if (_stmt.isNull(_columnIndexOfRegisteredAt)) {
            _tmp = null;
          } else {
            _tmp = _stmt.getLong(_columnIndexOfRegisteredAt);
          }
          _tmpRegisteredAt = DateConverter.fromTimestamp(_tmp);
          _result.setRegisteredAt(_tmpRegisteredAt);
          final Date _tmpLastActive;
          final Long _tmp_1;
          if (_stmt.isNull(_columnIndexOfLastActive)) {
            _tmp_1 = null;
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfLastActive);
          }
          _tmpLastActive = DateConverter.fromTimestamp(_tmp_1);
          _result.setLastActive(_tmpLastActive);
          final int _tmpCategoriesUnlocked;
          _tmpCategoriesUnlocked = (int) (_stmt.getLong(_columnIndexOfCategoriesUnlocked));
          _result.setCategoriesUnlocked(_tmpCategoriesUnlocked);
          final int _tmpAchievementsUnlocked;
          _tmpAchievementsUnlocked = (int) (_stmt.getLong(_columnIndexOfAchievementsUnlocked));
          _result.setAchievementsUnlocked(_tmpAchievementsUnlocked);
          final int _tmpBadgesEarned;
          _tmpBadgesEarned = (int) (_stmt.getLong(_columnIndexOfBadgesEarned));
          _result.setBadgesEarned(_tmpBadgesEarned);
        } else {
          _result = null;
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public int getDailyStreak(final int userId) {
    final String _sql = "SELECT dailyStreak FROM user_stats WHERE userId = ?";
    return DBUtil.performBlocking(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, userId);
        final int _result;
        if (_stmt.step()) {
          _result = (int) (_stmt.getLong(0));
        } else {
          _result = 0;
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public void updateDailyStreak(final int userId, final int streak) {
    final String _sql = "UPDATE user_stats SET dailyStreak = ? WHERE userId = ?";
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, streak);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, userId);
        _stmt.step();
        return null;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public void incrementWordsLearned(final int userId) {
    final String _sql = "UPDATE user_stats SET wordsLearned = wordsLearned + 1 WHERE userId = ?";
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, userId);
        _stmt.step();
        return null;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public void incrementExercisesCompleted(final int userId) {
    final String _sql = "UPDATE user_stats SET exercisesCompleted = exercisesCompleted + 1 WHERE userId = ?";
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, userId);
        _stmt.step();
        return null;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public void incrementPerfectScores(final int userId) {
    final String _sql = "UPDATE user_stats SET perfectScores = perfectScores + 1 WHERE userId = ?";
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, userId);
        _stmt.step();
        return null;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public void addTimeSpent(final int userId, final long seconds) {
    final String _sql = "UPDATE user_stats SET totalTimeSpent = totalTimeSpent + ? WHERE userId = ?";
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, seconds);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, userId);
        _stmt.step();
        return null;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public void updateLastActive(final int userId, final long timestamp) {
    final String _sql = "UPDATE user_stats SET lastActive = ? WHERE userId = ?";
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, timestamp);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, userId);
        _stmt.step();
        return null;
      } finally {
        _stmt.close();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
