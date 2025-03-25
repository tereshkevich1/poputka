package com.example.poputka.feature_journal.presentation.charts.bar_chart.xaxis.graph_modes

class MonthMode(totalDaysInMonth: Int, labelStep: Int = 5, barGapCoefficient: Float = 0.1f) :
    BaseChartMode(
        labels = generateMonthLabels(totalDaysInMonth, labelStep),
        labelStep = labelStep,
        barCount = totalDaysInMonth,
        barGapCoefficient = barGapCoefficient
    ) {
    companion object {
        private fun generateMonthLabels(totalDays: Int, step: Int) =
            (0 until (totalDays + step - 1) / step).map { (1 + it * step).toString() }
    }
}