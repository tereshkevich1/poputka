package com.example.poputka.presentation.canvas.bar_graph.xaxis.graph_modes

class WeekMode : BaseChartMode(
    labels = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"),
    labelStep = 1,
    barCount = 7,
    barGapCoefficient = 0.3f
)