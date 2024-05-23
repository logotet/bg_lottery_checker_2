package com.logotet.totochecker.domain.data


interface DataErrorType

sealed interface DataResult<out Data, out E : DataErrorType> {

    data class Success<Data>(val data: Data) : DataResult<Data, Nothing>

    data class Error<E>(val throwable: Throwable?, val e: E) : DataResult<Nothing, DataErrorType>
}
