package com.example.poputka.feature_journal.presentation.day_screen

sealed class DayScreenAction {
    data object OnNextPeriodClick : DayScreenAction()
    data object OnPreviousPeriodClick : DayScreenAction()
    data object OnAddRecordClick : DayScreenAction()
    data class OnRecordClick(val recordId: Long) : DayScreenAction()
}

