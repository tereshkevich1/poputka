package com.example.poputka.presentation.canvas

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp

object BarChartUtils {

    fun barDrawableArea(xAxisArea: Rect): Rect {
        return Rect(
            left = xAxisArea.left,
            top = 0f,
            right = xAxisArea.right,
            bottom = xAxisArea.top
        )
    }

    fun Bars.forEachWithArea(
        drawScope: DrawScope,
        barDrawableArea: Rect,
        progress: Float,
        valueDrawer: BarValueDrawer,
        barHorizontalMargin: Dp,
        block: (barArea: Rect, bar: Bar) -> Unit
    ) = with(drawScope){
        val totalBars = bars.size
        val widthOfBarArea = barDrawableArea.width / totalBars
        val barGapPx = barHorizontalMargin.toPx()

        bars.forEachIndexed { index, bar ->
            val left = barDrawableArea.left + (index * widthOfBarArea)
            val height = barDrawableArea.height

            val barHeight = (height - valueDrawer.requiredAboveBarHeight(drawScope)) * progress

            val barArea = Rect(
                left = left + barGapPx,
                top = barDrawableArea.bottom - (bar.value / maxBarValue) * barHeight,
                right = left + widthOfBarArea - barGapPx,
                bottom = barDrawableArea.bottom
            )
            block(barArea, bar)
        }
    }
}