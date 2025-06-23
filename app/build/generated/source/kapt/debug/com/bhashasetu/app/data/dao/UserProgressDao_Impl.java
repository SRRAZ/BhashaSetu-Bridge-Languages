package com.bhashasetu.app.data.dao;

import androidx.annotation.NonNull;
import androidx.collection.LongSparseArray;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeleteOrUpdateAdapter;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.coroutines.FlowUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.RelationUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteStatement;
import com.bhashasetu.app.data.model.UserProgress;
import com.bhashasetu.app.data.model.Word;
import com.bhashasetu.app.data.relation.WordWithProgress;
import java.lang.Class;
import java.lang.Float;
import java.lang.Integer;
import java.lang.Long;
import java.lang.NullPointerException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
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
public final class UserProgressDao_Impl implements UserProgressDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<UserProgress> __insertAdapterOfUserProgress;

  private final EntityDeleteOrUpdateAdapter<UserProgress> __deleteAdapterOfUserProgress;

  private final EntityDeleteOrUpdateAdapter<UserProgress> __updateAdapterOfUserProgress;

  public UserProgressDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfUserProgress = new EntityInsertAdapter<UserProgress>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `user_progress` (`wordId`,`proficiencyLevel`,`correctAttempts`,`totalAttempts`,`easeFactor`,`intervalDays`,`repetitions`,`lastAttemptAt`,`lastCorrectAt`,`nextReviewDue`,`timeSpentMs`,`lastConfidenceRating`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final UserProgress entity) {
        statement.bindLong(1, entity.getWordId());
        statement.bindLong(2, entity.getProficiencyLevel());
        statement.bindLong(3, entity.getCorrectAttempts());
        statement.bindLong(4, entity.getTotalAttempts());
        statement.bindDouble(5, entity.getEaseFactor());
        statement.bindLong(6, entity.getIntervalDays());
        statement.bindLong(7, entity.getRepetitions());
        if (entity.getLastAttemptAt() == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, entity.getLastAttemptAt());
        }
        if (entity.getLastCorrectAt() == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, entity.getLastCorrectAt());
        }
        if (entity.getNextReviewDue() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getNextReviewDue());
        }
        statement.bindLong(11, entity.getTimeSpentMs());
        statement.bindLong(12, entity.getLastConfidenceRating());
      }
    };
    this.__deleteAdapterOfUserProgress = new EntityDeleteOrUpdateAdapter<UserProgress>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `user_progress` WHERE `wordId` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final UserProgress entity) {
        statement.bindLong(1, entity.getWordId());
      }
    };
    this.__updateAdapterOfUserProgress = new EntityDeleteOrUpdateAdapter<UserProgress>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `user_progress` SET `wordId` = ?,`proficiencyLevel` = ?,`correctAttempts` = ?,`totalAttempts` = ?,`easeFactor` = ?,`intervalDays` = ?,`repetitions` = ?,`lastAttemptAt` = ?,`lastCorrectAt` = ?,`nextReviewDue` = ?,`timeSpentMs` = ?,`lastConfidenceRating` = ? WHERE `wordId` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final UserProgress entity) {
        statement.bindLong(1, entity.getWordId());
        statement.bindLong(2, entity.getProficiencyLevel());
        statement.bindLong(3, entity.getCorrectAttempts());
        statement.bindLong(4, entity.getTotalAttempts());
        statement.bindDouble(5, entity.getEaseFactor());
        statement.bindLong(6, entity.getIntervalDays());
        statement.bindLong(7, entity.getRepetitions());
        if (entity.getLastAttemptAt() == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, entity.getLastAttemptAt());
        }
        if (entity.getLastCorrectAt() == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, entity.getLastCorrectAt());
        }
        if (entity.getNextReviewDue() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getNextReviewDue());
        }
        statement.bindLong(11, entity.getTimeSpentMs());
        statement.bindLong(12, entity.getLastConfidenceRating());
        statement.bindLong(13, entity.getWordId());
      }
    };
  }

  @Override
  public Object insert(final UserProgress progress, final Continuation<? super Unit> $completion) {
    if (progress == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __insertAdapterOfUserProgress.insert(_connection, progress);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Object insertAll(final List<UserProgress> progressList,
      final Continuation<? super Unit> $completion) {
    if (progressList == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __insertAdapterOfUserProgress.insert(_connection, progressList);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Object delete(final UserProgress progress, final Continuation<? super Unit> $completion) {
    if (progress == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __deleteAdapterOfUserProgress.handle(_connection, progress);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Object update(final UserProgress progress, final Continuation<? super Unit> $completion) {
    if (progress == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __updateAdapterOfUserProgress.handle(_connection, progress);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Object getProgressForWord(final long wordId,
      final Continuation<? super UserProgress> $completion) {
    final String _sql = "SELECT * FROM user_progress WHERE wordId = ?";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, wordId);
        final int _columnIndexOfWordId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wordId");
        final int _columnIndexOfProficiencyLevel = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "proficiencyLevel");
        final int _columnIndexOfCorrectAttempts = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctAttempts");
        final int _columnIndexOfTotalAttempts = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalAttempts");
        final int _columnIndexOfEaseFactor = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "easeFactor");
        final int _columnIndexOfIntervalDays = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "intervalDays");
        final int _columnIndexOfRepetitions = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "repetitions");
        final int _columnIndexOfLastAttemptAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAttemptAt");
        final int _columnIndexOfLastCorrectAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastCorrectAt");
        final int _columnIndexOfNextReviewDue = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nextReviewDue");
        final int _columnIndexOfTimeSpentMs = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timeSpentMs");
        final int _columnIndexOfLastConfidenceRating = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastConfidenceRating");
        final UserProgress _result;
        if (_stmt.step()) {
          final long _tmpWordId;
          _tmpWordId = _stmt.getLong(_columnIndexOfWordId);
          final int _tmpProficiencyLevel;
          _tmpProficiencyLevel = (int) (_stmt.getLong(_columnIndexOfProficiencyLevel));
          final int _tmpCorrectAttempts;
          _tmpCorrectAttempts = (int) (_stmt.getLong(_columnIndexOfCorrectAttempts));
          final int _tmpTotalAttempts;
          _tmpTotalAttempts = (int) (_stmt.getLong(_columnIndexOfTotalAttempts));
          final float _tmpEaseFactor;
          _tmpEaseFactor = (float) (_stmt.getDouble(_columnIndexOfEaseFactor));
          final int _tmpIntervalDays;
          _tmpIntervalDays = (int) (_stmt.getLong(_columnIndexOfIntervalDays));
          final int _tmpRepetitions;
          _tmpRepetitions = (int) (_stmt.getLong(_columnIndexOfRepetitions));
          final Long _tmpLastAttemptAt;
          if (_stmt.isNull(_columnIndexOfLastAttemptAt)) {
            _tmpLastAttemptAt = null;
          } else {
            _tmpLastAttemptAt = _stmt.getLong(_columnIndexOfLastAttemptAt);
          }
          final Long _tmpLastCorrectAt;
          if (_stmt.isNull(_columnIndexOfLastCorrectAt)) {
            _tmpLastCorrectAt = null;
          } else {
            _tmpLastCorrectAt = _stmt.getLong(_columnIndexOfLastCorrectAt);
          }
          final Long _tmpNextReviewDue;
          if (_stmt.isNull(_columnIndexOfNextReviewDue)) {
            _tmpNextReviewDue = null;
          } else {
            _tmpNextReviewDue = _stmt.getLong(_columnIndexOfNextReviewDue);
          }
          final long _tmpTimeSpentMs;
          _tmpTimeSpentMs = _stmt.getLong(_columnIndexOfTimeSpentMs);
          final int _tmpLastConfidenceRating;
          _tmpLastConfidenceRating = (int) (_stmt.getLong(_columnIndexOfLastConfidenceRating));
          _result = new UserProgress(_tmpWordId,_tmpProficiencyLevel,_tmpCorrectAttempts,_tmpTotalAttempts,_tmpEaseFactor,_tmpIntervalDays,_tmpRepetitions,_tmpLastAttemptAt,_tmpLastCorrectAt,_tmpNextReviewDue,_tmpTimeSpentMs,_tmpLastConfidenceRating);
        } else {
          _result = null;
        }
        return _result;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public LiveData<UserProgress> getProgressForWordLiveData(final long wordId) {
    final String _sql = "SELECT * FROM user_progress WHERE wordId = ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"user_progress"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, wordId);
        final int _columnIndexOfWordId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wordId");
        final int _columnIndexOfProficiencyLevel = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "proficiencyLevel");
        final int _columnIndexOfCorrectAttempts = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctAttempts");
        final int _columnIndexOfTotalAttempts = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalAttempts");
        final int _columnIndexOfEaseFactor = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "easeFactor");
        final int _columnIndexOfIntervalDays = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "intervalDays");
        final int _columnIndexOfRepetitions = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "repetitions");
        final int _columnIndexOfLastAttemptAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAttemptAt");
        final int _columnIndexOfLastCorrectAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastCorrectAt");
        final int _columnIndexOfNextReviewDue = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nextReviewDue");
        final int _columnIndexOfTimeSpentMs = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timeSpentMs");
        final int _columnIndexOfLastConfidenceRating = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastConfidenceRating");
        final UserProgress _result;
        if (_stmt.step()) {
          final long _tmpWordId;
          _tmpWordId = _stmt.getLong(_columnIndexOfWordId);
          final int _tmpProficiencyLevel;
          _tmpProficiencyLevel = (int) (_stmt.getLong(_columnIndexOfProficiencyLevel));
          final int _tmpCorrectAttempts;
          _tmpCorrectAttempts = (int) (_stmt.getLong(_columnIndexOfCorrectAttempts));
          final int _tmpTotalAttempts;
          _tmpTotalAttempts = (int) (_stmt.getLong(_columnIndexOfTotalAttempts));
          final float _tmpEaseFactor;
          _tmpEaseFactor = (float) (_stmt.getDouble(_columnIndexOfEaseFactor));
          final int _tmpIntervalDays;
          _tmpIntervalDays = (int) (_stmt.getLong(_columnIndexOfIntervalDays));
          final int _tmpRepetitions;
          _tmpRepetitions = (int) (_stmt.getLong(_columnIndexOfRepetitions));
          final Long _tmpLastAttemptAt;
          if (_stmt.isNull(_columnIndexOfLastAttemptAt)) {
            _tmpLastAttemptAt = null;
          } else {
            _tmpLastAttemptAt = _stmt.getLong(_columnIndexOfLastAttemptAt);
          }
          final Long _tmpLastCorrectAt;
          if (_stmt.isNull(_columnIndexOfLastCorrectAt)) {
            _tmpLastCorrectAt = null;
          } else {
            _tmpLastCorrectAt = _stmt.getLong(_columnIndexOfLastCorrectAt);
          }
          final Long _tmpNextReviewDue;
          if (_stmt.isNull(_columnIndexOfNextReviewDue)) {
            _tmpNextReviewDue = null;
          } else {
            _tmpNextReviewDue = _stmt.getLong(_columnIndexOfNextReviewDue);
          }
          final long _tmpTimeSpentMs;
          _tmpTimeSpentMs = _stmt.getLong(_columnIndexOfTimeSpentMs);
          final int _tmpLastConfidenceRating;
          _tmpLastConfidenceRating = (int) (_stmt.getLong(_columnIndexOfLastConfidenceRating));
          _result = new UserProgress(_tmpWordId,_tmpProficiencyLevel,_tmpCorrectAttempts,_tmpTotalAttempts,_tmpEaseFactor,_tmpIntervalDays,_tmpRepetitions,_tmpLastAttemptAt,_tmpLastCorrectAt,_tmpNextReviewDue,_tmpTimeSpentMs,_tmpLastConfidenceRating);
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
  public Flow<UserProgress> getProgressForWordFlow(final long wordId) {
    final String _sql = "SELECT * FROM user_progress WHERE wordId = ?";
    return FlowUtil.createFlow(__db, false, new String[] {"user_progress"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, wordId);
        final int _columnIndexOfWordId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wordId");
        final int _columnIndexOfProficiencyLevel = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "proficiencyLevel");
        final int _columnIndexOfCorrectAttempts = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctAttempts");
        final int _columnIndexOfTotalAttempts = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalAttempts");
        final int _columnIndexOfEaseFactor = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "easeFactor");
        final int _columnIndexOfIntervalDays = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "intervalDays");
        final int _columnIndexOfRepetitions = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "repetitions");
        final int _columnIndexOfLastAttemptAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAttemptAt");
        final int _columnIndexOfLastCorrectAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastCorrectAt");
        final int _columnIndexOfNextReviewDue = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nextReviewDue");
        final int _columnIndexOfTimeSpentMs = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timeSpentMs");
        final int _columnIndexOfLastConfidenceRating = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastConfidenceRating");
        final UserProgress _result;
        if (_stmt.step()) {
          final long _tmpWordId;
          _tmpWordId = _stmt.getLong(_columnIndexOfWordId);
          final int _tmpProficiencyLevel;
          _tmpProficiencyLevel = (int) (_stmt.getLong(_columnIndexOfProficiencyLevel));
          final int _tmpCorrectAttempts;
          _tmpCorrectAttempts = (int) (_stmt.getLong(_columnIndexOfCorrectAttempts));
          final int _tmpTotalAttempts;
          _tmpTotalAttempts = (int) (_stmt.getLong(_columnIndexOfTotalAttempts));
          final float _tmpEaseFactor;
          _tmpEaseFactor = (float) (_stmt.getDouble(_columnIndexOfEaseFactor));
          final int _tmpIntervalDays;
          _tmpIntervalDays = (int) (_stmt.getLong(_columnIndexOfIntervalDays));
          final int _tmpRepetitions;
          _tmpRepetitions = (int) (_stmt.getLong(_columnIndexOfRepetitions));
          final Long _tmpLastAttemptAt;
          if (_stmt.isNull(_columnIndexOfLastAttemptAt)) {
            _tmpLastAttemptAt = null;
          } else {
            _tmpLastAttemptAt = _stmt.getLong(_columnIndexOfLastAttemptAt);
          }
          final Long _tmpLastCorrectAt;
          if (_stmt.isNull(_columnIndexOfLastCorrectAt)) {
            _tmpLastCorrectAt = null;
          } else {
            _tmpLastCorrectAt = _stmt.getLong(_columnIndexOfLastCorrectAt);
          }
          final Long _tmpNextReviewDue;
          if (_stmt.isNull(_columnIndexOfNextReviewDue)) {
            _tmpNextReviewDue = null;
          } else {
            _tmpNextReviewDue = _stmt.getLong(_columnIndexOfNextReviewDue);
          }
          final long _tmpTimeSpentMs;
          _tmpTimeSpentMs = _stmt.getLong(_columnIndexOfTimeSpentMs);
          final int _tmpLastConfidenceRating;
          _tmpLastConfidenceRating = (int) (_stmt.getLong(_columnIndexOfLastConfidenceRating));
          _result = new UserProgress(_tmpWordId,_tmpProficiencyLevel,_tmpCorrectAttempts,_tmpTotalAttempts,_tmpEaseFactor,_tmpIntervalDays,_tmpRepetitions,_tmpLastAttemptAt,_tmpLastCorrectAt,_tmpNextReviewDue,_tmpTimeSpentMs,_tmpLastConfidenceRating);
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
  public Flow<List<UserProgress>> getAllProgress() {
    final String _sql = "SELECT * FROM user_progress ORDER BY lastAttemptAt DESC";
    return FlowUtil.createFlow(__db, false, new String[] {"user_progress"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfWordId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wordId");
        final int _columnIndexOfProficiencyLevel = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "proficiencyLevel");
        final int _columnIndexOfCorrectAttempts = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctAttempts");
        final int _columnIndexOfTotalAttempts = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalAttempts");
        final int _columnIndexOfEaseFactor = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "easeFactor");
        final int _columnIndexOfIntervalDays = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "intervalDays");
        final int _columnIndexOfRepetitions = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "repetitions");
        final int _columnIndexOfLastAttemptAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAttemptAt");
        final int _columnIndexOfLastCorrectAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastCorrectAt");
        final int _columnIndexOfNextReviewDue = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nextReviewDue");
        final int _columnIndexOfTimeSpentMs = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timeSpentMs");
        final int _columnIndexOfLastConfidenceRating = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastConfidenceRating");
        final List<UserProgress> _result = new ArrayList<UserProgress>();
        while (_stmt.step()) {
          final UserProgress _item;
          final long _tmpWordId;
          _tmpWordId = _stmt.getLong(_columnIndexOfWordId);
          final int _tmpProficiencyLevel;
          _tmpProficiencyLevel = (int) (_stmt.getLong(_columnIndexOfProficiencyLevel));
          final int _tmpCorrectAttempts;
          _tmpCorrectAttempts = (int) (_stmt.getLong(_columnIndexOfCorrectAttempts));
          final int _tmpTotalAttempts;
          _tmpTotalAttempts = (int) (_stmt.getLong(_columnIndexOfTotalAttempts));
          final float _tmpEaseFactor;
          _tmpEaseFactor = (float) (_stmt.getDouble(_columnIndexOfEaseFactor));
          final int _tmpIntervalDays;
          _tmpIntervalDays = (int) (_stmt.getLong(_columnIndexOfIntervalDays));
          final int _tmpRepetitions;
          _tmpRepetitions = (int) (_stmt.getLong(_columnIndexOfRepetitions));
          final Long _tmpLastAttemptAt;
          if (_stmt.isNull(_columnIndexOfLastAttemptAt)) {
            _tmpLastAttemptAt = null;
          } else {
            _tmpLastAttemptAt = _stmt.getLong(_columnIndexOfLastAttemptAt);
          }
          final Long _tmpLastCorrectAt;
          if (_stmt.isNull(_columnIndexOfLastCorrectAt)) {
            _tmpLastCorrectAt = null;
          } else {
            _tmpLastCorrectAt = _stmt.getLong(_columnIndexOfLastCorrectAt);
          }
          final Long _tmpNextReviewDue;
          if (_stmt.isNull(_columnIndexOfNextReviewDue)) {
            _tmpNextReviewDue = null;
          } else {
            _tmpNextReviewDue = _stmt.getLong(_columnIndexOfNextReviewDue);
          }
          final long _tmpTimeSpentMs;
          _tmpTimeSpentMs = _stmt.getLong(_columnIndexOfTimeSpentMs);
          final int _tmpLastConfidenceRating;
          _tmpLastConfidenceRating = (int) (_stmt.getLong(_columnIndexOfLastConfidenceRating));
          _item = new UserProgress(_tmpWordId,_tmpProficiencyLevel,_tmpCorrectAttempts,_tmpTotalAttempts,_tmpEaseFactor,_tmpIntervalDays,_tmpRepetitions,_tmpLastAttemptAt,_tmpLastCorrectAt,_tmpNextReviewDue,_tmpTimeSpentMs,_tmpLastConfidenceRating);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Flow<List<WordWithProgress>> getWordsWithProgressByProficiency() {
    final String _sql = "\n"
            + "        SELECT w.* FROM words w\n"
            + "        INNER JOIN user_progress up ON w.id = up.wordId\n"
            + "        ORDER BY up.proficiencyLevel ASC\n"
            + "    ";
    return FlowUtil.createFlow(__db, true, new String[] {"user_progress",
        "words"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfEnglishText = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "englishText");
        final int _columnIndexOfHindiText = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiText");
        final int _columnIndexOfEnglishPronunciation = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "englishPronunciation");
        final int _columnIndexOfHindiPronunciation = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiPronunciation");
        final int _columnIndexOfEnglishExample = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "englishExample");
        final int _columnIndexOfHindiExample = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiExample");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfPartOfSpeech = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "partOfSpeech");
        final int _columnIndexOfIsFavorite = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isFavorite");
        final int _columnIndexOfEnglishAudioPath = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "englishAudioPath");
        final int _columnIndexOfHindiAudioPath = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiAudioPath");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _columnIndexOfLastModifiedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastModifiedAt");
        final int _columnIndexOfLastReviewedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastReviewedAt");
        final int _columnIndexOfNextReviewDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nextReviewDate");
        final LongSparseArray<UserProgress> _collectionProgress = new LongSparseArray<UserProgress>();
        while (_stmt.step()) {
          final long _tmpKey;
          _tmpKey = _stmt.getLong(_columnIndexOfId);
          _collectionProgress.put(_tmpKey, null);
        }
        _stmt.reset();
        __fetchRelationshipuserProgressAscomBhashasetuAppDataModelUserProgress(_connection, _collectionProgress);
        final List<WordWithProgress> _result = new ArrayList<WordWithProgress>();
        while (_stmt.step()) {
          final WordWithProgress _item;
          final Word _tmpWord;
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final String _tmpEnglishText;
          if (_stmt.isNull(_columnIndexOfEnglishText)) {
            _tmpEnglishText = null;
          } else {
            _tmpEnglishText = _stmt.getText(_columnIndexOfEnglishText);
          }
          final String _tmpHindiText;
          if (_stmt.isNull(_columnIndexOfHindiText)) {
            _tmpHindiText = null;
          } else {
            _tmpHindiText = _stmt.getText(_columnIndexOfHindiText);
          }
          final String _tmpEnglishPronunciation;
          if (_stmt.isNull(_columnIndexOfEnglishPronunciation)) {
            _tmpEnglishPronunciation = null;
          } else {
            _tmpEnglishPronunciation = _stmt.getText(_columnIndexOfEnglishPronunciation);
          }
          final String _tmpHindiPronunciation;
          if (_stmt.isNull(_columnIndexOfHindiPronunciation)) {
            _tmpHindiPronunciation = null;
          } else {
            _tmpHindiPronunciation = _stmt.getText(_columnIndexOfHindiPronunciation);
          }
          final String _tmpEnglishExample;
          if (_stmt.isNull(_columnIndexOfEnglishExample)) {
            _tmpEnglishExample = null;
          } else {
            _tmpEnglishExample = _stmt.getText(_columnIndexOfEnglishExample);
          }
          final String _tmpHindiExample;
          if (_stmt.isNull(_columnIndexOfHindiExample)) {
            _tmpHindiExample = null;
          } else {
            _tmpHindiExample = _stmt.getText(_columnIndexOfHindiExample);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final String _tmpPartOfSpeech;
          if (_stmt.isNull(_columnIndexOfPartOfSpeech)) {
            _tmpPartOfSpeech = null;
          } else {
            _tmpPartOfSpeech = _stmt.getText(_columnIndexOfPartOfSpeech);
          }
          final boolean _tmpIsFavorite;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsFavorite));
          _tmpIsFavorite = _tmp != 0;
          final String _tmpEnglishAudioPath;
          if (_stmt.isNull(_columnIndexOfEnglishAudioPath)) {
            _tmpEnglishAudioPath = null;
          } else {
            _tmpEnglishAudioPath = _stmt.getText(_columnIndexOfEnglishAudioPath);
          }
          final String _tmpHindiAudioPath;
          if (_stmt.isNull(_columnIndexOfHindiAudioPath)) {
            _tmpHindiAudioPath = null;
          } else {
            _tmpHindiAudioPath = _stmt.getText(_columnIndexOfHindiAudioPath);
          }
          final long _tmpCreatedAt;
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt);
          final long _tmpLastModifiedAt;
          _tmpLastModifiedAt = _stmt.getLong(_columnIndexOfLastModifiedAt);
          final Long _tmpLastReviewedAt;
          if (_stmt.isNull(_columnIndexOfLastReviewedAt)) {
            _tmpLastReviewedAt = null;
          } else {
            _tmpLastReviewedAt = _stmt.getLong(_columnIndexOfLastReviewedAt);
          }
          final Long _tmpNextReviewDate;
          if (_stmt.isNull(_columnIndexOfNextReviewDate)) {
            _tmpNextReviewDate = null;
          } else {
            _tmpNextReviewDate = _stmt.getLong(_columnIndexOfNextReviewDate);
          }
          _tmpWord = new Word(_tmpId,_tmpEnglishText,_tmpHindiText,_tmpEnglishPronunciation,_tmpHindiPronunciation,_tmpEnglishExample,_tmpHindiExample,_tmpDifficulty,_tmpPartOfSpeech,_tmpIsFavorite,_tmpEnglishAudioPath,_tmpHindiAudioPath,_tmpCreatedAt,_tmpLastModifiedAt,_tmpLastReviewedAt,_tmpNextReviewDate);
          final UserProgress _tmpProgress;
          final long _tmpKey_1;
          _tmpKey_1 = _stmt.getLong(_columnIndexOfId);
          _tmpProgress = _collectionProgress.get(_tmpKey_1);
          _item = new WordWithProgress(_tmpWord,_tmpProgress);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Object getRecentlyStudiedWords(final int limit,
      final Continuation<? super List<WordWithProgress>> $completion) {
    final String _sql = "\n"
            + "        SELECT w.* FROM words w\n"
            + "        INNER JOIN user_progress up ON w.id = up.wordId\n"
            + "        WHERE up.proficiencyLevel < 100\n"
            + "        ORDER BY up.lastAttemptAt DESC\n"
            + "        LIMIT ?\n"
            + "    ";
    return DBUtil.performSuspending(__db, true, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, limit);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfEnglishText = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "englishText");
        final int _columnIndexOfHindiText = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiText");
        final int _columnIndexOfEnglishPronunciation = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "englishPronunciation");
        final int _columnIndexOfHindiPronunciation = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiPronunciation");
        final int _columnIndexOfEnglishExample = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "englishExample");
        final int _columnIndexOfHindiExample = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiExample");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfPartOfSpeech = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "partOfSpeech");
        final int _columnIndexOfIsFavorite = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isFavorite");
        final int _columnIndexOfEnglishAudioPath = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "englishAudioPath");
        final int _columnIndexOfHindiAudioPath = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiAudioPath");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _columnIndexOfLastModifiedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastModifiedAt");
        final int _columnIndexOfLastReviewedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastReviewedAt");
        final int _columnIndexOfNextReviewDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nextReviewDate");
        final LongSparseArray<UserProgress> _collectionProgress = new LongSparseArray<UserProgress>();
        while (_stmt.step()) {
          final long _tmpKey;
          _tmpKey = _stmt.getLong(_columnIndexOfId);
          _collectionProgress.put(_tmpKey, null);
        }
        _stmt.reset();
        __fetchRelationshipuserProgressAscomBhashasetuAppDataModelUserProgress(_connection, _collectionProgress);
        final List<WordWithProgress> _result = new ArrayList<WordWithProgress>();
        while (_stmt.step()) {
          final WordWithProgress _item;
          final Word _tmpWord;
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final String _tmpEnglishText;
          if (_stmt.isNull(_columnIndexOfEnglishText)) {
            _tmpEnglishText = null;
          } else {
            _tmpEnglishText = _stmt.getText(_columnIndexOfEnglishText);
          }
          final String _tmpHindiText;
          if (_stmt.isNull(_columnIndexOfHindiText)) {
            _tmpHindiText = null;
          } else {
            _tmpHindiText = _stmt.getText(_columnIndexOfHindiText);
          }
          final String _tmpEnglishPronunciation;
          if (_stmt.isNull(_columnIndexOfEnglishPronunciation)) {
            _tmpEnglishPronunciation = null;
          } else {
            _tmpEnglishPronunciation = _stmt.getText(_columnIndexOfEnglishPronunciation);
          }
          final String _tmpHindiPronunciation;
          if (_stmt.isNull(_columnIndexOfHindiPronunciation)) {
            _tmpHindiPronunciation = null;
          } else {
            _tmpHindiPronunciation = _stmt.getText(_columnIndexOfHindiPronunciation);
          }
          final String _tmpEnglishExample;
          if (_stmt.isNull(_columnIndexOfEnglishExample)) {
            _tmpEnglishExample = null;
          } else {
            _tmpEnglishExample = _stmt.getText(_columnIndexOfEnglishExample);
          }
          final String _tmpHindiExample;
          if (_stmt.isNull(_columnIndexOfHindiExample)) {
            _tmpHindiExample = null;
          } else {
            _tmpHindiExample = _stmt.getText(_columnIndexOfHindiExample);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final String _tmpPartOfSpeech;
          if (_stmt.isNull(_columnIndexOfPartOfSpeech)) {
            _tmpPartOfSpeech = null;
          } else {
            _tmpPartOfSpeech = _stmt.getText(_columnIndexOfPartOfSpeech);
          }
          final boolean _tmpIsFavorite;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsFavorite));
          _tmpIsFavorite = _tmp != 0;
          final String _tmpEnglishAudioPath;
          if (_stmt.isNull(_columnIndexOfEnglishAudioPath)) {
            _tmpEnglishAudioPath = null;
          } else {
            _tmpEnglishAudioPath = _stmt.getText(_columnIndexOfEnglishAudioPath);
          }
          final String _tmpHindiAudioPath;
          if (_stmt.isNull(_columnIndexOfHindiAudioPath)) {
            _tmpHindiAudioPath = null;
          } else {
            _tmpHindiAudioPath = _stmt.getText(_columnIndexOfHindiAudioPath);
          }
          final long _tmpCreatedAt;
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt);
          final long _tmpLastModifiedAt;
          _tmpLastModifiedAt = _stmt.getLong(_columnIndexOfLastModifiedAt);
          final Long _tmpLastReviewedAt;
          if (_stmt.isNull(_columnIndexOfLastReviewedAt)) {
            _tmpLastReviewedAt = null;
          } else {
            _tmpLastReviewedAt = _stmt.getLong(_columnIndexOfLastReviewedAt);
          }
          final Long _tmpNextReviewDate;
          if (_stmt.isNull(_columnIndexOfNextReviewDate)) {
            _tmpNextReviewDate = null;
          } else {
            _tmpNextReviewDate = _stmt.getLong(_columnIndexOfNextReviewDate);
          }
          _tmpWord = new Word(_tmpId,_tmpEnglishText,_tmpHindiText,_tmpEnglishPronunciation,_tmpHindiPronunciation,_tmpEnglishExample,_tmpHindiExample,_tmpDifficulty,_tmpPartOfSpeech,_tmpIsFavorite,_tmpEnglishAudioPath,_tmpHindiAudioPath,_tmpCreatedAt,_tmpLastModifiedAt,_tmpLastReviewedAt,_tmpNextReviewDate);
          final UserProgress _tmpProgress;
          final long _tmpKey_1;
          _tmpKey_1 = _stmt.getLong(_columnIndexOfId);
          _tmpProgress = _collectionProgress.get(_tmpKey_1);
          _item = new WordWithProgress(_tmpWord,_tmpProgress);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object getWordsForReview(final long currentTimeMs, final int limit,
      final Continuation<? super List<WordWithProgress>> $completion) {
    final String _sql = "\n"
            + "        SELECT w.* FROM words w\n"
            + "        INNER JOIN user_progress up ON w.id = up.wordId\n"
            + "        WHERE up.nextReviewDue <= ?\n"
            + "        ORDER BY up.nextReviewDue ASC\n"
            + "        LIMIT ?\n"
            + "    ";
    return DBUtil.performSuspending(__db, true, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, currentTimeMs);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, limit);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfEnglishText = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "englishText");
        final int _columnIndexOfHindiText = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiText");
        final int _columnIndexOfEnglishPronunciation = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "englishPronunciation");
        final int _columnIndexOfHindiPronunciation = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiPronunciation");
        final int _columnIndexOfEnglishExample = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "englishExample");
        final int _columnIndexOfHindiExample = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiExample");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfPartOfSpeech = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "partOfSpeech");
        final int _columnIndexOfIsFavorite = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isFavorite");
        final int _columnIndexOfEnglishAudioPath = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "englishAudioPath");
        final int _columnIndexOfHindiAudioPath = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiAudioPath");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _columnIndexOfLastModifiedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastModifiedAt");
        final int _columnIndexOfLastReviewedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastReviewedAt");
        final int _columnIndexOfNextReviewDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nextReviewDate");
        final LongSparseArray<UserProgress> _collectionProgress = new LongSparseArray<UserProgress>();
        while (_stmt.step()) {
          final long _tmpKey;
          _tmpKey = _stmt.getLong(_columnIndexOfId);
          _collectionProgress.put(_tmpKey, null);
        }
        _stmt.reset();
        __fetchRelationshipuserProgressAscomBhashasetuAppDataModelUserProgress(_connection, _collectionProgress);
        final List<WordWithProgress> _result = new ArrayList<WordWithProgress>();
        while (_stmt.step()) {
          final WordWithProgress _item;
          final Word _tmpWord;
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final String _tmpEnglishText;
          if (_stmt.isNull(_columnIndexOfEnglishText)) {
            _tmpEnglishText = null;
          } else {
            _tmpEnglishText = _stmt.getText(_columnIndexOfEnglishText);
          }
          final String _tmpHindiText;
          if (_stmt.isNull(_columnIndexOfHindiText)) {
            _tmpHindiText = null;
          } else {
            _tmpHindiText = _stmt.getText(_columnIndexOfHindiText);
          }
          final String _tmpEnglishPronunciation;
          if (_stmt.isNull(_columnIndexOfEnglishPronunciation)) {
            _tmpEnglishPronunciation = null;
          } else {
            _tmpEnglishPronunciation = _stmt.getText(_columnIndexOfEnglishPronunciation);
          }
          final String _tmpHindiPronunciation;
          if (_stmt.isNull(_columnIndexOfHindiPronunciation)) {
            _tmpHindiPronunciation = null;
          } else {
            _tmpHindiPronunciation = _stmt.getText(_columnIndexOfHindiPronunciation);
          }
          final String _tmpEnglishExample;
          if (_stmt.isNull(_columnIndexOfEnglishExample)) {
            _tmpEnglishExample = null;
          } else {
            _tmpEnglishExample = _stmt.getText(_columnIndexOfEnglishExample);
          }
          final String _tmpHindiExample;
          if (_stmt.isNull(_columnIndexOfHindiExample)) {
            _tmpHindiExample = null;
          } else {
            _tmpHindiExample = _stmt.getText(_columnIndexOfHindiExample);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final String _tmpPartOfSpeech;
          if (_stmt.isNull(_columnIndexOfPartOfSpeech)) {
            _tmpPartOfSpeech = null;
          } else {
            _tmpPartOfSpeech = _stmt.getText(_columnIndexOfPartOfSpeech);
          }
          final boolean _tmpIsFavorite;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsFavorite));
          _tmpIsFavorite = _tmp != 0;
          final String _tmpEnglishAudioPath;
          if (_stmt.isNull(_columnIndexOfEnglishAudioPath)) {
            _tmpEnglishAudioPath = null;
          } else {
            _tmpEnglishAudioPath = _stmt.getText(_columnIndexOfEnglishAudioPath);
          }
          final String _tmpHindiAudioPath;
          if (_stmt.isNull(_columnIndexOfHindiAudioPath)) {
            _tmpHindiAudioPath = null;
          } else {
            _tmpHindiAudioPath = _stmt.getText(_columnIndexOfHindiAudioPath);
          }
          final long _tmpCreatedAt;
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt);
          final long _tmpLastModifiedAt;
          _tmpLastModifiedAt = _stmt.getLong(_columnIndexOfLastModifiedAt);
          final Long _tmpLastReviewedAt;
          if (_stmt.isNull(_columnIndexOfLastReviewedAt)) {
            _tmpLastReviewedAt = null;
          } else {
            _tmpLastReviewedAt = _stmt.getLong(_columnIndexOfLastReviewedAt);
          }
          final Long _tmpNextReviewDate;
          if (_stmt.isNull(_columnIndexOfNextReviewDate)) {
            _tmpNextReviewDate = null;
          } else {
            _tmpNextReviewDate = _stmt.getLong(_columnIndexOfNextReviewDate);
          }
          _tmpWord = new Word(_tmpId,_tmpEnglishText,_tmpHindiText,_tmpEnglishPronunciation,_tmpHindiPronunciation,_tmpEnglishExample,_tmpHindiExample,_tmpDifficulty,_tmpPartOfSpeech,_tmpIsFavorite,_tmpEnglishAudioPath,_tmpHindiAudioPath,_tmpCreatedAt,_tmpLastModifiedAt,_tmpLastReviewedAt,_tmpNextReviewDate);
          final UserProgress _tmpProgress;
          final long _tmpKey_1;
          _tmpKey_1 = _stmt.getLong(_columnIndexOfId);
          _tmpProgress = _collectionProgress.get(_tmpKey_1);
          _item = new WordWithProgress(_tmpWord,_tmpProgress);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object getWordsForReviewByCategory(final long categoryId, final long currentTimeMs,
      final int limit, final Continuation<? super List<WordWithProgress>> $completion) {
    final String _sql = "\n"
            + "        SELECT w.* FROM words w\n"
            + "        INNER JOIN user_progress up ON w.id = up.wordId\n"
            + "        INNER JOIN word_category_cross_refs wc ON w.id = wc.wordId\n"
            + "        WHERE wc.categoryId = ? AND up.nextReviewDue <= ?\n"
            + "        ORDER BY up.nextReviewDue ASC\n"
            + "        LIMIT ?\n"
            + "    ";
    return DBUtil.performSuspending(__db, true, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, categoryId);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, currentTimeMs);
        _argIndex = 3;
        _stmt.bindLong(_argIndex, limit);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfEnglishText = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "englishText");
        final int _columnIndexOfHindiText = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiText");
        final int _columnIndexOfEnglishPronunciation = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "englishPronunciation");
        final int _columnIndexOfHindiPronunciation = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiPronunciation");
        final int _columnIndexOfEnglishExample = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "englishExample");
        final int _columnIndexOfHindiExample = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiExample");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfPartOfSpeech = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "partOfSpeech");
        final int _columnIndexOfIsFavorite = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isFavorite");
        final int _columnIndexOfEnglishAudioPath = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "englishAudioPath");
        final int _columnIndexOfHindiAudioPath = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiAudioPath");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _columnIndexOfLastModifiedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastModifiedAt");
        final int _columnIndexOfLastReviewedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastReviewedAt");
        final int _columnIndexOfNextReviewDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nextReviewDate");
        final LongSparseArray<UserProgress> _collectionProgress = new LongSparseArray<UserProgress>();
        while (_stmt.step()) {
          final long _tmpKey;
          _tmpKey = _stmt.getLong(_columnIndexOfId);
          _collectionProgress.put(_tmpKey, null);
        }
        _stmt.reset();
        __fetchRelationshipuserProgressAscomBhashasetuAppDataModelUserProgress(_connection, _collectionProgress);
        final List<WordWithProgress> _result = new ArrayList<WordWithProgress>();
        while (_stmt.step()) {
          final WordWithProgress _item;
          final Word _tmpWord;
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final String _tmpEnglishText;
          if (_stmt.isNull(_columnIndexOfEnglishText)) {
            _tmpEnglishText = null;
          } else {
            _tmpEnglishText = _stmt.getText(_columnIndexOfEnglishText);
          }
          final String _tmpHindiText;
          if (_stmt.isNull(_columnIndexOfHindiText)) {
            _tmpHindiText = null;
          } else {
            _tmpHindiText = _stmt.getText(_columnIndexOfHindiText);
          }
          final String _tmpEnglishPronunciation;
          if (_stmt.isNull(_columnIndexOfEnglishPronunciation)) {
            _tmpEnglishPronunciation = null;
          } else {
            _tmpEnglishPronunciation = _stmt.getText(_columnIndexOfEnglishPronunciation);
          }
          final String _tmpHindiPronunciation;
          if (_stmt.isNull(_columnIndexOfHindiPronunciation)) {
            _tmpHindiPronunciation = null;
          } else {
            _tmpHindiPronunciation = _stmt.getText(_columnIndexOfHindiPronunciation);
          }
          final String _tmpEnglishExample;
          if (_stmt.isNull(_columnIndexOfEnglishExample)) {
            _tmpEnglishExample = null;
          } else {
            _tmpEnglishExample = _stmt.getText(_columnIndexOfEnglishExample);
          }
          final String _tmpHindiExample;
          if (_stmt.isNull(_columnIndexOfHindiExample)) {
            _tmpHindiExample = null;
          } else {
            _tmpHindiExample = _stmt.getText(_columnIndexOfHindiExample);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final String _tmpPartOfSpeech;
          if (_stmt.isNull(_columnIndexOfPartOfSpeech)) {
            _tmpPartOfSpeech = null;
          } else {
            _tmpPartOfSpeech = _stmt.getText(_columnIndexOfPartOfSpeech);
          }
          final boolean _tmpIsFavorite;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsFavorite));
          _tmpIsFavorite = _tmp != 0;
          final String _tmpEnglishAudioPath;
          if (_stmt.isNull(_columnIndexOfEnglishAudioPath)) {
            _tmpEnglishAudioPath = null;
          } else {
            _tmpEnglishAudioPath = _stmt.getText(_columnIndexOfEnglishAudioPath);
          }
          final String _tmpHindiAudioPath;
          if (_stmt.isNull(_columnIndexOfHindiAudioPath)) {
            _tmpHindiAudioPath = null;
          } else {
            _tmpHindiAudioPath = _stmt.getText(_columnIndexOfHindiAudioPath);
          }
          final long _tmpCreatedAt;
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt);
          final long _tmpLastModifiedAt;
          _tmpLastModifiedAt = _stmt.getLong(_columnIndexOfLastModifiedAt);
          final Long _tmpLastReviewedAt;
          if (_stmt.isNull(_columnIndexOfLastReviewedAt)) {
            _tmpLastReviewedAt = null;
          } else {
            _tmpLastReviewedAt = _stmt.getLong(_columnIndexOfLastReviewedAt);
          }
          final Long _tmpNextReviewDate;
          if (_stmt.isNull(_columnIndexOfNextReviewDate)) {
            _tmpNextReviewDate = null;
          } else {
            _tmpNextReviewDate = _stmt.getLong(_columnIndexOfNextReviewDate);
          }
          _tmpWord = new Word(_tmpId,_tmpEnglishText,_tmpHindiText,_tmpEnglishPronunciation,_tmpHindiPronunciation,_tmpEnglishExample,_tmpHindiExample,_tmpDifficulty,_tmpPartOfSpeech,_tmpIsFavorite,_tmpEnglishAudioPath,_tmpHindiAudioPath,_tmpCreatedAt,_tmpLastModifiedAt,_tmpLastReviewedAt,_tmpNextReviewDate);
          final UserProgress _tmpProgress;
          final long _tmpKey_1;
          _tmpKey_1 = _stmt.getLong(_columnIndexOfId);
          _tmpProgress = _collectionProgress.get(_tmpKey_1);
          _item = new WordWithProgress(_tmpWord,_tmpProgress);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Flow<Integer> getTotalProgressCount() {
    final String _sql = "SELECT COUNT(*) FROM user_progress";
    return FlowUtil.createFlow(__db, false, new String[] {"user_progress"}, (_connection) -> {
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
  public Flow<Integer> getMasteredWordCount() {
    final String _sql = "SELECT COUNT(*) FROM user_progress WHERE proficiencyLevel >= 85";
    return FlowUtil.createFlow(__db, false, new String[] {"user_progress"}, (_connection) -> {
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
  public Flow<Integer> getLearningWordCount() {
    final String _sql = "SELECT COUNT(*) FROM user_progress WHERE proficiencyLevel > 0 AND proficiencyLevel < 85";
    return FlowUtil.createFlow(__db, false, new String[] {"user_progress"}, (_connection) -> {
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
  public Flow<Integer> getStartedWordCount() {
    final String _sql = "SELECT COUNT(*) FROM user_progress WHERE totalAttempts > 0";
    return FlowUtil.createFlow(__db, false, new String[] {"user_progress"}, (_connection) -> {
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
  public Flow<Long> getTotalStudyTime() {
    final String _sql = "SELECT SUM(timeSpentMs) FROM user_progress";
    return FlowUtil.createFlow(__db, false, new String[] {"user_progress"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final Long _result;
        if (_stmt.step()) {
          final Long _tmp;
          if (_stmt.isNull(0)) {
            _tmp = null;
          } else {
            _tmp = _stmt.getLong(0);
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
  public Flow<Float> getAverageAccuracy() {
    final String _sql = "\n"
            + "        SELECT AVG(CAST(correctAttempts AS FLOAT) / CAST(totalAttempts AS FLOAT) * 100)\n"
            + "        FROM user_progress\n"
            + "        WHERE totalAttempts > 0\n"
            + "    ";
    return FlowUtil.createFlow(__db, false, new String[] {"user_progress"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
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
  public Flow<Integer> getMasteredWordCountByCategory(final long categoryId,
      final int targetProficiency) {
    final String _sql = "\n"
            + "        SELECT COUNT(*) FROM user_progress\n"
            + "        WHERE proficiencyLevel >= ?\n"
            + "        AND wordId IN (\n"
            + "            SELECT wordId FROM word_category_cross_refs\n"
            + "            WHERE categoryId = ?\n"
            + "        )\n"
            + "    ";
    return FlowUtil.createFlow(__db, false, new String[] {"user_progress",
        "word_category_cross_refs"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, targetProficiency);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, categoryId);
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
  public Flow<Integer> getTotalWordCountByCategory(final long categoryId) {
    final String _sql = "\n"
            + "        SELECT COUNT(*) FROM word_category_cross_refs\n"
            + "        WHERE categoryId = ?\n"
            + "    ";
    return FlowUtil.createFlow(__db, false, new String[] {"word_category_cross_refs"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, categoryId);
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
  public Object deleteProgressForWord(final long wordId,
      final Continuation<? super Unit> $completion) {
    final String _sql = "DELETE FROM user_progress WHERE wordId = ?";
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, wordId);
        _stmt.step();
        return Unit.INSTANCE;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object updateProgressAfterReview(final long wordId, final boolean isCorrect,
      final int proficiencyLevel, final int confidenceRating, final float newEaseFactor,
      final int newIntervalDays, final int newRepetitions, final long attemptTimeMs,
      final long nextReviewDueMs, final long timeSpentMs,
      final Continuation<? super Unit> $completion) {
    final String _sql = "\n"
            + "        UPDATE user_progress\n"
            + "        SET \n"
            + "            proficiencyLevel = ?,\n"
            + "            correctAttempts = correctAttempts + CASE WHEN ? THEN 1 ELSE 0 END,\n"
            + "            totalAttempts = totalAttempts + 1,\n"
            + "            lastAttemptAt = ?,\n"
            + "            lastCorrectAt = CASE WHEN ? THEN ? ELSE lastCorrectAt END,\n"
            + "            lastConfidenceRating = ?,\n"
            + "            easeFactor = ?,\n"
            + "            intervalDays = ?,\n"
            + "            repetitions = ?,\n"
            + "            nextReviewDue = ?,\n"
            + "            timeSpentMs = timeSpentMs + ?\n"
            + "        WHERE wordId = ?\n"
            + "    ";
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, proficiencyLevel);
        _argIndex = 2;
        final int _tmp = isCorrect ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 3;
        _stmt.bindLong(_argIndex, attemptTimeMs);
        _argIndex = 4;
        final int _tmp_1 = isCorrect ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp_1);
        _argIndex = 5;
        _stmt.bindLong(_argIndex, attemptTimeMs);
        _argIndex = 6;
        _stmt.bindLong(_argIndex, confidenceRating);
        _argIndex = 7;
        _stmt.bindDouble(_argIndex, newEaseFactor);
        _argIndex = 8;
        _stmt.bindLong(_argIndex, newIntervalDays);
        _argIndex = 9;
        _stmt.bindLong(_argIndex, newRepetitions);
        _argIndex = 10;
        _stmt.bindLong(_argIndex, nextReviewDueMs);
        _argIndex = 11;
        _stmt.bindLong(_argIndex, timeSpentMs);
        _argIndex = 12;
        _stmt.bindLong(_argIndex, wordId);
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

  private void __fetchRelationshipuserProgressAscomBhashasetuAppDataModelUserProgress(
      @NonNull final SQLiteConnection _connection,
      @NonNull final LongSparseArray<UserProgress> _map) {
    if (_map.isEmpty()) {
      return;
    }
    if (_map.size() > 999) {
      RelationUtil.recursiveFetchLongSparseArray(_map, false, (_tmpMap) -> {
        __fetchRelationshipuserProgressAscomBhashasetuAppDataModelUserProgress(_connection, _tmpMap);
        return Unit.INSTANCE;
      });
      return;
    }
    final StringBuilder _stringBuilder = new StringBuilder();
    _stringBuilder.append("SELECT `wordId`,`proficiencyLevel`,`correctAttempts`,`totalAttempts`,`easeFactor`,`intervalDays`,`repetitions`,`lastAttemptAt`,`lastCorrectAt`,`nextReviewDue`,`timeSpentMs`,`lastConfidenceRating` FROM `user_progress` WHERE `wordId` IN (");
    final int _inputSize = _map.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final SQLiteStatement _stmt = _connection.prepare(_sql);
    int _argIndex = 1;
    for (int i = 0; i < _map.size(); i++) {
      final long _item = _map.keyAt(i);
      _stmt.bindLong(_argIndex, _item);
      _argIndex++;
    }
    try {
      final int _itemKeyIndex = SQLiteStatementUtil.getColumnIndex(_stmt, "wordId");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _columnIndexOfWordId = 0;
      final int _columnIndexOfProficiencyLevel = 1;
      final int _columnIndexOfCorrectAttempts = 2;
      final int _columnIndexOfTotalAttempts = 3;
      final int _columnIndexOfEaseFactor = 4;
      final int _columnIndexOfIntervalDays = 5;
      final int _columnIndexOfRepetitions = 6;
      final int _columnIndexOfLastAttemptAt = 7;
      final int _columnIndexOfLastCorrectAt = 8;
      final int _columnIndexOfNextReviewDue = 9;
      final int _columnIndexOfTimeSpentMs = 10;
      final int _columnIndexOfLastConfidenceRating = 11;
      while (_stmt.step()) {
        final long _tmpKey;
        _tmpKey = _stmt.getLong(_itemKeyIndex);
        if (_map.containsKey(_tmpKey)) {
          final UserProgress _item_1;
          final long _tmpWordId;
          _tmpWordId = _stmt.getLong(_columnIndexOfWordId);
          final int _tmpProficiencyLevel;
          _tmpProficiencyLevel = (int) (_stmt.getLong(_columnIndexOfProficiencyLevel));
          final int _tmpCorrectAttempts;
          _tmpCorrectAttempts = (int) (_stmt.getLong(_columnIndexOfCorrectAttempts));
          final int _tmpTotalAttempts;
          _tmpTotalAttempts = (int) (_stmt.getLong(_columnIndexOfTotalAttempts));
          final float _tmpEaseFactor;
          _tmpEaseFactor = (float) (_stmt.getDouble(_columnIndexOfEaseFactor));
          final int _tmpIntervalDays;
          _tmpIntervalDays = (int) (_stmt.getLong(_columnIndexOfIntervalDays));
          final int _tmpRepetitions;
          _tmpRepetitions = (int) (_stmt.getLong(_columnIndexOfRepetitions));
          final Long _tmpLastAttemptAt;
          if (_stmt.isNull(_columnIndexOfLastAttemptAt)) {
            _tmpLastAttemptAt = null;
          } else {
            _tmpLastAttemptAt = _stmt.getLong(_columnIndexOfLastAttemptAt);
          }
          final Long _tmpLastCorrectAt;
          if (_stmt.isNull(_columnIndexOfLastCorrectAt)) {
            _tmpLastCorrectAt = null;
          } else {
            _tmpLastCorrectAt = _stmt.getLong(_columnIndexOfLastCorrectAt);
          }
          final Long _tmpNextReviewDue;
          if (_stmt.isNull(_columnIndexOfNextReviewDue)) {
            _tmpNextReviewDue = null;
          } else {
            _tmpNextReviewDue = _stmt.getLong(_columnIndexOfNextReviewDue);
          }
          final long _tmpTimeSpentMs;
          _tmpTimeSpentMs = _stmt.getLong(_columnIndexOfTimeSpentMs);
          final int _tmpLastConfidenceRating;
          _tmpLastConfidenceRating = (int) (_stmt.getLong(_columnIndexOfLastConfidenceRating));
          _item_1 = new UserProgress(_tmpWordId,_tmpProficiencyLevel,_tmpCorrectAttempts,_tmpTotalAttempts,_tmpEaseFactor,_tmpIntervalDays,_tmpRepetitions,_tmpLastAttemptAt,_tmpLastCorrectAt,_tmpNextReviewDue,_tmpTimeSpentMs,_tmpLastConfidenceRating);
          _map.put(_tmpKey, _item_1);
        }
      }
    } finally {
      _stmt.close();
    }
  }
}
