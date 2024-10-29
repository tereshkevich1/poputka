package com.example.poputka.presentation.canvas.bar_graph.render

import android.graphics.Paint.Align.CENTER
import android.graphics.RectF
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.poputka.presentation.canvas.common.Formatter
import com.example.poputka.presentation.canvas.common.toLegacyInt
import kotlin.math.abs

class SimpleBarValueDrawer(
    private val valueTextSize: TextUnit = 14.sp,
    private val valueTextColor: Color = Black,
    private val formatter: Formatter = { value -> "%.1f".format(value) },
    private val textBackgroundRectColor: Color = Color.LightGray,
    private val valueLineColor: Color = Color.LightGray,
    private val valueLineThickness: Dp = 2.dp,
    private val dashEffect: PathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f)),
    private val textPadding: Float = 5f,
    private val rectVerticalPadding: Float = 5f,
    private val rectHorizontalPaddingMultiplier: Float = 1f
) : BarValueDrawer {

    private val rectPaint = Paint().apply {
        isAntiAlias = true
        color = textBackgroundRectColor
        style = PaintingStyle.Fill
    }

    private val valueLinePaint = Paint().apply {
        isAntiAlias = true
        color = valueLineColor
        style = PaintingStyle.Stroke
        pathEffect = dashEffect
    }

    private val _labelTextArea: Float? = null
    private val textPaint = android.graphics.Paint().apply {
        isAntiAlias = true
        textAlign = CENTER
        color = valueTextColor.toLegacyInt()
    }

    private val textBounds = android.graphics.Rect()

    override fun requiredAboveBarHeight(drawScope: DrawScope): Float =
        (3f / 2f) * labelTextHeight(drawScope)

    override fun draw(
        drawScope: DrawScope,
        canvas: Canvas,
        totalSize: Size,
        maxBarValue: Float,
        value: Float,
        barArea: Rect,
        xAxisArea: Rect
    ) = with(drawScope) {

        val text = formatter(value)
        textPaint.getTextBounds(text, 0, text.length, textBounds)
        val textHeight = textBounds.height().toFloat()
        val textWidth = textBounds.width().toFloat()

        // Calculate xCenter for the bar line
        val barCenterX = barArea.left + (barArea.width / 2)

        // Center text horizontally, adjusted for canvas bounds
        val xCenterText = adjustXCenterForText(barCenterX, textWidth, totalSize)
        val rectBounds = calculateRectBounds(xCenterText, textWidth, textHeight)

        // Draw background rectangle with rounded corners
        canvas.drawRoundRect(
            rectBounds.left, rectBounds.top, rectBounds.right, rectBounds.bottom,
            50f, 50f, rectPaint
        )

        // Draw a connecting line in the center of the bar
        canvas.drawLine(
            p1 = Offset(barCenterX, 0f),
            p2 = Offset(barCenterX, barArea.bottom),
            paint = valueLinePaint.apply { strokeWidth = valueLineThickness.toPx() }
        )

        // Draw the text centered within the rectangle
        canvas.nativeCanvas.drawText(text, xCenterText, 0f, paint(drawScope))
    }

    // Helper function to adjust X-center for text within bounds
    private fun adjustXCenterForText(xCenter: Float, textWidth: Float, totalSize: Size): Float {
        val maxLeft = 0f
        val maxRight = totalSize.width
        val horizontalTextPadding = textWidth * rectHorizontalPaddingMultiplier
        return when {
            xCenter - horizontalTextPadding - textPadding < maxLeft ->
                xCenter + abs(xCenter - horizontalTextPadding - textPadding)

            xCenter + horizontalTextPadding + textPadding > maxRight ->
                xCenter - (xCenter + horizontalTextPadding + textPadding - maxRight)

            else -> xCenter
        }
    }

    // Helper function to calculate rectangle bounds for text
    private fun calculateRectBounds(xCenter: Float, textWidth: Float, textHeight: Float): RectF {
        val horizontalTextPadding = textWidth * rectHorizontalPaddingMultiplier
        val rectTop = -textHeight * 1.5f - rectVerticalPadding
        val rectBottom = textHeight * 0.5f + rectVerticalPadding
        return RectF(
            xCenter - horizontalTextPadding - textPadding,
            rectTop,
            xCenter + horizontalTextPadding + textPadding,
            rectBottom
        )
    }

    private fun labelTextHeight(drawScope: DrawScope) = with(drawScope) {
        _labelTextArea ?: ((3f / 2f) * valueTextSize.toPx())
    }

    private fun paint(drawScope: DrawScope) = with(drawScope) {
        textPaint.apply {
            this.textSize = valueTextSize.toPx()
        }
    }
}