package com.example.poputka.feature_journal.presentation.charts.bar_chart

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.example.poputka.feature_journal.presentation.charts.bar_chart.xaxis.XAxisDrawer
import com.example.poputka.feature_journal.presentation.charts.bar_chart.yaxis.YAxisDrawer

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
            Rect(0f, 0f, xAxisRight, xAxisTop)
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
                top = barDrawableArea.bottom - (bar.value / maxYValue) * barHeight,
                right = left + widthOfBarArea - barGapPx,
                bottom = barDrawableArea.bottom
            )
            block(barArea, bar)
        }
    }
}
