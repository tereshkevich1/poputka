package com.example.poputka.core.global_state

import com.example.poputka.core.domain.model.VolumeUnit
import com.example.poputka.core.domain.repository.AppDataStoreSource
import com.example.poputka.core.domain.repository.AppPreferencesStateHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class AppPreferencesStateHolderImpl @Inject constructor(private val appDataStoreSource: AppDataStoreSource) :
    AppPreferencesStateHolder {
    private val _volumeUnitFlow: StateFlow<VolumeUnit> =
        appDataStoreSource.stringFlow(VOLUME_UNIT_SETTING)
            .map {
                runCatching { VolumeUnit.valueOf(it) }.getOrDefault(VolumeUnit.Milliliters)
            }
            .stateIn(
                scope = CoroutineScope(SupervisorJob() + Dispatchers.IO),
                started = SharingStarted.Eagerly,
                initialValue = VolumeUnit.Milliliters
            )

    override val volumeUnitFlow: StateFlow<VolumeUnit> get() = _volumeUnitFlow

    override suspend fun toggleVolumeUnit(newValue: VolumeUnit) {
        appDataStoreSource.putString(VOLUME_UNIT_SETTING, newValue.name)
    }

    companion object {
        const val VOLUME_UNIT_SETTING = "volume_unit_setting"
    }
}