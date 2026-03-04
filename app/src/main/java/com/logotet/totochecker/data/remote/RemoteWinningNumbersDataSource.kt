package com.logotet.totochecker.data.remote

import com.logotet.totochecker.data.model.NumbersDto
import com.logotet.totochecker.domain.data.AppError
import com.logotet.totochecker.domain.data.DataResult
import com.logotet.totochecker.domain.data.DataResult.Success
import com.logotet.totochecker.domain.data.WinningNumbersDataSource
import com.logotet.totochecker.domain.data.handleApiCall
import com.logotet.totochecker.domain.data.onMultipleResults
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class RemoteWinningNumbersDataSource(
    private val ktorClient: KtorClient
) : WinningNumbersDataSource {

    private var allWinningNumbers: List<NumbersDto> = emptyList()

    override suspend fun getAllWinningNumbers(): DataResult<List<NumbersDto>, AppError> {
        return if (allWinningNumbers.isEmpty()) {
            coroutineScope {
                val numbers49Deferred = async { getWinningNumbers49() }
                val numbers42Deferred = async { getWinningNumbers42() }
                val numbers35FirstPickDeferred = async { getWinningNumbers35FirstPick() }
                val numbers35SecondPickDeferred = async { getWinningNumbers35SecondPick() }

                val numbers49 = numbers49Deferred.await()
                val numbers42 = numbers42Deferred.await()
                val numbers35FirstPick = numbers35FirstPickDeferred.await()
                val numbers35SecondPick = numbers35SecondPickDeferred.await()

                onMultipleResults(
                    numbers49,
                    numbers42,
                    numbers35FirstPick,
                    numbers35SecondPick,
                    onCombinedSuccess = { arrayOfNumbers ->
                        allWinningNumbers = arrayOfNumbers
                    },
                    onFailure = {
                            ////
                    }
                )
            }
        } else {
            Success(allWinningNumbers)
        }
    }

    override suspend fun getWinningNumbers49(): DataResult<NumbersDto, AppError> =
        handleApiCall<NumbersDto> {
            ktorClient.get("6-49")
        }

    override suspend fun getWinningNumbers42(): DataResult<NumbersDto, AppError> =
        handleApiCall<NumbersDto> {
            ktorClient.get("6-42")
        }

    override suspend fun getWinningNumbers35FirstPick(): DataResult<NumbersDto, AppError> =
        handleApiCall<NumbersDto> {
            ktorClient.get("6-42")
        }

    override suspend fun getWinningNumbers35SecondPick(): DataResult<NumbersDto, AppError> =
        handleApiCall<NumbersDto> {
            ktorClient.get("6-42")
        }
}