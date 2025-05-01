package com.example.poputka.feature_settings.presentation.settings_screen

sealed class SettingsScreenEvent {
    data class ShowToast(val message: String): SettingsScreenEvent()
    data object NavigateToPersonalInfoScreen: SettingsScreenEvent()
    data object NavigateToNotificationSettingsScreen: SettingsScreenEvent()
}