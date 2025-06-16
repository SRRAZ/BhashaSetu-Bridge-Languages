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
import com.bhashasetu.app.data.model.Lesson;
import com.bhashasetu.app.data.model.LessonWord;
import com.bhashasetu.app.data.model.Word;
import com.bhashasetu.app.data.relation.LessonWithWords;
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
public final class LessonDao_Impl implements LessonDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<Lesson> __insertAdapterOfLesson;

  private final EntityInsertAdapter<LessonWord> __insertAdapterOfLessonWord;

  private final EntityDeleteOrUpdateAdapter<Lesson> __deleteAdapterOfLesson;

  private final EntityDeleteOrUpdateAdapter<LessonWord> __deleteAdapterOfLessonWord;

  private final EntityDeleteOrUpdateAdapter<Lesson> __updateAdapterOfLesson;

  public LessonDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfLesson = new EntityInsertAdapter<Lesson>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `lessons` (`id`,`titleEnglish`,`titleHindi`,`descriptionEnglish`,`descriptionHindi`,`contentEnglish`,`contentHindi`,`categoryId`,`difficulty`,`orderInCategory`,`estimatedDurationMinutes`,`isCompleted`,`completedAt`,`lastAccessedAt`,`createdAt`,`lastModifiedAt`,`isSystemLesson`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, @NonNull final Lesson entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTitleEnglish() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getTitleEnglish());
        }
        if (entity.getTitleHindi() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getTitleHindi());
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
        if (entity.getContentEnglish() == null) {
          statement.bindNull(6);
        } else {
          statement.bindText(6, entity.getContentEnglish());
        }
        if (entity.getContentHindi() == null) {
          statement.bindNull(7);
        } else {
          statement.bindText(7, entity.getContentHindi());
        }
        if (entity.getCategoryId() == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, entity.getCategoryId());
        }
        statement.bindLong(9, entity.getDifficulty());
        statement.bindLong(10, entity.getOrderInCategory());
        statement.bindLong(11, entity.getEstimatedDurationMinutes());
        final int _tmp = entity.isCompleted() ? 1 : 0;
        statement.bindLong(12, _tmp);
        if (entity.getCompletedAt() == null) {
          statement.bindNull(13);
        } else {
          statement.bindLong(13, entity.getCompletedAt());
        }
        if (entity.getLastAccessedAt() == null) {
          statement.bindNull(14);
        } else {
          statement.bindLong(14, entity.getLastAccessedAt());
        }
        statement.bindLong(15, entity.getCreatedAt());
        statement.bindLong(16, entity.getLastModifiedAt());
        final int _tmp_1 = entity.isSystemLesson() ? 1 : 0;
        statement.bindLong(17, _tmp_1);
      }
    };
    this.__insertAdapterOfLessonWord = new EntityInsertAdapter<LessonWord>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `lesson_words` (`lessonId`,`wordId`,`orderInLesson`,`isKeyword`,`notes`,`includeInQuiz`,`highlightInContent`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final LessonWord entity) {
        statement.bindLong(1, entity.getLessonId());
        statement.bindLong(2, entity.getWordId());
        statement.bindLong(3, entity.getOrderInLesson());
        final int _tmp = entity.isKeyword() ? 1 : 0;
        statement.bindLong(4, _tmp);
        if (entity.getNotes() == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, entity.getNotes());
        }
        final int _tmp_1 = entity.getIncludeInQuiz() ? 1 : 0;
        statement.bindLong(6, _tmp_1);
        final int _tmp_2 = entity.getHighlightInContent() ? 1 : 0;
        statement.bindLong(7, _tmp_2);
      }
    };
    this.__deleteAdapterOfLesson = new EntityDeleteOrUpdateAdapter<Lesson>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `lessons` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, @NonNull final Lesson entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__deleteAdapterOfLessonWord = new EntityDeleteOrUpdateAdapter<LessonWord>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `lesson_words` WHERE `lessonId` = ? AND `wordId` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final LessonWord entity) {
        statement.bindLong(1, entity.getLessonId());
        statement.bindLong(2, entity.getWordId());
      }
    };
    this.__updateAdapterOfLesson = new EntityDeleteOrUpdateAdapter<Lesson>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `lessons` SET `id` = ?,`titleEnglish` = ?,`titleHindi` = ?,`descriptionEnglish` = ?,`descriptionHindi` = ?,`contentEnglish` = ?,`contentHindi` = ?,`categoryId` = ?,`difficulty` = ?,`orderInCategory` = ?,`estimatedDurationMinutes` = ?,`isCompleted` = ?,`completedAt` = ?,`lastAccessedAt` = ?,`createdAt` = ?,`lastModifiedAt` = ?,`isSystemLesson` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, @NonNull final Lesson entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTitleEnglish() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getTitleEnglish());
        }
        if (entity.getTitleHindi() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getTitleHindi());
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
        if (entity.getContentEnglish() == null) {
          statement.bindNull(6);
        } else {
          statement.bindText(6, entity.getContentEnglish());
        }
        if (entity.getContentHindi() == null) {
          statement.bindNull(7);
        } else {
          statement.bindText(7, entity.getContentHindi());
        }
        if (entity.getCategoryId() == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, entity.getCategoryId());
        }
        statement.bindLong(9, entity.getDifficulty());
        statement.bindLong(10, entity.getOrderInCategory());
        statement.bindLong(11, entity.getEstimatedDurationMinutes());
        final int _tmp = entity.isCompleted() ? 1 : 0;
        statement.bindLong(12, _tmp);
        if (entity.getCompletedAt() == null) {
          statement.bindNull(13);
        } else {
          statement.bindLong(13, entity.getCompletedAt());
        }
        if (entity.getLastAccessedAt() == null) {
          statement.bindNull(14);
        } else {
          statement.bindLong(14, entity.getLastAccessedAt());
        }
        statement.bindLong(15, entity.getCreatedAt());
        statement.bindLong(16, entity.getLastModifiedAt());
        final int _tmp_1 = entity.isSystemLesson() ? 1 : 0;
        statement.bindLong(17, _tmp_1);
        statement.bindLong(18, entity.getId());
      }
    };
  }

  @Override
  public Object insert(final Lesson lesson, final Continuation<? super Long> $completion) {
    if (lesson == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      return __insertAdapterOfLesson.insertAndReturnId(_connection, lesson);
    }, $completion);
  }

  @Override
  public Object insertAll(final List<Lesson> lessons,
      final Continuation<? super List<Long>> $completion) {
    if (lessons == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      return __insertAdapterOfLesson.insertAndReturnIdsList(_connection, lessons);
    }, $completion);
  }

  @Override
  public Object addWordToLesson(final LessonWord lessonWord,
      final Continuation<? super Unit> $completion) {
    if (lessonWord == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __insertAdapterOfLessonWord.insert(_connection, lessonWord);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Object addWordsToLesson(final List<LessonWord> lessonWords,
      final Continuation<? super Unit> $completion) {
    if (lessonWords == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __insertAdapterOfLessonWord.insert(_connection, lessonWords);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Object delete(final Lesson lesson, final Continuation<? super Unit> $completion) {
    if (lesson == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __deleteAdapterOfLesson.handle(_connection, lesson);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Object removeWordFromLesson(final LessonWord lessonWord,
      final Continuation<? super Unit> $completion) {
    if (lessonWord == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __deleteAdapterOfLessonWord.handle(_connection, lessonWord);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Object update(final Lesson lesson, final Continuation<? super Unit> $completion) {
    if (lesson == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __updateAdapterOfLesson.handle(_connection, lesson);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Object getLessonById(final long lessonId, final Continuation<? super Lesson> $completion) {
    final String _sql = "SELECT * FROM lessons WHERE id = ?";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, lessonId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitleEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleEnglish");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfDescriptionEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionEnglish");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfContentEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "contentEnglish");
        final int _columnIndexOfContentHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "contentHindi");
        final int _columnIndexOfCategoryId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "categoryId");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfOrderInCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "orderInCategory");
        final int _columnIndexOfEstimatedDurationMinutes = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "estimatedDurationMinutes");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfCompletedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAt");
        final int _columnIndexOfLastAccessedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAccessedAt");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _columnIndexOfLastModifiedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastModifiedAt");
        final int _columnIndexOfIsSystemLesson = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isSystemLesson");
        final Lesson _result;
        if (_stmt.step()) {
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final String _tmpTitleEnglish;
          if (_stmt.isNull(_columnIndexOfTitleEnglish)) {
            _tmpTitleEnglish = null;
          } else {
            _tmpTitleEnglish = _stmt.getText(_columnIndexOfTitleEnglish);
          }
          final String _tmpTitleHindi;
          if (_stmt.isNull(_columnIndexOfTitleHindi)) {
            _tmpTitleHindi = null;
          } else {
            _tmpTitleHindi = _stmt.getText(_columnIndexOfTitleHindi);
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
          final String _tmpContentEnglish;
          if (_stmt.isNull(_columnIndexOfContentEnglish)) {
            _tmpContentEnglish = null;
          } else {
            _tmpContentEnglish = _stmt.getText(_columnIndexOfContentEnglish);
          }
          final String _tmpContentHindi;
          if (_stmt.isNull(_columnIndexOfContentHindi)) {
            _tmpContentHindi = null;
          } else {
            _tmpContentHindi = _stmt.getText(_columnIndexOfContentHindi);
          }
          final Long _tmpCategoryId;
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null;
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final int _tmpOrderInCategory;
          _tmpOrderInCategory = (int) (_stmt.getLong(_columnIndexOfOrderInCategory));
          final int _tmpEstimatedDurationMinutes;
          _tmpEstimatedDurationMinutes = (int) (_stmt.getLong(_columnIndexOfEstimatedDurationMinutes));
          final boolean _tmpIsCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp != 0;
          final Long _tmpCompletedAt;
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmpCompletedAt = null;
          } else {
            _tmpCompletedAt = _stmt.getLong(_columnIndexOfCompletedAt);
          }
          final Long _tmpLastAccessedAt;
          if (_stmt.isNull(_columnIndexOfLastAccessedAt)) {
            _tmpLastAccessedAt = null;
          } else {
            _tmpLastAccessedAt = _stmt.getLong(_columnIndexOfLastAccessedAt);
          }
          final long _tmpCreatedAt;
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt);
          final long _tmpLastModifiedAt;
          _tmpLastModifiedAt = _stmt.getLong(_columnIndexOfLastModifiedAt);
          final boolean _tmpIsSystemLesson;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsSystemLesson));
          _tmpIsSystemLesson = _tmp_1 != 0;
          _result = new Lesson(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpDescriptionEnglish,_tmpDescriptionHindi,_tmpContentEnglish,_tmpContentHindi,_tmpCategoryId,_tmpDifficulty,_tmpOrderInCategory,_tmpEstimatedDurationMinutes,_tmpIsCompleted,_tmpCompletedAt,_tmpLastAccessedAt,_tmpCreatedAt,_tmpLastModifiedAt,_tmpIsSystemLesson);
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
  public LiveData<Lesson> getLessonByIdLiveData(final long lessonId) {
    final String _sql = "SELECT * FROM lessons WHERE id = ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"lessons"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, lessonId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitleEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleEnglish");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfDescriptionEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionEnglish");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfContentEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "contentEnglish");
        final int _columnIndexOfContentHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "contentHindi");
        final int _columnIndexOfCategoryId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "categoryId");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfOrderInCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "orderInCategory");
        final int _columnIndexOfEstimatedDurationMinutes = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "estimatedDurationMinutes");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfCompletedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAt");
        final int _columnIndexOfLastAccessedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAccessedAt");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _columnIndexOfLastModifiedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastModifiedAt");
        final int _columnIndexOfIsSystemLesson = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isSystemLesson");
        final Lesson _result;
        if (_stmt.step()) {
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final String _tmpTitleEnglish;
          if (_stmt.isNull(_columnIndexOfTitleEnglish)) {
            _tmpTitleEnglish = null;
          } else {
            _tmpTitleEnglish = _stmt.getText(_columnIndexOfTitleEnglish);
          }
          final String _tmpTitleHindi;
          if (_stmt.isNull(_columnIndexOfTitleHindi)) {
            _tmpTitleHindi = null;
          } else {
            _tmpTitleHindi = _stmt.getText(_columnIndexOfTitleHindi);
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
          final String _tmpContentEnglish;
          if (_stmt.isNull(_columnIndexOfContentEnglish)) {
            _tmpContentEnglish = null;
          } else {
            _tmpContentEnglish = _stmt.getText(_columnIndexOfContentEnglish);
          }
          final String _tmpContentHindi;
          if (_stmt.isNull(_columnIndexOfContentHindi)) {
            _tmpContentHindi = null;
          } else {
            _tmpContentHindi = _stmt.getText(_columnIndexOfContentHindi);
          }
          final Long _tmpCategoryId;
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null;
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final int _tmpOrderInCategory;
          _tmpOrderInCategory = (int) (_stmt.getLong(_columnIndexOfOrderInCategory));
          final int _tmpEstimatedDurationMinutes;
          _tmpEstimatedDurationMinutes = (int) (_stmt.getLong(_columnIndexOfEstimatedDurationMinutes));
          final boolean _tmpIsCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp != 0;
          final Long _tmpCompletedAt;
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmpCompletedAt = null;
          } else {
            _tmpCompletedAt = _stmt.getLong(_columnIndexOfCompletedAt);
          }
          final Long _tmpLastAccessedAt;
          if (_stmt.isNull(_columnIndexOfLastAccessedAt)) {
            _tmpLastAccessedAt = null;
          } else {
            _tmpLastAccessedAt = _stmt.getLong(_columnIndexOfLastAccessedAt);
          }
          final long _tmpCreatedAt;
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt);
          final long _tmpLastModifiedAt;
          _tmpLastModifiedAt = _stmt.getLong(_columnIndexOfLastModifiedAt);
          final boolean _tmpIsSystemLesson;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsSystemLesson));
          _tmpIsSystemLesson = _tmp_1 != 0;
          _result = new Lesson(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpDescriptionEnglish,_tmpDescriptionHindi,_tmpContentEnglish,_tmpContentHindi,_tmpCategoryId,_tmpDifficulty,_tmpOrderInCategory,_tmpEstimatedDurationMinutes,_tmpIsCompleted,_tmpCompletedAt,_tmpLastAccessedAt,_tmpCreatedAt,_tmpLastModifiedAt,_tmpIsSystemLesson);
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
  public Flow<Lesson> getLessonByIdFlow(final long lessonId) {
    final String _sql = "SELECT * FROM lessons WHERE id = ?";
    return FlowUtil.createFlow(__db, false, new String[] {"lessons"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, lessonId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitleEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleEnglish");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfDescriptionEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionEnglish");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfContentEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "contentEnglish");
        final int _columnIndexOfContentHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "contentHindi");
        final int _columnIndexOfCategoryId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "categoryId");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfOrderInCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "orderInCategory");
        final int _columnIndexOfEstimatedDurationMinutes = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "estimatedDurationMinutes");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfCompletedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAt");
        final int _columnIndexOfLastAccessedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAccessedAt");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _columnIndexOfLastModifiedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastModifiedAt");
        final int _columnIndexOfIsSystemLesson = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isSystemLesson");
        final Lesson _result;
        if (_stmt.step()) {
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final String _tmpTitleEnglish;
          if (_stmt.isNull(_columnIndexOfTitleEnglish)) {
            _tmpTitleEnglish = null;
          } else {
            _tmpTitleEnglish = _stmt.getText(_columnIndexOfTitleEnglish);
          }
          final String _tmpTitleHindi;
          if (_stmt.isNull(_columnIndexOfTitleHindi)) {
            _tmpTitleHindi = null;
          } else {
            _tmpTitleHindi = _stmt.getText(_columnIndexOfTitleHindi);
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
          final String _tmpContentEnglish;
          if (_stmt.isNull(_columnIndexOfContentEnglish)) {
            _tmpContentEnglish = null;
          } else {
            _tmpContentEnglish = _stmt.getText(_columnIndexOfContentEnglish);
          }
          final String _tmpContentHindi;
          if (_stmt.isNull(_columnIndexOfContentHindi)) {
            _tmpContentHindi = null;
          } else {
            _tmpContentHindi = _stmt.getText(_columnIndexOfContentHindi);
          }
          final Long _tmpCategoryId;
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null;
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final int _tmpOrderInCategory;
          _tmpOrderInCategory = (int) (_stmt.getLong(_columnIndexOfOrderInCategory));
          final int _tmpEstimatedDurationMinutes;
          _tmpEstimatedDurationMinutes = (int) (_stmt.getLong(_columnIndexOfEstimatedDurationMinutes));
          final boolean _tmpIsCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp != 0;
          final Long _tmpCompletedAt;
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmpCompletedAt = null;
          } else {
            _tmpCompletedAt = _stmt.getLong(_columnIndexOfCompletedAt);
          }
          final Long _tmpLastAccessedAt;
          if (_stmt.isNull(_columnIndexOfLastAccessedAt)) {
            _tmpLastAccessedAt = null;
          } else {
            _tmpLastAccessedAt = _stmt.getLong(_columnIndexOfLastAccessedAt);
          }
          final long _tmpCreatedAt;
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt);
          final long _tmpLastModifiedAt;
          _tmpLastModifiedAt = _stmt.getLong(_columnIndexOfLastModifiedAt);
          final boolean _tmpIsSystemLesson;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsSystemLesson));
          _tmpIsSystemLesson = _tmp_1 != 0;
          _result = new Lesson(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpDescriptionEnglish,_tmpDescriptionHindi,_tmpContentEnglish,_tmpContentHindi,_tmpCategoryId,_tmpDifficulty,_tmpOrderInCategory,_tmpEstimatedDurationMinutes,_tmpIsCompleted,_tmpCompletedAt,_tmpLastAccessedAt,_tmpCreatedAt,_tmpLastModifiedAt,_tmpIsSystemLesson);
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
  public Flow<List<Lesson>> getAllLessons() {
    final String _sql = "SELECT * FROM lessons ORDER BY difficulty ASC, orderInCategory ASC, titleEnglish ASC";
    return FlowUtil.createFlow(__db, false, new String[] {"lessons"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitleEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleEnglish");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfDescriptionEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionEnglish");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfContentEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "contentEnglish");
        final int _columnIndexOfContentHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "contentHindi");
        final int _columnIndexOfCategoryId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "categoryId");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfOrderInCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "orderInCategory");
        final int _columnIndexOfEstimatedDurationMinutes = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "estimatedDurationMinutes");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfCompletedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAt");
        final int _columnIndexOfLastAccessedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAccessedAt");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _columnIndexOfLastModifiedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastModifiedAt");
        final int _columnIndexOfIsSystemLesson = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isSystemLesson");
        final List<Lesson> _result = new ArrayList<Lesson>();
        while (_stmt.step()) {
          final Lesson _item;
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final String _tmpTitleEnglish;
          if (_stmt.isNull(_columnIndexOfTitleEnglish)) {
            _tmpTitleEnglish = null;
          } else {
            _tmpTitleEnglish = _stmt.getText(_columnIndexOfTitleEnglish);
          }
          final String _tmpTitleHindi;
          if (_stmt.isNull(_columnIndexOfTitleHindi)) {
            _tmpTitleHindi = null;
          } else {
            _tmpTitleHindi = _stmt.getText(_columnIndexOfTitleHindi);
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
          final String _tmpContentEnglish;
          if (_stmt.isNull(_columnIndexOfContentEnglish)) {
            _tmpContentEnglish = null;
          } else {
            _tmpContentEnglish = _stmt.getText(_columnIndexOfContentEnglish);
          }
          final String _tmpContentHindi;
          if (_stmt.isNull(_columnIndexOfContentHindi)) {
            _tmpContentHindi = null;
          } else {
            _tmpContentHindi = _stmt.getText(_columnIndexOfContentHindi);
          }
          final Long _tmpCategoryId;
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null;
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final int _tmpOrderInCategory;
          _tmpOrderInCategory = (int) (_stmt.getLong(_columnIndexOfOrderInCategory));
          final int _tmpEstimatedDurationMinutes;
          _tmpEstimatedDurationMinutes = (int) (_stmt.getLong(_columnIndexOfEstimatedDurationMinutes));
          final boolean _tmpIsCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp != 0;
          final Long _tmpCompletedAt;
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmpCompletedAt = null;
          } else {
            _tmpCompletedAt = _stmt.getLong(_columnIndexOfCompletedAt);
          }
          final Long _tmpLastAccessedAt;
          if (_stmt.isNull(_columnIndexOfLastAccessedAt)) {
            _tmpLastAccessedAt = null;
          } else {
            _tmpLastAccessedAt = _stmt.getLong(_columnIndexOfLastAccessedAt);
          }
          final long _tmpCreatedAt;
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt);
          final long _tmpLastModifiedAt;
          _tmpLastModifiedAt = _stmt.getLong(_columnIndexOfLastModifiedAt);
          final boolean _tmpIsSystemLesson;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsSystemLesson));
          _tmpIsSystemLesson = _tmp_1 != 0;
          _item = new Lesson(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpDescriptionEnglish,_tmpDescriptionHindi,_tmpContentEnglish,_tmpContentHindi,_tmpCategoryId,_tmpDifficulty,_tmpOrderInCategory,_tmpEstimatedDurationMinutes,_tmpIsCompleted,_tmpCompletedAt,_tmpLastAccessedAt,_tmpCreatedAt,_tmpLastModifiedAt,_tmpIsSystemLesson);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Lesson>> getAllLessonsLiveData() {
    final String _sql = "SELECT * FROM lessons ORDER BY difficulty ASC, orderInCategory ASC, titleEnglish ASC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"lessons"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitleEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleEnglish");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfDescriptionEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionEnglish");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfContentEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "contentEnglish");
        final int _columnIndexOfContentHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "contentHindi");
        final int _columnIndexOfCategoryId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "categoryId");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfOrderInCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "orderInCategory");
        final int _columnIndexOfEstimatedDurationMinutes = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "estimatedDurationMinutes");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfCompletedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAt");
        final int _columnIndexOfLastAccessedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAccessedAt");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _columnIndexOfLastModifiedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastModifiedAt");
        final int _columnIndexOfIsSystemLesson = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isSystemLesson");
        final List<Lesson> _result = new ArrayList<Lesson>();
        while (_stmt.step()) {
          final Lesson _item;
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final String _tmpTitleEnglish;
          if (_stmt.isNull(_columnIndexOfTitleEnglish)) {
            _tmpTitleEnglish = null;
          } else {
            _tmpTitleEnglish = _stmt.getText(_columnIndexOfTitleEnglish);
          }
          final String _tmpTitleHindi;
          if (_stmt.isNull(_columnIndexOfTitleHindi)) {
            _tmpTitleHindi = null;
          } else {
            _tmpTitleHindi = _stmt.getText(_columnIndexOfTitleHindi);
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
          final String _tmpContentEnglish;
          if (_stmt.isNull(_columnIndexOfContentEnglish)) {
            _tmpContentEnglish = null;
          } else {
            _tmpContentEnglish = _stmt.getText(_columnIndexOfContentEnglish);
          }
          final String _tmpContentHindi;
          if (_stmt.isNull(_columnIndexOfContentHindi)) {
            _tmpContentHindi = null;
          } else {
            _tmpContentHindi = _stmt.getText(_columnIndexOfContentHindi);
          }
          final Long _tmpCategoryId;
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null;
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final int _tmpOrderInCategory;
          _tmpOrderInCategory = (int) (_stmt.getLong(_columnIndexOfOrderInCategory));
          final int _tmpEstimatedDurationMinutes;
          _tmpEstimatedDurationMinutes = (int) (_stmt.getLong(_columnIndexOfEstimatedDurationMinutes));
          final boolean _tmpIsCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp != 0;
          final Long _tmpCompletedAt;
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmpCompletedAt = null;
          } else {
            _tmpCompletedAt = _stmt.getLong(_columnIndexOfCompletedAt);
          }
          final Long _tmpLastAccessedAt;
          if (_stmt.isNull(_columnIndexOfLastAccessedAt)) {
            _tmpLastAccessedAt = null;
          } else {
            _tmpLastAccessedAt = _stmt.getLong(_columnIndexOfLastAccessedAt);
          }
          final long _tmpCreatedAt;
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt);
          final long _tmpLastModifiedAt;
          _tmpLastModifiedAt = _stmt.getLong(_columnIndexOfLastModifiedAt);
          final boolean _tmpIsSystemLesson;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsSystemLesson));
          _tmpIsSystemLesson = _tmp_1 != 0;
          _item = new Lesson(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpDescriptionEnglish,_tmpDescriptionHindi,_tmpContentEnglish,_tmpContentHindi,_tmpCategoryId,_tmpDifficulty,_tmpOrderInCategory,_tmpEstimatedDurationMinutes,_tmpIsCompleted,_tmpCompletedAt,_tmpLastAccessedAt,_tmpCreatedAt,_tmpLastModifiedAt,_tmpIsSystemLesson);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Flow<List<Lesson>> getLessonsByCategory(final long categoryId) {
    final String _sql = "SELECT * FROM lessons WHERE categoryId = ? ORDER BY orderInCategory ASC, titleEnglish ASC";
    return FlowUtil.createFlow(__db, false, new String[] {"lessons"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, categoryId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitleEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleEnglish");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfDescriptionEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionEnglish");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfContentEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "contentEnglish");
        final int _columnIndexOfContentHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "contentHindi");
        final int _columnIndexOfCategoryId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "categoryId");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfOrderInCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "orderInCategory");
        final int _columnIndexOfEstimatedDurationMinutes = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "estimatedDurationMinutes");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfCompletedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAt");
        final int _columnIndexOfLastAccessedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAccessedAt");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _columnIndexOfLastModifiedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastModifiedAt");
        final int _columnIndexOfIsSystemLesson = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isSystemLesson");
        final List<Lesson> _result = new ArrayList<Lesson>();
        while (_stmt.step()) {
          final Lesson _item;
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final String _tmpTitleEnglish;
          if (_stmt.isNull(_columnIndexOfTitleEnglish)) {
            _tmpTitleEnglish = null;
          } else {
            _tmpTitleEnglish = _stmt.getText(_columnIndexOfTitleEnglish);
          }
          final String _tmpTitleHindi;
          if (_stmt.isNull(_columnIndexOfTitleHindi)) {
            _tmpTitleHindi = null;
          } else {
            _tmpTitleHindi = _stmt.getText(_columnIndexOfTitleHindi);
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
          final String _tmpContentEnglish;
          if (_stmt.isNull(_columnIndexOfContentEnglish)) {
            _tmpContentEnglish = null;
          } else {
            _tmpContentEnglish = _stmt.getText(_columnIndexOfContentEnglish);
          }
          final String _tmpContentHindi;
          if (_stmt.isNull(_columnIndexOfContentHindi)) {
            _tmpContentHindi = null;
          } else {
            _tmpContentHindi = _stmt.getText(_columnIndexOfContentHindi);
          }
          final Long _tmpCategoryId;
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null;
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final int _tmpOrderInCategory;
          _tmpOrderInCategory = (int) (_stmt.getLong(_columnIndexOfOrderInCategory));
          final int _tmpEstimatedDurationMinutes;
          _tmpEstimatedDurationMinutes = (int) (_stmt.getLong(_columnIndexOfEstimatedDurationMinutes));
          final boolean _tmpIsCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp != 0;
          final Long _tmpCompletedAt;
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmpCompletedAt = null;
          } else {
            _tmpCompletedAt = _stmt.getLong(_columnIndexOfCompletedAt);
          }
          final Long _tmpLastAccessedAt;
          if (_stmt.isNull(_columnIndexOfLastAccessedAt)) {
            _tmpLastAccessedAt = null;
          } else {
            _tmpLastAccessedAt = _stmt.getLong(_columnIndexOfLastAccessedAt);
          }
          final long _tmpCreatedAt;
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt);
          final long _tmpLastModifiedAt;
          _tmpLastModifiedAt = _stmt.getLong(_columnIndexOfLastModifiedAt);
          final boolean _tmpIsSystemLesson;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsSystemLesson));
          _tmpIsSystemLesson = _tmp_1 != 0;
          _item = new Lesson(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpDescriptionEnglish,_tmpDescriptionHindi,_tmpContentEnglish,_tmpContentHindi,_tmpCategoryId,_tmpDifficulty,_tmpOrderInCategory,_tmpEstimatedDurationMinutes,_tmpIsCompleted,_tmpCompletedAt,_tmpLastAccessedAt,_tmpCreatedAt,_tmpLastModifiedAt,_tmpIsSystemLesson);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Flow<List<Lesson>> getLessonsByDifficulty(final int difficulty) {
    final String _sql = "SELECT * FROM lessons WHERE difficulty = ? ORDER BY orderInCategory ASC, titleEnglish ASC";
    return FlowUtil.createFlow(__db, false, new String[] {"lessons"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, difficulty);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitleEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleEnglish");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfDescriptionEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionEnglish");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfContentEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "contentEnglish");
        final int _columnIndexOfContentHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "contentHindi");
        final int _columnIndexOfCategoryId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "categoryId");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfOrderInCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "orderInCategory");
        final int _columnIndexOfEstimatedDurationMinutes = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "estimatedDurationMinutes");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfCompletedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAt");
        final int _columnIndexOfLastAccessedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAccessedAt");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _columnIndexOfLastModifiedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastModifiedAt");
        final int _columnIndexOfIsSystemLesson = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isSystemLesson");
        final List<Lesson> _result = new ArrayList<Lesson>();
        while (_stmt.step()) {
          final Lesson _item;
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final String _tmpTitleEnglish;
          if (_stmt.isNull(_columnIndexOfTitleEnglish)) {
            _tmpTitleEnglish = null;
          } else {
            _tmpTitleEnglish = _stmt.getText(_columnIndexOfTitleEnglish);
          }
          final String _tmpTitleHindi;
          if (_stmt.isNull(_columnIndexOfTitleHindi)) {
            _tmpTitleHindi = null;
          } else {
            _tmpTitleHindi = _stmt.getText(_columnIndexOfTitleHindi);
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
          final String _tmpContentEnglish;
          if (_stmt.isNull(_columnIndexOfContentEnglish)) {
            _tmpContentEnglish = null;
          } else {
            _tmpContentEnglish = _stmt.getText(_columnIndexOfContentEnglish);
          }
          final String _tmpContentHindi;
          if (_stmt.isNull(_columnIndexOfContentHindi)) {
            _tmpContentHindi = null;
          } else {
            _tmpContentHindi = _stmt.getText(_columnIndexOfContentHindi);
          }
          final Long _tmpCategoryId;
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null;
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final int _tmpOrderInCategory;
          _tmpOrderInCategory = (int) (_stmt.getLong(_columnIndexOfOrderInCategory));
          final int _tmpEstimatedDurationMinutes;
          _tmpEstimatedDurationMinutes = (int) (_stmt.getLong(_columnIndexOfEstimatedDurationMinutes));
          final boolean _tmpIsCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp != 0;
          final Long _tmpCompletedAt;
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmpCompletedAt = null;
          } else {
            _tmpCompletedAt = _stmt.getLong(_columnIndexOfCompletedAt);
          }
          final Long _tmpLastAccessedAt;
          if (_stmt.isNull(_columnIndexOfLastAccessedAt)) {
            _tmpLastAccessedAt = null;
          } else {
            _tmpLastAccessedAt = _stmt.getLong(_columnIndexOfLastAccessedAt);
          }
          final long _tmpCreatedAt;
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt);
          final long _tmpLastModifiedAt;
          _tmpLastModifiedAt = _stmt.getLong(_columnIndexOfLastModifiedAt);
          final boolean _tmpIsSystemLesson;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsSystemLesson));
          _tmpIsSystemLesson = _tmp_1 != 0;
          _item = new Lesson(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpDescriptionEnglish,_tmpDescriptionHindi,_tmpContentEnglish,_tmpContentHindi,_tmpCategoryId,_tmpDifficulty,_tmpOrderInCategory,_tmpEstimatedDurationMinutes,_tmpIsCompleted,_tmpCompletedAt,_tmpLastAccessedAt,_tmpCreatedAt,_tmpLastModifiedAt,_tmpIsSystemLesson);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Flow<List<Lesson>> getLessonsByCompletionStatus(final boolean isCompleted) {
    final String _sql = "SELECT * FROM lessons WHERE isCompleted = ? ORDER BY difficulty ASC, orderInCategory ASC";
    return FlowUtil.createFlow(__db, false, new String[] {"lessons"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        final int _tmp = isCompleted ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitleEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleEnglish");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfDescriptionEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionEnglish");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfContentEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "contentEnglish");
        final int _columnIndexOfContentHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "contentHindi");
        final int _columnIndexOfCategoryId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "categoryId");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfOrderInCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "orderInCategory");
        final int _columnIndexOfEstimatedDurationMinutes = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "estimatedDurationMinutes");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfCompletedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAt");
        final int _columnIndexOfLastAccessedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAccessedAt");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _columnIndexOfLastModifiedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastModifiedAt");
        final int _columnIndexOfIsSystemLesson = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isSystemLesson");
        final List<Lesson> _result = new ArrayList<Lesson>();
        while (_stmt.step()) {
          final Lesson _item;
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final String _tmpTitleEnglish;
          if (_stmt.isNull(_columnIndexOfTitleEnglish)) {
            _tmpTitleEnglish = null;
          } else {
            _tmpTitleEnglish = _stmt.getText(_columnIndexOfTitleEnglish);
          }
          final String _tmpTitleHindi;
          if (_stmt.isNull(_columnIndexOfTitleHindi)) {
            _tmpTitleHindi = null;
          } else {
            _tmpTitleHindi = _stmt.getText(_columnIndexOfTitleHindi);
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
          final String _tmpContentEnglish;
          if (_stmt.isNull(_columnIndexOfContentEnglish)) {
            _tmpContentEnglish = null;
          } else {
            _tmpContentEnglish = _stmt.getText(_columnIndexOfContentEnglish);
          }
          final String _tmpContentHindi;
          if (_stmt.isNull(_columnIndexOfContentHindi)) {
            _tmpContentHindi = null;
          } else {
            _tmpContentHindi = _stmt.getText(_columnIndexOfContentHindi);
          }
          final Long _tmpCategoryId;
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null;
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final int _tmpOrderInCategory;
          _tmpOrderInCategory = (int) (_stmt.getLong(_columnIndexOfOrderInCategory));
          final int _tmpEstimatedDurationMinutes;
          _tmpEstimatedDurationMinutes = (int) (_stmt.getLong(_columnIndexOfEstimatedDurationMinutes));
          final boolean _tmpIsCompleted;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp_1 != 0;
          final Long _tmpCompletedAt;
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmpCompletedAt = null;
          } else {
            _tmpCompletedAt = _stmt.getLong(_columnIndexOfCompletedAt);
          }
          final Long _tmpLastAccessedAt;
          if (_stmt.isNull(_columnIndexOfLastAccessedAt)) {
            _tmpLastAccessedAt = null;
          } else {
            _tmpLastAccessedAt = _stmt.getLong(_columnIndexOfLastAccessedAt);
          }
          final long _tmpCreatedAt;
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt);
          final long _tmpLastModifiedAt;
          _tmpLastModifiedAt = _stmt.getLong(_columnIndexOfLastModifiedAt);
          final boolean _tmpIsSystemLesson;
          final int _tmp_2;
          _tmp_2 = (int) (_stmt.getLong(_columnIndexOfIsSystemLesson));
          _tmpIsSystemLesson = _tmp_2 != 0;
          _item = new Lesson(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpDescriptionEnglish,_tmpDescriptionHindi,_tmpContentEnglish,_tmpContentHindi,_tmpCategoryId,_tmpDifficulty,_tmpOrderInCategory,_tmpEstimatedDurationMinutes,_tmpIsCompleted,_tmpCompletedAt,_tmpLastAccessedAt,_tmpCreatedAt,_tmpLastModifiedAt,_tmpIsSystemLesson);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Flow<List<Lesson>> searchLessons(final String query) {
    final String _sql = "SELECT * FROM lessons WHERE titleEnglish LIKE '%' || ? || '%' OR titleHindi LIKE '%' || ? || '%'";
    return FlowUtil.createFlow(__db, false, new String[] {"lessons"}, (_connection) -> {
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
        final int _columnIndexOfTitleEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleEnglish");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfDescriptionEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionEnglish");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfContentEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "contentEnglish");
        final int _columnIndexOfContentHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "contentHindi");
        final int _columnIndexOfCategoryId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "categoryId");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfOrderInCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "orderInCategory");
        final int _columnIndexOfEstimatedDurationMinutes = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "estimatedDurationMinutes");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfCompletedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAt");
        final int _columnIndexOfLastAccessedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAccessedAt");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _columnIndexOfLastModifiedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastModifiedAt");
        final int _columnIndexOfIsSystemLesson = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isSystemLesson");
        final List<Lesson> _result = new ArrayList<Lesson>();
        while (_stmt.step()) {
          final Lesson _item;
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final String _tmpTitleEnglish;
          if (_stmt.isNull(_columnIndexOfTitleEnglish)) {
            _tmpTitleEnglish = null;
          } else {
            _tmpTitleEnglish = _stmt.getText(_columnIndexOfTitleEnglish);
          }
          final String _tmpTitleHindi;
          if (_stmt.isNull(_columnIndexOfTitleHindi)) {
            _tmpTitleHindi = null;
          } else {
            _tmpTitleHindi = _stmt.getText(_columnIndexOfTitleHindi);
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
          final String _tmpContentEnglish;
          if (_stmt.isNull(_columnIndexOfContentEnglish)) {
            _tmpContentEnglish = null;
          } else {
            _tmpContentEnglish = _stmt.getText(_columnIndexOfContentEnglish);
          }
          final String _tmpContentHindi;
          if (_stmt.isNull(_columnIndexOfContentHindi)) {
            _tmpContentHindi = null;
          } else {
            _tmpContentHindi = _stmt.getText(_columnIndexOfContentHindi);
          }
          final Long _tmpCategoryId;
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null;
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final int _tmpOrderInCategory;
          _tmpOrderInCategory = (int) (_stmt.getLong(_columnIndexOfOrderInCategory));
          final int _tmpEstimatedDurationMinutes;
          _tmpEstimatedDurationMinutes = (int) (_stmt.getLong(_columnIndexOfEstimatedDurationMinutes));
          final boolean _tmpIsCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp != 0;
          final Long _tmpCompletedAt;
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmpCompletedAt = null;
          } else {
            _tmpCompletedAt = _stmt.getLong(_columnIndexOfCompletedAt);
          }
          final Long _tmpLastAccessedAt;
          if (_stmt.isNull(_columnIndexOfLastAccessedAt)) {
            _tmpLastAccessedAt = null;
          } else {
            _tmpLastAccessedAt = _stmt.getLong(_columnIndexOfLastAccessedAt);
          }
          final long _tmpCreatedAt;
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt);
          final long _tmpLastModifiedAt;
          _tmpLastModifiedAt = _stmt.getLong(_columnIndexOfLastModifiedAt);
          final boolean _tmpIsSystemLesson;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsSystemLesson));
          _tmpIsSystemLesson = _tmp_1 != 0;
          _item = new Lesson(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpDescriptionEnglish,_tmpDescriptionHindi,_tmpContentEnglish,_tmpContentHindi,_tmpCategoryId,_tmpDifficulty,_tmpOrderInCategory,_tmpEstimatedDurationMinutes,_tmpIsCompleted,_tmpCompletedAt,_tmpLastAccessedAt,_tmpCreatedAt,_tmpLastModifiedAt,_tmpIsSystemLesson);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Object getLessonWithWords(final long lessonId,
      final Continuation<? super LessonWithWords> $completion) {
    final String _sql = "SELECT * FROM lessons WHERE id = ?";
    return DBUtil.performSuspending(__db, true, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, lessonId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitleEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleEnglish");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfDescriptionEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionEnglish");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfContentEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "contentEnglish");
        final int _columnIndexOfContentHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "contentHindi");
        final int _columnIndexOfCategoryId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "categoryId");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfOrderInCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "orderInCategory");
        final int _columnIndexOfEstimatedDurationMinutes = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "estimatedDurationMinutes");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfCompletedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAt");
        final int _columnIndexOfLastAccessedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAccessedAt");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _columnIndexOfLastModifiedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastModifiedAt");
        final int _columnIndexOfIsSystemLesson = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isSystemLesson");
        final LongSparseArray<ArrayList<Word>> _collectionWords = new LongSparseArray<ArrayList<Word>>();
        final LongSparseArray<ArrayList<LessonWord>> _collectionLessonWords = new LongSparseArray<ArrayList<LessonWord>>();
        while (_stmt.step()) {
          final long _tmpKey;
          _tmpKey = _stmt.getLong(_columnIndexOfId);
          if (!_collectionWords.containsKey(_tmpKey)) {
            _collectionWords.put(_tmpKey, new ArrayList<Word>());
          }
          final long _tmpKey_1;
          _tmpKey_1 = _stmt.getLong(_columnIndexOfId);
          if (!_collectionLessonWords.containsKey(_tmpKey_1)) {
            _collectionLessonWords.put(_tmpKey_1, new ArrayList<LessonWord>());
          }
        }
        _stmt.reset();
        __fetchRelationshipwordsAscomBhashasetuAppDataModelWord(_connection, _collectionWords);
        __fetchRelationshiplessonWordsAscomBhashasetuAppDataModelLessonWord(_connection, _collectionLessonWords);
        final LessonWithWords _result;
        if (_stmt.step()) {
          final Lesson _tmpLesson;
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final String _tmpTitleEnglish;
          if (_stmt.isNull(_columnIndexOfTitleEnglish)) {
            _tmpTitleEnglish = null;
          } else {
            _tmpTitleEnglish = _stmt.getText(_columnIndexOfTitleEnglish);
          }
          final String _tmpTitleHindi;
          if (_stmt.isNull(_columnIndexOfTitleHindi)) {
            _tmpTitleHindi = null;
          } else {
            _tmpTitleHindi = _stmt.getText(_columnIndexOfTitleHindi);
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
          final String _tmpContentEnglish;
          if (_stmt.isNull(_columnIndexOfContentEnglish)) {
            _tmpContentEnglish = null;
          } else {
            _tmpContentEnglish = _stmt.getText(_columnIndexOfContentEnglish);
          }
          final String _tmpContentHindi;
          if (_stmt.isNull(_columnIndexOfContentHindi)) {
            _tmpContentHindi = null;
          } else {
            _tmpContentHindi = _stmt.getText(_columnIndexOfContentHindi);
          }
          final Long _tmpCategoryId;
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null;
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final int _tmpOrderInCategory;
          _tmpOrderInCategory = (int) (_stmt.getLong(_columnIndexOfOrderInCategory));
          final int _tmpEstimatedDurationMinutes;
          _tmpEstimatedDurationMinutes = (int) (_stmt.getLong(_columnIndexOfEstimatedDurationMinutes));
          final boolean _tmpIsCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp != 0;
          final Long _tmpCompletedAt;
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmpCompletedAt = null;
          } else {
            _tmpCompletedAt = _stmt.getLong(_columnIndexOfCompletedAt);
          }
          final Long _tmpLastAccessedAt;
          if (_stmt.isNull(_columnIndexOfLastAccessedAt)) {
            _tmpLastAccessedAt = null;
          } else {
            _tmpLastAccessedAt = _stmt.getLong(_columnIndexOfLastAccessedAt);
          }
          final long _tmpCreatedAt;
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt);
          final long _tmpLastModifiedAt;
          _tmpLastModifiedAt = _stmt.getLong(_columnIndexOfLastModifiedAt);
          final boolean _tmpIsSystemLesson;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsSystemLesson));
          _tmpIsSystemLesson = _tmp_1 != 0;
          _tmpLesson = new Lesson(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpDescriptionEnglish,_tmpDescriptionHindi,_tmpContentEnglish,_tmpContentHindi,_tmpCategoryId,_tmpDifficulty,_tmpOrderInCategory,_tmpEstimatedDurationMinutes,_tmpIsCompleted,_tmpCompletedAt,_tmpLastAccessedAt,_tmpCreatedAt,_tmpLastModifiedAt,_tmpIsSystemLesson);
          final ArrayList<Word> _tmpWordsCollection;
          final long _tmpKey_2;
          _tmpKey_2 = _stmt.getLong(_columnIndexOfId);
          _tmpWordsCollection = _collectionWords.get(_tmpKey_2);
          final ArrayList<LessonWord> _tmpLessonWordsCollection;
          final long _tmpKey_3;
          _tmpKey_3 = _stmt.getLong(_columnIndexOfId);
          _tmpLessonWordsCollection = _collectionLessonWords.get(_tmpKey_3);
          _result = new LessonWithWords(_tmpLesson,_tmpWordsCollection,_tmpLessonWordsCollection);
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
  public Flow<LessonWithWords> getLessonWithWordsFlow(final long lessonId) {
    final String _sql = "SELECT * FROM lessons WHERE id = ?";
    return FlowUtil.createFlow(__db, true, new String[] {"lesson_words", "words",
        "lessons"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, lessonId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitleEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleEnglish");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfDescriptionEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionEnglish");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfContentEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "contentEnglish");
        final int _columnIndexOfContentHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "contentHindi");
        final int _columnIndexOfCategoryId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "categoryId");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfOrderInCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "orderInCategory");
        final int _columnIndexOfEstimatedDurationMinutes = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "estimatedDurationMinutes");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfCompletedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAt");
        final int _columnIndexOfLastAccessedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAccessedAt");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _columnIndexOfLastModifiedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastModifiedAt");
        final int _columnIndexOfIsSystemLesson = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isSystemLesson");
        final LongSparseArray<ArrayList<Word>> _collectionWords = new LongSparseArray<ArrayList<Word>>();
        final LongSparseArray<ArrayList<LessonWord>> _collectionLessonWords = new LongSparseArray<ArrayList<LessonWord>>();
        while (_stmt.step()) {
          final long _tmpKey;
          _tmpKey = _stmt.getLong(_columnIndexOfId);
          if (!_collectionWords.containsKey(_tmpKey)) {
            _collectionWords.put(_tmpKey, new ArrayList<Word>());
          }
          final long _tmpKey_1;
          _tmpKey_1 = _stmt.getLong(_columnIndexOfId);
          if (!_collectionLessonWords.containsKey(_tmpKey_1)) {
            _collectionLessonWords.put(_tmpKey_1, new ArrayList<LessonWord>());
          }
        }
        _stmt.reset();
        __fetchRelationshipwordsAscomBhashasetuAppDataModelWord(_connection, _collectionWords);
        __fetchRelationshiplessonWordsAscomBhashasetuAppDataModelLessonWord(_connection, _collectionLessonWords);
        final LessonWithWords _result;
        if (_stmt.step()) {
          final Lesson _tmpLesson;
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final String _tmpTitleEnglish;
          if (_stmt.isNull(_columnIndexOfTitleEnglish)) {
            _tmpTitleEnglish = null;
          } else {
            _tmpTitleEnglish = _stmt.getText(_columnIndexOfTitleEnglish);
          }
          final String _tmpTitleHindi;
          if (_stmt.isNull(_columnIndexOfTitleHindi)) {
            _tmpTitleHindi = null;
          } else {
            _tmpTitleHindi = _stmt.getText(_columnIndexOfTitleHindi);
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
          final String _tmpContentEnglish;
          if (_stmt.isNull(_columnIndexOfContentEnglish)) {
            _tmpContentEnglish = null;
          } else {
            _tmpContentEnglish = _stmt.getText(_columnIndexOfContentEnglish);
          }
          final String _tmpContentHindi;
          if (_stmt.isNull(_columnIndexOfContentHindi)) {
            _tmpContentHindi = null;
          } else {
            _tmpContentHindi = _stmt.getText(_columnIndexOfContentHindi);
          }
          final Long _tmpCategoryId;
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null;
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final int _tmpOrderInCategory;
          _tmpOrderInCategory = (int) (_stmt.getLong(_columnIndexOfOrderInCategory));
          final int _tmpEstimatedDurationMinutes;
          _tmpEstimatedDurationMinutes = (int) (_stmt.getLong(_columnIndexOfEstimatedDurationMinutes));
          final boolean _tmpIsCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp != 0;
          final Long _tmpCompletedAt;
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmpCompletedAt = null;
          } else {
            _tmpCompletedAt = _stmt.getLong(_columnIndexOfCompletedAt);
          }
          final Long _tmpLastAccessedAt;
          if (_stmt.isNull(_columnIndexOfLastAccessedAt)) {
            _tmpLastAccessedAt = null;
          } else {
            _tmpLastAccessedAt = _stmt.getLong(_columnIndexOfLastAccessedAt);
          }
          final long _tmpCreatedAt;
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt);
          final long _tmpLastModifiedAt;
          _tmpLastModifiedAt = _stmt.getLong(_columnIndexOfLastModifiedAt);
          final boolean _tmpIsSystemLesson;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsSystemLesson));
          _tmpIsSystemLesson = _tmp_1 != 0;
          _tmpLesson = new Lesson(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpDescriptionEnglish,_tmpDescriptionHindi,_tmpContentEnglish,_tmpContentHindi,_tmpCategoryId,_tmpDifficulty,_tmpOrderInCategory,_tmpEstimatedDurationMinutes,_tmpIsCompleted,_tmpCompletedAt,_tmpLastAccessedAt,_tmpCreatedAt,_tmpLastModifiedAt,_tmpIsSystemLesson);
          final ArrayList<Word> _tmpWordsCollection;
          final long _tmpKey_2;
          _tmpKey_2 = _stmt.getLong(_columnIndexOfId);
          _tmpWordsCollection = _collectionWords.get(_tmpKey_2);
          final ArrayList<LessonWord> _tmpLessonWordsCollection;
          final long _tmpKey_3;
          _tmpKey_3 = _stmt.getLong(_columnIndexOfId);
          _tmpLessonWordsCollection = _collectionLessonWords.get(_tmpKey_3);
          _result = new LessonWithWords(_tmpLesson,_tmpWordsCollection,_tmpLessonWordsCollection);
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
  public Flow<List<LessonWithWords>> getAllLessonsWithWords() {
    final String _sql = "SELECT * FROM lessons";
    return FlowUtil.createFlow(__db, true, new String[] {"lesson_words", "words",
        "lessons"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitleEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleEnglish");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfDescriptionEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionEnglish");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfContentEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "contentEnglish");
        final int _columnIndexOfContentHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "contentHindi");
        final int _columnIndexOfCategoryId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "categoryId");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfOrderInCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "orderInCategory");
        final int _columnIndexOfEstimatedDurationMinutes = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "estimatedDurationMinutes");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfCompletedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAt");
        final int _columnIndexOfLastAccessedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAccessedAt");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _columnIndexOfLastModifiedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastModifiedAt");
        final int _columnIndexOfIsSystemLesson = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isSystemLesson");
        final LongSparseArray<ArrayList<Word>> _collectionWords = new LongSparseArray<ArrayList<Word>>();
        final LongSparseArray<ArrayList<LessonWord>> _collectionLessonWords = new LongSparseArray<ArrayList<LessonWord>>();
        while (_stmt.step()) {
          final long _tmpKey;
          _tmpKey = _stmt.getLong(_columnIndexOfId);
          if (!_collectionWords.containsKey(_tmpKey)) {
            _collectionWords.put(_tmpKey, new ArrayList<Word>());
          }
          final long _tmpKey_1;
          _tmpKey_1 = _stmt.getLong(_columnIndexOfId);
          if (!_collectionLessonWords.containsKey(_tmpKey_1)) {
            _collectionLessonWords.put(_tmpKey_1, new ArrayList<LessonWord>());
          }
        }
        _stmt.reset();
        __fetchRelationshipwordsAscomBhashasetuAppDataModelWord(_connection, _collectionWords);
        __fetchRelationshiplessonWordsAscomBhashasetuAppDataModelLessonWord(_connection, _collectionLessonWords);
        final List<LessonWithWords> _result = new ArrayList<LessonWithWords>();
        while (_stmt.step()) {
          final LessonWithWords _item;
          final Lesson _tmpLesson;
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final String _tmpTitleEnglish;
          if (_stmt.isNull(_columnIndexOfTitleEnglish)) {
            _tmpTitleEnglish = null;
          } else {
            _tmpTitleEnglish = _stmt.getText(_columnIndexOfTitleEnglish);
          }
          final String _tmpTitleHindi;
          if (_stmt.isNull(_columnIndexOfTitleHindi)) {
            _tmpTitleHindi = null;
          } else {
            _tmpTitleHindi = _stmt.getText(_columnIndexOfTitleHindi);
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
          final String _tmpContentEnglish;
          if (_stmt.isNull(_columnIndexOfContentEnglish)) {
            _tmpContentEnglish = null;
          } else {
            _tmpContentEnglish = _stmt.getText(_columnIndexOfContentEnglish);
          }
          final String _tmpContentHindi;
          if (_stmt.isNull(_columnIndexOfContentHindi)) {
            _tmpContentHindi = null;
          } else {
            _tmpContentHindi = _stmt.getText(_columnIndexOfContentHindi);
          }
          final Long _tmpCategoryId;
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null;
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final int _tmpOrderInCategory;
          _tmpOrderInCategory = (int) (_stmt.getLong(_columnIndexOfOrderInCategory));
          final int _tmpEstimatedDurationMinutes;
          _tmpEstimatedDurationMinutes = (int) (_stmt.getLong(_columnIndexOfEstimatedDurationMinutes));
          final boolean _tmpIsCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp != 0;
          final Long _tmpCompletedAt;
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmpCompletedAt = null;
          } else {
            _tmpCompletedAt = _stmt.getLong(_columnIndexOfCompletedAt);
          }
          final Long _tmpLastAccessedAt;
          if (_stmt.isNull(_columnIndexOfLastAccessedAt)) {
            _tmpLastAccessedAt = null;
          } else {
            _tmpLastAccessedAt = _stmt.getLong(_columnIndexOfLastAccessedAt);
          }
          final long _tmpCreatedAt;
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt);
          final long _tmpLastModifiedAt;
          _tmpLastModifiedAt = _stmt.getLong(_columnIndexOfLastModifiedAt);
          final boolean _tmpIsSystemLesson;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsSystemLesson));
          _tmpIsSystemLesson = _tmp_1 != 0;
          _tmpLesson = new Lesson(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpDescriptionEnglish,_tmpDescriptionHindi,_tmpContentEnglish,_tmpContentHindi,_tmpCategoryId,_tmpDifficulty,_tmpOrderInCategory,_tmpEstimatedDurationMinutes,_tmpIsCompleted,_tmpCompletedAt,_tmpLastAccessedAt,_tmpCreatedAt,_tmpLastModifiedAt,_tmpIsSystemLesson);
          final ArrayList<Word> _tmpWordsCollection;
          final long _tmpKey_2;
          _tmpKey_2 = _stmt.getLong(_columnIndexOfId);
          _tmpWordsCollection = _collectionWords.get(_tmpKey_2);
          final ArrayList<LessonWord> _tmpLessonWordsCollection;
          final long _tmpKey_3;
          _tmpKey_3 = _stmt.getLong(_columnIndexOfId);
          _tmpLessonWordsCollection = _collectionLessonWords.get(_tmpKey_3);
          _item = new LessonWithWords(_tmpLesson,_tmpWordsCollection,_tmpLessonWordsCollection);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Flow<List<LessonWithWords>> getLessonsWithWordsByCategory(final long categoryId) {
    final String _sql = "SELECT * FROM lessons WHERE categoryId = ? ORDER BY orderInCategory ASC";
    return FlowUtil.createFlow(__db, true, new String[] {"lesson_words", "words",
        "lessons"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, categoryId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitleEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleEnglish");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfDescriptionEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionEnglish");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfContentEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "contentEnglish");
        final int _columnIndexOfContentHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "contentHindi");
        final int _columnIndexOfCategoryId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "categoryId");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfOrderInCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "orderInCategory");
        final int _columnIndexOfEstimatedDurationMinutes = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "estimatedDurationMinutes");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfCompletedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAt");
        final int _columnIndexOfLastAccessedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAccessedAt");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _columnIndexOfLastModifiedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastModifiedAt");
        final int _columnIndexOfIsSystemLesson = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isSystemLesson");
        final LongSparseArray<ArrayList<Word>> _collectionWords = new LongSparseArray<ArrayList<Word>>();
        final LongSparseArray<ArrayList<LessonWord>> _collectionLessonWords = new LongSparseArray<ArrayList<LessonWord>>();
        while (_stmt.step()) {
          final long _tmpKey;
          _tmpKey = _stmt.getLong(_columnIndexOfId);
          if (!_collectionWords.containsKey(_tmpKey)) {
            _collectionWords.put(_tmpKey, new ArrayList<Word>());
          }
          final long _tmpKey_1;
          _tmpKey_1 = _stmt.getLong(_columnIndexOfId);
          if (!_collectionLessonWords.containsKey(_tmpKey_1)) {
            _collectionLessonWords.put(_tmpKey_1, new ArrayList<LessonWord>());
          }
        }
        _stmt.reset();
        __fetchRelationshipwordsAscomBhashasetuAppDataModelWord(_connection, _collectionWords);
        __fetchRelationshiplessonWordsAscomBhashasetuAppDataModelLessonWord(_connection, _collectionLessonWords);
        final List<LessonWithWords> _result = new ArrayList<LessonWithWords>();
        while (_stmt.step()) {
          final LessonWithWords _item;
          final Lesson _tmpLesson;
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final String _tmpTitleEnglish;
          if (_stmt.isNull(_columnIndexOfTitleEnglish)) {
            _tmpTitleEnglish = null;
          } else {
            _tmpTitleEnglish = _stmt.getText(_columnIndexOfTitleEnglish);
          }
          final String _tmpTitleHindi;
          if (_stmt.isNull(_columnIndexOfTitleHindi)) {
            _tmpTitleHindi = null;
          } else {
            _tmpTitleHindi = _stmt.getText(_columnIndexOfTitleHindi);
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
          final String _tmpContentEnglish;
          if (_stmt.isNull(_columnIndexOfContentEnglish)) {
            _tmpContentEnglish = null;
          } else {
            _tmpContentEnglish = _stmt.getText(_columnIndexOfContentEnglish);
          }
          final String _tmpContentHindi;
          if (_stmt.isNull(_columnIndexOfContentHindi)) {
            _tmpContentHindi = null;
          } else {
            _tmpContentHindi = _stmt.getText(_columnIndexOfContentHindi);
          }
          final Long _tmpCategoryId;
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null;
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final int _tmpOrderInCategory;
          _tmpOrderInCategory = (int) (_stmt.getLong(_columnIndexOfOrderInCategory));
          final int _tmpEstimatedDurationMinutes;
          _tmpEstimatedDurationMinutes = (int) (_stmt.getLong(_columnIndexOfEstimatedDurationMinutes));
          final boolean _tmpIsCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp != 0;
          final Long _tmpCompletedAt;
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmpCompletedAt = null;
          } else {
            _tmpCompletedAt = _stmt.getLong(_columnIndexOfCompletedAt);
          }
          final Long _tmpLastAccessedAt;
          if (_stmt.isNull(_columnIndexOfLastAccessedAt)) {
            _tmpLastAccessedAt = null;
          } else {
            _tmpLastAccessedAt = _stmt.getLong(_columnIndexOfLastAccessedAt);
          }
          final long _tmpCreatedAt;
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt);
          final long _tmpLastModifiedAt;
          _tmpLastModifiedAt = _stmt.getLong(_columnIndexOfLastModifiedAt);
          final boolean _tmpIsSystemLesson;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsSystemLesson));
          _tmpIsSystemLesson = _tmp_1 != 0;
          _tmpLesson = new Lesson(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpDescriptionEnglish,_tmpDescriptionHindi,_tmpContentEnglish,_tmpContentHindi,_tmpCategoryId,_tmpDifficulty,_tmpOrderInCategory,_tmpEstimatedDurationMinutes,_tmpIsCompleted,_tmpCompletedAt,_tmpLastAccessedAt,_tmpCreatedAt,_tmpLastModifiedAt,_tmpIsSystemLesson);
          final ArrayList<Word> _tmpWordsCollection;
          final long _tmpKey_2;
          _tmpKey_2 = _stmt.getLong(_columnIndexOfId);
          _tmpWordsCollection = _collectionWords.get(_tmpKey_2);
          final ArrayList<LessonWord> _tmpLessonWordsCollection;
          final long _tmpKey_3;
          _tmpKey_3 = _stmt.getLong(_columnIndexOfId);
          _tmpLessonWordsCollection = _collectionLessonWords.get(_tmpKey_3);
          _item = new LessonWithWords(_tmpLesson,_tmpWordsCollection,_tmpLessonWordsCollection);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Flow<List<Lesson>> getLessonsContainingWord(final long wordId) {
    final String _sql = "\n"
            + "        SELECT l.* FROM lessons l\n"
            + "        INNER JOIN lesson_words lw ON l.id = lw.lessonId\n"
            + "        WHERE lw.wordId = ?\n"
            + "        ORDER BY l.difficulty ASC, l.orderInCategory ASC\n"
            + "    ";
    return FlowUtil.createFlow(__db, false, new String[] {"lessons",
        "lesson_words"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, wordId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitleEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleEnglish");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfDescriptionEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionEnglish");
        final int _columnIndexOfDescriptionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "descriptionHindi");
        final int _columnIndexOfContentEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "contentEnglish");
        final int _columnIndexOfContentHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "contentHindi");
        final int _columnIndexOfCategoryId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "categoryId");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfOrderInCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "orderInCategory");
        final int _columnIndexOfEstimatedDurationMinutes = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "estimatedDurationMinutes");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfCompletedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAt");
        final int _columnIndexOfLastAccessedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAccessedAt");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _columnIndexOfLastModifiedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastModifiedAt");
        final int _columnIndexOfIsSystemLesson = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isSystemLesson");
        final List<Lesson> _result = new ArrayList<Lesson>();
        while (_stmt.step()) {
          final Lesson _item;
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final String _tmpTitleEnglish;
          if (_stmt.isNull(_columnIndexOfTitleEnglish)) {
            _tmpTitleEnglish = null;
          } else {
            _tmpTitleEnglish = _stmt.getText(_columnIndexOfTitleEnglish);
          }
          final String _tmpTitleHindi;
          if (_stmt.isNull(_columnIndexOfTitleHindi)) {
            _tmpTitleHindi = null;
          } else {
            _tmpTitleHindi = _stmt.getText(_columnIndexOfTitleHindi);
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
          final String _tmpContentEnglish;
          if (_stmt.isNull(_columnIndexOfContentEnglish)) {
            _tmpContentEnglish = null;
          } else {
            _tmpContentEnglish = _stmt.getText(_columnIndexOfContentEnglish);
          }
          final String _tmpContentHindi;
          if (_stmt.isNull(_columnIndexOfContentHindi)) {
            _tmpContentHindi = null;
          } else {
            _tmpContentHindi = _stmt.getText(_columnIndexOfContentHindi);
          }
          final Long _tmpCategoryId;
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null;
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final int _tmpOrderInCategory;
          _tmpOrderInCategory = (int) (_stmt.getLong(_columnIndexOfOrderInCategory));
          final int _tmpEstimatedDurationMinutes;
          _tmpEstimatedDurationMinutes = (int) (_stmt.getLong(_columnIndexOfEstimatedDurationMinutes));
          final boolean _tmpIsCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp != 0;
          final Long _tmpCompletedAt;
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmpCompletedAt = null;
          } else {
            _tmpCompletedAt = _stmt.getLong(_columnIndexOfCompletedAt);
          }
          final Long _tmpLastAccessedAt;
          if (_stmt.isNull(_columnIndexOfLastAccessedAt)) {
            _tmpLastAccessedAt = null;
          } else {
            _tmpLastAccessedAt = _stmt.getLong(_columnIndexOfLastAccessedAt);
          }
          final long _tmpCreatedAt;
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt);
          final long _tmpLastModifiedAt;
          _tmpLastModifiedAt = _stmt.getLong(_columnIndexOfLastModifiedAt);
          final boolean _tmpIsSystemLesson;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsSystemLesson));
          _tmpIsSystemLesson = _tmp_1 != 0;
          _item = new Lesson(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpDescriptionEnglish,_tmpDescriptionHindi,_tmpContentEnglish,_tmpContentHindi,_tmpCategoryId,_tmpDifficulty,_tmpOrderInCategory,_tmpEstimatedDurationMinutes,_tmpIsCompleted,_tmpCompletedAt,_tmpLastAccessedAt,_tmpCreatedAt,_tmpLastModifiedAt,_tmpIsSystemLesson);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Flow<Integer> getLessonCount() {
    final String _sql = "SELECT COUNT(*) FROM lessons";
    return FlowUtil.createFlow(__db, false, new String[] {"lessons"}, (_connection) -> {
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
  public Flow<Integer> getCompletedLessonCount() {
    final String _sql = "SELECT COUNT(*) FROM lessons WHERE isCompleted = 1";
    return FlowUtil.createFlow(__db, false, new String[] {"lessons"}, (_connection) -> {
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
  public Flow<Integer> getLessonCountByCategory(final long categoryId) {
    final String _sql = "SELECT COUNT(*) FROM lessons WHERE categoryId = ?";
    return FlowUtil.createFlow(__db, false, new String[] {"lessons"}, (_connection) -> {
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
  public Flow<Integer> getCompletedLessonCountByCategory(final long categoryId) {
    final String _sql = "SELECT COUNT(*) FROM lessons WHERE categoryId = ? AND isCompleted = 1";
    return FlowUtil.createFlow(__db, false, new String[] {"lessons"}, (_connection) -> {
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
  public Object lessonExistsByTitle(final String titleEnglish,
      final Continuation<? super Boolean> $completion) {
    final String _sql = "SELECT EXISTS(SELECT 1 FROM lessons WHERE titleEnglish = ? LIMIT 1)";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (titleEnglish == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, titleEnglish);
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
  public Object isWordInLesson(final long lessonId, final long wordId,
      final Continuation<? super Boolean> $completion) {
    final String _sql = "SELECT EXISTS(SELECT 1 FROM lesson_words WHERE lessonId = ? AND wordId = ? LIMIT 1)";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, lessonId);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, wordId);
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
  public Object getNextOrderInCategory(final long categoryId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT MAX(orderInCategory) + 1 FROM lessons WHERE categoryId = ?";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
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
    }, $completion);
  }

  @Override
  public Object removeWordFromLessonById(final long lessonId, final long wordId,
      final Continuation<? super Unit> $completion) {
    final String _sql = "DELETE FROM lesson_words WHERE lessonId = ? AND wordId = ?";
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, lessonId);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, wordId);
        _stmt.step();
        return Unit.INSTANCE;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object removeAllWordsFromLesson(final long lessonId,
      final Continuation<? super Unit> $completion) {
    final String _sql = "DELETE FROM lesson_words WHERE lessonId = ?";
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, lessonId);
        _stmt.step();
        return Unit.INSTANCE;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object updateWordOrder(final long lessonId, final long wordId, final int newOrder,
      final Continuation<? super Unit> $completion) {
    final String _sql = "UPDATE lesson_words SET orderInLesson = ? WHERE lessonId = ? AND wordId = ?";
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, newOrder);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, lessonId);
        _argIndex = 3;
        _stmt.bindLong(_argIndex, wordId);
        _stmt.step();
        return Unit.INSTANCE;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object updateLessonCompletion(final long lessonId, final boolean isCompleted,
      final Long completedAt, final Continuation<? super Unit> $completion) {
    final String _sql = "UPDATE lessons SET isCompleted = ?, completedAt = ? WHERE id = ?";
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        final int _tmp = isCompleted ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        if (completedAt == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, completedAt);
        }
        _argIndex = 3;
        _stmt.bindLong(_argIndex, lessonId);
        _stmt.step();
        return Unit.INSTANCE;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object updateLessonLastAccessed(final long lessonId, final long accessedAt,
      final Continuation<? super Unit> $completion) {
    final String _sql = "UPDATE lessons SET lastAccessedAt = ? WHERE id = ?";
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, accessedAt);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, lessonId);
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
    _stringBuilder.append("SELECT `words`.`id` AS `id`,`words`.`englishText` AS `englishText`,`words`.`hindiText` AS `hindiText`,`words`.`englishPronunciation` AS `englishPronunciation`,`words`.`hindiPronunciation` AS `hindiPronunciation`,`words`.`englishExample` AS `englishExample`,`words`.`hindiExample` AS `hindiExample`,`words`.`difficulty` AS `difficulty`,`words`.`partOfSpeech` AS `partOfSpeech`,`words`.`isFavorite` AS `isFavorite`,`words`.`englishAudioPath` AS `englishAudioPath`,`words`.`hindiAudioPath` AS `hindiAudioPath`,`words`.`createdAt` AS `createdAt`,`words`.`lastModifiedAt` AS `lastModifiedAt`,`words`.`lastReviewedAt` AS `lastReviewedAt`,`words`.`nextReviewDate` AS `nextReviewDate`,_junction.`lessonId` FROM `lesson_words` AS _junction INNER JOIN `words` ON (_junction.`wordId` = `words`.`id`) WHERE _junction.`lessonId` IN (");
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
      // _junction.lessonId;
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

  private void __fetchRelationshiplessonWordsAscomBhashasetuAppDataModelLessonWord(
      @NonNull final SQLiteConnection _connection,
      @NonNull final LongSparseArray<ArrayList<LessonWord>> _map) {
    if (_map.isEmpty()) {
      return;
    }
    if (_map.size() > 999) {
      RelationUtil.recursiveFetchLongSparseArray(_map, true, (_tmpMap) -> {
        __fetchRelationshiplessonWordsAscomBhashasetuAppDataModelLessonWord(_connection, _tmpMap);
        return Unit.INSTANCE;
      });
      return;
    }
    final StringBuilder _stringBuilder = new StringBuilder();
    _stringBuilder.append("SELECT `lessonId`,`wordId`,`orderInLesson`,`isKeyword`,`notes`,`includeInQuiz`,`highlightInContent` FROM `lesson_words` WHERE `lessonId` IN (");
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
      final int _itemKeyIndex = SQLiteStatementUtil.getColumnIndex(_stmt, "lessonId");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _columnIndexOfLessonId = 0;
      final int _columnIndexOfWordId = 1;
      final int _columnIndexOfOrderInLesson = 2;
      final int _columnIndexOfIsKeyword = 3;
      final int _columnIndexOfNotes = 4;
      final int _columnIndexOfIncludeInQuiz = 5;
      final int _columnIndexOfHighlightInContent = 6;
      while (_stmt.step()) {
        final long _tmpKey;
        _tmpKey = _stmt.getLong(_itemKeyIndex);
        final ArrayList<LessonWord> _tmpRelation = _map.get(_tmpKey);
        if (_tmpRelation != null) {
          final LessonWord _item_1;
          final long _tmpLessonId;
          _tmpLessonId = _stmt.getLong(_columnIndexOfLessonId);
          final long _tmpWordId;
          _tmpWordId = _stmt.getLong(_columnIndexOfWordId);
          final int _tmpOrderInLesson;
          _tmpOrderInLesson = (int) (_stmt.getLong(_columnIndexOfOrderInLesson));
          final boolean _tmpIsKeyword;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsKeyword));
          _tmpIsKeyword = _tmp != 0;
          final String _tmpNotes;
          if (_stmt.isNull(_columnIndexOfNotes)) {
            _tmpNotes = null;
          } else {
            _tmpNotes = _stmt.getText(_columnIndexOfNotes);
          }
          final boolean _tmpIncludeInQuiz;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIncludeInQuiz));
          _tmpIncludeInQuiz = _tmp_1 != 0;
          final boolean _tmpHighlightInContent;
          final int _tmp_2;
          _tmp_2 = (int) (_stmt.getLong(_columnIndexOfHighlightInContent));
          _tmpHighlightInContent = _tmp_2 != 0;
          _item_1 = new LessonWord(_tmpLessonId,_tmpWordId,_tmpOrderInLesson,_tmpIsKeyword,_tmpNotes,_tmpIncludeInQuiz,_tmpHighlightInContent);
          _tmpRelation.add(_item_1);
        }
      }
    } finally {
      _stmt.close();
    }
  }
}
