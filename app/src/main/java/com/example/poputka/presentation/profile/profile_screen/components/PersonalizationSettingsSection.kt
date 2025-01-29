package com.example.poputka.presentation.profile.profile_screen.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.poputka.R

@Composable
fun PersonalizationSettingsSection(
    onGenderWeightClick: () -> Unit,
    onWeatherClick: () -> Unit,
    onTimeFormatClick: () -> Unit,
) {
    val paddingMedium = dimensionResource(R.dimen.padding_medium)
    SettingsBlock {
        SettingsRowItem(
            onClick = onGenderWeightClick,
            painterResource(R.drawable.person),
            stringResource(R.string.gender_and_weight),
            paddingMedium
        )
        SettingsRowItem(
            onClick = onWeatherClick,
            painterResource(R.drawable.cloud),
            stringResource(R.string.weather),
            paddingMedium
        )
        SettingsRowItem(
            onClick = onTimeFormatClick,
            painterResource(R.drawable.time),
            stringResource(R.string.time_format),
            paddingMedium
        )
    }
}