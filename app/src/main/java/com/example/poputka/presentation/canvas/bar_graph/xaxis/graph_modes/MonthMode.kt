package com.example.poputka.presentation.canvas.bar_graph.xaxis.graph_modes

class MonthMode(private val totalDaysInMonth: Int) : ChartMode {
    override fun getMarkerCount(): Int = 31
    override fun getBarGapCoefficient(): Float = 0.1f
    override fun getLabels(): List<String> = (1..31).map { it.toString() }
    override fun getLabelStep(): Int = 5
    override fun getBarCount(): Int = totalDaysInMonth
}