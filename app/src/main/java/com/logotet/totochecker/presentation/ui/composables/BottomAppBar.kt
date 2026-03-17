package com.logotet.totochecker.presentation.ui.composables

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.logotet.totochecker.presentation.navigation.BottomBarItem
import com.logotet.totochecker.ui.theme.TotoCheckerTheme

private const val INITIAL_INDEX_ROUTE = 0

@Composable
fun BottomAppBar(
    modifier: Modifier = Modifier,
    navigate: (String) -> Unit
) {
    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        var selectedIndex by remember {
            mutableIntStateOf(INITIAL_INDEX_ROUTE)
        }

        BottomBarItem.bottomBarItems.forEachIndexed { index, bottomBarItem ->
            NavigationBarItem(
                selected = index == selectedIndex,
                onClick = {
                    selectedIndex = index
                    navigate(bottomBarItem.route.route)
                },
                icon = {
                    Icon(
                        painter = painterResource(id = bottomBarItem.iconId),
                        contentDescription = null
                    )
                },
                label = {
                    Text(text = bottomBarItem.name)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    indicatorColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    disabledIconColor = MaterialTheme.colorScheme.onPrimary,
                    disabledTextColor = MaterialTheme.colorScheme.onPrimary,
                )
            )
        }
    }
}

@Preview
@Composable
fun BottomAppBarPreview() {
    TotoCheckerTheme {
        BottomAppBar(navigate = {})
    }
}