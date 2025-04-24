package com.example.poputka.feature_settings.presentation.settings_screen.bottom_sheets.daily_goal_bottom_sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.window.Dialog
import com.example.poputka.R
import com.example.poputka.ui.theme.DpSpSize

@Composable
fun GoalInputDialog(
    inputValue: String,
    errorMessage: String,
    volumeUnit: String,
    onValueChange: (String) -> Unit,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    val focusManager = LocalFocusManager.current

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.extraLarge,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(R.string.daily_goal),
                    modifier = Modifier
                        .padding(DpSpSize.paddingMedium)
                        .align(Alignment.Start),
                    style = MaterialTheme.typography.headlineSmall
                )

                OutlinedTextField(
                    value = inputValue,
                    onValueChange = {
                        onValueChange(it)
                    },
                    placeholder = {
                        Text(text = stringResource(R.string.goal_dialog_placeholder))
                    },
                    supportingText = {
                        if (errorMessage.isNotBlank()) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = errorMessage,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    suffix = { Text(text = volumeUnit) },
                    isError = errorMessage.isNotBlank(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            onConfirmation()
                            focusManager.clearFocus()
                        }
                    ),
                    shape = MaterialTheme.shapes.large,
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .fillMaxWidth()
                        .padding(horizontal = DpSpSize.paddingMedium)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(DpSpSize.paddingSmall),
                    ) {
                        Text(text = stringResource(R.string.cancel))
                    }
                    TextButton(
                        onClick = { onConfirmation() },
                        modifier = Modifier.padding(DpSpSize.paddingSmall),
                        enabled = inputValue.isNotBlank() && errorMessage.isBlank()
                    ) {
                        Text(text = stringResource(R.string.save))
                    }
                }
            }
        }
    }
}