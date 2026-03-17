package com.logotet.totochecker.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType

class  KtorClient(
    val httpClient: HttpClient
) {
    suspend fun get(
        path: String = EMPTY_PATH,
        vararg parameters: Pair<String, String>
    ): HttpResponse {
        val url = BASE_URL.plus(path)

        return httpClient.get(url) {
            parameters.forEach { (key, value) ->
                parameter(key, value)
            }
        }
    }

    suspend inline fun <reified T> post(
        path: String = EMPTY_PATH,
        vararg parameters: Pair<String, String>,
        body: T
    ): HttpResponse {
        val url = BASE_URL.plus(path)

        return httpClient.post(url) {
            parameters.forEach { (key, value) ->
                parameter(key, value)
            }

            contentType(ContentType.Application.Json)

            setBody(body)
        }
    }

    companion object {
        const val BASE_URL = "https://totofastapi2.vercel.app/"
        const val ALL_WINNING_NUMBERS = "winning-numbers"
        const val EMPTY_PATH = ""
    }
}