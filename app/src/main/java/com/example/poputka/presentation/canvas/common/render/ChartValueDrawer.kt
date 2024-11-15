package com.example.poputka.presentation.canvas.common.render

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.drawscope.DrawScope

interface ChartValueDrawer {
    fun draw(
        drawScope: DrawScope,
        canvas: Canvas,
        totalSize: Size,
        centerXPosition: Float,
        startYPosition: Float,
        endYPosition: Float,
        indicatorCenterY: Float,
        value: Float
    )
}