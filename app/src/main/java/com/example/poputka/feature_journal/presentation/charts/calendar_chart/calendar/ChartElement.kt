package com.example.poputka.feature_journal.presentation.charts.calendar_chart.calendar

import androidx.compose.ui.graphics.Color

class ChartElement (
    val value: Float,
    val color: Color = Color.Blue,
    val onTap: (ChartElement) -> Unit = { }
)