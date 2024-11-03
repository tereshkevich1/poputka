package com.example.poputka.presentation.canvas.bar_chart.xaxis.graph_modes

class WeekMode(
    labels: List<String> = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"),
    labelStep: Int = 1,
    barCount: Int = labels.size,
    barGapCoefficient: Float = 0.3f
) : BaseChartMode(
    labels = labels,
    labelStep = labelStep,
    barCount = barCount,
    barGapCoefficient = barGapCoefficient
)