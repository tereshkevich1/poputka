package com.example.poputka.presentation.profile.profile_screen.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.poputka.R

@Composable
fun GeneralSettingsSection(
    onNotificationsClick: () -> Unit,
    onDailyGoalClick: () -> Unit,
    onSoundsAndVibrationClick: () -> Unit,
    onMeasurementUnitsClick: () -> Unit,
) {
    val paddingMedium = dimensionResource(R.dimen.padding_medium)
    SettingsBlock {
        SettingsRowItem(
            onClick = onNotificationsClick,
            painterResource(R.drawable.notifications),
            stringResource(R.string.notifications),
            paddingMedium
        )
        SettingsRowItem(
            onClick = onDailyGoalClick,
            painterResource(R.drawable.target),
            stringResource(R.string.daily_goal),
            paddingMedium
        )
        SettingsRowItem(
            onClick = onSoundsAndVibrationClick,
            painterResource(R.drawable.volume_up),
            stringResource(R.string.sounds_and_vibration),
            paddingMedium
        )
        SettingsRowItem(
            onClick = onMeasurementUnitsClick,
            painterResource(R.drawable.square_foot),
            stringResource(R.string.measurement_units),
            paddingMedium
        )
    }
}