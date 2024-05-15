package com.logotet.totochecker.domain.data

import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withTimeout

private const val TIMEOUT: Long = 20000

suspend fun <T> handleApiCall(
    invoke: suspend () -> T
): DataResult<T> {
    return try {
        withTimeout(TIMEOUT) {
            val result = invoke()
            DataResult.Success(result)
        }
    } catch (throwable: Throwable) {
        parseError(throwable)
    }
}

private fun parseError(throwable: Throwable) = if (throwable is TimeoutCancellationException) {
    DataResult.Error.Timeout(throwable)
} else {
    DataResult.Error.Generic(throwable)
}