package com.logotet.totochecker.presentation.ui.composables

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BallList(
    numbers: List<String>,
    @StringRes listHeader: Int,
    @StringRes subHeader: Int? = null,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(16.dp).fillMaxWidth()
    ) {

        Text(
            text = stringResource(id = listHeader),
            fontSize = 24.sp,
            style = TextStyle(
                fontWeight = FontWeight.Bold
            )
        )

        subHeader?.let {
            Text(
                text = stringResource(id = subHeader),
                fontSize = 18.sp,
                style = TextStyle(
                    fontWeight = FontWeight.Bold
                )
            )
        }

        Row(modifier = Modifier.padding(top = 8.dp)) {
            numbers.forEach { value ->
                Ball(ballValue = value)
            }
        }
    }
}