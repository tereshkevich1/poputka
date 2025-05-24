package com.example.poputka.common.presentation.models.mappers

import com.example.poputka.common.domain.model.VolumeUnit
import com.example.poputka.common.presentation.models.DisplayableInt
import java.util.Locale

fun Int.toDisplayableVolume(volumeUnit: VolumeUnit): DisplayableInt {
    val convertedValue = volumeUnit.convertFromMilliliters(this)
    val formatted = when (volumeUnit) {
        VolumeUnit.Milliliters -> convertedValue.toInt().toString()
        VolumeUnit.Liters, VolumeUnit.Ounces -> String.format(
            Locale.getDefault(),
            "%.2f",
            convertedValue
        )
    }
    return DisplayableInt(this, formatted)
}