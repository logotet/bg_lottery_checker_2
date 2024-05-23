package com.logotet.totochecker.presentation.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.logotet.totochecker.data.remote.RemoteWinningNumbersDataSource
import com.logotet.totochecker.domain.data.onMultipleResults
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    remoteWinningNumbersDataSource: RemoteWinningNumbersDataSource
) : ViewModel() {

    var screenState by mutableStateOf(MainUIState())

    init {
        loadAllWinningCombinations(remoteWinningNumbersDataSource)
    }

    private fun loadAllWinningCombinations(remoteWinningNumbersDataSource: RemoteWinningNumbersDataSource) {
        viewModelScope.launch {
            remoteWinningNumbersDataSource.apply {
                val winningNumbers49Result = getWinningNumbers49()
                val winningNumbers42Result = getWinningNumbers42()
                val winningNumbers35FirstPickResult = getWinningNumbers35FirstPick()
                val winningNumbers35SecondPickResult = getWinningNumbers35SecondPick()

                onMultipleResults(
                    winningNumbers49Result,
                    winningNumbers42Result,
                    winningNumbers35FirstPickResult,
                    winningNumbers35SecondPickResult,
                    onCombinedSuccess = { arrayOfNumbers ->
                        screenState = MainUIState(
                            numbers49 = arrayOfNumbers[0],
                            numbers42 = arrayOfNumbers[2],
                            numbers35FirstPick = arrayOfNumbers[2],
                            numbers35SecondPick = arrayOfNumbers[3],
                            dataState = DataState.Success
                        )
                    },
                    onFailure = {
                        screenState = MainUIState(dataState = DataState.ErrorPrompt(it.throwable?.message))
                    }
                )
            }
        }
    }

    fun onAction(action: Action) {
        when (action) {
            is Action.OnDismissDialog -> {
                screenState = screenState.copy(
                    dataState = DataState.ErrorFinal
                )
            }

            is Action.OnNumbersCheck -> Unit
        }
    }
}

data class MainUIState(
    val numbers49: List<String> = emptyList(),
    val numbers42: List<String> = emptyList(),
    val numbers35FirstPick: List<String> = emptyList(),
    val numbers35SecondPick: List<String> = emptyList(),
    val dataState: DataState = DataState.Loading
)

sealed class DataState {
    data object Success: DataState()
    data object Loading: DataState()
    data object ErrorPrompt: DataState()
    data object ErrorFinal: DataState()
}

sealed class Action {
    data object OnNumbersCheck : Action()
    data object OnDismissDialog : Action()
}