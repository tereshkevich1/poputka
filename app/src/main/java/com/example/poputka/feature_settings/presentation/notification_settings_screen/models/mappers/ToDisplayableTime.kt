package com.example.poputka.feature_settings.presentation.notification_settings_screen.models.mappers

import com.example.poputka.common.presentation.util.DateTimeUtils
import com.example.poputka.feature_settings.presentation.personal_settings_screen.DisplayableLong
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Long.toDisplayableTime(): DisplayableLong {
    val localTime = Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalTime()
    val formatted = localTime.format(DateTimeFormatter.ofPattern(DateTimeUtils.DEFAULT_TIME_PATTERN))
    return DisplayableLong(this, formatted)
}