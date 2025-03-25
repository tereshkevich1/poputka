package com.example.poputka.feature_journal.presentation.charts.bar_chart.xaxis.xaxis_markers

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
