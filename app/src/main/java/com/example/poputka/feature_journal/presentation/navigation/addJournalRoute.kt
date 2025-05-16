package com.example.poputka.feature_journal.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.poputka.feature_journal.presentation.journal_screen.JournalScreenRoute

fun NavGraphBuilder.addJournalRoute(navController: NavController) {
    navigation<JournalDestination.JournalNav>(
        startDestination = JournalDestination.Journal,
    ) {
        journalDestination()
    }
}

fun NavGraphBuilder.journalDestination() {
    composable<JournalDestination.Journal> {
        JournalScreenRoute()
    }
}