package com.example.poputka.feature_settings.presentation.settings_screen.components.bottom_sheets.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.poputka.R
import com.example.poputka.ui.theme.DpSpSize.paddingMedium

@Composable
fun ActionButtons(
    onCancelClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingMedium),
    ) {
        val buttonModifier = Modifier
            .weight(1f)
            .height(52.dp)

        OutlinedButton(
            onClick = onCancelClick,
            modifier = buttonModifier
        ) {
            Text(stringResource(R.string.cancel))
        }
        Spacer(modifier = Modifier.width(paddingMedium))
        Button(
            onClick = onSaveClick,
            modifier = buttonModifier
        ) {
            Text(stringResource(R.string.save))
        }
    }
}