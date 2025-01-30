@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.poputka.presentation.profile.profile_screen.components.bottom_sheets

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.poputka.R

@Composable
fun SettingsBottomSheet(
    title: String,
    onDismissRequest: () -> Unit,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    val paddingMedium = dimensionResource(R.dimen.padding_medium)
    val paddingSmall = dimensionResource(R.dimen.padding_small)

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = rememberModalBottomSheetState()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(
                top = paddingSmall,
                bottom = paddingMedium,
                start = paddingMedium,
                end = paddingMedium
            )
        )

        content()

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
}