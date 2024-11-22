package com.example.poputka.presentation.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.poputka.presentation.home.home_screen.HomeScreen


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