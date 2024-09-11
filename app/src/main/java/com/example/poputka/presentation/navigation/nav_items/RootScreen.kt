package com.example.poputka.presentation.navigation.nav_items

sealed class RootScreen(val route: String) {
    data object Home : RootScreen(Screen.Home.name)
    data object Search: RootScreen(Screen.Search.name)
}