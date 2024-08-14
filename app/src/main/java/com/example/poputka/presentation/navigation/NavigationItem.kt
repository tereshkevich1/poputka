package com.example.poputka.presentation.navigation

sealed class NavigationItem(val route: String) {
    data object LoginNavigationItem : NavigationItem(Screen.Login.name)
    data object SmsVerificationNavigationItem : NavigationItem(Screen.SmsVerification.name)
    data object HomeNavigationItem : NavigationItem(Screen.Home.name)
    data object ProfileNavigationItem : NavigationItem(Screen.Profile.name)
}