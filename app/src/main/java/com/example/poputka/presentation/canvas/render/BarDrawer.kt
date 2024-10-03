package com.example.poputka.presentation.canvas.render

import android.graphics.Canvas
import android.graphics.Rect
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.example.poputka.presentation.canvas.Bar

interface BarDrawer {
    fun drawBar(
        drawScope: DrawScope,
        canvas: Canvas,
        barArea: Rect,
        bar: Bar
    )
}