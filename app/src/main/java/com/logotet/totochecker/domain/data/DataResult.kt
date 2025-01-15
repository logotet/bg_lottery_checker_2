package com.logotet.totochecker.domain.data

interface AppError

sealed interface DataResult<out Data, out E : AppError> {

    data class Success<Data>(val data: Data) : DataResult<Data, Nothing>

    data class Error<E>(val throwable: Throwable?, val errorType: E) : DataResult<Nothing, AppError>
}
