package com.example.poputka.presentation.profile.profile_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.poputka.R
import com.example.poputka.core.volumeUnitList
import com.example.poputka.data.settings.SettingsDataStore
import com.example.poputka.presentation.profile.profile_screen.components.GeneralSettingsSection
import com.example.poputka.presentation.profile.profile_screen.components.PersonalizationSettingsSection
import com.example.poputka.presentation.profile.profile_screen.components.ProgressOverviewSection
import com.example.poputka.presentation.profile.profile_screen.components.SynchronizeProfileSection
import com.example.poputka.presentation.profile.profile_screen.components.bottom_sheets.VolumeUnitBottomSheet
import com.example.poputka.presentation.profile.profile_screen.util.SettingsEvent
import com.example.poputka.presentation.settings.LocalSettingsState
import com.example.poputka.presentation.settings.SettingsViewModel
import com.example.poputka.presentation.util.constants.UiConstants.bottomNavAndFabPadding
import com.example.poputka.ui.theme.PoputkaTheme

@Composable
fun ProfileScreen(settingsViewModel: SettingsViewModel = hiltViewModel()) {
    val currentVolumeUnit = LocalSettingsState.current.volumeUnitSetting
    var showBottomSheet by remember { mutableStateOf(false) }
    var settingsEvent by remember { mutableStateOf(SettingsEvent.MeasurementUnitsSettings) }

    val paddingMedium = dimensionResource(R.dimen.horizontal_default_padding)
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(
                    start = paddingMedium,
                    end = paddingMedium,
                    bottom = bottomNavAndFabPadding
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SynchronizeProfileSection(onClick = {})
            Spacer(modifier = Modifier.height(paddingMedium))
            ProgressOverviewSection(
                "100 ${currentVolumeUnit.abbreviation}",
                "20 дней"
            )

            GeneralSettingsSection(
                onNotificationsClick = {},
                onDailyGoalClick = {},
                onSoundsAndVibrationClick = {},
                onMeasurementUnitsClick = {
                    showBottomSheet = true
                    settingsEvent = SettingsEvent.MeasurementUnitsSettings
                }
            )
            PersonalizationSettingsSection(
                onGenderWeightClick = {},
                onWeatherClick = {},
                onTimeFormatClick = {}
            )
        }
        if (showBottomSheet) {
            when (settingsEvent) {
                SettingsEvent.MeasurementUnitsSettings -> {
                    val (selectedOption, onOptionSelected) = remember {
                        mutableStateOf(
                            currentVolumeUnit.name
                        )
                    }
                    VolumeUnitBottomSheet(
                        onDismissRequest = { showBottomSheet = false },
                        onSaveClick = {
                            settingsViewModel.changeVolumeUnit(selectedOption)
                            showBottomSheet = false
                        },
                        onCancelClick = { showBottomSheet = false },
                        radioOptions = volumeUnitList,
                        selectedOption = selectedOption,
                        onOptionSelected = onOptionSelected
                    )
                }
            }
        }
    }
}


@SuppressLint("ViewModelConstructorInComposable")
@Composable
@Preview
fun ProfileScreenPreview() {
    PoputkaTheme {
        ProfileScreen(settingsViewModel = SettingsViewModel(SettingsDataStore(LocalContext.current)))
    }
}