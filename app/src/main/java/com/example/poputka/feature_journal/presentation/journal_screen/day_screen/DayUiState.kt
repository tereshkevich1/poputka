package com.example.poputka.feature_journal.presentation.journal_screen.day_screen

import com.example.poputka.common.domain.model.VolumeUnit
import com.example.poputka.common.presentation.models.DisplayableInt
import com.example.poputka.common.presentation.models.DisplayableLocaleDate
import com.example.poputka.common.presentation.models.mappers.formatDateRange
import com.example.poputka.common.presentation.models.mappers.toDisplayableDateFull
import com.example.poputka.common.presentation.models.mappers.toDisplayableVolume
import com.example.poputka.feature_journal.presentation.charts.bar_chart.Bar
import com.example.poputka.feature_journal.presentation.journal_screen.DrinkRecordUi
import java.time.LocalDate

data class DayUiState(
    val volumeUnit: VolumeUnit = VolumeUnit.Milliliters,
    val currentDate: DisplayableLocaleDate = LocalDate.now().toDisplayableDateFull(),
    val totalHydration: DisplayableInt = 0.toDisplayableVolume(volumeUnit),
    val achievementValue: Float = 0f,
    val records: List<DrinkRecordUi> = emptyList(),
    val bars: List<Bar> = emptyList()
)

data class WeekUiState(
    val volumeUnit: VolumeUnit = VolumeUnit.Milliliters,
    val endDate: LocalDate = LocalDate.now(),
    val startDate: LocalDate = endDate.minusDays(7),
    val formattedDateRange: String = formatDateRange(startDate, endDate),
    val totalHydration: DisplayableInt = 0.toDisplayableVolume(volumeUnit),
    val achievementValue: Float = 0f,
    val records: List<DrinkRecordUi> = emptyList(),
    val bars: List<Bar> = emptyList()
)

