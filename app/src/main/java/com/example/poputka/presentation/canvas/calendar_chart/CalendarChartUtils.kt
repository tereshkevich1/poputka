package com.example.poputka.presentation.canvas.calendar_chart

import android.content.Context
import android.util.TypedValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.time.YearMonth

object CalendarChartUtils {
    fun getCanvasHeight(
        context: Context,
        dayCellHeight: Dp,
        reqHeightHeader: Float,
        calendarRows: Int
    ): Dp = dayCellHeight * calendarRows + reqHeightHeader.toDp(context)

    fun Dp.toPx(context: Context): Float =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.value, context.resources.displayMetrics)

    fun calculateStartOffset(currentMonth: YearMonth): Int =
        currentMonth.atDay(1).dayOfWeek.value - 1

    fun calculateCalendarRows(daysInMonth: Int, startOffset: Int): Int =
        (daysInMonth + startOffset + 6) / 7

    private fun Float.toDp(context: Context): Dp =
        (this / context.resources.displayMetrics.density).dp
}