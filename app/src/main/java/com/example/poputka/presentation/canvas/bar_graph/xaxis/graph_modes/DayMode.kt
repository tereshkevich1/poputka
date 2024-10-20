package com.example.poputka.presentation.canvas.bar_graph.xaxis.graph_modes

class DayMode : BaseChartMode(
    labels = listOf("0", "4", "8",  "12",  "16",  "20",  "24"),
    labelStep = 8,
    barCount = 48,
    barGapCoefficient = 0.2f
){
    override fun getMarkerCount(): Int {
        return labels.size
    }
}
