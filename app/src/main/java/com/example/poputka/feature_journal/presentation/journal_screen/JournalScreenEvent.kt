package com.example.poputka.feature_journal.presentation.journal_screen

sealed class JournalScreenEvent {
    data class ShowToast(val message: String) : JournalScreenEvent()
}