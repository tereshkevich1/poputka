package com.example.poputka.feature_settings.presentation.settings_screen.bottom_sheets.daily_goal_bottom_sheet

sealed class DailyGoalAction {
    data class OnAutoToggle(val enabled: Boolean) : DailyGoalAction()
    data class OnGoalValueChange(val value: String) : DailyGoalAction()
    data object OnDialogConfirm : DailyGoalAction()
    data object OnDialogDismiss : DailyGoalAction()
    data object OnSaveClick : DailyGoalAction()
    data object OnCancelClick : DailyGoalAction()
}