@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.poputka.feature_settings.presentation.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.poputka.core.presentation.navigation.util.enterFadeTransaction
import com.example.poputka.core.presentation.navigation.util.exitFadeTransaction
import com.example.poputka.feature_settings.presentation.personal_settings_screen.PersonalSettingsRoute
import com.example.poputka.feature_settings.presentation.settings_screen.SettingsScreenRoute

fun NavGraphBuilder.addProfileRoute(navController: NavController) {
    navigation<ProfileDestination.ProfileNav>(
        startDestination = ProfileDestination.Profile,
    ) {
        settingsScreen(navController)
        personalInfoDestination(navController)
    }
}

fun NavGraphBuilder.settingsScreen(navController: NavController) {
    composable<ProfileDestination.Profile> {
        SettingsScreenRoute(onNavigateToPersonalScree = { navController.navigate(ProfileDestination.PersonalInfo) })
    }
}

fun NavGraphBuilder.personalInfoDestination(navController: NavController) {
    composable<ProfileDestination.PersonalInfo>(
        enterTransition = { enterFadeTransaction() },
        exitTransition = { exitFadeTransaction() }) {
        PersonalSettingsRoute(onNavigateToSettingsScreen = { navController.navigateUp() })
    }
}