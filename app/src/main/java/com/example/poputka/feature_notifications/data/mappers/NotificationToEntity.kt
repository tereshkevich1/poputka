package com.example.poputka.feature_notifications.data.mappers

import com.example.poputka.feature_notifications.domain.models.Notification
import com.example.poputka.feature_notifications.data.models.NotificationEntity

fun Notification.toEntity(): NotificationEntity = NotificationEntity(
    id = id,
    titleResId = titleResId,
    time = time,
    isEnabled = isEnabled
)