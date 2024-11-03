package com.example.poputka.presentation.canvas.calendar_chart

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.poputka.R
import com.example.poputka.ui.theme.PoputkaTheme

@Composable
fun CalendarChart(
    modifier: Modifier = Modifier,
    simpleCalendarDrawer: SimpleCalendarDrawer = SimpleCalendarDrawer(),
    viewModel: CalendarViewModel = CalendarViewModel()
) {

    val currentMonth by viewModel.currentDate.collectAsState()

    var canvasSize by remember {
        mutableStateOf(Size.Zero)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        DateNavigationBar(
            modifier = Modifier.padding(bottom = 8.dp),
            currentDate = viewModel.getCurrentMonthAsString(),
            onPrevious = {viewModel.previousMonth()},
            onNext = {viewModel.nextMonth()})

        Canvas(modifier = modifier
            .fillMaxSize()
            .pointerInput(true) {
                detectTapGestures(onTap = { offset ->
                    Log.d(
                        "detectTapGestures",
                        simpleCalendarDrawer
                            .getDayForPosition(offset, canvasSize)
                            .toString()
                    )
                    simpleCalendarDrawer.getDayForPosition(offset, canvasSize)
                })
            }) {

            val canvasHeight = size.height
            val canvasWidth = size.width
            canvasSize = Size(canvasWidth, canvasHeight)

            drawIntoCanvas { canvas ->
                simpleCalendarDrawer.drawWeekHeader(this, canvas)
                simpleCalendarDrawer.drawCalendar(this, canvas, currentMonth)
            }
        }
    }
}

@Composable
@Preview
fun CalendarPreview() {
    Column(
        modifier = Modifier
            .padding(horizontal = 30.dp)
            .fillMaxWidth()
            .height(360.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PoputkaTheme(darkTheme = true) {
            CalendarChart()
        }
    }
}

@Composable
fun NextButton(onClick: () -> Unit, iconSize: Dp = 32.dp) {
    IconButton(onClick = onClick, modifier = Modifier.fillMaxSize()) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_keyboard_arrow_right_24),
            contentDescription = "Arrow right icon",
            modifier = Modifier.size(iconSize)
        )
    }
}

@Composable
fun PreviousButton(onClick: () -> Unit, iconSize: Dp = 32.dp) {
    IconButton(onClick = onClick, modifier = Modifier.fillMaxSize()) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_keyboard_arrow_left_24),
            contentDescription = "Arrow left icon",
            modifier = Modifier.size(iconSize)
        )
    }
}

@Composable
fun DateLabel(currentDate: String) {
    Text(text = currentDate)
}

@Composable
fun DateNavigationBar(
    modifier: Modifier = Modifier,
    currentDate: String,
    onPrevious: () -> Unit,
    onNext: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        IconButton(onClick = onPrevious, modifier = Modifier) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_keyboard_arrow_left_24),
                contentDescription = "Arrow left icon",
                modifier = Modifier.size(32.dp)
            )
        }

        Text(
            text = currentDate,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )

        IconButton(onClick = onNext, modifier = Modifier) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_keyboard_arrow_right_24),
                contentDescription = "Arrow right icon",
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
@Preview
fun NextButtonPreview() {
    PoputkaTheme(darkTheme = true) {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //DateNavigationBar("November", {}, {})
            }
        }
    }
}
