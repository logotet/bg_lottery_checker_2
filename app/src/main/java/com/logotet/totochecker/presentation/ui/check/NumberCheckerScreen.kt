package com.logotet.totochecker.presentation.ui.check

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.logotet.totochecker.R
import com.logotet.totochecker.domain.data.LotteryType
import com.logotet.totochecker.presentation.ui.check.Event.OnError
import com.logotet.totochecker.presentation.ui.check.Event.OnMatchingNumbersSelected
import com.logotet.totochecker.presentation.ui.composables.AppAlertDialog
import com.logotet.totochecker.presentation.ui.composables.ClickableBall
import com.logotet.totochecker.presentation.ui.composables.DropdownMenu
import com.logotet.totochecker.ui.theme.TotoCheckerTheme

@Composable
fun NumberCheckerScreen(
    viewModel: NumbersCheckerViewModel
) {
    val state = viewModel.state
    val event by viewModel.events.collectAsStateWithLifecycle(Event.DismissEvent)

    when (event) {
        is OnMatchingNumbersSelected -> {
            val matchingNumbers = (event as? OnMatchingNumbersSelected)?.matchingNumbers.toString()
            AppAlertDialog(
                text = {
                    Text(text = matchingNumbers)
                },
                confirmButtonTextId = R.string.dialog_confirm_button,
                onConfirm = { viewModel.onAction(Action.DismissDialog) }) {
            }
        }

        is OnError -> {}
        is Event.DismissEvent -> {}
    }

    CheckScreenContent(
        state = state,
        onAction = { action ->
            viewModel.onAction(action)
        }
    )
}

@Composable
fun CheckScreenContent(
    modifier: Modifier = Modifier,
    state: CheckScreenState,
    onAction: (Action) -> Unit
) {
    var counter by remember {
        mutableIntStateOf(0)
    }

    LaunchedEffect(state.lotteryType) {
        counter = 0
    }

    val isMaxNumbersReached = counter >= state.lotteryType.maxListSize

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            DropdownMenu(
                options = LotteryType.entries,
                onValueChange = { type ->
                    onAction(Action.OnMenuItemSelected(type))
                })

            key(state.lotteryType) {
                LazyVerticalGrid(
                    modifier = modifier.fillMaxWidth(), columns = GridCells.Fixed(6),
                    contentPadding = PaddingValues(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    items(state.numberSequence) { ballValue ->
                        ClickableBall(
                            ballValue = ballValue,
                            shouldBlockClick = isMaxNumbersReached,
                            onBallClick = { selected ->
                                if (selected) {
                                    onAction(Action.OnNumberSelected(ballValue))
                                    counter++
                                } else {
                                    onAction(Action.OnNumberUnselected(ballValue))
                                    counter--
                                }
                            }
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                enabled = isMaxNumbersReached,
                onClick = { onAction(Action.OnCheckNumbers) }) {
                Text(text = stringResource(id = R.string.check_button))
            }
        }
    }
}

@Preview
@Composable
fun CheckScreenContentPreview() {
    TotoCheckerTheme {
        CheckScreenContent(state = CheckScreenState.defaultState, onAction = {})
    }
}