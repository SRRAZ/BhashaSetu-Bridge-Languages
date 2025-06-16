package com.bhashasetu.app.data.dao;

import androidx.annotation.NonNull;
import androidx.room.EntityDeleteOrUpdateAdapter;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.coroutines.FlowUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteStatement;
import com.bhashasetu.app.data.model.Achievement;
import java.lang.Class;
import java.lang.Integer;
import java.lang.Long;
import java.lang.NullPointerException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation", "removal"})
public final class AchievementDao_Impl implements AchievementDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<Achievement> __insertAdapterOfAchievement;

  private final EntityDeleteOrUpdateAdapter<Achievement> __deleteAdapterOfAchievement;

  private final EntityDeleteOrUpdateAdapter<Achievement> __updateAdapterOfAchievement;

  public AchievementDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfAchievement = new EntityInsertAdapter<Achievement>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `achievements` (`id`,`titleEnglish`,`titleHindi`,`descriptionEnglish`,`descriptionHindi`,`iconResId`,`badgeImageResId`,`colorHex`,`category`,`type`,`maxProgress`,`isHidden`,`triggerConditions`,`pointsRewarded`,`unlockMessage`,`isUnlocked`,`currentProgress`,`unlockedAt`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
        final int _tmp = entity.isHidden() ? 1 : 0;
        statement.bindLong(12, _tmp);
        if (entity.getTriggerConditions() == null) {
          statement.bindNull(13);
        } else {
          statement.bindText(13, entity.getTriggerConditions());
        }
        statement.bindLong(14, entity.getPointsRewarded());
        if (entity.getUnlockMessage() == null) {
          statement.bindNull(15);
        } else {
          statement.bindText(15, entity.getUnlockMessage());
        }
        final int _tmp_1 = entity.isUnlocked() ? 1 : 0;
        statement.bindLong(16, _tmp_1);
        statement.bindLong(17, entity.getCurrentProgress());
        if (entity.getUnlockedAt() == null) {
          statement.bindNull(18);
        } else {
          statement.bindLong(18, entity.getUnlockedAt());
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
        return "UPDATE OR ABORT `achievements` SET `id` = ?,`titleEnglish` = ?,`titleHindi` = ?,`descriptionEnglish` = ?,`descriptionHindi` = ?,`iconResId` = ?,`badgeImageResId` = ?,`colorHex` = ?,`category` = ?,`type` = ?,`maxProgress` = ?,`isHidden` = ?,`triggerConditions` = ?,`pointsRewarded` = ?,`unlockMessage` = ?,`isUnlocked` = ?,`currentProgress` = ?,`unlockedAt` = ? WHERE `id` = ?";
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
        final int _tmp = entity.isHidden() ? 1 : 0;
        statement.bindLong(12, _tmp);
        if (entity.getTriggerConditions() == null) {
          statement.bindNull(13);
        } else {
          statement.bindText(13, entity.getTriggerConditions());
        }
        statement.bindLong(14, entity.getPointsRewarded());
        if (entity.getUnlockMessage() == null) {
          statement.bindNull(15);
        } else {
          statement.bindText(15, entity.getUnlockMessage());
        }
        final int _tmp_1 = entity.isUnlocked() ? 1 : 0;
        statement.bindLong(16, _tmp_1);
        statement.bindLong(17, entity.getCurrentProgress());
        if (entity.getUnlockedAt() == null) {
          statement.bindNull(18);
        } else {
          statement.bindLong(18, entity.getUnlockedAt());
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
  public Object insert(final Achievement achievement,
      final Continuation<? super Unit> $completion) {
    if (achievement == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __insertAdapterOfAchievement.insert(_connection, achievement);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Object delete(final Achievement achievement,
      final Continuation<? super Unit> $completion) {
    if (achievement == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __deleteAdapterOfAchievement.handle(_connection, achievement);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Object update(final Achievement achievement,
      final Continuation<? super Unit> $completion) {
    if (achievement == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __updateAdapterOfAchievement.handle(_connection, achievement);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Flow<List<Achievement>> getAllAchievements() {
    final String _sql = "SELECT * FROM achievements";
    return FlowUtil.createFlow(__db, false, new String[] {"achievements"}, (_connection) -> {
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
        final int _columnIndexOfIsHidden = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isHidden");
        final int _columnIndexOfTriggerConditions = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "triggerConditions");
        final int _columnIndexOfPointsRewarded = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "pointsRewarded");
        final int _columnIndexOfUnlockMessage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlockMessage");
        final int _columnIndexOfIsUnlocked = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isUnlocked");
        final int _columnIndexOfCurrentProgress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "currentProgress");
        final int _columnIndexOfUnlockedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlockedAt");
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
          final boolean _tmpIsHidden;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsHidden));
          _tmpIsHidden = _tmp != 0;
          final String _tmpTriggerConditions;
          if (_stmt.isNull(_columnIndexOfTriggerConditions)) {
            _tmpTriggerConditions = null;
          } else {
            _tmpTriggerConditions = _stmt.getText(_columnIndexOfTriggerConditions);
          }
          final int _tmpPointsRewarded;
          _tmpPointsRewarded = (int) (_stmt.getLong(_columnIndexOfPointsRewarded));
          final String _tmpUnlockMessage;
          if (_stmt.isNull(_columnIndexOfUnlockMessage)) {
            _tmpUnlockMessage = null;
          } else {
            _tmpUnlockMessage = _stmt.getText(_columnIndexOfUnlockMessage);
          }
          final boolean _tmpIsUnlocked;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsUnlocked));
          _tmpIsUnlocked = _tmp_1 != 0;
          final int _tmpCurrentProgress;
          _tmpCurrentProgress = (int) (_stmt.getLong(_columnIndexOfCurrentProgress));
          final Long _tmpUnlockedAt;
          if (_stmt.isNull(_columnIndexOfUnlockedAt)) {
            _tmpUnlockedAt = null;
          } else {
            _tmpUnlockedAt = _stmt.getLong(_columnIndexOfUnlockedAt);
          }
          _item = new Achievement(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpDescriptionEnglish,_tmpDescriptionHindi,_tmpIconResId,_tmpBadgeImageResId,_tmpColorHex,_tmpCategory,_tmpType,_tmpMaxProgress,_tmpIsHidden,_tmpTriggerConditions,_tmpPointsRewarded,_tmpUnlockMessage,_tmpIsUnlocked,_tmpCurrentProgress,_tmpUnlockedAt);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Flow<Achievement> getAchievementById(final String id) {
    final String _sql = "SELECT * FROM achievements WHERE id = ?";
    return FlowUtil.createFlow(__db, false, new String[] {"achievements"}, (_connection) -> {
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
        final int _columnIndexOfIsHidden = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isHidden");
        final int _columnIndexOfTriggerConditions = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "triggerConditions");
        final int _columnIndexOfPointsRewarded = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "pointsRewarded");
        final int _columnIndexOfUnlockMessage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlockMessage");
        final int _columnIndexOfIsUnlocked = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isUnlocked");
        final int _columnIndexOfCurrentProgress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "currentProgress");
        final int _columnIndexOfUnlockedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlockedAt");
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
          final boolean _tmpIsHidden;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsHidden));
          _tmpIsHidden = _tmp != 0;
          final String _tmpTriggerConditions;
          if (_stmt.isNull(_columnIndexOfTriggerConditions)) {
            _tmpTriggerConditions = null;
          } else {
            _tmpTriggerConditions = _stmt.getText(_columnIndexOfTriggerConditions);
          }
          final int _tmpPointsRewarded;
          _tmpPointsRewarded = (int) (_stmt.getLong(_columnIndexOfPointsRewarded));
          final String _tmpUnlockMessage;
          if (_stmt.isNull(_columnIndexOfUnlockMessage)) {
            _tmpUnlockMessage = null;
          } else {
            _tmpUnlockMessage = _stmt.getText(_columnIndexOfUnlockMessage);
          }
          final boolean _tmpIsUnlocked;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsUnlocked));
          _tmpIsUnlocked = _tmp_1 != 0;
          final int _tmpCurrentProgress;
          _tmpCurrentProgress = (int) (_stmt.getLong(_columnIndexOfCurrentProgress));
          final Long _tmpUnlockedAt;
          if (_stmt.isNull(_columnIndexOfUnlockedAt)) {
            _tmpUnlockedAt = null;
          } else {
            _tmpUnlockedAt = _stmt.getLong(_columnIndexOfUnlockedAt);
          }
          _result = new Achievement(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpDescriptionEnglish,_tmpDescriptionHindi,_tmpIconResId,_tmpBadgeImageResId,_tmpColorHex,_tmpCategory,_tmpType,_tmpMaxProgress,_tmpIsHidden,_tmpTriggerConditions,_tmpPointsRewarded,_tmpUnlockMessage,_tmpIsUnlocked,_tmpCurrentProgress,_tmpUnlockedAt);
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
  public Flow<List<Achievement>> getUnlockedAchievements() {
    final String _sql = "SELECT * FROM achievements WHERE isUnlocked = 1";
    return FlowUtil.createFlow(__db, false, new String[] {"achievements"}, (_connection) -> {
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
        final int _columnIndexOfIsHidden = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isHidden");
        final int _columnIndexOfTriggerConditions = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "triggerConditions");
        final int _columnIndexOfPointsRewarded = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "pointsRewarded");
        final int _columnIndexOfUnlockMessage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlockMessage");
        final int _columnIndexOfIsUnlocked = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isUnlocked");
        final int _columnIndexOfCurrentProgress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "currentProgress");
        final int _columnIndexOfUnlockedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlockedAt");
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
          final boolean _tmpIsHidden;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsHidden));
          _tmpIsHidden = _tmp != 0;
          final String _tmpTriggerConditions;
          if (_stmt.isNull(_columnIndexOfTriggerConditions)) {
            _tmpTriggerConditions = null;
          } else {
            _tmpTriggerConditions = _stmt.getText(_columnIndexOfTriggerConditions);
          }
          final int _tmpPointsRewarded;
          _tmpPointsRewarded = (int) (_stmt.getLong(_columnIndexOfPointsRewarded));
          final String _tmpUnlockMessage;
          if (_stmt.isNull(_columnIndexOfUnlockMessage)) {
            _tmpUnlockMessage = null;
          } else {
            _tmpUnlockMessage = _stmt.getText(_columnIndexOfUnlockMessage);
          }
          final boolean _tmpIsUnlocked;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsUnlocked));
          _tmpIsUnlocked = _tmp_1 != 0;
          final int _tmpCurrentProgress;
          _tmpCurrentProgress = (int) (_stmt.getLong(_columnIndexOfCurrentProgress));
          final Long _tmpUnlockedAt;
          if (_stmt.isNull(_columnIndexOfUnlockedAt)) {
            _tmpUnlockedAt = null;
          } else {
            _tmpUnlockedAt = _stmt.getLong(_columnIndexOfUnlockedAt);
          }
          _item = new Achievement(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpDescriptionEnglish,_tmpDescriptionHindi,_tmpIconResId,_tmpBadgeImageResId,_tmpColorHex,_tmpCategory,_tmpType,_tmpMaxProgress,_tmpIsHidden,_tmpTriggerConditions,_tmpPointsRewarded,_tmpUnlockMessage,_tmpIsUnlocked,_tmpCurrentProgress,_tmpUnlockedAt);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Flow<List<Achievement>> getLockedAchievements() {
    final String _sql = "SELECT * FROM achievements WHERE isUnlocked = 0";
    return FlowUtil.createFlow(__db, false, new String[] {"achievements"}, (_connection) -> {
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
        final int _columnIndexOfIsHidden = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isHidden");
        final int _columnIndexOfTriggerConditions = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "triggerConditions");
        final int _columnIndexOfPointsRewarded = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "pointsRewarded");
        final int _columnIndexOfUnlockMessage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlockMessage");
        final int _columnIndexOfIsUnlocked = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isUnlocked");
        final int _columnIndexOfCurrentProgress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "currentProgress");
        final int _columnIndexOfUnlockedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "unlockedAt");
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
          final boolean _tmpIsHidden;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsHidden));
          _tmpIsHidden = _tmp != 0;
          final String _tmpTriggerConditions;
          if (_stmt.isNull(_columnIndexOfTriggerConditions)) {
            _tmpTriggerConditions = null;
          } else {
            _tmpTriggerConditions = _stmt.getText(_columnIndexOfTriggerConditions);
          }
          final int _tmpPointsRewarded;
          _tmpPointsRewarded = (int) (_stmt.getLong(_columnIndexOfPointsRewarded));
          final String _tmpUnlockMessage;
          if (_stmt.isNull(_columnIndexOfUnlockMessage)) {
            _tmpUnlockMessage = null;
          } else {
            _tmpUnlockMessage = _stmt.getText(_columnIndexOfUnlockMessage);
          }
          final boolean _tmpIsUnlocked;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsUnlocked));
          _tmpIsUnlocked = _tmp_1 != 0;
          final int _tmpCurrentProgress;
          _tmpCurrentProgress = (int) (_stmt.getLong(_columnIndexOfCurrentProgress));
          final Long _tmpUnlockedAt;
          if (_stmt.isNull(_columnIndexOfUnlockedAt)) {
            _tmpUnlockedAt = null;
          } else {
            _tmpUnlockedAt = _stmt.getLong(_columnIndexOfUnlockedAt);
          }
          _item = new Achievement(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpDescriptionEnglish,_tmpDescriptionHindi,_tmpIconResId,_tmpBadgeImageResId,_tmpColorHex,_tmpCategory,_tmpType,_tmpMaxProgress,_tmpIsHidden,_tmpTriggerConditions,_tmpPointsRewarded,_tmpUnlockMessage,_tmpIsUnlocked,_tmpCurrentProgress,_tmpUnlockedAt);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Flow<Integer> getUnlockedAchievementCount() {
    final String _sql = "SELECT COUNT(*) FROM achievements WHERE isUnlocked = 1";
    return FlowUtil.createFlow(__db, false, new String[] {"achievements"}, (_connection) -> {
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
  public Object unlockAchievement(final String achievementId, final long timestamp,
      final Continuation<? super Unit> $completion) {
    final String _sql = "UPDATE achievements SET isUnlocked = 1, unlockedAt = ? WHERE id = ?";
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, timestamp);
        _argIndex = 2;
        if (achievementId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, achievementId);
        }
        _stmt.step();
        return Unit.INSTANCE;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
