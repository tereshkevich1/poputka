@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.poputka.feature_settings.presentation.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.poputka.core.presentation.navigation.util.enterFadeTransaction
import com.example.poputka.core.presentation.navigation.util.exitFadeTransaction
import com.example.poputka.presentation.profile.settings_screen.SettingsScreen
import com.example.poputka.presentation.profile.settings_screen.components.personal_info_screen.PersonalInfoScreen

fun NavGraphBuilder.addProfileRoute(navController: NavController) {
    navigation<ProfileDestination.ProfileNav>(
        startDestination = ProfileDestination.Profile,
    ) {
        journalDestination(navController)
        personalInfoDestination()
    }
}

fun NavGraphBuilder.journalDestination(navController: NavController) {
    composable<ProfileDestination.Profile> {
        SettingsScreen(
            onPersonalInfoClick = {
                navController.navigate(
                    ProfileDestination.PersonalInfo
                )
            }
        )
    }
}

fun NavGraphBuilder.personalInfoDestination() {
    composable<ProfileDestination.PersonalInfo>(
        enterTransition = { enterFadeTransaction() },
        exitTransition = { exitFadeTransaction() }) {
        PersonalInfoScreen()
    }
}