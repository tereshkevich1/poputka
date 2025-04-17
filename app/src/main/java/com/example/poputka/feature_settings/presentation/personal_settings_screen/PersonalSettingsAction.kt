package com.example.poputka.feature_settings.presentation.personal_settings_screen

sealed class PersonalSettingsAction {
    data object OnBackClick : PersonalSettingsAction()
    data object OnBottomSheetClosed : PersonalSettingsAction()

    data object OnBirthdayClick : PersonalSettingsAction()
    data object OnGenderClick : PersonalSettingsAction()
    data object OnHeightClick : PersonalSettingsAction()
    data object OnWeightClick : PersonalSettingsAction()
    data object OnActivityLevelClick : PersonalSettingsAction()
}