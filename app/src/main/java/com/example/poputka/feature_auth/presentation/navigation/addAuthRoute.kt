package com.example.poputka.feature_auth.presentation.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation


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

    }
}


fun NavGraphBuilder.smsVerificationDestination(
    onBackPressed: () -> Unit,
    getBackStackEntry: () -> NavBackStackEntry
) {
    composable<AuthDestination.SmsVerification> {
    }
}
