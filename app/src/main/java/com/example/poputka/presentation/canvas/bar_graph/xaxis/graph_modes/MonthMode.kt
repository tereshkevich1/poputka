package com.example.poputka.presentation.canvas.bar_graph.xaxis.graph_modes

class MonthMode(totalDaysInMonth: Int, labelStep: Int = 5, barGapCoefficient: Float = 0.1f) : BaseChartMode(
    labels = generateMonthLabels(totalDaysInMonth, labelStep),
    labelStep = labelStep,
    barCount = totalDaysInMonth,
    barGapCoefficient = barGapCoefficient
) {
    companion object {
        private fun generateMonthLabels(totalDays: Int, step: Int): List<String> =
            (0..totalDays / step).map { (1 + it * step).toString() }
    }
}