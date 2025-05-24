package com.example.poputka.feature_journal.presentation.week_screen

import com.example.poputka.common.domain.model.VolumeUnit
import com.example.poputka.common.presentation.models.DisplayableInt
import com.example.poputka.common.presentation.models.DisplayableLocaleDate
import com.example.poputka.common.presentation.models.mappers.formatDateRange
import com.example.poputka.common.presentation.models.mappers.toDisplayableDateFull
import com.example.poputka.common.presentation.models.mappers.toDisplayableVolume
import com.example.poputka.feature_home.domain.models.DailyConsumption
import com.example.poputka.feature_journal.presentation.charts.bar_chart.Bar
import java.time.DayOfWeek
import java.time.LocalDate

data class WeekState(
    val volumeUnit: VolumeUnit = VolumeUnit.Milliliters,
    val today: LocalDate = LocalDate.now(),
    val startDate: LocalDate = today.with(DayOfWeek.MONDAY),
    val endDate: LocalDate = today.with(DayOfWeek.SUNDAY),
    val formattedDateRange: String = formatDateRange(startDate, endDate),
    val totalHydration: DisplayableInt = 0.toDisplayableVolume(volumeUnit),
    val achievementValue: Float = 0f,
    val records: List<DailyConsumptionUi> = emptyList(),
    val bars: List<Bar> = emptyList()
)


class DailyConsumptionUi(
    val date: DisplayableLocaleDate,
    val totalVolume: DisplayableInt
)

fun DailyConsumption.toUi(volumeUnit: VolumeUnit): DailyConsumptionUi = DailyConsumptionUi(
    date = date.toDisplayableDateFull(),
    totalVolume = totalHydrationVolume.toDisplayableVolume(volumeUnit)
)