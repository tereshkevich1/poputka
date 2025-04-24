package com.example.poputka.core.global_state.local_settings_state

import androidx.compose.runtime.compositionLocalOf

val LocalSettingsState =
    compositionLocalOf { AppPreferencesState() }