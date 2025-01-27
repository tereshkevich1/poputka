package com.example.poputka.presentation.settings

import com.example.poputka.core.VolumeUnit

data class SettingsState(
    val goalSetting: Int = 1500,
    val volumeUnitSetting: VolumeUnit = VolumeUnit.Milliliters
)