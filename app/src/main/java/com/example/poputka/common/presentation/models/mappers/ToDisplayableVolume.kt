package com.example.poputka.common.presentation.models.mappers

import com.example.poputka.common.domain.model.VolumeUnit
import com.example.poputka.common.presentation.models.DisplayableInt
import java.util.Locale

fun Int.toDisplayableVolume(unit: VolumeUnit): DisplayableInt {
    val convertedValue = unit.convertFromMilliliters(this)
    val formatted = when (unit) {
        VolumeUnit.Milliliters -> convertedValue.toInt().toString()
        VolumeUnit.Liters, VolumeUnit.Ounces -> String.format(
            Locale.getDefault(),
            "%.2f",
            convertedValue
        )
    }
    return DisplayableInt(this, formatted)
}