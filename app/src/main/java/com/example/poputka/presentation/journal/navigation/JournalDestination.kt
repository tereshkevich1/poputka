package com.example.poputka.presentation.journal.navigation

import kotlinx.serialization.Serializable

sealed class JournalDestination {
    @Serializable
    data object JournalNav : JournalDestination()
    @Serializable
    data object Journal : JournalDestination()
}