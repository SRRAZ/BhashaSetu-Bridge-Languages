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
import com.bhashasetu.app.data.model.Category;
import com.bhashasetu.app.data.model.UserProgress;
import com.bhashasetu.app.data.model.Word;
import com.bhashasetu.app.data.relation.WordWithCategories;
import com.bhashasetu.app.data.relation.WordWithProgress;
import java.lang.Boolean;
import java.lang.Class;
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
public final class WordDao_Impl implements WordDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<Word> __insertAdapterOfWord;

  private final EntityDeleteOrUpdateAdapter<Word> __deleteAdapterOfWord;

  private final EntityDeleteOrUpdateAdapter<Word> __updateAdapterOfWord;

  public WordDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfWord = new EntityInsertAdapter<Word>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `words` (`id`,`englishText`,`hindiText`,`englishPronunciation`,`hindiPronunciation`,`englishExample`,`hindiExample`,`difficulty`,`partOfSpeech`,`isFavorite`,`englishAudioPath`,`hindiAudioPath`,`createdAt`,`lastModifiedAt`,`lastReviewedAt`,`nextReviewDate`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, @NonNull final Word entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getEnglishText() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getEnglishText());
        }
        if (entity.getHindiText() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getHindiText());
        }
        if (entity.getEnglishPronunciation() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getEnglishPronunciation());
        }
        if (entity.getHindiPronunciation() == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, entity.getHindiPronunciation());
        }
        if (entity.getEnglishExample() == null) {
          statement.bindNull(6);
        } else {
          statement.bindText(6, entity.getEnglishExample());
        }
        if (entity.getHindiExample() == null) {
          statement.bindNull(7);
        } else {
          statement.bindText(7, entity.getHindiExample());
        }
        statement.bindLong(8, entity.getDifficulty());
        if (entity.getPartOfSpeech() == null) {
          statement.bindNull(9);
        } else {
          statement.bindText(9, entity.getPartOfSpeech());
        }
        final int _tmp = entity.isFavorite() ? 1 : 0;
        statement.bindLong(10, _tmp);
        if (entity.getEnglishAudioPath() == null) {
          statement.bindNull(11);
        } else {
          statement.bindText(11, entity.getEnglishAudioPath());
        }
        if (entity.getHindiAudioPath() == null) {
          statement.bindNull(12);
        } else {
          statement.bindText(12, entity.getHindiAudioPath());
        }
        statement.bindLong(13, entity.getCreatedAt());
        statement.bindLong(14, entity.getLastModifiedAt());
        if (entity.getLastReviewedAt() == null) {
          statement.bindNull(15);
        } else {
          statement.bindLong(15, entity.getLastReviewedAt());
        }
        if (entity.getNextReviewDate() == null) {
          statement.bindNull(16);
        } else {
          statement.bindLong(16, entity.getNextReviewDate());
        }
      }
    };
    this.__deleteAdapterOfWord = new EntityDeleteOrUpdateAdapter<Word>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `words` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, @NonNull final Word entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfWord = new EntityDeleteOrUpdateAdapter<Word>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `words` SET `id` = ?,`englishText` = ?,`hindiText` = ?,`englishPronunciation` = ?,`hindiPronunciation` = ?,`englishExample` = ?,`hindiExample` = ?,`difficulty` = ?,`partOfSpeech` = ?,`isFavorite` = ?,`englishAudioPath` = ?,`hindiAudioPath` = ?,`createdAt` = ?,`lastModifiedAt` = ?,`lastReviewedAt` = ?,`nextReviewDate` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, @NonNull final Word entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getEnglishText() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getEnglishText());
        }
        if (entity.getHindiText() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getHindiText());
        }
        if (entity.getEnglishPronunciation() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getEnglishPronunciation());
        }
        if (entity.getHindiPronunciation() == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, entity.getHindiPronunciation());
        }
        if (entity.getEnglishExample() == null) {
          statement.bindNull(6);
        } else {
          statement.bindText(6, entity.getEnglishExample());
        }
        if (entity.getHindiExample() == null) {
          statement.bindNull(7);
        } else {
          statement.bindText(7, entity.getHindiExample());
        }
        statement.bindLong(8, entity.getDifficulty());
        if (entity.getPartOfSpeech() == null) {
          statement.bindNull(9);
        } else {
          statement.bindText(9, entity.getPartOfSpeech());
        }
        final int _tmp = entity.isFavorite() ? 1 : 0;
        statement.bindLong(10, _tmp);
        if (entity.getEnglishAudioPath() == null) {
          statement.bindNull(11);
        } else {
          statement.bindText(11, entity.getEnglishAudioPath());
        }
        if (entity.getHindiAudioPath() == null) {
          statement.bindNull(12);
        } else {
          statement.bindText(12, entity.getHindiAudioPath());
        }
        statement.bindLong(13, entity.getCreatedAt());
        statement.bindLong(14, entity.getLastModifiedAt());
        if (entity.getLastReviewedAt() == null) {
          statement.bindNull(15);
        } else {
          statement.bindLong(15, entity.getLastReviewedAt());
        }
        if (entity.getNextReviewDate() == null) {
          statement.bindNull(16);
        } else {
          statement.bindLong(16, entity.getNextReviewDate());
        }
        statement.bindLong(17, entity.getId());
      }
    };
  }

  @Override
  public Object insert(final Word word, final Continuation<? super Long> $completion) {
    if (word == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      return __insertAdapterOfWord.insertAndReturnId(_connection, word);
    }, $completion);
  }

  @Override
  public Object insertAll(final List<Word> words,
      final Continuation<? super List<Long>> $completion) {
    if (words == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      return __insertAdapterOfWord.insertAndReturnIdsList(_connection, words);
    }, $completion);
  }

  @Override
  public Object delete(final Word word, final Continuation<? super Unit> $completion) {
    if (word == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __deleteAdapterOfWord.handle(_connection, word);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Object update(final Word word, final Continuation<? super Unit> $completion) {
    if (word == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __updateAdapterOfWord.handle(_connection, word);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Object getWordById(final long wordId, final Continuation<? super Word> $completion) {
    final String _sql = "SELECT * FROM words WHERE id = ?";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, wordId);
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
        final Word _result;
        if (_stmt.step()) {
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
          _result = new Word(_tmpId,_tmpEnglishText,_tmpHindiText,_tmpEnglishPronunciation,_tmpHindiPronunciation,_tmpEnglishExample,_tmpHindiExample,_tmpDifficulty,_tmpPartOfSpeech,_tmpIsFavorite,_tmpEnglishAudioPath,_tmpHindiAudioPath,_tmpCreatedAt,_tmpLastModifiedAt,_tmpLastReviewedAt,_tmpNextReviewDate);
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
  public LiveData<Word> getWordByIdLiveData(final long wordId) {
    final String _sql = "SELECT * FROM words WHERE id = ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"words"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, wordId);
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
        final Word _result;
        if (_stmt.step()) {
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
          _result = new Word(_tmpId,_tmpEnglishText,_tmpHindiText,_tmpEnglishPronunciation,_tmpHindiPronunciation,_tmpEnglishExample,_tmpHindiExample,_tmpDifficulty,_tmpPartOfSpeech,_tmpIsFavorite,_tmpEnglishAudioPath,_tmpHindiAudioPath,_tmpCreatedAt,_tmpLastModifiedAt,_tmpLastReviewedAt,_tmpNextReviewDate);
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
  public Flow<Word> getWordByIdFlow(final long wordId) {
    final String _sql = "SELECT * FROM words WHERE id = ?";
    return FlowUtil.createFlow(__db, false, new String[] {"words"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, wordId);
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
        final Word _result;
        if (_stmt.step()) {
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
          _result = new Word(_tmpId,_tmpEnglishText,_tmpHindiText,_tmpEnglishPronunciation,_tmpHindiPronunciation,_tmpEnglishExample,_tmpHindiExample,_tmpDifficulty,_tmpPartOfSpeech,_tmpIsFavorite,_tmpEnglishAudioPath,_tmpHindiAudioPath,_tmpCreatedAt,_tmpLastModifiedAt,_tmpLastReviewedAt,_tmpNextReviewDate);
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
  public Flow<List<Word>> getAllWords() {
    final String _sql = "SELECT * FROM words ORDER BY englishText ASC";
    return FlowUtil.createFlow(__db, false, new String[] {"words"}, (_connection) -> {
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
        final List<Word> _result = new ArrayList<Word>();
        while (_stmt.step()) {
          final Word _item;
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
          _item = new Word(_tmpId,_tmpEnglishText,_tmpHindiText,_tmpEnglishPronunciation,_tmpHindiPronunciation,_tmpEnglishExample,_tmpHindiExample,_tmpDifficulty,_tmpPartOfSpeech,_tmpIsFavorite,_tmpEnglishAudioPath,_tmpHindiAudioPath,_tmpCreatedAt,_tmpLastModifiedAt,_tmpLastReviewedAt,_tmpNextReviewDate);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Word>> getAllWordsLiveData() {
    final String _sql = "SELECT * FROM words ORDER BY englishText ASC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"words"}, false, (_connection) -> {
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
        final List<Word> _result = new ArrayList<Word>();
        while (_stmt.step()) {
          final Word _item;
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
          _item = new Word(_tmpId,_tmpEnglishText,_tmpHindiText,_tmpEnglishPronunciation,_tmpHindiPronunciation,_tmpEnglishExample,_tmpHindiExample,_tmpDifficulty,_tmpPartOfSpeech,_tmpIsFavorite,_tmpEnglishAudioPath,_tmpHindiAudioPath,_tmpCreatedAt,_tmpLastModifiedAt,_tmpLastReviewedAt,_tmpNextReviewDate);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Flow<List<Word>> getFavoriteWords() {
    final String _sql = "SELECT * FROM words WHERE isFavorite = 1 ORDER BY englishText ASC";
    return FlowUtil.createFlow(__db, false, new String[] {"words"}, (_connection) -> {
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
        final List<Word> _result = new ArrayList<Word>();
        while (_stmt.step()) {
          final Word _item;
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
          _item = new Word(_tmpId,_tmpEnglishText,_tmpHindiText,_tmpEnglishPronunciation,_tmpHindiPronunciation,_tmpEnglishExample,_tmpHindiExample,_tmpDifficulty,_tmpPartOfSpeech,_tmpIsFavorite,_tmpEnglishAudioPath,_tmpHindiAudioPath,_tmpCreatedAt,_tmpLastModifiedAt,_tmpLastReviewedAt,_tmpNextReviewDate);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Flow<List<Word>> searchWords(final String query) {
    final String _sql = "SELECT * FROM words WHERE englishText LIKE '%' || ? || '%' OR hindiText LIKE '%' || ? || '%' ORDER BY englishText ASC";
    return FlowUtil.createFlow(__db, false, new String[] {"words"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (query == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, query);
        }
        _argIndex = 2;
        if (query == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, query);
        }
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
        final List<Word> _result = new ArrayList<Word>();
        while (_stmt.step()) {
          final Word _item;
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
          _item = new Word(_tmpId,_tmpEnglishText,_tmpHindiText,_tmpEnglishPronunciation,_tmpHindiPronunciation,_tmpEnglishExample,_tmpHindiExample,_tmpDifficulty,_tmpPartOfSpeech,_tmpIsFavorite,_tmpEnglishAudioPath,_tmpHindiAudioPath,_tmpCreatedAt,_tmpLastModifiedAt,_tmpLastReviewedAt,_tmpNextReviewDate);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Flow<List<Word>> getWordsByDifficulty(final int level) {
    final String _sql = "SELECT * FROM words WHERE difficulty = ? ORDER BY englishText ASC";
    return FlowUtil.createFlow(__db, false, new String[] {"words"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, level);
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
        final List<Word> _result = new ArrayList<Word>();
        while (_stmt.step()) {
          final Word _item;
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
          _item = new Word(_tmpId,_tmpEnglishText,_tmpHindiText,_tmpEnglishPronunciation,_tmpHindiPronunciation,_tmpEnglishExample,_tmpHindiExample,_tmpDifficulty,_tmpPartOfSpeech,_tmpIsFavorite,_tmpEnglishAudioPath,_tmpHindiAudioPath,_tmpCreatedAt,_tmpLastModifiedAt,_tmpLastReviewedAt,_tmpNextReviewDate);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Object getRandomWords(final int limit,
      final Continuation<? super List<Word>> $completion) {
    final String _sql = "SELECT * FROM words ORDER BY RANDOM() LIMIT ?";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
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
        final List<Word> _result = new ArrayList<Word>();
        while (_stmt.step()) {
          final Word _item;
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
          _item = new Word(_tmpId,_tmpEnglishText,_tmpHindiText,_tmpEnglishPronunciation,_tmpHindiPronunciation,_tmpEnglishExample,_tmpHindiExample,_tmpDifficulty,_tmpPartOfSpeech,_tmpIsFavorite,_tmpEnglishAudioPath,_tmpHindiAudioPath,_tmpCreatedAt,_tmpLastModifiedAt,_tmpLastReviewedAt,_tmpNextReviewDate);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object getWordWithCategories(final long wordId,
      final Continuation<? super WordWithCategories> $completion) {
    final String _sql = "SELECT * FROM words WHERE id = ?";
    return DBUtil.performSuspending(__db, true, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, wordId);
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
        final LongSparseArray<ArrayList<Category>> _collectionCategories = new LongSparseArray<ArrayList<Category>>();
        while (_stmt.step()) {
          final long _tmpKey;
          _tmpKey = _stmt.getLong(_columnIndexOfId);
          if (!_collectionCategories.containsKey(_tmpKey)) {
            _collectionCategories.put(_tmpKey, new ArrayList<Category>());
          }
        }
        _stmt.reset();
        __fetchRelationshipcategoriesAscomBhashasetuAppDataModelCategory(_connection, _collectionCategories);
        final WordWithCategories _result;
        if (_stmt.step()) {
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
          final ArrayList<Category> _tmpCategoriesCollection;
          final long _tmpKey_1;
          _tmpKey_1 = _stmt.getLong(_columnIndexOfId);
          _tmpCategoriesCollection = _collectionCategories.get(_tmpKey_1);
          _result = new WordWithCategories(_tmpWord,_tmpCategoriesCollection);
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
  public Flow<List<WordWithCategories>> getAllWordsWithCategories() {
    final String _sql = "SELECT * FROM words";
    return FlowUtil.createFlow(__db, true, new String[] {"word_category_cross_refs", "categories",
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
        final LongSparseArray<ArrayList<Category>> _collectionCategories = new LongSparseArray<ArrayList<Category>>();
        while (_stmt.step()) {
          final long _tmpKey;
          _tmpKey = _stmt.getLong(_columnIndexOfId);
          if (!_collectionCategories.containsKey(_tmpKey)) {
            _collectionCategories.put(_tmpKey, new ArrayList<Category>());
          }
        }
        _stmt.reset();
        __fetchRelationshipcategoriesAscomBhashasetuAppDataModelCategory(_connection, _collectionCategories);
        final List<WordWithCategories> _result = new ArrayList<WordWithCategories>();
        while (_stmt.step()) {
          final WordWithCategories _item;
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
          final ArrayList<Category> _tmpCategoriesCollection;
          final long _tmpKey_1;
          _tmpKey_1 = _stmt.getLong(_columnIndexOfId);
          _tmpCategoriesCollection = _collectionCategories.get(_tmpKey_1);
          _item = new WordWithCategories(_tmpWord,_tmpCategoriesCollection);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Object getWordWithProgress(final long wordId,
      final Continuation<? super WordWithProgress> $completion) {
    final String _sql = "SELECT * FROM words WHERE id = ?";
    return DBUtil.performSuspending(__db, true, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, wordId);
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
        final WordWithProgress _result;
        if (_stmt.step()) {
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
          _result = new WordWithProgress(_tmpWord,_tmpProgress);
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
  public Flow<List<Word>> getWordsByCategory(final long categoryId) {
    final String _sql = "SELECT w.* FROM words w INNER JOIN word_category_cross_refs wc ON w.id = wc.wordId WHERE wc.categoryId = ? ORDER BY w.englishText ASC";
    return FlowUtil.createFlow(__db, true, new String[] {"words",
        "word_category_cross_refs"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, categoryId);
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
        final List<Word> _result = new ArrayList<Word>();
        while (_stmt.step()) {
          final Word _item;
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
          _item = new Word(_tmpId,_tmpEnglishText,_tmpHindiText,_tmpEnglishPronunciation,_tmpHindiPronunciation,_tmpEnglishExample,_tmpHindiExample,_tmpDifficulty,_tmpPartOfSpeech,_tmpIsFavorite,_tmpEnglishAudioPath,_tmpHindiAudioPath,_tmpCreatedAt,_tmpLastModifiedAt,_tmpLastReviewedAt,_tmpNextReviewDate);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Word>> getWordsByCategoryLiveData(final long categoryId) {
    final String _sql = "SELECT w.* FROM words w INNER JOIN word_category_cross_refs wc ON w.id = wc.wordId WHERE wc.categoryId = ? ORDER BY w.englishText ASC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"words",
        "word_category_cross_refs"}, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, categoryId);
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
        final List<Word> _result = new ArrayList<Word>();
        while (_stmt.step()) {
          final Word _item;
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
          _item = new Word(_tmpId,_tmpEnglishText,_tmpHindiText,_tmpEnglishPronunciation,_tmpHindiPronunciation,_tmpEnglishExample,_tmpHindiExample,_tmpDifficulty,_tmpPartOfSpeech,_tmpIsFavorite,_tmpEnglishAudioPath,_tmpHindiAudioPath,_tmpCreatedAt,_tmpLastModifiedAt,_tmpLastReviewedAt,_tmpNextReviewDate);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Object getWordsForReview(final long currentTimeMillis, final int limit,
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
        _stmt.bindLong(_argIndex, currentTimeMillis);
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
  public Object getNewWordsForLearning(final int limit,
      final Continuation<? super List<Word>> $completion) {
    final String _sql = "\n"
            + "        SELECT w.* FROM words w\n"
            + "        LEFT JOIN user_progress up ON w.id = up.wordId\n"
            + "        WHERE up.wordId IS NULL\n"
            + "        ORDER BY RANDOM()\n"
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
        final List<Word> _result = new ArrayList<Word>();
        while (_stmt.step()) {
          final Word _item;
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
          _item = new Word(_tmpId,_tmpEnglishText,_tmpHindiText,_tmpEnglishPronunciation,_tmpHindiPronunciation,_tmpEnglishExample,_tmpHindiExample,_tmpDifficulty,_tmpPartOfSpeech,_tmpIsFavorite,_tmpEnglishAudioPath,_tmpHindiAudioPath,_tmpCreatedAt,_tmpLastModifiedAt,_tmpLastReviewedAt,_tmpNextReviewDate);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Flow<Integer> getTotalWordCount() {
    final String _sql = "SELECT COUNT(*) FROM words";
    return FlowUtil.createFlow(__db, false, new String[] {"words"}, (_connection) -> {
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
  public Flow<Integer> getFavoriteWordCount() {
    final String _sql = "SELECT COUNT(*) FROM words WHERE isFavorite = 1";
    return FlowUtil.createFlow(__db, false, new String[] {"words"}, (_connection) -> {
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
    final String _sql = "\n"
            + "        SELECT COUNT(*) FROM words w\n"
            + "        INNER JOIN user_progress up ON w.id = up.wordId\n"
            + "        WHERE up.proficiencyLevel >= 85\n"
            + "    ";
    return FlowUtil.createFlow(__db, false, new String[] {"words",
        "user_progress"}, (_connection) -> {
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
    final String _sql = "\n"
            + "        SELECT COUNT(*) FROM words w\n"
            + "        INNER JOIN user_progress up ON w.id = up.wordId\n"
            + "        WHERE up.totalAttempts > 0\n"
            + "    ";
    return FlowUtil.createFlow(__db, false, new String[] {"words",
        "user_progress"}, (_connection) -> {
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
  public Object wordExistsByEnglishText(final String englishText,
      final Continuation<? super Boolean> $completion) {
    final String _sql = "SELECT EXISTS(SELECT 1 FROM words WHERE englishText = ? LIMIT 1)";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (englishText == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, englishText);
        }
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
    }, $completion);
  }

  @Override
  public Flow<Integer> getWordCountByCategory(final long categoryId) {
    final String _sql = "SELECT COUNT(*) FROM words w INNER JOIN word_category_cross_refs wc ON w.id = wc.wordId WHERE wc.categoryId = ?";
    return FlowUtil.createFlow(__db, false, new String[] {"words",
        "word_category_cross_refs"}, (_connection) -> {
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private void __fetchRelationshipcategoriesAscomBhashasetuAppDataModelCategory(
      @NonNull final SQLiteConnection _connection,
      @NonNull final LongSparseArray<ArrayList<Category>> _map) {
    if (_map.isEmpty()) {
      return;
    }
    if (_map.size() > 999) {
      RelationUtil.recursiveFetchLongSparseArray(_map, true, (_tmpMap) -> {
        __fetchRelationshipcategoriesAscomBhashasetuAppDataModelCategory(_connection, _tmpMap);
        return Unit.INSTANCE;
      });
      return;
    }
    final StringBuilder _stringBuilder = new StringBuilder();
    _stringBuilder.append("SELECT `categories`.`id` AS `id`,`categories`.`nameEnglish` AS `nameEnglish`,`categories`.`nameHindi` AS `nameHindi`,`categories`.`descriptionEnglish` AS `descriptionEnglish`,`categories`.`descriptionHindi` AS `descriptionHindi`,`categories`.`iconResId` AS `iconResId`,`categories`.`colorHex` AS `colorHex`,`categories`.`orderIndex` AS `orderIndex`,`categories`.`isDefault` AS `isDefault`,`categories`.`createdAt` AS `createdAt`,_junction.`wordId` FROM `word_category_cross_refs` AS _junction INNER JOIN `categories` ON (_junction.`categoryId` = `categories`.`id`) WHERE _junction.`wordId` IN (");
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
      // _junction.wordId;
      final int _itemKeyIndex = 10;
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _columnIndexOfId = 0;
      final int _columnIndexOfNameEnglish = 1;
      final int _columnIndexOfNameHindi = 2;
      final int _columnIndexOfDescriptionEnglish = 3;
      final int _columnIndexOfDescriptionHindi = 4;
      final int _columnIndexOfIconResId = 5;
      final int _columnIndexOfColorHex = 6;
      final int _columnIndexOfOrderIndex = 7;
      final int _columnIndexOfIsDefault = 8;
      final int _columnIndexOfCreatedAt = 9;
      while (_stmt.step()) {
        final long _tmpKey;
        _tmpKey = _stmt.getLong(_itemKeyIndex);
        final ArrayList<Category> _tmpRelation = _map.get(_tmpKey);
        if (_tmpRelation != null) {
          final Category _item_1;
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final String _tmpNameEnglish;
          if (_stmt.isNull(_columnIndexOfNameEnglish)) {
            _tmpNameEnglish = null;
          } else {
            _tmpNameEnglish = _stmt.getText(_columnIndexOfNameEnglish);
          }
          final String _tmpNameHindi;
          if (_stmt.isNull(_columnIndexOfNameHindi)) {
            _tmpNameHindi = null;
          } else {
            _tmpNameHindi = _stmt.getText(_columnIndexOfNameHindi);
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
          final Integer _tmpIconResId;
          if (_stmt.isNull(_columnIndexOfIconResId)) {
            _tmpIconResId = null;
          } else {
            _tmpIconResId = (int) (_stmt.getLong(_columnIndexOfIconResId));
          }
          final String _tmpColorHex;
          if (_stmt.isNull(_columnIndexOfColorHex)) {
            _tmpColorHex = null;
          } else {
            _tmpColorHex = _stmt.getText(_columnIndexOfColorHex);
          }
          final int _tmpOrderIndex;
          _tmpOrderIndex = (int) (_stmt.getLong(_columnIndexOfOrderIndex));
          final boolean _tmpIsDefault;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsDefault));
          _tmpIsDefault = _tmp != 0;
          final long _tmpCreatedAt;
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt);
          _item_1 = new Category(_tmpId,_tmpNameEnglish,_tmpNameHindi,_tmpDescriptionEnglish,_tmpDescriptionHindi,_tmpIconResId,_tmpColorHex,_tmpOrderIndex,_tmpIsDefault,_tmpCreatedAt);
          _tmpRelation.add(_item_1);
        }
      }
    } finally {
      _stmt.close();
    }
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
