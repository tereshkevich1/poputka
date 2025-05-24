package com.example.poputka.feature_journal.presentation.week_screen

import java.time.LocalDate

sealed class WeekScreenAction {
    data object OnNextPeriodClick : WeekScreenAction()
    data object OnPreviousPeriodClick : WeekScreenAction()
    data object OnAddRecordClick : WeekScreenAction()
    data class OnRecordClick(val localDate: LocalDate) : WeekScreenAction()
}