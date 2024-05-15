package com.logotet.totochecker.presentation.ui.composables

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.logotet.totochecker.presentation.navigation.BottomBarItem

private const val INITIAL_INDEX_ROUTE = 0

@Composable
fun BottomAppBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
    ) {
        var selectedIndex by remember {
            mutableIntStateOf(INITIAL_INDEX_ROUTE)
        }

        BottomBarItem.bottomBarItems.forEachIndexed { index, bottomBarItem ->
            NavigationBarItem(
                selected = index == selectedIndex,
                onClick = {
                    selectedIndex = index
                    navController.navigate(bottomBarItem.route.route)
                },
                icon = {
                    Icon(
                        painter = painterResource(id = bottomBarItem.iconId),
                        contentDescription = null
                    )
                },
                label = {
                    Text(text = bottomBarItem.name)
                }
            )
        }
    }
}