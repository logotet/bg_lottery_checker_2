package com.logotet.totochecker.presentation.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.logotet.totochecker.presentation.ui.composables.SelectableBallState.*

@Composable
fun Ball(
    modifier: Modifier = Modifier,
    ballValue: String,
    sizes: Dp = 40.dp,
    ballState: SelectableBallState = STANDARD
) {
    val gradientColors = ballState.getGradientColors()

    val textColor = if (ballState == STANDARD || ballState == SELECTED) {
        Color.Black
    } else {
        Color.White
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(sizes)
            .aspectRatio(1f)
            .background(
                brush = Brush.verticalGradient(
                    colors = gradientColors
                ),
                shape = CircleShape
            )
    ) {
        Text(
            text = ballValue,
            fontSize = 18.sp,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = textColor
            )
        )
    }
}

enum class SelectableBallState {
    SELECTED,
    STANDARD,
    DRAW,
    WINNING
}

fun SelectableBallState.getGradientColors() =
    when (this) {
        STANDARD -> listOf(
            Color.White,
            Color(0xFFE6E6E6)
        )

        SELECTED -> listOf(
            Color(0xFFFFF9C4),
            Color(0xFFFFF176)
        )

        DRAW -> listOf(
            Color(0xFFFFCDD2),
            Color(0xFFE57373)
        )

        WINNING -> listOf(
            Color(0xFFC8E6C9),
            Color(0xFF81C784)
        )
    }

@Preview
@Composable
fun LotteryBallPreview() {
    Ball(
        ballValue = "1",
        ballState = STANDARD
    )
}