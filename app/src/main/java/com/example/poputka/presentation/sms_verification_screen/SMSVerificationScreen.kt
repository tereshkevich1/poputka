@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.poputka.presentation.sms_verification_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.poputka.R
import com.example.poputka.presentation.auth_screen.AuthViewModel
import com.example.poputka.presentation.sms_verification_screen.components.TopAppBar
import com.example.poputka.ui.theme.PoputkaTheme

@Composable
fun SMSVerificationScreen(
    onNavigateToComposable: () -> Unit,
    onBackPressed: () -> Unit,
    authViewModel: AuthViewModel = hiltViewModel(),
    smsVerificationViewModel: SMSVerificationViewModel = hiltViewModel()
) {
    val smsVerifTitle = stringResource(id = R.string.sms_verification_screen_title)
    val instructionsText = stringResource(id = R.string.instructions_text)
    val retryViaButtonText = stringResource(id = R.string.retry_the_code_via)
    val retryButtonText = stringResource(id = R.string.retry_the_code)
    val signInButtonText = stringResource(id = R.string.sign_in_text)
    val appBarTitle = stringResource(id = R.string.sms_verification_app_bar_title)

    val mediumPadding = dimensionResource(id = R.dimen.padding_medium)
    val largePadding = dimensionResource(id = R.dimen.padding_large)

    val counter = smsVerificationViewModel.counter
    val isButtonEnabled = smsVerificationViewModel.isButtonEnabled

    val displayText = if (counter > 0) "$retryViaButtonText $counter с"
    else retryButtonText

    var verificationCode by rememberSaveable { mutableStateOf("") }

    val inputTextStyle = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Center)

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = appBarTitle,
                onBackPressed = onBackPressed
            )
        })
    { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(mediumPadding)
        ) {
            Text(
                text = smsVerifTitle,
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(bottom = mediumPadding)
            )

            Text(text = instructionsText, modifier = Modifier.padding(bottom = largePadding))

            OutlinedTextField(
                value = verificationCode,
                onValueChange = {
                    verificationCode = if (it.length >= 4) {
                        it.substring(0..3)
                    } else it
                },
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = largePadding),
                textStyle = inputTextStyle,
                placeholder = {
                    Text(
                        text = "0000",
                        modifier = Modifier.fillMaxWidth(),
                        style = inputTextStyle
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )

            ClickableText(
                text = AnnotatedString(displayText),
                modifier = Modifier.padding(bottom = largePadding),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.White,
                    textDecoration = TextDecoration.Underline
                ),
                onClick = {
                    if (isButtonEnabled) {
                        smsVerificationViewModel.disableButton()
                    }
                }
            )

            Button(
                onClick = { onNavigateToComposable() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = signInButtonText)
            }
        }
    }
}

@Composable
@Preview
fun SMSVerificationScreenPreview() {
    PoputkaTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            SMSVerificationScreen({}, {})
        }
    }
}