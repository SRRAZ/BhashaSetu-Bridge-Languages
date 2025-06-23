package com.bhashasetu.app.data.dao;

import androidx.annotation.NonNull;
import androidx.room.EntityDeleteOrUpdateAdapter;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.coroutines.FlowUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteStatement;
import com.bhashasetu.app.data.model.DailyStreak;
import com.bhashasetu.app.data.util.DateConverter;
import java.lang.Boolean;
import java.lang.Class;
import java.lang.Integer;
import java.lang.Long;
import java.lang.NullPointerException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation", "removal"})
public final class DailyStreakDao_Impl implements DailyStreakDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<DailyStreak> __insertAdapterOfDailyStreak;

  private final EntityDeleteOrUpdateAdapter<DailyStreak> __updateAdapterOfDailyStreak;

  private final DateConverter __dateConverter = new DateConverter();

  public DailyStreakDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfDailyStreak = new EntityInsertAdapter<DailyStreak>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `daily_streaks` (`id`,`currentStreak`,`longestStreak`,`totalDaysActive`,`lastActiveDate`,`isTodayCompleted`,`streakFreezeAvailable`,`streakFreezeUsed`,`lastStreakFreezeDate`,`nextMilestone`,`milestoneReached`,`weeklyActivity`,`monthlyActivity`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final DailyStreak entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCurrentStreak());
        statement.bindLong(3, entity.getLongestStreak());
        statement.bindLong(4, entity.getTotalDaysActive());
        if (entity.getLastActiveDate() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getLastActiveDate());
        }
        final int _tmp = entity.isTodayCompleted() ? 1 : 0;
        statement.bindLong(6, _tmp);
        statement.bindLong(7, entity.getStreakFreezeAvailable());
        statement.bindLong(8, entity.getStreakFreezeUsed());
        if (entity.getLastStreakFreezeDate() == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, entity.getLastStreakFreezeDate());
        }
        statement.bindLong(10, entity.getNextMilestone());
        statement.bindLong(11, entity.getMilestoneReached());
        if (entity.getWeeklyActivity() == null) {
          statement.bindNull(12);
        } else {
          statement.bindText(12, entity.getWeeklyActivity());
        }
        if (entity.getMonthlyActivity() == null) {
          statement.bindNull(13);
        } else {
          statement.bindText(13, entity.getMonthlyActivity());
        }
      }
    };
    this.__updateAdapterOfDailyStreak = new EntityDeleteOrUpdateAdapter<DailyStreak>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `daily_streaks` SET `id` = ?,`currentStreak` = ?,`longestStreak` = ?,`totalDaysActive` = ?,`lastActiveDate` = ?,`isTodayCompleted` = ?,`streakFreezeAvailable` = ?,`streakFreezeUsed` = ?,`lastStreakFreezeDate` = ?,`nextMilestone` = ?,`milestoneReached` = ?,`weeklyActivity` = ?,`monthlyActivity` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final DailyStreak entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCurrentStreak());
        statement.bindLong(3, entity.getLongestStreak());
        statement.bindLong(4, entity.getTotalDaysActive());
        if (entity.getLastActiveDate() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getLastActiveDate());
        }
        final int _tmp = entity.isTodayCompleted() ? 1 : 0;
        statement.bindLong(6, _tmp);
        statement.bindLong(7, entity.getStreakFreezeAvailable());
        statement.bindLong(8, entity.getStreakFreezeUsed());
        if (entity.getLastStreakFreezeDate() == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, entity.getLastStreakFreezeDate());
        }
        statement.bindLong(10, entity.getNextMilestone());
        statement.bindLong(11, entity.getMilestoneReached());
        if (entity.getWeeklyActivity() == null) {
          statement.bindNull(12);
        } else {
          statement.bindText(12, entity.getWeeklyActivity());
        }
        if (entity.getMonthlyActivity() == null) {
          statement.bindNull(13);
        } else {
          statement.bindText(13, entity.getMonthlyActivity());
        }
        statement.bindLong(14, entity.getId());
      }
    };
  }

  @Override
  public Object insert(final DailyStreak dailyStreak,
      final Continuation<? super Unit> $completion) {
    if (dailyStreak == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __insertAdapterOfDailyStreak.insert(_connection, dailyStreak);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Object update(final DailyStreak dailyStreak,
      final Continuation<? super Unit> $completion) {
    if (dailyStreak == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __updateAdapterOfDailyStreak.handle(_connection, dailyStreak);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Flow<DailyStreak> getDailyStreak() {
    final String _sql = "SELECT * FROM daily_streaks WHERE id = 1";
    return FlowUtil.createFlow(__db, false, new String[] {"daily_streaks"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfCurrentStreak = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "currentStreak");
        final int _columnIndexOfLongestStreak = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "longestStreak");
        final int _columnIndexOfTotalDaysActive = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalDaysActive");
        final int _columnIndexOfLastActiveDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastActiveDate");
        final int _columnIndexOfIsTodayCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isTodayCompleted");
        final int _columnIndexOfStreakFreezeAvailable = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "streakFreezeAvailable");
        final int _columnIndexOfStreakFreezeUsed = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "streakFreezeUsed");
        final int _columnIndexOfLastStreakFreezeDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastStreakFreezeDate");
        final int _columnIndexOfNextMilestone = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nextMilestone");
        final int _columnIndexOfMilestoneReached = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "milestoneReached");
        final int _columnIndexOfWeeklyActivity = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "weeklyActivity");
        final int _columnIndexOfMonthlyActivity = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "monthlyActivity");
        final DailyStreak _result;
        if (_stmt.step()) {
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          final int _tmpCurrentStreak;
          _tmpCurrentStreak = (int) (_stmt.getLong(_columnIndexOfCurrentStreak));
          final int _tmpLongestStreak;
          _tmpLongestStreak = (int) (_stmt.getLong(_columnIndexOfLongestStreak));
          final int _tmpTotalDaysActive;
          _tmpTotalDaysActive = (int) (_stmt.getLong(_columnIndexOfTotalDaysActive));
          final Long _tmpLastActiveDate;
          if (_stmt.isNull(_columnIndexOfLastActiveDate)) {
            _tmpLastActiveDate = null;
          } else {
            _tmpLastActiveDate = _stmt.getLong(_columnIndexOfLastActiveDate);
          }
          final boolean _tmpIsTodayCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsTodayCompleted));
          _tmpIsTodayCompleted = _tmp != 0;
          final int _tmpStreakFreezeAvailable;
          _tmpStreakFreezeAvailable = (int) (_stmt.getLong(_columnIndexOfStreakFreezeAvailable));
          final int _tmpStreakFreezeUsed;
          _tmpStreakFreezeUsed = (int) (_stmt.getLong(_columnIndexOfStreakFreezeUsed));
          final Long _tmpLastStreakFreezeDate;
          if (_stmt.isNull(_columnIndexOfLastStreakFreezeDate)) {
            _tmpLastStreakFreezeDate = null;
          } else {
            _tmpLastStreakFreezeDate = _stmt.getLong(_columnIndexOfLastStreakFreezeDate);
          }
          final int _tmpNextMilestone;
          _tmpNextMilestone = (int) (_stmt.getLong(_columnIndexOfNextMilestone));
          final int _tmpMilestoneReached;
          _tmpMilestoneReached = (int) (_stmt.getLong(_columnIndexOfMilestoneReached));
          final String _tmpWeeklyActivity;
          if (_stmt.isNull(_columnIndexOfWeeklyActivity)) {
            _tmpWeeklyActivity = null;
          } else {
            _tmpWeeklyActivity = _stmt.getText(_columnIndexOfWeeklyActivity);
          }
          final String _tmpMonthlyActivity;
          if (_stmt.isNull(_columnIndexOfMonthlyActivity)) {
            _tmpMonthlyActivity = null;
          } else {
            _tmpMonthlyActivity = _stmt.getText(_columnIndexOfMonthlyActivity);
          }
          _result = new DailyStreak(_tmpId,_tmpCurrentStreak,_tmpLongestStreak,_tmpTotalDaysActive,_tmpLastActiveDate,_tmpIsTodayCompleted,_tmpStreakFreezeAvailable,_tmpStreakFreezeUsed,_tmpLastStreakFreezeDate,_tmpNextMilestone,_tmpMilestoneReached,_tmpWeeklyActivity,_tmpMonthlyActivity);
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
  public Flow<Boolean> hasCheckedInToday() {
    final String _sql = "SELECT EXISTS(SELECT 1 FROM daily_streaks WHERE id = 1 AND date(lastActiveDate/1000, 'unixepoch') = date('now'))";
    return FlowUtil.createFlow(__db, false, new String[] {"daily_streaks"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final Boolean _result;
        if (_stmt.step()) {
          final Integer _tmp;
          if (_stmt.isNull(0)) {
            _tmp = null;
          } else {
            _tmp = (int) (_stmt.getLong(0));
          }
          _result = _tmp == null ? null : _tmp != 0;
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
  public Object updateStreak(final int newStreak, final Date checkInDate,
      final Continuation<? super Unit> $completion) {
    final String _sql = "UPDATE daily_streaks SET currentStreak = ?, lastActiveDate = ? WHERE id = 1";
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, newStreak);
        _argIndex = 2;
        final Long _tmp = __dateConverter.dateToTimestamp(checkInDate);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp);
        }
        _stmt.step();
        return Unit.INSTANCE;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object updateLongestStreak(final int longestStreak,
      final Continuation<? super Unit> $completion) {
    final String _sql = "UPDATE daily_streaks SET longestStreak = ? WHERE id = 1 AND longestStreak < ?";
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, longestStreak);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, longestStreak);
        _stmt.step();
        return Unit.INSTANCE;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object resetStreak(final Date checkInDate, final Continuation<? super Unit> $completion) {
    final String _sql = "UPDATE daily_streaks SET currentStreak = 1, lastActiveDate = ? WHERE id = 1";
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        final Long _tmp = __dateConverter.dateToTimestamp(checkInDate);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp);
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
