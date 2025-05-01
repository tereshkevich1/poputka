package com.example.poputka.feature_notifications.data.mappers

import com.example.poputka.feature_notifications.domain.models.Notification
import com.example.poputka.feature_notifications.data.models.NotificationEntity

fun NotificationEntity.toDomain(): Notification = Notification(
    id = id,
    titleResId = titleResId,
    time = time,
    isEnabled = isEnabled
)