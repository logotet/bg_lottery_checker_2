package com.logotet.totochecker.presentation.ui.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.logotet.totochecker.R
import com.logotet.totochecker.presentation.ui.composables.BallList

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    var state = viewModel.screenState

    MainScreenContent(state = state)
}

@Composable
fun MainScreenContent(state: MainUIState, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (!state.isDataLoading) {
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
        } else {
            CircularProgressIndicator()
        }
    }
}