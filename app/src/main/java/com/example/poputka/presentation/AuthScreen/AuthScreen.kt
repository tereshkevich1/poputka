package com.example.poputka.presentation.AuthScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.poputka.R
import com.example.poputka.presentation.AuthScreen.components.PhoneNumberInputRow
import com.example.poputka.presentation.AuthScreen.util.NUMERIC_REGEX
import com.example.poputka.ui.theme.PoputkaTheme

@Composable
fun AuthScreen() {

    val authTitle = stringResource(id = R.string.auth_screen_title)
    val phoneNumberLabel = stringResource(id = R.string.phone_number_label)
    val getCodeButtonText = stringResource(id = R.string.get_code_button_text)
    val innerPadding = dimensionResource(id = R.dimen.padding_medium)
    val largePadding = dimensionResource(id = R.dimen.padding_large)
    val smallPadding = dimensionResource(id = R.dimen.padding_small)

    var phoneNumber by rememberSaveable { mutableStateOf("") }

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            Spacer(modifier = Modifier.fillMaxHeight(0.2f))

            Text(
                text = authTitle,
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(bottom = largePadding)
            )

            Text(text = phoneNumberLabel, modifier = Modifier.padding(bottom = smallPadding))

            PhoneNumberInputRow(
                phoneNumber = phoneNumber,
                onPhoneNumberChange = {
                    val stripped = NUMERIC_REGEX.replace(it, "")
                    phoneNumber = if (stripped.length >= 9) {
                        stripped.substring(0..8)
                    } else {
                        stripped
                    }
                },
                modifier = Modifier
                    .padding(bottom = innerPadding)
            )

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = getCodeButtonText)
            }
        }
    }
}

@Composable
@Preview
fun AuthScreenPreview() {
    PoputkaTheme {
        AuthScreen()
    }
}
