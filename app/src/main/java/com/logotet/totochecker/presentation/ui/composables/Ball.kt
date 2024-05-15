package com.logotet.totochecker.presentation.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.logotet.totochecker.R

@Composable
fun Ball(
    ballValue: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(60.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.image_white_ball),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Text(
            text = ballValue,
            fontSize = 18.sp,
            style = TextStyle(
                fontWeight = FontWeight.Bold
            ), modifier = Modifier
                .align(BiasAlignment(verticalBias = 0f, horizontalBias = 0.2f))
                .padding(16.dp)
        )
    }
}