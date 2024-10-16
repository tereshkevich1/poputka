package com.example.poputka.presentation.canvas.bar_graph.xaxis.graph_modes

class DayMode : ChartMode {
    override fun getMarkerCount(): Int = 13
    override fun getBarGapCoefficient(): Float = 0.4f
    override fun getLabels(): List<String> = listOf("0","4","8","12","16","20","24")
    override fun getLabelStep(): Int = 4
    override fun getBarCount() = 48
}

