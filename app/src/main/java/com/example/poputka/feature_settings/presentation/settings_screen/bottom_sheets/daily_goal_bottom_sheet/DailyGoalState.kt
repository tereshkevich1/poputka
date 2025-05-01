package com.example.poputka.feature_settings.presentation.settings_screen.bottom_sheets.daily_goal_bottom_sheet

import com.example.poputka.common.domain.model.VolumeUnit
import com.example.poputka.core.presentation.UiText

data class DailyGoalState(
    val currentVolumeUnit: VolumeUnit = VolumeUnit.Milliliters,
    val currentGoal: String = "",
    val autoCalculation: Boolean = true,
    val showDialog: Boolean = false,
    val inputValue: String = "",
    val errorMessage: UiText? = null
)