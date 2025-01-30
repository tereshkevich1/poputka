package com.example.poputka.core

enum class VolumeUnit(val abbreviation: String) {
    Milliliters("ml"),
    Liters("l"),
    Ounces("oz")
}

val volumeUnitList =
    listOf(
        VolumeUnit.Milliliters.name,
        VolumeUnit.Liters.name,
        VolumeUnit.Ounces.name
    )