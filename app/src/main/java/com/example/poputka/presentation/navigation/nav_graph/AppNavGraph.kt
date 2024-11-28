package com.example.poputka.presentation.navigation.nav_graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.poputka.presentation.authorization.navigation.addAuthRoute
import com.example.poputka.presentation.home.navigation.HomeDestination
import com.example.poputka.presentation.home.navigation.addHomeRoute
import com.example.poputka.presentation.journal.navigation.addJournalRoute
import com.example.poputka.presentation.navigation.util.enterSlideTransition
import com.example.poputka.presentation.navigation.util.exitSlideTransition
import com.example.poputka.presentation.navigation.util.popEnterSlideTransition
import com.example.poputka.presentation.navigation.util.popExitSlideTransition
import com.example.poputka.presentation.profile.navigation.addProfileRoute
import com.example.poputka.presentation.tips.navigation.addTipsRoute

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(modifier = modifier,
        navController = navController,
        startDestination = HomeDestination.HomeNav,
        enterTransition = { enterSlideTransition() },
        exitTransition = { exitSlideTransition() },
        popEnterTransition = { popEnterSlideTransition() },
        popExitTransition = { popExitSlideTransition() })
    {
        addAuthRoute(navController = navController)
        addHomeRoute(navController = navController)
        addJournalRoute(navController = navController)
        addTipsRoute(navController = navController)
        addProfileRoute(navController = navController)
    }
}