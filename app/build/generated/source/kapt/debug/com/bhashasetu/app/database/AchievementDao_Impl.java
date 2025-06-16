package com.bhashasetu.app.database;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeleteOrUpdateAdapter;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.util.DBUtil;
import androidx.sqlite.SQLiteStatement;
import com.bhashasetu.app.model.Achievement;
import java.lang.Class;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation", "removal"})
public final class AchievementDao_Impl implements AchievementDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<Achievement> __insertAdapterOfAchievement;

  private final EntityDeleteOrUpdateAdapter<Achievement> __updateAdapterOfAchievement;

  public AchievementDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfAchievement = new EntityInsertAdapter<Achievement>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `legacy_achievements` (`id`,`title`,`description`,`type`,`iconResId`,`pointsValue`,`unlocked`,`dateUnlocked`,`progressCurrent`,`progressTarget`,`secret`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final Achievement entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindText(1, entity.getId());
        }
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
        if (entity.getType() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getType());
        }
        statement.bindLong(5, entity.getIconResId());
        statement.bindLong(6, entity.getPointsValue());
        final int _tmp = entity.isUnlocked() ? 1 : 0;
        statement.bindLong(7, _tmp);
        statement.bindLong(8, entity.getDateUnlocked());
        statement.bindLong(9, entity.getProgressCurrent());
        statement.bindLong(10, entity.getProgressTarget());
        final int _tmp_1 = entity.isSecret() ? 1 : 0;
        statement.bindLong(11, _tmp_1);
      }
    };
    this.__updateAdapterOfAchievement = new EntityDeleteOrUpdateAdapter<Achievement>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `legacy_achievements` SET `id` = ?,`title` = ?,`description` = ?,`type` = ?,`iconResId` = ?,`pointsValue` = ?,`unlocked` = ?,`dateUnlocked` = ?,`progressCurrent` = ?,`progressTarget` = ?,`secret` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final Achievement entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindText(1, entity.getId());
        }
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
        if (entity.getType() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getType());
        }
        statement.bindLong(5, entity.getIconResId());
        statement.bindLong(6, entity.getPointsValue());
        final int _tmp = entity.isUnlocked() ? 1 : 0;
        statement.bindLong(7, _tmp);
        statement.bindLong(8, entity.getDateUnlocked());
        statement.bindLong(9, entity.getProgressCurrent());
        statement.bindLong(10, entity.getProgressTarget());
        final int _tmp_1 = entity.isSecret() ? 1 : 0;
        statement.bindLong(11, _tmp_1);
        if (entity.getId() == null) {
          statement.bindNull(12);
        } else {
          statement.bindText(12, entity.getId());
        }
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
  public void update(final Achievement achievement) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __updateAdapterOfAchievement.handle(_connection, achievement);
      return null;
    });
  }

  @Override
  public LiveData<List<Achievement>> getAllAchievements() {
    final String _sql = "SELECT * FROM achievements ORDER BY type, progressTarget";
    return __db.getInvalidationTracker().createLiveData(new String[] {"achievements"}, false, (_connection) -> {
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
    final String _sql = "SELECT * FROM achievements WHERE unlocked = 1 ORDER BY dateUnlocked DESC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"achievements"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Achievement>> getAchievementsByType(final String type) {
    final String _sql = "SELECT * FROM achievements WHERE type = ? ORDER BY progressTarget";
    return __db.getInvalidationTracker().createLiveData(new String[] {"achievements"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (type == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, type);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<Achievement> getAchievementById(final String id) {
    final String _sql = "SELECT * FROM achievements WHERE id = ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"achievements"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (id == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, id);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Achievement getAchievementByIdSync(final String id) {
    final String _sql = "SELECT * FROM achievements WHERE id = ?";
    return DBUtil.performBlocking(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (id == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, id);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<Integer> getTotalPoints() {
    final String _sql = "SELECT SUM(pointsValue) FROM achievements WHERE unlocked = 1";
    return __db.getInvalidationTracker().createLiveData(new String[] {"achievements"}, false, (_connection) -> {
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
    final String _sql = "SELECT COUNT(*) FROM achievements WHERE unlocked = 1";
    return __db.getInvalidationTracker().createLiveData(new String[] {"achievements"}, false, (_connection) -> {
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
    final String _sql = "SELECT COUNT(*) FROM achievements";
    return __db.getInvalidationTracker().createLiveData(new String[] {"achievements"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public void deleteAll() {
    final String _sql = "DELETE FROM achievements";
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
