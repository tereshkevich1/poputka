package com.example.poputka.presentation.navigation

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.poputka.presentation.navigation.icons.HomeIcon
import com.example.poputka.presentation.navigation.icons.SearchIcon
import com.example.poputka.presentation.navigation.nav_items.RootScreen

@Composable
fun BottomNavBar(navController: NavController, currentSelectedScreen: RootScreen) {
    NavigationBar {
        NavigationBarItem(
            selected = currentSelectedScreen == RootScreen.Home,
            onClick = { navController.navigate(RootScreen.Home) },
            icon = { HomeIcon() })

        NavigationBarItem(
            selected = currentSelectedScreen == RootScreen.Search,
            onClick = { navController.navigate(RootScreen.Search) },
            icon = { SearchIcon() },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                disabledIconColor = Color.Red
            )
        )
    }
}