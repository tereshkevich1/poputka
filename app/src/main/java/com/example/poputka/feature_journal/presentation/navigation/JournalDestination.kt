package com.example.poputka.feature_journal.presentation.navigation

import kotlinx.serialization.Serializable

sealed class JournalDestination {
    @Serializable
    data object JournalNav : JournalDestination()
    @Serializable
    data object Journal : JournalDestination()
}