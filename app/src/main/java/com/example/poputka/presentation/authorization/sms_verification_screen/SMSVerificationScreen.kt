@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.poputka.presentation.authorization.sms_verification_screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.poputka.presentation.authorization.auth_screen.AuthViewModel
import com.example.poputka.presentation.authorization.auth_screen.util.SendVerificationCodeState
import com.example.poputka.presentation.authorization.sms_verification_screen.components.TopAppBar
import com.example.poputka.presentation.common.components.LoadingButton
import com.example.poputka.presentation.util.CODE_LENGTH
import com.example.poputka.presentation.util.ScreenUIState
import com.example.poputka.ui.theme.PoputkaTheme

@Composable
fun SMSVerificationScreen(
    onNavigateToComposable: () -> Unit,
    onBackPressed: () -> Unit,
    authViewModel: AuthViewModel = hiltViewModel(),
    smsVerificationViewModel: SMSVerificationViewModel = hiltViewModel()
) {
    val sendCodeState by authViewModel.sendCodeState.collectAsState()
    val signInState by authViewModel.signInState.collectAsState()

    val messageIds by authViewModel.messageIds.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    val smsVerifTitle = stringResource(id = R.string.sms_verification_screen_title)
    val instructionsText = stringResource(id = R.string.instructions_text)
    val retryViaButtonText = stringResource(id = R.string.retry_the_code_via)
    val retryButtonText = stringResource(id = R.string.retry_the_code)
    val signInButtonText = stringResource(id = R.string.sign_in_text)
    val appBarTitle = stringResource(id = R.string.sms_verification_app_bar_title)
    val smsCodeInputLabel = stringResource(id = R.string.sms_code_input_label)

    val inputTextStyle = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Center)
    val underlineTextStyle = MaterialTheme.typography.bodyMedium.copy(
        color = Color.White,
        textDecoration = TextDecoration.Underline
    )
    val titleTextStyle = MaterialTheme.typography.displaySmall

    val mediumPadding = dimensionResource(id = R.dimen.padding_medium)
    val largePadding = dimensionResource(id = R.dimen.padding_large)

    val counter = smsVerificationViewModel.counter
    val isButtonEnabled = smsVerificationViewModel.isButtonEnabled

    val displayText = if (counter > 0) "$retryViaButtonText $counter с"
    else retryButtonText

    var verificationCode by rememberSaveable { mutableStateOf("") }

    val isLoading =
        sendCodeState is SendVerificationCodeState.Loading || signInState is ScreenUIState.Loading

    Log.d("SMSVerificationScreen", "signInState: $sendCodeState")

    when (sendCodeState) {
        is SendVerificationCodeState.PhoneNumberVerified -> {
            verificationCode =
                (sendCodeState as SendVerificationCodeState.PhoneNumberVerified).credential.smsCode
                    ?: ""
        }

        else -> Unit
    }

    when (signInState) {
        is ScreenUIState.Success -> {
            onNavigateToComposable()
            LaunchedEffect(snackbarHostState) {
                snackbarHostState.showSnackbar("ВХОД")
            }
        }

        else -> Unit
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = appBarTitle,
                onBackPressed = onBackPressed
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) })
    { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(mediumPadding)
        ) {
            Text(
                text = smsVerifTitle,
                style = titleTextStyle,
                modifier = Modifier.padding(bottom = mediumPadding)
            )

            Text(text = instructionsText, modifier = Modifier.padding(bottom = largePadding))

            OutlinedTextField(
                value = verificationCode,
                onValueChange = {
                    verificationCode = if (it.length >= CODE_LENGTH) {
                        it.substring(0..<CODE_LENGTH)
                    } else it
                },
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = largePadding),
                textStyle = inputTextStyle,
                placeholder = {
                    Text(
                        text = smsCodeInputLabel,
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
                style = underlineTextStyle,
                onClick = {
                    if (isButtonEnabled) {
                        smsVerificationViewModel.disableButton()
                    }
                }
            )

            LoadingButton(
                isLoading = isLoading,
                buttonText = signInButtonText,
                onClick = { authViewModel.getCredential(verificationCode) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            )
        }
    }

    if (messageIds.isNotEmpty()) {
        val messageId = messageIds.first()
        val message = stringResource(id = messageId)

        LaunchedEffect(key1 = messageId) {
            snackbarHostState.showSnackbar(
                message = message,
                withDismissAction = true
            )
            authViewModel.setMessageShown(messageId)
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