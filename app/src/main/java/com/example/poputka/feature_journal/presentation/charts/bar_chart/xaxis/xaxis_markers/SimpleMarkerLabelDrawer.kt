package com.example.poputka.feature_journal.presentation.charts.bar_chart.xaxis.xaxis_markers

import android.graphics.Paint.Align
import android.graphics.Rect
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import com.example.poputka.presentation.canvas.bar_chart.xaxis.graph_modes.BaseChartMode

class SimpleMarkerLabelDrawer(
    private val markerHeight: Float,
    private val axisLineThickness: Dp,
    private val labelMarkerSpacing: Float,
    private val labelTextSize: TextUnit,
    private val chartMode: BaseChartMode,
    private val drawMarkersBetweenBars: Boolean = true
) : com.example.poputka.feature_journal.presentation.charts.bar_chart.xaxis.xaxis_markers.MarkerLabelDrawer {
    override fun drawMarkersAndLabels(
        drawScope: DrawScope,
        canvas: Canvas,
        drawableArea: androidx.compose.ui.geometry.Rect,
        axisLinePaint: Paint,
        textPaint: android.graphics.Paint,
        textBounds: Rect
    ) = with(drawScope) {

        val labelPaint = textPaint.apply {
            textSize = labelTextSize.toPx()
            textAlign = Align.CENTER
        }
        val labels = chartMode.retrieveLabels()
        val labelStep = chartMode.getLabelStep()

        val numberOfBars = chartMode.getBarCount()
        val numberOfMarkers = chartMode.getMarkerCount()

        val barWidth = drawableArea.width / numberOfBars

        val lineThickness = axisLineThickness.toPx()

        val y = drawableArea.top + (lineThickness / 2f)
        val textY =
            drawableArea.top + markerHeight + labelMarkerSpacing + textBounds.height()

        val markerBaseX = if (drawMarkersBetweenBars) drawableArea.left + barWidth / 2
        else drawableArea.left

        for (markerIndex in 0 until numberOfMarkers) {
            val barIndex = markerIndex * labelStep

            val x = markerBaseX + barIndex * barWidth
            val label = labels.getOrNull(markerIndex) ?: ""

            canvas.drawLine(
                p1 = Offset(x = x, y = y),
                p2 = Offset(x = x, y = y + markerHeight),
                paint = axisLinePaint
            )
            canvas.nativeCanvas.drawText(label, x, textY, labelPaint)
        }
    }
}
