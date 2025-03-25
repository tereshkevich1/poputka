package com.example.poputka.feature_journal.presentation.charts.calendar_chart.calendar

class ChartData (
    val elements: List<ChartElement>,
    val padBy: Float = 10f,
    val startAtZero: Boolean = false
){
    private val radiusMinMax: Pair<Float, Float>
        get() {
            val min = elements.minByOrNull { it.value }?.value ?: 0f
            val max = elements.maxByOrNull { it.value }?.value ?: 0f
            return min to max
        }

    val maxRadiusValue: Float = radiusMinMax.second + ((radiusMinMax.second - radiusMinMax.first) * padBy / 100f)
    val minRadiusValue: Float
        get() {
            return if (startAtZero) {
                0f
            } else {
                radiusMinMax.first - ((radiusMinMax.second - radiusMinMax.first) * padBy / 100f)
            }
        }
    val maxCircleRadius = elements.maxByOrNull { it.value }?.value ?: 0f
}