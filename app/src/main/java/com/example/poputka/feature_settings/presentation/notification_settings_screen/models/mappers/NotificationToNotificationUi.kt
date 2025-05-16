package com.example.poputka.feature_settings.presentation.notification_settings_screen.models.mappers

import com.example.poputka.common.presentation.models.mappers.toDisplayableTime
import com.example.poputka.feature_notifications.domain.models.Notification
import com.example.poputka.feature_settings.presentation.notification_settings_screen.models.NotificationUi

fun Notification.toNotificationUi(): NotificationUi {
    return NotificationUi(
        id = id,
        titleResId = titleResId,
        time = time.toDisplayableTime(),
        isEnabled = isEnabled
    )
}