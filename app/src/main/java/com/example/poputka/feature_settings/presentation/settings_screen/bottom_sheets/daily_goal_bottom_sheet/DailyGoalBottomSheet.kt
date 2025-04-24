package com.example.poputka.feature_settings.presentation.settings_screen.bottom_sheets.daily_goal_bottom_sheet

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.poputka.R
import com.example.poputka.feature_settings.presentation.settings_screen.bottom_sheets.common.SettingsBottomSheet
import com.example.poputka.ui.theme.DpSpSize.paddingMedium
import com.example.poputka.ui.theme.DpSpSize.paddingSmall

@Composable
fun DailyGoalBottomSheet(
    state: DailyGoalState,
    onAction: (DailyGoalAction) -> Unit,
    onDismissRequest: () -> Unit,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    SettingsBottomSheet(
        title = stringResource(R.string.daily_goal),
        onDismissRequest = {
            onAction(DailyGoalAction.OnCancelClick)
            onDismissRequest()
        },
        onSaveClick = {
            onAction(DailyGoalAction.OnSaveClick)
            onSaveClick()
        },
        onCancelClick = {
            onAction(DailyGoalAction.OnCancelClick)
            onCancelClick()
        },
        skipPartiallyExpanded = true
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = paddingMedium),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.auto_calculation),
                style = MaterialTheme.typography.titleMedium
            )

            Switch(
                checked = state.autoCalculation,
                onCheckedChange = {
                    onAction(DailyGoalAction.OnAutoToggle(it))
                }
            )
        }

        Spacer(modifier = Modifier.height(paddingSmall))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = paddingMedium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .border(
                        width = 6.dp,
                        color = MaterialTheme.colorScheme.tertiary,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${state.currentGoal} ${state.currentVolumeUnit.asString()}",
                    style = MaterialTheme.typography.headlineSmall
                )
            }

            Spacer(modifier = Modifier.width(paddingMedium))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (state.autoCalculation) stringResource(R.string.recommended_goal)
                    else stringResource(R.string.manual_goal),
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(paddingSmall))
                Text(
                    text = if (state.autoCalculation) stringResource(R.string.auto_calculation_description)
                    else stringResource(R.string.manual_input_description),
                    color = MaterialTheme.colorScheme.outline,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
    if (state.showDialog) {
        GoalInputDialog(
            inputValue = state.inputValue,
            errorMessage = state.errorMessage?.asString() ?: "",
            volumeUnit = state.currentVolumeUnit.asString(),
            onValueChange = { onAction(DailyGoalAction.OnGoalValueChange(it)) },
            onDismissRequest = { onAction(DailyGoalAction.OnDialogDismiss) },
            onConfirmation = { onAction(DailyGoalAction.OnDialogConfirm) }
        )
    }
}


