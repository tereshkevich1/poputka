package com.example.poputka.feature_home.presentation.add_drink_screen.components.vertical_slider.util

import kotlin.math.roundToInt

fun Float.snapToStep(step: Float): Float {
    return (this / step).roundToInt() * step
}