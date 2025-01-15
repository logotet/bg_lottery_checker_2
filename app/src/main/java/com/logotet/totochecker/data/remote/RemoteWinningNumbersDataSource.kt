package com.logotet.totochecker.data.remote

import com.logotet.totochecker.domain.data.AppError
import com.logotet.totochecker.domain.data.DataResult
import com.logotet.totochecker.domain.data.DataResult.Success
import com.logotet.totochecker.domain.data.LotteryType
import com.logotet.totochecker.domain.data.LotteryType.FIVE_35_FIRST
import com.logotet.totochecker.domain.data.LotteryType.FIVE_35_SECOND
import com.logotet.totochecker.domain.data.LotteryType.SIX_42
import com.logotet.totochecker.domain.data.LotteryType.SIX_49
import com.logotet.totochecker.domain.data.WinningNumbersDataSource
import com.logotet.totochecker.domain.data.mapResult

class RemoteWinningNumbersDataSource(
    private val jsoupClient: JsoupClient
) : WinningNumbersDataSource {

    private var allWinningNumbers: List<String> = emptyList()

    suspend fun getAllWinningNumbers(): DataResult<List<String>, AppError> {

        return if (allWinningNumbers.isEmpty()) {
            val result = jsoupClient.getElements()

            result.mapResult { elements ->
                allWinningNumbers = elements.map { element ->
                    element.text()
                }

                allWinningNumbers
            }
        } else {
            Success(allWinningNumbers)
        }
    }

    override suspend fun getWinningNumbers49(): DataResult<List<String>, AppError> =
        getAllWinningNumbers().mapWiningNumbersByType(SIX_49)

    override suspend fun getWinningNumbers42(): DataResult<List<String>, AppError> =
        getAllWinningNumbers().mapWiningNumbersByType(SIX_42)

    override suspend fun getWinningNumbers35FirstPick(): DataResult<List<String>, AppError> =
        getAllWinningNumbers().mapWiningNumbersByType(FIVE_35_FIRST)

    override suspend fun getWinningNumbers35SecondPick(): DataResult<List<String>, AppError> =
        getAllWinningNumbers().mapWiningNumbersByType(FIVE_35_SECOND)

    private suspend fun DataResult<List<String>, AppError>.mapWiningNumbersByType(
        type: LotteryType
    ): DataResult<List<String>, AppError> =
        mapResult { list ->
            val strings = when (type) {
                SIX_49 -> list.getSubset(range49)
                SIX_42 -> list.getSubset(range42)
                FIVE_35_FIRST -> list.getSubset(range35First)
                FIVE_35_SECOND -> list.getSubset(range35Second)
            }
            strings
        }

    private fun List<String>.getSubset(range: IntRange) =
        subList(range.first, range.last)

    companion object {
        private val range49 = 0..6
        private val range35First = 6..11
        private val range35Second = 11..16
        private val range42 = 16..22
    }
}