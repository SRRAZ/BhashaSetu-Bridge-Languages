package com.bhashasetu.app.database;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeleteOrUpdateAdapter;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteStatement;
import com.bhashasetu.app.model.Achievement;
import java.lang.Class;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation", "removal"})
public final class AchievementDao_WordDatabase_Impl implements AchievementDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<Achievement> __insertAdapterOfAchievement;

  private final EntityDeleteOrUpdateAdapter<Achievement> __deleteAdapterOfAchievement;

  private final EntityDeleteOrUpdateAdapter<Achievement> __updateAdapterOfAchievement;

  public AchievementDao_WordDatabase_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfAchievement = new EntityInsertAdapter<Achievement>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `legacy_achievements` (`id`,`title`,`description`,`type`,`progressTarget`,`currentProgress`,`unlocked`,`dateUnlocked`,`pointsValue`,`iconResId`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
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
        if (entity.getType() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getType());
        }
        statement.bindLong(5, entity.getProgressTarget());
        statement.bindLong(6, entity.getCurrentProgress());
        final int _tmp = entity.isUnlocked() ? 1 : 0;
        statement.bindLong(7, _tmp);
        if (entity.getDateUnlocked() == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, entity.getDateUnlocked());
        }
        statement.bindLong(9, entity.getPointsValue());
        statement.bindLong(10, entity.getIconResId());
      }
    };
    this.__deleteAdapterOfAchievement = new EntityDeleteOrUpdateAdapter<Achievement>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `legacy_achievements` WHERE `id` = ?";
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
        return "UPDATE OR ABORT `legacy_achievements` SET `id` = ?,`title` = ?,`description` = ?,`type` = ?,`progressTarget` = ?,`currentProgress` = ?,`unlocked` = ?,`dateUnlocked` = ?,`pointsValue` = ?,`iconResId` = ? WHERE `id` = ?";
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
        if (entity.getType() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getType());
        }
        statement.bindLong(5, entity.getProgressTarget());
        statement.bindLong(6, entity.getCurrentProgress());
        final int _tmp = entity.isUnlocked() ? 1 : 0;
        statement.bindLong(7, _tmp);
        if (entity.getDateUnlocked() == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, entity.getDateUnlocked());
        }
        statement.bindLong(9, entity.getPointsValue());
        statement.bindLong(10, entity.getIconResId());
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
    final String _sql = "SELECT * FROM legacy_achievements ORDER BY type, progressTarget";
    return __db.getInvalidationTracker().createLiveData(new String[] {"legacy_achievements"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "title");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "type");
        final int _columnIndexOfProgressTarget = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "progressTarget");
        final int _columnIndexOfCurrentProgress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "currentProgress");
        final int _columnIndexOfUnlocked = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlocked");
        final int _columnIndexOfDateUnlocked = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "dateUnlocked");
        final int _columnIndexOfPointsValue = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "pointsValue");
        final int _columnIndexOfIconResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "iconResId");
        final List<Achievement> _result = new ArrayList<Achievement>();
        while (_stmt.step()) {
          final Achievement _item;
          _item = new Achievement();
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          _item.setId(_tmpId);
          final String _tmpTitle;
          if (_stmt.isNull(_columnIndexOfTitle)) {
            _tmpTitle = null;
          } else {
            _tmpTitle = _stmt.getText(_columnIndexOfTitle);
          }
          _item.setTitle(_tmpTitle);
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          _item.setDescription(_tmpDescription);
          final String _tmpType;
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmpType = null;
          } else {
            _tmpType = _stmt.getText(_columnIndexOfType);
          }
          _item.setType(_tmpType);
          final int _tmpProgressTarget;
          _tmpProgressTarget = (int) (_stmt.getLong(_columnIndexOfProgressTarget));
          _item.setProgressTarget(_tmpProgressTarget);
          final int _tmpCurrentProgress;
          _tmpCurrentProgress = (int) (_stmt.getLong(_columnIndexOfCurrentProgress));
          _item.setCurrentProgress(_tmpCurrentProgress);
          final boolean _tmpUnlocked;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfUnlocked));
          _tmpUnlocked = _tmp != 0;
          _item.setUnlocked(_tmpUnlocked);
          final Long _tmpDateUnlocked;
          if (_stmt.isNull(_columnIndexOfDateUnlocked)) {
            _tmpDateUnlocked = null;
          } else {
            _tmpDateUnlocked = _stmt.getLong(_columnIndexOfDateUnlocked);
          }
          _item.setDateUnlocked(_tmpDateUnlocked);
          final int _tmpPointsValue;
          _tmpPointsValue = (int) (_stmt.getLong(_columnIndexOfPointsValue));
          _item.setPointsValue(_tmpPointsValue);
          final int _tmpIconResId;
          _tmpIconResId = (int) (_stmt.getLong(_columnIndexOfIconResId));
          _item.setIconResId(_tmpIconResId);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Achievement>> getUnlockedAchievements() {
    final String _sql = "SELECT * FROM legacy_achievements WHERE unlocked = 1 ORDER BY dateUnlocked DESC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"legacy_achievements"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "title");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "type");
        final int _columnIndexOfProgressTarget = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "progressTarget");
        final int _columnIndexOfCurrentProgress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "currentProgress");
        final int _columnIndexOfUnlocked = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlocked");
        final int _columnIndexOfDateUnlocked = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "dateUnlocked");
        final int _columnIndexOfPointsValue = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "pointsValue");
        final int _columnIndexOfIconResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "iconResId");
        final List<Achievement> _result = new ArrayList<Achievement>();
        while (_stmt.step()) {
          final Achievement _item;
          _item = new Achievement();
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          _item.setId(_tmpId);
          final String _tmpTitle;
          if (_stmt.isNull(_columnIndexOfTitle)) {
            _tmpTitle = null;
          } else {
            _tmpTitle = _stmt.getText(_columnIndexOfTitle);
          }
          _item.setTitle(_tmpTitle);
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          _item.setDescription(_tmpDescription);
          final String _tmpType;
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmpType = null;
          } else {
            _tmpType = _stmt.getText(_columnIndexOfType);
          }
          _item.setType(_tmpType);
          final int _tmpProgressTarget;
          _tmpProgressTarget = (int) (_stmt.getLong(_columnIndexOfProgressTarget));
          _item.setProgressTarget(_tmpProgressTarget);
          final int _tmpCurrentProgress;
          _tmpCurrentProgress = (int) (_stmt.getLong(_columnIndexOfCurrentProgress));
          _item.setCurrentProgress(_tmpCurrentProgress);
          final boolean _tmpUnlocked;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfUnlocked));
          _tmpUnlocked = _tmp != 0;
          _item.setUnlocked(_tmpUnlocked);
          final Long _tmpDateUnlocked;
          if (_stmt.isNull(_columnIndexOfDateUnlocked)) {
            _tmpDateUnlocked = null;
          } else {
            _tmpDateUnlocked = _stmt.getLong(_columnIndexOfDateUnlocked);
          }
          _item.setDateUnlocked(_tmpDateUnlocked);
          final int _tmpPointsValue;
          _tmpPointsValue = (int) (_stmt.getLong(_columnIndexOfPointsValue));
          _item.setPointsValue(_tmpPointsValue);
          final int _tmpIconResId;
          _tmpIconResId = (int) (_stmt.getLong(_columnIndexOfIconResId));
          _item.setIconResId(_tmpIconResId);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Achievement>> getAchievementsByType(final String type) {
    final String _sql = "SELECT * FROM legacy_achievements WHERE type = ? ORDER BY progressTarget";
    return __db.getInvalidationTracker().createLiveData(new String[] {"legacy_achievements"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (type == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, type);
        }
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "title");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "type");
        final int _columnIndexOfProgressTarget = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "progressTarget");
        final int _columnIndexOfCurrentProgress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "currentProgress");
        final int _columnIndexOfUnlocked = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlocked");
        final int _columnIndexOfDateUnlocked = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "dateUnlocked");
        final int _columnIndexOfPointsValue = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "pointsValue");
        final int _columnIndexOfIconResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "iconResId");
        final List<Achievement> _result = new ArrayList<Achievement>();
        while (_stmt.step()) {
          final Achievement _item;
          _item = new Achievement();
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          _item.setId(_tmpId);
          final String _tmpTitle;
          if (_stmt.isNull(_columnIndexOfTitle)) {
            _tmpTitle = null;
          } else {
            _tmpTitle = _stmt.getText(_columnIndexOfTitle);
          }
          _item.setTitle(_tmpTitle);
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          _item.setDescription(_tmpDescription);
          final String _tmpType;
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmpType = null;
          } else {
            _tmpType = _stmt.getText(_columnIndexOfType);
          }
          _item.setType(_tmpType);
          final int _tmpProgressTarget;
          _tmpProgressTarget = (int) (_stmt.getLong(_columnIndexOfProgressTarget));
          _item.setProgressTarget(_tmpProgressTarget);
          final int _tmpCurrentProgress;
          _tmpCurrentProgress = (int) (_stmt.getLong(_columnIndexOfCurrentProgress));
          _item.setCurrentProgress(_tmpCurrentProgress);
          final boolean _tmpUnlocked;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfUnlocked));
          _tmpUnlocked = _tmp != 0;
          _item.setUnlocked(_tmpUnlocked);
          final Long _tmpDateUnlocked;
          if (_stmt.isNull(_columnIndexOfDateUnlocked)) {
            _tmpDateUnlocked = null;
          } else {
            _tmpDateUnlocked = _stmt.getLong(_columnIndexOfDateUnlocked);
          }
          _item.setDateUnlocked(_tmpDateUnlocked);
          final int _tmpPointsValue;
          _tmpPointsValue = (int) (_stmt.getLong(_columnIndexOfPointsValue));
          _item.setPointsValue(_tmpPointsValue);
          final int _tmpIconResId;
          _tmpIconResId = (int) (_stmt.getLong(_columnIndexOfIconResId));
          _item.setIconResId(_tmpIconResId);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<Achievement> getAchievementById(final long id) {
    final String _sql = "SELECT * FROM legacy_achievements WHERE id = ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"legacy_achievements"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "title");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "type");
        final int _columnIndexOfProgressTarget = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "progressTarget");
        final int _columnIndexOfCurrentProgress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "currentProgress");
        final int _columnIndexOfUnlocked = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlocked");
        final int _columnIndexOfDateUnlocked = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "dateUnlocked");
        final int _columnIndexOfPointsValue = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "pointsValue");
        final int _columnIndexOfIconResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "iconResId");
        final Achievement _result;
        if (_stmt.step()) {
          _result = new Achievement();
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          _result.setId(_tmpId);
          final String _tmpTitle;
          if (_stmt.isNull(_columnIndexOfTitle)) {
            _tmpTitle = null;
          } else {
            _tmpTitle = _stmt.getText(_columnIndexOfTitle);
          }
          _result.setTitle(_tmpTitle);
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          _result.setDescription(_tmpDescription);
          final String _tmpType;
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmpType = null;
          } else {
            _tmpType = _stmt.getText(_columnIndexOfType);
          }
          _result.setType(_tmpType);
          final int _tmpProgressTarget;
          _tmpProgressTarget = (int) (_stmt.getLong(_columnIndexOfProgressTarget));
          _result.setProgressTarget(_tmpProgressTarget);
          final int _tmpCurrentProgress;
          _tmpCurrentProgress = (int) (_stmt.getLong(_columnIndexOfCurrentProgress));
          _result.setCurrentProgress(_tmpCurrentProgress);
          final boolean _tmpUnlocked;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfUnlocked));
          _tmpUnlocked = _tmp != 0;
          _result.setUnlocked(_tmpUnlocked);
          final Long _tmpDateUnlocked;
          if (_stmt.isNull(_columnIndexOfDateUnlocked)) {
            _tmpDateUnlocked = null;
          } else {
            _tmpDateUnlocked = _stmt.getLong(_columnIndexOfDateUnlocked);
          }
          _result.setDateUnlocked(_tmpDateUnlocked);
          final int _tmpPointsValue;
          _tmpPointsValue = (int) (_stmt.getLong(_columnIndexOfPointsValue));
          _result.setPointsValue(_tmpPointsValue);
          final int _tmpIconResId;
          _tmpIconResId = (int) (_stmt.getLong(_columnIndexOfIconResId));
          _result.setIconResId(_tmpIconResId);
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
  public Achievement getAchievementByIdSync(final long id) {
    final String _sql = "SELECT * FROM legacy_achievements WHERE id = ?";
    return DBUtil.performBlocking(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "title");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "type");
        final int _columnIndexOfProgressTarget = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "progressTarget");
        final int _columnIndexOfCurrentProgress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "currentProgress");
        final int _columnIndexOfUnlocked = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlocked");
        final int _columnIndexOfDateUnlocked = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "dateUnlocked");
        final int _columnIndexOfPointsValue = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "pointsValue");
        final int _columnIndexOfIconResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "iconResId");
        final Achievement _result;
        if (_stmt.step()) {
          _result = new Achievement();
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          _result.setId(_tmpId);
          final String _tmpTitle;
          if (_stmt.isNull(_columnIndexOfTitle)) {
            _tmpTitle = null;
          } else {
            _tmpTitle = _stmt.getText(_columnIndexOfTitle);
          }
          _result.setTitle(_tmpTitle);
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          _result.setDescription(_tmpDescription);
          final String _tmpType;
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmpType = null;
          } else {
            _tmpType = _stmt.getText(_columnIndexOfType);
          }
          _result.setType(_tmpType);
          final int _tmpProgressTarget;
          _tmpProgressTarget = (int) (_stmt.getLong(_columnIndexOfProgressTarget));
          _result.setProgressTarget(_tmpProgressTarget);
          final int _tmpCurrentProgress;
          _tmpCurrentProgress = (int) (_stmt.getLong(_columnIndexOfCurrentProgress));
          _result.setCurrentProgress(_tmpCurrentProgress);
          final boolean _tmpUnlocked;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfUnlocked));
          _tmpUnlocked = _tmp != 0;
          _result.setUnlocked(_tmpUnlocked);
          final Long _tmpDateUnlocked;
          if (_stmt.isNull(_columnIndexOfDateUnlocked)) {
            _tmpDateUnlocked = null;
          } else {
            _tmpDateUnlocked = _stmt.getLong(_columnIndexOfDateUnlocked);
          }
          _result.setDateUnlocked(_tmpDateUnlocked);
          final int _tmpPointsValue;
          _tmpPointsValue = (int) (_stmt.getLong(_columnIndexOfPointsValue));
          _result.setPointsValue(_tmpPointsValue);
          final int _tmpIconResId;
          _tmpIconResId = (int) (_stmt.getLong(_columnIndexOfIconResId));
          _result.setIconResId(_tmpIconResId);
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
  public LiveData<Integer> getTotalPoints() {
    final String _sql = "SELECT SUM(pointsValue) FROM legacy_achievements WHERE unlocked = 1";
    return __db.getInvalidationTracker().createLiveData(new String[] {"legacy_achievements"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final Integer _result;
        if (_stmt.step()) {
          final Integer _tmp;
          if (_stmt.isNull(0)) {
            _tmp = null;
          } else {
            _tmp = (int) (_stmt.getLong(0));
          }
          _result = _tmp;
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
  public LiveData<Integer> getUnlockedCount() {
    final String _sql = "SELECT COUNT(*) FROM legacy_achievements WHERE unlocked = 1";
    return __db.getInvalidationTracker().createLiveData(new String[] {"legacy_achievements"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final Integer _result;
        if (_stmt.step()) {
          final Integer _tmp;
          if (_stmt.isNull(0)) {
            _tmp = null;
          } else {
            _tmp = (int) (_stmt.getLong(0));
          }
          _result = _tmp;
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
  public LiveData<Integer> getTotalCount() {
    final String _sql = "SELECT COUNT(*) FROM legacy_achievements";
    return __db.getInvalidationTracker().createLiveData(new String[] {"legacy_achievements"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final Integer _result;
        if (_stmt.step()) {
          final Integer _tmp;
          if (_stmt.isNull(0)) {
            _tmp = null;
          } else {
            _tmp = (int) (_stmt.getLong(0));
          }
          _result = _tmp;
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
  public LiveData<List<Achievement>> getAchievementsByUnlockStatus(final boolean unlocked) {
    final String _sql = "SELECT * FROM legacy_achievements WHERE unlocked = ? ORDER BY type, progressTarget";
    return __db.getInvalidationTracker().createLiveData(new String[] {"legacy_achievements"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        final int _tmp = unlocked ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "title");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "type");
        final int _columnIndexOfProgressTarget = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "progressTarget");
        final int _columnIndexOfCurrentProgress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "currentProgress");
        final int _columnIndexOfUnlocked = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlocked");
        final int _columnIndexOfDateUnlocked = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "dateUnlocked");
        final int _columnIndexOfPointsValue = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "pointsValue");
        final int _columnIndexOfIconResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "iconResId");
        final List<Achievement> _result = new ArrayList<Achievement>();
        while (_stmt.step()) {
          final Achievement _item;
          _item = new Achievement();
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          _item.setId(_tmpId);
          final String _tmpTitle;
          if (_stmt.isNull(_columnIndexOfTitle)) {
            _tmpTitle = null;
          } else {
            _tmpTitle = _stmt.getText(_columnIndexOfTitle);
          }
          _item.setTitle(_tmpTitle);
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          _item.setDescription(_tmpDescription);
          final String _tmpType;
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmpType = null;
          } else {
            _tmpType = _stmt.getText(_columnIndexOfType);
          }
          _item.setType(_tmpType);
          final int _tmpProgressTarget;
          _tmpProgressTarget = (int) (_stmt.getLong(_columnIndexOfProgressTarget));
          _item.setProgressTarget(_tmpProgressTarget);
          final int _tmpCurrentProgress;
          _tmpCurrentProgress = (int) (_stmt.getLong(_columnIndexOfCurrentProgress));
          _item.setCurrentProgress(_tmpCurrentProgress);
          final boolean _tmpUnlocked;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfUnlocked));
          _tmpUnlocked = _tmp_1 != 0;
          _item.setUnlocked(_tmpUnlocked);
          final Long _tmpDateUnlocked;
          if (_stmt.isNull(_columnIndexOfDateUnlocked)) {
            _tmpDateUnlocked = null;
          } else {
            _tmpDateUnlocked = _stmt.getLong(_columnIndexOfDateUnlocked);
          }
          _item.setDateUnlocked(_tmpDateUnlocked);
          final int _tmpPointsValue;
          _tmpPointsValue = (int) (_stmt.getLong(_columnIndexOfPointsValue));
          _item.setPointsValue(_tmpPointsValue);
          final int _tmpIconResId;
          _tmpIconResId = (int) (_stmt.getLong(_columnIndexOfIconResId));
          _item.setIconResId(_tmpIconResId);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Achievement>> getAchievementsWithMinProgress(final int minProgress) {
    final String _sql = "SELECT * FROM legacy_achievements WHERE currentProgress >= ? ORDER BY currentProgress DESC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"legacy_achievements"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, minProgress);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "title");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "type");
        final int _columnIndexOfProgressTarget = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "progressTarget");
        final int _columnIndexOfCurrentProgress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "currentProgress");
        final int _columnIndexOfUnlocked = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlocked");
        final int _columnIndexOfDateUnlocked = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "dateUnlocked");
        final int _columnIndexOfPointsValue = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "pointsValue");
        final int _columnIndexOfIconResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "iconResId");
        final List<Achievement> _result = new ArrayList<Achievement>();
        while (_stmt.step()) {
          final Achievement _item;
          _item = new Achievement();
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          _item.setId(_tmpId);
          final String _tmpTitle;
          if (_stmt.isNull(_columnIndexOfTitle)) {
            _tmpTitle = null;
          } else {
            _tmpTitle = _stmt.getText(_columnIndexOfTitle);
          }
          _item.setTitle(_tmpTitle);
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          _item.setDescription(_tmpDescription);
          final String _tmpType;
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmpType = null;
          } else {
            _tmpType = _stmt.getText(_columnIndexOfType);
          }
          _item.setType(_tmpType);
          final int _tmpProgressTarget;
          _tmpProgressTarget = (int) (_stmt.getLong(_columnIndexOfProgressTarget));
          _item.setProgressTarget(_tmpProgressTarget);
          final int _tmpCurrentProgress;
          _tmpCurrentProgress = (int) (_stmt.getLong(_columnIndexOfCurrentProgress));
          _item.setCurrentProgress(_tmpCurrentProgress);
          final boolean _tmpUnlocked;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfUnlocked));
          _tmpUnlocked = _tmp != 0;
          _item.setUnlocked(_tmpUnlocked);
          final Long _tmpDateUnlocked;
          if (_stmt.isNull(_columnIndexOfDateUnlocked)) {
            _tmpDateUnlocked = null;
          } else {
            _tmpDateUnlocked = _stmt.getLong(_columnIndexOfDateUnlocked);
          }
          _item.setDateUnlocked(_tmpDateUnlocked);
          final int _tmpPointsValue;
          _tmpPointsValue = (int) (_stmt.getLong(_columnIndexOfPointsValue));
          _item.setPointsValue(_tmpPointsValue);
          final int _tmpIconResId;
          _tmpIconResId = (int) (_stmt.getLong(_columnIndexOfIconResId));
          _item.setIconResId(_tmpIconResId);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Achievement>> getRecentlyUnlocked(final long since, final int limit) {
    final String _sql = "SELECT * FROM legacy_achievements WHERE unlocked = 1 AND dateUnlocked > ? ORDER BY dateUnlocked DESC LIMIT ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"legacy_achievements"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, since);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, limit);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "title");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "type");
        final int _columnIndexOfProgressTarget = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "progressTarget");
        final int _columnIndexOfCurrentProgress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "currentProgress");
        final int _columnIndexOfUnlocked = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlocked");
        final int _columnIndexOfDateUnlocked = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "dateUnlocked");
        final int _columnIndexOfPointsValue = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "pointsValue");
        final int _columnIndexOfIconResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "iconResId");
        final List<Achievement> _result = new ArrayList<Achievement>();
        while (_stmt.step()) {
          final Achievement _item;
          _item = new Achievement();
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          _item.setId(_tmpId);
          final String _tmpTitle;
          if (_stmt.isNull(_columnIndexOfTitle)) {
            _tmpTitle = null;
          } else {
            _tmpTitle = _stmt.getText(_columnIndexOfTitle);
          }
          _item.setTitle(_tmpTitle);
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          _item.setDescription(_tmpDescription);
          final String _tmpType;
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmpType = null;
          } else {
            _tmpType = _stmt.getText(_columnIndexOfType);
          }
          _item.setType(_tmpType);
          final int _tmpProgressTarget;
          _tmpProgressTarget = (int) (_stmt.getLong(_columnIndexOfProgressTarget));
          _item.setProgressTarget(_tmpProgressTarget);
          final int _tmpCurrentProgress;
          _tmpCurrentProgress = (int) (_stmt.getLong(_columnIndexOfCurrentProgress));
          _item.setCurrentProgress(_tmpCurrentProgress);
          final boolean _tmpUnlocked;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfUnlocked));
          _tmpUnlocked = _tmp != 0;
          _item.setUnlocked(_tmpUnlocked);
          final Long _tmpDateUnlocked;
          if (_stmt.isNull(_columnIndexOfDateUnlocked)) {
            _tmpDateUnlocked = null;
          } else {
            _tmpDateUnlocked = _stmt.getLong(_columnIndexOfDateUnlocked);
          }
          _item.setDateUnlocked(_tmpDateUnlocked);
          final int _tmpPointsValue;
          _tmpPointsValue = (int) (_stmt.getLong(_columnIndexOfPointsValue));
          _item.setPointsValue(_tmpPointsValue);
          final int _tmpIconResId;
          _tmpIconResId = (int) (_stmt.getLong(_columnIndexOfIconResId));
          _item.setIconResId(_tmpIconResId);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public void deleteAll() {
    final String _sql = "DELETE FROM legacy_achievements";
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
  public void updateProgress(final long id, final int progress) {
    final String _sql = "UPDATE legacy_achievements SET currentProgress = ? WHERE id = ?";
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
  public void unlockAchievement(final long id, final long dateUnlocked) {
    final String _sql = "UPDATE legacy_achievements SET unlocked = 1, dateUnlocked = ? WHERE id = ?";
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, dateUnlocked);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, id);
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
