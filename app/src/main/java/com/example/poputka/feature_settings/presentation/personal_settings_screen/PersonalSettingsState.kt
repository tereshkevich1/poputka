package com.example.poputka.feature_settings.presentation.personal_settings_screen

import com.example.poputka.common.presentation.models.DisplayableLong
import com.example.poputka.common.presentation.models.mappers.toDisplayableDate
import com.example.poputka.feature_settings.domain.PersonalSettingsState
import com.example.poputka.feature_settings.domain.model.ActivityLevel
import com.example.poputka.feature_settings.domain.model.Gender
import com.example.poputka.feature_settings.presentation.personal_settings_screen.models.PersonalSettingsBottomSheet


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
        birthday = birthday.toDisplayableDate(),
        height = height.toString(),
        weight = weight.toWeightParts(),
        gender = gender,
        activityLevel = activityLevel
    )


fun Int.toWeightParts(): Pair<String, String> {
    val kg = this / 1000
    val decimal = (this % 1000) / 100
    return kg.toString() to decimal.toString()
}