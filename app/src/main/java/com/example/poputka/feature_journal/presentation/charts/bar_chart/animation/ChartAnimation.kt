package com.example.poputka.feature_journal.presentation.charts.bar_chart.animation

import android.view.animation.PathInterpolator
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween


fun fadeInAnimation(durationMillis: Int = 3000) = tween<Float>(durationMillis = durationMillis)

fun fadeOutAnimation() = spring(
    dampingRatio = Spring.DampingRatioMediumBouncy,
    stiffness = Spring.StiffnessLow,
    visibilityThreshold = 0.01f
)

val emphasizedInterpolator = PathInterpolator(0.05f, 0.7f, 0.1f, 1f)
fun emphasizedAnimationSpec() =
    tween<Float>(durationMillis = 1000, easing = { emphasizedInterpolator.getInterpolation(it) })