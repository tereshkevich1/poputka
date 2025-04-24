package com.example.poputka.feature_settings.presentation.settings_screen

data class SettingsScreenState(
    val showBottomSheet: Boolean = false,
    val bottomSheet: SettingsBottomSheetType? = null
)

enum class SettingsBottomSheetType {
    MEASUREMENT_BOTTOM_SHEET,
    DAILY_GOAL_BOTTOM_SHEET
}