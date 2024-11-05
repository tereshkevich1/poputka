package com.example.poputka.presentation.canvas.calendar_chart.render

import androidx.compose.ui.geometry.Offset

interface CalendarDrawer {
    fun drawHeader(
        month: String,
        year: Int,
        canvasWidth: Float
    )

    fun drawDay(
        day: Int,
        position: Offset,
        textSize: Float,
        isSelected: Boolean = false
    )
}