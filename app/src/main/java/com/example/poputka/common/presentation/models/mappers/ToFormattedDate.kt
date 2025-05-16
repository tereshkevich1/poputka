package com.example.poputka.common.presentation.models.mappers

import android.text.format.DateUtils
import android.text.format.DateUtils.isToday
import com.example.poputka.common.presentation.models.DisplayableLong
import com.example.poputka.common.presentation.util.DateTimeUtils
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.toDisplayableDate(): DisplayableLong {
    val date = Date(this)
    val formatter = SimpleDateFormat(DateTimeUtils.DATE_PATTERN_DOT_SEPARATOR, Locale.getDefault())
    val formatted = formatter.format(date)
    return DisplayableLong(this, formatted)
}

fun Long.toSmartDisplayableDate(): DisplayableLong {
    val value = this
    val formatted = when {
        isToday(value) -> "Сегодня"
        isYesterday(value) -> "Вчера"
        else -> {
            val formatter = SimpleDateFormat("EEEE, d MMMM", Locale.getDefault())
            formatter.format(Date(value))
        }
    }
    return DisplayableLong(value, formatted)
}

fun isYesterday(timestamp: Long): Boolean {
    return isToday(timestamp + DateUtils.DAY_IN_MILLIS)
}



