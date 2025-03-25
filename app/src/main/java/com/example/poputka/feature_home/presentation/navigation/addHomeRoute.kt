package com.example.poputka.feature_home.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.poputka.feature_home.presentation.home_screen.HomeScreen

fun NavGraphBuilder.addHomeRoute(navController: NavController) {
    navigation<HomeDestination.HomeNav>(
        startDestination = HomeDestination.Home,
    ) {
        journalDestination()
    }
}

fun NavGraphBuilder.journalDestination() {
    composable<HomeDestination.Home> {
        HomeScreen()
    }
}