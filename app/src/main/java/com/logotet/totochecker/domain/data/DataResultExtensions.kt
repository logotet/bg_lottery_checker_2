package com.logotet.totochecker.domain.data

suspend fun <T, R> DataResult<T, AppError>.mapResult(
    onSuccess: suspend (T) -> R
): DataResult<R, AppError> {
    return if (this is DataResult.Success<T>) {
        DataResult.Success(onSuccess(this.data))
    } else {
        this as DataResult.Error<AppError>
    }
}

suspend fun <T> DataResult<T, AppError>.collectResult(
    onSuccess: suspend (T) -> Unit,
    onFailure: suspend (DataResult.Error<AppError>) -> Unit,
) {
    if (this is DataResult.Success) {
        onSuccess(this.data)
    } else {
        onFailure(this as DataResult.Error<AppError>)
    }
}

suspend fun <T> onMultipleResults(
    vararg results: DataResult<T, AppError>,
    onCombinedSuccess: suspend (List<T>) -> Unit,
    onFailure: suspend (DataResult.Error<AppError>) -> Unit
) {
    for (singleResult in results) {
        if (singleResult is DataResult.Error<*>) {
            onFailure(singleResult as DataResult.Error<AppError>)
            return
        }
    }

    val listOfData = (results as Array<DataResult.Success<T>>)
        .map {
            it.data
        }

    onCombinedSuccess(listOfData)
}