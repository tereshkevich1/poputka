package com.example.poputka.presentation.canvas

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class Bar(
    val value: Float,
    val label: String,
    val onTap: (Bar) -> Unit = { }
)

@Composable
fun BarChart(xAxisScaleData: List<Int>, barData: List<Float>,  onBarClick: (Int) -> Unit) {
    val density = LocalDensity.current
    val strokeWidth = with(density) { 1.dp.toPx() }


    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)

    val spaceForText: Float = 80f

    val rectangles = remember(barData) { mutableStateMapOf<Bar, Rect>() }

    val animateFloat = remember { Animatable(0f) }
    LaunchedEffect(animateFloat) {
        animateFloat.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000, easing = LinearEasing))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        // Handle click on the Canvas
                        val canvasWidth = size.width
                        val canvasHeight = size.height - spaceForText
                        val xStep = canvasWidth / (xAxisScaleData.size + 1)
                        val barWidth = xStep * 0.6f

                        for (i in xAxisScaleData.indices) {
                            val xPosition = (i + 1) * xStep
                            val barHeight = barData[i] * canvasHeight * animateFloat.value
                            val barLeft = xPosition - barWidth / 2
                            val barRight = xPosition + barWidth / 2
                            val barTop = canvasHeight - barHeight
                            val barBottom = canvasHeight

                            // Check if the tap offset is within the bounds of the current bar
                            if (offset.x in barLeft..barRight && offset.y in barTop..barBottom) {
                                onBarClick(i) // Trigger the click for the specific bar
                                Log.d("BarChart", "Clicked on bar index: $i")
                                break
                            }
                        }
                    }
                }
                .drawBehind {

                    drawLine(
                        color = androidx.compose.ui.graphics.Color.Gray,
                        start = Offset(0f, size.height - spaceForText),
                        end = Offset(size.width, size.height - spaceForText),
                        strokeWidth = strokeWidth
                    )

                }

        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height - spaceForText

            val xStep = canvasWidth / (xAxisScaleData.size + 1)
            val barWidth = xStep * 0.6f


            for (i in xAxisScaleData.indices) {
                val xPosition = (i + 1) * xStep


                val barHeight = barData[i] * canvasHeight * animateFloat.value

                // Adjust the top position to ensure the bar grows from the bottom

                drawRect(
                    color = androidx.compose.ui.graphics.Color.Blue,
                    topLeft = Offset(xPosition - barWidth / 2, canvasHeight - barHeight),
                    size = androidx.compose.ui.geometry.Size(barWidth, barHeight)
                )

                drawLine(androidx.compose.ui.graphics.Color.Gray,
                    start = Offset(xPosition, canvasHeight),
                    end = Offset(xPosition, size.height - 70f),
                    strokeWidth = strokeWidth)

                drawContext.canvas.nativeCanvas.drawText(
                    xAxisScaleData[i].toString(),
                    xPosition,
                    size.height - spaceForText / 3,
                    Paint().apply {
                        color = Color.BLACK
                        textAlign = android.graphics.Paint.Align.CENTER
                        textSize = 40f
                    }
                )
            }
        }
    }
}


@Composable
@Preview
fun BarsGraphPreview() {
    Column(
        modifier = Modifier
            .padding(horizontal = 30.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val xAxisData = listOf(1, 2, 3, 4, 5, 6, 7, 8, 1, 1, 1, 1, 1, 1, 1, 1, 1)
        val barData = listOf(
            0.2f,
            0.4f,
            0.6f,
            0.8f,
            1f,
            0.5f,
            0.3f,
            0.7f,
            0.5f,
            0.3f,
            0.7f,
            0.5f,
            0.3f,
            0.7f,
            0.5f,
            0.3f,
            0.7f,
        )

        BarChart(xAxisScaleData = xAxisData, barData = barData,{})

    }
}