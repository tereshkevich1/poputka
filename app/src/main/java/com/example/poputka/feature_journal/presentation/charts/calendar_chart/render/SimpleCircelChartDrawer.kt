package com.example.poputka.feature_journal.presentation.charts.calendar_chart.render

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint

class SimpleCircleChartDrawer {

    private val circlePainter = Paint().apply {
        isAntiAlias = true
    }

    fun draw(
        canvas: Canvas,
        offset: Offset,
        radius: Float,
        elementColor: Color
    ) = canvas.drawCircle(offset, radius, circlePainter.apply { color = elementColor })
}


