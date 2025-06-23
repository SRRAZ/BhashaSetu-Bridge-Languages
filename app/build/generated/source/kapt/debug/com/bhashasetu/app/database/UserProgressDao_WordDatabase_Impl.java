package com.bhashasetu.app.database;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeleteOrUpdateAdapter;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteStatement;
import com.bhashasetu.app.model.UserProgress;
import java.lang.Class;
import java.lang.Float;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation", "removal"})
public final class UserProgressDao_WordDatabase_Impl implements UserProgressDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<UserProgress> __insertAdapterOfUserProgress;

  private final EntityDeleteOrUpdateAdapter<UserProgress> __updateAdapterOfUserProgress;

  public UserProgressDao_WordDatabase_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfUserProgress = new EntityInsertAdapter<UserProgress>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `user_progress` (`id`,`userId`,`itemType`,`itemId`,`completionLevel`,`lastPracticed`,`reviewDue`,`attemptCount`,`correctCount`,`srLevel`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final UserProgress entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getUserId());
        if (entity.getItemType() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getItemType());
        }
        statement.bindLong(4, entity.getItemId());
        statement.bindLong(5, entity.getCompletionLevel());
        statement.bindLong(6, entity.getLastPracticed());
        statement.bindLong(7, entity.getReviewDue());
        statement.bindLong(8, entity.getAttemptCount());
        statement.bindLong(9, entity.getCorrectCount());
        statement.bindLong(10, entity.getSrLevel());
      }
    };
    this.__updateAdapterOfUserProgress = new EntityDeleteOrUpdateAdapter<UserProgress>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `user_progress` SET `id` = ?,`userId` = ?,`itemType` = ?,`itemId` = ?,`completionLevel` = ?,`lastPracticed` = ?,`reviewDue` = ?,`attemptCount` = ?,`correctCount` = ?,`srLevel` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final UserProgress entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getUserId());
        if (entity.getItemType() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getItemType());
        }
        statement.bindLong(4, entity.getItemId());
        statement.bindLong(5, entity.getCompletionLevel());
        statement.bindLong(6, entity.getLastPracticed());
        statement.bindLong(7, entity.getReviewDue());
        statement.bindLong(8, entity.getAttemptCount());
        statement.bindLong(9, entity.getCorrectCount());
        statement.bindLong(10, entity.getSrLevel());
        statement.bindLong(11, entity.getId());
      }
    };
  }

  @Override
  public long insert(final UserProgress progress) {
    return DBUtil.performBlocking(__db, false, true, (_connection) -> {
      return __insertAdapterOfUserProgress.insertAndReturnId(_connection, progress);
    });
  }

  @Override
  public void update(final UserProgress progress) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __updateAdapterOfUserProgress.handle(_connection, progress);
      return null;
    });
  }

  @Override
  public LiveData<List<UserProgress>> getAllProgressForUser(final int userId) {
    final String _sql = "SELECT * FROM user_progress WHERE userId = ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"user_progress"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, userId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfUserId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "userId");
        final int _columnIndexOfItemType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "itemType");
        final int _columnIndexOfItemId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "itemId");
        final int _columnIndexOfCompletionLevel = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completionLevel");
        final int _columnIndexOfLastPracticed = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastPracticed");
        final int _columnIndexOfReviewDue = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "reviewDue");
        final int _columnIndexOfAttemptCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "attemptCount");
        final int _columnIndexOfCorrectCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctCount");
        final int _columnIndexOfSrLevel = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "srLevel");
        final List<UserProgress> _result = new ArrayList<UserProgress>();
        while (_stmt.step()) {
          final UserProgress _item;
          final int _tmpUserId;
          _tmpUserId = (int) (_stmt.getLong(_columnIndexOfUserId));
          final String _tmpItemType;
          if (_stmt.isNull(_columnIndexOfItemType)) {
            _tmpItemType = null;
          } else {
            _tmpItemType = _stmt.getText(_columnIndexOfItemType);
          }
          final int _tmpItemId;
          _tmpItemId = (int) (_stmt.getLong(_columnIndexOfItemId));
          _item = new UserProgress(_tmpUserId,_tmpItemType,_tmpItemId);
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final int _tmpCompletionLevel;
          _tmpCompletionLevel = (int) (_stmt.getLong(_columnIndexOfCompletionLevel));
          _item.setCompletionLevel(_tmpCompletionLevel);
          final long _tmpLastPracticed;
          _tmpLastPracticed = _stmt.getLong(_columnIndexOfLastPracticed);
          _item.setLastPracticed(_tmpLastPracticed);
          final long _tmpReviewDue;
          _tmpReviewDue = _stmt.getLong(_columnIndexOfReviewDue);
          _item.setReviewDue(_tmpReviewDue);
          final int _tmpAttemptCount;
          _tmpAttemptCount = (int) (_stmt.getLong(_columnIndexOfAttemptCount));
          _item.setAttemptCount(_tmpAttemptCount);
          final int _tmpCorrectCount;
          _tmpCorrectCount = (int) (_stmt.getLong(_columnIndexOfCorrectCount));
          _item.setCorrectCount(_tmpCorrectCount);
          final int _tmpSrLevel;
          _tmpSrLevel = (int) (_stmt.getLong(_columnIndexOfSrLevel));
          _item.setSrLevel(_tmpSrLevel);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<UserProgress> getWordProgress(final int userId, final int wordId) {
    final String _sql = "SELECT * FROM user_progress WHERE userId = ? AND itemType = 'word' AND itemId = ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"user_progress"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, userId);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, wordId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfUserId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "userId");
        final int _columnIndexOfItemType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "itemType");
        final int _columnIndexOfItemId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "itemId");
        final int _columnIndexOfCompletionLevel = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completionLevel");
        final int _columnIndexOfLastPracticed = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastPracticed");
        final int _columnIndexOfReviewDue = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "reviewDue");
        final int _columnIndexOfAttemptCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "attemptCount");
        final int _columnIndexOfCorrectCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctCount");
        final int _columnIndexOfSrLevel = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "srLevel");
        final UserProgress _result;
        if (_stmt.step()) {
          final int _tmpUserId;
          _tmpUserId = (int) (_stmt.getLong(_columnIndexOfUserId));
          final String _tmpItemType;
          if (_stmt.isNull(_columnIndexOfItemType)) {
            _tmpItemType = null;
          } else {
            _tmpItemType = _stmt.getText(_columnIndexOfItemType);
          }
          final int _tmpItemId;
          _tmpItemId = (int) (_stmt.getLong(_columnIndexOfItemId));
          _result = new UserProgress(_tmpUserId,_tmpItemType,_tmpItemId);
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _result.setId(_tmpId);
          final int _tmpCompletionLevel;
          _tmpCompletionLevel = (int) (_stmt.getLong(_columnIndexOfCompletionLevel));
          _result.setCompletionLevel(_tmpCompletionLevel);
          final long _tmpLastPracticed;
          _tmpLastPracticed = _stmt.getLong(_columnIndexOfLastPracticed);
          _result.setLastPracticed(_tmpLastPracticed);
          final long _tmpReviewDue;
          _tmpReviewDue = _stmt.getLong(_columnIndexOfReviewDue);
          _result.setReviewDue(_tmpReviewDue);
          final int _tmpAttemptCount;
          _tmpAttemptCount = (int) (_stmt.getLong(_columnIndexOfAttemptCount));
          _result.setAttemptCount(_tmpAttemptCount);
          final int _tmpCorrectCount;
          _tmpCorrectCount = (int) (_stmt.getLong(_columnIndexOfCorrectCount));
          _result.setCorrectCount(_tmpCorrectCount);
          final int _tmpSrLevel;
          _tmpSrLevel = (int) (_stmt.getLong(_columnIndexOfSrLevel));
          _result.setSrLevel(_tmpSrLevel);
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
  public LiveData<UserProgress> getLessonProgress(final int userId, final int lessonId) {
    final String _sql = "SELECT * FROM user_progress WHERE userId = ? AND itemType = 'lesson' AND itemId = ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"user_progress"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, userId);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, lessonId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfUserId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "userId");
        final int _columnIndexOfItemType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "itemType");
        final int _columnIndexOfItemId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "itemId");
        final int _columnIndexOfCompletionLevel = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completionLevel");
        final int _columnIndexOfLastPracticed = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastPracticed");
        final int _columnIndexOfReviewDue = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "reviewDue");
        final int _columnIndexOfAttemptCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "attemptCount");
        final int _columnIndexOfCorrectCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctCount");
        final int _columnIndexOfSrLevel = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "srLevel");
        final UserProgress _result;
        if (_stmt.step()) {
          final int _tmpUserId;
          _tmpUserId = (int) (_stmt.getLong(_columnIndexOfUserId));
          final String _tmpItemType;
          if (_stmt.isNull(_columnIndexOfItemType)) {
            _tmpItemType = null;
          } else {
            _tmpItemType = _stmt.getText(_columnIndexOfItemType);
          }
          final int _tmpItemId;
          _tmpItemId = (int) (_stmt.getLong(_columnIndexOfItemId));
          _result = new UserProgress(_tmpUserId,_tmpItemType,_tmpItemId);
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _result.setId(_tmpId);
          final int _tmpCompletionLevel;
          _tmpCompletionLevel = (int) (_stmt.getLong(_columnIndexOfCompletionLevel));
          _result.setCompletionLevel(_tmpCompletionLevel);
          final long _tmpLastPracticed;
          _tmpLastPracticed = _stmt.getLong(_columnIndexOfLastPracticed);
          _result.setLastPracticed(_tmpLastPracticed);
          final long _tmpReviewDue;
          _tmpReviewDue = _stmt.getLong(_columnIndexOfReviewDue);
          _result.setReviewDue(_tmpReviewDue);
          final int _tmpAttemptCount;
          _tmpAttemptCount = (int) (_stmt.getLong(_columnIndexOfAttemptCount));
          _result.setAttemptCount(_tmpAttemptCount);
          final int _tmpCorrectCount;
          _tmpCorrectCount = (int) (_stmt.getLong(_columnIndexOfCorrectCount));
          _result.setCorrectCount(_tmpCorrectCount);
          final int _tmpSrLevel;
          _tmpSrLevel = (int) (_stmt.getLong(_columnIndexOfSrLevel));
          _result.setSrLevel(_tmpSrLevel);
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
  public LiveData<Float> getOverallProgress(final int userId) {
    final String _sql = "SELECT AVG(completionLevel) FROM user_progress WHERE userId = ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"user_progress"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, userId);
        final Float _result;
        if (_stmt.step()) {
          final Float _tmp;
          if (_stmt.isNull(0)) {
            _tmp = null;
          } else {
            _tmp = (float) (_stmt.getDouble(0));
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
  public LiveData<List<UserProgress>> getDueWordReviews(final int userId, final long currentTime,
      final int limit) {
    final String _sql = "SELECT * FROM user_progress WHERE userId = ? AND reviewDue <= ? AND itemType = 'word' ORDER BY reviewDue ASC LIMIT ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"user_progress"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, userId);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, currentTime);
        _argIndex = 3;
        _stmt.bindLong(_argIndex, limit);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfUserId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "userId");
        final int _columnIndexOfItemType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "itemType");
        final int _columnIndexOfItemId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "itemId");
        final int _columnIndexOfCompletionLevel = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completionLevel");
        final int _columnIndexOfLastPracticed = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastPracticed");
        final int _columnIndexOfReviewDue = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "reviewDue");
        final int _columnIndexOfAttemptCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "attemptCount");
        final int _columnIndexOfCorrectCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctCount");
        final int _columnIndexOfSrLevel = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "srLevel");
        final List<UserProgress> _result = new ArrayList<UserProgress>();
        while (_stmt.step()) {
          final UserProgress _item;
          final int _tmpUserId;
          _tmpUserId = (int) (_stmt.getLong(_columnIndexOfUserId));
          final String _tmpItemType;
          if (_stmt.isNull(_columnIndexOfItemType)) {
            _tmpItemType = null;
          } else {
            _tmpItemType = _stmt.getText(_columnIndexOfItemType);
          }
          final int _tmpItemId;
          _tmpItemId = (int) (_stmt.getLong(_columnIndexOfItemId));
          _item = new UserProgress(_tmpUserId,_tmpItemType,_tmpItemId);
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final int _tmpCompletionLevel;
          _tmpCompletionLevel = (int) (_stmt.getLong(_columnIndexOfCompletionLevel));
          _item.setCompletionLevel(_tmpCompletionLevel);
          final long _tmpLastPracticed;
          _tmpLastPracticed = _stmt.getLong(_columnIndexOfLastPracticed);
          _item.setLastPracticed(_tmpLastPracticed);
          final long _tmpReviewDue;
          _tmpReviewDue = _stmt.getLong(_columnIndexOfReviewDue);
          _item.setReviewDue(_tmpReviewDue);
          final int _tmpAttemptCount;
          _tmpAttemptCount = (int) (_stmt.getLong(_columnIndexOfAttemptCount));
          _item.setAttemptCount(_tmpAttemptCount);
          final int _tmpCorrectCount;
          _tmpCorrectCount = (int) (_stmt.getLong(_columnIndexOfCorrectCount));
          _item.setCorrectCount(_tmpCorrectCount);
          final int _tmpSrLevel;
          _tmpSrLevel = (int) (_stmt.getLong(_columnIndexOfSrLevel));
          _item.setSrLevel(_tmpSrLevel);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<UserProgress>> getLeastMasteredItems(final int userId, final int limit) {
    final String _sql = "SELECT * FROM user_progress WHERE userId = ? AND itemType = 'word' ORDER BY completionLevel ASC LIMIT ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"user_progress"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, userId);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, limit);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfUserId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "userId");
        final int _columnIndexOfItemType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "itemType");
        final int _columnIndexOfItemId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "itemId");
        final int _columnIndexOfCompletionLevel = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completionLevel");
        final int _columnIndexOfLastPracticed = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastPracticed");
        final int _columnIndexOfReviewDue = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "reviewDue");
        final int _columnIndexOfAttemptCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "attemptCount");
        final int _columnIndexOfCorrectCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctCount");
        final int _columnIndexOfSrLevel = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "srLevel");
        final List<UserProgress> _result = new ArrayList<UserProgress>();
        while (_stmt.step()) {
          final UserProgress _item;
          final int _tmpUserId;
          _tmpUserId = (int) (_stmt.getLong(_columnIndexOfUserId));
          final String _tmpItemType;
          if (_stmt.isNull(_columnIndexOfItemType)) {
            _tmpItemType = null;
          } else {
            _tmpItemType = _stmt.getText(_columnIndexOfItemType);
          }
          final int _tmpItemId;
          _tmpItemId = (int) (_stmt.getLong(_columnIndexOfItemId));
          _item = new UserProgress(_tmpUserId,_tmpItemType,_tmpItemId);
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final int _tmpCompletionLevel;
          _tmpCompletionLevel = (int) (_stmt.getLong(_columnIndexOfCompletionLevel));
          _item.setCompletionLevel(_tmpCompletionLevel);
          final long _tmpLastPracticed;
          _tmpLastPracticed = _stmt.getLong(_columnIndexOfLastPracticed);
          _item.setLastPracticed(_tmpLastPracticed);
          final long _tmpReviewDue;
          _tmpReviewDue = _stmt.getLong(_columnIndexOfReviewDue);
          _item.setReviewDue(_tmpReviewDue);
          final int _tmpAttemptCount;
          _tmpAttemptCount = (int) (_stmt.getLong(_columnIndexOfAttemptCount));
          _item.setAttemptCount(_tmpAttemptCount);
          final int _tmpCorrectCount;
          _tmpCorrectCount = (int) (_stmt.getLong(_columnIndexOfCorrectCount));
          _item.setCorrectCount(_tmpCorrectCount);
          final int _tmpSrLevel;
          _tmpSrLevel = (int) (_stmt.getLong(_columnIndexOfSrLevel));
          _item.setSrLevel(_tmpSrLevel);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public void updateCompletionLevel(final int userId, final String itemType, final int itemId,
      final int level) {
    final String _sql = "UPDATE user_progress SET completionLevel = ? WHERE userId = ? AND itemType = ? AND itemId = ?";
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, level);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, userId);
        _argIndex = 3;
        if (itemType == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, itemType);
        }
        _argIndex = 4;
        _stmt.bindLong(_argIndex, itemId);
        _stmt.step();
        return null;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public void updateNextReview(final int progressId, final long nextReview) {
    final String _sql = "UPDATE user_progress SET reviewDue = ? WHERE id = ?";
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, nextReview);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, progressId);
        _stmt.step();
        return null;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public void updateSpacedRepetitionLevel(final int progressId, final int newLevel) {
    final String _sql = "UPDATE user_progress SET srLevel = ? WHERE id = ?";
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, newLevel);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, progressId);
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
