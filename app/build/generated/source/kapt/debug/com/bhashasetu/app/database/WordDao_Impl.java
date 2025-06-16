package com.bhashasetu.app.database;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeleteOrUpdateAdapter;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.SQLiteStatement;
import com.bhashasetu.app.model.Word;
import java.lang.Class;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

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
        return "INSERT OR ABORT INTO `words` (`id`,`englishWord`,`hindiWord`,`englishPronunciation`,`hindiPronunciation`,`category`,`difficulty`,`isFavorite`,`timeAdded`,`masteryLevel`,`exampleSentenceEnglish`,`exampleSentenceHindi`,`notes`,`usageContext`,`partOfSpeech`,`imageUrl`,`englishAudioFileName`,`hindiAudioFileName`,`hasImage`,`hasEnglishAudio`,`hasHindiAudio`,`correctAttempts`,`totalAttempts`,`lastPracticed`,`nextReviewDue`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final Word entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getEnglishWord() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getEnglishWord());
        }
        if (entity.getHindiWord() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getHindiWord());
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
        if (entity.getCategory() == null) {
          statement.bindNull(6);
        } else {
          statement.bindText(6, entity.getCategory());
        }
        statement.bindLong(7, entity.getDifficulty());
        final int _tmp = entity.isFavorite() ? 1 : 0;
        statement.bindLong(8, _tmp);
        statement.bindLong(9, entity.getTimeAdded());
        statement.bindLong(10, entity.getMasteryLevel());
        if (entity.getExampleSentenceEnglish() == null) {
          statement.bindNull(11);
        } else {
          statement.bindText(11, entity.getExampleSentenceEnglish());
        }
        if (entity.getExampleSentenceHindi() == null) {
          statement.bindNull(12);
        } else {
          statement.bindText(12, entity.getExampleSentenceHindi());
        }
        if (entity.getNotes() == null) {
          statement.bindNull(13);
        } else {
          statement.bindText(13, entity.getNotes());
        }
        if (entity.getUsageContext() == null) {
          statement.bindNull(14);
        } else {
          statement.bindText(14, entity.getUsageContext());
        }
        if (entity.getPartOfSpeech() == null) {
          statement.bindNull(15);
        } else {
          statement.bindText(15, entity.getPartOfSpeech());
        }
        if (entity.getImageUrl() == null) {
          statement.bindNull(16);
        } else {
          statement.bindText(16, entity.getImageUrl());
        }
        if (entity.getEnglishAudioFileName() == null) {
          statement.bindNull(17);
        } else {
          statement.bindText(17, entity.getEnglishAudioFileName());
        }
        if (entity.getHindiAudioFileName() == null) {
          statement.bindNull(18);
        } else {
          statement.bindText(18, entity.getHindiAudioFileName());
        }
        final int _tmp_1 = entity.hasImage() ? 1 : 0;
        statement.bindLong(19, _tmp_1);
        final int _tmp_2 = entity.hasEnglishAudio() ? 1 : 0;
        statement.bindLong(20, _tmp_2);
        final int _tmp_3 = entity.hasHindiAudio() ? 1 : 0;
        statement.bindLong(21, _tmp_3);
        statement.bindLong(22, entity.getCorrectAttempts());
        statement.bindLong(23, entity.getTotalAttempts());
        statement.bindLong(24, entity.getLastPracticed());
        statement.bindLong(25, entity.getNextReviewDue());
      }
    };
    this.__deleteAdapterOfWord = new EntityDeleteOrUpdateAdapter<Word>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `words` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final Word entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfWord = new EntityDeleteOrUpdateAdapter<Word>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `words` SET `id` = ?,`englishWord` = ?,`hindiWord` = ?,`englishPronunciation` = ?,`hindiPronunciation` = ?,`category` = ?,`difficulty` = ?,`isFavorite` = ?,`timeAdded` = ?,`masteryLevel` = ?,`exampleSentenceEnglish` = ?,`exampleSentenceHindi` = ?,`notes` = ?,`usageContext` = ?,`partOfSpeech` = ?,`imageUrl` = ?,`englishAudioFileName` = ?,`hindiAudioFileName` = ?,`hasImage` = ?,`hasEnglishAudio` = ?,`hasHindiAudio` = ?,`correctAttempts` = ?,`totalAttempts` = ?,`lastPracticed` = ?,`nextReviewDue` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final Word entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getEnglishWord() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getEnglishWord());
        }
        if (entity.getHindiWord() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getHindiWord());
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
        if (entity.getCategory() == null) {
          statement.bindNull(6);
        } else {
          statement.bindText(6, entity.getCategory());
        }
        statement.bindLong(7, entity.getDifficulty());
        final int _tmp = entity.isFavorite() ? 1 : 0;
        statement.bindLong(8, _tmp);
        statement.bindLong(9, entity.getTimeAdded());
        statement.bindLong(10, entity.getMasteryLevel());
        if (entity.getExampleSentenceEnglish() == null) {
          statement.bindNull(11);
        } else {
          statement.bindText(11, entity.getExampleSentenceEnglish());
        }
        if (entity.getExampleSentenceHindi() == null) {
          statement.bindNull(12);
        } else {
          statement.bindText(12, entity.getExampleSentenceHindi());
        }
        if (entity.getNotes() == null) {
          statement.bindNull(13);
        } else {
          statement.bindText(13, entity.getNotes());
        }
        if (entity.getUsageContext() == null) {
          statement.bindNull(14);
        } else {
          statement.bindText(14, entity.getUsageContext());
        }
        if (entity.getPartOfSpeech() == null) {
          statement.bindNull(15);
        } else {
          statement.bindText(15, entity.getPartOfSpeech());
        }
        if (entity.getImageUrl() == null) {
          statement.bindNull(16);
        } else {
          statement.bindText(16, entity.getImageUrl());
        }
        if (entity.getEnglishAudioFileName() == null) {
          statement.bindNull(17);
        } else {
          statement.bindText(17, entity.getEnglishAudioFileName());
        }
        if (entity.getHindiAudioFileName() == null) {
          statement.bindNull(18);
        } else {
          statement.bindText(18, entity.getHindiAudioFileName());
        }
        final int _tmp_1 = entity.hasImage() ? 1 : 0;
        statement.bindLong(19, _tmp_1);
        final int _tmp_2 = entity.hasEnglishAudio() ? 1 : 0;
        statement.bindLong(20, _tmp_2);
        final int _tmp_3 = entity.hasHindiAudio() ? 1 : 0;
        statement.bindLong(21, _tmp_3);
        statement.bindLong(22, entity.getCorrectAttempts());
        statement.bindLong(23, entity.getTotalAttempts());
        statement.bindLong(24, entity.getLastPracticed());
        statement.bindLong(25, entity.getNextReviewDue());
        statement.bindLong(26, entity.getId());
      }
    };
  }

  @Override
  public long insert(final Word word) {
    return DBUtil.performBlocking(__db, false, true, (_connection) -> {
      return __insertAdapterOfWord.insertAndReturnId(_connection, word);
    });
  }

  @Override
  public void delete(final Word word) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __deleteAdapterOfWord.handle(_connection, word);
      return null;
    });
  }

  @Override
  public void update(final Word word) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __updateAdapterOfWord.handle(_connection, word);
      return null;
    });
  }

  @Override
  public int getWordCount() {
    final String _sql = "SELECT COUNT(*) FROM words";
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
  public LiveData<List<Word>> getAllWords() {
    final String _sql = "SELECT * FROM words ORDER BY englishWord ASC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"words"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfEnglishWord = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "englishWord");
        final int _columnIndexOfHindiWord = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiWord");
        final int _columnIndexOfEnglishPronunciation = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "englishPronunciation");
        final int _columnIndexOfHindiPronunciation = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiPronunciation");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfIsFavorite = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isFavorite");
        final int _columnIndexOfTimeAdded = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timeAdded");
        final int _columnIndexOfMasteryLevel = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "masteryLevel");
        final int _columnIndexOfExampleSentenceEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "exampleSentenceEnglish");
        final int _columnIndexOfExampleSentenceHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "exampleSentenceHindi");
        final int _columnIndexOfNotes = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "notes");
        final int _columnIndexOfUsageContext = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "usageContext");
        final int _columnIndexOfPartOfSpeech = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "partOfSpeech");
        final int _columnIndexOfImageUrl = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "imageUrl");
        final int _columnIndexOfEnglishAudioFileName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "englishAudioFileName");
        final int _columnIndexOfHindiAudioFileName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiAudioFileName");
        final int _columnIndexOfHasImage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hasImage");
        final int _columnIndexOfHasEnglishAudio = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hasEnglishAudio");
        final int _columnIndexOfHasHindiAudio = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hasHindiAudio");
        final int _columnIndexOfCorrectAttempts = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctAttempts");
        final int _columnIndexOfTotalAttempts = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalAttempts");
        final int _columnIndexOfLastPracticed = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastPracticed");
        final int _columnIndexOfNextReviewDue = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nextReviewDue");
        final List<Word> _result = new ArrayList<Word>();
        while (_stmt.step()) {
          final Word _item;
          final String _tmpEnglishWord;
          if (_stmt.isNull(_columnIndexOfEnglishWord)) {
            _tmpEnglishWord = null;
          } else {
            _tmpEnglishWord = _stmt.getText(_columnIndexOfEnglishWord);
          }
          final String _tmpHindiWord;
          if (_stmt.isNull(_columnIndexOfHindiWord)) {
            _tmpHindiWord = null;
          } else {
            _tmpHindiWord = _stmt.getText(_columnIndexOfHindiWord);
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
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final String _tmpExampleSentenceEnglish;
          if (_stmt.isNull(_columnIndexOfExampleSentenceEnglish)) {
            _tmpExampleSentenceEnglish = null;
          } else {
            _tmpExampleSentenceEnglish = _stmt.getText(_columnIndexOfExampleSentenceEnglish);
          }
          final String _tmpExampleSentenceHindi;
          if (_stmt.isNull(_columnIndexOfExampleSentenceHindi)) {
            _tmpExampleSentenceHindi = null;
          } else {
            _tmpExampleSentenceHindi = _stmt.getText(_columnIndexOfExampleSentenceHindi);
          }
          final String _tmpUsageContext;
          if (_stmt.isNull(_columnIndexOfUsageContext)) {
            _tmpUsageContext = null;
          } else {
            _tmpUsageContext = _stmt.getText(_columnIndexOfUsageContext);
          }
          final String _tmpPartOfSpeech;
          if (_stmt.isNull(_columnIndexOfPartOfSpeech)) {
            _tmpPartOfSpeech = null;
          } else {
            _tmpPartOfSpeech = _stmt.getText(_columnIndexOfPartOfSpeech);
          }
          _item = new Word(_tmpEnglishWord,_tmpHindiWord,_tmpEnglishPronunciation,_tmpHindiPronunciation,_tmpCategory,_tmpDifficulty,_tmpExampleSentenceEnglish,_tmpExampleSentenceHindi,_tmpPartOfSpeech,_tmpUsageContext);
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final boolean _tmpIsFavorite;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsFavorite));
          _tmpIsFavorite = _tmp != 0;
          _item.setFavorite(_tmpIsFavorite);
          final long _tmpTimeAdded;
          _tmpTimeAdded = _stmt.getLong(_columnIndexOfTimeAdded);
          _item.setTimeAdded(_tmpTimeAdded);
          final int _tmpMasteryLevel;
          _tmpMasteryLevel = (int) (_stmt.getLong(_columnIndexOfMasteryLevel));
          _item.setMasteryLevel(_tmpMasteryLevel);
          final String _tmpNotes;
          if (_stmt.isNull(_columnIndexOfNotes)) {
            _tmpNotes = null;
          } else {
            _tmpNotes = _stmt.getText(_columnIndexOfNotes);
          }
          _item.setNotes(_tmpNotes);
          final String _tmpImageUrl;
          if (_stmt.isNull(_columnIndexOfImageUrl)) {
            _tmpImageUrl = null;
          } else {
            _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl);
          }
          _item.setImageUrl(_tmpImageUrl);
          final String _tmpEnglishAudioFileName;
          if (_stmt.isNull(_columnIndexOfEnglishAudioFileName)) {
            _tmpEnglishAudioFileName = null;
          } else {
            _tmpEnglishAudioFileName = _stmt.getText(_columnIndexOfEnglishAudioFileName);
          }
          _item.setEnglishAudioFileName(_tmpEnglishAudioFileName);
          final String _tmpHindiAudioFileName;
          if (_stmt.isNull(_columnIndexOfHindiAudioFileName)) {
            _tmpHindiAudioFileName = null;
          } else {
            _tmpHindiAudioFileName = _stmt.getText(_columnIndexOfHindiAudioFileName);
          }
          _item.setHindiAudioFileName(_tmpHindiAudioFileName);
          final boolean _tmpHasImage;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfHasImage));
          _tmpHasImage = _tmp_1 != 0;
          _item.setHasImage(_tmpHasImage);
          final boolean _tmpHasEnglishAudio;
          final int _tmp_2;
          _tmp_2 = (int) (_stmt.getLong(_columnIndexOfHasEnglishAudio));
          _tmpHasEnglishAudio = _tmp_2 != 0;
          _item.setHasEnglishAudio(_tmpHasEnglishAudio);
          final boolean _tmpHasHindiAudio;
          final int _tmp_3;
          _tmp_3 = (int) (_stmt.getLong(_columnIndexOfHasHindiAudio));
          _tmpHasHindiAudio = _tmp_3 != 0;
          _item.setHasHindiAudio(_tmpHasHindiAudio);
          final int _tmpCorrectAttempts;
          _tmpCorrectAttempts = (int) (_stmt.getLong(_columnIndexOfCorrectAttempts));
          _item.setCorrectAttempts(_tmpCorrectAttempts);
          final int _tmpTotalAttempts;
          _tmpTotalAttempts = (int) (_stmt.getLong(_columnIndexOfTotalAttempts));
          _item.setTotalAttempts(_tmpTotalAttempts);
          final long _tmpLastPracticed;
          _tmpLastPracticed = _stmt.getLong(_columnIndexOfLastPracticed);
          _item.setLastPracticed(_tmpLastPracticed);
          final long _tmpNextReviewDue;
          _tmpNextReviewDue = _stmt.getLong(_columnIndexOfNextReviewDue);
          _item.setNextReviewDue(_tmpNextReviewDue);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Word>> getWordsByCategory(final String category) {
    final String _sql = "SELECT * FROM words WHERE category = ? ORDER BY englishWord ASC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"words"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (category == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, category);
        }
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfEnglishWord = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "englishWord");
        final int _columnIndexOfHindiWord = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiWord");
        final int _columnIndexOfEnglishPronunciation = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "englishPronunciation");
        final int _columnIndexOfHindiPronunciation = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiPronunciation");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfIsFavorite = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isFavorite");
        final int _columnIndexOfTimeAdded = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timeAdded");
        final int _columnIndexOfMasteryLevel = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "masteryLevel");
        final int _columnIndexOfExampleSentenceEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "exampleSentenceEnglish");
        final int _columnIndexOfExampleSentenceHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "exampleSentenceHindi");
        final int _columnIndexOfNotes = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "notes");
        final int _columnIndexOfUsageContext = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "usageContext");
        final int _columnIndexOfPartOfSpeech = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "partOfSpeech");
        final int _columnIndexOfImageUrl = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "imageUrl");
        final int _columnIndexOfEnglishAudioFileName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "englishAudioFileName");
        final int _columnIndexOfHindiAudioFileName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiAudioFileName");
        final int _columnIndexOfHasImage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hasImage");
        final int _columnIndexOfHasEnglishAudio = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hasEnglishAudio");
        final int _columnIndexOfHasHindiAudio = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hasHindiAudio");
        final int _columnIndexOfCorrectAttempts = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctAttempts");
        final int _columnIndexOfTotalAttempts = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalAttempts");
        final int _columnIndexOfLastPracticed = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastPracticed");
        final int _columnIndexOfNextReviewDue = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nextReviewDue");
        final List<Word> _result = new ArrayList<Word>();
        while (_stmt.step()) {
          final Word _item;
          final String _tmpEnglishWord;
          if (_stmt.isNull(_columnIndexOfEnglishWord)) {
            _tmpEnglishWord = null;
          } else {
            _tmpEnglishWord = _stmt.getText(_columnIndexOfEnglishWord);
          }
          final String _tmpHindiWord;
          if (_stmt.isNull(_columnIndexOfHindiWord)) {
            _tmpHindiWord = null;
          } else {
            _tmpHindiWord = _stmt.getText(_columnIndexOfHindiWord);
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
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final String _tmpExampleSentenceEnglish;
          if (_stmt.isNull(_columnIndexOfExampleSentenceEnglish)) {
            _tmpExampleSentenceEnglish = null;
          } else {
            _tmpExampleSentenceEnglish = _stmt.getText(_columnIndexOfExampleSentenceEnglish);
          }
          final String _tmpExampleSentenceHindi;
          if (_stmt.isNull(_columnIndexOfExampleSentenceHindi)) {
            _tmpExampleSentenceHindi = null;
          } else {
            _tmpExampleSentenceHindi = _stmt.getText(_columnIndexOfExampleSentenceHindi);
          }
          final String _tmpUsageContext;
          if (_stmt.isNull(_columnIndexOfUsageContext)) {
            _tmpUsageContext = null;
          } else {
            _tmpUsageContext = _stmt.getText(_columnIndexOfUsageContext);
          }
          final String _tmpPartOfSpeech;
          if (_stmt.isNull(_columnIndexOfPartOfSpeech)) {
            _tmpPartOfSpeech = null;
          } else {
            _tmpPartOfSpeech = _stmt.getText(_columnIndexOfPartOfSpeech);
          }
          _item = new Word(_tmpEnglishWord,_tmpHindiWord,_tmpEnglishPronunciation,_tmpHindiPronunciation,_tmpCategory,_tmpDifficulty,_tmpExampleSentenceEnglish,_tmpExampleSentenceHindi,_tmpPartOfSpeech,_tmpUsageContext);
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final boolean _tmpIsFavorite;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsFavorite));
          _tmpIsFavorite = _tmp != 0;
          _item.setFavorite(_tmpIsFavorite);
          final long _tmpTimeAdded;
          _tmpTimeAdded = _stmt.getLong(_columnIndexOfTimeAdded);
          _item.setTimeAdded(_tmpTimeAdded);
          final int _tmpMasteryLevel;
          _tmpMasteryLevel = (int) (_stmt.getLong(_columnIndexOfMasteryLevel));
          _item.setMasteryLevel(_tmpMasteryLevel);
          final String _tmpNotes;
          if (_stmt.isNull(_columnIndexOfNotes)) {
            _tmpNotes = null;
          } else {
            _tmpNotes = _stmt.getText(_columnIndexOfNotes);
          }
          _item.setNotes(_tmpNotes);
          final String _tmpImageUrl;
          if (_stmt.isNull(_columnIndexOfImageUrl)) {
            _tmpImageUrl = null;
          } else {
            _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl);
          }
          _item.setImageUrl(_tmpImageUrl);
          final String _tmpEnglishAudioFileName;
          if (_stmt.isNull(_columnIndexOfEnglishAudioFileName)) {
            _tmpEnglishAudioFileName = null;
          } else {
            _tmpEnglishAudioFileName = _stmt.getText(_columnIndexOfEnglishAudioFileName);
          }
          _item.setEnglishAudioFileName(_tmpEnglishAudioFileName);
          final String _tmpHindiAudioFileName;
          if (_stmt.isNull(_columnIndexOfHindiAudioFileName)) {
            _tmpHindiAudioFileName = null;
          } else {
            _tmpHindiAudioFileName = _stmt.getText(_columnIndexOfHindiAudioFileName);
          }
          _item.setHindiAudioFileName(_tmpHindiAudioFileName);
          final boolean _tmpHasImage;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfHasImage));
          _tmpHasImage = _tmp_1 != 0;
          _item.setHasImage(_tmpHasImage);
          final boolean _tmpHasEnglishAudio;
          final int _tmp_2;
          _tmp_2 = (int) (_stmt.getLong(_columnIndexOfHasEnglishAudio));
          _tmpHasEnglishAudio = _tmp_2 != 0;
          _item.setHasEnglishAudio(_tmpHasEnglishAudio);
          final boolean _tmpHasHindiAudio;
          final int _tmp_3;
          _tmp_3 = (int) (_stmt.getLong(_columnIndexOfHasHindiAudio));
          _tmpHasHindiAudio = _tmp_3 != 0;
          _item.setHasHindiAudio(_tmpHasHindiAudio);
          final int _tmpCorrectAttempts;
          _tmpCorrectAttempts = (int) (_stmt.getLong(_columnIndexOfCorrectAttempts));
          _item.setCorrectAttempts(_tmpCorrectAttempts);
          final int _tmpTotalAttempts;
          _tmpTotalAttempts = (int) (_stmt.getLong(_columnIndexOfTotalAttempts));
          _item.setTotalAttempts(_tmpTotalAttempts);
          final long _tmpLastPracticed;
          _tmpLastPracticed = _stmt.getLong(_columnIndexOfLastPracticed);
          _item.setLastPracticed(_tmpLastPracticed);
          final long _tmpNextReviewDue;
          _tmpNextReviewDue = _stmt.getLong(_columnIndexOfNextReviewDue);
          _item.setNextReviewDue(_tmpNextReviewDue);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Word>> getFavoriteWords() {
    final String _sql = "SELECT * FROM words WHERE isFavorite = 1 ORDER BY englishWord ASC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"words"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfEnglishWord = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "englishWord");
        final int _columnIndexOfHindiWord = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiWord");
        final int _columnIndexOfEnglishPronunciation = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "englishPronunciation");
        final int _columnIndexOfHindiPronunciation = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiPronunciation");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfIsFavorite = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isFavorite");
        final int _columnIndexOfTimeAdded = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timeAdded");
        final int _columnIndexOfMasteryLevel = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "masteryLevel");
        final int _columnIndexOfExampleSentenceEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "exampleSentenceEnglish");
        final int _columnIndexOfExampleSentenceHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "exampleSentenceHindi");
        final int _columnIndexOfNotes = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "notes");
        final int _columnIndexOfUsageContext = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "usageContext");
        final int _columnIndexOfPartOfSpeech = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "partOfSpeech");
        final int _columnIndexOfImageUrl = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "imageUrl");
        final int _columnIndexOfEnglishAudioFileName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "englishAudioFileName");
        final int _columnIndexOfHindiAudioFileName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiAudioFileName");
        final int _columnIndexOfHasImage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hasImage");
        final int _columnIndexOfHasEnglishAudio = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hasEnglishAudio");
        final int _columnIndexOfHasHindiAudio = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hasHindiAudio");
        final int _columnIndexOfCorrectAttempts = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctAttempts");
        final int _columnIndexOfTotalAttempts = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalAttempts");
        final int _columnIndexOfLastPracticed = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastPracticed");
        final int _columnIndexOfNextReviewDue = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nextReviewDue");
        final List<Word> _result = new ArrayList<Word>();
        while (_stmt.step()) {
          final Word _item;
          final String _tmpEnglishWord;
          if (_stmt.isNull(_columnIndexOfEnglishWord)) {
            _tmpEnglishWord = null;
          } else {
            _tmpEnglishWord = _stmt.getText(_columnIndexOfEnglishWord);
          }
          final String _tmpHindiWord;
          if (_stmt.isNull(_columnIndexOfHindiWord)) {
            _tmpHindiWord = null;
          } else {
            _tmpHindiWord = _stmt.getText(_columnIndexOfHindiWord);
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
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final String _tmpExampleSentenceEnglish;
          if (_stmt.isNull(_columnIndexOfExampleSentenceEnglish)) {
            _tmpExampleSentenceEnglish = null;
          } else {
            _tmpExampleSentenceEnglish = _stmt.getText(_columnIndexOfExampleSentenceEnglish);
          }
          final String _tmpExampleSentenceHindi;
          if (_stmt.isNull(_columnIndexOfExampleSentenceHindi)) {
            _tmpExampleSentenceHindi = null;
          } else {
            _tmpExampleSentenceHindi = _stmt.getText(_columnIndexOfExampleSentenceHindi);
          }
          final String _tmpUsageContext;
          if (_stmt.isNull(_columnIndexOfUsageContext)) {
            _tmpUsageContext = null;
          } else {
            _tmpUsageContext = _stmt.getText(_columnIndexOfUsageContext);
          }
          final String _tmpPartOfSpeech;
          if (_stmt.isNull(_columnIndexOfPartOfSpeech)) {
            _tmpPartOfSpeech = null;
          } else {
            _tmpPartOfSpeech = _stmt.getText(_columnIndexOfPartOfSpeech);
          }
          _item = new Word(_tmpEnglishWord,_tmpHindiWord,_tmpEnglishPronunciation,_tmpHindiPronunciation,_tmpCategory,_tmpDifficulty,_tmpExampleSentenceEnglish,_tmpExampleSentenceHindi,_tmpPartOfSpeech,_tmpUsageContext);
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final boolean _tmpIsFavorite;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsFavorite));
          _tmpIsFavorite = _tmp != 0;
          _item.setFavorite(_tmpIsFavorite);
          final long _tmpTimeAdded;
          _tmpTimeAdded = _stmt.getLong(_columnIndexOfTimeAdded);
          _item.setTimeAdded(_tmpTimeAdded);
          final int _tmpMasteryLevel;
          _tmpMasteryLevel = (int) (_stmt.getLong(_columnIndexOfMasteryLevel));
          _item.setMasteryLevel(_tmpMasteryLevel);
          final String _tmpNotes;
          if (_stmt.isNull(_columnIndexOfNotes)) {
            _tmpNotes = null;
          } else {
            _tmpNotes = _stmt.getText(_columnIndexOfNotes);
          }
          _item.setNotes(_tmpNotes);
          final String _tmpImageUrl;
          if (_stmt.isNull(_columnIndexOfImageUrl)) {
            _tmpImageUrl = null;
          } else {
            _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl);
          }
          _item.setImageUrl(_tmpImageUrl);
          final String _tmpEnglishAudioFileName;
          if (_stmt.isNull(_columnIndexOfEnglishAudioFileName)) {
            _tmpEnglishAudioFileName = null;
          } else {
            _tmpEnglishAudioFileName = _stmt.getText(_columnIndexOfEnglishAudioFileName);
          }
          _item.setEnglishAudioFileName(_tmpEnglishAudioFileName);
          final String _tmpHindiAudioFileName;
          if (_stmt.isNull(_columnIndexOfHindiAudioFileName)) {
            _tmpHindiAudioFileName = null;
          } else {
            _tmpHindiAudioFileName = _stmt.getText(_columnIndexOfHindiAudioFileName);
          }
          _item.setHindiAudioFileName(_tmpHindiAudioFileName);
          final boolean _tmpHasImage;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfHasImage));
          _tmpHasImage = _tmp_1 != 0;
          _item.setHasImage(_tmpHasImage);
          final boolean _tmpHasEnglishAudio;
          final int _tmp_2;
          _tmp_2 = (int) (_stmt.getLong(_columnIndexOfHasEnglishAudio));
          _tmpHasEnglishAudio = _tmp_2 != 0;
          _item.setHasEnglishAudio(_tmpHasEnglishAudio);
          final boolean _tmpHasHindiAudio;
          final int _tmp_3;
          _tmp_3 = (int) (_stmt.getLong(_columnIndexOfHasHindiAudio));
          _tmpHasHindiAudio = _tmp_3 != 0;
          _item.setHasHindiAudio(_tmpHasHindiAudio);
          final int _tmpCorrectAttempts;
          _tmpCorrectAttempts = (int) (_stmt.getLong(_columnIndexOfCorrectAttempts));
          _item.setCorrectAttempts(_tmpCorrectAttempts);
          final int _tmpTotalAttempts;
          _tmpTotalAttempts = (int) (_stmt.getLong(_columnIndexOfTotalAttempts));
          _item.setTotalAttempts(_tmpTotalAttempts);
          final long _tmpLastPracticed;
          _tmpLastPracticed = _stmt.getLong(_columnIndexOfLastPracticed);
          _item.setLastPracticed(_tmpLastPracticed);
          final long _tmpNextReviewDue;
          _tmpNextReviewDue = _stmt.getLong(_columnIndexOfNextReviewDue);
          _item.setNextReviewDue(_tmpNextReviewDue);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Word>> searchWords(final String query) {
    final String _sql = "SELECT * FROM words WHERE englishWord LIKE '%' || ? || '%' OR hindiWord LIKE '%' || ? || '%'";
    return __db.getInvalidationTracker().createLiveData(new String[] {"words"}, false, (_connection) -> {
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
        final int _columnIndexOfEnglishWord = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "englishWord");
        final int _columnIndexOfHindiWord = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiWord");
        final int _columnIndexOfEnglishPronunciation = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "englishPronunciation");
        final int _columnIndexOfHindiPronunciation = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiPronunciation");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfIsFavorite = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isFavorite");
        final int _columnIndexOfTimeAdded = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timeAdded");
        final int _columnIndexOfMasteryLevel = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "masteryLevel");
        final int _columnIndexOfExampleSentenceEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "exampleSentenceEnglish");
        final int _columnIndexOfExampleSentenceHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "exampleSentenceHindi");
        final int _columnIndexOfNotes = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "notes");
        final int _columnIndexOfUsageContext = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "usageContext");
        final int _columnIndexOfPartOfSpeech = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "partOfSpeech");
        final int _columnIndexOfImageUrl = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "imageUrl");
        final int _columnIndexOfEnglishAudioFileName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "englishAudioFileName");
        final int _columnIndexOfHindiAudioFileName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiAudioFileName");
        final int _columnIndexOfHasImage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hasImage");
        final int _columnIndexOfHasEnglishAudio = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hasEnglishAudio");
        final int _columnIndexOfHasHindiAudio = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hasHindiAudio");
        final int _columnIndexOfCorrectAttempts = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctAttempts");
        final int _columnIndexOfTotalAttempts = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalAttempts");
        final int _columnIndexOfLastPracticed = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastPracticed");
        final int _columnIndexOfNextReviewDue = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nextReviewDue");
        final List<Word> _result = new ArrayList<Word>();
        while (_stmt.step()) {
          final Word _item;
          final String _tmpEnglishWord;
          if (_stmt.isNull(_columnIndexOfEnglishWord)) {
            _tmpEnglishWord = null;
          } else {
            _tmpEnglishWord = _stmt.getText(_columnIndexOfEnglishWord);
          }
          final String _tmpHindiWord;
          if (_stmt.isNull(_columnIndexOfHindiWord)) {
            _tmpHindiWord = null;
          } else {
            _tmpHindiWord = _stmt.getText(_columnIndexOfHindiWord);
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
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final String _tmpExampleSentenceEnglish;
          if (_stmt.isNull(_columnIndexOfExampleSentenceEnglish)) {
            _tmpExampleSentenceEnglish = null;
          } else {
            _tmpExampleSentenceEnglish = _stmt.getText(_columnIndexOfExampleSentenceEnglish);
          }
          final String _tmpExampleSentenceHindi;
          if (_stmt.isNull(_columnIndexOfExampleSentenceHindi)) {
            _tmpExampleSentenceHindi = null;
          } else {
            _tmpExampleSentenceHindi = _stmt.getText(_columnIndexOfExampleSentenceHindi);
          }
          final String _tmpUsageContext;
          if (_stmt.isNull(_columnIndexOfUsageContext)) {
            _tmpUsageContext = null;
          } else {
            _tmpUsageContext = _stmt.getText(_columnIndexOfUsageContext);
          }
          final String _tmpPartOfSpeech;
          if (_stmt.isNull(_columnIndexOfPartOfSpeech)) {
            _tmpPartOfSpeech = null;
          } else {
            _tmpPartOfSpeech = _stmt.getText(_columnIndexOfPartOfSpeech);
          }
          _item = new Word(_tmpEnglishWord,_tmpHindiWord,_tmpEnglishPronunciation,_tmpHindiPronunciation,_tmpCategory,_tmpDifficulty,_tmpExampleSentenceEnglish,_tmpExampleSentenceHindi,_tmpPartOfSpeech,_tmpUsageContext);
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final boolean _tmpIsFavorite;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsFavorite));
          _tmpIsFavorite = _tmp != 0;
          _item.setFavorite(_tmpIsFavorite);
          final long _tmpTimeAdded;
          _tmpTimeAdded = _stmt.getLong(_columnIndexOfTimeAdded);
          _item.setTimeAdded(_tmpTimeAdded);
          final int _tmpMasteryLevel;
          _tmpMasteryLevel = (int) (_stmt.getLong(_columnIndexOfMasteryLevel));
          _item.setMasteryLevel(_tmpMasteryLevel);
          final String _tmpNotes;
          if (_stmt.isNull(_columnIndexOfNotes)) {
            _tmpNotes = null;
          } else {
            _tmpNotes = _stmt.getText(_columnIndexOfNotes);
          }
          _item.setNotes(_tmpNotes);
          final String _tmpImageUrl;
          if (_stmt.isNull(_columnIndexOfImageUrl)) {
            _tmpImageUrl = null;
          } else {
            _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl);
          }
          _item.setImageUrl(_tmpImageUrl);
          final String _tmpEnglishAudioFileName;
          if (_stmt.isNull(_columnIndexOfEnglishAudioFileName)) {
            _tmpEnglishAudioFileName = null;
          } else {
            _tmpEnglishAudioFileName = _stmt.getText(_columnIndexOfEnglishAudioFileName);
          }
          _item.setEnglishAudioFileName(_tmpEnglishAudioFileName);
          final String _tmpHindiAudioFileName;
          if (_stmt.isNull(_columnIndexOfHindiAudioFileName)) {
            _tmpHindiAudioFileName = null;
          } else {
            _tmpHindiAudioFileName = _stmt.getText(_columnIndexOfHindiAudioFileName);
          }
          _item.setHindiAudioFileName(_tmpHindiAudioFileName);
          final boolean _tmpHasImage;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfHasImage));
          _tmpHasImage = _tmp_1 != 0;
          _item.setHasImage(_tmpHasImage);
          final boolean _tmpHasEnglishAudio;
          final int _tmp_2;
          _tmp_2 = (int) (_stmt.getLong(_columnIndexOfHasEnglishAudio));
          _tmpHasEnglishAudio = _tmp_2 != 0;
          _item.setHasEnglishAudio(_tmpHasEnglishAudio);
          final boolean _tmpHasHindiAudio;
          final int _tmp_3;
          _tmp_3 = (int) (_stmt.getLong(_columnIndexOfHasHindiAudio));
          _tmpHasHindiAudio = _tmp_3 != 0;
          _item.setHasHindiAudio(_tmpHasHindiAudio);
          final int _tmpCorrectAttempts;
          _tmpCorrectAttempts = (int) (_stmt.getLong(_columnIndexOfCorrectAttempts));
          _item.setCorrectAttempts(_tmpCorrectAttempts);
          final int _tmpTotalAttempts;
          _tmpTotalAttempts = (int) (_stmt.getLong(_columnIndexOfTotalAttempts));
          _item.setTotalAttempts(_tmpTotalAttempts);
          final long _tmpLastPracticed;
          _tmpLastPracticed = _stmt.getLong(_columnIndexOfLastPracticed);
          _item.setLastPracticed(_tmpLastPracticed);
          final long _tmpNextReviewDue;
          _tmpNextReviewDue = _stmt.getLong(_columnIndexOfNextReviewDue);
          _item.setNextReviewDue(_tmpNextReviewDue);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Word>> getLeastMasteredWords(final int limit) {
    final String _sql = "SELECT * FROM words ORDER BY masteryLevel ASC LIMIT ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"words"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, limit);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfEnglishWord = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "englishWord");
        final int _columnIndexOfHindiWord = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiWord");
        final int _columnIndexOfEnglishPronunciation = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "englishPronunciation");
        final int _columnIndexOfHindiPronunciation = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiPronunciation");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfIsFavorite = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isFavorite");
        final int _columnIndexOfTimeAdded = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timeAdded");
        final int _columnIndexOfMasteryLevel = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "masteryLevel");
        final int _columnIndexOfExampleSentenceEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "exampleSentenceEnglish");
        final int _columnIndexOfExampleSentenceHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "exampleSentenceHindi");
        final int _columnIndexOfNotes = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "notes");
        final int _columnIndexOfUsageContext = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "usageContext");
        final int _columnIndexOfPartOfSpeech = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "partOfSpeech");
        final int _columnIndexOfImageUrl = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "imageUrl");
        final int _columnIndexOfEnglishAudioFileName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "englishAudioFileName");
        final int _columnIndexOfHindiAudioFileName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiAudioFileName");
        final int _columnIndexOfHasImage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hasImage");
        final int _columnIndexOfHasEnglishAudio = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hasEnglishAudio");
        final int _columnIndexOfHasHindiAudio = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hasHindiAudio");
        final int _columnIndexOfCorrectAttempts = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctAttempts");
        final int _columnIndexOfTotalAttempts = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalAttempts");
        final int _columnIndexOfLastPracticed = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastPracticed");
        final int _columnIndexOfNextReviewDue = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nextReviewDue");
        final List<Word> _result = new ArrayList<Word>();
        while (_stmt.step()) {
          final Word _item;
          final String _tmpEnglishWord;
          if (_stmt.isNull(_columnIndexOfEnglishWord)) {
            _tmpEnglishWord = null;
          } else {
            _tmpEnglishWord = _stmt.getText(_columnIndexOfEnglishWord);
          }
          final String _tmpHindiWord;
          if (_stmt.isNull(_columnIndexOfHindiWord)) {
            _tmpHindiWord = null;
          } else {
            _tmpHindiWord = _stmt.getText(_columnIndexOfHindiWord);
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
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final String _tmpExampleSentenceEnglish;
          if (_stmt.isNull(_columnIndexOfExampleSentenceEnglish)) {
            _tmpExampleSentenceEnglish = null;
          } else {
            _tmpExampleSentenceEnglish = _stmt.getText(_columnIndexOfExampleSentenceEnglish);
          }
          final String _tmpExampleSentenceHindi;
          if (_stmt.isNull(_columnIndexOfExampleSentenceHindi)) {
            _tmpExampleSentenceHindi = null;
          } else {
            _tmpExampleSentenceHindi = _stmt.getText(_columnIndexOfExampleSentenceHindi);
          }
          final String _tmpUsageContext;
          if (_stmt.isNull(_columnIndexOfUsageContext)) {
            _tmpUsageContext = null;
          } else {
            _tmpUsageContext = _stmt.getText(_columnIndexOfUsageContext);
          }
          final String _tmpPartOfSpeech;
          if (_stmt.isNull(_columnIndexOfPartOfSpeech)) {
            _tmpPartOfSpeech = null;
          } else {
            _tmpPartOfSpeech = _stmt.getText(_columnIndexOfPartOfSpeech);
          }
          _item = new Word(_tmpEnglishWord,_tmpHindiWord,_tmpEnglishPronunciation,_tmpHindiPronunciation,_tmpCategory,_tmpDifficulty,_tmpExampleSentenceEnglish,_tmpExampleSentenceHindi,_tmpPartOfSpeech,_tmpUsageContext);
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final boolean _tmpIsFavorite;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsFavorite));
          _tmpIsFavorite = _tmp != 0;
          _item.setFavorite(_tmpIsFavorite);
          final long _tmpTimeAdded;
          _tmpTimeAdded = _stmt.getLong(_columnIndexOfTimeAdded);
          _item.setTimeAdded(_tmpTimeAdded);
          final int _tmpMasteryLevel;
          _tmpMasteryLevel = (int) (_stmt.getLong(_columnIndexOfMasteryLevel));
          _item.setMasteryLevel(_tmpMasteryLevel);
          final String _tmpNotes;
          if (_stmt.isNull(_columnIndexOfNotes)) {
            _tmpNotes = null;
          } else {
            _tmpNotes = _stmt.getText(_columnIndexOfNotes);
          }
          _item.setNotes(_tmpNotes);
          final String _tmpImageUrl;
          if (_stmt.isNull(_columnIndexOfImageUrl)) {
            _tmpImageUrl = null;
          } else {
            _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl);
          }
          _item.setImageUrl(_tmpImageUrl);
          final String _tmpEnglishAudioFileName;
          if (_stmt.isNull(_columnIndexOfEnglishAudioFileName)) {
            _tmpEnglishAudioFileName = null;
          } else {
            _tmpEnglishAudioFileName = _stmt.getText(_columnIndexOfEnglishAudioFileName);
          }
          _item.setEnglishAudioFileName(_tmpEnglishAudioFileName);
          final String _tmpHindiAudioFileName;
          if (_stmt.isNull(_columnIndexOfHindiAudioFileName)) {
            _tmpHindiAudioFileName = null;
          } else {
            _tmpHindiAudioFileName = _stmt.getText(_columnIndexOfHindiAudioFileName);
          }
          _item.setHindiAudioFileName(_tmpHindiAudioFileName);
          final boolean _tmpHasImage;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfHasImage));
          _tmpHasImage = _tmp_1 != 0;
          _item.setHasImage(_tmpHasImage);
          final boolean _tmpHasEnglishAudio;
          final int _tmp_2;
          _tmp_2 = (int) (_stmt.getLong(_columnIndexOfHasEnglishAudio));
          _tmpHasEnglishAudio = _tmp_2 != 0;
          _item.setHasEnglishAudio(_tmpHasEnglishAudio);
          final boolean _tmpHasHindiAudio;
          final int _tmp_3;
          _tmp_3 = (int) (_stmt.getLong(_columnIndexOfHasHindiAudio));
          _tmpHasHindiAudio = _tmp_3 != 0;
          _item.setHasHindiAudio(_tmpHasHindiAudio);
          final int _tmpCorrectAttempts;
          _tmpCorrectAttempts = (int) (_stmt.getLong(_columnIndexOfCorrectAttempts));
          _item.setCorrectAttempts(_tmpCorrectAttempts);
          final int _tmpTotalAttempts;
          _tmpTotalAttempts = (int) (_stmt.getLong(_columnIndexOfTotalAttempts));
          _item.setTotalAttempts(_tmpTotalAttempts);
          final long _tmpLastPracticed;
          _tmpLastPracticed = _stmt.getLong(_columnIndexOfLastPracticed);
          _item.setLastPracticed(_tmpLastPracticed);
          final long _tmpNextReviewDue;
          _tmpNextReviewDue = _stmt.getLong(_columnIndexOfNextReviewDue);
          _item.setNextReviewDue(_tmpNextReviewDue);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Word>> getWordsByIds(final List<Integer> wordIds) {
    final StringBuilder _stringBuilder = new StringBuilder();
    _stringBuilder.append("SELECT * FROM words WHERE id IN (");
    final int _inputSize = wordIds == null ? 1 : wordIds.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    return __db.getInvalidationTracker().createLiveData(new String[] {"words"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (wordIds == null) {
          _stmt.bindNull(_argIndex);
        } else {
          for (Integer _item : wordIds) {
            if (_item == null) {
              _stmt.bindNull(_argIndex);
            } else {
              _stmt.bindLong(_argIndex, _item);
            }
            _argIndex++;
          }
        }
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfEnglishWord = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "englishWord");
        final int _columnIndexOfHindiWord = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiWord");
        final int _columnIndexOfEnglishPronunciation = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "englishPronunciation");
        final int _columnIndexOfHindiPronunciation = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiPronunciation");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfIsFavorite = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isFavorite");
        final int _columnIndexOfTimeAdded = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timeAdded");
        final int _columnIndexOfMasteryLevel = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "masteryLevel");
        final int _columnIndexOfExampleSentenceEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "exampleSentenceEnglish");
        final int _columnIndexOfExampleSentenceHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "exampleSentenceHindi");
        final int _columnIndexOfNotes = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "notes");
        final int _columnIndexOfUsageContext = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "usageContext");
        final int _columnIndexOfPartOfSpeech = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "partOfSpeech");
        final int _columnIndexOfImageUrl = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "imageUrl");
        final int _columnIndexOfEnglishAudioFileName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "englishAudioFileName");
        final int _columnIndexOfHindiAudioFileName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiAudioFileName");
        final int _columnIndexOfHasImage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hasImage");
        final int _columnIndexOfHasEnglishAudio = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hasEnglishAudio");
        final int _columnIndexOfHasHindiAudio = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hasHindiAudio");
        final int _columnIndexOfCorrectAttempts = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctAttempts");
        final int _columnIndexOfTotalAttempts = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalAttempts");
        final int _columnIndexOfLastPracticed = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastPracticed");
        final int _columnIndexOfNextReviewDue = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nextReviewDue");
        final List<Word> _result = new ArrayList<Word>();
        while (_stmt.step()) {
          final Word _item_1;
          final String _tmpEnglishWord;
          if (_stmt.isNull(_columnIndexOfEnglishWord)) {
            _tmpEnglishWord = null;
          } else {
            _tmpEnglishWord = _stmt.getText(_columnIndexOfEnglishWord);
          }
          final String _tmpHindiWord;
          if (_stmt.isNull(_columnIndexOfHindiWord)) {
            _tmpHindiWord = null;
          } else {
            _tmpHindiWord = _stmt.getText(_columnIndexOfHindiWord);
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
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final String _tmpExampleSentenceEnglish;
          if (_stmt.isNull(_columnIndexOfExampleSentenceEnglish)) {
            _tmpExampleSentenceEnglish = null;
          } else {
            _tmpExampleSentenceEnglish = _stmt.getText(_columnIndexOfExampleSentenceEnglish);
          }
          final String _tmpExampleSentenceHindi;
          if (_stmt.isNull(_columnIndexOfExampleSentenceHindi)) {
            _tmpExampleSentenceHindi = null;
          } else {
            _tmpExampleSentenceHindi = _stmt.getText(_columnIndexOfExampleSentenceHindi);
          }
          final String _tmpUsageContext;
          if (_stmt.isNull(_columnIndexOfUsageContext)) {
            _tmpUsageContext = null;
          } else {
            _tmpUsageContext = _stmt.getText(_columnIndexOfUsageContext);
          }
          final String _tmpPartOfSpeech;
          if (_stmt.isNull(_columnIndexOfPartOfSpeech)) {
            _tmpPartOfSpeech = null;
          } else {
            _tmpPartOfSpeech = _stmt.getText(_columnIndexOfPartOfSpeech);
          }
          _item_1 = new Word(_tmpEnglishWord,_tmpHindiWord,_tmpEnglishPronunciation,_tmpHindiPronunciation,_tmpCategory,_tmpDifficulty,_tmpExampleSentenceEnglish,_tmpExampleSentenceHindi,_tmpPartOfSpeech,_tmpUsageContext);
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item_1.setId(_tmpId);
          final boolean _tmpIsFavorite;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsFavorite));
          _tmpIsFavorite = _tmp != 0;
          _item_1.setFavorite(_tmpIsFavorite);
          final long _tmpTimeAdded;
          _tmpTimeAdded = _stmt.getLong(_columnIndexOfTimeAdded);
          _item_1.setTimeAdded(_tmpTimeAdded);
          final int _tmpMasteryLevel;
          _tmpMasteryLevel = (int) (_stmt.getLong(_columnIndexOfMasteryLevel));
          _item_1.setMasteryLevel(_tmpMasteryLevel);
          final String _tmpNotes;
          if (_stmt.isNull(_columnIndexOfNotes)) {
            _tmpNotes = null;
          } else {
            _tmpNotes = _stmt.getText(_columnIndexOfNotes);
          }
          _item_1.setNotes(_tmpNotes);
          final String _tmpImageUrl;
          if (_stmt.isNull(_columnIndexOfImageUrl)) {
            _tmpImageUrl = null;
          } else {
            _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl);
          }
          _item_1.setImageUrl(_tmpImageUrl);
          final String _tmpEnglishAudioFileName;
          if (_stmt.isNull(_columnIndexOfEnglishAudioFileName)) {
            _tmpEnglishAudioFileName = null;
          } else {
            _tmpEnglishAudioFileName = _stmt.getText(_columnIndexOfEnglishAudioFileName);
          }
          _item_1.setEnglishAudioFileName(_tmpEnglishAudioFileName);
          final String _tmpHindiAudioFileName;
          if (_stmt.isNull(_columnIndexOfHindiAudioFileName)) {
            _tmpHindiAudioFileName = null;
          } else {
            _tmpHindiAudioFileName = _stmt.getText(_columnIndexOfHindiAudioFileName);
          }
          _item_1.setHindiAudioFileName(_tmpHindiAudioFileName);
          final boolean _tmpHasImage;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfHasImage));
          _tmpHasImage = _tmp_1 != 0;
          _item_1.setHasImage(_tmpHasImage);
          final boolean _tmpHasEnglishAudio;
          final int _tmp_2;
          _tmp_2 = (int) (_stmt.getLong(_columnIndexOfHasEnglishAudio));
          _tmpHasEnglishAudio = _tmp_2 != 0;
          _item_1.setHasEnglishAudio(_tmpHasEnglishAudio);
          final boolean _tmpHasHindiAudio;
          final int _tmp_3;
          _tmp_3 = (int) (_stmt.getLong(_columnIndexOfHasHindiAudio));
          _tmpHasHindiAudio = _tmp_3 != 0;
          _item_1.setHasHindiAudio(_tmpHasHindiAudio);
          final int _tmpCorrectAttempts;
          _tmpCorrectAttempts = (int) (_stmt.getLong(_columnIndexOfCorrectAttempts));
          _item_1.setCorrectAttempts(_tmpCorrectAttempts);
          final int _tmpTotalAttempts;
          _tmpTotalAttempts = (int) (_stmt.getLong(_columnIndexOfTotalAttempts));
          _item_1.setTotalAttempts(_tmpTotalAttempts);
          final long _tmpLastPracticed;
          _tmpLastPracticed = _stmt.getLong(_columnIndexOfLastPracticed);
          _item_1.setLastPracticed(_tmpLastPracticed);
          final long _tmpNextReviewDue;
          _tmpNextReviewDue = _stmt.getLong(_columnIndexOfNextReviewDue);
          _item_1.setNextReviewDue(_tmpNextReviewDue);
          _result.add(_item_1);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Word>> getRandomWordsByDifficulty(final int maxDifficulty, final int limit) {
    final String _sql = "SELECT * FROM words WHERE difficulty <= ? ORDER BY RANDOM() LIMIT ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"words"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, maxDifficulty);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, limit);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfEnglishWord = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "englishWord");
        final int _columnIndexOfHindiWord = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiWord");
        final int _columnIndexOfEnglishPronunciation = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "englishPronunciation");
        final int _columnIndexOfHindiPronunciation = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiPronunciation");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfIsFavorite = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isFavorite");
        final int _columnIndexOfTimeAdded = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timeAdded");
        final int _columnIndexOfMasteryLevel = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "masteryLevel");
        final int _columnIndexOfExampleSentenceEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "exampleSentenceEnglish");
        final int _columnIndexOfExampleSentenceHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "exampleSentenceHindi");
        final int _columnIndexOfNotes = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "notes");
        final int _columnIndexOfUsageContext = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "usageContext");
        final int _columnIndexOfPartOfSpeech = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "partOfSpeech");
        final int _columnIndexOfImageUrl = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "imageUrl");
        final int _columnIndexOfEnglishAudioFileName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "englishAudioFileName");
        final int _columnIndexOfHindiAudioFileName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiAudioFileName");
        final int _columnIndexOfHasImage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hasImage");
        final int _columnIndexOfHasEnglishAudio = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hasEnglishAudio");
        final int _columnIndexOfHasHindiAudio = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hasHindiAudio");
        final int _columnIndexOfCorrectAttempts = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctAttempts");
        final int _columnIndexOfTotalAttempts = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalAttempts");
        final int _columnIndexOfLastPracticed = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastPracticed");
        final int _columnIndexOfNextReviewDue = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nextReviewDue");
        final List<Word> _result = new ArrayList<Word>();
        while (_stmt.step()) {
          final Word _item;
          final String _tmpEnglishWord;
          if (_stmt.isNull(_columnIndexOfEnglishWord)) {
            _tmpEnglishWord = null;
          } else {
            _tmpEnglishWord = _stmt.getText(_columnIndexOfEnglishWord);
          }
          final String _tmpHindiWord;
          if (_stmt.isNull(_columnIndexOfHindiWord)) {
            _tmpHindiWord = null;
          } else {
            _tmpHindiWord = _stmt.getText(_columnIndexOfHindiWord);
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
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final String _tmpExampleSentenceEnglish;
          if (_stmt.isNull(_columnIndexOfExampleSentenceEnglish)) {
            _tmpExampleSentenceEnglish = null;
          } else {
            _tmpExampleSentenceEnglish = _stmt.getText(_columnIndexOfExampleSentenceEnglish);
          }
          final String _tmpExampleSentenceHindi;
          if (_stmt.isNull(_columnIndexOfExampleSentenceHindi)) {
            _tmpExampleSentenceHindi = null;
          } else {
            _tmpExampleSentenceHindi = _stmt.getText(_columnIndexOfExampleSentenceHindi);
          }
          final String _tmpUsageContext;
          if (_stmt.isNull(_columnIndexOfUsageContext)) {
            _tmpUsageContext = null;
          } else {
            _tmpUsageContext = _stmt.getText(_columnIndexOfUsageContext);
          }
          final String _tmpPartOfSpeech;
          if (_stmt.isNull(_columnIndexOfPartOfSpeech)) {
            _tmpPartOfSpeech = null;
          } else {
            _tmpPartOfSpeech = _stmt.getText(_columnIndexOfPartOfSpeech);
          }
          _item = new Word(_tmpEnglishWord,_tmpHindiWord,_tmpEnglishPronunciation,_tmpHindiPronunciation,_tmpCategory,_tmpDifficulty,_tmpExampleSentenceEnglish,_tmpExampleSentenceHindi,_tmpPartOfSpeech,_tmpUsageContext);
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final boolean _tmpIsFavorite;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsFavorite));
          _tmpIsFavorite = _tmp != 0;
          _item.setFavorite(_tmpIsFavorite);
          final long _tmpTimeAdded;
          _tmpTimeAdded = _stmt.getLong(_columnIndexOfTimeAdded);
          _item.setTimeAdded(_tmpTimeAdded);
          final int _tmpMasteryLevel;
          _tmpMasteryLevel = (int) (_stmt.getLong(_columnIndexOfMasteryLevel));
          _item.setMasteryLevel(_tmpMasteryLevel);
          final String _tmpNotes;
          if (_stmt.isNull(_columnIndexOfNotes)) {
            _tmpNotes = null;
          } else {
            _tmpNotes = _stmt.getText(_columnIndexOfNotes);
          }
          _item.setNotes(_tmpNotes);
          final String _tmpImageUrl;
          if (_stmt.isNull(_columnIndexOfImageUrl)) {
            _tmpImageUrl = null;
          } else {
            _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl);
          }
          _item.setImageUrl(_tmpImageUrl);
          final String _tmpEnglishAudioFileName;
          if (_stmt.isNull(_columnIndexOfEnglishAudioFileName)) {
            _tmpEnglishAudioFileName = null;
          } else {
            _tmpEnglishAudioFileName = _stmt.getText(_columnIndexOfEnglishAudioFileName);
          }
          _item.setEnglishAudioFileName(_tmpEnglishAudioFileName);
          final String _tmpHindiAudioFileName;
          if (_stmt.isNull(_columnIndexOfHindiAudioFileName)) {
            _tmpHindiAudioFileName = null;
          } else {
            _tmpHindiAudioFileName = _stmt.getText(_columnIndexOfHindiAudioFileName);
          }
          _item.setHindiAudioFileName(_tmpHindiAudioFileName);
          final boolean _tmpHasImage;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfHasImage));
          _tmpHasImage = _tmp_1 != 0;
          _item.setHasImage(_tmpHasImage);
          final boolean _tmpHasEnglishAudio;
          final int _tmp_2;
          _tmp_2 = (int) (_stmt.getLong(_columnIndexOfHasEnglishAudio));
          _tmpHasEnglishAudio = _tmp_2 != 0;
          _item.setHasEnglishAudio(_tmpHasEnglishAudio);
          final boolean _tmpHasHindiAudio;
          final int _tmp_3;
          _tmp_3 = (int) (_stmt.getLong(_columnIndexOfHasHindiAudio));
          _tmpHasHindiAudio = _tmp_3 != 0;
          _item.setHasHindiAudio(_tmpHasHindiAudio);
          final int _tmpCorrectAttempts;
          _tmpCorrectAttempts = (int) (_stmt.getLong(_columnIndexOfCorrectAttempts));
          _item.setCorrectAttempts(_tmpCorrectAttempts);
          final int _tmpTotalAttempts;
          _tmpTotalAttempts = (int) (_stmt.getLong(_columnIndexOfTotalAttempts));
          _item.setTotalAttempts(_tmpTotalAttempts);
          final long _tmpLastPracticed;
          _tmpLastPracticed = _stmt.getLong(_columnIndexOfLastPracticed);
          _item.setLastPracticed(_tmpLastPracticed);
          final long _tmpNextReviewDue;
          _tmpNextReviewDue = _stmt.getLong(_columnIndexOfNextReviewDue);
          _item.setNextReviewDue(_tmpNextReviewDue);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<Word> getWordById(final int wordId) {
    final String _sql = "SELECT * FROM words WHERE id = ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"words"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, wordId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfEnglishWord = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "englishWord");
        final int _columnIndexOfHindiWord = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiWord");
        final int _columnIndexOfEnglishPronunciation = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "englishPronunciation");
        final int _columnIndexOfHindiPronunciation = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiPronunciation");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfIsFavorite = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isFavorite");
        final int _columnIndexOfTimeAdded = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timeAdded");
        final int _columnIndexOfMasteryLevel = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "masteryLevel");
        final int _columnIndexOfExampleSentenceEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "exampleSentenceEnglish");
        final int _columnIndexOfExampleSentenceHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "exampleSentenceHindi");
        final int _columnIndexOfNotes = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "notes");
        final int _columnIndexOfUsageContext = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "usageContext");
        final int _columnIndexOfPartOfSpeech = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "partOfSpeech");
        final int _columnIndexOfImageUrl = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "imageUrl");
        final int _columnIndexOfEnglishAudioFileName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "englishAudioFileName");
        final int _columnIndexOfHindiAudioFileName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiAudioFileName");
        final int _columnIndexOfHasImage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hasImage");
        final int _columnIndexOfHasEnglishAudio = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hasEnglishAudio");
        final int _columnIndexOfHasHindiAudio = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hasHindiAudio");
        final int _columnIndexOfCorrectAttempts = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctAttempts");
        final int _columnIndexOfTotalAttempts = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalAttempts");
        final int _columnIndexOfLastPracticed = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastPracticed");
        final int _columnIndexOfNextReviewDue = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nextReviewDue");
        final Word _result;
        if (_stmt.step()) {
          final String _tmpEnglishWord;
          if (_stmt.isNull(_columnIndexOfEnglishWord)) {
            _tmpEnglishWord = null;
          } else {
            _tmpEnglishWord = _stmt.getText(_columnIndexOfEnglishWord);
          }
          final String _tmpHindiWord;
          if (_stmt.isNull(_columnIndexOfHindiWord)) {
            _tmpHindiWord = null;
          } else {
            _tmpHindiWord = _stmt.getText(_columnIndexOfHindiWord);
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
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final String _tmpExampleSentenceEnglish;
          if (_stmt.isNull(_columnIndexOfExampleSentenceEnglish)) {
            _tmpExampleSentenceEnglish = null;
          } else {
            _tmpExampleSentenceEnglish = _stmt.getText(_columnIndexOfExampleSentenceEnglish);
          }
          final String _tmpExampleSentenceHindi;
          if (_stmt.isNull(_columnIndexOfExampleSentenceHindi)) {
            _tmpExampleSentenceHindi = null;
          } else {
            _tmpExampleSentenceHindi = _stmt.getText(_columnIndexOfExampleSentenceHindi);
          }
          final String _tmpUsageContext;
          if (_stmt.isNull(_columnIndexOfUsageContext)) {
            _tmpUsageContext = null;
          } else {
            _tmpUsageContext = _stmt.getText(_columnIndexOfUsageContext);
          }
          final String _tmpPartOfSpeech;
          if (_stmt.isNull(_columnIndexOfPartOfSpeech)) {
            _tmpPartOfSpeech = null;
          } else {
            _tmpPartOfSpeech = _stmt.getText(_columnIndexOfPartOfSpeech);
          }
          _result = new Word(_tmpEnglishWord,_tmpHindiWord,_tmpEnglishPronunciation,_tmpHindiPronunciation,_tmpCategory,_tmpDifficulty,_tmpExampleSentenceEnglish,_tmpExampleSentenceHindi,_tmpPartOfSpeech,_tmpUsageContext);
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _result.setId(_tmpId);
          final boolean _tmpIsFavorite;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsFavorite));
          _tmpIsFavorite = _tmp != 0;
          _result.setFavorite(_tmpIsFavorite);
          final long _tmpTimeAdded;
          _tmpTimeAdded = _stmt.getLong(_columnIndexOfTimeAdded);
          _result.setTimeAdded(_tmpTimeAdded);
          final int _tmpMasteryLevel;
          _tmpMasteryLevel = (int) (_stmt.getLong(_columnIndexOfMasteryLevel));
          _result.setMasteryLevel(_tmpMasteryLevel);
          final String _tmpNotes;
          if (_stmt.isNull(_columnIndexOfNotes)) {
            _tmpNotes = null;
          } else {
            _tmpNotes = _stmt.getText(_columnIndexOfNotes);
          }
          _result.setNotes(_tmpNotes);
          final String _tmpImageUrl;
          if (_stmt.isNull(_columnIndexOfImageUrl)) {
            _tmpImageUrl = null;
          } else {
            _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl);
          }
          _result.setImageUrl(_tmpImageUrl);
          final String _tmpEnglishAudioFileName;
          if (_stmt.isNull(_columnIndexOfEnglishAudioFileName)) {
            _tmpEnglishAudioFileName = null;
          } else {
            _tmpEnglishAudioFileName = _stmt.getText(_columnIndexOfEnglishAudioFileName);
          }
          _result.setEnglishAudioFileName(_tmpEnglishAudioFileName);
          final String _tmpHindiAudioFileName;
          if (_stmt.isNull(_columnIndexOfHindiAudioFileName)) {
            _tmpHindiAudioFileName = null;
          } else {
            _tmpHindiAudioFileName = _stmt.getText(_columnIndexOfHindiAudioFileName);
          }
          _result.setHindiAudioFileName(_tmpHindiAudioFileName);
          final boolean _tmpHasImage;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfHasImage));
          _tmpHasImage = _tmp_1 != 0;
          _result.setHasImage(_tmpHasImage);
          final boolean _tmpHasEnglishAudio;
          final int _tmp_2;
          _tmp_2 = (int) (_stmt.getLong(_columnIndexOfHasEnglishAudio));
          _tmpHasEnglishAudio = _tmp_2 != 0;
          _result.setHasEnglishAudio(_tmpHasEnglishAudio);
          final boolean _tmpHasHindiAudio;
          final int _tmp_3;
          _tmp_3 = (int) (_stmt.getLong(_columnIndexOfHasHindiAudio));
          _tmpHasHindiAudio = _tmp_3 != 0;
          _result.setHasHindiAudio(_tmpHasHindiAudio);
          final int _tmpCorrectAttempts;
          _tmpCorrectAttempts = (int) (_stmt.getLong(_columnIndexOfCorrectAttempts));
          _result.setCorrectAttempts(_tmpCorrectAttempts);
          final int _tmpTotalAttempts;
          _tmpTotalAttempts = (int) (_stmt.getLong(_columnIndexOfTotalAttempts));
          _result.setTotalAttempts(_tmpTotalAttempts);
          final long _tmpLastPracticed;
          _tmpLastPracticed = _stmt.getLong(_columnIndexOfLastPracticed);
          _result.setLastPracticed(_tmpLastPracticed);
          final long _tmpNextReviewDue;
          _tmpNextReviewDue = _stmt.getLong(_columnIndexOfNextReviewDue);
          _result.setNextReviewDue(_tmpNextReviewDue);
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
  public LiveData<List<String>> getAllCategories() {
    final String _sql = "SELECT DISTINCT category FROM words ORDER BY category ASC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"words"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final List<String> _result = new ArrayList<String>();
        while (_stmt.step()) {
          final String _item;
          if (_stmt.isNull(0)) {
            _item = null;
          } else {
            _item = _stmt.getText(0);
          }
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Word>> getWordsForLesson(final int lessonId) {
    final String _sql = "SELECT w.* FROM words w INNER JOIN lesson_words lw ON w.id = lw.wordId WHERE lw.lessonId = ? ORDER BY lw.`order` ASC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"words",
        "lesson_words"}, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, lessonId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfEnglishWord = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "englishWord");
        final int _columnIndexOfHindiWord = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiWord");
        final int _columnIndexOfEnglishPronunciation = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "englishPronunciation");
        final int _columnIndexOfHindiPronunciation = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiPronunciation");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfIsFavorite = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isFavorite");
        final int _columnIndexOfTimeAdded = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timeAdded");
        final int _columnIndexOfMasteryLevel = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "masteryLevel");
        final int _columnIndexOfExampleSentenceEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "exampleSentenceEnglish");
        final int _columnIndexOfExampleSentenceHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "exampleSentenceHindi");
        final int _columnIndexOfNotes = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "notes");
        final int _columnIndexOfUsageContext = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "usageContext");
        final int _columnIndexOfPartOfSpeech = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "partOfSpeech");
        final int _columnIndexOfImageUrl = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "imageUrl");
        final int _columnIndexOfEnglishAudioFileName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "englishAudioFileName");
        final int _columnIndexOfHindiAudioFileName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiAudioFileName");
        final int _columnIndexOfHasImage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hasImage");
        final int _columnIndexOfHasEnglishAudio = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hasEnglishAudio");
        final int _columnIndexOfHasHindiAudio = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hasHindiAudio");
        final int _columnIndexOfCorrectAttempts = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctAttempts");
        final int _columnIndexOfTotalAttempts = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalAttempts");
        final int _columnIndexOfLastPracticed = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastPracticed");
        final int _columnIndexOfNextReviewDue = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nextReviewDue");
        final List<Word> _result = new ArrayList<Word>();
        while (_stmt.step()) {
          final Word _item;
          final String _tmpEnglishWord;
          if (_stmt.isNull(_columnIndexOfEnglishWord)) {
            _tmpEnglishWord = null;
          } else {
            _tmpEnglishWord = _stmt.getText(_columnIndexOfEnglishWord);
          }
          final String _tmpHindiWord;
          if (_stmt.isNull(_columnIndexOfHindiWord)) {
            _tmpHindiWord = null;
          } else {
            _tmpHindiWord = _stmt.getText(_columnIndexOfHindiWord);
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
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final String _tmpExampleSentenceEnglish;
          if (_stmt.isNull(_columnIndexOfExampleSentenceEnglish)) {
            _tmpExampleSentenceEnglish = null;
          } else {
            _tmpExampleSentenceEnglish = _stmt.getText(_columnIndexOfExampleSentenceEnglish);
          }
          final String _tmpExampleSentenceHindi;
          if (_stmt.isNull(_columnIndexOfExampleSentenceHindi)) {
            _tmpExampleSentenceHindi = null;
          } else {
            _tmpExampleSentenceHindi = _stmt.getText(_columnIndexOfExampleSentenceHindi);
          }
          final String _tmpUsageContext;
          if (_stmt.isNull(_columnIndexOfUsageContext)) {
            _tmpUsageContext = null;
          } else {
            _tmpUsageContext = _stmt.getText(_columnIndexOfUsageContext);
          }
          final String _tmpPartOfSpeech;
          if (_stmt.isNull(_columnIndexOfPartOfSpeech)) {
            _tmpPartOfSpeech = null;
          } else {
            _tmpPartOfSpeech = _stmt.getText(_columnIndexOfPartOfSpeech);
          }
          _item = new Word(_tmpEnglishWord,_tmpHindiWord,_tmpEnglishPronunciation,_tmpHindiPronunciation,_tmpCategory,_tmpDifficulty,_tmpExampleSentenceEnglish,_tmpExampleSentenceHindi,_tmpPartOfSpeech,_tmpUsageContext);
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final boolean _tmpIsFavorite;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsFavorite));
          _tmpIsFavorite = _tmp != 0;
          _item.setFavorite(_tmpIsFavorite);
          final long _tmpTimeAdded;
          _tmpTimeAdded = _stmt.getLong(_columnIndexOfTimeAdded);
          _item.setTimeAdded(_tmpTimeAdded);
          final int _tmpMasteryLevel;
          _tmpMasteryLevel = (int) (_stmt.getLong(_columnIndexOfMasteryLevel));
          _item.setMasteryLevel(_tmpMasteryLevel);
          final String _tmpNotes;
          if (_stmt.isNull(_columnIndexOfNotes)) {
            _tmpNotes = null;
          } else {
            _tmpNotes = _stmt.getText(_columnIndexOfNotes);
          }
          _item.setNotes(_tmpNotes);
          final String _tmpImageUrl;
          if (_stmt.isNull(_columnIndexOfImageUrl)) {
            _tmpImageUrl = null;
          } else {
            _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl);
          }
          _item.setImageUrl(_tmpImageUrl);
          final String _tmpEnglishAudioFileName;
          if (_stmt.isNull(_columnIndexOfEnglishAudioFileName)) {
            _tmpEnglishAudioFileName = null;
          } else {
            _tmpEnglishAudioFileName = _stmt.getText(_columnIndexOfEnglishAudioFileName);
          }
          _item.setEnglishAudioFileName(_tmpEnglishAudioFileName);
          final String _tmpHindiAudioFileName;
          if (_stmt.isNull(_columnIndexOfHindiAudioFileName)) {
            _tmpHindiAudioFileName = null;
          } else {
            _tmpHindiAudioFileName = _stmt.getText(_columnIndexOfHindiAudioFileName);
          }
          _item.setHindiAudioFileName(_tmpHindiAudioFileName);
          final boolean _tmpHasImage;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfHasImage));
          _tmpHasImage = _tmp_1 != 0;
          _item.setHasImage(_tmpHasImage);
          final boolean _tmpHasEnglishAudio;
          final int _tmp_2;
          _tmp_2 = (int) (_stmt.getLong(_columnIndexOfHasEnglishAudio));
          _tmpHasEnglishAudio = _tmp_2 != 0;
          _item.setHasEnglishAudio(_tmpHasEnglishAudio);
          final boolean _tmpHasHindiAudio;
          final int _tmp_3;
          _tmp_3 = (int) (_stmt.getLong(_columnIndexOfHasHindiAudio));
          _tmpHasHindiAudio = _tmp_3 != 0;
          _item.setHasHindiAudio(_tmpHasHindiAudio);
          final int _tmpCorrectAttempts;
          _tmpCorrectAttempts = (int) (_stmt.getLong(_columnIndexOfCorrectAttempts));
          _item.setCorrectAttempts(_tmpCorrectAttempts);
          final int _tmpTotalAttempts;
          _tmpTotalAttempts = (int) (_stmt.getLong(_columnIndexOfTotalAttempts));
          _item.setTotalAttempts(_tmpTotalAttempts);
          final long _tmpLastPracticed;
          _tmpLastPracticed = _stmt.getLong(_columnIndexOfLastPracticed);
          _item.setLastPracticed(_tmpLastPracticed);
          final long _tmpNextReviewDue;
          _tmpNextReviewDue = _stmt.getLong(_columnIndexOfNextReviewDue);
          _item.setNextReviewDue(_tmpNextReviewDue);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public void deleteAllWords() {
    final String _sql = "DELETE FROM words";
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
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
