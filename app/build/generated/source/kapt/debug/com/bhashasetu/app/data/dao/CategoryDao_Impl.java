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
import com.bhashasetu.app.data.model.Word;
import com.bhashasetu.app.data.model.WordCategoryCrossRef;
import com.bhashasetu.app.data.relation.CategoryWithWords;
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
public final class CategoryDao_Impl implements CategoryDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<Category> __insertAdapterOfCategory;

  private final EntityInsertAdapter<WordCategoryCrossRef> __insertAdapterOfWordCategoryCrossRef;

  private final EntityDeleteOrUpdateAdapter<Category> __deleteAdapterOfCategory;

  private final EntityDeleteOrUpdateAdapter<WordCategoryCrossRef> __deleteAdapterOfWordCategoryCrossRef;

  private final EntityDeleteOrUpdateAdapter<Category> __updateAdapterOfCategory;

  public CategoryDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfCategory = new EntityInsertAdapter<Category>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `categories` (`id`,`nameEnglish`,`nameHindi`,`descriptionEnglish`,`descriptionHindi`,`iconResId`,`colorHex`,`orderIndex`,`isDefault`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final Category entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getNameEnglish() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getNameEnglish());
        }
        if (entity.getNameHindi() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getNameHindi());
        }
        if (entity.getDescriptionEnglish() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getDescriptionEnglish());
        }
        if (entity.getDescriptionHindi() == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, entity.getDescriptionHindi());
        }
        if (entity.getIconResId() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getIconResId());
        }
        if (entity.getColorHex() == null) {
          statement.bindNull(7);
        } else {
          statement.bindText(7, entity.getColorHex());
        }
        statement.bindLong(8, entity.getOrderIndex());
        final int _tmp = entity.isDefault() ? 1 : 0;
        statement.bindLong(9, _tmp);
        statement.bindLong(10, entity.getCreatedAt());
      }
    };
    this.__insertAdapterOfWordCategoryCrossRef = new EntityInsertAdapter<WordCategoryCrossRef>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `word_category_cross_refs` (`wordId`,`categoryId`,`addedAt`) VALUES (?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final WordCategoryCrossRef entity) {
        statement.bindLong(1, entity.getWordId());
        statement.bindLong(2, entity.getCategoryId());
        statement.bindLong(3, entity.getAddedAt());
      }
    };
    this.__deleteAdapterOfCategory = new EntityDeleteOrUpdateAdapter<Category>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `categories` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final Category entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__deleteAdapterOfWordCategoryCrossRef = new EntityDeleteOrUpdateAdapter<WordCategoryCrossRef>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `word_category_cross_refs` WHERE `wordId` = ? AND `categoryId` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final WordCategoryCrossRef entity) {
        statement.bindLong(1, entity.getWordId());
        statement.bindLong(2, entity.getCategoryId());
      }
    };
    this.__updateAdapterOfCategory = new EntityDeleteOrUpdateAdapter<Category>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `categories` SET `id` = ?,`nameEnglish` = ?,`nameHindi` = ?,`descriptionEnglish` = ?,`descriptionHindi` = ?,`iconResId` = ?,`colorHex` = ?,`orderIndex` = ?,`isDefault` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final Category entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getNameEnglish() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getNameEnglish());
        }
        if (entity.getNameHindi() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getNameHindi());
        }
        if (entity.getDescriptionEnglish() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getDescriptionEnglish());
        }
        if (entity.getDescriptionHindi() == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, entity.getDescriptionHindi());
        }
        if (entity.getIconResId() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getIconResId());
        }
        if (entity.getColorHex() == null) {
          statement.bindNull(7);
        } else {
          statement.bindText(7, entity.getColorHex());
        }
        statement.bindLong(8, entity.getOrderIndex());
        final int _tmp = entity.isDefault() ? 1 : 0;
        statement.bindLong(9, _tmp);
        statement.bindLong(10, entity.getCreatedAt());
        statement.bindLong(11, entity.getId());
      }
    };
  }

  @Override
  public Object insert(final Category category, final Continuation<? super Long> $completion) {
    if (category == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      return __insertAdapterOfCategory.insertAndReturnId(_connection, category);
    }, $completion);
  }

  @Override
  public Object insertAll(final List<Category> categories,
      final Continuation<? super List<Long>> $completion) {
    if (categories == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      return __insertAdapterOfCategory.insertAndReturnIdsList(_connection, categories);
    }, $completion);
  }

  @Override
  public Object addWordToCategory(final WordCategoryCrossRef wordCategoryCrossRef,
      final Continuation<? super Unit> $completion) {
    if (wordCategoryCrossRef == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __insertAdapterOfWordCategoryCrossRef.insert(_connection, wordCategoryCrossRef);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Object addWordsToCategory(final List<WordCategoryCrossRef> wordCategoryCrossRefs,
      final Continuation<? super Unit> $completion) {
    if (wordCategoryCrossRefs == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __insertAdapterOfWordCategoryCrossRef.insert(_connection, wordCategoryCrossRefs);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Object delete(final Category category, final Continuation<? super Unit> $completion) {
    if (category == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __deleteAdapterOfCategory.handle(_connection, category);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Object removeWordFromCategory(final WordCategoryCrossRef wordCategoryCrossRef,
      final Continuation<? super Unit> $completion) {
    if (wordCategoryCrossRef == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __deleteAdapterOfWordCategoryCrossRef.handle(_connection, wordCategoryCrossRef);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Object update(final Category category, final Continuation<? super Unit> $completion) {
    if (category == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __updateAdapterOfCategory.handle(_connection, category);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Object getCategoryById(final long categoryId,
      final Continuation<? super Category> $completion) {
    final String _sql = "SELECT * FROM categories WHERE id = ?";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, categoryId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfNameEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nameEnglish");
        final int _columnIndexOfNameHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nameHindi");
        final int _columnIndexOfDescriptionEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionEnglish");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfIconResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "iconResId");
        final int _columnIndexOfColorHex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "colorHex");
        final int _columnIndexOfOrderIndex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "orderIndex");
        final int _columnIndexOfIsDefault = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isDefault");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final Category _result;
        if (_stmt.step()) {
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
          _result = new Category(_tmpId,_tmpNameEnglish,_tmpNameHindi,_tmpDescriptionEnglish,_tmpDescriptionHindi,_tmpIconResId,_tmpColorHex,_tmpOrderIndex,_tmpIsDefault,_tmpCreatedAt);
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
  public LiveData<Category> getCategoryByIdLiveData(final long categoryId) {
    final String _sql = "SELECT * FROM categories WHERE id = ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"categories"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, categoryId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfNameEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nameEnglish");
        final int _columnIndexOfNameHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nameHindi");
        final int _columnIndexOfDescriptionEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionEnglish");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfIconResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "iconResId");
        final int _columnIndexOfColorHex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "colorHex");
        final int _columnIndexOfOrderIndex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "orderIndex");
        final int _columnIndexOfIsDefault = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isDefault");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final Category _result;
        if (_stmt.step()) {
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
          _result = new Category(_tmpId,_tmpNameEnglish,_tmpNameHindi,_tmpDescriptionEnglish,_tmpDescriptionHindi,_tmpIconResId,_tmpColorHex,_tmpOrderIndex,_tmpIsDefault,_tmpCreatedAt);
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
  public Flow<Category> getCategoryByIdFlow(final long categoryId) {
    final String _sql = "SELECT * FROM categories WHERE id = ?";
    return FlowUtil.createFlow(__db, false, new String[] {"categories"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, categoryId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfNameEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nameEnglish");
        final int _columnIndexOfNameHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nameHindi");
        final int _columnIndexOfDescriptionEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionEnglish");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfIconResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "iconResId");
        final int _columnIndexOfColorHex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "colorHex");
        final int _columnIndexOfOrderIndex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "orderIndex");
        final int _columnIndexOfIsDefault = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isDefault");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final Category _result;
        if (_stmt.step()) {
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
          _result = new Category(_tmpId,_tmpNameEnglish,_tmpNameHindi,_tmpDescriptionEnglish,_tmpDescriptionHindi,_tmpIconResId,_tmpColorHex,_tmpOrderIndex,_tmpIsDefault,_tmpCreatedAt);
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
  public Flow<List<Category>> getAllCategories() {
    final String _sql = "SELECT * FROM categories ORDER BY orderIndex ASC, nameEnglish ASC";
    return FlowUtil.createFlow(__db, false, new String[] {"categories"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfNameEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nameEnglish");
        final int _columnIndexOfNameHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nameHindi");
        final int _columnIndexOfDescriptionEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionEnglish");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfIconResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "iconResId");
        final int _columnIndexOfColorHex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "colorHex");
        final int _columnIndexOfOrderIndex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "orderIndex");
        final int _columnIndexOfIsDefault = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isDefault");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final List<Category> _result = new ArrayList<Category>();
        while (_stmt.step()) {
          final Category _item;
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
          _item = new Category(_tmpId,_tmpNameEnglish,_tmpNameHindi,_tmpDescriptionEnglish,_tmpDescriptionHindi,_tmpIconResId,_tmpColorHex,_tmpOrderIndex,_tmpIsDefault,_tmpCreatedAt);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Category>> getAllCategoriesLiveData() {
    final String _sql = "SELECT * FROM categories ORDER BY orderIndex ASC, nameEnglish ASC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"categories"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfNameEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nameEnglish");
        final int _columnIndexOfNameHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nameHindi");
        final int _columnIndexOfDescriptionEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionEnglish");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfIconResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "iconResId");
        final int _columnIndexOfColorHex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "colorHex");
        final int _columnIndexOfOrderIndex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "orderIndex");
        final int _columnIndexOfIsDefault = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isDefault");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final List<Category> _result = new ArrayList<Category>();
        while (_stmt.step()) {
          final Category _item;
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
          _item = new Category(_tmpId,_tmpNameEnglish,_tmpNameHindi,_tmpDescriptionEnglish,_tmpDescriptionHindi,_tmpIconResId,_tmpColorHex,_tmpOrderIndex,_tmpIsDefault,_tmpCreatedAt);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Flow<List<Category>> getDefaultCategories() {
    final String _sql = "SELECT * FROM categories WHERE isDefault = 1 ORDER BY orderIndex ASC, nameEnglish ASC";
    return FlowUtil.createFlow(__db, false, new String[] {"categories"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfNameEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nameEnglish");
        final int _columnIndexOfNameHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nameHindi");
        final int _columnIndexOfDescriptionEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionEnglish");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfIconResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "iconResId");
        final int _columnIndexOfColorHex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "colorHex");
        final int _columnIndexOfOrderIndex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "orderIndex");
        final int _columnIndexOfIsDefault = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isDefault");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final List<Category> _result = new ArrayList<Category>();
        while (_stmt.step()) {
          final Category _item;
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
          _item = new Category(_tmpId,_tmpNameEnglish,_tmpNameHindi,_tmpDescriptionEnglish,_tmpDescriptionHindi,_tmpIconResId,_tmpColorHex,_tmpOrderIndex,_tmpIsDefault,_tmpCreatedAt);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Flow<List<Category>> searchCategories(final String query) {
    final String _sql = "SELECT * FROM categories WHERE nameEnglish LIKE '%' || ? || '%' OR nameHindi LIKE '%' || ? || '%' ORDER BY nameEnglish ASC";
    return FlowUtil.createFlow(__db, false, new String[] {"categories"}, (_connection) -> {
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
        final int _columnIndexOfNameEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nameEnglish");
        final int _columnIndexOfNameHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nameHindi");
        final int _columnIndexOfDescriptionEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionEnglish");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfIconResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "iconResId");
        final int _columnIndexOfColorHex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "colorHex");
        final int _columnIndexOfOrderIndex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "orderIndex");
        final int _columnIndexOfIsDefault = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isDefault");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final List<Category> _result = new ArrayList<Category>();
        while (_stmt.step()) {
          final Category _item;
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
          _item = new Category(_tmpId,_tmpNameEnglish,_tmpNameHindi,_tmpDescriptionEnglish,_tmpDescriptionHindi,_tmpIconResId,_tmpColorHex,_tmpOrderIndex,_tmpIsDefault,_tmpCreatedAt);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Object getCategoryWithWords(final long categoryId,
      final Continuation<? super CategoryWithWords> $completion) {
    final String _sql = "SELECT * FROM categories WHERE id = ?";
    return DBUtil.performSuspending(__db, true, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, categoryId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfNameEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nameEnglish");
        final int _columnIndexOfNameHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nameHindi");
        final int _columnIndexOfDescriptionEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionEnglish");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfIconResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "iconResId");
        final int _columnIndexOfColorHex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "colorHex");
        final int _columnIndexOfOrderIndex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "orderIndex");
        final int _columnIndexOfIsDefault = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isDefault");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final LongSparseArray<ArrayList<Word>> _collectionWords = new LongSparseArray<ArrayList<Word>>();
        while (_stmt.step()) {
          final long _tmpKey;
          _tmpKey = _stmt.getLong(_columnIndexOfId);
          if (!_collectionWords.containsKey(_tmpKey)) {
            _collectionWords.put(_tmpKey, new ArrayList<Word>());
          }
        }
        _stmt.reset();
        __fetchRelationshipwordsAscomBhashasetuAppDataModelWord(_connection, _collectionWords);
        final CategoryWithWords _result;
        if (_stmt.step()) {
          final Category _tmpCategory;
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
          _tmpCategory = new Category(_tmpId,_tmpNameEnglish,_tmpNameHindi,_tmpDescriptionEnglish,_tmpDescriptionHindi,_tmpIconResId,_tmpColorHex,_tmpOrderIndex,_tmpIsDefault,_tmpCreatedAt);
          final ArrayList<Word> _tmpWordsCollection;
          final long _tmpKey_1;
          _tmpKey_1 = _stmt.getLong(_columnIndexOfId);
          _tmpWordsCollection = _collectionWords.get(_tmpKey_1);
          _result = new CategoryWithWords(_tmpCategory,_tmpWordsCollection);
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
  public Flow<CategoryWithWords> getCategoryWithWordsFlow(final long categoryId) {
    final String _sql = "SELECT * FROM categories WHERE id = ?";
    return FlowUtil.createFlow(__db, true, new String[] {"word_category_cross_refs", "words",
        "categories"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, categoryId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfNameEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nameEnglish");
        final int _columnIndexOfNameHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nameHindi");
        final int _columnIndexOfDescriptionEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionEnglish");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfIconResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "iconResId");
        final int _columnIndexOfColorHex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "colorHex");
        final int _columnIndexOfOrderIndex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "orderIndex");
        final int _columnIndexOfIsDefault = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isDefault");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final LongSparseArray<ArrayList<Word>> _collectionWords = new LongSparseArray<ArrayList<Word>>();
        while (_stmt.step()) {
          final long _tmpKey;
          _tmpKey = _stmt.getLong(_columnIndexOfId);
          if (!_collectionWords.containsKey(_tmpKey)) {
            _collectionWords.put(_tmpKey, new ArrayList<Word>());
          }
        }
        _stmt.reset();
        __fetchRelationshipwordsAscomBhashasetuAppDataModelWord(_connection, _collectionWords);
        final CategoryWithWords _result;
        if (_stmt.step()) {
          final Category _tmpCategory;
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
          _tmpCategory = new Category(_tmpId,_tmpNameEnglish,_tmpNameHindi,_tmpDescriptionEnglish,_tmpDescriptionHindi,_tmpIconResId,_tmpColorHex,_tmpOrderIndex,_tmpIsDefault,_tmpCreatedAt);
          final ArrayList<Word> _tmpWordsCollection;
          final long _tmpKey_1;
          _tmpKey_1 = _stmt.getLong(_columnIndexOfId);
          _tmpWordsCollection = _collectionWords.get(_tmpKey_1);
          _result = new CategoryWithWords(_tmpCategory,_tmpWordsCollection);
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
  public Flow<List<CategoryWithWords>> getAllCategoriesWithWords() {
    final String _sql = "SELECT * FROM categories ORDER BY orderIndex ASC, nameEnglish ASC";
    return FlowUtil.createFlow(__db, true, new String[] {"word_category_cross_refs", "words",
        "categories"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfNameEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nameEnglish");
        final int _columnIndexOfNameHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nameHindi");
        final int _columnIndexOfDescriptionEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionEnglish");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfIconResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "iconResId");
        final int _columnIndexOfColorHex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "colorHex");
        final int _columnIndexOfOrderIndex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "orderIndex");
        final int _columnIndexOfIsDefault = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isDefault");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final LongSparseArray<ArrayList<Word>> _collectionWords = new LongSparseArray<ArrayList<Word>>();
        while (_stmt.step()) {
          final long _tmpKey;
          _tmpKey = _stmt.getLong(_columnIndexOfId);
          if (!_collectionWords.containsKey(_tmpKey)) {
            _collectionWords.put(_tmpKey, new ArrayList<Word>());
          }
        }
        _stmt.reset();
        __fetchRelationshipwordsAscomBhashasetuAppDataModelWord(_connection, _collectionWords);
        final List<CategoryWithWords> _result = new ArrayList<CategoryWithWords>();
        while (_stmt.step()) {
          final CategoryWithWords _item;
          final Category _tmpCategory;
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
          _tmpCategory = new Category(_tmpId,_tmpNameEnglish,_tmpNameHindi,_tmpDescriptionEnglish,_tmpDescriptionHindi,_tmpIconResId,_tmpColorHex,_tmpOrderIndex,_tmpIsDefault,_tmpCreatedAt);
          final ArrayList<Word> _tmpWordsCollection;
          final long _tmpKey_1;
          _tmpKey_1 = _stmt.getLong(_columnIndexOfId);
          _tmpWordsCollection = _collectionWords.get(_tmpKey_1);
          _item = new CategoryWithWords(_tmpCategory,_tmpWordsCollection);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Flow<List<Category>> getCategoriesForWord(final long wordId) {
    final String _sql = "\n"
            + "        SELECT c.* FROM categories c\n"
            + "        INNER JOIN word_category_cross_refs wc ON c.id = wc.categoryId\n"
            + "        WHERE wc.wordId = ?\n"
            + "        ORDER BY c.orderIndex ASC, c.nameEnglish ASC\n"
            + "    ";
    return FlowUtil.createFlow(__db, false, new String[] {"categories",
        "word_category_cross_refs"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, wordId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfNameEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nameEnglish");
        final int _columnIndexOfNameHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nameHindi");
        final int _columnIndexOfDescriptionEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionEnglish");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfIconResId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "iconResId");
        final int _columnIndexOfColorHex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "colorHex");
        final int _columnIndexOfOrderIndex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "orderIndex");
        final int _columnIndexOfIsDefault = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isDefault");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final List<Category> _result = new ArrayList<Category>();
        while (_stmt.step()) {
          final Category _item;
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
          _item = new Category(_tmpId,_tmpNameEnglish,_tmpNameHindi,_tmpDescriptionEnglish,_tmpDescriptionHindi,_tmpIconResId,_tmpColorHex,_tmpOrderIndex,_tmpIsDefault,_tmpCreatedAt);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Flow<Integer> getCategoryCount() {
    final String _sql = "SELECT COUNT(*) FROM categories";
    return FlowUtil.createFlow(__db, false, new String[] {"categories"}, (_connection) -> {
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
  public Flow<Integer> getDefaultCategoryCount() {
    final String _sql = "SELECT COUNT(*) FROM categories WHERE isDefault = 1";
    return FlowUtil.createFlow(__db, false, new String[] {"categories"}, (_connection) -> {
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
  public Flow<Integer> getStartedCategoryCount() {
    final String _sql = "\n"
            + "        SELECT COUNT(DISTINCT c.id) FROM categories c\n"
            + "        INNER JOIN word_category_cross_refs wc ON c.id = wc.categoryId\n"
            + "        INNER JOIN user_progress up ON wc.wordId = up.wordId\n"
            + "        WHERE up.totalAttempts > 0\n"
            + "    ";
    return FlowUtil.createFlow(__db, false, new String[] {"categories", "word_category_cross_refs",
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
  public Object categoryExistsByName(final String nameEnglish,
      final Continuation<? super Boolean> $completion) {
    final String _sql = "SELECT EXISTS(SELECT 1 FROM categories WHERE nameEnglish = ? LIMIT 1)";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (nameEnglish == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, nameEnglish);
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
  public Object getNextOrderIndex(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT MAX(orderIndex) + 1 FROM categories";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
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
    }, $completion);
  }

  @Override
  public Object isWordInCategory(final long wordId, final long categoryId,
      final Continuation<? super Boolean> $completion) {
    final String _sql = "SELECT EXISTS(SELECT 1 FROM word_category_cross_refs WHERE wordId = ? AND categoryId = ? LIMIT 1)";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, wordId);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, categoryId);
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
  public Object removeWordFromCategoryById(final long wordId, final long categoryId,
      final Continuation<? super Unit> $completion) {
    final String _sql = "DELETE FROM word_category_cross_refs WHERE wordId = ? AND categoryId = ?";
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, wordId);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, categoryId);
        _stmt.step();
        return Unit.INSTANCE;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object removeAllWordsFromCategory(final long categoryId,
      final Continuation<? super Unit> $completion) {
    final String _sql = "DELETE FROM word_category_cross_refs WHERE categoryId = ?";
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, categoryId);
        _stmt.step();
        return Unit.INSTANCE;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object updateCategoryOrder(final long categoryId, final int newIndex,
      final Continuation<? super Unit> $completion) {
    final String _sql = "UPDATE categories SET orderIndex = ? WHERE id = ?";
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, newIndex);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, categoryId);
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

  private void __fetchRelationshipwordsAscomBhashasetuAppDataModelWord(
      @NonNull final SQLiteConnection _connection,
      @NonNull final LongSparseArray<ArrayList<Word>> _map) {
    if (_map.isEmpty()) {
      return;
    }
    if (_map.size() > 999) {
      RelationUtil.recursiveFetchLongSparseArray(_map, true, (_tmpMap) -> {
        __fetchRelationshipwordsAscomBhashasetuAppDataModelWord(_connection, _tmpMap);
        return Unit.INSTANCE;
      });
      return;
    }
    final StringBuilder _stringBuilder = new StringBuilder();
    _stringBuilder.append("SELECT `words`.`id` AS `id`,`words`.`englishText` AS `englishText`,`words`.`hindiText` AS `hindiText`,`words`.`englishPronunciation` AS `englishPronunciation`,`words`.`hindiPronunciation` AS `hindiPronunciation`,`words`.`englishExample` AS `englishExample`,`words`.`hindiExample` AS `hindiExample`,`words`.`difficulty` AS `difficulty`,`words`.`partOfSpeech` AS `partOfSpeech`,`words`.`isFavorite` AS `isFavorite`,`words`.`englishAudioPath` AS `englishAudioPath`,`words`.`hindiAudioPath` AS `hindiAudioPath`,`words`.`createdAt` AS `createdAt`,`words`.`lastModifiedAt` AS `lastModifiedAt`,`words`.`lastReviewedAt` AS `lastReviewedAt`,`words`.`nextReviewDate` AS `nextReviewDate`,_junction.`categoryId` FROM `word_category_cross_refs` AS _junction INNER JOIN `words` ON (_junction.`wordId` = `words`.`id`) WHERE _junction.`categoryId` IN (");
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
      // _junction.categoryId;
      final int _itemKeyIndex = 16;
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _columnIndexOfId = 0;
      final int _columnIndexOfEnglishText = 1;
      final int _columnIndexOfHindiText = 2;
      final int _columnIndexOfEnglishPronunciation = 3;
      final int _columnIndexOfHindiPronunciation = 4;
      final int _columnIndexOfEnglishExample = 5;
      final int _columnIndexOfHindiExample = 6;
      final int _columnIndexOfDifficulty = 7;
      final int _columnIndexOfPartOfSpeech = 8;
      final int _columnIndexOfIsFavorite = 9;
      final int _columnIndexOfEnglishAudioPath = 10;
      final int _columnIndexOfHindiAudioPath = 11;
      final int _columnIndexOfCreatedAt = 12;
      final int _columnIndexOfLastModifiedAt = 13;
      final int _columnIndexOfLastReviewedAt = 14;
      final int _columnIndexOfNextReviewDate = 15;
      while (_stmt.step()) {
        final long _tmpKey;
        _tmpKey = _stmt.getLong(_itemKeyIndex);
        final ArrayList<Word> _tmpRelation = _map.get(_tmpKey);
        if (_tmpRelation != null) {
          final Word _item_1;
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
          _item_1 = new Word(_tmpId,_tmpEnglishText,_tmpHindiText,_tmpEnglishPronunciation,_tmpHindiPronunciation,_tmpEnglishExample,_tmpHindiExample,_tmpDifficulty,_tmpPartOfSpeech,_tmpIsFavorite,_tmpEnglishAudioPath,_tmpHindiAudioPath,_tmpCreatedAt,_tmpLastModifiedAt,_tmpLastReviewedAt,_tmpNextReviewDate);
          _tmpRelation.add(_item_1);
        }
      }
    } finally {
      _stmt.close();
    }
  }
}
