package com.logotet.totochecker.domain.data

import com.logotet.totochecker.domain.data.NetworkAppError.GENERIC
import com.logotet.totochecker.domain.data.NetworkAppError.UNAVAILABLE
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withTimeout
import org.jsoup.HttpStatusException
import java.net.UnknownHostException

private const val TIMEOUT: Long = 20000

suspend fun <T> handleApiCall(
    invoke: suspend () -> T
): DataResult<T, AppError> {
    return try {
        withTimeout(TIMEOUT) {
            val result = invoke()
            DataResult.Success(result)
        }
    } catch (throwable: Throwable) {
        parseError(throwable)
    }
}

private fun parseError(throwable: Throwable) =
    when (throwable) {
    is TimeoutCancellationException -> DataResult.Error(throwable, TIMEOUT)
    is HttpStatusException -> DataResult.Error(throwable, UNAVAILABLE)
    is UnknownHostException -> DataResult.Error(throwable, NetworkAppError.NOT_FOUND)
    else -> DataResult.Error(null, GENERIC)
}

enum class NetworkAppError : AppError {
    GENERIC,
    TIMEOUT,
    UNAUTHORIZED,
    NOT_FOUND,
    INTERNAL_SERVER_ERROR,
    BAD_REQUEST,
    UNPROCESSABLE_ENTITY,
    UNSUPPORTED_MEDIA_TYPE,
    FORBIDDEN,
    UNAVAILABLE,
    UNKNOWN
}