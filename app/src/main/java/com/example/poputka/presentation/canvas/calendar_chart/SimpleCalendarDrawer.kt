package com.example.poputka.presentation.canvas.calendar_chart

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.poputka.presentation.canvas.common.toLegacyInt
import java.time.LocalDate
import java.time.YearMonth

class SimpleCalendarDrawer(
    private val dayNumberTextColor: Color = Color.DarkGray,
    private val dayNumberTextSize: TextUnit = 14.sp,
    private val labelTextColor: Color = Color.DarkGray,
    private val labelTextSize: TextUnit = 14.sp,
    private val labels: List<String> = listOf("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN")
) {

    private val dayNumberTextPaint = android.graphics.Paint().apply {
        isAntiAlias = true
        color = dayNumberTextColor.toLegacyInt()
    }

    private val labelTextPaint = android.graphics.Paint().apply {
        isAntiAlias = true
        color = labelTextColor.toLegacyInt()
    }

    private val textBounds = android.graphics.Rect()

    private val headerHeight = requiredHeightForHeader()

    fun drawCalendar(
        drawScope: DrawScope,
        canvas: Canvas,
        month: YearMonth
    ) = with(drawScope) {
        val canvasHeight = size.height
        val canvasWidth = size.width


        val daysInMonth = month.lengthOfMonth()

        val firstDayOfMonth = month.atDay(1)

        val dayOfWeek = firstDayOfMonth.dayOfWeek

        val startOffset = dayOfWeek.value - 1

        val calendarRows = (daysInMonth + startOffset + CALENDAR_COLUMNS - 1) / CALENDAR_COLUMNS

        dayNumberTextPaint.textSize = dayNumberTextSize.toPx()
        dayNumberTextPaint.getTextBounds("0", 0, 1, textBounds)

        val ySteps = (canvasHeight - headerHeight) / 6
        val xSteps = canvasWidth / CALENDAR_COLUMNS

        for (i in 0..calendarRows) {
            val linePositionY = i * ySteps
            drawLine(Color.DarkGray, Offset(0f, linePositionY), Offset(canvasWidth, linePositionY))
        }

        for (i in 0..CALENDAR_COLUMNS) {
            val linePositionX = i * xSteps
            drawLine(
                Color.DarkGray,
                Offset(linePositionX, 0f),
                Offset(linePositionX, canvasHeight - headerHeight)
            )
        }

        for (i in 0 until daysInMonth) {
            val text = i.inc().toString()
            dayNumberTextPaint.getTextBounds(text, 0, text.length, textBounds)

            val s = startOffset + i
            val textPositionX =
                xSteps * (s % CALENDAR_COLUMNS) + xSteps / 2 - textBounds.width() / 2
            val textPositionY =
                (s / CALENDAR_COLUMNS) * ySteps + ySteps / 2 + textBounds.height() / 2

            canvas.nativeCanvas.drawText(text, textPositionX, textPositionY, dayNumberTextPaint)
        }
    }


    fun requiredHeightForHeader(): Float {
        labelTextPaint.getTextBounds("0", 0, 1, textBounds)
        return 60f + textBounds.height()
    }

    fun drawWeekHeader(
        drawScope: DrawScope,
        canvas: Canvas
    ) = with(drawScope) {
        val headerHeight = size.height
        val headerWidth = size.width

        labelTextPaint.textSize = labelTextSize.toPx()
        labelTextPaint.getTextBounds("0", 0, 1, textBounds)

        val yPosition = headerHeight
        val xSteps = headerWidth / CALENDAR_COLUMNS

        for (i in labels.indices) {
            val text = labels[i]
            labelTextPaint.getTextBounds(text, 0, text.length, textBounds)
            val xPosition = i * xSteps + xSteps / 2 - textBounds.width() / 2
            canvas.nativeCanvas.drawText(text, xPosition, yPosition, labelTextPaint)
        }

    }

    fun getDayForPosition(offset: Offset, calendarSize: Size): Int? {

        val calendarWithoutHeaderSize = calendarSize.height - headerHeight

        if (offset.y > calendarWithoutHeaderSize) {
            return null
        }

        val column =
            (offset.x / calendarSize.width * 7).toInt() + 1
        val row = (offset.y / calendarWithoutHeaderSize * 5).toInt() + 1
        val day = column + (row - 1) * 7

        return if (day in 1..YearMonth.now().lengthOfMonth()) day else null
    }
}

private const val CALENDAR_COLUMNS = 7
