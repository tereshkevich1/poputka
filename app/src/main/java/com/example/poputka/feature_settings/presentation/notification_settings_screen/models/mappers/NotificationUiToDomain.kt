package com.example.poputka.feature_settings.presentation.notification_settings_screen.models.mappers

import com.example.poputka.feature_notifications.domain.models.Notification
import com.example.poputka.feature_settings.presentation.notification_settings_screen.models.NotificationUi

fun NotificationUi.toDomain(): Notification {
    return Notification(
        id = id,
        titleResId = titleResId,
        time = time.value,
        isEnabled = isEnabled
    )
}