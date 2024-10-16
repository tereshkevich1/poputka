package com.example.poputka.presentation.canvas.bar_graph.xaxis.graph_modes

interface ChartMode {
    fun getMarkerCount(): Int
    fun getBarGapCoefficient(): Float
    fun getLabels(): List<String>
    fun getLabelStep(): Int
    fun getBarCount(): Int
}
