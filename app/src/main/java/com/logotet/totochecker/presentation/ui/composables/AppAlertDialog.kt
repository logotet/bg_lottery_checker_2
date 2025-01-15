package com.logotet.totochecker.presentation.ui.composables

import androidx.annotation.StringRes
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.logotet.totochecker.R

@Composable
fun AppAlertDialog(
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit = {},
    title: @Composable () -> Unit = {},
    text: @Composable () -> Unit,
    @StringRes confirmButtonTextId: Int = R.string.dialog_confirm_button,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        modifier = modifier,
        icon = icon,
        title = title,
        text = text,
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = onConfirm
            ) {
                Text(text = stringResource(confirmButtonTextId))
            }
        }
    )
}