package com.bhashasetu.app.database;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeleteOrUpdateAdapter;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteStatement;
import com.bhashasetu.app.model.WordCategory;
import java.lang.Class;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation", "removal"})
public final class WordCategoryDao_Impl implements WordCategoryDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<WordCategory> __insertAdapterOfWordCategory;

  private final EntityDeleteOrUpdateAdapter<WordCategory> __deleteAdapterOfWordCategory;

  private final EntityDeleteOrUpdateAdapter<WordCategory> __updateAdapterOfWordCategory;

  public WordCategoryDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfWordCategory = new EntityInsertAdapter<WordCategory>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `word_categories` (`id`,`name`,`description`,`color`,`totalWords`,`isActive`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final WordCategory entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getName());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getDescription());
        }
        if (entity.getColor() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getColor());
        }
        statement.bindLong(5, entity.getTotalWords());
        final int _tmp = entity.isActive() ? 1 : 0;
        statement.bindLong(6, _tmp);
      }
    };
    this.__deleteAdapterOfWordCategory = new EntityDeleteOrUpdateAdapter<WordCategory>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `word_categories` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final WordCategory entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfWordCategory = new EntityDeleteOrUpdateAdapter<WordCategory>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `word_categories` SET `id` = ?,`name` = ?,`description` = ?,`color` = ?,`totalWords` = ?,`isActive` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final WordCategory entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getName());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getDescription());
        }
        if (entity.getColor() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getColor());
        }
        statement.bindLong(5, entity.getTotalWords());
        final int _tmp = entity.isActive() ? 1 : 0;
        statement.bindLong(6, _tmp);
        statement.bindLong(7, entity.getId());
      }
    };
  }

  @Override
  public void insert(final WordCategory wordCategory) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __insertAdapterOfWordCategory.insert(_connection, wordCategory);
      return null;
    });
  }

  @Override
  public void insertAll(final List<WordCategory> wordCategories) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __insertAdapterOfWordCategory.insert(_connection, wordCategories);
      return null;
    });
  }

  @Override
  public void delete(final WordCategory wordCategory) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __deleteAdapterOfWordCategory.handle(_connection, wordCategory);
      return null;
    });
  }

  @Override
  public void update(final WordCategory wordCategory) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __updateAdapterOfWordCategory.handle(_connection, wordCategory);
      return null;
    });
  }

  @Override
  public LiveData<List<WordCategory>> getAllCategories() {
    final String _sql = "SELECT * FROM word_categories ORDER BY name";
    return __db.getInvalidationTracker().createLiveData(new String[] {"word_categories"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "name");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfColor = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "color");
        final int _columnIndexOfTotalWords = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalWords");
        final int _columnIndexOfIsActive = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isActive");
        final List<WordCategory> _result = new ArrayList<WordCategory>();
        while (_stmt.step()) {
          final WordCategory _item;
          _item = new WordCategory();
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final String _tmpName;
          if (_stmt.isNull(_columnIndexOfName)) {
            _tmpName = null;
          } else {
            _tmpName = _stmt.getText(_columnIndexOfName);
          }
          _item.setName(_tmpName);
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          _item.setDescription(_tmpDescription);
          final String _tmpColor;
          if (_stmt.isNull(_columnIndexOfColor)) {
            _tmpColor = null;
          } else {
            _tmpColor = _stmt.getText(_columnIndexOfColor);
          }
          _item.setColor(_tmpColor);
          final int _tmpTotalWords;
          _tmpTotalWords = (int) (_stmt.getLong(_columnIndexOfTotalWords));
          _item.setTotalWords(_tmpTotalWords);
          final boolean _tmpIsActive;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsActive));
          _tmpIsActive = _tmp != 0;
          _item.setActive(_tmpIsActive);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<WordCategory>> getActiveCategories() {
    final String _sql = "SELECT * FROM word_categories WHERE isActive = 1 ORDER BY name";
    return __db.getInvalidationTracker().createLiveData(new String[] {"word_categories"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "name");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfColor = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "color");
        final int _columnIndexOfTotalWords = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalWords");
        final int _columnIndexOfIsActive = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isActive");
        final List<WordCategory> _result = new ArrayList<WordCategory>();
        while (_stmt.step()) {
          final WordCategory _item;
          _item = new WordCategory();
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final String _tmpName;
          if (_stmt.isNull(_columnIndexOfName)) {
            _tmpName = null;
          } else {
            _tmpName = _stmt.getText(_columnIndexOfName);
          }
          _item.setName(_tmpName);
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          _item.setDescription(_tmpDescription);
          final String _tmpColor;
          if (_stmt.isNull(_columnIndexOfColor)) {
            _tmpColor = null;
          } else {
            _tmpColor = _stmt.getText(_columnIndexOfColor);
          }
          _item.setColor(_tmpColor);
          final int _tmpTotalWords;
          _tmpTotalWords = (int) (_stmt.getLong(_columnIndexOfTotalWords));
          _item.setTotalWords(_tmpTotalWords);
          final boolean _tmpIsActive;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsActive));
          _tmpIsActive = _tmp != 0;
          _item.setActive(_tmpIsActive);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<WordCategory> getCategoryById(final int id) {
    final String _sql = "SELECT * FROM word_categories WHERE id = ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"word_categories"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "name");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfColor = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "color");
        final int _columnIndexOfTotalWords = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalWords");
        final int _columnIndexOfIsActive = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isActive");
        final WordCategory _result;
        if (_stmt.step()) {
          _result = new WordCategory();
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _result.setId(_tmpId);
          final String _tmpName;
          if (_stmt.isNull(_columnIndexOfName)) {
            _tmpName = null;
          } else {
            _tmpName = _stmt.getText(_columnIndexOfName);
          }
          _result.setName(_tmpName);
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          _result.setDescription(_tmpDescription);
          final String _tmpColor;
          if (_stmt.isNull(_columnIndexOfColor)) {
            _tmpColor = null;
          } else {
            _tmpColor = _stmt.getText(_columnIndexOfColor);
          }
          _result.setColor(_tmpColor);
          final int _tmpTotalWords;
          _tmpTotalWords = (int) (_stmt.getLong(_columnIndexOfTotalWords));
          _result.setTotalWords(_tmpTotalWords);
          final boolean _tmpIsActive;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsActive));
          _tmpIsActive = _tmp != 0;
          _result.setActive(_tmpIsActive);
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
  public WordCategory getCategoryByIdSync(final int id) {
    final String _sql = "SELECT * FROM word_categories WHERE id = ?";
    return DBUtil.performBlocking(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "name");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfColor = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "color");
        final int _columnIndexOfTotalWords = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalWords");
        final int _columnIndexOfIsActive = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isActive");
        final WordCategory _result;
        if (_stmt.step()) {
          _result = new WordCategory();
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _result.setId(_tmpId);
          final String _tmpName;
          if (_stmt.isNull(_columnIndexOfName)) {
            _tmpName = null;
          } else {
            _tmpName = _stmt.getText(_columnIndexOfName);
          }
          _result.setName(_tmpName);
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          _result.setDescription(_tmpDescription);
          final String _tmpColor;
          if (_stmt.isNull(_columnIndexOfColor)) {
            _tmpColor = null;
          } else {
            _tmpColor = _stmt.getText(_columnIndexOfColor);
          }
          _result.setColor(_tmpColor);
          final int _tmpTotalWords;
          _tmpTotalWords = (int) (_stmt.getLong(_columnIndexOfTotalWords));
          _result.setTotalWords(_tmpTotalWords);
          final boolean _tmpIsActive;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsActive));
          _tmpIsActive = _tmp != 0;
          _result.setActive(_tmpIsActive);
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
  public LiveData<WordCategory> getCategoryByName(final String name) {
    final String _sql = "SELECT * FROM word_categories WHERE name = ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"word_categories"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (name == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, name);
        }
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "name");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfColor = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "color");
        final int _columnIndexOfTotalWords = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalWords");
        final int _columnIndexOfIsActive = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isActive");
        final WordCategory _result;
        if (_stmt.step()) {
          _result = new WordCategory();
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _result.setId(_tmpId);
          final String _tmpName;
          if (_stmt.isNull(_columnIndexOfName)) {
            _tmpName = null;
          } else {
            _tmpName = _stmt.getText(_columnIndexOfName);
          }
          _result.setName(_tmpName);
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          _result.setDescription(_tmpDescription);
          final String _tmpColor;
          if (_stmt.isNull(_columnIndexOfColor)) {
            _tmpColor = null;
          } else {
            _tmpColor = _stmt.getText(_columnIndexOfColor);
          }
          _result.setColor(_tmpColor);
          final int _tmpTotalWords;
          _tmpTotalWords = (int) (_stmt.getLong(_columnIndexOfTotalWords));
          _result.setTotalWords(_tmpTotalWords);
          final boolean _tmpIsActive;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsActive));
          _tmpIsActive = _tmp != 0;
          _result.setActive(_tmpIsActive);
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
  public LiveData<Integer> getTotalCount() {
    final String _sql = "SELECT COUNT(*) FROM word_categories";
    return __db.getInvalidationTracker().createLiveData(new String[] {"word_categories"}, false, (_connection) -> {
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
  public void deleteAll() {
    final String _sql = "DELETE FROM word_categories";
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
