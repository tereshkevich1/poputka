package com.example.poputka.feature_settings.presentation

import androidx.lifecycle.viewModelScope
import com.example.poputka.core.domain.repository.AppDataStoreSource
import com.example.poputka.core.presentation.BaseViewModel
import com.example.poputka.feature_settings.presentation.settings_screen.SettingsBottomSheetType
import com.example.poputka.feature_settings.presentation.settings_screen.SettingsScreenAction
import com.example.poputka.feature_settings.presentation.settings_screen.SettingsScreenEvent
import com.example.poputka.feature_settings.presentation.settings_screen.SettingsScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
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
            SettingsScreenAction.OnMeasurementSettingsClick -> showBottomSheet(
                SettingsBottomSheetType.MEASUREMENT_BOTTOM_SHEET)
            SettingsScreenAction.OnDailyGoalSettingsClick -> showBottomSheet(SettingsBottomSheetType.DAILY_GOAL_BOTTOM_SHEET)
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

    private fun showBottomSheet(bottomSheet: SettingsBottomSheetType) {
        _settingState.update {
            it.copy(
                showBottomSheet = true,
                bottomSheet = bottomSheet
            )
        }
    }
}