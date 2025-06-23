package com.bhashasetu.app.database;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeleteOrUpdateAdapter;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteStatement;
import com.bhashasetu.app.model.Lesson;
import com.bhashasetu.app.model.LessonWord;
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
public final class LessonDao_Impl implements LessonDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<Lesson> __insertAdapterOfLesson;

  private final EntityInsertAdapter<LessonWord> __insertAdapterOfLessonWord;

  private final EntityDeleteOrUpdateAdapter<Lesson> __deleteAdapterOfLesson;

  private final EntityDeleteOrUpdateAdapter<Lesson> __updateAdapterOfLesson;

  public LessonDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfLesson = new EntityInsertAdapter<Lesson>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `lessons` (`id`,`title`,`titleHindi`,`description`,`descriptionHindi`,`category`,`level`,`orderInCategory`,`content`,`contentHindi`,`imageUrl`,`hasCompleted`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final Lesson entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getTitle());
        }
        if (entity.getTitleHindi() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getTitleHindi());
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
        if (entity.getLevel() == null) {
          statement.bindNull(7);
        } else {
          statement.bindText(7, entity.getLevel());
        }
        statement.bindLong(8, entity.getOrderInCategory());
        if (entity.getContent() == null) {
          statement.bindNull(9);
        } else {
          statement.bindText(9, entity.getContent());
        }
        if (entity.getContentHindi() == null) {
          statement.bindNull(10);
        } else {
          statement.bindText(10, entity.getContentHindi());
        }
        if (entity.getImageUrl() == null) {
          statement.bindNull(11);
        } else {
          statement.bindText(11, entity.getImageUrl());
        }
        final int _tmp = entity.isHasCompleted() ? 1 : 0;
        statement.bindLong(12, _tmp);
      }
    };
    this.__insertAdapterOfLessonWord = new EntityInsertAdapter<LessonWord>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `lesson_words` (`lessonId`,`wordId`,`order`,`notes`) VALUES (?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final LessonWord entity) {
        statement.bindLong(1, entity.getLessonId());
        statement.bindLong(2, entity.getWordId());
        statement.bindLong(3, entity.getOrder());
        if (entity.getNotes() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getNotes());
        }
      }
    };
    this.__deleteAdapterOfLesson = new EntityDeleteOrUpdateAdapter<Lesson>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `lessons` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final Lesson entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfLesson = new EntityDeleteOrUpdateAdapter<Lesson>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `lessons` SET `id` = ?,`title` = ?,`titleHindi` = ?,`description` = ?,`descriptionHindi` = ?,`category` = ?,`level` = ?,`orderInCategory` = ?,`content` = ?,`contentHindi` = ?,`imageUrl` = ?,`hasCompleted` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final Lesson entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getTitle());
        }
        if (entity.getTitleHindi() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getTitleHindi());
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
        if (entity.getLevel() == null) {
          statement.bindNull(7);
        } else {
          statement.bindText(7, entity.getLevel());
        }
        statement.bindLong(8, entity.getOrderInCategory());
        if (entity.getContent() == null) {
          statement.bindNull(9);
        } else {
          statement.bindText(9, entity.getContent());
        }
        if (entity.getContentHindi() == null) {
          statement.bindNull(10);
        } else {
          statement.bindText(10, entity.getContentHindi());
        }
        if (entity.getImageUrl() == null) {
          statement.bindNull(11);
        } else {
          statement.bindText(11, entity.getImageUrl());
        }
        final int _tmp = entity.isHasCompleted() ? 1 : 0;
        statement.bindLong(12, _tmp);
        statement.bindLong(13, entity.getId());
      }
    };
  }

  @Override
  public long insert(final Lesson lesson) {
    return DBUtil.performBlocking(__db, false, true, (_connection) -> {
      return __insertAdapterOfLesson.insertAndReturnId(_connection, lesson);
    });
  }

  @Override
  public void insertLessonWord(final LessonWord lessonWord) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __insertAdapterOfLessonWord.insert(_connection, lessonWord);
      return null;
    });
  }

  @Override
  public void delete(final Lesson lesson) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __deleteAdapterOfLesson.handle(_connection, lesson);
      return null;
    });
  }

  @Override
  public void update(final Lesson lesson) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __updateAdapterOfLesson.handle(_connection, lesson);
      return null;
    });
  }

  @Override
  public LiveData<List<Lesson>> getAllLessons() {
    final String _sql = "SELECT * FROM lessons ORDER BY category, orderInCategory ASC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"lessons"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "title");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfLevel = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "level");
        final int _columnIndexOfOrderInCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "orderInCategory");
        final int _columnIndexOfContent = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "content");
        final int _columnIndexOfContentHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "contentHindi");
        final int _columnIndexOfImageUrl = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "imageUrl");
        final int _columnIndexOfHasCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hasCompleted");
        final List<Lesson> _result = new ArrayList<Lesson>();
        while (_stmt.step()) {
          final Lesson _item;
          final String _tmpTitle;
          if (_stmt.isNull(_columnIndexOfTitle)) {
            _tmpTitle = null;
          } else {
            _tmpTitle = _stmt.getText(_columnIndexOfTitle);
          }
          final String _tmpTitleHindi;
          if (_stmt.isNull(_columnIndexOfTitleHindi)) {
            _tmpTitleHindi = null;
          } else {
            _tmpTitleHindi = _stmt.getText(_columnIndexOfTitleHindi);
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
          final String _tmpLevel;
          if (_stmt.isNull(_columnIndexOfLevel)) {
            _tmpLevel = null;
          } else {
            _tmpLevel = _stmt.getText(_columnIndexOfLevel);
          }
          final int _tmpOrderInCategory;
          _tmpOrderInCategory = (int) (_stmt.getLong(_columnIndexOfOrderInCategory));
          final String _tmpContent;
          if (_stmt.isNull(_columnIndexOfContent)) {
            _tmpContent = null;
          } else {
            _tmpContent = _stmt.getText(_columnIndexOfContent);
          }
          final String _tmpContentHindi;
          if (_stmt.isNull(_columnIndexOfContentHindi)) {
            _tmpContentHindi = null;
          } else {
            _tmpContentHindi = _stmt.getText(_columnIndexOfContentHindi);
          }
          final String _tmpImageUrl;
          if (_stmt.isNull(_columnIndexOfImageUrl)) {
            _tmpImageUrl = null;
          } else {
            _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl);
          }
          _item = new Lesson(_tmpTitle,_tmpTitleHindi,_tmpDescription,_tmpDescriptionHindi,_tmpCategory,_tmpLevel,_tmpOrderInCategory,_tmpContent,_tmpContentHindi,_tmpImageUrl);
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final boolean _tmpHasCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfHasCompleted));
          _tmpHasCompleted = _tmp != 0;
          _item.setHasCompleted(_tmpHasCompleted);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Lesson>> getLessonsByCategory(final String category) {
    final String _sql = "SELECT * FROM lessons WHERE category = ? ORDER BY orderInCategory ASC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"lessons"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (category == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, category);
        }
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "title");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfLevel = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "level");
        final int _columnIndexOfOrderInCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "orderInCategory");
        final int _columnIndexOfContent = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "content");
        final int _columnIndexOfContentHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "contentHindi");
        final int _columnIndexOfImageUrl = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "imageUrl");
        final int _columnIndexOfHasCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hasCompleted");
        final List<Lesson> _result = new ArrayList<Lesson>();
        while (_stmt.step()) {
          final Lesson _item;
          final String _tmpTitle;
          if (_stmt.isNull(_columnIndexOfTitle)) {
            _tmpTitle = null;
          } else {
            _tmpTitle = _stmt.getText(_columnIndexOfTitle);
          }
          final String _tmpTitleHindi;
          if (_stmt.isNull(_columnIndexOfTitleHindi)) {
            _tmpTitleHindi = null;
          } else {
            _tmpTitleHindi = _stmt.getText(_columnIndexOfTitleHindi);
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
          final String _tmpLevel;
          if (_stmt.isNull(_columnIndexOfLevel)) {
            _tmpLevel = null;
          } else {
            _tmpLevel = _stmt.getText(_columnIndexOfLevel);
          }
          final int _tmpOrderInCategory;
          _tmpOrderInCategory = (int) (_stmt.getLong(_columnIndexOfOrderInCategory));
          final String _tmpContent;
          if (_stmt.isNull(_columnIndexOfContent)) {
            _tmpContent = null;
          } else {
            _tmpContent = _stmt.getText(_columnIndexOfContent);
          }
          final String _tmpContentHindi;
          if (_stmt.isNull(_columnIndexOfContentHindi)) {
            _tmpContentHindi = null;
          } else {
            _tmpContentHindi = _stmt.getText(_columnIndexOfContentHindi);
          }
          final String _tmpImageUrl;
          if (_stmt.isNull(_columnIndexOfImageUrl)) {
            _tmpImageUrl = null;
          } else {
            _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl);
          }
          _item = new Lesson(_tmpTitle,_tmpTitleHindi,_tmpDescription,_tmpDescriptionHindi,_tmpCategory,_tmpLevel,_tmpOrderInCategory,_tmpContent,_tmpContentHindi,_tmpImageUrl);
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final boolean _tmpHasCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfHasCompleted));
          _tmpHasCompleted = _tmp != 0;
          _item.setHasCompleted(_tmpHasCompleted);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Lesson>> getLessonsByLevel(final String level) {
    final String _sql = "SELECT * FROM lessons WHERE level = ? ORDER BY category, orderInCategory ASC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"lessons"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (level == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, level);
        }
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "title");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfLevel = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "level");
        final int _columnIndexOfOrderInCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "orderInCategory");
        final int _columnIndexOfContent = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "content");
        final int _columnIndexOfContentHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "contentHindi");
        final int _columnIndexOfImageUrl = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "imageUrl");
        final int _columnIndexOfHasCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hasCompleted");
        final List<Lesson> _result = new ArrayList<Lesson>();
        while (_stmt.step()) {
          final Lesson _item;
          final String _tmpTitle;
          if (_stmt.isNull(_columnIndexOfTitle)) {
            _tmpTitle = null;
          } else {
            _tmpTitle = _stmt.getText(_columnIndexOfTitle);
          }
          final String _tmpTitleHindi;
          if (_stmt.isNull(_columnIndexOfTitleHindi)) {
            _tmpTitleHindi = null;
          } else {
            _tmpTitleHindi = _stmt.getText(_columnIndexOfTitleHindi);
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
          final String _tmpLevel;
          if (_stmt.isNull(_columnIndexOfLevel)) {
            _tmpLevel = null;
          } else {
            _tmpLevel = _stmt.getText(_columnIndexOfLevel);
          }
          final int _tmpOrderInCategory;
          _tmpOrderInCategory = (int) (_stmt.getLong(_columnIndexOfOrderInCategory));
          final String _tmpContent;
          if (_stmt.isNull(_columnIndexOfContent)) {
            _tmpContent = null;
          } else {
            _tmpContent = _stmt.getText(_columnIndexOfContent);
          }
          final String _tmpContentHindi;
          if (_stmt.isNull(_columnIndexOfContentHindi)) {
            _tmpContentHindi = null;
          } else {
            _tmpContentHindi = _stmt.getText(_columnIndexOfContentHindi);
          }
          final String _tmpImageUrl;
          if (_stmt.isNull(_columnIndexOfImageUrl)) {
            _tmpImageUrl = null;
          } else {
            _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl);
          }
          _item = new Lesson(_tmpTitle,_tmpTitleHindi,_tmpDescription,_tmpDescriptionHindi,_tmpCategory,_tmpLevel,_tmpOrderInCategory,_tmpContent,_tmpContentHindi,_tmpImageUrl);
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final boolean _tmpHasCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfHasCompleted));
          _tmpHasCompleted = _tmp != 0;
          _item.setHasCompleted(_tmpHasCompleted);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<Lesson> getLessonById(final int id) {
    final String _sql = "SELECT * FROM lessons WHERE id = ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"lessons"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "title");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfLevel = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "level");
        final int _columnIndexOfOrderInCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "orderInCategory");
        final int _columnIndexOfContent = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "content");
        final int _columnIndexOfContentHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "contentHindi");
        final int _columnIndexOfImageUrl = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "imageUrl");
        final int _columnIndexOfHasCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hasCompleted");
        final Lesson _result;
        if (_stmt.step()) {
          final String _tmpTitle;
          if (_stmt.isNull(_columnIndexOfTitle)) {
            _tmpTitle = null;
          } else {
            _tmpTitle = _stmt.getText(_columnIndexOfTitle);
          }
          final String _tmpTitleHindi;
          if (_stmt.isNull(_columnIndexOfTitleHindi)) {
            _tmpTitleHindi = null;
          } else {
            _tmpTitleHindi = _stmt.getText(_columnIndexOfTitleHindi);
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
          final String _tmpLevel;
          if (_stmt.isNull(_columnIndexOfLevel)) {
            _tmpLevel = null;
          } else {
            _tmpLevel = _stmt.getText(_columnIndexOfLevel);
          }
          final int _tmpOrderInCategory;
          _tmpOrderInCategory = (int) (_stmt.getLong(_columnIndexOfOrderInCategory));
          final String _tmpContent;
          if (_stmt.isNull(_columnIndexOfContent)) {
            _tmpContent = null;
          } else {
            _tmpContent = _stmt.getText(_columnIndexOfContent);
          }
          final String _tmpContentHindi;
          if (_stmt.isNull(_columnIndexOfContentHindi)) {
            _tmpContentHindi = null;
          } else {
            _tmpContentHindi = _stmt.getText(_columnIndexOfContentHindi);
          }
          final String _tmpImageUrl;
          if (_stmt.isNull(_columnIndexOfImageUrl)) {
            _tmpImageUrl = null;
          } else {
            _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl);
          }
          _result = new Lesson(_tmpTitle,_tmpTitleHindi,_tmpDescription,_tmpDescriptionHindi,_tmpCategory,_tmpLevel,_tmpOrderInCategory,_tmpContent,_tmpContentHindi,_tmpImageUrl);
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _result.setId(_tmpId);
          final boolean _tmpHasCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfHasCompleted));
          _tmpHasCompleted = _tmp != 0;
          _result.setHasCompleted(_tmpHasCompleted);
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
  public LiveData<Lesson> getNextIncompleteLesson() {
    final String _sql = "SELECT * FROM lessons WHERE hasCompleted = 0 ORDER BY level, orderInCategory ASC LIMIT 1";
    return __db.getInvalidationTracker().createLiveData(new String[] {"lessons"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "title");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfLevel = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "level");
        final int _columnIndexOfOrderInCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "orderInCategory");
        final int _columnIndexOfContent = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "content");
        final int _columnIndexOfContentHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "contentHindi");
        final int _columnIndexOfImageUrl = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "imageUrl");
        final int _columnIndexOfHasCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hasCompleted");
        final Lesson _result;
        if (_stmt.step()) {
          final String _tmpTitle;
          if (_stmt.isNull(_columnIndexOfTitle)) {
            _tmpTitle = null;
          } else {
            _tmpTitle = _stmt.getText(_columnIndexOfTitle);
          }
          final String _tmpTitleHindi;
          if (_stmt.isNull(_columnIndexOfTitleHindi)) {
            _tmpTitleHindi = null;
          } else {
            _tmpTitleHindi = _stmt.getText(_columnIndexOfTitleHindi);
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
          final String _tmpLevel;
          if (_stmt.isNull(_columnIndexOfLevel)) {
            _tmpLevel = null;
          } else {
            _tmpLevel = _stmt.getText(_columnIndexOfLevel);
          }
          final int _tmpOrderInCategory;
          _tmpOrderInCategory = (int) (_stmt.getLong(_columnIndexOfOrderInCategory));
          final String _tmpContent;
          if (_stmt.isNull(_columnIndexOfContent)) {
            _tmpContent = null;
          } else {
            _tmpContent = _stmt.getText(_columnIndexOfContent);
          }
          final String _tmpContentHindi;
          if (_stmt.isNull(_columnIndexOfContentHindi)) {
            _tmpContentHindi = null;
          } else {
            _tmpContentHindi = _stmt.getText(_columnIndexOfContentHindi);
          }
          final String _tmpImageUrl;
          if (_stmt.isNull(_columnIndexOfImageUrl)) {
            _tmpImageUrl = null;
          } else {
            _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl);
          }
          _result = new Lesson(_tmpTitle,_tmpTitleHindi,_tmpDescription,_tmpDescriptionHindi,_tmpCategory,_tmpLevel,_tmpOrderInCategory,_tmpContent,_tmpContentHindi,_tmpImageUrl);
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _result.setId(_tmpId);
          final boolean _tmpHasCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfHasCompleted));
          _tmpHasCompleted = _tmp != 0;
          _result.setHasCompleted(_tmpHasCompleted);
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
    final String _sql = "SELECT DISTINCT category FROM lessons ORDER BY category ASC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"lessons"}, false, (_connection) -> {
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
  public LiveData<List<Lesson>> getLessonsContainingWord(final int wordId) {
    final String _sql = "SELECT * FROM lessons WHERE id IN (SELECT lessonId FROM lesson_words WHERE wordId = ?)";
    return __db.getInvalidationTracker().createLiveData(new String[] {"lessons",
        "lesson_words"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, wordId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "title");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfLevel = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "level");
        final int _columnIndexOfOrderInCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "orderInCategory");
        final int _columnIndexOfContent = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "content");
        final int _columnIndexOfContentHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "contentHindi");
        final int _columnIndexOfImageUrl = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "imageUrl");
        final int _columnIndexOfHasCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hasCompleted");
        final List<Lesson> _result = new ArrayList<Lesson>();
        while (_stmt.step()) {
          final Lesson _item;
          final String _tmpTitle;
          if (_stmt.isNull(_columnIndexOfTitle)) {
            _tmpTitle = null;
          } else {
            _tmpTitle = _stmt.getText(_columnIndexOfTitle);
          }
          final String _tmpTitleHindi;
          if (_stmt.isNull(_columnIndexOfTitleHindi)) {
            _tmpTitleHindi = null;
          } else {
            _tmpTitleHindi = _stmt.getText(_columnIndexOfTitleHindi);
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
          final String _tmpLevel;
          if (_stmt.isNull(_columnIndexOfLevel)) {
            _tmpLevel = null;
          } else {
            _tmpLevel = _stmt.getText(_columnIndexOfLevel);
          }
          final int _tmpOrderInCategory;
          _tmpOrderInCategory = (int) (_stmt.getLong(_columnIndexOfOrderInCategory));
          final String _tmpContent;
          if (_stmt.isNull(_columnIndexOfContent)) {
            _tmpContent = null;
          } else {
            _tmpContent = _stmt.getText(_columnIndexOfContent);
          }
          final String _tmpContentHindi;
          if (_stmt.isNull(_columnIndexOfContentHindi)) {
            _tmpContentHindi = null;
          } else {
            _tmpContentHindi = _stmt.getText(_columnIndexOfContentHindi);
          }
          final String _tmpImageUrl;
          if (_stmt.isNull(_columnIndexOfImageUrl)) {
            _tmpImageUrl = null;
          } else {
            _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl);
          }
          _item = new Lesson(_tmpTitle,_tmpTitleHindi,_tmpDescription,_tmpDescriptionHindi,_tmpCategory,_tmpLevel,_tmpOrderInCategory,_tmpContent,_tmpContentHindi,_tmpImageUrl);
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final boolean _tmpHasCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfHasCompleted));
          _tmpHasCompleted = _tmp != 0;
          _item.setHasCompleted(_tmpHasCompleted);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public boolean lessonContainsWord(final int lessonId, final int wordId) {
    final String _sql = "SELECT EXISTS(SELECT 1 FROM lesson_words WHERE lessonId = ? AND wordId = ?)";
    return DBUtil.performBlocking(__db, true, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, lessonId);
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
  public void removeLessonWord(final int lessonId, final int wordId) {
    final String _sql = "DELETE FROM lesson_words WHERE lessonId = ? AND wordId = ?";
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, lessonId);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, wordId);
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
