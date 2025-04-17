package com.example.poputka.core.domain.repository

import com.example.poputka.core.domain.model.VolumeUnit
import kotlinx.coroutines.flow.StateFlow

interface AppPreferencesStateHolder {
    val volumeUnitFlow: StateFlow<VolumeUnit>

    suspend fun toggleVolumeUnit(newValue: VolumeUnit)
}