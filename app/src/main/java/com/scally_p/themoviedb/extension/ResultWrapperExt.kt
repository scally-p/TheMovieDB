package com.scally_p.themoviedb.extension

import com.scally_p.themoviedb.data.network.NoInternetException
import com.scally_p.themoviedb.data.network.ResultWrapper

fun ResultWrapper.Failure.exception(): Exception {
    return Exception("$code:${this.exception?.message}")
}

fun <T> ResultWrapper<T>.exceptionOrNull(): Exception? {
    return when {
        this.isNetworkFailure -> NoInternetException()
        this.isFailure -> (this as ResultWrapper.Failure).exception()
        else -> null
    }
}

fun <T> ResultWrapper<T>.toResult(): Result<T> {
    return if (this.isSuccess) {
        Result.success(this.getOrNull()!!)
    } else {
        Result.failure(this.exceptionOrNull()!!)
    }
}

fun <T, T1> ResultWrapper<T>.toResult(action: (T) -> T1): Result<T1> {
    return when {
        this.isSuccess -> Result.success<T1>(action.invoke(this.getOrNull()!!))
        this.isNetworkFailure -> Result.failure<T1>(NoInternetException())
        else -> Result.failure<T1>(this.exceptionOrNull()!!)
    }
}