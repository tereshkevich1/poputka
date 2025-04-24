package com.example.poputka.feature_settings.presentation.personal_settings_screen

import com.example.poputka.feature_settings.domain.model.ActivityLevel
import com.example.poputka.feature_settings.domain.model.Gender

sealed class PersonalSettingsAction {
    data object OnBackClick : PersonalSettingsAction()
    data object OnBottomSheetClosed : PersonalSettingsAction()

    data object OnBirthdayClick : PersonalSettingsAction()
    data object OnGenderClick : PersonalSettingsAction()
    data object OnHeightClick : PersonalSettingsAction()
    data object OnWeightClick : PersonalSettingsAction()
    data object OnActivityLevelClick : PersonalSettingsAction()

    data class OnBirthdaySave(val birthday: Long?) : PersonalSettingsAction()
    data class OnGenderSave(val gender: Gender) : PersonalSettingsAction()
    data class OnHeightSave(val height: String?) : PersonalSettingsAction()
    data class OnWeightSave(val integerPart: String, val decimalPart: String) :
        PersonalSettingsAction()

    data class OnActivityLevelSave(val level: ActivityLevel) : PersonalSettingsAction()
}