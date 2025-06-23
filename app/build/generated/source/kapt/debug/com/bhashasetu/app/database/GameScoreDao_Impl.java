package com.bhashasetu.app.database;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeleteOrUpdateAdapter;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteStatement;
import com.bhashasetu.app.model.GameScore;
import java.lang.Class;
import java.lang.Double;
import java.lang.Integer;
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
public final class GameScoreDao_Impl implements GameScoreDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<GameScore> __insertAdapterOfGameScore;

  private final EntityDeleteOrUpdateAdapter<GameScore> __deleteAdapterOfGameScore;

  private final EntityDeleteOrUpdateAdapter<GameScore> __updateAdapterOfGameScore;

  public GameScoreDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfGameScore = new EntityInsertAdapter<GameScore>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `game_scores` (`id`,`userId`,`gameType`,`category`,`difficulty`,`score`,`maxScore`,`correctAnswers`,`totalQuestions`,`timeTaken`,`datePlayed`,`isCompleted`,`gameMode`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final GameScore entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getUserId());
        if (entity.getGameType() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getGameType());
        }
        if (entity.getCategory() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getCategory());
        }
        if (entity.getDifficulty() == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, entity.getDifficulty());
        }
        statement.bindLong(6, entity.getScore());
        statement.bindLong(7, entity.getMaxScore());
        statement.bindLong(8, entity.getCorrectAnswers());
        statement.bindLong(9, entity.getTotalQuestions());
        statement.bindLong(10, entity.getTimeTaken());
        final Long _tmp = Converters.dateToTimestamp(entity.getDatePlayed());
        if (_tmp == null) {
          statement.bindNull(11);
        } else {
          statement.bindLong(11, _tmp);
        }
        final int _tmp_1 = entity.isCompleted() ? 1 : 0;
        statement.bindLong(12, _tmp_1);
        if (entity.getGameMode() == null) {
          statement.bindNull(13);
        } else {
          statement.bindText(13, entity.getGameMode());
        }
      }
    };
    this.__deleteAdapterOfGameScore = new EntityDeleteOrUpdateAdapter<GameScore>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `game_scores` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final GameScore entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfGameScore = new EntityDeleteOrUpdateAdapter<GameScore>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `game_scores` SET `id` = ?,`userId` = ?,`gameType` = ?,`category` = ?,`difficulty` = ?,`score` = ?,`maxScore` = ?,`correctAnswers` = ?,`totalQuestions` = ?,`timeTaken` = ?,`datePlayed` = ?,`isCompleted` = ?,`gameMode` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final GameScore entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getUserId());
        if (entity.getGameType() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getGameType());
        }
        if (entity.getCategory() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getCategory());
        }
        if (entity.getDifficulty() == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, entity.getDifficulty());
        }
        statement.bindLong(6, entity.getScore());
        statement.bindLong(7, entity.getMaxScore());
        statement.bindLong(8, entity.getCorrectAnswers());
        statement.bindLong(9, entity.getTotalQuestions());
        statement.bindLong(10, entity.getTimeTaken());
        final Long _tmp = Converters.dateToTimestamp(entity.getDatePlayed());
        if (_tmp == null) {
          statement.bindNull(11);
        } else {
          statement.bindLong(11, _tmp);
        }
        final int _tmp_1 = entity.isCompleted() ? 1 : 0;
        statement.bindLong(12, _tmp_1);
        if (entity.getGameMode() == null) {
          statement.bindNull(13);
        } else {
          statement.bindText(13, entity.getGameMode());
        }
        statement.bindLong(14, entity.getId());
      }
    };
  }

  @Override
  public void insert(final GameScore gameScore) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __insertAdapterOfGameScore.insert(_connection, gameScore);
      return null;
    });
  }

  @Override
  public void insertAll(final List<GameScore> gameScores) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __insertAdapterOfGameScore.insert(_connection, gameScores);
      return null;
    });
  }

  @Override
  public void delete(final GameScore gameScore) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __deleteAdapterOfGameScore.handle(_connection, gameScore);
      return null;
    });
  }

  @Override
  public void update(final GameScore gameScore) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __updateAdapterOfGameScore.handle(_connection, gameScore);
      return null;
    });
  }

  @Override
  public LiveData<List<GameScore>> getAllScores() {
    final String _sql = "SELECT * FROM game_scores ORDER BY datePlayed DESC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"game_scores"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfUserId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "userId");
        final int _columnIndexOfGameType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "gameType");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "score");
        final int _columnIndexOfMaxScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "maxScore");
        final int _columnIndexOfCorrectAnswers = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctAnswers");
        final int _columnIndexOfTotalQuestions = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalQuestions");
        final int _columnIndexOfTimeTaken = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timeTaken");
        final int _columnIndexOfDatePlayed = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "datePlayed");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfGameMode = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "gameMode");
        final List<GameScore> _result = new ArrayList<GameScore>();
        while (_stmt.step()) {
          final GameScore _item;
          _item = new GameScore();
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final int _tmpUserId;
          _tmpUserId = (int) (_stmt.getLong(_columnIndexOfUserId));
          _item.setUserId(_tmpUserId);
          final String _tmpGameType;
          if (_stmt.isNull(_columnIndexOfGameType)) {
            _tmpGameType = null;
          } else {
            _tmpGameType = _stmt.getText(_columnIndexOfGameType);
          }
          _item.setGameType(_tmpGameType);
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          _item.setCategory(_tmpCategory);
          final String _tmpDifficulty;
          if (_stmt.isNull(_columnIndexOfDifficulty)) {
            _tmpDifficulty = null;
          } else {
            _tmpDifficulty = _stmt.getText(_columnIndexOfDifficulty);
          }
          _item.setDifficulty(_tmpDifficulty);
          final int _tmpScore;
          _tmpScore = (int) (_stmt.getLong(_columnIndexOfScore));
          _item.setScore(_tmpScore);
          final int _tmpMaxScore;
          _tmpMaxScore = (int) (_stmt.getLong(_columnIndexOfMaxScore));
          _item.setMaxScore(_tmpMaxScore);
          final int _tmpCorrectAnswers;
          _tmpCorrectAnswers = (int) (_stmt.getLong(_columnIndexOfCorrectAnswers));
          _item.setCorrectAnswers(_tmpCorrectAnswers);
          final int _tmpTotalQuestions;
          _tmpTotalQuestions = (int) (_stmt.getLong(_columnIndexOfTotalQuestions));
          _item.setTotalQuestions(_tmpTotalQuestions);
          final long _tmpTimeTaken;
          _tmpTimeTaken = _stmt.getLong(_columnIndexOfTimeTaken);
          _item.setTimeTaken(_tmpTimeTaken);
          final Date _tmpDatePlayed;
          final Long _tmp;
          if (_stmt.isNull(_columnIndexOfDatePlayed)) {
            _tmp = null;
          } else {
            _tmp = _stmt.getLong(_columnIndexOfDatePlayed);
          }
          _tmpDatePlayed = Converters.fromTimestamp(_tmp);
          _item.setDatePlayed(_tmpDatePlayed);
          final boolean _tmpIsCompleted;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp_1 != 0;
          _item.setCompleted(_tmpIsCompleted);
          final String _tmpGameMode;
          if (_stmt.isNull(_columnIndexOfGameMode)) {
            _tmpGameMode = null;
          } else {
            _tmpGameMode = _stmt.getText(_columnIndexOfGameMode);
          }
          _item.setGameMode(_tmpGameMode);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<GameScore>> getScoresByUserId(final int userId) {
    final String _sql = "SELECT * FROM game_scores WHERE userId = ? ORDER BY datePlayed DESC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"game_scores"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, userId);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfUserId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "userId");
        final int _columnIndexOfGameType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "gameType");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "score");
        final int _columnIndexOfMaxScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "maxScore");
        final int _columnIndexOfCorrectAnswers = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctAnswers");
        final int _columnIndexOfTotalQuestions = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalQuestions");
        final int _columnIndexOfTimeTaken = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timeTaken");
        final int _columnIndexOfDatePlayed = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "datePlayed");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfGameMode = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "gameMode");
        final List<GameScore> _result = new ArrayList<GameScore>();
        while (_stmt.step()) {
          final GameScore _item;
          _item = new GameScore();
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final int _tmpUserId;
          _tmpUserId = (int) (_stmt.getLong(_columnIndexOfUserId));
          _item.setUserId(_tmpUserId);
          final String _tmpGameType;
          if (_stmt.isNull(_columnIndexOfGameType)) {
            _tmpGameType = null;
          } else {
            _tmpGameType = _stmt.getText(_columnIndexOfGameType);
          }
          _item.setGameType(_tmpGameType);
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          _item.setCategory(_tmpCategory);
          final String _tmpDifficulty;
          if (_stmt.isNull(_columnIndexOfDifficulty)) {
            _tmpDifficulty = null;
          } else {
            _tmpDifficulty = _stmt.getText(_columnIndexOfDifficulty);
          }
          _item.setDifficulty(_tmpDifficulty);
          final int _tmpScore;
          _tmpScore = (int) (_stmt.getLong(_columnIndexOfScore));
          _item.setScore(_tmpScore);
          final int _tmpMaxScore;
          _tmpMaxScore = (int) (_stmt.getLong(_columnIndexOfMaxScore));
          _item.setMaxScore(_tmpMaxScore);
          final int _tmpCorrectAnswers;
          _tmpCorrectAnswers = (int) (_stmt.getLong(_columnIndexOfCorrectAnswers));
          _item.setCorrectAnswers(_tmpCorrectAnswers);
          final int _tmpTotalQuestions;
          _tmpTotalQuestions = (int) (_stmt.getLong(_columnIndexOfTotalQuestions));
          _item.setTotalQuestions(_tmpTotalQuestions);
          final long _tmpTimeTaken;
          _tmpTimeTaken = _stmt.getLong(_columnIndexOfTimeTaken);
          _item.setTimeTaken(_tmpTimeTaken);
          final Date _tmpDatePlayed;
          final Long _tmp;
          if (_stmt.isNull(_columnIndexOfDatePlayed)) {
            _tmp = null;
          } else {
            _tmp = _stmt.getLong(_columnIndexOfDatePlayed);
          }
          _tmpDatePlayed = Converters.fromTimestamp(_tmp);
          _item.setDatePlayed(_tmpDatePlayed);
          final boolean _tmpIsCompleted;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp_1 != 0;
          _item.setCompleted(_tmpIsCompleted);
          final String _tmpGameMode;
          if (_stmt.isNull(_columnIndexOfGameMode)) {
            _tmpGameMode = null;
          } else {
            _tmpGameMode = _stmt.getText(_columnIndexOfGameMode);
          }
          _item.setGameMode(_tmpGameMode);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<GameScore>> getScoresByGameType(final String gameType) {
    final String _sql = "SELECT * FROM game_scores WHERE gameType = ? ORDER BY score DESC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"game_scores"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (gameType == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, gameType);
        }
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfUserId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "userId");
        final int _columnIndexOfGameType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "gameType");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "score");
        final int _columnIndexOfMaxScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "maxScore");
        final int _columnIndexOfCorrectAnswers = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctAnswers");
        final int _columnIndexOfTotalQuestions = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalQuestions");
        final int _columnIndexOfTimeTaken = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timeTaken");
        final int _columnIndexOfDatePlayed = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "datePlayed");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfGameMode = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "gameMode");
        final List<GameScore> _result = new ArrayList<GameScore>();
        while (_stmt.step()) {
          final GameScore _item;
          _item = new GameScore();
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final int _tmpUserId;
          _tmpUserId = (int) (_stmt.getLong(_columnIndexOfUserId));
          _item.setUserId(_tmpUserId);
          final String _tmpGameType;
          if (_stmt.isNull(_columnIndexOfGameType)) {
            _tmpGameType = null;
          } else {
            _tmpGameType = _stmt.getText(_columnIndexOfGameType);
          }
          _item.setGameType(_tmpGameType);
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          _item.setCategory(_tmpCategory);
          final String _tmpDifficulty;
          if (_stmt.isNull(_columnIndexOfDifficulty)) {
            _tmpDifficulty = null;
          } else {
            _tmpDifficulty = _stmt.getText(_columnIndexOfDifficulty);
          }
          _item.setDifficulty(_tmpDifficulty);
          final int _tmpScore;
          _tmpScore = (int) (_stmt.getLong(_columnIndexOfScore));
          _item.setScore(_tmpScore);
          final int _tmpMaxScore;
          _tmpMaxScore = (int) (_stmt.getLong(_columnIndexOfMaxScore));
          _item.setMaxScore(_tmpMaxScore);
          final int _tmpCorrectAnswers;
          _tmpCorrectAnswers = (int) (_stmt.getLong(_columnIndexOfCorrectAnswers));
          _item.setCorrectAnswers(_tmpCorrectAnswers);
          final int _tmpTotalQuestions;
          _tmpTotalQuestions = (int) (_stmt.getLong(_columnIndexOfTotalQuestions));
          _item.setTotalQuestions(_tmpTotalQuestions);
          final long _tmpTimeTaken;
          _tmpTimeTaken = _stmt.getLong(_columnIndexOfTimeTaken);
          _item.setTimeTaken(_tmpTimeTaken);
          final Date _tmpDatePlayed;
          final Long _tmp;
          if (_stmt.isNull(_columnIndexOfDatePlayed)) {
            _tmp = null;
          } else {
            _tmp = _stmt.getLong(_columnIndexOfDatePlayed);
          }
          _tmpDatePlayed = Converters.fromTimestamp(_tmp);
          _item.setDatePlayed(_tmpDatePlayed);
          final boolean _tmpIsCompleted;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp_1 != 0;
          _item.setCompleted(_tmpIsCompleted);
          final String _tmpGameMode;
          if (_stmt.isNull(_columnIndexOfGameMode)) {
            _tmpGameMode = null;
          } else {
            _tmpGameMode = _stmt.getText(_columnIndexOfGameMode);
          }
          _item.setGameMode(_tmpGameMode);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<GameScore>> getScoresByUserAndGameType(final int userId,
      final String gameType) {
    final String _sql = "SELECT * FROM game_scores WHERE userId = ? AND gameType = ? ORDER BY datePlayed DESC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"game_scores"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, userId);
        _argIndex = 2;
        if (gameType == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, gameType);
        }
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfUserId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "userId");
        final int _columnIndexOfGameType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "gameType");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "score");
        final int _columnIndexOfMaxScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "maxScore");
        final int _columnIndexOfCorrectAnswers = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctAnswers");
        final int _columnIndexOfTotalQuestions = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalQuestions");
        final int _columnIndexOfTimeTaken = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timeTaken");
        final int _columnIndexOfDatePlayed = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "datePlayed");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfGameMode = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "gameMode");
        final List<GameScore> _result = new ArrayList<GameScore>();
        while (_stmt.step()) {
          final GameScore _item;
          _item = new GameScore();
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final int _tmpUserId;
          _tmpUserId = (int) (_stmt.getLong(_columnIndexOfUserId));
          _item.setUserId(_tmpUserId);
          final String _tmpGameType;
          if (_stmt.isNull(_columnIndexOfGameType)) {
            _tmpGameType = null;
          } else {
            _tmpGameType = _stmt.getText(_columnIndexOfGameType);
          }
          _item.setGameType(_tmpGameType);
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          _item.setCategory(_tmpCategory);
          final String _tmpDifficulty;
          if (_stmt.isNull(_columnIndexOfDifficulty)) {
            _tmpDifficulty = null;
          } else {
            _tmpDifficulty = _stmt.getText(_columnIndexOfDifficulty);
          }
          _item.setDifficulty(_tmpDifficulty);
          final int _tmpScore;
          _tmpScore = (int) (_stmt.getLong(_columnIndexOfScore));
          _item.setScore(_tmpScore);
          final int _tmpMaxScore;
          _tmpMaxScore = (int) (_stmt.getLong(_columnIndexOfMaxScore));
          _item.setMaxScore(_tmpMaxScore);
          final int _tmpCorrectAnswers;
          _tmpCorrectAnswers = (int) (_stmt.getLong(_columnIndexOfCorrectAnswers));
          _item.setCorrectAnswers(_tmpCorrectAnswers);
          final int _tmpTotalQuestions;
          _tmpTotalQuestions = (int) (_stmt.getLong(_columnIndexOfTotalQuestions));
          _item.setTotalQuestions(_tmpTotalQuestions);
          final long _tmpTimeTaken;
          _tmpTimeTaken = _stmt.getLong(_columnIndexOfTimeTaken);
          _item.setTimeTaken(_tmpTimeTaken);
          final Date _tmpDatePlayed;
          final Long _tmp;
          if (_stmt.isNull(_columnIndexOfDatePlayed)) {
            _tmp = null;
          } else {
            _tmp = _stmt.getLong(_columnIndexOfDatePlayed);
          }
          _tmpDatePlayed = Converters.fromTimestamp(_tmp);
          _item.setDatePlayed(_tmpDatePlayed);
          final boolean _tmpIsCompleted;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp_1 != 0;
          _item.setCompleted(_tmpIsCompleted);
          final String _tmpGameMode;
          if (_stmt.isNull(_columnIndexOfGameMode)) {
            _tmpGameMode = null;
          } else {
            _tmpGameMode = _stmt.getText(_columnIndexOfGameMode);
          }
          _item.setGameMode(_tmpGameMode);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<Integer> getHighestScore(final int userId, final String gameType) {
    final String _sql = "SELECT MAX(score) FROM game_scores WHERE userId = ? AND gameType = ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"game_scores"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, userId);
        _argIndex = 2;
        if (gameType == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, gameType);
        }
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
  public LiveData<Double> getAverageScore(final int userId, final String gameType) {
    final String _sql = "SELECT AVG(score) FROM game_scores WHERE userId = ? AND gameType = ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"game_scores"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, userId);
        _argIndex = 2;
        if (gameType == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, gameType);
        }
        final Double _result;
        if (_stmt.step()) {
          final Double _tmp;
          if (_stmt.isNull(0)) {
            _tmp = null;
          } else {
            _tmp = _stmt.getDouble(0);
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
  public LiveData<List<GameScore>> getRecentScores(final int userId, final int limit) {
    final String _sql = "SELECT * FROM game_scores WHERE userId = ? ORDER BY datePlayed DESC LIMIT ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"game_scores"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, userId);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, limit);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfUserId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "userId");
        final int _columnIndexOfGameType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "gameType");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "score");
        final int _columnIndexOfMaxScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "maxScore");
        final int _columnIndexOfCorrectAnswers = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctAnswers");
        final int _columnIndexOfTotalQuestions = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalQuestions");
        final int _columnIndexOfTimeTaken = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timeTaken");
        final int _columnIndexOfDatePlayed = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "datePlayed");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfGameMode = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "gameMode");
        final List<GameScore> _result = new ArrayList<GameScore>();
        while (_stmt.step()) {
          final GameScore _item;
          _item = new GameScore();
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final int _tmpUserId;
          _tmpUserId = (int) (_stmt.getLong(_columnIndexOfUserId));
          _item.setUserId(_tmpUserId);
          final String _tmpGameType;
          if (_stmt.isNull(_columnIndexOfGameType)) {
            _tmpGameType = null;
          } else {
            _tmpGameType = _stmt.getText(_columnIndexOfGameType);
          }
          _item.setGameType(_tmpGameType);
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          _item.setCategory(_tmpCategory);
          final String _tmpDifficulty;
          if (_stmt.isNull(_columnIndexOfDifficulty)) {
            _tmpDifficulty = null;
          } else {
            _tmpDifficulty = _stmt.getText(_columnIndexOfDifficulty);
          }
          _item.setDifficulty(_tmpDifficulty);
          final int _tmpScore;
          _tmpScore = (int) (_stmt.getLong(_columnIndexOfScore));
          _item.setScore(_tmpScore);
          final int _tmpMaxScore;
          _tmpMaxScore = (int) (_stmt.getLong(_columnIndexOfMaxScore));
          _item.setMaxScore(_tmpMaxScore);
          final int _tmpCorrectAnswers;
          _tmpCorrectAnswers = (int) (_stmt.getLong(_columnIndexOfCorrectAnswers));
          _item.setCorrectAnswers(_tmpCorrectAnswers);
          final int _tmpTotalQuestions;
          _tmpTotalQuestions = (int) (_stmt.getLong(_columnIndexOfTotalQuestions));
          _item.setTotalQuestions(_tmpTotalQuestions);
          final long _tmpTimeTaken;
          _tmpTimeTaken = _stmt.getLong(_columnIndexOfTimeTaken);
          _item.setTimeTaken(_tmpTimeTaken);
          final Date _tmpDatePlayed;
          final Long _tmp;
          if (_stmt.isNull(_columnIndexOfDatePlayed)) {
            _tmp = null;
          } else {
            _tmp = _stmt.getLong(_columnIndexOfDatePlayed);
          }
          _tmpDatePlayed = Converters.fromTimestamp(_tmp);
          _item.setDatePlayed(_tmpDatePlayed);
          final boolean _tmpIsCompleted;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp_1 != 0;
          _item.setCompleted(_tmpIsCompleted);
          final String _tmpGameMode;
          if (_stmt.isNull(_columnIndexOfGameMode)) {
            _tmpGameMode = null;
          } else {
            _tmpGameMode = _stmt.getText(_columnIndexOfGameMode);
          }
          _item.setGameMode(_tmpGameMode);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<GameScore>> getTopScores(final String gameType, final int limit) {
    final String _sql = "SELECT * FROM game_scores WHERE gameType = ? ORDER BY score DESC LIMIT ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"game_scores"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (gameType == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, gameType);
        }
        _argIndex = 2;
        _stmt.bindLong(_argIndex, limit);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfUserId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "userId");
        final int _columnIndexOfGameType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "gameType");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "score");
        final int _columnIndexOfMaxScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "maxScore");
        final int _columnIndexOfCorrectAnswers = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctAnswers");
        final int _columnIndexOfTotalQuestions = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalQuestions");
        final int _columnIndexOfTimeTaken = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timeTaken");
        final int _columnIndexOfDatePlayed = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "datePlayed");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfGameMode = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "gameMode");
        final List<GameScore> _result = new ArrayList<GameScore>();
        while (_stmt.step()) {
          final GameScore _item;
          _item = new GameScore();
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final int _tmpUserId;
          _tmpUserId = (int) (_stmt.getLong(_columnIndexOfUserId));
          _item.setUserId(_tmpUserId);
          final String _tmpGameType;
          if (_stmt.isNull(_columnIndexOfGameType)) {
            _tmpGameType = null;
          } else {
            _tmpGameType = _stmt.getText(_columnIndexOfGameType);
          }
          _item.setGameType(_tmpGameType);
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          _item.setCategory(_tmpCategory);
          final String _tmpDifficulty;
          if (_stmt.isNull(_columnIndexOfDifficulty)) {
            _tmpDifficulty = null;
          } else {
            _tmpDifficulty = _stmt.getText(_columnIndexOfDifficulty);
          }
          _item.setDifficulty(_tmpDifficulty);
          final int _tmpScore;
          _tmpScore = (int) (_stmt.getLong(_columnIndexOfScore));
          _item.setScore(_tmpScore);
          final int _tmpMaxScore;
          _tmpMaxScore = (int) (_stmt.getLong(_columnIndexOfMaxScore));
          _item.setMaxScore(_tmpMaxScore);
          final int _tmpCorrectAnswers;
          _tmpCorrectAnswers = (int) (_stmt.getLong(_columnIndexOfCorrectAnswers));
          _item.setCorrectAnswers(_tmpCorrectAnswers);
          final int _tmpTotalQuestions;
          _tmpTotalQuestions = (int) (_stmt.getLong(_columnIndexOfTotalQuestions));
          _item.setTotalQuestions(_tmpTotalQuestions);
          final long _tmpTimeTaken;
          _tmpTimeTaken = _stmt.getLong(_columnIndexOfTimeTaken);
          _item.setTimeTaken(_tmpTimeTaken);
          final Date _tmpDatePlayed;
          final Long _tmp;
          if (_stmt.isNull(_columnIndexOfDatePlayed)) {
            _tmp = null;
          } else {
            _tmp = _stmt.getLong(_columnIndexOfDatePlayed);
          }
          _tmpDatePlayed = Converters.fromTimestamp(_tmp);
          _item.setDatePlayed(_tmpDatePlayed);
          final boolean _tmpIsCompleted;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp_1 != 0;
          _item.setCompleted(_tmpIsCompleted);
          final String _tmpGameMode;
          if (_stmt.isNull(_columnIndexOfGameMode)) {
            _tmpGameMode = null;
          } else {
            _tmpGameMode = _stmt.getText(_columnIndexOfGameMode);
          }
          _item.setGameMode(_tmpGameMode);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<GameScore>> getScoresByCategory(final String category) {
    final String _sql = "SELECT * FROM game_scores WHERE category = ? ORDER BY score DESC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"game_scores"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (category == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, category);
        }
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfUserId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "userId");
        final int _columnIndexOfGameType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "gameType");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfDifficulty = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "difficulty");
        final int _columnIndexOfScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "score");
        final int _columnIndexOfMaxScore = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "maxScore");
        final int _columnIndexOfCorrectAnswers = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "correctAnswers");
        final int _columnIndexOfTotalQuestions = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalQuestions");
        final int _columnIndexOfTimeTaken = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timeTaken");
        final int _columnIndexOfDatePlayed = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "datePlayed");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final int _columnIndexOfGameMode = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "gameMode");
        final List<GameScore> _result = new ArrayList<GameScore>();
        while (_stmt.step()) {
          final GameScore _item;
          _item = new GameScore();
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final int _tmpUserId;
          _tmpUserId = (int) (_stmt.getLong(_columnIndexOfUserId));
          _item.setUserId(_tmpUserId);
          final String _tmpGameType;
          if (_stmt.isNull(_columnIndexOfGameType)) {
            _tmpGameType = null;
          } else {
            _tmpGameType = _stmt.getText(_columnIndexOfGameType);
          }
          _item.setGameType(_tmpGameType);
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          _item.setCategory(_tmpCategory);
          final String _tmpDifficulty;
          if (_stmt.isNull(_columnIndexOfDifficulty)) {
            _tmpDifficulty = null;
          } else {
            _tmpDifficulty = _stmt.getText(_columnIndexOfDifficulty);
          }
          _item.setDifficulty(_tmpDifficulty);
          final int _tmpScore;
          _tmpScore = (int) (_stmt.getLong(_columnIndexOfScore));
          _item.setScore(_tmpScore);
          final int _tmpMaxScore;
          _tmpMaxScore = (int) (_stmt.getLong(_columnIndexOfMaxScore));
          _item.setMaxScore(_tmpMaxScore);
          final int _tmpCorrectAnswers;
          _tmpCorrectAnswers = (int) (_stmt.getLong(_columnIndexOfCorrectAnswers));
          _item.setCorrectAnswers(_tmpCorrectAnswers);
          final int _tmpTotalQuestions;
          _tmpTotalQuestions = (int) (_stmt.getLong(_columnIndexOfTotalQuestions));
          _item.setTotalQuestions(_tmpTotalQuestions);
          final long _tmpTimeTaken;
          _tmpTimeTaken = _stmt.getLong(_columnIndexOfTimeTaken);
          _item.setTimeTaken(_tmpTimeTaken);
          final Date _tmpDatePlayed;
          final Long _tmp;
          if (_stmt.isNull(_columnIndexOfDatePlayed)) {
            _tmp = null;
          } else {
            _tmp = _stmt.getLong(_columnIndexOfDatePlayed);
          }
          _tmpDatePlayed = Converters.fromTimestamp(_tmp);
          _item.setDatePlayed(_tmpDatePlayed);
          final boolean _tmpIsCompleted;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp_1 != 0;
          _item.setCompleted(_tmpIsCompleted);
          final String _tmpGameMode;
          if (_stmt.isNull(_columnIndexOfGameMode)) {
            _tmpGameMode = null;
          } else {
            _tmpGameMode = _stmt.getText(_columnIndexOfGameMode);
          }
          _item.setGameMode(_tmpGameMode);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<Integer> getTotalGamesPlayed(final int userId) {
    final String _sql = "SELECT COUNT(*) FROM game_scores WHERE userId = ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"game_scores"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, userId);
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
  public LiveData<Integer> getTotalCount() {
    final String _sql = "SELECT COUNT(*) FROM game_scores";
    return __db.getInvalidationTracker().createLiveData(new String[] {"game_scores"}, false, (_connection) -> {
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
    final String _sql = "DELETE FROM game_scores";
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

  @Override
  public void deleteScoresByUserId(final int userId) {
    final String _sql = "DELETE FROM game_scores WHERE userId = ?";
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, userId);
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
