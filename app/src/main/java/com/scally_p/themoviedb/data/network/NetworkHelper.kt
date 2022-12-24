package com.scally_p.themoviedb.data.network

import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException

object NetworkHelper {
    suspend fun <T> apiRequest(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        apiRequest: suspend () -> T,
    ): ResultWrapper<T> {
        return withContext(dispatcher) {
            try {
                ResultWrapper.Success(apiRequest.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> ResultWrapper.NetworkFailure
                    is HttpException -> {
                        val code = throwable.code()
                        ResultWrapper.Failure(code, throwable as Exception)
                    }
                    else -> {
                        println(throwable)
                        ResultWrapper.Failure(null, throwable as Exception)
                    }
                }
            }
        }
    }
}