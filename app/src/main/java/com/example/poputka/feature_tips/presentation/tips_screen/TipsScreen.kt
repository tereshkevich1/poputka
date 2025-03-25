package com.example.poputka.feature_tips.presentation.tips_screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.poputka.ui.theme.PoputkaTheme

@Composable
fun TipsScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {

    }
}

@Composable
@Preview
fun TipsScreenPreview(){
    PoputkaTheme {
        TipsScreen()
    }
}