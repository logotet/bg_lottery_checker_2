package com.logotet.totochecker.domain.data

suspend fun <T, R> DataResult<T, DataErrorType>.mapResult(
    onSuccess: suspend (T) -> R
): DataResult<R, DataErrorType> {
    return if (this is DataResult.Success<T>) {
        DataResult.Success(onSuccess(this.data))
    } else {
        this as DataResult.Error<DataErrorType>
    }
}

suspend fun <T> DataResult<T, Nothing>.collectResult(
    onSuccess: suspend (T) -> Unit,
    onFailure: suspend (DataResult.Error<DataErrorType>) -> Unit,
) {
    if (this is DataResult.Success) {
        onSuccess(this.data)
    } else {
        onFailure(this as DataResult.Error<DataErrorType>)
    }
}

suspend fun <T> onMultipleResults(
    vararg results: DataResult<T, DataErrorType>,
    onCombinedSuccess: suspend (List<T>) -> Unit,
    onFailure: suspend (DataResult.Error<DataErrorType>) -> Unit
) {
    for (singleResult in results) {
        if (singleResult is DataResult.Error<*>) {
            onFailure(singleResult as DataResult.Error<DataErrorType>)
            return
        }
    }

    val listOfData = (results as Array<DataResult.Success<T>>)
        .map {
            it.data
        }

    onCombinedSuccess(listOfData)
}