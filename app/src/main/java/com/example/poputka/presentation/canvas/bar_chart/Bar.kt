package com.example.poputka.presentation.canvas.bar_chart

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color

data class Bar(
    val value: Float,
    val label: String,
    val color: Color = Color.Blue,
    val cornerRadius: CornerRadius = CornerRadius(10f, 10f),
    val onTap: (Bar) -> Unit = { }
)