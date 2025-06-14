package com.bhashasetu.app.data.dao;

/**
 * Data Access Object for the Quiz entity.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0012\n\u0002\u0010\u000e\n\u0002\b\n\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\rH\u00a7@\u00a2\u0006\u0002\u0010\u000eJ\u0014\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00110\u0010H\'J\u0014\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00110\u0013H\'J\u0014\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\u00110\u0010H\'J\u0010\u0010\u0016\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00170\u0010H\'J\u000e\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u0010H\'J\u0018\u0010\u001a\u001a\u0004\u0018\u00010\r2\u0006\u0010\u001b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u001c\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u00110\u00102\u0006\u0010\b\u001a\u00020\tH\'J$\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u00110\u00102\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u001e\u001a\u00020\u0019H\'J\u0018\u0010\u001f\u001a\u0004\u0018\u00010\u00052\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0018\u0010 \u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u00102\u0006\u0010\b\u001a\u00020\tH\'J\u0018\u0010!\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u00132\u0006\u0010\b\u001a\u00020\tH\'J\u000e\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00190\u0010H\'J\u0018\u0010#\u001a\u0004\u0018\u00010\u00152\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0018\u0010$\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00150\u00102\u0006\u0010\b\u001a\u00020\tH\'J\u001c\u0010%\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00110\u00102\u0006\u0010&\u001a\u00020\tH\'J\u001c\u0010\'\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00110\u00102\u0006\u0010(\u001a\u00020)H\'J\u001c\u0010*\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00110\u00102\u0006\u0010+\u001a\u00020\u0019H\'J\u001c\u0010,\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00110\u00102\u0006\u0010-\u001a\u00020\tH\'J\u001c\u0010.\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\u00110\u00102\u0006\u0010&\u001a\u00020\tH\'J\u001c\u0010/\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\u00110\u00102\u0006\u0010-\u001a\u00020\tH\'J\u0010\u00100\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00190\u0010H\'J\u0010\u00101\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00190\u0010H\'J\u000e\u00102\u001a\b\u0012\u0004\u0012\u00020\u00190\u0010H\'J\u0016\u00103\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\"\u00104\u001a\b\u0012\u0004\u0012\u00020\t0\u00112\f\u00105\u001a\b\u0012\u0004\u0012\u00020\u00050\u0011H\u00a7@\u00a2\u0006\u0002\u00106J\u0016\u00107\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\rH\u00a7@\u00a2\u0006\u0002\u0010\u000eJ\"\u00108\u001a\b\u0012\u0004\u0012\u00020\t0\u00112\f\u00109\u001a\b\u0012\u0004\u0012\u00020\r0\u0011H\u00a7@\u00a2\u0006\u0002\u00106J\u001c\u0010:\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00110\u00102\u0006\u0010;\u001a\u00020<H\'J\u0016\u0010=\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010>\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\rH\u00a7@\u00a2\u0006\u0002\u0010\u000eJ\u001e\u0010?\u001a\u00020\u00032\u0006\u0010\u001b\u001a\u00020\t2\u0006\u0010@\u001a\u00020)H\u00a7@\u00a2\u0006\u0002\u0010AJ.\u0010B\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\u0006\u0010(\u001a\u00020)2\u0006\u0010C\u001a\u00020\u00192\u0006\u0010D\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010E\u00a8\u0006F"}, d2 = {"Lcom/bhashasetu/app/data/dao/QuizDao;", "", "delete", "", "quiz", "Lcom/bhashasetu/app/data/model/Quiz;", "(Lcom/bhashasetu/app/data/model/Quiz;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteAllQuestionsForQuiz", "quizId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteQuestion", "question", "Lcom/bhashasetu/app/data/model/QuizQuestion;", "(Lcom/bhashasetu/app/data/model/QuizQuestion;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllQuizzes", "Lkotlinx/coroutines/flow/Flow;", "", "getAllQuizzesLiveData", "Landroidx/lifecycle/LiveData;", "getAllQuizzesWithQuestions", "Lcom/bhashasetu/app/data/relation/QuizWithQuestions;", "getAverageQuizScore", "", "getCompletedQuizCount", "", "getQuestionById", "questionId", "getQuestionsForQuiz", "getQuestionsForQuizByType", "questionType", "getQuizById", "getQuizByIdFlow", "getQuizByIdLiveData", "getQuizCount", "getQuizWithQuestions", "getQuizWithQuestionsFlow", "getQuizzesByCategory", "categoryId", "getQuizzesByCompletionStatus", "isCompleted", "", "getQuizzesByDifficulty", "difficulty", "getQuizzesByLesson", "lessonId", "getQuizzesWithQuestionsByCategory", "getQuizzesWithQuestionsByLesson", "getTotalCorrectAnswers", "getTotalQuestionAttempts", "getTotalQuestionCount", "insert", "insertAll", "quizzes", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertQuestion", "insertQuestions", "questions", "searchQuizzes", "query", "", "update", "updateQuestion", "updateQuestionStats", "isCorrect", "(JZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateQuizProgress", "score", "attemptTime", "(JZIJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_freeDebug"})
@androidx.room.Dao()
public abstract interface QuizDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Quiz quiz, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertAll(@org.jetbrains.annotations.NotNull()
    java.util.List<com.bhashasetu.app.data.model.Quiz> quizzes, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.Long>> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object update(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Quiz quiz, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object delete(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.Quiz quiz, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertQuestion(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.QuizQuestion question, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertQuestions(@org.jetbrains.annotations.NotNull()
    java.util.List<com.bhashasetu.app.data.model.QuizQuestion> questions, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.Long>> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateQuestion(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.QuizQuestion question, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteQuestion(@org.jetbrains.annotations.NotNull()
    com.bhashasetu.app.data.model.QuizQuestion question, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM quiz_questions WHERE quizId = :quizId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteAllQuestionsForQuiz(long quizId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM quizzes WHERE id = :quizId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getQuizById(long quizId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.bhashasetu.app.data.model.Quiz> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM quizzes WHERE id = :quizId")
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<com.bhashasetu.app.data.model.Quiz> getQuizByIdLiveData(long quizId);
    
    @androidx.room.Query(value = "SELECT * FROM quizzes WHERE id = :quizId")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.bhashasetu.app.data.model.Quiz> getQuizByIdFlow(long quizId);
    
    @androidx.room.Query(value = "SELECT * FROM quizzes ORDER BY titleEnglish ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Quiz>> getAllQuizzes();
    
    @androidx.room.Query(value = "SELECT * FROM quizzes ORDER BY titleEnglish ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<java.util.List<com.bhashasetu.app.data.model.Quiz>> getAllQuizzesLiveData();
    
    @androidx.room.Query(value = "SELECT * FROM quizzes WHERE lessonId = :lessonId")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Quiz>> getQuizzesByLesson(long lessonId);
    
    @androidx.room.Query(value = "SELECT * FROM quizzes WHERE categoryId = :categoryId")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Quiz>> getQuizzesByCategory(long categoryId);
    
    @androidx.room.Query(value = "SELECT * FROM quizzes WHERE difficulty = :difficulty")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Quiz>> getQuizzesByDifficulty(int difficulty);
    
    @androidx.room.Query(value = "SELECT * FROM quizzes WHERE isCompleted = :isCompleted")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Quiz>> getQuizzesByCompletionStatus(boolean isCompleted);
    
    @androidx.room.Query(value = "SELECT * FROM quizzes WHERE titleEnglish LIKE \'%\' || :query || \'%\' OR titleHindi LIKE \'%\' || :query || \'%\'")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.Quiz>> searchQuizzes(@org.jetbrains.annotations.NotNull()
    java.lang.String query);
    
    @androidx.room.Query(value = "SELECT * FROM quiz_questions WHERE id = :questionId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getQuestionById(long questionId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.bhashasetu.app.data.model.QuizQuestion> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM quiz_questions WHERE quizId = :quizId ORDER BY orderInQuiz ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.QuizQuestion>> getQuestionsForQuiz(long quizId);
    
    @androidx.room.Query(value = "SELECT * FROM quiz_questions WHERE quizId = :quizId AND questionType = :questionType ORDER BY orderInQuiz ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.model.QuizQuestion>> getQuestionsForQuizByType(long quizId, int questionType);
    
    @androidx.room.Transaction()
    @androidx.room.Query(value = "SELECT * FROM quizzes WHERE id = :quizId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getQuizWithQuestions(long quizId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.bhashasetu.app.data.relation.QuizWithQuestions> $completion);
    
    @androidx.room.Transaction()
    @androidx.room.Query(value = "SELECT * FROM quizzes WHERE id = :quizId")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.bhashasetu.app.data.relation.QuizWithQuestions> getQuizWithQuestionsFlow(long quizId);
    
    @androidx.room.Transaction()
    @androidx.room.Query(value = "SELECT * FROM quizzes")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.relation.QuizWithQuestions>> getAllQuizzesWithQuestions();
    
    @androidx.room.Transaction()
    @androidx.room.Query(value = "SELECT * FROM quizzes WHERE lessonId = :lessonId")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.relation.QuizWithQuestions>> getQuizzesWithQuestionsByLesson(long lessonId);
    
    @androidx.room.Transaction()
    @androidx.room.Query(value = "SELECT * FROM quizzes WHERE categoryId = :categoryId")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bhashasetu.app.data.relation.QuizWithQuestions>> getQuizzesWithQuestionsByCategory(long categoryId);
    
    @androidx.room.Query(value = "\n        UPDATE quizzes\n        SET isCompleted = :isCompleted,\n            lastAttemptScore = :score,\n            bestScore = CASE WHEN :score > IFNULL(bestScore, 0) THEN :score ELSE bestScore END,\n            attemptCount = attemptCount + 1,\n            lastAttemptAt = :attemptTime,\n            completedAt = CASE WHEN :isCompleted THEN :attemptTime ELSE completedAt END\n        WHERE id = :quizId\n    ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateQuizProgress(long quizId, boolean isCompleted, int score, long attemptTime, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "\n        UPDATE quiz_questions\n        SET correctAttempts = correctAttempts + CASE WHEN :isCorrect THEN 1 ELSE 0 END,\n            totalAttempts = totalAttempts + 1\n        WHERE id = :questionId\n    ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateQuestionStats(long questionId, boolean isCorrect, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM quizzes")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getQuizCount();
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM quizzes WHERE isCompleted = 1")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getCompletedQuizCount();
    
    @androidx.room.Query(value = "SELECT AVG(CAST(lastAttemptScore AS FLOAT) / (SELECT COUNT(*) FROM quiz_questions WHERE quizId = quizzes.id)) FROM quizzes WHERE lastAttemptScore IS NOT NULL")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Float> getAverageQuizScore();
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM quiz_questions")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getTotalQuestionCount();
    
    @androidx.room.Query(value = "SELECT SUM(correctAttempts) FROM quiz_questions")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getTotalCorrectAnswers();
    
    @androidx.room.Query(value = "SELECT SUM(totalAttempts) FROM quiz_questions")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getTotalQuestionAttempts();
}