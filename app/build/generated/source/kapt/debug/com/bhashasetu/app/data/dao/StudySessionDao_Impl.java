package com.bhashasetu.app.data.dao;

import androidx.annotation.NonNull;
import androidx.room.EntityDeleteOrUpdateAdapter;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.coroutines.FlowUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteStatement;
import com.bhashasetu.app.data.model.StudySession;
import com.bhashasetu.app.data.util.DateConverter;
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
import java.util.Date;
import java.util.List;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation", "removal"})
public final class StudySessionDao_Impl implements StudySessionDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<StudySession> __insertAdapterOfStudySession;

  private final EntityDeleteOrUpdateAdapter<StudySession> __deleteAdapterOfStudySession;

  private final EntityDeleteOrUpdateAdapter<StudySession> __updateAdapterOfStudySession;

  private final DateConverter __dateConverter = new DateConverter();

  public StudySessionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfStudySession = new EntityInsertAdapter<StudySession>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `study_sessions` (`id`,`sessionType`,`categoryId`,`lessonId`,`startTime`,`endTime`,`durationMs`,`durationMinutes`,`itemCount`,`correctCount`,`score`,`deviceInfo`,`interfaceLanguage`,`isCompleted`,`wasPaused`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final StudySession entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getSessionType() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getSessionType());
        }
        if (entity.getCategoryId() == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, entity.getCategoryId());
        }
        if (entity.getLessonId() == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, entity.getLessonId());
        }
        statement.bindLong(5, entity.getStartTime());
        if (entity.getEndTime() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getEndTime());
        }
        if (entity.getDurationMs() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getDurationMs());
        }
        if (entity.getDurationMinutes() == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, entity.getDurationMinutes());
        }
        statement.bindLong(9, entity.getItemCount());
        statement.bindLong(10, entity.getCorrectCount());
        if (entity.getScore() == null) {
          statement.bindNull(11);
        } else {
          statement.bindLong(11, entity.getScore());
        }
        if (entity.getDeviceInfo() == null) {
          statement.bindNull(12);
        } else {
          statement.bindText(12, entity.getDeviceInfo());
        }
        if (entity.getInterfaceLanguage() == null) {
          statement.bindNull(13);
        } else {
          statement.bindText(13, entity.getInterfaceLanguage());
        }
        final int _tmp = entity.isCompleted() ? 1 : 0;
        statement.bindLong(14, _tmp);
        final int _tmp_1 = entity.getWasPaused() ? 1 : 0;
        statement.bindLong(15, _tmp_1);
      }
    };
    this.__deleteAdapterOfStudySession = new EntityDeleteOrUpdateAdapter<StudySession>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `study_sessions` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final StudySession entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfStudySession = new EntityDeleteOrUpdateAdapter<StudySession>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `study_sessions` SET `id` = ?,`sessionType` = ?,`categoryId` = ?,`lessonId` = ?,`startTime` = ?,`endTime` = ?,`durationMs` = ?,`durationMinutes` = ?,`itemCount` = ?,`correctCount` = ?,`score` = ?,`deviceInfo` = ?,`interfaceLanguage` = ?,`isCompleted` = ?,`wasPaused` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final StudySession entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getSessionType() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getSessionType());
        }
        if (entity.getCategoryId() == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, entity.getCategoryId());
        }
        if (entity.getLessonId() == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, entity.getLessonId());
        }
        statement.bindLong(5, entity.getStartTime());
        if (entity.getEndTime() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getEndTime());
        }
        if (entity.getDurationMs() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getDurationMs());
        }
        if (entity.getDurationMinutes() == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, entity.getDurationMinutes());
        }
        statement.bindLong(9, entity.getItemCount());
        statement.bindLong(10, entity.getCorrectCount());
        if (entity.getScore() == null) {
          statement.bindNull(11);
        } else {
          statement.bindLong(11, entity.getScore());
        }
        if (entity.getDeviceInfo() == null) {
          statement.bindNull(12);
        } else {
          statement.bindText(12, entity.getDeviceInfo());
        }
        if (entity.getInterfaceLanguage() == null) {
          statement.bindNull(13);
        } else {
          statement.bindText(13, entity.getInterfaceLanguage());
        }
        final int _tmp = entity.isCompleted() ? 1 : 0;
        statement.bindLong(14, _tmp);
        final int _tmp_1 = entity.getWasPaused() ? 1 : 0;
        statement.bindLong(15, _tmp_1);
        statement.bindLong(16, entity.getId());
      }
    };
  }

  @Override
  public Object insert(final StudySession studySession,
      final Continuation<? super Long> $completion) {
    if (studySession == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      return __insertAdapterOfStudySession.insertAndReturnId(_connection, studySession);
    }, $completion);
  }

  @Override
  public Object delete(final StudySession studySession,
      final Continuation<? super Unit> $completion) {
    if (studySession == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __deleteAdapterOfStudySession.handle(_connection, studySession);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Object update(final StudySession studySession,
      final Continuation<? super Unit> $completion) {
    if (studySession == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __updateAdapterOfStudySession.handle(_connection, studySession);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Flow<List<StudySession>> getAllStudySessions() {
    final String _sql = "SELECT * FROM study_sessions ORDER BY startTime DESC";
    return FlowUtil.createFlow(__db, false, new String[] {"study_sessions"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfSessionType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "sessionType");
        final int _columnIndexOfCategoryId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "categoryId");
        final int _columnIndexOfLessonId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lessonId");
        final int _columnIndexOfStartTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "startTime");
        final int _columnIndexOfEndTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "endTime");
        final int _columnIndexOfDurationMs = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "durationMs");
        final int _columnIndexOfDurationMinutes = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "durationMinutes");
        final int _columnIndexOfItemCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "itemCount");
        final int _columnIndexOfCorrectCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctCount");
        final int _columnIndexOfScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "score");
        final int _columnIndexOfDeviceInfo = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "deviceInfo");
        final int _columnIndexOfInterfaceLanguage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "interfaceLanguage");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfWasPaused = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wasPaused");
        final List<StudySession> _result = new ArrayList<StudySession>();
        while (_stmt.step()) {
          final StudySession _item;
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final String _tmpSessionType;
          if (_stmt.isNull(_columnIndexOfSessionType)) {
            _tmpSessionType = null;
          } else {
            _tmpSessionType = _stmt.getText(_columnIndexOfSessionType);
          }
          final Long _tmpCategoryId;
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null;
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId);
          }
          final Long _tmpLessonId;
          if (_stmt.isNull(_columnIndexOfLessonId)) {
            _tmpLessonId = null;
          } else {
            _tmpLessonId = _stmt.getLong(_columnIndexOfLessonId);
          }
          final long _tmpStartTime;
          _tmpStartTime = _stmt.getLong(_columnIndexOfStartTime);
          final Long _tmpEndTime;
          if (_stmt.isNull(_columnIndexOfEndTime)) {
            _tmpEndTime = null;
          } else {
            _tmpEndTime = _stmt.getLong(_columnIndexOfEndTime);
          }
          final Long _tmpDurationMs;
          if (_stmt.isNull(_columnIndexOfDurationMs)) {
            _tmpDurationMs = null;
          } else {
            _tmpDurationMs = _stmt.getLong(_columnIndexOfDurationMs);
          }
          final Integer _tmpDurationMinutes;
          if (_stmt.isNull(_columnIndexOfDurationMinutes)) {
            _tmpDurationMinutes = null;
          } else {
            _tmpDurationMinutes = (int) (_stmt.getLong(_columnIndexOfDurationMinutes));
          }
          final int _tmpItemCount;
          _tmpItemCount = (int) (_stmt.getLong(_columnIndexOfItemCount));
          final int _tmpCorrectCount;
          _tmpCorrectCount = (int) (_stmt.getLong(_columnIndexOfCorrectCount));
          final Integer _tmpScore;
          if (_stmt.isNull(_columnIndexOfScore)) {
            _tmpScore = null;
          } else {
            _tmpScore = (int) (_stmt.getLong(_columnIndexOfScore));
          }
          final String _tmpDeviceInfo;
          if (_stmt.isNull(_columnIndexOfDeviceInfo)) {
            _tmpDeviceInfo = null;
          } else {
            _tmpDeviceInfo = _stmt.getText(_columnIndexOfDeviceInfo);
          }
          final String _tmpInterfaceLanguage;
          if (_stmt.isNull(_columnIndexOfInterfaceLanguage)) {
            _tmpInterfaceLanguage = null;
          } else {
            _tmpInterfaceLanguage = _stmt.getText(_columnIndexOfInterfaceLanguage);
          }
          final boolean _tmpIsCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp != 0;
          final boolean _tmpWasPaused;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfWasPaused));
          _tmpWasPaused = _tmp_1 != 0;
          _item = new StudySession(_tmpId,_tmpSessionType,_tmpCategoryId,_tmpLessonId,_tmpStartTime,_tmpEndTime,_tmpDurationMs,_tmpDurationMinutes,_tmpItemCount,_tmpCorrectCount,_tmpScore,_tmpDeviceInfo,_tmpInterfaceLanguage,_tmpIsCompleted,_tmpWasPaused);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Flow<StudySession> getStudySessionById(final long id) {
    final String _sql = "SELECT * FROM study_sessions WHERE id = ?";
    return FlowUtil.createFlow(__db, false, new String[] {"study_sessions"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfSessionType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "sessionType");
        final int _columnIndexOfCategoryId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "categoryId");
        final int _columnIndexOfLessonId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lessonId");
        final int _columnIndexOfStartTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "startTime");
        final int _columnIndexOfEndTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "endTime");
        final int _columnIndexOfDurationMs = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "durationMs");
        final int _columnIndexOfDurationMinutes = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "durationMinutes");
        final int _columnIndexOfItemCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "itemCount");
        final int _columnIndexOfCorrectCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctCount");
        final int _columnIndexOfScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "score");
        final int _columnIndexOfDeviceInfo = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "deviceInfo");
        final int _columnIndexOfInterfaceLanguage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "interfaceLanguage");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfWasPaused = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wasPaused");
        final StudySession _result;
        if (_stmt.step()) {
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final String _tmpSessionType;
          if (_stmt.isNull(_columnIndexOfSessionType)) {
            _tmpSessionType = null;
          } else {
            _tmpSessionType = _stmt.getText(_columnIndexOfSessionType);
          }
          final Long _tmpCategoryId;
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null;
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId);
          }
          final Long _tmpLessonId;
          if (_stmt.isNull(_columnIndexOfLessonId)) {
            _tmpLessonId = null;
          } else {
            _tmpLessonId = _stmt.getLong(_columnIndexOfLessonId);
          }
          final long _tmpStartTime;
          _tmpStartTime = _stmt.getLong(_columnIndexOfStartTime);
          final Long _tmpEndTime;
          if (_stmt.isNull(_columnIndexOfEndTime)) {
            _tmpEndTime = null;
          } else {
            _tmpEndTime = _stmt.getLong(_columnIndexOfEndTime);
          }
          final Long _tmpDurationMs;
          if (_stmt.isNull(_columnIndexOfDurationMs)) {
            _tmpDurationMs = null;
          } else {
            _tmpDurationMs = _stmt.getLong(_columnIndexOfDurationMs);
          }
          final Integer _tmpDurationMinutes;
          if (_stmt.isNull(_columnIndexOfDurationMinutes)) {
            _tmpDurationMinutes = null;
          } else {
            _tmpDurationMinutes = (int) (_stmt.getLong(_columnIndexOfDurationMinutes));
          }
          final int _tmpItemCount;
          _tmpItemCount = (int) (_stmt.getLong(_columnIndexOfItemCount));
          final int _tmpCorrectCount;
          _tmpCorrectCount = (int) (_stmt.getLong(_columnIndexOfCorrectCount));
          final Integer _tmpScore;
          if (_stmt.isNull(_columnIndexOfScore)) {
            _tmpScore = null;
          } else {
            _tmpScore = (int) (_stmt.getLong(_columnIndexOfScore));
          }
          final String _tmpDeviceInfo;
          if (_stmt.isNull(_columnIndexOfDeviceInfo)) {
            _tmpDeviceInfo = null;
          } else {
            _tmpDeviceInfo = _stmt.getText(_columnIndexOfDeviceInfo);
          }
          final String _tmpInterfaceLanguage;
          if (_stmt.isNull(_columnIndexOfInterfaceLanguage)) {
            _tmpInterfaceLanguage = null;
          } else {
            _tmpInterfaceLanguage = _stmt.getText(_columnIndexOfInterfaceLanguage);
          }
          final boolean _tmpIsCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp != 0;
          final boolean _tmpWasPaused;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfWasPaused));
          _tmpWasPaused = _tmp_1 != 0;
          _result = new StudySession(_tmpId,_tmpSessionType,_tmpCategoryId,_tmpLessonId,_tmpStartTime,_tmpEndTime,_tmpDurationMs,_tmpDurationMinutes,_tmpItemCount,_tmpCorrectCount,_tmpScore,_tmpDeviceInfo,_tmpInterfaceLanguage,_tmpIsCompleted,_tmpWasPaused);
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
  public Flow<List<StudySession>> getStudySessionsByDateRange(final Date startDate,
      final Date endDate) {
    final String _sql = "SELECT * FROM study_sessions WHERE startTime >= ? AND startTime <= ? ORDER BY startTime DESC";
    return FlowUtil.createFlow(__db, false, new String[] {"study_sessions"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        final Long _tmp = __dateConverter.dateToTimestamp(startDate);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp);
        }
        _argIndex = 2;
        final Long _tmp_1 = __dateConverter.dateToTimestamp(endDate);
        if (_tmp_1 == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp_1);
        }
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfSessionType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "sessionType");
        final int _columnIndexOfCategoryId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "categoryId");
        final int _columnIndexOfLessonId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lessonId");
        final int _columnIndexOfStartTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "startTime");
        final int _columnIndexOfEndTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "endTime");
        final int _columnIndexOfDurationMs = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "durationMs");
        final int _columnIndexOfDurationMinutes = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "durationMinutes");
        final int _columnIndexOfItemCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "itemCount");
        final int _columnIndexOfCorrectCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctCount");
        final int _columnIndexOfScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "score");
        final int _columnIndexOfDeviceInfo = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "deviceInfo");
        final int _columnIndexOfInterfaceLanguage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "interfaceLanguage");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfWasPaused = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wasPaused");
        final List<StudySession> _result = new ArrayList<StudySession>();
        while (_stmt.step()) {
          final StudySession _item;
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final String _tmpSessionType;
          if (_stmt.isNull(_columnIndexOfSessionType)) {
            _tmpSessionType = null;
          } else {
            _tmpSessionType = _stmt.getText(_columnIndexOfSessionType);
          }
          final Long _tmpCategoryId;
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null;
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId);
          }
          final Long _tmpLessonId;
          if (_stmt.isNull(_columnIndexOfLessonId)) {
            _tmpLessonId = null;
          } else {
            _tmpLessonId = _stmt.getLong(_columnIndexOfLessonId);
          }
          final long _tmpStartTime;
          _tmpStartTime = _stmt.getLong(_columnIndexOfStartTime);
          final Long _tmpEndTime;
          if (_stmt.isNull(_columnIndexOfEndTime)) {
            _tmpEndTime = null;
          } else {
            _tmpEndTime = _stmt.getLong(_columnIndexOfEndTime);
          }
          final Long _tmpDurationMs;
          if (_stmt.isNull(_columnIndexOfDurationMs)) {
            _tmpDurationMs = null;
          } else {
            _tmpDurationMs = _stmt.getLong(_columnIndexOfDurationMs);
          }
          final Integer _tmpDurationMinutes;
          if (_stmt.isNull(_columnIndexOfDurationMinutes)) {
            _tmpDurationMinutes = null;
          } else {
            _tmpDurationMinutes = (int) (_stmt.getLong(_columnIndexOfDurationMinutes));
          }
          final int _tmpItemCount;
          _tmpItemCount = (int) (_stmt.getLong(_columnIndexOfItemCount));
          final int _tmpCorrectCount;
          _tmpCorrectCount = (int) (_stmt.getLong(_columnIndexOfCorrectCount));
          final Integer _tmpScore;
          if (_stmt.isNull(_columnIndexOfScore)) {
            _tmpScore = null;
          } else {
            _tmpScore = (int) (_stmt.getLong(_columnIndexOfScore));
          }
          final String _tmpDeviceInfo;
          if (_stmt.isNull(_columnIndexOfDeviceInfo)) {
            _tmpDeviceInfo = null;
          } else {
            _tmpDeviceInfo = _stmt.getText(_columnIndexOfDeviceInfo);
          }
          final String _tmpInterfaceLanguage;
          if (_stmt.isNull(_columnIndexOfInterfaceLanguage)) {
            _tmpInterfaceLanguage = null;
          } else {
            _tmpInterfaceLanguage = _stmt.getText(_columnIndexOfInterfaceLanguage);
          }
          final boolean _tmpIsCompleted;
          final int _tmp_2;
          _tmp_2 = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp_2 != 0;
          final boolean _tmpWasPaused;
          final int _tmp_3;
          _tmp_3 = (int) (_stmt.getLong(_columnIndexOfWasPaused));
          _tmpWasPaused = _tmp_3 != 0;
          _item = new StudySession(_tmpId,_tmpSessionType,_tmpCategoryId,_tmpLessonId,_tmpStartTime,_tmpEndTime,_tmpDurationMs,_tmpDurationMinutes,_tmpItemCount,_tmpCorrectCount,_tmpScore,_tmpDeviceInfo,_tmpInterfaceLanguage,_tmpIsCompleted,_tmpWasPaused);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Flow<Integer> getTotalStudyTime() {
    final String _sql = "SELECT SUM(durationMinutes) FROM study_sessions";
    return FlowUtil.createFlow(__db, false, new String[] {"study_sessions"}, (_connection) -> {
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
  public Flow<Integer> getStudyTimeInRange(final Date startDate, final Date endDate) {
    final String _sql = "SELECT SUM(durationMinutes) FROM study_sessions WHERE startTime >= ? AND startTime <= ?";
    return FlowUtil.createFlow(__db, false, new String[] {"study_sessions"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        final Long _tmp = __dateConverter.dateToTimestamp(startDate);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp);
        }
        _argIndex = 2;
        final Long _tmp_1 = __dateConverter.dateToTimestamp(endDate);
        if (_tmp_1 == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp_1);
        }
        final Integer _result;
        if (_stmt.step()) {
          final Integer _tmp_2;
          if (_stmt.isNull(0)) {
            _tmp_2 = null;
          } else {
            _tmp_2 = (int) (_stmt.getLong(0));
          }
          _result = _tmp_2;
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
  public Flow<Integer> getStudySessionCountToday() {
    final String _sql = "SELECT COUNT(*) FROM study_sessions WHERE DATE(startTime / 1000, 'unixepoch') = DATE('now')";
    return FlowUtil.createFlow(__db, false, new String[] {"study_sessions"}, (_connection) -> {
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
  public Flow<Integer> getTodayStudyTime() {
    final String _sql = "SELECT SUM(durationMinutes) FROM study_sessions WHERE DATE(startTime / 1000, 'unixepoch') = DATE('now')";
    return FlowUtil.createFlow(__db, false, new String[] {"study_sessions"}, (_connection) -> {
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
