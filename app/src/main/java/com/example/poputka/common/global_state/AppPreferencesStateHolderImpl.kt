package com.example.poputka.common.global_state

import com.example.poputka.common.domain.model.VolumeUnit
import com.example.poputka.common.domain.repository.AppDataStoreSource
import com.example.poputka.common.domain.repository.AppPreferencesStateHolder
import com.example.poputka.common.global_state.local_settings_state.AppPreferencesState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class AppPreferencesStateHolderImpl @Inject constructor(private val appDataStoreSource: AppDataStoreSource) :
    AppPreferencesStateHolder {
    private val _volumeUnitFlow: StateFlow<AppPreferencesState> =
        combine(
            appDataStoreSource.stringFlow(VOLUME_UNIT_SETTING),
            appDataStoreSource.intFlow(DAILY_GOAL_SETTING),
            appDataStoreSource.booleanFlow(AUTO_CALCULATION_SETTING)
        ) { flows ->

            val volumeUnit =
                runCatching { VolumeUnit.valueOf(flows[0] as String) }.getOrDefault(VolumeUnit.Milliliters)
            val goal = volumeUnit.convertFromMilliliters(flows[1] as Int)
            val autoCalculation = flows[2] as Boolean

            AppPreferencesState(
                goalSetting = goal,
                volumeUnitSetting = volumeUnit,
                autoCalculation = autoCalculation
            )
        }.stateIn(
            scope = CoroutineScope(SupervisorJob() + Dispatchers.IO),
            started = SharingStarted.Eagerly,
            initialValue = AppPreferencesState()
        )


    override val appPrefFlow: StateFlow<AppPreferencesState> get() = _volumeUnitFlow

    override suspend fun toggleVolumeUnit(newValue: VolumeUnit) {
        appDataStoreSource.putString(VOLUME_UNIT_SETTING, newValue.name)
    }

    override suspend fun saveGoal(newValue: Double) {
        val newValueInt = _volumeUnitFlow.value.volumeUnitSetting.convertToMilliliters(newValue)
        appDataStoreSource.putInt(DAILY_GOAL_SETTING, newValueInt)
    }

    override suspend fun saveAutoCalculation(newValue: Boolean) {
        appDataStoreSource.putBoolean(AUTO_CALCULATION_SETTING, newValue)
    }

    override suspend fun getAppPreferencesSnapshot(): AppPreferencesState {
        val volumeUnit =
            runCatching {
                VolumeUnit.valueOf(
                    appDataStoreSource.getStringValue(
                        VOLUME_UNIT_SETTING,
                        ""
                    )
                )
            }.getOrDefault(VolumeUnit.Milliliters)
        return AppPreferencesState(
            goalSetting = appDataStoreSource.getIntValue(
                DAILY_GOAL_SETTING, 0
            ).toDouble(),
            volumeUnitSetting = volumeUnit,
            autoCalculation = appDataStoreSource.getBooleanValue(AUTO_CALCULATION_SETTING, false)
        )
    }

    companion object {
        const val VOLUME_UNIT_SETTING = "volume_unit_setting"
        const val DAILY_GOAL_SETTING = "daily_goal_setting"
        const val AUTO_CALCULATION_SETTING = "auto_calculation"
    }
}


