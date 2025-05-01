package com.example.poputka.feature_settings.presentation.notification_settings_screen.models

import com.example.poputka.feature_settings.presentation.personal_settings_screen.DisplayableLong

data class NotificationUi(
    val id: Int,
    val titleResId: Int,
    val time: DisplayableLong,
    val isEnabled: Boolean
)