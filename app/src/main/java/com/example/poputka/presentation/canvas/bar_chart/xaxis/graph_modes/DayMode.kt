package com.example.poputka.presentation.canvas.bar_chart.xaxis.graph_modes

class DayMode(labelStep: Int = 8, barCount: Int = 48, barGapCoefficient: Float = 0.1f) :
    BaseChartMode(
        labels = generateDayLabels(labelStep),
        labelStep = labelStep,
        barCount = barCount,
        barGapCoefficient = barGapCoefficient
    ) {
    companion object {
        private fun generateDayLabels(step: Int): List<String> =
            (0..24 / (step / 2)).map { (it * (step / 2)).toString() }
    }
}
