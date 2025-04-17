package com.example.poputka.feature_settings.presentation.settings_screen

data class SettingsScreenState(
    val showBottomSheet: Boolean = false,
    val settingsBottomSheetType: SettingsBottomSheetType = SettingsBottomSheetType.MEASUREMENT_BOTTOM_SHEET
)

enum class SettingsBottomSheetType {
    MEASUREMENT_BOTTOM_SHEET
}