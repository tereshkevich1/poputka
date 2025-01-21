package com.example.poputka

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.poputka.presentation.home.home_screen.add_water_button.AddWaterButton
import com.example.poputka.presentation.navigation.BottomNavBar
import com.example.poputka.presentation.navigation.nav_graph.AppNavGraph
import com.example.poputka.presentation.navigation.topLevelRoutes
import com.example.poputka.presentation.util.constants.UiConstants.BottomNavBarHeight
import com.example.poputka.presentation.util.constants.UiConstants.fabYOffset
import com.example.poputka.ui.theme.PoputkaTheme
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.appCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.google.firebase.initialize
import dagger.hilt.android.AndroidEntryPoint

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(applicationContext)
        Firebase.initialize(applicationContext)
        Firebase.appCheck.installAppCheckProviderFactory(
            DebugAppCheckProviderFactory.getInstance(),
        )
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            PoputkaTheme {

                // Scaffold with bottom padding removal to prevent conflicts
                // between content and the BottomNavBar.
                Scaffold(
                    bottomBar = {
                        if (currentDestination == null || topLevelRoutes.any {
                                currentDestination.hasRoute(
                                    it.route::class
                                )
                            }) {

                            // Setting the BottomNavBar height explicitly using Modifier.height.
                            // The height is fixed and defined in UiConstants.
                            BottomNavBar(
                                navController = navController,
                                currentDestination = currentDestination,
                                modifier = Modifier.height(BottomNavBarHeight)
                            )
                        }
                    },
                    floatingActionButton = {
                        AddWaterButton(
                            onClick = {},
                            onLongClick = {},
                            modifier = Modifier
                                .offset(y = fabYOffset)
                        )
                    },
                    floatingActionButtonPosition = FabPosition.Center
                ) { paddingValues ->

                    // Ignoring bottom padding to allow the content to fill the screen
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = paddingValues.calculateTopPadding())
                    ) {
                        AppNavGraph(navController = navController)
                    }
                }
            }
        }
    }
}
