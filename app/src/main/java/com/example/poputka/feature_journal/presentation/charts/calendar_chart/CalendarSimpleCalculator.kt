package com.example.poputka.feature_journal.presentation.charts.calendar_chart

import android.util.Log
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size

class CalendarSimpleCalculator : CalendarCalculator {
    override fun getDayIndexFromDragPosition(
        offset: Offset,
        calendarSize: Size,
        daysInMonth: Int,
        calendarRows: Int,
        startOffset: Int,
    ): Int {

        val row =
            ((offset.y / calendarSize.height * calendarRows).toInt() + 1).coerceIn(1, calendarRows)
        val column = ((offset.x / calendarSize.width) * 7).toInt().coerceIn(0, 6) - startOffset + 1
        val day = column + (row - 1) * 7

        Log.d("position", "day - $day")
        return when {
            day <= 0 -> 0
            day > daysInMonth -> daysInMonth - 1
            else -> day - 1
        }
    }

    override fun getDayIndexFromTapPosition(
        offset: Offset,
        calendarSize: Size,
        daysInMonth: Int,
        calendarRows: Int,
        startOffset: Int
    ): Int? {
        if (offset.y > calendarSize.height) {
            return null
        }

        val column = (offset.x / calendarSize.width * 7).toInt() + 1 - startOffset
        val row = (offset.y / calendarSize.height * calendarRows).toInt() + 1
        val day = column + (row - 1) * 7

        return if (day in 1..daysInMonth) day - 1 else null
    }
}