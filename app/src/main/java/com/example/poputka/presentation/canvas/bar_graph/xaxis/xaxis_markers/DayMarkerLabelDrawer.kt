package com.example.poputka.presentation.canvas.bar_graph.xaxis.xaxis_markers

import android.graphics.Paint.Align
import android.graphics.Rect
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import com.example.poputka.presentation.canvas.bar_graph.xaxis.graph_modes.BaseChartMode

class DayMarkerLabelDrawer : MarkerLabelDrawer {
    override fun drawMarkersAndLabels(
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
    ) = with(drawScope) {
        val numberOfBars = chartMode.getBarCount()
        val numberOfMarkers = chartMode.getMarkerCount()

        val labels = chartMode.retrieveLabels()
        val labelStep = chartMode.getLabelStep()

        val barWidth = drawableArea.width / numberOfBars

        val lineThickness = axisLineThickness.toPx()
        val y = drawableArea.top + (lineThickness / 2f)
        val labelPaint = textPaint.apply {
            textSize = labelTextSize.toPx()
            textAlign = Align.CENTER
        }

        for (markerIndex in 0 until numberOfMarkers) {
            val barIndex = markerIndex * labelStep

            if (barIndex <= numberOfBars) {
                val x = drawableArea.left + barIndex * barWidth

                canvas.drawLine(
                    p1 = Offset(x = x, y = y),
                    p2 = Offset(x = x, y = y + markerHeight),
                    paint = axisLinePaint.apply { strokeWidth = lineThickness }
                )

                val label = labels.getOrNull(markerIndex) ?: ""
                labelPaint.getTextBounds(label, 0, label.length, textBounds)

                val textY =
                    drawableArea.top + markerHeight + labelMarkerSpacing + textBounds.height()
                canvas.nativeCanvas.drawText(label, x, textY, labelPaint)
            }
        }
    }
}
