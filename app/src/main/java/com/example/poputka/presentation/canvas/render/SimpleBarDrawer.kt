package com.example.poputka.presentation.canvas.render

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.example.poputka.presentation.canvas.Bar

class SimpleBarDrawer : BarDrawer {
    private val barPaint = Paint().apply {
        this.isAntiAlias = true
    }

    override fun drawBar(
        drawScope: DrawScope,
        canvas: Canvas,
        barArea: Rect,
        bar: Bar
    ) {
        canvas.drawRect(barArea, barPaint.apply {
            //color = bar.color
            color = Color.BLUE
        })
    }
}