package com.logotet.totochecker.presentation.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.logotet.totochecker.domain.data.LotteryType
import com.logotet.totochecker.ui.theme.TotoCheckerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenu(
    modifier: Modifier = Modifier,
    options: List<LotteryType>,
    onValueChange: (LotteryType) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    var selectedValue by remember { mutableStateOf(options[0]) }

    ExposedDropdownMenuBox(
        modifier = modifier
            .padding(vertical = 20.dp)
            .fillMaxWidth(),
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            modifier = Modifier
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth(),
            readOnly = true,
            value = selectedValue.typeName,
            onValueChange = {},
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            shape = RoundedCornerShape(corner = CornerSize(10.dp)),
            colors = OutlinedTextFieldDefaults.colors()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(
                    modifier = Modifier.fillMaxWidth(),
                    text = { Text(text = option.typeName) },
                    onClick = {
                        expanded = false
                        selectedValue = option
                        onValueChange(selectedValue)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun DropdownMenuPreview() {
    TotoCheckerTheme {
        Box(
            modifier = Modifier.fillMaxSize().background(Color.White)
        ) {
            DropdownMenu(options = LotteryType.entries, onValueChange = {})
        }
    }
}