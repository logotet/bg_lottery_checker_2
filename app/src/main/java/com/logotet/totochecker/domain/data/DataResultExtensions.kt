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
): DataResult<List<T>, AppError> {
    val mutableList = mutableListOf<T>()

    for (singleResult in results) {
        if (singleResult is DataResult.Error<*>) {
            onFailure(singleResult as DataResult.Error<AppError>)
            return DataResult.Error(singleResult.errorType)
        } else {
            (singleResult as? DataResult.Success<T>)?.data?.let {
                mutableList.add(it)
            }
        }
    }

    onCombinedSuccess(mutableList)

    return DataResult.Success(mutableList)
}

suspend fun <T> DataResult<T, AppError>.onSuccess(
    onSuccess: suspend (T) -> Unit
): DataResult<T, AppError> {
    if (this is DataResult.Success) {
        onSuccess(this.data)
    }

    return this
}
