package com.example.poputka.feature_journal.presentation.charts.bar_chart.xaxis.graph_modes

abstract class BaseChartMode(
    protected val labels: List<String>,
    private val labelStep: Int,
    private val barCount: Int,
    private val barGapCoefficient: Float
) {
    open fun getMarkerCount(): Int = labels.size

    fun getBarGapCoefficient(): Float = barGapCoefficient

    fun retrieveLabels(): List<String> = labels

    fun getLabelStep(): Int = labelStep

    fun getBarCount(): Int = barCount
}
