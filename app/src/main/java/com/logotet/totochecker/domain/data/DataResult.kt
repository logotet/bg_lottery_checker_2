package com.logotet.totochecker.domain.data

sealed interface DataResult<out Data> {
    data class Success<Data>(val data: Data) : DataResult<Data>

    sealed class Error(throwable: Throwable?) : DataResult<Nothing>{
        data class Generic(val throwable: Throwable?): Error(throwable)
        data class Timeout(val throwable: Throwable?): Error(throwable)
    }
}