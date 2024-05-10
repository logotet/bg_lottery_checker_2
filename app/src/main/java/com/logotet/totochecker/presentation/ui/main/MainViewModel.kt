package com.logotet.totochecker.presentation.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.logotet.totochecker.data.remote.RemoteWinningNumbersDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    remoteWinningNumbersDataSource: RemoteWinningNumbersDataSource
) : ViewModel() {

    var screenState by mutableStateOf(MainUIState(isDataLoading = true))

    init {
        loadAllWinningCombinations(remoteWinningNumbersDataSource)
    }

    private fun loadAllWinningCombinations(remoteWinningNumbersDataSource: RemoteWinningNumbersDataSource) {
        viewModelScope.launch {
            remoteWinningNumbersDataSource.apply {
                val winningNumbers49 = getWinningNumbers49()
                val winningNumbers42 = getWinningNumbers42()
                val winningNumbers35FirstPick = getWinningNumbers35FirstPick()
                val winningNumbers35SecondPick = getWinningNumbers35SecondPick()

                screenState = MainUIState(
                    numbers49 = winningNumbers49,
                    numbers42 = winningNumbers42,
                    numbers35FirstPick = winningNumbers35FirstPick,
                    numbers35SecondPick = winningNumbers35SecondPick,
                    isDataLoading = false
                )
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
    val isDataLoading: Boolean = false
)

sealed class Action() {
    data object OnNumbersCheck : Action()
}