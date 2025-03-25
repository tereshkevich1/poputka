package com.example.poputka.feature_settings.presentation.local_settings_state

import com.example.poputka.feature_settings.domain.VolumeUnit

data class SettingsState(
    val goalSetting: Int = 1500,
    val volumeUnitSetting: VolumeUnit = VolumeUnit.Milliliters
)