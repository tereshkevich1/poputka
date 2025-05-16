@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.poputka.feature_home.presentation.add_drink_screen.components.date_time_block

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.poputka.R
import com.example.poputka.common.presentation.components.DatePickerModule
import com.example.poputka.common.presentation.components.TimePickerModule
import com.example.poputka.common.presentation.models.DisplayableLong

@Composable
fun TimeRow(
    displayableTime: DisplayableLong,
    displayableDate: DisplayableLong,
    onDateSelect: (Long?) -> Unit,
    onDateDismiss: () -> Unit,
    onTimeSelect: (TimePickerState) -> Unit,
    onTimeDismiss: () -> Unit,
    modifier: Modifier
) {
    var showDateDialog by rememberSaveable { mutableStateOf(false) }
    var showTimeDialog by rememberSaveable { mutableStateOf(false) }

    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {

        Text(stringResource(R.string.time_title))
        Spacer(modifier = Modifier.weight(1f))

        TextButton(onClick = { showDateDialog = true }) {
            Text(displayableDate.formatted)
        }

        TextButton(onClick = { showTimeDialog = true }) {
            Text(displayableTime.formatted)
        }

        if (showDateDialog) {
            DatePickerModule(
                initialSelectedDateMillis = displayableDate.value,
                onDateSelected = {
                    showDateDialog = false
                    onDateSelect(it)
                },
                onDismiss = {
                    showDateDialog = false
                    onDateDismiss()
                })
        }

        if (showTimeDialog) {
            TimePickerModule(
                selectedTime = displayableTime.value,
                onConfirm = {
                    showTimeDialog = false
                    onTimeSelect(it)
                },
                onDismiss = {
                    showTimeDialog = false
                    onTimeDismiss()
                })
        }
    }
}