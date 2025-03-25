@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.poputka.feature_settings.presentation.settings_screen.components.personal_info_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.poputka.R
import com.example.poputka.presentation.profile.settings_screen.components.SettingsInfoRow
import com.example.poputka.ui.theme.PoputkaTheme

@ExperimentalMaterial3Api
@Composable
fun PersonalInfoScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        val padding = 24.dp



        SettingsInfoRow(
            onClick = {},
            settingsIcon = painterResource(R.drawable.cake),
            description = stringResource(R.string.birthday),
            info = "25 нояб. 2003 г.",
            padding = padding
        )

        SettingsInfoRow(
            onClick = {},
            settingsIcon = painterResource(R.drawable.person),
            description = stringResource(R.string.gender),
            info = "Мужской",
            padding = padding
        )

        SettingsInfoRow(
            onClick = {},
            settingsIcon = painterResource(R.drawable.height),
            description = stringResource(R.string.height),
            info = "175 см",
            padding = padding
        )

        SettingsInfoRow(
            onClick = {},
            settingsIcon = painterResource(R.drawable.monitor_weight),
            description = stringResource(R.string.weight),
            info = "59 кг",
            padding = padding
        )

        SettingsInfoRow(
            onClick = {},
            settingsIcon = painterResource(R.drawable.activity),
            description = stringResource(R.string.activity_level),
            info = "Умеренная",
            padding = padding
        )
    }

}


@Composable
@Preview
fun PersonalInfoScreenPreview() {
    PoputkaTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            PersonalInfoScreen()
        }
    }
}