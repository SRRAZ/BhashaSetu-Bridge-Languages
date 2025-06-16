package com.bhashasetu.app.database;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeleteOrUpdateAdapter;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteStatement;
import com.bhashasetu.app.database.converters.BadgeTierConverter;
import com.bhashasetu.app.database.converters.DateConverter;
import com.bhashasetu.app.model.gamification.Badge;
import com.bhashasetu.app.model.gamification.BadgeTier;
import java.lang.Class;
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
public final class BadgeDao_Impl implements BadgeDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<Badge> __insertAdapterOfBadge;

  private final EntityDeleteOrUpdateAdapter<Badge> __updateAdapterOfBadge;

  public BadgeDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfBadge = new EntityInsertAdapter<Badge>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `badges` (`id`,`name`,`description`,`tier`,`imagePath`,`earned`,`earnedAt`,`achievementId`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final Badge entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getName());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getDescription());
        }
        final String _tmp = BadgeTierConverter.fromBadgeTier(entity.getTier());
        if (_tmp == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, _tmp);
        }
        if (entity.getImagePath() == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, entity.getImagePath());
        }
        final int _tmp_1 = entity.isEarned() ? 1 : 0;
        statement.bindLong(6, _tmp_1);
        final Long _tmp_2 = DateConverter.dateToTimestamp(entity.getEarnedAt());
        if (_tmp_2 == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, _tmp_2);
        }
        statement.bindLong(8, entity.getAchievementId());
      }
    };
    this.__updateAdapterOfBadge = new EntityDeleteOrUpdateAdapter<Badge>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `badges` SET `id` = ?,`name` = ?,`description` = ?,`tier` = ?,`imagePath` = ?,`earned` = ?,`earnedAt` = ?,`achievementId` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final Badge entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getName());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getDescription());
        }
        final String _tmp = BadgeTierConverter.fromBadgeTier(entity.getTier());
        if (_tmp == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, _tmp);
        }
        if (entity.getImagePath() == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, entity.getImagePath());
        }
        final int _tmp_1 = entity.isEarned() ? 1 : 0;
        statement.bindLong(6, _tmp_1);
        final Long _tmp_2 = DateConverter.dateToTimestamp(entity.getEarnedAt());
        if (_tmp_2 == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, _tmp_2);
        }
        statement.bindLong(8, entity.getAchievementId());
        statement.bindLong(9, entity.getId());
      }
    };
  }

  @Override
  public long insert(final Badge badge) {
    return DBUtil.performBlocking(__db, false, true, (_connection) -> {
      return __insertAdapterOfBadge.insertAndReturnId(_connection, badge);
    });
  }

  @Override
  public void update(final Badge badge) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __updateAdapterOfBadge.handle(_connection, badge);
      return null;
    });
  }

  @Override
  public LiveData<List<Badge>> getAllBadges() {
    final String _sql = "SELECT * FROM badges";
    return __db.getInvalidationTracker().createLiveData(new String[] {"badges"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "name");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfTier = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "tier");
        final int _columnIndexOfImagePath = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "imagePath");
        final int _columnIndexOfEarned = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "earned");
        final int _columnIndexOfEarnedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "earnedAt");
        final int _columnIndexOfAchievementId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "achievementId");
        final List<Badge> _result = new ArrayList<Badge>();
        while (_stmt.step()) {
          final Badge _item;
          _item = new Badge();
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final String _tmpName;
          if (_stmt.isNull(_columnIndexOfName)) {
            _tmpName = null;
          } else {
            _tmpName = _stmt.getText(_columnIndexOfName);
          }
          _item.setName(_tmpName);
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          _item.setDescription(_tmpDescription);
          final BadgeTier _tmpTier;
          final String _tmp;
          if (_stmt.isNull(_columnIndexOfTier)) {
            _tmp = null;
          } else {
            _tmp = _stmt.getText(_columnIndexOfTier);
          }
          _tmpTier = BadgeTierConverter.toBadgeTier(_tmp);
          _item.setTier(_tmpTier);
          final String _tmpImagePath;
          if (_stmt.isNull(_columnIndexOfImagePath)) {
            _tmpImagePath = null;
          } else {
            _tmpImagePath = _stmt.getText(_columnIndexOfImagePath);
          }
          _item.setImagePath(_tmpImagePath);
          final boolean _tmpEarned;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfEarned));
          _tmpEarned = _tmp_1 != 0;
          _item.setEarned(_tmpEarned);
          final Date _tmpEarnedAt;
          final Long _tmp_2;
          if (_stmt.isNull(_columnIndexOfEarnedAt)) {
            _tmp_2 = null;
          } else {
            _tmp_2 = _stmt.getLong(_columnIndexOfEarnedAt);
          }
          _tmpEarnedAt = DateConverter.fromTimestamp(_tmp_2);
          _item.setEarnedAt(_tmpEarnedAt);
          final int _tmpAchievementId;
          _tmpAchievementId = (int) (_stmt.getLong(_columnIndexOfAchievementId));
          _item.setAchievementId(_tmpAchievementId);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<Badge> getBadgeById(final int id) {
    final String _sql = "SELECT * FROM badges WHERE id = ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"badges"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "name");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfTier = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "tier");
        final int _columnIndexOfImagePath = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "imagePath");
        final int _columnIndexOfEarned = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "earned");
        final int _columnIndexOfEarnedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "earnedAt");
        final int _columnIndexOfAchievementId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "achievementId");
        final Badge _result;
        if (_stmt.step()) {
          _result = new Badge();
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _result.setId(_tmpId);
          final String _tmpName;
          if (_stmt.isNull(_columnIndexOfName)) {
            _tmpName = null;
          } else {
            _tmpName = _stmt.getText(_columnIndexOfName);
          }
          _result.setName(_tmpName);
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          _result.setDescription(_tmpDescription);
          final BadgeTier _tmpTier;
          final String _tmp;
          if (_stmt.isNull(_columnIndexOfTier)) {
            _tmp = null;
          } else {
            _tmp = _stmt.getText(_columnIndexOfTier);
          }
          _tmpTier = BadgeTierConverter.toBadgeTier(_tmp);
          _result.setTier(_tmpTier);
          final String _tmpImagePath;
          if (_stmt.isNull(_columnIndexOfImagePath)) {
            _tmpImagePath = null;
          } else {
            _tmpImagePath = _stmt.getText(_columnIndexOfImagePath);
          }
          _result.setImagePath(_tmpImagePath);
          final boolean _tmpEarned;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfEarned));
          _tmpEarned = _tmp_1 != 0;
          _result.setEarned(_tmpEarned);
          final Date _tmpEarnedAt;
          final Long _tmp_2;
          if (_stmt.isNull(_columnIndexOfEarnedAt)) {
            _tmp_2 = null;
          } else {
            _tmp_2 = _stmt.getLong(_columnIndexOfEarnedAt);
          }
          _tmpEarnedAt = DateConverter.fromTimestamp(_tmp_2);
          _result.setEarnedAt(_tmpEarnedAt);
          final int _tmpAchievementId;
          _tmpAchievementId = (int) (_stmt.getLong(_columnIndexOfAchievementId));
          _result.setAchievementId(_tmpAchievementId);
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
  public LiveData<List<Badge>> getEarnedBadges() {
    final String _sql = "SELECT * FROM badges WHERE earned = 1 ORDER BY earnedAt DESC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"badges"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "name");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfTier = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "tier");
        final int _columnIndexOfImagePath = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "imagePath");
        final int _columnIndexOfEarned = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "earned");
        final int _columnIndexOfEarnedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "earnedAt");
        final int _columnIndexOfAchievementId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "achievementId");
        final List<Badge> _result = new ArrayList<Badge>();
        while (_stmt.step()) {
          final Badge _item;
          _item = new Badge();
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final String _tmpName;
          if (_stmt.isNull(_columnIndexOfName)) {
            _tmpName = null;
          } else {
            _tmpName = _stmt.getText(_columnIndexOfName);
          }
          _item.setName(_tmpName);
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          _item.setDescription(_tmpDescription);
          final BadgeTier _tmpTier;
          final String _tmp;
          if (_stmt.isNull(_columnIndexOfTier)) {
            _tmp = null;
          } else {
            _tmp = _stmt.getText(_columnIndexOfTier);
          }
          _tmpTier = BadgeTierConverter.toBadgeTier(_tmp);
          _item.setTier(_tmpTier);
          final String _tmpImagePath;
          if (_stmt.isNull(_columnIndexOfImagePath)) {
            _tmpImagePath = null;
          } else {
            _tmpImagePath = _stmt.getText(_columnIndexOfImagePath);
          }
          _item.setImagePath(_tmpImagePath);
          final boolean _tmpEarned;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfEarned));
          _tmpEarned = _tmp_1 != 0;
          _item.setEarned(_tmpEarned);
          final Date _tmpEarnedAt;
          final Long _tmp_2;
          if (_stmt.isNull(_columnIndexOfEarnedAt)) {
            _tmp_2 = null;
          } else {
            _tmp_2 = _stmt.getLong(_columnIndexOfEarnedAt);
          }
          _tmpEarnedAt = DateConverter.fromTimestamp(_tmp_2);
          _item.setEarnedAt(_tmpEarnedAt);
          final int _tmpAchievementId;
          _tmpAchievementId = (int) (_stmt.getLong(_columnIndexOfAchievementId));
          _item.setAchievementId(_tmpAchievementId);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Badge>> getUnearnedBadges() {
    final String _sql = "SELECT * FROM badges WHERE earned = 0";
    return __db.getInvalidationTracker().createLiveData(new String[] {"badges"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "name");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfTier = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "tier");
        final int _columnIndexOfImagePath = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "imagePath");
        final int _columnIndexOfEarned = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "earned");
        final int _columnIndexOfEarnedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "earnedAt");
        final int _columnIndexOfAchievementId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "achievementId");
        final List<Badge> _result = new ArrayList<Badge>();
        while (_stmt.step()) {
          final Badge _item;
          _item = new Badge();
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final String _tmpName;
          if (_stmt.isNull(_columnIndexOfName)) {
            _tmpName = null;
          } else {
            _tmpName = _stmt.getText(_columnIndexOfName);
          }
          _item.setName(_tmpName);
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          _item.setDescription(_tmpDescription);
          final BadgeTier _tmpTier;
          final String _tmp;
          if (_stmt.isNull(_columnIndexOfTier)) {
            _tmp = null;
          } else {
            _tmp = _stmt.getText(_columnIndexOfTier);
          }
          _tmpTier = BadgeTierConverter.toBadgeTier(_tmp);
          _item.setTier(_tmpTier);
          final String _tmpImagePath;
          if (_stmt.isNull(_columnIndexOfImagePath)) {
            _tmpImagePath = null;
          } else {
            _tmpImagePath = _stmt.getText(_columnIndexOfImagePath);
          }
          _item.setImagePath(_tmpImagePath);
          final boolean _tmpEarned;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfEarned));
          _tmpEarned = _tmp_1 != 0;
          _item.setEarned(_tmpEarned);
          final Date _tmpEarnedAt;
          final Long _tmp_2;
          if (_stmt.isNull(_columnIndexOfEarnedAt)) {
            _tmp_2 = null;
          } else {
            _tmp_2 = _stmt.getLong(_columnIndexOfEarnedAt);
          }
          _tmpEarnedAt = DateConverter.fromTimestamp(_tmp_2);
          _item.setEarnedAt(_tmpEarnedAt);
          final int _tmpAchievementId;
          _tmpAchievementId = (int) (_stmt.getLong(_columnIndexOfAchievementId));
          _item.setAchievementId(_tmpAchievementId);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Badge>> getBadgesByTier(final BadgeTier tier) {
    final String _sql = "SELECT * FROM badges WHERE tier = ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"badges"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        final String _tmp = BadgeTierConverter.fromBadgeTier(tier);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, _tmp);
        }
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "name");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfTier = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "tier");
        final int _columnIndexOfImagePath = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "imagePath");
        final int _columnIndexOfEarned = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "earned");
        final int _columnIndexOfEarnedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "earnedAt");
        final int _columnIndexOfAchievementId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "achievementId");
        final List<Badge> _result = new ArrayList<Badge>();
        while (_stmt.step()) {
          final Badge _item;
          _item = new Badge();
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final String _tmpName;
          if (_stmt.isNull(_columnIndexOfName)) {
            _tmpName = null;
          } else {
            _tmpName = _stmt.getText(_columnIndexOfName);
          }
          _item.setName(_tmpName);
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          _item.setDescription(_tmpDescription);
          final BadgeTier _tmpTier;
          final String _tmp_1;
          if (_stmt.isNull(_columnIndexOfTier)) {
            _tmp_1 = null;
          } else {
            _tmp_1 = _stmt.getText(_columnIndexOfTier);
          }
          _tmpTier = BadgeTierConverter.toBadgeTier(_tmp_1);
          _item.setTier(_tmpTier);
          final String _tmpImagePath;
          if (_stmt.isNull(_columnIndexOfImagePath)) {
            _tmpImagePath = null;
          } else {
            _tmpImagePath = _stmt.getText(_columnIndexOfImagePath);
          }
          _item.setImagePath(_tmpImagePath);
          final boolean _tmpEarned;
          final int _tmp_2;
          _tmp_2 = (int) (_stmt.getLong(_columnIndexOfEarned));
          _tmpEarned = _tmp_2 != 0;
          _item.setEarned(_tmpEarned);
          final Date _tmpEarnedAt;
          final Long _tmp_3;
          if (_stmt.isNull(_columnIndexOfEarnedAt)) {
            _tmp_3 = null;
          } else {
            _tmp_3 = _stmt.getLong(_columnIndexOfEarnedAt);
          }
          _tmpEarnedAt = DateConverter.fromTimestamp(_tmp_3);
          _item.setEarnedAt(_tmpEarnedAt);
          final int _tmpAchievementId;
          _tmpAchievementId = (int) (_stmt.getLong(_columnIndexOfAchievementId));
          _item.setAchievementId(_tmpAchievementId);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Badge getBadgeByAchievementId(final int achievementId) {
    final String _sql = "SELECT * FROM badges WHERE achievementId = ?";
    return DBUtil.performBlocking(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, achievementId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "name");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfTier = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "tier");
        final int _columnIndexOfImagePath = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "imagePath");
        final int _columnIndexOfEarned = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "earned");
        final int _columnIndexOfEarnedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "earnedAt");
        final int _columnIndexOfAchievementId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "achievementId");
        final Badge _result;
        if (_stmt.step()) {
          _result = new Badge();
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _result.setId(_tmpId);
          final String _tmpName;
          if (_stmt.isNull(_columnIndexOfName)) {
            _tmpName = null;
          } else {
            _tmpName = _stmt.getText(_columnIndexOfName);
          }
          _result.setName(_tmpName);
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          _result.setDescription(_tmpDescription);
          final BadgeTier _tmpTier;
          final String _tmp;
          if (_stmt.isNull(_columnIndexOfTier)) {
            _tmp = null;
          } else {
            _tmp = _stmt.getText(_columnIndexOfTier);
          }
          _tmpTier = BadgeTierConverter.toBadgeTier(_tmp);
          _result.setTier(_tmpTier);
          final String _tmpImagePath;
          if (_stmt.isNull(_columnIndexOfImagePath)) {
            _tmpImagePath = null;
          } else {
            _tmpImagePath = _stmt.getText(_columnIndexOfImagePath);
          }
          _result.setImagePath(_tmpImagePath);
          final boolean _tmpEarned;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfEarned));
          _tmpEarned = _tmp_1 != 0;
          _result.setEarned(_tmpEarned);
          final Date _tmpEarnedAt;
          final Long _tmp_2;
          if (_stmt.isNull(_columnIndexOfEarnedAt)) {
            _tmp_2 = null;
          } else {
            _tmp_2 = _stmt.getLong(_columnIndexOfEarnedAt);
          }
          _tmpEarnedAt = DateConverter.fromTimestamp(_tmp_2);
          _result.setEarnedAt(_tmpEarnedAt);
          final int _tmpAchievementId;
          _tmpAchievementId = (int) (_stmt.getLong(_columnIndexOfAchievementId));
          _result.setAchievementId(_tmpAchievementId);
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
  public int getEarnedBadgeCount() {
    final String _sql = "SELECT COUNT(*) FROM badges WHERE earned = 1";
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
  public int getEarnedBadgeCountByTier(final BadgeTier tier) {
    final String _sql = "SELECT COUNT(*) FROM badges WHERE earned = 1 AND tier = ?";
    return DBUtil.performBlocking(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        final String _tmp = BadgeTierConverter.fromBadgeTier(tier);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, _tmp);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
