package com.example.poputka.feature_journal.presentation.charts.bar_chart.xaxis

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.poputka.presentation.canvas.bar_chart.xaxis.graph_modes.BaseChartMode
import com.example.poputka.presentation.canvas.bar_chart.xaxis.graph_modes.DayMode
import com.example.poputka.feature_journal.presentation.charts.common.toLegacyInt

class BarXAxisDrawer(
    private val axisLineThickness: Dp = 2.dp,
    private val axisLineColor: Color = Color.DarkGray,
    private val labelTextSize: TextUnit = 12.sp,
    private val markerHeight: Float = 10f,
    private val labelMarkerSpacing: Float = 20f,
    val chartMode: BaseChartMode,
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

    override fun requiredHeight(drawScope: DrawScope): Float = with(drawScope) {
        textPaint.textSize = labelTextSize.toPx()
        textPaint.getTextBounds("0", 0, 1, textBounds)
        val textHeight = textBounds.height().toFloat()
        return textHeight + markerHeight + labelMarkerSpacing
    }

    override fun drawAxisLine(drawScope: DrawScope, canvas: Canvas, drawableArea: Rect) =
        with(drawScope) {
            val lineThickness = axisLineThickness.toPx()
            val y = drawableArea.top + (lineThickness / 2f)

            canvas.drawLine(
                p1 = Offset(
                    x = drawableArea.left - lineThickness / 2,
                    y = y
                ),
                p2 = Offset(
                    x = drawableArea.right + lineThickness / 2,
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
    ) {
        val markerLabelDrawer = when (chartMode) {
            is DayMode -> com.example.poputka.feature_journal.presentation.charts.bar_chart.xaxis.xaxis_markers.SimpleMarkerLabelDrawer(
                markerHeight,
                axisLineThickness,
                labelMarkerSpacing,
                labelTextSize,
                chartMode,
                false
            )

            else -> com.example.poputka.feature_journal.presentation.charts.bar_chart.xaxis.xaxis_markers.SimpleMarkerLabelDrawer(
                markerHeight,
                axisLineThickness,
                labelMarkerSpacing,
                labelTextSize,
                chartMode,
                true
            )
        }
        markerLabelDrawer.drawMarkersAndLabels(
            drawScope,
            canvas,
            drawableArea,
            axisLinePaint,
            textPaint,
            textBounds
        )
    }
}