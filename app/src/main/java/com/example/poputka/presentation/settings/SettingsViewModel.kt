package com.example.poputka.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.poputka.core.SettingsConstants.GOAL_SETTING
import com.example.poputka.core.SettingsConstants.VOLUME_UNIT_SETTING
import com.example.poputka.core.VolumeUnit
import com.example.poputka.data.settings.SettingsDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val settingsDataStore: SettingsDataStore) :
    ViewModel() {
    private val _settingState = MutableStateFlow(SettingsState())
    val settingsState = _settingState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        SettingsState()
    )

    var setKeepOnScreenCondition = true

    init {
        viewModelScope.launch {
            loadSettings().onEach { settings ->
                _settingState.update {
                    it.copy(
                        goalSetting = settings.goalSetting,
                        volumeUnitSetting = settings.volumeUnitSetting
                    )
                }
                setKeepOnScreenCondition = false
            }.collect()
        }
    }

    private fun loadSettings(): Flow<SettingsState> = combine(
        settingsDataStore.getIntFlow(key = GOAL_SETTING),
        settingsDataStore.getStringFlow(key = VOLUME_UNIT_SETTING)
    ) { flows ->
        val units = VolumeUnit.valueOf(flows[1] as String? ?: VolumeUnit.Liters.name)
        SettingsState(
            goalSetting = flows[0] as Int? ?: 1500,
            volumeUnitSetting = units
        )
    }

    fun changeVolumeUnit(value: VolumeUnit) {
        viewModelScope.launch {
            settingsDataStore.putString(value.name, key = VOLUME_UNIT_SETTING)
        }
    }
}