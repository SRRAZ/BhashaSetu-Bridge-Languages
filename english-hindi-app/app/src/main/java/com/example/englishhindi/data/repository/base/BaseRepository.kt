package com.example.englishhindi.data.repository.base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

/**
 * Base Repository class with common functionality for all repositories
 */
abstract class BaseRepository {

    /**
     * Resource wrapper class to handle loading, success, and error states
     */
    sealed class Resource<out T> {
        data class Success<out T>(val data: T) : Resource<T>()
        data class Error(val exception: Throwable) : Resource<Nothing>()
        object Loading : Resource<Nothing>()
    }

    /**
     * Wraps a Flow with loading, success, and error states
     */
    protected fun <T> Flow<T>.asResource(): Flow<Resource<T>> {
        return this
            .map { Resource.Success(it) as Resource<T> }
            .onStart { emit(Resource.Loading) }
            .catch { emit(Resource.Error(it)) }
    }

    /**
     * Interface defining the basic CRUD operations
     */
    interface BaseDataSource<T, ID> {
        suspend fun insert(item: T): Long
        suspend fun update(item: T)
        suspend fun delete(item: T)
        suspend fun getById(id: ID): T?
        fun observeById(id: ID): Flow<T?>
        fun observeAll(): Flow<List<T>>
    }
}