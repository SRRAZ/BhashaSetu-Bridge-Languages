package com.bhashasetu.app.database;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeleteOrUpdateAdapter;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteStatement;
import com.bhashasetu.app.model.PracticeSession;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation", "removal"})
public final class PracticeSessionDao_Impl implements PracticeSessionDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<PracticeSession> __insertAdapterOfPracticeSession;

  private final EntityDeleteOrUpdateAdapter<PracticeSession> __deleteAdapterOfPracticeSession;

  private final EntityDeleteOrUpdateAdapter<PracticeSession> __updateAdapterOfPracticeSession;

  public PracticeSessionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfPracticeSession = new EntityInsertAdapter<PracticeSession>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `practice_sessions` (`id`,`userId`,`sessionType`,`startTime`,`endTime`,`totalItems`,`correctAnswers`,`deckId`,`lessonId`,`score`,`isCompleted`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final PracticeSession entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getUserId());
        if (entity.getSessionType() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getSessionType());
        }
        statement.bindLong(4, entity.getStartTime());
        statement.bindLong(5, entity.getEndTime());
        statement.bindLong(6, entity.getTotalItems());
        statement.bindLong(7, entity.getCorrectAnswers());
        statement.bindLong(8, entity.getDeckId());
        statement.bindLong(9, entity.getLessonId());
        statement.bindLong(10, entity.getScore());
        final int _tmp = entity.isCompleted() ? 1 : 0;
        statement.bindLong(11, _tmp);
      }
    };
    this.__deleteAdapterOfPracticeSession = new EntityDeleteOrUpdateAdapter<PracticeSession>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `practice_sessions` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final PracticeSession entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfPracticeSession = new EntityDeleteOrUpdateAdapter<PracticeSession>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `practice_sessions` SET `id` = ?,`userId` = ?,`sessionType` = ?,`startTime` = ?,`endTime` = ?,`totalItems` = ?,`correctAnswers` = ?,`deckId` = ?,`lessonId` = ?,`score` = ?,`isCompleted` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final PracticeSession entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getUserId());
        if (entity.getSessionType() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getSessionType());
        }
        statement.bindLong(4, entity.getStartTime());
        statement.bindLong(5, entity.getEndTime());
        statement.bindLong(6, entity.getTotalItems());
        statement.bindLong(7, entity.getCorrectAnswers());
        statement.bindLong(8, entity.getDeckId());
        statement.bindLong(9, entity.getLessonId());
        statement.bindLong(10, entity.getScore());
        final int _tmp = entity.isCompleted() ? 1 : 0;
        statement.bindLong(11, _tmp);
        statement.bindLong(12, entity.getId());
      }
    };
  }

  @Override
  public long insert(final PracticeSession session) {
    return DBUtil.performBlocking(__db, false, true, (_connection) -> {
      return __insertAdapterOfPracticeSession.insertAndReturnId(_connection, session);
    });
  }

  @Override
  public void delete(final PracticeSession session) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __deleteAdapterOfPracticeSession.handle(_connection, session);
      return null;
    });
  }

  @Override
  public void update(final PracticeSession session) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __updateAdapterOfPracticeSession.handle(_connection, session);
      return null;
    });
  }

  @Override
  public LiveData<List<PracticeSession>> getAllSessionsForUser(final int userId) {
    final String _sql = "SELECT * FROM practice_sessions WHERE userId = ? ORDER BY startTime DESC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"practice_sessions"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, userId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfUserId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "userId");
        final int _columnIndexOfSessionType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "sessionType");
        final int _columnIndexOfStartTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "startTime");
        final int _columnIndexOfEndTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "endTime");
        final int _columnIndexOfTotalItems = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalItems");
        final int _columnIndexOfCorrectAnswers = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctAnswers");
        final int _columnIndexOfDeckId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "deckId");
        final int _columnIndexOfLessonId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lessonId");
        final int _columnIndexOfScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "score");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final List<PracticeSession> _result = new ArrayList<PracticeSession>();
        while (_stmt.step()) {
          final PracticeSession _item;
          final int _tmpUserId;
          _tmpUserId = (int) (_stmt.getLong(_columnIndexOfUserId));
          final String _tmpSessionType;
          if (_stmt.isNull(_columnIndexOfSessionType)) {
            _tmpSessionType = null;
          } else {
            _tmpSessionType = _stmt.getText(_columnIndexOfSessionType);
          }
          _item = new PracticeSession(_tmpUserId,_tmpSessionType);
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final long _tmpStartTime;
          _tmpStartTime = _stmt.getLong(_columnIndexOfStartTime);
          _item.setStartTime(_tmpStartTime);
          final long _tmpEndTime;
          _tmpEndTime = _stmt.getLong(_columnIndexOfEndTime);
          _item.setEndTime(_tmpEndTime);
          final int _tmpTotalItems;
          _tmpTotalItems = (int) (_stmt.getLong(_columnIndexOfTotalItems));
          _item.setTotalItems(_tmpTotalItems);
          final int _tmpCorrectAnswers;
          _tmpCorrectAnswers = (int) (_stmt.getLong(_columnIndexOfCorrectAnswers));
          _item.setCorrectAnswers(_tmpCorrectAnswers);
          final int _tmpDeckId;
          _tmpDeckId = (int) (_stmt.getLong(_columnIndexOfDeckId));
          _item.setDeckId(_tmpDeckId);
          final int _tmpLessonId;
          _tmpLessonId = (int) (_stmt.getLong(_columnIndexOfLessonId));
          _item.setLessonId(_tmpLessonId);
          final int _tmpScore;
          _tmpScore = (int) (_stmt.getLong(_columnIndexOfScore));
          _item.setScore(_tmpScore);
          final boolean _tmpIsCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp != 0;
          _item.setCompleted(_tmpIsCompleted);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<PracticeSession>> getRecentSessionsForUser(final int userId,
      final int limit) {
    final String _sql = "SELECT * FROM practice_sessions WHERE userId = ? ORDER BY startTime DESC LIMIT ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"practice_sessions"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, userId);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, limit);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfUserId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "userId");
        final int _columnIndexOfSessionType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "sessionType");
        final int _columnIndexOfStartTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "startTime");
        final int _columnIndexOfEndTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "endTime");
        final int _columnIndexOfTotalItems = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalItems");
        final int _columnIndexOfCorrectAnswers = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctAnswers");
        final int _columnIndexOfDeckId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "deckId");
        final int _columnIndexOfLessonId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lessonId");
        final int _columnIndexOfScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "score");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final List<PracticeSession> _result = new ArrayList<PracticeSession>();
        while (_stmt.step()) {
          final PracticeSession _item;
          final int _tmpUserId;
          _tmpUserId = (int) (_stmt.getLong(_columnIndexOfUserId));
          final String _tmpSessionType;
          if (_stmt.isNull(_columnIndexOfSessionType)) {
            _tmpSessionType = null;
          } else {
            _tmpSessionType = _stmt.getText(_columnIndexOfSessionType);
          }
          _item = new PracticeSession(_tmpUserId,_tmpSessionType);
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final long _tmpStartTime;
          _tmpStartTime = _stmt.getLong(_columnIndexOfStartTime);
          _item.setStartTime(_tmpStartTime);
          final long _tmpEndTime;
          _tmpEndTime = _stmt.getLong(_columnIndexOfEndTime);
          _item.setEndTime(_tmpEndTime);
          final int _tmpTotalItems;
          _tmpTotalItems = (int) (_stmt.getLong(_columnIndexOfTotalItems));
          _item.setTotalItems(_tmpTotalItems);
          final int _tmpCorrectAnswers;
          _tmpCorrectAnswers = (int) (_stmt.getLong(_columnIndexOfCorrectAnswers));
          _item.setCorrectAnswers(_tmpCorrectAnswers);
          final int _tmpDeckId;
          _tmpDeckId = (int) (_stmt.getLong(_columnIndexOfDeckId));
          _item.setDeckId(_tmpDeckId);
          final int _tmpLessonId;
          _tmpLessonId = (int) (_stmt.getLong(_columnIndexOfLessonId));
          _item.setLessonId(_tmpLessonId);
          final int _tmpScore;
          _tmpScore = (int) (_stmt.getLong(_columnIndexOfScore));
          _item.setScore(_tmpScore);
          final boolean _tmpIsCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp != 0;
          _item.setCompleted(_tmpIsCompleted);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<PracticeSession> getSessionById(final int sessionId) {
    final String _sql = "SELECT * FROM practice_sessions WHERE id = ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"practice_sessions"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, sessionId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfUserId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "userId");
        final int _columnIndexOfSessionType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "sessionType");
        final int _columnIndexOfStartTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "startTime");
        final int _columnIndexOfEndTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "endTime");
        final int _columnIndexOfTotalItems = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalItems");
        final int _columnIndexOfCorrectAnswers = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctAnswers");
        final int _columnIndexOfDeckId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "deckId");
        final int _columnIndexOfLessonId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lessonId");
        final int _columnIndexOfScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "score");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final PracticeSession _result;
        if (_stmt.step()) {
          final int _tmpUserId;
          _tmpUserId = (int) (_stmt.getLong(_columnIndexOfUserId));
          final String _tmpSessionType;
          if (_stmt.isNull(_columnIndexOfSessionType)) {
            _tmpSessionType = null;
          } else {
            _tmpSessionType = _stmt.getText(_columnIndexOfSessionType);
          }
          _result = new PracticeSession(_tmpUserId,_tmpSessionType);
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _result.setId(_tmpId);
          final long _tmpStartTime;
          _tmpStartTime = _stmt.getLong(_columnIndexOfStartTime);
          _result.setStartTime(_tmpStartTime);
          final long _tmpEndTime;
          _tmpEndTime = _stmt.getLong(_columnIndexOfEndTime);
          _result.setEndTime(_tmpEndTime);
          final int _tmpTotalItems;
          _tmpTotalItems = (int) (_stmt.getLong(_columnIndexOfTotalItems));
          _result.setTotalItems(_tmpTotalItems);
          final int _tmpCorrectAnswers;
          _tmpCorrectAnswers = (int) (_stmt.getLong(_columnIndexOfCorrectAnswers));
          _result.setCorrectAnswers(_tmpCorrectAnswers);
          final int _tmpDeckId;
          _tmpDeckId = (int) (_stmt.getLong(_columnIndexOfDeckId));
          _result.setDeckId(_tmpDeckId);
          final int _tmpLessonId;
          _tmpLessonId = (int) (_stmt.getLong(_columnIndexOfLessonId));
          _result.setLessonId(_tmpLessonId);
          final int _tmpScore;
          _tmpScore = (int) (_stmt.getLong(_columnIndexOfScore));
          _result.setScore(_tmpScore);
          final boolean _tmpIsCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp != 0;
          _result.setCompleted(_tmpIsCompleted);
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
  public LiveData<List<PracticeSession>> getSessionsByType(final int userId,
      final String sessionType) {
    final String _sql = "SELECT * FROM practice_sessions WHERE userId = ? AND sessionType = ? ORDER BY startTime DESC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"practice_sessions"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, userId);
        _argIndex = 2;
        if (sessionType == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, sessionType);
        }
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfUserId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "userId");
        final int _columnIndexOfSessionType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "sessionType");
        final int _columnIndexOfStartTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "startTime");
        final int _columnIndexOfEndTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "endTime");
        final int _columnIndexOfTotalItems = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalItems");
        final int _columnIndexOfCorrectAnswers = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctAnswers");
        final int _columnIndexOfDeckId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "deckId");
        final int _columnIndexOfLessonId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lessonId");
        final int _columnIndexOfScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "score");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final List<PracticeSession> _result = new ArrayList<PracticeSession>();
        while (_stmt.step()) {
          final PracticeSession _item;
          final int _tmpUserId;
          _tmpUserId = (int) (_stmt.getLong(_columnIndexOfUserId));
          final String _tmpSessionType;
          if (_stmt.isNull(_columnIndexOfSessionType)) {
            _tmpSessionType = null;
          } else {
            _tmpSessionType = _stmt.getText(_columnIndexOfSessionType);
          }
          _item = new PracticeSession(_tmpUserId,_tmpSessionType);
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final long _tmpStartTime;
          _tmpStartTime = _stmt.getLong(_columnIndexOfStartTime);
          _item.setStartTime(_tmpStartTime);
          final long _tmpEndTime;
          _tmpEndTime = _stmt.getLong(_columnIndexOfEndTime);
          _item.setEndTime(_tmpEndTime);
          final int _tmpTotalItems;
          _tmpTotalItems = (int) (_stmt.getLong(_columnIndexOfTotalItems));
          _item.setTotalItems(_tmpTotalItems);
          final int _tmpCorrectAnswers;
          _tmpCorrectAnswers = (int) (_stmt.getLong(_columnIndexOfCorrectAnswers));
          _item.setCorrectAnswers(_tmpCorrectAnswers);
          final int _tmpDeckId;
          _tmpDeckId = (int) (_stmt.getLong(_columnIndexOfDeckId));
          _item.setDeckId(_tmpDeckId);
          final int _tmpLessonId;
          _tmpLessonId = (int) (_stmt.getLong(_columnIndexOfLessonId));
          _item.setLessonId(_tmpLessonId);
          final int _tmpScore;
          _tmpScore = (int) (_stmt.getLong(_columnIndexOfScore));
          _item.setScore(_tmpScore);
          final boolean _tmpIsCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp != 0;
          _item.setCompleted(_tmpIsCompleted);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<PracticeSession>> getSessionsForDeck(final int userId, final int deckId) {
    final String _sql = "SELECT * FROM practice_sessions WHERE userId = ? AND deckId = ? ORDER BY startTime DESC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"practice_sessions"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, userId);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, deckId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfUserId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "userId");
        final int _columnIndexOfSessionType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "sessionType");
        final int _columnIndexOfStartTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "startTime");
        final int _columnIndexOfEndTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "endTime");
        final int _columnIndexOfTotalItems = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalItems");
        final int _columnIndexOfCorrectAnswers = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctAnswers");
        final int _columnIndexOfDeckId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "deckId");
        final int _columnIndexOfLessonId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lessonId");
        final int _columnIndexOfScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "score");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final List<PracticeSession> _result = new ArrayList<PracticeSession>();
        while (_stmt.step()) {
          final PracticeSession _item;
          final int _tmpUserId;
          _tmpUserId = (int) (_stmt.getLong(_columnIndexOfUserId));
          final String _tmpSessionType;
          if (_stmt.isNull(_columnIndexOfSessionType)) {
            _tmpSessionType = null;
          } else {
            _tmpSessionType = _stmt.getText(_columnIndexOfSessionType);
          }
          _item = new PracticeSession(_tmpUserId,_tmpSessionType);
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final long _tmpStartTime;
          _tmpStartTime = _stmt.getLong(_columnIndexOfStartTime);
          _item.setStartTime(_tmpStartTime);
          final long _tmpEndTime;
          _tmpEndTime = _stmt.getLong(_columnIndexOfEndTime);
          _item.setEndTime(_tmpEndTime);
          final int _tmpTotalItems;
          _tmpTotalItems = (int) (_stmt.getLong(_columnIndexOfTotalItems));
          _item.setTotalItems(_tmpTotalItems);
          final int _tmpCorrectAnswers;
          _tmpCorrectAnswers = (int) (_stmt.getLong(_columnIndexOfCorrectAnswers));
          _item.setCorrectAnswers(_tmpCorrectAnswers);
          final int _tmpDeckId;
          _tmpDeckId = (int) (_stmt.getLong(_columnIndexOfDeckId));
          _item.setDeckId(_tmpDeckId);
          final int _tmpLessonId;
          _tmpLessonId = (int) (_stmt.getLong(_columnIndexOfLessonId));
          _item.setLessonId(_tmpLessonId);
          final int _tmpScore;
          _tmpScore = (int) (_stmt.getLong(_columnIndexOfScore));
          _item.setScore(_tmpScore);
          final boolean _tmpIsCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp != 0;
          _item.setCompleted(_tmpIsCompleted);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<PracticeSession>> getSessionsForLesson(final int userId,
      final int lessonId) {
    final String _sql = "SELECT * FROM practice_sessions WHERE userId = ? AND lessonId = ? ORDER BY startTime DESC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"practice_sessions"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, userId);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, lessonId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfUserId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "userId");
        final int _columnIndexOfSessionType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "sessionType");
        final int _columnIndexOfStartTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "startTime");
        final int _columnIndexOfEndTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "endTime");
        final int _columnIndexOfTotalItems = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalItems");
        final int _columnIndexOfCorrectAnswers = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctAnswers");
        final int _columnIndexOfDeckId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "deckId");
        final int _columnIndexOfLessonId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lessonId");
        final int _columnIndexOfScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "score");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final List<PracticeSession> _result = new ArrayList<PracticeSession>();
        while (_stmt.step()) {
          final PracticeSession _item;
          final int _tmpUserId;
          _tmpUserId = (int) (_stmt.getLong(_columnIndexOfUserId));
          final String _tmpSessionType;
          if (_stmt.isNull(_columnIndexOfSessionType)) {
            _tmpSessionType = null;
          } else {
            _tmpSessionType = _stmt.getText(_columnIndexOfSessionType);
          }
          _item = new PracticeSession(_tmpUserId,_tmpSessionType);
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final long _tmpStartTime;
          _tmpStartTime = _stmt.getLong(_columnIndexOfStartTime);
          _item.setStartTime(_tmpStartTime);
          final long _tmpEndTime;
          _tmpEndTime = _stmt.getLong(_columnIndexOfEndTime);
          _item.setEndTime(_tmpEndTime);
          final int _tmpTotalItems;
          _tmpTotalItems = (int) (_stmt.getLong(_columnIndexOfTotalItems));
          _item.setTotalItems(_tmpTotalItems);
          final int _tmpCorrectAnswers;
          _tmpCorrectAnswers = (int) (_stmt.getLong(_columnIndexOfCorrectAnswers));
          _item.setCorrectAnswers(_tmpCorrectAnswers);
          final int _tmpDeckId;
          _tmpDeckId = (int) (_stmt.getLong(_columnIndexOfDeckId));
          _item.setDeckId(_tmpDeckId);
          final int _tmpLessonId;
          _tmpLessonId = (int) (_stmt.getLong(_columnIndexOfLessonId));
          _item.setLessonId(_tmpLessonId);
          final int _tmpScore;
          _tmpScore = (int) (_stmt.getLong(_columnIndexOfScore));
          _item.setScore(_tmpScore);
          final boolean _tmpIsCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp != 0;
          _item.setCompleted(_tmpIsCompleted);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public int getCompletedSessionCount(final int userId, final String sessionType) {
    final String _sql = "SELECT COUNT(*) FROM practice_sessions WHERE userId = ? AND sessionType = ? AND isCompleted = 1";
    return DBUtil.performBlocking(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, userId);
        _argIndex = 2;
        if (sessionType == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, sessionType);
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
  public float getAverageScore(final int userId) {
    final String _sql = "SELECT AVG(score) FROM practice_sessions WHERE userId = ? AND isCompleted = 1";
    return DBUtil.performBlocking(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, userId);
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
  public int getTotalItemsPracticed(final int userId) {
    final String _sql = "SELECT SUM(totalItems) FROM practice_sessions WHERE userId = ? AND isCompleted = 1";
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
  public int getTotalCorrectAnswers(final int userId) {
    final String _sql = "SELECT SUM(correctAnswers) FROM practice_sessions WHERE userId = ? AND isCompleted = 1";
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
  public int getSessionCountToday(final int userId) {
    final String _sql = "SELECT COUNT(*) FROM practice_sessions WHERE userId = ? AND date(startTime/1000, 'unixepoch') = date('now')";
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
  public void completeSession(final int sessionId, final long endTime, final int score,
      final int correctAnswers) {
    final String _sql = "UPDATE practice_sessions SET isCompleted = 1, endTime = ?, score = ?, correctAnswers = ? WHERE id = ?";
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, endTime);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, score);
        _argIndex = 3;
        _stmt.bindLong(_argIndex, correctAnswers);
        _argIndex = 4;
        _stmt.bindLong(_argIndex, sessionId);
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
