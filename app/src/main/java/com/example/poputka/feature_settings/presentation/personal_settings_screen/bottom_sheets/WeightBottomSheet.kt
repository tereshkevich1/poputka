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
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import com.example.poputka.R
import com.example.poputka.common.presentation.components.selectors.vertical_selector.VerticalSelector
import com.example.poputka.feature_settings.presentation.settings_screen.bottom_sheets.common.SettingsBottomSheet
import com.example.poputka.ui.theme.DpSpSize.paddingMedium
import com.example.poputka.ui.theme.DpSpSize.paddingSmall

@Composable
fun WeightBottomSheet(
    currentWeight: Pair<String, String>,
    onDismissRequest: () -> Unit,
    onSaveClick: (integer: String, decimal: String) -> Unit,
    onCancelClick: () -> Unit,
) {
    val weightValues = remember { (11..300).map { it.toString() } }
    val fractions = stringArrayResource(R.array.weight_fraction_values).toList()
    val units = stringArrayResource(R.array.weight_units).toList()

    var integerIndex by remember { mutableIntStateOf(0) }
    var floatIndex by remember { mutableIntStateOf(0) }

    val preselectedIntIndex = weightValues.indexOf(currentWeight.first)
    val preselectedDecimalIndex = currentWeight.second.toIntOrNull() ?: 0

    SettingsBottomSheet(
        title = stringResource(R.string.title_weight),
        onDismissRequest = onDismissRequest,
        onSaveClick = {
            onSaveClick(
                weightValues[integerIndex],
                fractions[floatIndex]
            )
        },
        onCancelClick = onCancelClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            VerticalSelector(
                values = weightValues,
                onIndexChanged = { integerIndex = it },
                preselectedIndex = if (preselectedIntIndex != -1) preselectedIntIndex else 0
            )

            Spacer(modifier = Modifier.width(paddingSmall))
            Text(
                stringResource(R.string.weight_decimal_separator),
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.width(paddingSmall))

            VerticalSelector(
                values = fractions,
                onIndexChanged = { floatIndex = it },
                preselectedIndex = preselectedDecimalIndex
            )

            Spacer(modifier = Modifier.width(paddingMedium))

            VerticalSelector(
                units,
                textStyle = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

