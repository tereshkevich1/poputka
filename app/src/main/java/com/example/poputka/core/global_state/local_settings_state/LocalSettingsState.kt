package com.example.poputka.core.global_state.local_settings_state

import androidx.compose.runtime.compositionLocalOf
import com.example.poputka.core.domain.model.VolumeUnit

val LocalSettingsState =
    compositionLocalOf { VolumeUnit.Milliliters }