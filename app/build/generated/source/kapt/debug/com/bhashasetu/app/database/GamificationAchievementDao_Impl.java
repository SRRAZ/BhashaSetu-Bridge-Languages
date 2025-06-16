package com.bhashasetu.app.database;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeleteOrUpdateAdapter;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.util.DBUtil;
import androidx.sqlite.SQLiteStatement;
import com.bhashasetu.app.database.converters.AchievementTypeConverter;
import com.bhashasetu.app.database.converters.DateConverter;
import com.bhashasetu.app.model.gamification.Achievement;
import com.bhashasetu.app.model.gamification.AchievementType;
import java.lang.Class;
import java.lang.Double;
import java.lang.Integer;
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
public final class GamificationAchievementDao_Impl implements GamificationAchievementDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<Achievement> __insertAdapterOfAchievement;

  private final EntityDeleteOrUpdateAdapter<Achievement> __deleteAdapterOfAchievement;

  private final EntityDeleteOrUpdateAdapter<Achievement> __updateAdapterOfAchievement;

  public GamificationAchievementDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfAchievement = new EntityInsertAdapter<Achievement>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `user_achievements` (`id`,`title`,`description`,`type`,`threshold`,`currentProgress`,`unlocked`,`unlockedAt`,`pointsAwarded`,`badgeImagePath`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final Achievement entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getTitle());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getDescription());
        }
        final String _tmp = AchievementTypeConverter.fromAchievementType(entity.getType());
        if (_tmp == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, _tmp);
        }
        statement.bindLong(5, entity.getThreshold());
        statement.bindLong(6, entity.getCurrentProgress());
        final int _tmp_1 = entity.isUnlocked() ? 1 : 0;
        statement.bindLong(7, _tmp_1);
        final Long _tmp_2 = DateConverter.dateToTimestamp(entity.getUnlockedAt());
        if (_tmp_2 == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, _tmp_2);
        }
        statement.bindLong(9, entity.getPointsAwarded());
        if (entity.getBadgeImagePath() == null) {
          statement.bindNull(10);
        } else {
          statement.bindText(10, entity.getBadgeImagePath());
        }
      }
    };
    this.__deleteAdapterOfAchievement = new EntityDeleteOrUpdateAdapter<Achievement>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `user_achievements` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final Achievement entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfAchievement = new EntityDeleteOrUpdateAdapter<Achievement>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `user_achievements` SET `id` = ?,`title` = ?,`description` = ?,`type` = ?,`threshold` = ?,`currentProgress` = ?,`unlocked` = ?,`unlockedAt` = ?,`pointsAwarded` = ?,`badgeImagePath` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final Achievement entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getTitle());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getDescription());
        }
        final String _tmp = AchievementTypeConverter.fromAchievementType(entity.getType());
        if (_tmp == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, _tmp);
        }
        statement.bindLong(5, entity.getThreshold());
        statement.bindLong(6, entity.getCurrentProgress());
        final int _tmp_1 = entity.isUnlocked() ? 1 : 0;
        statement.bindLong(7, _tmp_1);
        final Long _tmp_2 = DateConverter.dateToTimestamp(entity.getUnlockedAt());
        if (_tmp_2 == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, _tmp_2);
        }
        statement.bindLong(9, entity.getPointsAwarded());
        if (entity.getBadgeImagePath() == null) {
          statement.bindNull(10);
        } else {
          statement.bindText(10, entity.getBadgeImagePath());
        }
        statement.bindLong(11, entity.getId());
      }
    };
  }

  @Override
  public void insert(final Achievement achievement) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __insertAdapterOfAchievement.insert(_connection, achievement);
      return null;
    });
  }

  @Override
  public void insertAll(final List<Achievement> achievements) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __insertAdapterOfAchievement.insert(_connection, achievements);
      return null;
    });
  }

  @Override
  public void delete(final Achievement achievement) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __deleteAdapterOfAchievement.handle(_connection, achievement);
      return null;
    });
  }

  @Override
  public void update(final Achievement achievement) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __updateAdapterOfAchievement.handle(_connection, achievement);
      return null;
    });
  }

  @Override
  public LiveData<List<Achievement>> getAllAchievements() {
    final String _sql = "SELECT * FROM user_achievements ORDER BY type, title";
    return __db.getInvalidationTracker().createLiveData(new String[] {"user_achievements"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Achievement>> getUnlockedAchievements() {
    final String _sql = "SELECT * FROM user_achievements WHERE unlocked = 1 ORDER BY unlockedAt DESC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"user_achievements"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Achievement>> getLockedAchievements() {
    final String _sql = "SELECT * FROM user_achievements WHERE unlocked = 0 ORDER BY type, title";
    return __db.getInvalidationTracker().createLiveData(new String[] {"user_achievements"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Achievement>> getAchievementsByType(final AchievementType type) {
    final String _sql = "SELECT * FROM user_achievements WHERE type = ? ORDER BY threshold";
    return __db.getInvalidationTracker().createLiveData(new String[] {"user_achievements"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        final String _tmp = AchievementTypeConverter.fromAchievementType(type);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, _tmp);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<Achievement> getAchievementById(final int id) {
    final String _sql = "SELECT * FROM user_achievements WHERE id = ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"user_achievements"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Achievement getAchievementByIdSync(final int id) {
    final String _sql = "SELECT * FROM user_achievements WHERE id = ?";
    return DBUtil.performBlocking(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Achievement>> getAchievementsByTitle(final String title) {
    final String _sql = "SELECT * FROM user_achievements WHERE title LIKE '%' || ? || '%' ORDER BY title";
    return __db.getInvalidationTracker().createLiveData(new String[] {"user_achievements"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (title == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, title);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Achievement>> getAchievementsWithMinProgress(final int minProgress) {
    final String _sql = "SELECT * FROM user_achievements WHERE currentProgress >= ? ORDER BY currentProgress DESC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"user_achievements"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, minProgress);
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Achievement>> getAchievementsNearCompletion(final double percentage) {
    final String _sql = "SELECT * FROM user_achievements WHERE (currentProgress * 100.0 / threshold) >= ? AND unlocked = 0 ORDER BY (currentProgress * 100.0 / threshold) DESC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"user_achievements"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindDouble(_argIndex, percentage);
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Achievement>> getAchievementsReadyToUnlock() {
    final String _sql = "SELECT * FROM user_achievements WHERE currentProgress >= threshold AND unlocked = 0";
    return __db.getInvalidationTracker().createLiveData(new String[] {"user_achievements"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<Integer> getTotalPointsFromAchievements() {
    final String _sql = "SELECT SUM(pointsAwarded) FROM user_achievements WHERE unlocked = 1";
    return __db.getInvalidationTracker().createLiveData(new String[] {"user_achievements"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<Integer> getUnlockedCount() {
    final String _sql = "SELECT COUNT(*) FROM user_achievements WHERE unlocked = 1";
    return __db.getInvalidationTracker().createLiveData(new String[] {"user_achievements"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<Integer> getTotalCount() {
    final String _sql = "SELECT COUNT(*) FROM user_achievements";
    return __db.getInvalidationTracker().createLiveData(new String[] {"user_achievements"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Achievement>> getAchievementsByPoints() {
    final String _sql = "SELECT * FROM user_achievements ORDER BY pointsAwarded DESC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"user_achievements"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Achievement>> getRecentlyUnlocked(final int limit) {
    final String _sql = "SELECT * FROM user_achievements WHERE unlocked = 1 ORDER BY unlockedAt DESC LIMIT ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"user_achievements"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, limit);
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<Double> getCompletionPercentage() {
    final String _sql = "SELECT (COUNT(CASE WHEN unlocked = 1 THEN 1 END) * 100.0 / COUNT(*)) FROM user_achievements";
    return __db.getInvalidationTracker().createLiveData(new String[] {"user_achievements"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<GamificationAchievementDao.AchievementTypeStats>> getStatsByType() {
    final String _sql = "SELECT type, COUNT(*) as total, COUNT(CASE WHEN unlocked = 1 THEN 1 END) as unlocked, SUM(pointsAwarded) as totalPoints FROM user_achievements GROUP BY type ORDER BY type";
    return __db.getInvalidationTracker().createLiveData(new String[] {"user_achievements"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public boolean achievementExists(final String title) {
    final String _sql = "SELECT COUNT(*) > 0 FROM user_achievements WHERE title = ?";
    return DBUtil.performBlocking(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (title == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, title);
        }
        final boolean _result;
        if (_stmt.step()) {
          final int _tmp;
          _tmp = (int) (_stmt.getLong(0));
          _result = _tmp != 0;
        } else {
          _result = false;
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public void updateProgress(final int id, final int progress) {
    final String _sql = "UPDATE user_achievements SET currentProgress = ? WHERE id = ?";
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, progress);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, id);
        _stmt.step();
        return null;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public void incrementProgress(final int id, final int increment) {
    final String _sql = "UPDATE user_achievements SET currentProgress = currentProgress + ? WHERE id = ?";
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, increment);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, id);
        _stmt.step();
        return null;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public void unlockAchievement(final int id, final Date unlockedAt) {
    final String _sql = "UPDATE user_achievements SET unlocked = 1, unlockedAt = ? WHERE id = ?";
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        final Long _tmp = DateConverter.dateToTimestamp(unlockedAt);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp);
        }
        _argIndex = 2;
        _stmt.bindLong(_argIndex, id);
        _stmt.step();
        return null;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public void resetAchievement(final int id) {
    final String _sql = "UPDATE user_achievements SET currentProgress = 0, unlocked = 0, unlockedAt = NULL WHERE id = ?";
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        _stmt.step();
        return null;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public void deleteAll() {
    final String _sql = "DELETE FROM user_achievements";
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        _stmt.step();
        return null;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public void deleteByType(final AchievementType type) {
    final String _sql = "DELETE FROM user_achievements WHERE type = ?";
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        final String _tmp = AchievementTypeConverter.fromAchievementType(type);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, _tmp);
        }
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
