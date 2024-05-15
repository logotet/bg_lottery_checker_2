package com.logotet.totochecker.presentation.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.logotet.totochecker.R
import com.logotet.totochecker.presentation.ui.composables.AppAlertDialog
import com.logotet.totochecker.presentation.ui.composables.BallList

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    backgroundColor: Color = Color.White
) {
    val state = viewModel.screenState

    MainScreenContent(
        state = state,
        onAction = { action -> viewModel.onAction(action) },
        backgroundColor = backgroundColor
    )
}

@Composable
fun MainScreenContent(
    state: MainUIState, modifier: Modifier = Modifier,
    onAction: (Action) -> Unit,
    backgroundColor: Color
) {
    val scrollableState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
            .verticalScroll(scrollableState),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (state.dataState) {
            is DataState.Success -> {
                BallList(
                    numbers = state.numbers49,
                    listHeader = R.string.header_49
                )

                BallList(
                    numbers = state.numbers42,
                    listHeader = R.string.header_42
                )

                BallList(
                    numbers = state.numbers35FirstPick,
                    listHeader = R.string.header_35,
                    subHeader = R.string.header_pick_first
                )

                BallList(
                    numbers = state.numbers35SecondPick,
                    listHeader = R.string.header_35,
                    subHeader = R.string.header_pick_second
                )
            }

            is DataState.Loading -> {
                CircularProgressIndicator()
            }

            is DataState.ErrorPrompt -> {
                AppAlertDialog(
                    text = { Text(text = stringResource(R.string.dialog_general_error)) },
                    confirmButtonTextId = R.string.dialog_confirm_button,
                    onConfirm = { onAction(Action.OnDismissDialog) },
                    onDismiss = { onAction(Action.OnDismissDialog) })
            }

            is DataState.ErrorFinal -> {
                Text(text = stringResource(R.string.data_error_message))
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun MainScreenPreview() {
    MainScreenContent(
        state = MainUIState(
            numbers49 = listOf("1", "2", "3", "4", "5", "6"),
            numbers42 = listOf("1", "2", "3", "4", "5", "6"),
            numbers35FirstPick = listOf("1", "2", "3", "4", "5"),
            numbers35SecondPick = listOf("1", "2", "3", "4", "5"),
            isDataLoading = false
        ),
        backgroundColor = Color.White,
        onAction = {}
    )
}