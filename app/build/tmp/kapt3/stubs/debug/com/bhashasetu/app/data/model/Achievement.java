package com.bhashasetu.app.data.model;

/**
 * Entity representing a modern bilingual achievement that can be unlocked by users.
 */
@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0002\b\u001d\n\u0002\u0010\u0006\n\u0002\b\u001c\b\u0087\b\u0018\u00002\u00020\u0001B\u00b5\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\f\u001a\u00020\u0003\u0012\u0006\u0010\r\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000e\u001a\u00020\t\u0012\b\b\u0002\u0010\u000f\u001a\u00020\t\u0012\b\b\u0002\u0010\u0010\u001a\u00020\t\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0012\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0014\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0012\u0012\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u0006\u00101\u001a\u000202J\u0010\u00103\u001a\u00020\u00122\b\b\u0002\u00104\u001a\u000202J\u0006\u00105\u001a\u00020\u0012J\t\u00106\u001a\u00020\u0003H\u00c6\u0003J\t\u00107\u001a\u00020\u0003H\u00c6\u0003J\t\u00108\u001a\u00020\u0003H\u00c6\u0003J\t\u00109\u001a\u00020\u0003H\u00c6\u0003J\t\u0010:\u001a\u00020\u0003H\u00c6\u0003J\t\u0010;\u001a\u00020\tH\u00c6\u0003J\u0010\u0010<\u001a\u0004\u0018\u00010\tH\u00c6\u0003\u00a2\u0006\u0002\u0010#J\u000b\u0010=\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010>\u001a\u00020\u0003H\u00c6\u0003J\t\u0010?\u001a\u00020\u0003H\u00c6\u0003J\t\u0010@\u001a\u00020\tH\u00c6\u0003J\t\u0010A\u001a\u00020\tH\u00c6\u0003J\t\u0010B\u001a\u00020\tH\u00c6\u0003J\t\u0010C\u001a\u00020\u0012H\u00c6\u0003J\u0010\u0010D\u001a\u0004\u0018\u00010\u0014H\u00c6\u0003\u00a2\u0006\u0002\u0010-J\t\u0010E\u001a\u00020\u0012H\u00c6\u0003J\u000b\u0010F\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010G\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u00cc\u0001\u0010H\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\f\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\u00032\b\b\u0002\u0010\u000e\u001a\u00020\t2\b\b\u0002\u0010\u000f\u001a\u00020\t2\b\b\u0002\u0010\u0010\u001a\u00020\t2\b\b\u0002\u0010\u0011\u001a\u00020\u00122\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00142\b\b\u0002\u0010\u0015\u001a\u00020\u00122\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001\u00a2\u0006\u0002\u0010IJ\u0013\u0010J\u001a\u00020\u00122\b\u0010K\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010L\u001a\u00020\tH\u00d6\u0001J\t\u0010M\u001a\u00020\u0003H\u00d6\u0001R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001bR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001bR\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001bR\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001bR\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0015\u0010\n\u001a\u0004\u0018\u00010\t\u00a2\u0006\n\n\u0002\u0010$\u001a\u0004\b\"\u0010#R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u001bR\u0011\u0010\f\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u001bR\u0011\u0010\r\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\u001bR\u0011\u0010\u000e\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010!R\u0011\u0010\u000f\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010!R\u0011\u0010\u0010\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010!R\u0011\u0010\u0011\u001a\u00020\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010+R\u0015\u0010\u0013\u001a\u0004\u0018\u00010\u0014\u00a2\u0006\n\n\u0002\u0010.\u001a\u0004\b,\u0010-R\u0011\u0010\u0015\u001a\u00020\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010+R\u0013\u0010\u0016\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u0010\u001bR\u0013\u0010\u0017\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u0010\u001b\u00a8\u0006N"}, d2 = {"Lcom/bhashasetu/app/data/model/Achievement;", "", "id", "", "titleEnglish", "titleHindi", "descriptionEnglish", "descriptionHindi", "iconResId", "", "badgeImageResId", "colorHex", "category", "type", "maxProgress", "currentProgress", "pointsRewarded", "isUnlocked", "", "unlockedAt", "", "isHidden", "unlockMessage", "triggerConditions", "<init>", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IIIZLjava/lang/Long;ZLjava/lang/String;Ljava/lang/String;)V", "getId", "()Ljava/lang/String;", "getTitleEnglish", "getTitleHindi", "getDescriptionEnglish", "getDescriptionHindi", "getIconResId", "()I", "getBadgeImageResId", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getColorHex", "getCategory", "getType", "getMaxProgress", "getCurrentProgress", "getPointsRewarded", "()Z", "getUnlockedAt", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getUnlockMessage", "getTriggerConditions", "getProgressPercentage", "", "isNearCompletion", "threshold", "canBeUnlocked", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "copy", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IIIZLjava/lang/Long;ZLjava/lang/String;Ljava/lang/String;)Lcom/bhashasetu/app/data/model/Achievement;", "equals", "other", "hashCode", "toString", "app_debug"})
@androidx.room.Entity(tableName = "achievements")
public final class Achievement {
    @androidx.room.PrimaryKey()
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String id = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String titleEnglish = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String titleHindi = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String descriptionEnglish = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String descriptionHindi = null;
    private final int iconResId = 0;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer badgeImageResId = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String colorHex = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String category = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String type = null;
    private final int maxProgress = 0;
    private final int currentProgress = 0;
    private final int pointsRewarded = 0;
    private final boolean isUnlocked = false;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long unlockedAt = null;
    private final boolean isHidden = false;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String unlockMessage = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String triggerConditions = null;
    
