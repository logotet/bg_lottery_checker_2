package com.logotet.totochecker.presentation.navigation

sealed class Route(var route: String, var label: String) {
    data object Home : Route(HOME_ROUTE, HOME_TITLE)
    data object Check : Route(CHECK_ROUTE, CHECK_TITLE)
    data object MyNumbers : Route(MY_NUMBERS_ROUTE, MY_NUMBERS_TITLE)

    companion object {
        private const val HOME_ROUTE: String = "home"
        private const val CHECK_ROUTE: String = "check"
        private const val MY_NUMBERS_ROUTE: String = "local"

        private const val HOME_TITLE: String = "Home"
        private const val CHECK_TITLE: String = "Check"
        private const val MY_NUMBERS_TITLE: String = "My numbers"
    }
}