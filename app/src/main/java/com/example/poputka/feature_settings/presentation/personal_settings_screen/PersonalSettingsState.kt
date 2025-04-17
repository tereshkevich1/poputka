package com.example.poputka.feature_settings.presentation.personal_settings_screen

data class PersonalSettingsState(
    val birthday: String = "",
    val gender: String = "",
    val height: String = "",
    val weight: String = "",
    val activityLevel: String = "",
    val showBottomSheet: Boolean = false,
    val bottomSheet: PersonalSettingsBottomSheet? = null
)