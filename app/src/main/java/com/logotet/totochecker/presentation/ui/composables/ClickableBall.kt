package com.logotet.totochecker.presentation.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun ClickableBall(
    modifier: Modifier = Modifier,
    ballValue: String,
    shouldBlockClick: Boolean,
    selectableBallState: SelectableBallState = SelectableBallState.STANDARD,
    onBallClick: (Boolean) -> Unit
) {
    var ballSelected by remember {
        mutableStateOf(false)
    }

    var ballState by remember {
        mutableStateOf(selectableBallState)
    }

    Ball(
        modifier = modifier
            .clickable(enabled = !shouldBlockClick || ballSelected) {
                ballSelected = !ballSelected

                ballState = if(ballSelected){
                    SelectableBallState.SELECTED
                } else {
                    selectableBallState
                }

                onBallClick(ballSelected)
            },
        ballValue = ballValue,
        ballState = ballState
    )
}