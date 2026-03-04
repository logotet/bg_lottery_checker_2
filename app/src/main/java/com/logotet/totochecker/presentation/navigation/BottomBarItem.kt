package com.logotet.totochecker.presentation.navigation

import androidx.annotation.DrawableRes
import com.logotet.totochecker.R

sealed class BottomBarItem(
    var route: Route,
    @param:DrawableRes var iconId: Int,
    var name: String
) {
    data object Home : BottomBarItem(Route.Home, R.drawable.ic_home, Route.Home.label)
    data object Check : BottomBarItem(Route.Check, R.drawable.ic_edit, Route.Check.label)
    data object MyNumbers :
        BottomBarItem(Route.MyNumbers, R.drawable.ic_person, Route.MyNumbers.label)

    companion object {
        var bottomBarItems: List<BottomBarItem> =
            listOf(
                Home,
                Check,
                MyNumbers
            )
    }
}