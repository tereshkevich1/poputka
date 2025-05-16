package com.example.poputka.feature_journal.presentation.charts.bar_chart

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import com.example.poputka.ui.theme.customColor1Light

data class Bar(
    val value: Float,
    val label: String,
    val color: Color = customColor1Light,
    val cornerRadius: CornerRadius = CornerRadius(10f, 10f),
)