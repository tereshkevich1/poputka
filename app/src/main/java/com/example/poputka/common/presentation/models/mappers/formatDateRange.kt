package com.example.poputka.common.presentation.models.mappers

import com.example.poputka.common.presentation.util.DateTimeUtils
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun formatDateRange(startDate: LocalDate, endDate: LocalDate): String {
    val currentYear = LocalDate.now().year
    val longFormatter = DateTimeFormatter.ofPattern(DateTimeUtils.DATE_PATTERN_DAY_MONTH_SHORT_COMMA_YEAR_FULL)
    return if (endDate.year == currentYear) {
        val shortFormatter = DateTimeFormatter.ofPattern(DateTimeUtils.DATE_PATTERN_DAY_MONTH_SHORT)

        "${shortFormatter.format(startDate)} - ${longFormatter.format(endDate)}"
    } else "${longFormatter.format(startDate)} - ${longFormatter.format(endDate)}"
}