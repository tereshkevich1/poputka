package com.example.poputka.feature_settings.presentation.settings_screen.bottom_sheets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.poputka.R
import com.example.poputka.common.domain.model.VolumeUnit
import com.example.poputka.common.presentation.models.asUiText
import com.example.poputka.feature_settings.presentation.settings_screen.bottom_sheets.common.RadioButtonSingleSelection
import com.example.poputka.feature_settings.presentation.settings_screen.bottom_sheets.common.SettingsBottomSheet
import com.example.poputka.ui.theme.DpSpSize

@Composable
fun VolumeUnitBottomSheet(
    onDismissRequest: () -> Unit,
    onSaveClick: (selectedUnit: VolumeUnit) -> Unit,
    onCancelClick: () -> Unit,
    currentUnit: VolumeUnit
) {
    var selectedOption by remember { mutableStateOf(currentUnit) }

    SettingsBottomSheet(
        title = stringResource(R.string.title_measurement_units),
        onDismissRequest = onDismissRequest,
        onSaveClick = { onSaveClick(selectedOption) },
        onCancelClick = onCancelClick
    ) {
        Column(Modifier.selectableGroup()) {
            VolumeUnit.entries.forEach { unit ->
                val text = unit.asUiText().asString()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .selectable(
                            selected = (unit == selectedOption),
                            onClick = { selectedOption = unit },
                            role = Role.RadioButton
                        )
                        .padding(DpSpSize.paddingMedium),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (unit == selectedOption),
                        onClick = null
                    )
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = DpSpSize.paddingMedium)
                    )
                }
            }
        }
    }
}
