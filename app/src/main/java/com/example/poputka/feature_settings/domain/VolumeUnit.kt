package com.example.poputka.feature_settings.domain

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