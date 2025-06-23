package com.bhashasetu.app.database;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeleteOrUpdateAdapter;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteStatement;
import com.bhashasetu.app.model.Quiz;
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
public final class QuizDao_Impl implements QuizDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<Quiz> __insertAdapterOfQuiz;

  private final EntityDeleteOrUpdateAdapter<Quiz> __deleteAdapterOfQuiz;

  private final EntityDeleteOrUpdateAdapter<Quiz> __updateAdapterOfQuiz;

  public QuizDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfQuiz = new EntityInsertAdapter<Quiz>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `quizzes` (`id`,`lessonId`,`question`,`questionHindi`,`correctAnswer`,`wrongAnswer1`,`wrongAnswer2`,`wrongAnswer3`,`explanation`,`explanationHindi`,`type`,`difficulty`,`mediaUrl`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final Quiz entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getLessonId());
        if (entity.getQuestion() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getQuestion());
        }
        if (entity.getQuestionHindi() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getQuestionHindi());
        }
        if (entity.getCorrectAnswer() == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, entity.getCorrectAnswer());
        }
        if (entity.getWrongAnswer1() == null) {
          statement.bindNull(6);
        } else {
          statement.bindText(6, entity.getWrongAnswer1());
        }
        if (entity.getWrongAnswer2() == null) {
          statement.bindNull(7);
        } else {
          statement.bindText(7, entity.getWrongAnswer2());
        }
        if (entity.getWrongAnswer3() == null) {
          statement.bindNull(8);
        } else {
          statement.bindText(8, entity.getWrongAnswer3());
        }
        if (entity.getExplanation() == null) {
          statement.bindNull(9);
        } else {
          statement.bindText(9, entity.getExplanation());
        }
        if (entity.getExplanationHindi() == null) {
          statement.bindNull(10);
        } else {
          statement.bindText(10, entity.getExplanationHindi());
        }
        if (entity.getType() == null) {
          statement.bindNull(11);
        } else {
          statement.bindText(11, entity.getType());
        }
        statement.bindLong(12, entity.getDifficulty());
        if (entity.getMediaUrl() == null) {
          statement.bindNull(13);
        } else {
          statement.bindText(13, entity.getMediaUrl());
        }
      }
    };
    this.__deleteAdapterOfQuiz = new EntityDeleteOrUpdateAdapter<Quiz>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `quizzes` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final Quiz entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfQuiz = new EntityDeleteOrUpdateAdapter<Quiz>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `quizzes` SET `id` = ?,`lessonId` = ?,`question` = ?,`questionHindi` = ?,`correctAnswer` = ?,`wrongAnswer1` = ?,`wrongAnswer2` = ?,`wrongAnswer3` = ?,`explanation` = ?,`explanationHindi` = ?,`type` = ?,`difficulty` = ?,`mediaUrl` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final Quiz entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getLessonId());
        if (entity.getQuestion() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getQuestion());
        }
        if (entity.getQuestionHindi() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getQuestionHindi());
        }
        if (entity.getCorrectAnswer() == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, entity.getCorrectAnswer());
        }
        if (entity.getWrongAnswer1() == null) {
          statement.bindNull(6);
        } else {
          statement.bindText(6, entity.getWrongAnswer1());
        }
        if (entity.getWrongAnswer2() == null) {
          statement.bindNull(7);
        } else {
          statement.bindText(7, entity.getWrongAnswer2());
        }
        if (entity.getWrongAnswer3() == null) {
          statement.bindNull(8);
        } else {
          statement.bindText(8, entity.getWrongAnswer3());
        }
        if (entity.getExplanation() == null) {
          statement.bindNull(9);
        } else {
          statement.bindText(9, entity.getExplanation());
        }
        if (entity.getExplanationHindi() == null) {
          statement.bindNull(10);
        } else {
          statement.bindText(10, entity.getExplanationHindi());
        }
        if (entity.getType() == null) {
          statement.bindNull(11);
        } else {
          statement.bindText(11, entity.getType());
        }
        statement.bindLong(12, entity.getDifficulty());
        if (entity.getMediaUrl() == null) {
          statement.bindNull(13);
        } else {
          statement.bindText(13, entity.getMediaUrl());
        }
        statement.bindLong(14, entity.getId());
      }
    };
  }

  @Override
  public long insert(final Quiz quiz) {
    return DBUtil.performBlocking(__db, false, true, (_connection) -> {
      return __insertAdapterOfQuiz.insertAndReturnId(_connection, quiz);
    });
  }

  @Override
  public void delete(final Quiz quiz) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __deleteAdapterOfQuiz.handle(_connection, quiz);
      return null;
    });
  }

  @Override
  public void update(final Quiz quiz) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __updateAdapterOfQuiz.handle(_connection, quiz);
      return null;
    });
  }

  @Override
  public LiveData<List<Quiz>> getQuizzesByLesson(final int lessonId) {
    final String _sql = "SELECT * FROM quizzes WHERE lessonId = ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"quizzes"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, lessonId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfLessonId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lessonId");
        final int _columnIndexOfQuestion = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "question");
        final int _columnIndexOfQuestionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "questionHindi");
        final int _columnIndexOfCorrectAnswer = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctAnswer");
        final int _columnIndexOfWrongAnswer1 = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wrongAnswer1");
        final int _columnIndexOfWrongAnswer2 = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wrongAnswer2");
        final int _columnIndexOfWrongAnswer3 = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wrongAnswer3");
        final int _columnIndexOfExplanation = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "explanation");
        final int _columnIndexOfExplanationHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "explanationHindi");
        final int _columnIndexOfType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "type");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfMediaUrl = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "mediaUrl");
        final List<Quiz> _result = new ArrayList<Quiz>();
        while (_stmt.step()) {
          final Quiz _item;
          final int _tmpLessonId;
          _tmpLessonId = (int) (_stmt.getLong(_columnIndexOfLessonId));
          final String _tmpQuestion;
          if (_stmt.isNull(_columnIndexOfQuestion)) {
            _tmpQuestion = null;
          } else {
            _tmpQuestion = _stmt.getText(_columnIndexOfQuestion);
          }
          final String _tmpQuestionHindi;
          if (_stmt.isNull(_columnIndexOfQuestionHindi)) {
            _tmpQuestionHindi = null;
          } else {
            _tmpQuestionHindi = _stmt.getText(_columnIndexOfQuestionHindi);
          }
          final String _tmpCorrectAnswer;
          if (_stmt.isNull(_columnIndexOfCorrectAnswer)) {
            _tmpCorrectAnswer = null;
          } else {
            _tmpCorrectAnswer = _stmt.getText(_columnIndexOfCorrectAnswer);
          }
          final String _tmpWrongAnswer1;
          if (_stmt.isNull(_columnIndexOfWrongAnswer1)) {
            _tmpWrongAnswer1 = null;
          } else {
            _tmpWrongAnswer1 = _stmt.getText(_columnIndexOfWrongAnswer1);
          }
          final String _tmpWrongAnswer2;
          if (_stmt.isNull(_columnIndexOfWrongAnswer2)) {
            _tmpWrongAnswer2 = null;
          } else {
            _tmpWrongAnswer2 = _stmt.getText(_columnIndexOfWrongAnswer2);
          }
          final String _tmpWrongAnswer3;
          if (_stmt.isNull(_columnIndexOfWrongAnswer3)) {
            _tmpWrongAnswer3 = null;
          } else {
            _tmpWrongAnswer3 = _stmt.getText(_columnIndexOfWrongAnswer3);
          }
          final String _tmpExplanation;
          if (_stmt.isNull(_columnIndexOfExplanation)) {
            _tmpExplanation = null;
          } else {
            _tmpExplanation = _stmt.getText(_columnIndexOfExplanation);
          }
          final String _tmpExplanationHindi;
          if (_stmt.isNull(_columnIndexOfExplanationHindi)) {
            _tmpExplanationHindi = null;
          } else {
            _tmpExplanationHindi = _stmt.getText(_columnIndexOfExplanationHindi);
          }
          final String _tmpType;
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmpType = null;
          } else {
            _tmpType = _stmt.getText(_columnIndexOfType);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          _item = new Quiz(_tmpLessonId,_tmpQuestion,_tmpQuestionHindi,_tmpCorrectAnswer,_tmpWrongAnswer1,_tmpWrongAnswer2,_tmpWrongAnswer3,_tmpExplanation,_tmpExplanationHindi,_tmpType,_tmpDifficulty);
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final String _tmpMediaUrl;
          if (_stmt.isNull(_columnIndexOfMediaUrl)) {
            _tmpMediaUrl = null;
          } else {
            _tmpMediaUrl = _stmt.getText(_columnIndexOfMediaUrl);
          }
          _item.setMediaUrl(_tmpMediaUrl);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Quiz>> getRandomQuizzes(final int limit) {
    final String _sql = "SELECT * FROM quizzes ORDER BY RANDOM() LIMIT ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"quizzes"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, limit);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfLessonId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lessonId");
        final int _columnIndexOfQuestion = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "question");
        final int _columnIndexOfQuestionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "questionHindi");
        final int _columnIndexOfCorrectAnswer = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctAnswer");
        final int _columnIndexOfWrongAnswer1 = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wrongAnswer1");
        final int _columnIndexOfWrongAnswer2 = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wrongAnswer2");
        final int _columnIndexOfWrongAnswer3 = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wrongAnswer3");
        final int _columnIndexOfExplanation = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "explanation");
        final int _columnIndexOfExplanationHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "explanationHindi");
        final int _columnIndexOfType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "type");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfMediaUrl = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "mediaUrl");
        final List<Quiz> _result = new ArrayList<Quiz>();
        while (_stmt.step()) {
          final Quiz _item;
          final int _tmpLessonId;
          _tmpLessonId = (int) (_stmt.getLong(_columnIndexOfLessonId));
          final String _tmpQuestion;
          if (_stmt.isNull(_columnIndexOfQuestion)) {
            _tmpQuestion = null;
          } else {
            _tmpQuestion = _stmt.getText(_columnIndexOfQuestion);
          }
          final String _tmpQuestionHindi;
          if (_stmt.isNull(_columnIndexOfQuestionHindi)) {
            _tmpQuestionHindi = null;
          } else {
            _tmpQuestionHindi = _stmt.getText(_columnIndexOfQuestionHindi);
          }
          final String _tmpCorrectAnswer;
          if (_stmt.isNull(_columnIndexOfCorrectAnswer)) {
            _tmpCorrectAnswer = null;
          } else {
            _tmpCorrectAnswer = _stmt.getText(_columnIndexOfCorrectAnswer);
          }
          final String _tmpWrongAnswer1;
          if (_stmt.isNull(_columnIndexOfWrongAnswer1)) {
            _tmpWrongAnswer1 = null;
          } else {
            _tmpWrongAnswer1 = _stmt.getText(_columnIndexOfWrongAnswer1);
          }
          final String _tmpWrongAnswer2;
          if (_stmt.isNull(_columnIndexOfWrongAnswer2)) {
            _tmpWrongAnswer2 = null;
          } else {
            _tmpWrongAnswer2 = _stmt.getText(_columnIndexOfWrongAnswer2);
          }
          final String _tmpWrongAnswer3;
          if (_stmt.isNull(_columnIndexOfWrongAnswer3)) {
            _tmpWrongAnswer3 = null;
          } else {
            _tmpWrongAnswer3 = _stmt.getText(_columnIndexOfWrongAnswer3);
          }
          final String _tmpExplanation;
          if (_stmt.isNull(_columnIndexOfExplanation)) {
            _tmpExplanation = null;
          } else {
            _tmpExplanation = _stmt.getText(_columnIndexOfExplanation);
          }
          final String _tmpExplanationHindi;
          if (_stmt.isNull(_columnIndexOfExplanationHindi)) {
            _tmpExplanationHindi = null;
          } else {
            _tmpExplanationHindi = _stmt.getText(_columnIndexOfExplanationHindi);
          }
          final String _tmpType;
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmpType = null;
          } else {
            _tmpType = _stmt.getText(_columnIndexOfType);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          _item = new Quiz(_tmpLessonId,_tmpQuestion,_tmpQuestionHindi,_tmpCorrectAnswer,_tmpWrongAnswer1,_tmpWrongAnswer2,_tmpWrongAnswer3,_tmpExplanation,_tmpExplanationHindi,_tmpType,_tmpDifficulty);
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final String _tmpMediaUrl;
          if (_stmt.isNull(_columnIndexOfMediaUrl)) {
            _tmpMediaUrl = null;
          } else {
            _tmpMediaUrl = _stmt.getText(_columnIndexOfMediaUrl);
          }
          _item.setMediaUrl(_tmpMediaUrl);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Quiz>> getQuizzesByDifficulty(final int maxDifficulty, final int limit) {
    final String _sql = "SELECT * FROM quizzes WHERE difficulty <= ? ORDER BY RANDOM() LIMIT ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"quizzes"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, maxDifficulty);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, limit);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfLessonId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lessonId");
        final int _columnIndexOfQuestion = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "question");
        final int _columnIndexOfQuestionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "questionHindi");
        final int _columnIndexOfCorrectAnswer = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctAnswer");
        final int _columnIndexOfWrongAnswer1 = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wrongAnswer1");
        final int _columnIndexOfWrongAnswer2 = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wrongAnswer2");
        final int _columnIndexOfWrongAnswer3 = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wrongAnswer3");
        final int _columnIndexOfExplanation = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "explanation");
        final int _columnIndexOfExplanationHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "explanationHindi");
        final int _columnIndexOfType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "type");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfMediaUrl = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "mediaUrl");
        final List<Quiz> _result = new ArrayList<Quiz>();
        while (_stmt.step()) {
          final Quiz _item;
          final int _tmpLessonId;
          _tmpLessonId = (int) (_stmt.getLong(_columnIndexOfLessonId));
          final String _tmpQuestion;
          if (_stmt.isNull(_columnIndexOfQuestion)) {
            _tmpQuestion = null;
          } else {
            _tmpQuestion = _stmt.getText(_columnIndexOfQuestion);
          }
          final String _tmpQuestionHindi;
          if (_stmt.isNull(_columnIndexOfQuestionHindi)) {
            _tmpQuestionHindi = null;
          } else {
            _tmpQuestionHindi = _stmt.getText(_columnIndexOfQuestionHindi);
          }
          final String _tmpCorrectAnswer;
          if (_stmt.isNull(_columnIndexOfCorrectAnswer)) {
            _tmpCorrectAnswer = null;
          } else {
            _tmpCorrectAnswer = _stmt.getText(_columnIndexOfCorrectAnswer);
          }
          final String _tmpWrongAnswer1;
          if (_stmt.isNull(_columnIndexOfWrongAnswer1)) {
            _tmpWrongAnswer1 = null;
          } else {
            _tmpWrongAnswer1 = _stmt.getText(_columnIndexOfWrongAnswer1);
          }
          final String _tmpWrongAnswer2;
          if (_stmt.isNull(_columnIndexOfWrongAnswer2)) {
            _tmpWrongAnswer2 = null;
          } else {
            _tmpWrongAnswer2 = _stmt.getText(_columnIndexOfWrongAnswer2);
          }
          final String _tmpWrongAnswer3;
          if (_stmt.isNull(_columnIndexOfWrongAnswer3)) {
            _tmpWrongAnswer3 = null;
          } else {
            _tmpWrongAnswer3 = _stmt.getText(_columnIndexOfWrongAnswer3);
          }
          final String _tmpExplanation;
          if (_stmt.isNull(_columnIndexOfExplanation)) {
            _tmpExplanation = null;
          } else {
            _tmpExplanation = _stmt.getText(_columnIndexOfExplanation);
          }
          final String _tmpExplanationHindi;
          if (_stmt.isNull(_columnIndexOfExplanationHindi)) {
            _tmpExplanationHindi = null;
          } else {
            _tmpExplanationHindi = _stmt.getText(_columnIndexOfExplanationHindi);
          }
          final String _tmpType;
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmpType = null;
          } else {
            _tmpType = _stmt.getText(_columnIndexOfType);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          _item = new Quiz(_tmpLessonId,_tmpQuestion,_tmpQuestionHindi,_tmpCorrectAnswer,_tmpWrongAnswer1,_tmpWrongAnswer2,_tmpWrongAnswer3,_tmpExplanation,_tmpExplanationHindi,_tmpType,_tmpDifficulty);
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final String _tmpMediaUrl;
          if (_stmt.isNull(_columnIndexOfMediaUrl)) {
            _tmpMediaUrl = null;
          } else {
            _tmpMediaUrl = _stmt.getText(_columnIndexOfMediaUrl);
          }
          _item.setMediaUrl(_tmpMediaUrl);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<Quiz> getQuizById(final int quizId) {
    final String _sql = "SELECT * FROM quizzes WHERE id = ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"quizzes"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, quizId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfLessonId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lessonId");
        final int _columnIndexOfQuestion = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "question");
        final int _columnIndexOfQuestionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "questionHindi");
        final int _columnIndexOfCorrectAnswer = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctAnswer");
        final int _columnIndexOfWrongAnswer1 = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wrongAnswer1");
        final int _columnIndexOfWrongAnswer2 = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wrongAnswer2");
        final int _columnIndexOfWrongAnswer3 = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wrongAnswer3");
        final int _columnIndexOfExplanation = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "explanation");
        final int _columnIndexOfExplanationHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "explanationHindi");
        final int _columnIndexOfType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "type");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfMediaUrl = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "mediaUrl");
        final Quiz _result;
        if (_stmt.step()) {
          final int _tmpLessonId;
          _tmpLessonId = (int) (_stmt.getLong(_columnIndexOfLessonId));
          final String _tmpQuestion;
          if (_stmt.isNull(_columnIndexOfQuestion)) {
            _tmpQuestion = null;
          } else {
            _tmpQuestion = _stmt.getText(_columnIndexOfQuestion);
          }
          final String _tmpQuestionHindi;
          if (_stmt.isNull(_columnIndexOfQuestionHindi)) {
            _tmpQuestionHindi = null;
          } else {
            _tmpQuestionHindi = _stmt.getText(_columnIndexOfQuestionHindi);
          }
          final String _tmpCorrectAnswer;
          if (_stmt.isNull(_columnIndexOfCorrectAnswer)) {
            _tmpCorrectAnswer = null;
          } else {
            _tmpCorrectAnswer = _stmt.getText(_columnIndexOfCorrectAnswer);
          }
          final String _tmpWrongAnswer1;
          if (_stmt.isNull(_columnIndexOfWrongAnswer1)) {
            _tmpWrongAnswer1 = null;
          } else {
            _tmpWrongAnswer1 = _stmt.getText(_columnIndexOfWrongAnswer1);
          }
          final String _tmpWrongAnswer2;
          if (_stmt.isNull(_columnIndexOfWrongAnswer2)) {
            _tmpWrongAnswer2 = null;
          } else {
            _tmpWrongAnswer2 = _stmt.getText(_columnIndexOfWrongAnswer2);
          }
          final String _tmpWrongAnswer3;
          if (_stmt.isNull(_columnIndexOfWrongAnswer3)) {
            _tmpWrongAnswer3 = null;
          } else {
            _tmpWrongAnswer3 = _stmt.getText(_columnIndexOfWrongAnswer3);
          }
          final String _tmpExplanation;
          if (_stmt.isNull(_columnIndexOfExplanation)) {
            _tmpExplanation = null;
          } else {
            _tmpExplanation = _stmt.getText(_columnIndexOfExplanation);
          }
          final String _tmpExplanationHindi;
          if (_stmt.isNull(_columnIndexOfExplanationHindi)) {
            _tmpExplanationHindi = null;
          } else {
            _tmpExplanationHindi = _stmt.getText(_columnIndexOfExplanationHindi);
          }
          final String _tmpType;
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmpType = null;
          } else {
            _tmpType = _stmt.getText(_columnIndexOfType);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          _result = new Quiz(_tmpLessonId,_tmpQuestion,_tmpQuestionHindi,_tmpCorrectAnswer,_tmpWrongAnswer1,_tmpWrongAnswer2,_tmpWrongAnswer3,_tmpExplanation,_tmpExplanationHindi,_tmpType,_tmpDifficulty);
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _result.setId(_tmpId);
          final String _tmpMediaUrl;
          if (_stmt.isNull(_columnIndexOfMediaUrl)) {
            _tmpMediaUrl = null;
          } else {
            _tmpMediaUrl = _stmt.getText(_columnIndexOfMediaUrl);
          }
          _result.setMediaUrl(_tmpMediaUrl);
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
  public int getQuizCountForLesson(final int lessonId) {
    final String _sql = "SELECT COUNT(*) FROM quizzes WHERE lessonId = ?";
    return DBUtil.performBlocking(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, lessonId);
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
  public LiveData<List<Quiz>> getQuizzesByType(final String quizType, final int limit) {
    final String _sql = "SELECT * FROM quizzes WHERE type = ? ORDER BY RANDOM() LIMIT ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"quizzes"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (quizType == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, quizType);
        }
        _argIndex = 2;
        _stmt.bindLong(_argIndex, limit);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfLessonId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lessonId");
        final int _columnIndexOfQuestion = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "question");
        final int _columnIndexOfQuestionHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "questionHindi");
        final int _columnIndexOfCorrectAnswer = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctAnswer");
        final int _columnIndexOfWrongAnswer1 = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wrongAnswer1");
        final int _columnIndexOfWrongAnswer2 = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wrongAnswer2");
        final int _columnIndexOfWrongAnswer3 = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wrongAnswer3");
        final int _columnIndexOfExplanation = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "explanation");
        final int _columnIndexOfExplanationHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "explanationHindi");
        final int _columnIndexOfType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "type");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfMediaUrl = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "mediaUrl");
        final List<Quiz> _result = new ArrayList<Quiz>();
        while (_stmt.step()) {
          final Quiz _item;
          final int _tmpLessonId;
          _tmpLessonId = (int) (_stmt.getLong(_columnIndexOfLessonId));
          final String _tmpQuestion;
          if (_stmt.isNull(_columnIndexOfQuestion)) {
            _tmpQuestion = null;
          } else {
            _tmpQuestion = _stmt.getText(_columnIndexOfQuestion);
          }
          final String _tmpQuestionHindi;
          if (_stmt.isNull(_columnIndexOfQuestionHindi)) {
            _tmpQuestionHindi = null;
          } else {
            _tmpQuestionHindi = _stmt.getText(_columnIndexOfQuestionHindi);
          }
          final String _tmpCorrectAnswer;
          if (_stmt.isNull(_columnIndexOfCorrectAnswer)) {
            _tmpCorrectAnswer = null;
          } else {
            _tmpCorrectAnswer = _stmt.getText(_columnIndexOfCorrectAnswer);
          }
          final String _tmpWrongAnswer1;
          if (_stmt.isNull(_columnIndexOfWrongAnswer1)) {
            _tmpWrongAnswer1 = null;
          } else {
            _tmpWrongAnswer1 = _stmt.getText(_columnIndexOfWrongAnswer1);
          }
          final String _tmpWrongAnswer2;
          if (_stmt.isNull(_columnIndexOfWrongAnswer2)) {
            _tmpWrongAnswer2 = null;
          } else {
            _tmpWrongAnswer2 = _stmt.getText(_columnIndexOfWrongAnswer2);
          }
          final String _tmpWrongAnswer3;
          if (_stmt.isNull(_columnIndexOfWrongAnswer3)) {
            _tmpWrongAnswer3 = null;
          } else {
            _tmpWrongAnswer3 = _stmt.getText(_columnIndexOfWrongAnswer3);
          }
          final String _tmpExplanation;
          if (_stmt.isNull(_columnIndexOfExplanation)) {
            _tmpExplanation = null;
          } else {
            _tmpExplanation = _stmt.getText(_columnIndexOfExplanation);
          }
          final String _tmpExplanationHindi;
          if (_stmt.isNull(_columnIndexOfExplanationHindi)) {
            _tmpExplanationHindi = null;
          } else {
            _tmpExplanationHindi = _stmt.getText(_columnIndexOfExplanationHindi);
          }
          final String _tmpType;
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmpType = null;
          } else {
            _tmpType = _stmt.getText(_columnIndexOfType);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          _item = new Quiz(_tmpLessonId,_tmpQuestion,_tmpQuestionHindi,_tmpCorrectAnswer,_tmpWrongAnswer1,_tmpWrongAnswer2,_tmpWrongAnswer3,_tmpExplanation,_tmpExplanationHindi,_tmpType,_tmpDifficulty);
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final String _tmpMediaUrl;
          if (_stmt.isNull(_columnIndexOfMediaUrl)) {
            _tmpMediaUrl = null;
          } else {
            _tmpMediaUrl = _stmt.getText(_columnIndexOfMediaUrl);
          }
          _item.setMediaUrl(_tmpMediaUrl);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public void insertQuizDetails(final int lessonId, final String question,
      final String questionHindi, final String correctAnswer, final String wrongAnswer1,
      final String wrongAnswer2, final String wrongAnswer3, final String explanation,
      final String explanationHindi, final String type, final int difficulty) {
    final String _sql = "INSERT INTO quizzes (lessonId, question, questionHindi, correctAnswer, wrongAnswer1, wrongAnswer2, wrongAnswer3, explanation, explanationHindi, type, difficulty) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, lessonId);
        _argIndex = 2;
        if (question == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, question);
        }
        _argIndex = 3;
        if (questionHindi == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, questionHindi);
        }
        _argIndex = 4;
        if (correctAnswer == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, correctAnswer);
        }
        _argIndex = 5;
        if (wrongAnswer1 == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, wrongAnswer1);
        }
        _argIndex = 6;
        if (wrongAnswer2 == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, wrongAnswer2);
        }
        _argIndex = 7;
        if (wrongAnswer3 == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, wrongAnswer3);
        }
        _argIndex = 8;
        if (explanation == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, explanation);
        }
        _argIndex = 9;
        if (explanationHindi == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, explanationHindi);
        }
        _argIndex = 10;
        if (type == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, type);
        }
        _argIndex = 11;
        _stmt.bindLong(_argIndex, difficulty);
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
