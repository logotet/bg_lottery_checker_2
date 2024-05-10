package com.logotet.totochecker.domain.data

interface WinningNumbersDataSource {
    suspend fun getWinningNumbers49(): List<String>

    suspend fun getWinningNumbers42(): List<String>

    suspend fun getWinningNumbers35FirstPick(): List<String>

    suspend fun getWinningNumbers35SecondPick(): List<String>
}