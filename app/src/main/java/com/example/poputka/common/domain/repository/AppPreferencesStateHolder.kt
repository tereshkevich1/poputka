package com.example.poputka.common.domain.repository

import com.example.poputka.common.domain.model.VolumeUnit
import com.example.poputka.common.global_state.local_settings_state.AppPreferencesState
import kotlinx.coroutines.flow.StateFlow

interface AppPreferencesStateHolder {
    val appPrefFlow: StateFlow<AppPreferencesState>

    suspend fun toggleVolumeUnit(newValue: VolumeUnit)

    suspend fun saveGoal(newValue: Int)

    suspend fun saveAutoCalculation(newValue: Double)

    suspend fun saveAutoCalculation(newValue: Boolean)

    suspend fun getAppPreferencesSnapshot(): AppPreferencesState
}