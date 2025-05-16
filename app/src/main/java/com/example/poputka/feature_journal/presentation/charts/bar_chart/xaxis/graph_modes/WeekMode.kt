package com.example.poputka.feature_journal.presentation.charts.bar_chart.xaxis.graph_modes

import com.example.poputka.feature_journal.presentation.charts.common.ChartModeConstants.WEEK_BAR_COUNT

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
){
    init {
        require(labels.size == WEEK_BAR_COUNT) { "WeekMode requires exactly 7 labels" }
    }
}