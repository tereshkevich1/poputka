package com.example.poputka.feature_settings.presentation.settings_screen.bottom_sheets

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.poputka.R
import com.example.poputka.feature_settings.presentation.settings_screen.bottom_sheets.common.RadioButtonSingleSelection
import com.example.poputka.feature_settings.presentation.settings_screen.bottom_sheets.common.SettingsBottomSheet

@Composable
fun VolumeUnitBottomSheet(
    onDismissRequest: () -> Unit,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
    radioOptions: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
) {
    SettingsBottomSheet(
        title = stringResource(R.string.title_measurement_units),
        onDismissRequest = onDismissRequest,
        onSaveClick = onSaveClick,
        onCancelClick = onCancelClick
    ) {
        RadioButtonSingleSelection(
            radioOptions = radioOptions,
            selectedOption = selectedOption,
            onOptionSelected = onOptionSelected
        )
    }
}

