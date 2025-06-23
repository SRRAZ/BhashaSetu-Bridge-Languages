package com.bhashasetu.app.database;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeleteOrUpdateAdapter;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteStatement;
import com.bhashasetu.app.model.gamification.UserLevel;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation", "removal"})
public final class UserLevelDao_AppDatabase_Impl implements UserLevelDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<UserLevel> __insertAdapterOfUserLevel;

  private final EntityDeleteOrUpdateAdapter<UserLevel> __updateAdapterOfUserLevel;

  public UserLevelDao_AppDatabase_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfUserLevel = new EntityInsertAdapter<UserLevel>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `user_levels` (`id`,`userId`,`level`,`currentExp`,`expToNextLevel`,`totalExp`,`title`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final UserLevel entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getUserId());
        statement.bindLong(3, entity.getLevel());
        statement.bindLong(4, entity.getCurrentExp());
        statement.bindLong(5, entity.getExpToNextLevel());
        statement.bindLong(6, entity.getTotalExp());
        if (entity.getTitle() == null) {
          statement.bindNull(7);
        } else {
          statement.bindText(7, entity.getTitle());
        }
      }
    };
    this.__updateAdapterOfUserLevel = new EntityDeleteOrUpdateAdapter<UserLevel>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `user_levels` SET `id` = ?,`userId` = ?,`level` = ?,`currentExp` = ?,`expToNextLevel` = ?,`totalExp` = ?,`title` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final UserLevel entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getUserId());
        statement.bindLong(3, entity.getLevel());
        statement.bindLong(4, entity.getCurrentExp());
        statement.bindLong(5, entity.getExpToNextLevel());
        statement.bindLong(6, entity.getTotalExp());
        if (entity.getTitle() == null) {
          statement.bindNull(7);
        } else {
          statement.bindText(7, entity.getTitle());
        }
        statement.bindLong(8, entity.getId());
      }
    };
  }

  @Override
  public long insert(final UserLevel userLevel) {
    return DBUtil.performBlocking(__db, false, true, (_connection) -> {
      return __insertAdapterOfUserLevel.insertAndReturnId(_connection, userLevel);
    });
  }

  @Override
  public void update(final UserLevel userLevel) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __updateAdapterOfUserLevel.handle(_connection, userLevel);
      return null;
    });
  }

  @Override
  public UserLevel getUserLevel(final int userId) {
    final String _sql = "SELECT * FROM user_levels WHERE userId = ?";
    return DBUtil.performBlocking(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, userId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfUserId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "userId");
        final int _columnIndexOfLevel = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "level");
        final int _columnIndexOfCurrentExp = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "currentExp");
        final int _columnIndexOfExpToNextLevel = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "expToNextLevel");
        final int _columnIndexOfTotalExp = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalExp");
        final int _columnIndexOfTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "title");
        final UserLevel _result;
        if (_stmt.step()) {
          _result = new UserLevel();
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _result.setId(_tmpId);
          final int _tmpUserId;
          _tmpUserId = (int) (_stmt.getLong(_columnIndexOfUserId));
          _result.setUserId(_tmpUserId);
          final int _tmpLevel;
          _tmpLevel = (int) (_stmt.getLong(_columnIndexOfLevel));
          _result.setLevel(_tmpLevel);
          final int _tmpCurrentExp;
          _tmpCurrentExp = (int) (_stmt.getLong(_columnIndexOfCurrentExp));
          _result.setCurrentExp(_tmpCurrentExp);
          final int _tmpExpToNextLevel;
          _tmpExpToNextLevel = (int) (_stmt.getLong(_columnIndexOfExpToNextLevel));
          _result.setExpToNextLevel(_tmpExpToNextLevel);
          final int _tmpTotalExp;
          _tmpTotalExp = (int) (_stmt.getLong(_columnIndexOfTotalExp));
          _result.setTotalExp(_tmpTotalExp);
          final String _tmpTitle;
          if (_stmt.isNull(_columnIndexOfTitle)) {
            _tmpTitle = null;
          } else {
            _tmpTitle = _stmt.getText(_columnIndexOfTitle);
          }
          _result.setTitle(_tmpTitle);
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
  public LiveData<UserLevel> getUserLevelLive(final int userId) {
    final String _sql = "SELECT * FROM user_levels WHERE userId = ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"user_levels"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, userId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfUserId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "userId");
        final int _columnIndexOfLevel = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "level");
        final int _columnIndexOfCurrentExp = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "currentExp");
        final int _columnIndexOfExpToNextLevel = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "expToNextLevel");
        final int _columnIndexOfTotalExp = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalExp");
        final int _columnIndexOfTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "title");
        final UserLevel _result;
        if (_stmt.step()) {
          _result = new UserLevel();
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _result.setId(_tmpId);
          final int _tmpUserId;
          _tmpUserId = (int) (_stmt.getLong(_columnIndexOfUserId));
          _result.setUserId(_tmpUserId);
          final int _tmpLevel;
          _tmpLevel = (int) (_stmt.getLong(_columnIndexOfLevel));
          _result.setLevel(_tmpLevel);
          final int _tmpCurrentExp;
          _tmpCurrentExp = (int) (_stmt.getLong(_columnIndexOfCurrentExp));
          _result.setCurrentExp(_tmpCurrentExp);
          final int _tmpExpToNextLevel;
          _tmpExpToNextLevel = (int) (_stmt.getLong(_columnIndexOfExpToNextLevel));
          _result.setExpToNextLevel(_tmpExpToNextLevel);
          final int _tmpTotalExp;
          _tmpTotalExp = (int) (_stmt.getLong(_columnIndexOfTotalExp));
          _result.setTotalExp(_tmpTotalExp);
          final String _tmpTitle;
          if (_stmt.isNull(_columnIndexOfTitle)) {
            _tmpTitle = null;
          } else {
            _tmpTitle = _stmt.getText(_columnIndexOfTitle);
          }
          _result.setTitle(_tmpTitle);
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
  public int getHighestLevel() {
    final String _sql = "SELECT MAX(level) FROM user_levels";
    return DBUtil.performBlocking(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
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
  public float getAverageLevel() {
    final String _sql = "SELECT AVG(level) FROM user_levels";
    return DBUtil.performBlocking(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final float _result;
        if (_stmt.step()) {
          _result = (float) (_stmt.getDouble(0));
        } else {
          _result = 0f;
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public void updateUserLevel(final int userId, final int level, final int currentExp,
      final int expToNextLevel, final int totalExp, final String title) {
    final String _sql = "UPDATE user_levels SET level = ?, currentExp = ?, expToNextLevel = ?, totalExp = ?, title = ? WHERE userId = ?";
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, level);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, currentExp);
        _argIndex = 3;
        _stmt.bindLong(_argIndex, expToNextLevel);
        _argIndex = 4;
        _stmt.bindLong(_argIndex, totalExp);
        _argIndex = 5;
        if (title == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, title);
        }
        _argIndex = 6;
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
