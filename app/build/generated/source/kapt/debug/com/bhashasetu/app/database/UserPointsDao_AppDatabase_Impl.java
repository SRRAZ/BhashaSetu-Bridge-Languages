package com.bhashasetu.app.database;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteStatement;
import com.bhashasetu.app.database.converters.DateConverter;
import com.bhashasetu.app.database.converters.PointsSourceConverter;
import com.bhashasetu.app.model.gamification.PointsSource;
import com.bhashasetu.app.model.gamification.UserPoints;
import java.lang.Class;
import java.lang.IllegalArgumentException;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation", "removal"})
public final class UserPointsDao_AppDatabase_Impl implements UserPointsDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<UserPoints> __insertAdapterOfUserPoints;

  public UserPointsDao_AppDatabase_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfUserPoints = new EntityInsertAdapter<UserPoints>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `user_points` (`id`,`userId`,`points`,`description`,`source`,`earnedAt`,`sourceId`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final UserPoints entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getUserId());
        statement.bindLong(3, entity.getPoints());
        if (entity.getDescription() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getDescription());
        }
        final String _tmp = PointsSourceConverter.fromPointsSource(entity.getSource());
        if (_tmp == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, _tmp);
        }
        final Long _tmp_1 = DateConverter.dateToTimestamp(entity.getEarnedAt());
        if (_tmp_1 == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, _tmp_1);
        }
        statement.bindLong(7, entity.getSourceId());
      }
    };
  }

  @Override
  public long insert(final UserPoints userPoints) {
    return DBUtil.performBlocking(__db, false, true, (_connection) -> {
      return __insertAdapterOfUserPoints.insertAndReturnId(_connection, userPoints);
    });
  }

  @Override
  public LiveData<List<UserPoints>> getAllPoints(final int userId) {
    final String _sql = "SELECT * FROM user_points WHERE userId = ? ORDER BY earnedAt DESC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"user_points"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, userId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfUserId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "userId");
        final int _columnIndexOfPoints = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "points");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfSource = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "source");
        final int _columnIndexOfEarnedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "earnedAt");
        final int _columnIndexOfSourceId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "sourceId");
        final List<UserPoints> _result = new ArrayList<UserPoints>();
        while (_stmt.step()) {
          final UserPoints _item;
          _item = new UserPoints();
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final int _tmpUserId;
          _tmpUserId = (int) (_stmt.getLong(_columnIndexOfUserId));
          _item.setUserId(_tmpUserId);
          final int _tmpPoints;
          _tmpPoints = (int) (_stmt.getLong(_columnIndexOfPoints));
          _item.setPoints(_tmpPoints);
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          _item.setDescription(_tmpDescription);
          final PointsSource _tmpSource;
          final String _tmp;
          if (_stmt.isNull(_columnIndexOfSource)) {
            _tmp = null;
          } else {
            _tmp = _stmt.getText(_columnIndexOfSource);
          }
          _tmpSource = PointsSourceConverter.toPointsSource(_tmp);
          _item.setSource(_tmpSource);
          final Date _tmpEarnedAt;
          final Long _tmp_1;
          if (_stmt.isNull(_columnIndexOfEarnedAt)) {
            _tmp_1 = null;
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfEarnedAt);
          }
          _tmpEarnedAt = DateConverter.fromTimestamp(_tmp_1);
          _item.setEarnedAt(_tmpEarnedAt);
          final int _tmpSourceId;
          _tmpSourceId = (int) (_stmt.getLong(_columnIndexOfSourceId));
          _item.setSourceId(_tmpSourceId);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<UserPoints>> getRecentPoints(final int userId, final int limit) {
    final String _sql = "SELECT * FROM user_points WHERE userId = ? ORDER BY earnedAt DESC LIMIT ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"user_points"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, userId);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, limit);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfUserId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "userId");
        final int _columnIndexOfPoints = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "points");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfSource = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "source");
        final int _columnIndexOfEarnedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "earnedAt");
        final int _columnIndexOfSourceId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "sourceId");
        final List<UserPoints> _result = new ArrayList<UserPoints>();
        while (_stmt.step()) {
          final UserPoints _item;
          _item = new UserPoints();
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final int _tmpUserId;
          _tmpUserId = (int) (_stmt.getLong(_columnIndexOfUserId));
          _item.setUserId(_tmpUserId);
          final int _tmpPoints;
          _tmpPoints = (int) (_stmt.getLong(_columnIndexOfPoints));
          _item.setPoints(_tmpPoints);
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          _item.setDescription(_tmpDescription);
          final PointsSource _tmpSource;
          final String _tmp;
          if (_stmt.isNull(_columnIndexOfSource)) {
            _tmp = null;
          } else {
            _tmp = _stmt.getText(_columnIndexOfSource);
          }
          _tmpSource = PointsSourceConverter.toPointsSource(_tmp);
          _item.setSource(_tmpSource);
          final Date _tmpEarnedAt;
          final Long _tmp_1;
          if (_stmt.isNull(_columnIndexOfEarnedAt)) {
            _tmp_1 = null;
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfEarnedAt);
          }
          _tmpEarnedAt = DateConverter.fromTimestamp(_tmp_1);
          _item.setEarnedAt(_tmpEarnedAt);
          final int _tmpSourceId;
          _tmpSourceId = (int) (_stmt.getLong(_columnIndexOfSourceId));
          _item.setSourceId(_tmpSourceId);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<UserPoints>> getPointsBySource(final int userId, final PointsSource source) {
    final String _sql = "SELECT * FROM user_points WHERE userId = ? AND source = ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"user_points"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, userId);
        _argIndex = 2;
        if (source == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, __PointsSource_enumToString(source));
        }
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfUserId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "userId");
        final int _columnIndexOfPoints = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "points");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfSource = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "source");
        final int _columnIndexOfEarnedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "earnedAt");
        final int _columnIndexOfSourceId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "sourceId");
        final List<UserPoints> _result = new ArrayList<UserPoints>();
        while (_stmt.step()) {
          final UserPoints _item;
          _item = new UserPoints();
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final int _tmpUserId;
          _tmpUserId = (int) (_stmt.getLong(_columnIndexOfUserId));
          _item.setUserId(_tmpUserId);
          final int _tmpPoints;
          _tmpPoints = (int) (_stmt.getLong(_columnIndexOfPoints));
          _item.setPoints(_tmpPoints);
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          _item.setDescription(_tmpDescription);
          final PointsSource _tmpSource;
          final String _tmp;
          if (_stmt.isNull(_columnIndexOfSource)) {
            _tmp = null;
          } else {
            _tmp = _stmt.getText(_columnIndexOfSource);
          }
          _tmpSource = PointsSourceConverter.toPointsSource(_tmp);
          _item.setSource(_tmpSource);
          final Date _tmpEarnedAt;
          final Long _tmp_1;
          if (_stmt.isNull(_columnIndexOfEarnedAt)) {
            _tmp_1 = null;
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfEarnedAt);
          }
          _tmpEarnedAt = DateConverter.fromTimestamp(_tmp_1);
          _item.setEarnedAt(_tmpEarnedAt);
          final int _tmpSourceId;
          _tmpSourceId = (int) (_stmt.getLong(_columnIndexOfSourceId));
          _item.setSourceId(_tmpSourceId);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public int getTotalPoints(final int userId) {
    final String _sql = "SELECT SUM(points) FROM user_points WHERE userId = ?";
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
  public int getTotalPointsBySource(final int userId, final PointsSource source) {
    final String _sql = "SELECT SUM(points) FROM user_points WHERE userId = ? AND source = ?";
    return DBUtil.performBlocking(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, userId);
        _argIndex = 2;
        if (source == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, __PointsSource_enumToString(source));
        }
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
  public int getPointsEntryCount(final int userId) {
    final String _sql = "SELECT COUNT(*) FROM user_points WHERE userId = ?";
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
  public int getHighestPointsEarned(final int userId) {
    final String _sql = "SELECT MAX(points) FROM user_points WHERE userId = ?";
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
  public int getPointsEarnedInTimeRange(final int userId, final long startTime,
      final long endTime) {
    final String _sql = "SELECT SUM(points) FROM user_points WHERE userId = ? AND earnedAt >= ? AND earnedAt <= ?";
    return DBUtil.performBlocking(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, userId);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, startTime);
        _argIndex = 3;
        _stmt.bindLong(_argIndex, endTime);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private String __PointsSource_enumToString(@NonNull final PointsSource _value) {
    switch (_value) {
      case EXERCISE_COMPLETION: return "EXERCISE_COMPLETION";
      case ACHIEVEMENT_UNLOCK: return "ACHIEVEMENT_UNLOCK";
      case DAILY_STREAK: return "DAILY_STREAK";
      case WORD_MASTERY: return "WORD_MASTERY";
      case INITIAL_BONUS: return "INITIAL_BONUS";
      case PERFECT_SCORE: return "PERFECT_SCORE";
      case LEVEL_UP: return "LEVEL_UP";
      default: throw new IllegalArgumentException("Can't convert enum to string, unknown enum value: " + _value);
    }
  }
}
