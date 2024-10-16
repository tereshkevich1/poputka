package com.example.poputka.presentation.canvas.bar_graph

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.example.poputka.presentation.canvas.bar_graph.xaxis.XAxisDrawer
import com.example.poputka.presentation.canvas.bar_graph.yaxis.YAxisDrawer

object BarChartUtils {

    fun axisAreas(
        drawScope: DrawScope,
        totalSize: Size,
        xAxisDrawer: XAxisDrawer,
        yAxisDrawer: YAxisDrawer
    ): Pair<Rect, Rect> = with(drawScope) {

        val yAxisRight = yAxisDrawer.marginRight(this)
        val xAxisRight = totalSize.width
        val xAxisTop = totalSize.height - xAxisDrawer.requiredHeight(drawScope)
        val xAxisBottom = totalSize.height + xAxisDrawer.requiredHeight(drawScope)

        return Pair(
            Rect(yAxisRight, xAxisTop, xAxisRight, xAxisBottom),
            Rect(0f, 0f, yAxisRight, xAxisTop)
        )
    }

    fun barDrawableArea(xAxisArea: Rect): Rect {
        return Rect(
            left = xAxisArea.left,
            top = 0f,
            right = xAxisArea.right,
            bottom = xAxisArea.top
        )
    }


    fun Bars.forEachWithArea(
        barDrawableArea: Rect,
        progress: Float,
        barsCountForPeriod: Int,
        barGapCoefficient: Float,
        block: (barArea: Rect, bar: Bar) -> Unit
    ) {
        val widthOfBarArea = barDrawableArea.width / barsCountForPeriod
        val barGapPx = widthOfBarArea * barGapCoefficient

        return bars.forEachIndexed { index, bar ->
            val left = barDrawableArea.left + (index * widthOfBarArea)
            val height = barDrawableArea.height
            val barHeight = (height) * progress

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
