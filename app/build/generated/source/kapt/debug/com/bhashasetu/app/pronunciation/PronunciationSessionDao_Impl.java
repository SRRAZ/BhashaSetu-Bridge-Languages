package com.bhashasetu.app.pronunciation;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeleteOrUpdateAdapter;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteStatement;
import com.bhashasetu.app.database.Converters;
import com.bhashasetu.app.model.PronunciationSession;
import com.bhashasetu.app.model.Word;
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
public final class PronunciationSessionDao_Impl implements PronunciationSessionDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<PronunciationSession> __insertAdapterOfPronunciationSession;

  private final EntityDeleteOrUpdateAdapter<PronunciationSession> __deleteAdapterOfPronunciationSession;

  private final EntityDeleteOrUpdateAdapter<PronunciationSession> __updateAdapterOfPronunciationSession;

  public PronunciationSessionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfPronunciationSession = new EntityInsertAdapter<PronunciationSession>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `pronunciation_sessions` (`id`,`startTime`,`endTime`,`words`,`scores`,`averageAccuracyScore`,`averageRhythmScore`,`averageIntonationScore`,`userId`,`difficultyLevel`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          final PronunciationSession entity) {
        statement.bindLong(1, entity.getId());
        final Long _tmp = Converters.dateToTimestamp(entity.getStartTime());
        if (_tmp == null) {
          statement.bindNull(2);
        } else {
          statement.bindLong(2, _tmp);
        }
        final Long _tmp_1 = Converters.dateToTimestamp(entity.getEndTime());
        if (_tmp_1 == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, _tmp_1);
        }
        final String _tmp_2 = Converters.fromWordList(entity.getWords());
        if (_tmp_2 == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, _tmp_2);
        }
        final String _tmp_3 = Converters.fromPronunciationScoreList(entity.getScores());
        if (_tmp_3 == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, _tmp_3);
        }
        statement.bindLong(6, entity.getAverageAccuracyScore());
        statement.bindLong(7, entity.getAverageRhythmScore());
        statement.bindLong(8, entity.getAverageIntonationScore());
        statement.bindLong(9, entity.getUserId());
        statement.bindLong(10, entity.getDifficultyLevel());
      }
    };
    this.__deleteAdapterOfPronunciationSession = new EntityDeleteOrUpdateAdapter<PronunciationSession>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `pronunciation_sessions` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          final PronunciationSession entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfPronunciationSession = new EntityDeleteOrUpdateAdapter<PronunciationSession>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `pronunciation_sessions` SET `id` = ?,`startTime` = ?,`endTime` = ?,`words` = ?,`scores` = ?,`averageAccuracyScore` = ?,`averageRhythmScore` = ?,`averageIntonationScore` = ?,`userId` = ?,`difficultyLevel` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          final PronunciationSession entity) {
        statement.bindLong(1, entity.getId());
        final Long _tmp = Converters.dateToTimestamp(entity.getStartTime());
        if (_tmp == null) {
          statement.bindNull(2);
        } else {
          statement.bindLong(2, _tmp);
        }
        final Long _tmp_1 = Converters.dateToTimestamp(entity.getEndTime());
        if (_tmp_1 == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, _tmp_1);
        }
        final String _tmp_2 = Converters.fromWordList(entity.getWords());
        if (_tmp_2 == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, _tmp_2);
        }
        final String _tmp_3 = Converters.fromPronunciationScoreList(entity.getScores());
        if (_tmp_3 == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, _tmp_3);
        }
        statement.bindLong(6, entity.getAverageAccuracyScore());
        statement.bindLong(7, entity.getAverageRhythmScore());
        statement.bindLong(8, entity.getAverageIntonationScore());
        statement.bindLong(9, entity.getUserId());
        statement.bindLong(10, entity.getDifficultyLevel());
        statement.bindLong(11, entity.getId());
      }
    };
  }

  @Override
  public long insert(final PronunciationSession session) {
    return DBUtil.performBlocking(__db, false, true, (_connection) -> {
      return __insertAdapterOfPronunciationSession.insertAndReturnId(_connection, session);
    });
  }

  @Override
  public void delete(final PronunciationSession session) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __deleteAdapterOfPronunciationSession.handle(_connection, session);
      return null;
    });
  }

  @Override
  public void update(final PronunciationSession session) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __updateAdapterOfPronunciationSession.handle(_connection, session);
      return null;
    });
  }

  @Override
  public LiveData<PronunciationSession> getSessionById(final long sessionId) {
    final String _sql = "SELECT * FROM pronunciation_sessions WHERE id = ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"pronunciation_sessions"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, sessionId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfStartTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "startTime");
        final int _columnIndexOfEndTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "endTime");
        final int _columnIndexOfWords = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "words");
        final int _columnIndexOfScores = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "scores");
        final int _columnIndexOfAverageAccuracyScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "averageAccuracyScore");
        final int _columnIndexOfAverageRhythmScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "averageRhythmScore");
        final int _columnIndexOfAverageIntonationScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "averageIntonationScore");
        final int _columnIndexOfUserId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "userId");
        final int _columnIndexOfDifficultyLevel = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficultyLevel");
        final PronunciationSession _result;
        if (_stmt.step()) {
          _result = new PronunciationSession();
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          _result.setId(_tmpId);
          final Date _tmpStartTime;
          final Long _tmp;
          if (_stmt.isNull(_columnIndexOfStartTime)) {
            _tmp = null;
          } else {
            _tmp = _stmt.getLong(_columnIndexOfStartTime);
          }
          _tmpStartTime = Converters.fromTimestamp(_tmp);
          _result.setStartTime(_tmpStartTime);
          final Date _tmpEndTime;
          final Long _tmp_1;
          if (_stmt.isNull(_columnIndexOfEndTime)) {
            _tmp_1 = null;
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfEndTime);
          }
          _tmpEndTime = Converters.fromTimestamp(_tmp_1);
          _result.setEndTime(_tmpEndTime);
          final List<Word> _tmpWords;
          final String _tmp_2;
          if (_stmt.isNull(_columnIndexOfWords)) {
            _tmp_2 = null;
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfWords);
          }
          _tmpWords = Converters.toWordList(_tmp_2);
          _result.setWords(_tmpWords);
          final List<PronunciationScore> _tmpScores;
          final String _tmp_3;
          if (_stmt.isNull(_columnIndexOfScores)) {
            _tmp_3 = null;
          } else {
            _tmp_3 = _stmt.getText(_columnIndexOfScores);
          }
          _tmpScores = Converters.toPronunciationScoreList(_tmp_3);
          _result.setScores(_tmpScores);
          final int _tmpAverageAccuracyScore;
          _tmpAverageAccuracyScore = (int) (_stmt.getLong(_columnIndexOfAverageAccuracyScore));
          _result.setAverageAccuracyScore(_tmpAverageAccuracyScore);
          final int _tmpAverageRhythmScore;
          _tmpAverageRhythmScore = (int) (_stmt.getLong(_columnIndexOfAverageRhythmScore));
          _result.setAverageRhythmScore(_tmpAverageRhythmScore);
          final int _tmpAverageIntonationScore;
          _tmpAverageIntonationScore = (int) (_stmt.getLong(_columnIndexOfAverageIntonationScore));
          _result.setAverageIntonationScore(_tmpAverageIntonationScore);
          final long _tmpUserId;
          _tmpUserId = _stmt.getLong(_columnIndexOfUserId);
          _result.setUserId(_tmpUserId);
          final int _tmpDifficultyLevel;
          _tmpDifficultyLevel = (int) (_stmt.getLong(_columnIndexOfDifficultyLevel));
          _result.setDifficultyLevel(_tmpDifficultyLevel);
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
  public LiveData<List<PronunciationSession>> getSessionsForUser(final long userId) {
    final String _sql = "SELECT * FROM pronunciation_sessions WHERE userId = ? ORDER BY startTime DESC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"pronunciation_sessions"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, userId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfStartTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "startTime");
        final int _columnIndexOfEndTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "endTime");
        final int _columnIndexOfWords = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "words");
        final int _columnIndexOfScores = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "scores");
        final int _columnIndexOfAverageAccuracyScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "averageAccuracyScore");
        final int _columnIndexOfAverageRhythmScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "averageRhythmScore");
        final int _columnIndexOfAverageIntonationScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "averageIntonationScore");
        final int _columnIndexOfUserId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "userId");
        final int _columnIndexOfDifficultyLevel = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficultyLevel");
        final List<PronunciationSession> _result = new ArrayList<PronunciationSession>();
        while (_stmt.step()) {
          final PronunciationSession _item;
          _item = new PronunciationSession();
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          _item.setId(_tmpId);
          final Date _tmpStartTime;
          final Long _tmp;
          if (_stmt.isNull(_columnIndexOfStartTime)) {
            _tmp = null;
          } else {
            _tmp = _stmt.getLong(_columnIndexOfStartTime);
          }
          _tmpStartTime = Converters.fromTimestamp(_tmp);
          _item.setStartTime(_tmpStartTime);
          final Date _tmpEndTime;
          final Long _tmp_1;
          if (_stmt.isNull(_columnIndexOfEndTime)) {
            _tmp_1 = null;
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfEndTime);
          }
          _tmpEndTime = Converters.fromTimestamp(_tmp_1);
          _item.setEndTime(_tmpEndTime);
          final List<Word> _tmpWords;
          final String _tmp_2;
          if (_stmt.isNull(_columnIndexOfWords)) {
            _tmp_2 = null;
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfWords);
          }
          _tmpWords = Converters.toWordList(_tmp_2);
          _item.setWords(_tmpWords);
          final List<PronunciationScore> _tmpScores;
          final String _tmp_3;
          if (_stmt.isNull(_columnIndexOfScores)) {
            _tmp_3 = null;
          } else {
            _tmp_3 = _stmt.getText(_columnIndexOfScores);
          }
          _tmpScores = Converters.toPronunciationScoreList(_tmp_3);
          _item.setScores(_tmpScores);
          final int _tmpAverageAccuracyScore;
          _tmpAverageAccuracyScore = (int) (_stmt.getLong(_columnIndexOfAverageAccuracyScore));
          _item.setAverageAccuracyScore(_tmpAverageAccuracyScore);
          final int _tmpAverageRhythmScore;
          _tmpAverageRhythmScore = (int) (_stmt.getLong(_columnIndexOfAverageRhythmScore));
          _item.setAverageRhythmScore(_tmpAverageRhythmScore);
          final int _tmpAverageIntonationScore;
          _tmpAverageIntonationScore = (int) (_stmt.getLong(_columnIndexOfAverageIntonationScore));
          _item.setAverageIntonationScore(_tmpAverageIntonationScore);
          final long _tmpUserId;
          _tmpUserId = _stmt.getLong(_columnIndexOfUserId);
          _item.setUserId(_tmpUserId);
          final int _tmpDifficultyLevel;
          _tmpDifficultyLevel = (int) (_stmt.getLong(_columnIndexOfDifficultyLevel));
          _item.setDifficultyLevel(_tmpDifficultyLevel);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<PronunciationSession> getMostRecentSession() {
    final String _sql = "SELECT * FROM pronunciation_sessions ORDER BY startTime DESC LIMIT 1";
    return __db.getInvalidationTracker().createLiveData(new String[] {"pronunciation_sessions"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfStartTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "startTime");
        final int _columnIndexOfEndTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "endTime");
        final int _columnIndexOfWords = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "words");
        final int _columnIndexOfScores = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "scores");
        final int _columnIndexOfAverageAccuracyScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "averageAccuracyScore");
        final int _columnIndexOfAverageRhythmScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "averageRhythmScore");
        final int _columnIndexOfAverageIntonationScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "averageIntonationScore");
        final int _columnIndexOfUserId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "userId");
        final int _columnIndexOfDifficultyLevel = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficultyLevel");
        final PronunciationSession _result;
        if (_stmt.step()) {
          _result = new PronunciationSession();
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          _result.setId(_tmpId);
          final Date _tmpStartTime;
          final Long _tmp;
          if (_stmt.isNull(_columnIndexOfStartTime)) {
            _tmp = null;
          } else {
            _tmp = _stmt.getLong(_columnIndexOfStartTime);
          }
          _tmpStartTime = Converters.fromTimestamp(_tmp);
          _result.setStartTime(_tmpStartTime);
          final Date _tmpEndTime;
          final Long _tmp_1;
          if (_stmt.isNull(_columnIndexOfEndTime)) {
            _tmp_1 = null;
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfEndTime);
          }
          _tmpEndTime = Converters.fromTimestamp(_tmp_1);
          _result.setEndTime(_tmpEndTime);
          final List<Word> _tmpWords;
          final String _tmp_2;
          if (_stmt.isNull(_columnIndexOfWords)) {
            _tmp_2 = null;
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfWords);
          }
          _tmpWords = Converters.toWordList(_tmp_2);
          _result.setWords(_tmpWords);
          final List<PronunciationScore> _tmpScores;
          final String _tmp_3;
          if (_stmt.isNull(_columnIndexOfScores)) {
            _tmp_3 = null;
          } else {
            _tmp_3 = _stmt.getText(_columnIndexOfScores);
          }
          _tmpScores = Converters.toPronunciationScoreList(_tmp_3);
          _result.setScores(_tmpScores);
          final int _tmpAverageAccuracyScore;
          _tmpAverageAccuracyScore = (int) (_stmt.getLong(_columnIndexOfAverageAccuracyScore));
          _result.setAverageAccuracyScore(_tmpAverageAccuracyScore);
          final int _tmpAverageRhythmScore;
          _tmpAverageRhythmScore = (int) (_stmt.getLong(_columnIndexOfAverageRhythmScore));
          _result.setAverageRhythmScore(_tmpAverageRhythmScore);
          final int _tmpAverageIntonationScore;
          _tmpAverageIntonationScore = (int) (_stmt.getLong(_columnIndexOfAverageIntonationScore));
          _result.setAverageIntonationScore(_tmpAverageIntonationScore);
          final long _tmpUserId;
          _tmpUserId = _stmt.getLong(_columnIndexOfUserId);
          _result.setUserId(_tmpUserId);
          final int _tmpDifficultyLevel;
          _tmpDifficultyLevel = (int) (_stmt.getLong(_columnIndexOfDifficultyLevel));
          _result.setDifficultyLevel(_tmpDifficultyLevel);
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
  public int getSessionCount() {
    final String _sql = "SELECT COUNT(*) FROM pronunciation_sessions";
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
  public int getUserSessionCount(final long userId) {
    final String _sql = "SELECT COUNT(*) FROM pronunciation_sessions WHERE userId = ?";
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
  public double getUserAverageAccuracyScore(final long userId) {
    final String _sql = "SELECT AVG(averageAccuracyScore) FROM pronunciation_sessions WHERE userId = ?";
    return DBUtil.performBlocking(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, userId);
        final double _result;
        if (_stmt.step()) {
          _result = _stmt.getDouble(0);
        } else {
          _result = 0.0;
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public double getUserAverageRhythmScore(final long userId) {
    final String _sql = "SELECT AVG(averageRhythmScore) FROM pronunciation_sessions WHERE userId = ?";
    return DBUtil.performBlocking(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, userId);
        final double _result;
        if (_stmt.step()) {
          _result = _stmt.getDouble(0);
        } else {
          _result = 0.0;
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public double getUserAverageIntonationScore(final long userId) {
    final String _sql = "SELECT AVG(averageIntonationScore) FROM pronunciation_sessions WHERE userId = ?";
    return DBUtil.performBlocking(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, userId);
        final double _result;
        if (_stmt.step()) {
          _result = _stmt.getDouble(0);
        } else {
          _result = 0.0;
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<PronunciationProgressStats> getPronunciationProgressStats(final long userId) {
    final String _sql = "SELECT COUNT(*) as sessionCount, (SELECT COUNT(*) FROM pronunciation_scores WHERE sessionId IN   (SELECT id FROM pronunciation_sessions WHERE userId = ?)) as wordAttempts, AVG(averageAccuracyScore) as averageAccuracyScore, MAX(averageAccuracyScore) as bestAccuracyScore, (SELECT COUNT(DISTINCT word) FROM pronunciation_scores WHERE sessionId IN   (SELECT id FROM pronunciation_sessions WHERE userId = ?)) as uniqueWordsPracticed FROM pronunciation_sessions WHERE userId = ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"pronunciation_scores",
        "pronunciation_sessions"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, userId);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, userId);
        _argIndex = 3;
        _stmt.bindLong(_argIndex, userId);
        final int _columnIndexOfSessionCount = 0;
        final int _columnIndexOfWordAttempts = 1;
        final int _columnIndexOfAverageAccuracyScore = 2;
        final int _columnIndexOfBestAccuracyScore = 3;
        final int _columnIndexOfUniqueWordsPracticed = 4;
        final PronunciationProgressStats _result;
        if (_stmt.step()) {
          _result = new PronunciationProgressStats();
          final int _tmpSessionCount;
          _tmpSessionCount = (int) (_stmt.getLong(_columnIndexOfSessionCount));
          _result.setSessionCount(_tmpSessionCount);
          final int _tmpWordAttempts;
          _tmpWordAttempts = (int) (_stmt.getLong(_columnIndexOfWordAttempts));
          _result.setWordAttempts(_tmpWordAttempts);
          final double _tmpAverageAccuracyScore;
          _tmpAverageAccuracyScore = _stmt.getDouble(_columnIndexOfAverageAccuracyScore);
          _result.setAverageAccuracyScore(_tmpAverageAccuracyScore);
          final double _tmpBestAccuracyScore;
          _tmpBestAccuracyScore = _stmt.getDouble(_columnIndexOfBestAccuracyScore);
          _result.setBestAccuracyScore(_tmpBestAccuracyScore);
          final int _tmpUniqueWordsPracticed;
          _tmpUniqueWordsPracticed = (int) (_stmt.getLong(_columnIndexOfUniqueWordsPracticed));
          _result.setUniqueWordsPracticed(_tmpUniqueWordsPracticed);
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
