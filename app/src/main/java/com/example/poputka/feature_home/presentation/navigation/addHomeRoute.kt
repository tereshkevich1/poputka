@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.poputka.feature_home.presentation.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.poputka.feature_home.presentation.add_drink_screen.AddDrinkRoute
import com.example.poputka.feature_home.presentation.home_screen.HomeRoute

fun NavGraphBuilder.addHomeRoute(navController: NavController) {
    navigation<HomeDestination.HomeNav>(
        startDestination = HomeDestination.Home,
    ) {
        homeDestination()
        addDrinkDestination(navController)
    }
}

fun NavGraphBuilder.homeDestination() {
    composable<HomeDestination.Home> {
        HomeRoute()
    }
}

fun NavGraphBuilder.addDrinkDestination(navController: NavController) {
    composable<HomeDestination.AddDrink> {
        AddDrinkRoute(onBack = { navController.navigateUp() })
    }
}