package com.example.poputka.presentation.canvas.bar_graph.xaxis.xaxis_markers

import android.graphics.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import com.example.poputka.presentation.canvas.bar_graph.xaxis.graph_modes.BaseChartMode

interface MarkerLabelDrawer {
    fun drawMarkersAndLabels(
        drawScope: DrawScope,
        canvas: Canvas,
        drawableArea: androidx.compose.ui.geometry.Rect,
        axisLinePaint: Paint,
        textPaint: android.graphics.Paint,
        markerHeight: Float,
        axisLineThickness: Dp,
        labelMarkerSpacing: Float,
        labelTextSize: TextUnit,
        chartMode: BaseChartMode,
        textBounds: Rect
    )
}
