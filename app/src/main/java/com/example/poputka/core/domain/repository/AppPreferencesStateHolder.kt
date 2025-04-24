package com.example.poputka.core.domain.repository

import com.example.poputka.core.domain.model.VolumeUnit
import com.example.poputka.core.global_state.local_settings_state.AppPreferencesState
import kotlinx.coroutines.flow.StateFlow

interface AppPreferencesStateHolder {
    val appPrefFlow: StateFlow<AppPreferencesState>

    suspend fun toggleVolumeUnit(newValue: VolumeUnit)

    suspend fun saveGoal(newValue: Double)

    suspend fun saveAutoCalculation(newValue: Boolean)
}