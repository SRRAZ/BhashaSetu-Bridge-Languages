package com.bhashasetu.app.data.dao;

/**
 * Data Access Object for the Lesson entity.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0010\u000e\n\u0002\b\u0010\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\u0007\u001a\u00020\u00032\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\rH\u00a7@\u00a2\u0006\u0002\u0010\u000eJ\u0014\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\t0\u0010H\'J\u0014\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\t0\u0012H\'J\u0014\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\t0\u0010H\'J\u000e\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u0010H\'J\u0016\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00160\u00102\u0006\u0010\u0018\u001a\u00020\u0019H\'J\u0018\u0010\u001a\u001a\u0004\u0018\u00010\r2\u0006\u0010\u001b\u001a\u00020\u0019H\u00a7@\u00a2\u0006\u0002\u0010\u001cJ\u0018\u0010\u001d\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\u00102\u0006\u0010\u001b\u001a\u00020\u0019H\'J\u0018\u0010\u001e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\u00122\u0006\u0010\u001b\u001a\u00020\u0019H\'J\u000e\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00160\u0010H\'J\u0016\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00160\u00102\u0006\u0010\u0018\u001a\u00020\u0019H\'J\u0018\u0010!\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u001b\u001a\u00020\u0019H\u00a7@\u00a2\u0006\u0002\u0010\u001cJ\u0018\u0010\"\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00140\u00102\u0006\u0010\u001b\u001a\u00020\u0019H\'J\u001c\u0010#\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\t0\u00102\u0006\u0010\u0018\u001a\u00020\u0019H\'J\u001c\u0010$\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\t0\u00102\u0006\u0010%\u001a\u00020&H\'J\u001c\u0010\'\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\t0\u00102\u0006\u0010(\u001a\u00020\u0016H\'J\u001c\u0010)\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\t0\u00102\u0006\u0010*\u001a\u00020\u0019H\'J\u001c\u0010+\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\t0\u00102\u0006\u0010\u0018\u001a\u00020\u0019H\'J\u0016\u0010,\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0019H\u00a7@\u00a2\u0006\u0002\u0010\u001cJ\u0016\u0010-\u001a\u00020\u00192\u0006\u0010\f\u001a\u00020\rH\u00a7@\u00a2\u0006\u0002\u0010\u000eJ\"\u0010.\u001a\b\u0012\u0004\u0012\u00020\u00190\t2\f\u0010/\u001a\b\u0012\u0004\u0012\u00020\r0\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u001e\u00100\u001a\u00020&2\u0006\u0010\u001b\u001a\u00020\u00192\u0006\u0010*\u001a\u00020\u0019H\u00a7@\u00a2\u0006\u0002\u00101J\u0016\u00102\u001a\u00020&2\u0006\u00103\u001a\u000204H\u00a7@\u00a2\u0006\u0002\u00105J\u0016\u00106\u001a\u00020\u00032\u0006\u0010\u001b\u001a\u00020\u0019H\u00a7@\u00a2\u0006\u0002\u0010\u001cJ\u0016\u00107\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u001e\u00108\u001a\u00020\u00032\u0006\u0010\u001b\u001a\u00020\u00192\u0006\u0010*\u001a\u00020\u0019H\u00a7@\u00a2\u0006\u0002\u00101J\u001c\u00109\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\t0\u00102\u0006\u0010:\u001a\u000204H\'J\u0016\u0010;\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\rH\u00a7@\u00a2\u0006\u0002\u0010\u000eJ(\u0010<\u001a\u00020\u00032\u0006\u0010\u001b\u001a\u00020\u00192\u0006\u0010%\u001a\u00020&2\b\u0010=\u001a\u0004\u0018\u00010\u0019H\u00a7@\u00a2\u0006\u0002\u0010>J\u001e\u0010?\u001a\u00020\u00032\u0006\u0010\u001b\u001a\u00020\u00192\u0006\u0010@\u001a\u00020\u0019H\u00a7@\u00a2\u0006\u0002\u00101J&\u0010A\u001a\u00020\u00032\u0006\u0010\u001b\u001a\u00020\u00192\u0006\u0010*\u001a\u00020\u00192\u0006\u0010B\u001a\u00020\u0016H\u00a7@\u00a2\u0006\u0002\u0010C\u00a8\u0006D"}, d2 = {"Lcom/bhashasetu/app/data/dao/LessonDao;", "", "addWordToLesson", "", "lessonWord", "Lcom/bhashasetu/app/data/model/LessonWord;", "(Lcom/bhashasetu/app/data/model/LessonWord;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addWordsToLesson", "lessonWords", "", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "delete", "lesson", "Lcom/bhashasetu/app/data/model/Lesson;", "(Lcom/bhashasetu/app/data/model/Lesson;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllLessons", "Lkotlinx/coroutines/flow/Flow;", "getAllLessonsLiveData", "Landroidx/lifecycle/LiveData;", "getAllLessonsWithWords", "Lcom/bhashasetu/app/data/relation/LessonWithWords;", "getCompletedLessonCount", "", "getCompletedLessonCountByCategory", "categoryId", "", "getLessonById", "lessonId", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getLessonByIdFlow", "getLessonByIdLiveData", "getLessonCount", "getLessonCountByCategory", "getLessonWithWords", "getLessonWithWordsFlow", "getLessonsByCategory", "getLessonsByCompletionStatus", "isCompleted", "", "getLessonsByDifficulty", "difficulty", "getLessonsContainingWord", "wordId", "getLessonsWithWordsByCategory", "getNextOrderInCategory", "insert", "insertAll", "lessons", "isWordInLesson", "(JJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "lessonExistsByTitle", "titleEnglish", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "removeAllWordsFromLesson", "removeWordFromLesson", "removeWordFromLessonById", "searchLessons", "query", "update", "updateLessonCompletion", "completedAt", "(JZLjava/lang/Long;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateLessonLastAccessed", "accessedAt", "updateWordOrder", "newOrder", "(JJILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_freeDebug"})
@androidx.room.Dao()
public abstract interface LessonDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Lesson lesson, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertAll(@org.jetbrains.annotations.NotNull()
    java.util.List<com.bhashasetu.app.data.model.Lesson> lessons, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.Long>> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object update(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Lesson lesson, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object delete(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Lesson lesson, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object addWordToLesson(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.LessonWord lessonWord, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object addWordsToLesson(@org.jetbrains.annotations.NotNull()
    java.util.List<com.bhashasetu.app.data.model.LessonWord> lessonWords, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object removeWordFromLesson(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.LessonWord lessonWord, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM lesson_words WHERE lessonId = :lessonId AND wordId = :wordId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object removeWordFromLessonById(long lessonId, long wordId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM lesson_words WHERE lessonId = :lessonId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object removeAllWordsFromLesson(long lessonId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE lesson_words SET orderInLesson = :newOrder WHERE lessonId = :lessonId AND wordId = :wordId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateWordOrder(long lessonId, long wordId, int newOrder, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM lessons WHERE id = :lessonId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getLessonById(long lessonId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.bhashasetu.app.data.model.Lesson> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM lessons WHERE id = :lessonId")
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<com.bhashasetu.app.data.model.Lesson> getLessonByIdLiveData(long lessonId);
    
    @androidx.room.Query(value = "SELECT * FROM lessons WHERE id = :lessonId")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.bhashasetu.app.data.model.Lesson> getLessonByIdFlow(long lessonId);
    
    @androidx.room.Query(value = "SELECT * FROM lessons ORDER BY difficulty ASC, orderInCategory ASC, titleEnglish ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Lesson>> getAllLessons();
    
    @androidx.room.Query(value = "SELECT * FROM lessons ORDER BY difficulty ASC, orderInCategory ASC, titleEnglish ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<java.util.List<com.bhashasetu.app.data.model.Lesson>> getAllLessonsLiveData();
    
    @androidx.room.Query(value = "SELECT * FROM lessons WHERE categoryId = :categoryId ORDER BY orderInCategory ASC, titleEnglish ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Lesson>> getLessonsByCategory(long categoryId);
    
    @androidx.room.Query(value = "SELECT * FROM lessons WHERE difficulty = :difficulty ORDER BY orderInCategory ASC, titleEnglish ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Lesson>> getLessonsByDifficulty(int difficulty);
    
    @androidx.room.Query(value = "SELECT * FROM lessons WHERE isCompleted = :isCompleted ORDER BY difficulty ASC, orderInCategory ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Lesson>> getLessonsByCompletionStatus(boolean isCompleted);
    
    @androidx.room.Query(value = "SELECT * FROM lessons WHERE titleEnglish LIKE \'%\' || :query || \'%\' OR titleHindi LIKE \'%\' || :query || \'%\'")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Lesson>> searchLessons(@org.jetbrains.annotations.NotNull()
    java.lang.String query);
    
    @androidx.room.Transaction()
    @androidx.room.Query(value = "SELECT * FROM lessons WHERE id = :lessonId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getLessonWithWords(long lessonId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.bhashasetu.app.data.relation.LessonWithWords> $completion);
    
    @androidx.room.Transaction()
    @androidx.room.Query(value = "SELECT * FROM lessons WHERE id = :lessonId")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.bhashasetu.app.data.relation.LessonWithWords> getLessonWithWordsFlow(long lessonId);
    
    @androidx.room.Transaction()
    @androidx.room.Query(value = "SELECT * FROM lessons")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.relation.LessonWithWords>> getAllLessonsWithWords();
    
    @androidx.room.Transaction()
    @androidx.room.Query(value = "SELECT * FROM lessons WHERE categoryId = :categoryId ORDER BY orderInCategory ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.relation.LessonWithWords>> getLessonsWithWordsByCategory(long categoryId);
    
    @androidx.room.Query(value = "\n        SELECT l.* FROM lessons l\n        INNER JOIN lesson_words lw ON l.id = lw.lessonId\n        WHERE lw.wordId = :wordId\n        ORDER BY l.difficulty ASC, l.orderInCategory ASC\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Lesson>> getLessonsContainingWord(long wordId);
    
    @androidx.room.Query(value = "UPDATE lessons SET isCompleted = :isCompleted, completedAt = :completedAt WHERE id = :lessonId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateLessonCompletion(long lessonId, boolean isCompleted, @org.jetbrains.annotations.Nullable()
    java.lang.Long completedAt, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE lessons SET lastAccessedAt = :accessedAt WHERE id = :lessonId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateLessonLastAccessed(long lessonId, long accessedAt, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM lessons")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getLessonCount();
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM lessons WHERE isCompleted = 1")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getCompletedLessonCount();
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM lessons WHERE categoryId = :categoryId")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getLessonCountByCategory(long categoryId);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM lessons WHERE categoryId = :categoryId AND isCompleted = 1")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getCompletedLessonCountByCategory(long categoryId);
    
    @androidx.room.Query(value = "SELECT EXISTS(SELECT 1 FROM lessons WHERE titleEnglish = :titleEnglish LIMIT 1)")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object lessonExistsByTitle(@org.jetbrains.annotations.NotNull()
    java.lang.String titleEnglish, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion);
    
    @androidx.room.Query(value = "SELECT EXISTS(SELECT 1 FROM lesson_words WHERE lessonId = :lessonId AND wordId = :wordId LIMIT 1)")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object isWordInLesson(long lessonId, long wordId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion);
    
    @androidx.room.Query(value = "SELECT MAX(orderInCategory) + 1 FROM lessons WHERE categoryId = :categoryId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getNextOrderInCategory(long categoryId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
}