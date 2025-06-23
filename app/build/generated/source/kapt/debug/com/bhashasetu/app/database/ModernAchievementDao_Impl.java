package com.bhashasetu.app.database;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeleteOrUpdateAdapter;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.SQLiteStatement;
import com.bhashasetu.app.data.model.Achievement;
import java.lang.Class;
import java.lang.Double;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation", "removal"})
public final class ModernAchievementDao_Impl implements ModernAchievementDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<Achievement> __insertAdapterOfAchievement;

  private final EntityDeleteOrUpdateAdapter<Achievement> __deleteAdapterOfAchievement;

  private final EntityDeleteOrUpdateAdapter<Achievement> __updateAdapterOfAchievement;

  public ModernAchievementDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfAchievement = new EntityInsertAdapter<Achievement>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `achievements` (`id`,`titleEnglish`,`titleHindi`,`descriptionEnglish`,`descriptionHindi`,`iconResId`,`badgeImageResId`,`colorHex`,`category`,`type`,`maxProgress`,`currentProgress`,`pointsRewarded`,`isUnlocked`,`unlockedAt`,`isHidden`,`unlockMessage`,`triggerConditions`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final Achievement entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindText(1, entity.getId());
        }
        if (entity.getTitleEnglish() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getTitleEnglish());
        }
        if (entity.getTitleHindi() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getTitleHindi());
        }
        if (entity.getDescriptionEnglish() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getDescriptionEnglish());
        }
        if (entity.getDescriptionHindi() == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, entity.getDescriptionHindi());
        }
        statement.bindLong(6, entity.getIconResId());
        if (entity.getBadgeImageResId() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getBadgeImageResId());
        }
        if (entity.getColorHex() == null) {
          statement.bindNull(8);
        } else {
          statement.bindText(8, entity.getColorHex());
        }
        if (entity.getCategory() == null) {
          statement.bindNull(9);
        } else {
          statement.bindText(9, entity.getCategory());
        }
        if (entity.getType() == null) {
          statement.bindNull(10);
        } else {
          statement.bindText(10, entity.getType());
        }
        statement.bindLong(11, entity.getMaxProgress());
        statement.bindLong(12, entity.getCurrentProgress());
        statement.bindLong(13, entity.getPointsRewarded());
        final int _tmp = entity.isUnlocked() ? 1 : 0;
        statement.bindLong(14, _tmp);
        if (entity.getUnlockedAt() == null) {
          statement.bindNull(15);
        } else {
          statement.bindLong(15, entity.getUnlockedAt());
        }
        final int _tmp_1 = entity.isHidden() ? 1 : 0;
        statement.bindLong(16, _tmp_1);
        if (entity.getUnlockMessage() == null) {
          statement.bindNull(17);
        } else {
          statement.bindText(17, entity.getUnlockMessage());
        }
        if (entity.getTriggerConditions() == null) {
          statement.bindNull(18);
        } else {
          statement.bindText(18, entity.getTriggerConditions());
        }
      }
    };
    this.__deleteAdapterOfAchievement = new EntityDeleteOrUpdateAdapter<Achievement>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `achievements` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final Achievement entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindText(1, entity.getId());
        }
      }
    };
    this.__updateAdapterOfAchievement = new EntityDeleteOrUpdateAdapter<Achievement>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `achievements` SET `id` = ?,`titleEnglish` = ?,`titleHindi` = ?,`descriptionEnglish` = ?,`descriptionHindi` = ?,`iconResId` = ?,`badgeImageResId` = ?,`colorHex` = ?,`category` = ?,`type` = ?,`maxProgress` = ?,`currentProgress` = ?,`pointsRewarded` = ?,`isUnlocked` = ?,`unlockedAt` = ?,`isHidden` = ?,`unlockMessage` = ?,`triggerConditions` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final Achievement entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindText(1, entity.getId());
        }
        if (entity.getTitleEnglish() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getTitleEnglish());
        }
        if (entity.getTitleHindi() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getTitleHindi());
        }
        if (entity.getDescriptionEnglish() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getDescriptionEnglish());
        }
        if (entity.getDescriptionHindi() == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, entity.getDescriptionHindi());
        }
        statement.bindLong(6, entity.getIconResId());
        if (entity.getBadgeImageResId() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getBadgeImageResId());
        }
        if (entity.getColorHex() == null) {
          statement.bindNull(8);
        } else {
          statement.bindText(8, entity.getColorHex());
        }
        if (entity.getCategory() == null) {
          statement.bindNull(9);
        } else {
          statement.bindText(9, entity.getCategory());
        }
        if (entity.getType() == null) {
          statement.bindNull(10);
        } else {
          statement.bindText(10, entity.getType());
        }
        statement.bindLong(11, entity.getMaxProgress());
        statement.bindLong(12, entity.getCurrentProgress());
        statement.bindLong(13, entity.getPointsRewarded());
        final int _tmp = entity.isUnlocked() ? 1 : 0;
        statement.bindLong(14, _tmp);
        if (entity.getUnlockedAt() == null) {
          statement.bindNull(15);
        } else {
          statement.bindLong(15, entity.getUnlockedAt());
        }
        final int _tmp_1 = entity.isHidden() ? 1 : 0;
        statement.bindLong(16, _tmp_1);
        if (entity.getUnlockMessage() == null) {
          statement.bindNull(17);
        } else {
          statement.bindText(17, entity.getUnlockMessage());
        }
        if (entity.getTriggerConditions() == null) {
          statement.bindNull(18);
        } else {
          statement.bindText(18, entity.getTriggerConditions());
        }
        if (entity.getId() == null) {
          statement.bindNull(19);
        } else {
          statement.bindText(19, entity.getId());
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
    final String _sql = "SELECT * FROM achievements ORDER BY category, type, titleEnglish";
    return __db.getInvalidationTracker().createLiveData(new String[] {"achievements"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitleEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleEnglish");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfDescriptionEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionEnglish");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfIconResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "iconResId");
        final int _columnIndexOfBadgeImageResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "badgeImageResId");
        final int _columnIndexOfColorHex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "colorHex");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "type");
        final int _columnIndexOfMaxProgress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "maxProgress");
        final int _columnIndexOfCurrentProgress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "currentProgress");
        final int _columnIndexOfPointsRewarded = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "pointsRewarded");
        final int _columnIndexOfIsUnlocked = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isUnlocked");
        final int _columnIndexOfUnlockedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlockedAt");
        final int _columnIndexOfIsHidden = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isHidden");
        final int _columnIndexOfUnlockMessage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlockMessage");
        final int _columnIndexOfTriggerConditions = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "triggerConditions");
        final List<Achievement> _result = new ArrayList<Achievement>();
        while (_stmt.step()) {
          final Achievement _item;
          final String _tmpId;
          if (_stmt.isNull(_columnIndexOfId)) {
            _tmpId = null;
          } else {
            _tmpId = _stmt.getText(_columnIndexOfId);
          }
          final String _tmpTitleEnglish;
          if (_stmt.isNull(_columnIndexOfTitleEnglish)) {
            _tmpTitleEnglish = null;
          } else {
            _tmpTitleEnglish = _stmt.getText(_columnIndexOfTitleEnglish);
          }
          final String _tmpTitleHindi;
          if (_stmt.isNull(_columnIndexOfTitleHindi)) {
            _tmpTitleHindi = null;
          } else {
            _tmpTitleHindi = _stmt.getText(_columnIndexOfTitleHindi);
          }
          final String _tmpDescriptionEnglish;
          if (_stmt.isNull(_columnIndexOfDescriptionEnglish)) {
            _tmpDescriptionEnglish = null;
          } else {
            _tmpDescriptionEnglish = _stmt.getText(_columnIndexOfDescriptionEnglish);
          }
          final String _tmpDescriptionHindi;
          if (_stmt.isNull(_columnIndexOfDescriptionHindi)) {
            _tmpDescriptionHindi = null;
          } else {
            _tmpDescriptionHindi = _stmt.getText(_columnIndexOfDescriptionHindi);
          }
          final int _tmpIconResId;
          _tmpIconResId = (int) (_stmt.getLong(_columnIndexOfIconResId));
          final Integer _tmpBadgeImageResId;
          if (_stmt.isNull(_columnIndexOfBadgeImageResId)) {
            _tmpBadgeImageResId = null;
          } else {
            _tmpBadgeImageResId = (int) (_stmt.getLong(_columnIndexOfBadgeImageResId));
          }
          final String _tmpColorHex;
          if (_stmt.isNull(_columnIndexOfColorHex)) {
            _tmpColorHex = null;
          } else {
            _tmpColorHex = _stmt.getText(_columnIndexOfColorHex);
          }
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          final String _tmpType;
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmpType = null;
          } else {
            _tmpType = _stmt.getText(_columnIndexOfType);
          }
          final int _tmpMaxProgress;
          _tmpMaxProgress = (int) (_stmt.getLong(_columnIndexOfMaxProgress));
          final int _tmpCurrentProgress;
          _tmpCurrentProgress = (int) (_stmt.getLong(_columnIndexOfCurrentProgress));
          final int _tmpPointsRewarded;
          _tmpPointsRewarded = (int) (_stmt.getLong(_columnIndexOfPointsRewarded));
          final boolean _tmpIsUnlocked;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsUnlocked));
          _tmpIsUnlocked = _tmp != 0;
          final Long _tmpUnlockedAt;
          if (_stmt.isNull(_columnIndexOfUnlockedAt)) {
            _tmpUnlockedAt = null;
          } else {
            _tmpUnlockedAt = _stmt.getLong(_columnIndexOfUnlockedAt);
          }
          final boolean _tmpIsHidden;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsHidden));
          _tmpIsHidden = _tmp_1 != 0;
          final String _tmpUnlockMessage;
          if (_stmt.isNull(_columnIndexOfUnlockMessage)) {
            _tmpUnlockMessage = null;
          } else {
            _tmpUnlockMessage = _stmt.getText(_columnIndexOfUnlockMessage);
          }
          final String _tmpTriggerConditions;
          if (_stmt.isNull(_columnIndexOfTriggerConditions)) {
            _tmpTriggerConditions = null;
          } else {
            _tmpTriggerConditions = _stmt.getText(_columnIndexOfTriggerConditions);
          }
          _item = new Achievement(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpDescriptionEnglish,_tmpDescriptionHindi,_tmpIconResId,_tmpBadgeImageResId,_tmpColorHex,_tmpCategory,_tmpType,_tmpMaxProgress,_tmpCurrentProgress,_tmpPointsRewarded,_tmpIsUnlocked,_tmpUnlockedAt,_tmpIsHidden,_tmpUnlockMessage,_tmpTriggerConditions);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public List<Achievement> getAllAchievementsSync() {
    final String _sql = "SELECT * FROM achievements ORDER BY category, type, titleEnglish";
    return DBUtil.performBlocking(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitleEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleEnglish");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfDescriptionEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionEnglish");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfIconResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "iconResId");
        final int _columnIndexOfBadgeImageResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "badgeImageResId");
        final int _columnIndexOfColorHex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "colorHex");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "type");
        final int _columnIndexOfMaxProgress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "maxProgress");
        final int _columnIndexOfCurrentProgress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "currentProgress");
        final int _columnIndexOfPointsRewarded = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "pointsRewarded");
        final int _columnIndexOfIsUnlocked = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isUnlocked");
        final int _columnIndexOfUnlockedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlockedAt");
        final int _columnIndexOfIsHidden = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isHidden");
        final int _columnIndexOfUnlockMessage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlockMessage");
        final int _columnIndexOfTriggerConditions = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "triggerConditions");
        final List<Achievement> _result = new ArrayList<Achievement>();
        while (_stmt.step()) {
          final Achievement _item;
          final String _tmpId;
          if (_stmt.isNull(_columnIndexOfId)) {
            _tmpId = null;
          } else {
            _tmpId = _stmt.getText(_columnIndexOfId);
          }
          final String _tmpTitleEnglish;
          if (_stmt.isNull(_columnIndexOfTitleEnglish)) {
            _tmpTitleEnglish = null;
          } else {
            _tmpTitleEnglish = _stmt.getText(_columnIndexOfTitleEnglish);
          }
          final String _tmpTitleHindi;
          if (_stmt.isNull(_columnIndexOfTitleHindi)) {
            _tmpTitleHindi = null;
          } else {
            _tmpTitleHindi = _stmt.getText(_columnIndexOfTitleHindi);
          }
          final String _tmpDescriptionEnglish;
          if (_stmt.isNull(_columnIndexOfDescriptionEnglish)) {
            _tmpDescriptionEnglish = null;
          } else {
            _tmpDescriptionEnglish = _stmt.getText(_columnIndexOfDescriptionEnglish);
          }
          final String _tmpDescriptionHindi;
          if (_stmt.isNull(_columnIndexOfDescriptionHindi)) {
            _tmpDescriptionHindi = null;
          } else {
            _tmpDescriptionHindi = _stmt.getText(_columnIndexOfDescriptionHindi);
          }
          final int _tmpIconResId;
          _tmpIconResId = (int) (_stmt.getLong(_columnIndexOfIconResId));
          final Integer _tmpBadgeImageResId;
          if (_stmt.isNull(_columnIndexOfBadgeImageResId)) {
            _tmpBadgeImageResId = null;
          } else {
            _tmpBadgeImageResId = (int) (_stmt.getLong(_columnIndexOfBadgeImageResId));
          }
          final String _tmpColorHex;
          if (_stmt.isNull(_columnIndexOfColorHex)) {
            _tmpColorHex = null;
          } else {
            _tmpColorHex = _stmt.getText(_columnIndexOfColorHex);
          }
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          final String _tmpType;
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmpType = null;
          } else {
            _tmpType = _stmt.getText(_columnIndexOfType);
          }
          final int _tmpMaxProgress;
          _tmpMaxProgress = (int) (_stmt.getLong(_columnIndexOfMaxProgress));
          final int _tmpCurrentProgress;
          _tmpCurrentProgress = (int) (_stmt.getLong(_columnIndexOfCurrentProgress));
          final int _tmpPointsRewarded;
          _tmpPointsRewarded = (int) (_stmt.getLong(_columnIndexOfPointsRewarded));
          final boolean _tmpIsUnlocked;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsUnlocked));
          _tmpIsUnlocked = _tmp != 0;
          final Long _tmpUnlockedAt;
          if (_stmt.isNull(_columnIndexOfUnlockedAt)) {
            _tmpUnlockedAt = null;
          } else {
            _tmpUnlockedAt = _stmt.getLong(_columnIndexOfUnlockedAt);
          }
          final boolean _tmpIsHidden;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsHidden));
          _tmpIsHidden = _tmp_1 != 0;
          final String _tmpUnlockMessage;
          if (_stmt.isNull(_columnIndexOfUnlockMessage)) {
            _tmpUnlockMessage = null;
          } else {
            _tmpUnlockMessage = _stmt.getText(_columnIndexOfUnlockMessage);
          }
          final String _tmpTriggerConditions;
          if (_stmt.isNull(_columnIndexOfTriggerConditions)) {
            _tmpTriggerConditions = null;
          } else {
            _tmpTriggerConditions = _stmt.getText(_columnIndexOfTriggerConditions);
          }
          _item = new Achievement(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpDescriptionEnglish,_tmpDescriptionHindi,_tmpIconResId,_tmpBadgeImageResId,_tmpColorHex,_tmpCategory,_tmpType,_tmpMaxProgress,_tmpCurrentProgress,_tmpPointsRewarded,_tmpIsUnlocked,_tmpUnlockedAt,_tmpIsHidden,_tmpUnlockMessage,_tmpTriggerConditions);
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
    final String _sql = "SELECT * FROM achievements WHERE isUnlocked = 1 ORDER BY unlockedAt DESC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"achievements"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitleEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleEnglish");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfDescriptionEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionEnglish");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfIconResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "iconResId");
        final int _columnIndexOfBadgeImageResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "badgeImageResId");
        final int _columnIndexOfColorHex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "colorHex");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "type");
        final int _columnIndexOfMaxProgress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "maxProgress");
        final int _columnIndexOfCurrentProgress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "currentProgress");
        final int _columnIndexOfPointsRewarded = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "pointsRewarded");
        final int _columnIndexOfIsUnlocked = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isUnlocked");
        final int _columnIndexOfUnlockedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlockedAt");
        final int _columnIndexOfIsHidden = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isHidden");
        final int _columnIndexOfUnlockMessage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlockMessage");
        final int _columnIndexOfTriggerConditions = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "triggerConditions");
        final List<Achievement> _result = new ArrayList<Achievement>();
        while (_stmt.step()) {
          final Achievement _item;
          final String _tmpId;
          if (_stmt.isNull(_columnIndexOfId)) {
            _tmpId = null;
          } else {
            _tmpId = _stmt.getText(_columnIndexOfId);
          }
          final String _tmpTitleEnglish;
          if (_stmt.isNull(_columnIndexOfTitleEnglish)) {
            _tmpTitleEnglish = null;
          } else {
            _tmpTitleEnglish = _stmt.getText(_columnIndexOfTitleEnglish);
          }
          final String _tmpTitleHindi;
          if (_stmt.isNull(_columnIndexOfTitleHindi)) {
            _tmpTitleHindi = null;
          } else {
            _tmpTitleHindi = _stmt.getText(_columnIndexOfTitleHindi);
          }
          final String _tmpDescriptionEnglish;
          if (_stmt.isNull(_columnIndexOfDescriptionEnglish)) {
            _tmpDescriptionEnglish = null;
          } else {
            _tmpDescriptionEnglish = _stmt.getText(_columnIndexOfDescriptionEnglish);
          }
          final String _tmpDescriptionHindi;
          if (_stmt.isNull(_columnIndexOfDescriptionHindi)) {
            _tmpDescriptionHindi = null;
          } else {
            _tmpDescriptionHindi = _stmt.getText(_columnIndexOfDescriptionHindi);
          }
          final int _tmpIconResId;
          _tmpIconResId = (int) (_stmt.getLong(_columnIndexOfIconResId));
          final Integer _tmpBadgeImageResId;
          if (_stmt.isNull(_columnIndexOfBadgeImageResId)) {
            _tmpBadgeImageResId = null;
          } else {
            _tmpBadgeImageResId = (int) (_stmt.getLong(_columnIndexOfBadgeImageResId));
          }
          final String _tmpColorHex;
          if (_stmt.isNull(_columnIndexOfColorHex)) {
            _tmpColorHex = null;
          } else {
            _tmpColorHex = _stmt.getText(_columnIndexOfColorHex);
          }
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          final String _tmpType;
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmpType = null;
          } else {
            _tmpType = _stmt.getText(_columnIndexOfType);
          }
          final int _tmpMaxProgress;
          _tmpMaxProgress = (int) (_stmt.getLong(_columnIndexOfMaxProgress));
          final int _tmpCurrentProgress;
          _tmpCurrentProgress = (int) (_stmt.getLong(_columnIndexOfCurrentProgress));
          final int _tmpPointsRewarded;
          _tmpPointsRewarded = (int) (_stmt.getLong(_columnIndexOfPointsRewarded));
          final boolean _tmpIsUnlocked;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsUnlocked));
          _tmpIsUnlocked = _tmp != 0;
          final Long _tmpUnlockedAt;
          if (_stmt.isNull(_columnIndexOfUnlockedAt)) {
            _tmpUnlockedAt = null;
          } else {
            _tmpUnlockedAt = _stmt.getLong(_columnIndexOfUnlockedAt);
          }
          final boolean _tmpIsHidden;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsHidden));
          _tmpIsHidden = _tmp_1 != 0;
          final String _tmpUnlockMessage;
          if (_stmt.isNull(_columnIndexOfUnlockMessage)) {
            _tmpUnlockMessage = null;
          } else {
            _tmpUnlockMessage = _stmt.getText(_columnIndexOfUnlockMessage);
          }
          final String _tmpTriggerConditions;
          if (_stmt.isNull(_columnIndexOfTriggerConditions)) {
            _tmpTriggerConditions = null;
          } else {
            _tmpTriggerConditions = _stmt.getText(_columnIndexOfTriggerConditions);
          }
          _item = new Achievement(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpDescriptionEnglish,_tmpDescriptionHindi,_tmpIconResId,_tmpBadgeImageResId,_tmpColorHex,_tmpCategory,_tmpType,_tmpMaxProgress,_tmpCurrentProgress,_tmpPointsRewarded,_tmpIsUnlocked,_tmpUnlockedAt,_tmpIsHidden,_tmpUnlockMessage,_tmpTriggerConditions);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Achievement>> getLockedVisibleAchievements() {
    final String _sql = "SELECT * FROM achievements WHERE isUnlocked = 0 AND isHidden = 0 ORDER BY category, titleEnglish";
    return __db.getInvalidationTracker().createLiveData(new String[] {"achievements"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitleEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleEnglish");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfDescriptionEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionEnglish");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfIconResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "iconResId");
        final int _columnIndexOfBadgeImageResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "badgeImageResId");
        final int _columnIndexOfColorHex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "colorHex");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "type");
        final int _columnIndexOfMaxProgress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "maxProgress");
        final int _columnIndexOfCurrentProgress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "currentProgress");
        final int _columnIndexOfPointsRewarded = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "pointsRewarded");
        final int _columnIndexOfIsUnlocked = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isUnlocked");
        final int _columnIndexOfUnlockedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlockedAt");
        final int _columnIndexOfIsHidden = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isHidden");
        final int _columnIndexOfUnlockMessage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlockMessage");
        final int _columnIndexOfTriggerConditions = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "triggerConditions");
        final List<Achievement> _result = new ArrayList<Achievement>();
        while (_stmt.step()) {
          final Achievement _item;
          final String _tmpId;
          if (_stmt.isNull(_columnIndexOfId)) {
            _tmpId = null;
          } else {
            _tmpId = _stmt.getText(_columnIndexOfId);
          }
          final String _tmpTitleEnglish;
          if (_stmt.isNull(_columnIndexOfTitleEnglish)) {
            _tmpTitleEnglish = null;
          } else {
            _tmpTitleEnglish = _stmt.getText(_columnIndexOfTitleEnglish);
          }
          final String _tmpTitleHindi;
          if (_stmt.isNull(_columnIndexOfTitleHindi)) {
            _tmpTitleHindi = null;
          } else {
            _tmpTitleHindi = _stmt.getText(_columnIndexOfTitleHindi);
          }
          final String _tmpDescriptionEnglish;
          if (_stmt.isNull(_columnIndexOfDescriptionEnglish)) {
            _tmpDescriptionEnglish = null;
          } else {
            _tmpDescriptionEnglish = _stmt.getText(_columnIndexOfDescriptionEnglish);
          }
          final String _tmpDescriptionHindi;
          if (_stmt.isNull(_columnIndexOfDescriptionHindi)) {
            _tmpDescriptionHindi = null;
          } else {
            _tmpDescriptionHindi = _stmt.getText(_columnIndexOfDescriptionHindi);
          }
          final int _tmpIconResId;
          _tmpIconResId = (int) (_stmt.getLong(_columnIndexOfIconResId));
          final Integer _tmpBadgeImageResId;
          if (_stmt.isNull(_columnIndexOfBadgeImageResId)) {
            _tmpBadgeImageResId = null;
          } else {
            _tmpBadgeImageResId = (int) (_stmt.getLong(_columnIndexOfBadgeImageResId));
          }
          final String _tmpColorHex;
          if (_stmt.isNull(_columnIndexOfColorHex)) {
            _tmpColorHex = null;
          } else {
            _tmpColorHex = _stmt.getText(_columnIndexOfColorHex);
          }
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          final String _tmpType;
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmpType = null;
          } else {
            _tmpType = _stmt.getText(_columnIndexOfType);
          }
          final int _tmpMaxProgress;
          _tmpMaxProgress = (int) (_stmt.getLong(_columnIndexOfMaxProgress));
          final int _tmpCurrentProgress;
          _tmpCurrentProgress = (int) (_stmt.getLong(_columnIndexOfCurrentProgress));
          final int _tmpPointsRewarded;
          _tmpPointsRewarded = (int) (_stmt.getLong(_columnIndexOfPointsRewarded));
          final boolean _tmpIsUnlocked;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsUnlocked));
          _tmpIsUnlocked = _tmp != 0;
          final Long _tmpUnlockedAt;
          if (_stmt.isNull(_columnIndexOfUnlockedAt)) {
            _tmpUnlockedAt = null;
          } else {
            _tmpUnlockedAt = _stmt.getLong(_columnIndexOfUnlockedAt);
          }
          final boolean _tmpIsHidden;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsHidden));
          _tmpIsHidden = _tmp_1 != 0;
          final String _tmpUnlockMessage;
          if (_stmt.isNull(_columnIndexOfUnlockMessage)) {
            _tmpUnlockMessage = null;
          } else {
            _tmpUnlockMessage = _stmt.getText(_columnIndexOfUnlockMessage);
          }
          final String _tmpTriggerConditions;
          if (_stmt.isNull(_columnIndexOfTriggerConditions)) {
            _tmpTriggerConditions = null;
          } else {
            _tmpTriggerConditions = _stmt.getText(_columnIndexOfTriggerConditions);
          }
          _item = new Achievement(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpDescriptionEnglish,_tmpDescriptionHindi,_tmpIconResId,_tmpBadgeImageResId,_tmpColorHex,_tmpCategory,_tmpType,_tmpMaxProgress,_tmpCurrentProgress,_tmpPointsRewarded,_tmpIsUnlocked,_tmpUnlockedAt,_tmpIsHidden,_tmpUnlockMessage,_tmpTriggerConditions);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Achievement>> getVisibleAchievements() {
    final String _sql = "SELECT * FROM achievements WHERE isHidden = 0 ORDER BY category, type, titleEnglish";
    return __db.getInvalidationTracker().createLiveData(new String[] {"achievements"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitleEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleEnglish");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfDescriptionEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionEnglish");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfIconResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "iconResId");
        final int _columnIndexOfBadgeImageResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "badgeImageResId");
        final int _columnIndexOfColorHex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "colorHex");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "type");
        final int _columnIndexOfMaxProgress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "maxProgress");
        final int _columnIndexOfCurrentProgress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "currentProgress");
        final int _columnIndexOfPointsRewarded = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "pointsRewarded");
        final int _columnIndexOfIsUnlocked = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isUnlocked");
        final int _columnIndexOfUnlockedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlockedAt");
        final int _columnIndexOfIsHidden = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isHidden");
        final int _columnIndexOfUnlockMessage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlockMessage");
        final int _columnIndexOfTriggerConditions = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "triggerConditions");
        final List<Achievement> _result = new ArrayList<Achievement>();
        while (_stmt.step()) {
          final Achievement _item;
          final String _tmpId;
          if (_stmt.isNull(_columnIndexOfId)) {
            _tmpId = null;
          } else {
            _tmpId = _stmt.getText(_columnIndexOfId);
          }
          final String _tmpTitleEnglish;
          if (_stmt.isNull(_columnIndexOfTitleEnglish)) {
            _tmpTitleEnglish = null;
          } else {
            _tmpTitleEnglish = _stmt.getText(_columnIndexOfTitleEnglish);
          }
          final String _tmpTitleHindi;
          if (_stmt.isNull(_columnIndexOfTitleHindi)) {
            _tmpTitleHindi = null;
          } else {
            _tmpTitleHindi = _stmt.getText(_columnIndexOfTitleHindi);
          }
          final String _tmpDescriptionEnglish;
          if (_stmt.isNull(_columnIndexOfDescriptionEnglish)) {
            _tmpDescriptionEnglish = null;
          } else {
            _tmpDescriptionEnglish = _stmt.getText(_columnIndexOfDescriptionEnglish);
          }
          final String _tmpDescriptionHindi;
          if (_stmt.isNull(_columnIndexOfDescriptionHindi)) {
            _tmpDescriptionHindi = null;
          } else {
            _tmpDescriptionHindi = _stmt.getText(_columnIndexOfDescriptionHindi);
          }
          final int _tmpIconResId;
          _tmpIconResId = (int) (_stmt.getLong(_columnIndexOfIconResId));
          final Integer _tmpBadgeImageResId;
          if (_stmt.isNull(_columnIndexOfBadgeImageResId)) {
            _tmpBadgeImageResId = null;
          } else {
            _tmpBadgeImageResId = (int) (_stmt.getLong(_columnIndexOfBadgeImageResId));
          }
          final String _tmpColorHex;
          if (_stmt.isNull(_columnIndexOfColorHex)) {
            _tmpColorHex = null;
          } else {
            _tmpColorHex = _stmt.getText(_columnIndexOfColorHex);
          }
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          final String _tmpType;
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmpType = null;
          } else {
            _tmpType = _stmt.getText(_columnIndexOfType);
          }
          final int _tmpMaxProgress;
          _tmpMaxProgress = (int) (_stmt.getLong(_columnIndexOfMaxProgress));
          final int _tmpCurrentProgress;
          _tmpCurrentProgress = (int) (_stmt.getLong(_columnIndexOfCurrentProgress));
          final int _tmpPointsRewarded;
          _tmpPointsRewarded = (int) (_stmt.getLong(_columnIndexOfPointsRewarded));
          final boolean _tmpIsUnlocked;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsUnlocked));
          _tmpIsUnlocked = _tmp != 0;
          final Long _tmpUnlockedAt;
          if (_stmt.isNull(_columnIndexOfUnlockedAt)) {
            _tmpUnlockedAt = null;
          } else {
            _tmpUnlockedAt = _stmt.getLong(_columnIndexOfUnlockedAt);
          }
          final boolean _tmpIsHidden;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsHidden));
          _tmpIsHidden = _tmp_1 != 0;
          final String _tmpUnlockMessage;
          if (_stmt.isNull(_columnIndexOfUnlockMessage)) {
            _tmpUnlockMessage = null;
          } else {
            _tmpUnlockMessage = _stmt.getText(_columnIndexOfUnlockMessage);
          }
          final String _tmpTriggerConditions;
          if (_stmt.isNull(_columnIndexOfTriggerConditions)) {
            _tmpTriggerConditions = null;
          } else {
            _tmpTriggerConditions = _stmt.getText(_columnIndexOfTriggerConditions);
          }
          _item = new Achievement(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpDescriptionEnglish,_tmpDescriptionHindi,_tmpIconResId,_tmpBadgeImageResId,_tmpColorHex,_tmpCategory,_tmpType,_tmpMaxProgress,_tmpCurrentProgress,_tmpPointsRewarded,_tmpIsUnlocked,_tmpUnlockedAt,_tmpIsHidden,_tmpUnlockMessage,_tmpTriggerConditions);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Achievement>> getAchievementsByCategory(final String category) {
    final String _sql = "SELECT * FROM achievements WHERE category = ? ORDER BY type, titleEnglish";
    return __db.getInvalidationTracker().createLiveData(new String[] {"achievements"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (category == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, category);
        }
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitleEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleEnglish");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfDescriptionEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionEnglish");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfIconResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "iconResId");
        final int _columnIndexOfBadgeImageResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "badgeImageResId");
        final int _columnIndexOfColorHex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "colorHex");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "type");
        final int _columnIndexOfMaxProgress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "maxProgress");
        final int _columnIndexOfCurrentProgress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "currentProgress");
        final int _columnIndexOfPointsRewarded = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "pointsRewarded");
        final int _columnIndexOfIsUnlocked = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isUnlocked");
        final int _columnIndexOfUnlockedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlockedAt");
        final int _columnIndexOfIsHidden = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isHidden");
        final int _columnIndexOfUnlockMessage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlockMessage");
        final int _columnIndexOfTriggerConditions = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "triggerConditions");
        final List<Achievement> _result = new ArrayList<Achievement>();
        while (_stmt.step()) {
          final Achievement _item;
          final String _tmpId;
          if (_stmt.isNull(_columnIndexOfId)) {
            _tmpId = null;
          } else {
            _tmpId = _stmt.getText(_columnIndexOfId);
          }
          final String _tmpTitleEnglish;
          if (_stmt.isNull(_columnIndexOfTitleEnglish)) {
            _tmpTitleEnglish = null;
          } else {
            _tmpTitleEnglish = _stmt.getText(_columnIndexOfTitleEnglish);
          }
          final String _tmpTitleHindi;
          if (_stmt.isNull(_columnIndexOfTitleHindi)) {
            _tmpTitleHindi = null;
          } else {
            _tmpTitleHindi = _stmt.getText(_columnIndexOfTitleHindi);
          }
          final String _tmpDescriptionEnglish;
          if (_stmt.isNull(_columnIndexOfDescriptionEnglish)) {
            _tmpDescriptionEnglish = null;
          } else {
            _tmpDescriptionEnglish = _stmt.getText(_columnIndexOfDescriptionEnglish);
          }
          final String _tmpDescriptionHindi;
          if (_stmt.isNull(_columnIndexOfDescriptionHindi)) {
            _tmpDescriptionHindi = null;
          } else {
            _tmpDescriptionHindi = _stmt.getText(_columnIndexOfDescriptionHindi);
          }
          final int _tmpIconResId;
          _tmpIconResId = (int) (_stmt.getLong(_columnIndexOfIconResId));
          final Integer _tmpBadgeImageResId;
          if (_stmt.isNull(_columnIndexOfBadgeImageResId)) {
            _tmpBadgeImageResId = null;
          } else {
            _tmpBadgeImageResId = (int) (_stmt.getLong(_columnIndexOfBadgeImageResId));
          }
          final String _tmpColorHex;
          if (_stmt.isNull(_columnIndexOfColorHex)) {
            _tmpColorHex = null;
          } else {
            _tmpColorHex = _stmt.getText(_columnIndexOfColorHex);
          }
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          final String _tmpType;
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmpType = null;
          } else {
            _tmpType = _stmt.getText(_columnIndexOfType);
          }
          final int _tmpMaxProgress;
          _tmpMaxProgress = (int) (_stmt.getLong(_columnIndexOfMaxProgress));
          final int _tmpCurrentProgress;
          _tmpCurrentProgress = (int) (_stmt.getLong(_columnIndexOfCurrentProgress));
          final int _tmpPointsRewarded;
          _tmpPointsRewarded = (int) (_stmt.getLong(_columnIndexOfPointsRewarded));
          final boolean _tmpIsUnlocked;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsUnlocked));
          _tmpIsUnlocked = _tmp != 0;
          final Long _tmpUnlockedAt;
          if (_stmt.isNull(_columnIndexOfUnlockedAt)) {
            _tmpUnlockedAt = null;
          } else {
            _tmpUnlockedAt = _stmt.getLong(_columnIndexOfUnlockedAt);
          }
          final boolean _tmpIsHidden;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsHidden));
          _tmpIsHidden = _tmp_1 != 0;
          final String _tmpUnlockMessage;
          if (_stmt.isNull(_columnIndexOfUnlockMessage)) {
            _tmpUnlockMessage = null;
          } else {
            _tmpUnlockMessage = _stmt.getText(_columnIndexOfUnlockMessage);
          }
          final String _tmpTriggerConditions;
          if (_stmt.isNull(_columnIndexOfTriggerConditions)) {
            _tmpTriggerConditions = null;
          } else {
            _tmpTriggerConditions = _stmt.getText(_columnIndexOfTriggerConditions);
          }
          _item = new Achievement(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpDescriptionEnglish,_tmpDescriptionHindi,_tmpIconResId,_tmpBadgeImageResId,_tmpColorHex,_tmpCategory,_tmpType,_tmpMaxProgress,_tmpCurrentProgress,_tmpPointsRewarded,_tmpIsUnlocked,_tmpUnlockedAt,_tmpIsHidden,_tmpUnlockMessage,_tmpTriggerConditions);
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
    final String _sql = "SELECT * FROM achievements WHERE type = ? ORDER BY category, titleEnglish";
    return __db.getInvalidationTracker().createLiveData(new String[] {"achievements"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (type == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, type);
        }
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitleEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleEnglish");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfDescriptionEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionEnglish");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfIconResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "iconResId");
        final int _columnIndexOfBadgeImageResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "badgeImageResId");
        final int _columnIndexOfColorHex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "colorHex");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "type");
        final int _columnIndexOfMaxProgress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "maxProgress");
        final int _columnIndexOfCurrentProgress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "currentProgress");
        final int _columnIndexOfPointsRewarded = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "pointsRewarded");
        final int _columnIndexOfIsUnlocked = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isUnlocked");
        final int _columnIndexOfUnlockedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlockedAt");
        final int _columnIndexOfIsHidden = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isHidden");
        final int _columnIndexOfUnlockMessage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlockMessage");
        final int _columnIndexOfTriggerConditions = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "triggerConditions");
        final List<Achievement> _result = new ArrayList<Achievement>();
        while (_stmt.step()) {
          final Achievement _item;
          final String _tmpId;
          if (_stmt.isNull(_columnIndexOfId)) {
            _tmpId = null;
          } else {
            _tmpId = _stmt.getText(_columnIndexOfId);
          }
          final String _tmpTitleEnglish;
          if (_stmt.isNull(_columnIndexOfTitleEnglish)) {
            _tmpTitleEnglish = null;
          } else {
            _tmpTitleEnglish = _stmt.getText(_columnIndexOfTitleEnglish);
          }
          final String _tmpTitleHindi;
          if (_stmt.isNull(_columnIndexOfTitleHindi)) {
            _tmpTitleHindi = null;
          } else {
            _tmpTitleHindi = _stmt.getText(_columnIndexOfTitleHindi);
          }
          final String _tmpDescriptionEnglish;
          if (_stmt.isNull(_columnIndexOfDescriptionEnglish)) {
            _tmpDescriptionEnglish = null;
          } else {
            _tmpDescriptionEnglish = _stmt.getText(_columnIndexOfDescriptionEnglish);
          }
          final String _tmpDescriptionHindi;
          if (_stmt.isNull(_columnIndexOfDescriptionHindi)) {
            _tmpDescriptionHindi = null;
          } else {
            _tmpDescriptionHindi = _stmt.getText(_columnIndexOfDescriptionHindi);
          }
          final int _tmpIconResId;
          _tmpIconResId = (int) (_stmt.getLong(_columnIndexOfIconResId));
          final Integer _tmpBadgeImageResId;
          if (_stmt.isNull(_columnIndexOfBadgeImageResId)) {
            _tmpBadgeImageResId = null;
          } else {
            _tmpBadgeImageResId = (int) (_stmt.getLong(_columnIndexOfBadgeImageResId));
          }
          final String _tmpColorHex;
          if (_stmt.isNull(_columnIndexOfColorHex)) {
            _tmpColorHex = null;
          } else {
            _tmpColorHex = _stmt.getText(_columnIndexOfColorHex);
          }
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          final String _tmpType;
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmpType = null;
          } else {
            _tmpType = _stmt.getText(_columnIndexOfType);
          }
          final int _tmpMaxProgress;
          _tmpMaxProgress = (int) (_stmt.getLong(_columnIndexOfMaxProgress));
          final int _tmpCurrentProgress;
          _tmpCurrentProgress = (int) (_stmt.getLong(_columnIndexOfCurrentProgress));
          final int _tmpPointsRewarded;
          _tmpPointsRewarded = (int) (_stmt.getLong(_columnIndexOfPointsRewarded));
          final boolean _tmpIsUnlocked;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsUnlocked));
          _tmpIsUnlocked = _tmp != 0;
          final Long _tmpUnlockedAt;
          if (_stmt.isNull(_columnIndexOfUnlockedAt)) {
            _tmpUnlockedAt = null;
          } else {
            _tmpUnlockedAt = _stmt.getLong(_columnIndexOfUnlockedAt);
          }
          final boolean _tmpIsHidden;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsHidden));
          _tmpIsHidden = _tmp_1 != 0;
          final String _tmpUnlockMessage;
          if (_stmt.isNull(_columnIndexOfUnlockMessage)) {
            _tmpUnlockMessage = null;
          } else {
            _tmpUnlockMessage = _stmt.getText(_columnIndexOfUnlockMessage);
          }
          final String _tmpTriggerConditions;
          if (_stmt.isNull(_columnIndexOfTriggerConditions)) {
            _tmpTriggerConditions = null;
          } else {
            _tmpTriggerConditions = _stmt.getText(_columnIndexOfTriggerConditions);
          }
          _item = new Achievement(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpDescriptionEnglish,_tmpDescriptionHindi,_tmpIconResId,_tmpBadgeImageResId,_tmpColorHex,_tmpCategory,_tmpType,_tmpMaxProgress,_tmpCurrentProgress,_tmpPointsRewarded,_tmpIsUnlocked,_tmpUnlockedAt,_tmpIsHidden,_tmpUnlockMessage,_tmpTriggerConditions);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Achievement>> getAchievementsByCategoryAndType(final String category,
      final String type) {
    final String _sql = "SELECT * FROM achievements WHERE category = ? AND type = ? ORDER BY titleEnglish";
    return __db.getInvalidationTracker().createLiveData(new String[] {"achievements"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (category == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, category);
        }
        _argIndex = 2;
        if (type == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, type);
        }
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitleEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleEnglish");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfDescriptionEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionEnglish");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfIconResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "iconResId");
        final int _columnIndexOfBadgeImageResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "badgeImageResId");
        final int _columnIndexOfColorHex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "colorHex");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "type");
        final int _columnIndexOfMaxProgress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "maxProgress");
        final int _columnIndexOfCurrentProgress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "currentProgress");
        final int _columnIndexOfPointsRewarded = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "pointsRewarded");
        final int _columnIndexOfIsUnlocked = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isUnlocked");
        final int _columnIndexOfUnlockedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlockedAt");
        final int _columnIndexOfIsHidden = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isHidden");
        final int _columnIndexOfUnlockMessage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlockMessage");
        final int _columnIndexOfTriggerConditions = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "triggerConditions");
        final List<Achievement> _result = new ArrayList<Achievement>();
        while (_stmt.step()) {
          final Achievement _item;
          final String _tmpId;
          if (_stmt.isNull(_columnIndexOfId)) {
            _tmpId = null;
          } else {
            _tmpId = _stmt.getText(_columnIndexOfId);
          }
          final String _tmpTitleEnglish;
          if (_stmt.isNull(_columnIndexOfTitleEnglish)) {
            _tmpTitleEnglish = null;
          } else {
            _tmpTitleEnglish = _stmt.getText(_columnIndexOfTitleEnglish);
          }
          final String _tmpTitleHindi;
          if (_stmt.isNull(_columnIndexOfTitleHindi)) {
            _tmpTitleHindi = null;
          } else {
            _tmpTitleHindi = _stmt.getText(_columnIndexOfTitleHindi);
          }
          final String _tmpDescriptionEnglish;
          if (_stmt.isNull(_columnIndexOfDescriptionEnglish)) {
            _tmpDescriptionEnglish = null;
          } else {
            _tmpDescriptionEnglish = _stmt.getText(_columnIndexOfDescriptionEnglish);
          }
          final String _tmpDescriptionHindi;
          if (_stmt.isNull(_columnIndexOfDescriptionHindi)) {
            _tmpDescriptionHindi = null;
          } else {
            _tmpDescriptionHindi = _stmt.getText(_columnIndexOfDescriptionHindi);
          }
          final int _tmpIconResId;
          _tmpIconResId = (int) (_stmt.getLong(_columnIndexOfIconResId));
          final Integer _tmpBadgeImageResId;
          if (_stmt.isNull(_columnIndexOfBadgeImageResId)) {
            _tmpBadgeImageResId = null;
          } else {
            _tmpBadgeImageResId = (int) (_stmt.getLong(_columnIndexOfBadgeImageResId));
          }
          final String _tmpColorHex;
          if (_stmt.isNull(_columnIndexOfColorHex)) {
            _tmpColorHex = null;
          } else {
            _tmpColorHex = _stmt.getText(_columnIndexOfColorHex);
          }
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          final String _tmpType;
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmpType = null;
          } else {
            _tmpType = _stmt.getText(_columnIndexOfType);
          }
          final int _tmpMaxProgress;
          _tmpMaxProgress = (int) (_stmt.getLong(_columnIndexOfMaxProgress));
          final int _tmpCurrentProgress;
          _tmpCurrentProgress = (int) (_stmt.getLong(_columnIndexOfCurrentProgress));
          final int _tmpPointsRewarded;
          _tmpPointsRewarded = (int) (_stmt.getLong(_columnIndexOfPointsRewarded));
          final boolean _tmpIsUnlocked;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsUnlocked));
          _tmpIsUnlocked = _tmp != 0;
          final Long _tmpUnlockedAt;
          if (_stmt.isNull(_columnIndexOfUnlockedAt)) {
            _tmpUnlockedAt = null;
          } else {
            _tmpUnlockedAt = _stmt.getLong(_columnIndexOfUnlockedAt);
          }
          final boolean _tmpIsHidden;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsHidden));
          _tmpIsHidden = _tmp_1 != 0;
          final String _tmpUnlockMessage;
          if (_stmt.isNull(_columnIndexOfUnlockMessage)) {
            _tmpUnlockMessage = null;
          } else {
            _tmpUnlockMessage = _stmt.getText(_columnIndexOfUnlockMessage);
          }
          final String _tmpTriggerConditions;
          if (_stmt.isNull(_columnIndexOfTriggerConditions)) {
            _tmpTriggerConditions = null;
          } else {
            _tmpTriggerConditions = _stmt.getText(_columnIndexOfTriggerConditions);
          }
          _item = new Achievement(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpDescriptionEnglish,_tmpDescriptionHindi,_tmpIconResId,_tmpBadgeImageResId,_tmpColorHex,_tmpCategory,_tmpType,_tmpMaxProgress,_tmpCurrentProgress,_tmpPointsRewarded,_tmpIsUnlocked,_tmpUnlockedAt,_tmpIsHidden,_tmpUnlockMessage,_tmpTriggerConditions);
          _result.add(_item);
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
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitleEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleEnglish");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfDescriptionEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionEnglish");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfIconResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "iconResId");
        final int _columnIndexOfBadgeImageResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "badgeImageResId");
        final int _columnIndexOfColorHex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "colorHex");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "type");
        final int _columnIndexOfMaxProgress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "maxProgress");
        final int _columnIndexOfCurrentProgress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "currentProgress");
        final int _columnIndexOfPointsRewarded = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "pointsRewarded");
        final int _columnIndexOfIsUnlocked = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isUnlocked");
        final int _columnIndexOfUnlockedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlockedAt");
        final int _columnIndexOfIsHidden = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isHidden");
        final int _columnIndexOfUnlockMessage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlockMessage");
        final int _columnIndexOfTriggerConditions = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "triggerConditions");
        final Achievement _result;
        if (_stmt.step()) {
          final String _tmpId;
          if (_stmt.isNull(_columnIndexOfId)) {
            _tmpId = null;
          } else {
            _tmpId = _stmt.getText(_columnIndexOfId);
          }
          final String _tmpTitleEnglish;
          if (_stmt.isNull(_columnIndexOfTitleEnglish)) {
            _tmpTitleEnglish = null;
          } else {
            _tmpTitleEnglish = _stmt.getText(_columnIndexOfTitleEnglish);
          }
          final String _tmpTitleHindi;
          if (_stmt.isNull(_columnIndexOfTitleHindi)) {
            _tmpTitleHindi = null;
          } else {
            _tmpTitleHindi = _stmt.getText(_columnIndexOfTitleHindi);
          }
          final String _tmpDescriptionEnglish;
          if (_stmt.isNull(_columnIndexOfDescriptionEnglish)) {
            _tmpDescriptionEnglish = null;
          } else {
            _tmpDescriptionEnglish = _stmt.getText(_columnIndexOfDescriptionEnglish);
          }
          final String _tmpDescriptionHindi;
          if (_stmt.isNull(_columnIndexOfDescriptionHindi)) {
            _tmpDescriptionHindi = null;
          } else {
            _tmpDescriptionHindi = _stmt.getText(_columnIndexOfDescriptionHindi);
          }
          final int _tmpIconResId;
          _tmpIconResId = (int) (_stmt.getLong(_columnIndexOfIconResId));
          final Integer _tmpBadgeImageResId;
          if (_stmt.isNull(_columnIndexOfBadgeImageResId)) {
            _tmpBadgeImageResId = null;
          } else {
            _tmpBadgeImageResId = (int) (_stmt.getLong(_columnIndexOfBadgeImageResId));
          }
          final String _tmpColorHex;
          if (_stmt.isNull(_columnIndexOfColorHex)) {
            _tmpColorHex = null;
          } else {
            _tmpColorHex = _stmt.getText(_columnIndexOfColorHex);
          }
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          final String _tmpType;
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmpType = null;
          } else {
            _tmpType = _stmt.getText(_columnIndexOfType);
          }
          final int _tmpMaxProgress;
          _tmpMaxProgress = (int) (_stmt.getLong(_columnIndexOfMaxProgress));
          final int _tmpCurrentProgress;
          _tmpCurrentProgress = (int) (_stmt.getLong(_columnIndexOfCurrentProgress));
          final int _tmpPointsRewarded;
          _tmpPointsRewarded = (int) (_stmt.getLong(_columnIndexOfPointsRewarded));
          final boolean _tmpIsUnlocked;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsUnlocked));
          _tmpIsUnlocked = _tmp != 0;
          final Long _tmpUnlockedAt;
          if (_stmt.isNull(_columnIndexOfUnlockedAt)) {
            _tmpUnlockedAt = null;
          } else {
            _tmpUnlockedAt = _stmt.getLong(_columnIndexOfUnlockedAt);
          }
          final boolean _tmpIsHidden;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsHidden));
          _tmpIsHidden = _tmp_1 != 0;
          final String _tmpUnlockMessage;
          if (_stmt.isNull(_columnIndexOfUnlockMessage)) {
            _tmpUnlockMessage = null;
          } else {
            _tmpUnlockMessage = _stmt.getText(_columnIndexOfUnlockMessage);
          }
          final String _tmpTriggerConditions;
          if (_stmt.isNull(_columnIndexOfTriggerConditions)) {
            _tmpTriggerConditions = null;
          } else {
            _tmpTriggerConditions = _stmt.getText(_columnIndexOfTriggerConditions);
          }
          _result = new Achievement(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpDescriptionEnglish,_tmpDescriptionHindi,_tmpIconResId,_tmpBadgeImageResId,_tmpColorHex,_tmpCategory,_tmpType,_tmpMaxProgress,_tmpCurrentProgress,_tmpPointsRewarded,_tmpIsUnlocked,_tmpUnlockedAt,_tmpIsHidden,_tmpUnlockMessage,_tmpTriggerConditions);
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
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitleEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleEnglish");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfDescriptionEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionEnglish");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfIconResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "iconResId");
        final int _columnIndexOfBadgeImageResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "badgeImageResId");
        final int _columnIndexOfColorHex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "colorHex");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "type");
        final int _columnIndexOfMaxProgress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "maxProgress");
        final int _columnIndexOfCurrentProgress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "currentProgress");
        final int _columnIndexOfPointsRewarded = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "pointsRewarded");
        final int _columnIndexOfIsUnlocked = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isUnlocked");
        final int _columnIndexOfUnlockedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlockedAt");
        final int _columnIndexOfIsHidden = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isHidden");
        final int _columnIndexOfUnlockMessage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlockMessage");
        final int _columnIndexOfTriggerConditions = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "triggerConditions");
        final Achievement _result;
        if (_stmt.step()) {
          final String _tmpId;
          if (_stmt.isNull(_columnIndexOfId)) {
            _tmpId = null;
          } else {
            _tmpId = _stmt.getText(_columnIndexOfId);
          }
          final String _tmpTitleEnglish;
          if (_stmt.isNull(_columnIndexOfTitleEnglish)) {
            _tmpTitleEnglish = null;
          } else {
            _tmpTitleEnglish = _stmt.getText(_columnIndexOfTitleEnglish);
          }
          final String _tmpTitleHindi;
          if (_stmt.isNull(_columnIndexOfTitleHindi)) {
            _tmpTitleHindi = null;
          } else {
            _tmpTitleHindi = _stmt.getText(_columnIndexOfTitleHindi);
          }
          final String _tmpDescriptionEnglish;
          if (_stmt.isNull(_columnIndexOfDescriptionEnglish)) {
            _tmpDescriptionEnglish = null;
          } else {
            _tmpDescriptionEnglish = _stmt.getText(_columnIndexOfDescriptionEnglish);
          }
          final String _tmpDescriptionHindi;
          if (_stmt.isNull(_columnIndexOfDescriptionHindi)) {
            _tmpDescriptionHindi = null;
          } else {
            _tmpDescriptionHindi = _stmt.getText(_columnIndexOfDescriptionHindi);
          }
          final int _tmpIconResId;
          _tmpIconResId = (int) (_stmt.getLong(_columnIndexOfIconResId));
          final Integer _tmpBadgeImageResId;
          if (_stmt.isNull(_columnIndexOfBadgeImageResId)) {
            _tmpBadgeImageResId = null;
          } else {
            _tmpBadgeImageResId = (int) (_stmt.getLong(_columnIndexOfBadgeImageResId));
          }
          final String _tmpColorHex;
          if (_stmt.isNull(_columnIndexOfColorHex)) {
            _tmpColorHex = null;
          } else {
            _tmpColorHex = _stmt.getText(_columnIndexOfColorHex);
          }
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          final String _tmpType;
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmpType = null;
          } else {
            _tmpType = _stmt.getText(_columnIndexOfType);
          }
          final int _tmpMaxProgress;
          _tmpMaxProgress = (int) (_stmt.getLong(_columnIndexOfMaxProgress));
          final int _tmpCurrentProgress;
          _tmpCurrentProgress = (int) (_stmt.getLong(_columnIndexOfCurrentProgress));
          final int _tmpPointsRewarded;
          _tmpPointsRewarded = (int) (_stmt.getLong(_columnIndexOfPointsRewarded));
          final boolean _tmpIsUnlocked;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsUnlocked));
          _tmpIsUnlocked = _tmp != 0;
          final Long _tmpUnlockedAt;
          if (_stmt.isNull(_columnIndexOfUnlockedAt)) {
            _tmpUnlockedAt = null;
          } else {
            _tmpUnlockedAt = _stmt.getLong(_columnIndexOfUnlockedAt);
          }
          final boolean _tmpIsHidden;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsHidden));
          _tmpIsHidden = _tmp_1 != 0;
          final String _tmpUnlockMessage;
          if (_stmt.isNull(_columnIndexOfUnlockMessage)) {
            _tmpUnlockMessage = null;
          } else {
            _tmpUnlockMessage = _stmt.getText(_columnIndexOfUnlockMessage);
          }
          final String _tmpTriggerConditions;
          if (_stmt.isNull(_columnIndexOfTriggerConditions)) {
            _tmpTriggerConditions = null;
          } else {
            _tmpTriggerConditions = _stmt.getText(_columnIndexOfTriggerConditions);
          }
          _result = new Achievement(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpDescriptionEnglish,_tmpDescriptionHindi,_tmpIconResId,_tmpBadgeImageResId,_tmpColorHex,_tmpCategory,_tmpType,_tmpMaxProgress,_tmpCurrentProgress,_tmpPointsRewarded,_tmpIsUnlocked,_tmpUnlockedAt,_tmpIsHidden,_tmpUnlockMessage,_tmpTriggerConditions);
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
  public LiveData<List<Achievement>> getAchievementsNearCompletion(final double percentage) {
    final String _sql = "SELECT * FROM achievements WHERE (currentProgress * 100.0 / maxProgress) >= ? AND isUnlocked = 0 ORDER BY (currentProgress * 100.0 / maxProgress) DESC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"achievements"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindDouble(_argIndex, percentage);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitleEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleEnglish");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfDescriptionEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionEnglish");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfIconResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "iconResId");
        final int _columnIndexOfBadgeImageResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "badgeImageResId");
        final int _columnIndexOfColorHex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "colorHex");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "type");
        final int _columnIndexOfMaxProgress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "maxProgress");
        final int _columnIndexOfCurrentProgress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "currentProgress");
        final int _columnIndexOfPointsRewarded = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "pointsRewarded");
        final int _columnIndexOfIsUnlocked = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isUnlocked");
        final int _columnIndexOfUnlockedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlockedAt");
        final int _columnIndexOfIsHidden = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isHidden");
        final int _columnIndexOfUnlockMessage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlockMessage");
        final int _columnIndexOfTriggerConditions = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "triggerConditions");
        final List<Achievement> _result = new ArrayList<Achievement>();
        while (_stmt.step()) {
          final Achievement _item;
          final String _tmpId;
          if (_stmt.isNull(_columnIndexOfId)) {
            _tmpId = null;
          } else {
            _tmpId = _stmt.getText(_columnIndexOfId);
          }
          final String _tmpTitleEnglish;
          if (_stmt.isNull(_columnIndexOfTitleEnglish)) {
            _tmpTitleEnglish = null;
          } else {
            _tmpTitleEnglish = _stmt.getText(_columnIndexOfTitleEnglish);
          }
          final String _tmpTitleHindi;
          if (_stmt.isNull(_columnIndexOfTitleHindi)) {
            _tmpTitleHindi = null;
          } else {
            _tmpTitleHindi = _stmt.getText(_columnIndexOfTitleHindi);
          }
          final String _tmpDescriptionEnglish;
          if (_stmt.isNull(_columnIndexOfDescriptionEnglish)) {
            _tmpDescriptionEnglish = null;
          } else {
            _tmpDescriptionEnglish = _stmt.getText(_columnIndexOfDescriptionEnglish);
          }
          final String _tmpDescriptionHindi;
          if (_stmt.isNull(_columnIndexOfDescriptionHindi)) {
            _tmpDescriptionHindi = null;
          } else {
            _tmpDescriptionHindi = _stmt.getText(_columnIndexOfDescriptionHindi);
          }
          final int _tmpIconResId;
          _tmpIconResId = (int) (_stmt.getLong(_columnIndexOfIconResId));
          final Integer _tmpBadgeImageResId;
          if (_stmt.isNull(_columnIndexOfBadgeImageResId)) {
            _tmpBadgeImageResId = null;
          } else {
            _tmpBadgeImageResId = (int) (_stmt.getLong(_columnIndexOfBadgeImageResId));
          }
          final String _tmpColorHex;
          if (_stmt.isNull(_columnIndexOfColorHex)) {
            _tmpColorHex = null;
          } else {
            _tmpColorHex = _stmt.getText(_columnIndexOfColorHex);
          }
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          final String _tmpType;
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmpType = null;
          } else {
            _tmpType = _stmt.getText(_columnIndexOfType);
          }
          final int _tmpMaxProgress;
          _tmpMaxProgress = (int) (_stmt.getLong(_columnIndexOfMaxProgress));
          final int _tmpCurrentProgress;
          _tmpCurrentProgress = (int) (_stmt.getLong(_columnIndexOfCurrentProgress));
          final int _tmpPointsRewarded;
          _tmpPointsRewarded = (int) (_stmt.getLong(_columnIndexOfPointsRewarded));
          final boolean _tmpIsUnlocked;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsUnlocked));
          _tmpIsUnlocked = _tmp != 0;
          final Long _tmpUnlockedAt;
          if (_stmt.isNull(_columnIndexOfUnlockedAt)) {
            _tmpUnlockedAt = null;
          } else {
            _tmpUnlockedAt = _stmt.getLong(_columnIndexOfUnlockedAt);
          }
          final boolean _tmpIsHidden;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsHidden));
          _tmpIsHidden = _tmp_1 != 0;
          final String _tmpUnlockMessage;
          if (_stmt.isNull(_columnIndexOfUnlockMessage)) {
            _tmpUnlockMessage = null;
          } else {
            _tmpUnlockMessage = _stmt.getText(_columnIndexOfUnlockMessage);
          }
          final String _tmpTriggerConditions;
          if (_stmt.isNull(_columnIndexOfTriggerConditions)) {
            _tmpTriggerConditions = null;
          } else {
            _tmpTriggerConditions = _stmt.getText(_columnIndexOfTriggerConditions);
          }
          _item = new Achievement(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpDescriptionEnglish,_tmpDescriptionHindi,_tmpIconResId,_tmpBadgeImageResId,_tmpColorHex,_tmpCategory,_tmpType,_tmpMaxProgress,_tmpCurrentProgress,_tmpPointsRewarded,_tmpIsUnlocked,_tmpUnlockedAt,_tmpIsHidden,_tmpUnlockMessage,_tmpTriggerConditions);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public List<Achievement> getAchievementsReadyToUnlock() {
    final String _sql = "SELECT * FROM achievements WHERE currentProgress >= maxProgress AND isUnlocked = 0";
    return DBUtil.performBlocking(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitleEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleEnglish");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfDescriptionEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionEnglish");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfIconResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "iconResId");
        final int _columnIndexOfBadgeImageResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "badgeImageResId");
        final int _columnIndexOfColorHex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "colorHex");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "type");
        final int _columnIndexOfMaxProgress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "maxProgress");
        final int _columnIndexOfCurrentProgress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "currentProgress");
        final int _columnIndexOfPointsRewarded = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "pointsRewarded");
        final int _columnIndexOfIsUnlocked = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isUnlocked");
        final int _columnIndexOfUnlockedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlockedAt");
        final int _columnIndexOfIsHidden = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isHidden");
        final int _columnIndexOfUnlockMessage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlockMessage");
        final int _columnIndexOfTriggerConditions = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "triggerConditions");
        final List<Achievement> _result = new ArrayList<Achievement>();
        while (_stmt.step()) {
          final Achievement _item;
          final String _tmpId;
          if (_stmt.isNull(_columnIndexOfId)) {
            _tmpId = null;
          } else {
            _tmpId = _stmt.getText(_columnIndexOfId);
          }
          final String _tmpTitleEnglish;
          if (_stmt.isNull(_columnIndexOfTitleEnglish)) {
            _tmpTitleEnglish = null;
          } else {
            _tmpTitleEnglish = _stmt.getText(_columnIndexOfTitleEnglish);
          }
          final String _tmpTitleHindi;
          if (_stmt.isNull(_columnIndexOfTitleHindi)) {
            _tmpTitleHindi = null;
          } else {
            _tmpTitleHindi = _stmt.getText(_columnIndexOfTitleHindi);
          }
          final String _tmpDescriptionEnglish;
          if (_stmt.isNull(_columnIndexOfDescriptionEnglish)) {
            _tmpDescriptionEnglish = null;
          } else {
            _tmpDescriptionEnglish = _stmt.getText(_columnIndexOfDescriptionEnglish);
          }
          final String _tmpDescriptionHindi;
          if (_stmt.isNull(_columnIndexOfDescriptionHindi)) {
            _tmpDescriptionHindi = null;
          } else {
            _tmpDescriptionHindi = _stmt.getText(_columnIndexOfDescriptionHindi);
          }
          final int _tmpIconResId;
          _tmpIconResId = (int) (_stmt.getLong(_columnIndexOfIconResId));
          final Integer _tmpBadgeImageResId;
          if (_stmt.isNull(_columnIndexOfBadgeImageResId)) {
            _tmpBadgeImageResId = null;
          } else {
            _tmpBadgeImageResId = (int) (_stmt.getLong(_columnIndexOfBadgeImageResId));
          }
          final String _tmpColorHex;
          if (_stmt.isNull(_columnIndexOfColorHex)) {
            _tmpColorHex = null;
          } else {
            _tmpColorHex = _stmt.getText(_columnIndexOfColorHex);
          }
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          final String _tmpType;
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmpType = null;
          } else {
            _tmpType = _stmt.getText(_columnIndexOfType);
          }
          final int _tmpMaxProgress;
          _tmpMaxProgress = (int) (_stmt.getLong(_columnIndexOfMaxProgress));
          final int _tmpCurrentProgress;
          _tmpCurrentProgress = (int) (_stmt.getLong(_columnIndexOfCurrentProgress));
          final int _tmpPointsRewarded;
          _tmpPointsRewarded = (int) (_stmt.getLong(_columnIndexOfPointsRewarded));
          final boolean _tmpIsUnlocked;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsUnlocked));
          _tmpIsUnlocked = _tmp != 0;
          final Long _tmpUnlockedAt;
          if (_stmt.isNull(_columnIndexOfUnlockedAt)) {
            _tmpUnlockedAt = null;
          } else {
            _tmpUnlockedAt = _stmt.getLong(_columnIndexOfUnlockedAt);
          }
          final boolean _tmpIsHidden;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsHidden));
          _tmpIsHidden = _tmp_1 != 0;
          final String _tmpUnlockMessage;
          if (_stmt.isNull(_columnIndexOfUnlockMessage)) {
            _tmpUnlockMessage = null;
          } else {
            _tmpUnlockMessage = _stmt.getText(_columnIndexOfUnlockMessage);
          }
          final String _tmpTriggerConditions;
          if (_stmt.isNull(_columnIndexOfTriggerConditions)) {
            _tmpTriggerConditions = null;
          } else {
            _tmpTriggerConditions = _stmt.getText(_columnIndexOfTriggerConditions);
          }
          _item = new Achievement(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpDescriptionEnglish,_tmpDescriptionHindi,_tmpIconResId,_tmpBadgeImageResId,_tmpColorHex,_tmpCategory,_tmpType,_tmpMaxProgress,_tmpCurrentProgress,_tmpPointsRewarded,_tmpIsUnlocked,_tmpUnlockedAt,_tmpIsHidden,_tmpUnlockMessage,_tmpTriggerConditions);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<Integer> getTotalPointsFromAchievements() {
    final String _sql = "SELECT COALESCE(SUM(pointsRewarded), 0) FROM achievements WHERE isUnlocked = 1";
    return __db.getInvalidationTracker().createLiveData(new String[] {"achievements"}, false, (_connection) -> {
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
  public int getTotalPointsFromAchievementsSync() {
    final String _sql = "SELECT COALESCE(SUM(pointsRewarded), 0) FROM achievements WHERE isUnlocked = 1";
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
  public LiveData<Integer> getUnlockedCount() {
    final String _sql = "SELECT COUNT(*) FROM achievements WHERE isUnlocked = 1";
    return __db.getInvalidationTracker().createLiveData(new String[] {"achievements"}, false, (_connection) -> {
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
  public LiveData<Integer> getVisibleAchievementCount() {
    final String _sql = "SELECT COUNT(*) FROM achievements WHERE isHidden = 0";
    return __db.getInvalidationTracker().createLiveData(new String[] {"achievements"}, false, (_connection) -> {
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
    final String _sql = "SELECT COUNT(*) FROM achievements";
    return __db.getInvalidationTracker().createLiveData(new String[] {"achievements"}, false, (_connection) -> {
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
  public LiveData<Double> getCompletionPercentage() {
    final String _sql = "SELECT CASE WHEN (SELECT COUNT(*) FROM achievements WHERE isHidden = 0) = 0 THEN 0.0 ELSE (SELECT COUNT(*) * 100.0 FROM achievements WHERE isUnlocked = 1 AND isHidden = 0) / (SELECT COUNT(*) FROM achievements WHERE isHidden = 0) END";
    return __db.getInvalidationTracker().createLiveData(new String[] {"achievements"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final Double _result;
        if (_stmt.step()) {
          final Double _tmp;
          if (_stmt.isNull(0)) {
            _tmp = null;
          } else {
            _tmp = _stmt.getDouble(0);
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
  public LiveData<List<ModernAchievementDao.CategoryStats>> getStatsByCategory() {
    final String _sql = "SELECT category, COUNT(*) as total, SUM(CASE WHEN isUnlocked = 1 THEN 1 ELSE 0 END) as unlocked FROM achievements WHERE isHidden = 0 GROUP BY category ORDER BY category";
    return __db.getInvalidationTracker().createLiveData(new String[] {"achievements"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfCategory = 0;
        final int _columnIndexOfTotal = 1;
        final int _columnIndexOfUnlocked = 2;
        final List<ModernAchievementDao.CategoryStats> _result = new ArrayList<ModernAchievementDao.CategoryStats>();
        while (_stmt.step()) {
          final ModernAchievementDao.CategoryStats _item;
          _item = new ModernAchievementDao.CategoryStats();
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _item.category = null;
          } else {
            _item.category = _stmt.getText(_columnIndexOfCategory);
          }
          _item.total = (int) (_stmt.getLong(_columnIndexOfTotal));
          _item.unlocked = (int) (_stmt.getLong(_columnIndexOfUnlocked));
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Achievement>> getRecentUnlockedWithMessages(final int limit) {
    final String _sql = "SELECT * FROM achievements WHERE isUnlocked = 1 AND unlockMessage IS NOT NULL AND unlockMessage != '' ORDER BY unlockedAt DESC LIMIT ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"achievements"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, limit);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitleEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleEnglish");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfDescriptionEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionEnglish");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfIconResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "iconResId");
        final int _columnIndexOfBadgeImageResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "badgeImageResId");
        final int _columnIndexOfColorHex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "colorHex");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "type");
        final int _columnIndexOfMaxProgress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "maxProgress");
        final int _columnIndexOfCurrentProgress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "currentProgress");
        final int _columnIndexOfPointsRewarded = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "pointsRewarded");
        final int _columnIndexOfIsUnlocked = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isUnlocked");
        final int _columnIndexOfUnlockedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlockedAt");
        final int _columnIndexOfIsHidden = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isHidden");
        final int _columnIndexOfUnlockMessage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlockMessage");
        final int _columnIndexOfTriggerConditions = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "triggerConditions");
        final List<Achievement> _result = new ArrayList<Achievement>();
        while (_stmt.step()) {
          final Achievement _item;
          final String _tmpId;
          if (_stmt.isNull(_columnIndexOfId)) {
            _tmpId = null;
          } else {
            _tmpId = _stmt.getText(_columnIndexOfId);
          }
          final String _tmpTitleEnglish;
          if (_stmt.isNull(_columnIndexOfTitleEnglish)) {
            _tmpTitleEnglish = null;
          } else {
            _tmpTitleEnglish = _stmt.getText(_columnIndexOfTitleEnglish);
          }
          final String _tmpTitleHindi;
          if (_stmt.isNull(_columnIndexOfTitleHindi)) {
            _tmpTitleHindi = null;
          } else {
            _tmpTitleHindi = _stmt.getText(_columnIndexOfTitleHindi);
          }
          final String _tmpDescriptionEnglish;
          if (_stmt.isNull(_columnIndexOfDescriptionEnglish)) {
            _tmpDescriptionEnglish = null;
          } else {
            _tmpDescriptionEnglish = _stmt.getText(_columnIndexOfDescriptionEnglish);
          }
          final String _tmpDescriptionHindi;
          if (_stmt.isNull(_columnIndexOfDescriptionHindi)) {
            _tmpDescriptionHindi = null;
          } else {
            _tmpDescriptionHindi = _stmt.getText(_columnIndexOfDescriptionHindi);
          }
          final int _tmpIconResId;
          _tmpIconResId = (int) (_stmt.getLong(_columnIndexOfIconResId));
          final Integer _tmpBadgeImageResId;
          if (_stmt.isNull(_columnIndexOfBadgeImageResId)) {
            _tmpBadgeImageResId = null;
          } else {
            _tmpBadgeImageResId = (int) (_stmt.getLong(_columnIndexOfBadgeImageResId));
          }
          final String _tmpColorHex;
          if (_stmt.isNull(_columnIndexOfColorHex)) {
            _tmpColorHex = null;
          } else {
            _tmpColorHex = _stmt.getText(_columnIndexOfColorHex);
          }
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          final String _tmpType;
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmpType = null;
          } else {
            _tmpType = _stmt.getText(_columnIndexOfType);
          }
          final int _tmpMaxProgress;
          _tmpMaxProgress = (int) (_stmt.getLong(_columnIndexOfMaxProgress));
          final int _tmpCurrentProgress;
          _tmpCurrentProgress = (int) (_stmt.getLong(_columnIndexOfCurrentProgress));
          final int _tmpPointsRewarded;
          _tmpPointsRewarded = (int) (_stmt.getLong(_columnIndexOfPointsRewarded));
          final boolean _tmpIsUnlocked;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsUnlocked));
          _tmpIsUnlocked = _tmp != 0;
          final Long _tmpUnlockedAt;
          if (_stmt.isNull(_columnIndexOfUnlockedAt)) {
            _tmpUnlockedAt = null;
          } else {
            _tmpUnlockedAt = _stmt.getLong(_columnIndexOfUnlockedAt);
          }
          final boolean _tmpIsHidden;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsHidden));
          _tmpIsHidden = _tmp_1 != 0;
          final String _tmpUnlockMessage;
          if (_stmt.isNull(_columnIndexOfUnlockMessage)) {
            _tmpUnlockMessage = null;
          } else {
            _tmpUnlockMessage = _stmt.getText(_columnIndexOfUnlockMessage);
          }
          final String _tmpTriggerConditions;
          if (_stmt.isNull(_columnIndexOfTriggerConditions)) {
            _tmpTriggerConditions = null;
          } else {
            _tmpTriggerConditions = _stmt.getText(_columnIndexOfTriggerConditions);
          }
          _item = new Achievement(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpDescriptionEnglish,_tmpDescriptionHindi,_tmpIconResId,_tmpBadgeImageResId,_tmpColorHex,_tmpCategory,_tmpType,_tmpMaxProgress,_tmpCurrentProgress,_tmpPointsRewarded,_tmpIsUnlocked,_tmpUnlockedAt,_tmpIsHidden,_tmpUnlockMessage,_tmpTriggerConditions);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Achievement>> getUnlockedSecretAchievements() {
    final String _sql = "SELECT * FROM achievements WHERE isHidden = 1 AND isUnlocked = 1 ORDER BY unlockedAt DESC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"achievements"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitleEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleEnglish");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfDescriptionEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionEnglish");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfIconResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "iconResId");
        final int _columnIndexOfBadgeImageResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "badgeImageResId");
        final int _columnIndexOfColorHex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "colorHex");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "type");
        final int _columnIndexOfMaxProgress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "maxProgress");
        final int _columnIndexOfCurrentProgress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "currentProgress");
        final int _columnIndexOfPointsRewarded = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "pointsRewarded");
        final int _columnIndexOfIsUnlocked = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isUnlocked");
        final int _columnIndexOfUnlockedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlockedAt");
        final int _columnIndexOfIsHidden = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isHidden");
        final int _columnIndexOfUnlockMessage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlockMessage");
        final int _columnIndexOfTriggerConditions = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "triggerConditions");
        final List<Achievement> _result = new ArrayList<Achievement>();
        while (_stmt.step()) {
          final Achievement _item;
          final String _tmpId;
          if (_stmt.isNull(_columnIndexOfId)) {
            _tmpId = null;
          } else {
            _tmpId = _stmt.getText(_columnIndexOfId);
          }
          final String _tmpTitleEnglish;
          if (_stmt.isNull(_columnIndexOfTitleEnglish)) {
            _tmpTitleEnglish = null;
          } else {
            _tmpTitleEnglish = _stmt.getText(_columnIndexOfTitleEnglish);
          }
          final String _tmpTitleHindi;
          if (_stmt.isNull(_columnIndexOfTitleHindi)) {
            _tmpTitleHindi = null;
          } else {
            _tmpTitleHindi = _stmt.getText(_columnIndexOfTitleHindi);
          }
          final String _tmpDescriptionEnglish;
          if (_stmt.isNull(_columnIndexOfDescriptionEnglish)) {
            _tmpDescriptionEnglish = null;
          } else {
            _tmpDescriptionEnglish = _stmt.getText(_columnIndexOfDescriptionEnglish);
          }
          final String _tmpDescriptionHindi;
          if (_stmt.isNull(_columnIndexOfDescriptionHindi)) {
            _tmpDescriptionHindi = null;
          } else {
            _tmpDescriptionHindi = _stmt.getText(_columnIndexOfDescriptionHindi);
          }
          final int _tmpIconResId;
          _tmpIconResId = (int) (_stmt.getLong(_columnIndexOfIconResId));
          final Integer _tmpBadgeImageResId;
          if (_stmt.isNull(_columnIndexOfBadgeImageResId)) {
            _tmpBadgeImageResId = null;
          } else {
            _tmpBadgeImageResId = (int) (_stmt.getLong(_columnIndexOfBadgeImageResId));
          }
          final String _tmpColorHex;
          if (_stmt.isNull(_columnIndexOfColorHex)) {
            _tmpColorHex = null;
          } else {
            _tmpColorHex = _stmt.getText(_columnIndexOfColorHex);
          }
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          final String _tmpType;
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmpType = null;
          } else {
            _tmpType = _stmt.getText(_columnIndexOfType);
          }
          final int _tmpMaxProgress;
          _tmpMaxProgress = (int) (_stmt.getLong(_columnIndexOfMaxProgress));
          final int _tmpCurrentProgress;
          _tmpCurrentProgress = (int) (_stmt.getLong(_columnIndexOfCurrentProgress));
          final int _tmpPointsRewarded;
          _tmpPointsRewarded = (int) (_stmt.getLong(_columnIndexOfPointsRewarded));
          final boolean _tmpIsUnlocked;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsUnlocked));
          _tmpIsUnlocked = _tmp != 0;
          final Long _tmpUnlockedAt;
          if (_stmt.isNull(_columnIndexOfUnlockedAt)) {
            _tmpUnlockedAt = null;
          } else {
            _tmpUnlockedAt = _stmt.getLong(_columnIndexOfUnlockedAt);
          }
          final boolean _tmpIsHidden;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsHidden));
          _tmpIsHidden = _tmp_1 != 0;
          final String _tmpUnlockMessage;
          if (_stmt.isNull(_columnIndexOfUnlockMessage)) {
            _tmpUnlockMessage = null;
          } else {
            _tmpUnlockMessage = _stmt.getText(_columnIndexOfUnlockMessage);
          }
          final String _tmpTriggerConditions;
          if (_stmt.isNull(_columnIndexOfTriggerConditions)) {
            _tmpTriggerConditions = null;
          } else {
            _tmpTriggerConditions = _stmt.getText(_columnIndexOfTriggerConditions);
          }
          _item = new Achievement(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpDescriptionEnglish,_tmpDescriptionHindi,_tmpIconResId,_tmpBadgeImageResId,_tmpColorHex,_tmpCategory,_tmpType,_tmpMaxProgress,_tmpCurrentProgress,_tmpPointsRewarded,_tmpIsUnlocked,_tmpUnlockedAt,_tmpIsHidden,_tmpUnlockMessage,_tmpTriggerConditions);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Achievement>> getAchievementsUnlockedInRange(final long startTime,
      final long endTime) {
    final String _sql = "SELECT * FROM achievements WHERE isUnlocked = 1 AND unlockedAt BETWEEN ? AND ? ORDER BY unlockedAt DESC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"achievements"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, startTime);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, endTime);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitleEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleEnglish");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfDescriptionEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionEnglish");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfIconResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "iconResId");
        final int _columnIndexOfBadgeImageResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "badgeImageResId");
        final int _columnIndexOfColorHex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "colorHex");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "type");
        final int _columnIndexOfMaxProgress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "maxProgress");
        final int _columnIndexOfCurrentProgress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "currentProgress");
        final int _columnIndexOfPointsRewarded = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "pointsRewarded");
        final int _columnIndexOfIsUnlocked = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isUnlocked");
        final int _columnIndexOfUnlockedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlockedAt");
        final int _columnIndexOfIsHidden = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isHidden");
        final int _columnIndexOfUnlockMessage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlockMessage");
        final int _columnIndexOfTriggerConditions = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "triggerConditions");
        final List<Achievement> _result = new ArrayList<Achievement>();
        while (_stmt.step()) {
          final Achievement _item;
          final String _tmpId;
          if (_stmt.isNull(_columnIndexOfId)) {
            _tmpId = null;
          } else {
            _tmpId = _stmt.getText(_columnIndexOfId);
          }
          final String _tmpTitleEnglish;
          if (_stmt.isNull(_columnIndexOfTitleEnglish)) {
            _tmpTitleEnglish = null;
          } else {
            _tmpTitleEnglish = _stmt.getText(_columnIndexOfTitleEnglish);
          }
          final String _tmpTitleHindi;
          if (_stmt.isNull(_columnIndexOfTitleHindi)) {
            _tmpTitleHindi = null;
          } else {
            _tmpTitleHindi = _stmt.getText(_columnIndexOfTitleHindi);
          }
          final String _tmpDescriptionEnglish;
          if (_stmt.isNull(_columnIndexOfDescriptionEnglish)) {
            _tmpDescriptionEnglish = null;
          } else {
            _tmpDescriptionEnglish = _stmt.getText(_columnIndexOfDescriptionEnglish);
          }
          final String _tmpDescriptionHindi;
          if (_stmt.isNull(_columnIndexOfDescriptionHindi)) {
            _tmpDescriptionHindi = null;
          } else {
            _tmpDescriptionHindi = _stmt.getText(_columnIndexOfDescriptionHindi);
          }
          final int _tmpIconResId;
          _tmpIconResId = (int) (_stmt.getLong(_columnIndexOfIconResId));
          final Integer _tmpBadgeImageResId;
          if (_stmt.isNull(_columnIndexOfBadgeImageResId)) {
            _tmpBadgeImageResId = null;
          } else {
            _tmpBadgeImageResId = (int) (_stmt.getLong(_columnIndexOfBadgeImageResId));
          }
          final String _tmpColorHex;
          if (_stmt.isNull(_columnIndexOfColorHex)) {
            _tmpColorHex = null;
          } else {
            _tmpColorHex = _stmt.getText(_columnIndexOfColorHex);
          }
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          final String _tmpType;
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmpType = null;
          } else {
            _tmpType = _stmt.getText(_columnIndexOfType);
          }
          final int _tmpMaxProgress;
          _tmpMaxProgress = (int) (_stmt.getLong(_columnIndexOfMaxProgress));
          final int _tmpCurrentProgress;
          _tmpCurrentProgress = (int) (_stmt.getLong(_columnIndexOfCurrentProgress));
          final int _tmpPointsRewarded;
          _tmpPointsRewarded = (int) (_stmt.getLong(_columnIndexOfPointsRewarded));
          final boolean _tmpIsUnlocked;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsUnlocked));
          _tmpIsUnlocked = _tmp != 0;
          final Long _tmpUnlockedAt;
          if (_stmt.isNull(_columnIndexOfUnlockedAt)) {
            _tmpUnlockedAt = null;
          } else {
            _tmpUnlockedAt = _stmt.getLong(_columnIndexOfUnlockedAt);
          }
          final boolean _tmpIsHidden;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsHidden));
          _tmpIsHidden = _tmp_1 != 0;
          final String _tmpUnlockMessage;
          if (_stmt.isNull(_columnIndexOfUnlockMessage)) {
            _tmpUnlockMessage = null;
          } else {
            _tmpUnlockMessage = _stmt.getText(_columnIndexOfUnlockMessage);
          }
          final String _tmpTriggerConditions;
          if (_stmt.isNull(_columnIndexOfTriggerConditions)) {
            _tmpTriggerConditions = null;
          } else {
            _tmpTriggerConditions = _stmt.getText(_columnIndexOfTriggerConditions);
          }
          _item = new Achievement(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpDescriptionEnglish,_tmpDescriptionHindi,_tmpIconResId,_tmpBadgeImageResId,_tmpColorHex,_tmpCategory,_tmpType,_tmpMaxProgress,_tmpCurrentProgress,_tmpPointsRewarded,_tmpIsUnlocked,_tmpUnlockedAt,_tmpIsHidden,_tmpUnlockMessage,_tmpTriggerConditions);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Achievement>> searchAchievements(final String searchQuery) {
    final String _sql = "SELECT * FROM achievements WHERE (titleEnglish LIKE '%' || ? || '%' OR titleHindi LIKE '%' || ? || '%') AND isHidden = 0 ORDER BY titleEnglish";
    return __db.getInvalidationTracker().createLiveData(new String[] {"achievements"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (searchQuery == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, searchQuery);
        }
        _argIndex = 2;
        if (searchQuery == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, searchQuery);
        }
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitleEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleEnglish");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfDescriptionEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionEnglish");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfIconResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "iconResId");
        final int _columnIndexOfBadgeImageResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "badgeImageResId");
        final int _columnIndexOfColorHex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "colorHex");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "type");
        final int _columnIndexOfMaxProgress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "maxProgress");
        final int _columnIndexOfCurrentProgress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "currentProgress");
        final int _columnIndexOfPointsRewarded = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "pointsRewarded");
        final int _columnIndexOfIsUnlocked = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isUnlocked");
        final int _columnIndexOfUnlockedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlockedAt");
        final int _columnIndexOfIsHidden = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isHidden");
        final int _columnIndexOfUnlockMessage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlockMessage");
        final int _columnIndexOfTriggerConditions = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "triggerConditions");
        final List<Achievement> _result = new ArrayList<Achievement>();
        while (_stmt.step()) {
          final Achievement _item;
          final String _tmpId;
          if (_stmt.isNull(_columnIndexOfId)) {
            _tmpId = null;
          } else {
            _tmpId = _stmt.getText(_columnIndexOfId);
          }
          final String _tmpTitleEnglish;
          if (_stmt.isNull(_columnIndexOfTitleEnglish)) {
            _tmpTitleEnglish = null;
          } else {
            _tmpTitleEnglish = _stmt.getText(_columnIndexOfTitleEnglish);
          }
          final String _tmpTitleHindi;
          if (_stmt.isNull(_columnIndexOfTitleHindi)) {
            _tmpTitleHindi = null;
          } else {
            _tmpTitleHindi = _stmt.getText(_columnIndexOfTitleHindi);
          }
          final String _tmpDescriptionEnglish;
          if (_stmt.isNull(_columnIndexOfDescriptionEnglish)) {
            _tmpDescriptionEnglish = null;
          } else {
            _tmpDescriptionEnglish = _stmt.getText(_columnIndexOfDescriptionEnglish);
          }
          final String _tmpDescriptionHindi;
          if (_stmt.isNull(_columnIndexOfDescriptionHindi)) {
            _tmpDescriptionHindi = null;
          } else {
            _tmpDescriptionHindi = _stmt.getText(_columnIndexOfDescriptionHindi);
          }
          final int _tmpIconResId;
          _tmpIconResId = (int) (_stmt.getLong(_columnIndexOfIconResId));
          final Integer _tmpBadgeImageResId;
          if (_stmt.isNull(_columnIndexOfBadgeImageResId)) {
            _tmpBadgeImageResId = null;
          } else {
            _tmpBadgeImageResId = (int) (_stmt.getLong(_columnIndexOfBadgeImageResId));
          }
          final String _tmpColorHex;
          if (_stmt.isNull(_columnIndexOfColorHex)) {
            _tmpColorHex = null;
          } else {
            _tmpColorHex = _stmt.getText(_columnIndexOfColorHex);
          }
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          final String _tmpType;
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmpType = null;
          } else {
            _tmpType = _stmt.getText(_columnIndexOfType);
          }
          final int _tmpMaxProgress;
          _tmpMaxProgress = (int) (_stmt.getLong(_columnIndexOfMaxProgress));
          final int _tmpCurrentProgress;
          _tmpCurrentProgress = (int) (_stmt.getLong(_columnIndexOfCurrentProgress));
          final int _tmpPointsRewarded;
          _tmpPointsRewarded = (int) (_stmt.getLong(_columnIndexOfPointsRewarded));
          final boolean _tmpIsUnlocked;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsUnlocked));
          _tmpIsUnlocked = _tmp != 0;
          final Long _tmpUnlockedAt;
          if (_stmt.isNull(_columnIndexOfUnlockedAt)) {
            _tmpUnlockedAt = null;
          } else {
            _tmpUnlockedAt = _stmt.getLong(_columnIndexOfUnlockedAt);
          }
          final boolean _tmpIsHidden;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsHidden));
          _tmpIsHidden = _tmp_1 != 0;
          final String _tmpUnlockMessage;
          if (_stmt.isNull(_columnIndexOfUnlockMessage)) {
            _tmpUnlockMessage = null;
          } else {
            _tmpUnlockMessage = _stmt.getText(_columnIndexOfUnlockMessage);
          }
          final String _tmpTriggerConditions;
          if (_stmt.isNull(_columnIndexOfTriggerConditions)) {
            _tmpTriggerConditions = null;
          } else {
            _tmpTriggerConditions = _stmt.getText(_columnIndexOfTriggerConditions);
          }
          _item = new Achievement(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpDescriptionEnglish,_tmpDescriptionHindi,_tmpIconResId,_tmpBadgeImageResId,_tmpColorHex,_tmpCategory,_tmpType,_tmpMaxProgress,_tmpCurrentProgress,_tmpPointsRewarded,_tmpIsUnlocked,_tmpUnlockedAt,_tmpIsHidden,_tmpUnlockMessage,_tmpTriggerConditions);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<String>> getAllCategories() {
    final String _sql = "SELECT DISTINCT category FROM achievements WHERE isHidden = 0 ORDER BY category";
    return __db.getInvalidationTracker().createLiveData(new String[] {"achievements"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final List<String> _result = new ArrayList<String>();
        while (_stmt.step()) {
          final String _item;
          if (_stmt.isNull(0)) {
            _item = null;
          } else {
            _item = _stmt.getText(0);
          }
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<String>> getAllTypes() {
    final String _sql = "SELECT DISTINCT type FROM achievements WHERE isHidden = 0 ORDER BY type";
    return __db.getInvalidationTracker().createLiveData(new String[] {"achievements"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final List<String> _result = new ArrayList<String>();
        while (_stmt.step()) {
          final String _item;
          if (_stmt.isNull(0)) {
            _item = null;
          } else {
            _item = _stmt.getText(0);
          }
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public void incrementProgress(final String id, final int increment) {
    final String _sql = "UPDATE achievements SET currentProgress = currentProgress + ? WHERE id = ?";
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, increment);
        _argIndex = 2;
        if (id == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, id);
        }
        _stmt.step();
        return null;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public void setProgress(final String id, final int progress) {
    final String _sql = "UPDATE achievements SET currentProgress = ? WHERE id = ?";
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, progress);
        _argIndex = 2;
        if (id == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, id);
        }
        _stmt.step();
        return null;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public void unlockAchievement(final String id, final long unlockedAt) {
    final String _sql = "UPDATE achievements SET isUnlocked = 1, unlockedAt = ? WHERE id = ?";
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, unlockedAt);
        _argIndex = 2;
        if (id == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, id);
        }
        _stmt.step();
        return null;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public void unlockAchievements(final List<String> ids, final long unlockedAt) {
    final StringBuilder _stringBuilder = new StringBuilder();
    _stringBuilder.append("UPDATE achievements SET isUnlocked = 1, unlockedAt = ");
    _stringBuilder.append("?");
    _stringBuilder.append(" WHERE id IN (");
    final int _inputSize = ids == null ? 1 : ids.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, unlockedAt);
        _argIndex = 2;
        if (ids == null) {
          _stmt.bindNull(_argIndex);
        } else {
          for (String _item : ids) {
            if (_item == null) {
              _stmt.bindNull(_argIndex);
            } else {
              _stmt.bindText(_argIndex, _item);
            }
            _argIndex++;
          }
        }
        _stmt.step();
        return null;
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

  @Override
  public void deleteByCategory(final String category) {
    final String _sql = "DELETE FROM achievements WHERE category = ?";
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (category == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, category);
        }
        _stmt.step();
        return null;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public void resetAllProgress() {
    final String _sql = "UPDATE achievements SET currentProgress = 0, isUnlocked = 0, unlockedAt = NULL";
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
  public void resetProgressByCategory(final String category) {
    final String _sql = "UPDATE achievements SET currentProgress = 0, isUnlocked = 0, unlockedAt = NULL WHERE category = ?";
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (category == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, category);
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
