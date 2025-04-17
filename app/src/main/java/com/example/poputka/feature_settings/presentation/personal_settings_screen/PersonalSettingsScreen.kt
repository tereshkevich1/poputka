@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.poputka.feature_settings.presentation.personal_settings_screen

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
import com.example.poputka.feature_settings.presentation.settings_screen.components.SettingsInfoRow
import com.example.poputka.feature_settings.presentation.settings_screen.components.bottom_sheets.HeightBottomSheet
import com.example.poputka.feature_settings.presentation.settings_screen.components.bottom_sheets.WeightBottomSheet
import com.example.poputka.ui.theme.DpSpSize.paddingLarge
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun PersonalSettingsRoute(
    onNavigateToSettingsScreen: () -> Unit,
    viewModel: PersonalSettingsViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(Unit) {
        lifecycleOwner.lifecycleScope.launch {
            viewModel.events.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect {
                    when (it) {
                        is PersonalSettingsEvent.ShowToast -> TODO()

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
    state: PersonalSettingsState,
    onAction: (PersonalSettingsAction) -> Unit,
    modifier: Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.personal_settings)) },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "backIcon")
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
                onClick = {

                },
                settingsIcon = painterResource(R.drawable.cake),
                description = stringResource(R.string.birthday),
                info = "25 нояб. 2003 г.",
                padding = paddingLarge
            )

            SettingsInfoRow(
                onClick = {},
                settingsIcon = painterResource(R.drawable.person),
                description = stringResource(R.string.gender),
                info = "Мужской",
                padding = paddingLarge
            )

            SettingsInfoRow(
                onClick = { onAction(PersonalSettingsAction.OnHeightClick) },
                settingsIcon = painterResource(R.drawable.height),
                description = stringResource(R.string.height),
                info = "175 см",
                padding = paddingLarge
            )

            SettingsInfoRow(
                onClick = { onAction(PersonalSettingsAction.OnWeightClick) },
                settingsIcon = painterResource(R.drawable.monitor_weight),
                description = stringResource(R.string.weight),
                info = "59 кг",
                padding = paddingLarge
            )

            SettingsInfoRow(
                onClick = {},
                settingsIcon = painterResource(R.drawable.activity),
                description = stringResource(R.string.activity_level),
                info = "Умеренная",
                padding = paddingLarge
            )
        }

        if (state.showBottomSheet) {
            when (state.bottomSheet) {
                PersonalSettingsBottomSheet.BIRTHDAY -> TODO()
                PersonalSettingsBottomSheet.GENDER -> TODO()

                PersonalSettingsBottomSheet.HEIGHT -> {
                    HeightBottomSheet(
                        onDismissRequest = { onAction(PersonalSettingsAction.OnBottomSheetClosed) },
                        onSaveClick = {},
                        onCancelClick = { onAction(PersonalSettingsAction.OnBottomSheetClosed) }
                    )
                }

                PersonalSettingsBottomSheet.WEIGHT -> {
                    WeightBottomSheet(
                        onDismissRequest = { onAction(PersonalSettingsAction.OnBottomSheetClosed) },
                        onSaveClick = {},
                        onCancelClick = { onAction(PersonalSettingsAction.OnBottomSheetClosed) }
                    )
                }

                PersonalSettingsBottomSheet.ACTIVITY_LEVEL -> TODO()

                null -> {}
            }
        }
    }
}
