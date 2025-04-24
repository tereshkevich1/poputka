package com.example.poputka.core.global_state.local_settings_state

import com.example.poputka.core.domain.model.VolumeUnit

data class AppPreferencesState(
    val goalSetting: Double = 1500.0,
    val volumeUnitSetting: VolumeUnit = VolumeUnit.Milliliters,
    val autoCalculation: Boolean = true
)