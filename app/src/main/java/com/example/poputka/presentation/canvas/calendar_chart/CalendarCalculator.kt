package com.example.poputka.presentation.canvas.calendar_chart

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size

interface CalendarCalculator {
    fun getDayForPosition(offset: Offset, calendarSize: Size, startOffset: Int): Int?
}