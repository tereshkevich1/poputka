package com.example.poputka.core.global_state.local_settings_state

import com.example.poputka.core.domain.model.VolumeUnit

data class SettingsState(
    val goalSetting: Int = 1500,
    val volumeUnitSetting: VolumeUnit = VolumeUnit.Milliliters
)