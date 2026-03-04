package com.logotet.totochecker.domain.di

import android.util.Log
import com.logotet.totochecker.data.remote.KtorClient
import com.logotet.totochecker.data.remote.RemoteWinningNumbersDataSource
import com.logotet.totochecker.domain.data.WinningNumbersDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

private const val HTTP_LOGGER_TAG = "KTOR"
private const val TIMEOUT_MILLIS = 30000L

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideWinningNumbersDataSource(
        ktorClient: KtorClient
    ): WinningNumbersDataSource =
        RemoteWinningNumbersDataSource(ktorClient)

    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient =
        HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                    }
                )
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.i(HTTP_LOGGER_TAG, message )
                    }
                }
                level = LogLevel.ALL
            }
            install(HttpTimeout) {
                connectTimeoutMillis = TIMEOUT_MILLIS
                socketTimeoutMillis = TIMEOUT_MILLIS
                requestTimeoutMillis = TIMEOUT_MILLIS
            }

            expectSuccess = true
        }

    @Singleton
    @Provides
    fun provideKtorClient(
        httpClient: HttpClient
    ): KtorClient =
        KtorClient(httpClient)
}