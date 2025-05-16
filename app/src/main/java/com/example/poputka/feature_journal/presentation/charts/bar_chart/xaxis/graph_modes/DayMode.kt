package com.example.poputka.feature_journal.presentation.charts.bar_chart.xaxis.graph_modes

import com.example.poputka.feature_journal.presentation.charts.common.ChartModeConstants.DAY_BAR_COUNT

class DayMode(
    barCount: Int = DAY_BAR_COUNT,
    labelStep: Int = 8,
    barGapCoefficient: Float = 0.1f
) :
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
