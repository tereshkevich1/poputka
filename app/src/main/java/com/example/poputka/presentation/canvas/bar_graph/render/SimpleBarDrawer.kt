package com.example.poputka.presentation.canvas.bar_graph.render


import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.example.poputka.presentation.canvas.bar_graph.Bar

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
        barPaint.color = bar.color
        val cornerRadius = bar.cornerRadius
        val path = Path().apply {
            addRoundRect(
                RoundRect(barArea, topLeft = cornerRadius, topRight = cornerRadius),
                Path.Direction.Clockwise
            )
        }
        canvas.drawPath(path, barPaint)
    }
}