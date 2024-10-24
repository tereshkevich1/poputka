package com.example.poputka.presentation.canvas.bar_graph.xaxis.xaxis_markers

import android.graphics.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope

interface MarkerLabelDrawer {
    fun drawMarkersAndLabels(
        drawScope: DrawScope,
        canvas: Canvas,
        drawableArea: androidx.compose.ui.geometry.Rect,
        axisLinePaint: Paint,
        textPaint: android.graphics.Paint,
        textBounds: Rect
    )
}
