package com.example.poputka.presentation.tips.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.poputka.presentation.tips.tips_screen.TipsScreen


fun NavGraphBuilder.addTipsRoute(navController: NavController) {
    navigation<TipsDestination.TipsNav>(
        startDestination = TipsDestination.Tips,
    ) {
        journalDestination()
    }
}

fun NavGraphBuilder.journalDestination() {
    composable<TipsDestination.Tips> {
        TipsScreen()
    }
}