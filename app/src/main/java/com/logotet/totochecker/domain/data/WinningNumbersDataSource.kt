package com.logotet.totochecker.domain.data

interface WinningNumbersDataSource {
    suspend fun getWinningNumbers49(): List<String>

    suspend fun getWinningNumbers42(): List<String>

    suspend fun getWinningNumbers35First(): List<String>

    suspend fun getWinningNumbers35Second(): List<String>
}