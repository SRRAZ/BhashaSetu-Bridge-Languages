package com.bhashasetu.app.database;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeleteOrUpdateAdapter;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteStatement;
import com.bhashasetu.app.model.DeckWord;
import com.bhashasetu.app.model.FlashcardDeck;
import com.bhashasetu.app.model.Word;
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
public final class FlashcardDeckDao_Impl implements FlashcardDeckDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<FlashcardDeck> __insertAdapterOfFlashcardDeck;

  private final EntityInsertAdapter<DeckWord> __insertAdapterOfDeckWord;

  private final EntityDeleteOrUpdateAdapter<FlashcardDeck> __deleteAdapterOfFlashcardDeck;

  private final EntityDeleteOrUpdateAdapter<FlashcardDeck> __updateAdapterOfFlashcardDeck;

  public FlashcardDeckDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfFlashcardDeck = new EntityInsertAdapter<FlashcardDeck>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `flashcard_decks` (`id`,`name`,`nameHindi`,`description`,`descriptionHindi`,`category`,`difficulty`,`isFavorite`,`createdDate`,`lastPracticed`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final FlashcardDeck entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getName());
        }
        if (entity.getNameHindi() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getNameHindi());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getDescription());
        }
        if (entity.getDescriptionHindi() == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, entity.getDescriptionHindi());
        }
        if (entity.getCategory() == null) {
          statement.bindNull(6);
        } else {
          statement.bindText(6, entity.getCategory());
        }
        statement.bindLong(7, entity.getDifficulty());
        final int _tmp = entity.isFavorite() ? 1 : 0;
        statement.bindLong(8, _tmp);
        statement.bindLong(9, entity.getCreatedDate());
        statement.bindLong(10, entity.getLastPracticed());
      }
    };
    this.__insertAdapterOfDeckWord = new EntityInsertAdapter<DeckWord>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `deck_words` (`deckId`,`wordId`,`order`,`notes`) VALUES (?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final DeckWord entity) {
        statement.bindLong(1, entity.getDeckId());
        statement.bindLong(2, entity.getWordId());
        statement.bindLong(3, entity.getOrder());
        if (entity.getNotes() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getNotes());
        }
      }
    };
    this.__deleteAdapterOfFlashcardDeck = new EntityDeleteOrUpdateAdapter<FlashcardDeck>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `flashcard_decks` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final FlashcardDeck entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfFlashcardDeck = new EntityDeleteOrUpdateAdapter<FlashcardDeck>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `flashcard_decks` SET `id` = ?,`name` = ?,`nameHindi` = ?,`description` = ?,`descriptionHindi` = ?,`category` = ?,`difficulty` = ?,`isFavorite` = ?,`createdDate` = ?,`lastPracticed` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final FlashcardDeck entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getName());
        }
        if (entity.getNameHindi() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getNameHindi());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getDescription());
        }
        if (entity.getDescriptionHindi() == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, entity.getDescriptionHindi());
        }
        if (entity.getCategory() == null) {
          statement.bindNull(6);
        } else {
          statement.bindText(6, entity.getCategory());
        }
        statement.bindLong(7, entity.getDifficulty());
        final int _tmp = entity.isFavorite() ? 1 : 0;
        statement.bindLong(8, _tmp);
        statement.bindLong(9, entity.getCreatedDate());
        statement.bindLong(10, entity.getLastPracticed());
        statement.bindLong(11, entity.getId());
      }
    };
  }

  @Override
  public long insert(final FlashcardDeck deck) {
    return DBUtil.performBlocking(__db, false, true, (_connection) -> {
      return __insertAdapterOfFlashcardDeck.insertAndReturnId(_connection, deck);
    });
  }

  @Override
  public void insertDeckWord(final DeckWord deckWord) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __insertAdapterOfDeckWord.insert(_connection, deckWord);
      return null;
    });
  }

  @Override
  public void delete(final FlashcardDeck deck) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __deleteAdapterOfFlashcardDeck.handle(_connection, deck);
      return null;
    });
  }

  @Override
  public void update(final FlashcardDeck deck) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __updateAdapterOfFlashcardDeck.handle(_connection, deck);
      return null;
    });
  }

  @Override
  public LiveData<List<FlashcardDeck>> getAllDecks() {
    final String _sql = "SELECT * FROM flashcard_decks ORDER BY name ASC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"flashcard_decks"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "name");
        final int _columnIndexOfNameHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nameHindi");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfIsFavorite = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isFavorite");
        final int _columnIndexOfCreatedDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdDate");
        final int _columnIndexOfLastPracticed = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastPracticed");
        final List<FlashcardDeck> _result = new ArrayList<FlashcardDeck>();
        while (_stmt.step()) {
          final FlashcardDeck _item;
          final String _tmpName;
          if (_stmt.isNull(_columnIndexOfName)) {
            _tmpName = null;
          } else {
            _tmpName = _stmt.getText(_columnIndexOfName);
          }
          final String _tmpNameHindi;
          if (_stmt.isNull(_columnIndexOfNameHindi)) {
            _tmpNameHindi = null;
          } else {
            _tmpNameHindi = _stmt.getText(_columnIndexOfNameHindi);
          }
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          final String _tmpDescriptionHindi;
          if (_stmt.isNull(_columnIndexOfDescriptionHindi)) {
            _tmpDescriptionHindi = null;
          } else {
            _tmpDescriptionHindi = _stmt.getText(_columnIndexOfDescriptionHindi);
          }
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          _item = new FlashcardDeck(_tmpName,_tmpNameHindi,_tmpDescription,_tmpDescriptionHindi,_tmpCategory,_tmpDifficulty);
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final boolean _tmpIsFavorite;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsFavorite));
          _tmpIsFavorite = _tmp != 0;
          _item.setFavorite(_tmpIsFavorite);
          final long _tmpCreatedDate;
          _tmpCreatedDate = _stmt.getLong(_columnIndexOfCreatedDate);
          _item.setCreatedDate(_tmpCreatedDate);
          final long _tmpLastPracticed;
          _tmpLastPracticed = _stmt.getLong(_columnIndexOfLastPracticed);
          _item.setLastPracticed(_tmpLastPracticed);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<FlashcardDeck>> getDecksByCategory(final String category) {
    final String _sql = "SELECT * FROM flashcard_decks WHERE category = ? ORDER BY name ASC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"flashcard_decks"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (category == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, category);
        }
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "name");
        final int _columnIndexOfNameHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nameHindi");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfIsFavorite = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isFavorite");
        final int _columnIndexOfCreatedDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdDate");
        final int _columnIndexOfLastPracticed = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastPracticed");
        final List<FlashcardDeck> _result = new ArrayList<FlashcardDeck>();
        while (_stmt.step()) {
          final FlashcardDeck _item;
          final String _tmpName;
          if (_stmt.isNull(_columnIndexOfName)) {
            _tmpName = null;
          } else {
            _tmpName = _stmt.getText(_columnIndexOfName);
          }
          final String _tmpNameHindi;
          if (_stmt.isNull(_columnIndexOfNameHindi)) {
            _tmpNameHindi = null;
          } else {
            _tmpNameHindi = _stmt.getText(_columnIndexOfNameHindi);
          }
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          final String _tmpDescriptionHindi;
          if (_stmt.isNull(_columnIndexOfDescriptionHindi)) {
            _tmpDescriptionHindi = null;
          } else {
            _tmpDescriptionHindi = _stmt.getText(_columnIndexOfDescriptionHindi);
          }
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          _item = new FlashcardDeck(_tmpName,_tmpNameHindi,_tmpDescription,_tmpDescriptionHindi,_tmpCategory,_tmpDifficulty);
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final boolean _tmpIsFavorite;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsFavorite));
          _tmpIsFavorite = _tmp != 0;
          _item.setFavorite(_tmpIsFavorite);
          final long _tmpCreatedDate;
          _tmpCreatedDate = _stmt.getLong(_columnIndexOfCreatedDate);
          _item.setCreatedDate(_tmpCreatedDate);
          final long _tmpLastPracticed;
          _tmpLastPracticed = _stmt.getLong(_columnIndexOfLastPracticed);
          _item.setLastPracticed(_tmpLastPracticed);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<FlashcardDeck>> getFavoriteDecks() {
    final String _sql = "SELECT * FROM flashcard_decks WHERE isFavorite = 1 ORDER BY name ASC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"flashcard_decks"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "name");
        final int _columnIndexOfNameHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nameHindi");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfIsFavorite = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isFavorite");
        final int _columnIndexOfCreatedDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdDate");
        final int _columnIndexOfLastPracticed = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastPracticed");
        final List<FlashcardDeck> _result = new ArrayList<FlashcardDeck>();
        while (_stmt.step()) {
          final FlashcardDeck _item;
          final String _tmpName;
          if (_stmt.isNull(_columnIndexOfName)) {
            _tmpName = null;
          } else {
            _tmpName = _stmt.getText(_columnIndexOfName);
          }
          final String _tmpNameHindi;
          if (_stmt.isNull(_columnIndexOfNameHindi)) {
            _tmpNameHindi = null;
          } else {
            _tmpNameHindi = _stmt.getText(_columnIndexOfNameHindi);
          }
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          final String _tmpDescriptionHindi;
          if (_stmt.isNull(_columnIndexOfDescriptionHindi)) {
            _tmpDescriptionHindi = null;
          } else {
            _tmpDescriptionHindi = _stmt.getText(_columnIndexOfDescriptionHindi);
          }
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          _item = new FlashcardDeck(_tmpName,_tmpNameHindi,_tmpDescription,_tmpDescriptionHindi,_tmpCategory,_tmpDifficulty);
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final boolean _tmpIsFavorite;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsFavorite));
          _tmpIsFavorite = _tmp != 0;
          _item.setFavorite(_tmpIsFavorite);
          final long _tmpCreatedDate;
          _tmpCreatedDate = _stmt.getLong(_columnIndexOfCreatedDate);
          _item.setCreatedDate(_tmpCreatedDate);
          final long _tmpLastPracticed;
          _tmpLastPracticed = _stmt.getLong(_columnIndexOfLastPracticed);
          _item.setLastPracticed(_tmpLastPracticed);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<FlashcardDeck>> getDecksByMaxDifficulty(final int maxDifficulty) {
    final String _sql = "SELECT * FROM flashcard_decks WHERE difficulty <= ? ORDER BY name ASC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"flashcard_decks"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, maxDifficulty);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "name");
        final int _columnIndexOfNameHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nameHindi");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfIsFavorite = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isFavorite");
        final int _columnIndexOfCreatedDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdDate");
        final int _columnIndexOfLastPracticed = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastPracticed");
        final List<FlashcardDeck> _result = new ArrayList<FlashcardDeck>();
        while (_stmt.step()) {
          final FlashcardDeck _item;
          final String _tmpName;
          if (_stmt.isNull(_columnIndexOfName)) {
            _tmpName = null;
          } else {
            _tmpName = _stmt.getText(_columnIndexOfName);
          }
          final String _tmpNameHindi;
          if (_stmt.isNull(_columnIndexOfNameHindi)) {
            _tmpNameHindi = null;
          } else {
            _tmpNameHindi = _stmt.getText(_columnIndexOfNameHindi);
          }
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          final String _tmpDescriptionHindi;
          if (_stmt.isNull(_columnIndexOfDescriptionHindi)) {
            _tmpDescriptionHindi = null;
          } else {
            _tmpDescriptionHindi = _stmt.getText(_columnIndexOfDescriptionHindi);
          }
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          _item = new FlashcardDeck(_tmpName,_tmpNameHindi,_tmpDescription,_tmpDescriptionHindi,_tmpCategory,_tmpDifficulty);
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final boolean _tmpIsFavorite;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsFavorite));
          _tmpIsFavorite = _tmp != 0;
          _item.setFavorite(_tmpIsFavorite);
          final long _tmpCreatedDate;
          _tmpCreatedDate = _stmt.getLong(_columnIndexOfCreatedDate);
          _item.setCreatedDate(_tmpCreatedDate);
          final long _tmpLastPracticed;
          _tmpLastPracticed = _stmt.getLong(_columnIndexOfLastPracticed);
          _item.setLastPracticed(_tmpLastPracticed);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<FlashcardDeck> getDeckById(final int deckId) {
    final String _sql = "SELECT * FROM flashcard_decks WHERE id = ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"flashcard_decks"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, deckId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "name");
        final int _columnIndexOfNameHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nameHindi");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfIsFavorite = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isFavorite");
        final int _columnIndexOfCreatedDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdDate");
        final int _columnIndexOfLastPracticed = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastPracticed");
        final FlashcardDeck _result;
        if (_stmt.step()) {
          final String _tmpName;
          if (_stmt.isNull(_columnIndexOfName)) {
            _tmpName = null;
          } else {
            _tmpName = _stmt.getText(_columnIndexOfName);
          }
          final String _tmpNameHindi;
          if (_stmt.isNull(_columnIndexOfNameHindi)) {
            _tmpNameHindi = null;
          } else {
            _tmpNameHindi = _stmt.getText(_columnIndexOfNameHindi);
          }
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          final String _tmpDescriptionHindi;
          if (_stmt.isNull(_columnIndexOfDescriptionHindi)) {
            _tmpDescriptionHindi = null;
          } else {
            _tmpDescriptionHindi = _stmt.getText(_columnIndexOfDescriptionHindi);
          }
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          _result = new FlashcardDeck(_tmpName,_tmpNameHindi,_tmpDescription,_tmpDescriptionHindi,_tmpCategory,_tmpDifficulty);
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _result.setId(_tmpId);
          final boolean _tmpIsFavorite;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsFavorite));
          _tmpIsFavorite = _tmp != 0;
          _result.setFavorite(_tmpIsFavorite);
          final long _tmpCreatedDate;
          _tmpCreatedDate = _stmt.getLong(_columnIndexOfCreatedDate);
          _result.setCreatedDate(_tmpCreatedDate);
          final long _tmpLastPracticed;
          _tmpLastPracticed = _stmt.getLong(_columnIndexOfLastPracticed);
          _result.setLastPracticed(_tmpLastPracticed);
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
  public LiveData<List<Word>> getWordsForDeck(final int deckId) {
    final String _sql = "SELECT w.* FROM words w INNER JOIN deck_words dw ON w.id = dw.wordId WHERE dw.deckId = ? ORDER BY dw.`order` ASC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"words",
        "deck_words"}, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, deckId);
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
  public int getWordCountForDeck(final int deckId) {
    final String _sql = "SELECT COUNT(*) FROM deck_words WHERE deckId = ?";
    return DBUtil.performBlocking(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, deckId);
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
  public LiveData<List<String>> getAllCategories() {
    final String _sql = "SELECT DISTINCT category FROM flashcard_decks ORDER BY category ASC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"flashcard_decks"}, false, (_connection) -> {
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
  public boolean deckContainsWord(final int deckId, final int wordId) {
    final String _sql = "SELECT EXISTS(SELECT 1 FROM deck_words WHERE deckId = ? AND wordId = ?)";
    return DBUtil.performBlocking(__db, true, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, deckId);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, wordId);
        final boolean _result;
        if (_stmt.step()) {
          final int _tmp;
          _tmp = (int) (_stmt.getLong(0));
          _result = _tmp != 0;
        } else {
          _result = false;
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public void removeDeckWord(final int deckId, final int wordId) {
    final String _sql = "DELETE FROM deck_words WHERE deckId = ? AND wordId = ?";
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, deckId);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, wordId);
        _stmt.step();
        return null;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public void updateLastPracticed(final int deckId, final long timestamp) {
    final String _sql = "UPDATE flashcard_decks SET lastPracticed = ? WHERE id = ?";
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, timestamp);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, deckId);
        _stmt.step();
        return null;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public void updateFavoriteStatus(final int deckId, final boolean isFavorite) {
    final String _sql = "UPDATE flashcard_decks SET isFavorite = ? WHERE id = ?";
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        final int _tmp = isFavorite ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, deckId);
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
