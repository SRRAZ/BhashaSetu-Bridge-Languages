package com.bhashasetu.app.data.model;

/**
 * Entity storing application settings with a singleton pattern.
 * Only one row should exist in this table.
 */
@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0012\n\u0002\u0010\t\n\u0002\bM\b\u0087\b\u0018\u00002\u00020\u0001B\u00a7\u0002\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\b\b\u0002\u0010\r\u001a\u00020\t\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0012\u001a\u00020\t\u0012\b\b\u0002\u0010\u0013\u001a\u00020\f\u0012\b\b\u0002\u0010\u0014\u001a\u00020\t\u0012\b\b\u0002\u0010\u0015\u001a\u00020\t\u0012\b\b\u0002\u0010\u0016\u001a\u00020\t\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0019\u001a\u00020\t\u0012\b\b\u0002\u0010\u001a\u001a\u00020\t\u0012\b\b\u0002\u0010\u001b\u001a\u00020\t\u0012\b\b\u0002\u0010\u001c\u001a\u00020\t\u0012\b\b\u0002\u0010\u001d\u001a\u00020\t\u0012\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u001f\u0012\n\b\u0002\u0010 \u001a\u0004\u0018\u00010\u001f\u0012\b\b\u0002\u0010!\u001a\u00020\u001f\u0012\b\b\u0002\u0010\"\u001a\u00020\u001f\u00a2\u0006\u0004\b#\u0010$J\t\u0010J\u001a\u00020\u0003H\u00c6\u0003J\t\u0010K\u001a\u00020\u0005H\u00c6\u0003J\t\u0010L\u001a\u00020\u0005H\u00c6\u0003J\t\u0010M\u001a\u00020\u0005H\u00c6\u0003J\t\u0010N\u001a\u00020\tH\u00c6\u0003J\t\u0010O\u001a\u00020\u0005H\u00c6\u0003J\t\u0010P\u001a\u00020\fH\u00c6\u0003J\t\u0010Q\u001a\u00020\tH\u00c6\u0003J\t\u0010R\u001a\u00020\u0003H\u00c6\u0003J\t\u0010S\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010T\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010U\u001a\u00020\u0005H\u00c6\u0003J\t\u0010V\u001a\u00020\tH\u00c6\u0003J\t\u0010W\u001a\u00020\fH\u00c6\u0003J\t\u0010X\u001a\u00020\tH\u00c6\u0003J\t\u0010Y\u001a\u00020\tH\u00c6\u0003J\t\u0010Z\u001a\u00020\tH\u00c6\u0003J\t\u0010[\u001a\u00020\u0003H\u00c6\u0003J\u0010\u0010\\\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010<J\t\u0010]\u001a\u00020\tH\u00c6\u0003J\t\u0010^\u001a\u00020\tH\u00c6\u0003J\t\u0010_\u001a\u00020\tH\u00c6\u0003J\t\u0010`\u001a\u00020\tH\u00c6\u0003J\t\u0010a\u001a\u00020\tH\u00c6\u0003J\u0010\u0010b\u001a\u0004\u0018\u00010\u001fH\u00c6\u0003\u00a2\u0006\u0002\u0010DJ\u0010\u0010c\u001a\u0004\u0018\u00010\u001fH\u00c6\u0003\u00a2\u0006\u0002\u0010DJ\t\u0010d\u001a\u00020\u001fH\u00c6\u0003J\t\u0010e\u001a\u00020\u001fH\u00c6\u0003J\u00ae\u0002\u0010f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u00052\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\t2\b\b\u0002\u0010\u000e\u001a\u00020\u00032\b\b\u0002\u0010\u000f\u001a\u00020\u00032\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0011\u001a\u00020\u00052\b\b\u0002\u0010\u0012\u001a\u00020\t2\b\b\u0002\u0010\u0013\u001a\u00020\f2\b\b\u0002\u0010\u0014\u001a\u00020\t2\b\b\u0002\u0010\u0015\u001a\u00020\t2\b\b\u0002\u0010\u0016\u001a\u00020\t2\b\b\u0002\u0010\u0017\u001a\u00020\u00032\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0019\u001a\u00020\t2\b\b\u0002\u0010\u001a\u001a\u00020\t2\b\b\u0002\u0010\u001b\u001a\u00020\t2\b\b\u0002\u0010\u001c\u001a\u00020\t2\b\b\u0002\u0010\u001d\u001a\u00020\t2\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\n\b\u0002\u0010 \u001a\u0004\u0018\u00010\u001f2\b\b\u0002\u0010!\u001a\u00020\u001f2\b\b\u0002\u0010\"\u001a\u00020\u001fH\u00c6\u0001\u00a2\u0006\u0002\u0010gJ\u0013\u0010h\u001a\u00020\t2\b\u0010i\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010j\u001a\u00020\u0003H\u00d6\u0001J\t\u0010k\u001a\u00020\u0005H\u00d6\u0001R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010(R\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010(R\u0011\u0010\u0007\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010(R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010,R\u0011\u0010\n\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010(R\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010/R\u0011\u0010\r\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u0010,R\u0011\u0010\u000e\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b1\u0010&R\u0011\u0010\u000f\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b2\u0010&R\u0013\u0010\u0010\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b3\u0010(R\u0011\u0010\u0011\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b4\u0010(R\u0011\u0010\u0012\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b5\u0010,R\u0011\u0010\u0013\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b6\u0010/R\u0011\u0010\u0014\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b7\u0010,R\u0011\u0010\u0015\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b8\u0010,R\u0011\u0010\u0016\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b9\u0010,R\u0011\u0010\u0017\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b:\u0010&R\u0015\u0010\u0018\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010=\u001a\u0004\b;\u0010<R\u0011\u0010\u0019\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b>\u0010,R\u0011\u0010\u001a\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b?\u0010,R\u0011\u0010\u001b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b@\u0010,R\u0011\u0010\u001c\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\bA\u0010,R\u0011\u0010\u001d\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\bB\u0010,R\u0015\u0010\u001e\u001a\u0004\u0018\u00010\u001f\u00a2\u0006\n\n\u0002\u0010E\u001a\u0004\bC\u0010DR\u0015\u0010 \u001a\u0004\u0018\u00010\u001f\u00a2\u0006\n\n\u0002\u0010E\u001a\u0004\bF\u0010DR\u0011\u0010!\u001a\u00020\u001f\u00a2\u0006\b\n\u0000\u001a\u0004\bG\u0010HR\u0011\u0010\"\u001a\u00020\u001f\u00a2\u0006\b\n\u0000\u001a\u0004\bI\u0010H\u00a8\u0006l"}, d2 = {"Lcom/bhashasetu/app/data/model/AppSettings;", "", "id", "", "interfaceLanguage", "", "primaryLanguage", "secondaryLanguage", "useDevanagariDigits", "", "theme", "fontSizeMultiplier", "", "highContrastMode", "dailyWordGoal", "dailyTimeGoalMinutes", "reminderTime", "reminderDays", "autoPlayPronunciation", "pronunciationSpeed", "autoRecordEnabled", "soundEnabled", "vibrationEnabled", "flashcardSessionSize", "quizTimeLimit", "showTranslationHints", "dailyReminderEnabled", "streakReminderEnabled", "achievementNotificationsEnabled", "quizResultsNotificationsEnabled", "lastSelectedCategoryId", "", "lastSelectedLessonId", "installDate", "lastUpdatedAt", "<init>", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;ZLjava/lang/String;FZIILjava/lang/String;Ljava/lang/String;ZFZZZILjava/lang/Integer;ZZZZZLjava/lang/Long;Ljava/lang/Long;JJ)V", "getId", "()I", "getInterfaceLanguage", "()Ljava/lang/String;", "getPrimaryLanguage", "getSecondaryLanguage", "getUseDevanagariDigits", "()Z", "getTheme", "getFontSizeMultiplier", "()F", "getHighContrastMode", "getDailyWordGoal", "getDailyTimeGoalMinutes", "getReminderTime", "getReminderDays", "getAutoPlayPronunciation", "getPronunciationSpeed", "getAutoRecordEnabled", "getSoundEnabled", "getVibrationEnabled", "getFlashcardSessionSize", "getQuizTimeLimit", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getShowTranslationHints", "getDailyReminderEnabled", "getStreakReminderEnabled", "getAchievementNotificationsEnabled", "getQuizResultsNotificationsEnabled", "getLastSelectedCategoryId", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getLastSelectedLessonId", "getInstallDate", "()J", "getLastUpdatedAt", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component20", "component21", "component22", "component23", "component24", "component25", "component26", "component27", "component28", "copy", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;ZLjava/lang/String;FZIILjava/lang/String;Ljava/lang/String;ZFZZZILjava/lang/Integer;ZZZZZLjava/lang/Long;Ljava/lang/Long;JJ)Lcom/bhashasetu/app/data/model/AppSettings;", "equals", "other", "hashCode", "toString", "app_debug"})
@androidx.room.Entity(tableName = "app_settings")
public final class AppSettings {
    @androidx.room.PrimaryKey()
    private final int id = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String interfaceLanguage = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String primaryLanguage = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String secondaryLanguage = null;
    private final boolean useDevanagariDigits = false;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String theme = null;
    private final float fontSizeMultiplier = 0.0F;
    private final boolean highContrastMode = false;
    private final int dailyWordGoal = 0;
    private final int dailyTimeGoalMinutes = 0;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String reminderTime = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String reminderDays = null;
    private final boolean autoPlayPronunciation = false;
    private final float pronunciationSpeed = 0.0F;
    private final boolean autoRecordEnabled = false;
    private final boolean soundEnabled = false;
    private final boolean vibrationEnabled = false;
    private final int flashcardSessionSize = 0;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer quizTimeLimit = null;
    private final boolean showTranslationHints = false;
    private final boolean dailyReminderEnabled = false;
    private final boolean streakReminderEnabled = false;
    private final boolean achievementNotificationsEnabled = false;
    private final boolean quizResultsNotificationsEnabled = false;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long lastSelectedCategoryId = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long lastSelectedLessonId = null;
    private final long installDate = 0L;
    private final long lastUpdatedAt = 0L;
    
