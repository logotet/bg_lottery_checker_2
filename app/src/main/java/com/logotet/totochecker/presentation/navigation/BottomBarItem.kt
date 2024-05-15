package com.logotet.totochecker.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarItem(var route: Route, var icon: ImageVector, var name: String) {
    data object Home : BottomBarItem(Route.Home, Icons.Default.Home, Route.Home.title)
    data object Check : BottomBarItem(Route.Check, Icons.Default.Edit, Route.Check.title)
    data object MyNumbers : BottomBarItem(Route.MyNumbers, Icons.Default.Favorite, Route.MyNumbers.title)

    companion object {
        var bottomBarItems: List<BottomBarItem> =
            listOf(
                Home,
                Check,
                MyNumbers
            )
    }
}