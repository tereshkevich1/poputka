package com.example.poputka.presentation.navigation.nav_items

sealed class NavigationItem(val route: String) {
    data object LoginNavigationItem : NavigationItem(Screen.Login.name)
    data object SmsVerificationNavigationItem : NavigationItem(Screen.SmsVerification.name)
    data object AutNavigationItem : NavigationItem(Screen.Authorization.name)
}