package com.bhashasetu.app.data.dao;

import androidx.annotation.NonNull;
import androidx.room.EntityDeleteOrUpdateAdapter;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.coroutines.FlowUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteStatement;
import com.bhashasetu.app.data.model.UserGoal;
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
public final class UserGoalDao_AppDatabase_0_Impl implements UserGoalDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<UserGoal> __insertAdapterOfUserGoal;

  private final EntityDeleteOrUpdateAdapter<UserGoal> __deleteAdapterOfUserGoal;

  private final EntityDeleteOrUpdateAdapter<UserGoal> __updateAdapterOfUserGoal;

  public UserGoalDao_AppDatabase_0_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfUserGoal = new EntityInsertAdapter<UserGoal>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `user_goals` (`id`,`title`,`description`,`goalType`,`targetValue`,`currentValue`,`categoryId`,`periodType`,`startDate`,`endDate`,`repeatDaily`,`reminderEnabled`,`reminderTime`,`completed`,`completedAt`,`isActive`,`createdAt`,`lastUpdatedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final UserGoal entity) {
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
        if (entity.getGoalType() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getGoalType());
        }
        statement.bindLong(5, entity.getTargetValue());
        statement.bindLong(6, entity.getCurrentValue());
        if (entity.getCategoryId() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getCategoryId());
        }
        if (entity.getPeriodType() == null) {
          statement.bindNull(8);
        } else {
          statement.bindText(8, entity.getPeriodType());
        }
        statement.bindLong(9, entity.getStartDate());
        if (entity.getEndDate() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getEndDate());
        }
        final int _tmp = entity.getRepeatDaily() ? 1 : 0;
        statement.bindLong(11, _tmp);
        final int _tmp_1 = entity.getReminderEnabled() ? 1 : 0;
        statement.bindLong(12, _tmp_1);
        if (entity.getReminderTime() == null) {
          statement.bindNull(13);
        } else {
          statement.bindText(13, entity.getReminderTime());
        }
        final int _tmp_2 = entity.getCompleted() ? 1 : 0;
        statement.bindLong(14, _tmp_2);
        if (entity.getCompletedAt() == null) {
          statement.bindNull(15);
        } else {
          statement.bindLong(15, entity.getCompletedAt());
        }
        final int _tmp_3 = entity.isActive() ? 1 : 0;
        statement.bindLong(16, _tmp_3);
        statement.bindLong(17, entity.getCreatedAt());
        statement.bindLong(18, entity.getLastUpdatedAt());
      }
    };
    this.__deleteAdapterOfUserGoal = new EntityDeleteOrUpdateAdapter<UserGoal>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `user_goals` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final UserGoal entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfUserGoal = new EntityDeleteOrUpdateAdapter<UserGoal>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `user_goals` SET `id` = ?,`title` = ?,`description` = ?,`goalType` = ?,`targetValue` = ?,`currentValue` = ?,`categoryId` = ?,`periodType` = ?,`startDate` = ?,`endDate` = ?,`repeatDaily` = ?,`reminderEnabled` = ?,`reminderTime` = ?,`completed` = ?,`completedAt` = ?,`isActive` = ?,`createdAt` = ?,`lastUpdatedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final UserGoal entity) {
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
        if (entity.getGoalType() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getGoalType());
        }
        statement.bindLong(5, entity.getTargetValue());
        statement.bindLong(6, entity.getCurrentValue());
        if (entity.getCategoryId() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getCategoryId());
        }
        if (entity.getPeriodType() == null) {
          statement.bindNull(8);
        } else {
          statement.bindText(8, entity.getPeriodType());
        }
        statement.bindLong(9, entity.getStartDate());
        if (entity.getEndDate() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getEndDate());
        }
        final int _tmp = entity.getRepeatDaily() ? 1 : 0;
        statement.bindLong(11, _tmp);
        final int _tmp_1 = entity.getReminderEnabled() ? 1 : 0;
        statement.bindLong(12, _tmp_1);
        if (entity.getReminderTime() == null) {
          statement.bindNull(13);
        } else {
          statement.bindText(13, entity.getReminderTime());
        }
        final int _tmp_2 = entity.getCompleted() ? 1 : 0;
        statement.bindLong(14, _tmp_2);
        if (entity.getCompletedAt() == null) {
          statement.bindNull(15);
        } else {
          statement.bindLong(15, entity.getCompletedAt());
        }
        final int _tmp_3 = entity.isActive() ? 1 : 0;
        statement.bindLong(16, _tmp_3);
        statement.bindLong(17, entity.getCreatedAt());
        statement.bindLong(18, entity.getLastUpdatedAt());
        statement.bindLong(19, entity.getId());
      }
    };
  }

  @Override
  public Object insert(final UserGoal userGoal, final Continuation<? super Long> $completion) {
    if (userGoal == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      return __insertAdapterOfUserGoal.insertAndReturnId(_connection, userGoal);
    }, $completion);
  }

  @Override
  public Object delete(final UserGoal userGoal, final Continuation<? super Unit> $completion) {
    if (userGoal == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __deleteAdapterOfUserGoal.handle(_connection, userGoal);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Object update(final UserGoal userGoal, final Continuation<? super Unit> $completion) {
    if (userGoal == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __updateAdapterOfUserGoal.handle(_connection, userGoal);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Flow<List<UserGoal>> getAllUserGoals() {
    final String _sql = "SELECT * FROM user_goals ORDER BY endDate ASC";
    return FlowUtil.createFlow(__db, false, new String[] {"user_goals"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "title");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfGoalType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "goalType");
        final int _columnIndexOfTargetValue = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "targetValue");
        final int _columnIndexOfCurrentValue = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "currentValue");
        final int _columnIndexOfCategoryId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "categoryId");
        final int _columnIndexOfPeriodType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "periodType");
        final int _columnIndexOfStartDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "startDate");
        final int _columnIndexOfEndDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "endDate");
        final int _columnIndexOfRepeatDaily = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "repeatDaily");
        final int _columnIndexOfReminderEnabled = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "reminderEnabled");
        final int _columnIndexOfReminderTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "reminderTime");
        final int _columnIndexOfCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completed");
        final int _columnIndexOfCompletedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAt");
        final int _columnIndexOfIsActive = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isActive");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _columnIndexOfLastUpdatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastUpdatedAt");
        final List<UserGoal> _result = new ArrayList<UserGoal>();
        while (_stmt.step()) {
          final UserGoal _item;
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final String _tmpTitle;
          if (_stmt.isNull(_columnIndexOfTitle)) {
            _tmpTitle = null;
          } else {
            _tmpTitle = _stmt.getText(_columnIndexOfTitle);
          }
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          final String _tmpGoalType;
          if (_stmt.isNull(_columnIndexOfGoalType)) {
            _tmpGoalType = null;
          } else {
            _tmpGoalType = _stmt.getText(_columnIndexOfGoalType);
          }
          final int _tmpTargetValue;
          _tmpTargetValue = (int) (_stmt.getLong(_columnIndexOfTargetValue));
          final int _tmpCurrentValue;
          _tmpCurrentValue = (int) (_stmt.getLong(_columnIndexOfCurrentValue));
          final Long _tmpCategoryId;
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null;
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId);
          }
          final String _tmpPeriodType;
          if (_stmt.isNull(_columnIndexOfPeriodType)) {
            _tmpPeriodType = null;
          } else {
            _tmpPeriodType = _stmt.getText(_columnIndexOfPeriodType);
          }
          final long _tmpStartDate;
          _tmpStartDate = _stmt.getLong(_columnIndexOfStartDate);
          final Long _tmpEndDate;
          if (_stmt.isNull(_columnIndexOfEndDate)) {
            _tmpEndDate = null;
          } else {
            _tmpEndDate = _stmt.getLong(_columnIndexOfEndDate);
          }
          final boolean _tmpRepeatDaily;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfRepeatDaily));
          _tmpRepeatDaily = _tmp != 0;
          final boolean _tmpReminderEnabled;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfReminderEnabled));
          _tmpReminderEnabled = _tmp_1 != 0;
          final String _tmpReminderTime;
          if (_stmt.isNull(_columnIndexOfReminderTime)) {
            _tmpReminderTime = null;
          } else {
            _tmpReminderTime = _stmt.getText(_columnIndexOfReminderTime);
          }
          final boolean _tmpCompleted;
          final int _tmp_2;
          _tmp_2 = (int) (_stmt.getLong(_columnIndexOfCompleted));
          _tmpCompleted = _tmp_2 != 0;
          final Long _tmpCompletedAt;
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmpCompletedAt = null;
          } else {
            _tmpCompletedAt = _stmt.getLong(_columnIndexOfCompletedAt);
          }
          final boolean _tmpIsActive;
          final int _tmp_3;
          _tmp_3 = (int) (_stmt.getLong(_columnIndexOfIsActive));
          _tmpIsActive = _tmp_3 != 0;
          final long _tmpCreatedAt;
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt);
          final long _tmpLastUpdatedAt;
          _tmpLastUpdatedAt = _stmt.getLong(_columnIndexOfLastUpdatedAt);
          _item = new UserGoal(_tmpId,_tmpTitle,_tmpDescription,_tmpGoalType,_tmpTargetValue,_tmpCurrentValue,_tmpCategoryId,_tmpPeriodType,_tmpStartDate,_tmpEndDate,_tmpRepeatDaily,_tmpReminderEnabled,_tmpReminderTime,_tmpCompleted,_tmpCompletedAt,_tmpIsActive,_tmpCreatedAt,_tmpLastUpdatedAt);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Flow<UserGoal> getUserGoalById(final long id) {
    final String _sql = "SELECT * FROM user_goals WHERE id = ?";
    return FlowUtil.createFlow(__db, false, new String[] {"user_goals"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "title");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfGoalType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "goalType");
        final int _columnIndexOfTargetValue = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "targetValue");
        final int _columnIndexOfCurrentValue = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "currentValue");
        final int _columnIndexOfCategoryId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "categoryId");
        final int _columnIndexOfPeriodType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "periodType");
        final int _columnIndexOfStartDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "startDate");
        final int _columnIndexOfEndDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "endDate");
        final int _columnIndexOfRepeatDaily = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "repeatDaily");
        final int _columnIndexOfReminderEnabled = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "reminderEnabled");
        final int _columnIndexOfReminderTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "reminderTime");
        final int _columnIndexOfCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completed");
        final int _columnIndexOfCompletedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAt");
        final int _columnIndexOfIsActive = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isActive");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _columnIndexOfLastUpdatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastUpdatedAt");
        final UserGoal _result;
        if (_stmt.step()) {
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final String _tmpTitle;
          if (_stmt.isNull(_columnIndexOfTitle)) {
            _tmpTitle = null;
          } else {
            _tmpTitle = _stmt.getText(_columnIndexOfTitle);
          }
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          final String _tmpGoalType;
          if (_stmt.isNull(_columnIndexOfGoalType)) {
            _tmpGoalType = null;
          } else {
            _tmpGoalType = _stmt.getText(_columnIndexOfGoalType);
          }
          final int _tmpTargetValue;
          _tmpTargetValue = (int) (_stmt.getLong(_columnIndexOfTargetValue));
          final int _tmpCurrentValue;
          _tmpCurrentValue = (int) (_stmt.getLong(_columnIndexOfCurrentValue));
          final Long _tmpCategoryId;
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null;
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId);
          }
          final String _tmpPeriodType;
          if (_stmt.isNull(_columnIndexOfPeriodType)) {
            _tmpPeriodType = null;
          } else {
            _tmpPeriodType = _stmt.getText(_columnIndexOfPeriodType);
          }
          final long _tmpStartDate;
          _tmpStartDate = _stmt.getLong(_columnIndexOfStartDate);
          final Long _tmpEndDate;
          if (_stmt.isNull(_columnIndexOfEndDate)) {
            _tmpEndDate = null;
          } else {
            _tmpEndDate = _stmt.getLong(_columnIndexOfEndDate);
          }
          final boolean _tmpRepeatDaily;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfRepeatDaily));
          _tmpRepeatDaily = _tmp != 0;
          final boolean _tmpReminderEnabled;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfReminderEnabled));
          _tmpReminderEnabled = _tmp_1 != 0;
          final String _tmpReminderTime;
          if (_stmt.isNull(_columnIndexOfReminderTime)) {
            _tmpReminderTime = null;
          } else {
            _tmpReminderTime = _stmt.getText(_columnIndexOfReminderTime);
          }
          final boolean _tmpCompleted;
          final int _tmp_2;
          _tmp_2 = (int) (_stmt.getLong(_columnIndexOfCompleted));
          _tmpCompleted = _tmp_2 != 0;
          final Long _tmpCompletedAt;
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmpCompletedAt = null;
          } else {
            _tmpCompletedAt = _stmt.getLong(_columnIndexOfCompletedAt);
          }
          final boolean _tmpIsActive;
          final int _tmp_3;
          _tmp_3 = (int) (_stmt.getLong(_columnIndexOfIsActive));
          _tmpIsActive = _tmp_3 != 0;
          final long _tmpCreatedAt;
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt);
          final long _tmpLastUpdatedAt;
          _tmpLastUpdatedAt = _stmt.getLong(_columnIndexOfLastUpdatedAt);
          _result = new UserGoal(_tmpId,_tmpTitle,_tmpDescription,_tmpGoalType,_tmpTargetValue,_tmpCurrentValue,_tmpCategoryId,_tmpPeriodType,_tmpStartDate,_tmpEndDate,_tmpRepeatDaily,_tmpReminderEnabled,_tmpReminderTime,_tmpCompleted,_tmpCompletedAt,_tmpIsActive,_tmpCreatedAt,_tmpLastUpdatedAt);
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
  public Flow<List<UserGoal>> getActiveUserGoals() {
    final String _sql = "SELECT * FROM user_goals WHERE completed = 0 ORDER BY endDate ASC";
    return FlowUtil.createFlow(__db, false, new String[] {"user_goals"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "title");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfGoalType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "goalType");
        final int _columnIndexOfTargetValue = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "targetValue");
        final int _columnIndexOfCurrentValue = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "currentValue");
        final int _columnIndexOfCategoryId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "categoryId");
        final int _columnIndexOfPeriodType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "periodType");
        final int _columnIndexOfStartDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "startDate");
        final int _columnIndexOfEndDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "endDate");
        final int _columnIndexOfRepeatDaily = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "repeatDaily");
        final int _columnIndexOfReminderEnabled = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "reminderEnabled");
        final int _columnIndexOfReminderTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "reminderTime");
        final int _columnIndexOfCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completed");
        final int _columnIndexOfCompletedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAt");
        final int _columnIndexOfIsActive = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isActive");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _columnIndexOfLastUpdatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastUpdatedAt");
        final List<UserGoal> _result = new ArrayList<UserGoal>();
        while (_stmt.step()) {
          final UserGoal _item;
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final String _tmpTitle;
          if (_stmt.isNull(_columnIndexOfTitle)) {
            _tmpTitle = null;
          } else {
            _tmpTitle = _stmt.getText(_columnIndexOfTitle);
          }
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          final String _tmpGoalType;
          if (_stmt.isNull(_columnIndexOfGoalType)) {
            _tmpGoalType = null;
          } else {
            _tmpGoalType = _stmt.getText(_columnIndexOfGoalType);
          }
          final int _tmpTargetValue;
          _tmpTargetValue = (int) (_stmt.getLong(_columnIndexOfTargetValue));
          final int _tmpCurrentValue;
          _tmpCurrentValue = (int) (_stmt.getLong(_columnIndexOfCurrentValue));
          final Long _tmpCategoryId;
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null;
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId);
          }
          final String _tmpPeriodType;
          if (_stmt.isNull(_columnIndexOfPeriodType)) {
            _tmpPeriodType = null;
          } else {
            _tmpPeriodType = _stmt.getText(_columnIndexOfPeriodType);
          }
          final long _tmpStartDate;
          _tmpStartDate = _stmt.getLong(_columnIndexOfStartDate);
          final Long _tmpEndDate;
          if (_stmt.isNull(_columnIndexOfEndDate)) {
            _tmpEndDate = null;
          } else {
            _tmpEndDate = _stmt.getLong(_columnIndexOfEndDate);
          }
          final boolean _tmpRepeatDaily;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfRepeatDaily));
          _tmpRepeatDaily = _tmp != 0;
          final boolean _tmpReminderEnabled;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfReminderEnabled));
          _tmpReminderEnabled = _tmp_1 != 0;
          final String _tmpReminderTime;
          if (_stmt.isNull(_columnIndexOfReminderTime)) {
            _tmpReminderTime = null;
          } else {
            _tmpReminderTime = _stmt.getText(_columnIndexOfReminderTime);
          }
          final boolean _tmpCompleted;
          final int _tmp_2;
          _tmp_2 = (int) (_stmt.getLong(_columnIndexOfCompleted));
          _tmpCompleted = _tmp_2 != 0;
          final Long _tmpCompletedAt;
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmpCompletedAt = null;
          } else {
            _tmpCompletedAt = _stmt.getLong(_columnIndexOfCompletedAt);
          }
          final boolean _tmpIsActive;
          final int _tmp_3;
          _tmp_3 = (int) (_stmt.getLong(_columnIndexOfIsActive));
          _tmpIsActive = _tmp_3 != 0;
          final long _tmpCreatedAt;
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt);
          final long _tmpLastUpdatedAt;
          _tmpLastUpdatedAt = _stmt.getLong(_columnIndexOfLastUpdatedAt);
          _item = new UserGoal(_tmpId,_tmpTitle,_tmpDescription,_tmpGoalType,_tmpTargetValue,_tmpCurrentValue,_tmpCategoryId,_tmpPeriodType,_tmpStartDate,_tmpEndDate,_tmpRepeatDaily,_tmpReminderEnabled,_tmpReminderTime,_tmpCompleted,_tmpCompletedAt,_tmpIsActive,_tmpCreatedAt,_tmpLastUpdatedAt);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Flow<List<UserGoal>> getCompletedUserGoals() {
    final String _sql = "SELECT * FROM user_goals WHERE completed = 1 ORDER BY completedAt DESC";
    return FlowUtil.createFlow(__db, false, new String[] {"user_goals"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "title");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfGoalType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "goalType");
        final int _columnIndexOfTargetValue = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "targetValue");
        final int _columnIndexOfCurrentValue = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "currentValue");
        final int _columnIndexOfCategoryId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "categoryId");
        final int _columnIndexOfPeriodType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "periodType");
        final int _columnIndexOfStartDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "startDate");
        final int _columnIndexOfEndDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "endDate");
        final int _columnIndexOfRepeatDaily = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "repeatDaily");
        final int _columnIndexOfReminderEnabled = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "reminderEnabled");
        final int _columnIndexOfReminderTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "reminderTime");
        final int _columnIndexOfCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completed");
        final int _columnIndexOfCompletedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAt");
        final int _columnIndexOfIsActive = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isActive");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _columnIndexOfLastUpdatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastUpdatedAt");
        final List<UserGoal> _result = new ArrayList<UserGoal>();
        while (_stmt.step()) {
          final UserGoal _item;
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final String _tmpTitle;
          if (_stmt.isNull(_columnIndexOfTitle)) {
            _tmpTitle = null;
          } else {
            _tmpTitle = _stmt.getText(_columnIndexOfTitle);
          }
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          final String _tmpGoalType;
          if (_stmt.isNull(_columnIndexOfGoalType)) {
            _tmpGoalType = null;
          } else {
            _tmpGoalType = _stmt.getText(_columnIndexOfGoalType);
          }
          final int _tmpTargetValue;
          _tmpTargetValue = (int) (_stmt.getLong(_columnIndexOfTargetValue));
          final int _tmpCurrentValue;
          _tmpCurrentValue = (int) (_stmt.getLong(_columnIndexOfCurrentValue));
          final Long _tmpCategoryId;
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null;
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId);
          }
          final String _tmpPeriodType;
          if (_stmt.isNull(_columnIndexOfPeriodType)) {
            _tmpPeriodType = null;
          } else {
            _tmpPeriodType = _stmt.getText(_columnIndexOfPeriodType);
          }
          final long _tmpStartDate;
          _tmpStartDate = _stmt.getLong(_columnIndexOfStartDate);
          final Long _tmpEndDate;
          if (_stmt.isNull(_columnIndexOfEndDate)) {
            _tmpEndDate = null;
          } else {
            _tmpEndDate = _stmt.getLong(_columnIndexOfEndDate);
          }
          final boolean _tmpRepeatDaily;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfRepeatDaily));
          _tmpRepeatDaily = _tmp != 0;
          final boolean _tmpReminderEnabled;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfReminderEnabled));
          _tmpReminderEnabled = _tmp_1 != 0;
          final String _tmpReminderTime;
          if (_stmt.isNull(_columnIndexOfReminderTime)) {
            _tmpReminderTime = null;
          } else {
            _tmpReminderTime = _stmt.getText(_columnIndexOfReminderTime);
          }
          final boolean _tmpCompleted;
          final int _tmp_2;
          _tmp_2 = (int) (_stmt.getLong(_columnIndexOfCompleted));
          _tmpCompleted = _tmp_2 != 0;
          final Long _tmpCompletedAt;
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmpCompletedAt = null;
          } else {
            _tmpCompletedAt = _stmt.getLong(_columnIndexOfCompletedAt);
          }
          final boolean _tmpIsActive;
          final int _tmp_3;
          _tmp_3 = (int) (_stmt.getLong(_columnIndexOfIsActive));
          _tmpIsActive = _tmp_3 != 0;
          final long _tmpCreatedAt;
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt);
          final long _tmpLastUpdatedAt;
          _tmpLastUpdatedAt = _stmt.getLong(_columnIndexOfLastUpdatedAt);
          _item = new UserGoal(_tmpId,_tmpTitle,_tmpDescription,_tmpGoalType,_tmpTargetValue,_tmpCurrentValue,_tmpCategoryId,_tmpPeriodType,_tmpStartDate,_tmpEndDate,_tmpRepeatDaily,_tmpReminderEnabled,_tmpReminderTime,_tmpCompleted,_tmpCompletedAt,_tmpIsActive,_tmpCreatedAt,_tmpLastUpdatedAt);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Flow<UserGoal> getActiveGoalByType(final String goalType) {
    final String _sql = "SELECT * FROM user_goals WHERE goalType = ? AND completed = 0 LIMIT 1";
    return FlowUtil.createFlow(__db, false, new String[] {"user_goals"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (goalType == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, goalType);
        }
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "title");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfGoalType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "goalType");
        final int _columnIndexOfTargetValue = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "targetValue");
        final int _columnIndexOfCurrentValue = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "currentValue");
        final int _columnIndexOfCategoryId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "categoryId");
        final int _columnIndexOfPeriodType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "periodType");
        final int _columnIndexOfStartDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "startDate");
        final int _columnIndexOfEndDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "endDate");
        final int _columnIndexOfRepeatDaily = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "repeatDaily");
        final int _columnIndexOfReminderEnabled = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "reminderEnabled");
        final int _columnIndexOfReminderTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "reminderTime");
        final int _columnIndexOfCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completed");
        final int _columnIndexOfCompletedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAt");
        final int _columnIndexOfIsActive = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isActive");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _columnIndexOfLastUpdatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastUpdatedAt");
        final UserGoal _result;
        if (_stmt.step()) {
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final String _tmpTitle;
          if (_stmt.isNull(_columnIndexOfTitle)) {
            _tmpTitle = null;
          } else {
            _tmpTitle = _stmt.getText(_columnIndexOfTitle);
          }
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          final String _tmpGoalType;
          if (_stmt.isNull(_columnIndexOfGoalType)) {
            _tmpGoalType = null;
          } else {
            _tmpGoalType = _stmt.getText(_columnIndexOfGoalType);
          }
          final int _tmpTargetValue;
          _tmpTargetValue = (int) (_stmt.getLong(_columnIndexOfTargetValue));
          final int _tmpCurrentValue;
          _tmpCurrentValue = (int) (_stmt.getLong(_columnIndexOfCurrentValue));
          final Long _tmpCategoryId;
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null;
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId);
          }
          final String _tmpPeriodType;
          if (_stmt.isNull(_columnIndexOfPeriodType)) {
            _tmpPeriodType = null;
          } else {
            _tmpPeriodType = _stmt.getText(_columnIndexOfPeriodType);
          }
          final long _tmpStartDate;
          _tmpStartDate = _stmt.getLong(_columnIndexOfStartDate);
          final Long _tmpEndDate;
          if (_stmt.isNull(_columnIndexOfEndDate)) {
            _tmpEndDate = null;
          } else {
            _tmpEndDate = _stmt.getLong(_columnIndexOfEndDate);
          }
          final boolean _tmpRepeatDaily;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfRepeatDaily));
          _tmpRepeatDaily = _tmp != 0;
          final boolean _tmpReminderEnabled;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfReminderEnabled));
          _tmpReminderEnabled = _tmp_1 != 0;
          final String _tmpReminderTime;
          if (_stmt.isNull(_columnIndexOfReminderTime)) {
            _tmpReminderTime = null;
          } else {
            _tmpReminderTime = _stmt.getText(_columnIndexOfReminderTime);
          }
          final boolean _tmpCompleted;
          final int _tmp_2;
          _tmp_2 = (int) (_stmt.getLong(_columnIndexOfCompleted));
          _tmpCompleted = _tmp_2 != 0;
          final Long _tmpCompletedAt;
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmpCompletedAt = null;
          } else {
            _tmpCompletedAt = _stmt.getLong(_columnIndexOfCompletedAt);
          }
          final boolean _tmpIsActive;
          final int _tmp_3;
          _tmp_3 = (int) (_stmt.getLong(_columnIndexOfIsActive));
          _tmpIsActive = _tmp_3 != 0;
          final long _tmpCreatedAt;
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt);
          final long _tmpLastUpdatedAt;
          _tmpLastUpdatedAt = _stmt.getLong(_columnIndexOfLastUpdatedAt);
          _result = new UserGoal(_tmpId,_tmpTitle,_tmpDescription,_tmpGoalType,_tmpTargetValue,_tmpCurrentValue,_tmpCategoryId,_tmpPeriodType,_tmpStartDate,_tmpEndDate,_tmpRepeatDaily,_tmpReminderEnabled,_tmpReminderTime,_tmpCompleted,_tmpCompletedAt,_tmpIsActive,_tmpCreatedAt,_tmpLastUpdatedAt);
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
  public Flow<List<UserGoal>> getGoalsByPeriod(final String periodType) {
    final String _sql = "SELECT * FROM user_goals WHERE periodType = ? AND isActive = 1";
    return FlowUtil.createFlow(__db, false, new String[] {"user_goals"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (periodType == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, periodType);
        }
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "title");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfGoalType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "goalType");
        final int _columnIndexOfTargetValue = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "targetValue");
        final int _columnIndexOfCurrentValue = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "currentValue");
        final int _columnIndexOfCategoryId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "categoryId");
        final int _columnIndexOfPeriodType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "periodType");
        final int _columnIndexOfStartDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "startDate");
        final int _columnIndexOfEndDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "endDate");
        final int _columnIndexOfRepeatDaily = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "repeatDaily");
        final int _columnIndexOfReminderEnabled = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "reminderEnabled");
        final int _columnIndexOfReminderTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "reminderTime");
        final int _columnIndexOfCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completed");
        final int _columnIndexOfCompletedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAt");
        final int _columnIndexOfIsActive = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isActive");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _columnIndexOfLastUpdatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastUpdatedAt");
        final List<UserGoal> _result = new ArrayList<UserGoal>();
        while (_stmt.step()) {
          final UserGoal _item;
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final String _tmpTitle;
          if (_stmt.isNull(_columnIndexOfTitle)) {
            _tmpTitle = null;
          } else {
            _tmpTitle = _stmt.getText(_columnIndexOfTitle);
          }
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          final String _tmpGoalType;
          if (_stmt.isNull(_columnIndexOfGoalType)) {
            _tmpGoalType = null;
          } else {
            _tmpGoalType = _stmt.getText(_columnIndexOfGoalType);
          }
          final int _tmpTargetValue;
          _tmpTargetValue = (int) (_stmt.getLong(_columnIndexOfTargetValue));
          final int _tmpCurrentValue;
          _tmpCurrentValue = (int) (_stmt.getLong(_columnIndexOfCurrentValue));
          final Long _tmpCategoryId;
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null;
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId);
          }
          final String _tmpPeriodType;
          if (_stmt.isNull(_columnIndexOfPeriodType)) {
            _tmpPeriodType = null;
          } else {
            _tmpPeriodType = _stmt.getText(_columnIndexOfPeriodType);
          }
          final long _tmpStartDate;
          _tmpStartDate = _stmt.getLong(_columnIndexOfStartDate);
          final Long _tmpEndDate;
          if (_stmt.isNull(_columnIndexOfEndDate)) {
            _tmpEndDate = null;
          } else {
            _tmpEndDate = _stmt.getLong(_columnIndexOfEndDate);
          }
          final boolean _tmpRepeatDaily;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfRepeatDaily));
          _tmpRepeatDaily = _tmp != 0;
          final boolean _tmpReminderEnabled;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfReminderEnabled));
          _tmpReminderEnabled = _tmp_1 != 0;
          final String _tmpReminderTime;
          if (_stmt.isNull(_columnIndexOfReminderTime)) {
            _tmpReminderTime = null;
          } else {
            _tmpReminderTime = _stmt.getText(_columnIndexOfReminderTime);
          }
          final boolean _tmpCompleted;
          final int _tmp_2;
          _tmp_2 = (int) (_stmt.getLong(_columnIndexOfCompleted));
          _tmpCompleted = _tmp_2 != 0;
          final Long _tmpCompletedAt;
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmpCompletedAt = null;
          } else {
            _tmpCompletedAt = _stmt.getLong(_columnIndexOfCompletedAt);
          }
          final boolean _tmpIsActive;
          final int _tmp_3;
          _tmp_3 = (int) (_stmt.getLong(_columnIndexOfIsActive));
          _tmpIsActive = _tmp_3 != 0;
          final long _tmpCreatedAt;
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt);
          final long _tmpLastUpdatedAt;
          _tmpLastUpdatedAt = _stmt.getLong(_columnIndexOfLastUpdatedAt);
          _item = new UserGoal(_tmpId,_tmpTitle,_tmpDescription,_tmpGoalType,_tmpTargetValue,_tmpCurrentValue,_tmpCategoryId,_tmpPeriodType,_tmpStartDate,_tmpEndDate,_tmpRepeatDaily,_tmpReminderEnabled,_tmpReminderTime,_tmpCompleted,_tmpCompletedAt,_tmpIsActive,_tmpCreatedAt,_tmpLastUpdatedAt);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Flow<List<UserGoal>> getGoalsByCategory(final long categoryId) {
    final String _sql = "SELECT * FROM user_goals WHERE categoryId = ? AND isActive = 1";
    return FlowUtil.createFlow(__db, false, new String[] {"user_goals"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, categoryId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "title");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfGoalType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "goalType");
        final int _columnIndexOfTargetValue = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "targetValue");
        final int _columnIndexOfCurrentValue = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "currentValue");
        final int _columnIndexOfCategoryId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "categoryId");
        final int _columnIndexOfPeriodType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "periodType");
        final int _columnIndexOfStartDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "startDate");
        final int _columnIndexOfEndDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "endDate");
        final int _columnIndexOfRepeatDaily = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "repeatDaily");
        final int _columnIndexOfReminderEnabled = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "reminderEnabled");
        final int _columnIndexOfReminderTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "reminderTime");
        final int _columnIndexOfCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completed");
        final int _columnIndexOfCompletedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAt");
        final int _columnIndexOfIsActive = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isActive");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _columnIndexOfLastUpdatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastUpdatedAt");
        final List<UserGoal> _result = new ArrayList<UserGoal>();
        while (_stmt.step()) {
          final UserGoal _item;
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final String _tmpTitle;
          if (_stmt.isNull(_columnIndexOfTitle)) {
            _tmpTitle = null;
          } else {
            _tmpTitle = _stmt.getText(_columnIndexOfTitle);
          }
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          final String _tmpGoalType;
          if (_stmt.isNull(_columnIndexOfGoalType)) {
            _tmpGoalType = null;
          } else {
            _tmpGoalType = _stmt.getText(_columnIndexOfGoalType);
          }
          final int _tmpTargetValue;
          _tmpTargetValue = (int) (_stmt.getLong(_columnIndexOfTargetValue));
          final int _tmpCurrentValue;
          _tmpCurrentValue = (int) (_stmt.getLong(_columnIndexOfCurrentValue));
          final Long _tmpCategoryId;
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null;
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId);
          }
          final String _tmpPeriodType;
          if (_stmt.isNull(_columnIndexOfPeriodType)) {
            _tmpPeriodType = null;
          } else {
            _tmpPeriodType = _stmt.getText(_columnIndexOfPeriodType);
          }
          final long _tmpStartDate;
          _tmpStartDate = _stmt.getLong(_columnIndexOfStartDate);
          final Long _tmpEndDate;
          if (_stmt.isNull(_columnIndexOfEndDate)) {
            _tmpEndDate = null;
          } else {
            _tmpEndDate = _stmt.getLong(_columnIndexOfEndDate);
          }
          final boolean _tmpRepeatDaily;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfRepeatDaily));
          _tmpRepeatDaily = _tmp != 0;
          final boolean _tmpReminderEnabled;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfReminderEnabled));
          _tmpReminderEnabled = _tmp_1 != 0;
          final String _tmpReminderTime;
          if (_stmt.isNull(_columnIndexOfReminderTime)) {
            _tmpReminderTime = null;
          } else {
            _tmpReminderTime = _stmt.getText(_columnIndexOfReminderTime);
          }
          final boolean _tmpCompleted;
          final int _tmp_2;
          _tmp_2 = (int) (_stmt.getLong(_columnIndexOfCompleted));
          _tmpCompleted = _tmp_2 != 0;
          final Long _tmpCompletedAt;
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmpCompletedAt = null;
          } else {
            _tmpCompletedAt = _stmt.getLong(_columnIndexOfCompletedAt);
          }
          final boolean _tmpIsActive;
          final int _tmp_3;
          _tmp_3 = (int) (_stmt.getLong(_columnIndexOfIsActive));
          _tmpIsActive = _tmp_3 != 0;
          final long _tmpCreatedAt;
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt);
          final long _tmpLastUpdatedAt;
          _tmpLastUpdatedAt = _stmt.getLong(_columnIndexOfLastUpdatedAt);
          _item = new UserGoal(_tmpId,_tmpTitle,_tmpDescription,_tmpGoalType,_tmpTargetValue,_tmpCurrentValue,_tmpCategoryId,_tmpPeriodType,_tmpStartDate,_tmpEndDate,_tmpRepeatDaily,_tmpReminderEnabled,_tmpReminderTime,_tmpCompleted,_tmpCompletedAt,_tmpIsActive,_tmpCreatedAt,_tmpLastUpdatedAt);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Flow<Integer> getCompletedGoalCount() {
    final String _sql = "SELECT COUNT(*) FROM user_goals WHERE completed = 1";
    return FlowUtil.createFlow(__db, false, new String[] {"user_goals"}, (_connection) -> {
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
  public Flow<Integer> getActiveGoalCount() {
    final String _sql = "SELECT COUNT(*) FROM user_goals WHERE completed = 0 AND isActive = 1";
    return FlowUtil.createFlow(__db, false, new String[] {"user_goals"}, (_connection) -> {
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
  public Object markGoalAsCompleted(final long goalId, final long timestamp,
      final Continuation<? super Unit> $completion) {
    final String _sql = "UPDATE user_goals SET completed = 1, completedAt = ?, currentValue = targetValue WHERE id = ?";
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, timestamp);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, goalId);
        _stmt.step();
        return Unit.INSTANCE;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object updateGoalProgress(final long goalId, final int progress,
      final Continuation<? super Unit> $completion) {
    final String _sql = "UPDATE user_goals SET currentValue = ? WHERE id = ?";
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, progress);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, goalId);
        _stmt.step();
        return Unit.INSTANCE;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object deactivateGoal(final long goalId, final Continuation<? super Unit> $completion) {
    final String _sql = "UPDATE user_goals SET isActive = 0 WHERE id = ?";
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, goalId);
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