    public AppSettings(int id, @org.jetbrains.annotations.NotNull()
    java.lang.String interfaceLanguage, @org.jetbrains.annotations.NotNull()
    java.lang.String primaryLanguage, @org.jetbrains.annotations.NotNull()
    java.lang.String secondaryLanguage, boolean useDevanagariDigits, @org.jetbrains.annotations.NotNull()
    java.lang.String theme, float fontSizeMultiplier, boolean highContrastMode, int dailyWordGoal, int dailyTimeGoalMinutes, @org.jetbrains.annotations.Nullable()
    java.lang.String reminderTime, @org.jetbrains.annotations.NotNull()
    java.lang.String reminderDays, boolean autoPlayPronunciation, float pronunciationSpeed, boolean autoRecordEnabled, boolean soundEnabled, boolean vibrationEnabled, int flashcardSessionSize, @org.jetbrains.annotations.Nullable()
    java.lang.Integer quizTimeLimit, boolean showTranslationHints, boolean dailyReminderEnabled, boolean streakReminderEnabled, boolean achievementNotificationsEnabled, boolean quizResultsNotificationsEnabled, @org.jetbrains.annotations.Nullable()
    java.lang.Long lastSelectedCategoryId, @org.jetbrains.annotations.Nullable()
    java.lang.Long lastSelectedLessonId, long installDate, long lastUpdatedAt) {
        super();
    }
    
