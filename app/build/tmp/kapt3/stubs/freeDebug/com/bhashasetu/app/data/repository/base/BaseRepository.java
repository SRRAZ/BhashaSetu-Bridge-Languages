package com.bhashasetu.app.data.repository.base;

/**
 * Base Repository class with common functionality for all repositories
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u00002\u00020\u0001:\u0002\u0007\bB\u0005\u00a2\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00060\u00050\u0004\"\u0004\b\u0000\u0010\u0006*\b\u0012\u0004\u0012\u0002H\u00060\u0004H\u0004\u00a8\u0006\t"}, d2 = {"Lcom/bhashasetu/app/data/repository/base/BaseRepository;", "", "()V", "asResource", "Lkotlinx/coroutines/flow/Flow;", "Lcom/bhashasetu/app/data/repository/base/BaseRepository$Resource;", "T", "BaseDataSource", "Resource", "app_freeDebug"})
public abstract class BaseRepository {
    
    public BaseRepository() {
        super();
    }
    
    /**
     * Wraps a Flow with loading, success, and error states
     */
    @org.jetbrains.annotations.NotNull()
    protected final <T extends java.lang.Object>kotlinx.coroutines.flow.Flow<com.bhashasetu.app.data.repository.base.BaseRepository.Resource<T>> asResource(@org.jetbrains.annotations.NotNull()
    kotlinx.coroutines.flow.Flow<? extends T> $this$asResource) {
        return null;
    }
    
    /**
     * Interface defining the basic CRUD operations
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0004\bf\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u00020\u0003J\u0016\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00028\u0000H\u00a6@\u00a2\u0006\u0002\u0010\u0007J\u0018\u0010\b\u001a\u0004\u0018\u00018\u00002\u0006\u0010\t\u001a\u00028\u0001H\u00a6@\u00a2\u0006\u0002\u0010\u0007J\u0016\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0006\u001a\u00028\u0000H\u00a6@\u00a2\u0006\u0002\u0010\u0007J\u0014\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u000e0\rH&J\u001d\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00018\u00000\r2\u0006\u0010\t\u001a\u00028\u0001H&\u00a2\u0006\u0002\u0010\u0010J\u0016\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00028\u0000H\u00a6@\u00a2\u0006\u0002\u0010\u0007\u00a8\u0006\u0012"}, d2 = {"Lcom/bhashasetu/app/data/repository/base/BaseRepository$BaseDataSource;", "T", "ID", "", "delete", "", "item", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getById", "id", "insert", "", "observeAll", "Lkotlinx/coroutines/flow/Flow;", "", "observeById", "(Ljava/lang/Object;)Lkotlinx/coroutines/flow/Flow;", "update", "app_freeDebug"})
    public static abstract interface BaseDataSource<T extends java.lang.Object, ID extends java.lang.Object> {
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.Object insert(T item, @org.jetbrains.annotations.NotNull()
        kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.Object update(T item, @org.jetbrains.annotations.NotNull()
        kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.Object delete(T item, @org.jetbrains.annotations.NotNull()
        kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.Object getById(ID id, @org.jetbrains.annotations.NotNull()
        kotlin.coroutines.Continuation<? super T> $completion);
        
        @org.jetbrains.annotations.NotNull()
        public abstract kotlinx.coroutines.flow.Flow<T> observeById(ID id);
        
        @org.jetbrains.annotations.NotNull()
        public abstract kotlinx.coroutines.flow.Flow<java.util.List<T>> observeAll();
    }
    
    /**
     * Resource wrapper class to handle loading, success, and error states
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\u00020\u0002:\u0003\u0004\u0005\u0006B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0003\u0082\u0001\u0003\u0007\b\t\u00a8\u0006\n"}, d2 = {"Lcom/bhashasetu/app/data/repository/base/BaseRepository$Resource;", "T", "", "()V", "Error", "Loading", "Success", "Lcom/bhashasetu/app/data/repository/base/BaseRepository$Resource$Error;", "Lcom/bhashasetu/app/data/repository/base/BaseRepository$Resource$Loading;", "Lcom/bhashasetu/app/data/repository/base/BaseRepository$Resource$Success;", "app_freeDebug"})
    public static abstract class Resource<T extends java.lang.Object> {
        
        private Resource() {
            super();
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\t\u0010\b\u001a\u00020\u0004H\u00c6\u0003J\u0013\u0010\t\u001a\u00020\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u00c6\u0001J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u00d6\u0003J\t\u0010\u000e\u001a\u00020\u000fH\u00d6\u0001J\t\u0010\u0010\u001a\u00020\u0011H\u00d6\u0001R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u0012"}, d2 = {"Lcom/bhashasetu/app/data/repository/base/BaseRepository$Resource$Error;", "Lcom/bhashasetu/app/data/repository/base/BaseRepository$Resource;", "", "exception", "", "(Ljava/lang/Throwable;)V", "getException", "()Ljava/lang/Throwable;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "app_freeDebug"})
        public static final class Error extends com.bhashasetu.app.data.repository.base.BaseRepository.Resource {
            @org.jetbrains.annotations.NotNull()
            private final java.lang.Throwable exception = null;
            
            public Error(@org.jetbrains.annotations.NotNull()
            java.lang.Throwable exception) {
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.lang.Throwable getException() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.lang.Throwable component1() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.bhashasetu.app.data.repository.base.BaseRepository.Resource.Error copy(@org.jetbrains.annotations.NotNull()
            java.lang.Throwable exception) {
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
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0003\u00a8\u0006\u0004"}, d2 = {"Lcom/bhashasetu/app/data/repository/base/BaseRepository$Resource$Loading;", "Lcom/bhashasetu/app/data/repository/base/BaseRepository$Resource;", "", "()V", "app_freeDebug"})
        public static final class Loading extends com.bhashasetu.app.data.repository.base.BaseRepository.Resource {
            @org.jetbrains.annotations.NotNull()
            public static final com.bhashasetu.app.data.repository.base.BaseRepository.Resource.Loading INSTANCE = null;
            
            private Loading() {
            }
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u0000*\u0006\b\u0001\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00028\u0001\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\b\u001a\u00028\u0001H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0006J\u001e\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00010\u00002\b\b\u0002\u0010\u0003\u001a\u00028\u0001H\u00c6\u0001\u00a2\u0006\u0002\u0010\nJ\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u00d6\u0003J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001J\t\u0010\u0011\u001a\u00020\u0012H\u00d6\u0001R\u0013\u0010\u0003\u001a\u00028\u0001\u00a2\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0013"}, d2 = {"Lcom/bhashasetu/app/data/repository/base/BaseRepository$Resource$Success;", "T", "Lcom/bhashasetu/app/data/repository/base/BaseRepository$Resource;", "data", "(Ljava/lang/Object;)V", "getData", "()Ljava/lang/Object;", "Ljava/lang/Object;", "component1", "copy", "(Ljava/lang/Object;)Lcom/bhashasetu/app/data/repository/base/BaseRepository$Resource$Success;", "equals", "", "other", "", "hashCode", "", "toString", "", "app_freeDebug"})
        public static final class Success<T extends java.lang.Object> extends com.bhashasetu.app.data.repository.base.BaseRepository.Resource<T> {
            private final T data = null;
            
            public Success(T data) {
            }
            
            public final T getData() {
                return null;
            }
            
            public final T component1() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.bhashasetu.app.data.repository.base.BaseRepository.Resource.Success<T> copy(T data) {
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
    }
}