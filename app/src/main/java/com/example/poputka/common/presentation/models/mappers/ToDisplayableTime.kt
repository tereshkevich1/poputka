package com.example.poputka.common.presentation.models.mappers

import com.example.poputka.common.presentation.models.DisplayableLong
import com.example.poputka.common.presentation.util.DateTimeUtils
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