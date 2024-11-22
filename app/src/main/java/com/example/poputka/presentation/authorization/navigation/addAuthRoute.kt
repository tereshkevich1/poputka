package com.example.poputka.presentation.authorization.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.poputka.presentation.authorization.auth_screen.AuthScreen
import com.example.poputka.presentation.authorization.auth_screen.AuthViewModel
import com.example.poputka.presentation.authorization.sms_verification_screen.SMSVerificationScreen


fun NavGraphBuilder.addAuthRoute(
    navController: NavHostController,
) {
    navigation<AuthDestination.Login>(
        startDestination = AuthDestination.SmsVerification
    ) {

        authDestination(
            onNavigateToContactDetails = {
                navController.navigate(AuthDestination.SmsVerification) {
                    launchSingleTop = true
                }
            }
        )

        smsVerificationDestination(
            onBackPressed = {
                navController.navigateUp()
            },
            getBackStackEntry = {
                navController.getBackStackEntry(AuthDestination.Login)
            }
        )
    }
}

fun NavGraphBuilder.authDestination(
    onNavigateToContactDetails: () -> Unit
) {
    composable<AuthDestination.SmsVerification> { backStackEntry ->
        val authViewModel: AuthViewModel = hiltViewModel(backStackEntry)

        AuthScreen(
            onNavigateToComposable = onNavigateToContactDetails,
            authViewModel = authViewModel
        )
    }
}


fun NavGraphBuilder.smsVerificationDestination(
    onBackPressed: () -> Unit,
    getBackStackEntry: () -> NavBackStackEntry
) {
    composable<AuthDestination.SmsVerification> {
        val authViewModel: AuthViewModel = hiltViewModel(getBackStackEntry())

        SMSVerificationScreen(
            onNavigateToComposable = { },
            onBackPressed = { onBackPressed() },
            authViewModel = authViewModel
        )
    }
}
