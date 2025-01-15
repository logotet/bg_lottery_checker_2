package com.logotet.totochecker.domain.di

import com.logotet.totochecker.domain.data.WinningNumbersDataSource
import com.logotet.totochecker.domain.interactors.CheckMatchingNumbers
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
   fun provideCheckMatchingNumbers(winningNumbersDataSource: WinningNumbersDataSource): CheckMatchingNumbers =
       CheckMatchingNumbers(winningNumbersDataSource)
}