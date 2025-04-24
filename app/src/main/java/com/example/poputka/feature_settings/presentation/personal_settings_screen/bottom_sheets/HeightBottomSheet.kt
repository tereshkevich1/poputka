package com.example.poputka.feature_settings.presentation.personal_settings_screen.bottom_sheets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.poputka.R
import com.example.poputka.core.presentation.components.selectors.vertical_selector.VerticalSelector
import com.example.poputka.feature_settings.presentation.settings_screen.bottom_sheets.common.SettingsBottomSheet
import com.example.poputka.ui.theme.DpSpSize

@Composable
fun HeightBottomSheet(
    onDismissRequest: () -> Unit,
    onSaveClick: (String?) -> Unit,
    onCancelClick: () -> Unit,
    currentHeight: String
) {
    val heightValues = remember { (100..300).map { it.toString() } }
    val preselectedIndex = heightValues.indexOf(currentHeight)
    var selectedIndex by remember { mutableIntStateOf(0) }

    SettingsBottomSheet(
        title = stringResource(R.string.title_height),
        onDismissRequest = onDismissRequest,
        onSaveClick = { onSaveClick(heightValues.getOrNull(selectedIndex)) },
        onCancelClick = onCancelClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            VerticalSelector(
                values = heightValues,
                onIndexChanged = { selectedIndex = it },
                preselectedIndex = if (preselectedIndex != -1) preselectedIndex else 0
            )

            Spacer(modifier = Modifier.width(DpSpSize.paddingMedium))
            Text(
                text = stringResource(R.string.centimeters),
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}