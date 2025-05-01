package com.example.poputka.feature_settings.presentation.notification_settings_screen

import com.example.poputka.feature_settings.presentation.notification_settings_screen.models.NotificationUi

data class NotificationSettingsState(
    val isNotificationEnabled: Boolean = false,
    val showTimePicker: Boolean = false,
    val selectedNotification: NotificationUi? = null,
    val notifications: List<NotificationUi> = emptyList()
)