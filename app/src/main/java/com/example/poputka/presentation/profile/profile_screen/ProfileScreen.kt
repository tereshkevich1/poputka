package com.example.poputka.presentation.profile.profile_screen

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.poputka.presentation.settings.SettingsViewModel
import com.example.poputka.ui.theme.PoputkaTheme

@Composable
fun ProfileScreen(settingsViewModel: SettingsViewModel = hiltViewModel()) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Log.d("settingViewModel", settingsViewModel.toString())
    }
}

@Composable
@Preview
fun ProfileScreenPreview(){
    PoputkaTheme {
        ProfileScreen()
    }
}