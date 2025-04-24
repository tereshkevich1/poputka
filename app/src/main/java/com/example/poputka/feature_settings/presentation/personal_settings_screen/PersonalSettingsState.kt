package com.example.poputka.feature_settings.presentation.personal_settings_screen

import com.example.poputka.core.presentation.util.DateUtils.DEFAULT_DATE_PATTERN
import com.example.poputka.feature_settings.domain.PersonalSettingsState
import com.example.poputka.feature_settings.domain.model.ActivityLevel
import com.example.poputka.feature_settings.domain.model.Gender
import com.example.poputka.feature_settings.presentation.personal_settings_screen.models.PersonalSettingsBottomSheet
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


data class PersonalSettingsScreenState(
    val display: PersonalSettingsStateUi = PersonalSettingsStateUi(),
    val showBottomSheet: Boolean = false,
    val bottomSheet: PersonalSettingsBottomSheet? = null
)

data class PersonalSettingsStateUi(
    val birthday: DisplayableLong = DisplayableLong(0, ""),
    val height: String = "",
    val weight: Pair<String, String> = Pair("", ""),
    val gender: Gender = Gender.MAN,
    val activityLevel: ActivityLevel = ActivityLevel.LOW

)

fun PersonalSettingsState.toUi(): PersonalSettingsStateUi =
    PersonalSettingsStateUi(
        birthday = birthday.toFormattedDate(),
        height = height.toString(),
        weight = weight.toWeightParts(),
        gender = gender,
        activityLevel = activityLevel
    )


data class DisplayableLong(val value: Long, val formatted: String)

fun Long.toFormattedDate(): DisplayableLong {
    val date = Date(this)
    val formatter = SimpleDateFormat(DEFAULT_DATE_PATTERN, Locale.getDefault())
    val formatted = formatter.format(date)
    return DisplayableLong(this, formatted)
}

fun Int.toWeightParts(): Pair<String, String> {
    val kg = this / 1000
    val decimal = (this % 1000) / 100
    return kg.toString() to decimal.toString()
}