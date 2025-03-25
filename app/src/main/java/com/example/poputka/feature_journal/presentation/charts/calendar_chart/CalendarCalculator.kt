package com.example.poputka.feature_journal.presentation.charts.calendar_chart

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size

interface CalendarCalculator {
    fun getDayIndexFromDragPosition (
        offset: Offset,
        calendarSize: Size,
        daysInMonth: Int,
        calendarRows: Int,
        startOffset: Int,
    ): Int

    fun getDayIndexFromTapPosition(
        offset: Offset,
        calendarSize: Size,
        daysInMonth: Int,
        calendarRows: Int,
        startOffset: Int,
    ): Int?
}