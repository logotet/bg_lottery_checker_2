package com.logotet.totochecker.data.remote

import com.logotet.totochecker.data.remote.LotteryType.FIVE_35_FIRST
import com.logotet.totochecker.data.remote.LotteryType.FIVE_35_SECOND
import com.logotet.totochecker.data.remote.LotteryType.SIX_42
import com.logotet.totochecker.data.remote.LotteryType.SIX_49
import com.logotet.totochecker.domain.data.WinningNumbersDataSource

class RemoteWinningNumbersDataSource(
    private val jsoupClient: JsoupClient
) : WinningNumbersDataSource {

    private suspend fun getAllWinningNumbers(): List<String> =
        jsoupClient.getElements()?.map { element ->
            element.text()
        } ?: emptyList()

    override suspend fun getWinningNumbers49(): List<String> =
        getAllWinningNumbers().mapWiningNumbersByType(SIX_49)

    override suspend fun getWinningNumbers42(): List<String> =
        getAllWinningNumbers().mapWiningNumbersByType(SIX_42)

    override suspend fun getWinningNumbers35FirstPick(): List<String> =
        getAllWinningNumbers().mapWiningNumbersByType(FIVE_35_FIRST)

    override suspend fun getWinningNumbers35SecondPick(): List<String> =
        getAllWinningNumbers().mapWiningNumbersByType(FIVE_35_SECOND)

    private fun List<String>.mapWiningNumbersByType(
        type: LotteryType
    ): List<String> =
        when (type) {
            SIX_49 -> getSubset(range49)
            SIX_42 -> getSubset(range42)
            FIVE_35_FIRST -> getSubset(range35First)
            FIVE_35_SECOND -> getSubset(range35Second)
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