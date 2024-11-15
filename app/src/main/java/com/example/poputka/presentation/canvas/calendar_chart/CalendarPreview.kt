package com.example.poputka.presentation.canvas.calendar_chart

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
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.poputka.presentation.canvas.DateNavigationBar
import com.example.poputka.presentation.canvas.bar_chart.animation.fadeInAnimation
import com.example.poputka.presentation.canvas.calendar_chart.CalendarChartUtils.toPx
import com.example.poputka.presentation.canvas.calendar_chart.CalendarChartUtilsV.forEach
import com.example.poputka.presentation.canvas.calendar_chart.calendar.ChartData
import com.example.poputka.presentation.canvas.calendar_chart.calendar.ChartElement
import com.example.poputka.presentation.canvas.calendar_chart.render.SimpleCalendarDrawer
import com.example.poputka.presentation.canvas.calendar_chart.render.SimpleCircleChartDrawer
import com.example.poputka.presentation.canvas.common.render.ChartValueDrawer
import com.example.poputka.presentation.canvas.common.render.SimpleChartValueDrawer
import com.example.poputka.ui.theme.PoputkaTheme
import java.time.YearMonth
import kotlin.random.Random

@Composable
fun CalendarChart(
    currentMonth: YearMonth,
    chartData: ChartData,
    modifier: Modifier = Modifier,
    animation: AnimationSpec<Float> = fadeInAnimation(1000),
    simpleCalendarDrawer: SimpleCalendarDrawer = remember { SimpleCalendarDrawer() },
    calendarCalculator: CalendarCalculator = remember { CalendarSimpleCalculator() },
    circleChartDrawer: SimpleCircleChartDrawer = remember { SimpleCircleChartDrawer() },
    dayCellHeight: Dp = 52.dp,
    valueBarDrawer: ChartValueDrawer = SimpleChartValueDrawer()
) {
    val transitionAnimation = remember(chartData.elements) { Animatable(initialValue = 0f) }
    LaunchedEffect(chartData.elements) {
        transitionAnimation.animateTo(1f, animationSpec = animation)
    }

    var position by remember {
        mutableStateOf<Int?>(null)
    }

    val context = LocalContext.current
    val daysInMonth = currentMonth.lengthOfMonth()
    val startOffset = CalendarChartUtils.calculateStartOffset(currentMonth)
    Log.d("currentMonth", "$startOffset  $currentMonth")
    val calendarRows = CalendarChartUtils.calculateCalendarRows(daysInMonth, startOffset)
    val canvasHeight = CalendarChartUtils.getCanvasHeight(
        context,
        dayCellHeight,
        simpleCalendarDrawer.requiredHeightForHeader(),
        calendarRows
    )

    var canvasSize by remember {
        mutableStateOf(Size.Zero)
    }

    Log.d("CalendarChart", "parameters")

    Canvas(modifier = modifier
        .fillMaxWidth()
        .height(canvasHeight)
        .pointerInput(chartData.elements) {
            detectDragGestures(onDrag = { change, _ ->
                val offset = change.position
                val newPosition = calendarCalculator.getDayIndexFromDragPosition(
                    offset, canvasSize, daysInMonth,
                    calendarRows, startOffset
                )
                if (newPosition != position) position = newPosition

            }, onDragEnd = { position = null })
        }
        .pointerInput(Unit) {
            detectTapGestures(onTap = { offset ->
                Log.d(
                    "detectTapGestures",
                    calendarCalculator
                        .getDayIndexFromTapPosition(
                            offset,
                            canvasSize,
                            daysInMonth,
                            calendarRows,
                            startOffset
                        )
                        .toString()
                )
                calendarCalculator.getDayIndexFromTapPosition(
                    offset,
                    canvasSize,
                    daysInMonth,
                    calendarRows,
                    startOffset
                )
            })
        }) {

        val adjustedCanvasHeight = size.height - simpleCalendarDrawer.requiredHeightForHeader()
        val canvasWidth = size.width
        canvasSize = Size(canvasWidth, adjustedCanvasHeight)

        val dayCellHeightPx = dayCellHeight.toPx(context)

        drawIntoCanvas { canvas ->
            Log.d("drawIntoCanvas", "draw")
            chartData.forEach(
                this,
                dayCellHeightPx,
                startOffset,
                transitionAnimation.value
            ) { offset, radius, alpha, chartElement ->
                circleChartDrawer.draw(
                    canvas,
                    offset,
                    radius,
                    chartElement.color.copy(alpha = alpha)
                )
            }
            simpleCalendarDrawer.drawWeekHeader(this, canvas)
            simpleCalendarDrawer.drawCalendar(
                this,
                canvas,
                dayCellHeightPx,
                daysInMonth,
                startOffset,
                calendarRows
            )

            position?.let {
                Log.d("column", "position?.let - $it")
                val _position = it
                val selectionElement = chartData.elements.getOrNull(_position)
                selectionElement?.let {

                    val ySteps = dayCellHeightPx
                    val xSteps = canvasWidth / 7


                    val s = startOffset + _position
                    val centerXPosition =
                        xSteps * (s % 7) + xSteps / 2
                    val endYPosition =
                        (s / 7) * ySteps + ySteps / 2

                    valueBarDrawer.draw(
                        this,
                        canvas,
                        size,
                        centerXPosition,
                        endYPosition - 180f,
                        endYPosition,
                        endYPosition,
                        it.value
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun CalendarPreview() {
    PoputkaTheme {
        Surface {
            val viewModel = CalendarViewModel()
            Column(
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .fillMaxWidth()
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PoputkaTheme(darkTheme = true) {
                    val currentDate by viewModel.currentDate.collectAsState()

                    val elements = List(currentDate.lengthOfMonth()) {
                        ChartElement(
                            value = Random.nextFloat() * (1000f - 200f) + 200f,
                            color = Color.Blue,
                            onTap = { }
                        )
                    }

                    DateNavigationBar(
                        modifier = Modifier,
                        currentDate = currentDate.toString(),
                        onNext = { viewModel.nextMonth() },
                        onPrevious = { viewModel.previousMonth() })
                    CalendarChart(currentMonth = currentDate, ChartData(elements))
                }
            }
        }
    }
}

