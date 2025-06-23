package com.bhashasetu.app.database;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeleteOrUpdateAdapter;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteStatement;
import com.bhashasetu.app.model.User;
import java.lang.Class;
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
public final class UserDao_Impl implements UserDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<User> __insertAdapterOfUser;

  private final EntityDeleteOrUpdateAdapter<User> __deleteAdapterOfUser;

  private final EntityDeleteOrUpdateAdapter<User> __updateAdapterOfUser;

  public UserDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfUser = new EntityInsertAdapter<User>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `users` (`id`,`username`,`email`,`fullName`,`profileImageUrl`,`preferredLanguage`,`totalPoints`,`currentStreak`,`maxStreak`,`dateJoined`,`lastActiveDate`,`learningGoal`,`isActive`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final User entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getUsername() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getUsername());
        }
        if (entity.getEmail() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getEmail());
        }
        if (entity.getFullName() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getFullName());
        }
        if (entity.getProfileImageUrl() == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, entity.getProfileImageUrl());
        }
        if (entity.getPreferredLanguage() == null) {
          statement.bindNull(6);
        } else {
          statement.bindText(6, entity.getPreferredLanguage());
        }
        statement.bindLong(7, entity.getTotalPoints());
        statement.bindLong(8, entity.getCurrentStreak());
        statement.bindLong(9, entity.getMaxStreak());
        final Long _tmp = Converters.dateToTimestamp(entity.getDateJoined());
        if (_tmp == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, _tmp);
        }
        final Long _tmp_1 = Converters.dateToTimestamp(entity.getLastActiveDate());
        if (_tmp_1 == null) {
          statement.bindNull(11);
        } else {
          statement.bindLong(11, _tmp_1);
        }
        if (entity.getLearningGoal() == null) {
          statement.bindNull(12);
        } else {
          statement.bindText(12, entity.getLearningGoal());
        }
        final int _tmp_2 = entity.isActive() ? 1 : 0;
        statement.bindLong(13, _tmp_2);
      }
    };
    this.__deleteAdapterOfUser = new EntityDeleteOrUpdateAdapter<User>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `users` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final User entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfUser = new EntityDeleteOrUpdateAdapter<User>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `users` SET `id` = ?,`username` = ?,`email` = ?,`fullName` = ?,`profileImageUrl` = ?,`preferredLanguage` = ?,`totalPoints` = ?,`currentStreak` = ?,`maxStreak` = ?,`dateJoined` = ?,`lastActiveDate` = ?,`learningGoal` = ?,`isActive` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final User entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getUsername() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getUsername());
        }
        if (entity.getEmail() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getEmail());
        }
        if (entity.getFullName() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getFullName());
        }
        if (entity.getProfileImageUrl() == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, entity.getProfileImageUrl());
        }
        if (entity.getPreferredLanguage() == null) {
          statement.bindNull(6);
        } else {
          statement.bindText(6, entity.getPreferredLanguage());
        }
        statement.bindLong(7, entity.getTotalPoints());
        statement.bindLong(8, entity.getCurrentStreak());
        statement.bindLong(9, entity.getMaxStreak());
        final Long _tmp = Converters.dateToTimestamp(entity.getDateJoined());
        if (_tmp == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, _tmp);
        }
        final Long _tmp_1 = Converters.dateToTimestamp(entity.getLastActiveDate());
        if (_tmp_1 == null) {
          statement.bindNull(11);
        } else {
          statement.bindLong(11, _tmp_1);
        }
        if (entity.getLearningGoal() == null) {
          statement.bindNull(12);
        } else {
          statement.bindText(12, entity.getLearningGoal());
        }
        final int _tmp_2 = entity.isActive() ? 1 : 0;
        statement.bindLong(13, _tmp_2);
        statement.bindLong(14, entity.getId());
      }
    };
  }

  @Override
  public void insert(final User user) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __insertAdapterOfUser.insert(_connection, user);
      return null;
    });
  }

  @Override
  public void insertAll(final List<User> users) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __insertAdapterOfUser.insert(_connection, users);
      return null;
    });
  }

  @Override
  public void delete(final User user) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __deleteAdapterOfUser.handle(_connection, user);
      return null;
    });
  }

  @Override
  public void update(final User user) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __updateAdapterOfUser.handle(_connection, user);
      return null;
    });
  }

  @Override
  public LiveData<List<User>> getAllUsers() {
    final String _sql = "SELECT * FROM users ORDER BY username";
    return __db.getInvalidationTracker().createLiveData(new String[] {"users"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfUsername = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "username");
        final int _columnIndexOfEmail = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "email");
        final int _columnIndexOfFullName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "fullName");
        final int _columnIndexOfProfileImageUrl = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "profileImageUrl");
        final int _columnIndexOfPreferredLanguage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "preferredLanguage");
        final int _columnIndexOfTotalPoints = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalPoints");
        final int _columnIndexOfCurrentStreak = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "currentStreak");
        final int _columnIndexOfMaxStreak = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "maxStreak");
        final int _columnIndexOfDateJoined = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "dateJoined");
        final int _columnIndexOfLastActiveDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastActiveDate");
        final int _columnIndexOfLearningGoal = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "learningGoal");
        final int _columnIndexOfIsActive = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isActive");
        final List<User> _result = new ArrayList<User>();
        while (_stmt.step()) {
          final User _item;
          _item = new User();
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final String _tmpUsername;
          if (_stmt.isNull(_columnIndexOfUsername)) {
            _tmpUsername = null;
          } else {
            _tmpUsername = _stmt.getText(_columnIndexOfUsername);
          }
          _item.setUsername(_tmpUsername);
          final String _tmpEmail;
          if (_stmt.isNull(_columnIndexOfEmail)) {
            _tmpEmail = null;
          } else {
            _tmpEmail = _stmt.getText(_columnIndexOfEmail);
          }
          _item.setEmail(_tmpEmail);
          final String _tmpFullName;
          if (_stmt.isNull(_columnIndexOfFullName)) {
            _tmpFullName = null;
          } else {
            _tmpFullName = _stmt.getText(_columnIndexOfFullName);
          }
          _item.setFullName(_tmpFullName);
          final String _tmpProfileImageUrl;
          if (_stmt.isNull(_columnIndexOfProfileImageUrl)) {
            _tmpProfileImageUrl = null;
          } else {
            _tmpProfileImageUrl = _stmt.getText(_columnIndexOfProfileImageUrl);
          }
          _item.setProfileImageUrl(_tmpProfileImageUrl);
          final String _tmpPreferredLanguage;
          if (_stmt.isNull(_columnIndexOfPreferredLanguage)) {
            _tmpPreferredLanguage = null;
          } else {
            _tmpPreferredLanguage = _stmt.getText(_columnIndexOfPreferredLanguage);
          }
          _item.setPreferredLanguage(_tmpPreferredLanguage);
          final int _tmpTotalPoints;
          _tmpTotalPoints = (int) (_stmt.getLong(_columnIndexOfTotalPoints));
          _item.setTotalPoints(_tmpTotalPoints);
          final int _tmpCurrentStreak;
          _tmpCurrentStreak = (int) (_stmt.getLong(_columnIndexOfCurrentStreak));
          _item.setCurrentStreak(_tmpCurrentStreak);
          final int _tmpMaxStreak;
          _tmpMaxStreak = (int) (_stmt.getLong(_columnIndexOfMaxStreak));
          _item.setMaxStreak(_tmpMaxStreak);
          final Date _tmpDateJoined;
          final Long _tmp;
          if (_stmt.isNull(_columnIndexOfDateJoined)) {
            _tmp = null;
          } else {
            _tmp = _stmt.getLong(_columnIndexOfDateJoined);
          }
          _tmpDateJoined = Converters.fromTimestamp(_tmp);
          _item.setDateJoined(_tmpDateJoined);
          final Date _tmpLastActiveDate;
          final Long _tmp_1;
          if (_stmt.isNull(_columnIndexOfLastActiveDate)) {
            _tmp_1 = null;
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfLastActiveDate);
          }
          _tmpLastActiveDate = Converters.fromTimestamp(_tmp_1);
          _item.setLastActiveDate(_tmpLastActiveDate);
          final String _tmpLearningGoal;
          if (_stmt.isNull(_columnIndexOfLearningGoal)) {
            _tmpLearningGoal = null;
          } else {
            _tmpLearningGoal = _stmt.getText(_columnIndexOfLearningGoal);
          }
          _item.setLearningGoal(_tmpLearningGoal);
          final boolean _tmpIsActive;
          final int _tmp_2;
          _tmp_2 = (int) (_stmt.getLong(_columnIndexOfIsActive));
          _tmpIsActive = _tmp_2 != 0;
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
  public LiveData<User> getUserById(final int id) {
    final String _sql = "SELECT * FROM users WHERE id = ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"users"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfUsername = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "username");
        final int _columnIndexOfEmail = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "email");
        final int _columnIndexOfFullName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "fullName");
        final int _columnIndexOfProfileImageUrl = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "profileImageUrl");
        final int _columnIndexOfPreferredLanguage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "preferredLanguage");
        final int _columnIndexOfTotalPoints = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalPoints");
        final int _columnIndexOfCurrentStreak = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "currentStreak");
        final int _columnIndexOfMaxStreak = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "maxStreak");
        final int _columnIndexOfDateJoined = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "dateJoined");
        final int _columnIndexOfLastActiveDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastActiveDate");
        final int _columnIndexOfLearningGoal = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "learningGoal");
        final int _columnIndexOfIsActive = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isActive");
        final User _result;
        if (_stmt.step()) {
          _result = new User();
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _result.setId(_tmpId);
          final String _tmpUsername;
          if (_stmt.isNull(_columnIndexOfUsername)) {
            _tmpUsername = null;
          } else {
            _tmpUsername = _stmt.getText(_columnIndexOfUsername);
          }
          _result.setUsername(_tmpUsername);
          final String _tmpEmail;
          if (_stmt.isNull(_columnIndexOfEmail)) {
            _tmpEmail = null;
          } else {
            _tmpEmail = _stmt.getText(_columnIndexOfEmail);
          }
          _result.setEmail(_tmpEmail);
          final String _tmpFullName;
          if (_stmt.isNull(_columnIndexOfFullName)) {
            _tmpFullName = null;
          } else {
            _tmpFullName = _stmt.getText(_columnIndexOfFullName);
          }
          _result.setFullName(_tmpFullName);
          final String _tmpProfileImageUrl;
          if (_stmt.isNull(_columnIndexOfProfileImageUrl)) {
            _tmpProfileImageUrl = null;
          } else {
            _tmpProfileImageUrl = _stmt.getText(_columnIndexOfProfileImageUrl);
          }
          _result.setProfileImageUrl(_tmpProfileImageUrl);
          final String _tmpPreferredLanguage;
          if (_stmt.isNull(_columnIndexOfPreferredLanguage)) {
            _tmpPreferredLanguage = null;
          } else {
            _tmpPreferredLanguage = _stmt.getText(_columnIndexOfPreferredLanguage);
          }
          _result.setPreferredLanguage(_tmpPreferredLanguage);
          final int _tmpTotalPoints;
          _tmpTotalPoints = (int) (_stmt.getLong(_columnIndexOfTotalPoints));
          _result.setTotalPoints(_tmpTotalPoints);
          final int _tmpCurrentStreak;
          _tmpCurrentStreak = (int) (_stmt.getLong(_columnIndexOfCurrentStreak));
          _result.setCurrentStreak(_tmpCurrentStreak);
          final int _tmpMaxStreak;
          _tmpMaxStreak = (int) (_stmt.getLong(_columnIndexOfMaxStreak));
          _result.setMaxStreak(_tmpMaxStreak);
          final Date _tmpDateJoined;
          final Long _tmp;
          if (_stmt.isNull(_columnIndexOfDateJoined)) {
            _tmp = null;
          } else {
            _tmp = _stmt.getLong(_columnIndexOfDateJoined);
          }
          _tmpDateJoined = Converters.fromTimestamp(_tmp);
          _result.setDateJoined(_tmpDateJoined);
          final Date _tmpLastActiveDate;
          final Long _tmp_1;
          if (_stmt.isNull(_columnIndexOfLastActiveDate)) {
            _tmp_1 = null;
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfLastActiveDate);
          }
          _tmpLastActiveDate = Converters.fromTimestamp(_tmp_1);
          _result.setLastActiveDate(_tmpLastActiveDate);
          final String _tmpLearningGoal;
          if (_stmt.isNull(_columnIndexOfLearningGoal)) {
            _tmpLearningGoal = null;
          } else {
            _tmpLearningGoal = _stmt.getText(_columnIndexOfLearningGoal);
          }
          _result.setLearningGoal(_tmpLearningGoal);
          final boolean _tmpIsActive;
          final int _tmp_2;
          _tmp_2 = (int) (_stmt.getLong(_columnIndexOfIsActive));
          _tmpIsActive = _tmp_2 != 0;
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
  public User getUserByIdSync(final int id) {
    final String _sql = "SELECT * FROM users WHERE id = ?";
    return DBUtil.performBlocking(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfUsername = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "username");
        final int _columnIndexOfEmail = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "email");
        final int _columnIndexOfFullName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "fullName");
        final int _columnIndexOfProfileImageUrl = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "profileImageUrl");
        final int _columnIndexOfPreferredLanguage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "preferredLanguage");
        final int _columnIndexOfTotalPoints = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalPoints");
        final int _columnIndexOfCurrentStreak = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "currentStreak");
        final int _columnIndexOfMaxStreak = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "maxStreak");
        final int _columnIndexOfDateJoined = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "dateJoined");
        final int _columnIndexOfLastActiveDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastActiveDate");
        final int _columnIndexOfLearningGoal = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "learningGoal");
        final int _columnIndexOfIsActive = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isActive");
        final User _result;
        if (_stmt.step()) {
          _result = new User();
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _result.setId(_tmpId);
          final String _tmpUsername;
          if (_stmt.isNull(_columnIndexOfUsername)) {
            _tmpUsername = null;
          } else {
            _tmpUsername = _stmt.getText(_columnIndexOfUsername);
          }
          _result.setUsername(_tmpUsername);
          final String _tmpEmail;
          if (_stmt.isNull(_columnIndexOfEmail)) {
            _tmpEmail = null;
          } else {
            _tmpEmail = _stmt.getText(_columnIndexOfEmail);
          }
          _result.setEmail(_tmpEmail);
          final String _tmpFullName;
          if (_stmt.isNull(_columnIndexOfFullName)) {
            _tmpFullName = null;
          } else {
            _tmpFullName = _stmt.getText(_columnIndexOfFullName);
          }
          _result.setFullName(_tmpFullName);
          final String _tmpProfileImageUrl;
          if (_stmt.isNull(_columnIndexOfProfileImageUrl)) {
            _tmpProfileImageUrl = null;
          } else {
            _tmpProfileImageUrl = _stmt.getText(_columnIndexOfProfileImageUrl);
          }
          _result.setProfileImageUrl(_tmpProfileImageUrl);
          final String _tmpPreferredLanguage;
          if (_stmt.isNull(_columnIndexOfPreferredLanguage)) {
            _tmpPreferredLanguage = null;
          } else {
            _tmpPreferredLanguage = _stmt.getText(_columnIndexOfPreferredLanguage);
          }
          _result.setPreferredLanguage(_tmpPreferredLanguage);
          final int _tmpTotalPoints;
          _tmpTotalPoints = (int) (_stmt.getLong(_columnIndexOfTotalPoints));
          _result.setTotalPoints(_tmpTotalPoints);
          final int _tmpCurrentStreak;
          _tmpCurrentStreak = (int) (_stmt.getLong(_columnIndexOfCurrentStreak));
          _result.setCurrentStreak(_tmpCurrentStreak);
          final int _tmpMaxStreak;
          _tmpMaxStreak = (int) (_stmt.getLong(_columnIndexOfMaxStreak));
          _result.setMaxStreak(_tmpMaxStreak);
          final Date _tmpDateJoined;
          final Long _tmp;
          if (_stmt.isNull(_columnIndexOfDateJoined)) {
            _tmp = null;
          } else {
            _tmp = _stmt.getLong(_columnIndexOfDateJoined);
          }
          _tmpDateJoined = Converters.fromTimestamp(_tmp);
          _result.setDateJoined(_tmpDateJoined);
          final Date _tmpLastActiveDate;
          final Long _tmp_1;
          if (_stmt.isNull(_columnIndexOfLastActiveDate)) {
            _tmp_1 = null;
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfLastActiveDate);
          }
          _tmpLastActiveDate = Converters.fromTimestamp(_tmp_1);
          _result.setLastActiveDate(_tmpLastActiveDate);
          final String _tmpLearningGoal;
          if (_stmt.isNull(_columnIndexOfLearningGoal)) {
            _tmpLearningGoal = null;
          } else {
            _tmpLearningGoal = _stmt.getText(_columnIndexOfLearningGoal);
          }
          _result.setLearningGoal(_tmpLearningGoal);
          final boolean _tmpIsActive;
          final int _tmp_2;
          _tmp_2 = (int) (_stmt.getLong(_columnIndexOfIsActive));
          _tmpIsActive = _tmp_2 != 0;
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
  public LiveData<User> getUserByUsername(final String username) {
    final String _sql = "SELECT * FROM users WHERE username = ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"users"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (username == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, username);
        }
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfUsername = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "username");
        final int _columnIndexOfEmail = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "email");
        final int _columnIndexOfFullName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "fullName");
        final int _columnIndexOfProfileImageUrl = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "profileImageUrl");
        final int _columnIndexOfPreferredLanguage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "preferredLanguage");
        final int _columnIndexOfTotalPoints = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalPoints");
        final int _columnIndexOfCurrentStreak = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "currentStreak");
        final int _columnIndexOfMaxStreak = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "maxStreak");
        final int _columnIndexOfDateJoined = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "dateJoined");
        final int _columnIndexOfLastActiveDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastActiveDate");
        final int _columnIndexOfLearningGoal = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "learningGoal");
        final int _columnIndexOfIsActive = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isActive");
        final User _result;
        if (_stmt.step()) {
          _result = new User();
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _result.setId(_tmpId);
          final String _tmpUsername;
          if (_stmt.isNull(_columnIndexOfUsername)) {
            _tmpUsername = null;
          } else {
            _tmpUsername = _stmt.getText(_columnIndexOfUsername);
          }
          _result.setUsername(_tmpUsername);
          final String _tmpEmail;
          if (_stmt.isNull(_columnIndexOfEmail)) {
            _tmpEmail = null;
          } else {
            _tmpEmail = _stmt.getText(_columnIndexOfEmail);
          }
          _result.setEmail(_tmpEmail);
          final String _tmpFullName;
          if (_stmt.isNull(_columnIndexOfFullName)) {
            _tmpFullName = null;
          } else {
            _tmpFullName = _stmt.getText(_columnIndexOfFullName);
          }
          _result.setFullName(_tmpFullName);
          final String _tmpProfileImageUrl;
          if (_stmt.isNull(_columnIndexOfProfileImageUrl)) {
            _tmpProfileImageUrl = null;
          } else {
            _tmpProfileImageUrl = _stmt.getText(_columnIndexOfProfileImageUrl);
          }
          _result.setProfileImageUrl(_tmpProfileImageUrl);
          final String _tmpPreferredLanguage;
          if (_stmt.isNull(_columnIndexOfPreferredLanguage)) {
            _tmpPreferredLanguage = null;
          } else {
            _tmpPreferredLanguage = _stmt.getText(_columnIndexOfPreferredLanguage);
          }
          _result.setPreferredLanguage(_tmpPreferredLanguage);
          final int _tmpTotalPoints;
          _tmpTotalPoints = (int) (_stmt.getLong(_columnIndexOfTotalPoints));
          _result.setTotalPoints(_tmpTotalPoints);
          final int _tmpCurrentStreak;
          _tmpCurrentStreak = (int) (_stmt.getLong(_columnIndexOfCurrentStreak));
          _result.setCurrentStreak(_tmpCurrentStreak);
          final int _tmpMaxStreak;
          _tmpMaxStreak = (int) (_stmt.getLong(_columnIndexOfMaxStreak));
          _result.setMaxStreak(_tmpMaxStreak);
          final Date _tmpDateJoined;
          final Long _tmp;
          if (_stmt.isNull(_columnIndexOfDateJoined)) {
            _tmp = null;
          } else {
            _tmp = _stmt.getLong(_columnIndexOfDateJoined);
          }
          _tmpDateJoined = Converters.fromTimestamp(_tmp);
          _result.setDateJoined(_tmpDateJoined);
          final Date _tmpLastActiveDate;
          final Long _tmp_1;
          if (_stmt.isNull(_columnIndexOfLastActiveDate)) {
            _tmp_1 = null;
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfLastActiveDate);
          }
          _tmpLastActiveDate = Converters.fromTimestamp(_tmp_1);
          _result.setLastActiveDate(_tmpLastActiveDate);
          final String _tmpLearningGoal;
          if (_stmt.isNull(_columnIndexOfLearningGoal)) {
            _tmpLearningGoal = null;
          } else {
            _tmpLearningGoal = _stmt.getText(_columnIndexOfLearningGoal);
          }
          _result.setLearningGoal(_tmpLearningGoal);
          final boolean _tmpIsActive;
          final int _tmp_2;
          _tmp_2 = (int) (_stmt.getLong(_columnIndexOfIsActive));
          _tmpIsActive = _tmp_2 != 0;
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
  public LiveData<User> getUserByEmail(final String email) {
    final String _sql = "SELECT * FROM users WHERE email = ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"users"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (email == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, email);
        }
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfUsername = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "username");
        final int _columnIndexOfEmail = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "email");
        final int _columnIndexOfFullName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "fullName");
        final int _columnIndexOfProfileImageUrl = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "profileImageUrl");
        final int _columnIndexOfPreferredLanguage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "preferredLanguage");
        final int _columnIndexOfTotalPoints = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalPoints");
        final int _columnIndexOfCurrentStreak = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "currentStreak");
        final int _columnIndexOfMaxStreak = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "maxStreak");
        final int _columnIndexOfDateJoined = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "dateJoined");
        final int _columnIndexOfLastActiveDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastActiveDate");
        final int _columnIndexOfLearningGoal = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "learningGoal");
        final int _columnIndexOfIsActive = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isActive");
        final User _result;
        if (_stmt.step()) {
          _result = new User();
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _result.setId(_tmpId);
          final String _tmpUsername;
          if (_stmt.isNull(_columnIndexOfUsername)) {
            _tmpUsername = null;
          } else {
            _tmpUsername = _stmt.getText(_columnIndexOfUsername);
          }
          _result.setUsername(_tmpUsername);
          final String _tmpEmail;
          if (_stmt.isNull(_columnIndexOfEmail)) {
            _tmpEmail = null;
          } else {
            _tmpEmail = _stmt.getText(_columnIndexOfEmail);
          }
          _result.setEmail(_tmpEmail);
          final String _tmpFullName;
          if (_stmt.isNull(_columnIndexOfFullName)) {
            _tmpFullName = null;
          } else {
            _tmpFullName = _stmt.getText(_columnIndexOfFullName);
          }
          _result.setFullName(_tmpFullName);
          final String _tmpProfileImageUrl;
          if (_stmt.isNull(_columnIndexOfProfileImageUrl)) {
            _tmpProfileImageUrl = null;
          } else {
            _tmpProfileImageUrl = _stmt.getText(_columnIndexOfProfileImageUrl);
          }
          _result.setProfileImageUrl(_tmpProfileImageUrl);
          final String _tmpPreferredLanguage;
          if (_stmt.isNull(_columnIndexOfPreferredLanguage)) {
            _tmpPreferredLanguage = null;
          } else {
            _tmpPreferredLanguage = _stmt.getText(_columnIndexOfPreferredLanguage);
          }
          _result.setPreferredLanguage(_tmpPreferredLanguage);
          final int _tmpTotalPoints;
          _tmpTotalPoints = (int) (_stmt.getLong(_columnIndexOfTotalPoints));
          _result.setTotalPoints(_tmpTotalPoints);
          final int _tmpCurrentStreak;
          _tmpCurrentStreak = (int) (_stmt.getLong(_columnIndexOfCurrentStreak));
          _result.setCurrentStreak(_tmpCurrentStreak);
          final int _tmpMaxStreak;
          _tmpMaxStreak = (int) (_stmt.getLong(_columnIndexOfMaxStreak));
          _result.setMaxStreak(_tmpMaxStreak);
          final Date _tmpDateJoined;
          final Long _tmp;
          if (_stmt.isNull(_columnIndexOfDateJoined)) {
            _tmp = null;
          } else {
            _tmp = _stmt.getLong(_columnIndexOfDateJoined);
          }
          _tmpDateJoined = Converters.fromTimestamp(_tmp);
          _result.setDateJoined(_tmpDateJoined);
          final Date _tmpLastActiveDate;
          final Long _tmp_1;
          if (_stmt.isNull(_columnIndexOfLastActiveDate)) {
            _tmp_1 = null;
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfLastActiveDate);
          }
          _tmpLastActiveDate = Converters.fromTimestamp(_tmp_1);
          _result.setLastActiveDate(_tmpLastActiveDate);
          final String _tmpLearningGoal;
          if (_stmt.isNull(_columnIndexOfLearningGoal)) {
            _tmpLearningGoal = null;
          } else {
            _tmpLearningGoal = _stmt.getText(_columnIndexOfLearningGoal);
          }
          _result.setLearningGoal(_tmpLearningGoal);
          final boolean _tmpIsActive;
          final int _tmp_2;
          _tmp_2 = (int) (_stmt.getLong(_columnIndexOfIsActive));
          _tmpIsActive = _tmp_2 != 0;
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
  public boolean usernameExists(final String username) {
    final String _sql = "SELECT COUNT(*) > 0 FROM users WHERE username = ?";
    return DBUtil.performBlocking(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (username == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, username);
        }
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
  public boolean emailExists(final String email) {
    final String _sql = "SELECT COUNT(*) > 0 FROM users WHERE email = ?";
    return DBUtil.performBlocking(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (email == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, email);
        }
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
  public LiveData<List<User>> getUsersByLanguage(final String language) {
    final String _sql = "SELECT * FROM users WHERE preferredLanguage = ? ORDER BY username";
    return __db.getInvalidationTracker().createLiveData(new String[] {"users"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (language == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, language);
        }
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfUsername = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "username");
        final int _columnIndexOfEmail = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "email");
        final int _columnIndexOfFullName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "fullName");
        final int _columnIndexOfProfileImageUrl = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "profileImageUrl");
        final int _columnIndexOfPreferredLanguage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "preferredLanguage");
        final int _columnIndexOfTotalPoints = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalPoints");
        final int _columnIndexOfCurrentStreak = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "currentStreak");
        final int _columnIndexOfMaxStreak = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "maxStreak");
        final int _columnIndexOfDateJoined = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "dateJoined");
        final int _columnIndexOfLastActiveDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastActiveDate");
        final int _columnIndexOfLearningGoal = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "learningGoal");
        final int _columnIndexOfIsActive = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isActive");
        final List<User> _result = new ArrayList<User>();
        while (_stmt.step()) {
          final User _item;
          _item = new User();
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final String _tmpUsername;
          if (_stmt.isNull(_columnIndexOfUsername)) {
            _tmpUsername = null;
          } else {
            _tmpUsername = _stmt.getText(_columnIndexOfUsername);
          }
          _item.setUsername(_tmpUsername);
          final String _tmpEmail;
          if (_stmt.isNull(_columnIndexOfEmail)) {
            _tmpEmail = null;
          } else {
            _tmpEmail = _stmt.getText(_columnIndexOfEmail);
          }
          _item.setEmail(_tmpEmail);
          final String _tmpFullName;
          if (_stmt.isNull(_columnIndexOfFullName)) {
            _tmpFullName = null;
          } else {
            _tmpFullName = _stmt.getText(_columnIndexOfFullName);
          }
          _item.setFullName(_tmpFullName);
          final String _tmpProfileImageUrl;
          if (_stmt.isNull(_columnIndexOfProfileImageUrl)) {
            _tmpProfileImageUrl = null;
          } else {
            _tmpProfileImageUrl = _stmt.getText(_columnIndexOfProfileImageUrl);
          }
          _item.setProfileImageUrl(_tmpProfileImageUrl);
          final String _tmpPreferredLanguage;
          if (_stmt.isNull(_columnIndexOfPreferredLanguage)) {
            _tmpPreferredLanguage = null;
          } else {
            _tmpPreferredLanguage = _stmt.getText(_columnIndexOfPreferredLanguage);
          }
          _item.setPreferredLanguage(_tmpPreferredLanguage);
          final int _tmpTotalPoints;
          _tmpTotalPoints = (int) (_stmt.getLong(_columnIndexOfTotalPoints));
          _item.setTotalPoints(_tmpTotalPoints);
          final int _tmpCurrentStreak;
          _tmpCurrentStreak = (int) (_stmt.getLong(_columnIndexOfCurrentStreak));
          _item.setCurrentStreak(_tmpCurrentStreak);
          final int _tmpMaxStreak;
          _tmpMaxStreak = (int) (_stmt.getLong(_columnIndexOfMaxStreak));
          _item.setMaxStreak(_tmpMaxStreak);
          final Date _tmpDateJoined;
          final Long _tmp;
          if (_stmt.isNull(_columnIndexOfDateJoined)) {
            _tmp = null;
          } else {
            _tmp = _stmt.getLong(_columnIndexOfDateJoined);
          }
          _tmpDateJoined = Converters.fromTimestamp(_tmp);
          _item.setDateJoined(_tmpDateJoined);
          final Date _tmpLastActiveDate;
          final Long _tmp_1;
          if (_stmt.isNull(_columnIndexOfLastActiveDate)) {
            _tmp_1 = null;
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfLastActiveDate);
          }
          _tmpLastActiveDate = Converters.fromTimestamp(_tmp_1);
          _item.setLastActiveDate(_tmpLastActiveDate);
          final String _tmpLearningGoal;
          if (_stmt.isNull(_columnIndexOfLearningGoal)) {
            _tmpLearningGoal = null;
          } else {
            _tmpLearningGoal = _stmt.getText(_columnIndexOfLearningGoal);
          }
          _item.setLearningGoal(_tmpLearningGoal);
          final boolean _tmpIsActive;
          final int _tmp_2;
          _tmp_2 = (int) (_stmt.getLong(_columnIndexOfIsActive));
          _tmpIsActive = _tmp_2 != 0;
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
  public LiveData<List<User>> getTopUsersByPoints(final int limit) {
    final String _sql = "SELECT * FROM users ORDER BY totalPoints DESC LIMIT ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"users"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, limit);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfUsername = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "username");
        final int _columnIndexOfEmail = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "email");
        final int _columnIndexOfFullName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "fullName");
        final int _columnIndexOfProfileImageUrl = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "profileImageUrl");
        final int _columnIndexOfPreferredLanguage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "preferredLanguage");
        final int _columnIndexOfTotalPoints = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "totalPoints");
        final int _columnIndexOfCurrentStreak = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "currentStreak");
        final int _columnIndexOfMaxStreak = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "maxStreak");
        final int _columnIndexOfDateJoined = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "dateJoined");
        final int _columnIndexOfLastActiveDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastActiveDate");
        final int _columnIndexOfLearningGoal = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "learningGoal");
        final int _columnIndexOfIsActive = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isActive");
        final List<User> _result = new ArrayList<User>();
        while (_stmt.step()) {
          final User _item;
          _item = new User();
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final String _tmpUsername;
          if (_stmt.isNull(_columnIndexOfUsername)) {
            _tmpUsername = null;
          } else {
            _tmpUsername = _stmt.getText(_columnIndexOfUsername);
          }
          _item.setUsername(_tmpUsername);
          final String _tmpEmail;
          if (_stmt.isNull(_columnIndexOfEmail)) {
            _tmpEmail = null;
          } else {
            _tmpEmail = _stmt.getText(_columnIndexOfEmail);
          }
          _item.setEmail(_tmpEmail);
          final String _tmpFullName;
          if (_stmt.isNull(_columnIndexOfFullName)) {
            _tmpFullName = null;
          } else {
            _tmpFullName = _stmt.getText(_columnIndexOfFullName);
          }
          _item.setFullName(_tmpFullName);
          final String _tmpProfileImageUrl;
          if (_stmt.isNull(_columnIndexOfProfileImageUrl)) {
            _tmpProfileImageUrl = null;
          } else {
            _tmpProfileImageUrl = _stmt.getText(_columnIndexOfProfileImageUrl);
          }
          _item.setProfileImageUrl(_tmpProfileImageUrl);
          final String _tmpPreferredLanguage;
          if (_stmt.isNull(_columnIndexOfPreferredLanguage)) {
            _tmpPreferredLanguage = null;
          } else {
            _tmpPreferredLanguage = _stmt.getText(_columnIndexOfPreferredLanguage);
          }
          _item.setPreferredLanguage(_tmpPreferredLanguage);
          final int _tmpTotalPoints;
          _tmpTotalPoints = (int) (_stmt.getLong(_columnIndexOfTotalPoints));
          _item.setTotalPoints(_tmpTotalPoints);
          final int _tmpCurrentStreak;
          _tmpCurrentStreak = (int) (_stmt.getLong(_columnIndexOfCurrentStreak));
          _item.setCurrentStreak(_tmpCurrentStreak);
          final int _tmpMaxStreak;
          _tmpMaxStreak = (int) (_stmt.getLong(_columnIndexOfMaxStreak));
          _item.setMaxStreak(_tmpMaxStreak);
          final Date _tmpDateJoined;
          final Long _tmp;
          if (_stmt.isNull(_columnIndexOfDateJoined)) {
            _tmp = null;
          } else {
            _tmp = _stmt.getLong(_columnIndexOfDateJoined);
          }
          _tmpDateJoined = Converters.fromTimestamp(_tmp);
          _item.setDateJoined(_tmpDateJoined);
          final Date _tmpLastActiveDate;
          final Long _tmp_1;
          if (_stmt.isNull(_columnIndexOfLastActiveDate)) {
            _tmp_1 = null;
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfLastActiveDate);
          }
          _tmpLastActiveDate = Converters.fromTimestamp(_tmp_1);
          _item.setLastActiveDate(_tmpLastActiveDate);
          final String _tmpLearningGoal;
          if (_stmt.isNull(_columnIndexOfLearningGoal)) {
            _tmpLearningGoal = null;
          } else {
            _tmpLearningGoal = _stmt.getText(_columnIndexOfLearningGoal);
          }
          _item.setLearningGoal(_tmpLearningGoal);
          final boolean _tmpIsActive;
          final int _tmp_2;
          _tmp_2 = (int) (_stmt.getLong(_columnIndexOfIsActive));
          _tmpIsActive = _tmp_2 != 0;
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
  public LiveData<Integer> getTotalCount() {
    final String _sql = "SELECT COUNT(*) FROM users";
    return __db.getInvalidationTracker().createLiveData(new String[] {"users"}, false, (_connection) -> {
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
    final String _sql = "DELETE FROM users";
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
