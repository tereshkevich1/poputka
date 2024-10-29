package com.example.poputka.presentation.canvas.bar_graph.render

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.drawscope.DrawScope

interface BarValueDrawer {
    fun requiredAboveBarHeight(drawScope: DrawScope): Float = 0f

    fun draw(
        drawScope: DrawScope,
        canvas: Canvas,
        totalSize: Size,
        maxBarValue: Float,
        value: Float,
        barArea: Rect,
        xAxisArea: Rect
    )
}