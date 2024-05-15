package com.logotet.totochecker.domain.data

suspend fun <T, R> DataResult<T>.mapResult(
    onSuccess: suspend (T) -> R,
): DataResult<R> {
    return if (this is DataResult.Success) {
        DataResult.Success(onSuccess(this.data))
    } else {
        DataResult.Error.Generic(null)
    }
}

suspend fun <T> DataResult<T>.onResult(
    onSuccess: suspend (T) -> Unit,
    onFailure: suspend (DataResult.Error) -> Unit,
) {
    if (this is DataResult.Success) {
        onSuccess(this.data)
    } else {
        onFailure(DataResult.Error.Generic(null))
    }
}

suspend fun <T> onMultipleResults(
    vararg results: DataResult<T>,
    onCombinedSuccess: suspend (Array<out DataResult<T>>) -> Unit,
    onFailure: suspend (DataResult.Error) -> Unit
) {
    for (i in results) {
        if (i is DataResult.Error) {
            onFailure(DataResult.Error.Generic(null))
            break
        }
    }

    onCombinedSuccess(results)
}