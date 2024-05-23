package com.logotet.totochecker.presentation.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.logotet.totochecker.R

@Composable
fun ClickableBall(
    modifier: Modifier = Modifier,
    ballValue: String,
    shouldBlockClick: Boolean,
    onBallSelected: (String) -> Unit,
    onBallUnselected: (String) -> Unit
) {
    var ballBackgroundImageId by remember {
        mutableIntStateOf(R.drawable.image_white_ball)
    }

    val canBallBeUnselected = ballBackgroundImageId == R.drawable.image_selected_ball
    val ballModifier =
        if (!shouldBlockClick ||
            canBallBeUnselected
        ) {
            Modifier.clickable {
                ballBackgroundImageId =
                    if (ballBackgroundImageId == R.drawable.image_white_ball) {
                        onBallSelected(ballValue)
                        R.drawable.image_selected_ball
                    } else {
                        onBallUnselected(ballValue)
                        R.drawable.image_white_ball
                    }
            }
        } else {
            Modifier
        }

    Ball(
        modifier = modifier,
        ballModifier = ballModifier,
        ballValue = ballValue,
        textColor = if (canBallBeUnselected) Color.White else Color.Black,
        imageId = ballBackgroundImageId
    )
}