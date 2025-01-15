package com.logotet.totochecker.domain.di

import com.logotet.totochecker.data.remote.JsoupClient
import com.logotet.totochecker.data.remote.RemoteWinningNumbersDataSource
import com.logotet.totochecker.domain.data.WinningNumbersDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideJsoupClient(): JsoupClient =
        JsoupClient()

    @Singleton
    @Provides
    fun provideWinningNumbersDataSource(
        jsoupClient: JsoupClient
    ): WinningNumbersDataSource =
        RemoteWinningNumbersDataSource(jsoupClient)
}