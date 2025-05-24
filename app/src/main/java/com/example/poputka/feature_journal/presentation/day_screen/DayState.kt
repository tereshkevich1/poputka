package com.example.poputka.feature_journal.presentation.day_screen

import com.example.poputka.common.domain.model.VolumeUnit
import com.example.poputka.common.presentation.models.DisplayableInt
import com.example.poputka.common.presentation.models.DisplayableLocaleDate
import com.example.poputka.common.presentation.models.mappers.toDisplayableDateFull
import com.example.poputka.common.presentation.models.mappers.toDisplayableVolume
import com.example.poputka.feature_journal.presentation.charts.bar_chart.Bar
import com.example.poputka.feature_journal.presentation.journal_screen.DrinkRecordUi
import java.time.LocalDate

data class DayState(
    val volumeUnit: VolumeUnit = VolumeUnit.Milliliters,
    val currentDate: DisplayableLocaleDate = LocalDate.now().toDisplayableDateFull(),
    val totalHydration: DisplayableInt = 0.toDisplayableVolume(volumeUnit),
    val achievementValue: Float = 0f,
    val records: List<DrinkRecordUi> = emptyList(),
    val bars: List<Bar> = emptyList()
)

