@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.poputka

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.poputka.common.global_state.AppStateHolder
import com.example.poputka.common.global_state.local_settings_state.LocalSettingsState
import com.example.poputka.common.presentation.constants.UiConstants.BottomNavBarHeight
import com.example.poputka.common.presentation.constants.UiConstants.fabYOffset
import com.example.poputka.common.presentation.navigation.BottomNavBar
import com.example.poputka.common.presentation.navigation.nav_graph.AppNavGraph
import com.example.poputka.common.presentation.navigation.topLevelRoutes
import com.example.poputka.common.presentation.navigation.util.enterFadeTransaction
import com.example.poputka.common.presentation.navigation.util.exitFadeTransaction
import com.example.poputka.feature_home.presentation.home_screen.add_water_button.AddWaterButton
import com.example.poputka.ui.theme.PoputkaTheme
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.appCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.google.firebase.initialize
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * MainActivity — the main entry point of the application.
 *
 * Changes related to `BottomNavBar`:
 * 1. Removed the automatic bottom padding (`paddingValues`) in the `Scaffold` to ensure
 *    main content is displayed under the navigation bar without overlapping.
 * 2. Defined the BottomNavBar height in `UiConstants.BottomNavBarHeight` for consistent usage.
 * 3. Added manual padding where necessary using `Spacer` or `Modifier.padding`.
 *
 * These changes improve the UI structure and prevent conflicts between the main content
 * and the BottomNavBar.
 */

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var appStateHolder: AppStateHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(applicationContext)
        Firebase.initialize(applicationContext)
        Firebase.appCheck.installAppCheckProviderFactory(
            DebugAppCheckProviderFactory.getInstance(),
        )
        enableEdgeToEdge()

        setContent {
            val settingsState by appStateHolder.appPreferencesStateHolder.appPrefFlow.collectAsStateWithLifecycle()

            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            var bottomBarState by rememberSaveable { mutableStateOf(true) }
            var topBarState = !bottomBarState

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
                    topBar = {
                        // TopBar(navController, bottomBarState)
                    },
                    bottomBar = {
                        // Setting the BottomNavBar height explicitly using Modifier.height.
                        // The height is fixed and defined in UiConstants.
                        BottomNavBar(
                            navController = navController,
                            currentDestination = currentDestination,
                            bottomBarState = bottomBarState,
                            modifier = Modifier.height(BottomNavBarHeight)
                        )
                    },
                    floatingActionButton = {
                        AddWaterButton(
                            onClick = {},
                            onLongClick = {},
                            bottomBarState = bottomBarState,
                            modifier = Modifier
                                .offset(y = fabYOffset)
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
    }
}

@Composable
fun TopBar(
    navController: NavController,
    bottomBarState: Boolean
) {

    AnimatedVisibility(
        visible = !bottomBarState,
        enter = enterFadeTransaction(),
        exit = exitFadeTransaction()
    ) {
        TopAppBar(
            title = {
                Text("sss")
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "backIcon")
                }
            }
        )
    }
}