    public final int getId() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getInterfaceLanguage() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPrimaryLanguage() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSecondaryLanguage() {
        return null;
    }
    
    public final boolean getUseDevanagariDigits() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTheme() {
        return null;
    }
    
    public final float getFontSizeMultiplier() {
        return 0.0F;
    }
    
    public final boolean getHighContrastMode() {
        return false;
    }
    
    public final int getDailyWordGoal() {
        return 0;
    }
    
    public final int getDailyTimeGoalMinutes() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getReminderTime() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getReminderDays() {
        return null;
    }
    
    public final boolean getAutoPlayPronunciation() {
        return false;
    }
    
    public final float getPronunciationSpeed() {
        return 0.0F;
    }
    
    public final boolean getAutoRecordEnabled() {
        return false;
    }
    
    public final boolean getSoundEnabled() {
        return false;
    }
    
    public final boolean getVibrationEnabled() {
        return false;
    }
    
    public final int getFlashcardSessionSize() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getQuizTimeLimit() {
        return null;
    }
    
    public final boolean getShowTranslationHints() {
        return false;
    }
    
    public final boolean getDailyReminderEnabled() {
        return false;
    }
    
    public final boolean getStreakReminderEnabled() {
        return false;
    }
    
    public final boolean getAchievementNotificationsEnabled() {
        return false;
    }
    
