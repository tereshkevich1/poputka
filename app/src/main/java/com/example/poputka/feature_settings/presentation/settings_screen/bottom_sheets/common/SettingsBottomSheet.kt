@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.poputka.feature_settings.presentation.settings_screen.bottom_sheets.common

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.poputka.ui.theme.DpSpSize.paddingMedium
import com.example.poputka.ui.theme.DpSpSize.paddingSmall

@Composable
fun SettingsBottomSheet(
    title: String,
    onDismissRequest: () -> Unit,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
    skipPartiallyExpanded: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = skipPartiallyExpanded)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(
                top = paddingSmall,
                bottom = paddingMedium,
                start = paddingMedium,
                end = paddingMedium
            )
        )

        content()

        ActionButtons(
            onCancelClick = onCancelClick,
            onSaveClick = onSaveClick
        )
    }
}

