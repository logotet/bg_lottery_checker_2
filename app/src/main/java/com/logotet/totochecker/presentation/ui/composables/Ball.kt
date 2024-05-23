package com.logotet.totochecker.presentation.ui.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
    modifier: Modifier = Modifier,
    ballModifier: Modifier = Modifier,
    textColor: Color = Color.Black,
    @DrawableRes imageId: Int = R.drawable.image_white_ball
) {
    Box(
        modifier = modifier
            .size(60.dp)
            .clip(CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = ballModifier
                .fillMaxSize(),
            painter = painterResource(id = imageId),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Text(
            text = ballValue,
            fontSize = 18.sp,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = textColor
            ), modifier = Modifier
                .align(BiasAlignment(verticalBias = 0f, horizontalBias = 0.2f))
                .padding(16.dp)
        )
    }
}