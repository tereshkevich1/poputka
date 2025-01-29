package com.example.poputka.presentation.canvas.custom_circular_progress_indicator

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.poputka.presentation.canvas.bar_chart.animation.fadeInAnimation

import com.example.poputka.presentation.canvas.custom_circular_progress_indicator.render.drawCircularProgressIndicator
import com.example.poputka.ui.theme.PoputkaTheme

@Composable
fun AnimatedCircularProgressIndicator(
    maxValue: Float,
    progressBackgroundColor: Color,
    progressIndicatorColor: Color,
    indicatorColor: Color,
    circularIndicatorDiameter: Dp = 184.dp,
    strokeWidth: Dp = 12.dp,
    animation: AnimationSpec<Float> = fadeInAnimation(800),
    fontSize: TextUnit = 28.sp,
    modifier: Modifier = Modifier,
    currentValueProvider: () -> Float
) {
    val stroke = with(LocalDensity.current) {
        Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
    }

    val animateFloat = remember { Animatable(0f) }
    val currentValue = currentValueProvider()

    LaunchedEffect(currentValue) {
        animateFloat.animateTo(
            targetValue = currentValue / maxValue,
            animationSpec = animation
        )
    }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Canvas(
            Modifier.size(circularIndicatorDiameter)
        ) {
            val startAngle = 270f
            val sweep: Float = animateFloat.value * 360f
            val diameterOffset = stroke.width / 2

            drawCircle(
                color = progressBackgroundColor,
                style = stroke,
                radius = size.minDimension / 2.0f - diameterOffset
            )

            drawCircularProgressIndicator(
                startAngle,
                sweep,
                progressIndicatorColor,
                indicatorColor,
                stroke
            )
        }
        ProgressStatus((animateFloat.value * maxValue).toInt(), fontSize)
    }
}

@Composable
fun ProgressStatus(
    currentValue: Int,
    fontSize: TextUnit
) {
    Text(text = currentValue.toString(), fontSize = fontSize, fontWeight = FontWeight.Bold)
}

@Composable
@Preview
fun CircularPIPreview() {
    var currentAnimValue by remember { mutableFloatStateOf(600f) }
    val maxValue = 2000f

    PoputkaTheme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedCircularProgressIndicator(
                    maxValue = maxValue,
                    progressBackgroundColor = Color.Green,
                    progressIndicatorColor = Color.Blue,
                    indicatorColor = MaterialTheme.colorScheme.surface,
                    currentValueProvider = { currentAnimValue }
                )

                Button(
                    onClick = { currentAnimValue += 200f },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Increase Progress")
                }
            }
        }
    }
}
