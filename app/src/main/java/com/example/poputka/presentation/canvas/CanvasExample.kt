package com.example.poputka.presentation.canvas

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.poputka.ui.theme.PoputkaTheme

@Composable
fun BarGraphExample(
    modifier: Modifier = Modifier,
    values: List<Float>,
    maxHeight: Dp
) {
    val borderColor = Color.hsv(136f, 0.56f, 0.85f)
    val brushSecondColor = Color.hsv(136f, 0.78f, 0.93f)
    val density = LocalDensity.current
    val strokeWidth = with(density) { 3.dp.toPx() }

    val brush = Brush.verticalGradient(listOf(borderColor, brushSecondColor))
    val textMeasurer = rememberTextMeasurer()

    val paddingForText: Float = 80f
    val tickMarkHeight: Float = 20f

    Row(
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(maxHeight)
                .drawBehind {
                    // draw X-Axis
                    drawLine(
                        color = Color.Gray,
                        start = Offset(0f, size.height - paddingForText),
                        end = Offset(size.width, size.height - paddingForText),
                        strokeWidth = strokeWidth
                    )
                    repeat(7) {
                        drawLine(
                            color = Color.Gray,
                            start = Offset(0f + it * size.width / 7, size.height - paddingForText),
                            end = Offset(
                                0f + it * size.width / 7,
                                size.height - paddingForText + tickMarkHeight
                            ),
                            strokeWidth = strokeWidth
                        )
                        drawText(
                            textMeasurer,
                            "s",
                            topLeft = Offset(0f + it * size.width / 7, size.height)
                        )
                    }

                }
        ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        values.forEachIndexed { index, item ->
            Bar(item, brush, 100.dp, 20.dp)
        }
    }
}

@Composable
fun RowScope.Bar(
    value: Float,    // The current value represented by the bar
    brush: Brush,    // The color of the bar
    maxHeight: Dp,   // Maximum height the bar can have
    barWidth: Dp     // Width of the bar
) {
    val itemHeight = remember(value, maxHeight) { value * maxHeight.value / 100 }

    Box(
        modifier = Modifier
            .height(maxHeight)       // Set the max height of the container
            .width(barWidth)          // Set the width of the bar
            .padding(4.dp),           // Optional padding for visual space
        contentAlignment = Alignment.BottomCenter // Align bar at the bottom
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(itemHeight.dp)
                .clickable { }
            // Set height based on the value
        ) {
            drawRoundRect(
                brush = brush,   // Use the passed color
                size = Size(this.size.width, this.size.height),
                cornerRadius = CornerRadius(10f, 10f) // Adjust corner radius
            )
        }
    }
}


@Composable
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
fun BarGraphPreview() {
    PoputkaTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            val barChartData = (0..10).map {
                (1..100).random().toFloat()
            }
            BarGraphExample(values = barChartData, maxHeight = 400.dp)
        }
    }
}


@Composable
fun CanvasExample() {
    Canvas(
        modifier = Modifier
            .size(400.dp)
            .padding(16.dp)
    ) {
        drawRoundRect(
            color = Color.Red,
            cornerRadius = CornerRadius(250f, 250f),
            style = Stroke(width = 120.0f, cap = StrokeCap.Round)
        )
    }
    Canvas(
        modifier = Modifier
            .size(10.dp)
    ) {
        drawCircle(
            color = Color.Blue,
            center = Offset(size.width * 0.8f, size.height * 0.2f),
            radius = 80f,
        )
    }

    Canvas(
        modifier = Modifier
            .size(400.dp)
            .padding(16.dp)
    ) {
        drawCircle(
            color = Color.Blue,
            radius = size.width / 4,
            style = Stroke(width = 120f)
        )
    }
}

@Composable
fun CanvasExample2() {
    Canvas(
        modifier = Modifier
            .size(400.dp)
            .padding(16.dp)
    ) {
        val path = Path().apply {
            moveTo(50f, 50f)
            lineTo(175f, 175f)
            lineTo(300f, 50f)
            lineTo(300f, 400f)
            lineTo(225f, 300f)
            lineTo(125f, 300f)
            lineTo(50f, 400f)
            close()
        }
        drawPath(
            path = path,
            color = Color.Gray,
            alpha = 1f,
            style = Stroke(8f, cap = StrokeCap.Round)
        )
    }
}

@Composable
@Preview
fun CanvasPreview2() {
    MaterialTheme {
        Surface(
            modifier = Modifier
                .size(400.dp)
        ) {
            CanvasExample2()
        }
    }
}

@Composable
@Preview
fun CanvasPreview() {
    MaterialTheme {
        Surface(
            modifier = Modifier
                .size(400.dp)
                .padding(16.dp)
        ) {
            CanvasExample()
        }
    }
}
