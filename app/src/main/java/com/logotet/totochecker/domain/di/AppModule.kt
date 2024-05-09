package com.logotet.totochecker.domain.di

import com.logotet.totochecker.data.remote.JsoupClient
import com.logotet.totochecker.data.remote.RemoteWinningNumbersDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideJsoupClient(): JsoupClient =
        JsoupClient()

    @Singleton
    @Provides
    fun provideWinningNumbersDataSource(
        jsoupClient: JsoupClient
    ): RemoteWinningNumbersDataSource =
        RemoteWinningNumbersDataSource(jsoupClient)
}