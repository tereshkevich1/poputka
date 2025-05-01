package com.example.poputka.feature_settings.domain

import kotlinx.coroutines.flow.StateFlow

interface NotificationSettingsStateHolder {
    val notificationSettingsFlow: StateFlow<NotificationSettingsStateH>
    suspend fun updateNotificationsEnabled(isEnabled: Boolean)
}

