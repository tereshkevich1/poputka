package com.example.poputka.presentation.canvas.bar_graph.xaxis

import android.graphics.Paint.Align
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.poputka.presentation.canvas.bar_graph.xaxis.graph_modes.ChartMode
import com.example.poputka.presentation.canvas.common.toLegacyInt

class BarXAxisDrawer(
    private val axisLineThickness: Dp = 1.dp,
    private val axisLineColor: Color = Color.Black,
    private val labelTextSize: TextUnit = 12.sp,
    private val markerHeight: Float = 10f,
    private val labelMarkerSpacing: Float = 20f,
    val chartMode: ChartMode
) : XAxisDrawer {

    private val textPaint = android.graphics.Paint().apply {
        isAntiAlias = true
        color = axisLineColor.toLegacyInt()
    }
    private val textBounds = android.graphics.Rect()

    private val axisLinePaint = Paint().apply {
        isAntiAlias = true
        color = axisLineColor
        style = PaintingStyle.Stroke
    }

    private fun calculateMarkerAndLabelHeight(drawScope: DrawScope): Float = with(drawScope) {
        textPaint.textSize = labelTextSize.toPx()
        textPaint.getTextBounds("0", 0, 1, textBounds)
        val textHeight = textBounds.height().toFloat()
        return textHeight + markerHeight + labelMarkerSpacing
    }

    override fun requiredHeight(drawScope: DrawScope): Float = calculateMarkerAndLabelHeight(drawScope)

    override fun drawAxisLine(drawScope: DrawScope, canvas: Canvas, drawableArea: Rect) =
        with(drawScope) {
            val lineThickness = axisLineThickness.toPx()
            val y = drawableArea.top + (lineThickness / 2f)

            canvas.drawLine(
                p1 = Offset(
                    x = drawableArea.left,
                    y = y
                ),
                p2 = Offset(
                    x = drawableArea.right,
                    y = y
                ),
                paint = axisLinePaint.apply {
                    strokeWidth = lineThickness
                }
            )
        }

    override fun drawAxisMarkersAndLabels(
        drawScope: DrawScope,
        canvas: Canvas,
        drawableArea: Rect
    ) = with(drawScope) {
        val numberOfBars = chartMode.getBarCount()
        val numberOfMarkers = chartMode.getMarkerCount()

        val labelStep = chartMode.getLabelStep()

        val lineThickness = axisLineThickness.toPx()
        val y = drawableArea.top + (lineThickness / 2f)
        val labelPaint = textPaint.apply {
            textSize = labelTextSize.toPx()
            textAlign = Align.CENTER
        }

        val labels = chartMode.getLabels()
        val barWidth = drawableArea.width / numberOfBars

        for (markerIndex in 0 until numberOfMarkers) {
            val barIndex = markerIndex * labelStep

            if (barIndex < numberOfBars) {
                val x = drawableArea.left + barIndex * barWidth + barWidth / 2

                canvas.drawLine(
                    p1 = Offset(x = x, y = y),
                    p2 = Offset(x = x, y = y + markerHeight),
                    paint = axisLinePaint.apply { strokeWidth = lineThickness }
                )

                val label = labels.getOrNull(markerIndex) ?: ""
                labelPaint.getTextBounds(label, 0, label.length, textBounds)

                val textY = drawableArea.top + markerHeight + labelMarkerSpacing + textBounds.height()
                canvas.nativeCanvas.drawText(label, x, textY, labelPaint)
            }
        }

        val lastBarIndex = numberOfBars - 1
        val lastMarkerX = drawableArea.left + lastBarIndex * barWidth + barWidth / 2

        if (lastBarIndex % labelStep != 0) {
            canvas.drawLine(
                p1 = Offset(x = lastMarkerX, y = y),
                p2 = Offset(x = lastMarkerX, y = y + markerHeight),
                paint = axisLinePaint.apply { strokeWidth = lineThickness }
            )

            val lastLabel = labels.lastOrNull() ?: ""
            labelPaint.getTextBounds(lastLabel, 0, lastLabel.length, textBounds)

            val lastTextY = drawableArea.top + markerHeight + labelMarkerSpacing + textBounds.height()
            canvas.nativeCanvas.drawText(lastLabel, lastMarkerX, lastTextY, labelPaint)
        }
    }
}