    public final boolean getQuizResultsNotificationsEnabled() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getLastSelectedCategoryId() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getLastSelectedLessonId() {
        return null;
    }
    
    public final long getInstallDate() {
        return 0L;
    }
    
    public final long getLastUpdatedAt() {
        return 0L;
    }
    
    public AppSettings() {
        super();
    }
    
    public final int component1() {
        return 0;
    }
    
    public final int component10() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component11() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component12() {
        return null;
    }
    
    public final boolean component13() {
        return false;
    }
    
    public final float component14() {
        return 0.0F;
    }
    
    public final boolean component15() {
        return false;
    }
    
    public final boolean component16() {
        return false;
    }
    
    public final boolean component17() {
        return false;
    }
    
    public final int component18() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component19() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    public final boolean component20() {
        return false;
    }
    
    public final boolean component21() {
        return false;
    }
    
    public final boolean component22() {
        return false;
    }
    
    public final boolean component23() {
        return false;
    }
    
    public final boolean component24() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component25() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component26() {
        return null;
    }
    
    public final long component27() {
        return 0L;
    }
    
    public final long component28() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    public final boolean component5() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component6() {
        return null;
    }
    
    public final float component7() {
        return 0.0F;
    }
    
    public final boolean component8() {
        return false;
    }
    
    public final int component9() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.bhashasetu.app.data.model.AppSettings copy(int id, @org.jetbrains.annotations.NotNull()
    java.lang.String interfaceLanguage, @org.jetbrains.annotations.NotNull()
    java.lang.String primaryLanguage, @org.jetbrains.annotations.NotNull()
    java.lang.String secondaryLanguage, boolean useDevanagariDigits, @org.jetbrains.annotations.NotNull()
    java.lang.String theme, float fontSizeMultiplier, boolean highContrastMode, int dailyWordGoal, int dailyTimeGoalMinutes, @org.jetbrains.annotations.Nullable()
    java.lang.String reminderTime, @org.jetbrains.annotations.NotNull()
    java.lang.String reminderDays, boolean autoPlayPronunciation, float pronunciationSpeed, boolean autoRecordEnabled, boolean soundEnabled, boolean vibrationEnabled, int flashcardSessionSize, @org.jetbrains.annotations.Nullable()
    java.lang.Integer quizTimeLimit, boolean showTranslationHints, boolean dailyReminderEnabled, boolean streakReminderEnabled, boolean achievementNotificationsEnabled, boolean quizResultsNotificationsEnabled, @org.jetbrains.annotations.Nullable()
    java.lang.Long lastSelectedCategoryId, @org.jetbrains.annotations.Nullable()
    java.lang.Long lastSelectedLessonId, long installDate, long lastUpdatedAt) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}