package com.example.poputka.feature_notifications.domain.models

data class Notification(
    val id: Int,
    val titleResId: Int,
    val time: Long,
    val isEnabled: Boolean
)