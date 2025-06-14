package com.bhashasetu.app.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u000f\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u000fJ\u0016\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u0012J\u0012\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u00070\u0006J\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u0006J\u0012\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006J\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\b0\u00062\u0006\u0010\u0019\u001a\u00020\u001aJ\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00160\u0006J\u0014\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00140\u00062\u0006\u0010\u001d\u001a\u00020\u001aJ\u001a\u0010\u001e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00062\u0006\u0010\u001f\u001a\u00020\u0016J\u0012\u0010 \u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006J\u0012\u0010!\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006J\u0016\u0010\"\u001a\u00020\u001a2\u0006\u0010\u0011\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u0012J\u0016\u0010#\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020\u001aH\u0086@\u00a2\u0006\u0002\u0010$J\u001e\u0010%\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020\u001a2\u0006\u0010&\u001a\u00020\u001aH\u0086@\u00a2\u0006\u0002\u0010\'J\u0016\u0010(\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u0012R\u001d\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006)"}, d2 = {"Lcom/bhashasetu/app/data/repository/LessonRepository;", "", "lessonDao", "Lcom/bhashasetu/app/data/dao/LessonDao;", "(Lcom/bhashasetu/app/data/dao/LessonDao;)V", "allLessons", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/bhashasetu/app/data/model/Lesson;", "getAllLessons", "()Lkotlinx/coroutines/flow/Flow;", "addWordToLesson", "", "lessonWord", "Lcom/bhashasetu/app/data/model/LessonWord;", "(Lcom/bhashasetu/app/data/model/LessonWord;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "delete", "lesson", "(Lcom/bhashasetu/app/data/model/Lesson;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllLessonsWithWords", "Lcom/bhashasetu/app/data/relation/LessonWithWords;", "getCompletedLessonCount", "", "getCompletedLessons", "getLessonById", "id", "", "getLessonCount", "getLessonWithWords", "lessonId", "getLessonsByLevel", "level", "getPendingLessons", "getUnlockedLessons", "insert", "markLessonAsCompleted", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "removeWordFromLesson", "wordId", "(JJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "update", "app_freeDebug"})
public final class LessonRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.bhashasetu.app.data.dao.LessonDao lessonDao = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Lesson>> allLessons = null;
    
    public LessonRepository(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.dao.LessonDao lessonDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Lesson>> getAllLessons() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Lesson lesson, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object update(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Lesson lesson, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object delete(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Lesson lesson, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.bhashasetu.app.data.model.Lesson> getLessonById(long id) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Lesson>> getLessonsByLevel(int level) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Lesson>> getCompletedLessons() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Lesson>> getPendingLessons() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.bhashasetu.app.data.relation.LessonWithWords> getLessonWithWords(long lessonId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.relation.LessonWithWords>> getAllLessonsWithWords() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addWordToLesson(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.LessonWord lessonWord, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object removeWordFromLesson(long lessonId, long wordId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object markLessonAsCompleted(long lessonId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Lesson>> getUnlockedLessons() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Integer> getLessonCount() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Integer> getCompletedLessonCount() {
        return null;
    }
}