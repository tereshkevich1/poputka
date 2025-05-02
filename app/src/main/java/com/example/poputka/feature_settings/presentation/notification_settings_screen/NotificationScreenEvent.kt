package com.example.poputka.feature_settings.presentation.notification_settings_screen

sealed class NotificationScreenEvent() {
    data class ShowToast(val message: String) : NotificationScreenEvent()
    data object NavigateToSettingsScreen : NotificationScreenEvent()
}