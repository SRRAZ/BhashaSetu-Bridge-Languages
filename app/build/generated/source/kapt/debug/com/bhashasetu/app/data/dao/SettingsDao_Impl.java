package com.bhashasetu.app.data.dao;

import androidx.annotation.NonNull;
import androidx.room.EntityDeleteOrUpdateAdapter;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.coroutines.FlowUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteStatement;
import com.bhashasetu.app.data.model.AppSettings;
import java.lang.Class;
import java.lang.Integer;
import java.lang.Long;
import java.lang.NullPointerException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation", "removal"})
public final class SettingsDao_Impl implements SettingsDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<AppSettings> __insertAdapterOfAppSettings;

  private final EntityDeleteOrUpdateAdapter<AppSettings> __updateAdapterOfAppSettings;

  public SettingsDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfAppSettings = new EntityInsertAdapter<AppSettings>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `app_settings` (`id`,`interfaceLanguage`,`primaryLanguage`,`secondaryLanguage`,`useDevanagariDigits`,`theme`,`fontSizeMultiplier`,`highContrastMode`,`dailyWordGoal`,`dailyTimeGoalMinutes`,`reminderTime`,`reminderDays`,`autoPlayPronunciation`,`pronunciationSpeed`,`autoRecordEnabled`,`soundEnabled`,`vibrationEnabled`,`flashcardSessionSize`,`quizTimeLimit`,`showTranslationHints`,`dailyReminderEnabled`,`streakReminderEnabled`,`achievementNotificationsEnabled`,`quizResultsNotificationsEnabled`,`lastSelectedCategoryId`,`lastSelectedLessonId`,`installDate`,`lastUpdatedAt`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final AppSettings entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getInterfaceLanguage() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getInterfaceLanguage());
        }
        if (entity.getPrimaryLanguage() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getPrimaryLanguage());
        }
        if (entity.getSecondaryLanguage() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getSecondaryLanguage());
        }
        final int _tmp = entity.getUseDevanagariDigits() ? 1 : 0;
        statement.bindLong(5, _tmp);
        if (entity.getTheme() == null) {
          statement.bindNull(6);
        } else {
          statement.bindText(6, entity.getTheme());
        }
        statement.bindDouble(7, entity.getFontSizeMultiplier());
        final int _tmp_1 = entity.getHighContrastMode() ? 1 : 0;
        statement.bindLong(8, _tmp_1);
        statement.bindLong(9, entity.getDailyWordGoal());
        statement.bindLong(10, entity.getDailyTimeGoalMinutes());
        if (entity.getReminderTime() == null) {
          statement.bindNull(11);
        } else {
          statement.bindText(11, entity.getReminderTime());
        }
        if (entity.getReminderDays() == null) {
          statement.bindNull(12);
        } else {
          statement.bindText(12, entity.getReminderDays());
        }
        final int _tmp_2 = entity.getAutoPlayPronunciation() ? 1 : 0;
        statement.bindLong(13, _tmp_2);
        statement.bindDouble(14, entity.getPronunciationSpeed());
        final int _tmp_3 = entity.getAutoRecordEnabled() ? 1 : 0;
        statement.bindLong(15, _tmp_3);
        final int _tmp_4 = entity.getSoundEnabled() ? 1 : 0;
        statement.bindLong(16, _tmp_4);
        final int _tmp_5 = entity.getVibrationEnabled() ? 1 : 0;
        statement.bindLong(17, _tmp_5);
        statement.bindLong(18, entity.getFlashcardSessionSize());
        if (entity.getQuizTimeLimit() == null) {
          statement.bindNull(19);
        } else {
          statement.bindLong(19, entity.getQuizTimeLimit());
        }
        final int _tmp_6 = entity.getShowTranslationHints() ? 1 : 0;
        statement.bindLong(20, _tmp_6);
        final int _tmp_7 = entity.getDailyReminderEnabled() ? 1 : 0;
        statement.bindLong(21, _tmp_7);
        final int _tmp_8 = entity.getStreakReminderEnabled() ? 1 : 0;
        statement.bindLong(22, _tmp_8);
        final int _tmp_9 = entity.getAchievementNotificationsEnabled() ? 1 : 0;
        statement.bindLong(23, _tmp_9);
        final int _tmp_10 = entity.getQuizResultsNotificationsEnabled() ? 1 : 0;
        statement.bindLong(24, _tmp_10);
        if (entity.getLastSelectedCategoryId() == null) {
          statement.bindNull(25);
        } else {
          statement.bindLong(25, entity.getLastSelectedCategoryId());
        }
        if (entity.getLastSelectedLessonId() == null) {
          statement.bindNull(26);
        } else {
          statement.bindLong(26, entity.getLastSelectedLessonId());
        }
        statement.bindLong(27, entity.getInstallDate());
        statement.bindLong(28, entity.getLastUpdatedAt());
      }
    };
    this.__updateAdapterOfAppSettings = new EntityDeleteOrUpdateAdapter<AppSettings>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `app_settings` SET `id` = ?,`interfaceLanguage` = ?,`primaryLanguage` = ?,`secondaryLanguage` = ?,`useDevanagariDigits` = ?,`theme` = ?,`fontSizeMultiplier` = ?,`highContrastMode` = ?,`dailyWordGoal` = ?,`dailyTimeGoalMinutes` = ?,`reminderTime` = ?,`reminderDays` = ?,`autoPlayPronunciation` = ?,`pronunciationSpeed` = ?,`autoRecordEnabled` = ?,`soundEnabled` = ?,`vibrationEnabled` = ?,`flashcardSessionSize` = ?,`quizTimeLimit` = ?,`showTranslationHints` = ?,`dailyReminderEnabled` = ?,`streakReminderEnabled` = ?,`achievementNotificationsEnabled` = ?,`quizResultsNotificationsEnabled` = ?,`lastSelectedCategoryId` = ?,`lastSelectedLessonId` = ?,`installDate` = ?,`lastUpdatedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final AppSettings entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getInterfaceLanguage() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getInterfaceLanguage());
        }
        if (entity.getPrimaryLanguage() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getPrimaryLanguage());
        }
        if (entity.getSecondaryLanguage() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getSecondaryLanguage());
        }
        final int _tmp = entity.getUseDevanagariDigits() ? 1 : 0;
        statement.bindLong(5, _tmp);
        if (entity.getTheme() == null) {
          statement.bindNull(6);
        } else {
          statement.bindText(6, entity.getTheme());
        }
        statement.bindDouble(7, entity.getFontSizeMultiplier());
        final int _tmp_1 = entity.getHighContrastMode() ? 1 : 0;
        statement.bindLong(8, _tmp_1);
        statement.bindLong(9, entity.getDailyWordGoal());
        statement.bindLong(10, entity.getDailyTimeGoalMinutes());
        if (entity.getReminderTime() == null) {
          statement.bindNull(11);
        } else {
          statement.bindText(11, entity.getReminderTime());
        }
        if (entity.getReminderDays() == null) {
          statement.bindNull(12);
        } else {
          statement.bindText(12, entity.getReminderDays());
        }
        final int _tmp_2 = entity.getAutoPlayPronunciation() ? 1 : 0;
        statement.bindLong(13, _tmp_2);
        statement.bindDouble(14, entity.getPronunciationSpeed());
        final int _tmp_3 = entity.getAutoRecordEnabled() ? 1 : 0;
        statement.bindLong(15, _tmp_3);
        final int _tmp_4 = entity.getSoundEnabled() ? 1 : 0;
        statement.bindLong(16, _tmp_4);
        final int _tmp_5 = entity.getVibrationEnabled() ? 1 : 0;
        statement.bindLong(17, _tmp_5);
        statement.bindLong(18, entity.getFlashcardSessionSize());
        if (entity.getQuizTimeLimit() == null) {
          statement.bindNull(19);
        } else {
          statement.bindLong(19, entity.getQuizTimeLimit());
        }
        final int _tmp_6 = entity.getShowTranslationHints() ? 1 : 0;
        statement.bindLong(20, _tmp_6);
        final int _tmp_7 = entity.getDailyReminderEnabled() ? 1 : 0;
        statement.bindLong(21, _tmp_7);
        final int _tmp_8 = entity.getStreakReminderEnabled() ? 1 : 0;
        statement.bindLong(22, _tmp_8);
        final int _tmp_9 = entity.getAchievementNotificationsEnabled() ? 1 : 0;
        statement.bindLong(23, _tmp_9);
        final int _tmp_10 = entity.getQuizResultsNotificationsEnabled() ? 1 : 0;
        statement.bindLong(24, _tmp_10);
        if (entity.getLastSelectedCategoryId() == null) {
          statement.bindNull(25);
        } else {
          statement.bindLong(25, entity.getLastSelectedCategoryId());
        }
        if (entity.getLastSelectedLessonId() == null) {
          statement.bindNull(26);
        } else {
          statement.bindLong(26, entity.getLastSelectedLessonId());
        }
        statement.bindLong(27, entity.getInstallDate());
        statement.bindLong(28, entity.getLastUpdatedAt());
        statement.bindLong(29, entity.getId());
      }
    };
  }

  @Override
  public Object insert(final AppSettings settings, final Continuation<? super Unit> $completion) {
    if (settings == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __insertAdapterOfAppSettings.insert(_connection, settings);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Object update(final AppSettings settings, final Continuation<? super Unit> $completion) {
    if (settings == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __updateAdapterOfAppSettings.handle(_connection, settings);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Flow<AppSettings> getSettings() {
    final String _sql = "SELECT * FROM app_settings WHERE id = 1";
    return FlowUtil.createFlow(__db, false, new String[] {"app_settings"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfInterfaceLanguage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "interfaceLanguage");
        final int _columnIndexOfPrimaryLanguage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "primaryLanguage");
        final int _columnIndexOfSecondaryLanguage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "secondaryLanguage");
        final int _columnIndexOfUseDevanagariDigits = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "useDevanagariDigits");
        final int _columnIndexOfTheme = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "theme");
        final int _columnIndexOfFontSizeMultiplier = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "fontSizeMultiplier");
        final int _columnIndexOfHighContrastMode = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "highContrastMode");
        final int _columnIndexOfDailyWordGoal = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "dailyWordGoal");
        final int _columnIndexOfDailyTimeGoalMinutes = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "dailyTimeGoalMinutes");
        final int _columnIndexOfReminderTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "reminderTime");
        final int _columnIndexOfReminderDays = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "reminderDays");
        final int _columnIndexOfAutoPlayPronunciation = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "autoPlayPronunciation");
        final int _columnIndexOfPronunciationSpeed = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "pronunciationSpeed");
        final int _columnIndexOfAutoRecordEnabled = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "autoRecordEnabled");
        final int _columnIndexOfSoundEnabled = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "soundEnabled");
        final int _columnIndexOfVibrationEnabled = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "vibrationEnabled");
        final int _columnIndexOfFlashcardSessionSize = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "flashcardSessionSize");
        final int _columnIndexOfQuizTimeLimit = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "quizTimeLimit");
        final int _columnIndexOfShowTranslationHints = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "showTranslationHints");
        final int _columnIndexOfDailyReminderEnabled = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "dailyReminderEnabled");
        final int _columnIndexOfStreakReminderEnabled = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "streakReminderEnabled");
        final int _columnIndexOfAchievementNotificationsEnabled = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "achievementNotificationsEnabled");
        final int _columnIndexOfQuizResultsNotificationsEnabled = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "quizResultsNotificationsEnabled");
        final int _columnIndexOfLastSelectedCategoryId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastSelectedCategoryId");
        final int _columnIndexOfLastSelectedLessonId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastSelectedLessonId");
        final int _columnIndexOfInstallDate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "installDate");
        final int _columnIndexOfLastUpdatedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "lastUpdatedAt");
        final AppSettings _result;
        if (_stmt.step()) {
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          final String _tmpInterfaceLanguage;
          if (_stmt.isNull(_columnIndexOfInterfaceLanguage)) {
            _tmpInterfaceLanguage = null;
          } else {
            _tmpInterfaceLanguage = _stmt.getText(_columnIndexOfInterfaceLanguage);
          }
          final String _tmpPrimaryLanguage;
          if (_stmt.isNull(_columnIndexOfPrimaryLanguage)) {
            _tmpPrimaryLanguage = null;
          } else {
            _tmpPrimaryLanguage = _stmt.getText(_columnIndexOfPrimaryLanguage);
          }
          final String _tmpSecondaryLanguage;
          if (_stmt.isNull(_columnIndexOfSecondaryLanguage)) {
            _tmpSecondaryLanguage = null;
          } else {
            _tmpSecondaryLanguage = _stmt.getText(_columnIndexOfSecondaryLanguage);
          }
          final boolean _tmpUseDevanagariDigits;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfUseDevanagariDigits));
          _tmpUseDevanagariDigits = _tmp != 0;
          final String _tmpTheme;
          if (_stmt.isNull(_columnIndexOfTheme)) {
            _tmpTheme = null;
          } else {
            _tmpTheme = _stmt.getText(_columnIndexOfTheme);
          }
          final float _tmpFontSizeMultiplier;
          _tmpFontSizeMultiplier = (float) (_stmt.getDouble(_columnIndexOfFontSizeMultiplier));
          final boolean _tmpHighContrastMode;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfHighContrastMode));
          _tmpHighContrastMode = _tmp_1 != 0;
          final int _tmpDailyWordGoal;
          _tmpDailyWordGoal = (int) (_stmt.getLong(_columnIndexOfDailyWordGoal));
          final int _tmpDailyTimeGoalMinutes;
          _tmpDailyTimeGoalMinutes = (int) (_stmt.getLong(_columnIndexOfDailyTimeGoalMinutes));
          final String _tmpReminderTime;
          if (_stmt.isNull(_columnIndexOfReminderTime)) {
            _tmpReminderTime = null;
          } else {
            _tmpReminderTime = _stmt.getText(_columnIndexOfReminderTime);
          }
          final String _tmpReminderDays;
          if (_stmt.isNull(_columnIndexOfReminderDays)) {
            _tmpReminderDays = null;
          } else {
            _tmpReminderDays = _stmt.getText(_columnIndexOfReminderDays);
          }
          final boolean _tmpAutoPlayPronunciation;
          final int _tmp_2;
          _tmp_2 = (int) (_stmt.getLong(_columnIndexOfAutoPlayPronunciation));
          _tmpAutoPlayPronunciation = _tmp_2 != 0;
          final float _tmpPronunciationSpeed;
          _tmpPronunciationSpeed = (float) (_stmt.getDouble(_columnIndexOfPronunciationSpeed));
          final boolean _tmpAutoRecordEnabled;
          final int _tmp_3;
          _tmp_3 = (int) (_stmt.getLong(_columnIndexOfAutoRecordEnabled));
          _tmpAutoRecordEnabled = _tmp_3 != 0;
          final boolean _tmpSoundEnabled;
          final int _tmp_4;
          _tmp_4 = (int) (_stmt.getLong(_columnIndexOfSoundEnabled));
          _tmpSoundEnabled = _tmp_4 != 0;
          final boolean _tmpVibrationEnabled;
          final int _tmp_5;
          _tmp_5 = (int) (_stmt.getLong(_columnIndexOfVibrationEnabled));
          _tmpVibrationEnabled = _tmp_5 != 0;
          final int _tmpFlashcardSessionSize;
          _tmpFlashcardSessionSize = (int) (_stmt.getLong(_columnIndexOfFlashcardSessionSize));
          final Integer _tmpQuizTimeLimit;
          if (_stmt.isNull(_columnIndexOfQuizTimeLimit)) {
            _tmpQuizTimeLimit = null;
          } else {
            _tmpQuizTimeLimit = (int) (_stmt.getLong(_columnIndexOfQuizTimeLimit));
          }
          final boolean _tmpShowTranslationHints;
          final int _tmp_6;
          _tmp_6 = (int) (_stmt.getLong(_columnIndexOfShowTranslationHints));
          _tmpShowTranslationHints = _tmp_6 != 0;
          final boolean _tmpDailyReminderEnabled;
          final int _tmp_7;
          _tmp_7 = (int) (_stmt.getLong(_columnIndexOfDailyReminderEnabled));
          _tmpDailyReminderEnabled = _tmp_7 != 0;
          final boolean _tmpStreakReminderEnabled;
          final int _tmp_8;
          _tmp_8 = (int) (_stmt.getLong(_columnIndexOfStreakReminderEnabled));
          _tmpStreakReminderEnabled = _tmp_8 != 0;
          final boolean _tmpAchievementNotificationsEnabled;
          final int _tmp_9;
          _tmp_9 = (int) (_stmt.getLong(_columnIndexOfAchievementNotificationsEnabled));
          _tmpAchievementNotificationsEnabled = _tmp_9 != 0;
          final boolean _tmpQuizResultsNotificationsEnabled;
          final int _tmp_10;
          _tmp_10 = (int) (_stmt.getLong(_columnIndexOfQuizResultsNotificationsEnabled));
          _tmpQuizResultsNotificationsEnabled = _tmp_10 != 0;
          final Long _tmpLastSelectedCategoryId;
          if (_stmt.isNull(_columnIndexOfLastSelectedCategoryId)) {
            _tmpLastSelectedCategoryId = null;
          } else {
            _tmpLastSelectedCategoryId = _stmt.getLong(_columnIndexOfLastSelectedCategoryId);
          }
          final Long _tmpLastSelectedLessonId;
          if (_stmt.isNull(_columnIndexOfLastSelectedLessonId)) {
            _tmpLastSelectedLessonId = null;
          } else {
            _tmpLastSelectedLessonId = _stmt.getLong(_columnIndexOfLastSelectedLessonId);
          }
          final long _tmpInstallDate;
          _tmpInstallDate = _stmt.getLong(_columnIndexOfInstallDate);
          final long _tmpLastUpdatedAt;
          _tmpLastUpdatedAt = _stmt.getLong(_columnIndexOfLastUpdatedAt);
          _result = new AppSettings(_tmpId,_tmpInterfaceLanguage,_tmpPrimaryLanguage,_tmpSecondaryLanguage,_tmpUseDevanagariDigits,_tmpTheme,_tmpFontSizeMultiplier,_tmpHighContrastMode,_tmpDailyWordGoal,_tmpDailyTimeGoalMinutes,_tmpReminderTime,_tmpReminderDays,_tmpAutoPlayPronunciation,_tmpPronunciationSpeed,_tmpAutoRecordEnabled,_tmpSoundEnabled,_tmpVibrationEnabled,_tmpFlashcardSessionSize,_tmpQuizTimeLimit,_tmpShowTranslationHints,_tmpDailyReminderEnabled,_tmpStreakReminderEnabled,_tmpAchievementNotificationsEnabled,_tmpQuizResultsNotificationsEnabled,_tmpLastSelectedCategoryId,_tmpLastSelectedLessonId,_tmpInstallDate,_tmpLastUpdatedAt);
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
  public Object updateLanguage(final String language,
      final Continuation<? super Unit> $completion) {
    final String _sql = "UPDATE app_settings SET interfaceLanguage = ? WHERE id = 1";
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (language == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, language);
        }
        _stmt.step();
        return Unit.INSTANCE;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object updateNotificationsEnabled(final boolean enabled,
      final Continuation<? super Unit> $completion) {
    final String _sql = "UPDATE app_settings SET dailyReminderEnabled = ? WHERE id = 1";
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        final int _tmp = enabled ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _stmt.step();
        return Unit.INSTANCE;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object updateDailyReminderTime(final String time,
      final Continuation<? super Unit> $completion) {
    final String _sql = "UPDATE app_settings SET reminderTime = ? WHERE id = 1";
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (time == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, time);
        }
        _stmt.step();
        return Unit.INSTANCE;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object updateSoundEnabled(final boolean enabled,
      final Continuation<? super Unit> $completion) {
    final String _sql = "UPDATE app_settings SET soundEnabled = ? WHERE id = 1";
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        final int _tmp = enabled ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _stmt.step();
        return Unit.INSTANCE;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object updateVibrationEnabled(final boolean enabled,
      final Continuation<? super Unit> $completion) {
    final String _sql = "UPDATE app_settings SET vibrationEnabled = ? WHERE id = 1";
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        final int _tmp = enabled ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
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
}
