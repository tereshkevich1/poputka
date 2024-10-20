package com.example.poputka.presentation.canvas.bar_graph.xaxis.graph_modes

class MonthMode(totalDaysInMonth: Int) : BaseChartMode(
    labels = (1..totalDaysInMonth).map { it.toString() },
    labelStep = 5,
    barCount = totalDaysInMonth,
    barGapCoefficient = 0.1f
)