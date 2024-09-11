package com.example.poputka.presentation.navigation.graphs

import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.poputka.presentation.authorization.auth_screen.AuthScreen
import com.example.poputka.presentation.authorization.auth_screen.AuthViewModel
import com.example.poputka.presentation.authorization.sms_verification_screen.SMSVerificationScreen
import com.example.poputka.presentation.navigation.nav_items.NavigationItem
import com.example.poputka.presentation.navigation.util.navigateBackIfResumed
import com.example.poputka.presentation.navigation.util.navigateIfResumed
import com.example.poputka.presentation.navigation.util.sharedViewModel

fun NavGraphBuilder.addAuthRoute(
    navController: NavHostController,
) {
    navigation(
        startDestination = NavigationItem.LoginNavigationItem.route,
        route = NavigationItem.AutNavigationItem.route
    ) {
        composable(NavigationItem.LoginNavigationItem.route) { backStackEntry ->
            val authViewModel = backStackEntry.sharedViewModel<AuthViewModel>(navController)
            val lifecycleOwner = LocalLifecycleOwner.current
            AuthScreen(
                onNavigateToComposable = {
                    navController.navigateIfResumed(
                        lifecycleOwner = lifecycleOwner,
                        route = NavigationItem.SmsVerificationNavigationItem.route
                    )
                },
                authViewModel = authViewModel
            )
        }
        composable(NavigationItem.SmsVerificationNavigationItem.route) { backStackEntry ->
            val authViewModel = backStackEntry.sharedViewModel<AuthViewModel>(navController)
            val lifecycleOwner = LocalLifecycleOwner.current
            SMSVerificationScreen(
                onNavigateToComposable = { },
                onBackPressed = {
                    navController.navigateBackIfResumed(lifecycleOwner = lifecycleOwner)
                },
                authViewModel = authViewModel
            )
        }
    }
}