package com.logotet.totochecker.presentation.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.logotet.totochecker.data.remote.RemoteWinningNumbersDataSource
import com.logotet.totochecker.domain.data.DataResult
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

                if (
                    winningNumbers49Result is DataResult.Success &&
                    winningNumbers42Result is DataResult.Success &&
                    winningNumbers35FirstPickResult is DataResult.Success &&
                    winningNumbers35SecondPickResult is DataResult.Success
                ) {
                    screenState = MainUIState(
                        numbers49 = winningNumbers49Result.data,
                        numbers42 = winningNumbers42Result.data,
                        numbers35FirstPick = winningNumbers35FirstPickResult.data,
                        numbers35SecondPick = winningNumbers35SecondPickResult.data,
                        isDataLoading = false
                    )
                } else {
                    screenState = MainUIState(dataState = DataState.ErrorPrompt)
                }
            }
        }
    }

    fun onAction(action: Action) {
        when (action) {
            is Action.OnNumbersCheck -> Unit
            else -> Unit
        }
    }
}

data class MainUIState(
    val numbers49: List<String> = emptyList(),
    val numbers42: List<String> = emptyList(),
    val numbers35FirstPick: List<String> = emptyList(),
    val numbers35SecondPick: List<String> = emptyList(),
    val isDataLoading: Boolean = false,
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
}