    public Achievement(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String titleEnglish, @org.jetbrains.annotations.NotNull()
    java.lang.String titleHindi, @org.jetbrains.annotations.NotNull()
    java.lang.String descriptionEnglish, @org.jetbrains.annotations.NotNull()
    java.lang.String descriptionHindi, int iconResId, @org.jetbrains.annotations.Nullable()
    java.lang.Integer badgeImageResId, @org.jetbrains.annotations.Nullable()
    java.lang.String colorHex, @org.jetbrains.annotations.NotNull()
    java.lang.String category, @org.jetbrains.annotations.NotNull()
    java.lang.String type, int maxProgress, int currentProgress, int pointsRewarded, boolean isUnlocked, @org.jetbrains.annotations.Nullable()
    java.lang.Long unlockedAt, boolean isHidden, @org.jetbrains.annotations.Nullable()
    java.lang.String unlockMessage, @org.jetbrains.annotations.Nullable()
    java.lang.String triggerConditions) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTitleEnglish() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTitleHindi() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDescriptionEnglish() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDescriptionHindi() {
        return null;
    }
    
    public final int getIconResId() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getBadgeImageResId() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getColorHex() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCategory() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getType() {
        return null;
    }
    
    public final int getMaxProgress() {
        return 0;
    }
    
    public final int getCurrentProgress() {
        return 0;
    }
    
    public final int getPointsRewarded() {
        return 0;
    }
    
    public final boolean isUnlocked() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getUnlockedAt() {
        return null;
    }
    
    public final boolean isHidden() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getUnlockMessage() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getTriggerConditions() {
        return null;
    }
    
    public final double getProgressPercentage() {
        return 0.0;
    }
    
    public final boolean isNearCompletion(double threshold) {
        return false;
    }
    
    public final boolean canBeUnlocked() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component10() {
        return null;
    }
    
    public final int component11() {
        return 0;
    }
    
    public final int component12() {
        return 0;
    }
    
    public final int component13() {
        return 0;
    }
    
    public final boolean component14() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component15() {
        return null;
    }
    
    public final boolean component16() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component17() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component18() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    public final int component6() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.bhashasetu.app.data.model.Achievement copy(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String titleEnglish, @org.jetbrains.annotations.NotNull()
    java.lang.String titleHindi, @org.jetbrains.annotations.NotNull()
    java.lang.String descriptionEnglish, @org.jetbrains.annotations.NotNull()
    java.lang.String descriptionHindi, int iconResId, @org.jetbrains.annotations.Nullable()
    java.lang.Integer badgeImageResId, @org.jetbrains.annotations.Nullable()
    java.lang.String colorHex, @org.jetbrains.annotations.NotNull()
    java.lang.String category, @org.jetbrains.annotations.NotNull()
    java.lang.String type, int maxProgress, int currentProgress, int pointsRewarded, boolean isUnlocked, @org.jetbrains.annotations.Nullable()
    java.lang.Long unlockedAt, boolean isHidden, @org.jetbrains.annotations.Nullable()
    java.lang.String unlockMessage, @org.jetbrains.annotations.Nullable()
    java.lang.String triggerConditions) {
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