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
public final class PronunciationScoreDao_Impl implements PronunciationScoreDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<PronunciationScore> __insertAdapterOfPronunciationScore;

  private final EntityDeleteOrUpdateAdapter<PronunciationScore> __deleteAdapterOfPronunciationScore;

  private final EntityDeleteOrUpdateAdapter<PronunciationScore> __updateAdapterOfPronunciationScore;

  public PronunciationScoreDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfPronunciationScore = new EntityInsertAdapter<PronunciationScore>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `pronunciation_scores` (`id`,`word`,`accuracyScore`,`rhythmScore`,`intonationScore`,`phoneticFeedback`,`recordingPath`,`timestamp`,`sessionId`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          final PronunciationScore entity) {
        statement.bindLong(1, entity.getId());
        final String _tmp = Converters.fromWord(entity.getWord());
        if (_tmp == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, _tmp);
        }
        statement.bindLong(3, entity.getAccuracyScore());
        statement.bindLong(4, entity.getRhythmScore());
        statement.bindLong(5, entity.getIntonationScore());
        if (entity.getPhoneticFeedback() == null) {
          statement.bindNull(6);
        } else {
          statement.bindText(6, entity.getPhoneticFeedback());
        }
        if (entity.getRecordingPath() == null) {
          statement.bindNull(7);
        } else {
          statement.bindText(7, entity.getRecordingPath());
        }
        final Long _tmp_1 = Converters.dateToTimestamp(entity.getTimestamp());
        if (_tmp_1 == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, _tmp_1);
        }
        statement.bindLong(9, entity.getSessionId());
      }
    };
    this.__deleteAdapterOfPronunciationScore = new EntityDeleteOrUpdateAdapter<PronunciationScore>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `pronunciation_scores` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          final PronunciationScore entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfPronunciationScore = new EntityDeleteOrUpdateAdapter<PronunciationScore>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `pronunciation_scores` SET `id` = ?,`word` = ?,`accuracyScore` = ?,`rhythmScore` = ?,`intonationScore` = ?,`phoneticFeedback` = ?,`recordingPath` = ?,`timestamp` = ?,`sessionId` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          final PronunciationScore entity) {
        statement.bindLong(1, entity.getId());
        final String _tmp = Converters.fromWord(entity.getWord());
        if (_tmp == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, _tmp);
        }
        statement.bindLong(3, entity.getAccuracyScore());
        statement.bindLong(4, entity.getRhythmScore());
        statement.bindLong(5, entity.getIntonationScore());
        if (entity.getPhoneticFeedback() == null) {
          statement.bindNull(6);
        } else {
          statement.bindText(6, entity.getPhoneticFeedback());
        }
        if (entity.getRecordingPath() == null) {
          statement.bindNull(7);
        } else {
          statement.bindText(7, entity.getRecordingPath());
        }
        final Long _tmp_1 = Converters.dateToTimestamp(entity.getTimestamp());
        if (_tmp_1 == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, _tmp_1);
        }
        statement.bindLong(9, entity.getSessionId());
        statement.bindLong(10, entity.getId());
      }
    };
  }

  @Override
  public long insert(final PronunciationScore score) {
    return DBUtil.performBlocking(__db, false, true, (_connection) -> {
      return __insertAdapterOfPronunciationScore.insertAndReturnId(_connection, score);
    });
  }

  @Override
  public void delete(final PronunciationScore score) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __deleteAdapterOfPronunciationScore.handle(_connection, score);
      return null;
    });
  }

  @Override
  public void update(final PronunciationScore score) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __updateAdapterOfPronunciationScore.handle(_connection, score);
      return null;
    });
  }

  @Override
  public LiveData<PronunciationScore> getScoreById(final long scoreId) {
    final String _sql = "SELECT * FROM pronunciation_scores WHERE id = ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"pronunciation_scores"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, scoreId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfWord = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "word");
        final int _columnIndexOfAccuracyScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "accuracyScore");
        final int _columnIndexOfRhythmScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "rhythmScore");
        final int _columnIndexOfIntonationScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "intonationScore");
        final int _columnIndexOfPhoneticFeedback = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "phoneticFeedback");
        final int _columnIndexOfRecordingPath = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "recordingPath");
        final int _columnIndexOfTimestamp = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timestamp");
        final int _columnIndexOfSessionId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "sessionId");
        final PronunciationScore _result;
        if (_stmt.step()) {
          _result = new PronunciationScore();
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          _result.setId(_tmpId);
          final Word _tmpWord;
          final String _tmp;
          if (_stmt.isNull(_columnIndexOfWord)) {
            _tmp = null;
          } else {
            _tmp = _stmt.getText(_columnIndexOfWord);
          }
          _tmpWord = Converters.toWord(_tmp);
          _result.setWord(_tmpWord);
          final int _tmpAccuracyScore;
          _tmpAccuracyScore = (int) (_stmt.getLong(_columnIndexOfAccuracyScore));
          _result.setAccuracyScore(_tmpAccuracyScore);
          final int _tmpRhythmScore;
          _tmpRhythmScore = (int) (_stmt.getLong(_columnIndexOfRhythmScore));
          _result.setRhythmScore(_tmpRhythmScore);
          final int _tmpIntonationScore;
          _tmpIntonationScore = (int) (_stmt.getLong(_columnIndexOfIntonationScore));
          _result.setIntonationScore(_tmpIntonationScore);
          final String _tmpPhoneticFeedback;
          if (_stmt.isNull(_columnIndexOfPhoneticFeedback)) {
            _tmpPhoneticFeedback = null;
          } else {
            _tmpPhoneticFeedback = _stmt.getText(_columnIndexOfPhoneticFeedback);
          }
          _result.setPhoneticFeedback(_tmpPhoneticFeedback);
          final String _tmpRecordingPath;
          if (_stmt.isNull(_columnIndexOfRecordingPath)) {
            _tmpRecordingPath = null;
          } else {
            _tmpRecordingPath = _stmt.getText(_columnIndexOfRecordingPath);
          }
          _result.setRecordingPath(_tmpRecordingPath);
          final Date _tmpTimestamp;
          final Long _tmp_1;
          if (_stmt.isNull(_columnIndexOfTimestamp)) {
            _tmp_1 = null;
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfTimestamp);
          }
          _tmpTimestamp = Converters.fromTimestamp(_tmp_1);
          _result.setTimestamp(_tmpTimestamp);
          final long _tmpSessionId;
          _tmpSessionId = _stmt.getLong(_columnIndexOfSessionId);
          _result.setSessionId(_tmpSessionId);
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
  public LiveData<List<PronunciationScore>> getScoresForSession(final long sessionId) {
    final String _sql = "SELECT * FROM pronunciation_scores WHERE sessionId = ? ORDER BY timestamp ASC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"pronunciation_scores"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, sessionId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfWord = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "word");
        final int _columnIndexOfAccuracyScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "accuracyScore");
        final int _columnIndexOfRhythmScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "rhythmScore");
        final int _columnIndexOfIntonationScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "intonationScore");
        final int _columnIndexOfPhoneticFeedback = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "phoneticFeedback");
        final int _columnIndexOfRecordingPath = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "recordingPath");
        final int _columnIndexOfTimestamp = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timestamp");
        final int _columnIndexOfSessionId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "sessionId");
        final List<PronunciationScore> _result = new ArrayList<PronunciationScore>();
        while (_stmt.step()) {
          final PronunciationScore _item;
          _item = new PronunciationScore();
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          _item.setId(_tmpId);
          final Word _tmpWord;
          final String _tmp;
          if (_stmt.isNull(_columnIndexOfWord)) {
            _tmp = null;
          } else {
            _tmp = _stmt.getText(_columnIndexOfWord);
          }
          _tmpWord = Converters.toWord(_tmp);
          _item.setWord(_tmpWord);
          final int _tmpAccuracyScore;
          _tmpAccuracyScore = (int) (_stmt.getLong(_columnIndexOfAccuracyScore));
          _item.setAccuracyScore(_tmpAccuracyScore);
          final int _tmpRhythmScore;
          _tmpRhythmScore = (int) (_stmt.getLong(_columnIndexOfRhythmScore));
          _item.setRhythmScore(_tmpRhythmScore);
          final int _tmpIntonationScore;
          _tmpIntonationScore = (int) (_stmt.getLong(_columnIndexOfIntonationScore));
          _item.setIntonationScore(_tmpIntonationScore);
          final String _tmpPhoneticFeedback;
          if (_stmt.isNull(_columnIndexOfPhoneticFeedback)) {
            _tmpPhoneticFeedback = null;
          } else {
            _tmpPhoneticFeedback = _stmt.getText(_columnIndexOfPhoneticFeedback);
          }
          _item.setPhoneticFeedback(_tmpPhoneticFeedback);
          final String _tmpRecordingPath;
          if (_stmt.isNull(_columnIndexOfRecordingPath)) {
            _tmpRecordingPath = null;
          } else {
            _tmpRecordingPath = _stmt.getText(_columnIndexOfRecordingPath);
          }
          _item.setRecordingPath(_tmpRecordingPath);
          final Date _tmpTimestamp;
          final Long _tmp_1;
          if (_stmt.isNull(_columnIndexOfTimestamp)) {
            _tmp_1 = null;
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfTimestamp);
          }
          _tmpTimestamp = Converters.fromTimestamp(_tmp_1);
          _item.setTimestamp(_tmpTimestamp);
          final long _tmpSessionId;
          _tmpSessionId = _stmt.getLong(_columnIndexOfSessionId);
          _item.setSessionId(_tmpSessionId);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<PronunciationScore>> getScoresForWord(final long wordId) {
    final String _sql = "SELECT * FROM pronunciation_scores WHERE word = ? ORDER BY timestamp DESC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"pronunciation_scores"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, wordId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfWord = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "word");
        final int _columnIndexOfAccuracyScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "accuracyScore");
        final int _columnIndexOfRhythmScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "rhythmScore");
        final int _columnIndexOfIntonationScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "intonationScore");
        final int _columnIndexOfPhoneticFeedback = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "phoneticFeedback");
        final int _columnIndexOfRecordingPath = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "recordingPath");
        final int _columnIndexOfTimestamp = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timestamp");
        final int _columnIndexOfSessionId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "sessionId");
        final List<PronunciationScore> _result = new ArrayList<PronunciationScore>();
        while (_stmt.step()) {
          final PronunciationScore _item;
          _item = new PronunciationScore();
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          _item.setId(_tmpId);
          final Word _tmpWord;
          final String _tmp;
          if (_stmt.isNull(_columnIndexOfWord)) {
            _tmp = null;
          } else {
            _tmp = _stmt.getText(_columnIndexOfWord);
          }
          _tmpWord = Converters.toWord(_tmp);
          _item.setWord(_tmpWord);
          final int _tmpAccuracyScore;
          _tmpAccuracyScore = (int) (_stmt.getLong(_columnIndexOfAccuracyScore));
          _item.setAccuracyScore(_tmpAccuracyScore);
          final int _tmpRhythmScore;
          _tmpRhythmScore = (int) (_stmt.getLong(_columnIndexOfRhythmScore));
          _item.setRhythmScore(_tmpRhythmScore);
          final int _tmpIntonationScore;
          _tmpIntonationScore = (int) (_stmt.getLong(_columnIndexOfIntonationScore));
          _item.setIntonationScore(_tmpIntonationScore);
          final String _tmpPhoneticFeedback;
          if (_stmt.isNull(_columnIndexOfPhoneticFeedback)) {
            _tmpPhoneticFeedback = null;
          } else {
            _tmpPhoneticFeedback = _stmt.getText(_columnIndexOfPhoneticFeedback);
          }
          _item.setPhoneticFeedback(_tmpPhoneticFeedback);
          final String _tmpRecordingPath;
          if (_stmt.isNull(_columnIndexOfRecordingPath)) {
            _tmpRecordingPath = null;
          } else {
            _tmpRecordingPath = _stmt.getText(_columnIndexOfRecordingPath);
          }
          _item.setRecordingPath(_tmpRecordingPath);
          final Date _tmpTimestamp;
          final Long _tmp_1;
          if (_stmt.isNull(_columnIndexOfTimestamp)) {
            _tmp_1 = null;
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfTimestamp);
          }
          _tmpTimestamp = Converters.fromTimestamp(_tmp_1);
          _item.setTimestamp(_tmpTimestamp);
          final long _tmpSessionId;
          _tmpSessionId = _stmt.getLong(_columnIndexOfSessionId);
          _item.setSessionId(_tmpSessionId);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<PronunciationScore> getBestScoreForWord(final long wordId) {
    final String _sql = "SELECT * FROM pronunciation_scores WHERE word = ? ORDER BY accuracyScore DESC LIMIT 1";
    return __db.getInvalidationTracker().createLiveData(new String[] {"pronunciation_scores"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, wordId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfWord = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "word");
        final int _columnIndexOfAccuracyScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "accuracyScore");
        final int _columnIndexOfRhythmScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "rhythmScore");
        final int _columnIndexOfIntonationScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "intonationScore");
        final int _columnIndexOfPhoneticFeedback = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "phoneticFeedback");
        final int _columnIndexOfRecordingPath = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "recordingPath");
        final int _columnIndexOfTimestamp = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timestamp");
        final int _columnIndexOfSessionId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "sessionId");
        final PronunciationScore _result;
        if (_stmt.step()) {
          _result = new PronunciationScore();
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          _result.setId(_tmpId);
          final Word _tmpWord;
          final String _tmp;
          if (_stmt.isNull(_columnIndexOfWord)) {
            _tmp = null;
          } else {
            _tmp = _stmt.getText(_columnIndexOfWord);
          }
          _tmpWord = Converters.toWord(_tmp);
          _result.setWord(_tmpWord);
          final int _tmpAccuracyScore;
          _tmpAccuracyScore = (int) (_stmt.getLong(_columnIndexOfAccuracyScore));
          _result.setAccuracyScore(_tmpAccuracyScore);
          final int _tmpRhythmScore;
          _tmpRhythmScore = (int) (_stmt.getLong(_columnIndexOfRhythmScore));
          _result.setRhythmScore(_tmpRhythmScore);
          final int _tmpIntonationScore;
          _tmpIntonationScore = (int) (_stmt.getLong(_columnIndexOfIntonationScore));
          _result.setIntonationScore(_tmpIntonationScore);
          final String _tmpPhoneticFeedback;
          if (_stmt.isNull(_columnIndexOfPhoneticFeedback)) {
            _tmpPhoneticFeedback = null;
          } else {
            _tmpPhoneticFeedback = _stmt.getText(_columnIndexOfPhoneticFeedback);
          }
          _result.setPhoneticFeedback(_tmpPhoneticFeedback);
          final String _tmpRecordingPath;
          if (_stmt.isNull(_columnIndexOfRecordingPath)) {
            _tmpRecordingPath = null;
          } else {
            _tmpRecordingPath = _stmt.getText(_columnIndexOfRecordingPath);
          }
          _result.setRecordingPath(_tmpRecordingPath);
          final Date _tmpTimestamp;
          final Long _tmp_1;
          if (_stmt.isNull(_columnIndexOfTimestamp)) {
            _tmp_1 = null;
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfTimestamp);
          }
          _tmpTimestamp = Converters.fromTimestamp(_tmp_1);
          _result.setTimestamp(_tmpTimestamp);
          final long _tmpSessionId;
          _tmpSessionId = _stmt.getLong(_columnIndexOfSessionId);
          _result.setSessionId(_tmpSessionId);
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
  public double getAverageScoreForWord(final long wordId) {
    final String _sql = "SELECT AVG(accuracyScore) FROM pronunciation_scores WHERE word = ?";
    return DBUtil.performBlocking(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, wordId);
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
  public int getHighScoreCount() {
    final String _sql = "SELECT COUNT(*) FROM pronunciation_scores WHERE accuracyScore >= 80";
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
  public LiveData<List<PronunciationScore>> getRecentScores(final int limit) {
    final String _sql = "SELECT * FROM pronunciation_scores ORDER BY timestamp DESC LIMIT ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"pronunciation_scores"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, limit);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfWord = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "word");
        final int _columnIndexOfAccuracyScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "accuracyScore");
        final int _columnIndexOfRhythmScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "rhythmScore");
        final int _columnIndexOfIntonationScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "intonationScore");
        final int _columnIndexOfPhoneticFeedback = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "phoneticFeedback");
        final int _columnIndexOfRecordingPath = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "recordingPath");
        final int _columnIndexOfTimestamp = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timestamp");
        final int _columnIndexOfSessionId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "sessionId");
        final List<PronunciationScore> _result = new ArrayList<PronunciationScore>();
        while (_stmt.step()) {
          final PronunciationScore _item;
          _item = new PronunciationScore();
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          _item.setId(_tmpId);
          final Word _tmpWord;
          final String _tmp;
          if (_stmt.isNull(_columnIndexOfWord)) {
            _tmp = null;
          } else {
            _tmp = _stmt.getText(_columnIndexOfWord);
          }
          _tmpWord = Converters.toWord(_tmp);
          _item.setWord(_tmpWord);
          final int _tmpAccuracyScore;
          _tmpAccuracyScore = (int) (_stmt.getLong(_columnIndexOfAccuracyScore));
          _item.setAccuracyScore(_tmpAccuracyScore);
          final int _tmpRhythmScore;
          _tmpRhythmScore = (int) (_stmt.getLong(_columnIndexOfRhythmScore));
          _item.setRhythmScore(_tmpRhythmScore);
          final int _tmpIntonationScore;
          _tmpIntonationScore = (int) (_stmt.getLong(_columnIndexOfIntonationScore));
          _item.setIntonationScore(_tmpIntonationScore);
          final String _tmpPhoneticFeedback;
          if (_stmt.isNull(_columnIndexOfPhoneticFeedback)) {
            _tmpPhoneticFeedback = null;
          } else {
            _tmpPhoneticFeedback = _stmt.getText(_columnIndexOfPhoneticFeedback);
          }
          _item.setPhoneticFeedback(_tmpPhoneticFeedback);
          final String _tmpRecordingPath;
          if (_stmt.isNull(_columnIndexOfRecordingPath)) {
            _tmpRecordingPath = null;
          } else {
            _tmpRecordingPath = _stmt.getText(_columnIndexOfRecordingPath);
          }
          _item.setRecordingPath(_tmpRecordingPath);
          final Date _tmpTimestamp;
          final Long _tmp_1;
          if (_stmt.isNull(_columnIndexOfTimestamp)) {
            _tmp_1 = null;
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfTimestamp);
          }
          _tmpTimestamp = Converters.fromTimestamp(_tmp_1);
          _item.setTimestamp(_tmpTimestamp);
          final long _tmpSessionId;
          _tmpSessionId = _stmt.getLong(_columnIndexOfSessionId);
          _item.setSessionId(_tmpSessionId);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Long>> getWordsThatNeedPractice(final int threshold) {
    final String _sql = "SELECT DISTINCT CAST(word AS INTEGER) FROM pronunciation_scores GROUP BY word HAVING AVG(accuracyScore) < ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"pronunciation_scores"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, threshold);
        final List<Long> _result = new ArrayList<Long>();
        while (_stmt.step()) {
          final Long _item;
          final Long _tmp;
          if (_stmt.isNull(0)) {
            _tmp = null;
          } else {
            _tmp = _stmt.getLong(0);
          }
          _item = _tmp;
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public void deleteScoresForSession(final long sessionId) {
    final String _sql = "DELETE FROM pronunciation_scores WHERE sessionId = ?";
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
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
