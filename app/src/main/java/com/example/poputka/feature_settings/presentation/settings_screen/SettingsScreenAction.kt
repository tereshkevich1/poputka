package com.example.poputka.feature_settings.presentation.settings_screen

sealed class SettingsScreenAction {
    data object OnSynchronizeProfileClick : SettingsScreenAction()

    data object OnBottomSheetClosed : SettingsScreenAction()
    data object OnNotificationSettingsClick : SettingsScreenAction()

    data object OnSoundSettingsClick : SettingsScreenAction()
    data object OnMeasurementSettingsClick : SettingsScreenAction()
    data object OnDailyGoalSettingsClick : SettingsScreenAction()

    data object OnPersonalSettingsClick : SettingsScreenAction()
    data object OnWeatherSettingsClick : SettingsScreenAction()
    data object OnTimeFormatSettingsClick : SettingsScreenAction()

    data class OnChangeVolumeUnit(val value: String) : SettingsScreenAction()
}


