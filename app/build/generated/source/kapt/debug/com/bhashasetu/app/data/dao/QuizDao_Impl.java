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
import com.bhashasetu.app.data.model.Quiz;
import com.bhashasetu.app.data.model.QuizQuestion;
import com.bhashasetu.app.data.relation.QuizWithQuestions;
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
public final class QuizDao_Impl implements QuizDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<Quiz> __insertAdapterOfQuiz;

  private final EntityInsertAdapter<QuizQuestion> __insertAdapterOfQuizQuestion;

  private final EntityDeleteOrUpdateAdapter<Quiz> __deleteAdapterOfQuiz;

  private final EntityDeleteOrUpdateAdapter<QuizQuestion> __deleteAdapterOfQuizQuestion;

  private final EntityDeleteOrUpdateAdapter<Quiz> __updateAdapterOfQuiz;

  private final EntityDeleteOrUpdateAdapter<QuizQuestion> __updateAdapterOfQuizQuestion;

  public QuizDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfQuiz = new EntityInsertAdapter<Quiz>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `quizzes` (`id`,`titleEnglish`,`titleHindi`,`instructionsEnglish`,`instructionsHindi`,`lessonId`,`categoryId`,`difficulty`,`questionCount`,`timeLimit`,`passingScorePercent`,`quizType`,`directionType`,`isCompleted`,`lastAttemptScore`,`bestScore`,`attemptCount`,`lastAttemptAt`,`completedAt`,`createdAt`,`isSystemQuiz`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, @NonNull final Quiz entity) {
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
        if (entity.getInstructionsEnglish() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getInstructionsEnglish());
        }
        if (entity.getInstructionsHindi() == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, entity.getInstructionsHindi());
        }
        if (entity.getLessonId() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getLessonId());
        }
        if (entity.getCategoryId() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getCategoryId());
        }
        statement.bindLong(8, entity.getDifficulty());
        statement.bindLong(9, entity.getQuestionCount());
        if (entity.getTimeLimit() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getTimeLimit());
        }
        statement.bindLong(11, entity.getPassingScorePercent());
        statement.bindLong(12, entity.getQuizType());
        statement.bindLong(13, entity.getDirectionType());
        final int _tmp = entity.isCompleted() ? 1 : 0;
        statement.bindLong(14, _tmp);
        if (entity.getLastAttemptScore() == null) {
          statement.bindNull(15);
        } else {
          statement.bindLong(15, entity.getLastAttemptScore());
        }
        if (entity.getBestScore() == null) {
          statement.bindNull(16);
        } else {
          statement.bindLong(16, entity.getBestScore());
        }
        statement.bindLong(17, entity.getAttemptCount());
        if (entity.getLastAttemptAt() == null) {
          statement.bindNull(18);
        } else {
          statement.bindLong(18, entity.getLastAttemptAt());
        }
        if (entity.getCompletedAt() == null) {
          statement.bindNull(19);
        } else {
          statement.bindLong(19, entity.getCompletedAt());
        }
        statement.bindLong(20, entity.getCreatedAt());
        final int _tmp_1 = entity.isSystemQuiz() ? 1 : 0;
        statement.bindLong(21, _tmp_1);
      }
    };
    this.__insertAdapterOfQuizQuestion = new EntityInsertAdapter<QuizQuestion>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `quiz_questions` (`id`,`quizId`,`wordId`,`questionTextEnglish`,`questionTextHindi`,`questionType`,`correctAnswer`,`options`,`imageUrl`,`audioUrl`,`explanationEnglish`,`explanationHindi`,`difficulty`,`points`,`orderInQuiz`,`correctAttempts`,`totalAttempts`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final QuizQuestion entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getQuizId());
        if (entity.getWordId() == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, entity.getWordId());
        }
        if (entity.getQuestionTextEnglish() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getQuestionTextEnglish());
        }
        if (entity.getQuestionTextHindi() == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, entity.getQuestionTextHindi());
        }
        statement.bindLong(6, entity.getQuestionType());
        if (entity.getCorrectAnswer() == null) {
          statement.bindNull(7);
        } else {
          statement.bindText(7, entity.getCorrectAnswer());
        }
        if (entity.getOptions() == null) {
          statement.bindNull(8);
        } else {
          statement.bindText(8, entity.getOptions());
        }
        if (entity.getImageUrl() == null) {
          statement.bindNull(9);
        } else {
          statement.bindText(9, entity.getImageUrl());
        }
        if (entity.getAudioUrl() == null) {
          statement.bindNull(10);
        } else {
          statement.bindText(10, entity.getAudioUrl());
        }
        if (entity.getExplanationEnglish() == null) {
          statement.bindNull(11);
        } else {
          statement.bindText(11, entity.getExplanationEnglish());
        }
        if (entity.getExplanationHindi() == null) {
          statement.bindNull(12);
        } else {
          statement.bindText(12, entity.getExplanationHindi());
        }
        statement.bindLong(13, entity.getDifficulty());
        statement.bindLong(14, entity.getPoints());
        statement.bindLong(15, entity.getOrderInQuiz());
        statement.bindLong(16, entity.getCorrectAttempts());
        statement.bindLong(17, entity.getTotalAttempts());
      }
    };
    this.__deleteAdapterOfQuiz = new EntityDeleteOrUpdateAdapter<Quiz>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `quizzes` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, @NonNull final Quiz entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__deleteAdapterOfQuizQuestion = new EntityDeleteOrUpdateAdapter<QuizQuestion>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `quiz_questions` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final QuizQuestion entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfQuiz = new EntityDeleteOrUpdateAdapter<Quiz>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `quizzes` SET `id` = ?,`titleEnglish` = ?,`titleHindi` = ?,`instructionsEnglish` = ?,`instructionsHindi` = ?,`lessonId` = ?,`categoryId` = ?,`difficulty` = ?,`questionCount` = ?,`timeLimit` = ?,`passingScorePercent` = ?,`quizType` = ?,`directionType` = ?,`isCompleted` = ?,`lastAttemptScore` = ?,`bestScore` = ?,`attemptCount` = ?,`lastAttemptAt` = ?,`completedAt` = ?,`createdAt` = ?,`isSystemQuiz` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, @NonNull final Quiz entity) {
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
        if (entity.getInstructionsEnglish() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getInstructionsEnglish());
        }
        if (entity.getInstructionsHindi() == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, entity.getInstructionsHindi());
        }
        if (entity.getLessonId() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getLessonId());
        }
        if (entity.getCategoryId() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getCategoryId());
        }
        statement.bindLong(8, entity.getDifficulty());
        statement.bindLong(9, entity.getQuestionCount());
        if (entity.getTimeLimit() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getTimeLimit());
        }
        statement.bindLong(11, entity.getPassingScorePercent());
        statement.bindLong(12, entity.getQuizType());
        statement.bindLong(13, entity.getDirectionType());
        final int _tmp = entity.isCompleted() ? 1 : 0;
        statement.bindLong(14, _tmp);
        if (entity.getLastAttemptScore() == null) {
          statement.bindNull(15);
        } else {
          statement.bindLong(15, entity.getLastAttemptScore());
        }
        if (entity.getBestScore() == null) {
          statement.bindNull(16);
        } else {
          statement.bindLong(16, entity.getBestScore());
        }
        statement.bindLong(17, entity.getAttemptCount());
        if (entity.getLastAttemptAt() == null) {
          statement.bindNull(18);
        } else {
          statement.bindLong(18, entity.getLastAttemptAt());
        }
        if (entity.getCompletedAt() == null) {
          statement.bindNull(19);
        } else {
          statement.bindLong(19, entity.getCompletedAt());
        }
        statement.bindLong(20, entity.getCreatedAt());
        final int _tmp_1 = entity.isSystemQuiz() ? 1 : 0;
        statement.bindLong(21, _tmp_1);
        statement.bindLong(22, entity.getId());
      }
    };
    this.__updateAdapterOfQuizQuestion = new EntityDeleteOrUpdateAdapter<QuizQuestion>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `quiz_questions` SET `id` = ?,`quizId` = ?,`wordId` = ?,`questionTextEnglish` = ?,`questionTextHindi` = ?,`questionType` = ?,`correctAnswer` = ?,`options` = ?,`imageUrl` = ?,`audioUrl` = ?,`explanationEnglish` = ?,`explanationHindi` = ?,`difficulty` = ?,`points` = ?,`orderInQuiz` = ?,`correctAttempts` = ?,`totalAttempts` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final QuizQuestion entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getQuizId());
        if (entity.getWordId() == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, entity.getWordId());
        }
        if (entity.getQuestionTextEnglish() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getQuestionTextEnglish());
        }
        if (entity.getQuestionTextHindi() == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, entity.getQuestionTextHindi());
        }
        statement.bindLong(6, entity.getQuestionType());
        if (entity.getCorrectAnswer() == null) {
          statement.bindNull(7);
        } else {
          statement.bindText(7, entity.getCorrectAnswer());
        }
        if (entity.getOptions() == null) {
          statement.bindNull(8);
        } else {
          statement.bindText(8, entity.getOptions());
        }
        if (entity.getImageUrl() == null) {
          statement.bindNull(9);
        } else {
          statement.bindText(9, entity.getImageUrl());
        }
        if (entity.getAudioUrl() == null) {
          statement.bindNull(10);
        } else {
          statement.bindText(10, entity.getAudioUrl());
        }
        if (entity.getExplanationEnglish() == null) {
          statement.bindNull(11);
        } else {
          statement.bindText(11, entity.getExplanationEnglish());
        }
        if (entity.getExplanationHindi() == null) {
          statement.bindNull(12);
        } else {
          statement.bindText(12, entity.getExplanationHindi());
        }
        statement.bindLong(13, entity.getDifficulty());
        statement.bindLong(14, entity.getPoints());
        statement.bindLong(15, entity.getOrderInQuiz());
        statement.bindLong(16, entity.getCorrectAttempts());
        statement.bindLong(17, entity.getTotalAttempts());
        statement.bindLong(18, entity.getId());
      }
    };
  }

  @Override
  public Object insert(final Quiz quiz, final Continuation<? super Long> $completion) {
    if (quiz == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      return __insertAdapterOfQuiz.insertAndReturnId(_connection, quiz);
    }, $completion);
  }

  @Override
  public Object insertAll(final List<Quiz> quizzes,
      final Continuation<? super List<Long>> $completion) {
    if (quizzes == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      return __insertAdapterOfQuiz.insertAndReturnIdsList(_connection, quizzes);
    }, $completion);
  }

  @Override
  public Object insertQuestion(final QuizQuestion question,
      final Continuation<? super Long> $completion) {
    if (question == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      return __insertAdapterOfQuizQuestion.insertAndReturnId(_connection, question);
    }, $completion);
  }

  @Override
  public Object insertQuestions(final List<QuizQuestion> questions,
      final Continuation<? super List<Long>> $completion) {
    if (questions == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      return __insertAdapterOfQuizQuestion.insertAndReturnIdsList(_connection, questions);
    }, $completion);
  }

  @Override
  public Object delete(final Quiz quiz, final Continuation<? super Unit> $completion) {
    if (quiz == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __deleteAdapterOfQuiz.handle(_connection, quiz);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Object deleteQuestion(final QuizQuestion question,
      final Continuation<? super Unit> $completion) {
    if (question == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __deleteAdapterOfQuizQuestion.handle(_connection, question);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Object update(final Quiz quiz, final Continuation<? super Unit> $completion) {
    if (quiz == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __updateAdapterOfQuiz.handle(_connection, quiz);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Object updateQuestion(final QuizQuestion question,
      final Continuation<? super Unit> $completion) {
    if (question == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __updateAdapterOfQuizQuestion.handle(_connection, question);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Object getQuizById(final long quizId, final Continuation<? super Quiz> $completion) {
    final String _sql = "SELECT * FROM quizzes WHERE id = ?";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, quizId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitleEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleEnglish");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfInstructionsEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "instructionsEnglish");
        final int _columnIndexOfInstructionsHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "instructionsHindi");
        final int _columnIndexOfLessonId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lessonId");
        final int _columnIndexOfCategoryId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "categoryId");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfQuestionCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "questionCount");
        final int _columnIndexOfTimeLimit = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timeLimit");
        final int _columnIndexOfPassingScorePercent = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "passingScorePercent");
        final int _columnIndexOfQuizType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "quizType");
        final int _columnIndexOfDirectionType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "directionType");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfLastAttemptScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAttemptScore");
        final int _columnIndexOfBestScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "bestScore");
        final int _columnIndexOfAttemptCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "attemptCount");
        final int _columnIndexOfLastAttemptAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAttemptAt");
        final int _columnIndexOfCompletedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAt");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _columnIndexOfIsSystemQuiz = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isSystemQuiz");
        final Quiz _result;
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
          final String _tmpInstructionsEnglish;
          if (_stmt.isNull(_columnIndexOfInstructionsEnglish)) {
            _tmpInstructionsEnglish = null;
          } else {
            _tmpInstructionsEnglish = _stmt.getText(_columnIndexOfInstructionsEnglish);
          }
          final String _tmpInstructionsHindi;
          if (_stmt.isNull(_columnIndexOfInstructionsHindi)) {
            _tmpInstructionsHindi = null;
          } else {
            _tmpInstructionsHindi = _stmt.getText(_columnIndexOfInstructionsHindi);
          }
          final Long _tmpLessonId;
          if (_stmt.isNull(_columnIndexOfLessonId)) {
            _tmpLessonId = null;
          } else {
            _tmpLessonId = _stmt.getLong(_columnIndexOfLessonId);
          }
          final Long _tmpCategoryId;
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null;
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final int _tmpQuestionCount;
          _tmpQuestionCount = (int) (_stmt.getLong(_columnIndexOfQuestionCount));
          final Integer _tmpTimeLimit;
          if (_stmt.isNull(_columnIndexOfTimeLimit)) {
            _tmpTimeLimit = null;
          } else {
            _tmpTimeLimit = (int) (_stmt.getLong(_columnIndexOfTimeLimit));
          }
          final int _tmpPassingScorePercent;
          _tmpPassingScorePercent = (int) (_stmt.getLong(_columnIndexOfPassingScorePercent));
          final int _tmpQuizType;
          _tmpQuizType = (int) (_stmt.getLong(_columnIndexOfQuizType));
          final int _tmpDirectionType;
          _tmpDirectionType = (int) (_stmt.getLong(_columnIndexOfDirectionType));
          final boolean _tmpIsCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp != 0;
          final Integer _tmpLastAttemptScore;
          if (_stmt.isNull(_columnIndexOfLastAttemptScore)) {
            _tmpLastAttemptScore = null;
          } else {
            _tmpLastAttemptScore = (int) (_stmt.getLong(_columnIndexOfLastAttemptScore));
          }
          final Integer _tmpBestScore;
          if (_stmt.isNull(_columnIndexOfBestScore)) {
            _tmpBestScore = null;
          } else {
            _tmpBestScore = (int) (_stmt.getLong(_columnIndexOfBestScore));
          }
          final int _tmpAttemptCount;
          _tmpAttemptCount = (int) (_stmt.getLong(_columnIndexOfAttemptCount));
          final Long _tmpLastAttemptAt;
          if (_stmt.isNull(_columnIndexOfLastAttemptAt)) {
            _tmpLastAttemptAt = null;
          } else {
            _tmpLastAttemptAt = _stmt.getLong(_columnIndexOfLastAttemptAt);
          }
          final Long _tmpCompletedAt;
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmpCompletedAt = null;
          } else {
            _tmpCompletedAt = _stmt.getLong(_columnIndexOfCompletedAt);
          }
          final long _tmpCreatedAt;
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt);
          final boolean _tmpIsSystemQuiz;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsSystemQuiz));
          _tmpIsSystemQuiz = _tmp_1 != 0;
          _result = new Quiz(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpInstructionsEnglish,_tmpInstructionsHindi,_tmpLessonId,_tmpCategoryId,_tmpDifficulty,_tmpQuestionCount,_tmpTimeLimit,_tmpPassingScorePercent,_tmpQuizType,_tmpDirectionType,_tmpIsCompleted,_tmpLastAttemptScore,_tmpBestScore,_tmpAttemptCount,_tmpLastAttemptAt,_tmpCompletedAt,_tmpCreatedAt,_tmpIsSystemQuiz);
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
  public LiveData<Quiz> getQuizByIdLiveData(final long quizId) {
    final String _sql = "SELECT * FROM quizzes WHERE id = ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"quizzes"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, quizId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitleEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleEnglish");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfInstructionsEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "instructionsEnglish");
        final int _columnIndexOfInstructionsHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "instructionsHindi");
        final int _columnIndexOfLessonId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lessonId");
        final int _columnIndexOfCategoryId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "categoryId");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfQuestionCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "questionCount");
        final int _columnIndexOfTimeLimit = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timeLimit");
        final int _columnIndexOfPassingScorePercent = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "passingScorePercent");
        final int _columnIndexOfQuizType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "quizType");
        final int _columnIndexOfDirectionType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "directionType");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfLastAttemptScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAttemptScore");
        final int _columnIndexOfBestScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "bestScore");
        final int _columnIndexOfAttemptCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "attemptCount");
        final int _columnIndexOfLastAttemptAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAttemptAt");
        final int _columnIndexOfCompletedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAt");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _columnIndexOfIsSystemQuiz = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isSystemQuiz");
        final Quiz _result;
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
          final String _tmpInstructionsEnglish;
          if (_stmt.isNull(_columnIndexOfInstructionsEnglish)) {
            _tmpInstructionsEnglish = null;
          } else {
            _tmpInstructionsEnglish = _stmt.getText(_columnIndexOfInstructionsEnglish);
          }
          final String _tmpInstructionsHindi;
          if (_stmt.isNull(_columnIndexOfInstructionsHindi)) {
            _tmpInstructionsHindi = null;
          } else {
            _tmpInstructionsHindi = _stmt.getText(_columnIndexOfInstructionsHindi);
          }
          final Long _tmpLessonId;
          if (_stmt.isNull(_columnIndexOfLessonId)) {
            _tmpLessonId = null;
          } else {
            _tmpLessonId = _stmt.getLong(_columnIndexOfLessonId);
          }
          final Long _tmpCategoryId;
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null;
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final int _tmpQuestionCount;
          _tmpQuestionCount = (int) (_stmt.getLong(_columnIndexOfQuestionCount));
          final Integer _tmpTimeLimit;
          if (_stmt.isNull(_columnIndexOfTimeLimit)) {
            _tmpTimeLimit = null;
          } else {
            _tmpTimeLimit = (int) (_stmt.getLong(_columnIndexOfTimeLimit));
          }
          final int _tmpPassingScorePercent;
          _tmpPassingScorePercent = (int) (_stmt.getLong(_columnIndexOfPassingScorePercent));
          final int _tmpQuizType;
          _tmpQuizType = (int) (_stmt.getLong(_columnIndexOfQuizType));
          final int _tmpDirectionType;
          _tmpDirectionType = (int) (_stmt.getLong(_columnIndexOfDirectionType));
          final boolean _tmpIsCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp != 0;
          final Integer _tmpLastAttemptScore;
          if (_stmt.isNull(_columnIndexOfLastAttemptScore)) {
            _tmpLastAttemptScore = null;
          } else {
            _tmpLastAttemptScore = (int) (_stmt.getLong(_columnIndexOfLastAttemptScore));
          }
          final Integer _tmpBestScore;
          if (_stmt.isNull(_columnIndexOfBestScore)) {
            _tmpBestScore = null;
          } else {
            _tmpBestScore = (int) (_stmt.getLong(_columnIndexOfBestScore));
          }
          final int _tmpAttemptCount;
          _tmpAttemptCount = (int) (_stmt.getLong(_columnIndexOfAttemptCount));
          final Long _tmpLastAttemptAt;
          if (_stmt.isNull(_columnIndexOfLastAttemptAt)) {
            _tmpLastAttemptAt = null;
          } else {
            _tmpLastAttemptAt = _stmt.getLong(_columnIndexOfLastAttemptAt);
          }
          final Long _tmpCompletedAt;
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmpCompletedAt = null;
          } else {
            _tmpCompletedAt = _stmt.getLong(_columnIndexOfCompletedAt);
          }
          final long _tmpCreatedAt;
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt);
          final boolean _tmpIsSystemQuiz;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsSystemQuiz));
          _tmpIsSystemQuiz = _tmp_1 != 0;
          _result = new Quiz(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpInstructionsEnglish,_tmpInstructionsHindi,_tmpLessonId,_tmpCategoryId,_tmpDifficulty,_tmpQuestionCount,_tmpTimeLimit,_tmpPassingScorePercent,_tmpQuizType,_tmpDirectionType,_tmpIsCompleted,_tmpLastAttemptScore,_tmpBestScore,_tmpAttemptCount,_tmpLastAttemptAt,_tmpCompletedAt,_tmpCreatedAt,_tmpIsSystemQuiz);
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
  public Flow<Quiz> getQuizByIdFlow(final long quizId) {
    final String _sql = "SELECT * FROM quizzes WHERE id = ?";
    return FlowUtil.createFlow(__db, false, new String[] {"quizzes"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, quizId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitleEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleEnglish");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfInstructionsEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "instructionsEnglish");
        final int _columnIndexOfInstructionsHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "instructionsHindi");
        final int _columnIndexOfLessonId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lessonId");
        final int _columnIndexOfCategoryId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "categoryId");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfQuestionCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "questionCount");
        final int _columnIndexOfTimeLimit = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timeLimit");
        final int _columnIndexOfPassingScorePercent = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "passingScorePercent");
        final int _columnIndexOfQuizType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "quizType");
        final int _columnIndexOfDirectionType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "directionType");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfLastAttemptScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAttemptScore");
        final int _columnIndexOfBestScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "bestScore");
        final int _columnIndexOfAttemptCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "attemptCount");
        final int _columnIndexOfLastAttemptAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAttemptAt");
        final int _columnIndexOfCompletedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAt");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _columnIndexOfIsSystemQuiz = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isSystemQuiz");
        final Quiz _result;
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
          final String _tmpInstructionsEnglish;
          if (_stmt.isNull(_columnIndexOfInstructionsEnglish)) {
            _tmpInstructionsEnglish = null;
          } else {
            _tmpInstructionsEnglish = _stmt.getText(_columnIndexOfInstructionsEnglish);
          }
          final String _tmpInstructionsHindi;
          if (_stmt.isNull(_columnIndexOfInstructionsHindi)) {
            _tmpInstructionsHindi = null;
          } else {
            _tmpInstructionsHindi = _stmt.getText(_columnIndexOfInstructionsHindi);
          }
          final Long _tmpLessonId;
          if (_stmt.isNull(_columnIndexOfLessonId)) {
            _tmpLessonId = null;
          } else {
            _tmpLessonId = _stmt.getLong(_columnIndexOfLessonId);
          }
          final Long _tmpCategoryId;
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null;
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final int _tmpQuestionCount;
          _tmpQuestionCount = (int) (_stmt.getLong(_columnIndexOfQuestionCount));
          final Integer _tmpTimeLimit;
          if (_stmt.isNull(_columnIndexOfTimeLimit)) {
            _tmpTimeLimit = null;
          } else {
            _tmpTimeLimit = (int) (_stmt.getLong(_columnIndexOfTimeLimit));
          }
          final int _tmpPassingScorePercent;
          _tmpPassingScorePercent = (int) (_stmt.getLong(_columnIndexOfPassingScorePercent));
          final int _tmpQuizType;
          _tmpQuizType = (int) (_stmt.getLong(_columnIndexOfQuizType));
          final int _tmpDirectionType;
          _tmpDirectionType = (int) (_stmt.getLong(_columnIndexOfDirectionType));
          final boolean _tmpIsCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp != 0;
          final Integer _tmpLastAttemptScore;
          if (_stmt.isNull(_columnIndexOfLastAttemptScore)) {
            _tmpLastAttemptScore = null;
          } else {
            _tmpLastAttemptScore = (int) (_stmt.getLong(_columnIndexOfLastAttemptScore));
          }
          final Integer _tmpBestScore;
          if (_stmt.isNull(_columnIndexOfBestScore)) {
            _tmpBestScore = null;
          } else {
            _tmpBestScore = (int) (_stmt.getLong(_columnIndexOfBestScore));
          }
          final int _tmpAttemptCount;
          _tmpAttemptCount = (int) (_stmt.getLong(_columnIndexOfAttemptCount));
          final Long _tmpLastAttemptAt;
          if (_stmt.isNull(_columnIndexOfLastAttemptAt)) {
            _tmpLastAttemptAt = null;
          } else {
            _tmpLastAttemptAt = _stmt.getLong(_columnIndexOfLastAttemptAt);
          }
          final Long _tmpCompletedAt;
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmpCompletedAt = null;
          } else {
            _tmpCompletedAt = _stmt.getLong(_columnIndexOfCompletedAt);
          }
          final long _tmpCreatedAt;
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt);
          final boolean _tmpIsSystemQuiz;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsSystemQuiz));
          _tmpIsSystemQuiz = _tmp_1 != 0;
          _result = new Quiz(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpInstructionsEnglish,_tmpInstructionsHindi,_tmpLessonId,_tmpCategoryId,_tmpDifficulty,_tmpQuestionCount,_tmpTimeLimit,_tmpPassingScorePercent,_tmpQuizType,_tmpDirectionType,_tmpIsCompleted,_tmpLastAttemptScore,_tmpBestScore,_tmpAttemptCount,_tmpLastAttemptAt,_tmpCompletedAt,_tmpCreatedAt,_tmpIsSystemQuiz);
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
  public Flow<List<Quiz>> getAllQuizzes() {
    final String _sql = "SELECT * FROM quizzes ORDER BY titleEnglish ASC";
    return FlowUtil.createFlow(__db, false, new String[] {"quizzes"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitleEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleEnglish");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfInstructionsEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "instructionsEnglish");
        final int _columnIndexOfInstructionsHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "instructionsHindi");
        final int _columnIndexOfLessonId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lessonId");
        final int _columnIndexOfCategoryId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "categoryId");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfQuestionCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "questionCount");
        final int _columnIndexOfTimeLimit = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timeLimit");
        final int _columnIndexOfPassingScorePercent = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "passingScorePercent");
        final int _columnIndexOfQuizType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "quizType");
        final int _columnIndexOfDirectionType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "directionType");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfLastAttemptScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAttemptScore");
        final int _columnIndexOfBestScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "bestScore");
        final int _columnIndexOfAttemptCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "attemptCount");
        final int _columnIndexOfLastAttemptAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAttemptAt");
        final int _columnIndexOfCompletedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAt");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _columnIndexOfIsSystemQuiz = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isSystemQuiz");
        final List<Quiz> _result = new ArrayList<Quiz>();
        while (_stmt.step()) {
          final Quiz _item;
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
          final String _tmpInstructionsEnglish;
          if (_stmt.isNull(_columnIndexOfInstructionsEnglish)) {
            _tmpInstructionsEnglish = null;
          } else {
            _tmpInstructionsEnglish = _stmt.getText(_columnIndexOfInstructionsEnglish);
          }
          final String _tmpInstructionsHindi;
          if (_stmt.isNull(_columnIndexOfInstructionsHindi)) {
            _tmpInstructionsHindi = null;
          } else {
            _tmpInstructionsHindi = _stmt.getText(_columnIndexOfInstructionsHindi);
          }
          final Long _tmpLessonId;
          if (_stmt.isNull(_columnIndexOfLessonId)) {
            _tmpLessonId = null;
          } else {
            _tmpLessonId = _stmt.getLong(_columnIndexOfLessonId);
          }
          final Long _tmpCategoryId;
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null;
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final int _tmpQuestionCount;
          _tmpQuestionCount = (int) (_stmt.getLong(_columnIndexOfQuestionCount));
          final Integer _tmpTimeLimit;
          if (_stmt.isNull(_columnIndexOfTimeLimit)) {
            _tmpTimeLimit = null;
          } else {
            _tmpTimeLimit = (int) (_stmt.getLong(_columnIndexOfTimeLimit));
          }
          final int _tmpPassingScorePercent;
          _tmpPassingScorePercent = (int) (_stmt.getLong(_columnIndexOfPassingScorePercent));
          final int _tmpQuizType;
          _tmpQuizType = (int) (_stmt.getLong(_columnIndexOfQuizType));
          final int _tmpDirectionType;
          _tmpDirectionType = (int) (_stmt.getLong(_columnIndexOfDirectionType));
          final boolean _tmpIsCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp != 0;
          final Integer _tmpLastAttemptScore;
          if (_stmt.isNull(_columnIndexOfLastAttemptScore)) {
            _tmpLastAttemptScore = null;
          } else {
            _tmpLastAttemptScore = (int) (_stmt.getLong(_columnIndexOfLastAttemptScore));
          }
          final Integer _tmpBestScore;
          if (_stmt.isNull(_columnIndexOfBestScore)) {
            _tmpBestScore = null;
          } else {
            _tmpBestScore = (int) (_stmt.getLong(_columnIndexOfBestScore));
          }
          final int _tmpAttemptCount;
          _tmpAttemptCount = (int) (_stmt.getLong(_columnIndexOfAttemptCount));
          final Long _tmpLastAttemptAt;
          if (_stmt.isNull(_columnIndexOfLastAttemptAt)) {
            _tmpLastAttemptAt = null;
          } else {
            _tmpLastAttemptAt = _stmt.getLong(_columnIndexOfLastAttemptAt);
          }
          final Long _tmpCompletedAt;
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmpCompletedAt = null;
          } else {
            _tmpCompletedAt = _stmt.getLong(_columnIndexOfCompletedAt);
          }
          final long _tmpCreatedAt;
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt);
          final boolean _tmpIsSystemQuiz;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsSystemQuiz));
          _tmpIsSystemQuiz = _tmp_1 != 0;
          _item = new Quiz(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpInstructionsEnglish,_tmpInstructionsHindi,_tmpLessonId,_tmpCategoryId,_tmpDifficulty,_tmpQuestionCount,_tmpTimeLimit,_tmpPassingScorePercent,_tmpQuizType,_tmpDirectionType,_tmpIsCompleted,_tmpLastAttemptScore,_tmpBestScore,_tmpAttemptCount,_tmpLastAttemptAt,_tmpCompletedAt,_tmpCreatedAt,_tmpIsSystemQuiz);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Quiz>> getAllQuizzesLiveData() {
    final String _sql = "SELECT * FROM quizzes ORDER BY titleEnglish ASC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"quizzes"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitleEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleEnglish");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfInstructionsEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "instructionsEnglish");
        final int _columnIndexOfInstructionsHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "instructionsHindi");
        final int _columnIndexOfLessonId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lessonId");
        final int _columnIndexOfCategoryId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "categoryId");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfQuestionCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "questionCount");
        final int _columnIndexOfTimeLimit = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timeLimit");
        final int _columnIndexOfPassingScorePercent = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "passingScorePercent");
        final int _columnIndexOfQuizType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "quizType");
        final int _columnIndexOfDirectionType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "directionType");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfLastAttemptScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAttemptScore");
        final int _columnIndexOfBestScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "bestScore");
        final int _columnIndexOfAttemptCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "attemptCount");
        final int _columnIndexOfLastAttemptAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAttemptAt");
        final int _columnIndexOfCompletedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAt");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _columnIndexOfIsSystemQuiz = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isSystemQuiz");
        final List<Quiz> _result = new ArrayList<Quiz>();
        while (_stmt.step()) {
          final Quiz _item;
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
          final String _tmpInstructionsEnglish;
          if (_stmt.isNull(_columnIndexOfInstructionsEnglish)) {
            _tmpInstructionsEnglish = null;
          } else {
            _tmpInstructionsEnglish = _stmt.getText(_columnIndexOfInstructionsEnglish);
          }
          final String _tmpInstructionsHindi;
          if (_stmt.isNull(_columnIndexOfInstructionsHindi)) {
            _tmpInstructionsHindi = null;
          } else {
            _tmpInstructionsHindi = _stmt.getText(_columnIndexOfInstructionsHindi);
          }
          final Long _tmpLessonId;
          if (_stmt.isNull(_columnIndexOfLessonId)) {
            _tmpLessonId = null;
          } else {
            _tmpLessonId = _stmt.getLong(_columnIndexOfLessonId);
          }
          final Long _tmpCategoryId;
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null;
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final int _tmpQuestionCount;
          _tmpQuestionCount = (int) (_stmt.getLong(_columnIndexOfQuestionCount));
          final Integer _tmpTimeLimit;
          if (_stmt.isNull(_columnIndexOfTimeLimit)) {
            _tmpTimeLimit = null;
          } else {
            _tmpTimeLimit = (int) (_stmt.getLong(_columnIndexOfTimeLimit));
          }
          final int _tmpPassingScorePercent;
          _tmpPassingScorePercent = (int) (_stmt.getLong(_columnIndexOfPassingScorePercent));
          final int _tmpQuizType;
          _tmpQuizType = (int) (_stmt.getLong(_columnIndexOfQuizType));
          final int _tmpDirectionType;
          _tmpDirectionType = (int) (_stmt.getLong(_columnIndexOfDirectionType));
          final boolean _tmpIsCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp != 0;
          final Integer _tmpLastAttemptScore;
          if (_stmt.isNull(_columnIndexOfLastAttemptScore)) {
            _tmpLastAttemptScore = null;
          } else {
            _tmpLastAttemptScore = (int) (_stmt.getLong(_columnIndexOfLastAttemptScore));
          }
          final Integer _tmpBestScore;
          if (_stmt.isNull(_columnIndexOfBestScore)) {
            _tmpBestScore = null;
          } else {
            _tmpBestScore = (int) (_stmt.getLong(_columnIndexOfBestScore));
          }
          final int _tmpAttemptCount;
          _tmpAttemptCount = (int) (_stmt.getLong(_columnIndexOfAttemptCount));
          final Long _tmpLastAttemptAt;
          if (_stmt.isNull(_columnIndexOfLastAttemptAt)) {
            _tmpLastAttemptAt = null;
          } else {
            _tmpLastAttemptAt = _stmt.getLong(_columnIndexOfLastAttemptAt);
          }
          final Long _tmpCompletedAt;
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmpCompletedAt = null;
          } else {
            _tmpCompletedAt = _stmt.getLong(_columnIndexOfCompletedAt);
          }
          final long _tmpCreatedAt;
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt);
          final boolean _tmpIsSystemQuiz;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsSystemQuiz));
          _tmpIsSystemQuiz = _tmp_1 != 0;
          _item = new Quiz(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpInstructionsEnglish,_tmpInstructionsHindi,_tmpLessonId,_tmpCategoryId,_tmpDifficulty,_tmpQuestionCount,_tmpTimeLimit,_tmpPassingScorePercent,_tmpQuizType,_tmpDirectionType,_tmpIsCompleted,_tmpLastAttemptScore,_tmpBestScore,_tmpAttemptCount,_tmpLastAttemptAt,_tmpCompletedAt,_tmpCreatedAt,_tmpIsSystemQuiz);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Flow<List<Quiz>> getQuizzesByLesson(final long lessonId) {
    final String _sql = "SELECT * FROM quizzes WHERE lessonId = ?";
    return FlowUtil.createFlow(__db, false, new String[] {"quizzes"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, lessonId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitleEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleEnglish");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfInstructionsEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "instructionsEnglish");
        final int _columnIndexOfInstructionsHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "instructionsHindi");
        final int _columnIndexOfLessonId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lessonId");
        final int _columnIndexOfCategoryId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "categoryId");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfQuestionCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "questionCount");
        final int _columnIndexOfTimeLimit = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timeLimit");
        final int _columnIndexOfPassingScorePercent = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "passingScorePercent");
        final int _columnIndexOfQuizType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "quizType");
        final int _columnIndexOfDirectionType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "directionType");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfLastAttemptScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAttemptScore");
        final int _columnIndexOfBestScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "bestScore");
        final int _columnIndexOfAttemptCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "attemptCount");
        final int _columnIndexOfLastAttemptAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAttemptAt");
        final int _columnIndexOfCompletedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAt");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _columnIndexOfIsSystemQuiz = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isSystemQuiz");
        final List<Quiz> _result = new ArrayList<Quiz>();
        while (_stmt.step()) {
          final Quiz _item;
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
          final String _tmpInstructionsEnglish;
          if (_stmt.isNull(_columnIndexOfInstructionsEnglish)) {
            _tmpInstructionsEnglish = null;
          } else {
            _tmpInstructionsEnglish = _stmt.getText(_columnIndexOfInstructionsEnglish);
          }
          final String _tmpInstructionsHindi;
          if (_stmt.isNull(_columnIndexOfInstructionsHindi)) {
            _tmpInstructionsHindi = null;
          } else {
            _tmpInstructionsHindi = _stmt.getText(_columnIndexOfInstructionsHindi);
          }
          final Long _tmpLessonId;
          if (_stmt.isNull(_columnIndexOfLessonId)) {
            _tmpLessonId = null;
          } else {
            _tmpLessonId = _stmt.getLong(_columnIndexOfLessonId);
          }
          final Long _tmpCategoryId;
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null;
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final int _tmpQuestionCount;
          _tmpQuestionCount = (int) (_stmt.getLong(_columnIndexOfQuestionCount));
          final Integer _tmpTimeLimit;
          if (_stmt.isNull(_columnIndexOfTimeLimit)) {
            _tmpTimeLimit = null;
          } else {
            _tmpTimeLimit = (int) (_stmt.getLong(_columnIndexOfTimeLimit));
          }
          final int _tmpPassingScorePercent;
          _tmpPassingScorePercent = (int) (_stmt.getLong(_columnIndexOfPassingScorePercent));
          final int _tmpQuizType;
          _tmpQuizType = (int) (_stmt.getLong(_columnIndexOfQuizType));
          final int _tmpDirectionType;
          _tmpDirectionType = (int) (_stmt.getLong(_columnIndexOfDirectionType));
          final boolean _tmpIsCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp != 0;
          final Integer _tmpLastAttemptScore;
          if (_stmt.isNull(_columnIndexOfLastAttemptScore)) {
            _tmpLastAttemptScore = null;
          } else {
            _tmpLastAttemptScore = (int) (_stmt.getLong(_columnIndexOfLastAttemptScore));
          }
          final Integer _tmpBestScore;
          if (_stmt.isNull(_columnIndexOfBestScore)) {
            _tmpBestScore = null;
          } else {
            _tmpBestScore = (int) (_stmt.getLong(_columnIndexOfBestScore));
          }
          final int _tmpAttemptCount;
          _tmpAttemptCount = (int) (_stmt.getLong(_columnIndexOfAttemptCount));
          final Long _tmpLastAttemptAt;
          if (_stmt.isNull(_columnIndexOfLastAttemptAt)) {
            _tmpLastAttemptAt = null;
          } else {
            _tmpLastAttemptAt = _stmt.getLong(_columnIndexOfLastAttemptAt);
          }
          final Long _tmpCompletedAt;
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmpCompletedAt = null;
          } else {
            _tmpCompletedAt = _stmt.getLong(_columnIndexOfCompletedAt);
          }
          final long _tmpCreatedAt;
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt);
          final boolean _tmpIsSystemQuiz;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsSystemQuiz));
          _tmpIsSystemQuiz = _tmp_1 != 0;
          _item = new Quiz(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpInstructionsEnglish,_tmpInstructionsHindi,_tmpLessonId,_tmpCategoryId,_tmpDifficulty,_tmpQuestionCount,_tmpTimeLimit,_tmpPassingScorePercent,_tmpQuizType,_tmpDirectionType,_tmpIsCompleted,_tmpLastAttemptScore,_tmpBestScore,_tmpAttemptCount,_tmpLastAttemptAt,_tmpCompletedAt,_tmpCreatedAt,_tmpIsSystemQuiz);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Flow<List<Quiz>> getQuizzesByCategory(final long categoryId) {
    final String _sql = "SELECT * FROM quizzes WHERE categoryId = ?";
    return FlowUtil.createFlow(__db, false, new String[] {"quizzes"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, categoryId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitleEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleEnglish");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfInstructionsEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "instructionsEnglish");
        final int _columnIndexOfInstructionsHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "instructionsHindi");
        final int _columnIndexOfLessonId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lessonId");
        final int _columnIndexOfCategoryId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "categoryId");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfQuestionCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "questionCount");
        final int _columnIndexOfTimeLimit = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timeLimit");
        final int _columnIndexOfPassingScorePercent = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "passingScorePercent");
        final int _columnIndexOfQuizType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "quizType");
        final int _columnIndexOfDirectionType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "directionType");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfLastAttemptScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAttemptScore");
        final int _columnIndexOfBestScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "bestScore");
        final int _columnIndexOfAttemptCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "attemptCount");
        final int _columnIndexOfLastAttemptAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAttemptAt");
        final int _columnIndexOfCompletedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAt");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _columnIndexOfIsSystemQuiz = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isSystemQuiz");
        final List<Quiz> _result = new ArrayList<Quiz>();
        while (_stmt.step()) {
          final Quiz _item;
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
          final String _tmpInstructionsEnglish;
          if (_stmt.isNull(_columnIndexOfInstructionsEnglish)) {
            _tmpInstructionsEnglish = null;
          } else {
            _tmpInstructionsEnglish = _stmt.getText(_columnIndexOfInstructionsEnglish);
          }
          final String _tmpInstructionsHindi;
          if (_stmt.isNull(_columnIndexOfInstructionsHindi)) {
            _tmpInstructionsHindi = null;
          } else {
            _tmpInstructionsHindi = _stmt.getText(_columnIndexOfInstructionsHindi);
          }
          final Long _tmpLessonId;
          if (_stmt.isNull(_columnIndexOfLessonId)) {
            _tmpLessonId = null;
          } else {
            _tmpLessonId = _stmt.getLong(_columnIndexOfLessonId);
          }
          final Long _tmpCategoryId;
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null;
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final int _tmpQuestionCount;
          _tmpQuestionCount = (int) (_stmt.getLong(_columnIndexOfQuestionCount));
          final Integer _tmpTimeLimit;
          if (_stmt.isNull(_columnIndexOfTimeLimit)) {
            _tmpTimeLimit = null;
          } else {
            _tmpTimeLimit = (int) (_stmt.getLong(_columnIndexOfTimeLimit));
          }
          final int _tmpPassingScorePercent;
          _tmpPassingScorePercent = (int) (_stmt.getLong(_columnIndexOfPassingScorePercent));
          final int _tmpQuizType;
          _tmpQuizType = (int) (_stmt.getLong(_columnIndexOfQuizType));
          final int _tmpDirectionType;
          _tmpDirectionType = (int) (_stmt.getLong(_columnIndexOfDirectionType));
          final boolean _tmpIsCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp != 0;
          final Integer _tmpLastAttemptScore;
          if (_stmt.isNull(_columnIndexOfLastAttemptScore)) {
            _tmpLastAttemptScore = null;
          } else {
            _tmpLastAttemptScore = (int) (_stmt.getLong(_columnIndexOfLastAttemptScore));
          }
          final Integer _tmpBestScore;
          if (_stmt.isNull(_columnIndexOfBestScore)) {
            _tmpBestScore = null;
          } else {
            _tmpBestScore = (int) (_stmt.getLong(_columnIndexOfBestScore));
          }
          final int _tmpAttemptCount;
          _tmpAttemptCount = (int) (_stmt.getLong(_columnIndexOfAttemptCount));
          final Long _tmpLastAttemptAt;
          if (_stmt.isNull(_columnIndexOfLastAttemptAt)) {
            _tmpLastAttemptAt = null;
          } else {
            _tmpLastAttemptAt = _stmt.getLong(_columnIndexOfLastAttemptAt);
          }
          final Long _tmpCompletedAt;
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmpCompletedAt = null;
          } else {
            _tmpCompletedAt = _stmt.getLong(_columnIndexOfCompletedAt);
          }
          final long _tmpCreatedAt;
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt);
          final boolean _tmpIsSystemQuiz;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsSystemQuiz));
          _tmpIsSystemQuiz = _tmp_1 != 0;
          _item = new Quiz(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpInstructionsEnglish,_tmpInstructionsHindi,_tmpLessonId,_tmpCategoryId,_tmpDifficulty,_tmpQuestionCount,_tmpTimeLimit,_tmpPassingScorePercent,_tmpQuizType,_tmpDirectionType,_tmpIsCompleted,_tmpLastAttemptScore,_tmpBestScore,_tmpAttemptCount,_tmpLastAttemptAt,_tmpCompletedAt,_tmpCreatedAt,_tmpIsSystemQuiz);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Flow<List<Quiz>> getQuizzesByDifficulty(final int difficulty) {
    final String _sql = "SELECT * FROM quizzes WHERE difficulty = ?";
    return FlowUtil.createFlow(__db, false, new String[] {"quizzes"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, difficulty);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitleEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleEnglish");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfInstructionsEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "instructionsEnglish");
        final int _columnIndexOfInstructionsHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "instructionsHindi");
        final int _columnIndexOfLessonId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lessonId");
        final int _columnIndexOfCategoryId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "categoryId");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfQuestionCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "questionCount");
        final int _columnIndexOfTimeLimit = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timeLimit");
        final int _columnIndexOfPassingScorePercent = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "passingScorePercent");
        final int _columnIndexOfQuizType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "quizType");
        final int _columnIndexOfDirectionType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "directionType");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfLastAttemptScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAttemptScore");
        final int _columnIndexOfBestScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "bestScore");
        final int _columnIndexOfAttemptCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "attemptCount");
        final int _columnIndexOfLastAttemptAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAttemptAt");
        final int _columnIndexOfCompletedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAt");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _columnIndexOfIsSystemQuiz = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isSystemQuiz");
        final List<Quiz> _result = new ArrayList<Quiz>();
        while (_stmt.step()) {
          final Quiz _item;
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
          final String _tmpInstructionsEnglish;
          if (_stmt.isNull(_columnIndexOfInstructionsEnglish)) {
            _tmpInstructionsEnglish = null;
          } else {
            _tmpInstructionsEnglish = _stmt.getText(_columnIndexOfInstructionsEnglish);
          }
          final String _tmpInstructionsHindi;
          if (_stmt.isNull(_columnIndexOfInstructionsHindi)) {
            _tmpInstructionsHindi = null;
          } else {
            _tmpInstructionsHindi = _stmt.getText(_columnIndexOfInstructionsHindi);
          }
          final Long _tmpLessonId;
          if (_stmt.isNull(_columnIndexOfLessonId)) {
            _tmpLessonId = null;
          } else {
            _tmpLessonId = _stmt.getLong(_columnIndexOfLessonId);
          }
          final Long _tmpCategoryId;
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null;
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final int _tmpQuestionCount;
          _tmpQuestionCount = (int) (_stmt.getLong(_columnIndexOfQuestionCount));
          final Integer _tmpTimeLimit;
          if (_stmt.isNull(_columnIndexOfTimeLimit)) {
            _tmpTimeLimit = null;
          } else {
            _tmpTimeLimit = (int) (_stmt.getLong(_columnIndexOfTimeLimit));
          }
          final int _tmpPassingScorePercent;
          _tmpPassingScorePercent = (int) (_stmt.getLong(_columnIndexOfPassingScorePercent));
          final int _tmpQuizType;
          _tmpQuizType = (int) (_stmt.getLong(_columnIndexOfQuizType));
          final int _tmpDirectionType;
          _tmpDirectionType = (int) (_stmt.getLong(_columnIndexOfDirectionType));
          final boolean _tmpIsCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp != 0;
          final Integer _tmpLastAttemptScore;
          if (_stmt.isNull(_columnIndexOfLastAttemptScore)) {
            _tmpLastAttemptScore = null;
          } else {
            _tmpLastAttemptScore = (int) (_stmt.getLong(_columnIndexOfLastAttemptScore));
          }
          final Integer _tmpBestScore;
          if (_stmt.isNull(_columnIndexOfBestScore)) {
            _tmpBestScore = null;
          } else {
            _tmpBestScore = (int) (_stmt.getLong(_columnIndexOfBestScore));
          }
          final int _tmpAttemptCount;
          _tmpAttemptCount = (int) (_stmt.getLong(_columnIndexOfAttemptCount));
          final Long _tmpLastAttemptAt;
          if (_stmt.isNull(_columnIndexOfLastAttemptAt)) {
            _tmpLastAttemptAt = null;
          } else {
            _tmpLastAttemptAt = _stmt.getLong(_columnIndexOfLastAttemptAt);
          }
          final Long _tmpCompletedAt;
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmpCompletedAt = null;
          } else {
            _tmpCompletedAt = _stmt.getLong(_columnIndexOfCompletedAt);
          }
          final long _tmpCreatedAt;
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt);
          final boolean _tmpIsSystemQuiz;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsSystemQuiz));
          _tmpIsSystemQuiz = _tmp_1 != 0;
          _item = new Quiz(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpInstructionsEnglish,_tmpInstructionsHindi,_tmpLessonId,_tmpCategoryId,_tmpDifficulty,_tmpQuestionCount,_tmpTimeLimit,_tmpPassingScorePercent,_tmpQuizType,_tmpDirectionType,_tmpIsCompleted,_tmpLastAttemptScore,_tmpBestScore,_tmpAttemptCount,_tmpLastAttemptAt,_tmpCompletedAt,_tmpCreatedAt,_tmpIsSystemQuiz);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Flow<List<Quiz>> getQuizzesByCompletionStatus(final boolean isCompleted) {
    final String _sql = "SELECT * FROM quizzes WHERE isCompleted = ?";
    return FlowUtil.createFlow(__db, false, new String[] {"quizzes"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        final int _tmp = isCompleted ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitleEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleEnglish");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfInstructionsEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "instructionsEnglish");
        final int _columnIndexOfInstructionsHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "instructionsHindi");
        final int _columnIndexOfLessonId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lessonId");
        final int _columnIndexOfCategoryId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "categoryId");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfQuestionCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "questionCount");
        final int _columnIndexOfTimeLimit = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timeLimit");
        final int _columnIndexOfPassingScorePercent = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "passingScorePercent");
        final int _columnIndexOfQuizType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "quizType");
        final int _columnIndexOfDirectionType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "directionType");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfLastAttemptScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAttemptScore");
        final int _columnIndexOfBestScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "bestScore");
        final int _columnIndexOfAttemptCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "attemptCount");
        final int _columnIndexOfLastAttemptAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAttemptAt");
        final int _columnIndexOfCompletedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAt");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _columnIndexOfIsSystemQuiz = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isSystemQuiz");
        final List<Quiz> _result = new ArrayList<Quiz>();
        while (_stmt.step()) {
          final Quiz _item;
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
          final String _tmpInstructionsEnglish;
          if (_stmt.isNull(_columnIndexOfInstructionsEnglish)) {
            _tmpInstructionsEnglish = null;
          } else {
            _tmpInstructionsEnglish = _stmt.getText(_columnIndexOfInstructionsEnglish);
          }
          final String _tmpInstructionsHindi;
          if (_stmt.isNull(_columnIndexOfInstructionsHindi)) {
            _tmpInstructionsHindi = null;
          } else {
            _tmpInstructionsHindi = _stmt.getText(_columnIndexOfInstructionsHindi);
          }
          final Long _tmpLessonId;
          if (_stmt.isNull(_columnIndexOfLessonId)) {
            _tmpLessonId = null;
          } else {
            _tmpLessonId = _stmt.getLong(_columnIndexOfLessonId);
          }
          final Long _tmpCategoryId;
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null;
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final int _tmpQuestionCount;
          _tmpQuestionCount = (int) (_stmt.getLong(_columnIndexOfQuestionCount));
          final Integer _tmpTimeLimit;
          if (_stmt.isNull(_columnIndexOfTimeLimit)) {
            _tmpTimeLimit = null;
          } else {
            _tmpTimeLimit = (int) (_stmt.getLong(_columnIndexOfTimeLimit));
          }
          final int _tmpPassingScorePercent;
          _tmpPassingScorePercent = (int) (_stmt.getLong(_columnIndexOfPassingScorePercent));
          final int _tmpQuizType;
          _tmpQuizType = (int) (_stmt.getLong(_columnIndexOfQuizType));
          final int _tmpDirectionType;
          _tmpDirectionType = (int) (_stmt.getLong(_columnIndexOfDirectionType));
          final boolean _tmpIsCompleted;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp_1 != 0;
          final Integer _tmpLastAttemptScore;
          if (_stmt.isNull(_columnIndexOfLastAttemptScore)) {
            _tmpLastAttemptScore = null;
          } else {
            _tmpLastAttemptScore = (int) (_stmt.getLong(_columnIndexOfLastAttemptScore));
          }
          final Integer _tmpBestScore;
          if (_stmt.isNull(_columnIndexOfBestScore)) {
            _tmpBestScore = null;
          } else {
            _tmpBestScore = (int) (_stmt.getLong(_columnIndexOfBestScore));
          }
          final int _tmpAttemptCount;
          _tmpAttemptCount = (int) (_stmt.getLong(_columnIndexOfAttemptCount));
          final Long _tmpLastAttemptAt;
          if (_stmt.isNull(_columnIndexOfLastAttemptAt)) {
            _tmpLastAttemptAt = null;
          } else {
            _tmpLastAttemptAt = _stmt.getLong(_columnIndexOfLastAttemptAt);
          }
          final Long _tmpCompletedAt;
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmpCompletedAt = null;
          } else {
            _tmpCompletedAt = _stmt.getLong(_columnIndexOfCompletedAt);
          }
          final long _tmpCreatedAt;
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt);
          final boolean _tmpIsSystemQuiz;
          final int _tmp_2;
          _tmp_2 = (int) (_stmt.getLong(_columnIndexOfIsSystemQuiz));
          _tmpIsSystemQuiz = _tmp_2 != 0;
          _item = new Quiz(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpInstructionsEnglish,_tmpInstructionsHindi,_tmpLessonId,_tmpCategoryId,_tmpDifficulty,_tmpQuestionCount,_tmpTimeLimit,_tmpPassingScorePercent,_tmpQuizType,_tmpDirectionType,_tmpIsCompleted,_tmpLastAttemptScore,_tmpBestScore,_tmpAttemptCount,_tmpLastAttemptAt,_tmpCompletedAt,_tmpCreatedAt,_tmpIsSystemQuiz);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Flow<List<Quiz>> searchQuizzes(final String query) {
    final String _sql = "SELECT * FROM quizzes WHERE titleEnglish LIKE '%' || ? || '%' OR titleHindi LIKE '%' || ? || '%'";
    return FlowUtil.createFlow(__db, false, new String[] {"quizzes"}, (_connection) -> {
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
        final int _columnIndexOfInstructionsEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "instructionsEnglish");
        final int _columnIndexOfInstructionsHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "instructionsHindi");
        final int _columnIndexOfLessonId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lessonId");
        final int _columnIndexOfCategoryId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "categoryId");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfQuestionCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "questionCount");
        final int _columnIndexOfTimeLimit = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timeLimit");
        final int _columnIndexOfPassingScorePercent = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "passingScorePercent");
        final int _columnIndexOfQuizType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "quizType");
        final int _columnIndexOfDirectionType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "directionType");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfLastAttemptScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAttemptScore");
        final int _columnIndexOfBestScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "bestScore");
        final int _columnIndexOfAttemptCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "attemptCount");
        final int _columnIndexOfLastAttemptAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAttemptAt");
        final int _columnIndexOfCompletedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAt");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _columnIndexOfIsSystemQuiz = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isSystemQuiz");
        final List<Quiz> _result = new ArrayList<Quiz>();
        while (_stmt.step()) {
          final Quiz _item;
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
          final String _tmpInstructionsEnglish;
          if (_stmt.isNull(_columnIndexOfInstructionsEnglish)) {
            _tmpInstructionsEnglish = null;
          } else {
            _tmpInstructionsEnglish = _stmt.getText(_columnIndexOfInstructionsEnglish);
          }
          final String _tmpInstructionsHindi;
          if (_stmt.isNull(_columnIndexOfInstructionsHindi)) {
            _tmpInstructionsHindi = null;
          } else {
            _tmpInstructionsHindi = _stmt.getText(_columnIndexOfInstructionsHindi);
          }
          final Long _tmpLessonId;
          if (_stmt.isNull(_columnIndexOfLessonId)) {
            _tmpLessonId = null;
          } else {
            _tmpLessonId = _stmt.getLong(_columnIndexOfLessonId);
          }
          final Long _tmpCategoryId;
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null;
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final int _tmpQuestionCount;
          _tmpQuestionCount = (int) (_stmt.getLong(_columnIndexOfQuestionCount));
          final Integer _tmpTimeLimit;
          if (_stmt.isNull(_columnIndexOfTimeLimit)) {
            _tmpTimeLimit = null;
          } else {
            _tmpTimeLimit = (int) (_stmt.getLong(_columnIndexOfTimeLimit));
          }
          final int _tmpPassingScorePercent;
          _tmpPassingScorePercent = (int) (_stmt.getLong(_columnIndexOfPassingScorePercent));
          final int _tmpQuizType;
          _tmpQuizType = (int) (_stmt.getLong(_columnIndexOfQuizType));
          final int _tmpDirectionType;
          _tmpDirectionType = (int) (_stmt.getLong(_columnIndexOfDirectionType));
          final boolean _tmpIsCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp != 0;
          final Integer _tmpLastAttemptScore;
          if (_stmt.isNull(_columnIndexOfLastAttemptScore)) {
            _tmpLastAttemptScore = null;
          } else {
            _tmpLastAttemptScore = (int) (_stmt.getLong(_columnIndexOfLastAttemptScore));
          }
          final Integer _tmpBestScore;
          if (_stmt.isNull(_columnIndexOfBestScore)) {
            _tmpBestScore = null;
          } else {
            _tmpBestScore = (int) (_stmt.getLong(_columnIndexOfBestScore));
          }
          final int _tmpAttemptCount;
          _tmpAttemptCount = (int) (_stmt.getLong(_columnIndexOfAttemptCount));
          final Long _tmpLastAttemptAt;
          if (_stmt.isNull(_columnIndexOfLastAttemptAt)) {
            _tmpLastAttemptAt = null;
          } else {
            _tmpLastAttemptAt = _stmt.getLong(_columnIndexOfLastAttemptAt);
          }
          final Long _tmpCompletedAt;
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmpCompletedAt = null;
          } else {
            _tmpCompletedAt = _stmt.getLong(_columnIndexOfCompletedAt);
          }
          final long _tmpCreatedAt;
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt);
          final boolean _tmpIsSystemQuiz;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsSystemQuiz));
          _tmpIsSystemQuiz = _tmp_1 != 0;
          _item = new Quiz(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpInstructionsEnglish,_tmpInstructionsHindi,_tmpLessonId,_tmpCategoryId,_tmpDifficulty,_tmpQuestionCount,_tmpTimeLimit,_tmpPassingScorePercent,_tmpQuizType,_tmpDirectionType,_tmpIsCompleted,_tmpLastAttemptScore,_tmpBestScore,_tmpAttemptCount,_tmpLastAttemptAt,_tmpCompletedAt,_tmpCreatedAt,_tmpIsSystemQuiz);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Object getQuestionById(final long questionId,
      final Continuation<? super QuizQuestion> $completion) {
    final String _sql = "SELECT * FROM quiz_questions WHERE id = ?";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, questionId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfQuizId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "quizId");
        final int _columnIndexOfWordId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wordId");
        final int _columnIndexOfQuestionTextEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "questionTextEnglish");
        final int _columnIndexOfQuestionTextHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "questionTextHindi");
        final int _columnIndexOfQuestionType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "questionType");
        final int _columnIndexOfCorrectAnswer = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctAnswer");
        final int _columnIndexOfOptions = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "options");
        final int _columnIndexOfImageUrl = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "imageUrl");
        final int _columnIndexOfAudioUrl = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "audioUrl");
        final int _columnIndexOfExplanationEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "explanationEnglish");
        final int _columnIndexOfExplanationHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "explanationHindi");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfPoints = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "points");
        final int _columnIndexOfOrderInQuiz = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "orderInQuiz");
        final int _columnIndexOfCorrectAttempts = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctAttempts");
        final int _columnIndexOfTotalAttempts = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalAttempts");
        final QuizQuestion _result;
        if (_stmt.step()) {
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final long _tmpQuizId;
          _tmpQuizId = _stmt.getLong(_columnIndexOfQuizId);
          final Long _tmpWordId;
          if (_stmt.isNull(_columnIndexOfWordId)) {
            _tmpWordId = null;
          } else {
            _tmpWordId = _stmt.getLong(_columnIndexOfWordId);
          }
          final String _tmpQuestionTextEnglish;
          if (_stmt.isNull(_columnIndexOfQuestionTextEnglish)) {
            _tmpQuestionTextEnglish = null;
          } else {
            _tmpQuestionTextEnglish = _stmt.getText(_columnIndexOfQuestionTextEnglish);
          }
          final String _tmpQuestionTextHindi;
          if (_stmt.isNull(_columnIndexOfQuestionTextHindi)) {
            _tmpQuestionTextHindi = null;
          } else {
            _tmpQuestionTextHindi = _stmt.getText(_columnIndexOfQuestionTextHindi);
          }
          final int _tmpQuestionType;
          _tmpQuestionType = (int) (_stmt.getLong(_columnIndexOfQuestionType));
          final String _tmpCorrectAnswer;
          if (_stmt.isNull(_columnIndexOfCorrectAnswer)) {
            _tmpCorrectAnswer = null;
          } else {
            _tmpCorrectAnswer = _stmt.getText(_columnIndexOfCorrectAnswer);
          }
          final String _tmpOptions;
          if (_stmt.isNull(_columnIndexOfOptions)) {
            _tmpOptions = null;
          } else {
            _tmpOptions = _stmt.getText(_columnIndexOfOptions);
          }
          final String _tmpImageUrl;
          if (_stmt.isNull(_columnIndexOfImageUrl)) {
            _tmpImageUrl = null;
          } else {
            _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl);
          }
          final String _tmpAudioUrl;
          if (_stmt.isNull(_columnIndexOfAudioUrl)) {
            _tmpAudioUrl = null;
          } else {
            _tmpAudioUrl = _stmt.getText(_columnIndexOfAudioUrl);
          }
          final String _tmpExplanationEnglish;
          if (_stmt.isNull(_columnIndexOfExplanationEnglish)) {
            _tmpExplanationEnglish = null;
          } else {
            _tmpExplanationEnglish = _stmt.getText(_columnIndexOfExplanationEnglish);
          }
          final String _tmpExplanationHindi;
          if (_stmt.isNull(_columnIndexOfExplanationHindi)) {
            _tmpExplanationHindi = null;
          } else {
            _tmpExplanationHindi = _stmt.getText(_columnIndexOfExplanationHindi);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final int _tmpPoints;
          _tmpPoints = (int) (_stmt.getLong(_columnIndexOfPoints));
          final int _tmpOrderInQuiz;
          _tmpOrderInQuiz = (int) (_stmt.getLong(_columnIndexOfOrderInQuiz));
          final int _tmpCorrectAttempts;
          _tmpCorrectAttempts = (int) (_stmt.getLong(_columnIndexOfCorrectAttempts));
          final int _tmpTotalAttempts;
          _tmpTotalAttempts = (int) (_stmt.getLong(_columnIndexOfTotalAttempts));
          _result = new QuizQuestion(_tmpId,_tmpQuizId,_tmpWordId,_tmpQuestionTextEnglish,_tmpQuestionTextHindi,_tmpQuestionType,_tmpCorrectAnswer,_tmpOptions,_tmpImageUrl,_tmpAudioUrl,_tmpExplanationEnglish,_tmpExplanationHindi,_tmpDifficulty,_tmpPoints,_tmpOrderInQuiz,_tmpCorrectAttempts,_tmpTotalAttempts);
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
  public Flow<List<QuizQuestion>> getQuestionsForQuiz(final long quizId) {
    final String _sql = "SELECT * FROM quiz_questions WHERE quizId = ? ORDER BY orderInQuiz ASC";
    return FlowUtil.createFlow(__db, false, new String[] {"quiz_questions"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, quizId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfQuizId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "quizId");
        final int _columnIndexOfWordId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wordId");
        final int _columnIndexOfQuestionTextEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "questionTextEnglish");
        final int _columnIndexOfQuestionTextHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "questionTextHindi");
        final int _columnIndexOfQuestionType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "questionType");
        final int _columnIndexOfCorrectAnswer = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctAnswer");
        final int _columnIndexOfOptions = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "options");
        final int _columnIndexOfImageUrl = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "imageUrl");
        final int _columnIndexOfAudioUrl = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "audioUrl");
        final int _columnIndexOfExplanationEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "explanationEnglish");
        final int _columnIndexOfExplanationHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "explanationHindi");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfPoints = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "points");
        final int _columnIndexOfOrderInQuiz = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "orderInQuiz");
        final int _columnIndexOfCorrectAttempts = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctAttempts");
        final int _columnIndexOfTotalAttempts = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalAttempts");
        final List<QuizQuestion> _result = new ArrayList<QuizQuestion>();
        while (_stmt.step()) {
          final QuizQuestion _item;
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final long _tmpQuizId;
          _tmpQuizId = _stmt.getLong(_columnIndexOfQuizId);
          final Long _tmpWordId;
          if (_stmt.isNull(_columnIndexOfWordId)) {
            _tmpWordId = null;
          } else {
            _tmpWordId = _stmt.getLong(_columnIndexOfWordId);
          }
          final String _tmpQuestionTextEnglish;
          if (_stmt.isNull(_columnIndexOfQuestionTextEnglish)) {
            _tmpQuestionTextEnglish = null;
          } else {
            _tmpQuestionTextEnglish = _stmt.getText(_columnIndexOfQuestionTextEnglish);
          }
          final String _tmpQuestionTextHindi;
          if (_stmt.isNull(_columnIndexOfQuestionTextHindi)) {
            _tmpQuestionTextHindi = null;
          } else {
            _tmpQuestionTextHindi = _stmt.getText(_columnIndexOfQuestionTextHindi);
          }
          final int _tmpQuestionType;
          _tmpQuestionType = (int) (_stmt.getLong(_columnIndexOfQuestionType));
          final String _tmpCorrectAnswer;
          if (_stmt.isNull(_columnIndexOfCorrectAnswer)) {
            _tmpCorrectAnswer = null;
          } else {
            _tmpCorrectAnswer = _stmt.getText(_columnIndexOfCorrectAnswer);
          }
          final String _tmpOptions;
          if (_stmt.isNull(_columnIndexOfOptions)) {
            _tmpOptions = null;
          } else {
            _tmpOptions = _stmt.getText(_columnIndexOfOptions);
          }
          final String _tmpImageUrl;
          if (_stmt.isNull(_columnIndexOfImageUrl)) {
            _tmpImageUrl = null;
          } else {
            _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl);
          }
          final String _tmpAudioUrl;
          if (_stmt.isNull(_columnIndexOfAudioUrl)) {
            _tmpAudioUrl = null;
          } else {
            _tmpAudioUrl = _stmt.getText(_columnIndexOfAudioUrl);
          }
          final String _tmpExplanationEnglish;
          if (_stmt.isNull(_columnIndexOfExplanationEnglish)) {
            _tmpExplanationEnglish = null;
          } else {
            _tmpExplanationEnglish = _stmt.getText(_columnIndexOfExplanationEnglish);
          }
          final String _tmpExplanationHindi;
          if (_stmt.isNull(_columnIndexOfExplanationHindi)) {
            _tmpExplanationHindi = null;
          } else {
            _tmpExplanationHindi = _stmt.getText(_columnIndexOfExplanationHindi);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final int _tmpPoints;
          _tmpPoints = (int) (_stmt.getLong(_columnIndexOfPoints));
          final int _tmpOrderInQuiz;
          _tmpOrderInQuiz = (int) (_stmt.getLong(_columnIndexOfOrderInQuiz));
          final int _tmpCorrectAttempts;
          _tmpCorrectAttempts = (int) (_stmt.getLong(_columnIndexOfCorrectAttempts));
          final int _tmpTotalAttempts;
          _tmpTotalAttempts = (int) (_stmt.getLong(_columnIndexOfTotalAttempts));
          _item = new QuizQuestion(_tmpId,_tmpQuizId,_tmpWordId,_tmpQuestionTextEnglish,_tmpQuestionTextHindi,_tmpQuestionType,_tmpCorrectAnswer,_tmpOptions,_tmpImageUrl,_tmpAudioUrl,_tmpExplanationEnglish,_tmpExplanationHindi,_tmpDifficulty,_tmpPoints,_tmpOrderInQuiz,_tmpCorrectAttempts,_tmpTotalAttempts);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Flow<List<QuizQuestion>> getQuestionsForQuizByType(final long quizId,
      final int questionType) {
    final String _sql = "SELECT * FROM quiz_questions WHERE quizId = ? AND questionType = ? ORDER BY orderInQuiz ASC";
    return FlowUtil.createFlow(__db, false, new String[] {"quiz_questions"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, quizId);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, questionType);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfQuizId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "quizId");
        final int _columnIndexOfWordId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wordId");
        final int _columnIndexOfQuestionTextEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "questionTextEnglish");
        final int _columnIndexOfQuestionTextHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "questionTextHindi");
        final int _columnIndexOfQuestionType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "questionType");
        final int _columnIndexOfCorrectAnswer = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctAnswer");
        final int _columnIndexOfOptions = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "options");
        final int _columnIndexOfImageUrl = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "imageUrl");
        final int _columnIndexOfAudioUrl = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "audioUrl");
        final int _columnIndexOfExplanationEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "explanationEnglish");
        final int _columnIndexOfExplanationHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "explanationHindi");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfPoints = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "points");
        final int _columnIndexOfOrderInQuiz = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "orderInQuiz");
        final int _columnIndexOfCorrectAttempts = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctAttempts");
        final int _columnIndexOfTotalAttempts = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalAttempts");
        final List<QuizQuestion> _result = new ArrayList<QuizQuestion>();
        while (_stmt.step()) {
          final QuizQuestion _item;
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final long _tmpQuizId;
          _tmpQuizId = _stmt.getLong(_columnIndexOfQuizId);
          final Long _tmpWordId;
          if (_stmt.isNull(_columnIndexOfWordId)) {
            _tmpWordId = null;
          } else {
            _tmpWordId = _stmt.getLong(_columnIndexOfWordId);
          }
          final String _tmpQuestionTextEnglish;
          if (_stmt.isNull(_columnIndexOfQuestionTextEnglish)) {
            _tmpQuestionTextEnglish = null;
          } else {
            _tmpQuestionTextEnglish = _stmt.getText(_columnIndexOfQuestionTextEnglish);
          }
          final String _tmpQuestionTextHindi;
          if (_stmt.isNull(_columnIndexOfQuestionTextHindi)) {
            _tmpQuestionTextHindi = null;
          } else {
            _tmpQuestionTextHindi = _stmt.getText(_columnIndexOfQuestionTextHindi);
          }
          final int _tmpQuestionType;
          _tmpQuestionType = (int) (_stmt.getLong(_columnIndexOfQuestionType));
          final String _tmpCorrectAnswer;
          if (_stmt.isNull(_columnIndexOfCorrectAnswer)) {
            _tmpCorrectAnswer = null;
          } else {
            _tmpCorrectAnswer = _stmt.getText(_columnIndexOfCorrectAnswer);
          }
          final String _tmpOptions;
          if (_stmt.isNull(_columnIndexOfOptions)) {
            _tmpOptions = null;
          } else {
            _tmpOptions = _stmt.getText(_columnIndexOfOptions);
          }
          final String _tmpImageUrl;
          if (_stmt.isNull(_columnIndexOfImageUrl)) {
            _tmpImageUrl = null;
          } else {
            _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl);
          }
          final String _tmpAudioUrl;
          if (_stmt.isNull(_columnIndexOfAudioUrl)) {
            _tmpAudioUrl = null;
          } else {
            _tmpAudioUrl = _stmt.getText(_columnIndexOfAudioUrl);
          }
          final String _tmpExplanationEnglish;
          if (_stmt.isNull(_columnIndexOfExplanationEnglish)) {
            _tmpExplanationEnglish = null;
          } else {
            _tmpExplanationEnglish = _stmt.getText(_columnIndexOfExplanationEnglish);
          }
          final String _tmpExplanationHindi;
          if (_stmt.isNull(_columnIndexOfExplanationHindi)) {
            _tmpExplanationHindi = null;
          } else {
            _tmpExplanationHindi = _stmt.getText(_columnIndexOfExplanationHindi);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final int _tmpPoints;
          _tmpPoints = (int) (_stmt.getLong(_columnIndexOfPoints));
          final int _tmpOrderInQuiz;
          _tmpOrderInQuiz = (int) (_stmt.getLong(_columnIndexOfOrderInQuiz));
          final int _tmpCorrectAttempts;
          _tmpCorrectAttempts = (int) (_stmt.getLong(_columnIndexOfCorrectAttempts));
          final int _tmpTotalAttempts;
          _tmpTotalAttempts = (int) (_stmt.getLong(_columnIndexOfTotalAttempts));
          _item = new QuizQuestion(_tmpId,_tmpQuizId,_tmpWordId,_tmpQuestionTextEnglish,_tmpQuestionTextHindi,_tmpQuestionType,_tmpCorrectAnswer,_tmpOptions,_tmpImageUrl,_tmpAudioUrl,_tmpExplanationEnglish,_tmpExplanationHindi,_tmpDifficulty,_tmpPoints,_tmpOrderInQuiz,_tmpCorrectAttempts,_tmpTotalAttempts);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Object getQuizWithQuestions(final long quizId,
      final Continuation<? super QuizWithQuestions> $completion) {
    final String _sql = "SELECT * FROM quizzes WHERE id = ?";
    return DBUtil.performSuspending(__db, true, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, quizId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitleEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleEnglish");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfInstructionsEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "instructionsEnglish");
        final int _columnIndexOfInstructionsHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "instructionsHindi");
        final int _columnIndexOfLessonId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lessonId");
        final int _columnIndexOfCategoryId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "categoryId");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfQuestionCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "questionCount");
        final int _columnIndexOfTimeLimit = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timeLimit");
        final int _columnIndexOfPassingScorePercent = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "passingScorePercent");
        final int _columnIndexOfQuizType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "quizType");
        final int _columnIndexOfDirectionType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "directionType");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfLastAttemptScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAttemptScore");
        final int _columnIndexOfBestScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "bestScore");
        final int _columnIndexOfAttemptCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "attemptCount");
        final int _columnIndexOfLastAttemptAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAttemptAt");
        final int _columnIndexOfCompletedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAt");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _columnIndexOfIsSystemQuiz = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isSystemQuiz");
        final LongSparseArray<ArrayList<QuizQuestion>> _collectionQuestions = new LongSparseArray<ArrayList<QuizQuestion>>();
        while (_stmt.step()) {
          final long _tmpKey;
          _tmpKey = _stmt.getLong(_columnIndexOfId);
          if (!_collectionQuestions.containsKey(_tmpKey)) {
            _collectionQuestions.put(_tmpKey, new ArrayList<QuizQuestion>());
          }
        }
        _stmt.reset();
        __fetchRelationshipquizQuestionsAscomBhashasetuAppDataModelQuizQuestion(_connection, _collectionQuestions);
        final QuizWithQuestions _result;
        if (_stmt.step()) {
          final Quiz _tmpQuiz;
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
          final String _tmpInstructionsEnglish;
          if (_stmt.isNull(_columnIndexOfInstructionsEnglish)) {
            _tmpInstructionsEnglish = null;
          } else {
            _tmpInstructionsEnglish = _stmt.getText(_columnIndexOfInstructionsEnglish);
          }
          final String _tmpInstructionsHindi;
          if (_stmt.isNull(_columnIndexOfInstructionsHindi)) {
            _tmpInstructionsHindi = null;
          } else {
            _tmpInstructionsHindi = _stmt.getText(_columnIndexOfInstructionsHindi);
          }
          final Long _tmpLessonId;
          if (_stmt.isNull(_columnIndexOfLessonId)) {
            _tmpLessonId = null;
          } else {
            _tmpLessonId = _stmt.getLong(_columnIndexOfLessonId);
          }
          final Long _tmpCategoryId;
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null;
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final int _tmpQuestionCount;
          _tmpQuestionCount = (int) (_stmt.getLong(_columnIndexOfQuestionCount));
          final Integer _tmpTimeLimit;
          if (_stmt.isNull(_columnIndexOfTimeLimit)) {
            _tmpTimeLimit = null;
          } else {
            _tmpTimeLimit = (int) (_stmt.getLong(_columnIndexOfTimeLimit));
          }
          final int _tmpPassingScorePercent;
          _tmpPassingScorePercent = (int) (_stmt.getLong(_columnIndexOfPassingScorePercent));
          final int _tmpQuizType;
          _tmpQuizType = (int) (_stmt.getLong(_columnIndexOfQuizType));
          final int _tmpDirectionType;
          _tmpDirectionType = (int) (_stmt.getLong(_columnIndexOfDirectionType));
          final boolean _tmpIsCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp != 0;
          final Integer _tmpLastAttemptScore;
          if (_stmt.isNull(_columnIndexOfLastAttemptScore)) {
            _tmpLastAttemptScore = null;
          } else {
            _tmpLastAttemptScore = (int) (_stmt.getLong(_columnIndexOfLastAttemptScore));
          }
          final Integer _tmpBestScore;
          if (_stmt.isNull(_columnIndexOfBestScore)) {
            _tmpBestScore = null;
          } else {
            _tmpBestScore = (int) (_stmt.getLong(_columnIndexOfBestScore));
          }
          final int _tmpAttemptCount;
          _tmpAttemptCount = (int) (_stmt.getLong(_columnIndexOfAttemptCount));
          final Long _tmpLastAttemptAt;
          if (_stmt.isNull(_columnIndexOfLastAttemptAt)) {
            _tmpLastAttemptAt = null;
          } else {
            _tmpLastAttemptAt = _stmt.getLong(_columnIndexOfLastAttemptAt);
          }
          final Long _tmpCompletedAt;
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmpCompletedAt = null;
          } else {
            _tmpCompletedAt = _stmt.getLong(_columnIndexOfCompletedAt);
          }
          final long _tmpCreatedAt;
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt);
          final boolean _tmpIsSystemQuiz;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsSystemQuiz));
          _tmpIsSystemQuiz = _tmp_1 != 0;
          _tmpQuiz = new Quiz(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpInstructionsEnglish,_tmpInstructionsHindi,_tmpLessonId,_tmpCategoryId,_tmpDifficulty,_tmpQuestionCount,_tmpTimeLimit,_tmpPassingScorePercent,_tmpQuizType,_tmpDirectionType,_tmpIsCompleted,_tmpLastAttemptScore,_tmpBestScore,_tmpAttemptCount,_tmpLastAttemptAt,_tmpCompletedAt,_tmpCreatedAt,_tmpIsSystemQuiz);
          final ArrayList<QuizQuestion> _tmpQuestionsCollection;
          final long _tmpKey_1;
          _tmpKey_1 = _stmt.getLong(_columnIndexOfId);
          _tmpQuestionsCollection = _collectionQuestions.get(_tmpKey_1);
          _result = new QuizWithQuestions(_tmpQuiz,_tmpQuestionsCollection);
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
  public Flow<QuizWithQuestions> getQuizWithQuestionsFlow(final long quizId) {
    final String _sql = "SELECT * FROM quizzes WHERE id = ?";
    return FlowUtil.createFlow(__db, true, new String[] {"quiz_questions",
        "quizzes"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, quizId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitleEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleEnglish");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfInstructionsEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "instructionsEnglish");
        final int _columnIndexOfInstructionsHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "instructionsHindi");
        final int _columnIndexOfLessonId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lessonId");
        final int _columnIndexOfCategoryId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "categoryId");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfQuestionCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "questionCount");
        final int _columnIndexOfTimeLimit = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timeLimit");
        final int _columnIndexOfPassingScorePercent = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "passingScorePercent");
        final int _columnIndexOfQuizType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "quizType");
        final int _columnIndexOfDirectionType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "directionType");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfLastAttemptScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAttemptScore");
        final int _columnIndexOfBestScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "bestScore");
        final int _columnIndexOfAttemptCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "attemptCount");
        final int _columnIndexOfLastAttemptAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAttemptAt");
        final int _columnIndexOfCompletedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAt");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _columnIndexOfIsSystemQuiz = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isSystemQuiz");
        final LongSparseArray<ArrayList<QuizQuestion>> _collectionQuestions = new LongSparseArray<ArrayList<QuizQuestion>>();
        while (_stmt.step()) {
          final long _tmpKey;
          _tmpKey = _stmt.getLong(_columnIndexOfId);
          if (!_collectionQuestions.containsKey(_tmpKey)) {
            _collectionQuestions.put(_tmpKey, new ArrayList<QuizQuestion>());
          }
        }
        _stmt.reset();
        __fetchRelationshipquizQuestionsAscomBhashasetuAppDataModelQuizQuestion(_connection, _collectionQuestions);
        final QuizWithQuestions _result;
        if (_stmt.step()) {
          final Quiz _tmpQuiz;
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
          final String _tmpInstructionsEnglish;
          if (_stmt.isNull(_columnIndexOfInstructionsEnglish)) {
            _tmpInstructionsEnglish = null;
          } else {
            _tmpInstructionsEnglish = _stmt.getText(_columnIndexOfInstructionsEnglish);
          }
          final String _tmpInstructionsHindi;
          if (_stmt.isNull(_columnIndexOfInstructionsHindi)) {
            _tmpInstructionsHindi = null;
          } else {
            _tmpInstructionsHindi = _stmt.getText(_columnIndexOfInstructionsHindi);
          }
          final Long _tmpLessonId;
          if (_stmt.isNull(_columnIndexOfLessonId)) {
            _tmpLessonId = null;
          } else {
            _tmpLessonId = _stmt.getLong(_columnIndexOfLessonId);
          }
          final Long _tmpCategoryId;
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null;
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final int _tmpQuestionCount;
          _tmpQuestionCount = (int) (_stmt.getLong(_columnIndexOfQuestionCount));
          final Integer _tmpTimeLimit;
          if (_stmt.isNull(_columnIndexOfTimeLimit)) {
            _tmpTimeLimit = null;
          } else {
            _tmpTimeLimit = (int) (_stmt.getLong(_columnIndexOfTimeLimit));
          }
          final int _tmpPassingScorePercent;
          _tmpPassingScorePercent = (int) (_stmt.getLong(_columnIndexOfPassingScorePercent));
          final int _tmpQuizType;
          _tmpQuizType = (int) (_stmt.getLong(_columnIndexOfQuizType));
          final int _tmpDirectionType;
          _tmpDirectionType = (int) (_stmt.getLong(_columnIndexOfDirectionType));
          final boolean _tmpIsCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp != 0;
          final Integer _tmpLastAttemptScore;
          if (_stmt.isNull(_columnIndexOfLastAttemptScore)) {
            _tmpLastAttemptScore = null;
          } else {
            _tmpLastAttemptScore = (int) (_stmt.getLong(_columnIndexOfLastAttemptScore));
          }
          final Integer _tmpBestScore;
          if (_stmt.isNull(_columnIndexOfBestScore)) {
            _tmpBestScore = null;
          } else {
            _tmpBestScore = (int) (_stmt.getLong(_columnIndexOfBestScore));
          }
          final int _tmpAttemptCount;
          _tmpAttemptCount = (int) (_stmt.getLong(_columnIndexOfAttemptCount));
          final Long _tmpLastAttemptAt;
          if (_stmt.isNull(_columnIndexOfLastAttemptAt)) {
            _tmpLastAttemptAt = null;
          } else {
            _tmpLastAttemptAt = _stmt.getLong(_columnIndexOfLastAttemptAt);
          }
          final Long _tmpCompletedAt;
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmpCompletedAt = null;
          } else {
            _tmpCompletedAt = _stmt.getLong(_columnIndexOfCompletedAt);
          }
          final long _tmpCreatedAt;
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt);
          final boolean _tmpIsSystemQuiz;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsSystemQuiz));
          _tmpIsSystemQuiz = _tmp_1 != 0;
          _tmpQuiz = new Quiz(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpInstructionsEnglish,_tmpInstructionsHindi,_tmpLessonId,_tmpCategoryId,_tmpDifficulty,_tmpQuestionCount,_tmpTimeLimit,_tmpPassingScorePercent,_tmpQuizType,_tmpDirectionType,_tmpIsCompleted,_tmpLastAttemptScore,_tmpBestScore,_tmpAttemptCount,_tmpLastAttemptAt,_tmpCompletedAt,_tmpCreatedAt,_tmpIsSystemQuiz);
          final ArrayList<QuizQuestion> _tmpQuestionsCollection;
          final long _tmpKey_1;
          _tmpKey_1 = _stmt.getLong(_columnIndexOfId);
          _tmpQuestionsCollection = _collectionQuestions.get(_tmpKey_1);
          _result = new QuizWithQuestions(_tmpQuiz,_tmpQuestionsCollection);
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
  public Flow<List<QuizWithQuestions>> getAllQuizzesWithQuestions() {
    final String _sql = "SELECT * FROM quizzes";
    return FlowUtil.createFlow(__db, true, new String[] {"quiz_questions",
        "quizzes"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitleEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleEnglish");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfInstructionsEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "instructionsEnglish");
        final int _columnIndexOfInstructionsHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "instructionsHindi");
        final int _columnIndexOfLessonId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lessonId");
        final int _columnIndexOfCategoryId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "categoryId");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfQuestionCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "questionCount");
        final int _columnIndexOfTimeLimit = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timeLimit");
        final int _columnIndexOfPassingScorePercent = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "passingScorePercent");
        final int _columnIndexOfQuizType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "quizType");
        final int _columnIndexOfDirectionType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "directionType");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfLastAttemptScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAttemptScore");
        final int _columnIndexOfBestScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "bestScore");
        final int _columnIndexOfAttemptCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "attemptCount");
        final int _columnIndexOfLastAttemptAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAttemptAt");
        final int _columnIndexOfCompletedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAt");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _columnIndexOfIsSystemQuiz = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isSystemQuiz");
        final LongSparseArray<ArrayList<QuizQuestion>> _collectionQuestions = new LongSparseArray<ArrayList<QuizQuestion>>();
        while (_stmt.step()) {
          final long _tmpKey;
          _tmpKey = _stmt.getLong(_columnIndexOfId);
          if (!_collectionQuestions.containsKey(_tmpKey)) {
            _collectionQuestions.put(_tmpKey, new ArrayList<QuizQuestion>());
          }
        }
        _stmt.reset();
        __fetchRelationshipquizQuestionsAscomBhashasetuAppDataModelQuizQuestion(_connection, _collectionQuestions);
        final List<QuizWithQuestions> _result = new ArrayList<QuizWithQuestions>();
        while (_stmt.step()) {
          final QuizWithQuestions _item;
          final Quiz _tmpQuiz;
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
          final String _tmpInstructionsEnglish;
          if (_stmt.isNull(_columnIndexOfInstructionsEnglish)) {
            _tmpInstructionsEnglish = null;
          } else {
            _tmpInstructionsEnglish = _stmt.getText(_columnIndexOfInstructionsEnglish);
          }
          final String _tmpInstructionsHindi;
          if (_stmt.isNull(_columnIndexOfInstructionsHindi)) {
            _tmpInstructionsHindi = null;
          } else {
            _tmpInstructionsHindi = _stmt.getText(_columnIndexOfInstructionsHindi);
          }
          final Long _tmpLessonId;
          if (_stmt.isNull(_columnIndexOfLessonId)) {
            _tmpLessonId = null;
          } else {
            _tmpLessonId = _stmt.getLong(_columnIndexOfLessonId);
          }
          final Long _tmpCategoryId;
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null;
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final int _tmpQuestionCount;
          _tmpQuestionCount = (int) (_stmt.getLong(_columnIndexOfQuestionCount));
          final Integer _tmpTimeLimit;
          if (_stmt.isNull(_columnIndexOfTimeLimit)) {
            _tmpTimeLimit = null;
          } else {
            _tmpTimeLimit = (int) (_stmt.getLong(_columnIndexOfTimeLimit));
          }
          final int _tmpPassingScorePercent;
          _tmpPassingScorePercent = (int) (_stmt.getLong(_columnIndexOfPassingScorePercent));
          final int _tmpQuizType;
          _tmpQuizType = (int) (_stmt.getLong(_columnIndexOfQuizType));
          final int _tmpDirectionType;
          _tmpDirectionType = (int) (_stmt.getLong(_columnIndexOfDirectionType));
          final boolean _tmpIsCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp != 0;
          final Integer _tmpLastAttemptScore;
          if (_stmt.isNull(_columnIndexOfLastAttemptScore)) {
            _tmpLastAttemptScore = null;
          } else {
            _tmpLastAttemptScore = (int) (_stmt.getLong(_columnIndexOfLastAttemptScore));
          }
          final Integer _tmpBestScore;
          if (_stmt.isNull(_columnIndexOfBestScore)) {
            _tmpBestScore = null;
          } else {
            _tmpBestScore = (int) (_stmt.getLong(_columnIndexOfBestScore));
          }
          final int _tmpAttemptCount;
          _tmpAttemptCount = (int) (_stmt.getLong(_columnIndexOfAttemptCount));
          final Long _tmpLastAttemptAt;
          if (_stmt.isNull(_columnIndexOfLastAttemptAt)) {
            _tmpLastAttemptAt = null;
          } else {
            _tmpLastAttemptAt = _stmt.getLong(_columnIndexOfLastAttemptAt);
          }
          final Long _tmpCompletedAt;
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmpCompletedAt = null;
          } else {
            _tmpCompletedAt = _stmt.getLong(_columnIndexOfCompletedAt);
          }
          final long _tmpCreatedAt;
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt);
          final boolean _tmpIsSystemQuiz;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsSystemQuiz));
          _tmpIsSystemQuiz = _tmp_1 != 0;
          _tmpQuiz = new Quiz(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpInstructionsEnglish,_tmpInstructionsHindi,_tmpLessonId,_tmpCategoryId,_tmpDifficulty,_tmpQuestionCount,_tmpTimeLimit,_tmpPassingScorePercent,_tmpQuizType,_tmpDirectionType,_tmpIsCompleted,_tmpLastAttemptScore,_tmpBestScore,_tmpAttemptCount,_tmpLastAttemptAt,_tmpCompletedAt,_tmpCreatedAt,_tmpIsSystemQuiz);
          final ArrayList<QuizQuestion> _tmpQuestionsCollection;
          final long _tmpKey_1;
          _tmpKey_1 = _stmt.getLong(_columnIndexOfId);
          _tmpQuestionsCollection = _collectionQuestions.get(_tmpKey_1);
          _item = new QuizWithQuestions(_tmpQuiz,_tmpQuestionsCollection);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Flow<List<QuizWithQuestions>> getQuizzesWithQuestionsByLesson(final long lessonId) {
    final String _sql = "SELECT * FROM quizzes WHERE lessonId = ?";
    return FlowUtil.createFlow(__db, true, new String[] {"quiz_questions",
        "quizzes"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, lessonId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitleEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleEnglish");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfInstructionsEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "instructionsEnglish");
        final int _columnIndexOfInstructionsHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "instructionsHindi");
        final int _columnIndexOfLessonId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lessonId");
        final int _columnIndexOfCategoryId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "categoryId");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfQuestionCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "questionCount");
        final int _columnIndexOfTimeLimit = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timeLimit");
        final int _columnIndexOfPassingScorePercent = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "passingScorePercent");
        final int _columnIndexOfQuizType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "quizType");
        final int _columnIndexOfDirectionType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "directionType");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfLastAttemptScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAttemptScore");
        final int _columnIndexOfBestScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "bestScore");
        final int _columnIndexOfAttemptCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "attemptCount");
        final int _columnIndexOfLastAttemptAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAttemptAt");
        final int _columnIndexOfCompletedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAt");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _columnIndexOfIsSystemQuiz = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isSystemQuiz");
        final LongSparseArray<ArrayList<QuizQuestion>> _collectionQuestions = new LongSparseArray<ArrayList<QuizQuestion>>();
        while (_stmt.step()) {
          final long _tmpKey;
          _tmpKey = _stmt.getLong(_columnIndexOfId);
          if (!_collectionQuestions.containsKey(_tmpKey)) {
            _collectionQuestions.put(_tmpKey, new ArrayList<QuizQuestion>());
          }
        }
        _stmt.reset();
        __fetchRelationshipquizQuestionsAscomBhashasetuAppDataModelQuizQuestion(_connection, _collectionQuestions);
        final List<QuizWithQuestions> _result = new ArrayList<QuizWithQuestions>();
        while (_stmt.step()) {
          final QuizWithQuestions _item;
          final Quiz _tmpQuiz;
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
          final String _tmpInstructionsEnglish;
          if (_stmt.isNull(_columnIndexOfInstructionsEnglish)) {
            _tmpInstructionsEnglish = null;
          } else {
            _tmpInstructionsEnglish = _stmt.getText(_columnIndexOfInstructionsEnglish);
          }
          final String _tmpInstructionsHindi;
          if (_stmt.isNull(_columnIndexOfInstructionsHindi)) {
            _tmpInstructionsHindi = null;
          } else {
            _tmpInstructionsHindi = _stmt.getText(_columnIndexOfInstructionsHindi);
          }
          final Long _tmpLessonId;
          if (_stmt.isNull(_columnIndexOfLessonId)) {
            _tmpLessonId = null;
          } else {
            _tmpLessonId = _stmt.getLong(_columnIndexOfLessonId);
          }
          final Long _tmpCategoryId;
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null;
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final int _tmpQuestionCount;
          _tmpQuestionCount = (int) (_stmt.getLong(_columnIndexOfQuestionCount));
          final Integer _tmpTimeLimit;
          if (_stmt.isNull(_columnIndexOfTimeLimit)) {
            _tmpTimeLimit = null;
          } else {
            _tmpTimeLimit = (int) (_stmt.getLong(_columnIndexOfTimeLimit));
          }
          final int _tmpPassingScorePercent;
          _tmpPassingScorePercent = (int) (_stmt.getLong(_columnIndexOfPassingScorePercent));
          final int _tmpQuizType;
          _tmpQuizType = (int) (_stmt.getLong(_columnIndexOfQuizType));
          final int _tmpDirectionType;
          _tmpDirectionType = (int) (_stmt.getLong(_columnIndexOfDirectionType));
          final boolean _tmpIsCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp != 0;
          final Integer _tmpLastAttemptScore;
          if (_stmt.isNull(_columnIndexOfLastAttemptScore)) {
            _tmpLastAttemptScore = null;
          } else {
            _tmpLastAttemptScore = (int) (_stmt.getLong(_columnIndexOfLastAttemptScore));
          }
          final Integer _tmpBestScore;
          if (_stmt.isNull(_columnIndexOfBestScore)) {
            _tmpBestScore = null;
          } else {
            _tmpBestScore = (int) (_stmt.getLong(_columnIndexOfBestScore));
          }
          final int _tmpAttemptCount;
          _tmpAttemptCount = (int) (_stmt.getLong(_columnIndexOfAttemptCount));
          final Long _tmpLastAttemptAt;
          if (_stmt.isNull(_columnIndexOfLastAttemptAt)) {
            _tmpLastAttemptAt = null;
          } else {
            _tmpLastAttemptAt = _stmt.getLong(_columnIndexOfLastAttemptAt);
          }
          final Long _tmpCompletedAt;
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmpCompletedAt = null;
          } else {
            _tmpCompletedAt = _stmt.getLong(_columnIndexOfCompletedAt);
          }
          final long _tmpCreatedAt;
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt);
          final boolean _tmpIsSystemQuiz;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsSystemQuiz));
          _tmpIsSystemQuiz = _tmp_1 != 0;
          _tmpQuiz = new Quiz(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpInstructionsEnglish,_tmpInstructionsHindi,_tmpLessonId,_tmpCategoryId,_tmpDifficulty,_tmpQuestionCount,_tmpTimeLimit,_tmpPassingScorePercent,_tmpQuizType,_tmpDirectionType,_tmpIsCompleted,_tmpLastAttemptScore,_tmpBestScore,_tmpAttemptCount,_tmpLastAttemptAt,_tmpCompletedAt,_tmpCreatedAt,_tmpIsSystemQuiz);
          final ArrayList<QuizQuestion> _tmpQuestionsCollection;
          final long _tmpKey_1;
          _tmpKey_1 = _stmt.getLong(_columnIndexOfId);
          _tmpQuestionsCollection = _collectionQuestions.get(_tmpKey_1);
          _item = new QuizWithQuestions(_tmpQuiz,_tmpQuestionsCollection);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Flow<List<QuizWithQuestions>> getQuizzesWithQuestionsByCategory(final long categoryId) {
    final String _sql = "SELECT * FROM quizzes WHERE categoryId = ?";
    return FlowUtil.createFlow(__db, true, new String[] {"quiz_questions",
        "quizzes"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, categoryId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitleEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleEnglish");
        final int _columnIndexOfTitleHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "titleHindi");
        final int _columnIndexOfInstructionsEnglish = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "instructionsEnglish");
        final int _columnIndexOfInstructionsHindi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "instructionsHindi");
        final int _columnIndexOfLessonId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lessonId");
        final int _columnIndexOfCategoryId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "categoryId");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfQuestionCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "questionCount");
        final int _columnIndexOfTimeLimit = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timeLimit");
        final int _columnIndexOfPassingScorePercent = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "passingScorePercent");
        final int _columnIndexOfQuizType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "quizType");
        final int _columnIndexOfDirectionType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "directionType");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfLastAttemptScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAttemptScore");
        final int _columnIndexOfBestScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "bestScore");
        final int _columnIndexOfAttemptCount = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "attemptCount");
        final int _columnIndexOfLastAttemptAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastAttemptAt");
        final int _columnIndexOfCompletedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completedAt");
        final int _columnIndexOfCreatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAt");
        final int _columnIndexOfIsSystemQuiz = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isSystemQuiz");
        final LongSparseArray<ArrayList<QuizQuestion>> _collectionQuestions = new LongSparseArray<ArrayList<QuizQuestion>>();
        while (_stmt.step()) {
          final long _tmpKey;
          _tmpKey = _stmt.getLong(_columnIndexOfId);
          if (!_collectionQuestions.containsKey(_tmpKey)) {
            _collectionQuestions.put(_tmpKey, new ArrayList<QuizQuestion>());
          }
        }
        _stmt.reset();
        __fetchRelationshipquizQuestionsAscomBhashasetuAppDataModelQuizQuestion(_connection, _collectionQuestions);
        final List<QuizWithQuestions> _result = new ArrayList<QuizWithQuestions>();
        while (_stmt.step()) {
          final QuizWithQuestions _item;
          final Quiz _tmpQuiz;
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
          final String _tmpInstructionsEnglish;
          if (_stmt.isNull(_columnIndexOfInstructionsEnglish)) {
            _tmpInstructionsEnglish = null;
          } else {
            _tmpInstructionsEnglish = _stmt.getText(_columnIndexOfInstructionsEnglish);
          }
          final String _tmpInstructionsHindi;
          if (_stmt.isNull(_columnIndexOfInstructionsHindi)) {
            _tmpInstructionsHindi = null;
          } else {
            _tmpInstructionsHindi = _stmt.getText(_columnIndexOfInstructionsHindi);
          }
          final Long _tmpLessonId;
          if (_stmt.isNull(_columnIndexOfLessonId)) {
            _tmpLessonId = null;
          } else {
            _tmpLessonId = _stmt.getLong(_columnIndexOfLessonId);
          }
          final Long _tmpCategoryId;
          if (_stmt.isNull(_columnIndexOfCategoryId)) {
            _tmpCategoryId = null;
          } else {
            _tmpCategoryId = _stmt.getLong(_columnIndexOfCategoryId);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final int _tmpQuestionCount;
          _tmpQuestionCount = (int) (_stmt.getLong(_columnIndexOfQuestionCount));
          final Integer _tmpTimeLimit;
          if (_stmt.isNull(_columnIndexOfTimeLimit)) {
            _tmpTimeLimit = null;
          } else {
            _tmpTimeLimit = (int) (_stmt.getLong(_columnIndexOfTimeLimit));
          }
          final int _tmpPassingScorePercent;
          _tmpPassingScorePercent = (int) (_stmt.getLong(_columnIndexOfPassingScorePercent));
          final int _tmpQuizType;
          _tmpQuizType = (int) (_stmt.getLong(_columnIndexOfQuizType));
          final int _tmpDirectionType;
          _tmpDirectionType = (int) (_stmt.getLong(_columnIndexOfDirectionType));
          final boolean _tmpIsCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp != 0;
          final Integer _tmpLastAttemptScore;
          if (_stmt.isNull(_columnIndexOfLastAttemptScore)) {
            _tmpLastAttemptScore = null;
          } else {
            _tmpLastAttemptScore = (int) (_stmt.getLong(_columnIndexOfLastAttemptScore));
          }
          final Integer _tmpBestScore;
          if (_stmt.isNull(_columnIndexOfBestScore)) {
            _tmpBestScore = null;
          } else {
            _tmpBestScore = (int) (_stmt.getLong(_columnIndexOfBestScore));
          }
          final int _tmpAttemptCount;
          _tmpAttemptCount = (int) (_stmt.getLong(_columnIndexOfAttemptCount));
          final Long _tmpLastAttemptAt;
          if (_stmt.isNull(_columnIndexOfLastAttemptAt)) {
            _tmpLastAttemptAt = null;
          } else {
            _tmpLastAttemptAt = _stmt.getLong(_columnIndexOfLastAttemptAt);
          }
          final Long _tmpCompletedAt;
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmpCompletedAt = null;
          } else {
            _tmpCompletedAt = _stmt.getLong(_columnIndexOfCompletedAt);
          }
          final long _tmpCreatedAt;
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt);
          final boolean _tmpIsSystemQuiz;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsSystemQuiz));
          _tmpIsSystemQuiz = _tmp_1 != 0;
          _tmpQuiz = new Quiz(_tmpId,_tmpTitleEnglish,_tmpTitleHindi,_tmpInstructionsEnglish,_tmpInstructionsHindi,_tmpLessonId,_tmpCategoryId,_tmpDifficulty,_tmpQuestionCount,_tmpTimeLimit,_tmpPassingScorePercent,_tmpQuizType,_tmpDirectionType,_tmpIsCompleted,_tmpLastAttemptScore,_tmpBestScore,_tmpAttemptCount,_tmpLastAttemptAt,_tmpCompletedAt,_tmpCreatedAt,_tmpIsSystemQuiz);
          final ArrayList<QuizQuestion> _tmpQuestionsCollection;
          final long _tmpKey_1;
          _tmpKey_1 = _stmt.getLong(_columnIndexOfId);
          _tmpQuestionsCollection = _collectionQuestions.get(_tmpKey_1);
          _item = new QuizWithQuestions(_tmpQuiz,_tmpQuestionsCollection);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Flow<Integer> getQuizCount() {
    final String _sql = "SELECT COUNT(*) FROM quizzes";
    return FlowUtil.createFlow(__db, false, new String[] {"quizzes"}, (_connection) -> {
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
  public Flow<Integer> getCompletedQuizCount() {
    final String _sql = "SELECT COUNT(*) FROM quizzes WHERE isCompleted = 1";
    return FlowUtil.createFlow(__db, false, new String[] {"quizzes"}, (_connection) -> {
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
  public Flow<Float> getAverageQuizScore() {
    final String _sql = "SELECT AVG(CAST(lastAttemptScore AS FLOAT) / (SELECT COUNT(*) FROM quiz_questions WHERE quizId = quizzes.id)) FROM quizzes WHERE lastAttemptScore IS NOT NULL";
    return FlowUtil.createFlow(__db, false, new String[] {"quiz_questions",
        "quizzes"}, (_connection) -> {
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
  public Flow<Integer> getTotalQuestionCount() {
    final String _sql = "SELECT COUNT(*) FROM quiz_questions";
    return FlowUtil.createFlow(__db, false, new String[] {"quiz_questions"}, (_connection) -> {
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
  public Flow<Integer> getTotalCorrectAnswers() {
    final String _sql = "SELECT SUM(correctAttempts) FROM quiz_questions";
    return FlowUtil.createFlow(__db, false, new String[] {"quiz_questions"}, (_connection) -> {
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
  public Flow<Integer> getTotalQuestionAttempts() {
    final String _sql = "SELECT SUM(totalAttempts) FROM quiz_questions";
    return FlowUtil.createFlow(__db, false, new String[] {"quiz_questions"}, (_connection) -> {
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
  public Object deleteAllQuestionsForQuiz(final long quizId,
      final Continuation<? super Unit> $completion) {
    final String _sql = "DELETE FROM quiz_questions WHERE quizId = ?";
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, quizId);
        _stmt.step();
        return Unit.INSTANCE;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object updateQuizProgress(final long quizId, final boolean isCompleted, final int score,
      final long attemptTime, final Continuation<? super Unit> $completion) {
    final String _sql = "\n"
            + "        UPDATE quizzes\n"
            + "        SET isCompleted = ?,\n"
            + "            lastAttemptScore = ?,\n"
            + "            bestScore = CASE WHEN ? > IFNULL(bestScore, 0) THEN ? ELSE bestScore END,\n"
            + "            attemptCount = attemptCount + 1,\n"
            + "            lastAttemptAt = ?,\n"
            + "            completedAt = CASE WHEN ? THEN ? ELSE completedAt END\n"
            + "        WHERE id = ?\n"
            + "    ";
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        final int _tmp = isCompleted ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, score);
        _argIndex = 3;
        _stmt.bindLong(_argIndex, score);
        _argIndex = 4;
        _stmt.bindLong(_argIndex, score);
        _argIndex = 5;
        _stmt.bindLong(_argIndex, attemptTime);
        _argIndex = 6;
        final int _tmp_1 = isCompleted ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp_1);
        _argIndex = 7;
        _stmt.bindLong(_argIndex, attemptTime);
        _argIndex = 8;
        _stmt.bindLong(_argIndex, quizId);
        _stmt.step();
        return Unit.INSTANCE;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object updateQuestionStats(final long questionId, final boolean isCorrect,
      final Continuation<? super Unit> $completion) {
    final String _sql = "\n"
            + "        UPDATE quiz_questions\n"
            + "        SET correctAttempts = correctAttempts + CASE WHEN ? THEN 1 ELSE 0 END,\n"
            + "            totalAttempts = totalAttempts + 1\n"
            + "        WHERE id = ?\n"
            + "    ";
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        final int _tmp = isCorrect ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, questionId);
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

  private void __fetchRelationshipquizQuestionsAscomBhashasetuAppDataModelQuizQuestion(
      @NonNull final SQLiteConnection _connection,
      @NonNull final LongSparseArray<ArrayList<QuizQuestion>> _map) {
    if (_map.isEmpty()) {
      return;
    }
    if (_map.size() > 999) {
      RelationUtil.recursiveFetchLongSparseArray(_map, true, (_tmpMap) -> {
        __fetchRelationshipquizQuestionsAscomBhashasetuAppDataModelQuizQuestion(_connection, _tmpMap);
        return Unit.INSTANCE;
      });
      return;
    }
    final StringBuilder _stringBuilder = new StringBuilder();
    _stringBuilder.append("SELECT `id`,`quizId`,`wordId`,`questionTextEnglish`,`questionTextHindi`,`questionType`,`correctAnswer`,`options`,`imageUrl`,`audioUrl`,`explanationEnglish`,`explanationHindi`,`difficulty`,`points`,`orderInQuiz`,`correctAttempts`,`totalAttempts` FROM `quiz_questions` WHERE `quizId` IN (");
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
      final int _itemKeyIndex = SQLiteStatementUtil.getColumnIndex(_stmt, "quizId");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _columnIndexOfId = 0;
      final int _columnIndexOfQuizId = 1;
      final int _columnIndexOfWordId = 2;
      final int _columnIndexOfQuestionTextEnglish = 3;
      final int _columnIndexOfQuestionTextHindi = 4;
      final int _columnIndexOfQuestionType = 5;
      final int _columnIndexOfCorrectAnswer = 6;
      final int _columnIndexOfOptions = 7;
      final int _columnIndexOfImageUrl = 8;
      final int _columnIndexOfAudioUrl = 9;
      final int _columnIndexOfExplanationEnglish = 10;
      final int _columnIndexOfExplanationHindi = 11;
      final int _columnIndexOfDifficulty = 12;
      final int _columnIndexOfPoints = 13;
      final int _columnIndexOfOrderInQuiz = 14;
      final int _columnIndexOfCorrectAttempts = 15;
      final int _columnIndexOfTotalAttempts = 16;
      while (_stmt.step()) {
        final long _tmpKey;
        _tmpKey = _stmt.getLong(_itemKeyIndex);
        final ArrayList<QuizQuestion> _tmpRelation = _map.get(_tmpKey);
        if (_tmpRelation != null) {
          final QuizQuestion _item_1;
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final long _tmpQuizId;
          _tmpQuizId = _stmt.getLong(_columnIndexOfQuizId);
          final Long _tmpWordId;
          if (_stmt.isNull(_columnIndexOfWordId)) {
            _tmpWordId = null;
          } else {
            _tmpWordId = _stmt.getLong(_columnIndexOfWordId);
          }
          final String _tmpQuestionTextEnglish;
          if (_stmt.isNull(_columnIndexOfQuestionTextEnglish)) {
            _tmpQuestionTextEnglish = null;
          } else {
            _tmpQuestionTextEnglish = _stmt.getText(_columnIndexOfQuestionTextEnglish);
          }
          final String _tmpQuestionTextHindi;
          if (_stmt.isNull(_columnIndexOfQuestionTextHindi)) {
            _tmpQuestionTextHindi = null;
          } else {
            _tmpQuestionTextHindi = _stmt.getText(_columnIndexOfQuestionTextHindi);
          }
          final int _tmpQuestionType;
          _tmpQuestionType = (int) (_stmt.getLong(_columnIndexOfQuestionType));
          final String _tmpCorrectAnswer;
          if (_stmt.isNull(_columnIndexOfCorrectAnswer)) {
            _tmpCorrectAnswer = null;
          } else {
            _tmpCorrectAnswer = _stmt.getText(_columnIndexOfCorrectAnswer);
          }
          final String _tmpOptions;
          if (_stmt.isNull(_columnIndexOfOptions)) {
            _tmpOptions = null;
          } else {
            _tmpOptions = _stmt.getText(_columnIndexOfOptions);
          }
          final String _tmpImageUrl;
          if (_stmt.isNull(_columnIndexOfImageUrl)) {
            _tmpImageUrl = null;
          } else {
            _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl);
          }
          final String _tmpAudioUrl;
          if (_stmt.isNull(_columnIndexOfAudioUrl)) {
            _tmpAudioUrl = null;
          } else {
            _tmpAudioUrl = _stmt.getText(_columnIndexOfAudioUrl);
          }
          final String _tmpExplanationEnglish;
          if (_stmt.isNull(_columnIndexOfExplanationEnglish)) {
            _tmpExplanationEnglish = null;
          } else {
            _tmpExplanationEnglish = _stmt.getText(_columnIndexOfExplanationEnglish);
          }
          final String _tmpExplanationHindi;
          if (_stmt.isNull(_columnIndexOfExplanationHindi)) {
            _tmpExplanationHindi = null;
          } else {
            _tmpExplanationHindi = _stmt.getText(_columnIndexOfExplanationHindi);
          }
          final int _tmpDifficulty;
          _tmpDifficulty = (int) (_stmt.getLong(_columnIndexOfDifficulty));
          final int _tmpPoints;
          _tmpPoints = (int) (_stmt.getLong(_columnIndexOfPoints));
          final int _tmpOrderInQuiz;
          _tmpOrderInQuiz = (int) (_stmt.getLong(_columnIndexOfOrderInQuiz));
          final int _tmpCorrectAttempts;
          _tmpCorrectAttempts = (int) (_stmt.getLong(_columnIndexOfCorrectAttempts));
          final int _tmpTotalAttempts;
          _tmpTotalAttempts = (int) (_stmt.getLong(_columnIndexOfTotalAttempts));
          _item_1 = new QuizQuestion(_tmpId,_tmpQuizId,_tmpWordId,_tmpQuestionTextEnglish,_tmpQuestionTextHindi,_tmpQuestionType,_tmpCorrectAnswer,_tmpOptions,_tmpImageUrl,_tmpAudioUrl,_tmpExplanationEnglish,_tmpExplanationHindi,_tmpDifficulty,_tmpPoints,_tmpOrderInQuiz,_tmpCorrectAttempts,_tmpTotalAttempts);
          _tmpRelation.add(_item_1);
        }
      }
    } finally {
      _stmt.close();
    }
  }
}
