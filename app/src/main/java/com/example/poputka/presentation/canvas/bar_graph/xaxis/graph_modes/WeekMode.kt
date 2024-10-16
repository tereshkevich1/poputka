package com.example.poputka.presentation.canvas.bar_graph.xaxis.graph_modes

class WeekMode : ChartMode {
    override fun getMarkerCount(): Int = 7
    override fun getBarGapCoefficient(): Float = 0.4f
    override fun getLabels(): List<String> = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    override fun getLabelStep(): Int = 1
    override fun getBarCount() = 7
}