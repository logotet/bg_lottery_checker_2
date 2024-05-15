package com.logotet.totochecker.data.remote

import com.logotet.totochecker.data.remote.LotteryType.FIVE_35_FIRST
import com.logotet.totochecker.data.remote.LotteryType.FIVE_35_SECOND
import com.logotet.totochecker.data.remote.LotteryType.SIX_42
import com.logotet.totochecker.data.remote.LotteryType.SIX_49
import com.logotet.totochecker.domain.data.DataResult
import com.logotet.totochecker.domain.data.DataResult.Success
import com.logotet.totochecker.domain.data.WinningNumbersDataSource
import com.logotet.totochecker.domain.data.mapResult

class RemoteWinningNumbersDataSource(
    private val jsoupClient: JsoupClient
) : WinningNumbersDataSource {

    private var allWinningNumbers: List<String> = emptyList()

    private suspend fun getAllWinningNumbers(): DataResult<List<String>> {
        val result = jsoupClient.getElements()

        return if (allWinningNumbers.isEmpty()) {
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

    override suspend fun getWinningNumbers49(): DataResult<List<String>> =
        getAllWinningNumbers().mapWiningNumbersByType(SIX_49)

    override suspend fun getWinningNumbers42(): DataResult<List<String>> =
        getAllWinningNumbers().mapWiningNumbersByType(SIX_42)

    override suspend fun getWinningNumbers35FirstPick(): DataResult<List<String>> =
        getAllWinningNumbers().mapWiningNumbersByType(FIVE_35_FIRST)

    override suspend fun getWinningNumbers35SecondPick(): DataResult<List<String>> =
        getAllWinningNumbers().mapWiningNumbersByType(FIVE_35_SECOND)

    private suspend fun DataResult<List<String>>.mapWiningNumbersByType(
        type: LotteryType
    ): DataResult<List<String>> =
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

enum class LotteryType {
    SIX_49,
    SIX_42,
    FIVE_35_FIRST,
    FIVE_35_SECOND
}