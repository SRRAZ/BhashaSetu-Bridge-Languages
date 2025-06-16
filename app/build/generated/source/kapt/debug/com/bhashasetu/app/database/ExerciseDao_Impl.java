package com.bhashasetu.app.database;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeleteOrUpdateAdapter;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteStatement;
import com.bhashasetu.app.database.converters.DateConverter;
import com.bhashasetu.app.database.converters.ExerciseTypeConverter;
import com.bhashasetu.app.model.exercise.Exercise;
import com.bhashasetu.app.model.exercise.ExerciseType;
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
public final class ExerciseDao_Impl implements ExerciseDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<Exercise> __insertAdapterOfExercise;

  private final EntityDeleteOrUpdateAdapter<Exercise> __deleteAdapterOfExercise;

  private final EntityDeleteOrUpdateAdapter<Exercise> __updateAdapterOfExercise;

  public ExerciseDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfExercise = new EntityInsertAdapter<Exercise>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `exercises` (`id`,`title`,`description`,`type`,`difficulty`,`wordCount`,`points`,`isCompleted`,`createdAt`,`completedAt`,`correctAnswers`,`totalQuestions`,`lessonId`,`category`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final Exercise entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getTitle());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getDescription());
        }
        final String _tmp = ExerciseTypeConverter.fromExerciseType(entity.getType());
        if (_tmp == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, _tmp);
        }
        statement.bindLong(5, entity.getDifficulty());
        statement.bindLong(6, entity.getWordCount());
        statement.bindLong(7, entity.getPoints());
        final int _tmp_1 = entity.isCompleted() ? 1 : 0;
        statement.bindLong(8, _tmp_1);
        final Long _tmp_2 = DateConverter.dateToTimestamp(entity.getCreatedAt());
        if (_tmp_2 == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, _tmp_2);
        }
        final Long _tmp_3 = DateConverter.dateToTimestamp(entity.getCompletedAt());
        if (_tmp_3 == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, _tmp_3);
        }
        statement.bindLong(11, entity.getCorrectAnswers());
        statement.bindLong(12, entity.getTotalQuestions());
        if (entity.getLessonId() == null) {
          statement.bindNull(13);
        } else {
          statement.bindText(13, entity.getLessonId());
        }
        if (entity.getCategory() == null) {
          statement.bindNull(14);
        } else {
          statement.bindText(14, entity.getCategory());
        }
      }
    };
    this.__deleteAdapterOfExercise = new EntityDeleteOrUpdateAdapter<Exercise>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `exercises` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final Exercise entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfExercise = new EntityDeleteOrUpdateAdapter<Exercise>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `exercises` SET `id` = ?,`title` = ?,`description` = ?,`type` = ?,`difficulty` = ?,`wordCount` = ?,`points` = ?,`isCompleted` = ?,`createdAt` = ?,`completedAt` = ?,`correctAnswers` = ?,`totalQuestions` = ?,`lessonId` = ?,`category` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final Exercise entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getTitle());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getDescription());
        }
        final String _tmp = ExerciseTypeConverter.fromExerciseType(entity.getType());
        if (_tmp == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, _tmp);
        }
        statement.bindLong(5, entity.getDifficulty());
        statement.bindLong(6, entity.getWordCount());
        statement.bindLong(7, entity.getPoints());
        final int _tmp_1 = entity.isCompleted() ? 1 : 0;
        statement.bindLong(8, _tmp_1);
        final Long _tmp_2 = DateConverter.dateToTimestamp(entity.getCreatedAt());
        if (_tmp_2 == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, _tmp_2);
        }
        final Long _tmp_3 = DateConverter.dateToTimestamp(entity.getCompletedAt());
        if (_tmp_3 == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, _tmp_3);
        }
        statement.bindLong(11, entity.getCorrectAnswers());
        statement.bindLong(12, entity.getTotalQuestions());
        if (entity.getLessonId() == null) {
          statement.bindNull(13);
        } else {
          statement.bindText(13, entity.getLessonId());
        }
        if (entity.getCategory() == null) {
          statement.bindNull(14);
        } else {
          statement.bindText(14, entity.getCategory());
        }
        statement.bindLong(15, entity.getId());
      }
    };
  }

  @Override
  public long insert(final Exercise exercise) {
    return DBUtil.performBlocking(__db, false, true, (_connection) -> {
      return __insertAdapterOfExercise.insertAndReturnId(_connection, exercise);
    });
  }

  @Override
  public void delete(final Exercise exercise) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __deleteAdapterOfExercise.handle(_connection, exercise);
      return null;
    });
  }

  @Override
  public void update(final Exercise exercise) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __updateAdapterOfExercise.handle(_connection, exercise);
      return null;
    });
  }

  @Override
  public LiveData<Exercise> getExerciseById(final int id) {
    final String _sql = "SELECT * FROM exercises WHERE id = ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"exercises"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "title");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "type");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfWordCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wordCount");
        final int _columnIndexOfPoints = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "points");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _columnIndexOfCompletedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAt");
        final int _columnIndexOfCorrectAnswers = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctAnswers");
        final int _columnIndexOfTotalQuestions = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalQuestions");
        final int _columnIndexOfLessonId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lessonId");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final Exercise _result;
        if (_stmt.step()) {
          final String _tmpTitle;
          if (_stmt.isNull(_columnIndexOfTitle)) {
            _tmpTitle = null;
          } else {
            _tmpTitle = _stmt.getText(_columnIndexOfTitle);
          }
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          final ExerciseType _tmpType;
          final String _tmp;
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmp = null;
          } else {
            _tmp = _stmt.getText(_columnIndexOfType);
          }
          _tmpType = ExerciseTypeConverter.toExerciseType(_tmp);
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final int _tmpWordCount;
          _tmpWordCount = (int) (_stmt.getLong(_columnIndexOfWordCount));
          final int _tmpPoints;
          _tmpPoints = (int) (_stmt.getLong(_columnIndexOfPoints));
          final boolean _tmpIsCompleted;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp_1 != 0;
          final Date _tmpCreatedAt;
          final Long _tmp_2;
          if (_stmt.isNull(_columnIndexOfCreatedAt)) {
            _tmp_2 = null;
          } else {
            _tmp_2 = _stmt.getLong(_columnIndexOfCreatedAt);
          }
          _tmpCreatedAt = DateConverter.fromTimestamp(_tmp_2);
          final Date _tmpCompletedAt;
          final Long _tmp_3;
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmp_3 = null;
          } else {
            _tmp_3 = _stmt.getLong(_columnIndexOfCompletedAt);
          }
          _tmpCompletedAt = DateConverter.fromTimestamp(_tmp_3);
          final int _tmpCorrectAnswers;
          _tmpCorrectAnswers = (int) (_stmt.getLong(_columnIndexOfCorrectAnswers));
          final int _tmpTotalQuestions;
          _tmpTotalQuestions = (int) (_stmt.getLong(_columnIndexOfTotalQuestions));
          final String _tmpLessonId;
          if (_stmt.isNull(_columnIndexOfLessonId)) {
            _tmpLessonId = null;
          } else {
            _tmpLessonId = _stmt.getText(_columnIndexOfLessonId);
          }
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          _result = new Exercise(_tmpTitle,_tmpDescription,_tmpType,_tmpDifficulty,_tmpWordCount,_tmpPoints,_tmpIsCompleted,_tmpCreatedAt,_tmpCompletedAt,_tmpCorrectAnswers,_tmpTotalQuestions,_tmpLessonId,_tmpCategory);
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _result.setId(_tmpId);
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
  public LiveData<List<Exercise>> getAllExercises() {
    final String _sql = "SELECT * FROM exercises";
    return __db.getInvalidationTracker().createLiveData(new String[] {"exercises"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "title");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "type");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfWordCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wordCount");
        final int _columnIndexOfPoints = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "points");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _columnIndexOfCompletedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAt");
        final int _columnIndexOfCorrectAnswers = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctAnswers");
        final int _columnIndexOfTotalQuestions = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalQuestions");
        final int _columnIndexOfLessonId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lessonId");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final List<Exercise> _result = new ArrayList<Exercise>();
        while (_stmt.step()) {
          final Exercise _item;
          final String _tmpTitle;
          if (_stmt.isNull(_columnIndexOfTitle)) {
            _tmpTitle = null;
          } else {
            _tmpTitle = _stmt.getText(_columnIndexOfTitle);
          }
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          final ExerciseType _tmpType;
          final String _tmp;
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmp = null;
          } else {
            _tmp = _stmt.getText(_columnIndexOfType);
          }
          _tmpType = ExerciseTypeConverter.toExerciseType(_tmp);
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final int _tmpWordCount;
          _tmpWordCount = (int) (_stmt.getLong(_columnIndexOfWordCount));
          final int _tmpPoints;
          _tmpPoints = (int) (_stmt.getLong(_columnIndexOfPoints));
          final boolean _tmpIsCompleted;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp_1 != 0;
          final Date _tmpCreatedAt;
          final Long _tmp_2;
          if (_stmt.isNull(_columnIndexOfCreatedAt)) {
            _tmp_2 = null;
          } else {
            _tmp_2 = _stmt.getLong(_columnIndexOfCreatedAt);
          }
          _tmpCreatedAt = DateConverter.fromTimestamp(_tmp_2);
          final Date _tmpCompletedAt;
          final Long _tmp_3;
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmp_3 = null;
          } else {
            _tmp_3 = _stmt.getLong(_columnIndexOfCompletedAt);
          }
          _tmpCompletedAt = DateConverter.fromTimestamp(_tmp_3);
          final int _tmpCorrectAnswers;
          _tmpCorrectAnswers = (int) (_stmt.getLong(_columnIndexOfCorrectAnswers));
          final int _tmpTotalQuestions;
          _tmpTotalQuestions = (int) (_stmt.getLong(_columnIndexOfTotalQuestions));
          final String _tmpLessonId;
          if (_stmt.isNull(_columnIndexOfLessonId)) {
            _tmpLessonId = null;
          } else {
            _tmpLessonId = _stmt.getText(_columnIndexOfLessonId);
          }
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          _item = new Exercise(_tmpTitle,_tmpDescription,_tmpType,_tmpDifficulty,_tmpWordCount,_tmpPoints,_tmpIsCompleted,_tmpCreatedAt,_tmpCompletedAt,_tmpCorrectAnswers,_tmpTotalQuestions,_tmpLessonId,_tmpCategory);
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Exercise>> getCompletedExercises() {
    final String _sql = "SELECT * FROM exercises WHERE isCompleted = 1 ORDER BY completedAt DESC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"exercises"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "title");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "type");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfWordCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wordCount");
        final int _columnIndexOfPoints = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "points");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _columnIndexOfCompletedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAt");
        final int _columnIndexOfCorrectAnswers = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctAnswers");
        final int _columnIndexOfTotalQuestions = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalQuestions");
        final int _columnIndexOfLessonId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lessonId");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final List<Exercise> _result = new ArrayList<Exercise>();
        while (_stmt.step()) {
          final Exercise _item;
          final String _tmpTitle;
          if (_stmt.isNull(_columnIndexOfTitle)) {
            _tmpTitle = null;
          } else {
            _tmpTitle = _stmt.getText(_columnIndexOfTitle);
          }
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          final ExerciseType _tmpType;
          final String _tmp;
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmp = null;
          } else {
            _tmp = _stmt.getText(_columnIndexOfType);
          }
          _tmpType = ExerciseTypeConverter.toExerciseType(_tmp);
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final int _tmpWordCount;
          _tmpWordCount = (int) (_stmt.getLong(_columnIndexOfWordCount));
          final int _tmpPoints;
          _tmpPoints = (int) (_stmt.getLong(_columnIndexOfPoints));
          final boolean _tmpIsCompleted;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp_1 != 0;
          final Date _tmpCreatedAt;
          final Long _tmp_2;
          if (_stmt.isNull(_columnIndexOfCreatedAt)) {
            _tmp_2 = null;
          } else {
            _tmp_2 = _stmt.getLong(_columnIndexOfCreatedAt);
          }
          _tmpCreatedAt = DateConverter.fromTimestamp(_tmp_2);
          final Date _tmpCompletedAt;
          final Long _tmp_3;
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmp_3 = null;
          } else {
            _tmp_3 = _stmt.getLong(_columnIndexOfCompletedAt);
          }
          _tmpCompletedAt = DateConverter.fromTimestamp(_tmp_3);
          final int _tmpCorrectAnswers;
          _tmpCorrectAnswers = (int) (_stmt.getLong(_columnIndexOfCorrectAnswers));
          final int _tmpTotalQuestions;
          _tmpTotalQuestions = (int) (_stmt.getLong(_columnIndexOfTotalQuestions));
          final String _tmpLessonId;
          if (_stmt.isNull(_columnIndexOfLessonId)) {
            _tmpLessonId = null;
          } else {
            _tmpLessonId = _stmt.getText(_columnIndexOfLessonId);
          }
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          _item = new Exercise(_tmpTitle,_tmpDescription,_tmpType,_tmpDifficulty,_tmpWordCount,_tmpPoints,_tmpIsCompleted,_tmpCreatedAt,_tmpCompletedAt,_tmpCorrectAnswers,_tmpTotalQuestions,_tmpLessonId,_tmpCategory);
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Exercise>> getIncompleteExercises() {
    final String _sql = "SELECT * FROM exercises WHERE isCompleted = 0";
    return __db.getInvalidationTracker().createLiveData(new String[] {"exercises"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "title");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "type");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfWordCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wordCount");
        final int _columnIndexOfPoints = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "points");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _columnIndexOfCompletedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAt");
        final int _columnIndexOfCorrectAnswers = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctAnswers");
        final int _columnIndexOfTotalQuestions = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalQuestions");
        final int _columnIndexOfLessonId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lessonId");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final List<Exercise> _result = new ArrayList<Exercise>();
        while (_stmt.step()) {
          final Exercise _item;
          final String _tmpTitle;
          if (_stmt.isNull(_columnIndexOfTitle)) {
            _tmpTitle = null;
          } else {
            _tmpTitle = _stmt.getText(_columnIndexOfTitle);
          }
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          final ExerciseType _tmpType;
          final String _tmp;
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmp = null;
          } else {
            _tmp = _stmt.getText(_columnIndexOfType);
          }
          _tmpType = ExerciseTypeConverter.toExerciseType(_tmp);
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final int _tmpWordCount;
          _tmpWordCount = (int) (_stmt.getLong(_columnIndexOfWordCount));
          final int _tmpPoints;
          _tmpPoints = (int) (_stmt.getLong(_columnIndexOfPoints));
          final boolean _tmpIsCompleted;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp_1 != 0;
          final Date _tmpCreatedAt;
          final Long _tmp_2;
          if (_stmt.isNull(_columnIndexOfCreatedAt)) {
            _tmp_2 = null;
          } else {
            _tmp_2 = _stmt.getLong(_columnIndexOfCreatedAt);
          }
          _tmpCreatedAt = DateConverter.fromTimestamp(_tmp_2);
          final Date _tmpCompletedAt;
          final Long _tmp_3;
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmp_3 = null;
          } else {
            _tmp_3 = _stmt.getLong(_columnIndexOfCompletedAt);
          }
          _tmpCompletedAt = DateConverter.fromTimestamp(_tmp_3);
          final int _tmpCorrectAnswers;
          _tmpCorrectAnswers = (int) (_stmt.getLong(_columnIndexOfCorrectAnswers));
          final int _tmpTotalQuestions;
          _tmpTotalQuestions = (int) (_stmt.getLong(_columnIndexOfTotalQuestions));
          final String _tmpLessonId;
          if (_stmt.isNull(_columnIndexOfLessonId)) {
            _tmpLessonId = null;
          } else {
            _tmpLessonId = _stmt.getText(_columnIndexOfLessonId);
          }
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          _item = new Exercise(_tmpTitle,_tmpDescription,_tmpType,_tmpDifficulty,_tmpWordCount,_tmpPoints,_tmpIsCompleted,_tmpCreatedAt,_tmpCompletedAt,_tmpCorrectAnswers,_tmpTotalQuestions,_tmpLessonId,_tmpCategory);
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Exercise>> getExercisesByType(final ExerciseType type) {
    final String _sql = "SELECT * FROM exercises WHERE type = ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"exercises"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        final String _tmp = ExerciseTypeConverter.fromExerciseType(type);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, _tmp);
        }
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "title");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "type");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfWordCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wordCount");
        final int _columnIndexOfPoints = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "points");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _columnIndexOfCompletedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAt");
        final int _columnIndexOfCorrectAnswers = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctAnswers");
        final int _columnIndexOfTotalQuestions = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalQuestions");
        final int _columnIndexOfLessonId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lessonId");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final List<Exercise> _result = new ArrayList<Exercise>();
        while (_stmt.step()) {
          final Exercise _item;
          final String _tmpTitle;
          if (_stmt.isNull(_columnIndexOfTitle)) {
            _tmpTitle = null;
          } else {
            _tmpTitle = _stmt.getText(_columnIndexOfTitle);
          }
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          final ExerciseType _tmpType;
          final String _tmp_1;
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmp_1 = null;
          } else {
            _tmp_1 = _stmt.getText(_columnIndexOfType);
          }
          _tmpType = ExerciseTypeConverter.toExerciseType(_tmp_1);
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final int _tmpWordCount;
          _tmpWordCount = (int) (_stmt.getLong(_columnIndexOfWordCount));
          final int _tmpPoints;
          _tmpPoints = (int) (_stmt.getLong(_columnIndexOfPoints));
          final boolean _tmpIsCompleted;
          final int _tmp_2;
          _tmp_2 = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp_2 != 0;
          final Date _tmpCreatedAt;
          final Long _tmp_3;
          if (_stmt.isNull(_columnIndexOfCreatedAt)) {
            _tmp_3 = null;
          } else {
            _tmp_3 = _stmt.getLong(_columnIndexOfCreatedAt);
          }
          _tmpCreatedAt = DateConverter.fromTimestamp(_tmp_3);
          final Date _tmpCompletedAt;
          final Long _tmp_4;
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmp_4 = null;
          } else {
            _tmp_4 = _stmt.getLong(_columnIndexOfCompletedAt);
          }
          _tmpCompletedAt = DateConverter.fromTimestamp(_tmp_4);
          final int _tmpCorrectAnswers;
          _tmpCorrectAnswers = (int) (_stmt.getLong(_columnIndexOfCorrectAnswers));
          final int _tmpTotalQuestions;
          _tmpTotalQuestions = (int) (_stmt.getLong(_columnIndexOfTotalQuestions));
          final String _tmpLessonId;
          if (_stmt.isNull(_columnIndexOfLessonId)) {
            _tmpLessonId = null;
          } else {
            _tmpLessonId = _stmt.getText(_columnIndexOfLessonId);
          }
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          _item = new Exercise(_tmpTitle,_tmpDescription,_tmpType,_tmpDifficulty,_tmpWordCount,_tmpPoints,_tmpIsCompleted,_tmpCreatedAt,_tmpCompletedAt,_tmpCorrectAnswers,_tmpTotalQuestions,_tmpLessonId,_tmpCategory);
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Exercise>> getExercisesByCategory(final String category) {
    final String _sql = "SELECT * FROM exercises WHERE category = ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"exercises"}, false, (_connection) -> {
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
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "type");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfWordCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wordCount");
        final int _columnIndexOfPoints = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "points");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _columnIndexOfCompletedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAt");
        final int _columnIndexOfCorrectAnswers = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctAnswers");
        final int _columnIndexOfTotalQuestions = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalQuestions");
        final int _columnIndexOfLessonId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lessonId");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final List<Exercise> _result = new ArrayList<Exercise>();
        while (_stmt.step()) {
          final Exercise _item;
          final String _tmpTitle;
          if (_stmt.isNull(_columnIndexOfTitle)) {
            _tmpTitle = null;
          } else {
            _tmpTitle = _stmt.getText(_columnIndexOfTitle);
          }
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          final ExerciseType _tmpType;
          final String _tmp;
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmp = null;
          } else {
            _tmp = _stmt.getText(_columnIndexOfType);
          }
          _tmpType = ExerciseTypeConverter.toExerciseType(_tmp);
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final int _tmpWordCount;
          _tmpWordCount = (int) (_stmt.getLong(_columnIndexOfWordCount));
          final int _tmpPoints;
          _tmpPoints = (int) (_stmt.getLong(_columnIndexOfPoints));
          final boolean _tmpIsCompleted;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp_1 != 0;
          final Date _tmpCreatedAt;
          final Long _tmp_2;
          if (_stmt.isNull(_columnIndexOfCreatedAt)) {
            _tmp_2 = null;
          } else {
            _tmp_2 = _stmt.getLong(_columnIndexOfCreatedAt);
          }
          _tmpCreatedAt = DateConverter.fromTimestamp(_tmp_2);
          final Date _tmpCompletedAt;
          final Long _tmp_3;
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmp_3 = null;
          } else {
            _tmp_3 = _stmt.getLong(_columnIndexOfCompletedAt);
          }
          _tmpCompletedAt = DateConverter.fromTimestamp(_tmp_3);
          final int _tmpCorrectAnswers;
          _tmpCorrectAnswers = (int) (_stmt.getLong(_columnIndexOfCorrectAnswers));
          final int _tmpTotalQuestions;
          _tmpTotalQuestions = (int) (_stmt.getLong(_columnIndexOfTotalQuestions));
          final String _tmpLessonId;
          if (_stmt.isNull(_columnIndexOfLessonId)) {
            _tmpLessonId = null;
          } else {
            _tmpLessonId = _stmt.getText(_columnIndexOfLessonId);
          }
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          _item = new Exercise(_tmpTitle,_tmpDescription,_tmpType,_tmpDifficulty,_tmpWordCount,_tmpPoints,_tmpIsCompleted,_tmpCreatedAt,_tmpCompletedAt,_tmpCorrectAnswers,_tmpTotalQuestions,_tmpLessonId,_tmpCategory);
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public int getCompletedExerciseCount() {
    final String _sql = "SELECT COUNT(*) FROM exercises WHERE isCompleted = 1";
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
  public int getCompletedExerciseCountByType(final ExerciseType type) {
    final String _sql = "SELECT COUNT(*) FROM exercises WHERE type = ? AND isCompleted = 1";
    return DBUtil.performBlocking(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        final String _tmp = ExerciseTypeConverter.fromExerciseType(type);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, _tmp);
        }
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
  public float getAverageScorePercentage() {
    final String _sql = "SELECT AVG(correctAnswers * 100.0 / totalQuestions) FROM exercises WHERE isCompleted = 1";
    return DBUtil.performBlocking(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final float _result;
        if (_stmt.step()) {
          _result = (float) (_stmt.getDouble(0));
        } else {
          _result = 0f;
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Exercise>> getRecentExercises(final int limit) {
    final String _sql = "SELECT * FROM exercises ORDER BY id DESC LIMIT ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"exercises"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, limit);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "title");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "type");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfWordCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wordCount");
        final int _columnIndexOfPoints = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "points");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _columnIndexOfCompletedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAt");
        final int _columnIndexOfCorrectAnswers = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctAnswers");
        final int _columnIndexOfTotalQuestions = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalQuestions");
        final int _columnIndexOfLessonId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lessonId");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final List<Exercise> _result = new ArrayList<Exercise>();
        while (_stmt.step()) {
          final Exercise _item;
          final String _tmpTitle;
          if (_stmt.isNull(_columnIndexOfTitle)) {
            _tmpTitle = null;
          } else {
            _tmpTitle = _stmt.getText(_columnIndexOfTitle);
          }
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          final ExerciseType _tmpType;
          final String _tmp;
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmp = null;
          } else {
            _tmp = _stmt.getText(_columnIndexOfType);
          }
          _tmpType = ExerciseTypeConverter.toExerciseType(_tmp);
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final int _tmpWordCount;
          _tmpWordCount = (int) (_stmt.getLong(_columnIndexOfWordCount));
          final int _tmpPoints;
          _tmpPoints = (int) (_stmt.getLong(_columnIndexOfPoints));
          final boolean _tmpIsCompleted;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp_1 != 0;
          final Date _tmpCreatedAt;
          final Long _tmp_2;
          if (_stmt.isNull(_columnIndexOfCreatedAt)) {
            _tmp_2 = null;
          } else {
            _tmp_2 = _stmt.getLong(_columnIndexOfCreatedAt);
          }
          _tmpCreatedAt = DateConverter.fromTimestamp(_tmp_2);
          final Date _tmpCompletedAt;
          final Long _tmp_3;
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmp_3 = null;
          } else {
            _tmp_3 = _stmt.getLong(_columnIndexOfCompletedAt);
          }
          _tmpCompletedAt = DateConverter.fromTimestamp(_tmp_3);
          final int _tmpCorrectAnswers;
          _tmpCorrectAnswers = (int) (_stmt.getLong(_columnIndexOfCorrectAnswers));
          final int _tmpTotalQuestions;
          _tmpTotalQuestions = (int) (_stmt.getLong(_columnIndexOfTotalQuestions));
          final String _tmpLessonId;
          if (_stmt.isNull(_columnIndexOfLessonId)) {
            _tmpLessonId = null;
          } else {
            _tmpLessonId = _stmt.getText(_columnIndexOfLessonId);
          }
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          _item = new Exercise(_tmpTitle,_tmpDescription,_tmpType,_tmpDifficulty,_tmpWordCount,_tmpPoints,_tmpIsCompleted,_tmpCreatedAt,_tmpCompletedAt,_tmpCorrectAnswers,_tmpTotalQuestions,_tmpLessonId,_tmpCategory);
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          _result.add(_item);
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
