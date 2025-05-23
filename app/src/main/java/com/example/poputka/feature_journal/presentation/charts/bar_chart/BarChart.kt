package com.example.poputka.feature_journal.presentation.charts.bar_chart

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.poputka.feature_journal.presentation.charts.bar_chart.BarChartUtils.axisAreas
import com.example.poputka.feature_journal.presentation.charts.bar_chart.BarChartUtils.barDrawableArea
import com.example.poputka.feature_journal.presentation.charts.bar_chart.BarChartUtils.forEachWithArea
import com.example.poputka.feature_journal.presentation.charts.bar_chart.animation.fadeInAnimation
import com.example.poputka.feature_journal.presentation.charts.bar_chart.render.SimpleBarDrawer
import com.example.poputka.feature_journal.presentation.charts.bar_chart.xaxis.BarXAxisDrawer
import com.example.poputka.feature_journal.presentation.charts.bar_chart.xaxis.XAxisDrawer
import com.example.poputka.feature_journal.presentation.charts.bar_chart.xaxis.graph_modes.BaseChartMode
import com.example.poputka.feature_journal.presentation.charts.bar_chart.xaxis.graph_modes.DayMode
import com.example.poputka.feature_journal.presentation.charts.bar_chart.yaxis.BarYAxisWithValueDrawer
import com.example.poputka.feature_journal.presentation.charts.bar_chart.yaxis.YAxisDrawer
import com.example.poputka.feature_journal.presentation.charts.common.render.ChartValueDrawer
import com.example.poputka.feature_journal.presentation.charts.common.render.SimpleChartValueDrawer
import com.example.poputka.ui.theme.PoputkaTheme
import kotlin.random.Random

@Composable
fun BarChart(
    bars: Bars,
    chartMode: BaseChartMode,
    modifier: Modifier = Modifier,
    onBarTap: (bar: Bar) -> Unit = {},
    animation: AnimationSpec<Float> = fadeInAnimation(),
    xAxisDrawer: XAxisDrawer = BarXAxisDrawer(chartMode = chartMode),
    yAxisDrawer: YAxisDrawer = BarYAxisWithValueDrawer(),
    valueBarDrawer: ChartValueDrawer = SimpleChartValueDrawer()
) {
    val rectangles = remember(bars.bars) { mutableStateMapOf<Bar, Rect>() }
    val barDrawer = SimpleBarDrawer()

    val transitionAnimation = remember(bars.bars) { Animatable(initialValue = 0f) }
    LaunchedEffect(bars.bars) {
        transitionAnimation.animateTo(1f, animationSpec = animation)
    }

    var selectedBar by remember {
        mutableStateOf<Pair<Bar, Rect>?>(null)
    }

    Canvas(modifier = modifier
        .fillMaxSize()
        .pointerInput(bars.bars) {
            detectBarByDragGestures(chartMode, rectangles) { selectedBar = it }
        }
        .pointerInput(bars.bars) {
            detectTapGestures { offset ->
                rectangles
                    .filter { it.value.contains(offset) }
                    .forEach { onBarTap(it.key) }
            }
        }
    ) {
        drawIntoCanvas { canvas ->
            val (xAxisArea, yAxisArea) = axisAreas(this, size, xAxisDrawer, yAxisDrawer)
            val barDrawableArea = barDrawableArea(xAxisArea)

            yAxisDrawer.drawAxisLabels(
                this, canvas, yAxisArea, bars.minYValue, bars.maxYValue, bars.achievementValue
            )

            xAxisDrawer.drawAxisLine(this, canvas, xAxisArea)
            xAxisDrawer.drawAxisMarkersAndLabels(this, canvas, xAxisArea)


            bars.forEachWithArea(
                barDrawableArea,
                transitionAnimation.value,
                chartMode.getBarCount(),
                chartMode.getBarGapCoefficient()
            ) { barArea, bar ->
                Log.d("draw", " barDrawer.drawBat")
                barDrawer.drawBar(this, canvas, barArea, bar)
                rectangles[bar] = barArea
            }

            selectedBar?.let {
                Log.d("draw", "drawValueBar")
                drawValueBar(canvas, it.second, it.first.value, valueBarDrawer)
            }
        }
    }
}

private fun DrawScope.drawValueBar(
    canvas: Canvas,
    barArea: Rect,
    barValue: Float,
    valueBarDrawer: ChartValueDrawer
) {
    val barCenterX = barArea.left + (barArea.width / 2)
    valueBarDrawer.draw(
        drawScope = this,
        canvas = canvas,
        totalSize = size,
        centerXPosition = barCenterX,
        startYPosition = 0f,
        endYPosition = barArea.bottom,
        indicatorCenterY = barArea.top,
        value = barValue
    )
}

private suspend fun PointerInputScope.detectBarByDragGestures(
    chartMode: BaseChartMode,
    rectangles: Map<Bar, Rect>,
    onBarSelected: (Pair<Bar, Rect>?) -> Unit
) {
    val barWidthWithoutGap = size.width / chartMode.getBarCount()
    val barGapPadding = barWidthWithoutGap * chartMode.getBarGapCoefficient()

    detectDragGestures(onDrag = { change, _ ->
        onBarSelected(findBarAtPosition(change.position, rectangles, barGapPadding))
    }, onDragEnd = { onBarSelected(null) })
}

private fun findBarAtPosition(
    position: Offset,
    rectangles: Map<Bar, Rect>,
    barGapPadding: Float
): Pair<Bar, Rect>? {
    return rectangles.entries
        .firstOrNull { (_, rect) -> rect.containsHorizontally(position, barGapPadding) }
        ?.toPair()
}

private fun Rect.containsHorizontally(
    offset: Offset,
    barGapPadding: Float
): Boolean {
    val paddedLeft = this.left - barGapPadding
    val paddedRight = this.right + barGapPadding
    return offset.x in paddedLeft..paddedRight
}

@Composable
@Preview
fun BarChartPreviewV2() {
    PoputkaTheme(darkTheme = true) {
        Surface {
            Column(
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                var showChart by remember { mutableStateOf(false) }
                val chartMode = DayMode()

                val numberOfBars = chartMode.getBarCount()
                val max = 10.0f
                val min = 0f

                val barsListM = Bars(
                    bars = (1..numberOfBars).map {
                        Bar(
                            label = "BAR$it",
                            value = Random.nextFloat() * (max - min) + min
                        )
                    }, achievementValue = Random.nextFloat() * (max - min) + min
                )

                Button(onClick = { showChart = !showChart }) {
                    Text(text = if (showChart) "Hide" else "Show")
                }

                if (showChart) {
                    BarChart(
                        bars = barsListM,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp),
                        animation = fadeInAnimation(1500),
                        chartMode = chartMode
                    )
                }
            }
        }
    }
}
