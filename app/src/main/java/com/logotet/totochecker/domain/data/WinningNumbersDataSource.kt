package com.logotet.totochecker.domain.data

interface WinningNumbersDataSource {
    suspend fun getWinningNumbers49(): DataResult<List<String>, AppError>

    suspend fun getWinningNumbers42(): DataResult<List<String>, AppError>

    suspend fun getWinningNumbers35FirstPick(): DataResult<List<String>, AppError>

    suspend fun getWinningNumbers35SecondPick(): DataResult<List<String>, AppError>
}