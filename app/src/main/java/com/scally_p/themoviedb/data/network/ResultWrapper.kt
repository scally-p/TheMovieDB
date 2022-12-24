package com.scally_p.themoviedb.data.network

sealed class ResultWrapper<out T> {
    data class Success<out T>(val data: T) : ResultWrapper<T>()
    data class Failure(val code: Int? = null, val exception: Exception? = null) : ResultWrapper<Nothing>()
    object NetworkFailure : ResultWrapper<Nothing>()

    val isSuccess: Boolean
        get() {
            return (this !is Failure && this !is NetworkFailure)
        }

    val isFailure: Boolean
        get() {
            return this is Failure
        }

    val isNetworkFailure: Boolean
        get() {
            return this is NetworkFailure
        }

    fun getOrNull(): T? {
        return try {
            (this as Success<T>).data
        } catch (e: Exception) {
            null
        }
    }
}