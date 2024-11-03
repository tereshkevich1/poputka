package com.example.poputka.presentation.canvas.bar_chart

data class Bars(
    val bars: List<Bar>,
    val achievementValue: Float,
    val padBy: Float = 10f,
    val startAtZero: Boolean = true
) {
    init {
        require(padBy in 0f..100f)
    }

    private val yMinMax: Pair<Float, Float>
        get() {
            val min = bars.minByOrNull { it.value }?.value?.coerceAtMost(achievementValue)
                ?: achievementValue
            val max = bars.maxByOrNull { it.value }?.value?.coerceAtLeast(achievementValue)
                ?: achievementValue
            return min to max
        }

    val maxYValue: Float = if (yMinMax.second == achievementValue) yMinMax.second
    else yMinMax.second + ((yMinMax.second - yMinMax.first) * padBy / 100f)

    val minYValue: Float
        get() {
            return if (startAtZero) {
                0f
            } else {
                yMinMax.first - ((yMinMax.second - yMinMax.first) * padBy / 100f)
            }
        }
}