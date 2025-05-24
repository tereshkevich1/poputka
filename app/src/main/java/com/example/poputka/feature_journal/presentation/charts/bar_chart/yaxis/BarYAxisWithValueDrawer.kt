package com.example.poputka.feature_journal.presentation.charts.bar_chart.yaxis

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.poputka.feature_journal.presentation.charts.common.Formatter
import com.example.poputka.feature_journal.presentation.charts.common.toLegacyInt
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.roundToInt

class BarYAxisWithValueDrawer(
    private val labelTextSize: TextUnit = 12.sp,
    private val labelTextColor: Color = Color.DarkGray,
    private val labelRatio: Int = 5,
    private val labelValueFormatter: Formatter = { value -> "%.1f".format(value) },
    private val axisLineThickness: Dp = 1.dp,
    private val axisLineColor: Color = Color.DarkGray,
    private val achievementLineColor: Color = Color.Blue,
    private val achievementLineThickness: Dp = 1.dp,
    private val spacingFactor: Float = 0.07f,
    private val dashEffect: PathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
) : YAxisDrawer {
    private val axisLinePaint = Paint().apply {
        isAntiAlias = true
        color = axisLineColor
        style = PaintingStyle.Stroke
    }
    private val achievementLinePaint = Paint().apply {
        isAntiAlias = true
        color = achievementLineColor
        style = PaintingStyle.Stroke
        pathEffect = dashEffect
    }
    private val textPaint = android.graphics.Paint().apply {
        isAntiAlias = true
        color = labelTextColor.toLegacyInt()
    }
    private val textBounds = android.graphics.Rect()

    override fun drawAxisLabels(
        drawScope: DrawScope,
        canvas: Canvas,
        drawableArea: Rect,
        minValue: Float,
        maxValue: Float,
        achievementValue: Float
    ) = with(drawScope) {
        val labelPaint = textPaint.apply {
            textSize = labelTextSize.toPx()
            textAlign = android.graphics.Paint.Align.RIGHT
        }

        val totalHeight = drawableArea.height
        val minLabelHeight = (labelTextSize.toPx() * labelRatio.toFloat())

        val labelCount = max((drawableArea.height / minLabelHeight).roundToInt(), 2)

        val x = marginRight(drawScope)
        val xText = x - axisLineThickness.toPx() - (labelTextSize.toPx() / 2f)

        for (i in 1..labelCount) {
            val value = minValue + (i * ((maxValue - minValue) / labelCount))

            //if the distance between the achievementValue and the usual one is less than threshold , don't draw
            if (abs(value - achievementValue) < (maxValue - 0f) * spacingFactor) continue

            val label = labelValueFormatter(value)
            labelPaint.getTextBounds(label, 0, label.length, textBounds)

            val y = drawableArea.bottom - (i * (totalHeight / labelCount))
            val yText = y + (textBounds.height() / 2f)

            canvas.nativeCanvas.drawText(label, xText, yText, labelPaint)

            canvas.drawLine(
                p1 = Offset(
                    x = x,
                    y = y
                ),
                p2 = Offset(
                    x = drawableArea.right,
                    y = y
                ),
                paint = axisLinePaint.apply {
                    strokeWidth = axisLineThickness.toPx()
                }
            )
        }

        drawAchievementValue(
            drawScope,
            canvas,
            drawableArea,
            maxValue,
            achievementValue,
            totalHeight,
            x,
            xText
        )
    }

    private fun drawAchievementValue(
        drawScope: DrawScope,
        canvas: Canvas,
        drawableArea: Rect,
        maxValue: Float,
        achievementValue: Float,
        totalHeight: Float,
        x: Float,
        xText: Float,
    ) {
        with(drawScope) {
            val normalizedValue = achievementValue / maxValue
            val label = labelValueFormatter(achievementValue)

            val y = drawableArea.bottom - (normalizedValue * totalHeight)
            val yText = y + (textBounds.height() / 2f)

            canvas.drawLine(
                p1 = Offset(x, y),
                p2 = Offset(drawableArea.right, y),
                paint = achievementLinePaint.apply {
                    strokeWidth = achievementLineThickness.toPx()
                }
            )
            canvas.nativeCanvas.drawText(label, xText, yText, textPaint)
        }
    }

    override fun marginRight(drawScope: DrawScope): Float {
        with(drawScope) {
            return minOf(50.dp.toPx(), size.width * 10f / 100f)
        }
    }
}