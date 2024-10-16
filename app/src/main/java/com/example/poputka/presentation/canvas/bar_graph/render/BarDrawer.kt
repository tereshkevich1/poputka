package com.example.poputka.presentation.canvas.bar_graph.render


import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas

import androidx.compose.ui.graphics.drawscope.DrawScope
import com.example.poputka.presentation.canvas.bar_graph.Bar

interface BarDrawer {
    fun drawBar(
        drawScope: DrawScope,
        canvas: Canvas,
        barArea: Rect,
        bar: Bar
    )
}