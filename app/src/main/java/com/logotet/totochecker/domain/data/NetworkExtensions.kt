package com.logotet.totochecker.domain.data

import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.JsonConvertException
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException

suspend inline fun <reified Data> handleApiCall(
    crossinline execute: suspend () -> HttpResponse
): DataResult<Data, Remote> {
    val result = try {
        val response = execute()
        val body = response.body<Data>()

        DataResult.Success(body)
    } catch (throwable: Throwable) {
        parseError(throwable)
    }

    return result
}

suspend fun parseError(throwable: Throwable?): DataResult<Nothing, Remote> =
    DataResult.Error(
        when (throwable) {
            // for 3xx responses
            is RedirectResponseException -> Remote.Redirect
            // for 4xx responses
            is ClientRequestException -> {
                when (throwable.response.status) {
                    HttpStatusCode.Unauthorized -> Remote.Unauthorized
                    HttpStatusCode.Forbidden -> Remote.Forbidden
                    HttpStatusCode.NotFound -> Remote.NotFound
                    HttpStatusCode.MethodNotAllowed -> Remote.MethodNotAllowed
                    else -> Remote.BadRequest
                }
            }
            // for 5xx responses
            is ServerResponseException -> Remote.Server
            // for deserialization errors
            is JsonConvertException, is SerializationException -> Remote.Serialization
            // for timeout errors
            is HttpRequestTimeoutException -> Remote.Timeout
            // for any other errors
            else -> {
                currentCoroutineContext().ensureActive()
                Remote.Unknown
            }
        }
    )

sealed interface Remote : AppError {
    data object Redirect : Remote
    data object BadRequest : Remote
    data object Server : Remote
    data object Unauthorized : Remote
    data object Forbidden : Remote
    data object NotFound : Remote
    data object MethodNotAllowed : Remote
    data object Timeout : Remote
    data object Serialization : Remote
    data object Unknown : Remote
}