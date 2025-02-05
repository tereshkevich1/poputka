@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.poputka.presentation.profile.profile_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.poputka.R
import com.example.poputka.core.volumeUnitList
import com.example.poputka.data.settings.SettingsDataStore
import com.example.poputka.presentation.profile.profile_screen.components.GeneralSettingsSection
import com.example.poputka.presentation.profile.profile_screen.components.PersonalizationSettingsSection
import com.example.poputka.presentation.profile.profile_screen.components.ProgressOverviewSection
import com.example.poputka.presentation.profile.profile_screen.components.RadioButtonSingleSelection
import com.example.poputka.presentation.profile.profile_screen.components.SynchronizeProfileSection
import com.example.poputka.presentation.profile.profile_screen.components.bottom_sheets.ActionButtons
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
                onGenderWeightClick = {
                    showBottomSheet = true
                    settingsEvent = SettingsEvent.SelfSettings
                },
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

                SettingsEvent.SelfSettings -> {
                    SelfSettingsBottomSheet(
                        onDismissRequest = { showBottomSheet = false },
                        onSaveClick = {
                            showBottomSheet = false
                        },
                        onCancelClick = { showBottomSheet = false },
                    )
                }
            }
        }
    }
}

@Composable
fun TabRowComponent(
    tabs: List<TabItem>,
    contentScreens: List<@Composable () -> Unit>,
    modifier: Modifier = Modifier,
) {
    val enabledTextColor = MaterialTheme.colorScheme.onSurface
    val disabledTextColor = enabledTextColor.copy(alpha = 0.6f)
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Column(modifier = modifier) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
            contentColor = MaterialTheme.colorScheme.onSurface,
            indicator = { tabPositions ->
                SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                )
            },
            divider = {}
        ) {
            // Iterate through each tab title and create a tab
            tabs.forEachIndexed { index, tabItem ->
                Tab(
                    modifier = Modifier.height(44.dp),
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index }
                ) {

                    val color =
                        if (selectedTabIndex == index) enabledTextColor else disabledTextColor

                    Row {
                        Icon(
                            painter = tabItem.icon,
                            contentDescription = null,
                            tint = color,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(text = tabItem.title, color = color)
                    }
                }
            }
        }

        // Display the content screen corresponding to the selected tab
        contentScreens.getOrNull(selectedTabIndex)?.invoke()
    }
}

data class TabItem(val title: String, val icon: Painter)

@Composable
fun SelfSettingsBottomSheet(
    onDismissRequest: () -> Unit,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )
    ) {


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)

        ) {
            val tabItems = listOf(
                TabItem(stringResource(R.string.gender), painterResource(R.drawable.person)),
                TabItem(
                    stringResource(R.string.weight),
                    painterResource(R.drawable.monitor_weight)
                ),
                TabItem(stringResource(R.string.height), painterResource(R.drawable.height))
            )

            TabRowComponent(
                tabs = tabItems,
                contentScreens = listOf(
                    { GenderScreen() },
                    { WeightScreen() },
                    { HeightScreen() }
                ),
                modifier = Modifier
            )
            Spacer(modifier = Modifier.weight(1f))

            ActionButtons(onCancelClick = {}, onSaveClick = {})
        }
    }
}

@Composable
fun GenderScreen(

) {
    val (selectedOption, onOptionSelected) = remember {
        mutableStateOf(
            volumeUnitList[0]
        )
    }

    RadioButtonSingleSelection(
        radioOptions = volumeUnitList,
        selectedOption = selectedOption,
        onOptionSelected = onOptionSelected
    )
}

@Composable
fun WeightScreen() {
    Text("xlkgkjdrjgjjdriojgjdr")
}

@Composable
fun HeightScreen() {
    // Content for height screen
}


@SuppressLint("ViewModelConstructorInComposable")
@Composable
@Preview
fun ProfileScreenPreview() {
    PoputkaTheme {
        ProfileScreen(settingsViewModel = SettingsViewModel(SettingsDataStore(LocalContext.current)))
    }
}