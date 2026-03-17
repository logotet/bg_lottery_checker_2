package com.logotet.totochecker.domain.data

import com.logotet.totochecker.data.model.NumbersDto
import com.logotet.totochecker.data.model.WinningNumbersDto

interface WinningNumbersDataSource {
    suspend fun getAllWinningNumbers(): DataResult<WinningNumbersDto, AppError>

    suspend fun getWinningNumbers49(): DataResult<NumbersDto, AppError>

    suspend fun getWinningNumbers42(): DataResult<NumbersDto, AppError>

    suspend fun getWinningNumbers35FirstPick(): DataResult<NumbersDto, AppError>

    suspend fun getWinningNumbers35SecondPick(): DataResult<NumbersDto, AppError>
}