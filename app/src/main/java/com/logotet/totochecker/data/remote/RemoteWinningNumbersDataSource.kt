package com.logotet.totochecker.data.remote

import com.logotet.totochecker.data.model.GameTypeDto
import com.logotet.totochecker.data.model.NumbersDto
import com.logotet.totochecker.data.model.WinningNumbersDto
import com.logotet.totochecker.data.remote.KtorClient.Companion.ALL_WINNING_NUMBERS
import com.logotet.totochecker.domain.data.AppError
import com.logotet.totochecker.domain.data.DataResult
import com.logotet.totochecker.domain.data.Remote
import com.logotet.totochecker.domain.data.WinningNumbersDataSource
import com.logotet.totochecker.domain.data.handleApiCall
import com.logotet.totochecker.domain.data.onSuccess

class RemoteWinningNumbersDataSource(
    private val ktorClient: KtorClient
) : WinningNumbersDataSource {

    private var allWinningNumbers: Map<GameTypeDto, NumbersDto> = emptyMap()

    override suspend fun getAllWinningNumbers(): DataResult<WinningNumbersDto, AppError> =
        if (allWinningNumbers.isEmpty()) {
            handleApiCall<WinningNumbersDto> {
                ktorClient.get(ALL_WINNING_NUMBERS)
            }.onSuccess { winningNumbersDto ->
                allWinningNumbers = winningNumbersDto
            }
        } else {
            DataResult.Success(allWinningNumbers)
        }

    override suspend fun getWinningNumbers49(): DataResult<NumbersDto, AppError> {
        if (allWinningNumbers.isEmpty()) {
            getAllWinningNumbers()
        }

        return allWinningNumbers[GameTypeDto.SIX_49]?.let {
            DataResult.Success(it)
        } ?: DataResult.Error(Remote.NotFound)
    }
    override suspend fun getWinningNumbers42(): DataResult<NumbersDto, AppError> {
        if (allWinningNumbers.isEmpty()) {
            getAllWinningNumbers()
        }

        return allWinningNumbers[GameTypeDto.SIX_42]?.let {
            DataResult.Success(it)
        } ?: DataResult.Error(Remote.NotFound)
    }

    override suspend fun getWinningNumbers35FirstPick(): DataResult<NumbersDto, AppError> {
        if (allWinningNumbers.isEmpty()) {
            getAllWinningNumbers()
        }

        return allWinningNumbers[GameTypeDto.FIVE_35]?.let {
            DataResult.Success(it)
        } ?: DataResult.Error(Remote.NotFound)
    }

    override suspend fun getWinningNumbers35SecondPick(): DataResult<NumbersDto, AppError> {
        if (allWinningNumbers.isEmpty()) {
            getAllWinningNumbers()
        }

        return allWinningNumbers[GameTypeDto.FIVE_35]?.let {
            DataResult.Success(it)
        } ?: DataResult.Error(Remote.NotFound)
    }
}