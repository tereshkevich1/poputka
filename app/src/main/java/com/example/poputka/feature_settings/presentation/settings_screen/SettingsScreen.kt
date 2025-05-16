package com.example.poputka.feature_settings.presentation.settings_screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import com.example.poputka.common.global_state.local_settings_state.LocalSettingsState
import com.example.poputka.common.presentation.constants.UiConstants.bottomNavAndFabPadding
import com.example.poputka.common.presentation.models.asUiText
import com.example.poputka.feature_settings.presentation.SettingsViewModel
import com.example.poputka.feature_settings.presentation.settings_screen.bottom_sheets.VolumeUnitBottomSheet
import com.example.poputka.feature_settings.presentation.settings_screen.bottom_sheets.daily_goal_bottom_sheet.DailyGoalBottomSheet
import com.example.poputka.feature_settings.presentation.settings_screen.bottom_sheets.daily_goal_bottom_sheet.DailyGoalViewModel
import com.example.poputka.feature_settings.presentation.settings_screen.components.GeneralSettingsSection
import com.example.poputka.feature_settings.presentation.settings_screen.components.PersonalizationSettingsSection
import com.example.poputka.feature_settings.presentation.settings_screen.components.ProgressOverviewSection
import com.example.poputka.feature_settings.presentation.settings_screen.components.SynchronizeProfileSection
import com.example.poputka.ui.theme.DpSpSize.paddingMedium
import com.example.poputka.ui.theme.PoputkaTheme
import kotlinx.coroutines.launch

@Composable
fun SettingsScreenRoute(
    onNavigateToPersonalScree: () -> Unit,
    onNavigateToNotificationSettingsScreen: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val state by viewModel.settingsState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(Unit) {
        lifecycleOwner.lifecycleScope.launch {
            viewModel.events.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect {
                    when (it) {
                        is SettingsScreenEvent.ShowToast -> Toast.makeText(
                            context,
                            it.message,
                            Toast.LENGTH_LONG
                        )

                        SettingsScreenEvent.NavigateToPersonalInfoScreen -> onNavigateToPersonalScree()
                        SettingsScreenEvent.NavigateToNotificationSettingsScreen -> onNavigateToNotificationSettingsScreen()
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
    val currentAppPrefState = LocalSettingsState.current
    val volumeUnit = currentAppPrefState.volumeUnitSetting

    Column(
        modifier = modifier
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
            "100 ${volumeUnit.asUiText().asString()}",
            "20 дней"
        )

        GeneralSettingsSection(
            onNotificationsClick = { onAction(SettingsScreenAction.OnNotificationSettingsClick) },
            onDailyGoalClick = { onAction(SettingsScreenAction.OnDailyGoalSettingsClick) },
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
        when (state.bottomSheet) {
            SettingsBottomSheetType.MEASUREMENT_BOTTOM_SHEET -> {
                VolumeUnitBottomSheet(
                    onDismissRequest = { onAction(SettingsScreenAction.OnBottomSheetClosed) },
                    onSaveClick = { onAction(SettingsScreenAction.OnSaveVolumeUnit(it)) },
                    onCancelClick = { onAction(SettingsScreenAction.OnBottomSheetClosed) },
                    currentUnit = volumeUnit
                )
            }

            SettingsBottomSheetType.DAILY_GOAL_BOTTOM_SHEET -> {
                val goalViewModel = hiltViewModel<DailyGoalViewModel>()
                val goalState by goalViewModel.state.collectAsStateWithLifecycle()
                DailyGoalBottomSheet(
                    state = goalState,
                    onAction = goalViewModel::onAction,
                    onDismissRequest = { onAction(SettingsScreenAction.OnBottomSheetClosed) },
                    onSaveClick = { onAction(SettingsScreenAction.OnBottomSheetClosed) },
                    onCancelClick = { onAction(SettingsScreenAction.OnBottomSheetClosed) }
                )
            }

            null -> {}
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