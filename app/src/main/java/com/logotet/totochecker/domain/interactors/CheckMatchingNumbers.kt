package com.logotet.totochecker.domain.interactors

import com.logotet.totochecker.data.model.NumbersDto
import com.logotet.totochecker.domain.data.AppError
import com.logotet.totochecker.domain.data.DataResult
import com.logotet.totochecker.domain.data.LotteryType
import com.logotet.totochecker.domain.data.LotteryType.FIVE_35_FIRST
import com.logotet.totochecker.domain.data.LotteryType.SIX_42
import com.logotet.totochecker.domain.data.LotteryType.SIX_49
import com.logotet.totochecker.domain.data.WinningNumbersDataSource
import com.logotet.totochecker.domain.data.mapResult

class CheckMatchingNumbers(
    private val remoteWinningNumbersDataSource: WinningNumbersDataSource
) {
    suspend operator fun invoke(
        type: LotteryType,
        selectedNumbers: List<String>
    ): DataResult<List<String>, AppError> {
        val winningNumbersResult = type.getWinningNumbers()

        return winningNumbersResult.mapResult { winningNumbers ->
            val matchingNumbers = getMatchingNumbers(winningNumbers, selectedNumbers)

            matchingNumbers
        }
    }

    private fun getMatchingNumbers(
        winningNumbers: NumbersDto,
        selectedNumbers: List<String>
    ): List<String> = winningNumbers.numbers.filter { number ->
        selectedNumbers.contains(number)
    }

    private suspend fun LotteryType.getWinningNumbers(): DataResult<NumbersDto, AppError> =
        when (this) {
            SIX_49 -> remoteWinningNumbersDataSource.getWinningNumbers49()
            SIX_42 -> remoteWinningNumbersDataSource.getWinningNumbers42()
            FIVE_35_FIRST -> remoteWinningNumbersDataSource.getWinningNumbers35FirstPick()
        }
}

enum class ValidationError: AppError {
    MISSING_NUMBER,
    INVALID_NUMBER,
    DUPLICATE_NUMBER
}