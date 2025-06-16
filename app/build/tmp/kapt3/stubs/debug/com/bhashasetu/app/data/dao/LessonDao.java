package com.bhashasetu.app.data.dao;

/**
 * Data Access Object for the Lesson entity.
 */
@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0013\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\"\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\bH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\r\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u000e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u0010H\u00a7@\u00a2\u0006\u0002\u0010\u0011J\u001c\u0010\u0012\u001a\u00020\f2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00100\bH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u0014\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u0010H\u00a7@\u00a2\u0006\u0002\u0010\u0011J\u001e\u0010\u0015\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\u00032\u0006\u0010\u0017\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0018J\u0016\u0010\u0019\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u001aJ&\u0010\u001b\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\u00032\u0006\u0010\u0017\u001a\u00020\u00032\u0006\u0010\u001c\u001a\u00020\u001dH\u00a7@\u00a2\u0006\u0002\u0010\u001eJ\u0018\u0010\u001f\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0016\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u001aJ\u0018\u0010 \u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050!2\u0006\u0010\u0016\u001a\u00020\u0003H\'J\u0018\u0010\"\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050#2\u0006\u0010\u0016\u001a\u00020\u0003H\'J\u0014\u0010$\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\b0#H\'J\u0014\u0010%\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\b0!H\'J\u001c\u0010&\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\b0#2\u0006\u0010\'\u001a\u00020\u0003H\'J\u001c\u0010(\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\b0#2\u0006\u0010)\u001a\u00020\u001dH\'J\u001c\u0010*\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\b0#2\u0006\u0010+\u001a\u00020,H\'J\u001c\u0010-\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\b0#2\u0006\u0010.\u001a\u00020/H\'J\u0018\u00100\u001a\u0004\u0018\u0001012\u0006\u0010\u0016\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u001aJ\u0018\u00102\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001010#2\u0006\u0010\u0016\u001a\u00020\u0003H\'J\u0014\u00103\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002010\b0#H\'J\u001c\u00104\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002010\b0#2\u0006\u0010\'\u001a\u00020\u0003H\'J\u001c\u00105\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\b0#2\u0006\u0010\u0017\u001a\u00020\u0003H\'J(\u00106\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\u00032\u0006\u0010+\u001a\u00020,2\b\u00107\u001a\u0004\u0018\u00010\u0003H\u00a7@\u00a2\u0006\u0002\u00108J\u001e\u00109\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\u00032\u0006\u0010:\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0018J\u000e\u0010;\u001a\b\u0012\u0004\u0012\u00020\u001d0#H\'J\u000e\u0010<\u001a\b\u0012\u0004\u0012\u00020\u001d0#H\'J\u0016\u0010=\u001a\b\u0012\u0004\u0012\u00020\u001d0#2\u0006\u0010\'\u001a\u00020\u0003H\'J\u0016\u0010>\u001a\b\u0012\u0004\u0012\u00020\u001d0#2\u0006\u0010\'\u001a\u00020\u0003H\'J\u0016\u0010?\u001a\u00020,2\u0006\u0010@\u001a\u00020/H\u00a7@\u00a2\u0006\u0002\u0010AJ\u001e\u0010B\u001a\u00020,2\u0006\u0010\u0016\u001a\u00020\u00032\u0006\u0010\u0017\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0018J\u0016\u0010C\u001a\u00020\u001d2\u0006\u0010\'\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u001a\u00a8\u0006D"}, d2 = {"Lcom/bhashasetu/app/data/dao/LessonDao;", "", "insert", "", "lesson", "Lcom/bhashasetu/app/data/model/Lesson;", "(Lcom/bhashasetu/app/data/model/Lesson;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertAll", "", "lessons", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "update", "", "delete", "addWordToLesson", "lessonWord", "Lcom/bhashasetu/app/data/model/LessonWord;", "(Lcom/bhashasetu/app/data/model/LessonWord;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addWordsToLesson", "lessonWords", "removeWordFromLesson", "removeWordFromLessonById", "lessonId", "wordId", "(JJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "removeAllWordsFromLesson", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateWordOrder", "newOrder", "", "(JJILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getLessonById", "getLessonByIdLiveData", "Landroidx/lifecycle/LiveData;", "getLessonByIdFlow", "Lkotlinx/coroutines/flow/Flow;", "getAllLessons", "getAllLessonsLiveData", "getLessonsByCategory", "categoryId", "getLessonsByDifficulty", "difficulty", "getLessonsByCompletionStatus", "isCompleted", "", "searchLessons", "query", "", "getLessonWithWords", "Lcom/bhashasetu/app/data/relation/LessonWithWords;", "getLessonWithWordsFlow", "getAllLessonsWithWords", "getLessonsWithWordsByCategory", "getLessonsContainingWord", "updateLessonCompletion", "completedAt", "(JZLjava/lang/Long;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateLessonLastAccessed", "accessedAt", "getLessonCount", "getCompletedLessonCount", "getLessonCountByCategory", "getCompletedLessonCountByCategory", "lessonExistsByTitle", "titleEnglish", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "isWordInLesson", "getNextOrderInCategory", "app_debug"})
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