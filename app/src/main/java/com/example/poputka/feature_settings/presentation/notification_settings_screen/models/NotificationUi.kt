package com.example.poputka.feature_settings.presentation.notification_settings_screen.models

import com.example.poputka.common.presentation.models.DisplayableLong

data class NotificationUi(
    val id: Int,
    val titleResId: Int,
    val time: DisplayableLong,
    val isEnabled: Boolean
)