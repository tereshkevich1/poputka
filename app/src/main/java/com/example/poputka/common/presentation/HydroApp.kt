package com.example.poputka.common.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.poputka.common.global_state.local_settings_state.LocalSettingsState
import com.example.poputka.common.presentation.constants.UiConstants
import com.example.poputka.common.presentation.navigation.BottomNavBar
import com.example.poputka.common.presentation.navigation.nav_graph.AppNavGraph
import com.example.poputka.common.presentation.navigation.topLevelRoutes
import com.example.poputka.feature_home.presentation.home_screen.add_water_button.AddWaterButton
import com.example.poputka.feature_home.presentation.navigation.HomeDestination
import com.example.poputka.ui.theme.PoputkaTheme

@Composable
fun HydroApp(appStateViewModel: AppStateViewModel = hiltViewModel()) {
    val settingsState by appStateViewModel.appPrefFlow.collectAsStateWithLifecycle()

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    var bottomBarState by rememberSaveable { mutableStateOf(true) }

    LaunchedEffect(currentDestination) {
        if (currentDestination != null) {
            bottomBarState = topLevelRoutes.any {
                currentDestination.hasRoute(
                    it.route::class
                )
            }
        }
    }

    PoputkaTheme {
        // Scaffold with bottom padding removal to prevent conflicts
        // between content and the BottomNavBar.
        Scaffold(
            bottomBar = {
                // Setting the BottomNavBar height explicitly using Modifier.height.
                // The height is fixed and defined in UiConstants.
                BottomNavBar(
                    navController = navController,
                    currentDestination = currentDestination,
                    bottomBarState = bottomBarState,
                    modifier = Modifier.height(UiConstants.BottomNavBarHeight)
                )
            },
            floatingActionButton = {
                AddWaterButton(
                    onClick = { navController.navigate(HomeDestination.AddDrink) },
                    onLongClick = {},
                    bottomBarState = bottomBarState,
                    modifier = Modifier
                        .offset(y = UiConstants.fabYOffset)
                )
            },
            floatingActionButtonPosition = FabPosition.Center,
            containerColor = MaterialTheme.colorScheme.surface
        ) { paddingValues ->

            // Ignoring bottom padding to allow the content to fill the screen
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = paddingValues.calculateTopPadding())
                    .consumeWindowInsets(paddingValues)
            ) {
                CompositionLocalProvider(LocalSettingsState provides settingsState) {
                    AppNavGraph(navController = navController)
                }
            }
        }
    }
}