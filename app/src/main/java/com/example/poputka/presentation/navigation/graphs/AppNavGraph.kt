package com.example.poputka.presentation.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.poputka.presentation.navigation.nav_items.NavigationItem
import com.example.poputka.presentation.navigation.util.enterSlideTransition
import com.example.poputka.presentation.navigation.util.exitSlideTransition
import com.example.poputka.presentation.navigation.util.popEnterSlideTransition
import com.example.poputka.presentation.navigation.util.popExitSlideTransition

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = NavigationItem.AutNavigationItem.route
) {
    NavHost(modifier = modifier,
        navController = navController,
        startDestination = startDestination,
        enterTransition = { enterSlideTransition() },
        exitTransition = { exitSlideTransition() },
        popEnterTransition = { popEnterSlideTransition() },
        popExitTransition = { popExitSlideTransition() }) {
        addAuthRoute(navController = navController)
    }
}