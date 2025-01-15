package com.logotet.totochecker.presentation.ui.check

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.logotet.totochecker.domain.data.AppError
import com.logotet.totochecker.domain.data.LotteryType
import com.logotet.totochecker.domain.data.LotteryType.SIX_49
import com.logotet.totochecker.domain.data.collectResult
import com.logotet.totochecker.domain.interactors.CheckMatchingNumbers
import com.logotet.totochecker.presentation.ui.check.Event.OnError
import com.logotet.totochecker.presentation.ui.check.Event.OnMatchingNumbersSelected
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NumbersCheckerViewModel @Inject constructor(
    private val checkMatchingNumbers: CheckMatchingNumbers
) : ViewModel() {

    var state by mutableStateOf(CheckScreenState.defaultState)

    private var _events = Channel<Event>()
    val events = _events.receiveAsFlow()

    init {
        loadNumberListByType(SIX_49)
    }

    fun onAction(action: Action) {
        when (action) {
            is Action.OnMenuItemSelected -> loadNumberListByType(action.lotteryType)
            is Action.OnNumberSelected -> state.addNumber(action.number)
            is Action.OnNumberUnselected -> state.removeNumber(action.number)
            is Action.OnCheckNumbers -> checkNumbers()
            is Action.DismissDialog -> viewModelScope.launch{
                _events.send(Event.DismissEvent)
            }
        }
    }

    private fun checkNumbers() {
        viewModelScope.launch {
            checkMatchingNumbers(
                state.lotteryType,
                state.selectedNumbers
            ).collectResult(
                onSuccess = {
                    _events.send(OnMatchingNumbersSelected(it))
                },
                onFailure = {
                    _events.send(OnError(it.errorType))
                }
            )
        }
    }

    private fun loadNumberListByType(lotteryType: LotteryType) {
        state = state.copy(
            lotteryType = lotteryType,
            numberSequence = getListOfConsecutiveNumbers(lotteryType.maxNumber),
            selectedNumbers = mutableListOf()
        )
    }

    private fun CheckScreenState.addNumber(
        numberToAdd: String
    ) {
        val maxSize: Int = state.lotteryType.maxNumber

        if (selectedNumbers.size < maxSize) {
            selectedNumbers.add(numberToAdd)
        }
    }

    private fun CheckScreenState.removeNumber(
        numberToRemove: String
    ) {
        val minSize = 0

        if (selectedNumbers.size > minSize) {
            selectedNumbers.remove(numberToRemove)
        }
    }
}

data class CheckScreenState(
    val lotteryType: LotteryType,
    val numberSequence: List<String>,
    val selectedNumbers: MutableList<String>,
) {
    companion object {
        val defaultState = CheckScreenState(
            lotteryType = SIX_49,
            numberSequence = getListOfConsecutiveNumbers(SIX_49.maxNumber),
            selectedNumbers = mutableListOf()
        )
    }
}

sealed interface Action {
    data class OnMenuItemSelected(val lotteryType: LotteryType) : Action
    data class OnNumberSelected(val number: String) : Action
    data class OnNumberUnselected(val number: String) : Action
    data object OnCheckNumbers : Action
    data object DismissDialog : Action
}

sealed interface Event {
    data object DismissEvent : Event
    data class OnError(val errorType: AppError) : Event
    data class OnMatchingNumbersSelected(val matchingNumbers: List<String>) : Event
}

fun getListOfConsecutiveNumbers(endNumber: Int): List<String> =
    (1..endNumber).toList().map { number ->
        number.toString()
    }