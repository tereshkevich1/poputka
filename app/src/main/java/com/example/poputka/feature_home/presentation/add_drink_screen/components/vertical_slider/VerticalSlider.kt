package com.example.poputka.feature_home.presentation.add_drink_screen.components.vertical_slider

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.poputka.feature_home.presentation.add_drink_screen.components.vertical_slider.util.SliderConstants
import com.example.poputka.feature_home.presentation.add_drink_screen.components.vertical_slider.util.snapToStep

@Composable
fun VerticalSlider(
    sliderRatio: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier.Companion,
    backgroundColor: Color = MaterialTheme.colorScheme.outlineVariant,
    fillColor: Color = Color.Companion.Red,
    sliderWidth: Dp = 80.dp,
    cornerRadius: Dp = 100.dp,
    minFillHeight: Dp = 6.dp
) {
    val density = LocalDensity.current

    var sliderHeightPx by remember { mutableFloatStateOf(0f) }

    var currentSliderValue = remember { sliderHeightPx * sliderRatio }

    val minFillHeightPx = with(density) { minFillHeight.toPx() }
    val fillHeight by remember(sliderRatio, sliderHeightPx, minFillHeightPx) {
        derivedStateOf {
            -((sliderHeightPx * sliderRatio).coerceAtLeast(minFillHeightPx))
        }
    }

    Box(
        modifier = modifier
            .width(sliderWidth)
            .clip(RoundedCornerShape(cornerRadius))
            .onSizeChanged { size ->
                sliderHeightPx = size.height.toFloat()
            }
            .pointerInput(Unit) {
                detectVerticalDragGestures(
                    onDragStart = { offset ->
                        currentSliderValue = offset.y
                        val ratio = (offset.y / sliderHeightPx).coerceIn(0f, 1f)
                            .snapToStep(SliderConstants.SLIDER_SNAP_STEP)

                        onValueChange(1f - ratio)
                    }
                ) { _, dragAmount ->
                    currentSliderValue += dragAmount
                    val ratio = (currentSliderValue / sliderHeightPx).coerceIn(0f, 1f)
                        .snapToStep(SliderConstants.SLIDER_SNAP_STEP)

                    onValueChange(1f - ratio)
                }
            }
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    currentSliderValue = offset.y
                    val ratio = (currentSliderValue / sliderHeightPx).coerceIn(0f, 1f)
                        .snapToStep(SliderConstants.SLIDER_SNAP_STEP)

                    onValueChange(1f - ratio)
                }
            }
            .drawBehind {
                val cornerRadiusPx = with(density) {
                    cornerRadius.toPx()
                }
                val corner = CornerRadius(cornerRadiusPx, cornerRadiusPx)
                val width = size.width

                drawRoundRect(
                    color = backgroundColor,
                    topLeft = Offset(0f, 0f),
                    size = Size(width, size.height),
                    cornerRadius = corner
                )

                drawRoundRect(
                    color = fillColor,
                    topLeft = Offset(0f, size.height),
                    size = Size(width, fillHeight),
                    cornerRadius = corner
                )
            }
    )
}