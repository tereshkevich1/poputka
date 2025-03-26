package com.example.poputka.core.presentation.navigation.nav_graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.poputka.core.presentation.navigation.util.enterFadeTransaction
import com.example.poputka.core.presentation.navigation.util.exitFadeTransaction
import com.example.poputka.core.presentation.navigation.util.popExitSlideTransition
import com.example.poputka.feature_auth.presentation.navigation.addAuthRoute
import com.example.poputka.feature_home.presentation.navigation.HomeDestination
import com.example.poputka.feature_home.presentation.navigation.addHomeRoute
import com.example.poputka.feature_journal.presentation.navigation.addJournalRoute
import com.example.poputka.feature_settings.presentation.navigation.addProfileRoute
import com.example.poputka.feature_tips.presentation.navigation.addTipsRoute

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(modifier = modifier,
        navController = navController,
        startDestination = HomeDestination.HomeNav,
        enterTransition = { enterFadeTransaction() },
        exitTransition = { exitFadeTransaction() },
        popEnterTransition = { enterFadeTransaction() },
        popExitTransition = { popExitSlideTransition() })
    {
        addAuthRoute(navController = navController)
        addHomeRoute(navController = navController)
        addJournalRoute(navController = navController)
        addTipsRoute(navController = navController)
        addProfileRoute(navController = navController)
    }
}