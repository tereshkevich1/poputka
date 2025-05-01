package com.example.poputka.common.domain.use_case.format

import android.text.format.DateUtils
import android.text.format.DateUtils.isToday
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class DateFormatUseCase @Inject constructor() {
    operator fun invoke(timestamp: Long): String {
        if (isToday(timestamp)) return "Сегодня"

        if (isYesterday(timestamp)) return "Вчера"

        val formatter = SimpleDateFormat("EEEE, d MMMM", Locale.getDefault())
        return formatter.format(Date(timestamp))
    }
}

fun isYesterday(d: Long): Boolean {
    return isToday(d + DateUtils.DAY_IN_MILLIS)
}
