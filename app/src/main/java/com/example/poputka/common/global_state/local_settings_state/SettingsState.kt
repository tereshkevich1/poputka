package com.example.poputka.common.global_state.local_settings_state

import com.example.poputka.common.domain.model.VolumeUnit

data class AppPreferencesState(
    val goalSetting: Int = 1500,
    val volumeUnitSetting: VolumeUnit = VolumeUnit.Milliliters,
    val autoCalculation: Boolean = true
)