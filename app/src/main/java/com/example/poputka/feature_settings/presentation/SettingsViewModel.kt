package com.example.poputka.feature_settings.presentation

import androidx.lifecycle.viewModelScope
import com.example.poputka.core.domain.model.SettingsConstants.GOAL_SETTING
import com.example.poputka.core.domain.model.SettingsConstants.VOLUME_UNIT_SETTING
import com.example.poputka.core.domain.model.VolumeUnit
import com.example.poputka.core.domain.repository.AppDataStoreSource
import com.example.poputka.core.presentation.BaseViewModel
import com.example.poputka.core.global_state.local_settings_state.SettingsState
import com.example.poputka.feature_settings.presentation.settings_screen.SettingsBottomSheetType
import com.example.poputka.feature_settings.presentation.settings_screen.SettingsScreenAction
import com.example.poputka.feature_settings.presentation.settings_screen.SettingsScreenEvent
import com.example.poputka.feature_settings.presentation.settings_screen.SettingsScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val appDataStoreSource: AppDataStoreSource) :
    BaseViewModel<SettingsScreenEvent>() {
    private val _settingState = MutableStateFlow(SettingsScreenState())
    val settingsState = _settingState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        SettingsScreenState()
    )

    fun onAction(action: SettingsScreenAction) {
        when (action) {
            is SettingsScreenAction.OnChangeVolumeUnit -> TODO()
            SettingsScreenAction.OnBottomSheetClosed -> closeBottomSheet()
            SettingsScreenAction.OnMeasurementSettingsClick -> showMeasurementBottomSheet()
            SettingsScreenAction.OnNotificationSettingsClick -> TODO()
            SettingsScreenAction.OnPersonalSettingsClick -> sendEvent(SettingsScreenEvent.NavigateToPersonalInfoScreen)
            SettingsScreenAction.OnSoundSettingsClick -> TODO()
            SettingsScreenAction.OnSynchronizeProfileClick -> TODO()
            SettingsScreenAction.OnTimeFormatSettingsClick -> TODO()
            SettingsScreenAction.OnWeatherSettingsClick -> TODO()
        }
    }

    private fun closeBottomSheet() {
        _settingState.update {
            it.copy(showBottomSheet = false)
        }
    }

    private fun showMeasurementBottomSheet() {
        _settingState.update {
            it.copy(
                showBottomSheet = true,
                settingsBottomSheetType = SettingsBottomSheetType.MEASUREMENT_BOTTOM_SHEET
            )
        }
    }

    private fun loadSettings(): Flow<SettingsState> = combine(
        appDataStoreSource.intFlow(key = GOAL_SETTING),
        appDataStoreSource.stringFlow(key = VOLUME_UNIT_SETTING)
    ) { flows ->
        val units = VolumeUnit.valueOf(flows[1] as String? ?: VolumeUnit.Liters.name)
        SettingsState(
            goalSetting = flows[0] as Int? ?: 1500,
            volumeUnitSetting = units
        )
    }
}