package com.example.poputka.presentation.canvas.calendar_chart

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.example.poputka.presentation.canvas.calendar_chart.calendar.ChartData
import com.example.poputka.presentation.canvas.calendar_chart.calendar.ChartElement

object CalendarChartUtilsV {
    fun ChartData.forEach(
        drawScope: DrawScope,
        dayCellHeight: Float,
        startOffset: Int,
        progress: Float,
        block: (offset: Offset, radius: Float, alpha: Float, chartElement: ChartElement) -> Unit
    ) = with(drawScope) {

        val canvasWidth = size.width

        val ySteps = dayCellHeight
        val xSteps = canvasWidth / 7
        elements.forEachIndexed { index, chartElement ->

            val s = startOffset + index

            val xPosition =
                xSteps * (s % 7) + xSteps / 2
            val yPosition =
                (s / 7) * ySteps + ySteps / 2

            val alpha = 0.1f + (chartElement.value / maxRadiusValue) * (0.7f - 0.1f)
            val radius = 100f * chartElement.value / maxRadiusValue
            val offset = Offset(xPosition, yPosition)

            block(offset, radius, alpha, chartElement)
        }
    }
}

/*
fun draw(
    drawScope: DrawScope,
    canvas: Canvas,
    dayCellHeight: Float,
    daysInMonth: Int,
    startOffset: Int,
    calendarRows: Int
) = with(drawScope) {
    val canvasHeight = size.height
    val canvasWidth = size.width

    val ySteps = dayCellHeight
    val xSteps = canvasWidth / 7

    for (i in 0 until daysInMonth) {
        val s = startOffset + i

        val xPosition =
            xSteps * (s % 7) + xSteps / 2
        val yPosition =
            (s / 7) * ySteps + ySteps / 2

        canvas.nativeCanvas.drawCircle(xPosition, yPosition, 90f, circlePainter)
    }

}*/