package com.example.poputka.feature_settings.presentation.personal_settings_screen.bottom_sheets

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
import com.example.poputka.feature_settings.domain.model.ActivityLevel
import com.example.poputka.feature_settings.presentation.personal_settings_screen.models.asUiText
import com.example.poputka.feature_settings.presentation.settings_screen.bottom_sheets.common.SettingsBottomSheet
import com.example.poputka.ui.theme.DpSpSize

@Composable
fun ActivityLevelBottomSheet(
    onDismissRequest: () -> Unit,
    onSaveClick: (selectedOption: ActivityLevel) -> Unit,
    onCancelClick: () -> Unit,
    currentActivityLevel: ActivityLevel
) {
    var selectedOption by remember { mutableStateOf(currentActivityLevel) }

    SettingsBottomSheet(
        title = stringResource(R.string.title_activity_level),
        onDismissRequest = onDismissRequest,
        onSaveClick = { onSaveClick(selectedOption) },
        onCancelClick = onCancelClick
    ) {
        Column(Modifier.selectableGroup()) {
            ActivityLevel.entries.forEach { activityLevel ->
                val text = activityLevel.asUiText().asString()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .selectable(
                            selected = (activityLevel == selectedOption),
                            onClick = { selectedOption = activityLevel },
                            role = Role.RadioButton
                        )
                        .padding(DpSpSize.paddingMedium),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (activityLevel == selectedOption),
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