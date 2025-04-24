package com.example.poputka.core.global_state.local_settings_state.mappers

import com.example.poputka.feature_settings.domain.model.ActivityLevel

fun String?.toActivityLevelOrDefault(): ActivityLevel {
    return try {
        if (this != null) ActivityLevel.valueOf(this) else ActivityLevel.MODERATE
    } catch (e: IllegalArgumentException) {
        ActivityLevel.MODERATE
    }
}