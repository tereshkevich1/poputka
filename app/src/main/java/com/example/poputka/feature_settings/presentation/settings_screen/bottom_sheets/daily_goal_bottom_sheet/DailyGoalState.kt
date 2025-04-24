package com.example.poputka.feature_settings.presentation.settings_screen.bottom_sheets.daily_goal_bottom_sheet

import com.example.poputka.R
import com.example.poputka.core.presentation.UiText

data class DailyGoalState(
    val currentVolumeUnit: UiText = UiText.StringResource(R.string.ml),
    val currentGoal: String = "",
    val autoCalculation: Boolean = true,
    val showDialog: Boolean = false,
    val inputValue: String = "",
    val errorMessage: UiText? = null
)