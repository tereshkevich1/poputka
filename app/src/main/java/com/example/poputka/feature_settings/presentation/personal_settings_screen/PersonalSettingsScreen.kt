@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.poputka.feature_settings.presentation.personal_settings_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.poputka.R
import com.example.poputka.common.presentation.components.DatePickerModule
import com.example.poputka.feature_settings.presentation.personal_settings_screen.models.PersonalSettingsBottomSheet
import com.example.poputka.feature_settings.presentation.personal_settings_screen.models.asUiText
import com.example.poputka.feature_settings.presentation.settings_screen.components.SettingsInfoRow
import com.example.poputka.feature_settings.presentation.personal_settings_screen.bottom_sheets.ActivityLevelBottomSheet
import com.example.poputka.feature_settings.presentation.personal_settings_screen.bottom_sheets.GenderBottomSheet
import com.example.poputka.feature_settings.presentation.personal_settings_screen.bottom_sheets.HeightBottomSheet
import com.example.poputka.feature_settings.presentation.personal_settings_screen.bottom_sheets.WeightBottomSheet
import com.example.poputka.ui.theme.DpSpSize.paddingLarge
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun PersonalSettingsRoute(
    onNavigateToSettingsScreen: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PersonalSettingsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(Unit) {
        lifecycleOwner.lifecycleScope.launch {
            viewModel.events.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect {
                    when (it) {
                        is PersonalSettingsEvent.ShowToast -> Toast.makeText(
                            context,
                            it.message,
                            Toast.LENGTH_LONG
                        )

                        PersonalSettingsEvent.NavigateToSettingsScreen -> onNavigateToSettingsScreen()
                    }
                }
        }
    }

    PersonalSettingsScreen(
        state = state,
        onAction = viewModel::onAction,
        modifier = modifier
    )
}

@Composable
fun PersonalSettingsScreen(
    state: PersonalSettingsScreenState,
    onAction: (PersonalSettingsAction) -> Unit,
    modifier: Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.personal_settings)) },
                navigationIcon = {
                    IconButton(onClick = { onAction(PersonalSettingsAction.OnBackClick) }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            stringResource(R.string.back_icon)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            SettingsInfoRow(
                onClick = { onAction(PersonalSettingsAction.OnBirthdayClick) },
                settingsIcon = painterResource(R.drawable.cake),
                description = stringResource(R.string.birthday),
                info = state.display.birthday.formatted,
                padding = paddingLarge
            )

            SettingsInfoRow(
                onClick = { onAction(PersonalSettingsAction.OnGenderClick) },
                settingsIcon = painterResource(R.drawable.person),
                description = stringResource(R.string.gender),
                info = state.display.gender.asUiText().asString(),
                padding = paddingLarge
            )

            SettingsInfoRow(
                onClick = { onAction(PersonalSettingsAction.OnHeightClick) },
                settingsIcon = painterResource(R.drawable.height),
                description = stringResource(R.string.height),
                info = "${state.display.height} ${stringResource(R.string.centimeters)}",
                padding = paddingLarge
            )

            SettingsInfoRow(
                onClick = { onAction(PersonalSettingsAction.OnWeightClick) },
                settingsIcon = painterResource(R.drawable.weight),
                description = stringResource(R.string.weight),
                info = "${state.display.weight.first}.${state.display.weight.second} ${stringResource(R.string.kg)}",
                padding = paddingLarge
            )

            SettingsInfoRow(
                onClick = { onAction(PersonalSettingsAction.OnActivityLevelClick) },
                settingsIcon = painterResource(R.drawable.activity),
                description = stringResource(R.string.activity_level),
                info = state.display.activityLevel.asUiText().asString(),
                padding = paddingLarge
            )
        }

        if (state.showBottomSheet) {
            when (state.bottomSheet) {
                PersonalSettingsBottomSheet.BIRTHDAY -> {
                    DatePickerModule(
                        initialSelectedDateMillis = state.display.birthday.value,
                        onDateSelected = { onAction(PersonalSettingsAction.OnBirthdaySave(it)) },
                        onDismiss = { onAction(PersonalSettingsAction.OnBottomSheetClosed) }
                    )
                }

                PersonalSettingsBottomSheet.GENDER -> {
                    GenderBottomSheet(
                        onDismissRequest = { onAction(PersonalSettingsAction.OnBottomSheetClosed) },
                        onSaveClick = { onAction(PersonalSettingsAction.OnGenderSave(it)) },
                        onCancelClick = { onAction(PersonalSettingsAction.OnBottomSheetClosed) },
                        currentGender = state.display.gender
                    )
                }

                PersonalSettingsBottomSheet.HEIGHT -> {
                    HeightBottomSheet(
                        onDismissRequest = { onAction(PersonalSettingsAction.OnBottomSheetClosed) },
                        onSaveClick = { onAction(PersonalSettingsAction.OnHeightSave(it)) },
                        onCancelClick = { onAction(PersonalSettingsAction.OnBottomSheetClosed) },
                        currentHeight = state.display.height
                    )
                }

                PersonalSettingsBottomSheet.WEIGHT -> {
                    WeightBottomSheet(
                        currentWeight = state.display.weight,
                        onDismissRequest = { onAction(PersonalSettingsAction.OnBottomSheetClosed) },
                        onSaveClick = { integer, decimal ->
                            onAction(
                                PersonalSettingsAction.OnWeightSave(
                                    integerPart = integer,
                                    decimalPart = decimal
                                )
                            )
                        },
                        onCancelClick = { onAction(PersonalSettingsAction.OnBottomSheetClosed) }
                    )
                }

                PersonalSettingsBottomSheet.ACTIVITY_LEVEL -> {
                    ActivityLevelBottomSheet(
                        onDismissRequest = { onAction(PersonalSettingsAction.OnBottomSheetClosed) },
                        onSaveClick = { onAction(PersonalSettingsAction.OnActivityLevelSave(it)) },
                        onCancelClick = { onAction(PersonalSettingsAction.OnBottomSheetClosed) },
                        currentActivityLevel = state.display.activityLevel
                    )
                }

                null -> {}
            }
        }
    }
}
