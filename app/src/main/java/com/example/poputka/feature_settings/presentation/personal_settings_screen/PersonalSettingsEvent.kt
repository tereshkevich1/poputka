package com.example.poputka.feature_settings.presentation.personal_settings_screen

sealed class PersonalSettingsEvent {
    data class ShowToast(val message: String) : PersonalSettingsEvent()
    data object NavigateToSettingsScreen : PersonalSettingsEvent()
}