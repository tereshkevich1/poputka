package com.example.poputka.common.presentation.models.mappers

import com.example.poputka.common.presentation.models.DisplayableLocaleDate
import com.example.poputka.common.presentation.util.DateTimeUtils
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun LocalDate.toDisplayableDateFull(): DisplayableLocaleDate {
    val formatter = DateTimeFormatter.ofPattern(DateTimeUtils.DATE_PATTERN_DAY_MONTH_YEAR_FULL)
    val formatted = this.format(formatter)
    return DisplayableLocaleDate(this, formatted)
}