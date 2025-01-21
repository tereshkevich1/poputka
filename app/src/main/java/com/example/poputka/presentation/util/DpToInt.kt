package com.example.poputka.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun dpToInt(dpValue: Dp): Int {
    val density = LocalDensity.current
    return with(density) { dpValue.toPx().toInt() }
}