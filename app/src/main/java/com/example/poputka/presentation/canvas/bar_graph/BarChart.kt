package com.example.poputka.presentation.canvas.bar_graph

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.poputka.presentation.canvas.bar_graph.BarChartUtils.axisAreas
import com.example.poputka.presentation.canvas.bar_graph.BarChartUtils.barDrawableArea
import com.example.poputka.presentation.canvas.bar_graph.BarChartUtils.forEachWithArea
import com.example.poputka.presentation.canvas.bar_graph.animation.fadeInAnimation
import com.example.poputka.presentation.canvas.bar_graph.render.SimpleBarDrawer
import com.example.poputka.presentation.canvas.bar_graph.xaxis.BarXAxisDrawer
import com.example.poputka.presentation.canvas.bar_graph.xaxis.XAxisDrawer
import com.example.poputka.presentation.canvas.bar_graph.xaxis.graph_modes.ChartMode
import com.example.poputka.presentation.canvas.bar_graph.xaxis.graph_modes.DayMode
import com.example.poputka.presentation.canvas.bar_graph.xaxis.graph_modes.MonthMode
import com.example.poputka.presentation.canvas.bar_graph.xaxis.graph_modes.WeekMode
import com.example.poputka.presentation.canvas.bar_graph.yaxis.BarYAxisWithValueDrawer
import com.example.poputka.presentation.canvas.bar_graph.yaxis.YAxisDrawer
import kotlin.random.Random


@Composable
fun BarChart(
    modifier: Modifier = Modifier,
    bars: Bars,
    animation: AnimationSpec<Float> = fadeInAnimation(),
    chartMode: ChartMode,
    xAxisDrawer: XAxisDrawer = BarXAxisDrawer(chartMode = chartMode),
    yAxisDrawer: YAxisDrawer = BarYAxisWithValueDrawer()
) {
    val transitionAnimation = remember(bars.bars) { Animatable(initialValue = 0f) }
    val rectangles =
        remember(bars.bars) { mutableStateMapOf<Bar, Rect>() }
    val barDrawer = SimpleBarDrawer()
    LaunchedEffect(bars.bars) {
        transitionAnimation.animateTo(1f, animationSpec = animation)
    }

    Canvas(modifier = modifier
        .fillMaxSize()
        .pointerInput(bars.bars) {
            detectTapGestures { offset ->
                rectangles
                    .filter { it.value.contains(offset) }
                    .forEach { it.key.onTap(it.key) }
            }
        }) {
        drawIntoCanvas { canvas ->

            val (xAxisArea, yAxisArea) = axisAreas(this, size, xAxisDrawer, yAxisDrawer)
            val barDrawableArea = barDrawableArea(xAxisArea)

            yAxisDrawer.drawAxisLabels(this, canvas, yAxisArea, bars.minYValue, bars.maxYValue)
            yAxisDrawer.drawAxisLine(this, canvas, yAxisArea)

            xAxisDrawer.drawAxisLine(this, canvas, xAxisArea)
            xAxisDrawer.drawAxisMarkersAndLabels(this, canvas, xAxisArea)

            bars.forEachWithArea(
                barDrawableArea,
                transitionAnimation.value,
                chartMode.getBarCount(),
                chartMode.getBarGapCoefficient()
            ) { barArea, bar ->
                barDrawer.drawBar(this, canvas, barArea, bar)
                rectangles[bar] = barArea
            }
        }
    }

}

@Composable
@Preview
fun BarChartPreview() {
    Column(
        modifier = Modifier
            .padding(horizontal = 30.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val numberOfBars = 48

        BarChart(
            bars = Bars(
                bars = (1..numberOfBars).map {
                    Bar(label = "BAR$it", value = Random.nextFloat()) { bar ->

                    }
                }),
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp),
            animation = fadeInAnimation(3000),
            chartMode = WeekMode(),
        )
    }
}


@Composable
@Preview
fun BarChartPreviewV2() {
    Column(
        modifier = Modifier
            .padding(horizontal = 30.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var showChart by remember { mutableStateOf(false) }


        Button(onClick = { showChart = !showChart }) {
            Text(text = if (showChart) "Скрыть график" else "Показать график")
        }


        if (showChart) {
            val numberOfBars = 31

            BarChart(
                bars = Bars(
                    bars = (1..numberOfBars).map {
                        Bar(label = "BAR$it", value = Random.nextFloat()) { bar ->

                        }
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp),
                animation = fadeInAnimation(3000),
                chartMode = MonthMode(31),
            )
        }
    }
}