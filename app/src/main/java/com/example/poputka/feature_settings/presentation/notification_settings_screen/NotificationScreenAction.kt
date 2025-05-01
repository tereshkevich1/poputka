package com.example.poputka.feature_settings.presentation.notification_settings_screen

import com.example.poputka.feature_settings.presentation.notification_settings_screen.models.NotificationUi

sealed class NotificationScreenAction {
    data class OnNotificationClick(val notification: NotificationUi) : NotificationScreenAction()
    data class OnTimeSelected(val hour: Int, val minute: Int) : NotificationScreenAction()
    data object OnTimePickerDismissed : NotificationScreenAction()
    data class OnNotificationToggle(val notificationId: NotificationUi, val isEnabled: Boolean) :
        NotificationScreenAction()

    data class OnNotificationSettingsToggle(val isEnabled: Boolean) : NotificationScreenAction()
}