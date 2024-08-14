@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.poputka.presentation.sms_verification_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.poputka.R
import com.example.poputka.ui.theme.PoputkaTheme
import kotlinx.coroutines.delay

@Composable
fun SMSVerificationScreen(navController: NavController) {
    val codeTitle = stringResource(id = R.string.sms_verification_screen_title)
    val instructionsText = stringResource(id = R.string.instructions_text)
    val retryViaButtonText = stringResource(id = R.string.retry_the_code_via)
    val retryButtonText = stringResource(id = R.string.retry_the_code)
    val signInButtonText = stringResource(id = R.string.sign_in_text)

    val mediumPadding = dimensionResource(id = R.dimen.padding_medium)
    val largePadding = dimensionResource(id = R.dimen.padding_large)

    var counter by rememberSaveable { mutableIntStateOf(60) }
    var isButtonEnabled by rememberSaveable { mutableStateOf(false) }
    val displayText = if (counter > 0) "$retryViaButtonText $counter с"
    else retryButtonText

    LaunchedEffect(counter) {
        if (counter > 0) {
            delay(1000L)
            counter--
        } else {
            isButtonEnabled = true
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(colors = topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                actionIconContentColor = MaterialTheme.colorScheme.onSecondary
            ),
                title = { Text(text = "top") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                })
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(
                text = codeTitle,
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(bottom = mediumPadding)
            )

            Text(text = instructionsText, modifier = Modifier.padding(bottom = largePadding))

            OutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = largePadding),
                textStyle = TextStyle(textAlign = TextAlign.Center),
                placeholder = {
                    Text(
                        text = "0000",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                })

            ClickableText(
                text = AnnotatedString(displayText),
                modifier = Modifier.padding(bottom = largePadding),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.White,
                    textDecoration = TextDecoration.Underline
                ),
                onClick = {
                    if (isButtonEnabled) {
                        counter = 60
                        isButtonEnabled = false
                    }
                }
            )

            Button(
                onClick = { },
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
            SMSVerificationScreen(rememberNavController())
        }
    }
}