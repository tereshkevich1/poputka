package com.example.poputka.presentation.canvas.bar_chart.render


import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas

import androidx.compose.ui.graphics.drawscope.DrawScope
import com.example.poputka.presentation.canvas.bar_chart.Bar

interface BarDrawer {
    fun drawBar(
        drawScope: DrawScope,
        canvas: Canvas,
        barArea: Rect,
        bar: Bar
    )
}