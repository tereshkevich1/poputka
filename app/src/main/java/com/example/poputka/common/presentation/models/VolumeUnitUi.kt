package com.example.poputka.common.presentation.models

import com.example.poputka.R
import com.example.poputka.common.domain.model.VolumeUnit
import com.example.poputka.core.presentation.UiText

fun VolumeUnit.asUiText(): UiText = when (this) {
    VolumeUnit.Milliliters -> UiText.StringResource(R.string.ml)
    VolumeUnit.Liters -> UiText.StringResource(R.string.liters_short)
    VolumeUnit.Ounces -> UiText.StringResource(R.string.oz)
}