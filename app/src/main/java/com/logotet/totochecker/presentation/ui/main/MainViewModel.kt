package com.logotet.totochecker.presentation.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.logotet.totochecker.data.model.GameTypeDto
import com.logotet.totochecker.domain.data.WinningNumbersDataSource
import com.logotet.totochecker.domain.data.collectResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val winningNumbersDataSource: WinningNumbersDataSource
) : ViewModel() {

    var screenState by mutableStateOf(MainUIState())

    init {
        loadAllWinningCombinations()
    }

    private fun loadAllWinningCombinations() {
        viewModelScope.launch {
            winningNumbersDataSource.getAllWinningNumbers().collectResult(
                onSuccess = { allWinningNumbers->
                    screenState = MainUIState(
                        numbers49 = allWinningNumbers[GameTypeDto.SIX_49]?.numbers ?: emptyList(),
                        numbers42 = allWinningNumbers[GameTypeDto.SIX_42] ?.numbers?: emptyList(),
                        numbers35FirstPick = allWinningNumbers[GameTypeDto.FIVE_35]?.numbers ?: emptyList(),
                        numbers35SecondPick = allWinningNumbers[GameTypeDto.FIVE_35]?.numbers ?: emptyList(),
                        dataState = DataState.Success
                    )
                },
                onFailure = {
                    screenState = MainUIState(dataState = DataState.ErrorPrompt)
                }
            )
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
    data object Success : DataState()
    data object Loading : DataState()
    data object ErrorPrompt : DataState()
    data object ErrorFinal : DataState()
}

sealed class Action {
    data object OnNumbersCheck : Action()
    data object OnDismissDialog : Action()
}