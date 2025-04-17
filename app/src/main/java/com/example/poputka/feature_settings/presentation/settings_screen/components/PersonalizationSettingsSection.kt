package com.example.poputka.feature_settings.presentation.settings_screen.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.poputka.R
import com.example.poputka.ui.theme.DpSpSize.paddingMedium

@Composable
fun PersonalizationSettingsSection(
    onPersonalInfoClick: () -> Unit,
    onWeatherClick: () -> Unit,
    onTimeFormatClick: () -> Unit,
) {
    SettingsBlock {
        SettingsNavigationRow(
            onClick = onPersonalInfoClick,
            settingsIcon = painterResource(R.drawable.person),
            description = stringResource(R.string.gender_and_weight),
            padding = paddingMedium
        )
        SettingsNavigationRow(
            onClick = onWeatherClick,
            settingsIcon = painterResource(R.drawable.cloud),
            description = stringResource(R.string.weather),
            padding = paddingMedium
        )
        SettingsNavigationRow(
            onClick = onTimeFormatClick,
            settingsIcon = painterResource(R.drawable.time),
            description = stringResource(R.string.time_format),
            padding = paddingMedium
        )
    }
}