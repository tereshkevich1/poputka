package com.example.poputka.feature_journal.presentation.charts.bar_chart.xaxis

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.drawscope.DrawScope

interface XAxisDrawer {
    fun requiredHeight(drawScope: DrawScope): Float

    fun drawAxisLine(
        drawScope: DrawScope,
        canvas: Canvas,
        drawableArea: Rect
    )

    fun drawAxisMarkersAndLabels(
        drawScope: DrawScope,
        canvas: Canvas,
        drawableArea: Rect
    )
}