package com.example.poputka.common.domain.model

import kotlin.math.roundToInt

enum class VolumeUnit {
    Milliliters,
    Liters,
    Ounces;

    fun convertFromMilliliters(valueInMl: Int): Double =
        when (this) {
            Milliliters -> valueInMl.toDouble()
            Liters -> valueInMl / ML_IN_LITER
            Ounces -> valueInMl * ML_IN_OUNCE
        }

    fun convertToMilliliters(goal: Double): Int =
        when (this) {
            Milliliters -> goal.roundToInt()
            Liters -> (goal * ML_IN_LITER).roundToInt()
            Ounces -> (goal / ML_IN_OUNCE).roundToInt()
        }

    companion object {
        private const val ML_IN_LITER = 1000.0
        private const val ML_IN_OUNCE = 0.033814
    }
}

