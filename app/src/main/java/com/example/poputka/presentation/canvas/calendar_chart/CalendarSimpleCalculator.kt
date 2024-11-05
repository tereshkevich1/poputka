package com.example.poputka.presentation.canvas.calendar_chart

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import java.time.YearMonth

class CalendarSimpleCalculator : CalendarCalculator {
    override fun getDayForPosition(offset: Offset, calendarSize: Size, startOffset: Int): Int? {
        if (offset.y > calendarSize.height) {
            return null
        }

        val column =
            (offset.x / calendarSize.width * 7).toInt() + 1 - startOffset
        val row = (offset.y / calendarSize.height * 6).toInt() + 1
        val day = column + (row - 1) * 7

        return if (day in 1..YearMonth.now().lengthOfMonth()) day else null
    }
}