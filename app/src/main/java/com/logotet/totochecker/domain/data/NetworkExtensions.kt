package com.logotet.totochecker.domain.data

suspend fun <T> handleApiCall(
    invoke: suspend () -> T
): DataResult<T> {
    return try {
        val result = invoke()
        DataResult.Success(result)
    } catch (e: Exception) {
        DataResult.Error.Generic(e)
    }
}