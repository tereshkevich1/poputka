package com.example.poputka.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.poputka.presentation.auth_screen.AuthScreen
import com.example.poputka.presentation.sms_verification_screen.SMSVerificationScreen

@Composable
fun AuthNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = Screen.Login.name
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
        enterTransition = { enterSlideTransition() },
        exitTransition = { exitSlideTransition() },
        popEnterTransition = { popEnterSlideTransition() },
        popExitTransition = { popExitSlideTransition() }
    ) {
        composable(NavigationItem.LoginNavigationItem.route) {
            AuthScreen(navController)
        }
        composable(NavigationItem.SmsVerificationNavigationItem.route) {
            SMSVerificationScreen(navController)
        }
    }
}