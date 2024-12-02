package com.example.poputka.presentation.navigation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.example.poputka.presentation.navigation.custom_nav_bar.NavBar

@Composable
fun BottomNavBar(navController: NavController, currentDestination: NavDestination?) {
    NavBar(modifier = Modifier.height(108.dp)) {
        topLevelRoutes.forEachIndexed { index, topLevelRoute ->

            // TODO: Refactor
            if (index == topLevelRoutes.size / 2) {
                Spacer(modifier = Modifier.width(48.dp))
            }
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.hasRoute(topLevelRoute.route::class) } == true,
                onClick = {
                    if (!navController.popBackStack(
                            topLevelRoute.route,
                            inclusive = false,
                            saveState = true
                        )
                    ) {
                        navController.navigate(topLevelRoute.route) {
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    }
                },
                icon = { Icon(painterResource(topLevelRoute.icon), contentDescription = null) },
                label = { Text(topLevelRoute.name) }
            )
        }
    }
}