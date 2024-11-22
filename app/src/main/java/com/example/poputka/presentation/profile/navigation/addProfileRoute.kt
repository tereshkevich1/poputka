package com.example.poputka.presentation.profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.poputka.presentation.profile.profile_screen.ProfileScreen

fun NavGraphBuilder.addProfileRoute(navController: NavController) {
    navigation<ProfileDestination.ProfileNav>(
        startDestination = ProfileDestination.Profile,
    ) {
        journalDestination()
    }
}

fun NavGraphBuilder.journalDestination() {
    composable<ProfileDestination.Profile> {
        ProfileScreen()
    }
}