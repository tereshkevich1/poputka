package com.example.poputka.feature_home.presentation.home_screen.custom_circular_progress_indicator

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
import androidx.compose.ui.unit.dp
import com.example.poputka.common.domain.model.VolumeUnit
import com.example.poputka.feature_home.presentation.home_screen.custom_circular_progress_indicator.render.drawCircularProgressIndicator
import com.example.poputka.feature_journal.presentation.charts.bar_chart.animation.fadeInAnimation
import com.example.poputka.ui.theme.PoputkaTheme
import java.util.Locale

@Composable
fun AnimatedCircularProgressIndicator(
    unit: VolumeUnit,
    percent: Int,
    maxValue: Float,
    currentValueProvider: () -> Float,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 12.dp,
    circularIndicatorDiameter: Dp = 184.dp,
    animation: AnimationSpec<Float> = fadeInAnimation(800),
    progressBackgroundColor: Color = MaterialTheme.colorScheme.surfaceContainerHighest,
    progressIndicatorColor: Color = MaterialTheme.colorScheme.primary,
    indicatorColor: Color = MaterialTheme.colorScheme.surfaceContainerHighest
) {
    val stroke = with(LocalDensity.current) {
        Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
    }

    val animateFloat = remember { Animatable(0f) }
    val currentValue = currentValueProvider()

    LaunchedEffect(currentValue) {
        val target = if (maxValue > 0f && !currentValue.isNaN()) {
            currentValue / maxValue
        } else {
            0f
        }
        animateFloat.animateTo(
            targetValue = target,
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
        ProgressStatus(
            currentValue = animateFloat.value * maxValue,
            unit = unit,
            percent = percent
        )
    }
}

@Composable
fun ProgressStatus(
    currentValue: Float,
    unit: VolumeUnit,
    percent: Int
) {
    val formatted = when (unit) {
        VolumeUnit.Milliliters -> currentValue.toInt().toString()
        VolumeUnit.Liters, VolumeUnit.Ounces -> String.format(
            Locale.getDefault(),
            "%.2f",
            currentValue
        )
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = formatted,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = "$percent%",
            style = MaterialTheme.typography.bodyMedium
        )
    }

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
                    currentValueProvider = { currentAnimValue },
                    unit = VolumeUnit.Milliliters,
                    percent = 1
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
