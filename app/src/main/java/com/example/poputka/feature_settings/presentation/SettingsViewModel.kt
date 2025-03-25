package com.example.poputka.feature_settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.poputka.feature_settings.domain.SettingsConstants.GOAL_SETTING
import com.example.poputka.feature_settings.domain.SettingsConstants.VOLUME_UNIT_SETTING
import com.example.poputka.feature_settings.domain.VolumeUnit
import com.example.poputka.feature_settings.data.SettingsDataStore
import com.example.poputka.feature_settings.presentation.local_settings_state.SettingsState
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

    fun changeVolumeUnit(value: String) {
        val adjustedValue = try {
            VolumeUnit.valueOf(value)
        } catch (e: IllegalArgumentException) {
            null
        }
        if (adjustedValue != null) {
            viewModelScope.launch {
                settingsDataStore.putString(adjustedValue.name, key = VOLUME_UNIT_SETTING)
            }
        }
    }
}