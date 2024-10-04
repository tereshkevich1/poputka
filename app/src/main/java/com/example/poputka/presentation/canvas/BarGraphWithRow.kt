package com.example.poputka.presentation.canvas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.compose.ui.unit.toSize

@Composable
fun BarGraphWithRow(maxHeight: Dp, xAxisScaleData: List<Int>, barData: List<Float>) {
    val density = LocalDensity.current
    val strokeWidth = with(density) { 1.dp.toPx() }


    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)

    val spaceForText: Float = 80f

    val paddingForText: Float = 80f
    val paddingForTextDp = with(density) { paddingForText.toDp() }
    val tickMarkHeight: Float = 20f

    val textMeasurer = rememberTextMeasurer()

    val xAxisScaleDataSize = xAxisScaleData.size - 1

    var boxWidth by remember {
        mutableStateOf(0.dp)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
            .height(maxHeight)
            .onGloballyPositioned {
                    layoutCoordinates: LayoutCoordinates ->
                with(density) {
                    boxWidth = layoutCoordinates.size.height.toDp()
                }
            }
            .drawBehind {
                // draw X-Axis

                val spacingBetweenMarkers = size.width / xAxisScaleDataSize

                val extraOffset = spacingBetweenMarkers / 2f

                drawLine(
                    color = Color.Gray,
                    start = Offset(0f, size.height - paddingForText),
                    end = Offset(size.width, size.height - paddingForText),
                    strokeWidth = strokeWidth
                )
                repeat(xAxisScaleDataSize + 1) {

                    val xOffset =
                        0f + it * (size.width - extraOffset * 2) / xAxisScaleDataSize + 2f + extraOffset
                    val yOffset = size.height - paddingForText
                    val yOffsetWithMarker = yOffset + tickMarkHeight
                    drawLine(
                        color = Color.Gray,
                        start = Offset(xOffset, yOffset),
                        end = Offset(xOffset, yOffsetWithMarker),
                        strokeWidth = strokeWidth
                    )
                    val text = xAxisScaleData[it].toString()
                    val textSize = textMeasurer.measure(text).size

                    drawText(
                        textMeasurer,
                        text,
                        topLeft = Offset(
                            xOffset,
                            size.height - paddingForText + tickMarkHeight + 5f
                        ),
                        style = TextStyle(
                            fontSize = 8.sp
                        )
                    )
                }

            }
    ) {

        val spacingBetweenMarkers = boxWidth / xAxisScaleDataSize

        val extraOffset = spacingBetweenMarkers / 2

        val horizontalPadding = boxWidth - spacingBetweenMarkers

      //  val spacingBetweenMarkersDp = with(density) { spacingBetweenMarkers.toDp() }
        //val extraOffsetDp = with(density) { extraOffset.toDp() }


        xAxisScaleData.forEachIndexed() { item, index ->

            val xOffset =
                  item * (boxWidth) / xAxisScaleDataSize
           // val yOffset = parentSize.height

            //val xOffsetDp = with(density) { xOffset.toDp() }
            //val yOffsetDp = with(density) { yOffset.toDp() }

            Bar(
                value = 90.toFloat(),
                color = Color.Blue,
                maxHeight = maxHeight,
                modifier = Modifier
                    .offset(x = xOffset, y = 60.dp)
                    .padding(bottom = paddingForTextDp)
            )

        }


    }
}


@Composable
private fun BoxScope.Bar(
    value: Float,
    color: Color,
    maxHeight: Dp,
    modifier: Modifier
) {

    val itemHeight = remember(value) { value * maxHeight.value / 100 }

    Spacer(
        modifier = modifier
            .clickable { }
            .height(itemHeight.dp)
            .width(20.dp)
            .background(color)
    )

}


@Composable
@Preview
fun BarGraphWithRowPreview() {
    val xAxisData = listOf(
        0, 4, 8, 12, 16, 20, 24
    )
    val barChartInputsPercent = (0..6).map { (1..100).random().toFloat() }

    BarGraphWithRow(500.dp, xAxisScaleData = xAxisData, barData = barChartInputsPercent)

}