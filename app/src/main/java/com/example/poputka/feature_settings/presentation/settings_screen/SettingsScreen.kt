package com.example.poputka.feature_settings.presentation.settings_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.poputka.core.domain.model.volumeUnitList
import com.example.poputka.core.global_state.local_settings_state.LocalSettingsState
import com.example.poputka.core.presentation.constants.UiConstants.bottomNavAndFabPadding
import com.example.poputka.feature_settings.presentation.SettingsViewModel
import com.example.poputka.feature_settings.presentation.settings_screen.components.GeneralSettingsSection
import com.example.poputka.feature_settings.presentation.settings_screen.components.PersonalizationSettingsSection
import com.example.poputka.feature_settings.presentation.settings_screen.components.ProgressOverviewSection
import com.example.poputka.feature_settings.presentation.settings_screen.components.SynchronizeProfileSection
import com.example.poputka.feature_settings.presentation.settings_screen.components.bottom_sheets.VolumeUnitBottomSheet
import com.example.poputka.ui.theme.DpSpSize.paddingMedium
import com.example.poputka.ui.theme.PoputkaTheme
import kotlinx.coroutines.launch

@Composable
fun SettingsScreenRoute(
    onNavigateToPersonalScree: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val state by viewModel.settingsState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(Unit) {
        lifecycleOwner.lifecycleScope.launch {
            viewModel.events.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect {
                    when (it) {
                        is SettingsScreenEvent.ShowToast -> {

                        }

                        SettingsScreenEvent.NavigateToPersonalInfoScreen -> onNavigateToPersonalScree()
                    }
                }
        }
    }

    SettingsScreen(
        state = state,
        onAction = viewModel::onAction,
        modifier = modifier
    )
}

@Composable
fun SettingsScreen(
    state: SettingsScreenState,
    onAction: (SettingsScreenAction) -> Unit,
    modifier: Modifier
) {
    val currentVolumeUnit = LocalSettingsState.current

    Surface(modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.error)) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(
                    start = paddingMedium,
                    end = paddingMedium,
                    bottom = bottomNavAndFabPadding
                ),
            verticalArrangement = Arrangement.Top,
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
                onMeasurementUnitsClick = { onAction(SettingsScreenAction.OnMeasurementSettingsClick) }
            )
            PersonalizationSettingsSection(
                onPersonalInfoClick = { onAction(SettingsScreenAction.OnPersonalSettingsClick) },
                onWeatherClick = {},
                onTimeFormatClick = {}
            )
        }
        if (state.showBottomSheet) {
            when (state.settingsBottomSheetType) {
                SettingsBottomSheetType.MEASUREMENT_BOTTOM_SHEET -> {
                    val (selectedOption, onOptionSelected) = remember {
                        mutableStateOf(
                            currentVolumeUnit.name
                        )
                    }
                    VolumeUnitBottomSheet(
                        onDismissRequest = { onAction(SettingsScreenAction.OnBottomSheetClosed) },
                        onSaveClick = {},
                        onCancelClick = { onAction(SettingsScreenAction.OnBottomSheetClosed) },
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
    }
}