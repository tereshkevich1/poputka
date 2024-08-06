package com.example.poputka.presentation.AuthScreen.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.KeyboardType
import com.example.poputka.R
import com.example.poputka.presentation.AuthScreen.util.NanpVisualTransformation

@Composable
fun PhoneNumberInputRow(
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val innerPadding = dimensionResource(id = R.dimen.padding_medium)

    Row(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = "+375",
            onValueChange = {},
            modifier = Modifier
                .padding(end = innerPadding)
                .weight(1f),
            readOnly = true,
            singleLine = true
        )
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = onPhoneNumberChange,
            singleLine = true,
            modifier = Modifier.weight(3f),
            visualTransformation = NanpVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
    }
}