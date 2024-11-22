package com.example.poputka.presentation.journal.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.poputka.presentation.journal.journal_screen.JournalScreen

fun NavGraphBuilder.addJournalRoute(navController: NavController) {
    navigation<JournalDestination.JournalNav>(
        startDestination = JournalDestination.Journal,
    ) {
        journalDestination()
    }
}

fun NavGraphBuilder.journalDestination() {
    composable<JournalDestination.Journal> {
        JournalScreen()
    }
}